
package abaco.compiletime;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static abaco.compiletime.CodeGenerationUtils.*;
import abaco.compiletime.Solucion.URL;
import java.util.HashMap;
import javafx.application.ConditionalFeature;

/**
 * Scan all samples, finding all information needed and write a Samples.java file to generated sources.
 */
public class BuildSolucionesList {
    private static final Pattern findJavaDocComment = Pattern.compile("\\/\\*\\*(.*?)\\*\\/\\s*",Pattern.DOTALL);
    private static final Pattern findSharedResource = Pattern.compile("\"(/sofackar/soluciones/shared-resources/[^\"]+)\"",Pattern.DOTALL);
    private static File solucionesSrcDir;
    private static File solucionesResourcesDir;
    private static List<Solucion> highlightedSoluciones = new ArrayList<>();
    private static List<Solucion> allSoluciones = new ArrayList<>();
    
    public static List<Solucion> build(File solucionesSrcDir, File solucionesResourcesDir, File solucionesSourceFile) {
        BuildSolucionesList.solucionesSrcDir = solucionesSrcDir;                 
        File solucionesDir = new File(solucionesSrcDir,"sofackar/soluciones");
        BuildSolucionesList.solucionesSrcDir = solucionesResourcesDir; //Resources are in a different location from *.java files
        File resourcesDir = new File(solucionesResourcesDir, "sofackar/soluciones");
        SolucionCategory rootCategory = new SolucionCategory("ROOT","",null);
        for(File dir: solucionesDir.listFiles()) {
            if (dir.getName().charAt(0) != '.' && !"shared-resources".equals(dir.getName())) {
                processCategoryOrSolucionDir(rootCategory, dir, resourcesDir);
            }
        }
        // generate code chunks so we have all samples in ALL_SAMPLES
        final String rootCategoryCode = rootCategory.generateCode();
        final String solucionesArrayCode = solucionArrayToCode(highlightedSoluciones);
        // check if we want to write
        if (solucionesSourceFile == null) return allSoluciones;
        // write
        PrintWriter fout = null;
        try {
            fout = new PrintWriter(solucionesSourceFile, "UTF-8");
            fout.println("package sofackar.generated;");
            fout.println("import sofackar.*;");
            fout.println("import sofackar.playground.PlaygroundProperty;");
            fout.println("import javafx.application.ConditionalFeature;");
            fout.println("import java.util.HashMap;");
            fout.println("public class Soluciones{");
            // write samples         
            for (int solucionIndex=0; solucionIndex < ALL_SOLUCIONES.size(); solucionIndex ++) {
                fout.print("    private static final SolucionInfo SOLUCION_"+solucionIndex+" = ");
                fout.print(generateCode(ALL_SOLUCIONES.get(solucionIndex)));
                fout.println(";");
            }
            // write root category
            fout.print("    public static final SolucionCategory ROOT = ");
            fout.print(rootCategoryCode);
            fout.println(";");
            // write highlights
            fout.print("    public static final SolucionInfo[] HIGHLIGHTS = ");
            fout.print(solucionesArrayCode);
            fout.println(";");
            // write docs to samples map
            fout.println("    private static final HashMap<String,SolucionInfo[]> DOCS_URL_TO_SAMPLE = new HashMap<String,SolucionInfo[]>("+DOCS_TO_SAMPLE_MAP.size()+");");
            fout.println("    static {");
            for (Map.Entry<String,Set<String>> entry: DOCS_TO_SAMPLE_MAP.entrySet()) {
                fout.println("        DOCS_URL_TO_SAMPLE.put(\""+entry.getKey().replace('$', '.')+"\","+variableNameArrayToCode("SampleInfo",entry.getValue())+");");
            }
            fout.println("    }");
            fout.println("    public static SolucionInfo[] getSamplesForDoc(String docUrl) {");
            fout.println("        return DOCS_URL_TO_SAMPLE.get(docUrl);");
            fout.println("    }");
            // end class and flush
            fout.println("}");
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null) fout.close();
        }
        return allSoluciones;
    }
    
    private static void processCategoryOrSolucionDir(SolucionCategory category, File dir, File resourcesDir) {
        if (!dir.isDirectory()) {
            System.out.println("        found unexpected file: "+dir.getAbsolutePath());
            return;
        }
        // first see if we contain java files, if so assume we are a sample directory
        boolean containsJavaFile = false;
        for(String fileName: dir.list()) {
            if (fileName.endsWith(".java")) {
                containsJavaFile = true;
                break;
            }
        }
        if (containsJavaFile) {
            processSampleDir(category, dir, resourcesDir);
        } else {
            processCategoryDir(category, dir, resourcesDir);
        }
    }
    
    private static void processCategoryDir(SolucionCategory category, File dir, File resourcesDir) {
        System.out.println("========= CATEGORY ["+formatName(dir.getName())+"] ===============");
        // create new category
        SolucionCategory subCategory = new SolucionCategory(
                formatName(dir.getName()),
                category.ensemblePath + "/" + formatName(dir.getName()),
                category);
        category.subCategories.add(subCategory);
        // process each subdir
        for(File subDir: dir.listFiles()) {
            if (subDir.getName().charAt(0) != '.' && !"shared-resources".equals(subDir.getName())) {
                processCategoryOrSolucionDir(subCategory, subDir, resourcesDir);
            }
        }
    }
    
    private static void processSampleDir(SolucionCategory category, File dir, File resourcesDir) {
        Solucion solucion = new Solucion();
        Matcher matcher;
        System.out.println("============== SAMPLE ["+formatName(dir.getName())+"] ===============");
        // calculate base dir relative path
        solucion.baseUri = dir.getAbsolutePath().substring(solucionesSrcDir.getAbsolutePath().length()).replace('\\', '/');
        // find app main file
        File appFile = null;
        for (File file: dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith("App.java")) {
                appFile = file;
                break;
            }
        }
        if (appFile == null) {
            throw new IllegalArgumentException("Could not find JavaFX Application class for sample ["+dir.getName()+"] in ["+dir.getAbsolutePath()+"]");
        }
        solucion.mainFileUrl = calculateRelativePath(appFile, solucionesSrcDir);
        solucion.appClass = solucion.mainFileUrl.substring(1, solucion.mainFileUrl.length()-5).replace('/', '.');
        // load app main file
        StringBuilder appFileContents = loadFile(appFile);
        // parse out java doc comment contents
        matcher = findJavaDocComment.matcher(appFileContents);
        if (!matcher.find()) throw new IllegalArgumentException("Failed to find java doc comment in sample file ["+appFile.getAbsolutePath()+"]");
        String javaDocComment = matcher.group(1);
        String[] lines = javaDocComment.split("([ \\t]*\\n[ \\t]*\\*[ \\t]*)+");
        StringBuilder descBuilder = new StringBuilder();
        for (String jdocline:lines) {
            String trimedLine = jdocline.trim();
            if (trimedLine.length()!= 0) {
                if(trimedLine.startsWith("@related")) {
                    solucion.relatesSamplePaths.add(trimedLine.substring(8).trim());
                } else if(trimedLine.startsWith("@see")) {
                    solucion.apiClasspaths.add(trimedLine.substring(4).trim());
                } else if(trimedLine.startsWith("@docUrl")) {
                    solucion.docsUrls.add(new URL(trimedLine.substring(7).trim()));
                } else if(trimedLine.startsWith("@sampleName")) {
                    // todo resolve to a URL
                    solucion.name = trimedLine.substring(11).trim();
                } else if(trimedLine.startsWith("@preview")) {
                    solucion.previewUrl = solucion.baseUri+"/"+trimedLine.substring(8).trim();
                } else if(trimedLine.startsWith("@highlight")) {
                    highlightedSoluciones.add(solucion);
                } else if(trimedLine.startsWith("@playground")) {
                    String[] parts = trimedLine.substring(11).trim().split("\\s+\\(",2);
                    String name = parts[0].trim();
                    String[] nameparts = name.split("\\.");
                    Map<String,String> properties = new HashMap<>();
                    if (parts.length == 2) {
                        String props = parts[1].substring(0, parts[1].lastIndexOf(')')).trim();
                        parseProperties(props, properties);
                    }
                    String fieldName, propertyName;
                    if (nameparts.length >= 2) {
                        fieldName = nameparts[0].trim();
                        propertyName = nameparts[1].trim();
                    } else {
                        fieldName = null;
                        propertyName = nameparts[0].trim();
                    }
                    solucion.playgroundProperties.add(
                            new Solucion.PlaygroundProperty(fieldName, propertyName, properties));
                } else if (trimedLine.startsWith("@conditionalFeatures")) {
                    String[] features = trimedLine.substring(20).trim().split(",");
                    for (String feature : features) {
                        try {
                            ConditionalFeature cf = ConditionalFeature.valueOf(feature.trim());
                            solucion.conditionalFeatures.add(cf);
                        } catch (IllegalArgumentException ex) {
                            System.err.println("@conditionalFeatures entry is not a feature: " + feature); 
                        }
                    }
                } else if (trimedLine.startsWith("@embedded")) {
                    solucion.runsOnEmbedded = true;
                } else {
                    descBuilder.append(trimedLine);
                    descBuilder.append(' ');
                }
            }
        }
        solucion.description = descBuilder.toString();
        solucion.ensemblePath = category.ensemblePath + "/" + solucion.name;
        // scan sample dir for resources 
        compileResources(solucion, dir, true, solucionesSrcDir);
        // scan samples/resources dir for resources too
        compileExtraResources(solucion, resourcesDir, true, solucionesResourcesDir); 
        // add sample to category
        System.out.println(solucion);
        category.addSample(solucion);
        // add to all samples
        allSoluciones.add(solucion);
    }
    
    private static void compileResources(Solucion solucion, File dir, boolean root, File baseDir) {
        for (File file: dir.listFiles()) {
            if (file.getName().charAt(0) != '.') { // ignore hidden unix files
                if (file.isDirectory()) {
                    compileResources(solucion, file, false, baseDir);
                } else {
                    if (root && (file.getName().equalsIgnoreCase("preview.png") 
                            || file.getName().equalsIgnoreCase("preview@2x.png"))) {
                        continue; // ignore preview files
                    }
                    if (file.getName().endsWith(".java")) {
                        // search for usage of shared resouces
                        StringBuilder fileContents = loadFile(file);
                        Matcher matcher = findSharedResource.matcher(fileContents);
                        while(matcher.find()) {
                            solucion.resourceUrls.add(matcher.group(1));
                        }
                    }
                    // add file to resources list
                    solucion.resourceUrls.add(calculateRelativePath(file, baseDir));
                }
            }
        }
    }
   
  private static void compileExtraResources(Solucion solucion, File dir, boolean root, File baseDir) {
      File specificResDir = new File(solucionesResourcesDir, solucion.baseUri.toString());
        for (File file: specificResDir.listFiles()) {
            if (file.getName().charAt(0) != '.') { // ignore hidden unix files
                if (file.isDirectory()) {
                    compileResources(solucion, file, false, baseDir);
                } else {
                    if (file.getName().equalsIgnoreCase("preview.png")
                            || file.getName().equalsIgnoreCase("preview@2x.png")) {
                        continue; // ignore preview files
                    }
                    // add file to resources list
                    solucion.resourceUrls.add(calculateRelativePath(file, baseDir));
                }
            }
        }
    }
     
    private static String calculateRelativePath(File file, File baseDir) {
        return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length()).replace('\\', '/');
    }
    
    private static StringBuilder loadFile(File file) {
        StringBuilder builder = new StringBuilder();
        InputStream in = null;
        try {
            // load src into String
            in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(BuildSolucionesList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException ex) {
                Logger.getLogger(BuildSolucionesList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return builder;
    }
    
    private static String formatName(String dirName) {
        // remove ending "Sample" from name
        if(dirName.endsWith("Sample")) dirName = dirName.substring(0,dirName.length()-"Sample".length());
        // add spaces
        dirName = dirName.replaceAll("([\\p{Upper}\\d])"," $1");
        // uppercase first char
        dirName = dirName.substring(0,1).toUpperCase() + dirName.substring(1);
        return dirName.trim();
    }

    private static void parseProperties(String props, Map<String, String> properties) throws RuntimeException {
        boolean failed = true;
        StreamTokenizer st = new StreamTokenizer(new StringReader(props));
        st.resetSyntax();
        st.wordChars('\u0001', '\uFFFF');
        st.quoteChar('"');
        st.quoteChar('\'');
        st.ordinaryChar(',');
        st.ordinaryChar('=');
        st.whitespaceChars(' ', ' ');
        st.whitespaceChars('\t', '\t');
        try {
            int t = st.nextToken();
            do {
                if (t == StreamTokenizer.TT_EOF || t == StreamTokenizer.TT_EOL) {
                    break;
                }
                if (t != StreamTokenizer.TT_WORD) {
                    throw new RuntimeException("Property name expected here: " + st.toString());
                }
                String name = st.sval.trim();
                t = st.nextToken();
                if (t != '=') {
                    throw new RuntimeException("'=' expected here: " + st.toString());
                }
                t = st.nextToken();
                if (t != StreamTokenizer.TT_WORD && t != '"' && t != '\'') {
                    throw new RuntimeException("Property value expected here: " + st.toString());
                }
                String value = st.sval.trim();
                properties.put(name, value);
                t = st.nextToken();
                if (t == ',') {
                    t = st.nextToken();
                }
            } while (true);
            failed = false;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to parse properties due to unexpected IOException", ex);
        } finally {
            if (failed) {
                System.err.println("Failed to parse: " + props);
            }
        }
    }
}
