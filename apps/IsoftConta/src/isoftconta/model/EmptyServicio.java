package isoftconta.model;

import javafx.scene.Node;
import javafx.stage.Stage;
import isoftconta.Servicios;

public class EmptyServicio implements Servicios {
    private final String name;

    public EmptyServicio(String name) {
        this.name = name;
    }

    @Override public String getServicioNombre() {
        return name;
    }

    @Override public String getServicioDescripcion() {
        return null;
    }
    
    @Override public String getAreaNombre() {
        return null;
    }

	@Override
	public String getAreaVersion() {
		return null;
	}
	
    @Override public Node getPanel(Stage stage) {
        return null;
    }

   
    @Override public boolean isVisible() {
        return true;
    }

    @Override public Node getControlPanel() {
        return null;
    }
    
    public double getControlPanelDividerPosition() {
    	return 0.6;
    }



}