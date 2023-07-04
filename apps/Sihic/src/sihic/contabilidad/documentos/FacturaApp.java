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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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

import modelo.ConComprobanteProcedimiento;
import modelo.Puc;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.GenPersonas;
import modelo.HclFurisp;
import modelo.ProductoMedicamentos;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import static sihic.SihicApp.facturaitemcontrollerclient;
import sihic.contabilidad.FindServicios;
import sihic.contabilidad.ImpresionFactura;
import sihic.contabilidad.MovimientoCuentas;
import sihic.control.SearchBox;
import sihic.controlador.ConPucControllerClient;
import sihic.controlador.FacturaControllerClient;
import sihic.controlador.ProductoControllerClient;
import sihic.digitos.NumeroDigital;
import sihic.controlador.GenPersonasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.UtilDate;

public class FacturaApp {

    //grupo
    Group Gr_NumeroDigital;
    Group Gr_ValorCambio;
    LinearGradient gradient2;
    //String Converter
    StringConverter<Object> sc;
    //buttons
    private static Button B_Print;
    private static Button B_New;
    private static Button B_Save;
    
   
 
     private static Button B_Pdffacturaglobal;
    private static Button bu_printcomprobante;
    private static Button bu_furisp;
    //Tabla Items de SihicApp.s_factura
    private TableView Ta_FacturaItems;
    //GridPane row and columns
    private GridPane Gp_Factura;
    private GridPane Gp_DatosCliente;
    private GridPane Gp_Total;
    private GridPane Gp_Producto;
    private GridPane Gp_FacturaItems;
    private GridPane Gp_Totales;
    private GridPane Gp_VlrRecibido;
    private Text T_NoFactura;
    private Text T_Total;
    //Etiquetas datos cliente
    private static Label la_adquiriente;
    private static Label la_nitccadquiriente;
    private static Label la_fechexpedicion;
    private static Label la_fechexpiracion;
    private static Label L_Total;
    private static Label L_Iva;
    private static Label L_Subtotal;
    private static Label L_Producto;
    private static Label L_CodigoCups;
    private static Label L_Cantidad;
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
    private NumeroDigital numerodigital;
    private NumeroDigital Nd_ValorCambio;
    //Objetos que contiene los datos de la SihicApp.s_factura
    private ObservableList Ol_FacturaItems = FXCollections.observableArrayList();
    private Object[] O_ListFacturaItems;

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
    private TextField prestoservicio;
    private Label la_nitprestoservicio;
    private TextField nitprestoservicio;
    private Label la_nitusuarioservicio;
    private TextField nitusuarioservicio;
    private Label la_usuarioservicio;
    private TextField usuarioservicio;
    private Label la_credito;
    private ConComprobanteProcedimiento conComprobanteProcedimiento;
    private ImpresionFactura impresionFactura;
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
    private TextField totalcopagos;
    private Label la_valorcopago;
    private TextField valorcopagos;
    private DecimalFormat format = new DecimalFormat("#.0");
    private StackPane stack;

    //************Stage furisp
    private Parent P_Furisp;
    private String appPathFurisp;
    private Class clzFurisp;
    private Object appClassFurisp;
    private Stage stageFurisp = new Stage(StageStyle.DECORATED);
    public static Scene scenefurisp = null;
    private Label message;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private String codigo = "";
    private GridPane Gp_Message;
    private Label L_Message;
    Runnable Task_Message;
    Thread backgroundThread;
    private String conceptofacturas = "";

