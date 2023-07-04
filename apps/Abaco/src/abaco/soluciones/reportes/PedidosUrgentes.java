    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.reportes;

import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import static abaco.AbacoApp.kardexControllerClient;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Inf_Existencias;
import abaco.controlador.Inf_ExistenciasJerseyClient;
import abaco.controlador.ProductoControllerClient;
import com.aquafx_project.AquaFx;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IdleManager;
import com.sun.mail.util.MailSSLSocketFactory;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import jfxtras.styles.jmetro8.JMetro;
import jxl.Workbook;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import model.Kardex;
import model.Proveedores;
import org.aerofx.AeroFX;
import org.controlsfx.control.PopOver;


/**
 *
 * @author karolyani
 */
public class PedidosUrgentes extends Application {
       IMAPFolder folder = null;
        Store store = null;
        String subject = null;
        Flags.Flag flag = null;
        ExecutorService es = Executors.newCachedThreadPool();
         IdleManager idleManager = null;
          Session session;
    private TableView Ta_Existencias ;
    private ObservableList Ol_Inf_existencias;
     private TitledPane tp_generic;
   
    private GridPane gridpane;
    private GridPane gridpaneRoot;
    private Inf_ExistenciasJerseyClient inf_ExistenciasJerseyClient;
    ProductoControllerClient productoControllerClient;
    private Text T_Totales;
    private TextField T_Valor_total_todo;
    private TextField T_Cant_total_todo;
    private List<Inf_Existencias> inf_Existencias;
    private Label L_Producto;
    private TextField Tf_Producto;
    private List<Kardex> li_kardex=new ArrayList<>();
    private Button B_Exportar;
    private Button bu_email;
    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
} 
           String appClass2;
           Class clz2 ;
           Object app2;
           private Stage stage2;
           private Scene scene2 =null;
           private Parent parent2;
           private ContextMenu contextMenu;
           
    private MenuItem  itemcargarinventario;
    private PopOver popover;
    private  Task<Void>   task_generic;
    private  Thread  thimport_generic;
    private ProgressBar pb ;
    private ProgressIndicator pi;
    private GridPane gp=new GridPane();
    public static double  avance_progress=0;
    String no_ident_proveedor="";
    String html_header="<table style=\"width:100%;border: 1px solid black;\">"+
                          "<tr style=\"tr:hover {background-color: #f5f5f5;}\">"+
                          "<th style=\"border: 1px solid #ddd;\">Código</th>"+
                          "<th style=\"border: 1px solid #ddd;\">Producto</th>"+
                         // "<th style=\"border: 1px solid #ddd;\">Valor/U</th>"+
                          "<th style=\"border: 1px solid #ddd;\">Cantidad</th>"+
                         // "<th style=\"border: 1px solid #ddd;\">Valor total</th>"+
                          "</tr>";
    String html_content="";
    public Parent createContent() throws IOException {
        popover=new PopOver(gp);
        contextMenu=null;
          contextMenu=new ContextMenu(); 
       ImageView  img=null;
         img=new ImageView ("/images/new2.png");
         img.setFitHeight(20);
         img.setFitWidth(20);
         itemcargarinventario=new MenuItem("Cargar Producto a Inventario",img);
         itemcargarinventario.setOnAction(e->{
         
           try{
         if(checkregistroexistente())
                 {
                show2();
                 }
           }catch(Exception x)
           {
               x.printStackTrace();
           }
       });
         contextMenu.getItems().addAll(itemcargarinventario);
          
         stage2= new Stage(StageStyle.DECORATED);
         stage2.setTitle("Agregar Producto a Inventarios");
         stage2.setOnHidden(e->{
             if (e.getEventType() == e.WINDOW_HIDDEN ) {
               
            try{
                
             getRecords();
          
            }catch(Exception e2)
            {
                e2.printStackTrace();
            }
             }
        });
          productoControllerClient=new ProductoControllerClient();
          L_Producto=new Label("Producto: ");
          Tf_Producto=new TextField();
          Tf_Producto.setMinWidth(200);
          Tf_Producto.setPromptText("Encontrar por codigo o  nombre ");
           tp_generic=new TitledPane();
           tp_generic.setText("Existencias Por Producto");
          ImageView imageView;
            imageView=null;
            imageView=new ImageView("/images/email.png");
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            bu_email=new Button("",imageView);
            bu_email.setTooltip(new Tooltip("Enviar email a proveedores de productos  a pedir"));
            bu_email.setOnAction(e->{
            try {
                veremails();
            } catch (MessagingException ex) {
                Logger.getLogger(PedidosUrgentes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PedidosUrgentes.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (popover.isShowing()) {
                popover.hide();
            }  else if (!popover.isShowing()) {
               showAvancerestaurar();
                    }
               avanceenvioemailaproveedor();
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(PedidosUrgentes.class.getName()).log(Level.SEVERE, null, ex);
            } 
            });
        imageView=null;
        imageView=new ImageView("/images/excel.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Exportar=new Button("",imageView);
        B_Exportar.setTooltip(new Tooltip("Exportar a excel"));
        B_Exportar.setOnAction(new  EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent ke) {
                try {
                    if (store != null) { store.close();}
                    exportar_excel();
                } catch (IOException ex) {
                    Logger.getLogger(VentasPorProducto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (WriteException ex) {
                    Logger.getLogger(VentasPorProducto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(PedidosUrgentes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }});
       Tf_Producto.textProperty().addListener((observable, oldValue, newValue) -> {
           getDatosExistencias();
});
       
        T_Totales=new Text("Total: ");
         T_Totales.setFill(Color.BLACK);
          T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD,20));
        
          T_Valor_total_todo=new TextField();
          T_Valor_total_todo.setMaxWidth(130);
          T_Cant_total_todo=new TextField();
          T_Cant_total_todo.setMaxWidth(130);
          inf_ExistenciasJerseyClient=new Inf_ExistenciasJerseyClient();
        Ol_Inf_existencias= FXCollections.observableArrayList();
        gridpane=new GridPane();
        gridpaneRoot=new GridPane();
     //   gridpane.getStyleClass().add("category-page");
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        
        gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
        GridPane.setValignment(gridpaneRoot, VPos.TOP);
        //Creamos la tabla para ver existencias
       
        Ta_Existencias=new TableView();
        Ta_Existencias.setContextMenu(contextMenu);
        TableColumn Codigo = new TableColumn();
        Codigo.setMinWidth(200);
        Codigo.setText("Código");
        Codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                    return new SimpleStringProperty(kardex.getValue().getProducto().getCodigobarras());
               }catch(Exception e)
               {
                 return new SimpleStringProperty("0");
               
               }
                            
            }
        });
        
        TableColumn Nombre = new TableColumn();
        Nombre.setMinWidth(340);
        Nombre.setText("Producto");
        Nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                    return new SimpleStringProperty(kardex.getValue().getProducto().getNombre());
               }catch(Exception e)
               {
                 return new SimpleStringProperty("NT");
               
               }
                            
            }
        });
       
        TableColumn Cantidad_saldo = new TableColumn();
        Cantidad_saldo.setMinWidth(200);
        Cantidad_saldo.setText("Cantidad Saldo");
        Cantidad_saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                    return new SimpleStringProperty(String.valueOf(kardex.getValue().getCantidad_saldo()));
               }catch(Exception e)
               {
                 return new SimpleStringProperty("0");
               
               }
                            
            }
        });
 
        TableColumn Valor_saldo = new TableColumn();
        Valor_saldo.setMinWidth(200);
        Valor_saldo.setText("Valor Saldo");
        Valor_saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                    return new SimpleStringProperty(kardex.getValue().getValor_saldo().toString());
               }catch(Exception e)
               {
                 return new SimpleStringProperty("0");
               
               }
                            
            }
        });
 
      
        Ta_Existencias.setMinHeight(550);
        Ta_Existencias.setMaxHeight(550);
        Ta_Existencias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Ta_Existencias.getColumns().addAll(Codigo, Nombre,Cantidad_saldo,Valor_saldo );
        gridpane.add(L_Producto, 0, 0);
        gridpane.add(Tf_Producto, 1, 0);
        gridpane.add(B_Exportar, 2, 0);
        gridpane.add(bu_email, 3, 0);
        Tf_Producto.setAlignment(Pos.CENTER_LEFT);
        gridpane.add(Ta_Existencias, 0, 1,4,1);
        GridPane.setHalignment(Tf_Producto, HPos.LEFT);
        GridPane.setHalignment(L_Producto, HPos.CENTER);
       
        gridpane.setMinSize(1000, 610);
        StackPane stack=new StackPane();
        
        tp_generic.setContent(gridpane);
        stack.setAlignment(Pos.TOP_CENTER);
       
           stack.getChildren().addAll(tp_generic);
           switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gridpane);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gridpane);
                     break;              
         }
             //agregamos el formulario y la tabla
       // gridpane.getStyleClass().add("background");
        gridpane.setAlignment(Pos.TOP_CENTER);
     //  gridpane.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        getRecords();
        return stack;
       }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
          javafx.scene.Group g =
                new javafx.scene.Group(imageView);
   
        return g;
    }
 
 private void getDatosExistencias()
 {
      
      li_kardex=AbacoApp.li_kardex.stream().filter(line ->(line.getProducto().getCodigobarras().trim().toUpperCase().contains(Tf_Producto.getText().trim().toUpperCase()) || line.getProducto().getNombre().toUpperCase().contains(Tf_Producto.getText().toUpperCase())) && (line.getCantidad_saldo()<=line.getProducto().getTopminimo() ))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());
       Ol_Inf_existencias.clear();
       Ol_Inf_existencias.addAll(li_kardex);
       Ta_Existencias.setItems(Ol_Inf_existencias);
       float cant_total=0;
       BigDecimal valor_total=BigDecimal.ZERO;
       for(Kardex infe: li_kardex)
       {
           cant_total=cant_total+infe.getCantidad_saldo();
           valor_total=valor_total.add(infe.getValor_saldo());
       }
      
       T_Cant_total_todo.setText(String.valueOf(cant_total));
       T_Valor_total_todo.setText(valor_total.toString());
      
      
 }
   @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
   public void exportar_excel() throws IOException, WriteException
    {
        String rutaArchivo = System.getProperty("user.home")+"/Existencias.xls"; 
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        if(archivoXLS.exists()) 
        {
            archivoXLS.delete(); 
            archivoXLS.createNewFile();
        }
        WritableWorkbook libro;
         FileOutputStream archivo = new FileOutputStream(archivoXLS);
        libro = Workbook.createWorkbook(archivo);
        WritableSheet hoja = libro.createSheet("Mi hoja de trabajo 1",0);
         int f=0;
         
                
         for(Kardex ie:li_kardex )
         {
            
                       
            
              if(f==0)
             {
                
                 hoja.insertRow(f);
                 
                // CellType.LABEL;
                 WritableCellFeatures writableCellFeatures=new WritableCellFeatures();
                 writableCellFeatures.removeDataValidation();
                 label=null;
                 label=new jxl.write.Label(f, f, "Código");
                 hoja.addCell(label);
                             
                 label=null;
                 label=new jxl.write.Label(1, f, "Producto");
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(2, f, "Cantidad Saldo");
                 hoja.addCell(label);
               
              
               label=null;
                 label=new jxl.write.Label(3, f, "Valor Saldo");
                 hoja.addCell(label);
             }
               f++;
                 hoja.insertRow(f);
                 label=null;
                 label=new jxl.write.Label(0, f, ie.getProducto().getCodigobarras());
                 hoja.addCell(label);
              
               
                 label=null;
                 label=new jxl.write.Label(1, f, ie.getProducto().getNombre());
                 hoja.addCell(label);
                 
                 number=null;
                 number=new jxl.write.Number(2, f, ie.getCantidad_saldo());
                 hoja.addCell(number);
                 
                 
                number=null;
                 number=new jxl.write.Number(3, f, ie.getValor_saldo().floatValue());
                 hoja.addCell(number);
               
             
              
              
            
         }
         libro.write();
         libro.close();
         archivo.close();
         Desktop.getDesktop().open(archivoXLS);
    } 
   
  private void show2()  
  {
      
     
      appClass2="abaco.soluciones.documentos.IngresoDirectoInventario";
      clz2=null;
      try{
      clz2 = Class.forName(appClass2);
      app2 = clz2.newInstance();
  
      parent2=null;
      parent2 = (Parent) clz2.getMethod("createContent").invoke(app2);
      scene2=null;
       scene2=new Scene(parent2, Color.TRANSPARENT);
       
        if (stage2.isShowing())
       {
           stage2.close();
       }      
        
        
//set scene to stage
                stage2.sizeToScene();
                
              
                //center stage on screen
                stage2.centerOnScreen();
                stage2.setScene(scene2);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stage2.show();
      }catch(Exception e)
      {
         e.printStackTrace();
      }
  }
