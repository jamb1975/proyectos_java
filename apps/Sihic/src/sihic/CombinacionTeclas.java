package sihic;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CombinacionTeclas extends Application {

    StackPane stack;
    
    public static GridPane gp_generic;
  
        public Parent createContent() throws IOException {
         stack = new StackPane();
        gp_generic = new GridPane();
         gp_generic.getStylesheets().add(SihicApp.hojaestilos);
         gp_generic.setHgap(5);
         gp_generic.setVgap(5);
        gp_generic.getStyleClass().add("background");
        gp_generic.addRow(0,new Label("Ctrl+B"),new Label("Citas"));
        gp_generic.addRow(1,new Label("Ctrl+P"),new Label("Pacientes"));
        gp_generic.addRow(2,new Label("Ctrl+F"),new Label("Facturar"));
        gp_generic.addRow(3,new Label("Ctrl+O"),new Label("Cerrar Facturas"));
        gp_generic.addRow(4,new Label("Ctrl+M"),new Label("Modificar Facturas"));
        gp_generic.addRow(5,new Label("Ctrl+N"),new Label("Notas de Estudio"));
        gp_generic.addRow(6,new Label("Ctrl+D"),new Label("Convenios"));
        gp_generic.addRow(7,new Label("Ctrl+I"),new Label("Inv Servicios"));
        gp_generic.addRow(8,new Label("Ctrl+R"),new Label("Rips"));
        gp_generic.addRow(9,new Label("Ctrl+E"),new Label("Modificar Comprobantes"));
        gp_generic.addRow(10,new Label("Ctrl+T"),new Label("Admin Medicamentos"));
        gp_generic.addRow(11,new Label("Ctrl+S"),new Label("Admin Insumos"));
        
       
   
      
        return gp_generic;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

   

    public static void main(String[] args) {
        launch(args);
    }
}
