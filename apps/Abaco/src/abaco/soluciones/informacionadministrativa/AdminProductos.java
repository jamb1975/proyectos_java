/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.informacionadministrativa;

import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import abaco.soluciones.administrador.ImpresionFactura;
import com.aquafx_project.AquaFx;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IdleManager;
import com.sun.mail.util.MailSSLSocketFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import jfxtras.styles.jmetro8.JMetro;
import model.Producto;
import org.aerofx.AeroFX;
import org.controlsfx.control.PopOver;



/**
 *
 * @author adminlinux
 */
public class AdminProductos  extends Application{

    private GridPane  gp_generico;
    private static TableView tv_Productos=new TableView();
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_Productos=FXCollections.observableArrayList();;
    private Button bu_buscar;
    private Button bu_nuevo;
    private Button bu_nubeemail;
    private  GridPane Gp_Message;
    private  Label L_Message;
    private  Runnable Task_Message;
    Thread   backgroundThread;
    private  StackPane stack;
    private  ImpresionFactura impresionFactura;
    private  SimpleDateFormat df=new SimpleDateFormat("MMM-dd");
    private  String appClass;
    private  Class clz ;
    private  Object app ;
    private  Parent parent;
    private  Stage stage= new Stage(StageStyle.DECORATED);
    Scene scene =null;
    private ContextMenu contextMenu;
    private MenuItem  itemnuevo;
    private MenuItem  itemeditar;
    private MenuItem  itemdelete;
    private ImageView img;
    private TitledPane tp_generic;
    private static List<Producto> li_producto=new ArrayList<>();
    private static List<Producto> li_productotemp=new ArrayList<>();
    private PopOver popover;
    private  Task<Void>   task_generic;
    private  Thread  thimport_generic;
    private ProgressBar pb ;
    private ProgressIndicator pi;
    private GridPane gp=new GridPane();
    public static double  avance_progress=0;
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException  {
    popover=new PopOver(gp);
        tp_generic=new TitledPane();
     tp_generic.setText("Administrar Productos");
     tp_generic.setCollapsible(false);
        stage.setTitle("Producto");
        img=new ImageView ("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo=new MenuItem("Nuevo",img);
        itemnuevo.setOnAction(e->{
          try{
             AbacoApp.s_producto=null ; 
             show();
           }catch(Exception x)
           {
               
           }
       });
       img=null;
        img=new ImageView ("/images/editar.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
       itemeditar=new MenuItem("Editar",img);
        itemeditar.setOnAction(e->{
         
           try{
         checkregistroexistente();
          show();
           }catch(Exception x)
           {
               
           }
       });
       img=null;
       img=new ImageView ("/images/delete.png");
       img.setFitHeight(20);
       img.setFitWidth(20);
       itemdelete=new MenuItem("Eliminar",img);
        itemdelete.setOnAction(e->{
          
            try {
                checkregistroexistente();
                delete();
            } catch (ParseException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
       });
    contextMenu=new ContextMenu(itemnuevo,itemeditar,itemdelete);
    stack=new StackPane();
      la_buscar=new Label("Buscar: ");
    la_buscar.setMinWidth(100);
    buscar=new TextField();
    buscar.setMinWidth(600);
    buscar.textProperty().addListener((observable, oldValue, newValue) -> {
        buscarproducto();
            
});
     ImageView imageView;
       
         imageView=new ImageView("/images/find.png");
         imageView.setFitHeight(40);
         imageView.setFitWidth(40);
           
           bu_buscar=new Button("",imageView);
           bu_buscar.setDisable(false);
           bu_buscar.setOnAction(e->
          {
           try {
              getRecords();
             
           } catch (Exception ex) {
              
           }
          });
            imageView=new ImageView("/images/new2.png");
         imageView.setFitHeight(40);
         imageView.setFitWidth(40);
           
           bu_nuevo=new Button("",imageView);
           bu_nuevo.setDisable(false);
           bu_nuevo.setOnAction(e->
          {
           try {
                  AbacoApp.s_producto=null;
               show();
             
           } catch (Exception ex) {
               ex.printStackTrace();
           }
          });
         imageView=new ImageView("/images/nubeemail.png");
         imageView.setFitHeight(40);
         imageView.setFitWidth(40);
           
         bu_nubeemail=new Button("",imageView);
         bu_nubeemail.setTooltip(new Tooltip("Subir a la nube archivo listado de productos en formato JSON "));
         bu_nubeemail.setOnAction(e->{
             
         try {
             showAvancerestaurar();
             crearjsonproductos();
             enviarEmail();
         } catch (Exception ex) {
            ex.printStackTrace();
         }
         });
    gp_generico=new GridPane();
    tv_Productos=new TableView(); 
    
    
     
   
        TableColumn codigobarras = new TableColumn();
        codigobarras.setMinWidth(200);
        codigobarras.setText("Código de Barras");
        codigobarras.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               
                    return new SimpleStringProperty(Producto.getValue().getCodigobarras());
                            
            }
        });
       
       TableColumn nombre = new TableColumn();
        nombre.setMinWidth(250);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               
                    return new SimpleStringProperty(Producto.getValue().getNombre());
                            
            }
        });
        TableColumn precio = new TableColumn();
        precio.setMinWidth(100);
        precio.setText("Precio");
        precio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               
                    return new SimpleStringProperty(Producto.getValue().getPrecio().toString());
                            
            }
        });
        TableColumn proveedor = new TableColumn();
        proveedor.setMinWidth(200);
        proveedor.setText("Proveedor");
        proveedor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               try{
                    return new SimpleStringProperty(Producto.getValue().getProveedores().getNombre());
               }catch(Exception e)
               {
                 return new SimpleStringProperty("NO");  
               }
                            
            }
        });
        TableColumn proveedorcelular = new TableColumn();
        proveedorcelular.setMinWidth(100);
        proveedorcelular.setText("Teléfono");
        proveedorcelular.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               try{
                    return new SimpleStringProperty(Producto.getValue().getProveedores().getCelular());
               }catch(Exception e)
               {
                 return new SimpleStringProperty("NO");  
               }
                            
            }
        });
        tv_Productos.getColumns().addAll(codigobarras,nombre,precio,proveedor,proveedorcelular);
        //ta_librodiario.setMinWidth(650);
       
        tv_Productos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_Productos.setMinHeight(577);
        tv_Productos.setContextMenu(contextMenu);
        Gp_Message=new GridPane();
        Gp_Message=new GridPane();
        Gp_Message.setMinWidth(gp_generico.getLayoutBounds().getWidth());
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
        //gp_generico.getStylesheets().add("/abaco/SofackarStylesCommon.css");
      //  gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0,la_buscar,buscar,bu_buscar,bu_nuevo,bu_nubeemail);
       
        gp_generico.add(tv_Productos, 0, 3,7,1);
      
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        gp_generico.setMinSize(1000, 610);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic,Gp_Message);
        switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gp_generico);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gp_generico);
                     break;              
         }
          if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()!=0)
        {
          bu_buscar.setStyle("-fx-background-color:#007fff");
          bu_nuevo.setStyle("-fx-background-color:#007fff");
        }
        getRecords();
        return stack;
  }
  
