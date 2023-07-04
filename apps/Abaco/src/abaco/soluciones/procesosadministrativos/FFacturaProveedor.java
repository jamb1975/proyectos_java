package abaco.soluciones.procesosadministrativos;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import model.Categoria;
import model.FacturaItemProveedores;
import model.FacturaProveedores;
import model.Kardex;
import model.Producto;
import model.Proveedores;
import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import static abaco.AbacoApp.kardexControllerClient;
import abaco.PageBrowser;
import abaco.SearchPopover;
import abaco.control.SearchBox;
import abaco.controlador.KardexControllerClient;
import abaco.controlador.ProductoControllerClient;
import abaco.controlador.ProveedoresControllerClient;
import abaco.digitos.NumeroDigital;
import abaco.generic.LoadChoiceBoxGeneral;
import abaco.soluciones.informacionadministrativa.AdminProductos;
import abaco.soluciones.informacionadministrativa.FProducto;
import com.aquafx_project.AquaFx;
import fields.FieldDouble;
import fields.FieldNumero;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.scene.control.Tooltip;
import jfxtras.styles.jmetro8.JMetro;
import org.aerofx.AeroFX;

/**
 * A simple table with a header row.
 *
 * @sampleName TableView
 * @preview preview.png
 * @see javafx.scene.control.TableColumn
 * @see javafx.scene.control.TablePosition
 * @see javafx.scene.control.TableRow
 * @see javafx.scene.control.TableView
 * @related /Controls/TableCellFactory
 * @embedded
 */
public class FFacturaProveedor extends Application {

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
    private Button B_Print;
    private Button B_New;
    private Button B_Save;
    private Button B_AgregarAbono;
    private Button B_VerAbonos;
    private Button B_Buscar;
    private Button B_SaveProd;
    private Button B_verGanancias;
    private Button B_Buscarfacturas;
    private Button B_Kardex;
    private Button B_Existencias;
    private Button B_Insertar;

    private Button bu_importardesdeexcel = new Button();
    //Tabla Items de factura
    private TableView Ta_FacturaItems;
    private TableView Ta_MateriaPrima;
    //GridPane row and columns
    private GridPane Gp_Factura;
    private GridPane Gp_DatosEmpresa;
    private GridPane Gp_Proveedor;
    private GridPane Gp_Total;
    private GridPane Gp_Producto;
    private GridPane Gp_Inventario;
    private GridPane Gp_MateriaPrima;
    private GridPane Gp_FacturaItems;
    private GridPane Gp_Totales;
    private GridPane Gp_VlrRecibido;
    private TitledPane Tp_Proveedor;
    private TitledPane Tp_Producto;
    private TitledPane Tp_Inventario;
    private TitledPane Tp_MateriaPrima;
    private Label L_Cantidad_Salida;
    private TextField Tf_Cantidad_Salida;
    private HBox Hb_Totales;
    //TitledPane container solucion factura

    //Texto
    private Text T_NoFactura;
    private Text T_Total;
    //Etiquetas datos cliente
    private Label L_NombreProveedor;
    private Label L_NitProveedor;
    private Label L_DireccionProveedor;
    private Label L_TelefonoProveedor;
    private Label L_FormaPago;
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
    private Label L_Precio;
    private Label L_Iva;
    private Label L_PrecioCompra;
    private Label L_MateriaPrima;
    private Label L_Habilitar;
    private Label la_unidadesmedida;

    //Campos de texto datos cliente 
    private TextField Tf_NombreProveedor;
    private TextField Tf_NitProveedor;
    private TextField Tf_DireccionProveedor;
    private TextField Tf_TelefonoProveedor;
    private DatePicker Tf_Fecha;
    private TextField Tf_NoFactura;
    private SearchBox sb_producto = new SearchBox();
    private SearchPopover sp_producto;

    private FieldNumero Tf_Valor;
    private FieldNumero Tf_Cantidad;
    private TextField Tf_Total;
    private TextField Tf_ValorRecibido;
    private TextField Tf_Codigo;
    private TextField Tf_Precio;
    private FieldNumero Tf_PrecioCompra;
    private FieldDouble Tf_Iva;
    private Label la_buscar;
    private TextField buscar;
    private Label la_vencimiento;
    private DatePicker fechavencimiento;
    private Label la_descripcion;
    private TextArea descripcion;
    private Label la_referencia;
    private TextField referencia;
    private FieldDouble porcentajeganancia = new FieldDouble();

    //list box
    private static ChoiceBox Cb_Empleados;
    //radio button
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
    private ObservableList Ol_FacturaItems = FXCollections.observableArrayList();
    private Object[] O_ListFacturaItems;
    private ObservableList Ol_FacturaItemsI = FXCollections.observableArrayList();
    private Object[] O_ListFacturaItemsI;

    private TableColumn Tc_NoItem;
    private TableColumn Tc_Producto;
    private TableColumn Tc_Cantidad;
    private TableColumn Tc_ValorU;
    private TableColumn Tc_ValorT;
    private TableColumn Tc_codigo;
    //columnas tabla factura
    private TableColumn Tc_ProductoI;
    private TableColumn Tc_CantidadI;
    private TableColumn Tc_ValorUI;
    private TableColumn Tc_ValorTI;

    //Objetos de persistencia y modelso de datos
    ProveedoresControllerClient proveedoresControllerClient;
    ProductoControllerClient productoControllerClient;
    //Stage mostrar formulario findproducto

    private Proveedores proveedor;
    private File file;
    private FileChooser fileChooser = new FileChooser();
    private Stage stage;
    private static final Stage stage2 = new Stage(StageStyle.TRANSPARENT);
    final ProgressIndicator p5 = new ProgressIndicator();
    final Timeline timeline = new Timeline();
    ProgressBar p3 = new ProgressBar();
    Region veil = new Region();
    ProgressIndicator p = new ProgressIndicator();
    Thread thimport;
    private Thread Th_Message;
    StackPane stack;

    private Label L_EsMateriaPrima;
    private CheckBox Ch_EsMateriaPrima;
    private CheckBox Ch_AddMateriaPrima;

    String appClass;
    Class clz;
    Object app;
    String appClassKardex;
    Class clzKardex;
    Object appKardex;
    String appClassExistencias;
    Class clzExistencias;
    Object appExistencias;
    Parent findProductEmbbed;
    Parent P_Kardex;
    Parent P_Existencias;
    private Stage stageKardex = new Stage(StageStyle.DECORATED);
    private Stage stageExistencias = new Stage(StageStyle.DECORATED);
    private Stage stageproveedor = new Stage(StageStyle.DECORATED);
    // Create a KeyCombination for Alt + C

    Scene scene = null;

    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    SearchBox sb_proveedor = new SearchBox();
    SearchPopover sp_proveedor;
    private Button B_Buscarproveedor;
    private ImageView imageView;
    HBox hbox_proveedor = new HBox();
    private String ex_observaciones = "";
    private String ex_ubicacion = "";
    private String ex_stockmin = "";
    private String ex_stockmax = "";

    public Parent createContent() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        actualizarProductos();
        porcentajeganancia.setMinWidth(100);
        Gr_NumeroDigital = new Group();
        proveedoresControllerClient = new ProveedoresControllerClient();
        sp_proveedor = new SearchPopover(sb_proveedor, new PageBrowser(), AbacoApp.s_proveedores, false);
        sp_proveedor.setMaxSize(150, 150);