private void getRecords()
{
       AbacoApp.li_kardex =kardexControllerClient.getRecords();
       List<Kardex> l_kardex= AbacoApp.li_kardex.stream().filter(distinctByKey(b ->b.getProducto())).collect(Collectors.toList());//.distinct().collect(Collectors.toList());
       AbacoApp.li_kardex=l_kardex;
       getDatosExistencias();
}
private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
 {
       if ((Ta_Existencias.getSelectionModel())!=null)
       {
           AbacoApp.s_kardex=(Kardex)Ta_Existencias.getSelectionModel().getSelectedItem();
           return true;
       }
       else
       {
           return false;
       }
   }

public void veremails() throws NoSuchProviderException, MessagingException, IOException
 {
      String os = System.getProperty("os.name"); 
        try 
        {
          Properties props = System.getProperties();
          props.setProperty("mail.store.protocol", "imaps");
          props.put("mail.event.scope", "session"); // or "application"
          props.put("mail.event.executor", es);
          // props.put("mail.imaps.usesocketchannels", "true");
           session = Session.getDefaultInstance(props, null);
      idleManager = new IdleManager(session, es);
          store = session.getStore("imaps");
          store.connect("imap.gmail.com",AbacoApp.s_organizacion.getEmail(),AbacoApp.s_organizacion.getPasswordemail());

         // folder = (IMAPFolder) store.getFolder("[Gmail]/Spam"); // This doesn't work for other email account
          folder = (IMAPFolder) store.getFolder("inbox"); //This works for both email account
         
       
 
          if(!folder.isOpen())
          folder.open(Folder.READ_WRITE);
          folder.addMessageCountListener(new MessageCountAdapter() {
            public void messagesAdded(MessageCountEvent ev) {
                Folder folder = (Folder)ev.getSource();
                Message[] msgs = ev.getMessages();
                System.out.println("Folder: " + folder +
                    " got " + msgs.length + " new messages");
              
            }
        });
       
         Message[] messages = folder.getMessages();
          System.out.println("No of Messages : " + folder.getMessageCount());
          System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
          System.out.println(messages.length);
          
         //  idleManager.watch(folder);
          //  folder.idle();
         //  folder.idle();
          for (int i=0; i < messages.length;i++) 
          {

            System.out.println("*****************************************************************************");
            System.out.println("MESSAGE " + (i + 1) + ":");
            Message msg =  messages[i];
            subject = msg.getSubject();
            System.out.println("Subject: " + subject);
            System.out.println("From: " + msg.getFrom()[0]);
            System.out.println("To: "+msg.getAllRecipients()[0]);
            System.out.println("Date: "+msg.getReceivedDate());
            System.out.println("Size: "+msg.getSize());
            System.out.println(msg.getFlags());
            System.out.println("Body: \n"+ msg.getContent());
            System.out.println(msg.getContentType());
            Multipart multiPart = (Multipart) msg.getContent();
 
for (int j = 0; j < multiPart.getCount(); j++) {
    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
        
       String destFilePath = "";
  if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
      {
          destFilePath = "/Attachment/" + part.getFileName();
      }
  else
  {
    destFilePath = "\\Attachment\\" + part.getFileName();  
  }
FileOutputStream output = new FileOutputStream(destFilePath);
 
InputStream input = part.getInputStream();
 
byte[] buffer = new byte[4096];
 
int byteRead;
 
while ((byteRead = input.read(buffer)) != -1) {
    output.write(buffer, 0, byteRead);
}
output.close();
    }
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
  popover.setDetachedTitle("     Email a proveedor");
  double clickX = bu_email.localToScreen(bu_email.getBoundsInLocal()).getMinX();
        double clickY = (bu_email.localToScreen(bu_email.getBoundsInLocal()).getMinY() +
                bu_email.localToScreen(bu_email.getBoundsInLocal()).getMaxY()) / 2;

        popover.show(bu_email.getScene().getWindow());
        // Show on left
//        popOver.setX(clickX - popOver.getWidth());
//        popOver.setY(clickY - popOver.getHeight() / 2);
        // Show on right
        popover.setX(clickX + 40.0);
        popover.setY(clickY - popover.getHeight() / 2);
  
 }   
    private void avanceenvioemailaproveedor() throws ParseException, InterruptedException
    {
        
             gp.setAlignment(Pos.CENTER);
             avance_progress=0.0;
     
             task_generic=new Task<Void>() {
        
            @Override
            protected Void call() throws Exception {
          
             double i=0;
                Collections.sort(li_kardex, (o1, o2) -> o1.getProducto().getProveedores().getNo_ident().compareTo(o2.getProducto().getProveedores().getNo_ident()));
              int reg=0;
              Proveedores proveedores=null;
             for(Kardex k: li_kardex)
             {  
              if(reg==0)
              {
                 no_ident_proveedor=k.getProducto().getProveedores().getNo_ident();
                 proveedores=k.getProducto().getProveedores();
                 html_content=html_header+"<tr style=\"tr:hover {background-color: #f5f5f5;}\"><td style=\"border: 1px solid #ddd;\">"+k.getProducto().getCodigobarras()+"</td>"+
                                                   "<td style=\"border: 1px solid #ddd;\">"+k.getProducto().getNombre()+"</td>" +  
                                                  // "<td style=\"border: 1px solid #ddd;\">"+k.getProducto().getPrecio().toString()+"</td>"+
                                                   "<td style=\"border: 1px solid #ddd;\">"+k.getProducto().getTopmaximo()+"</td>";
                                                  // "<td style=\"border: 1px solid #ddd;\">"+k.getProducto().valortotalpedidoproducto()+"</td><tr>";
              }
              else
              {
                 if(no_ident_proveedor.equals(k.getProducto().getProveedores().getNo_ident()))
                 {
                     html_content=html_content+"<tr style=\"tr:hover {background-color: #f5f5f5;}\"><td style=\"border: 1px solid #ddd;\">"+k.getProducto().getCodigobarras()+"</td>"+
                                                   "<td style=\"border: 1px solid #ddd;\">"+k.getProducto().getNombre()+"</td>" +  
                                                  // "<td style=\"border: 1px solid #ddd;\">"+k.getProducto().getPrecio().toString()+"</td>"+
                                                   "<td style=\"border: 1px solid #ddd;\">"+k.getProducto().getTopmaximo()+"</td>";
                                                 //  "<td style=\"border: 1px solid #ddd;\">"+k.getProducto().valortotalpedidoproducto()+"</td><tr>";  
                 }
                 else
                 {
                     html_content=html_content+"</table>"+
                     "<br></br><p>"+"Por: " + AbacoApp.s_organizacion.getNombre()+" Nit: "+AbacoApp.s_organizacion.getNit()+ " Tel: "+AbacoApp.s_organizacion.getTel()+ " Dir: "+AbacoApp.s_organizacion.getDir()+"</p>"+
                     "<br/>"+"<p>\"Gracias, por su atención por favor confirmar por este medio el envío del pedido\"</p>";
                     enviarEmail(proveedores.getEmail());
                     no_ident_proveedor=k.getProducto().getProveedores().getNo_ident();
                     proveedores=k.getProducto().getProveedores();
                     html_content=html_header;
                 }
              }
              avance_progress=avance_progress+(1.0/li_kardex.size());
              i=avance_progress;
             reg++;
              if(reg==li_kardex.size())
              {
                   html_content=html_content+"</table>"+
                     "<br></br><p>"+"Por: " + AbacoApp.s_organizacion.getNombre()+" Nit: "+AbacoApp.s_organizacion.getNit()+ " Tel: "+AbacoApp.s_organizacion.getCelular()+ " Dir: "+AbacoApp.s_organizacion.getDir()+"</p>"+
                     "<br/>"+"<p>\"Gracias, por su atención por favor confirmar por este medio el envío del pedido\"</p>";
                     enviarEmail(proveedores.getEmail());
                     no_ident_proveedor=k.getProducto().getProveedores().getNo_ident();
                     proveedores=k.getProducto().getProveedores();
                     html_content=html_header;
              }
               System.out.println("avance:"+i);
              pb.setProgress(avance_progress);
              pi.setProgress(avance_progress);
              
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
 public void  enviarEmail(String toemail) throws GeneralSecurityException, MessagingException
 {
       
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
                return new PasswordAuthentication(AbacoApp.s_organizacion.getEmail(),AbacoApp.s_organizacion.getPasswordemail());
            }

        });
}
  try {
        MimeMessage msg = new MimeMessage(mailSession);
        msg.setFrom();
        msg.setRecipients(Message.RecipientType.TO,toemail);
        msg.setSubject("Pedido de "+AbacoApp.s_organizacion.getNombre());
        msg.setFrom(AbacoApp.s_organizacion.getEmail());
        msg.setSentDate(new Date());
       
       Multipart mailBody = new MimeMultipart();
       BodyPart texto = new MimeBodyPart();
       texto.setText(html_content);
       mailBody.addBodyPart(texto);
      
       

  
    // msg.setContent(mailBody); 
     msg.setText(html_content,"ISO-8859-1","html");
     Transport.send(msg);
    
     mailSession.getTransport().close();
     mailSession.getStore().close();
     msg.getSession().getTransport().close();
     mailSession=null;
    } catch (MessagingException mex) {
        System.out.println("send failed, exception: " + mex);
    }
         
               
            }     
}
