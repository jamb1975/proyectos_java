package abaco.compiletime;

import java.util.ArrayList;
import java.util.List;

import static abaco.compiletime.CodeGenerationUtils.solucionArrayToCode;
import static abaco.compiletime.CodeGenerationUtils.stringToCode;

/**
 * Descriptor for a category containing samples and sub categories.
 */
public class SolucionCategory {
    public final String name;
    public final String ensemblePath;
    public final List<Solucion> samples = new ArrayList<Solucion>();
    public final List<Solucion> samplesAll = new ArrayList<Solucion>();
    public final List<SolucionCategory> subCategories = new ArrayList<SolucionCategory>();
    public final SolucionCategory parent;

    public SolucionCategory(String name, String ensemblePath, SolucionCategory parent) {
        this.name = name;
        this.ensemblePath = ensemblePath;
        this.parent = parent;
    }

    public void addSample(Solucion sample) {
        samples.add(sample);
        // find top most category before root
        System.out.println("******** FINDING TOP CATEGORY FOR ["+name+"]");
        SolucionCategory topCategory = this;
        System.out.println("            topCategory = "+topCategory.name);
        while (topCategory.parent != null && topCategory.parent.parent != null) {
            topCategory = topCategory.parent;
            System.out.println("            topCategory = "+topCategory.name);
        }
        System.out.println("            FINAL topCategory = "+topCategory.name);
        // add sample to that categories all samples
        topCategory.samplesAll.add(sample);
    }
    
    public String generateCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("new SampleCategory(");
        sb.append(stringToCode(name)); sb.append(',');
        sb.append(solucionArrayToCode(samples)); sb.append(',');
        sb.append(solucionArrayToCode(samplesAll)); sb.append(',');
        categoryArrayToCode(sb, subCategories);
        sb.append(")");
        return sb.toString();
    }
    
    private void categoryArrayToCode(StringBuilder sb, List<SolucionCategory> array) {
        if (array == null || array.isEmpty()) {
            sb.append("null");
        } else {
            sb.append("new SampleCategory[]{");
            for (SolucionCategory category: array) {
                sb.append(category.generateCode());
                sb.append(',');
            }
            sb.append("}");
        }
    }
}