    public FacturaApp() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException {

        Gp_Message = new GridPane();
        Gp_Message.setMinWidth(1070);
        Gp_Message.setMaxHeight(40);
        // Gp_Message.getStyleClass().add("backgroundMessage");
        L_Message = new Label();
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 0);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);

        //GridPane.setHalignment(Gp_Message, HPos.RIGHT);
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
        la_valorcopago = new Label("Copago:");
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

        Object[] _listFacturaItem = (SihicApp.s_factura.getFacturaItems()).toArray();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(_listFacturaItem);
        Ta_FacturaItems.setItems(Ol_FacturaItems);

        V_Factura = new VBox();

        //botones
        ImageView imageView = new ImageView("/images/diario.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_Print = new Button("", imageView);
     
        //B_Pdf.setMaxWidth(130);
        imageView = new ImageView("/images/comprobante.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
         bu_printcomprobante = new Button("", imageView);
       
        //B_Pdf.setMaxWidth(130);
        imageView = new ImageView("/images/facturaglobal.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_Pdffacturaglobal = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/pedido.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_New = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/furisp.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_furisp = new Button("", imageView);

        //B_New.setMaxWidth(130);
        imageView = null;
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_Save = new Button("", imageView);
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
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

       

        //acciones de botones y cuadros de texto
        //*************************
        B_New.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                nuevaFactura();
            }
        });
        B_Print.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    pdfFactura();
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
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
            pdfComprobante();
        });

        bu_printcomprobante.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.ENTER) {
                    pdfComprobante();
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

        //eventos de guardar, salir e imprimir
        V_Factura.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.INSERT) {
                    if (B_New.isDisable()) {
                        try {
                            guardarFactura();
                        } catch (ParseException ex) {
                            Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        addGp_VlrRecibido();
                        Tf_ValorRecibido.requestFocus();
                        // nuevaFactura();
                    } else {
                        nuevaFactura();
                    }
                    // sb_producto.requestFocus();
                }

            }
        });

        Ta_FacturaItems.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.DELETE) {
                    deleteItem();
                }

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

                    // nuevaFactura();
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
        cb_credito = new CheckBox();
        la_credito = new Label("Crédito: ");
        la_credito.setMinWidth(70);
        cb_credito.setStyle("-fx-text-fill: white;");
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
        L_Producto = new Label("Detalle");
        L_CodigoCups = new Label("Cod. Servicio");
        L_Cantidad = new Label("Cantidad");
        L_Subtotal = new Label("Subtotal: ");
        L_Iva = new Label("Iva: ");
        L_Total = new Label("Total: ");
        L_DescuentoFactura = new Label("Descuento: ");
        L_ValorRecibido = new Label("Vlr Recibido: ");
        message = new Label();
        message.getStyleClass().add("message");
        message.setText("Error en la Conexión del Servidor Base de Datos");
        message.setOpacity(0);
        message.setMinWidth(440);
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
        H_Botones.getChildren().addAll(B_Save, B_Print, bu_printcomprobante, bu_furisp,B_Pdffacturaglobal);
        H_Botonesnav = new HBox();
        H_Botonesnav.setSpacing(10);//espacio verticalmente entre objetos
        GridPane.setHalignment(T_NoFactura, HPos.CENTER);
        Gp_DatosCliente.setVgap(1);//espacio verticalmente dentro de gridpane
        Gp_DatosCliente.setAlignment(Pos.CENTER);
        //GridPane.setColumnSpan(T_Total,2);
        GridPane.setRowSpan(T_Total, 3);

        GridPane.setValignment(Gp_DatosCliente, VPos.TOP);//alinea verticalmente

        GridPane.setValignment(T_Total, VPos.TOP);
        GridPane.setHalignment(T_Total, HPos.CENTER);

        Gp_DatosCliente.setHgap(8);
        Gp_Factura.setHgap(8);
        Gp_Factura.setVgap(8);
        //Traemos existencias
        String appClass = "sihic.contabilidad.FindProductEmbbed";
        Class clz = Class.forName(appClass);
        final Object app = clz.newInstance();
        Parent findProductEmbbed = (Parent) clz.getMethod("createContent").invoke(app);
        //  Gp_Factura.add(root, 0, 0,10,1);
        Gp_Total.add(T_NoFactura, 2, 0);
        GridPane.setValignment(T_NoFactura, VPos.TOP);
        GridPane.setHalignment(T_NoFactura, HPos.RIGHT);
        GridPane.setValignment(la_nitccadquiriente, VPos.BOTTOM);
        GridPane.setValignment(la_adquiriente, VPos.BOTTOM);
        Gp_DatosCliente.add(la_adquiriente, 0, 0);
        Gp_DatosCliente.add(la_nitccadquiriente, 1, 0);
        Gp_DatosCliente.add(adquiriente, 0, 1);
        Gp_DatosCliente.add(nitccadquiriente, 1, 1);
        Gp_DatosCliente.add(la_prestoservicio, 0, 2);
        Gp_DatosCliente.add(la_nitprestoservicio, 1, 2);
        Gp_DatosCliente.add(prestoservicio, 0, 3);
        Gp_DatosCliente.add(nitprestoservicio, 1, 3);
        Gp_DatosCliente.add(la_fechexpedicion, 0, 4);
        Gp_DatosCliente.add(la_fechexpiracion, 1, 4);
        Gp_DatosCliente.add(fechaexpedicion, 0, 5);
        Gp_DatosCliente.add(fechaexpiracion, 1, 5);
        if (Facturar.tipofact == 0 || Facturar.tipofact == 3) {
            Gp_DatosCliente.add(new Label("Médico que Ordena"), 0, 8, 2, 1);
            Gp_DatosCliente.add(medicoqueordena, 0, 9, 2, 1);
        } else {
            Gp_DatosCliente.add(new Label("Médico que Ordena"), 0, 6, 2, 1);
            Gp_DatosCliente.add(medicoqueordena, 0, 7, 2, 1);
        }
        Gp_Total.add(T_Total, 1, 1);
        Gp_Factura.add(Gp_Total, 1, 0);
        Gp_Factura.add(Gp_DatosCliente, 0, 0);
        Gp_Producto.add(la_valorcopago, 0, 0);
        Gp_Producto.add(la_valorcuota, 1, 0);
        Gp_Producto.add(L_CodigoCups, 2, 0);
        Gp_Producto.add(L_Producto, 3, 0);
        Gp_Producto.add(L_Cantidad, 4, 0);
        Gp_Producto.add(valorcopagos, 0, 1);
        Gp_Producto.add(valorcuota, 1, 1);
        Gp_Producto.add(Tf_CodigoCups, 2, 1);
        Gp_Producto.add(sb_producto, 3, 1);
        Gp_Producto.add(Tf_Cantidad, 4, 1);
        Gp_Producto.setHgap(10);

        GridPane.setHalignment(Gp_Producto, HPos.CENTER);
        Gp_Producto.setAlignment(Pos.CENTER);
        Gp_Factura.add(Gp_Producto, 0, 4, 2, 1);

        Gp_Factura.add(Gp_FacturaItems, 0, 5, 3, 1);

        //Panel con titulo que contiene todo los gridpane
        //Creamos el fondo para el led de 7 segmentos
        //create dragger with desired size
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
        GridPane.setRowSpan(Gr_NumeroDigital, 3);
        GridPane.setRowSpan(Gp_Total, 6);

        GridPane.setHalignment(Gr_NumeroDigital, HPos.CENTER);
        GridPane.setValignment(numerodigital, VPos.CENTER);
        Gp_Total.add(Gr_NumeroDigital, 1, 4, 2, 1);
        Gp_Factura.setAlignment(Pos.TOP_CENTER);

        Gp_FacturaItems.add(Ta_FacturaItems, 0, 0, 5, 1);

        // GridPane.setColumnSpan(Gp_Totales,1);
        GridPane.setHalignment(Gp_Totales, HPos.LEFT);
        Gp_Totales.setVgap(3);

        GridPane.setHalignment(findProductEmbbed, HPos.LEFT);
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

        Gp_FacturaItems.add(Gp_Totales, 4, 1, 1, 4);
        GridPane.setValignment(H_Botones, VPos.TOP);
        GridPane.setHalignment(Gp_Totales, HPos.RIGHT);
        Gp_Totales.alignmentProperty().setValue(Pos.TOP_RIGHT);
        Gp_FacturaItems.add(H_Botones, 0, 1);
        Gp_FacturaItems.add(message, 1, 1);
        Gp_FacturaItems.add(sp_producto, 3, 0, 2, 4);
        H_Botones.setAlignment(Pos.BOTTOM_LEFT);

        Gp_FacturaItems.setVgap(7);
        Gp_FacturaItems.setHgap(3);
        Ta_FacturaItems.setMaxHeight(Gp_Factura.getLayoutBounds().getHeight() * 0.15);
        Ta_FacturaItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Gp_Factura.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Factura.getStyleClass().add("background");
        Gp_FacturaItems.setAlignment(Pos.TOP_LEFT);
        changeColumns();
        disabledAllTextField();

        generarVlrCambio();
        nuevaFactura();
        validarfechacitaperiodofacturayeps();
        stack.getChildren().addAll(Gp_Factura, Gp_Message);
        stack.setAlignment(Pos.TOP_CENTER);

    }

    public void nuevaFactura() {
        removeGp_VlrRecibido();
        enabledAllTextField();
        
        Ol_FacturaItems.clear();
       // B_Print.setDisable(false);
       // B_Save.setDisable(false);
      //  B_New.setDisable(true);
      //  cb_credito.setSelected(false);
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
        if (SihicApp.hclconsultas.getId() != null) {
            setDatosSegunTipoFactura();
        }
        else
        {
            SihicApp.s_factura=null;
            SihicApp.s_factura=new Factura();
            
        }
    }

    private  void guardarFactura() throws ParseException, InterruptedException, InterruptedException, InterruptedException {
      //  B_Save.setDisable(true);
      //  B_New.setDisable(false);
        SihicApp.s_factura.setCredito(cb_credito.isSelected());
        SihicApp.s_factura.setDescuento(BigDecimal.valueOf(Double.parseDouble(Tf_DescuentoFactura.getText().trim())));
        SihicApp.s_factura.calculateTotals();
       
          if(SihicApp.s_factura.getPrefijo().substring(0,1).equals("E") || SihicApp.s_factura.getPrefijo().substring(0,1).equals("T"))
          {
             MovimientoCuentas.colocarasientoscontables(jenum.EnumTiposComprobantes.VENTAS3.ordinal());
          }
        SihicApp.s_factura = facturaControllerClient.guardarFactura(SihicApp.s_factura);
        System.out.println("Guardando");
        Tf_Subtotal.setText(SihicApp.s_factura.getNetAmount().toString());
        Tf_Iva.setText(SihicApp.s_factura.getIva().toString());
        Tf_Total.setText(SihicApp.s_factura.getTotalAmount().toString());
        totalvalorcuota.setText(SihicApp.s_factura.getTotalcuotasmoderadoras().toString());

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

    public void addProduct() {
        try {
          
            conComprobanteProcedimiento = null;
            conComprobanteProcedimiento = new ConComprobanteProcedimiento();
            if (SihicApp.conComprobanteProcedimiento != null) {
                conComprobanteProcedimiento = SihicApp.conComprobanteProcedimiento;

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
            conComprobanteProcedimiento.setFechaelaboracion(SihicApp.hclconsultas.getAgnCitas().getFechaHora());
            facturaControllerClient.saveComprobante(conComprobanteProcedimiento);
            SihicApp.conComprobanteProcedimiento = null;
            BigDecimal copago = BigDecimal.ZERO;
            BigDecimal cuotamoderadora = BigDecimal.ZERO;
            try {
                copago = (new BigDecimal(valorcopagos.getText().replace(".", ",")));
                cuotamoderadora = new BigDecimal(valorcuota.getText().replace(".", ","));

            } catch (Exception e) {
                copago = (new BigDecimal(valorcopagos.getText().replace(",", ".")));
                cuotamoderadora = new BigDecimal(valorcuota.getText().replace(",", "."));

            }
            SihicApp.hclconsultas.setMedicoqueordeno(medicoqueordena.getText());
            BigDecimal tarifa = BigDecimal.ZERO;
            switch (Facturar.s_tarifa) {
                case 0:
                    tarifa = findProduct.getKardex().getProducto().getPrecio2015();
                    break;
                case 1:
                    tarifa = findProduct.getKardex().getProducto().getPrecio2016();
                    break;
                case 2:
                    tarifa = findProduct.getKardex().getProducto().getPrecio2017();
                    break;
                case 3:
                    tarifa = findProduct.getKardex().getProducto().getPrecio2018();
                    break;
                case 4:
                    tarifa = findProduct.getKardex().getProducto().getPrecio2001();
                    break;

            }
            if (tarifa != null) {
                if (tarifa.toBigIntegerExact().floatValue() > 0.0) {
                    SihicApp.s_factura.addProduct(copago, cuotamoderadora, findProduct.getKardex().getProducto(), Float.valueOf(Tf_Cantidad.getText().trim()), BigDecimal.ZERO, BigDecimal.ZERO, porcentaje, SihicApp.hclconsultas, conComprobanteProcedimiento, Facturar.s_tarifa);
                    for (ProductoMedicamentos pm : findProduct.getKardex().getProducto().getMedicamentosItems()) {
                        if (pm.getMedicamento().getPrecio2018() != null) {
                            if (pm.getMedicamento().getPrecio2018().toBigIntegerExact().floatValue() > 0.0) {
                                {        
                                 SihicApp.s_factura.addProduct(new BigDecimal(BigInteger.ZERO), new BigDecimal(BigInteger.ZERO), pm.getMedicamento(), pm.getCantidad(), BigDecimal.ZERO, BigDecimal.ZERO, porcentaje, SihicApp.hclconsultas, conComprobanteProcedimiento, Facturar.s_tarifa);
                                }
                            } else {
                                alert.showAndWait();
                            }
                        } else {
                            alert.showAndWait();
                        }
                    }
                    if (SihicApp.s_factura.getPrefijo().equals("E") || SihicApp.s_factura.getPrefijo().equals("T")) {
                        SihicApp.s_factura.setGenPersonas(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenPersonas());

                    }

                    Factura facturaTemp = new Factura();
                    facturaTemp = SihicApp.s_factura;
                    try {

                       facturaControllerClient.crearFactura(SihicApp.s_factura);
                         if(SihicApp.s_factura.getPrefijo().substring(0,1).equals("E") || SihicApp.s_factura.getPrefijo().substring(0,1).equals("T"))
                        {
                            MovimientoCuentas.colocarasientoscontables(jenum.EnumTiposComprobantes.VENTAS3.ordinal());
                        }
                        L_Message.setText("Procedimiento Agregado");
                        mensajes_procesos();
                        if (SihicApp.s_factura == null) {

                            SihicApp.s_factura = facturaTemp;
                            deleteItem();
                        }
                        facturaItem = SihicApp.s_factura.getFacturaItems().get(SihicApp.s_factura.getFacturaItems().size() - 1);
                        SetDatosFactura();
                        valorcopagos.setText("0");
                        valorcuota.setText("0");
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

    public void deleteItem() {
        boolean _eliminar = false;
        FacturaItem fi = (FacturaItem) Ta_FacturaItems.getFocusModel().getFocusedItem();
        if (SihicApp.conComprobanteProcedimiento == null) {
            _eliminar = true;
            SihicApp.conComprobanteProcedimiento = fi.getConComprobanteProcedimiento();
        } else {
            if (SihicApp.conComprobanteProcedimiento.getId().equals(fi.getConComprobanteProcedimiento().getId())) {
                _eliminar = true;
            }
        }
        if (_eliminar) {
            SihicApp.s_factura.removeProduct(fi);
            facturaControllerClient.crearFactura(SihicApp.s_factura);

        }
        SetDatosFactura();

    }

    public void modifyItem(String quantity) {
        FacturaItem fi = (FacturaItem) Ta_FacturaItems.getFocusModel().getFocusedItem();
        SihicApp.s_factura.modifyItem(fi, Float.valueOf(quantity.trim()));
        facturaControllerClient.crearFactura(SihicApp.s_factura);
        SetDatosFactura();
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
        Tc_eapb.setCellFactory(column -> {
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
);
        Tc_NoIdent = new TableColumn();
        Tc_NoIdent.setText("N° Ident");
        Tc_NoIdent.setMinWidth(150);
        Tc_NoIdent.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getGenPacientes().getGenPersonas().getDocumento());
            }
        });
        
        Tc_Paciente = new TableColumn();
        Tc_Paciente.setText("Paciente");
        Tc_Paciente.setMinWidth(300);
        Tc_Paciente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getGenPacientes().getGenPersonas().getNombres());
            }
        });
        Tc_NoItem = new TableColumn();
        Tc_NoItem.setText("No Comp");
        Tc_NoItem.setMinWidth(100);
        Tc_NoItem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString());
            }
        });
        Tc_CodServicio = new TableColumn();
        Tc_CodServicio.setText("Cod. Servicio");
        Tc_CodServicio.setMinWidth(100);
        Tc_CodServicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getProducto().getCodigo());
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
                return new SimpleStringProperty(facturaitem.getValue().getProducto().getNombre());
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
        Tc_ValorU.setText("Valor/U");
        Tc_ValorU.setMinWidth(150);
        Tc_ValorU.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getValor_u().toString());
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

        impresionFactura.fprintpdffacturaglobal();

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

    private void SetDatosFactura() {
        impresionFactura = null;
        impresionFactura = new ImpresionFactura(SihicApp.s_factura);
        String cerosizq = "";

        for (int i = 6; i > SihicApp.s_factura.getNo_factura().toString().length(); i--) {
            cerosizq = cerosizq + "0";
        }
        T_NoFactura.setText("FACTURA DE VENTA\n" + SihicApp.s_factura.getNofacturacerosizquierda());
        if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("P")) {

            adquiriente.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenEapb().getNombre() + "-" + SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre());
            nitccadquiriente.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenEapb().getNit());
            usuarioservicio.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenPersonas().getNombres());
            nitusuarioservicio.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
            if (Gp_DatosCliente.getChildren().indexOf(usuarioservicio) == -1) {
                Gp_DatosCliente.add(la_usuarioservicio, 0, 6);
                Gp_DatosCliente.add(la_nitusuarioservicio, 1, 6);
                Gp_DatosCliente.add(usuarioservicio, 0, 7);
                Gp_DatosCliente.add(nitusuarioservicio, 1, 7);

            }

        } else {
            adquiriente.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenPersonas().getNombres());
            nitccadquiriente.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
            if (Gp_DatosCliente.getChildren().indexOf(usuarioservicio) == 1) {
                Gp_DatosCliente.getChildren().removeAll(la_nitusuarioservicio, la_usuarioservicio, usuarioservicio, nitusuarioservicio);
                Gp_DatosCliente.add(new Label("Médico que Ordena"), 0, 6, 2, 1);
                Gp_DatosCliente.add(medicoqueordena, 0, 7, 2, 1);

            }
        }
        prestoservicio.setText("CENTRO MÉDICO GUAVIARE CMG SAS");
        nitprestoservicio.setText("900767863-6");

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
        B_Save.setDisable(false);
        B_New.setDisable(false);

    }

    private void setDatosSegunTipoFactura() {
        SihicApp.conComprobanteProcedimiento = null;
        SihicApp.s_factura.setPrefijo(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString());
        verificarporcentajeconvenio();
        //SihicApp.s_factura.setHclConsultas(SihicApp.hclconsultas);
        bu_furisp.setDisable(true);

        bu_furisp.setVisible(false);

        if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A")) {
            SihicApp.s_factura.setFacturacerrada(false);
            SihicApp.s_factura.setGenEapb(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenEapb());
            SihicApp.s_factura = SihicApp.s_factura;//facturaControllerClient.FindLAstFacturaEps(SihicApp.s_factura,SihicApp.hclconsultas.getHclHistoriasClinicas().getGenPacientes().getGenTiposUsuarios());

            if (SihicApp.s_factura != null) {
                SetDatosFactura();
            }

        } else {

            if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("P")) {
                if (SihicApp.s_factura.getId() != null) {
                    SihicApp.s_factura = SihicApp.s_factura;

                } else {

                    SihicApp.s_factura.setGenEapb(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenEapb());
                    SihicApp.s_factura.setGenTiposUsuarios(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenTiposUsuarios());
                    SihicApp.s_factura.setFacturacerrada(false);
                }

               facturaControllerClient.crearFactura(SihicApp.s_factura);
                if (SihicApp.s_factura != null) {
                    SetDatosFactura();
                }

            } else {
                if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("T")) {
                    bu_furisp.setVisible(true);
                    bu_furisp.setDisable(false);
                }
                if (SihicApp.s_factura.getId() != null) {
                    SihicApp.s_factura = SihicApp.s_factura;

                } else {
                    //SihicApp.s_factura=facturaControllerClient.findfacturapornumeroytipo(Facturar.numerofactura,SihicApp.s_factura.getPrefijo());
                    SihicApp.s_factura.setFacturacerrada(true);
                    SihicApp.s_factura.setGenPersonas(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenPersonas());
                    facturaControllerClient.crearFactura(SihicApp.s_factura);
                }
                SetDatosFactura();
            }
        }

    }

    private void setcomprobante() {
        if (Ta_FacturaItems.getSelectionModel().getSelectedIndex() != -1) {
            facturaItem = (FacturaItem) Ta_FacturaItems.getSelectionModel().getSelectedItem();
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

    public void verificarporcentajeconvenio() {
        if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("P")) {
            if (SihicApp.hclconsultas != null) {
                try {
                    if (SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenEapb().getConvenioseps().size() > 0) {
                        porcentaje = SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenEapb().findlastconvenio().getPorcentajedescuento();
                    } else {
                        porcentaje = 0;
                    }
                } catch (Exception e) {
                    porcentaje = 0;
                }

            }
        } else {
            if (Facturar.tipofact == 1) {
                if (SihicApp.genConvenios != null) {

                    porcentaje = SihicApp.genConvenios.getPorcentajedescuento();
                } else {
                    porcentaje = 0;
                }
            } else {
                porcentaje = 0;
            }
        }

    }

    public static Scene getSceneFactura() {
        return sceneFactura;
    }

    public Stage getStageFactura() {
        return stageFactura;
    }

    public void updateFactura() {
        SihicApp.s_factura = SihicApp.s_factura;
        SetDatosFacturaUpdate();
    }

    private void SetDatosFacturaUpdate() {

        T_NoFactura.setText("FACTURA DE VENTA\n" + SihicApp.s_factura.getPrefijo() + SihicApp.s_factura.getNofacturacerosizquierda());
        if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("P")) {

            adquiriente.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenEapb().getNombre() + "-" + SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre());
            nitccadquiriente.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenEapb().getNit());
            usuarioservicio.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenPersonas().getNombres());
            nitusuarioservicio.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
            if (Gp_DatosCliente.getChildren().indexOf(usuarioservicio) == -1) {
                Gp_DatosCliente.add(la_usuarioservicio, 0, 6);
                Gp_DatosCliente.add(la_nitusuarioservicio, 1, 6);
                Gp_DatosCliente.add(usuarioservicio, 0, 7);
                Gp_DatosCliente.add(nitusuarioservicio, 1, 7);
            }
        } else {
            adquiriente.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenPersonas().getNombres());
            nitccadquiriente.setText(SihicApp.hclconsultas.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
            if (Gp_DatosCliente.getChildren().indexOf(usuarioservicio) == 1) {
                Gp_DatosCliente.getChildren().removeAll(la_nitusuarioservicio, la_usuarioservicio, usuarioservicio, nitusuarioservicio);
            }
        }
        prestoservicio.setText("CENTRO MÉDICO GUAVIARE CMG SAS");
        nitprestoservicio.setText("900767863-6");

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
        B_Save.setDisable(false);
        B_New.setDisable(false);

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

    private void validarfechacitaperiodofacturayeps() {
        boolean agregaproducto = false;
        int mescita = UtilDate.getmesfecha(SihicApp.hclconsultas.getAgnCitas().getFechaHora());
        int yearcita = UtilDate.getyearfecha(SihicApp.hclconsultas.getAgnCitas().getFechaHora());
        int mesfactura = UtilDate.getmesfecha(SihicApp.s_factura.getFacturaDate());
        int yearfactura = UtilDate.getyearfecha(SihicApp.s_factura.getFacturaDate());
        if (mescita == mesfactura && yearcita == yearfactura) {
            agregaproducto = true;
        }
        if (!agregaproducto) {
            valorcopagos.setDisable(true);
            valorcuota.setDisable(true);
            sb_producto.setDisable(true);
            sp_producto.setDisable(true);
            Tf_CodigoCups.setDisable(true);
            Tf_Cantidad.setDisable(true);
            message.setText("Fecha de Cita y Fecha de Periodo Facturado no coinciden");
            message.setStyle("-fx-background-color:#ff1600;-fx-text-fill:  white;");

        }
        else
        {
            valorcopagos.setDisable(false);
            valorcuota.setDisable(false);
            sb_producto.setDisable(false);
            sp_producto.setDisable(false);
            Tf_CodigoCups.setDisable(false);
            Tf_Cantidad.setDisable(false);
        }

    }

   
    private void mensajes_procesos() {

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

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;
        //ft.setFromValue(0.0);
        // ft.setToValue(1);

    }
 
}
