
package  abaco.soluciones.informacionadministrativa;

import abaco.soluciones.informacionadministrativa.AdminCategorias;
import abaco.AbacoApp;
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
import message.EstadosMensaje;
import model.Categoria;
import model.ConPuc;





/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FConfiguracionesCompra extends Application {
    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
  
   
   
    private GridPane gp_generic;
   
    private TextField  mercanciasnofab_natd ;
    private TextField  impuestoventas_natd;
    private TextField  retefuente_natc;
    private TextField  proveedores_natc;
    private TextField  reteiva_natc;
    private TextField  reteica_natc;
    private TextField  caja_natc;
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
    private ChoiceBox compraentreregimenes;
    public Parent createContent() throws IOException {
   
    //***********************************************  
      compraentreregimenes=new ChoiceBox();
      compraentreregimenes.getItems().addAll("Grande contribuyente compra a grande contribuyente","Grande contribuyente compra a régimen común","Grande contribuyente compra a régimen simplificado","Régimen común compra a grande contribuyente","Régimen común compra a régimen común","Régimen simplificado compra a régimen simplificado");
      estadosMensaje=new EstadosMensaje();
        stack = new StackPane();
        mercanciasnofab_natd=new TextField("");
        retefuente_natc=new TextField("");
        proveedores_natc=new TextField("");
        reteiva_natc=new TextField("");
        impuestoventas_natd=new TextField("");
        reteica_natc=new TextField("");
        caja_natc=new TextField("");
        ImageView imageView=new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        save =new Button("",imageView);
        save.setTooltip(new Tooltip("Guardar Configuración"));
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
        nuevo.setTooltip(new Tooltip("Nueva Categoría"));
        nuevo.setOnAction(e->{
           
            nuevo();
             
         });
         hb_botones=new HBox(10);
         hb_botones.getChildren().addAll(save,nuevo);
        
// gridpane
        gp_generic=new GridPane();
        gp_generic.add(new Label("Mercancias No Fab Nat. D: "), 0, 0);
        gp_generic.add(mercanciasnofab_natd, 1, 0);
        gp_generic.add(new Label(" Por tanto a partir del primero de enero de 2017, los responsables del régimen común no deberán calcular ese IVA teórico cuando realicen compras de bienes o servicios a los responsables del régimen simplificado del IVA.\n" +
"\n" +
"  : "), 2, 0);
        gp_generic.add(new Label("Iva Nat. D: "), 0, 1);
        gp_generic.add(impuestoventas_natd, 1, 1);
        gp_generic.add(new Label("Proveedores Nat C: "), 0, 2);
        gp_generic.add(proveedores_natc, 1, 2);
        gp_generic.add(new Label("Rte Fuente Nat. C: "), 0, 3);
        gp_generic.add(retefuente_natc, 1, 3);
        gp_generic.add(new Label("Rte Iva Nat. C: "), 0, 3);
        gp_generic.add(reteiva_natc, 1, 3);
        gp_generic.add(new Label("Rte Ica Nat. C: "), 0, 4);
        gp_generic.add(reteica_natc, 1, 4);
        gp_generic.add(hb_botones, 0, 5,2,1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        gp_generic.getStyleClass().add("background");
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
    AbacoApp.s_categoria=null;
    AbacoApp.s_categoria=new Categoria();
    empty_field();
}
public void save() throws InterruptedException
{ validar_formulario();
  if(permitirproceso)
  {
  set_datos_oranizacion();
  try{
  
if(AbacoApp.s_categoria.getId()==null)
      {
        AbacoApp.categoriaControllerClient.save();
         L_Message.setText("Registro Almacenado"); 
      }
      else
      {
        AbacoApp.s_categoria=  AbacoApp.categoriaControllerClient.update();
         L_Message.setText("Registro modificado"); 
      }
    AdminCategorias.getRecords();
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

private void set_datos_oranizacion()
{
       if(AbacoApp.s_configuraciones.getMercanciasnofab_natd()==null)
       {
          AbacoApp.s_configuraciones.setMercanciasnofab_natd(new ConPuc()); 
       }    
        AbacoApp.s_configuraciones.getMercanciasnofab_natd().setCodigo(mercanciasnofab_natd.getText());
        
       if(AbacoApp.s_configuraciones.getImpuestoventascompras_natd()==null)
       {
          AbacoApp.s_configuraciones.setImpuestoventascompras_natd(new ConPuc()); 
       }    
        AbacoApp.s_configuraciones.getImpuestoventascompras_natd().setCodigo(impuestoventas_natd.getText());
       if(AbacoApp.s_configuraciones.getProveedores_natc()==null)
       {
          AbacoApp.s_configuraciones.setProveedores_natc(new ConPuc()); 
       }    
        AbacoApp.s_configuraciones.getProveedores_natc().setCodigo(proveedores_natc.getText());

       if(AbacoApp.s_configuraciones.getRtefuente_natc()==null)
       {
          AbacoApp.s_configuraciones.setRtefuente_natc(new ConPuc()); 
       }    
        AbacoApp.s_configuraciones.getRtefuente_natc().setCodigo(retefuente_natc.getText());
        if(AbacoApp.s_configuraciones.getIvaretenidocompras_natc()==null)
       {
          AbacoApp.s_configuraciones.setIvaretenidocompras_natc(new ConPuc()); 
       }    
        AbacoApp.s_configuraciones.getIvaretenidocompras_natc().setCodigo(reteiva_natc.getText());
       if(AbacoApp.s_configuraciones.getIcaretenidocompras_natc()==null)
       {
          AbacoApp.s_configuraciones.setIcaretenidocompras_natc(new ConPuc()); 
       }    
        AbacoApp.s_configuraciones.getIcaretenidocompras_natc().setCodigo(reteica_natc.getText());
        if(AbacoApp.s_configuraciones.getCajageneral_natc()==null)
       {
          AbacoApp.s_configuraciones.setCajageneral_natc(new ConPuc()); 
       }    
        AbacoApp.s_configuraciones.getCajageneral_natc().setCodigo(caja_natc.getText());

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

public void getdatos()
{
  mercanciasnofab_natd.setText(AbacoApp.s_configuraciones.getMercanciasnofab_natd().getCodigo());
  impuestoventas_natd.setText(AbacoApp.s_configuraciones.getImpuestoventascompras_natd().getCodigo());
  retefuente_natc.setText(AbacoApp.s_configuraciones.getRtefuente_natc().getCodigo());
  reteiva_natc.setText(AbacoApp.s_configuraciones.getIvaretenidocompras_natc().getCodigo());
  reteica_natc.setText(AbacoApp.s_configuraciones.getIcaretenidocompras_natc().getCodigo());
  proveedores_natc.setText(AbacoApp.s_configuraciones.getProveedores_natc().getCodigo());
  caja_natc.setText(AbacoApp.s_configuraciones.getCajageneral_natc().getCodigo());
}

public void crearoeditar()
{
     AbacoApp.s_configuraciones=AbacoApp.configuracionesControllerClient.getConfiguraciones();
     
    if(AbacoApp.s_configuraciones.getId()!=null)
    {
       getdatos();
        
    }
    else
    {
       
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
    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}