
package abaco;

import abaco.generated.Soluciones;
import abaco.util.FeatureChecker;

/**
 * Descriptor for a category containing samples and sub categories.
 */
public class SolucionCategory {
    public final String name;
    public final String title;
    /* samples contained in this category directly */
    public final SolucionInfo[] soluciones;
    /* samples contained in this category directly and all sub categories recursively */
    public final SolucionInfo[] solucionesAll;
    public final SolucionCategory[] subCategories;

    public SolucionCategory(String name, SolucionInfo[] soluciones, SolucionInfo[] solucionesAll, SolucionCategory[] subCategories,String title) {
        this.name = name;
        this.soluciones = FeatureChecker.filterSamples(soluciones);
        this.solucionesAll = FeatureChecker.filterSamples(solucionesAll);
        this.subCategories = FeatureChecker.filterEmptyCategories(subCategories);
        this.title=title;
    }
    
    public SolucionInfo solucionForPath(String path) {
        if (path.charAt(0) == '/') { // absolute path
            return Soluciones.ROOT.solucionForPath(path.split("/"),1);
        } else {
            return solucionForPath(path.split("/"),0);
        }
    }
    
    private SolucionInfo solucionForPath(String[] pathParts, int index) {
        String part = pathParts[index];
        if (soluciones!=null) for (SolucionInfo solucion: soluciones) {
            if (solucion.name.equals(part))
                return solucion;
        }
        if (subCategories!=null) for (SolucionCategory category: subCategories) {
            if (category.name.equals(part)) 
                return category.solucionForPath(pathParts, index + 1);
        }
        return null;
    }
    
    @Override public String toString() {
        return name;
    }
}
