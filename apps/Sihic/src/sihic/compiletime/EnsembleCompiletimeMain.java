package sihic.compiletime;

import sihic.compiletime.search.BuildEnsembleSearchIndex;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * This is run during the build by ant to build sample files and search indexes
 */
public class EnsembleCompiletimeMain {

    static {
        System.setProperty("java.net.useSystemProxies", "true");
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("                 Ensemble Compile Time Build");
        System.out.println("args = " + Arrays.toString(args));
        System.out.println("==================================================================");
        File ensembleDir = new File(System.getProperty("user.dir"));
        File generatedSrcDir = new File(ensembleDir, "src/generated/java/sofackar/generated");
        generatedSrcDir.mkdirs();
        File samplesDir = new File(ensembleDir, "src/soluciones/java");
        File resourcesDir = new File(ensembleDir, "src/soluciones/resources");
        // process args
        boolean buildSearchIndex = false, buildSampleClass = false;
        for (int a = 0; a < args.length; a++) {
            if (args[a].equalsIgnoreCase("index")) {
                buildSearchIndex = true;
            }
            if (args[a].equalsIgnoreCase("soluciones")) {
                buildSampleClass = true;
            }
        }
        // build samples list
        List<Solucion> allSamples = BuildSolucionesList.build(samplesDir, resourcesDir, buildSampleClass ? new File(generatedSrcDir, "Samples.java") : null);
        System.out.println("TOTAL SAMPLES = " + allSamples.size());
        if (buildSampleClass) {
            System.out.println("==================================================================");
            System.out.println("                 Written Samples.java class file");
            System.out.println("==================================================================");
        }

        System.out.println("buildSearchIndex = " + buildSearchIndex);
        if (buildSearchIndex) {
            System.out.println("==================================================================");
            System.out.println("                     Building Search Index");
            System.out.println("==================================================================");
            File indexDir = new File(ensembleDir, "src/generated/resources/sofackar/search/index");
            indexDir.mkdirs();
            BuildEnsembleSearchIndex.buildSearchIndex(
                    allSamples,
                    "http://download.java.net/jdk8/jfxdocs/",
                    "http://docs.oracle.com/javafx/index.html",
                    indexDir);
        }
    }
}
