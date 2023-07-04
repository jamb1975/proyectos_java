package abaco.soluciones.procesosadministrativos;
import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import abaco.PageBrowser;
import abaco.SearchPopover;
import abaco.control.SearchBox;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.Factura;
import model.FacturaItem;
import model.Personas;
import abaco.controlador.FacturaControllerClient;
import abaco.controlador.GenPersonasControllerClient;
import abaco.controlador.ProductoControllerClient;
import abaco.digitos.NumeroDigital;
import abaco.soluciones.reportes.Existencias2;
import abaco.soluciones.reportes.ImpresoraTermica;
import abaco.soluciones.ventas.ImpresionFactura;
import abaco.util.UtilDate;
import abaco.util.Utils;
import com.aquafx_project.AquaFx;
import com.sun.mail.util.MailSSLSocketFactory;
import fields.FieldDouble;
import fields.FieldNumero;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.print.PrintException;
import jfxtras.styles.jmetro8.JMetro;
import model.Kardex;
import model.Producto;
import org.aerofx.AeroFX;
public class FFactura extends Application {
    //grupo
    Group Gr_NumeroDigital;
    Group Gr_ValorCambio;
    LinearGradient gradient2;
    //String Converter
    StringConverter<Object> sc;
        //buttons
         private Button B_Print;
         private Button B_New;
         private Button B_Save;
         private Button B_Next;
         private Button B_Previous; 
         private Button B_Pdf;
         private Button bu_email;
         private Button B_Ok;
         private Button B_No;
        //Tabla Items de facturaa
         private  TableView Ta_FacturaItems; 
        //GridPane row and columns
         private  GridPane  Gp_Factura;
         private  GridPane  Gp_DatosEmpresa;
         private  GridPane  Gp_DatosCliente;
         private  GridPane  Gp_Total;
         private  GridPane  Gp_Producto;
         private  GridPane  Gp_FacturaItems;
         private  GridPane  Gp_Totales;
         private  GridPane  Gp_VlrRecibido;
        //TitledPane container solucion AbacoApp.s_factura
         private  TitledPane Tp_Factura;
         private TitledPane P_DatosEmpresa;
        //Texto
         private  Text T_NombreNitEmpresa;
         private Text T_NoFactura;
         private Text T_DireccioTelEmpresa;
         private Text T_Total;
       //Etiquetas datos cliente
         private   Label L_NombreCliente;
         private  Label L_CcCliente;
         private  Label L_DireccionCliente;
         private  Label L_TelefonoCliente;
         private  Label L_FormaPago;
         private Label L_Fecha;
         private Label L_Total;
         private Label L_Iva;
         private Label L_Subtotal;
         private Label L_Producto;
         private Label L_Descuento;
         private Label L_Cantidad;
         private Label L_F6;
         private Label L_F7;
         private Label L_DescuentoFactura;
         private Label L_ValorRecibido;
         private Label L_ValorCambio;
         private Label L_MostrarIva;
         private Label la_celular; 
         private Label la_email;
         private Label la_codigo;
         private static Label L_Empleado;
         private Label success;
        //Campos de texto datos cliente 
         private  TextField Tf_NombreCliente;
         private  TextField Tf_CcCliente;
         private  TextField Tf_DireccionCliente;
         private  TextField Tf_TelefonoCliente;
         private  TextField Tf_Fecha;
         private  SearchBox sb_producto=new SearchBox();
         private  SearchPopover sp_producto;
         private  FieldDouble Tf_Descuento;
         private  FieldNumero Tf_Cantidad;
         private  FieldDouble Tf_Iva;
         private  TextField Tf_Subtotal;
         private  TextField Tf_Total;
         private  TextField Tf_DescuentoFactura;
         private  TextField Tf_ValorRecibido;
         private  TextField celular;
         private  TextField email;
         private  TextField codigo;
         private  CheckBox Ch_MostrarIva;
         private static TextField cajero;
         private RadioButton Rb_Contado;
         private RadioButton Rb_Credito;
         private Vector<Long>Ve_Empleados=new Vector<Long>();
        //Vbox
         private HBox V_box;
         private VBox V_Factura;
         private HBox H_Botones;
         private HBox H_Botonesnav;
       //Togglegroup
         private ToggleGroup toggleGroup;
        //creamos el numero digital
         private NumeroDigital numerodigital;
         private NumeroDigital Nd_ValorCambio;
        //Objetos que contiene los datos de la AbacoApp.s_factura
         private  ObservableList  Ol_FacturaItems = FXCollections.observableArrayList();
         private Object[] O_ListFacturaItems;
         private TableColumn Tc_NoItem;
         private TableColumn Tc_Producto;
         private TableColumn Tc_Cantidad;
         private TableColumn Tc_ValorU;
         private TableColumn Tc_ValorT;
         
         //Objetos de persistencia y modelso de datos
          FacturaControllerClient facturaControllerClient;
          GenPersonasControllerClient genPersonasControllerClient;
          ProductoControllerClient productoControllerClient;
          //Stage mostrar formulario findproducto
       
          private Personas cliente;
          private GridPane Gp_Message;
          private     Label L_Message;
          private  Runnable Task_Message;
          Thread backgroundThread;
          private StackPane  stack;
          DecimalFormat format = new DecimalFormat( "#.0" );
          ImpresionFactura impresionFactura=new ImpresionFactura();
          private TitledPane tp_generic;
          private HBox hbox=new HBox();
           BigDecimal vdescuento=BigDecimal.ZERO;
           SearchBox sb_cliente=new SearchBox();
           SearchPopover sp_cliente;
           private Button B_Buscar;
           private ImageView imageView;
           HBox hbox_cliente=new HBox();
           String appClass;
           Class clz ;
           Object app ;
           private Stage stage;
           private Scene scene =null;
           String appClass2;
           
           Class clz2 ;
           Object app2;
           String appClass_inv="abaco.soluciones.reportes.Existencias2";
           Class clz_inv ;
           Object app_inv;
           private Parent parent_inv;
           private Stage stage2;
           private Scene scene2 =null;
           private Parent parent2;
           Dialog<Pair<String, String>> dialog = new Dialog<>();
           ButtonType loginButtonType ;
           GridPane grid = new GridPane();
           PasswordField password = new PasswordField();
           boolean editarfactura=false;
           TextField username = new TextField();
           private int numeroregistrosanteriores=0;
         public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
               //Objetos gridpane
                     clz2=null;
     
      clz_inv = Class.forName(appClass_inv);
      app_inv = clz_inv.newInstance();
      parent_inv = (Parent) clz_inv.getMethod("createContent").invoke(app_inv);
         Gr_NumeroDigital=new Group();
        
dialog.setTitle("Login");
dialog.setHeaderText("Privilegios para Editar Facturas");

// Set the icon (must be included in the project).
dialog.setGraphic(new ImageView("/images/candado.png"));

// Set the button types.
loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);

// Create the username and password labels and fields.

grid.setHgap(10);
grid.setVgap(10);
grid.setPadding(new Insets(20, 150, 10, 10));


username.setPromptText("Username");

password.setPromptText("Password");

grid.add(new Label("Username:"), 0, 0);
grid.add(username, 1, 0);
grid.add(new Label("Password:"), 0, 1);
grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.

loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
username.textProperty().addListener((observable, oldValue, newValue) -> {
    loginButton.setDisable(newValue.trim().isEmpty());
});

dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
dialog.setResultConverter(dialogButton -> {
    if (dialogButton == loginButtonType) {
        return new Pair<>(username.getText(), password.getText());
    }
    return null;
});



   
        stage= new Stage(StageStyle.DECORATED);
        stage.setTitle("Clientes");
        stage2= new Stage(StageStyle.DECORATED);
        stage2.setTitle("Agregar Producto a Inventarios");
        stage.setOnHidden(e->{
             if (e.getEventType() == e.WINDOW_HIDDEN ) {
               
            try{
                
            
             Tf_CcCliente.setText(AbacoApp.s_genpersonas.getNo_ident());
             Tf_DireccionCliente.setText(AbacoApp.s_genpersonas.getDir1());
             celular.setText(AbacoApp.s_genpersonas.getCelular());
               sb_cliente.setText(AbacoApp.s_factura.getCustomer().getNo_ident()+" "+AbacoApp.s_factura.getCustomer().getNombre()); 
   
             email.setText(AbacoApp.s_genpersonas.getEmail());
           
            sp_cliente.hide();
            }catch(Exception e2)
            {
                e2.printStackTrace();
            }
             }
        });
         stage2.setOnHidden(e->{
             if (e.getEventType() == e.WINDOW_HIDDEN ) {
               
            try{
                
             findByCodigo();
          
            }catch(Exception e2)
            {
                e2.printStackTrace();
            }
             }
        });
             imageView=null;
            imageView=new ImageView("/images/find.png");
            imageView.setFitHeight(17);
            imageView.setFitWidth(17);
             B_Buscar=new Button();
             B_Buscar.setGraphic(imageView);
             B_Buscar.setText("");
             hbox_cliente.getChildren().addAll(sb_cliente,B_Buscar);
             B_Buscar.setOnAction(e->{
                 show();
             });
           stack=new StackPane();
           tp_generic=new TitledPane();
           tp_generic.setCollapsible(false);
           tp_generic.setText("Factura de Venta");
           Gp_Factura=new GridPane();
           Gp_DatosEmpresa=new GridPane();
           Gp_DatosCliente=new GridPane();
           Gp_Total=new GridPane();
           Gp_Producto=new GridPane();
           Gp_FacturaItems=new GridPane();
           Gp_Totales=new GridPane();
           Gp_VlrRecibido=new GridPane();
           Gp_VlrRecibido.setVisible(false);
           Gp_VlrRecibido.setStyle(" -fx-background-color: #DFDFDF");
           Gp_VlrRecibido.setOnKeyPressed(e->{
               if(e.getCode().equals(e.getCode().ESCAPE))
               {
                   removeGp_VlrRecibido();
                   
               }
           });
           
        //mensaje de procesos
         Gp_Message=new GridPane();
        Gp_Message=new GridPane();
        Gp_Message.setMinWidth(Gp_Factura.getLayoutBounds().getWidth());
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
        
        Thread th = new Thread(task);
        th.setDaemon(false);
        th.start();
      Tf_NombreCliente=new TextField("Cliente Varios");
      Tf_CcCliente=new TextField("0000000");
      Tf_DireccionCliente=new TextField();
      Tf_Fecha=new TextField();
      cajero=new TextField(AbacoApp.s_usuarios.getGenPersonas().getNombre());
      sb_producto.setMinWidth(200);
      sb_producto.setPromptText("Digite código o Enter para busqueda general");
      System.out.println("kardex->"+AbacoApp.s_kardex);
      if(AbacoApp.s_kardex==null)
      {
          AbacoApp.s_kardex=new Kardex();
      }
      System.out.println("persona->"+AbacoApp.s_genpersonas);
      AbacoApp.s_genpersonas=null;
      AbacoApp.s_genpersonas=new Personas();
      sp_producto=new SearchPopover(sb_producto, new PageBrowser(), AbacoApp.s_producto, false);
       sp_producto.setMaxSize(200, 250);
      sp_cliente=new SearchPopover(sb_cliente,new PageBrowser() , AbacoApp.s_genpersonas, false);
      sp_cliente.setMaxSize(200, 250);
      
      Tf_Descuento=new FieldDouble();
      Tf_Descuento.setText("0");
      Tf_Descuento.setOnAction(e->{
          
     // Tf_Descuento.setText(vdescuento.toBigIntegerExact().toString());  
      Tf_Cantidad.requestFocus();
      });
      Tf_Cantidad=new FieldNumero(7);
      Tf_Cantidad.setPromptText("Enter para agregar item");
      Tf_Subtotal=new TextField("0");
       Tf_Subtotal.setMinWidth(100);
      Tf_Iva=new FieldDouble();
      Tf_Iva.setText("0");
      Tf_Total=new TextField("0000000");
      Tf_Total.setMinWidth(100);
      getnumerodigital();
      Tf_TelefonoCliente=new TextField("");
      Tf_DescuentoFactura=new TextField("0");
      Tf_DescuentoFactura.setMinWidth(100);
      Tf_ValorRecibido=new FieldNumero(10);
      Tf_ValorRecibido.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 24));
      Tf_ValorRecibido.setMinHeight(30);
      Tf_ValorRecibido.setStyle("-fx-font-size:24;");
        Tf_ValorRecibido.setOnKeyPressed(e->{
               if(e.getCode().equals(e.getCode().ESCAPE))
               {
                   removeGp_VlrRecibido();
                   
               }
           });
      Ch_MostrarIva=new CheckBox();
        //Inicializamos objetos de pesistencia
              facturaControllerClient=new FacturaControllerClient();
              productoControllerClient=new ProductoControllerClient();
             
       //Inicializamos objetos de interfaz
             
           //Cargamos los datos en la tabla Ta_facturaItems
             
             Ta_FacturaItems=new TableView();
             changeColumns();
              sc = new StringConverter<Object>() {
            @Override
            public String toString(Object t) {
                return t == null ? null : t.toString();
            }
 
            @Override
            public Object fromString(String string) {
                modifyItem(string);
                return string;
            }
        };
           
            
            //botones
           
            ImageView imageView=new ImageView("/images/print.png");
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            B_Print=new Button("",imageView);
            
            imageView=null;
            imageView=new ImageView("/images/pdf.png");
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            B_Pdf=new Button("",imageView);
          
            imageView=null;
            imageView=new ImageView("/images/email.png");
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            bu_email=new Button("",imageView);
          
            imageView=null;
            imageView=new ImageView("/images/new2.png");
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            B_New=new Button("", imageView);
            
            imageView=null;
            imageView=new ImageView("/images/save.png");
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            B_Save=new Button("", imageView);
          
           
           //Botones de Navegación
           
            imageView=null;
            imageView=new ImageView("/images/previous.png");
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            B_Previous=new Button("", imageView);
            
           //acciones de botones y cuadros de texto
           B_Previous.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
              numeroregistrosanteriores++;
               AbacoApp.s_factura=facturaControllerClient.previous(AbacoApp.s_factura,1);
                SetDatosFactura();
            /*  if(numeroregistrosanteriores>7)
              {
             Optional<Pair<String, String>> result = dialog.showAndWait();

          result.ifPresent(usernamePassword -> {
           System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
          if(usernamePassword.getValue().equals("abaco2019") && usernamePassword.getKey().equals("admin"))
         {   editarfactura=true;
             if(editarfactura)
             {   
                AbacoApp.s_factura=facturaControllerClient.previous(AbacoApp.s_factura,1);
                SetDatosFactura();
                editarfactura=false;
             }
         }
         });
              
              }  
              else
              {
                AbacoApp.s_factura=facturaControllerClient.previous(AbacoApp.s_factura,1);
                SetDatosFactura();
                editarfactura=false;
              }
            */}});
            imageView=null;
            imageView=new ImageView("/images/next.png");
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            B_Next=new Button("", imageView);
            
          imageView=null;
            imageView=new ImageView("/images/print.png");
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            B_Ok=new Button("Imprimir", imageView);
            B_Ok.setOnAction(e->{
               try {
                   printFactura(0);
                   removeGp_VlrRecibido();
                   nuevaFactura();
               } catch (IOException ex) {
                   Logger.getLogger(FFactura.class.getName()).log(Level.SEVERE, null, ex);
               } catch (PrintException ex) {
                   Logger.getLogger(FFactura.class.getName()).log(Level.SEVERE, null, ex);
               }
            });
             B_Ok.setOnKeyPressed(e->{
                 if(e.getCode().equals(e.getCode().ENTER))
                 {
                     try{
                printFactura(0);
                   removeGp_VlrRecibido();
                   nuevaFactura();
               } catch (IOException ex) {
                   Logger.getLogger(FFactura.class.getName()).log(Level.SEVERE, null, ex);
               } catch (PrintException ex) {
                   Logger.getLogger(FFactura.class.getName()).log(Level.SEVERE, null, ex);
               }
                 }
                 
            });
             imageView=null;
            imageView=new ImageView("/images/cancelar.png");
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
              B_No=new Button("Cancelar", imageView);
            B_No.setOnAction(e->{
                   removeGp_VlrRecibido();
                   nuevaFactura();
            });
             B_No.setOnKeyPressed(e->{
                 if(e.getCode().equals(e.getCode().ENTER))
                 {
                     
                
                   removeGp_VlrRecibido();
                   nuevaFactura();
               
                 }
                 
            });
              sb_cliente.setOnAction(e->{
              Tf_CcCliente.setText(AbacoApp.s_genpersonas.getNo_ident());
              Tf_DireccionCliente.setText(AbacoApp.s_genpersonas.getCelular());
              celular.setText(AbacoApp.s_genpersonas.getDir1());
          });
              Tf_CcCliente.setDisable(true);
              Tf_DireccionCliente.setDisable(true);
               Tf_TelefonoCliente.setDisable(true);
              //radio button
             //objeto togglegroou me agrupa radio button o radio check
             hbox.setStyle("-fx-background-color: transparent;");
             hbox.getChildren().addAll(B_Ok,B_No);
             hbox.setSpacing(4);
            toggleGroup=new ToggleGroup();
            Rb_Contado=new RadioButton("Contado");
            Rb_Contado.setToggleGroup(toggleGroup);
            Rb_Credito=new RadioButton("Credito");
            Rb_Credito.setToggleGroup(toggleGroup);
     
       //etiquetas datos AbacoApp.s_factura
       L_Fecha=new Label("Fecha:");
       L_FormaPago=new Label("Forma de pago:   ");
       L_NombreCliente=new Label("Cliente:");
       L_CcCliente=new Label("Nit o Cc:");
       L_DireccionCliente=new Label("Dirección:"); 
       L_Producto=new Label("Producto");
       L_Descuento=new Label("Descuento(%)");
       L_Cantidad=new Label("Cantidad");
       L_Subtotal=new Label("Subtotal: ");
       L_Subtotal.setMinWidth(70);
       L_Iva=new Label("Iva: ");
       L_Total=new Label("Total: ");
       L_Total.setMinWidth(70);
       L_DescuentoFactura=new Label("Descuento: ");
       L_DescuentoFactura.setMinWidth(70);
       L_F6=new Label("Control + p -----> Imprimir Factura");
       L_F7=new Label("Contrl + d -----> Devolver Cambio");
      L_F6.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 10));
      L_F7.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 10));
      L_ValorRecibido=new Label("Recibido: ");
      L_ValorRecibido.setStyle("-fx-font-size:24;-fx-text-fill: #007fff;-fx-background-color:transparent;");
      L_ValorCambio=new Label("Cambio: ");
      L_ValorCambio.setStyle("-fx-font-size:24; -fx-text-fill: #007fff; ;-fx-background-color:transparent;");
      L_Empleado=new Label("Empleado: ");
      L_MostrarIva=new Label("No Mostrar Iva: ");
      la_celular=new Label("Celular: ");
      la_email=new Label("Email: ");
      la_codigo=new Label("Codigo");
      codigo=new TextField("");
      codigo.setMaxWidth(200);
      email=new TextField();
       
      celular=new TextField();
      success=new Label();
      //Texto configuracion
        T_NoFactura=new Text("FACTURA N° ");
        T_NoFactura.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_NoFactura.setFont(Font.font("ARIAL", FontWeight.BOLD,14));
        T_NoFactura.setFill(Color.WHITE);
        T_Total=new Text("TOTAL");
        T_Total.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_Total.setFont(Font.font("ARIAL", FontWeight.BOLD,30));
        
        
        //caja listbox
        
       //Objeto Vbox coloca dentro de una caja objetos verticalmente
         V_box=new HBox();
         V_box.setSpacing(5);//espacio verticalmente entre objetos
         V_box.getChildren().addAll(Rb_Contado,Rb_Credito);
       
         H_Botones=new HBox();
         H_Botones.setSpacing(5);//espacio verticalmente entre objetos
         H_Botones.getChildren().addAll(B_New,B_Save,B_Print,B_Pdf,bu_email,B_Previous,B_Next);
         
        Gp_Factura.setHgap(5);
         Gp_Factura.setVgap(7);
        
         //****************Captura datos del comprador***********************************
         Gp_Factura.add(L_Fecha, 0, 0,1,1);
         Gp_Factura.add(Tf_Fecha, 1, 0,1,1);
         Gp_Factura.add(L_NombreCliente, 0, 1,1,1);
         Gp_Factura.add(hbox_cliente, 1, 1,1,1);
         Gp_Factura.add(L_FormaPago, 0, 2,1,1);
         Gp_Factura.add(V_box, 1, 2,1,1);
        
       
         
         //*****************muestra en formato display el total de factura***************************
         getinitnumerodigital();
         GridPane.setHalignment(T_NoFactura, HPos.CENTER);
         GridPane.setValignment(T_NoFactura, VPos.TOP);
         Gp_Factura.add(T_NoFactura, 2, 0,1,1);
         GridPane.setHalignment(Gr_NumeroDigital, HPos.CENTER);
         GridPane.setValignment(Gr_NumeroDigital, VPos.TOP);
         
         Gp_Factura.add(Gr_NumeroDigital, 2, 1, 1, 5);
         
         //***********captura detalles producto************************
           ColumnConstraints column1 = new ColumnConstraints();
           column1.setHalignment(HPos.LEFT);
          Gp_Factura.getColumnConstraints().add(column1); 
         VBox vb_codigo=new VBox(la_codigo,codigo);
         vb_codigo.setAlignment(Pos.CENTER);
         VBox vb_producto=new VBox(L_Producto,sb_producto);
         vb_producto.setAlignment(Pos.CENTER);
         VBox vb_descuento=new VBox(L_Descuento,Tf_Descuento);
         vb_descuento.setAlignment(Pos.CENTER);
         VBox vb_cantidad=new VBox(L_Cantidad,Tf_Cantidad);
          vb_cantidad.setAlignment(Pos.CENTER);
         HBox hb_capturaproducto=new HBox(vb_codigo,vb_producto,vb_descuento,vb_cantidad);
        
           hb_capturaproducto.setSpacing(3);
          GridPane.setHalignment(hb_capturaproducto, HPos.CENTER);
          GridPane.setValignment(hb_capturaproducto, VPos.TOP);
        
         Gp_Factura.add(hb_capturaproducto, 1, 7,2,1);
        
           Gp_Factura.add(Ta_FacturaItems, 0, 8,3,1);
         //**********Muestra los botones de comando y anvegacion(Print, save,Pdf,etc)
              
         VBox hb_subtotal=  new VBox(L_Subtotal,L_DescuentoFactura,L_Total);
         hb_subtotal.setSpacing(7);
         VBox hb_descuento=  new VBox(Tf_Subtotal,Tf_DescuentoFactura,Tf_Total);
         hb_descuento.setSpacing(3);
        
         HBox vb_totalesdetalles=new HBox(hb_subtotal,hb_descuento);
         vb_totalesdetalles.setSpacing(3);
         GridPane.setHalignment(H_Botones, HPos.RIGHT);
         H_Botones.getChildren().add(vb_totalesdetalles);
       GridPane.setValignment(H_Botones, VPos.BOTTOM);
             GridPane.setHalignment(H_Botones, HPos.CENTER);
         Gp_Factura.add(H_Botones, 1,9 ,2,1);
        
         //****************muestra la tabla de existencias producto*****************************
         Gp_Factura.add(parent_inv, 0, 10,3,1);
        // GridPane.setHalignment(parent_inv, HPos.CENTER);
       //***************pupups busqueda colocar ultima no se sobrepongan otros controles por encima********************
        GridPane.setValignment(sp_cliente, VPos.TOP);
         Gp_Factura.add(sp_cliente, 1, 2,2,9);
         GridPane.setValignment(sp_producto, VPos.TOP);
         Gp_Factura.add(sp_producto, 1,8 ,2,4);
        
         //Ancho de tabla y ancho de porcentaje de columnas
          
          Ta_FacturaItems.setMaxHeight(200);
                
          Gp_FacturaItems.autosize();
          GridPane.setValignment(Gp_VlrRecibido, VPos.BOTTOM);
           Gp_VlrRecibido.setMinSize(600, 180);
             Gp_VlrRecibido.setMaxSize(600, 180);
            
            if(AbacoApp.s_factura==null)
            {
             disabledAllTextField();
            } 
            
            generarVlrCambio();
          
            stack.setAlignment(Pos.TOP_CENTER);
           Gp_Factura.setAlignment(Pos.TOP_LEFT);
         //  stack.setMinSize(1000, 635);
            Gp_Factura.setMinSize(1000, 610);
          // tp_generic.setMinSize(1000, 635);
            eventos_controles();
           
            KeyCombination kn = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
           Runnable task_new = () ->nuevaFactura();
           KeyCombination kg = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
           Runnable task_save = () ->guardarFactura();
           KeyCombination kp = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
           Runnable task_print = () ->{
          
                addGp_VlrRecibido();
         
           };
            KeyCombination kd = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
            Runnable task_devolver = () ->addGp_VlrRecibido();
            KeyCombination ks = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
            Runnable task_salir = () ->removeGp_VlrRecibido();
        
              
           tp_generic.setContent(Gp_Factura); 
           stack.getChildren().addAll(tp_generic,Gp_Message,Gp_VlrRecibido);
           switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(Gp_Factura);
                        AeroFX.styleAllAsGroupBox(Gp_VlrRecibido);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(Gp_Factura);
                     AquaFx.setGroupBox(Gp_VlrRecibido);
                     break;              
         }
           if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()!=0)
        {
          B_Buscar.setStyle("-fx-background-color:#007fff");
          B_Previous.setStyle("-fx-background-color:#007fff");
          B_Save.setStyle("-fx-background-color:#007fff");
          B_New.setStyle("-fx-background-color:#007fff");
          B_Next.setStyle("-fx-background-color:#007fff");
          B_Ok.setStyle("-fx-background-color:#007fff");
          B_Pdf.setStyle("-fx-background-color:#007fff");
          B_Print.setStyle("-fx-background-color:#007fff");
          bu_email.setStyle("-fx-background-color:#007fff");
          T_NoFactura.setFill(Color.valueOf("#007fff"));
                 
        }
           else
           {
               T_NoFactura.setFill(Color.WHITE);
           }
            AbacoApp.getScene().getAccelerators().clear();
             AbacoApp.getScene().getAccelerators().put(kn, task_new);
             AbacoApp.getScene().getAccelerators().put(kg, task_save);
             AbacoApp.getScene().getAccelerators().put(kp, task_print);
             AbacoApp.getScene().getAccelerators().put(kd, task_devolver);
             AbacoApp.getScene().getAccelerators().put(ks, task_salir);
          
          
         
            
              codigo.requestFocus();
            if(AbacoApp.nombresolucion.equals("Admin. Factura"))
             {   
              crearoeditar();
             }
            else
            {
            lastfactura();
            }
             System.out.println("Editando factura");
      return stack;
    }
  
 public void nuevaFactura() {

    try{
     removeGp_VlrRecibido();
    numeroregistrosanteriores=0;
     enabledAllTextField();
     AbacoApp.s_factura=null;
     AbacoApp.s_factura = new Factura();
    Ol_FacturaItems.clear();
    sb_cliente.setText("");
    sp_cliente.hide();
    Tf_CcCliente.setText("");
    Tf_DireccionCliente.setText("");
    celular.setText("");
    Rb_Credito.setSelected(false);
    Rb_Contado.setSelected(false);
    sb_producto.setText("");
    Tf_Descuento.setText("0");
    //Tf_Cantidad.setText("0");
    Tf_Subtotal.setText("0");
    Tf_Iva.setText("0");
    Tf_Total.setText("0000000");
     getnumerodigital();
    AbacoApp.s_genpersonas=null;
    AbacoApp.s_genpersonas=new Personas();
    AbacoApp.s_factura=facturaControllerClient.nuevaFactura(AbacoApp.s_factura,AbacoApp.consecutivoFactura);
    T_NoFactura.setText("FACTURA N° "+AbacoApp.s_factura.getNofacturacerosizquierda());
    SimpleDateFormat  df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha =df.format(AbacoApp.s_factura.getFacturaDate()); 
    Tf_Fecha.setText(fecha);
    Tf_ValorRecibido.setText("0");
    sb_producto.requestFocus();
    Tf_Subtotal.setEditable(false);
    Tf_Iva.setEditable(false);
    Tf_Total.setEditable(false);
    Tf_Fecha.setEditable(false);
    Tf_CcCliente.requestFocus();
   
    L_Message.setText("Registro nuevo"); 
    Task_Message = () -> {
         try {
             SetMessage();
         } catch (InterruptedException ex) {
             
         }
     };
     backgroundThread = new Thread(Task_Message);
     backgroundThread.setDaemon(true);
     backgroundThread.start();
  }catch(Exception e)
  {
      e.printStackTrace();
     L_Message.setText("Error en el proceso"); 
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

InicialVlrCambio();
codigo.requestFocus();
 }
    private void guardarFactura()
    {
        if(!B_Save.isDisabled())
        {
      
                       
                   AbacoApp.s_factura.setEmpleado(AbacoApp.s_usuarios.getGenPersonas());
                       
                   verificarcliente();
                   AbacoApp.s_factura.setCredito(Rb_Credito.isSelected());
                   AbacoApp.s_factura.setShowiva(Ch_MostrarIva.isSelected());
                   AbacoApp.s_factura.setDescuento(BigDecimal.valueOf(Double.parseDouble(Tf_DescuentoFactura.getText().trim().replaceAll(",","\\."))));
                   AbacoApp.s_factura.calculateTotals();
               try{
               AbacoApp.s_factura=facturaControllerClient.guardarFactura(AbacoApp.s_factura);
                 SetDatosFactura();
                
               L_Message.setText("Registro almacenado"); 
               
    Task_Message = () -> {
         try {
             if(AbacoApp.nombresolucion.equals("AdminFacturas"))
            { 
             AdminFacturas.getRecords();
            }
             SetMessage();
         } catch (InterruptedException ex) {
             
         }         catch (ParseException ex) {
                       Logger.getLogger(FFactura.class.getName()).log(Level.SEVERE, null, ex);
                   }
     };
     backgroundThread = new Thread(Task_Message);
     backgroundThread.setDaemon(true);
     backgroundThread.start();
  }catch(Exception e)
  {
     L_Message.setText("Error en el proceso"); 
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
               
               Tf_Subtotal.setText(AbacoApp.s_factura.getNetAmount().toString());
               Tf_Iva.setText(AbacoApp.s_factura.getIva().toString());
               Tf_Total.setText(String.valueOf(AbacoApp.s_factura.getTotalAmount().intValueExact()));
              
        
  }
        
    }      
    @Override public void start(Stage primaryStage) throws Exception {
        
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
Task<Void> task = new Task<Void>() {
         @Override protected Void call() throws Exception {
            
    //stage finproduct
    
             // Return null at the end of a Task of type Void
             return null;
         }
     };
public void addProduct()
{ 
   
    vdescuento=BigDecimal.ZERO;
   try
   {

    if(AbacoApp.s_factura.getFacturaItems().size()<=0)
    {
         guardarFactura();
      
    }
       try{
        vdescuento=Utils.round(AbacoApp.s_producto.getPrecio().multiply((new BigDecimal(Double.valueOf(Tf_Descuento.getText().trim())*0.01))));
        vdescuento=Utils.round(vdescuento.multiply(new BigDecimal(Tf_Cantidad.getText().trim().replaceAll(",", "\\.").trim())));
        System.out.println("descuento->"+vdescuento.toString());
  
       AbacoApp.s_factura.addProduct(AbacoApp.s_producto,Float.valueOf(Tf_Cantidad.getText().trim().replaceAll(",", "\\.").trim()), BigDecimal.ZERO,vdescuento);
       verificarcliente();
       AbacoApp.s_factura=facturaControllerClient.addproducto(AbacoApp.s_factura,AbacoApp.s_producto);
      if(AbacoApp.s_factura==null)
      {
        //  AbacoApp.s_factura.removeProduct(AbacoApp.s_producto);
         L_Message.setText("Error al procesar Venta y Salida de Prodcuto");  
      }
      else
      {
      Existencias2.Tf_Producto.setText("");
      Existencias2.Tf_Producto.setText(AbacoApp.s_producto.getCodigobarras());
      AbacoApp.s_producto=null;
      AbacoApp.s_producto=new Producto();
      AbacoApp.s_kardex=null;
      AbacoApp.s_kardex=new Kardex();
       L_Message.setText("Item de factura agregado"); 
      }
       Task_Message = () -> {
         try {
             
             SetMessage();
           
         } catch (InterruptedException ex) {
             ex.printStackTrace();
         }
     };
     backgroundThread = new Thread(Task_Message);
     backgroundThread.setDaemon(true);
     backgroundThread.start();
  }catch(Exception e)
  {
      e.printStackTrace();
     L_Message.setText("Error en el proceso"); 
    Task_Message = () -> {
         try {
             SetMessage();
         } catch (InterruptedException ex) {
             
         }
     };
       backgroundThread = new Thread(Task_Message);
       backgroundThread.setDaemon(true);
       backgroundThread.start();
  }
    SetDatosFactura();
    Tf_Cantidad.setText("1");
    Tf_Descuento.setText("0");
    sb_producto.setText("");
    sb_producto.setText("");
                 AbacoApp.s_kardex=null;
                 sp_producto.hide();
                // AbacoApp.s_kardex=null;
                 
                 Tf_Descuento.setText("0");
                 Tf_Cantidad.setText("1");
                 codigo.setText("");
                 codigo.requestFocus();
  
   }
   catch(Exception e)
            {
           
            }
}
public void deleteItem()
{
    try{
    FacturaItem fi=(FacturaItem)Ta_FacturaItems.getFocusModel().getFocusedItem();
    AbacoApp.s_producto=fi.getProduct();
    AbacoApp.s_factura.removeProduct(fi.getProduct());
    facturaControllerClient.addproducto(AbacoApp.s_factura,AbacoApp.s_producto);
     L_Message.setText("Item eliminado"); 
    Task_Message = () -> {
         try {
             AbacoApp.li_kardex=AbacoApp.kardexControllerClient.getRecords();
             SetMessage();
         } catch (InterruptedException ex) {
             
         }
     };
     backgroundThread = new Thread(Task_Message);
     backgroundThread.setDaemon(true);
     backgroundThread.start();
  }catch(Exception e)
  {
     L_Message.setText("Error en el proceso"); 
    Task_Message = () -> {
         try {
             SetMessage();
         } catch (InterruptedException ex) {
             
         }
     };
     backgroundThread = new Thread(Task_Message);
    backgroundThread.start();
  }
   SetDatosFactura();
    
   
}
public void modifyItem(String quantity)
{
    try{
    FacturaItem fi=(FacturaItem)Ta_FacturaItems.getFocusModel().getFocusedItem();
     AbacoApp.s_producto=fi.getProduct();
   
   
    AbacoApp.s_factura.modifyItem(fi.getProduct(),Float.valueOf(quantity.trim()));
    facturaControllerClient.addproducto(AbacoApp.s_factura,AbacoApp.s_producto);
    AbacoApp.li_kardex=AbacoApp.kardexControllerClient.getRecords();
    L_Message.setText("Item modificado"); 
    Task_Message = () -> {
         try {
             SetMessage();
         } catch (InterruptedException ex) {
             
         }
     };
     backgroundThread = new Thread(Task_Message);
     backgroundThread.setDaemon(true);
     backgroundThread.start();
    
 
  }catch(Exception e)
  {
      e.printStackTrace();
     L_Message.setText("Error en el proceso"); 
    Task_Message = () -> {
         try {
             SetMessage();
         } catch (InterruptedException ex) {
             
         }
     };
     backgroundThread = new Thread(Task_Message);
     backgroundThread.start();
  }
    SetDatosFactura();
}
public void changeColumns(){
              Tc_NoItem = new TableColumn();
              Tc_NoItem.setText("No Item");
              Tc_NoItem.setCellValueFactory(new PropertyValueFactory("position"));
          
              Tc_Producto = new TableColumn();
              Tc_Producto.setText("Producto");
              
              Tc_Producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getProduct().getNombre());
            }
            });
          
             Tc_Cantidad = new TableColumn();
             Tc_Cantidad.setText("Cantidad");
             Tc_Cantidad.setCellValueFactory(new PropertyValueFactory("quantity"));
             Tc_Cantidad.setCellFactory(TextFieldTableCell.forTableColumn(sc));      
            /* Tc_Cantidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(String.valueOf(facturaitem.getValue().getQuantity()).replaceAll("\\.", ","));          
            }
            });*/
             Tc_ValorU = new TableColumn();
             Tc_ValorU.setText("Valor/U");
             Tc_ValorU.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
               if(facturaitem.getValue().getDescuento().floatValue()>0.0)
               {  
                   System.out.println("Cantidad->"+facturaitem.getValue().getQuantity()+ " descuento->"+facturaitem.getValue().getDescuento());
                   float des=facturaitem.getValue().getDescuento().floatValue()/facturaitem.getValue().getQuantity();
                    System.out.println("valor descuento unidad->"+des);
                  
                   return new SimpleStringProperty(Utils.round(facturaitem.getValue().getProduct().getPrecio().subtract(new BigDecimal(des))).toBigInteger().toString());
               }
               else
               {
                     return new SimpleStringProperty(facturaitem.getValue().getProduct().getPrecio().toBigInteger().toString());
                     
               }
            }
            });
          
             Tc_ValorT = new TableColumn();
             Tc_ValorT.setText("Valor Total");
             Tc_ValorT.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getValor_total().toBigInteger().toString());
            }
            });;
          
             Ta_FacturaItems.getColumns().clear();
             Ta_FacturaItems.setEditable(true);
             Ta_FacturaItems.getColumns().addAll(Tc_NoItem,Tc_Producto,Tc_Cantidad,Tc_ValorU,Tc_ValorT);
             //Ancho de tabla y ancho de porcentaje de columnas
             Tc_NoItem.setMinWidth(175);
             Tc_Producto.setMinWidth(250);
             Tc_Cantidad.setMinWidth(150);
             Tc_ValorU.setMinWidth(150);
             Tc_ValorT.setMinWidth(150);
             Ta_FacturaItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
             Ta_FacturaItems.setMinHeight(221);
              Ta_FacturaItems.setMinWidth(980);
            
}
public void disabledAllTextField()
{
    Ta_FacturaItems.setDisable(true);
     for(Object n:Gp_Factura.getChildren().toArray())
    {
        if(n.getClass()==GridPane.class)
        {
            GridPane gp_gen=(GridPane)n;
           for(Object nh:gp_gen.getChildren().toArray())
           {
               if(nh.getClass()==VBox.class)
           {
              VBox v_gen=(VBox)nh;
             for(Object nhv:v_gen.getChildren().toArray())
           {
                if(nhv.getClass()==RadioButton.class)
           {
                        RadioButton rb_gen=(RadioButton)nhv;
                      rb_gen.setDisable(true);
           }
           }  
           }
               if(nh.getClass()==TextField.class)
           {
           TextField tf_gen=(TextField)nh;
           tf_gen.setDisable(true);
           }
             
           
        }  
        }
    }
    for(Object n:Gp_Totales.getChildren().toArray())
    {
           if(n.getClass()==TextField.class)
           {
            TextField tf_gen=(TextField)n;
            tf_gen.setDisable(true);
           }
    }
}        
public void enabledAllTextField()
{
    Ta_FacturaItems.setDisable(false);
     for(Object n:Gp_Factura.getChildren().toArray())
    {
        if(n.getClass()==GridPane.class)
        {
            GridPane gp_gen=(GridPane)n;
           for(Object nh:gp_gen.getChildren().toArray())
           {
               if(nh.getClass()==VBox.class)
           {
              VBox v_gen=(VBox)nh;
             for(Object nhv:v_gen.getChildren().toArray())
           {
                if(nhv.getClass()==RadioButton.class)
           {
                        RadioButton rb_gen=(RadioButton)nhv;
                      rb_gen.setDisable(false);
           }
           }  
           }
               if(nh.getClass()==TextField.class)
           {
           TextField tf_gen=(TextField)nh;
           tf_gen.setDisable(false);
           }
             
           
        }  
        }
    }
    for(Object n:Gp_Totales.getChildren().toArray())
    {
           if(n.getClass()==TextField.class)
           {
            TextField tf_gen=(TextField)n;
            tf_gen.setDisable(false);
           }
    }
}        
 public void findByCodigo()
 {
      List<Producto> li_producto=AbacoApp.li_producto.stream().filter(line ->line.getCodigobarras().trim().toUpperCase().equals(codigo.getText().trim()) )	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());
  
    if(li_producto.size()>0)
    { 
        
      AbacoApp.s_producto= li_producto.get(0);
      sb_producto.setText(AbacoApp.s_producto.getNombre());
      sp_producto.hide();
      Tf_Cantidad.setText("1");
      Tf_Cantidad.requestFocus();
      addProduct();
    }
                     
 }
 
  public void findByProducto()
 {
          if(AbacoApp.s_producto!=null)
          {
           if(AbacoApp.s_producto.getId()!=null)
           {  
              sb_producto.setText(AbacoApp.s_producto.getNombre());
              codigo.setText(AbacoApp.s_producto.getCodigobarras());
              sp_producto.hide();
              Tf_Cantidad.requestFocus();
           }
          }
 }
 
 public void generarVlrCambio()
 {
   Rectangle dragger = new Rectangle(330,55);
        dragger.setArcHeight(20);
        dragger.setArcWidth(20);
   
         gradient2 = new LinearGradient(0, 0, 0, 0.5,  true, CycleMethod.REFLECT, new Stop[] {
        new Stop(0, Color.BLACK),
        new Stop(0.1, Color.BLACK),
        new Stop(1, Color.BLACK)
        });
        dragger.setFill(gradient2);   
        Gr_ValorCambio = new Group();
   
        Nd_ValorCambio = new NumeroDigital(Color.LIGHTGREEN, Color.rgb(50, 50, 50),"000");
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));
        // add background and clock to sample
         Gr_ValorCambio.getChildren().addAll(dragger, Nd_ValorCambio);
         GridPane.setRowSpan(Gp_VlrRecibido,2); 
         GridPane.setColumnSpan(Gp_VlrRecibido,5); 
         GridPane.setHalignment(Gp_VlrRecibido, HPos.CENTER);
         GridPane.setHalignment(hbox, HPos.CENTER);
         GridPane.setValignment(Gp_VlrRecibido, VPos.CENTER);
         Gp_VlrRecibido.setAlignment(Pos.CENTER);
         
         Gp_VlrRecibido.setVgap(5);
         Gp_VlrRecibido.setHgap(5);
         Gp_VlrRecibido.add(L_ValorRecibido, 0, 0);
         Gp_VlrRecibido.add(Tf_ValorRecibido, 1, 0);
         Gp_VlrRecibido.add(L_ValorCambio, 0, 1);
         Gp_VlrRecibido.add(Gr_ValorCambio, 1, 1);
         Gp_VlrRecibido.add(hbox, 0, 3,2,1);
         hbox.setAlignment(Pos.CENTER);
        // Gp_VlrRecibido.getStyleClass().add("solucion-gridpane-background");
      
 }
 public void addGp_VlrRecibido()
 {
     
       Gp_VlrRecibido.setVisible(true);
       Tf_ValorRecibido.requestFocus();
   
       
 }
 public void removeGp_VlrRecibido()
 {
     Gp_VlrRecibido.setVisible(false);
     facturaControllerClient.guardarFactura(AbacoApp.s_factura);
 }
 
 public void InicialVlrCambio()
 {
     
        Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
        Nd_ValorCambio=null;
        Nd_ValorCambio = new NumeroDigital(Color.LIGHTGREEN, Color.rgb(50, 50, 50),"000");
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));
        
        Gr_ValorCambio.getChildren().add(Nd_ValorCambio);
        B_Print.requestFocus();
        //cronometroRemoveGp_VlrRecibido();
 } 
 public void showVlrCambio()
 {
       if(Tf_ValorRecibido.getText()==null)
       {
           Tf_ValorRecibido.setText("0");
       }
       else
       {
           if(Tf_ValorRecibido.getText().trim().length()==0)
           {
               Tf_ValorRecibido.setText("0");
           }
               
       }
               
        AbacoApp.s_factura.setEfectivo(BigDecimal.valueOf(Double.parseDouble(Tf_ValorRecibido.getText().trim())));
        Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
        Nd_ValorCambio=null;
        Nd_ValorCambio = new NumeroDigital(Color.LIGHTGREEN, Color.rgb(50, 50, 50),String.valueOf(AbacoApp.s_factura.getDevolver().longValue()));
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));
       Gr_ValorCambio.getChildren().add(Nd_ValorCambio);
      
        //cronometroRemoveGp_VlrRecibido();
 } 
 public void printFactura(int opc) throws IOException, PrintException
 {
     ImpresoraTermica ip=null;
     ip=new ImpresoraTermica();
     ip.abrircajonmodedero();
     ip.imprimir();
    
 
     
 }
 
 private void cronometroRemoveGp_VlrRecibido() {
        
        FadeTransition ft = new FadeTransition(Duration.millis(5000),Gp_VlrRecibido);
        //ft.setFromValue(0.0);
       // ft.setToValue(1);
      
        ft.play();
       
        //success.setOpacity(0);
      
       ft.setOnFinished(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
               removeGp_VlrRecibido();
               
            }
        });
        
           
       
    }
 private void animateMessage() {
         success.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);
      
        ft.play();
       
        //success.setOpacity(0); 
        
      
       ft.setOnFinished(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
               H_Botones.getChildren().remove(success);
                
            }
        });
        
           
       
    }
 
 private void SetDatosFactura()
 { 
     T_NoFactura.setText("FACTURA N° "+AbacoApp.s_factura.getNofacturacerosizquierda());
      Tf_Fecha.setText(UtilDate.formatodiamesyear(AbacoApp.s_factura.getFacturaDate()));
        if(AbacoApp.s_factura.getCredito()!=null)
      
     {
        if(AbacoApp.s_factura.getCredito().booleanValue())
        {
         Rb_Credito.setSelected( true);
        }
        else
        {
            Rb_Contado.setSelected( true); 
        }
     }
      else
      {
         Rb_Contado.setSelected( true);  
      }
     if(AbacoApp.s_factura.getCustomer()!=null)
     {
      sb_cliente.setText(AbacoApp.s_factura.getCustomer().getNo_ident()+" "+AbacoApp.s_factura.getCustomer().getNombre()); 
      Tf_CcCliente.setText(AbacoApp.s_factura.getCustomer().getNo_ident()); 
      Tf_CcCliente.setDisable(false);
      celular.setText(AbacoApp.s_factura.getCustomer().getCelular());
      Tf_DireccionCliente.setText(AbacoApp.s_factura.getCustomer().getDir1());
      email.setText(AbacoApp.s_factura.getCustomer().getEmail());
      celular.setText(AbacoApp.s_factura.getCustomer().getCelular());
      
     
     
     
     }
     else
     {
      sb_cliente.setText(""); 
      Tf_CcCliente.setText(""); 
      Tf_TelefonoCliente.setText("");
      Tf_DireccionCliente.setText("");
      email.setText("");
      celular.setText("");
    
     
     }
     
   
     O_ListFacturaItems=AbacoApp.s_factura.getFacturaItems().toArray();
    Ol_FacturaItems.clear();
    Ol_FacturaItems.addAll(O_ListFacturaItems);
    Ta_FacturaItems.setItems(Ol_FacturaItems);
    Tf_Subtotal.setText(AbacoApp.s_factura.getNetAmount().toBigInteger().toString());
    Tf_Iva.setText((AbacoApp.s_factura.getIva().toString().replaceAll("\\.", ",")));
    Tf_Total.setText(String.valueOf(Utils.round(AbacoApp.s_factura.getTotalAmount()).toBigInteger()));
    if(AbacoApp.s_factura.getTotalAmount().floatValue()<=0.0)
    {
       Tf_Total.setText("000000000");  
    }
    Tf_DescuentoFactura.setText(AbacoApp.s_factura.getDescuento().toBigInteger().toString().replaceAll("\\.", ","));
    getnumerodigital();
    AbacoApp.s_genpersonas=AbacoApp.s_factura.getCustomer();
    sp_cliente.hide();
    
    
 }
 
 
 private void eventos_controles()
 {
   
      //acciones de botones y cuadros de texto
          B_Next.setOnAction(e-> {
              numeroregistrosanteriores--;
           AbacoApp.s_factura=facturaControllerClient.next(AbacoApp.s_factura,1);
             SetDatosFactura();
                editarfactura=false;
           /*Optional<Pair<String, String>> result = dialog.showAndWait();
           password.setText("");
           result.ifPresent(usernamePassword -> {
           System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
          if(usernamePassword.getValue().equals("abaco2019") && usernamePassword.getKey().equals("admin"))
         {   editarfactura=true;
             if(editarfactura)
             {   
                   AbacoApp.s_factura=facturaControllerClient.next(AbacoApp.s_factura,1);
                   SetDatosFactura();
                editarfactura=false;
             }
         }
                  
          });*/
            });
           //*************************
          B_New.setOnAction(e->{nuevaFactura();});
          B_Save.setOnAction(e->{
                guardarFactura();
               
                Tf_ValorRecibido.requestFocus();
            });
    B_Print.setOnAction(e->{try {printFactura(0);
                } catch (IOException ex) {
                    Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PrintException ex) {
                   Logger.getLogger(FFactura.class.getName()).log(Level.SEVERE, null, ex);
               }
               
            });
   
     B_Pdf.setOnAction(e->{
                
                    try {
                        impresionFactura.fprintpdf(1);
                    } catch (Exception ex) {
                        Logger.getLogger(FFactura.class.getName()).log(Level.SEVERE, null, ex);
                    }
               
               
            });
     bu_email.setOnAction(e->{
                
                    try {
                        impresionFactura.fprintpdf(0);
                        enviarEmail(AbacoApp.s_factura.getCustomer().getEmail());
                    } catch (Exception ex) {
                        Logger.getLogger(FFactura.class.getName()).log(Level.SEVERE, null, ex);
                    }
               
               
                          });
     Tf_CcCliente.setOnAction(e->{
              buscarcliente();
     });
     Tf_CcCliente.focusedProperty().addListener(new ChangeListener<Boolean>()
     {
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
       
    }
});
        Rb_Credito.setOnKeyReleased(e->{
            if(e.getCode().equals(e.getCode().ENTER))
            {
               cajero.requestFocus();
            }
     });
        cajero.setOnKeyReleased(e->{
            if(e.getCode().equals(e.getCode().ENTER))
            {
               celular.requestFocus();
            }
     }); 
        celular.setOnAction(e->{
            email.requestFocus();
        });
        email.setOnAction(e->{
          codigo.requestFocus();
        });
       
        sb_producto.setOnAction(e->{
             findByProducto();
                 
        });
        
      //  codigo.textProperty().addListener((observable, oldValue, newValue) -> {
                             
       //  });
         codigo.setOnAction(e->{
            
                   findByCodigo();
             
         });
   
    Tf_Cantidad.setOnAction(e->{
       
                if(!Tf_Cantidad.getText().equals("")&& !Tf_Cantidad.getText().equals("0"))      
                {
                 addProduct();
                 
                }
        
           
           
            
        });
    
   
    Ch_MostrarIva.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
               AbacoApp.s_factura.setShowiva(Ch_MostrarIva.isSelected());
               
            }});
    
          
            Ta_FacturaItems.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
             if (keyEvent.getCode() == KeyCode.DELETE) {
                 deleteItem();
                }  
            
            }
        });
          
        Tf_ValorRecibido.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            showVlrCambio();
           
        });   
      Tf_ValorRecibido.setOnAction(e->{;
                
               B_Ok.requestFocus();
            
        });
    
           Tf_DescuentoFactura.setOnAction(e->{
                   //     Tf_DescuentoFactura.setText(((BigDecimal.valueOf(Double.valueOf(Tf_DescuentoFactura.getText().trim())).divide(BigDecimal.valueOf(100))).multiply(findProduct.getKardex().getProducto().getPrecio())).toString());
                        guardarFactura();
            
               
            
        });
           email.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        if (!newValue) { 
         if(email.getText()!=null)
         {
            if(!email.getText().matches("[-\\w\\.]+@\\w+\\.\\w+")){
                //when it not matches the pattern (1.0 - 6.0)
                //set the textField empty
                email.setText("");
            }
         }
        }

    });
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
 public void crearoeditar()
{
  if(AbacoApp.s_factura!=null)
  {
      if(AbacoApp.s_factura.getId()!=null)
      {
          SetDatosFactura();
          System.out.println("Editando factura");
      }
      else
      {
          nuevaFactura();
      }
  }
  else
  {
      nuevaFactura();
  }
   
}
    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
 private void runthreadmessage(String message)
 {
    L_Message.setText(message); 
    Task_Message = () -> {
         try {
             SetMessage();
         } catch (InterruptedException ex) {
             
         }
     };
     backgroundThread = new Thread(Task_Message);
     backgroundThread.setDaemon(true);
     backgroundThread.start();  
 }
 private void verificarcliente()
 {
    if(AbacoApp.s_genpersonas!=null)
    {
        if(AbacoApp.s_genpersonas.getId()!=null)
    {
       
    AbacoApp.s_factura.setCustomer(AbacoApp.s_genpersonas);
    }
    }
 }
 private void agregarpersona()
 {
     if(AbacoApp.s_genpersonas.getId()==null)
     {
         AbacoApp.s_genpersonas=AbacoApp.s_factura.getCustomer();
         AbacoApp.li_genpersonas.add(AbacoApp.s_genpersonas);
     }
 }
    public static void main(String[] args) {
        launch(args);
    }
    
    private void buscarcliente()
    {
                genPersonasControllerClient=null;
                genPersonasControllerClient=new GenPersonasControllerClient();
                cliente=genPersonasControllerClient.findbyident(Tf_CcCliente.getText());
                 List<Personas> li_genPersonas=AbacoApp.li_genpersonas.stream().filter(line ->line.getNo_ident().toUpperCase().contains(Tf_CcCliente.getText().trim()) )	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList()); 
   
                if(cliente!=null)
                {
                  Tf_NombreCliente.setText(cliente.getNombre());
                  Tf_TelefonoCliente.setText(cliente.getCelular());
                  Tf_DireccionCliente.setText(cliente.getDir1());
                  celular.setText(cliente.getCelular());
                  email.setText(cliente.getEmail());
                  Tf_NombreCliente.requestFocus();
                }
                else
                {
                  Tf_NombreCliente.setText("");
                  Tf_TelefonoCliente.setText("");
                  Tf_DireccionCliente.setText("");
                  celular.setText("");
                  email.setText("");
                
                }
           
            
               
    }
