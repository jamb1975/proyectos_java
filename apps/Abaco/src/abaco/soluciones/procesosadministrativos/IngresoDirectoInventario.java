
package  abaco.soluciones.procesosadministrativos;

import abaco.soluciones.informacionadministrativa.AdminUnidadesMedida;
import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import com.aquafx_project.AquaFx;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;
import message.EstadosMensaje;
import org.aerofx.AeroFX;





/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class IngresoDirectoInventario extends Application {
    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
  
   
   
    private GridPane gp_generic;
    private TextField  cantidad ;
    private Button save;
    
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private GridPane Gp_Message;
    private     Label L_Message;
    private  Runnable Task_Message;
    private boolean permitirproceso=false;
    Thread backgroundThread;
   public Parent createContent() throws IOException {
   
      //***********************************************  
        estadosMensaje=new EstadosMensaje();
        stack = new StackPane();
       
        cantidad=new TextField("0");
        cantidad.setMinWidth(400);
      
        ImageView imageView=new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        save =new Button("",imageView);
        save.setTooltip(new Tooltip("Guardar"));
        hb_botones=new HBox(2);
       
        save.setOnAction(e->{try {
            save();
              } catch (InterruptedException ex) {
                 
              }
         });
        imageView=null;
        imageView=new ImageView("/images/new2.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
       
         hb_botones=new HBox(10);
         hb_botones.getChildren().addAll(save);
        
// gridpane
        gp_generic=new GridPane();
        gp_generic.add(new Label("Cantidad:"), 0, 0);
        gp_generic.add(cantidad, 1, 0);
        
          gp_generic.add(hb_botones, 0, 2,2,1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
         gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_CENTER);
       // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
       // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message=new GridPane();
        Gp_Message=new GridPane();
        Gp_Message.setMinWidth(gp_generic.getLayoutBounds().getWidth());
        Gp_Message.setMaxHeight(40);
        L_Message=new Label();
        L_Message.getStylesheets().add("/abaco/SofackarStylesCommon.css"); 
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic,Gp_Message);
        switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gp_generic);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gp_generic);
                     break;              
         }
       if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()!=0)
        {
          save.setStyle("-fx-background-color:#007fff");
          
                 
        }
    return stack;
    }
  
 
   
      @Override
    public void start(Stage primaryStage) throws Exception {
 
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

public void save() throws InterruptedException
{ 
  try{
         AbacoApp.facturaProveedoresControllerClient.getlastitemfactura(AbacoApp.s_kardex.getProducto(), Float.valueOf(cantidad.getText().trim()));
         L_Message.setText("Inventario modificado"); 
  
   
    Task_Message = () -> {
         try {
             SetMessage();
            
         } catch (InterruptedException ex) {
             
         }
     };
     backgroundThread = new Thread(Task_Message);
      // Terminate the running thread if the application exits
       backgroundThread.setDaemon(true);
 
       // Start the thread
      backgroundThread.start();
  }catch(Exception e)
  {
      e.printStackTrace();
     L_Message.setText("Error Almacenando"); 
    Task_Message = () -> {
         try {
             SetMessage();
         } catch (InterruptedException ex) {
             
         }
     };
     backgroundThread = new Thread(Task_Message);
      // Terminate the running thread if the application exits
       backgroundThread.setDaemon(true);
 
       // Start the thread
      backgroundThread.start();
  
  
}
}







private void   SetMessage() throws InterruptedException
    {
       
       Gp_Message.setVisible(true);
       //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
         Thread.sleep(3000);
         Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
         Task_Message=null;
       
    }






    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}