public static void   getRecords() throws ParseException
{
         //colocamos los datos
         try{
            
       
         li_productotemp=AbacoApp.productoControllerClient.getRecordsProductos(null);
       li_producto.clear();
       li_producto.addAll(li_productotemp);
       ol_Productos.clear();
       
        ol_Productos.addAll(li_producto.toArray());
        tv_Productos.setItems(ol_Productos);
      } catch(Exception e)
       {
           e.printStackTrace();
       }
    }
 private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {
      appClass="abaco.soluciones.informacionadministrativa.FProducto";
      clz=null;
      clz = Class.forName(appClass);
      app = clz.newInstance();
  
      parent=null;
      parent = (Parent) clz.getMethod("createContent").invoke(app);
      scene=null;
       scene=new Scene(parent, Color.TRANSPARENT);
       
        if (stage.isShowing())
       {
           stage.close();
       }      
        
        
//set scene to stage
                stage.sizeToScene();
                
              
                //center stage on screen
                stage.centerOnScreen();
                stage.setScene(scene);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stage.show();
  }  
   
private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
   {
       if ((tv_Productos.getSelectionModel())!=null)
       {
           AbacoApp.s_producto=(Producto)tv_Productos.getSelectionModel().getSelectedItem();
         
       }
   }

    
   
     @Override
    public void start(Stage primaryStage) throws Exception {
        
        
    }
       public void delete() throws ParseException
    {
        AbacoApp.productoControllerClient.delete();
        getRecords();
   } 
   private static void buscarproducto()
   {
                try{
            
          li_producto=li_productotemp.stream().filter(line ->line.getCodigobarras().trim().toUpperCase().contains(buscar.getText().trim().toUpperCase()) || line.getNombre().toUpperCase().contains(buscar.getText().toUpperCase()))	//filters the line, equals to "mkyong"
	                      .collect(Collectors.toList());
        
       
       ol_Productos.clear();
       
        ol_Productos.addAll(li_producto.toArray());
        tv_Productos.setItems(ol_Productos);
      } catch(Exception e)
       {
           e.printStackTrace();
       }

   }
    private void crearjsonproductos() throws FileNotFoundException, IOException
    {
        OutputStream out=new FileOutputStream("\\home\\adminlinux\\abaco\\productos.json");
        JsonWriter jswriterproducto=Json.createWriter(out);
        JsonObjectBuilder jsonlistprodcutoproducto = Json.createObjectBuilder();
        JsonArrayBuilder jsonarrayproductos = Json.createArrayBuilder();
       for(Producto p: AbacoApp.li_producto)
       {
             jsonlistprodcutoproducto.add("codigo", p.getCodigobarras());
             jsonlistprodcutoproducto.add("nombre",  URLDecoder.decode(URLEncoder.encode(p.getNombre(), "UTF8")));
             jsonlistprodcutoproducto.add("precio", p.getPrecio().toBigInteger().toString());
             jsonarrayproductos.add(jsonlistprodcutoproducto);
            
       }
          
           jswriterproducto.writeArray(jsonarrayproductos.build());
           pb.setProgress(0.3);
           pi.setProgress(0.3);
           jswriterproducto.close();
           out.close();
    } 
 public void  enviarEmail() throws GeneralSecurityException, MessagingException
 {
      gp.setAlignment(Pos.CENTER);
      avance_progress=0.0;
      task_generic=new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                
                 borraremaillbdproductos();
                 pb.setProgress(0.6);
                 pi.setProgress(0.6);
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.user", "abaco7775@gmail.com");
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port","587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "true");
        MailSSLSocketFactory sf=new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.socketFactory", sf);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session mailSession;