private void show()  
  {
      String appClass;
      Class clz ;
      Object app ;
      Parent parent;
      appClass="abaco.soluciones.informacionadministrativa.FPersonas";
      clz=null;
      try{
      clz = Class.forName(appClass);
      app = clz.newInstance();
  
      parent=null;
      parent = (Parent) clz.getMethod("createContent").invoke(app);
      scene=null;
       scene=new Scene(parent, Color.TRANSPARENT);
     //  AquaFx.styleStage(stage, StageStyle.UNIFIED);
        if (stage.isShowing())
       {
           stage.close();
       }      
        
        
//set scene to stage
                stage.sizeToScene();
    
                stage.centerOnScreen();
                stage.setScene(scene);
                 
            
                stage.show();
      }catch(Exception e)
      {
         
      }
  }    

private void show2()  
  {
      
     
      appClass2="abaco.soluciones.procesosadministrativos.IngresoDirectoInventario";
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
 
                stage2.sizeToScene();
              
                stage2.centerOnScreen();
                stage2.setScene(scene2);
                 
              
                stage2.show();
      }catch(Exception e)
      {
         e.printStackTrace();
      }
  }  
public void lastfactura()
{
    try
    {
     AbacoApp.s_factura=facturaControllerClient.getlastfactura();
       SetDatosFactura();
    }
    catch(Exception e)
    {
     nuevaFactura();
    }
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
                return new PasswordAuthentication(AbacoApp.s_organizacion.getEmail(), AbacoApp.s_organizacion.getPasswordemail());
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
        msg.setFrom();
        msg.setRecipients(Message.RecipientType.TO,toemail);
        msg.setSubject(AbacoApp.s_organizacion.getNombre()+" Factura N° "+AbacoApp.s_factura.getNofacturacerosizquierda());
        msg.setFrom(AbacoApp.s_organizacion.getEmail());
        msg.setSentDate(new Date());
       
       Multipart mailBody = new MimeMultipart();
       BodyPart texto = new MimeBodyPart();
       texto.setText("Envío de Factura N° "+AbacoApp.s_factura.getNofacturacerosizquierda()+ "Por: " + AbacoApp.s_organizacion.getNombre()+" Nit: "+AbacoApp.s_organizacion.getNit()+ " Tel: "+AbacoApp.s_organizacion.getTel()+ " Dir: "+AbacoApp.s_organizacion.getDir());
       mailBody.addBodyPart(texto);
       MimeBodyPart mimeAttach = new MimeBodyPart();
       String os = System.getProperty("os.name"); 
       FileDataSource fds = new FileDataSource("/home/adminlinux/abaco/factura.pdf");
       if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
      { 
        fds = new FileDataSource("/home/adminlinux/abaco/factura.pdf");
      }
     else
      {
      
    fds = new FileDataSource("\\home\\adminlinux\\abaco\\factura.pdf");
 
 }
 mimeAttach.setDataHandler(new DataHandler(fds));
 mimeAttach.setFileName(fds.getName()); 
 mailBody.addBodyPart(mimeAttach);
 mimeAttach=null;
 mimeAttach = new MimeBodyPart();
 fds=null;


  msg.setText("Felicitaciones por su compra");
     msg.setContent(mailBody); 
     
     Transport.send(msg);
    
     mailSession.getTransport().close();
     mailSession.getStore().close();
     msg.getSession().getTransport().close();
     mailSession=null;
    } catch (MessagingException mex) {
        System.out.println("send failed, exception: " + mex);
    }
         
               
            }  
           
 public void getnumerodigital()
 {
   
    Gr_NumeroDigital.getChildren().remove(numerodigital);
    numerodigital=null;
    if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()==0)
    {
     numerodigital = new NumeroDigital(Color.valueOf("#007fff"), Color.rgb(50, 50, 50),Tf_Total.getText()); 
    }
    else
    {
         numerodigital = new NumeroDigital(Color.valueOf("#007fff"), Color.TRANSPARENT,Tf_Total.getText()); 
    }
    numerodigital.setLayoutX(10);
    numerodigital.setLayoutY(5);
    numerodigital.getTransforms().add(new Scale(0.73f, 0.73f, 0, 0));
    Gr_NumeroDigital.getChildren().add(numerodigital);
 }
 
 private void getinitnumerodigital()
 {
     //Creamos el fondo para el led de 7 segmentos
       //create dragger with desired size
        Rectangle dragger = new Rectangle();
        dragger.setArcHeight(20);
        dragger.setArcWidth(20);
       //fill the dragger with some nice radial background
         gradient2 = new LinearGradient((AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()==0?0:1), 0, 0, 0.5,  true, CycleMethod.REFLECT, new Stop[] {
        new Stop(0, Color.BLACK),
        new Stop(0.1, Color.BLACK),
        new Stop(1, Color.BLACK)
        });
        dragger.setFill(gradient2);
      //creamos el led de 7 segmentgetos
 }
 
}
