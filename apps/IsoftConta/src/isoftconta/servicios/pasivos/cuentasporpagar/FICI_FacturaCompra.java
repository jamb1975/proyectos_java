package isoftconta.servicios.pasivos.cuentasporpagar;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import isoftconta.IsoftConta;

import isoftconta.digitos.NumeroDigital;

import isoftconta.servicios.EnumDocumentoSoporte;
import isoftconta.servicios.FacturaProveedoresController;
import isoftconta.servicios.KardexController;
import isoftconta.servicios.ProductoController;
import isoftconta.servicios.TercerosController;
import isoftconta.servicios.configuracion.contabilidad.libroscontables.FICI_LibroAuxiliar;
import isoftconta.util.StageGeneric;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import javafx.scene.layout.Region;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import modelo.FacturaItemProveedores;
import modelo.FacturaProveedores;
import modelo.Kardex;
import modelo.Producto;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import modelo.EntidadesStatic;
import modelo.Terceros;
public class FICI_FacturaCompra extends Application {
    
//Threads
     Runnable Task_Message;
     Thread backgroundThread;
//grupo
    Group Gr_NumeroDigital;
    Group Gr_ValorCambio;
    LinearGradient gradient2;
    GridPane Gp_Message;
    Region Re_Message;
    Label L_Message;
    //String Converter
    StringConverter<Object> sc;
        //buttons
         private Button bu_print;
         private Button B_New;
         private Button B_Save;
         private Button bu_agregarproducto;
         private Button B_Insertar;
         private Button bu_importardesdeexcel;
         private Button bu_savefile;
         private  TableView Ta_FacturaItems; 
      
        //GridPane row and columns
         private  GridPane Gp_Factura;
         private GridPane Gp_DatosEmpresa;
         private  GridPane Gp_Proveedor;
         private  GridPane Gp_Total;
         private  GridPane Gp_Producto;
         private  GridPane Gp_Inventario;
         private  GridPane Gp_FacturaItems;
         private  GridPane Gp_Totales;
         private GridPane Gp_VlrRecibido;
         private HBox Hb_Totales;
         private Text T_Total;
         //Etiquetas datos cliente
         private  Label L_NitProveedor;
         private  Label L_DireccionProveedor;
         private  Label L_TelefonoProveedor;
         private  Label L_FormaPago;
         private Label L_Fecha;
         private Label L_NoFactura;
         private Label L_Total;
         private Label L_Producto;
         private Label L_Cantidad;
         private Label L_Valor;
         private Label L_F6;
         private Label L_F7;
         private Label L_ValorRecibido;
         private Label L_ValorCambio;
         private Label L_Codigo;
         private Label L_PrecioCompra;
        //Campos de texto datos cliente 
         private  TextField Tf_NitProveedor;
         private  TextField Tf_DireccionProveedor;
         private  TextField Tf_TelefonoProveedor;
         private  DatePicker Tf_Fecha;
         private  TextField Tf_NoFactura;
         private  TextField Tf_Valor;
         private  TextField Tf_Cantidad;
         private  TextField Tf_Total;
         private  TextField Tf_ValorRecibido;
         private TextField Tf_Codigo;
         private TextField Tf_Precio;
         private TextField Tf_PrecioCompra;
        
         private Label la_buscar;
         private TextField buscar;
         private Label la_iva;
         private TextField tf_iva;
         private DatePicker  fechavencimiento;
         
        
        
         private TextField porcentajeganancia=new TextField("0");
         
        
         private RadioButton Rb_Contado;
         private RadioButton Rb_Credito;
        
