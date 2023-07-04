
package  abaco.soluciones.informacionadministrativa;

import abaco.soluciones.informacionadministrativa.AdminPersonas;
import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import abaco.PageBrowser;
import abaco.SearchPopover;
import abaco.control.SearchBox;
import com.aquafx_project.AquaFx;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import jfxtras.styles.jmetro8.JMetro;
import message.EstadosMensaje;
import model.Personas;
import org.aerofx.AeroFX;





/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FPersonas extends Application {
    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
  
   
   
    private GridPane gp_generic;
   
    private TextField  profesion ;
    private TextField  noident ;
    private TextField  nombre;
    private TextField  telefono;
    private TextField  direccion;
    private RadioButton Rb_Masculino;
    private RadioButton Rb_Femenino;
    private ToggleGroup toggleGroup;
    private ChoiceBox tipo;
    private  TextField email;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private ImageView foto;
    private Button bu_foto;
    private Image loadfoto;
    private GridPane Gp_Message;
    private     Label L_Message;
    private  Runnable Task_Message;
    private boolean permitirproceso=false;
    Thread backgroundThread;
    private File fileFoto;
    private FileChooser fileChooser = new FileChooser();
    private WritableImage wi;
    private BufferedImage bi;
    private  SearchBox sb_genmunicipios=new SearchBox();
    private  SearchPopover sp_genmunicipios;
    private HBox V_box;
    public Parent createContent() throws IOException {
    sp_genmunicipios = new SearchPopover(sb_genmunicipios,new PageBrowser(),AbacoApp.s_genmunicipios,false);
    sb_genmunicipios.setMaxWidth(580);    
      foto=new ImageView("/images/foto.png");
      foto.setFitHeight(100);
      foto.setFitWidth(100);
      //***********************************************  
        estadosMensaje=new EstadosMensaje();
        stack = new StackPane();
        tipo=new ChoiceBox();
        tipo.getItems().addAll("Contactos","Clientes","Empleados");
        profesion=new TextField("");
        noident=new TextField("");
        profesion.getProperties().put("requerido", true);
        noident=new TextField("");
        noident.getProperties().put("requerido", true);
        nombre=new TextField("");
        nombre.getProperties().put("requerido", true);
        nombre.setMinWidth(580);
        direccion=new TextField("");
        email=new TextField("");
        telefono=new TextField("");
       toggleGroup=new ToggleGroup();
        
       Rb_Masculino=new RadioButton("Masculino");
       Rb_Masculino.setToggleGroup(toggleGroup);
       Rb_Femenino=new RadioButton("Feménino");
       Rb_Femenino.setToggleGroup(toggleGroup);
       V_box=new HBox();
       V_box.setSpacing(5);//es
       V_box.getChildren().addAll(Rb_Masculino,Rb_Femenino);
        ImageView imageView=new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
       
        save =new Button("",imageView);
         save.setTooltip(new Tooltip("Guardar Persona"));
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
        nuevo.setTooltip(new Tooltip("Nueva Persona"));
       
        nuevo.setOnAction(e->{
            nuevo();
         });
        imageView=null;
        imageView=new ImageView("/images/img.png");
         imageView.setFitHeight(30);
         imageView.setFitWidth(30);
        
         bu_foto =new Button("",imageView);
         bu_foto.setTooltip(new Tooltip("Cargar Foto de Persona"));
       
        bu_foto.setOnAction(e->{LoadImageLogo();
         });
          hb_botones=new HBox(10);
         hb_botones.getChildren().addAll(save,nuevo,bu_foto);
        
// gridpane
        gp_generic=new GridPane();
         gp_generic.add(new Label("Tipo: "), 0, 0);
        gp_generic.add(tipo, 1, 0);
        gp_generic.add(new Label("Profesión: "), 0, 1);
        gp_generic.add(profesion, 1, 1);
        gp_generic.add(new Label("N° Ident: "), 0, 2);
        gp_generic.add(noident, 1, 2);
        gp_generic.add(foto, 2, 0,2,5);
        gp_generic.add(new Label("Nombre: "), 0, 3);
        gp_generic.add(nombre, 1, 3);
        gp_generic.add(new Label("Email: "), 0, 5);
        gp_generic.add(email, 1, 5);
        gp_generic.add(new Label("Ciudad: "), 0, 4);
        gp_generic.add(sb_genmunicipios, 1, 4);
        gp_generic.add(new Label("Teléfono: "), 0, 6);
        gp_generic.add(telefono, 1, 6);
        gp_generic.add(new Label("Dirección: "), 0, 7,1,1);
        gp_generic.add(direccion, 1, 7);
        gp_generic.add(new Label("Sexo: "), 0, 8,1,1);
        gp_generic.add(V_box, 1, 8);
        gp_generic.add(hb_botones, 0, 9,2,1);
        gp_generic.add(sp_genmunicipios, 1, 5,2,10);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
       // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
     //   gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_CENTER);
              // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
       // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        gp_generic.maxWidth(600);
        stack.maxWidth(600);
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
          bu_foto.setStyle("-fx-background-color:#007fff");
          
          
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
 AbacoApp.s_genpersonas=null;
 AbacoApp.s_genpersonas=new Personas();
empty_field();
 
}
public void save() throws InterruptedException
{ validar_formulario();
  if(permitirproceso)
  {
  set_datos_oranizacion();
  try{
  
if(AbacoApp.s_genpersonas.getId()==null)
      {
        AbacoApp.genPersonasControllerClient.create();
         L_Message.setText("Registro Almacenado"); 
     
      }
      else
      {
         AbacoApp.genPersonasControllerClient.update();
         L_Message.setText("Registro modificado"); 
       }
if(AbacoApp.nombresolucion.equals("Terceros"))
{
    AdminPersonas.getRecords();
}
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
       if(AbacoApp.s_genpersonas==null)
       {
           AbacoApp.s_genpersonas=new Personas();
       }
        AbacoApp.s_genpersonas.setTipo_terc(tipo.getSelectionModel().getSelectedIndex());
        AbacoApp.s_genpersonas.setNo_ident(noident.getText().trim());
        AbacoApp.s_genpersonas.setNombre(nombre.getText());
        AbacoApp.s_genpersonas.setEmail(email.getText());
        AbacoApp.s_genpersonas.setCelular(telefono.getText());
        AbacoApp.s_genpersonas.setDir1(direccion.getText());
         AbacoApp.s_genpersonas.setProfesion(profesion.getText());
        if(AbacoApp.s_genmunicipios!=null)
        {
           if(AbacoApp.s_genmunicipios.getId()!=null)
           {
             AbacoApp.s_genpersonas.setGenMunicipios(AbacoApp.s_genmunicipios);
           }
           else
           {
               AbacoApp.s_genpersonas.setGenMunicipios(null);
           }
        }
        //cargamos la imagen
        save_img();
      
       
       
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
           if(general.getProperties().get("requerido")!=null)
            { 
         if(general.getSelectionModel().getSelectedIndex()<0 &&(boolean)general.getProperties().get("requerido")==true)
         {
                  permitirproceso=false; 
                  general.getStylesheets().add(0,"/abaco/personal.css");
               general.getStylesheets().set(0,"/abaco/personal.css");
         }  
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
           if(general.getProperties().get("requerido")!=null)
         {
         if(general.getSelectionModel().getSelectedIndex()<0 &&(boolean)general.getProperties().get("requerido")==true)
         {
             general.getStylesheets().set(0,"/abaco/SofackarStylesCommon.css");
             
         }
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

public void LoadImageLogo() {
        
       
        fileFoto=fileChooser.showOpenDialog(stage);
         
        if(fileFoto!=null)
        {
            byte[]img2;
            try {
                   bi = ImageIO.read(fileFoto);
                  
                  wi=SwingFXUtils.toFXImage(bi, wi);
                  loadfoto=wi;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
        }
    
        foto.setImage(loadfoto);
    }

public void save_img()
{
        byte[]data;
        InputStream in;
        try{
        in = new FileInputStream(fileFoto);
        data=new byte[in.available()];
	in.read(data);
        AbacoApp.s_genpersonas.setFoto(data);
        }catch(Exception e)
        {
           
        }
}
public void getDatos()
{
    
      
       profesion.setText(AbacoApp.s_genpersonas.getProfesion());
       noident.setText(AbacoApp.s_genpersonas.getNo_ident());  
       nombre.setText(AbacoApp.s_genpersonas.getNombre());
       email.setText(AbacoApp.s_genpersonas.getEmail());
       telefono.setText(AbacoApp.s_genpersonas.getCelular());
       direccion.setText(AbacoApp.s_genpersonas.getDir1());
       tipo.getSelectionModel().select(AbacoApp.s_genpersonas.getTipo_terc());
       if(AbacoApp.s_genpersonas.getGenMunicipios()!=null && AbacoApp.s_genpersonas.getGenMunicipios().getId()!=null)
        {
         sb_genmunicipios.setText(AbacoApp.s_genpersonas.getGenMunicipios().getNombre()+"-"+AbacoApp.s_genpersonas.getGenMunicipios().getGenDepartamentos().getNombre());
        }
        
       try{
       InputStream in = new ByteArrayInputStream(AbacoApp.s_genpersonas.getFoto());
          bi = ImageIO.read(in);
          wi=new WritableImage(1,1);
          wi=SwingFXUtils.toFXImage(bi, wi);
          //img=new ImageView();
          loadfoto=wi;
          foto.setImage(loadfoto);
       }catch(Exception e)
                  {
                  
                  }
       
        
}

public void crearoeditar()
{
   
    if(AbacoApp.s_genpersonas!=null)
    {
        getDatos();
        
    }
    else
    {
     AbacoApp.s_genpersonas=new Personas();
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