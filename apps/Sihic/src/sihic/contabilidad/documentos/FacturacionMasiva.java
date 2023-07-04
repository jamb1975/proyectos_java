package sihic.contabilidad.documentos;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import static javafx.application.Application.STYLESHEET_MODENA;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import modelo.Auditoria;
import modelo.ConComprobanteProcedimiento;
import modelo.Puc;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.GenConvenios;
import modelo.GenEapb;
import modelo.GenPersonas;
import modelo.GenTiposUsuarios;
import modelo.HclFurisp;
import modelo.Kardex;
import modelo.ProductoMedicamentos;
import service.AuditoriaController;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import static sihic.SihicApp.facturaitemcontrollerclient;
import sihic.contabilidad.FindServicios;
import sihic.contabilidad.ImpresionFactura;
import sihic.contabilidad.ImprimirPdf;
import sihic.contabilidad.MovimientoCuentas;
import sihic.control.SearchBox;
import sihic.controlador.ConPucControllerClient;
import sihic.controlador.FacturaControllerClient;
import sihic.controlador.ProductoControllerClient;
import sihic.digitos.NumeroDigital;
import sihic.controlador.GenPersonasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.UtilDate;

public class FacturacionMasiva {

    //grupo
   private static Group  Gr_NumeroDigital;
    Group Gr_ValorCambio;
    LinearGradient gradient2;
    //String Converter
    StringConverter<Object> sc;
    //buttons
    private static Button B_Print;
    
    private static Button B_Save;
    private static Button bu_nuevo;
    private static Button bu_detalles;
    
   
 
     private static Button B_Pdffacturaglobal;
     private static Button bu_facturacapitado;
    private static Button bu_printcomprobante;
    private static Button bu_furisp;
    //Tabla Items de SihicApp.s_factura
    private static TableView Ta_FacturaItems;
    //GridPane row and columns
    private GridPane Gp_Factura;
    private GridPane Gp_DatosCliente;
    private GridPane Gp_Total;
    private GridPane Gp_Producto;
    private GridPane Gp_FacturaItems;
    private GridPane Gp_Totales;
    private GridPane Gp_VlrRecibido;
    private static Text T_NoFactura;
    private Text T_Total;
    //Etiquetas datos cliente
    private static Label la_adquiriente;
    private static Label la_nitccadquiriente;
    private static Label la_fechexpedicion;
    private static Label la_fechexpiracion;
    private static Label L_Total;
    private static Label L_Iva;
    private static Label L_Subtotal;
 
    
    private static Label L_DescuentoFactura;
    private static Label L_ValorRecibido;
    private static Label L_ValorCambio;
    private static Label la_totalvalorcuota;

    private static Label success;
    //Campos de texto datos cliente 
    private static TextField adquiriente;
    private  static TextField nitccadquiriente;
    private static TextField medicoqueordena;
    private static DatePicker fechaexpedicion;
    private static DatePicker fechaexpiracion;
    private static SearchBox sb_producto = new SearchBox();
    private static SearchPopover sp_producto;
    private static TextField Tf_CodigoCups;
    private static TextField Tf_Cantidad;
    private static TextField Tf_Iva;
    private static TextField Tf_Subtotal;
    private static TextField Tf_Total;
    private static TextField Tf_DescuentoFactura;
    private static TextField Tf_ValorRecibido;
    private static CheckBox cb_credito;
    private static TextField totalvalorcuota;
    private  HBox V_box;
    private VBox V_Factura;
    private HBox H_Botones;
    private HBox H_Botonesnav;
    private TextField valorcuota;
    private Label la_valorcuota;
    //Togglegroup
    //creamos el numero digital
    private static NumeroDigital numerodigital;
    private NumeroDigital Nd_ValorCambio;
    //Objetos que contiene los datos de la SihicApp.s_factura
    private static ObservableList Ol_FacturaItems = FXCollections.observableArrayList();
    private static Object[] O_ListFacturaItems;
    private TableColumn Tc_NoItem;
    private TableColumn Tc_eapb;
    private TableColumn Tc_NoIdent;
    private TableColumn Tc_Paciente;
    private TableColumn Tc_CodServicio;
    private TableColumn Tc_CodCups;
    private TableColumn Tc_Producto;
    private TableColumn Tc_Cantidad;
    private TableColumn Tc_Descuento;
    private TableColumn Tc_ValorU;
    private TableColumn Tc_ValorT;

    //Objetos de persistencia y modelso de datos
   private static FacturaControllerClient facturaControllerClient;
    GenPersonasControllerClient genPersonasControllerClient;
    ProductoControllerClient productoControllerClient;
    //Stage mostrar formulario findproducto
    private FindServicios findProduct;
    private GenPersonas genPersonas;
    private HBox Hb_Existencias;
    private Label la_prestoservicio;
    private static TextField prestoservicio;
    private Label la_nitprestoservicio;
    private static TextField nitprestoservicio;
    private Label la_nitusuarioservicio;
    private TextField nitusuarioservicio;
    private Label la_usuarioservicio;
    private TextField usuarioservicio;
    private Label la_credito;
    private Label la_eapb=new Label("Eapb");
    private static SearchBox sb_eapb=new SearchBox();
    private static SearchPopover sp_eapb;
      private Label la_tipousuario=new Label("Tipo Usuario");
    private static SearchBox sb_tipousuario=new SearchBox();
    private static SearchPopover sp_tipousuario;
    private Label la_convenios=new Label("Convenios");
    private static TextField sb_convenios=new TextField();
    private static SearchPopover sp_convenios;
    private Label la_tarifas=new Label("Tarifas");
    private static ChoiceBox tarifas=new ChoiceBox();
    private Label la_prefijos=new Label("Prefijos");
    private static ConComprobanteProcedimiento conComprobanteProcedimiento;
    private static ImpresionFactura impresionFactura;
    private FacturaItem facturaItem;
    private Stage stageFactura = new Stage(StageStyle.DECORATED);
    public static Scene sceneFactura = null;
    private Stage stagePdfFactura = new Stage(StageStyle.DECORATED);
    public static Scene scenePdfFactura = null;
    private float porcentaje;
    private ConPucControllerClient conPucControllerClient;
    private Puc conPuc;
    private String cuentaparticulares = "13052500001";
    private Label la_totalcopagos;
    private static TextField totalcopagos;
    private static Label la_diferenciacapitado=new Label("Diferencia en capitación:");
     private static TextField diferenciacapitado=new TextField("0") ;
    private Label la_prefijosfacturas;
    private TextField valorcopagos;
    private DecimalFormat format = new DecimalFormat("#.0");
    private StackPane stack;

    //************Stage furisp
    private Parent P_Furisp;
    private String appPathFurisp;
    private Class clzFurisp;
    private Object appClassFurisp;
    private Stage stageFurisp = new Stage(StageStyle.DECORATED);
    private Stage stagedetalles = new Stage(StageStyle.DECORATED);
    public static Scene scenefurisp = null;
    public static Scene scene = null;
    private Label message;
    private static Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private String codigo = "";
    private static  GridPane Gp_Message;
    private static Label L_Message;
    private static Runnable Task_Message;
    private static Thread backgroundThread;
    private static boolean procesocorrecto;
    private static Label la_variosprocedimientos=new Label("Una Autorización varios procedimientos:");
    private static TextField tf_variosprocedimientos=new TextField();
    private static CheckBox variosprocedimientos=new CheckBox();
    private static  String[]li_variosprocedimientos;
    private static Label la_vercompletoprocedimiento=new Label("Ver Completo Prodimiento:");
    public static CheckBox vervompletoprocedimientp=new CheckBox();
    private static Label la_fechaespecifica=new Label("Fecha Especifica:");
    public static  TextField tf_fechaespecifica=new TextField("");
    public FacturacionMasiva() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException {
        la_variosprocedimientos.setMinWidth(250);
        tf_variosprocedimientos.setMinWidth(200);
        tf_variosprocedimientos.setPromptText("Codigos Separados por - sin espacios");
        sb_convenios.setMaxWidth(250);
        sb_convenios.setMinWidth(250);
        la_convenios.setMinWidth(250);
        sp_eapb = new SearchPopover(sb_eapb, new PageBrowser(), SihicApp.s_genEapb, true);
        sb_eapb.setMaxWidth(250);
        sb_eapb.setMinWidth(250);
        la_eapb.setMinWidth(250);
        sp_tipousuario = new SearchPopover(sb_tipousuario, new PageBrowser(), SihicApp.genTiposUsuarios, false);
        sb_tipousuario.setMaxWidth(250);
        sb_tipousuario.setMinWidth(250);
        la_tipousuario.setMinWidth(250);
        tarifas.getItems().clear();
        tarifas.setMinWidth(250);
        la_tarifas.setMinWidth(250);
        la_prefijos.setMinWidth(250);
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.setMinWidth(250);
        tarifas.getItems().addAll("2001","2015","2016","2017","2018","2001 Soat","2015 Soat","2016 Soat","2017 Soat","2018 Soat","2020","2020 Soat");
        Gp_Message = new GridPane();
        Gp_Message.setMinWidth(1070);
        Gp_Message.setMaxHeight(40);
        L_Message = new Label();
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 0);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.setAlignment(Pos.CENTER_RIGHT);
        Gp_Message.setVisible(false);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText("No hay Valor para esta Tarifa " + (Facturar.s_tarifa == 0 ? "2015" : Facturar.s_tarifa == 1 ? "2016" : Facturar.s_tarifa == 2 ? "2017" : Facturar.s_tarifa == 3 ? "2018" : "2001"));
        stack = new StackPane();
        stageFurisp.setTitle("Furisp");
        sb_producto.setMinWidth(400);
        sb_producto.autosize();
        sp_producto = new SearchPopover(sb_producto, new PageBrowser(), SihicApp.s_kardex, false);
        
