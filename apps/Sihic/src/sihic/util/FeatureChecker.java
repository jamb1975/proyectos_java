package sihic.util;

import sihic.PlatformFeatures;
import sihic.SolucionCategory;
import sihic.SolucionInfo;
import java.util.ArrayList;
import java.util.List;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;

public class FeatureChecker {

    public static boolean isSampleSupported(SolucionInfo solucion) {
        ConditionalFeature[] cf = solucion.conditionalFeatures;
        for (ConditionalFeature oneCF : cf) {
            if (!Platform.isSupported(oneCF)) {
                return false;
            }
        }

        if (PlatformFeatures.USE_EMBEDDED_FILTER && !solucion.runsOnEmbedded) {
            return false;
        }
        return true;
    }

    public static SolucionInfo[] filterSamples(SolucionInfo[] samples) {
        if (samples != null) {
            List<SolucionInfo> filteredSampleInfos = new ArrayList<>();
            for (SolucionInfo oneSampleInfo : samples) {
                if (isSampleSupported(oneSampleInfo)) {
                    filteredSampleInfos.add(oneSampleInfo);
                }
            }
            return filteredSampleInfos.toArray(new SolucionInfo[filteredSampleInfos.size()]);
        } else {
            return null;
        }
    }

    public static SolucionCategory[] filterEmptyCategories(SolucionCategory[] subCategories) {
        if (subCategories != null) {
            List<SolucionCategory> filteredSubcategories = new ArrayList<>();
            for (SolucionCategory subCategory : subCategories) {
                if (subCategory.soluciones != null && subCategory.soluciones.length > 0
                        || subCategory.solucionesAll != null && subCategory.solucionesAll.length > 0) {
                    filteredSubcategories.add(subCategory);
                }

            }
            return filteredSubcategories.toArray(new SolucionCategory[filteredSubcategories.size()]);
        } else {
            return null;
        }
    }
}
