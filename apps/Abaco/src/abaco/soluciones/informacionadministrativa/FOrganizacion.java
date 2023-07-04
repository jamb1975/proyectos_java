
package  abaco.soluciones.informacionadministrativa;

import abaco.soluciones.informacionadministrativa.AdminOrganizacion;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import jfxtras.styles.jmetro8.JMetro;
import message.EstadosMensaje;
import model.Organizacion;
import org.aerofx.AeroFX;





/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FOrganizacion extends Application {
    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
  
   
   
    private GridPane gp_generic;
   
    private TextField  codigo ;
    
    private TextField  nit ;
    private TextField  nombre;
    private TextField  telefono;
    private TextField  direccion;
    private  TextField sigla;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private ImageView logo;
    private Button bu_logo;
    private Image loadlogo;
    private GridPane Gp_Message;
    private     Label L_Message;
    private  Runnable Task_Message;
    private boolean permitirproceso=false;
    Thread backgroundThread;
    private File fileLogo;
    private FileChooser fileChooser = new FileChooser();
    private WritableImage wi;
    private BufferedImage bi;
    private  SearchBox sb_genmunicipios=new SearchBox();
    private  SearchPopover sp_genmunicipios;
    private  TextField email=new TextField("");
    private  PasswordField passwordemail=new PasswordField();
    private  TextField emailpedidos=new TextField("");
    private PasswordField passwordemailpedidos=new PasswordField();
    private  TextField emailbdproductos=new TextField("");
    private PasswordField passwordemailbdproductos=new PasswordField();
    public Parent createContent() throws IOException {
    sp_genmunicipios = new SearchPopover(sb_genmunicipios,new PageBrowser(),AbacoApp.s_genmunicipios,false);
    sb_genmunicipios.setMaxWidth(500); 
    sp_genmunicipios.setMaxWidth(300);
      logo=new ImageView("/images/foto.png");
      logo.setFitHeight(100);
      logo.setFitWidth(100);
       Rectangle clip = new Rectangle(
                logo.getFitWidth(), logo.getFitHeight()
            );
       clip.setArcWidth(110);
       clip.setArcHeight(110);
      // logo.setClip(clip);
      //***********************************************  
        estadosMensaje=new EstadosMensaje();
        stack = new StackPane();
       
        codigo=new TextField("");
        nit=new TextField("");
        codigo.getProperties().put("requerido", true);
        nit=new TextField("");
        nit.getProperties().put("requerido", true);
        nombre=new TextField("");
        nombre.getProperties().put("requerido", true);
        nombre.setMinWidth(500);
      
        direccion=new TextField("");
        sigla=new TextField("");
        telefono=new TextField("");
        ImageView imageView=new ImageView("/images/save.png");
         imageView.setFitHeight(30);
         imageView.setFitWidth(30);
       
        save =new Button("",imageView);
        save.setTooltip(new Tooltip("Guardar Organización"));
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
        nuevo.setTooltip(new Tooltip("Nueva Orgnización"));
       
        nuevo.setOnAction(e->{try {
            save();
              } catch (InterruptedException ex) {
                 
              }
         });
         imageView=null;
         imageView=new ImageView("/images/img.png");
         imageView.setFitHeight(30);
         imageView.setFitWidth(30);
        
         bu_logo =new Button("",imageView);
         bu_logo.setTooltip(new Tooltip("Cargar Logo de Organización"));
       
        bu_logo.setOnAction(e->{LoadImageLogo();
         });
          hb_botones=new HBox(10);
         hb_botones.getChildren().addAll(save,bu_logo);
        
// gridpane
        gp_generic=new GridPane();
        gp_generic.add(new Label("Codigo: "), 0, 0,1,1);
        gp_generic.add(codigo, 1, 0,1,1);
        gp_generic.add(new Label("Nit: "), 0, 1,1,1);
        gp_generic.add(nit, 1, 1,1,1);
        gp_generic.add(logo, 2, 0,2,5);
        gp_generic.add(new Label("Nombre: "), 0, 2,1,1);
        gp_generic.add(nombre, 1, 2,1,1);
        gp_generic.add(new Label("Sigla: "), 0, 3,1,1);
        gp_generic.add(sigla, 1, 3,1,1);
        gp_generic.add(new Label("Ciudad: "), 0, 4,1,1);
        gp_generic.add(sb_genmunicipios, 1, 4,1,1);
        gp_generic.add(new Label("Teléfono: "), 0, 5,1,1);
        gp_generic.add(telefono, 1, 5,1,1);
        gp_generic.add(new Label("Dirección: "), 0, 6,1,1);
        gp_generic.add(direccion, 1, 6,1,1);
        gp_generic.add(new Label("Email: "), 0, 7,1,1);
        gp_generic.add(email, 1, 7,1,1);
        gp_generic.add(new Label("Password: "), 0, 8,1,1);
        gp_generic.add(passwordemail, 1, 8,1,1);
        gp_generic.add(new Label("Email pedidos: "), 0, 9,1,1);
        gp_generic.add(emailpedidos, 1, 9,1,1);
        gp_generic.add(new Label("Password pedidos: "), 0, 10,1,1);
        gp_generic.add(passwordemailpedidos, 1, 10,1,1);
        gp_generic.add(new Label("Email bd productos: "), 0,11,1,1);
        gp_generic.add(emailbdproductos, 1, 11,1,1);
        gp_generic.add(new Label("Password bd productos: "), 0, 12,1,1);
        gp_generic.add(passwordemailbdproductos, 1, 12,1,1);
        gp_generic.add(hb_botones, 0, 13,3,1);
        gp_generic.add(sp_genmunicipios, 0, 5,4,3);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        //gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
       // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_LEFT);
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
          bu_logo.setStyle("-fx-background-color:#007fff");
        }
       crearoeditar();
    return stack;
    }
  
 
   
      @Override
    public void start(Stage primaryStage) throws Exception {
 
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

public void save() throws InterruptedException
{ validar_formulario();
  if(permitirproceso)
  {
  set_datos_oranizacion();
  try{
  
if(AbacoApp.s_organizacion.getId()==null)
      {
        AbacoApp.organizacionControllerClient.save();
         L_Message.setText("Registro Almacenado"); 
      }
      else
      {
        AbacoApp.s_organizacion=  AbacoApp.organizacionControllerClient.update();
         L_Message.setText("Registro modificado"); 
      }
    AdminOrganizacion.getOrganizacion();
    AbacoApp.loaddatosorganizacion_toobar();
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
       if(AbacoApp.s_organizacion==null)
       {
           AbacoApp.s_organizacion=new Organizacion();
       }
        AbacoApp.s_organizacion.setCodigo(codigo.getText().trim());
        AbacoApp.s_organizacion.setNit(nit.getText().trim());
        AbacoApp.s_organizacion.setNombre(nombre.getText());
        AbacoApp.s_organizacion.setSigla(sigla.getText());
        AbacoApp.s_organizacion.setCelular(telefono.getText());
        AbacoApp.s_organizacion.setDir(direccion.getText());
        AbacoApp.s_organizacion.setEmail(email.getText());
        AbacoApp.s_organizacion.setPasswordemail(passwordemail.getText());
        AbacoApp.s_organizacion.setEmailpedidos(emailpedidos.getText());
        AbacoApp.s_organizacion.setPasswordemailpedidos(passwordemailpedidos.getText());
        AbacoApp.s_organizacion.setEmailbdproductos(emailbdproductos.getText());
        AbacoApp.s_organizacion.setPasswordemailbdproductos(passwordemailbdproductos.getText());
        if(AbacoApp.s_genmunicipios!=null)
        {
           if(AbacoApp.s_genmunicipios.getId()!=null)
           {
             AbacoApp.s_organizacion.setGenMunicipios(AbacoApp.s_genmunicipios);
           }
           else
           {
               AbacoApp.s_organizacion.setGenMunicipios(null);
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

public void LoadImageLogo() {
        
       
        fileLogo=fileChooser.showOpenDialog(stage);
         
        if(fileLogo!=null)
        {
            byte[]img2;
            try {
                   bi = ImageIO.read(fileLogo);
                  
                  wi=SwingFXUtils.toFXImage(bi, wi);
                  loadlogo=wi;
                } catch (IOException ex) {
                   
                }
        }
    
        logo.setImage(loadlogo);
    }

public void save_img()
{
        byte[]data;
        InputStream in;
        try{
        in = new FileInputStream(fileLogo);
        data=new byte[in.available()];
	in.read(data);
        AbacoApp.s_organizacion.setLogo(data);
        }catch(Exception e)
        {
           
        }
}
public void getDatos()
{
    
      
       codigo.setText(AbacoApp.s_organizacion.getCodigo());
       nit.setText(AbacoApp.s_organizacion.getNit());  
       nombre.setText(AbacoApp.s_organizacion.getNombre());
       sigla.setText(AbacoApp.s_organizacion.getSigla());
       telefono.setText(AbacoApp.s_organizacion.getCelular());
       direccion.setText(AbacoApp.s_organizacion.getDir());
       email.setText(AbacoApp.s_organizacion.getEmail());
       passwordemail.setText(AbacoApp.s_organizacion.getPasswordemail());
       emailpedidos.setText(AbacoApp.s_organizacion.getEmailpedidos());
       passwordemailpedidos.setText(AbacoApp.s_organizacion.getPasswordemailpedidos());
       emailbdproductos.setText(AbacoApp.s_organizacion.getEmailbdproductos());
       passwordemailbdproductos.setText(AbacoApp.s_organizacion.getPasswordemailbdproductos());
       if(AbacoApp.s_organizacion.getGenMunicipios()!=null && AbacoApp.s_organizacion.getGenMunicipios().getId()!=null)
        {
         sb_genmunicipios.setText(AbacoApp.s_organizacion.getGenMunicipios().getNombre()+"-"+AbacoApp.s_organizacion.getGenMunicipios().getGenDepartamentos().getNombre());
        }
        
       try{
       InputStream in = new ByteArrayInputStream(AbacoApp.s_organizacion.getLogo());
          bi = ImageIO.read(in);
          wi=new WritableImage(1,1);
          wi=SwingFXUtils.toFXImage(bi, wi);
          //img=new ImageView();
          loadlogo=wi;
          logo.setImage(loadlogo);
       }catch(Exception e)
                  {
                  
                  }
       
        
}

public void crearoeditar()
{
   
    if(AbacoApp.s_organizacion!=null)
    {
        getDatos();
        
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