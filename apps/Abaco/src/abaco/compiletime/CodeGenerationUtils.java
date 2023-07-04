package abaco.compiletime;

import abaco.compiletime.Solucion.URL;
import java.util.*;
import javafx.application.ConditionalFeature;

/**
 * A utils class containing static methods that are useful when generating Java code.
 */
public class CodeGenerationUtils {
    public static List<Solucion> ALL_SOLUCIONES = new ArrayList<Solucion>();
    public static Map<String,Set<String>> DOCS_TO_SAMPLE_MAP = new HashMap<String, Set<String>>();

    public static String generateSolucionRef(Solucion solucion) {
        int solucionIndex = ALL_SOLUCIONES.indexOf(solucion);
        String solucionVarName;
        if (solucionIndex < 0) {
            solucionIndex = ALL_SOLUCIONES.size();
            solucionVarName = getSolucionVarName(solucionIndex);
            ALL_SOLUCIONES.add(solucion);
            // add any document references to DOCS_TO_SAMPLE_MAP
            for(String docRef: solucion.apiClasspaths) {
                addDocRef(docRef,solucionVarName);
            }
            for(URL docRef: solucion.docsUrls) {
                addDocRef(docRef.url, solucionVarName);
            }
        } else {
            solucionVarName = getSolucionVarName(solucionIndex);
        }
        return solucionVarName;
    }

    private static void addDocRef(String docUrl, String solucionVarName) {
        Set<String> docRefList = DOCS_TO_SAMPLE_MAP.get(docUrl);
        if (docRefList == null) {
            docRefList = new HashSet<>();
            DOCS_TO_SAMPLE_MAP.put(docUrl,docRefList);
        }
        docRefList.add(solucionVarName);
    }

    public static String getSolucionVarName(int solucionIndex) {
        return "SOLUCION_"+solucionIndex;
    }

    public static String generateCode(Solucion solucion) {
        StringBuilder sb = new StringBuilder();
        sb.append("new SolucionInfo(");
        sb.append(stringToCode(solucion.name)); sb.append(',');
        sb.append(stringToCode(solucion.description)); sb.append(',');
        sb.append(stringToCode(solucion.ensemblePath)); sb.append(',');
        sb.append(stringToCode(solucion.baseUri)); sb.append(',');
        sb.append(stringToCode(solucion.appClass)); sb.append(',');
        sb.append(stringToCode(solucion.previewUrl)); sb.append(',');
        sb.append(stringArrayToCode(solucion.resourceUrls)); sb.append(',');
        sb.append(stringArrayToCode(solucion.apiClasspaths)); sb.append(',');
        sb.append(urlArrayToCode(solucion.docsUrls)); sb.append(',');
        sb.append(stringArrayToCode(solucion.relatesSamplePaths)); sb.append(',');
        sb.append(stringToCode(solucion.mainFileUrl)); sb.append(',');
        sb.append(playgroundPropertyArrayToCode(solucion.playgroundProperties)); sb.append(',');
        sb.append(conditionalFeatureArrayToCode(solucion.conditionalFeatures)); sb.append(',');
        sb.append(Boolean.toString(solucion.runsOnEmbedded));
        sb.append(")");      
        return sb.toString();
    }

    public static String playgroundPropertyArrayToCode(List<Solucion.PlaygroundProperty> array) {
        StringBuilder sb = new StringBuilder();
        sb.append("new PlaygroundProperty[]{");
        for (Solucion.PlaygroundProperty prop: array) {
            sb.append("new PlaygroundProperty(");
            sb.append(stringToCode(prop.fieldName));
            sb.append(',');
            sb.append(stringToCode(prop.propertyName));
            for (Map.Entry<String,String> entry: prop.properties.entrySet()) {
                sb.append(',');
                sb.append(stringToCode(entry.getKey()));
                sb.append(',');
                sb.append(stringToCode(entry.getValue()));
            }
            sb.append("),");
        }
        sb.append("}");
        return sb.toString();
    }
 
    public static String conditionalFeatureArrayToCode(List<ConditionalFeature> array) {
        StringBuilder sb = new StringBuilder();
        sb.append("new ConditionalFeature[]{");
        for (ConditionalFeature feature : array) {
            sb.append("ConditionalFeature.");
            sb.append(feature.name());
            sb.append(',');
        }
        sb.append("}");
        return sb.toString();
    }
    
    public static String solucionArrayToCode(List<Solucion> array) {
        if (array == null || array.isEmpty()) return "null";
        StringBuilder sb = new StringBuilder();
        sb.append("new SolucionInfo[]{");
        for (Solucion solucion: array) {
            sb.append(generateSolucionRef(solucion));
            sb.append(',');
        }
        sb.append("}");
        return sb.toString();
    }

    public static String stringArrayToCode(List<String> array) {
        StringBuilder sb = new StringBuilder();
        sb.append("new String[]{");
        for (String string: array) {
            sb.append(stringToCode(string));
            sb.append(',');
        }
        sb.append("}");
        return sb.toString();
    }

    public static String urlArrayToCode(List<URL> array) {
        StringBuilder sb = new StringBuilder();
        sb.append("new String[]{");
        for (URL url: array) {
            sb.append(stringToCode(url.url));
            sb.append(',');
            sb.append(stringToCode(url.name));
            sb.append(',');
        }
        sb.append("}");
        return sb.toString();
    }

    public static String variableNameArrayToCode(String className, Collection<String> array) {
        StringBuilder sb = new StringBuilder();
        sb.append("new "+className+"[]{");
        for (String string: array) {
            sb.append(string);
            sb.append(',');
        }
        sb.append("}");
        return sb.toString();
    }

    public static String stringToCode(String string) {
        if (string == null) {
            return "null";
        } else {
            return '\"' + string.replaceAll("\\\\", "\\\\\\\\") + '\"';
        }
    }
}
