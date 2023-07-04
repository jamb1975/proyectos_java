
package  abaco.soluciones.informacionadministrativa;

import abaco.soluciones.informacionadministrativa.AdminPuc;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import model.ConPuc;
import abaco.controlador.ConPucControllerClient;



/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FPuc extends Application{
    StackPane stack;
    private ConPuc  conPuc;
    private GridPane gp_conpuc;
    private  Label la_nombre;
    private  Label la_codigo;
    private  Label la_descripcion;
    private  TextField codigo;
    private  TextField nombre;
    private  TextField descripcion;
    private ConPucControllerClient conpucControllerClient;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso=false;
    private ImageView imageView;
    private ObservableList ol_conpuc=FXCollections.observableArrayList();;
    private List<ConPuc>  l_conpuc;
    private GridPane Gp_Message;
    private     Label L_Message;
    private  Runnable Task_Message;
    Thread backgroundThread;
    private static boolean estadoeditar;
    public Parent createContent() throws IOException {
    conpucControllerClient=new ConPucControllerClient();
     imageView=new ImageView("/images/new2.png");
     imageView.setFitHeight(16);
           imageView.setFitWidth(16);
           nuevo=new Button("",imageView);
           //nuevo.setMaxWidth(130);  
          nuevo.setOnAction(e->
          {
           try {
              
             nuevo();
           } catch (Exception ex) {
             
           }
          });            
          
      //***********************************************  
        estadosMensaje=new EstadosMensaje();
        stack = new StackPane();
        conPuc=new ConPuc();
        la_codigo=new Label("Código:");
        la_nombre=new Label("Nombre:");
        la_descripcion=new Label("Descripción:");
        codigo=new TextField();
        nombre=new TextField();
        descripcion=new TextField();
        ImageView imageView=new ImageView("/images/bs_save.gif");
        save =new Button("",imageView);
        hb_botones=new HBox(10);
        hb_botones.getChildren().addAll(save,nuevo);
        save.setOnAction(e->{try {
            try {
                save();
            } catch (ParseException ex) {
            }
              } catch (InterruptedException ex) {
                 
              }
});
        
       
        // gridpane
        gp_conpuc=new GridPane();
        gp_conpuc.addRow(0,la_codigo, codigo);
        gp_conpuc.addRow(1,la_nombre,nombre);
        gp_conpuc.addRow(2,la_descripcion,descripcion);
        gp_conpuc.add(hb_botones, 0, 4,2,1);
    
        GridPane.setHalignment(hb_botones, HPos.CENTER);
       //gp_hclformulacionesmedicas.getStylesheets().add("/sihic/SofackarStylesCommon.css");
       //gp_hclformulacionesmedicas.getStyleClass().add("background");
        gp_conpuc.setHgap(5);
        gp_conpuc.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_conpuc.getStylesheets().add("/sihic/SofackarStylesCommon.css"); 
        gp_conpuc.getStyleClass().add("background");
        Gp_Message=new GridPane();
        Gp_Message=new GridPane();
        Gp_Message.setMinSize(gp_conpuc.getLayoutBounds().getHeight(),gp_conpuc.getLayoutBounds().getWidth());
        L_Message=new Label();
        L_Message.getStylesheets().add("/sihic/SofackarStylesCommon.css"); 
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
         L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
         GridPane.setValignment(L_Message, VPos.TOP);
          GridPane.setValignment(Gp_Message, VPos.TOP);
          Gp_Message.setVisible(false);
        stack.getChildren().addAll(gp_conpuc,Gp_Message);
        crearoeditar();
    return stack;
    }
  
 
   
      @Override
    public void start(Stage primaryStage) throws Exception {
 
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

public void nuevo()
{
    conPuc=null;
    conPuc=new ConPuc();
    codigo.setText("");
    nombre.setText("");
    descripcion.setText("");
}
public void save() throws InterruptedException, ParseException
{ validar_formulario();
  if(permitirproceso)
  {
  set_datos();
  try{
  conpucControllerClient.create(conPuc);
  L_Message.setText("Registro Almacenado"); 
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
}

private void set_datos()
{
    int i=0;
        
       conPuc.setCodigo(codigo.getText());
       conPuc.setDescripcion(descripcion.getText());
       conPuc.setNombre(nombre.getText());
       conPuc.setConpuc_id(AdminPuc.getConPuc());
           
       
       
}

public void validar_formulario() throws InterruptedException
{
    permitirproceso=true;
      for(Object n:gp_conpuc.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            if(general.getProperties().get("requerido")!=null)
            {
            if((general.getText().length()<=0) && ((boolean)general.getProperties().get("requerido")==true))
            {
                  permitirproceso=false; 
                  general.setStyle("-fx-background-color:#ff1600");
                  
            }
            }
           
        }
        else
        {
             if(n.getClass()==ChoiceBox.class)
        {
            ChoiceBox general=(ChoiceBox)n;
           
         if(general.getSelectionModel().getSelectedIndex()<0)
         {
                  permitirproceso=false; 
                  general.getStylesheets().add(0,"/sihic/personal.css");
               general.getStylesheets().set(0,"/sihic/personal.css");
         }  
           
        }
        }
        }
     FadeTransition ft = new FadeTransition(Duration.millis(2000),save);
        //ft.setFromValue(0.0);
       // ft.setToValue(1);
      
        ft.play();
       
        //success.setOpacity(0);
      
       ft.setOnFinished(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
              for(Object n:gp_conpuc.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            if(general.getProperties().get("requerido")!=null)
            {
                System.out.println("propiedad-->"+general.getProperties().get("requerido"));
            if(((boolean)general.getProperties().get("requerido")==true))
            {
                 
                  general.setStyle(null);
                  general.getStyleClass().add("text-field");
            }
            }
           
        }  
         else
        {
             if(n.getClass()==ChoiceBox.class)
        {
            ChoiceBox general=(ChoiceBox)n;
           
         if(general.getSelectionModel().getSelectedIndex()<0)
         {
             general.getStylesheets().set(0,"/sihic/SofackarStylesCommon.css");
             
         }
           
        }
        }
        }
        }});
        
         
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
public static boolean isEstadoeditar() {
        return estadoeditar;
    }

    public static void setEstadoeditar(boolean estadoeditar) {
        estadoeditar = estadoeditar;
    }
    public void crearoeditar()
{
    conPuc=AdminPuc.getConPuc();
    if(conPuc.getId()!=null)
    {
        codigo.setText(String.valueOf(conPuc.getCodigo()));
        nombre.setText(String.valueOf(conPuc.getNombre()));
        descripcion.setText(conPuc.getDescripcion());
    }
   
}
    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}