        //Vbox
         private HBox V_box;
         private VBox V_Factura;
         private HBox H_Botones;
         private HBox H_Producto;
         private HBox H_BotonesInv;
       //Togglegroup
         private ToggleGroup toggleGroup;
        //creamos el numero digital
         private NumeroDigital numerodigital;
         private NumeroDigital Nd_ValorCambio;
        //Objetos que contiene los datos de la factura
         private  ObservableList  Ol_FacturaItems = FXCollections.observableArrayList();
         private Object[] O_ListFacturaItems;
         private  ObservableList  Ol_FacturaItemsI = FXCollections.observableArrayList();
         private Object[] O_ListFacturaItemsI;
        
         
         private TableColumn Tc_NoItem;
         private TableColumn Tc_Producto;
         private TableColumn Tc_Cantidad;
         private TableColumn Tc_ValorU;
         private TableColumn Tc_ValorT;
         //columnas tabla factura
         private File file;
         private FileChooser fileChooser = new FileChooser();
         private Stage stage;
         final ProgressIndicator p5 = new ProgressIndicator();
         final Timeline timeline = new Timeline();
         ProgressBar p3 = new ProgressBar();
         Region veil = new Region();
         ProgressIndicator p = new ProgressIndicator();
         Thread thimport;
         StackPane stack;
         String appClass;
         Class clz ;
         Object app ;
          String appClassKardex;
         Class clzKardex ;
         Object appKardex ;
          String appClassExistencias;
         Class clzExistencias ;
         Object appExistencias ;
         Parent findProductEmbbed ;
         private final StageGeneric stageProveedores=new StageGeneric("isoftconta.servicios.configuracion.contabilidad.FICI_Terceros","Proveedor",false);
         private final StageGeneric stageProducto=new StageGeneric("isoftconta.servicios.configuracion.contabilidad.FICI_Producto","Producto",false);
         private static SuggestionProvider<String> provider = SuggestionProvider.create(EntidadesStatic.possibleSuggestions);
         private static final TextField tf_nombretercero = new TextField();
         private static final TextField tf_producto = new TextField();
         Scene scene =null;
         static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
        }
          
            private Button bu_agregarproveedor;
           private ImageView imageView;
           HBox hbox_proveedor=new HBox();
           private String gp_observaciones="";
           private String gp_ubicacion="";
         public Parent createContent() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
          Gr_NumeroDigital=new Group();
          
          new AutoCompletionTextFieldBinding<>(tf_nombretercero, provider);
          tf_nombretercero.textProperty().addListener((observable, oldValue, newValue) -> {
           try {
                    TercerosController.load_asterceros(tf_nombretercero.getText());
                } catch (ParseException ex) {
                    Logger.getLogger(FICI_ReciboCajaMenor.class.getName()).log(Level.SEVERE, null, ex);
                }
                       
                     if(EntidadesStatic.li_terceros!=null)
                     {
                       if(EntidadesStatic.li_terceros.size()>0)
                     {
                         provider.clearSuggestions();
                         provider.addPossibleSuggestions(EntidadesStatic.possibleSuggestions);
                     }  
                     }
        });
         
       tf_nombretercero.focusedProperty().addListener(new ChangeListener<Boolean>()
      {
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    { 
        if (!newPropertyValue)
        {
         TercerosController.findtercerospornoident(tf_nombretercero.getText());
         setdatosproveedor();
        }
     }
    });
          FICI_LibroAuxiliar.refrescardatossegunorigen=EnumDocumentoSoporte.COMPROBANTECAUSACIONEGRESO14.ordinal();
             bu_agregarproveedor=new Button("",IsoftConta.glyph_nuevo_14);
             hbox_proveedor.getChildren().addAll(tf_nombretercero,bu_agregarproveedor);
             bu_agregarproveedor.setOnAction(e->{
                 showproveedor();
             });
         EntidadesStatic.es_producto=null;
         EntidadesStatic.es_producto=new Producto();
       
        
         stageProducto.getStage().setOnHidden(e->{
             if (e.getEventType() == e.WINDOW_HIDDEN ) {
               
           
            tf_producto.setText(EntidadesStatic.es_producto.getNombre());
            Tf_Codigo.setText(EntidadesStatic.es_producto.getCodigobarras());
            
         
             }
        });
       
     
      tf_producto.setPromptText("Digite código o Nombre para busqueda general");
     
      stack=new StackPane();
      //Objetos gridpane
        Gp_Factura=new GridPane();
        Gp_DatosEmpresa=new GridPane();
        Gp_Proveedor=new GridPane();
        Gp_Total=new GridPane();
        Gp_Producto=new GridPane();
        Gp_Inventario=new GridPane();
        Gp_FacturaItems=new GridPane();
        Gp_Totales=new GridPane();
        Gp_VlrRecibido=new GridPane();
        Gp_DatosEmpresa.autosize();
        Gp_Producto.autosize();
        //Task
     
        Thread th = new Thread(task);
        th.setDaemon(false);
        th.start();
       //campos de texto
       //datos cliente
      porcentajeganancia.setMaxWidth(70);
      Tf_NitProveedor=new TextField();
      Tf_DireccionProveedor=new TextField();
      Tf_TelefonoProveedor=new TextField();
      Tf_Fecha=new DatePicker(LocalDate.now());
      Tf_NoFactura=new TextField();
      Tf_NoFactura.setMinWidth(250);
      //datos factura
      tf_nombretercero.setText("");
      tf_nombretercero.setMinWidth(234);
      EntidadesStatic.es_terceros=null;
      EntidadesStatic.es_terceros=new Terceros();
      Tf_Valor=new TextField("0");
      Tf_Cantidad=new TextField("0");
      Tf_Cantidad.setMaxWidth(100);
      Tf_Codigo=new TextField();
      Tf_Codigo.setMinWidth(100);
      Tf_Precio=new TextField("0");
      tf_iva=new TextField("0");
      Tf_Total=new TextField("000000");
      getnumerodigital();
      Tf_Total.setMaxWidth(150);
      Tf_TelefonoProveedor=new TextField("");
      Tf_ValorRecibido=new TextField("0");
      Tf_ValorRecibido.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
      Tf_ValorRecibido.setMinHeight(30);
      Tf_PrecioCompra=new TextField("0");
      la_iva=new Label("% IVA:");
      tf_iva=new TextField();
      tf_iva.setMaxWidth(70);
      la_buscar=new Label("Buscar:");
      buscar=new TextField();
      buscar.setMaxWidth(100);
      buscar.textProperty().addListener((observable, oldValue, newValue) -> {
           buscarproducto();
});
      fechavencimiento=new DatePicker(LocalDate.now());
       //Inicializamos objetos de pesistencia
      //Cargamos los datos en la tabla Ta_facturaItems
             
             Ta_FacturaItems=new TableView();
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
             fileChooser.setTitle("Abrir Archivo de excel");
             fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS", "*.xls")
              //  new FileChooser.ExtensionFilter("XLSX", "*.xls")
            );
                       
            //botones
            bu_print=new Button("",IsoftConta.glyph_print_32);
            bu_importardesdeexcel=new Button("",IsoftConta.glyph_excel_32);
            B_New=new Button("", IsoftConta.glyph_nuevo_32);
            bu_agregarproducto=new Button("",IsoftConta.glyph_nuevo_14_3);
            bu_agregarproducto.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
                    show();
                
                    
                  
            }});
            
            B_Save=new Button("", IsoftConta.glyph_guardar_32);
            B_Insertar=new Button("",IsoftConta.glyph_nuevo_16_2);
          
       
     
        
      
         //acciones de botones y cuadros de texto
          B_Insertar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                  addProduct();
                  Tf_Codigo.requestFocus();
                
            }});
         B_Save.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    guardarFactura();
                } catch (ParseException ex)
                {
                    Logger.getLogger(FICI_FacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                     
            }});
              tf_producto.setOnAction(e->{
            findByProducto();
         });
         
        toggleGroup=new ToggleGroup();
        
       Rb_Contado=new RadioButton("Contado");
       Rb_Contado.setMinWidth(100);
       Rb_Contado.setToggleGroup(toggleGroup);
      // Rb_Contado.setStyle("-fx-text-fill: white;");
       Rb_Credito=new RadioButton("Credito");
       Rb_Credito.setMinWidth(100);
       Rb_Credito.setToggleGroup(toggleGroup);
      // Rb_Credito.setStyle("-fx-text-fill: white;");
      
      
       //etiquetas datos factura
       L_Fecha=new Label("Fecha:");
       L_NoFactura=new Label("No. Factura: ");
        L_NoFactura.setMinWidth(80);
       L_FormaPago=new Label("Forma de pago:   ");
       L_FormaPago.setMinWidth(108);
       L_NitProveedor=new Label("Nit: ");
       L_DireccionProveedor=new Label("Dirección:"); 
       L_TelefonoProveedor=new Label("Teléfono:"); 
       L_Producto=new Label("Producto: ");
       L_Producto.setMinWidth(100);
       L_Cantidad=new Label("Cantidad: ");
       L_Cantidad.setMinWidth(70);
       L_Codigo=new Label("Código Producto: ");
       L_Codigo.setMinWidth(110);
       L_Valor=new Label("Valor:");
       L_Total=new Label("Total: ");
       L_F6=new Label("F6 -----> Imprimir Factura");
       L_F7=new Label("F7 -----> Guardar y Nueva Factura");
       L_F6.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 10));
       L_F7.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 10));
       L_ValorRecibido=new Label("Vlr Recibido: ");
       L_ValorRecibido.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
       L_ValorCambio=new Label("Vlr Cambio: ");
       L_ValorCambio.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
       T_Total=new Text("TOTAL");
       T_Total.textAlignmentProperty().setValue(TextAlignment.CENTER);
       T_Total.setFont(Font.font("ARIAL", FontWeight.BOLD,30));
       T_Total.setFill(Color.ROYALBLUE);
       L_PrecioCompra=new Label("Valor/U Compra: ");
       L_PrecioCompra.setMinWidth(115);
   
       
       //Objeto Vbox coloca dentro de una caja objetos verticalmente
         V_box=new HBox();
         V_box.setSpacing(2);//espacio verticalmente entre objetos
         V_box.getChildren().addAll(Rb_Contado,Rb_Credito);
         H_Botones=new HBox();
         H_Botones.setSpacing(2);//espacio verticalmente entre objetos
         H_Botones.getChildren().addAll(B_New,B_Save,bu_importardesdeexcel);
         H_Producto=new HBox();
         H_Producto.setSpacing(2);//espacio verticalmente entre objetos
         GridPane.setValignment(tf_producto, VPos.BOTTOM);
         GridPane.setValignment(H_Producto, VPos.BOTTOM);
         tf_producto.setAlignment(Pos.CENTER);
         H_Producto.getChildren().addAll(tf_producto,bu_agregarproducto);
         H_BotonesInv=new HBox();
         H_BotonesInv.setSpacing(2);//espacio verticalmente entre objetos
         Gp_Proveedor.setVgap(5);//espacio verticalmente dentro de gridpane
         Gp_Producto.setVgap(3);
         Gp_Inventario.setVgap(5);
         GridPane.setFillWidth(L_FormaPago, Boolean.TRUE);
         GridPane.setColumnSpan(T_Total,5);
         GridPane.setRowSpan(T_Total,3);
         GridPane.setColumnSpan(Ta_FacturaItems,2);
         GridPane.setValignment(Gp_Proveedor, VPos.TOP);
         GridPane.setValignment(Gp_Producto, VPos.TOP);//alinea verticalmente
         GridPane.setValignment(Gp_Inventario, VPos.TOP);
         GridPane.setValignment(L_FormaPago, VPos.TOP);
         GridPane.setValignment(L_Codigo, VPos.TOP);
         GridPane.setValignment(L_PrecioCompra, VPos.TOP);
         GridPane.setValignment(T_Total, VPos.TOP);
         GridPane.setHalignment(T_Total, HPos.CENTER);
         GridPane.setMargin(Gr_NumeroDigital, new Insets(0, 100, 0, 100));
          //Creamos el fondo para el led de 7 segmentos
       //create dragger with desired size
        Rectangle dragger = new Rectangle(550,200);
        dragger.setArcHeight(20);
        dragger.setArcWidth(20);
       //fill the dragger with some nice radial background
         gradient2 = new LinearGradient(0, 0, 0, 0.5,  true, CycleMethod.REFLECT, new Stop[] {
        new Stop(0, Color.BLACK),
        new Stop(0.1, Color.BLACK),
        new Stop(1, Color.BLACK)
        });
         dragger.setFill(gradient2);
         GridPane.setValignment(numerodigital, VPos.CENTER);
         GridPane.setRowSpan(Gp_Total,2);
         GridPane.setHalignment(numerodigital, HPos.RIGHT);
         //Gp_Proveedor.setHgap(100);
         Gp_Producto.setHgap(2);
         Gp_Inventario.setHgap(2);
         Gp_Inventario.setAlignment(Pos.CENTER);
         GridPane.setMargin(Gp_Inventario, new Insets(0, 20, 0, 20));
         Gp_Factura.setHgap(2);
         Gp_Factura.setPadding(new Insets(10));
         Gp_Factura.setVgap(7);
         Gp_Proveedor.add(L_Fecha, 0, 0);
         Gp_Proveedor.add(Tf_Fecha, 1, 0);
         Gp_Proveedor.add(L_NoFactura, 0, 1);
         Gp_Proveedor.add(Tf_NoFactura, 1, 1);
         Gp_Proveedor.add(new Label("Nombre:"), 0, 2);
         Gp_Proveedor.add(hbox_proveedor, 1, 2);
         Gp_Proveedor.add(L_NitProveedor, 0, 3);
         Gp_Proveedor.add(Tf_NitProveedor, 1, 3);
         Gp_Proveedor.add(L_TelefonoProveedor, 0, 4);
         Gp_Proveedor.add(Tf_TelefonoProveedor,1, 4);
         Gp_Proveedor.add(L_DireccionProveedor, 0, 5);
         Gp_Proveedor.add(Tf_DireccionProveedor, 1, 5);
         Gp_Proveedor.add(L_FormaPago, 0, 6);
         Gp_Proveedor.add(V_box, 1, 6);
         Gp_Proveedor.add(T_Total,2, 0, 1,2);
         Gp_Proveedor.add(Gr_NumeroDigital,2, 1, 1,6);
         Gp_Inventario.add(la_buscar, 0, 0);
         Gp_Inventario.add(buscar, 0, 1);
         Gp_Inventario.add(la_iva, 1, 0);
         Gp_Inventario.add(tf_iva, 1, 1);
         Gp_Inventario.add(new Label("% Ganancia"), 2, 0);
         Gp_Inventario.add(porcentajeganancia, 2, 1);
         Gp_Inventario.add(L_Codigo, 3, 0);
         Gp_Inventario.add(Tf_Codigo, 3, 1);
         Gp_Inventario.add(L_Producto, 4, 0);
         Gp_Inventario.add(H_Producto, 4, 1);
         Gp_Inventario.add(L_Cantidad, 5, 0);
         Gp_Inventario.add(Tf_Cantidad, 5, 1);
         Gp_Inventario.add(L_PrecioCompra, 6, 0);
         Gp_Inventario.add(Tf_PrecioCompra, 6, 1);
         Gp_Inventario.add(L_Valor, 7, 0);
         Gp_Inventario.add(Tf_Valor, 7, 1);
         Gp_Inventario.add(B_Insertar, 8, 1,1,1);
         Gp_Factura.add(Gp_Proveedor, 0, 0);
         Gp_Factura.add(Gp_Inventario, 0, 2,1,1);
         GridPane.setValignment(H_BotonesInv, VPos.BOTTOM);
         H_BotonesInv.setAlignment(Pos.CENTER);
         Gp_Factura.setAlignment(Pos.TOP_CENTER);
         Gp_Factura.autosize();
         Gp_FacturaItems.add(Ta_FacturaItems, 0, 0,5,1);
         Gp_Factura.add(Gp_FacturaItems, 0, 3,5,1);
         Hb_Totales=new HBox(L_Total,Tf_Total);
         Hb_Totales.setSpacing(5);
         Hb_Totales.setAlignment(Pos.CENTER_RIGHT);
         Gp_FacturaItems.add(Hb_Totales, 4, 1);
         GridPane.setValignment(H_Botones, VPos.BOTTOM);
         Gp_FacturaItems.add(H_Botones, 1, 1);
         H_Botones.setAlignment(Pos.BOTTOM_LEFT);
         
         Gp_FacturaItems.setVgap(7);
         Gp_FacturaItems.setHgap(3);
         Ta_FacturaItems.setMaxHeight(260);
         V_Factura=new VBox();
          V_Factura.setSpacing(5);
         
          V_Factura.getChildren().addAll(Gp_Factura);
         // Gp_Factura.setMaxSize(1245, 650);
          Gp_FacturaItems.autosize();
          Gp_FacturaItems.setAlignment(Pos.CENTER);
          Gp_Factura.setAlignment(Pos.CENTER);
           
            
           disabledAllTextField();
            V_Factura.autosize();
            generarVlrCambio();
           
            
             
              
         
        
          stack.getChildren().addAll(V_Factura);
         
          stack.setAlignment(Pos.TOP_CENTER);
          
       
          
          changeColumns();
          eventos_inputs();
           KeyCombination kc = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
           Runnable task = () ->nuevaFactura();
            KeyCombination kg = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
           Runnable task2 = () ->{
                try {
                    guardarFactura();
                } catch (ParseException ex) {
                    Logger.getLogger(FICI_FacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
             
             crearoeditar();
             
              Object[] _listFacturaItem=(EntidadesStatic.es_facturaproveedores.getFacturaItems()).toArray();
            Ol_FacturaItems.clear();
            Ol_FacturaItems.addAll(_listFacturaItem);
            Ta_FacturaItems.setItems(Ol_FacturaItems);
            
      return stack;
    }
 private  void buscarproducto()
 {
      List<FacturaItemProveedores>li_facFacturaItemProveedoreses=new ArrayList<>();
     li_facFacturaItemProveedoreses.addAll(EntidadesStatic.es_facturaproveedores.getFacturaItemsProveedores());
     List<FacturaItemProveedores>li_facFacturaItemProveedoreses2=li_facFacturaItemProveedoreses.stream().filter(line->line.getProduct().getCodigobarras().toUpperCase().contains(buscar.getText().toUpperCase().trim()) || line.getProduct().getNombre().toUpperCase().contains(buscar.getText().toUpperCase())).collect(Collectors.toList());
     //AbacoApp.s_facturaproveedores.setFacturaItemsProveedores(li_facFacturaItemProveedoreses2);
     O_ListFacturaItems=li_facFacturaItemProveedoreses2.toArray();
        Ol_FacturaItems.clear();
    
    Ol_FacturaItems.addAll(O_ListFacturaItems);
    Ta_FacturaItems.setItems(Ol_FacturaItems);
 }       
 public void nuevaFactura() 
 {
     if(!B_New.isDisabled())
     {
     enabledAllTextField();
     EntidadesStatic.es_facturaproveedores=null;
     EntidadesStatic.es_facturaproveedores = new FacturaProveedores();
     Ol_FacturaItems.clear();
   
    tf_nombretercero.setText("");
    Tf_NitProveedor.setText("");
    Tf_DireccionProveedor.setText("");
    Tf_TelefonoProveedor.setText("");
    Tf_Codigo.setText("");
    Rb_Credito.setSelected(false);
    Rb_Contado.setSelected(false);
    tf_producto.setText("");
    Tf_Valor.setText("0");
    Tf_Cantidad.setText("0");
    Tf_PrecioCompra.setText("0");
    Tf_Total.setText("0");
    buscar.setText("");
    fechavencimiento.setValue(LocalDate.now());
    
    
    
    getnumerodigital();
    EntidadesStatic.es_facturaproveedores.setFacturaDate(new Date());
    SimpleDateFormat  df=new SimpleDateFormat("yyyy-MM-dd");
     String fecha =df.format(EntidadesStatic.es_facturaproveedores.getFacturaDate()); 
     LocalDate ld=LocalDate.parse(fecha);
     Tf_Fecha.setValue(ld);
   
    Tf_ValorRecibido.setText("0");
    nuevo();
    L_Message.setText("Nueva Factura"); 
    Task_Message = () -> {
         try {
             SetMessage();
         } catch (InterruptedException ex) {
             Logger.getLogger(FICI_FacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
         }
     };
     backgroundThread = new Thread(Task_Message);
      // Terminate the running thread if the application exits
       backgroundThread.setDaemon(true);
 
       // Start the thread
      backgroundThread.start();
      Tf_Fecha.requestFocus();
   }
     
 }
    private void guardarFactura() throws ParseException, ParseException, ParseException, ParseException, ParseException
    {
       if(validar_guardar())
       {
           try
         {
         EntidadesStatic.es_facturaproveedores.setNo_factura(Long.valueOf(Tf_NoFactura.getText().trim()));
         }catch(Exception e)
         {
              EntidadesStatic.es_facturaproveedores.setNo_factura(Long.valueOf("0"));
         }
         EntidadesStatic.es_facturaproveedores.setCredito(Rb_Credito.isSelected());
         EntidadesStatic.es_facturaproveedores=FacturaProveedoresController.update();
         LocalDate localDate = Tf_Fecha.getValue();
         Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
         EntidadesStatic.es_facturaproveedores.setFacturaDate(Date.from(instant));
         EntidadesStatic.es_facturaproveedores.setProveedores(EntidadesStatic.es_terceros);
         FFacturaCompra.getRecords();
         IsoftConta.resultprocess=0;
         IsoftConta.mensajenotificacion();
        }
  }
    
    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
Task<Void> task = new Task<Void>() {
         @Override protected Void call() throws Exception {
            
  
             return null;
         }
     };

public void addProduct()
{ 
   actualizamosproducto();
    if(validar_guardar())
    {
         EntidadesStatic.es_facturaproveedores.setProveedores(EntidadesStatic.es_terceros);
         try
         {
         EntidadesStatic.es_facturaproveedores.setNo_factura(Long.valueOf(Tf_NoFactura.getText().trim()));
         }catch(Exception e)
         {
             EntidadesStatic.es_facturaproveedores.setNo_factura(Long.valueOf("0"));
         }
             EntidadesStatic.es_facturaproveedores.setCredito(Rb_Credito.isSelected());
             EntidadesStatic.es_facturaproveedores.addProduct(EntidadesStatic.es_producto,Float.valueOf(Tf_Cantidad.getText().trim()),BigDecimal.valueOf(Double.parseDouble(Tf_Valor.getText().trim())),null);
             EntidadesStatic.es_facturaproveedores=FacturaProveedoresController.editFactura(buscar.getText(),fechavencimiento.getValue());
             
                    
                    Tf_Total.setText(String.valueOf(EntidadesStatic.es_facturaproveedores.getTotalAmount().longValue()));
                    getnumerodigital();
    //Cargamos la tabla con el nuevo item
    O_ListFacturaItems=EntidadesStatic.es_facturaproveedores.getFacturaItems().toArray();
    Ol_FacturaItems.clear();
    
    Ol_FacturaItems.addAll(O_ListFacturaItems);
    Ta_FacturaItems.setItems(Ol_FacturaItems);
    nuevoitem();
   IsoftConta.resultprocess=3;
   IsoftConta.mensajenotificacion();
    
   
  
           
   
    }   
 
}
public void deleteItem()
{
    
    FacturaItemProveedores fi=(FacturaItemProveedores)Ta_FacturaItems.getFocusModel().getFocusedItem();
    EntidadesStatic.es_facturaproveedores.removeProduct(fi.getProduct());
    FacturaProveedoresController.editFactura(buscar.getText(),fechavencimiento.getValue());
    O_ListFacturaItems=EntidadesStatic.es_facturaproveedores.getFacturaItems().toArray();
    Ol_FacturaItems.clear();
    Ol_FacturaItems.addAll(O_ListFacturaItems);
    Ta_FacturaItems.setItems(Ol_FacturaItems);
    
    Tf_Total.setText(String.valueOf(EntidadesStatic.es_facturaproveedores.getTotalAmount().longValue()));
    Gr_NumeroDigital.getChildren().remove(numerodigital);
    numerodigital=null;
    numerodigital = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50),Tf_Total.getText()); 
    numerodigital.setLayoutX(10);
    numerodigital.setLayoutY(45);
    numerodigital.getTransforms().add(new Scale(1f, 1f, 0, 0));
    Gr_NumeroDigital.getChildren().add(numerodigital);
    IsoftConta.resultprocess=2;
    IsoftConta.mensajenotificacion();
}
public void modifyItem(String quantity)
{
 FacturaItemProveedores fi=(FacturaItemProveedores)Ta_FacturaItems.getFocusModel().getFocusedItem();
     EntidadesStatic.es_facturaproveedores.modifyItem(fi.getProduct(),Float.valueOf(quantity.trim()));
     FacturaProveedoresController.editFactura(buscar.getText(),fechavencimiento.getValue());
   
    
    O_ListFacturaItems=EntidadesStatic.es_facturaproveedores.getFacturaItems().toArray();
    EntidadesStatic.li_kardex=KardexController.getRecords();
    Ol_FacturaItems.clear();
    Ol_FacturaItems.addAll(O_ListFacturaItems);
    Ta_FacturaItems.setItems(Ol_FacturaItems);
    Tf_Total.setText(String.valueOf(EntidadesStatic.es_facturaproveedores.getTotalAmount().longValue()));
    Gr_NumeroDigital.getChildren().remove(numerodigital);
    numerodigital=null;
    numerodigital = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50),Tf_Total.getText()); 
    numerodigital.setLayoutX(10);
    numerodigital.setLayoutY(45);
    numerodigital.getTransforms().add(new Scale(1f, 1f, 0, 0));
    Gr_NumeroDigital.getChildren().add(numerodigital);
    IsoftConta.resultprocess=2;
    IsoftConta.mensajenotificacion();
}
public void changeColumns(){
              Tc_NoItem = new TableColumn();
              Tc_NoItem.setText("No Item");
              Tc_NoItem.setMinWidth(200);
              Tc_NoItem.setCellValueFactory(new PropertyValueFactory("position"));
              Tc_Producto = new TableColumn();
              Tc_Producto.setText("Producto");
              Tc_Producto.setMinWidth(240);
              Tc_Producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getProduct().getNombre());
            }
            });
          
             Tc_Cantidad = new TableColumn();
             Tc_Cantidad.setText("Cantidad");
             Tc_Cantidad.setMinWidth(200);
             Tc_Cantidad.setCellValueFactory(new PropertyValueFactory("quantity"));
             Tc_Cantidad.setCellFactory(TextFieldTableCell.forTableColumn(sc));           
             Tc_ValorU = new TableColumn();
             Tc_ValorU.setText("Valor/U");
              Tc_ValorU.setMinWidth(200);
             Tc_ValorU.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getValor_u().toString());
            }
            });
          
             Tc_ValorT = new TableColumn();
             Tc_ValorT.setText("Valor Total");
             Tc_ValorT.setMinWidth(200);
             Tc_ValorT.setCellValueFactory(new PropertyValueFactory("valor_total"));
          
             Ta_FacturaItems.getColumns().clear();
             Ta_FacturaItems.setEditable(true);
             Ta_FacturaItems.getColumns().addAll(Tc_NoItem,Tc_Producto,Tc_Cantidad,Tc_ValorU,Tc_ValorT);
             //Ancho de tabla y ancho de porcentaje de columnas
             
             Ta_FacturaItems.setMinHeight(150);
            // GridPane.setFillWidth(veil, Boolean.TRUE);
             Ta_FacturaItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
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
     if(EntidadesStatic.li_productos!=null)
     {
    List<Producto> li_producto=EntidadesStatic.li_productos.stream().filter(line ->line.getCodigobarras().toUpperCase().equals(Tf_Codigo.getText().trim()))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());
    if(li_producto.size()>0)
    { 
      EntidadesStatic.es_producto= li_producto.get(0);
      setProducto();
      try
      {
       Tf_Cantidad.requestFocus();
       }catch(Exception e)
                           {
                               
                           }
      }
                    else
                    {
                          EntidadesStatic.es_producto=null;
                          EntidadesStatic.es_producto=new Producto();
                           tf_producto.getSelectedText();
                           tf_producto.requestFocus();
                          
                           
                           
                    }
                   
 }else
     {
               tf_producto.getSelectedText();
               try
               {
                           tf_producto.requestFocus();
                           }catch(Exception e)
                           {
                               
                           }
                          
     }
    
 }
 public void generarVlrCambio()
 {
   Rectangle dragger = new Rectangle(330,55);
        dragger.setArcHeight(20);
        dragger.setArcWidth(20);
       //fill the dragger with some nice radial background
         gradient2 = new LinearGradient(0, 0, 0, 0.5,  true, CycleMethod.REFLECT, new Stop[] {
        new Stop(0, Color.BLACK),
        new Stop(0.1, Color.BLACK),
        new Stop(1, Color.BLACK)
        });
        dragger.setFill(gradient2);   
        Gr_ValorCambio = new Group();
        // background image
        // digital clock
        Nd_ValorCambio = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50),"000.000");
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(1f, 1f, 0, 0));
        // add background and clock to sample
         Gr_ValorCambio.getChildren().addAll(dragger, Nd_ValorCambio);
         GridPane.setRowSpan(Gp_VlrRecibido,2); 
         GridPane.setColumnSpan(Gp_VlrRecibido,5); 
         GridPane.setHalignment(Gp_VlrRecibido, HPos.RIGHT);
         Gp_VlrRecibido.setAlignment(Pos.CENTER);
         
         Gp_VlrRecibido.setVgap(5);
         Gp_VlrRecibido.setHgap(5);
         Gp_VlrRecibido.add(L_ValorRecibido, 0, 0);
         Gp_VlrRecibido.add(Tf_ValorRecibido, 1, 0);
         Gp_VlrRecibido.add(L_ValorCambio, 0, 1);
         Gp_VlrRecibido.add(Gr_ValorCambio, 1, 1);
         Gp_VlrRecibido.add(bu_print, 2, 1);
         Gp_VlrRecibido.getStyleClass().add("solucion-gridpane-background");
        
 }
 public void addGp_VlrRecibido()
 {
     
       
       
        // add background and clock to sample
       if(Gp_FacturaItems.getChildren().indexOf(Gp_VlrRecibido)==-1)
       {
        Gp_FacturaItems.add(Gp_VlrRecibido, 0, 1);
       Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
        Nd_ValorCambio=null;
        Nd_ValorCambio = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50),"000.000");
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(1f, 1f, 0, 0));
        
        Gr_ValorCambio.getChildren().add(Nd_ValorCambio);
       
        
        
       }
       
 }
 public void removeGp_VlrRecibido()
 {
   if(Gp_FacturaItems.getChildren().indexOf(Gp_VlrRecibido)!=-1)
       {
        Gp_FacturaItems.getChildren().remove(Gp_VlrRecibido);
       }  
 }
 public void showVlrCambio()
 {
     
        EntidadesStatic.es_facturaproveedores.setEfectivo(BigDecimal.valueOf(Double.parseDouble(Tf_ValorRecibido.getText().trim())));
        FacturaProveedoresController.editFactura(buscar.getText(),fechavencimiento.getValue());
   
        Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
        Nd_ValorCambio=null;
        Nd_ValorCambio = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50),EntidadesStatic.es_facturaproveedores.getDevolver().toString());
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(1f, 1f, 0, 0));
        
        Gr_ValorCambio.getChildren().add(Nd_ValorCambio);
        bu_print.requestFocus();
        cronometroRemoveGp_VlrRecibido();
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
  FadeTransition ft = new FadeTransition(Duration.millis(2000),tf_producto);
        //ft.setFromValue(0.0);
       // ft.setToValue(1);
      
        ft.play();
       
        //success.setOpacity(0);
      
       ft.setOnFinished(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                Tf_NoFactura.setStyle(null);
                Tf_NoFactura.getStyleClass().add("text-field");
                
                Tf_Codigo.setStyle(null);
                Tf_Codigo.getStyleClass().add("text-field");
                Tf_Precio.setStyle(null);
                Tf_Precio.getStyleClass().add("text-field");
                tf_iva.setStyle(null);
                tf_iva.getStyleClass().add("text-field");
                Tf_NitProveedor.setStyle(null);
                Tf_NitProveedor.getStyleClass().add("text-field");
                tf_nombretercero.setStyle(null);
                tf_nombretercero.getStyleClass().add("text-field");
                Tf_TelefonoProveedor.setStyle(null);
                Tf_TelefonoProveedor.getStyleClass().add("text-field");
                Ta_FacturaItems.setStyle(null);
                fechavencimiento.setStyle(null);
               
               
            }
        });
        
           
       
    }
  public void ImportarExcel() throws IOException {
       
        file=fileChooser.showOpenDialog(stage);
        Producto producto;
        Kardex kardex;
        if(file!=null)
        {
           System.out.println(file.getAbsolutePath());
           Workbook w;
    try {
     w = Workbook.getWorkbook(file);
    
     
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd mm yyyy hh:mm:ss"); 
   
      Sheet sheet = w.getSheet(0);
    
      
    
      for (int j = 1; j<sheet.getRows(); j++){
        Cell codigosbarras= sheet.getCell(0, j);
    	Cell nombreproducto = sheet.getCell(1, j);
        Cell preciounitariocompra = sheet.getCell(2, j);
        Cell cant = sheet.getCell(3, j);
        Cell cporcentajeganancia = sheet.getCell(4, j);
        Cell observaciones = sheet.getCell(5, j);
        Cell ubicacion = sheet.getCell(6, j);
        producto=null;
             	 if(!codigosbarras.getContents().equals(""))
                 {
                     producto=new Producto();
                     tf_producto.setText(nombreproducto.getContents());
                     Tf_Codigo.setText(codigosbarras.getContents().trim());
                     Tf_PrecioCompra.setText(preciounitariocompra.getContents());
                     Tf_Cantidad.setText(cant.getContents());
                     Tf_Valor.setText(new BigDecimal(Tf_Cantidad.getText().trim()).multiply(new BigDecimal(Tf_PrecioCompra.getText().trim())).toString());
                     gp_observaciones=observaciones.getContents();
                     gp_ubicacion=ubicacion.getContents();        
                     porcentajeganancia.setText(cporcentajeganancia.getContents().trim());
                     findByCodigo();
                     addProduct();
                 }
                 else
                     
                 {  
                      
                    thimport=null;
                     break;
                 }
             
        
         }
      
       
      
      //*********************************** formato 2
      
      
    // }
      
    } catch (BiffException e) {
      e.printStackTrace();
    }            catch (Throwable ex) {
                     Logger.getLogger(FICI_FacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
                 }
  
       
        }
   
      /* thimport = new Thread(task_import);
                
  
        thimport.setDaemon(true);
        thimport.start();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");      
        
        p.progressProperty().bind(task_import.progressProperty());
        veil.visibleProperty().bind(task_import.runningProperty());
        p.visibleProperty().bind(task_import.runningProperty());
        task_import=null;
      
      */
  }
 private void calcularValorTotal()
 {
     Tf_Valor.setText((BigDecimal.valueOf(Double.valueOf(Tf_Cantidad.getText().trim())).multiply(BigDecimal.valueOf(Double.valueOf(Tf_PrecioCompra.getText().trim()))).toString()));
     
 }
 private void setProducto()
 {
     
     tf_producto.setText(EntidadesStatic.es_producto.getNombre());
     
                   
             
 }

public void crearoeditar()
 {
     enabledAllTextField();
     EntidadesStatic.es_producto=null;
     EntidadesStatic.es_producto=new Producto();
     
     if(EntidadesStatic.es_facturaproveedores!=null)
     {
         try
     {
     Tf_NoFactura.setText(EntidadesStatic.es_facturaproveedores.getNo_factura().toString());
     }catch(Exception e)
     {
         
     }
     try
     {
      tf_nombretercero.setText(EntidadesStatic.es_facturaproveedores.getProveedores()!=null?EntidadesStatic.es_facturaproveedores.getProveedores().getNombres():"");
      
     }catch(Exception e)
     {
         
     }
      EntidadesStatic.es_terceros=EntidadesStatic.es_facturaproveedores.getProveedores();
      try
      {
      Tf_NitProveedor.setText(EntidadesStatic.es_facturaproveedores.getProveedores().getNo_ident());
      }catch(Exception e)
      {
          
      }
      try
      {
      Tf_TelefonoProveedor.setText(EntidadesStatic.es_facturaproveedores.getProveedores().getCelular());
      }catch(Exception e)
      {
     
      }
      try
      {
       Tf_DireccionProveedor.setText(EntidadesStatic.es_facturaproveedores.getProveedores().getDir1());
      }catch(Exception e)
      {
          
      }
      SimpleDateFormat  df=new SimpleDateFormat("yyyy-MM-dd");
      String fechaFab =df.format(EntidadesStatic.es_facturaproveedores.getFacturaDate()); 
      LocalDate ld=LocalDate.parse(fechaFab);
      Tf_Fecha.setValue(ld);
      Tf_Total.setText(String.valueOf(EntidadesStatic.es_facturaproveedores.getTotalAmount().longValue()));
      getnumerodigital();
      if(EntidadesStatic.es_facturaproveedores.getCredito())
     {
         Rb_Credito.setSelected(true);
     }
     else
     {
         Rb_Contado.setSelected(true);
     }
     O_ListFacturaItems=EntidadesStatic.es_facturaproveedores.getFacturaItems().toArray();
     Ol_FacturaItems.clear();
    
    Ol_FacturaItems.addAll(O_ListFacturaItems);
    Ta_FacturaItems.setItems(Ol_FacturaItems);
    tf_producto.setText("");
    Tf_Valor.setText("0");
    Tf_Cantidad.setText("0");
    Tf_PrecioCompra.setText("0");
    Tf_Codigo.setText("");
    buscar.setText("");
    fechavencimiento.setValue(LocalDate.now());
        Tf_Precio.setText("0");
    tf_iva.setText("0");
    Tf_PrecioCompra.setText("0");
     }
     else
     { 
         EntidadesStatic.es_facturaproveedores=new FacturaProveedores();
         nuevo();
     }
 } 
      
  private void show()  
  {
   try {
               stageProducto.showmodal();
            } catch (Exception ex) 
            {
              ex.printStackTrace();
            }
  }  
  private void showproveedor()  
  {
     try {
               stageProveedores.showmodal();
               setdatosproveedor();
         } catch (Exception ex) 
         {
              ex.printStackTrace();
            }
            }
     

 public static void main(String[] args) {
        launch(args);
    }
 
 
        
 
 private void nuevo()
 {
  
    Tf_Codigo.requestFocus();
        Tf_Precio.setDisable(false);
    tf_iva.setDisable(false);
    tf_iva.setText("0");
     Tf_PrecioCompra.setDisable(false);
    Tf_Codigo.setDisable(false);
    tf_producto.setDisable(false);
     Tf_Cantidad.setDisable(false);
    Tf_Valor.setDisable(false);
    tf_producto.setText("");
    Tf_Valor.setText("0");
    Tf_Cantidad.setText("0");
    Tf_PrecioCompra.setText("0");
    Tf_Codigo.setText("");
    Tf_Precio.setText("0");
    tf_iva.setText("0");
    Tf_PrecioCompra.setText("0");
    
   EntidadesStatic.es_producto=null;
   EntidadesStatic.es_producto=new Producto();
    buscar.setText("");
    fechavencimiento.setValue(LocalDate.now());
     Tf_Fecha.requestFocus();
   EntidadesStatic.es_terceros=null;
   EntidadesStatic.es_terceros=new Terceros();
 }
 private void nuevoitem()
 {
     try
     {
    Tf_Codigo.requestFocus();
    }catch(Exception e)
                           {
                               
                           }
  
    Tf_PrecioCompra.setDisable(false);
    Tf_Codigo.setDisable(false);
    tf_producto.setDisable(false);
    Tf_Cantidad.setDisable(false);
    Tf_Valor.setDisable(false);
    tf_producto.setText("");
    Tf_Valor.setText("0");
    Tf_Cantidad.setText("0");
    Tf_PrecioCompra.setText("0");
    Tf_Codigo.setText("");
    Tf_Precio.setText("0");
    tf_iva.setText("0");
    Tf_PrecioCompra.setText("0");
    
   EntidadesStatic.es_producto=null;
   EntidadesStatic.es_producto=new Producto();
    buscar.setText("");
    fechavencimiento.setValue(LocalDate.now());
  
    
 }
 private void   SetMessage() throws InterruptedException
    {
       
       Gp_Message.setVisible(true);
       //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
         Thread.sleep(3000);
         Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
         Task_Message=null;
        //ft.setFromValue(0.0);
       // ft.setToValue(1);
      
       // ft.play();
       
        //success.setOpacity(0);
      
       /*ft.setOnFinished(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
                      Gp_Message.setVisible(false);
                      backgroundThread.setDaemon(false);
 
       // Start the thread

      Task_Message=null;
            }
           
       });*/
    }
 
public boolean validar_guardar()
{
     boolean m_BCanSave=true;    
       if(EntidadesStatic.es_terceros==null)
        {
            IsoftConta.resultprocess=5;
            IsoftConta.mensajenotificacion();
            m_BCanSave=false;
        }
        else{
        if(EntidadesStatic.es_terceros.getId()==null)
        {
             IsoftConta.resultprocess=5;
            IsoftConta.mensajenotificacion();
            m_BCanSave=false;
        } 
        }
        if(EntidadesStatic.es_producto==null)
        {
             IsoftConta.resultprocess=6;
            IsoftConta.mensajenotificacion();
            m_BCanSave=false;
        }
        else{
        if(EntidadesStatic.es_producto.getId()==null)
        {
            IsoftConta.resultprocess=6;
            IsoftConta.mensajenotificacion();
            m_BCanSave=false;
        } 
        }
        if(Integer.valueOf(Tf_Cantidad.getText().trim())<=0)
        {
             IsoftConta.resultprocess=7;
            IsoftConta.mensajenotificacion();
        }
        
        return m_BCanSave; 
             
}

public void eventos_inputs()
{
    tf_nombretercero.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                  Tf_DireccionProveedor.requestFocus();
                  
               
            }});
     tf_nombretercero.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                  Tf_DireccionProveedor.requestFocus();
                  
               
            }});
     Tf_DireccionProveedor.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                  Tf_TelefonoProveedor.requestFocus();
                  
               
            }});
      Tf_TelefonoProveedor.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                  Rb_Contado.requestFocus();
                  
               
            }});
      
     Tf_Codigo.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                if(Tf_Codigo.getText()!=null)
                {
                  findByCodigo();
                }
                  
               
            }});
     
     
      Tf_NoFactura.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
                  
               
            }});
       
         
        
       
       
      
     
      Tf_Valor.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent keyEvent) {
             
                 B_Insertar.requestFocus();
                
            
            }
        });
    
    
   
          
            Ta_FacturaItems.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
             if (keyEvent.getCode() == KeyCode.DELETE) {
                 deleteItem();
                }  
            
            }
        });
          
           
      Tf_ValorRecibido.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                showVlrCambio();
                bu_print.requestFocus();
               
            }
        });
      
      //eventos de guardar, salir e imprimir
    Gp_VlrRecibido.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
             if (keyEvent.getCode() == KeyCode.ESCAPE) {
                
               removeGp_VlrRecibido();
            }
            }
        });
         Tf_NoFactura.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                  
                try{
                    Float.valueOf(Tf_NoFactura.getText());
                    
               
                }  catch(Exception e)
                {
                    keyEvent.consume(); 
                    Tf_NoFactura.deleteNextChar();
                    Tf_NoFactura.backward();
                    
                    
                }
                }
        }); 
         
        Rb_Contado.setOnKeyPressed(e->{
            if(e.getCode().equals(e.getCode().ENTER))
            {
                Rb_Credito.requestFocus();
            }
        }); 
       
       Rb_Credito.setOnKeyPressed(e->{
            if(e.getCode().equals(e.getCode().ENTER))
            {
                Tf_Codigo.requestFocus();
            }
        }); 
       
       fechavencimiento.setOnKeyPressed(e->{
            if(e.getCode().equals(e.getCode().ENTER))
            {
                Tf_PrecioCompra.requestFocus();
            }
        });
       
       
         
          buscar.setOnAction(e->{
            fechavencimiento.requestFocus();
          }
          );
        
          Tf_PrecioCompra.setOnAction(e->{ calcularValorTotal();
              Tf_Valor.requestFocus();});
          Tf_Cantidad.setOnAction(e->{ 
              Tf_PrecioCompra.requestFocus();
             });
          Tf_Fecha.setOnKeyPressed(e->{if(e.getCode().equals(e.getCode().ENTER))
                  {Tf_NoFactura.requestFocus();}});
          Tf_NoFactura.setOnAction(e->{Tf_NitProveedor.requestFocus();});
          
          B_Insertar.setOnKeyPressed(e->{
           if(e.getCode().equals(e.getCode().ENTER))
           {
                addProduct();
                Tf_Codigo.requestFocus();
           }
          });
          B_New.setOnAction(e->{
               
                    
                     nuevaFactura();
                
          });
          bu_importardesdeexcel.setOnAction(e->{
               
                    
        try {
            ImportarExcel();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
                
          });
    
}
 

 public void findByProducto()
 {
     if(EntidadesStatic.es_producto!=null)
             {
                 if(EntidadesStatic.es_producto.getId()!=null)
                 {
                    tf_producto.setText(EntidadesStatic.es_producto.getNombre());
                    Tf_Codigo.setText(EntidadesStatic.es_producto.getCodigobarras());
                    Tf_Cantidad.requestFocus();
                   
                      
                    
                 }else
                 {
                     tf_producto.getSelectedText();
                     Tf_Cantidad.requestFocus();
                     EntidadesStatic.es_producto=null;
                     EntidadesStatic.es_producto=new Producto();
                 }
                 
             }
     else
     {
                     tf_producto.getSelectedText();
                     Tf_Cantidad.requestFocus();
                     EntidadesStatic.es_producto=null;
                     EntidadesStatic.es_producto=new Producto();
     }
 }
 
 public void getnumerodigital()
 {
   
    Gr_NumeroDigital.getChildren().remove(numerodigital);
    numerodigital=null;
    
    // numerodigital = new NumeroDigital(Color.valueOf("#007fff"), Color.rgb(50, 50, 50),Tf_Total.getText()); 
   
   
         numerodigital = new NumeroDigital(Color.valueOf("#007fff"), Color.TRANSPARENT,Tf_Total.getText()); 
   
    numerodigital.setLayoutX(10);
    numerodigital.setLayoutY(5);
    numerodigital.getTransforms().add(new Scale(0.93f, 0.93f, 0, 0));
    Gr_NumeroDigital.getChildren().add(numerodigital);
 } 
 private void actualizamosproducto()
 {
         System.out.println(tf_producto.getText()+" "+Tf_Codigo.getText()+" "+Tf_PrecioCompra.getText()+ " "+Tf_Cantidad.getText()+" "+porcentajeganancia.getText());
                
     if(EntidadesStatic.es_producto==null)
     {
         EntidadesStatic.es_producto=new Producto();
     }
     EntidadesStatic.es_producto.setCodigobarras(Tf_Codigo.getText().trim());
     EntidadesStatic.es_producto.setNombre(tf_producto.getText());
     EntidadesStatic.es_producto.setPrecio(((new BigDecimal(porcentajeganancia.getText().trim()).multiply(BigDecimal.valueOf(0.01))).multiply(new BigDecimal(Tf_PrecioCompra.getText().trim()))).setScale(0, RoundingMode.HALF_UP).add(new BigDecimal(Tf_PrecioCompra.getText().trim())).setScale(0, RoundingMode.HALF_UP));
     EntidadesStatic.es_producto.setDescrip(gp_ubicacion);
     EntidadesStatic.es_producto.setObservaciones(gp_observaciones);
     EntidadesStatic.es_producto.setTipo("0");
     EntidadesStatic.es_producto.setIva(Integer.valueOf(tf_iva.getText().trim()));
     
     if(EntidadesStatic.es_producto.getId()==null)
     {
        ProductoController.servicio_crear();
     }
     else
     {
         ProductoController.servicio_actualizar();
     }
     EntidadesStatic.li_productos=ProductoController.getRecordsProductos(null);
 }
 private void setdatosproveedor()
 {  
    tf_nombretercero.setText(EntidadesStatic.es_terceros.getNo_ident()+"="+EntidadesStatic.es_terceros.getNombres());
    Tf_NitProveedor.setText(EntidadesStatic.es_terceros.getNo_ident());
    Tf_DireccionProveedor.setText(EntidadesStatic.es_terceros.getDir1());
    Tf_TelefonoProveedor.setText(EntidadesStatic.es_terceros.getCelular());   
 }
}
