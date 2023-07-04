package isoftconta;

import java.util.ServiceLoader;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * A base class for samples - it is recommended that they extend this class
 * rather than Application, as then the samples can be run either standalone
 * or within FXSampler. 
 */
public abstract class ServicioBase extends Application implements Servicios {
    
    /** {@inheritDoc} */
      public static Task<Void> task_consultorios;
      public static Thread thimport_consultorios;
    @Override public void start(Stage primaryStage) throws Exception {
        ServiceLoader<FXServicioConfiguracion> configurationServiceLoader = ServiceLoader.load(FXServicioConfiguracion.class);

        primaryStage.setTitle(getServicioNombre());
        
        Scene scene = new Scene((Parent)buildServicio(this, primaryStage), 800, 800);
        scene.getStylesheets().add(ServicioBase.class.getResource("fxservicio.css").toExternalForm());
        for (FXServicioConfiguracion fXServicioConfiguracion : configurationServiceLoader) {
        	String stylesheet = fXServicioConfiguracion.getSceneStylesheet();
        	if (stylesheet != null) {
            	scene.getStylesheets().add(stylesheet);
        	}
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /** {@inheritDoc} */
    @Override public boolean isVisible() {
        return true;
    }
    
    /** {@inheritDoc} */
    @Override public Node getControlPanel() {
        return null;
    }
    
    /** {@inheritDoc} */
    @Override
    public double getControlPanelDividerPosition() {
    	return 0.6;
    }
    
    /** {@inheritDoc} */
    @Override public String getServicioDescripcion() {
        return "";
    }
    
    /** {@inheritDoc} */
    @Override public String getAreaNombre() {
        return "IsoftContaFX";
    }
    
    /**
     * Utility method to create the default look for samples.
     */
    public static Node buildServicio(Servicios servicios, Stage stage) {
        SplitPane splitPane = new SplitPane();
        
        
        // we guarantee that the build order is panel then control panel.
        final Node servicioPanel = servicios.getPanel(stage);
        final Node controlPanel = servicios.getControlPanel();
        splitPane.setDividerPosition(0, servicios.getControlPanelDividerPosition());
        
        if (servicioPanel != null) {
            splitPane.getItems().add(servicioPanel);
        }
        
        final VBox rightPanel = new VBox();
        rightPanel.getStyleClass().add("right-panel");
        rightPanel.setMaxHeight(Double.MAX_VALUE);

        boolean addRightPanel = false;
        
        Label servicionombre = new Label(servicios.getServicioNombre());
        servicionombre.getStyleClass().add("sample-name");
        rightPanel.getChildren().add(servicionombre);
        
        // --- project name & version
        String version = servicios.getAreaVersion();
        version = version == null ? "" : 
                  version.equals("@version@") ? "" :
                  " " + version.trim();
        
        final String projectName = servicios.getAreaNombre() + version;
        if (projectName != null && ! projectName.isEmpty()) {
            Label projectNameTitleLabel = new Label("Project: ");
            projectNameTitleLabel.getStyleClass().add("project-name-title");
            
            Label projectNameLabel = new Label(projectName);
            projectNameLabel.getStyleClass().add("project-name");
            projectNameLabel.setWrapText(true);
            
            TextFlow textFlow = new TextFlow(projectNameTitleLabel, projectNameLabel);
            rightPanel.getChildren().add(textFlow);
        }
        
        // --- description
        final String description = servicios.getServicioDescripcion();
        if (description != null && ! description.isEmpty()) {
            Label descriptionLabel = new Label(description);
            descriptionLabel.getStyleClass().add("description");
            descriptionLabel.setWrapText(true);
            rightPanel.getChildren().add(descriptionLabel);
            
            addRightPanel = true;
        }
        
        if (controlPanel != null) {
            rightPanel.getChildren().add(new Separator());
            
            controlPanel.getStyleClass().add("control-panel");
            rightPanel.getChildren().add(controlPanel);
            VBox.setVgrow(controlPanel, Priority.ALWAYS);
            addRightPanel = true;
        }
        
        if (addRightPanel) {
            ScrollPane scrollPane = new ScrollPane(rightPanel);
            scrollPane.setMaxHeight(Double.MAX_VALUE);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            SplitPane.setResizableWithParent(scrollPane, false);
            splitPane.getItems().add(scrollPane);
        }
        
        return splitPane;
    }
}