        sb_proveedor.setOnAction(e -> {
            Tf_NitProveedor.setText(AbacoApp.s_proveedores.getNo_ident());
            Tf_TelefonoProveedor.setText(AbacoApp.s_proveedores.getCelular());
            Tf_DireccionProveedor.setText(AbacoApp.s_proveedores.getDir1());
        });
        imageView = null;
        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        B_Buscarproveedor = new Button();
        B_Buscarproveedor.setGraphic(imageView);
        B_Buscarproveedor.setText("");
        hbox_proveedor.getChildren().addAll(sb_proveedor, B_Buscarproveedor);
        B_Buscarproveedor.setOnAction(e -> {
            showproveedor();
        });
        AbacoApp.s_producto = null;
        AbacoApp.s_producto = new Producto();
        stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Producto");
        stage.setOnHidden(e -> {
            if (e.getEventType() == e.WINDOW_HIDDEN) {

                try {
                    sb_producto.setText(AbacoApp.s_producto.getNombre());
                    Tf_Codigo.setText(AbacoApp.s_producto.getCodigobarras());

                    // findByCodigo();
                    sp_producto.hide();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        stageproveedor.setOnHidden(e -> {
            if (e.getEventType() == e.WINDOW_HIDDEN) {

                try {

                    Tf_NitProveedor.setText(AbacoApp.s_proveedores.getNo_ident());
                    Tf_DireccionProveedor.setText(AbacoApp.s_proveedores.getDir1());
                    Tf_TelefonoProveedor.setText(AbacoApp.s_proveedores.getCelular());
                    sb_proveedor.setText(AbacoApp.s_proveedores.getNombre());

                    sp_proveedor.hide();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);;
        sb_producto.setMinWidth(250);
        sb_producto.setPromptText("Digite código o Nombre para busqueda general");
        sp_producto = new SearchPopover(sb_producto, new PageBrowser(), AbacoApp.s_producto, false);
        stack = new StackPane();

        /**
         * *********************************
         */
        L_Cantidad_Salida = new Label("Cantidad Salida(Unidades): ");
        Tf_Cantidad_Salida = new TextField("0");
        L_EsMateriaPrima = new Label("Materia Prima: ");
        L_Habilitar = new Label("Habilitar Materia Prima: ");
        Ch_EsMateriaPrima = new CheckBox();
        Ch_AddMateriaPrima = new CheckBox();
        Ta_MateriaPrima = new TableView();
        Tc_ProductoI = new TableColumn();
        Tc_ProductoI.setText("Producto");
        Tc_ProductoI.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getProduct().getNombre());
            }
        });

        Tc_CantidadI = new TableColumn();
        Tc_CantidadI.setText("Cantidad");
        Tc_CantidadI.setCellValueFactory(new PropertyValueFactory("quantity"));
        Tc_CantidadI.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        Tc_ValorUI = new TableColumn();
        Tc_ValorUI.setText("Valor/u");
        Tc_ValorUI.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getValor_u().toString());
            }
        });
        //Objetos gridpane
        Gp_Factura = new GridPane();
        Gp_DatosEmpresa = new GridPane();
        Gp_Proveedor = new GridPane();
        Gp_Total = new GridPane();
        Gp_Producto = new GridPane();
        Gp_MateriaPrima = new GridPane();
        Gp_MateriaPrima.autosize();
        Gp_Inventario = new GridPane();
        Gp_FacturaItems = new GridPane();
        Gp_Totales = new GridPane();
        Tc_ValorTI = new TableColumn();
        Tc_ValorTI.setText("Valor Total");
        Tc_ValorTI.setMinWidth(100);
        Tc_ValorTI.setCellValueFactory(new PropertyValueFactory("valor_total"));
        Ta_MateriaPrima.setMinWidth(350);
        // Ta_MateriaPrima.autosize();
        Ta_MateriaPrima.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Ta_MateriaPrima.getColumns().addAll(Tc_ProductoI, Tc_CantidadI, Tc_ValorTI);

        //******************************************************************************
        proveedor = new Proveedores();

        Gp_VlrRecibido = new GridPane();
        Gp_DatosEmpresa.autosize();
        Gp_Producto.autosize();
        //Task

        Thread th = new Thread(task);
        th.setDaemon(false);
        th.start();
        //campos de texto
        //datos cliente
        Tf_NombreProveedor = new TextField();
        Tf_NitProveedor = new TextField();
        Tf_DireccionProveedor = new TextField();
        Tf_TelefonoProveedor = new TextField();
        Tf_Fecha = new DatePicker(LocalDate.now());
        Tf_NoFactura = new TextField();
        Tf_NoFactura.setMinWidth(250);
        //datos factura
        sb_proveedor.setText("");
        AbacoApp.s_proveedores = null;
        AbacoApp.s_proveedores = new Proveedores();
        Tf_Valor = new FieldNumero(10);
        Tf_Valor.setText("0");
        Tf_Valor.setMinWidth(100);

        Tf_Cantidad = new FieldNumero(7);

        Tf_Cantidad.setMaxWidth(100);
        Tf_Codigo = new TextField();
        Tf_Codigo.setMinWidth(100);
        Tf_Precio = new TextField("0");
        Tf_Iva = new FieldDouble();
        Tf_Iva.setText("0");
        Tf_Total = new TextField("000000");
        getnumerodigital();
        Tf_Total.setMaxWidth(150);
        Tf_TelefonoProveedor = new TextField("");
        Tf_ValorRecibido = new TextField("0");
        Tf_ValorRecibido.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        Tf_ValorRecibido.setMinHeight(30);
        Tf_PrecioCompra = new FieldNumero(10);
        Tf_PrecioCompra.setText("0");
        la_buscar = new Label("Buscar:");
        buscar = new TextField();
        buscar.setMinWidth(120);
        buscar.textProperty().addListener((observable, oldValue, newValue) -> {
            buscarproducto();
        });
        la_referencia = new Label("Referencia:");
        referencia = new TextField();
        la_descripcion = new Label("Descripción:");
        descripcion = new TextArea();
        descripcion.setPrefRowCount(3);
        descripcion.setPrefColumnCount(40);
        descripcion.setWrapText(true);
        la_vencimiento = new Label("Fecha Vencimiento:");
        fechavencimiento = new DatePicker(LocalDate.now());
        //Inicializamos objetos de pesistencia

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
        fileChooser.setTitle("Abrir Archivo de excel");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS", "*.xls")
        //  new FileChooser.ExtensionFilter("XLSX", "*.xls")
        );

        //botones
        B_Print = new Button();
        ImageView imageView = new ImageView("/abaco/images/bs_print.gif");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Print.setGraphic(imageView);
        imageView = new ImageView("/images/excel.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        bu_importardesdeexcel.setGraphic(imageView);
        bu_importardesdeexcel.setTooltip(new Tooltip("Importar Inventario desde Excel extensión .xls"));
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_New = new Button("", imageView);
        B_New.setMaxWidth(130);
        imageView = null;
        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        B_Buscar = new Button();
        B_Buscar.setGraphic(imageView);
        B_Buscar.setText("");

        B_Buscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                show();

            }
        });
        imageView = null;
        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        B_Buscarfacturas = new Button("", imageView);

        B_Buscarfacturas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

            }
        });
        imageView = null;
        imageView = new ImageView("/images/kardex.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Kardex = new Button("", imageView);

        B_Kardex.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                try {
                    showKardex();
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        imageView = null;
        imageView = new ImageView("/images/existencias.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Existencias = new Button("", imageView);
        B_Existencias.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                try {
                    showExistencias();
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        imageView = null;
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Save = new Button("", imageView);

        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_SaveProd = new Button();
        B_SaveProd.setGraphic(imageView);
        B_SaveProd.setText("");

        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        B_Insertar = new Button();
        B_Insertar.setGraphic(imageView);
        B_Insertar.setText("");

        imageView = null;
        imageView = new ImageView("/images/bs_excel.jpg");
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        B_AgregarAbono = new Button("Agregar Abono", imageView);
        B_VerAbonos = new Button("Ver Pedidos y Pagos");
        B_VerAbonos.setMaxWidth(130);

        //acciones de botones y cuadros de texto
        B_Insertar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                addProduct();
                Tf_Codigo.requestFocus();

            }
        });
        B_Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    guardarFactura();
                } catch (ParseException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        B_SaveProd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                // guardarProducto();
                buscar.requestFocus();
            }
        });

        sb_producto.setOnAction(e -> {
            findByProducto();
        });

        toggleGroup = new ToggleGroup();

        Rb_Contado = new RadioButton("Contado");
        Rb_Contado.setMinWidth(100);
        Rb_Contado.setToggleGroup(toggleGroup);
        // Rb_Contado.setStyle("-fx-text-fill: white;");
        Rb_Credito = new RadioButton("Credito");
        Rb_Credito.setMinWidth(100);
        Rb_Credito.setToggleGroup(toggleGroup);
        // Rb_Credito.setStyle("-fx-text-fill: white;");

        //etiquetas datos factura
        L_Fecha = new Label("Fecha:");
        L_NoFactura = new Label("No. Factura: ");
        L_NoFactura.setMinWidth(80);
        L_FormaPago = new Label("Forma de pago:   ");
        L_FormaPago.setMinWidth(108);
        L_NombreProveedor = new Label("Proveedor: ");
        L_NitProveedor = new Label("Nit: ");
        L_DireccionProveedor = new Label("Dirección:");
        L_TelefonoProveedor = new Label("Teléfono:");
        L_Producto = new Label("Producto: ");
        L_Producto.setMinWidth(100);
        L_Cantidad = new Label("Cantidad: ");
        L_Cantidad.setMinWidth(70);
        L_Codigo = new Label("Código Producto: ");
        L_Codigo.setMinWidth(110);
        L_Precio = new Label("Precio de Venta: ");
        L_Iva = new Label("Iva (%):");
        L_Valor = new Label("Valor:");
        L_Total = new Label("Total: ");
        L_F6 = new Label("F6 -----> Imprimir Factura");
        L_F7 = new Label("F7 -----> Guardar y Nueva Factura");
        L_F6.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 10));
        L_F7.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 10));
        la_unidadesmedida = new Label("Unidad Medida: ");
        L_ValorRecibido = new Label("Vlr Recibido: ");
        L_ValorRecibido.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        L_ValorCambio = new Label("Vlr Cambio: ");
        L_ValorCambio.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        T_Total = new Text("TOTAL");
        T_Total.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_Total.setFont(Font.font("ARIAL", FontWeight.BOLD, 30));
        T_Total.setFill(Color.WHITE);
        L_PrecioCompra = new Label("Valor/U Compra: ");
        L_PrecioCompra.setMinWidth(115);
        L_MateriaPrima = new Label("Materia Prima: ");
        L_MateriaPrima.setMinWidth(120);

        //Objeto Vbox coloca dentro de una caja objetos verticalmente
        V_box = new HBox();
        V_box.setSpacing(2);//espacio verticalmente entre objetos
        V_box.getChildren().addAll(Rb_Contado, Rb_Credito);
        H_Botones = new HBox();
        H_Botones.setSpacing(2);//espacio verticalmente entre objetos
        H_Botones.getChildren().addAll(B_New, B_Save, bu_importardesdeexcel);
        H_Producto = new HBox();
        H_Producto.setSpacing(2);//espacio verticalmente entre objetos
        H_Producto.getChildren().addAll(sb_producto, B_Buscar);
        H_BotonesInv = new HBox();
        H_BotonesInv.setSpacing(2);//espacio verticalmente entre objetos
        // H_BotonesInv.getChildren().addAll(B_Buscarfacturas,B_Kardex,B_Existencias);
        //alineacion,colspan,verspan,bordes,etc
        Gp_Proveedor.setVgap(5);//espacio verticalmente dentro de gridpane
        Gp_Producto.setVgap(3);
        Gp_Inventario.setVgap(5);
        Gp_MateriaPrima.setVgap(5);
        GridPane.setFillWidth(L_FormaPago, Boolean.TRUE);
        GridPane.setColumnSpan(T_Total, 5);
        GridPane.setRowSpan(T_Total, 3);
        GridPane.setColumnSpan(Ta_FacturaItems, 2);
        GridPane.setValignment(Gp_Proveedor, VPos.TOP);
        GridPane.setValignment(Gp_Producto, VPos.TOP);//alinea verticalmente
        GridPane.setValignment(Gp_Inventario, VPos.TOP);
        GridPane.setValignment(L_FormaPago, VPos.TOP);
        GridPane.setValignment(L_Codigo, VPos.TOP);
        GridPane.setValignment(L_PrecioCompra, VPos.TOP);
        GridPane.setValignment(T_Total, VPos.TOP);
        GridPane.setHalignment(T_Total, HPos.CENTER);
        //Creamos el fondo para el led de 7 segmentos
        //create dragger with desired size
        Rectangle dragger = new Rectangle(850, 200);
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

        // background image
        // digital clock
        // add background and clock to sample
        GridPane.setValignment(numerodigital, VPos.CENTER);
        GridPane.setRowSpan(Gr_NumeroDigital, 2);
        GridPane.setRowSpan(Gp_Total, 2);
        GridPane.setColumnSpan(Gr_NumeroDigital, 5);
        GridPane.setHalignment(Gr_NumeroDigital, HPos.CENTER);

        Gp_Proveedor.setHgap(2);
        Gp_Producto.setHgap(2);
        Gp_Inventario.setHgap(2);
        Gp_Factura.setHgap(2);
        Gp_Proveedor.add(L_Fecha, 0, 0);
        Gp_Proveedor.add(Tf_Fecha, 1, 0);
        Gp_Proveedor.add(L_NoFactura, 0, 1);
        Gp_Proveedor.add(Tf_NoFactura, 1, 1);
        Gp_Proveedor.add(new Label("Nombre:"), 0, 2);
        Gp_Proveedor.add(hbox_proveedor, 1, 2);
        Gp_Proveedor.add(L_NitProveedor, 0, 3);
        Gp_Proveedor.add(Tf_NitProveedor, 1, 3);
        Gp_Proveedor.add(L_TelefonoProveedor, 0, 4);
        Gp_Proveedor.add(Tf_TelefonoProveedor, 1, 4);
        Gp_Proveedor.add(L_DireccionProveedor, 0, 5);
        Gp_Proveedor.add(Tf_DireccionProveedor, 1, 5);
        Gp_Proveedor.add(L_FormaPago, 0, 6);
        Gp_Proveedor.add(V_box, 1, 6);
        Gp_Proveedor.add(T_Total, 2, 0, 1, 2);

        Gp_Proveedor.add(Gr_NumeroDigital, 2, 1, 1, 6);

        Gp_Inventario.add(la_buscar, 0, 0);
        Gp_Inventario.add(buscar, 0, 1);
        Gp_Inventario.add(new Label("% Ganancia"), 1, 0);
        Gp_Inventario.add(porcentajeganancia, 1, 1);
        Gp_Inventario.add(L_Codigo, 2, 0);
        Gp_Inventario.add(Tf_Codigo, 2, 1);
        Gp_Inventario.add(L_Producto, 3, 0);
        Gp_Inventario.add(H_Producto, 3, 1);

        Gp_Inventario.add(L_Cantidad, 4, 0);
        Gp_Inventario.add(Tf_Cantidad, 4, 1);
        Gp_Inventario.add(L_PrecioCompra, 5, 0);
        Gp_Inventario.add(Tf_PrecioCompra, 5, 1);
        Gp_Inventario.add(L_Valor, 6, 0);
        Gp_Inventario.add(Tf_Valor, 6, 1);
        Gp_Inventario.add(B_Insertar, 7, 1, 1, 1);
        Ta_MateriaPrima.setMinHeight(70);
        Ta_MateriaPrima.setMaxHeight(70);

        Gp_MateriaPrima.add(L_Habilitar, 0, 0);
        Gp_MateriaPrima.add(Ch_AddMateriaPrima, 1, 0);
        Gp_MateriaPrima.add(Ta_MateriaPrima, 1, 1, 2, 1);
        Gp_MateriaPrima.add(L_Cantidad_Salida, 0, 2);
        Gp_MateriaPrima.add(Tf_Cantidad_Salida, 1, 2);

        // Tp_Producto=new TitledPane("Producto",Gp_Producto);
        // Gp_Total.add(T_Total, 1, 1);
        Gp_Proveedor.setStyle("-fx-padding: 5;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0.25px;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: #007fff;");
        Gp_Producto.setStyle("-fx-padding: 2;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0.25px;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: #007fff;");
        Gp_Inventario.setStyle("-fx-padding: 5;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0.25px;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: #007fff;");
        Gp_MateriaPrima.setStyle("-fx-padding: 2;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0.25px;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: #007fff;");
        Gp_Factura.add(Gp_Proveedor, 0, 0);

        // Gp_Factura.add(Gp_Producto, 0, 1,2,1);
        Gp_Factura.add(Gp_Inventario, 0, 2, 2, 1);

        // Gp_Factura.add(H_BotonesInv, 1, 2);
        GridPane.setValignment(H_BotonesInv, VPos.BOTTOM);
        H_BotonesInv.setAlignment(Pos.CENTER);
        //Panel con titulo que contiene todo los gridpane

        GridPane.setValignment(B_Buscarfacturas, VPos.TOP);
        GridPane.setValignment(B_Kardex, VPos.TOP);
        GridPane.setValignment(B_Existencias, VPos.TOP);
        // Gp_Total.add(Gr_NumeroDigital, 1, 4);
        Gp_Factura.setAlignment(Pos.TOP_CENTER);
        Gp_Factura.autosize();
        Gp_FacturaItems.add(Ta_FacturaItems, 0, 0, 5, 1);
        Gp_FacturaItems.add(sp_producto, 0, 0, 5, 2);
        /* Gp_FacturaItems.add(B_Buscarfacturas, 5, 0);
         Gp_FacturaItems.add(B_Kardex, 5, 1);
         Gp_FacturaItems.add(B_Existencias, 5, 2);*/
        Gp_Factura.add(Gp_FacturaItems, 0, 3, 5, 1);
        Hb_Totales = new HBox(L_Total, Tf_Total);
        Hb_Totales.setSpacing(5);
        Hb_Totales.setAlignment(Pos.CENTER_RIGHT);
        Gp_FacturaItems.add(Hb_Totales, 4, 1);
        GridPane.setValignment(H_Botones, VPos.BOTTOM);
        GridPane.setValignment(L_MateriaPrima, VPos.TOP);
        Gp_FacturaItems.add(H_Botones, 1, 1);
        H_Botones.setAlignment(Pos.BOTTOM_LEFT);

        Gp_FacturaItems.setVgap(7);
        Gp_FacturaItems.setHgap(3);
        //Ancho de tabla y ancho de porcentaje de columnas

        Ta_FacturaItems.setMaxHeight(260);
        V_Factura = new VBox();
        V_Factura.setSpacing(5);

        V_Factura.getChildren().addAll(Gp_Factura);
        //V_Factura.setMinSize(900, 1000);
        Gp_Factura.setMinSize(1000, 650);
        Gp_Factura.setMaxSize(1000, 650);
        Gp_FacturaItems.autosize();
        //Gp_Factura.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        //Gp_Factura.getStyleClass().add("background");

        Gp_FacturaItems.setAlignment(Pos.CENTER);
        Gp_Factura.setAlignment(Pos.CENTER);
        // Gp_DatosCliente.getStyleClass().add("background");
        // V_Factura.setAlignment(Pos.TOP_CENTER);
        //Creamos las columnas temporales Item de factura

        disabledAllTextField();
        //  Tp_Factura= new TitledPane("Factura de Venta", V_Factura);
        V_Factura.autosize();
        generarVlrCambio();

        //Message
        Gp_Message = new GridPane();
        Gp_Message.setMinWidth(800);
        Gp_Message.setMaxHeight(40);
        L_Message = new Label();
        L_Message.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        L_Message.getStyleClass().add("labelMessage");

        Gp_Message.add(L_Message, 0, 0);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        Gp_Message.setMaxSize(100, 50);
        //GridPane.setHalignment(Gp_Message, HPos.RIGHT);
        L_Message.setAlignment(Pos.CENTER_RIGHT);
        Gp_Message.setVisible(false);
        stack.getChildren().addAll(V_Factura, Gp_Message);
        switch (cb_temas.getSelectionModel().getSelectedIndex()) {
            case 0:
                new JMetro(JMetro.Style.DARK).applyTheme(stack);
                break;
            case 1:
                new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                break;
            case 2:
                AeroFX.style();
                AeroFX.styleAllAsGroupBox(Gp_Factura);
                break;
            case 3:
                AquaFx.style();
                AquaFx.setGroupBox(Gp_Factura);
                break;
        }
        if (AbacoApp.cb_temas.getSelectionModel().getSelectedIndex() != 0) {
            B_Buscar.setStyle("-fx-background-color:#007fff");
            B_AgregarAbono.setStyle("-fx-background-color:#007fff");
            B_Buscarfacturas.setStyle("-fx-background-color:#007fff");
            B_New.setStyle("-fx-background-color:#007fff");
            B_Existencias.setStyle("-fx-background-color:#007fff");
            B_Insertar.setStyle("-fx-background-color:#007fff");
            B_Kardex.setStyle("-fx-background-color:#007fff");
            B_New.setStyle("-fx-background-color:#007fff");
            B_Save.setStyle("-fx-background-color:#007fff");
            B_Buscarproveedor.setStyle("-fx-background-color:#007fff");
            T_Total.setFill(Color.valueOf("#007fff"));

        } else {
            T_Total.setFill(Color.WHITE);
        }
        stack.setAlignment(Pos.TOP_CENTER);

        Ta_MateriaPrima.setDisable(true);
        Tf_Cantidad_Salida.setDisable(true);
        changeColumns();
        eventos_inputs();
        KeyCombination kc = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        Runnable task = () -> nuevaFactura();
        KeyCombination kg = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
        Runnable task2 = () -> {
            try {
                guardarFactura();
            } catch (ParseException ex) {
                Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        // Mnemonic mnemonic = new Mnemonic(B_New, kc);
        AbacoApp.getScene().getAccelerators().clear();
        AbacoApp.getScene().getAccelerators().put(kc, task);
        AbacoApp.getScene().getAccelerators().put(kg, task2);

        crearoeditar();

        Object[] _listFacturaItem = (AbacoApp.s_facturaproveedores.getFacturaItems()).toArray();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(_listFacturaItem);
        Ta_FacturaItems.setItems(Ol_FacturaItems);
        Gp_Proveedor.add(sp_proveedor, 1, 3, 2, 4);
        return stack;
    }

    private void buscarproducto() {
        List<FacturaItemProveedores> li_facFacturaItemProveedoreses = new ArrayList<>();
        li_facFacturaItemProveedoreses.addAll(AbacoApp.s_facturaproveedores.getFacturaItemsProveedores());
        List<FacturaItemProveedores> li_facFacturaItemProveedoreses2 = li_facFacturaItemProveedoreses.stream().filter(line -> line.getProduct().getCodigobarras().toUpperCase().contains(buscar.getText().toUpperCase().trim()) || line.getProduct().getNombre().toUpperCase().contains(buscar.getText().toUpperCase())).collect(Collectors.toList());
        //AbacoApp.s_facturaproveedores.setFacturaItemsProveedores(li_facFacturaItemProveedoreses2);
        O_ListFacturaItems = li_facFacturaItemProveedoreses2.toArray();
        Ol_FacturaItems.clear();

        Ol_FacturaItems.addAll(O_ListFacturaItems);
        Ta_FacturaItems.setItems(Ol_FacturaItems);
    }

    public void nuevaFactura() {
        if (!B_New.isDisabled()) {
            enabledAllTextField();
            AbacoApp.s_facturaproveedores = null;
            AbacoApp.s_facturaproveedores = new FacturaProveedores();
            Ol_FacturaItems.clear();

            Tf_NombreProveedor.setText("");
            Tf_NitProveedor.setText("");
            Tf_DireccionProveedor.setText("");
            Tf_TelefonoProveedor.setText("");
            Tf_Codigo.setText("");
            Rb_Credito.setSelected(false);
            Rb_Contado.setSelected(false);
            sb_producto.setText("");
            Tf_Valor.setText("0");
            Tf_Cantidad.setText("0");
            Tf_PrecioCompra.setText("0");
            Tf_Total.setText("0");
            buscar.setText("");
            fechavencimiento.setValue(LocalDate.now());
            referencia.setText("");
            descripcion.setText("");
            LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().select(-1);
            getnumerodigital();
            AbacoApp.s_facturaproveedores.setFacturaDate(new Date());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = df.format(AbacoApp.s_facturaproveedores.getFacturaDate());
            LocalDate ld = LocalDate.parse(fecha);
            Tf_Fecha.setValue(ld);

            Tf_ValorRecibido.setText("0");
            Tf_Total.setMinWidth(100);
            nuevo();
            L_Message.setText("Nueva Factura");
            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
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

    private void guardarFactura() throws ParseException, ParseException, ParseException, ParseException, ParseException {

        if (validar_guardar()) {

            try {
                AbacoApp.s_facturaproveedores.setNo_factura(Long.valueOf(Tf_NoFactura.getText().trim()));
            } catch (Exception e) {
                AbacoApp.s_facturaproveedores.setNo_factura(Long.valueOf("0"));
            }
            AbacoApp.s_facturaproveedores.setCredito(Rb_Credito.isSelected());
            AbacoApp.s_facturaproveedores = AbacoApp.facturaProveedoresControllerClient.guardarFactura(AbacoApp.s_facturaproveedores);
            LocalDate localDate = Tf_Fecha.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            AbacoApp.s_facturaproveedores.setFacturaDate(Date.from(instant));
            AbacoApp.s_facturaproveedores.setProveedores(AbacoApp.s_proveedores);
            AdminFacturaProveedores.getRecords();
            L_Message.setText("Factura Guardada");
            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            backgroundThread = new Thread(Task_Message);
            // Terminate the running thread if the application exits
            backgroundThread.setDaemon(true);

            // Start the thread
            backgroundThread.start();

        } else {
            animateMessage();
            L_Message.setText("Algunos Valores no se han digitado");
            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            backgroundThread = new Thread(Task_Message);
            // Terminate the running thread if the application exits
            backgroundThread.setDaemon(true);

            // Start the thread
            backgroundThread.start();

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
    Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {

            return null;
        }
    };

    public void addProduct() {
        actualizamosproducto();
        if (AbacoApp.s_producto != null && Integer.valueOf(Tf_Cantidad.getText().trim()) > 0) {
            AbacoApp.s_facturaproveedores.setProveedores(AbacoApp.s_proveedores);
            try {
                AbacoApp.s_facturaproveedores.setNo_factura(Long.valueOf(Tf_NoFactura.getText().trim()));
            } catch (Exception e) {
                AbacoApp.s_facturaproveedores.setNo_factura(Long.valueOf("0"));
            }
            AbacoApp.s_facturaproveedores.setCredito(Rb_Credito.isSelected());

            AbacoApp.s_facturaproveedores.addProduct(AbacoApp.s_producto, Float.valueOf(Tf_Cantidad.getText().trim()), BigDecimal.valueOf(Double.parseDouble(Tf_Valor.getText().trim())), null);
            AbacoApp.s_facturaproveedores = AbacoApp.facturaProveedoresControllerClient.crearFactura(
                    AbacoApp.s_facturaproveedores, buscar.getText(), fechavencimiento.getValue());

            Tf_Total.setText(String.valueOf(AbacoApp.s_facturaproveedores.getTotalAmount().longValue()));
            getnumerodigital();
            //Cargamos la tabla con el nuevo item
            O_ListFacturaItems = AbacoApp.s_facturaproveedores.getFacturaItems().toArray();
            Ol_FacturaItems.clear();

            Ol_FacturaItems.addAll(O_ListFacturaItems);
            Ta_FacturaItems.setItems(Ol_FacturaItems);
            nuevoitem();

            L_Message.setText("Item Agregado");
            Task_Message = () -> {
                try {

                    SetMessage();
                    AbacoApp.li_kardex = kardexControllerClient.getRecords();
                    List<Kardex> l_kardex = AbacoApp.li_kardex.stream().filter(distinctByKey(b -> b.getProducto())).collect(Collectors.toList());//.distinct().collect(Collectors.toList());
                    AbacoApp.li_kardex = l_kardex;
                } catch (InterruptedException ex) {
                    Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            backgroundThread = new Thread(Task_Message);
            // Terminate the running thread if the application exits
            backgroundThread.setDaemon(true);

            // Start the thread
            backgroundThread.start();
        } else {
            Tf_NitProveedor.setStyle("-fx-background-color:#ff1600");
            Tf_NombreProveedor.setStyle("-fx-background-color:#ff1600");
        }
        animateMessage();

    }

    public void deleteItem() {

        FacturaItemProveedores fi = (FacturaItemProveedores) Ta_FacturaItems.getFocusModel().getFocusedItem();
        AbacoApp.s_facturaproveedores.removeProduct(fi.getProduct());
        AbacoApp.facturaProveedoresControllerClient.crearFactura(AbacoApp.s_facturaproveedores, buscar.getText(), fechavencimiento.getValue());
        O_ListFacturaItems = AbacoApp.s_facturaproveedores.getFacturaItems().toArray();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(O_ListFacturaItems);
        Ta_FacturaItems.setItems(Ol_FacturaItems);

        Tf_Total.setText(String.valueOf(AbacoApp.s_facturaproveedores.getTotalAmount().longValue()));
        Gr_NumeroDigital.getChildren().remove(numerodigital);
        numerodigital = null;
        numerodigital = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50), Tf_Total.getText());
        numerodigital.setLayoutX(10);
        numerodigital.setLayoutY(45);
        numerodigital.getTransforms().add(new Scale(1f, 1f, 0, 0));
        Gr_NumeroDigital.getChildren().add(numerodigital);

    }

    public void modifyItem(String quantity) {
        FacturaItemProveedores fi = (FacturaItemProveedores) Ta_FacturaItems.getFocusModel().getFocusedItem();
        AbacoApp.s_facturaproveedores.modifyItem(fi.getProduct(), Float.valueOf(quantity.trim()));
        AbacoApp.facturaProveedoresControllerClient.crearFactura(AbacoApp.s_facturaproveedores, buscar.getText(), fechavencimiento.getValue());

        O_ListFacturaItems = AbacoApp.s_facturaproveedores.getFacturaItems().toArray();
        AbacoApp.li_kardex = AbacoApp.kardexControllerClient.getRecords();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(O_ListFacturaItems);
        Ta_FacturaItems.setItems(Ol_FacturaItems);
        Tf_Total.setText(String.valueOf(AbacoApp.s_facturaproveedores.getTotalAmount().longValue()));
        Gr_NumeroDigital.getChildren().remove(numerodigital);
        numerodigital = null;
        numerodigital = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50), Tf_Total.getText());
        numerodigital.setLayoutX(10);
        numerodigital.setLayoutY(45);
        numerodigital.getTransforms().add(new Scale(1f, 1f, 0, 0));
        Gr_NumeroDigital.getChildren().add(numerodigital);
    }

    public void changeColumns() {
        Tc_NoItem = new TableColumn();
        Tc_NoItem.setText("N° Item");
        Tc_NoItem.setMinWidth(130);
        Tc_NoItem.setCellValueFactory(new PropertyValueFactory("position"));

        Tc_codigo = new TableColumn();
        Tc_codigo.setText("Código");
        Tc_codigo.setMinWidth(150);
        Tc_codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getProduct().getCodigobarras());
            }
        });
        Tc_Producto = new TableColumn();
        Tc_Producto.setText("Producto");
        Tc_Producto.setMinWidth(250);
        Tc_Producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getProduct().getNombre());
            }
        });

        Tc_Cantidad = new TableColumn();
        Tc_Cantidad.setText("Cantidad");
        Tc_Cantidad.setMinWidth(150);
        Tc_Cantidad.setCellValueFactory(new PropertyValueFactory("quantity"));
        Tc_Cantidad.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        Tc_ValorU = new TableColumn();
        Tc_ValorU.setText("Valor/U");
        Tc_ValorU.setMinWidth(150);
        Tc_ValorU.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getValor_u().toString());
            }
        });

        Tc_ValorT = new TableColumn();
        Tc_ValorT.setText("V/Total");
        Tc_ValorT.setMinWidth(150);
        Tc_ValorT.setCellValueFactory(new PropertyValueFactory("valor_total"));

        Ta_FacturaItems.getColumns().clear();
        Ta_FacturaItems.setEditable(true);
        Ta_FacturaItems.getColumns().addAll(Tc_NoItem, Tc_codigo, Tc_Producto, Tc_Cantidad, Tc_ValorU, Tc_ValorT);
        //Ancho de tabla y ancho de porcentaje de columnas

        Ta_FacturaItems.setMinHeight(150);
        // GridPane.setFillWidth(veil, Boolean.TRUE);
        Ta_FacturaItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //Tc_NoItem.setMinWidth(Double.POSITIVE_INFINITY);
        //Tc_Producto.setMinWidth(Tc_Producto.getMaxWidth());
        //Tc_Cantidad.setMinWidth(Tc_Cantidad.getMaxWidth());
        //Tc_ValorU.setMinWidth(Tc_ValorU.getMaxWidth());
        //Tc_ValorT.setMinWidth(Tc_ValorT.getMaxWidth());
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
        if (AbacoApp.li_producto != null) {
            List<Producto> li_producto = AbacoApp.li_producto.stream().filter(line -> line.getCodigobarras().toUpperCase().equals(Tf_Codigo.getText().trim())) //filters the line, equals to "mkyong"
                    .collect(Collectors.toList());
            if (li_producto.size() > 0) {
                AbacoApp.s_producto = li_producto.get(0);
                setProducto();
                try {
                    Tf_Cantidad.requestFocus();
                } catch (Exception e) {

                }
            } else {
                AbacoApp.s_producto = null;
                AbacoApp.s_producto = new Producto();
                sb_producto.getSelectedText();
                try {
                    sb_producto.requestFocus();
                } catch (Exception e) {

                }

            }

        } else {
            sb_producto.getSelectedText();
            try {
                sb_producto.requestFocus();
            } catch (Exception e) {

            }

        }
        sp_producto.hide();
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
        Nd_ValorCambio = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50), "000.000");
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(1f, 1f, 0, 0));
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
        Gp_VlrRecibido.add(B_Print, 2, 1);
        Gp_VlrRecibido.getStyleClass().add("solucion-gridpane-background");

    }

    public void addGp_VlrRecibido() {
        // add background and clock to sample
        if (Gp_FacturaItems.getChildren().indexOf(Gp_VlrRecibido) == -1) {
            Gp_FacturaItems.add(Gp_VlrRecibido, 0, 1);
            Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
            Nd_ValorCambio = null;
            Nd_ValorCambio = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50), "000.000");
            Nd_ValorCambio.setLayoutX(10);
            Nd_ValorCambio.setLayoutY(5);
            Nd_ValorCambio.getTransforms().add(new Scale(1f, 1f, 0, 0));
            Gr_ValorCambio.getChildren().add(Nd_ValorCambio);
        }

    }

    public void removeGp_VlrRecibido() {
        if (Gp_FacturaItems.getChildren().indexOf(Gp_VlrRecibido) != -1) {
            Gp_FacturaItems.getChildren().remove(Gp_VlrRecibido);
        }
    }

    public void showVlrCambio() {

        AbacoApp.s_facturaproveedores.setEfectivo(BigDecimal.valueOf(Double.parseDouble(Tf_ValorRecibido.getText().trim())));
        AbacoApp.facturaProveedoresControllerClient.crearFactura(AbacoApp.s_facturaproveedores, buscar.getText(), fechavencimiento.getValue());

        Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
        Nd_ValorCambio = null;
        Nd_ValorCambio = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50), AbacoApp.s_facturaproveedores.getDevolver().toString());
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(1f, 1f, 0, 0));

        Gr_ValorCambio.getChildren().add(Nd_ValorCambio);
        B_Print.requestFocus();
        cronometroRemoveGp_VlrRecibido();
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

    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), sb_producto);
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
                Tf_Iva.setStyle(null);
                Tf_Iva.getStyleClass().add("text-field");
                Tf_NitProveedor.setStyle(null);
                Tf_NitProveedor.getStyleClass().add("text-field");
                Tf_NombreProveedor.setStyle(null);
                Tf_NombreProveedor.getStyleClass().add("text-field");
                Tf_TelefonoProveedor.setStyle(null);
                Tf_TelefonoProveedor.getStyleClass().add("text-field");
                Ta_FacturaItems.setStyle(null);
                fechavencimiento.setStyle(null);
                LoadChoiceBoxGeneral.getCb_unidadesmedida().setStyle(null);

            }
        });

    }

    public void ImportarExcel() throws IOException {

        file = fileChooser.showOpenDialog(stage);

        Categoria categoria;
        Proveedores proveedores;
        Producto producto;
        Kardex kardex;

        KardexControllerClient kardexJerseyClient;

        if (file != null) {
            System.out.println(file.getAbsolutePath());
            Workbook w;
            try {
                w = Workbook.getWorkbook(file);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd mm yyyy hh:mm:ss");

                Sheet sheet = w.getSheet(0);

                for (int j = 1; j < sheet.getRows(); j++) {

                    Cell codigosbarras = sheet.getCell(0, j);
                    Cell nombreproducto = sheet.getCell(1, j);
                    Cell preciounitariocompra = sheet.getCell(2, j);
                    Cell cant = sheet.getCell(3, j);
                    Cell cporcentajeganancia = sheet.getCell(4, j);
                    Cell observaciones = sheet.getCell(5, j);
                    Cell ubicacion = sheet.getCell(6, j);
                    Cell stockmin = sheet.getCell(7, j);
                    Cell stockmax = sheet.getCell(8, j);

                    categoria = null;
                    proveedores = null;
                    producto = null;

                    if (!codigosbarras.getContents().equals("")) {
                        producto = new Producto();
                        sb_producto.setText(nombreproducto.getContents());
                        Tf_Codigo.setText(codigosbarras.getContents().trim());
                        Tf_PrecioCompra.setText(preciounitariocompra.getContents());

                        Tf_Cantidad.setText(cant.getContents());
                        Tf_Valor.setText(String.valueOf((new BigDecimal(Tf_Cantidad.getText().trim()).multiply(new BigDecimal(Tf_PrecioCompra.getText())).longValue())));
                        ex_observaciones = observaciones.getContents();
                        ex_ubicacion = ubicacion.getContents();
                        ex_stockmin = stockmin.getContents();
                        ex_stockmax = stockmax.getContents();
                        porcentajeganancia.setText(cporcentajeganancia.getContents().trim());
                        findByCodigo();
                        addProduct();
                    } else {

                        thimport = null;
                        break;
                    }

                }

                //*********************************** formato 2
                // }
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (Throwable ex) {
                Logger.getLogger(FFacturaProveedor.class.getName()).log(Level.SEVERE, null, ex);
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

    private void calcularValorTotal() {
        Tf_Valor.setText(String.valueOf((new BigDecimal(Tf_Cantidad.getText().trim()).multiply(new BigDecimal(Tf_PrecioCompra.getText())).longValue())));

    }

    private void setProducto() {

        sb_producto.setText(AbacoApp.s_producto.getNombre());

    }

    public void crearoeditar() {
        enabledAllTextField();
        AbacoApp.s_producto = null;
        AbacoApp.s_producto = new Producto();

        if (AbacoApp.s_facturaproveedores != null) {
            try {
                Tf_NoFactura.setText(AbacoApp.s_facturaproveedores.getNo_factura() != null ? AbacoApp.s_facturaproveedores.getNo_factura().toString() : "0");
            } catch (Exception e) {

            }
            try {
                sb_proveedor.setText(AbacoApp.s_facturaproveedores.getProveedores() != null ? AbacoApp.s_facturaproveedores.getProveedores().getNombre() : "");
                sp_proveedor.hide();
            } catch (Exception e) {

            }
            AbacoApp.s_proveedores = AbacoApp.s_facturaproveedores.getProveedores();
            try {
                Tf_NitProveedor.setText(AbacoApp.s_facturaproveedores.getProveedores().getNo_ident());
            } catch (Exception e) {

            }
            try {
                Tf_TelefonoProveedor.setText(AbacoApp.s_facturaproveedores.getProveedores().getCelular());
            } catch (Exception e) {

            }
            try {
                Tf_DireccionProveedor.setText(AbacoApp.s_facturaproveedores.getProveedores().getDir1());
            } catch (Exception e) {

            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFab = df.format(AbacoApp.s_facturaproveedores.getFacturaDate());
            LocalDate ld = LocalDate.parse(fechaFab);
            Tf_Fecha.setValue(ld);
            Tf_Total.setText(String.valueOf(AbacoApp.s_facturaproveedores.getTotalAmount().longValue()));
            getnumerodigital();
            if (AbacoApp.s_facturaproveedores.getCredito() == null) {
                Rb_Credito.setSelected(true);
            } else {
                if (AbacoApp.s_facturaproveedores.getCredito()) {
                    Rb_Credito.setSelected(true);
                } else {
                    Rb_Contado.setSelected(true);
                }
            }

            O_ListFacturaItems = AbacoApp.s_facturaproveedores.getFacturaItems().toArray();
            Ol_FacturaItems.clear();

            Ol_FacturaItems.addAll(O_ListFacturaItems);
            Ta_FacturaItems.setItems(Ol_FacturaItems);
            sb_producto.setText("");
            Tf_Valor.setText("0");
            Tf_Cantidad.setText("0");
            Tf_PrecioCompra.setText("0");
            Tf_Codigo.setText("");
            buscar.setText("");
            fechavencimiento.setValue(LocalDate.now());
            referencia.setText("");
            descripcion.setText("");
            LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().select(-1);
            Tf_Precio.setText("0");
            Tf_Iva.setText("0");
            Tf_PrecioCompra.setText("0");
        } else {
            AbacoApp.s_facturaproveedores = new FacturaProveedores();
            nuevo();
        }
    }

    public void getLastItemsMateriaPrima() {
        O_ListFacturaItemsI = AbacoApp.facturaProveedoresControllerClient.getLastItemsmateriaPrima(Tf_NitProveedor.getText().trim()).toArray();
        Ol_FacturaItemsI.clear();
        Ol_FacturaItemsI.addAll(O_ListFacturaItemsI);
        Ta_MateriaPrima.setItems(Ol_FacturaItemsI);
    }

    private void show() {
        String appClass;
        Class clz;
        Object app;
        Parent parent;

        appClass = "abaco.soluciones.informacionadministrativa.FProducto";
        clz = null;
        try {
            clz = Class.forName(appClass);
            app = clz.newInstance();

            parent = null;
            parent = (Parent) clz.getMethod("createContent").invoke(app);
            scene = null;
            scene = new Scene(parent, Color.TRANSPARENT);

            if (stage.isShowing()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showproveedor() {
        String appClass;
        Class clz;
        Object app;
        Parent parent;

        appClass = "abaco.soluciones.informacionadministrativa.FProveedor";
        clz = null;
        try {
            clz = Class.forName(appClass);
            app = clz.newInstance();

            parent = null;
            parent = (Parent) clz.getMethod("createContent").invoke(app);
            scene = null;
            scene = new Scene(parent, Color.TRANSPARENT);
            stageproveedor.setTitle("Proveedores");
            if (stageproveedor.isShowing()) {
                stageproveedor.close();
            }

//set scene to stage
            stageproveedor.sizeToScene();

            stageproveedor.centerOnScreen();
            stageproveedor.setScene(scene);

            stageproveedor.show();
        } catch (Exception e) {

        }
    }

    private void showKardex() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        P_Kardex = null;
        P_Kardex = (Parent) clzKardex.getMethod("createContent").invoke(appKardex);
        scene = null;
        scene = new Scene(P_Kardex, Color.TRANSPARENT);

        if (stageKardex.isShowing()) {
            stageKardex.close();
        }

//set scene to stage
        stageKardex.sizeToScene();

        //center stage on screen
        stageKardex.centerOnScreen();
        stageKardex.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageKardex.show();
    }

    private void showExistencias() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        P_Existencias = null;
        P_Existencias = (Parent) clzExistencias.getMethod("createContent").invoke(appExistencias);
        scene = null;
        scene = new Scene(P_Existencias, Color.TRANSPARENT);

        if (stageExistencias.isShowing()) {
            stageExistencias.close();
        }

//set scene to stage
        stageExistencias.sizeToScene();

        //center stage on screen
        stageExistencias.centerOnScreen();
        stageExistencias.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageExistencias.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void nuevo() {
        try {
            Tf_Codigo.requestFocus();
        } catch (Exception e) {

        }
        Tf_Precio.setDisable(false);
        Tf_Iva.setDisable(false);
        Tf_Iva.setText("0");
        Tf_PrecioCompra.setDisable(false);
        Tf_Codigo.setDisable(false);
        sb_producto.setDisable(false);
        Tf_Cantidad.setDisable(false);
        Tf_Valor.setDisable(false);
        sb_producto.setText("");
        Tf_Valor.setText("0");
        Tf_Cantidad.setText("0");
        Tf_PrecioCompra.setText("0");
        Tf_Codigo.setText("");
        Tf_Precio.setText("0");
        Tf_Iva.setText("0");
        Tf_PrecioCompra.setText("0");
        Ch_EsMateriaPrima.setSelected(false);
        Ch_AddMateriaPrima.setSelected(false);
        Ch_AddMateriaPrima.getOnMouseClicked();
        AbacoApp.s_producto = null;
        AbacoApp.s_producto = new Producto();
        buscar.setText("");
        fechavencimiento.setValue(LocalDate.now());
        referencia.setText("");
        descripcion.setText("");
        LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().select(-1);
        Tf_Fecha.requestFocus();
        AbacoApp.s_proveedores = null;
        AbacoApp.s_proveedores = new Proveedores();
    }

    private void nuevoitem() {
        try {
            Tf_Codigo.requestFocus();
        } catch (Exception e) {

        }

        Tf_PrecioCompra.setDisable(false);
        Tf_Codigo.setDisable(false);
        sb_producto.setDisable(false);
        Tf_Cantidad.setDisable(false);
        Tf_Valor.setDisable(false);
        sb_producto.setText("");
        Tf_Valor.setText("0");
        Tf_Cantidad.setText("0");
        Tf_PrecioCompra.setText("0");
        Tf_Codigo.setText("");
        Tf_Precio.setText("0");
        Tf_Iva.setText("0");
        Tf_PrecioCompra.setText("0");

        AbacoApp.s_producto = null;
        AbacoApp.s_producto = new Producto();
        buscar.setText("");
        fechavencimiento.setValue(LocalDate.now());
        referencia.setText("");
        descripcion.setText("");

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

    public boolean validar_guardar() {
        boolean m_BCanSave = true;

        if (sb_proveedor.getText().equals("")) {
            Tf_NombreProveedor.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }
        if (Tf_NitProveedor.getText().equals("")) {
            Tf_NitProveedor.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }

        if (Ta_FacturaItems.getItems().size() <= 0) {
            // Ta_FacturaItems.setStyle("-fx-background-color:#ff1600");
            // m_BCanSave=false;
        }

        return m_BCanSave;

    }

    public void eventos_inputs() {
        Tf_NombreProveedor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                Tf_DireccionProveedor.requestFocus();

            }
        });
        Tf_NombreProveedor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                Tf_DireccionProveedor.requestFocus();

            }
        });
        Tf_DireccionProveedor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                Tf_TelefonoProveedor.requestFocus();

            }
        });
        Tf_TelefonoProveedor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                Rb_Contado.requestFocus();

            }
        });

        Tf_Codigo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                if (Tf_Codigo.getText() != null) {
                    findByCodigo();
                }

            }
        });

        referencia.setOnAction(e -> {
            descripcion.requestFocus();
        });

        Tf_NoFactura.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

            }
        });
        Tf_Iva.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                LoadChoiceBoxGeneral.getCb_unidadesmedida().requestFocus();

            }
        });

        Tf_Valor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent keyEvent) {

                B_Insertar.requestFocus();

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

                    removeGp_VlrRecibido();
                }
            }
        });
        Tf_NoFactura.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                try {
                    Float.valueOf(Tf_NoFactura.getText());

                } catch (Exception e) {
                    keyEvent.consume();
                    Tf_NoFactura.deleteNextChar();
                    Tf_NoFactura.backward();

                }
            }
        });

        Rb_Contado.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                Rb_Credito.requestFocus();
            }
        });

        Rb_Credito.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                Tf_Codigo.requestFocus();
            }
        });

        fechavencimiento.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                Tf_PrecioCompra.requestFocus();
            }
        });
        Ch_EsMateriaPrima.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                referencia.requestFocus();
            }
        });
        descripcion.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                Tf_Iva.requestFocus();
            }
        });
        LoadChoiceBoxGeneral.getCb_unidadesmedida().setOnAction(e -> {
            B_SaveProd.requestFocus();

        }
        );
        buscar.setOnAction(e -> {
            fechavencimiento.requestFocus();
        }
        );

        Tf_PrecioCompra.setOnAction(e -> {
            calcularValorTotal();
            Tf_Valor.requestFocus();
        });
        Tf_Cantidad.setOnAction(e -> {
            Tf_PrecioCompra.requestFocus();
        });
        Tf_Fecha.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                Tf_NoFactura.requestFocus();
            }
        });
        Tf_NoFactura.setOnAction(e -> {
            Tf_NitProveedor.requestFocus();
        });

        B_Insertar.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                addProduct();
                Tf_Codigo.requestFocus();
            }
        });
        B_New.setOnAction(e -> {

            nuevaFactura();

        });
        bu_importardesdeexcel.setOnAction(e -> {

            try {
                ImportarExcel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        B_SaveProd.requestFocus();
    }

    public void findByProducto() {
        if (AbacoApp.s_producto != null) {
            if (AbacoApp.s_producto.getId() != null) {
                sb_producto.setText(AbacoApp.s_producto.getNombre());
                Tf_Codigo.setText(AbacoApp.s_producto.getCodigobarras());
                sp_producto.hide();
                Tf_Cantidad.requestFocus();

            } else {
                sb_producto.getSelectedText();
                Tf_Cantidad.requestFocus();
                AbacoApp.s_producto = null;
                AbacoApp.s_producto = new Producto();
            }

        } else {
            sb_producto.getSelectedText();
            Tf_Cantidad.requestFocus();
            AbacoApp.s_producto = null;
            AbacoApp.s_producto = new Producto();
        }
    }

    public void getnumerodigital() {

        Gr_NumeroDigital.getChildren().remove(numerodigital);
        numerodigital = null;
        if (AbacoApp.cb_temas.getSelectionModel().getSelectedIndex() == 0) {
            numerodigital = new NumeroDigital(Color.valueOf("#007fff"), Color.rgb(50, 50, 50), Tf_Total.getText());
        } else {
            numerodigital = new NumeroDigital(Color.valueOf("#007fff"), Color.TRANSPARENT, Tf_Total.getText());
        }
        numerodigital.setLayoutX(10);
        numerodigital.setLayoutY(5);
        numerodigital.getTransforms().add(new Scale(0.93f, 0.93f, 0, 0));
        Gr_NumeroDigital.getChildren().add(numerodigital);
    }

    private void actualizamosproducto() {
        System.out.println(sb_producto.getText() + " " + Tf_Codigo.getText() + " " + Tf_PrecioCompra.getText() + " " + Tf_Cantidad.getText() + " " + porcentajeganancia.getText());

        if (AbacoApp.s_producto == null) {
            AbacoApp.s_producto = new Producto();

            AbacoApp.s_producto.setCodigobarras(Tf_Codigo.getText().trim());
            AbacoApp.s_producto.setNombre(sb_producto.getText());
            AbacoApp.s_producto.setPrecio(((new BigDecimal(porcentajeganancia.getText().trim()).multiply(BigDecimal.valueOf(0.01))).multiply(new BigDecimal(Tf_PrecioCompra.getText().trim()))).setScale(0, RoundingMode.HALF_UP).add(new BigDecimal(Tf_PrecioCompra.getText().trim())).setScale(0, RoundingMode.HALF_UP));
            AbacoApp.s_producto.setDescrip(ex_ubicacion);
            AbacoApp.s_producto.setObservaciones(ex_observaciones);
            AbacoApp.s_producto.setProveedores(AbacoApp.s_proveedores);
            AbacoApp.s_producto.setTipo("0");
            try {
                AbacoApp.s_producto.setTopminimo(Integer.valueOf(ex_stockmin));
            } catch (Exception e) {
                AbacoApp.s_producto.setTopminimo(Integer.valueOf(0));
            }
            try {
                AbacoApp.s_producto.setTopmaximo(Integer.valueOf(ex_stockmax));
            } catch (Exception e) {
                AbacoApp.s_producto.setTopmaximo(Integer.valueOf(0));
            }
        }
        if (AbacoApp.s_producto.getId() == null) {
            AbacoApp.productoControllerClient.create();
        } else {
            AbacoApp.productoControllerClient.update();
        }
        AbacoApp.li_producto = AbacoApp.productoControllerClient.getRecords(null);
    }

    public void actualizarProductos() {
        Task_Message = () -> {

            AbacoApp.li_producto = AbacoApp.productoControllerClient.getRecords(null);

        };
        backgroundThread = new Thread(Task_Message);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);

        // Start the thread
        backgroundThread.start();

    }

}