        conComprobanteProcedimiento = new ConComprobanteProcedimiento();
        conPucControllerClient = new ConPucControllerClient();
        conPuc = new Puc();
        stageFactura.setTitle("Factura de Usuarios");
        stagePdfFactura.setTitle("Factura PDF");
        Gp_Factura = new GridPane();
        Gp_DatosCliente = new GridPane();
        Gp_Total = new GridPane();
        Gp_Total.setMinWidth(400);
        Gp_Producto = new GridPane();
        Gp_FacturaItems = new GridPane();
        Gp_Totales = new GridPane();
        Gp_VlrRecibido = new GridPane();
        //Task
        findProduct = new FindServicios(false);
        Thread th = new Thread(task);
        th.setDaemon(false);
        th.start();
        //campos de texto
        //datos cliente
        adquiriente = new TextField("");
        adquiriente.setMinWidth(250);
        //adquiriente.setMaxWidth(250);
        nitccadquiriente = new TextField("");
        nitccadquiriente.setMinWidth(250);
        //  nitccadquiriente.setMaxWidth(250);
        nitccadquiriente.setEditable(false);
        prestoservicio = new TextField();
        prestoservicio.setEditable(false);
        fechaexpedicion = new DatePicker(LocalDate.now());
        fechaexpedicion.setMinWidth(250);

        fechaexpiracion = new DatePicker(LocalDate.now());
        fechaexpiracion.setMinWidth(250);
        sb_producto.setPromptText("");
        Tf_CodigoCups = new TextField();
        Tf_CodigoCups.setPromptText("Digite código servicio o Enter para busqueda general");
        Tf_Cantidad = new TextField();
        Tf_Cantidad.setPromptText("Enter para agregar item");
        Tf_Subtotal = new TextField("0");
        Tf_Iva = new TextField("0");
        Tf_Total = new TextField("0.00");
        Tf_DescuentoFactura = new TextField("0");
        Tf_ValorRecibido = new TextField("0");
        Tf_ValorRecibido.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        medicoqueordena = new TextField("");
        valorcopagos = new TextField();
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().select(0);
        valorcopagos.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        la_prefijosfacturas = new Label("Copago:");
        totalvalorcuota = new TextField();
        valorcuota = new TextField();
        valorcuota.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        //Inicializamos objetos de pesistencia
        facturaControllerClient = new FacturaControllerClient();
        productoControllerClient = new ProductoControllerClient();
      
        //Inicializamos objetos de interfaz

        //Cargamos los datos en la tabla Ta_facturaItems
        Ta_FacturaItems = new TableView();
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

        Object[] _listFacturaItem ;

        V_Factura = new VBox();

