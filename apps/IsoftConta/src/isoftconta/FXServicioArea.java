package isoftconta;

import isoftconta.model.WelcomePage;

public interface FXServicioArea {

    /**
     * Returns the pretty name of the project, e.g. 'JFXtras' or 'ControlsFX'
     */
    public String getAreaNombre();
    
    /**
     * All samples should be beneath this base package. For example, in ControlsFX,
     * this may be 'org.controlsfx.samples'.
     */
    public String getServicioBasePackage();
    
    /**
     * Node that will be displayed in welcome tab, when project's root is
     * selected in the tree. If this method returns null, default page will 
     * be used
     */
    public WelcomePage getWelcomePage();
}
