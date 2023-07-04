//INSSEND JAVIER MURCIA
package isoftconta;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 */
public interface Servicios {

    /**
     * A short, most likely single-word, name to show to the user - e.g. "CheckBox"
     */
    public String getServicioNombre();

    /**
     * A short, multiple sentence description of the sample. 
     */
    public String getServicioDescripcion();
    
    /**
     * Returns the name of the project that this sample belongs to (e.g. 'JFXtras'
     * or 'ControlsFX').
     */
    public String getAreaNombre();
    
    /**
     * Returns the version of the project that this sample belongs to (e.g. '1.0.0')
     */
    public String getAreaVersion();
    
    /**
     * Returns the main sample panel.
     */
    public Node getPanel(final Stage stage);

    /**
     * Returns the panel to display to the user that allows for manipulating
     * the sample.
     */
    public Node getControlPanel();
    
    /**
     * Returns divider position to use for split between main panel and control panel 
     */
    public double getControlPanelDividerPosition();

    
    /**
     * If true this sample is shown to users, if false it is not.
     */
    public boolean isVisible();

}