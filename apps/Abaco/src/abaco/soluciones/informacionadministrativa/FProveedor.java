
package  abaco.soluciones.informacionadministrativa;

import abaco.soluciones.informacionadministrativa.AdminProveedores;
import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import com.aquafx_project.AquaFx;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.styles.jmetro8.JMetro;
import message.EstadosMensaje;
import model.Organizacion;
import model.Proveedores;
import org.aerofx.AeroFX;





/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FProveedor extends Application {
    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GridPane gp_generic;
    private TextField  nit ;
    private TextField  nombre ;
    private TextField  telefono ;
    private TextField  dir;
     private TextField  email;
    private Button save;
    private Button nuevo;
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
        nit=new TextField("");
        nit.getProperties().put("requerido", true);
        nombre=new TextField("");
        nombre.getProperties().put("requerido", true);
        nombre.setMinWidth(300);
        dir=new TextField("");
        telefono=new TextField("");
        telefono.getProperties().put("requerido", true);
        email=new TextField("");
        ImageView imageView=new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        save =new Button("",imageView);
        save.setTooltip(new Tooltip("Guardar Proveedor"));
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
        nuevo =new Button("",imageView);
         nuevo.setTooltip(new Tooltip("Nuevo Proveedor"));
        nuevo.setOnAction(e->{
            nuevo();
         });
          hb_botones=new HBox(10);
         hb_botones.getChildren().addAll(save,nuevo);
        
// gridpane
        gp_generic=new GridPane();
        gp_generic.add(new Label("Nit: "), 0, 0);
        gp_generic.add(nit, 1, 0);
        gp_generic.add(new Label("Nombre: "), 0, 1);
        gp_generic.add(nombre, 1, 1);
         gp_generic.add(new Label("Dirección: "), 0, 2);
        gp_generic.add(dir, 1, 2);
         gp_generic.add(new Label("Telefono: "), 0, 3);
        gp_generic.add(telefono, 1, 3);
         gp_generic.add(new Label("Email: "), 0, 4);
        gp_generic.add(email, 1, 4);
          gp_generic.add(hb_botones, 0, 5,2,1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
       // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
       // gp_generic.getStyleClass().add("background");
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
          nuevo.setStyle("-fx-background-color:#007fff");
          save.setStyle("-fx-background-color:#007fff");
            
          
        }
       crearoeditar();
    return stack;
    }
  
 
   
      @Override
    public void start(Stage primaryStage) throws Exception {
 
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
private void nuevo()
{  
    AbacoApp.s_proveedores=null;
    AbacoApp.s_proveedores=new Proveedores();
    empty_field();
    
     
}
public void save() throws InterruptedException
{ validar_formulario();
  if(permitirproceso)
  {
  set_datos();
  try{
  
if(AbacoApp.s_proveedores.getId()==null)
      {
        AbacoApp.proveedoresControllerClient.create();
         L_Message.setText("Registro Almacenado"); 
      }
      else
      {
       AbacoApp.proveedoresControllerClient.update();
         L_Message.setText("Registro modificado"); 
      }
    AdminProveedores.getRecords();
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
      
        AbacoApp.s_proveedores.setNo_ident(nit.getText().trim());
        AbacoApp.s_proveedores.setNombre(nombre.getText());
        AbacoApp.s_proveedores.setDir1(dir.getText());
        AbacoApp.s_proveedores.setCelular(telefono.getText());
        AbacoApp.s_proveedores.setEmail(email.getText());
        //cargamos la imagen
      
      
       
       
}

public void validar_formulario() throws InterruptedException
{
    permitirproceso=true;
      for(Object n:gp_generic.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            if(general.getProperties().get("requerido")!=null)
            {
            if((general.getLength()<=0) && ((boolean)general.getProperties().get("requerido")==true))
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
           
         if(general.getSelectionModel().getSelectedIndex()<0 &&(boolean)general.getProperties().get("requerido")==true)
         {
                  permitirproceso=false; 
                  general.getStylesheets().add(0,"/abaco/personal.css");
               general.getStylesheets().set(0,"/abaco/personal.css");
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
              for(Object n:gp_generic.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            if(general.getProperties().get("requerido")!=null)
            {
              
            if( ((boolean)general.getProperties().get("requerido")==true))
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
           
         if(general.getSelectionModel().getSelectedIndex()<0 &&(boolean)general.getProperties().get("requerido")==true)
         {
             general.getStylesheets().set(0,"/abaco/SofackarStylesCommon.css");
             
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
// A change listener to track the change in selected index
public   void indexChanged(ObservableValue<? extends Number> observable,
Number oldValue,
Number newValue) { 

} 


public void crearoeditar()
{
   
    if(AbacoApp.s_proveedores!=null)
    {
       if(AbacoApp.s_proveedores.getId()!=null)
    {
       getDatos();
    }
       else
       {
            AbacoApp.s_proveedores=new Proveedores();
       nuevo();
       }
    }
    else
    {
       AbacoApp.s_proveedores=new Proveedores();
       nuevo();
    }
   
}
private void empty_field()
{
               for(Object n:gp_generic.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            general.setText("");
           
        }  
         else
        {
             if(n.getClass()==ChoiceBox.class)
        {
            ChoiceBox general=(ChoiceBox)n;
           
            general.getSelectionModel().select(-1);
           
        }
              else
        {
             if(n.getClass()==TextArea.class)
        {
            TextArea general=(TextArea)n;
           
            general.setText("");
           
        }
                    else
        {
         if(n.getClass()==RadioButton.class)
        {
            RadioButton general=(RadioButton)n;
           
            general.setSelected(false);
           
        }
        else
        {
         if(n.getClass()==CheckBox.class)
        {
            CheckBox general=(CheckBox)n;
           
            general.setSelected(false);
           
        }
        }
        }
        }
        }
        }
       
}
private void getDatos()
{
        nit.setText(AbacoApp.s_proveedores.getNo_ident());
        nombre.setText(AbacoApp.s_proveedores.getNombre());
        dir.setText(AbacoApp.s_proveedores.getDir1());
        telefono.setText(AbacoApp.s_proveedores.getCelular());
        email.setText(AbacoApp.s_proveedores.getEmail());
}
    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}