        //botones
        ImageView imageView = new ImageView("/images/diario.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Print = new Button("", imageView);
     
        //B_Pdf.setMaxWidth(130);
        imageView = new ImageView("/images/comprobante.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
         bu_printcomprobante = new Button("", imageView);
       
        //B_Pdf.setMaxWidth(130);
        imageView = new ImageView("/images/facturaglobal.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Pdffacturaglobal = new Button("", imageView);
        imageView = new ImageView("/images/capitado.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        bu_facturacapitado = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/furisp.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        bu_furisp = new Button("", imageView);

        //B_New.setMaxWidth(130);
        imageView = null;
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Save = new Button("", imageView);
        B_Save.setTooltip(new Tooltip("Actualizar Factura"));
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        bu_nuevo = new Button("", imageView);
        bu_nuevo.setTooltip(new Tooltip("Nueva Factura"));
         imageView = null;
        imageView = new ImageView("/images/detalles.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        bu_detalles = new Button("", imageView);
        bu_detalles.setTooltip(new Tooltip("Detalles de la factura"));
        //B_Save.setMaxWidth(130);
        //acciones de botones y cuadros de texto
        B_Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    guardarFactura();
                } catch (ParseException ex) {
                    Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        bu_furisp.setOnAction(e -> {
            try {
                showFurisp();
            } catch (Exception ex) {
                 ex.printStackTrace();
            }
        });

       

        //acciones de botones y cuadros de texto
        //*************************
      
        B_Print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
              
                try {
                    ImprimirPdf.fprintpdffacturageneral();
                } catch (IOException ex) {
                    Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
                }
               

            }
        });
        B_Pdffacturaglobal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    printFacturaglobal();
                } catch (Exception ex) {
                    Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
                } 

            }
        });
        bu_facturacapitado.setOnAction(e->{
            try {
                printfacturacapitado();
            } catch (IOException ex) {
                Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        B_Print.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.ENTER) {
                    try {
                        pdfFactura();
                    } catch (NoSuchMethodException ex) {
                        Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        bu_printcomprobante.setOnAction(e -> {
            try {
                if(checkregistroexistente())
                {     
                ImprimirPdf.fpdfcomprobanteprocedimiento();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
        });

        bu_printcomprobante.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.ENTER) {
                    try {
                if(checkregistroexistente())
                {     
                ImprimirPdf.fpdfcomprobanteprocedimiento();
                }
            } catch (Exception ex) {
                Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
            } 
                }
            }
        });
        Tf_Cantidad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent keyEvent) {

                try {
                    Float.valueOf(Tf_Cantidad.getText().trim());

                    addProduct();
                    sb_producto.setText("");
                    Tf_CodigoCups.requestFocus();

                } catch (Exception e) {
                    keyEvent.consume();
                    Tf_Cantidad.backward();
                    Tf_Cantidad.deleteNextChar();

                }
            }
        });
       bu_nuevo.setOnAction(e->{
           nuevaFactura();
       });
  
       bu_detalles.setOnAction(e->{
            try {
                showdetalles();
            } catch (Exception ex) {
               ex.printStackTrace();
            }
       });
        

        Tf_ValorRecibido.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                showVlrCambio();
                B_Print.requestFocus();

            }
        });

        //eventos de guardar, salir e imprimir
        Gp_VlrRecibido.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {

                    
                    removeGp_VlrRecibido();
                }
            }
        });

        Tf_CodigoCups.setOnAction(e -> {

            findByCodigo();
            if (SihicApp.s_kardex == null) {
                sb_producto.requestFocus();
            }

        });
        sp_producto.getSearchBox().setOnAction(e -> {

            if (SihicApp.s_kardex != null) {
                Tf_CodigoCups.setText(SihicApp.s_kardex.getProducto().getCodigo());
                Tf_Cantidad.requestFocus();
                findProduct.setKardex(SihicApp.s_kardex);
            }

        });
        Tf_DescuentoFactura.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    Float.valueOf(Tf_DescuentoFactura.getText().trim());
                    //     Tf_DescuentoFactura.setText(((BigDecimal.valueOf(Double.valueOf(Tf_DescuentoFactura.getText().trim())).divide(BigDecimal.valueOf(100))).multiply(findProduct.getKardex().getProducto().getPrecio())).toString());
                    guardarFactura();
                } catch (Exception e) {
                    ke.consume();
                    Tf_DescuentoFactura.backward();
                    Tf_DescuentoFactura.deleteNextChar();

                }

            }
        });
        Ta_FacturaItems.setOnMouseClicked(e -> {
            setcomprobante();

        });
        diferenciacapitado.setOnAction(e->{
            try{
            SihicApp.s_factura.setDiferenciacapitado(new BigDecimal(diferenciacapitado.getText().trim()));
            }catch(Exception e2)
            {
               SihicApp.s_factura.setDiferenciacapitado(BigDecimal.ZERO); 
            }
            SihicApp.s_factura.calculardiferenciacapitado();
            try {
                guardarFactura();
            } catch (ParseException ex) {
                Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        cb_credito = new CheckBox();
        la_credito = new Label("Crédito: ");
        la_credito.setMinWidth(70);
        cb_credito.setStyle("-fx-text-fill: white;");
          variosprocedimientos.setStyle("-fx-text-fill: white;");
        la_fechexpedicion = new Label("Fecha Expedición");
        la_fechexpiracion = new Label("Fecha Expiración");
        la_adquiriente = new Label("Adquiriente Servicio");
        la_nitccadquiriente = new Label("Nit o Cc");
        la_prestoservicio = new Label("Prestador de Servicios");
        la_nitprestoservicio = new Label("Nit o CC");
        la_totalvalorcuota = new Label("Total Cuota Moderadora:");
        la_valorcuota = new Label("Cuota Moderadora:");
        la_totalcopagos = new Label("Total Copagos:");
        totalcopagos = new TextField();
        prestoservicio = new TextField();
        prestoservicio.setEditable(false);
        nitprestoservicio = new TextField();
        nitprestoservicio.setEditable(false);
        la_usuarioservicio = new Label("Usuario Recibio Atención");
        la_nitusuarioservicio = new Label("Nit o CC");
        usuarioservicio = new TextField();
        nitusuarioservicio = new TextField();
        
        L_Subtotal = new Label("Subtotal: ");
        L_Iva = new Label("Iva: ");
        L_Total = new Label("Total: ");
        L_DescuentoFactura = new Label("Descuento: ");
        L_ValorRecibido = new Label("Vlr Recibido: ");
        message = new Label();
        message.getStyleClass().add("message");
        message.setText("Error en la Conexión del Servidor Base de Datos");
        message.setOpacity(0);
        message.setMinWidth(240);
        L_ValorRecibido.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        L_ValorCambio = new Label("Vlr Cambio: ");
        L_ValorCambio.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        success = new Label();
        //Texto configuracion
        T_NoFactura = new Text("FACTURA DE VENTA\n");
        T_NoFactura.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_NoFactura.setFont(Font.font("ARIAL", FontWeight.BOLD, 14));
        T_NoFactura.setFill(Color.WHITE);
        T_Total = new Text("TOTAL");
        T_Total.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_Total.setFont(Font.font("ARIAL", FontWeight.BOLD, 30));
        T_Total.setFill(Color.WHITE);
        H_Botones = new HBox();
        H_Botones.setSpacing(5);//espacio verticalmente entre objetos
        H_Botones.getChildren().addAll(B_Save,bu_nuevo,B_Print, bu_printcomprobante,B_Pdffacturaglobal,bu_facturacapitado,bu_detalles, bu_furisp,la_variosprocedimientos,variosprocedimientos,tf_variosprocedimientos);
        H_Botonesnav = new HBox();
        H_Botonesnav.setSpacing(10);//espacio verticalmente entre objetos
        GridPane.setHalignment(T_NoFactura, HPos.CENTER);
        Gp_DatosCliente.setVgap(1);//espacio verticalmente dentro de gridpane
        Gp_DatosCliente.setAlignment(Pos.CENTER_LEFT);
        //GridPane.setColumnSpan(T_Total,2);
        GridPane.setRowSpan(T_Total, 3);
        GridPane.setValignment(Gp_DatosCliente, VPos.TOP);//alinea verticalmente
        GridPane.setValignment(T_Total, VPos.TOP);
        GridPane.setHalignment(T_Total, HPos.CENTER);
        Gp_DatosCliente.setHgap(2);
        Gp_Factura.setHgap(2);
        Gp_Factura.setVgap(2);
        //Traemos existencias
      
   
        //  Gp_Factura.add(root, 0, 0,10,1);
        Gp_Total.add(T_NoFactura, 2, 0,3,1);
        GridPane.setValignment(T_NoFactura, VPos.TOP);
        GridPane.setHalignment(T_NoFactura, HPos.RIGHT);
        GridPane.setValignment(la_nitccadquiriente, VPos.BOTTOM);
        GridPane.setValignment(la_adquiriente, VPos.BOTTOM);
        Gp_DatosCliente.add(la_adquiriente, 0, 1,1,1);
        Gp_DatosCliente.add(la_nitccadquiriente, 1, 1,1,1);
        Gp_DatosCliente.add(adquiriente, 0, 2,1,1);
        Gp_DatosCliente.add(nitccadquiriente, 1, 2,1,1);
        Gp_DatosCliente.add(la_prestoservicio, 0, 3,1,1);
        Gp_DatosCliente.add(la_nitprestoservicio, 1, 3,1,1);
        Gp_DatosCliente.add(prestoservicio, 0, 4,1,1);
        Gp_DatosCliente.add(nitprestoservicio, 1, 4,1,1);
        Gp_DatosCliente.add(la_fechexpedicion, 0, 5,1,1);
        Gp_DatosCliente.add(la_fechexpiracion, 1, 5,1,1);
        Gp_DatosCliente.add(fechaexpedicion, 0, 6,1,1);
        Gp_DatosCliente.add(fechaexpiracion, 1, 6,1,1);
        
        Gp_Total.add(T_Total, 0, 1,1,1);
        Gp_Factura.add(Gp_Total, 1, 0,1,2);
        Gp_Factura.add(Gp_DatosCliente, 0, 0,1,1);
        Gp_Factura.add(new HBox(2,la_eapb,la_prefijos,la_tarifas,la_convenios,la_tipousuario), 0, 4,2,1);
        Gp_Factura.add(new HBox(2,sb_eapb,LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal,tarifas,sb_convenios,sb_tipousuario), 0, 5,2,1);
    
        Gp_Factura.add(Gp_FacturaItems, 0, 6, 2, 1);

        Rectangle dragger = new Rectangle(730, 100);
        dragger.setArcHeight(20);
        dragger.setArcWidth(20);
        //fill the dragger with some nice radial background
        gradient2 = new LinearGradient(0, 0, 0, 0.5, true, CycleMethod.REFLECT, new Stop[]{
            new Stop(0, Color.BLACK),
            new Stop(0.1, Color.BLACK),
            new Stop(1, Color.BLACK)
        });
        dragger.setFill(gradient2);
        //creamos el led de 7 segmentos
        Gr_NumeroDigital = new Group();
        // background image
        // digital clock
        numerodigital = new NumeroDigital(Color.ORANGERED, Color.rgb(50, 50, 50), "0.00");
        numerodigital.setLayoutX(10);
        numerodigital.setLayoutY(5);
        numerodigital.getTransforms().add(new Scale(0.80f, 0.80f, 0, 0));
        // add background and clock to sample
        Gr_NumeroDigital.getChildren().addAll(dragger, numerodigital);
     
Ta_FacturaItems.setOnKeyPressed(e->{
    if(e.getCode()==e.getCode().DELETE)
    {
        try {
            removeitem();
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
});
        GridPane.setHalignment(Gr_NumeroDigital, HPos.RIGHT);
        GridPane.setValignment(numerodigital, VPos.CENTER);
        Gp_Total.add(Gr_NumeroDigital, 0, 4, 4, 3);
        Gp_Factura.setAlignment(Pos.TOP_CENTER);

        Gp_FacturaItems.add(Ta_FacturaItems, 0, 0, 2    , 1);
        Gp_Factura.add(sp_eapb, 0, 6, 2, 1);
      
        Gp_Factura.add(sp_tipousuario, 1, 6, 1, 1);
        // GridPane.setColumnSpan(Gp_Totales,1);
        GridPane.setHalignment(Gp_Totales, HPos.LEFT);
        Gp_Totales.setVgap(3);

       
        //Gp_Totales.setAlignment(Pos.CENTER_RIGHT);
        Gp_Totales.add(L_Subtotal, 3, 1);
        Gp_Totales.add(Tf_Subtotal, 5, 1);
        Gp_Totales.add(L_Iva, 3, 2);
        Gp_Totales.add(Tf_Iva, 5, 2);
        Gp_Totales.add(L_DescuentoFactura, 3, 3);
        Gp_Totales.add(Tf_DescuentoFactura, 5, 3);
        Gp_Totales.add(L_Total, 3, 4);
        Gp_Totales.add(Tf_Total, 5, 4);
        Gp_Totales.add(la_totalvalorcuota, 3, 5);
        Gp_Totales.add(totalvalorcuota, 5, 5);
        Gp_Totales.add(la_totalcopagos, 3, 6);
        Gp_Totales.add(totalcopagos, 5, 6);
        Gp_Totales.add(la_diferenciacapitado, 3, 7);
        Gp_Totales.add(diferenciacapitado, 5, 7);

        Gp_FacturaItems.add(Gp_Totales, 1, 1, 1, 4);
        GridPane.setValignment(H_Botones, VPos.TOP);
        GridPane.setHalignment(Gp_Totales, HPos.RIGHT);
        Gp_Totales.alignmentProperty().setValue(Pos.TOP_RIGHT);
        Gp_FacturaItems.add(H_Botones, 0, 1,1,1);
        HBox hBox_vercompleto=new HBox(la_vercompletoprocedimiento,vervompletoprocedimientp,la_fechaespecifica,tf_fechaespecifica);
        vervompletoprocedimientp.getStyleClass().add("checkbox");
        hBox_vercompleto.setSpacing(4);
        Gp_FacturaItems.add(hBox_vercompleto, 0, 2,1,1);
        Gp_FacturaItems.add(message, 1, 1);
        Gp_FacturaItems.add(sp_producto, 3, 0, 2, 4);
        H_Botones.setAlignment(Pos.BOTTOM_LEFT);
        Gp_FacturaItems.setVgap(7);
        Gp_FacturaItems.setHgap(3);
        Ta_FacturaItems.setMaxHeight(Gp_Factura.getLayoutBounds().getHeight() * 0.15);
        Ta_FacturaItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Gp_Factura.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Factura.getStyleClass().add("background");
         Gp_Message.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Message.getStyleClass().add("background");
        Gp_FacturaItems.setAlignment(Pos.TOP_LEFT);
        changeColumns();
        generarVlrCambio();
        crearoeditar();
        
        stack.getChildren().addAll(Gp_Factura, Gp_Message);
        stack.setAlignment(Pos.TOP_CENTER);

    }

    public void nuevaFactura() {
        removeGp_VlrRecibido();
        enabledAllTextField();
         T_NoFactura.setText("FACTURA DE VENTA\n" + "000");
        Ol_FacturaItems.clear();
        sb_producto.setText("");
        Tf_CodigoCups.setText("0");
        Tf_Subtotal.setText("0");
        Tf_Iva.setText("0");
        Tf_Total.setText("0.00");
        Gr_NumeroDigital.getChildren().remove(numerodigital);
        numerodigital = null;
        numerodigital = new NumeroDigital(Color.ORANGERED, Color.rgb(50, 50, 50), "0.00");
        numerodigital.setLayoutX(10);
        numerodigital.setLayoutY(5);
        numerodigital.getTransforms().add(new Scale(0.80f, 0.80f, 0, 0));
        Gr_NumeroDigital.getChildren().add(numerodigital);
        Tf_ValorRecibido.setText("0");
        sb_producto.requestFocus();
        Tf_Subtotal.setEditable(false);
        Tf_Iva.setEditable(false);
        Tf_Total.setEditable(false);
        fechaexpedicion.setEditable(false);
        valorcuota.setText("0");
        valorcopagos.setText("0");
       
        SihicApp.s_factura=null;
        SihicApp.s_factura=new Factura();
        SihicApp.genConvenios=null;
         SihicApp.genConvenios=new GenConvenios();
          SihicApp.s_genEapb=null;
         SihicApp.s_genEapb=new GenEapb();
          SihicApp.genTiposUsuarios=null;
         SihicApp.genTiposUsuarios=new GenTiposUsuarios();
         sb_convenios.setText("");
         sb_eapb.setText("");
         sb_tipousuario.setText("");
         adquiriente.setText("");
         nitccadquiriente.setText("");
         prestoservicio.setText("");
         nitprestoservicio.setText("");
         fechaexpedicion.setValue(LocalDate.now());
         fechaexpiracion.setValue(LocalDate.now());
         
         
        
        
      
    }

    private  void guardarFactura() throws ParseException, InterruptedException, InterruptedException, InterruptedException {
     
      
        if (setdatosafactura())
        {
         Auditoria auditoria=new Auditoria();
         boolean nueva=true;
                           if(SihicApp.s_factura.getId()!=null)
                           {
                              nueva=false; 
                           }
        System.out.println("Guardando antes de createfactura");                  
        procesocorrecto = facturaControllerClient.crearFactura(SihicApp.s_factura);
        System.out.println("Guardando");
        if(procesocorrecto)
                         {
                             
                          SihicApp.s_factura.calculateTotals();
                          // save_comprobante_diario();   
                         
                          getDatosFactura();
                          L_Message.setText("Factura guardada con exito");
                          L_Message.getStyleClass().set(0,"labelMessage");
                          mensajes_procesos();
                           auditoria.setFechasuceso(new Date());
                           auditoria.setUsuarios(SihicApp.usuarios);
                           auditoria.setModulo("FACTURA");
                           if(nueva)
                           {
                           auditoria.setTipo_proceso("1");
                           auditoria.setDescripcion("CREO NUEVA FACTURA N°: "+ SihicApp.s_factura.getNofacturacerosizquierda());
                           }
                           else
                           {
                                    auditoria.setTipo_proceso("2");
                                     auditoria.setDescripcion("MODIFICO FACTURA N°: "+ SihicApp.s_factura.getNofacturacerosizquierda());
                   
                           }
                             AuditoriaController.create(auditoria);
                                 
                         }
                     else {

                            L_Message.setText("Error al guardar la factura");
                            L_Message.getStyleClass().set(0,"labelError");
                            mensajes_procesos();
                            
                        }
        Tf_Subtotal.setText(SihicApp.s_factura.getNetAmount().toString());
        Tf_Iva.setText(SihicApp.s_factura.getIva().toString());
        Tf_Total.setText(SihicApp.s_factura.getTotalAmount().toString());
        totalvalorcuota.setText(SihicApp.s_factura.getTotalcuotasmoderadoras().toString());
        }
    }

    Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {

            //stage finproduct
            findProduct.loadservicioskardex();
            // Return null at the end of a Task of type Void
            return null;
        }
    };

    public static void addProduct() {
         boolean check_ifexists=false;
       // if(facturaitemcontrollerclient.check_ifexistsnoautorizacion(SihicApp.facturaItem.getNoautorizacion())  || check_ifexists)
         
       if(true){
                   
                  
        try {
         
            SihicApp.conComprobanteProcedimiento = null;
            SihicApp.conComprobanteProcedimiento = new ConComprobanteProcedimiento();
            if (SihicApp.facturaItem!= null) {
                
               if (SihicApp.facturaItem.getId()!= null) 
               { 
                check_ifexists=true;
                SihicApp.conComprobanteProcedimiento = SihicApp.facturaItem.getConComprobanteProcedimiento();
                if(SihicApp.conComprobanteProcedimiento.getConsecutivoComprobanteProcedimiento()==null)
                {
                    SihicApp.conComprobanteProcedimiento=new ConComprobanteProcedimiento();
                    SihicApp.conComprobanteProcedimiento.setFechaelaboracion(SihicApp.agnCitasTemp.getFechaHora());
                    facturaControllerClient.saveComprobante(SihicApp.conComprobanteProcedimiento);
                }
                SihicApp.s_factura.removerinsumosymedicamentos(SihicApp.conComprobanteProcedimiento);
                 procesocorrecto= facturaControllerClient.crearFactura(SihicApp.s_factura);
                 if(procesocorrecto)
                         {
                          L_Message.setText("Eliminado los items para ser actualizado con el nuevo procedimiento");
                          L_Message.getStyleClass().set(0,"labelMessage");
                          mensajes_procesos();
                         }
                     else {

                            L_Message.setText("Error en el Proceso de eliminacion");
                            L_Message.getStyleClass().set(0,"labelError");
                            mensajes_procesos();
                            deleteItemssinid();
                        }
               }
               else
               {
                    SihicApp.conComprobanteProcedimiento.setFechaelaboracion(SihicApp.agnCitasTemp.getFechaHora());
                    facturaControllerClient.saveComprobante(SihicApp.conComprobanteProcedimiento);
               }

            }
            else
            {
                SihicApp.conComprobanteProcedimiento.setFechaelaboracion(SihicApp.agnCitasTemp.getFechaHora());
                facturaControllerClient.saveComprobante(SihicApp.conComprobanteProcedimiento);
            }
           
          
            BigDecimal copago = BigDecimal.ZERO;
           
            BigDecimal tarifa = BigDecimal.ZERO;
            switch (tarifas.getSelectionModel().getSelectedIndex()) {
                case 0:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2001();
                    break;
                case 1:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2015();
                    break;
                case 2:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2016();
                    break;
                case 3:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2017();
                    break;
                case 4:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2018();
                    break;
                    case 5:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2001_soat();
                    break;
                case 6:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2015_soat();
                    break;
                case 7:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2016_soat();
                    break;
                case 8:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2017_soat();
                    break;
                case 9:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2018_soat();
                    break;  
                 case 10:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2020();
                    break;
                case 11:
                    tarifa = SihicApp.facturaItem.getProducto().getPrecio2020_soat();
                    break;    
            }
            if (tarifa != null) {
                if (tarifa.toBigIntegerExact().floatValue() > 0.0) 
                {
                    float porcentajeconvenio=0;
                    if(SihicApp.s_factura.getGenConvenios()!=null)
                    {
                     if(SihicApp.s_factura.getGenConvenios().getId()!=null)
                    {   
                      porcentajeconvenio=SihicApp.s_factura.getGenConvenios().getPorcentajedescuento();
                    }
                    }
                       SihicApp.s_factura.addProductV2(SihicApp.facturaItem.getId(),SihicApp.facturaItem.getCopago(),SihicApp.facturaItem.getCuotamoderadora() , SihicApp.s_kardex.getProducto(),SihicApp.facturaItem.getQuantity(), BigDecimal.ZERO, BigDecimal.ZERO,SihicApp.s_factura.getPorcentajedescuento(), SihicApp.agnCitasTemp, SihicApp.conComprobanteProcedimiento, tarifa,SihicApp.facturaItem.getDiagnosticoprincipal(),SihicApp.facturaItem.getDiagnosticoingreso(),SihicApp.facturaItem.getDiagnosticosecundario(),SihicApp.facturaItem.getTipodiagnostico(),SihicApp.facturaItem.getTipocuenta(),SihicApp.facturaItem.getClasificacionorigen(),SihicApp.facturaItem.getNoautorizacion(),SihicApp.facturaItem.getSucursales(),SihicApp.facturaItem.getGenEapb(),SihicApp.facturaItem.getDiscapacidad(),SihicApp.facturaItem.getEstadopaciente(),SihicApp.facturaItem.getTipoatencion());
                       if(variosprocedimientos.isSelected())
                    {
                      li_variosprocedimientos=tf_variosprocedimientos.getText().split("-");
                      System.out.println("size->"+li_variosprocedimientos.length    );
                       for(int i=0;i<li_variosprocedimientos.length;i++)
                       {
                           final String codigo=li_variosprocedimientos[i];
                           try
                           {
                               System.out.println("Codigos->"+codigo);
                               SihicApp.productoControllerClient.li_findproductosKardex(codigo);
                           Kardex k= SihicApp.li_productosKardex.stream().filter(f->f.getProducto().getCodigo().equals(codigo.trim())).collect(Collectors.toList()).get(0);
                          switch (tarifas.getSelectionModel().getSelectedIndex()) {
                case 0:
                    tarifa = k.getProducto().getPrecio2001();
                    break;
                case 1:
                    tarifa = k.getProducto().getPrecio2015();
                    break;
                case 2:
                    tarifa = k.getProducto().getPrecio2016();
                    break;
                case 3:
                    tarifa = k.getProducto().getPrecio2017();
                    break;
                case 4:
                    tarifa = k.getProducto().getPrecio2018();
                    break;
                 case 5:
                    tarifa = k.getProducto().getPrecio2001_soat();
                    break;
                case 6:
                    tarifa = k.getProducto().getPrecio2015_soat();
                    break;
                case 7:
                    tarifa = k.getProducto().getPrecio2016_soat();
                    break;
                case 8:
                    tarifa = k.getProducto().getPrecio2017_soat();
                    break;
                case 9:
                    tarifa = k.getProducto().getPrecio2018_soat();
                    break;    
               case 10:
                    tarifa = k.getProducto().getPrecio2020();
                    break; 
               case 11:
                    tarifa = k.getProducto().getPrecio2020_soat();
                    break;      
            }
                           SihicApp.s_factura.addProductV2(null,new BigDecimal(BigInteger.ZERO),new BigDecimal(BigInteger.ZERO) , k.getProducto(),SihicApp.facturaItem.getQuantity(), BigDecimal.ZERO, BigDecimal.ZERO,SihicApp.s_factura.getPorcentajedescuento(), SihicApp.agnCitasTemp, SihicApp.conComprobanteProcedimiento, tarifa,SihicApp.facturaItem.getDiagnosticoprincipal(),SihicApp.facturaItem.getDiagnosticoingreso(),SihicApp.facturaItem.getDiagnosticosecundario(),SihicApp.facturaItem.getTipodiagnostico(),SihicApp.facturaItem.getTipocuenta(),SihicApp.facturaItem.getClasificacionorigen(),SihicApp.facturaItem.getNoautorizacion(),SihicApp.facturaItem.getSucursales(),SihicApp.facturaItem.getGenEapb(),SihicApp.facturaItem.getDiscapacidad(),SihicApp.facturaItem.getEstadopaciente(),SihicApp.facturaItem.getTipoatencion());
                           }
                           catch(Exception e)
                           {
                               e.printStackTrace();
                           }
                       }   
                    }
                           System.out.println(" codigo proc->"+ SihicApp.s_kardex.getProducto().getCodigo());
                   
                    for (ProductoMedicamentos pm : SihicApp.s_kardex.getProducto().getMedicamentosItems()) {
                        if(pm.getMedicamento()!=null  )
                        {    
                        if (pm.getMedicamento().getPrecio2018() != null) {
                            if (pm.getMedicamento().getPrecio2018().toBigIntegerExact().floatValue() > 0.0) {
                              
                                SihicApp.s_factura.addProductV2(null,new BigDecimal(BigInteger.ZERO), new BigDecimal(BigInteger.ZERO), pm.getMedicamento(), pm.getCantidad(), BigDecimal.ZERO, BigDecimal.ZERO, SihicApp.s_factura.getPorcentajedescuento(), SihicApp.agnCitasTemp, SihicApp.conComprobanteProcedimiento, tarifa,SihicApp.facturaItem.getDiagnosticoprincipal(),SihicApp.facturaItem.getDiagnosticoingreso(),SihicApp.facturaItem.getDiagnosticosecundario(),SihicApp.facturaItem.getTipodiagnostico(),SihicApp.facturaItem.getTipocuenta(),SihicApp.facturaItem.getClasificacionorigen(),SihicApp.facturaItem.getNoautorizacion(),SihicApp.facturaItem.getSucursales(),SihicApp.facturaItem.getGenEapb(),SihicApp.facturaItem.getDiscapacidad(),SihicApp.facturaItem.getEstadopaciente(),SihicApp.facturaItem.getTipoatencion());
                            } else {
                                alert.showAndWait();
                            }
                        } else {
                            alert.showAndWait();
                        }
                    }
                    }
                    if (SihicApp.s_factura.getPrefijo().equals("E") || SihicApp.s_factura.getPrefijo().equals("T")) {
                        SihicApp.s_factura.setGenPersonas(SihicApp.agnCitasTemp.getGenPacientes().getGenPersonas());

                    }

                   
                    try {

                      procesocorrecto= facturaControllerClient.crearFactura(SihicApp.s_factura);
                         if(SihicApp.s_factura.getPrefijo().substring(0,1).equals("E") || SihicApp.s_factura.getPrefijo().substring(0,1).equals("T"))
                         {
                           // MovimientoCuentas.colocarasientoscontables(jenum.EnumTiposComprobantes.VENTAS3.ordinal());
                         }
                         if(procesocorrecto)
                         {
                          L_Message.setText("Procedimiento Agregado");
                          L_Message.getStyleClass().set(0,"labelMessage");
                          mensajes_procesos();
                         }
                     else {

                            L_Message.setText("Error en el Proceso de Agregar Procedimiento");
                            L_Message.getStyleClass().set(0,"labelError");
                            mensajes_procesos();
                            deleteItemssinid();
                        }
                        getDatosFactura(); 
                        SihicApp.facturaItem = SihicApp.s_factura.getFacturaItems().get(SihicApp.s_factura.getFacturaItems().size() - 1);
                       
                     
                        Tf_Cantidad.setText("0");
                        Tf_CodigoCups.setText("0");
                        sb_producto.setText("");
                    } catch (Exception e) {

                        e.printStackTrace();

                    }
                } else {
                    alert.showAndWait();
                }
            } else {
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
                   }
         else
                   {
                        alert.getDialogPane().setContentText("Número de autorización Existe");
                        alert.showAndWait();
       
                   }
    }

    public static void deleteItemssinid() {
       
       
       
            SihicApp.s_factura.removeitemssinid();
           

        
     

    }

    public void modifyItem(String quantity) {
        FacturaItem fi = (FacturaItem) Ta_FacturaItems.getFocusModel().getFocusedItem();
        SihicApp.s_factura.modifyItem(fi, Float.valueOf(quantity.trim()));
        facturaControllerClient.crearFactura(SihicApp.s_factura);
        getDatosFactura();
    }

    public void changeColumns() {
         Tc_eapb = new TableColumn();
        Tc_eapb.setText("Eapb/Tipo USuario");
        Tc_eapb.setMinWidth(250);
        Tc_eapb.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                try{
                return new SimpleStringProperty(facturaitem.getValue().getGenPacientes().getGenEapb().getNombre()+"||"+facturaitem.getValue().getGenPacientes().getGenTiposUsuarios().getNombre());
                }catch(Exception e)
                {
                    return new SimpleStringProperty("N/A");
               
                }
            }
        });
      /*  Tc_eapb.setCellFactory(column -> {
    return new TableCell<FacturaItem, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            System.out.println("item->"+item);
            if(item == null || empty) 
            {
                   setText("");
                    setStyle("");
            }
            else
            { 
             if(SihicApp.s_factura.getPrefijo().substring(0,1).equals("A") || SihicApp.s_factura.getPrefijo().substring(0,1).equals("P"))
              {    
             if((!item.equals(SihicApp.s_factura.getGenEapb().getNombre()+"||"+SihicApp.s_factura.getGenTiposUsuarios().getNombre())) && SihicApp.s_factura.getPrefijo().substring(0,1).equals("A"))
             {
               setTextFill(Color.WHITE);
               setStyle("-fx-background-color: green;-fx-text-fill: white;");
               setText(item);     
              }
             else
             {
               setStyle("");
               setText(item); 
             }
              }
              else
             {
               setStyle("");
               setText(item); 
             }
               }
            }
        };
    }
);*/
        Tc_NoIdent = new TableColumn();
        Tc_NoIdent.setText("N° Ident");
        Tc_NoIdent.setMinWidth(150);
        Tc_NoIdent.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                try{
                return new SimpleStringProperty(facturaitem.getValue().getGenPacientes().getGenPersonas().getDocumento());
                }catch(Exception e)
                {
                      return new SimpleStringProperty(facturaitem.getValue().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
            
                }
            }
        });
        
        Tc_Paciente = new TableColumn();
        Tc_Paciente.setText("Paciente");
        Tc_Paciente.setMinWidth(300);
        Tc_Paciente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                try{
                return new SimpleStringProperty(facturaitem.getValue().getGenPacientes().getGenPersonas().getNombres());
                   }catch(Exception e)
                {
                      return new SimpleStringProperty(facturaitem.getValue().getAgnCitas().getGenPacientes().getGenPersonas().getNombres());
            
                }
            }
        });
        Tc_NoItem = new TableColumn();
        Tc_NoItem.setText("No Comp");
        Tc_NoItem.setMinWidth(100);
        Tc_NoItem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
              try{
                return new SimpleStringProperty(facturaitem.getValue().getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString());
              }catch(Exception e)
              {
                   return new SimpleStringProperty("");
              }
            }
        });
        Tc_CodServicio = new TableColumn();
        Tc_CodServicio.setText("Cod. Servicio");
        Tc_CodServicio.setMinWidth(100);
        Tc_CodServicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                try{
                return new SimpleStringProperty(facturaitem.getValue().getProducto().getCodigo());
                }catch(Exception e)
                {
                     return new SimpleStringProperty(""); 
                }
            }
        });
        Tc_CodCups = new TableColumn();
        Tc_CodCups.setText("Cod. Cups");
        Tc_CodCups.setMinWidth(100);
        Tc_CodCups.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getProducto().getHclCups() != null ? facturaitem.getValue().getProducto().getHclCups().getCodigo() : "");
            }
        });
        Tc_Producto = new TableColumn();
        Tc_Producto.setText("Detalle");
        Tc_Producto.setMinWidth(200);
        Tc_Producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                try{
                return new SimpleStringProperty(facturaitem.getValue().getProducto().getNombre());
                }catch(Exception e)
                {
                     return new SimpleStringProperty(""); 
                }
            }
        });

        Tc_Cantidad = new TableColumn();
        Tc_Cantidad.setText("Cantidad");
        Tc_Cantidad.setCellValueFactory(new PropertyValueFactory("quantity"));
        Tc_Cantidad.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        Tc_Cantidad.setMinWidth(150);
        Tc_Descuento = new TableColumn();
        Tc_Descuento.setText("Descuento");
        Tc_Descuento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(String.valueOf(facturaitem.getValue().getDescuento()) + "(%" + String.valueOf(facturaitem.getValue().getProducto().getCodigo().equals("1") ? porcentaje : 0) + ")");
            }
        });
        Tc_ValorU = new TableColumn();
        Tc_ValorU.setText("Valor/Total");
        Tc_ValorU.setMinWidth(150);
        Tc_ValorU.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getValor_total().toString());
            }
        });

        Tc_ValorT = new TableColumn();
        Tc_ValorT.setText("Valor Total");
        Tc_ValorT.setCellValueFactory(new PropertyValueFactory("valor_total"));

        Ta_FacturaItems.getColumns().clear();
        Ta_FacturaItems.setEditable(true);
        Tc_NoItem.setMinWidth(100);
        Tc_NoIdent.setMinWidth(150);
        Tc_Paciente.setMinWidth(200);
        Tc_CodServicio.setMinWidth(150);
        Tc_CodCups.setMinWidth(100);
        Tc_Producto.setMinWidth(200);
        Tc_Cantidad.setMinWidth(100);
        Tc_ValorU.setMinWidth(100);
        Tc_ValorT.setMinWidth(150);
        Ta_FacturaItems.getColumns().addAll(Tc_NoItem,Tc_eapb, Tc_NoIdent, Tc_Paciente, Tc_CodServicio, Tc_Producto, Tc_Cantidad, Tc_ValorU, Tc_ValorT);
        //Ancho de tabla y ancho de porcentaje de columnas

        // Ta_FacturaItems.setMinWidth(Gp_Factura.getLayoutBounds().getWidth()+5-(Gp_Factura.getLayoutBounds().getWidth()*0.25));
        Ta_FacturaItems.setMinHeight(215);
        Ta_FacturaItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Gp_Factura.setMinWidth(1340);
        Gp_Factura.setMaxWidth(1340);
        Gp_FacturaItems.setMinWidth(1245);
        Gp_FacturaItems.setMaxWidth(1245);
        Ta_FacturaItems.setMinWidth(1235);
        Ta_FacturaItems.setMaxWidth(1235);
    }

    public void disabledAllTextField() {
        Ta_FacturaItems.setDisable(true);
        for (Object n : Gp_Factura.getChildren().toArray()) {
            if (n.getClass() == GridPane.class) {
                GridPane gp_gen = (GridPane) n;
                for (Object nh : gp_gen.getChildren().toArray()) {
                    if (nh.getClass() == VBox.class) {
                        VBox v_gen = (VBox) nh;
                        for (Object nhv : v_gen.getChildren().toArray()) {
                            if (nhv.getClass() == RadioButton.class) {
                                RadioButton rb_gen = (RadioButton) nhv;
                                rb_gen.setDisable(true);
                            }
                        }
                    }
                    if (nh.getClass() == TextField.class) {
                        TextField tf_gen = (TextField) nh;
                        tf_gen.setDisable(true);
                    }

                }
            }
        }
        for (Object n : Gp_Totales.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField tf_gen = (TextField) n;
                tf_gen.setDisable(true);
            }
        }
    }

    public void enabledAllTextField() {
        Ta_FacturaItems.setDisable(false);
        for (Object n : Gp_Factura.getChildren().toArray()) {
            if (n.getClass() == GridPane.class) {
                GridPane gp_gen = (GridPane) n;
                for (Object nh : gp_gen.getChildren().toArray()) {
                    if (nh.getClass() == VBox.class) {
                        VBox v_gen = (VBox) nh;
                        for (Object nhv : v_gen.getChildren().toArray()) {
                            if (nhv.getClass() == RadioButton.class) {
                                RadioButton rb_gen = (RadioButton) nhv;
                                rb_gen.setDisable(false);
                            }
                        }
                    }
                    if (nh.getClass() == TextField.class) {
                        TextField tf_gen = (TextField) nh;
                        tf_gen.setDisable(false);
                    }

                }
            }
        }
        for (Object n : Gp_Totales.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField tf_gen = (TextField) n;
                tf_gen.setDisable(false);
            }
        }
    }

    public void findByCodigo() {

        findProduct.setKardex((productoControllerClient.findByCodigoLastKardex(Tf_CodigoCups.getText().trim())));
        if (findProduct.getKardex() != null) {
            Tf_Cantidad.requestFocus();
            sp_producto.getSearchBox().setText(findProduct.getKardex().getProducto().getNombre());
            SihicApp.s_kardex = findProduct.getKardex();
            sp_producto.hide();
        }
    }

    public void generarVlrCambio() {
        Rectangle dragger = new Rectangle(330, 55);
        dragger.setArcHeight(20);
        dragger.setArcWidth(20);
        //fill the dragger with some nice radial background
        gradient2 = new LinearGradient(0, 0, 0, 0.5, true, CycleMethod.REFLECT, new Stop[]{
            new Stop(0, Color.BLACK),
            new Stop(0.1, Color.BLACK),
            new Stop(1, Color.BLACK)
        });
        dragger.setFill(gradient2);
        Gr_ValorCambio = new Group();
        // background image
        // digital clock
        Nd_ValorCambio = new NumeroDigital(Color.LIGHTGREEN, Color.rgb(50, 50, 50), "0.00");
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));
        // add background and clock to sample
        Gr_ValorCambio.getChildren().addAll(dragger, Nd_ValorCambio);
        GridPane.setRowSpan(Gp_VlrRecibido, 2);
        GridPane.setColumnSpan(Gp_VlrRecibido, 5);
        GridPane.setHalignment(Gp_VlrRecibido, HPos.RIGHT);
        Gp_VlrRecibido.setAlignment(Pos.CENTER);

        Gp_VlrRecibido.setVgap(5);
        Gp_VlrRecibido.setHgap(5);
        Gp_VlrRecibido.add(L_ValorRecibido, 0, 0);
        Gp_VlrRecibido.add(Tf_ValorRecibido, 1, 0);
        Gp_VlrRecibido.add(L_ValorCambio, 0, 1);
        Gp_VlrRecibido.add(Gr_ValorCambio, 1, 1);
        Gp_VlrRecibido.getStyleClass().add("solucion-gridpane-background");

    }

    public void addGp_VlrRecibido() {

        // add background and clock to sample
        if (Gp_FacturaItems.getChildren().indexOf(Gp_VlrRecibido) == -1) {
            Gp_FacturaItems.add(Gp_VlrRecibido, 0, 1);
            Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
            Nd_ValorCambio = null;
            Nd_ValorCambio = new NumeroDigital(Color.LIGHTGREEN, Color.rgb(50, 50, 50), "0.00");
            Nd_ValorCambio.setLayoutX(10);
            Nd_ValorCambio.setLayoutY(5);
            Nd_ValorCambio.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));

            Gr_ValorCambio.getChildren().add(Nd_ValorCambio);

        }

    }

    public void removeGp_VlrRecibido() {
        if (Gp_FacturaItems.getChildren().indexOf(Gp_VlrRecibido) != -1) {
            Gp_FacturaItems.getChildren().remove(Gp_VlrRecibido);
        }
    }

    public void showVlrCambio() {

        SihicApp.s_factura.setEfectivo(BigDecimal.valueOf(Double.parseDouble(Tf_ValorRecibido.getText().trim())));
        facturaControllerClient.crearFactura(SihicApp.s_factura);
        Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
        Nd_ValorCambio = null;
        Nd_ValorCambio = new NumeroDigital(Color.LIGHTGREEN, Color.rgb(50, 50, 50), SihicApp.s_factura.getDevolver().toString());
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));

        Gr_ValorCambio.getChildren().add(Nd_ValorCambio);
        B_Print.requestFocus();
        cronometroRemoveGp_VlrRecibido();
    }

    public void printFactura(int opc) throws IOException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, IllegalAccessException, NoSuchMethodException, NoSuchMethodException {

        impresionFactura.fprintpdffacturageneral(opc);

    }
     public void printFacturaglobal() throws IOException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, IllegalAccessException, NoSuchMethodException, NoSuchMethodException {

        ImprimirPdf.fprintpdffacturaglobal();

    }
       public void printfacturacapitado() throws IOException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, IllegalAccessException, NoSuchMethodException, NoSuchMethodException {

        ImprimirPdf.fprintpdffacturacapitado();

    }


    private void cronometroRemoveGp_VlrRecibido() {

        FadeTransition ft = new FadeTransition(Duration.millis(5000), Gp_VlrRecibido);
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

    private static void getDatosFactura() {
        impresionFactura = null;
        impresionFactura = new ImpresionFactura(SihicApp.s_factura);
        String cerosizq = "";

        for (int i = 6; i > SihicApp.s_factura.getNo_factura().toString().length(); i--) {
            cerosizq = cerosizq + "0";
        }
        T_NoFactura.setText("FACTURA DE VENTA\n" + SihicApp.s_factura.getNofacturacerosizquierda());
        if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("P") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("S")) {
            try
            {
            adquiriente.setText(SihicApp.s_factura.getGenEapb().getNombre() + "-" + SihicApp.s_factura.getGenEapb().getGenTiposUsuarios().getNombre());
            nitccadquiriente.setText(SihicApp.s_factura.getGenEapb().getNit());
            }catch(Exception e)
            {
                
            }

        } else 
        {  try{
            adquiriente.setText(SihicApp.s_factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getNombres());
            nitccadquiriente.setText(SihicApp.s_factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
        }catch(Exception e)
        {
            try{
            adquiriente.setText(SihicApp.s_factura.getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getNombres());
            nitccadquiriente.setText(SihicApp.s_factura.getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
            }
            catch(Exception e2)
            {
                
            }
        }
        }
        prestoservicio.setText("CENTRO MÉDICO GUAVIARE CMG SAS");
        nitprestoservicio.setText("900767863-6");
        SihicApp.s_genEapb=SihicApp.s_factura.getGenEapb();
        SihicApp.genConvenios=SihicApp.s_factura.getGenConvenios();
         SihicApp.genTiposUsuarios=SihicApp.s_factura.getGenTiposUsuarios();
        if(SihicApp.s_genEapb!=null)
        {
         sb_eapb.setText(SihicApp.s_genEapb.getNombre()+" Código: "+ SihicApp.s_genEapb.getCodigo());
    
        }
       
        try
        {
        sb_convenios.setText(String.valueOf(SihicApp.s_factura.getPorcentajedescuento()));
        }
        catch(Exception e)
        {
               sb_convenios.setText("0");
        }
        
        if(SihicApp.genTiposUsuarios!=null)
        {
        sb_tipousuario.setText(SihicApp.genTiposUsuarios.getNombre());
        }
        sp_eapb.hide();
       
        sp_tipousuario.hide();
        tarifas.getSelectionModel().select(SihicApp.s_factura.getTarifa());
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.setValue(SihicApp.s_factura.getPrefijo());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = df.format(SihicApp.s_factura.getFacturaDate());
        LocalDate ld = LocalDate.parse(fecha);
        fechaexpedicion.setValue(ld);

        fecha = df.format(SihicApp.s_factura.getFechavencimiento());
        ld = LocalDate.parse(fecha);

        fechaexpiracion.setValue(ld);

        if (SihicApp.s_factura.getCredito() != null) {
            cb_credito.setSelected(true);
        }
        Collections.sort(SihicApp.s_factura.getFacturaItems());

        SihicApp.s_factura.calculartotalescopagocuotamoeras();
        O_ListFacturaItems = SihicApp.s_factura.getFacturaItems().toArray();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(O_ListFacturaItems);
        Ta_FacturaItems.setItems(Ol_FacturaItems);
        Tf_Subtotal.setText(SihicApp.s_factura.getNetAmount().toString());
        totalcopagos.setText(SihicApp.s_factura.getTotalcopagos().toString());
        totalvalorcuota.setText(SihicApp.s_factura.getTotalcuotasmoderadoras().toString());
        diferenciacapitado.setText(SihicApp.s_factura.getDiferenciacapitado()!=null?SihicApp.s_factura.getDiferenciacapitado().toString():"0");
        Tf_Iva.setText(SihicApp.s_factura.getIva().toString());
        Tf_Total.setText(SihicApp.s_factura.getTotalAmount().toString());
        totalvalorcuota.setText(SihicApp.s_factura.getTotalcuotasmoderadoras().toString());
        Tf_DescuentoFactura.setText(SihicApp.s_factura.getDescuento().toString());
        Gr_NumeroDigital.getChildren().remove(numerodigital);
        numerodigital = null;
        numerodigital = new NumeroDigital(Color.ORANGERED, Color.rgb(50, 50, 50), Tf_Total.getText().equals("0") ? "0000000000" : Tf_Total.getText());
        numerodigital.setLayoutX(10);
        numerodigital.setLayoutY(5);
        numerodigital.getTransforms().add(new Scale(0.80f, 0.80f, 0, 0));
       

        Gr_NumeroDigital.getChildren().add(numerodigital);
       

    }

    private void setbotonfurisp() {
         bu_furisp.setDisable(true);
          bu_furisp.setVisible(false);
      if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("T")) {
                    bu_furisp.setVisible(true);
                    bu_furisp.setDisable(false);
                }
                
            }
        

    

    private void setcomprobante() 
    {
        if (Ta_FacturaItems.getSelectionModel().getSelectedIndex() != -1) {
            facturaItem = (FacturaItem) Ta_FacturaItems.getSelectionModel().getSelectedItem();
            SihicApp.facturaItem = (FacturaItem) Ta_FacturaItems.getSelectionModel().getSelectedItem();
        }
    }

    private void pdfComprobante() {
        if (facturaItem != null) {
            try {
                impresionFactura.fpdfcomprobanteprocedimiento(facturaItem);
            } catch (IOException | DocumentException ex) {
                ex.printStackTrace();

            }
        }
    }

    public void pdfFactura() throws NoSuchMethodException, IllegalAccessException {

        try {
            printFactura(1);
        } catch (IOException ex) {
            Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public void showfactura() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        sceneFactura = null;
        sceneFactura = new Scene(stack, Color.TRANSPARENT);
        KeyCombination kc = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        Runnable taskKc = () -> pdfComprobante();
        KeyCombination kf = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
        Runnable taskKf = () -> {
            try {
                pdfFactura();
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        sceneFactura.getAccelerators().clear();
        sceneFactura.getAccelerators().put(kc, taskKc);
        sceneFactura.getAccelerators().put(kf, taskKf);

        if (stageFactura.isShowing()) {
            stageFactura.close();
        }

//set scene to stage
        stageFactura.sizeToScene();

        //center stage on screen
        stageFactura.centerOnScreen();
        stageFactura.setScene(sceneFactura);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageFactura.show();
    }

    public void showpdf() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        scenePdfFactura = null;
        scenePdfFactura = new Scene(stack, Color.TRANSPARENT);

        if (stagePdfFactura.isShowing()) {
            stagePdfFactura.close();
        }

//set scene to stage
        stagePdfFactura.sizeToScene();

        //center stage on screen
        stagePdfFactura.centerOnScreen();
        stagePdfFactura.setScene(scenePdfFactura);
        // stagePdfFactura.setMaxHeight(500);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stagePdfFactura.show();
    }

  

    public static Scene getSceneFactura() {
        return sceneFactura;
    }

    public Stage getStageFactura() {
        return stageFactura;
    }

    

    private void showFurisp() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, IllegalArgumentException, InvocationTargetException {
        SihicApp.s_hclfurisp = SihicApp.hclFurispControllerClient.getHclFurisp(SihicApp.s_factura);
        if (SihicApp.s_hclfurisp == null) {
            SihicApp.s_hclfurisp = new HclFurisp();
            SihicApp.s_hclfurisp.setFactura(SihicApp.s_factura);
        }

        appPathFurisp = "sihic.historiasclinicas.historialpaciente.FHclFurisp";
        clzFurisp = Class.forName(appPathFurisp);
        appClassFurisp = clzFurisp.newInstance();
        P_Furisp = null;
        P_Furisp = (Parent) clzFurisp.getMethod("createContent").invoke(appClassFurisp);
        scenefurisp = null;
        scenefurisp = new Scene(P_Furisp, Color.TRANSPARENT);

        if (stageFurisp.isShowing()) {
            stageFurisp.close();
        }

//set scene to stagstageFurispe
        //stageFurisp.sizeToScene();
        stageFurisp.setMaxHeight(600);
        stageFurisp.setMinHeight(600);
        stageFurisp.setMinWidth(900);
        stageFurisp.setMaxWidth(900);
        stageFurisp.setScene(scenefurisp);

        stageFurisp.centerOnScreen();
        //stage.setMaxWidth(550);
        stageFurisp.setX(225);
        stageFurisp.setY(75);
        //show the stage
        stageFurisp.show();
    }

   

   private void showdetalles() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, IllegalArgumentException, InvocationTargetException {
       checkregistroexistente();

      String  appPath = "sihic.contabilidad.documentos.DatosFacturacion";
       Class clz = Class.forName(appPath);
        Object appClass = clz.newInstance();
        Parent P_detalles = null;
            P_detalles = (Parent) clz.getMethod("createContent").invoke(appClass);
        scene = null;
        scene = new Scene(P_detalles, Color.TRANSPARENT);

        if (stagedetalles.isShowing()) {
            stagedetalles.close();
        }

//set scene to stagstageFurispe
        stagedetalles.sizeToScene();
       
        stagedetalles.setScene(scene);

        stagedetalles.centerOnScreen();
      
        
        //show the stage
        stagedetalles.show();
    }

     
    private static void mensajes_procesos() {

        Task_Message = () -> {
            try {
                SetMessage();
            } catch (InterruptedException ex) {
                Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        backgroundThread = new Thread(Task_Message);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);

        // Start the thread
        backgroundThread.start();

    }

    private static void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;
        //ft.setFromValue(0.0);
        // ft.setToValue(1);

    }
 
  
 private void crearoeditar()
 {
     if(SihicApp.s_factura==null)
     {
         nuevaFactura();
     }
     else
     {
        if(SihicApp.s_factura.getId()==null) 
        {
            nuevaFactura();
        }
        else
        {
            getDatosFactura();
         
            
        }
     }
   
 }
  private static boolean setdatosafactura()
  {
      System.out.println("Guardando");  
     SihicApp.s_factura.setTarifa(tarifas.getSelectionModel().getSelectedIndex());
     try{
     SihicApp.s_factura.setPorcentajedescuento(Float.valueOf(sb_convenios.getText().trim()));
     }catch(Exception e)
     {
           SihicApp.s_factura.setPorcentajedescuento(0);
     }
     if(SihicApp.s_factura.getId()==null)
      {
        SihicApp.s_factura.setPrefijo(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString());
      }  
     if(SihicApp.s_factura.getPrefijo().substring(0,1).equals("A")|| SihicApp.s_factura.getPrefijo().substring(0,1).equals("P"))
     {
     if(SihicApp.s_genEapb==null)
     {
         L_Message.setText("Eps o Eapb no debe estar vacio");
         L_Message.getStyleClass().set(0,"labelError");
         mensajes_procesos();
         return false;
     } 
    
     if(SihicApp.s_genEapb.getId()==null)
     {
         L_Message.setText("Eps o Eapb no debe estar vacio");
         L_Message.getStyleClass().set(0,"labelError");
         mensajes_procesos();
         return false;
     }  
     
      if(SihicApp.genTiposUsuarios==null)
     {
         L_Message.setText("Tipos de usuario no debe estar vacio");
         L_Message.getStyleClass().set(0,"labelError");
          mensajes_procesos();
         return false;
     } 
     
     if(SihicApp.genTiposUsuarios.getId()==null)
     {
         L_Message.setText("Tipos de usuario no debe estar vacio");
         L_Message.getStyleClass().set(0,"labelError");
          mensajes_procesos();
         return false;
     }
     }  
     
     
      if(SihicApp.s_genEapb!=null)
      {    
       if(SihicApp.s_genEapb.getId()!=null)
       {
           SihicApp.s_factura.setGenEapb(SihicApp.s_genEapb);
       }
      }
   if(SihicApp.genConvenios!=null)
      {    
       if(SihicApp.genConvenios.getId()!=null)
       {
           SihicApp.s_factura.setGenConvenios(SihicApp.genConvenios);
       }
      }
      if(SihicApp.genTiposUsuarios!=null)
      {    
       if(SihicApp.genTiposUsuarios.getId()!=null)
       {
          SihicApp.s_factura.setGenTiposUsuarios(SihicApp.genTiposUsuarios);
       }
      }
      
      LocalDate localDate = fechaexpiracion.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            SihicApp.s_factura.setFechavencimiento(Date.from(instant));
            SihicApp.s_factura.setFacturaDate(UtilDate.localdatetodate(fechaexpedicion.getValue()));
            if(SihicApp.s_factura.getPrefijo().substring(0, 1).equals("E"))
            {
                SihicApp.s_factura.setFecharadicacion(UtilDate.localdatetodate(fechaexpedicion.getValue()));
                SihicApp.s_factura.setFechaaceptacion(UtilDate.localdatetodate(fechaexpedicion.getValue()));
                SihicApp.s_factura.setNumeroradicado("000");
                SihicApp.s_factura.setAceptada(true);
            }
            SihicApp.s_factura.setDescuento(BigDecimal.valueOf(Double.parseDouble(Tf_DescuentoFactura.getText().trim())));
            System.out.println("Guardando");  
            return true; 
  }
  private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((Ta_FacturaItems.getSelectionModel()) != null) {
            SihicApp.facturaItem = (FacturaItem) Ta_FacturaItems.getSelectionModel().getSelectedItem();
            return true;
        }
        else
        {
            SihicApp.facturaItem=null;
            return false;
        }
  }
  private void removeitem() throws IllegalAccessException, IllegalAccessException 
  {
       try {
           if(checkregistroexistente())
           {
               SihicApp.s_factura.removeProduct(SihicApp.facturaItem);
               facturaControllerClient.crearFactura(SihicApp.s_factura);
               getDatosFactura();
           }} catch (IllegalAccessException ex) {
           Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IllegalArgumentException ex) {
           Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
       } catch (InvocationTargetException ex) {
           Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
       } catch (NoSuchMethodException ex) {
           Logger.getLogger(FacturacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
       }
  }
}
