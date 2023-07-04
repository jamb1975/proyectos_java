package fxnomina;

import generated.Soluciones;
import util.FeatureChecker;

/**
 * Descriptor for a category containing samples and sub categories.
 */
public class SolucionCategory {

    public final String name;
    public final String title;
    public  SolucionInfo[] soluciones;
    public  SolucionInfo[] solucionesAll;
    public  SolucionCategory[] subCategories;

    public SolucionCategory(String name, SolucionInfo[] soluciones, SolucionInfo[] solucionesAll, SolucionCategory[] subCategories, String title) {
        this.name = name;
        this.soluciones = FeatureChecker.filterSamples(soluciones);
        this.solucionesAll = FeatureChecker.filterSamples(solucionesAll);
        this.subCategories = FeatureChecker.filterEmptyCategories(subCategories);
        this.title = title;
    }

    public SolucionInfo solucionForPath(String path) {
        if (path.charAt(0) == '/') { // absolute path
            return Soluciones.ROOT.solucionForPath(path.split("/"), 1);
        } else {
            return solucionForPath(path.split("/"), 0);
        }
    }

    private SolucionInfo solucionForPath(String[] pathParts, int index) {
        String part = pathParts[index];
        if (soluciones != null) {
            for (SolucionInfo solucion : soluciones) {
                if (solucion.name.equals(part)) {
                    return solucion;
                }
            }
        }
        if (subCategories != null) {
            for (SolucionCategory category : subCategories) {
                if (category.name.equals(part)) {
                    return category.solucionForPath(pathParts, index + 1);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