try{
  mailSession = Session.getDefaultInstance(props, new Authenticator() {
           @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AbacoApp.s_organizacion.getEmail(),AbacoApp.s_organizacion.getPasswordemail());
            }

        });
}
catch(Exception e){
   mailSession=Session.getInstance(props, new Authenticator() {
        
 
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AbacoApp.s_organizacion.getEmail(), AbacoApp.s_organizacion.getPasswordemail());
            }

        });
}
  try {
        MimeMessage msg = new MimeMessage(mailSession);
        msg.setRecipients(Message.RecipientType.TO,AbacoApp.s_organizacion.getEmailbdproductos());
        msg.setSubject("Envío productos formato json");
        msg.setFrom(AbacoApp.s_organizacion.getEmail());
        msg.setSentDate(new Date());
       Multipart mailBody = new MimeMultipart();
       BodyPart texto = new MimeBodyPart();
       texto.setText("Lista de Productos formato json");
       mailBody.addBodyPart(texto);
       MimeBodyPart mimeAttach = new MimeBodyPart();
       String os = System.getProperty("os.name"); 
       FileDataSource fds = new FileDataSource("");
       if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
      { 
        fds = new FileDataSource("/home/adminlinux/abaco/factura.pdf");
      }
     else
      {
      
    fds = new FileDataSource("\\home\\adminlinux\\abaco\\productos.json");
 
 }
 mimeAttach.setDataHandler(new DataHandler(fds));
 mimeAttach.setFileName(fds.getName()); 
 mailBody.addBodyPart(mimeAttach);
 mimeAttach=null;
 mimeAttach = new MimeBodyPart();
 fds=null;
 msg.setContent(mailBody); 
 Transport.send(msg);
    
     mailSession.getTransport().close();
     mailSession.getStore().close();
     msg.getSession().getTransport().close();
     mailSession=null;
    } catch (MessagingException mex) {
        System.out.println("send failed, exception: " + mex);
    }
     pb.setProgress(1);
     pi.setProgress(1);
     return null;
           
             }
         };
            
        thimport_generic = new Thread(task_generic);
        thimport_generic.setDaemon(true);
        thimport_generic.start();
  }
 
 public void borraremaillbdproductos() throws NoSuchProviderException, MessagingException, IOException
 {
        IMAPFolder folder = null;
        Store store = null;
        String subject = null;
        Flags.Flag flag = null;
        ExecutorService es = Executors.newCachedThreadPool();
         IdleManager idleManager = null;
          Session session;   
        try 
        {
          Properties props = System.getProperties();
          props.setProperty("mail.store.protocol", "imaps");
         // props.put("mail.event.scope", "session"); // or "application"
        // // props.put("mail.event.executor", es);
          session = Session.getDefaultInstance(props, null);
          store = session.getStore(props.getProperty("mail.store.protocol"));
          store.connect("imap.gmail.com",AbacoApp.s_organizacion.getEmailbdproductos(),AbacoApp.s_organizacion.getPasswordemailbdproductos());
      
         // store.connect("imap.gmail.com",AbacoApp.s_organizacion.getEmailbdproductos(),AbacoApp.s_organizacion.getPasswordemailbdproductos());
         // folder = (IMAPFolder) store.getFolder("[Gmail]/Spam"); // This doesn't work for other email account
        //  folder = (IMAPFolder)store.getFolder("inbox"); //This works for both email account
         folder = (IMAPFolder)store.getDefaultFolder(); 
     //    if(!folder.isOpen())
         //folder.open(Folder.READ_WRITE);
         
       
         Folder [] folders = folder.list();
           System.out.println("Total de folderes:"+folders.length);
				              
		long messgCnt = 0;
		for(Folder currFolder: folders) {
 
			try {
 
				//if ((currFolder.getType() & Folder.HOLDS_MESSAGES) == 0) {
				//	continue; // Skip this folder type
				//}
				currFolder.open(Folder.READ_WRITE);
				Message [] messages = currFolder.getMessages();
 
				// Mark all the messages for delete
				for (Message message: messages) {
					try {
                                                System.out.println("Mensaje contenttype:"+message.getContentType());
				                System.out.println("Mensaje subjet:"+message.getSubject());
						System.out.println("Mensajes before size:"+messages.length);
                                                System.out.println("Mensajes fecha:"+message.getSentDate());
                                                message.setFlag(Flag.DELETED, true);
						MimeMessage currMessage = (MimeMessage) message;
						messages = currFolder.expunge(); // Confirm delete for all the messages on the current folder
						messgCnt += messages.length;
                                                  System.out.println("Mensajes after size:"+messages.length);
						
					}  catch (MessagingException messgExp) {
					}
				}
                                
				currFolder.close(true);
                               
			} catch (MessagingException messgExp) {
			}
 
		}
        
          
        }
       
        finally 
        {
               System.out.println("Cerrando:");
          if (folder != null && folder.isOpen()) { folder.close(true); }
          if (store != null) { store.close(); }
         
        }
       
    }
  private void showAvancerestaurar() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  
 
  gp.getChildren().clear();
  pb=null;
  pi=null;
  pb=new ProgressBar(0);
  pi=new ProgressIndicator(1);
  gp.addRow(0, pb,pi);
  popover.setArrowLocation(PopOver.ArrowLocation.LEFT_CENTER);
  popover.setCornerRadius(4);
   popover.setDetached(true);
  popover.setDetachedTitle("     Subiendo productos..");
  double clickX = bu_nubeemail.localToScreen(bu_nubeemail.getBoundsInLocal()).getMinX();
        double clickY = (bu_nubeemail.localToScreen(bu_nubeemail.getBoundsInLocal()).getMinY() +
                bu_nubeemail.localToScreen(bu_nubeemail.getBoundsInLocal()).getMaxY()) / 2;

        popover.show(bu_nubeemail.getScene().getWindow());
        // Show on left
//        popOver.setX(clickX - popOver.getWidth());
//        popOver.setY(clickY - popOver.getHeight() / 2);
        // Show on right
        popover.setX(clickX + 40.0);
        popover.setY(clickY - popover.getHeight() / 2);
  
 }  
}
