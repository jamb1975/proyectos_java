package isoftconta.model;

import isoftconta.Servicios;


public class Area {
    
    private final String name;
    
    private final String basePackage;

    // A Project has a Tree of samples
    private final ServicioTree sampleTree;
    
    // Pojo that holds the welcome tab content and title
    private WelcomePage welcomePage;
    
    public Area(String name, String basePackage) {
        this.name = name;
        this.basePackage = basePackage;
        this.sampleTree = new ServicioTree(new EmptyServicio(name));
    }

    public void addServicio(String packagePath, Servicios servicios) {
        // convert something like 'org.controlsfx.samples.actions' to 'samples.actions'
        String packagesWithoutBase = "";
        try {
            if (! basePackage.equals(packagePath)) {
                packagesWithoutBase = packagePath.substring(basePackage.length() + 1);
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("packagePath: " + packagePath + ", basePackage: " + basePackage);
            e.printStackTrace();
            return;
        }
        
        // then split up the packages into separate strings
        String[] packages = packagesWithoutBase.isEmpty() ? new String[] { } : packagesWithoutBase.split("\\.");
        
        // then for each package convert to a prettier form
        for (int i = 0; i < packages.length; i++) {
            String packageName = packages[i];
            if (packageName.isEmpty()) continue;
            
            packageName = packageName.substring(0, 1).toUpperCase() + packageName.substring(1);
            packageName = packageName.replace("_", " ");
            packages[i] = packageName;
        }
        
        // now we have the pretty package names, we add this sample into the
        // tree in the appropriate place
        sampleTree.addServicio(packages, servicios);
    }
    
    public ServicioTree getServicioTree() {
        return sampleTree;
    }
    
    public void setWelcomePage(WelcomePage welcomePage) {
        if(null != welcomePage) {
            this.welcomePage = welcomePage;
        }
    }
    
    public WelcomePage getWelcomePage() {
        return this.welcomePage;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Project [ name: ");
        sb.append(name);
        sb.append(", sample count: ");
        sb.append(sampleTree.size());
        sb.append(", tree: ");
        sb.append(sampleTree);
        sb.append(" ]");
        
        return sb.toString();
    }
}
