package sihic.contabilidad;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;


import modelo.FacturaItemProveedores;
import modelo.FacturaProveedores;
import modelo.GenCategoriasProductos;
import modelo.HclCups;
import modelo.Producto;
import modelo.Proveedores;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.controlador.FacturaProveedoresControllerClient;
import sihic.controlador.ProductoControllerClient;
import sihic.controlador.ProductoJerseyClient;
import sihic.controlador.ProveedoresControllerClient;
import sihic.digitos.NumeroDigital;
import sihic.historiasclinicas.historialpaciente.BuscarCups;

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
public class FInventario extends Application {

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
    private HBox H_BotonesInv;
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
    private Button bu_agregarcups;
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
    private Label L_nocheque;
    private Label L_notargeta;
    private Label la_iva;
    //Campos de texto datos cliente 
    private TextField Tf_NombreProveedor;
    private TextField Tf_NitProveedor;
    private TextField Tf_DireccionProveedor;
    private TextField Tf_TelefonoProveedor;
    private TextField Tf_Fecha;
    private TextField Tf_NoFactura;
    private TextField Tf_Producto;
    private TextField Tf_Valor;
    private TextField Tf_Cantidad;
    private TextField Tf_Total;
    private TextField Tf_ValorRecibido;
    private SearchBox Tf_Codigo = new SearchBox();
    private SearchPopover sp_producto;
    private TextField Tf_Precio;
    private TextField Tf_PrecioCompra;
    private TextField Tf_Iva;
    private TextField nocheque;
    private TextField targeta;
    private Label la_gencategorias;
    private ChoiceBox gencategorias;
    private Vector<GenCategoriasProductos> v_gencategorias = new Vector<GenCategoriasProductos>();
    //list box
    private static ChoiceBox Cb_Empleados;
    //radio button
    private RadioButton Rb_Contado;
    private RadioButton Rb_Credito;
    private DecimalFormat format = new DecimalFormat("#.0");
    //Vbox
    private HBox V_box;
    private VBox V_Factura;
    private HBox H_Botones;
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
    //columnas tabla factura
    private TableColumn Tc_ProductoI;
    private TableColumn Tc_CantidadI;
    private TableColumn Tc_ValorUI;
    private TableColumn Tc_ValorTI;
    //Objetos de persistencia y modelso de datos
    FacturaProveedoresControllerClient facturaProveedoresControllerClient;
    ProveedoresControllerClient proveedoresControllerClient;
    ProductoControllerClient productoControllerClient;
    //Stage mostrar formulario findproducto
    private FindAllProduct findProduct;
    private FacturaProveedores facturaProveedores;
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
    // private PagarFacturaCompra pagarfactura;
    private Label L_EsMateriaPrima;
    private CheckBox Ch_EsMateriaPrima;
    private CheckBox Ch_AddMateriaPrima;
    // private GananciasMateriaPrima gananciasMateriaPrima;
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
    private Stage stage3 = new Stage(StageStyle.DECORATED);
    private Stage stageKardex = new Stage(StageStyle.DECORATED);
    private Stage stageExistencias = new Stage(StageStyle.DECORATED);

    Scene scene = null;

    private ImageView imageView;
    private BuscarCups buscarCups;
    private Label la_cups;
    private SearchBox cups = new SearchBox();
    private SearchPopover searchPopover;
    private HBox hb_codigoprod;
    /**
     * ** Inicalizamos objetos
     *///
    private Label la_principioactivo;
    private TextField principioactivo;
    private Label la_concentracion;
    private TextField concentracion;
    private Label la_presentacioncomercial;
    private TextField presentaciocomercial;
    private Label la_medformasfarmaceuticas;
    private TextField medformasfarmaceuticas;
    private Label la_laboratorio;
    private TextField laboratorio;
    private Label la_unidadmedida;
    private TextField unidamedida;
    private Label la_medida;
    private ChoiceBox medida;
    private Label la_reginvima;
    private TextField reginvima;
    private Label la_lote;
    private TextField lote;
    private Label la_marca;
    private TextField marca;
    private Label la_vencimiento;
    private DatePicker fechavencimiento;
    private String inventario = "14210100002";
    private TextField precio2015;
    private TextField precio2016;
    private TextField precio2017;
    private Label la_precio2015;
    private Label la_precio2016;
    private Label la_precio2017;

    public Parent createContent() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        searchPopover = new SearchPopover(cups, new PageBrowser(), SihicApp.s_hclCups, false);

        precio2015 = new TextField();
        //precio2015.setPromptText("2015");
        precio2016 = new TextField();
        // precio2016.setPromptText("2016");
        la_precio2015 = new Label("Precio 2015");
        la_precio2016 = new Label("Precio 2016");
        la_precio2017 = new Label("Precio 2017");
        precio2017 = new TextField();
        precio2015.setTextFormatter(new TextFormatter<>(c
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
        precio2016.setTextFormatter(new TextFormatter<>(c
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
        precio2017.setTextFormatter(new TextFormatter<>(c
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

        cups.setMinWidth(250);
        Tf_Codigo.setMinWidth(150);
        searchPopover.getSearchBox().setOnAction(e -> {
            if (!searchPopover.getSearchBox().getText().trim().equals("")) {
                Tf_Codigo.setText(SihicApp.s_hclCups.getCodigo());
                Tf_Producto.setText(SihicApp.s_hclCups.getDescripcion());
                Tf_Codigo.requestFocus();
                SihicApp.s_producto = null;
            }

        });
        Tf_Codigo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue.booleanValue()) {
                    SihicApp.s_producto = null;
                } else {
                    findByCodigo();
                    Tf_Producto.requestFocus();
                    sp_producto.hide();
                }

            }
        });
        sp_producto = new SearchPopover(Tf_Codigo, new PageBrowser(), SihicApp.s_producto, false);
        sp_producto.getSearchBox().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                findByCodigo();
                Tf_Producto.requestFocus();
            }
        });
        sp_producto.getSearchResultPopoverList().setOnMouseClicked(e -> {
            if (SihicApp.s_producto != null) {
                Tf_Codigo.setText(SihicApp.s_producto.getCodigo());
                findByCodigo();
                Tf_Producto.requestFocus();
                sp_producto.hide();
            }

        });
        stageKardex.setTitle("Kardex");
        stageExistencias.setTitle("Existencias");
        H_BotonesInv = new HBox();
        la_concentracion = new Label("Concentración:");
        la_concentracion.setMinWidth(130);
        concentracion = new TextField();

        la_principioactivo = new Label("Principio Activo:");
        la_principioactivo.setMinWidth(130);
        principioactivo = new TextField();
        principioactivo.setMinWidth(130);
        la_laboratorio = new Label("Laboratorio:");
        la_laboratorio.setMinWidth(130);
        laboratorio = new TextField();
        laboratorio.setMinWidth(130);
        la_lote = new Label("Lote:");
        la_lote.setMinWidth(130);
        lote = new TextField();
        lote.setMinWidth(130);
        la_marca = new Label("Marca:");
        la_marca.setMinWidth(130);
        marca = new TextField();
        marca.setMinWidth(130);
        la_medida = new Label("Tipo de Medto:");
        medida = new ChoiceBox();
        medida.getItems().add(0, "POS");
        medida.getItems().add(1, " NO POS");
        la_reginvima = new Label("Registro Invima:");
        reginvima = new TextField();
        la_medformasfarmaceuticas = new Label("F. Farmaceutica:");
        medformasfarmaceuticas = new TextField();

        la_presentacioncomercial = new Label("P. Comercial:");
        presentaciocomercial = new TextField();
        la_unidadmedida = new Label("Unidad Medida:");
        unidamedida = new TextField();
        la_vencimiento = new Label("Fecha Vencimiento:");
        fechavencimiento = new DatePicker(LocalDate.now());

        hb_codigoprod = new HBox(3);
        gencategorias = new ChoiceBox();
        gencategorias.autosize();
        //gencategorias.setMinWidth(160);
        gencategorias.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);

        la_gencategorias = new Label("Categoria:");
        stack = new StackPane();
        appClassKardex = "sihic.contabilidad.KardexPorProducto";
        clzKardex = Class.forName(appClassKardex);
        //Objetos gridpane
        appKardex = clzKardex.newInstance();
        appClassExistencias = "sihic.contabilidad.Existencias";
        clzExistencias = Class.forName(appClassExistencias);
        //Objetos gridpane
        appExistencias = clzExistencias.newInstance();
        //  gananciasMateriaPrima=new GananciasMateriaPrima();

        /**
         * *********************************
         */
        buscarCups = new BuscarCups();
        imageView = new ImageView("/images/procedimiento.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        bu_agregarcups = new Button("", imageView);

        la_cups = new Label("Cups:");

        L_Cantidad_Salida = new Label("Cantidad Salida(Unidades): ");
        Tf_Cantidad_Salida = new TextField("0");
        L_EsMateriaPrima = new Label("Materia Prima: ");
        L_Habilitar = new Label("Habilitar Materia Prima: ");
        Ch_EsMateriaPrima = new CheckBox();
        Ch_AddMateriaPrima = new CheckBox();
        Ta_MateriaPrima = new TableView();
        Tc_ProductoI = new TableColumn();
        Tc_ProductoI.setText("Producto");
        Tc_ProductoI.setMinWidth(150);
        Tc_ProductoI.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getProducto().getNombre());
            }
        });

        Tc_CantidadI = new TableColumn();
        Tc_CantidadI.setText("Cantidad");
        Tc_CantidadI.setCellValueFactory(new PropertyValueFactory("quantity"));
        Tc_CantidadI.setCellFactory(TextFieldTableCell.forTableColumn(sc));

        Tc_ValorUI = new TableColumn();
        Tc_ValorUI.setText("Valor/U");
        Tc_ValorUI.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getValor_u().toString());
            }
        });

        Tc_ValorTI = new TableColumn();
        Tc_ValorTI.setText("Valor Total");
        Tc_ValorTI.setCellValueFactory(new PropertyValueFactory("valor_total"));
        Ta_MateriaPrima.getColumns().addAll(Tc_ProductoI, Tc_CantidadI, Tc_ValorTI);
        //******************************************************************************
        //pagarfactura =new PagarFacturaCompra();
        proveedor = new Proveedores();
        //Objetos gridpane
        Gp_Factura = new GridPane();
        Gp_DatosEmpresa = new GridPane();
        Gp_Proveedor = new GridPane();
        Gp_Total = new GridPane();
        Gp_Producto = new GridPane();
        Gp_MateriaPrima = new GridPane();
        Gp_Inventario = new GridPane();
        Gp_FacturaItems = new GridPane();
        Gp_Totales = new GridPane();
        Gp_VlrRecibido = new GridPane();
        //Task
        findProduct = new FindAllProduct();
        Thread th = new Thread(task);
        th.setDaemon(false);
        th.start();
        //campos de texto
        //datos cliente
        Tf_NombreProveedor = new TextField();
        Tf_NitProveedor = new TextField();
        Tf_DireccionProveedor = new TextField();
        Tf_TelefonoProveedor = new TextField();
        Tf_Fecha = new TextField();
        Tf_NoFactura = new TextField();
        Tf_NoFactura.setMinWidth(250);
        nocheque = new TextField();
        targeta = new TextField();
        //datos factura
        Tf_Producto = new TextField();
        // Tf_Producto.setMinWidth(120);

        Tf_Valor = new TextField("0");
        Tf_Valor.setTextFormatter(new TextFormatter<>(c
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
        Tf_Valor.setMinWidth(100);
        Tf_Cantidad = new TextField("0");
        Tf_Cantidad.setTextFormatter(new TextFormatter<>(c
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
        Tf_Cantidad.setMinWidth(100);

        Tf_Precio = new TextField("0");
        Tf_Precio.setTextFormatter(new TextFormatter<>(c
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
        Tf_Iva = new TextField("0");
        Tf_Iva.setTextFormatter(new TextFormatter<>(c
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
        Tf_Total = new TextField("0");
        Tf_Total.setTextFormatter(new TextFormatter<>(c
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
        Tf_Total.setMaxWidth(150);
        Tf_TelefonoProveedor = new TextField("");
        Tf_ValorRecibido = new TextField("0");
        Tf_ValorRecibido.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        Tf_ValorRecibido.setMinHeight(30);
        Tf_PrecioCompra = new TextField("0");
        Tf_PrecioCompra.setMinWidth(100);
        //Inicializamos objetos de pesistencia
        facturaProveedoresControllerClient = new FacturaProveedoresControllerClient();
        productoControllerClient = new ProductoControllerClient();
        facturaProveedores = new FacturaProveedores();
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
        Object[] _listFacturaItem = (facturaProveedores.getFacturaItems()).toArray();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(_listFacturaItem);
        Ta_FacturaItems.setItems(Ol_FacturaItems);
        V_Factura = new VBox();

        //botones
        B_Print = new Button();
        ImageView imageView;

        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        B_New = new Button("", imageView);
        // B_New.setMaxWidth(130);
        imageView = null;
        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        B_Buscar = new Button();
        B_Buscar.setGraphic(imageView);
        B_Buscar.setText("");
        //B_Buscar.setMaxWidth(130);
        B_Buscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                findProduct.show();
                findProduct.getTf_Search().requestFocus();

            }
        });
        B_verGanancias = new Button("Ver Ganancias");
        B_verGanancias.setMinWidth(130);
        B_verGanancias.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                //gananciasMateriaPrima.show();
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
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        imageView = null;
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        B_Save = new Button("", imageView);
        //B_Save.setMaxWidth(130);
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        B_SaveProd = new Button();
        B_SaveProd.setGraphic(imageView);
        B_SaveProd.setText("");
        //B_SaveProd.setMinWidth(130);
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        B_Insertar = new Button();
        B_Insertar.setGraphic(imageView);
        B_Insertar.setText("");
        //B_Insertar.setMinWidth(130);
        imageView = null;
        imageView = new ImageView("/images/bs_excel.jpg");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        B_AgregarAbono = new Button("Agregar Abono", imageView);
        B_VerAbonos = new Button("Ver Pedidos y Pagos");
        B_VerAbonos.setMaxWidth(130);
        B_AgregarAbono.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                // pagarfactura.PagarFacturaOVerPagos(true,facturaProveedores);
                // pagarfactura.show();

            }
        });
        B_VerAbonos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    show();
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        //acciones de botones y cuadros de texto
        B_Insertar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                addProduct();

            }
        });
        B_Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                guardarFactura();

                Tf_ValorRecibido.requestFocus();
            }
        });
        B_SaveProd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                guardarProducto();

                Tf_PrecioCompra.requestFocus();
            }
        });
        B_New.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                nuevaFactura();
            }
        });

        findProduct.getStage().setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    if (findProduct.getProducto() != null) {
                        setProducto();
                        Tf_Cantidad.requestFocus();
                    }
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

        toggleGroup = new ToggleGroup();

        Rb_Contado = new RadioButton("Contado");
        Rb_Contado.setMinWidth(100);
        Rb_Contado.setToggleGroup(toggleGroup);
        Rb_Contado.setStyle("-fx-text-fill: white;");
        Rb_Credito = new RadioButton("Credito");
        Rb_Credito.setMinWidth(100);
        Rb_Credito.setToggleGroup(toggleGroup);
        Rb_Credito.setStyle("-fx-text-fill: white;");

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
        L_nocheque = new Label("N° Cheque:");
        L_notargeta = new Label("N° Targeta:");
        L_Producto = new Label("Producto: ");
        //  L_Producto.setMinWidth(100);
        L_Cantidad = new Label("Cantidad: ");
        L_Cantidad.setMinWidth(70);
        L_Codigo = new Label("Código: ");

        L_Precio = new Label("Precio de Venta: ");
        L_Precio.setMinWidth(110);
        L_Iva = new Label("Iva (%):");

        L_Valor = new Label("Valor:");
        L_Valor.setMinWidth(50);

        L_Total = new Label("Total: ");
        L_Total.setMinWidth(100);
        L_F6 = new Label("F6 -----> Imprimir Factura");
        L_F7 = new Label("F7 -----> Guardar y Nueva Factura");
        L_F6.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 10));
        L_F7.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 10));

        L_ValorRecibido = new Label("Vlr Recibido: ");
        L_ValorRecibido.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        L_ValorCambio = new Label("Vlr Cambio: ");
        L_ValorCambio.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        T_Total = new Text("TOTAL");
        T_Total.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_Total.setFont(Font.font("ARIAL", FontWeight.BOLD, 30));
        T_Total.setFill(Color.WHITE);
        L_PrecioCompra = new Label("Precio de Compra: ");
        L_PrecioCompra.setMinWidth(130);
        L_MateriaPrima = new Label("Materia Prima: ");
        L_MateriaPrima.setMinWidth(100);
        //Objeto Vbox coloca dentro de una caja objetos verticalmente
        V_box = new HBox();
        V_box.setSpacing(5);//espacio verticalmente entre objetos
        V_box.getChildren().addAll(Rb_Contado, Rb_Credito);
        H_Botones = new HBox();
        H_Botones.setSpacing(5);//espacio verticalmente entre objetos
        H_Botones.getChildren().addAll(B_New, B_Save);
        //alineacion,colspan,verspan,bordes,etc
        Gp_Proveedor.setVgap(5);//espacio verticalmente dentro de gridpane
        Gp_Producto.setVgap(5);
        Gp_Inventario.setVgap(5);
        Gp_MateriaPrima.setVgap(5);
        GridPane.setRowSpan(Gp_DatosEmpresa, 2);//expande registro en dos o mas registros
        GridPane.setColumnSpan(T_Total, 5);
        GridPane.setRowSpan(T_Total, 3);
        GridPane.setColumnSpan(Ta_FacturaItems, 2);
        GridPane.setValignment(Gp_Proveedor, VPos.TOP);
        GridPane.setValignment(Gp_Producto, VPos.TOP);//alinea verticalmente
        GridPane.setHalignment(Gp_Producto, HPos.CENTER);//alinea verticalmente
        GridPane.setValignment(Gp_Inventario, VPos.TOP);
        GridPane.setValignment(L_FormaPago, VPos.TOP);
        GridPane.setValignment(L_Codigo, VPos.TOP);
        GridPane.setValignment(L_PrecioCompra, VPos.TOP);
        GridPane.setValignment(T_Total, VPos.TOP);
        GridPane.setHalignment(T_Total, HPos.CENTER);
        Gp_Producto.setAlignment(Pos.CENTER);
        Gp_Inventario.setAlignment(Pos.CENTER);
        Gp_Proveedor.setHgap(5);
        Gp_Producto.setHgap(5);
        Gp_Inventario.setHgap(5);
        Gp_Factura.setHgap(5);
        Gp_Proveedor.add(L_Fecha, 0, 0);
        Gp_Proveedor.add(Tf_Fecha, 1, 0);
        Gp_Proveedor.add(L_NoFactura, 2, 0);
        Gp_Proveedor.add(Tf_NoFactura, 3, 0);
        Gp_Proveedor.add(L_NombreProveedor, 2, 1);
        Gp_Proveedor.add(L_NitProveedor, 0, 1);
        Gp_Proveedor.add(L_DireccionProveedor, 0, 2);
        Gp_Proveedor.add(L_TelefonoProveedor, 2, 2);
        Gp_Proveedor.add(Tf_NombreProveedor, 3, 1);
        Gp_Proveedor.add(Tf_NitProveedor, 1, 1);
        Gp_Proveedor.add(Tf_DireccionProveedor, 1, 2);
        Gp_Proveedor.add(Tf_TelefonoProveedor, 3, 2);
        Gp_Proveedor.add(L_FormaPago, 0, 3);
        Gp_Proveedor.add(V_box, 1, 3, 2, 1);
        //  Tp_Proveedor=new TitledPane("Proveedor",Gp_Proveedor);
        Gp_Producto.add(la_cups, 1, 0);
        Gp_Producto.add(cups, 1, 1);
        Gp_Producto.add(la_gencategorias, 0, 0);
        Gp_Producto.add(gencategorias, 0, 1);
        Gp_Producto.add(L_Codigo, 2, 0);
        Gp_Producto.add(Tf_Codigo, 2, 1);
        //  Gp_Producto.add(sp_producto, 1, 1,7,30);
        Gp_Producto.add(L_Producto, 3, 0);
        Gp_Producto.add(Tf_Producto, 3, 1);
        Gp_Producto.add(la_precio2015, 4, 0);
        Gp_Producto.add(precio2015, 4, 1);
        Gp_Producto.add(la_precio2016, 5, 0);
        Gp_Producto.add(precio2016, 5, 1);
        Gp_Producto.add(la_precio2017, 6, 0);
        Gp_Producto.add(precio2017, 6, 1);
        Gp_Producto.add(L_Iva, 7, 0);
        Gp_Producto.add(Tf_Iva, 7, 1);

        // Gp_Producto.add(searchPopover, 4, 2,4,30);
        Gp_Producto.add(B_SaveProd, 8, 1);

        // Tp_Proveedor=new TitledPane("Proveedor",Gp_Proveedor);
        Gp_Inventario.add(L_PrecioCompra, 0, 0);
        Gp_Inventario.add(Tf_PrecioCompra, 0, 1);
        Gp_Inventario.add(L_Cantidad, 1, 0);
        Gp_Inventario.add(Tf_Cantidad, 1, 1);
        Gp_Inventario.add(L_Valor, 2, 0);
        Gp_Inventario.add(Tf_Valor, 2, 1);
        Gp_Inventario.add(B_Insertar, 3, 1);
        Gp_Inventario.add(H_BotonesInv, 4, 1, 1, 2);
        //Gp_MateriaPrima.add(L_MateriaPrima,0, 1);

        Ta_MateriaPrima.setMinHeight(70);
        Ta_MateriaPrima.setMaxHeight(70);
        Gp_MateriaPrima.add(L_Habilitar, 0, 0);
        Gp_MateriaPrima.add(Ch_AddMateriaPrima, 1, 0);
        Gp_MateriaPrima.add(Ta_MateriaPrima, 1, 1, 2, 1);
        Gp_MateriaPrima.add(L_Cantidad_Salida, 0, 2);
        Gp_MateriaPrima.add(Tf_Cantidad_Salida, 1, 2);

        // Gp_Proveedor.setMinWidth(270);
        // Tp_Producto=new TitledPane("Producto",Gp_Producto);
        Gp_Total.add(T_Total, 1, 1);
        Gp_Proveedor.setStyle("-fx-padding: 4;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0.25;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: #007fff;");
        Gp_Producto.setStyle("-fx-padding: 4;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0.25;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: #007fff;");
        Gp_Inventario.setStyle("-fx-padding: 4;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0.25;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: #007fff;");
        Gp_MateriaPrima.setStyle("-fx-padding: 2;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0.25;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: #007fff;");
        Gp_FacturaItems.setStyle("-fx-padding: 4;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0.25;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: #007fff;");

        //Gp_Factura.add(Gp_MateriaPrima, 1, 1,1,2);
        //Panel con titulo que contiene todo los gridpane
        //Creamos el fondo para el led de 7 segmentos
        //create dragger with desired size
        Rectangle dragger = new Rectangle(550, 55);
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
        numerodigital = new NumeroDigital(Color.WHITE, Color.rgb(50, 50, 50), "0.00");
        numerodigital.setLayoutX(10);
        numerodigital.setLayoutY(5);
        numerodigital.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));
        // add background and clock to sample
        Gr_NumeroDigital.getChildren().addAll(dragger, numerodigital);

        GridPane.setColumnSpan(Gr_NumeroDigital, 5);
        GridPane.setHalignment(Gr_NumeroDigital, HPos.CENTER);
        GridPane.setValignment(numerodigital, VPos.CENTER);
        // GridPane.setValignment(B_verGanancias, VPos.TOP);
        GridPane.setValignment(B_Buscarfacturas, VPos.TOP);
        // GridPane.setValignment(B_VerAbonos, VPos.TOP);
        GridPane.setValignment(B_Kardex, VPos.TOP);
        GridPane.setValignment(B_Existencias, VPos.TOP);
        Gp_Total.add(Gr_NumeroDigital, 1, 4);
        Gp_Factura.setAlignment(Pos.TOP_CENTER);
        Gp_Factura.autosize();
        Gp_FacturaItems.autosize();
        Gp_Producto.autosize();
        GridPane.setColumnSpan(Ta_FacturaItems, 5);
        Gp_FacturaItems.add(Ta_FacturaItems, 0, 2, 5, 5);

        /*Gp_FacturaItems.add(B_Buscarfacturas, 5, 0);
         Gp_FacturaItems.add(B_Kardex, 5, 1);
         Gp_FacturaItems.add(B_Existencias, 5, 2);*/
        Gp_Factura.add(Gp_FacturaItems, 0, 6, 2, 1);
        Hb_Totales = new HBox(L_Total, Tf_Total);
        Hb_Totales.setSpacing(5);
        Hb_Totales.setAlignment(Pos.CENTER_RIGHT);
        H_BotonesInv.setSpacing(2);//espacio verticalmente entre objetos
        H_BotonesInv.getChildren().addAll(B_Buscarfacturas, B_Kardex, B_Existencias);
        B_Buscarfacturas.setAlignment(Pos.TOP_RIGHT);
        Gp_FacturaItems.add(Hb_Totales, 4, 7);
        GridPane.setValignment(H_Botones, VPos.BOTTOM);
        GridPane.setValignment(L_MateriaPrima, VPos.TOP);
        Gp_FacturaItems.add(H_Botones, 1, 7);
        H_Botones.setAlignment(Pos.BOTTOM_LEFT);
        Gp_FacturaItems.setVgap(7);
        Gp_FacturaItems.setHgap(3);
        V_Factura.setSpacing(5);
        V_Factura.getChildren().addAll(Gp_Factura);
        Gp_Factura.add(Gp_Proveedor, 0, 0);
        Gp_Factura.add(Gp_Producto, 0, 1, 2, 1);
        Gp_Factura.add(Gp_Inventario, 0, 3, 2, 1);
        Gp_Factura.add(Gp_Total, 1, 0);
        H_BotonesInv.setAlignment(Pos.TOP_RIGHT);
        GridPane.setHalignment(H_BotonesInv, HPos.RIGHT);
        Gp_Factura.getStyleClass().add("background");
        // Gp_FacturaItems.getStyleClass().add("background");
        Gp_FacturaItems.setAlignment(Pos.CENTER);
        Gp_Factura.setAlignment(Pos.CENTER);
        // Gp_DatosCliente.getStyleClass().add("background");
        // V_Factura.setAlignment(Pos.TOP_CENTER);
        //Creamos las columnas temporales Item de factura

        //  disabledAllTextField();
        //  Tp_Factura= new TitledPane("Factura de Venta", V_Factura);
        V_Factura.autosize();
        generarVlrCambio();

        //Message
        Gp_Message = new GridPane();
        Gp_Message.setMaxSize(1070, 40);

        // Gp_Message.getStyleClass().add("backgroundMessage");
        L_Message = new Label();
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);

        //GridPane.setHalignment(Gp_Message, HPos.RIGHT);
        L_Message.setAlignment(Pos.CENTER_RIGHT);
        Gp_Message.setVisible(false);

        stack.getChildren().addAll(V_Factura, Gp_Message, searchPopover, sp_producto);
        stack.setAlignment(Pos.TOP_CENTER);

        Ta_MateriaPrima.setDisable(true);
        Tf_Cantidad_Salida.setDisable(true);
        changeColumns();
        Load_ChoiceBox();
        eventos_inputs();
        disabledAllTextField();
        KeyCombination kc = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        Runnable task = () -> nuevaFactura();
        KeyCombination kg = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
        Runnable task2 = () -> guardarFactura();
        // Mnemonic mnemonic = new Mnemonic(B_New, kc);
        SihicApp.getScene().getAccelerators().clear();
        SihicApp.getScene().getAccelerators().put(kc, task);
        SihicApp.getScene().getAccelerators().put(kg, task2);
        String y = String.valueOf(Gp_Producto.getLayoutBounds().getMaxY());
        // -fx-padding=y1 x1 y2 x2|
        searchPopover.setStyle("-fx-padding:198 100 0 -100;position: relative;");
        //searchPopover.setStyle("-fx-position: absolute;-fx-left: -100px;-fx-top: 150px;");
        //searchPopover.setMaxSize(400,200);
        sp_producto.setStyle("-fx-padding:198 0 0 100;position: relative;\n"
                + "    -fx-left: 750px;");

        return stack;
    }

    public void nuevaFactura() {

        if (!B_New.isDisabled()) {
            enabledAllTextField();
            Tf_Iva.setText("");
            SihicApp.s_producto = null;
            SihicApp.s_producto = new Producto();
            facturaProveedores = null;
            facturaProveedores = new FacturaProveedores();
            Ol_FacturaItems.clear();
            B_Save.setDisable(false);
            B_New.setDisable(true);
            cups.setText("");
            Tf_NombreProveedor.setText("");
            Tf_NitProveedor.setText("");
            Tf_DireccionProveedor.setText("");
            Tf_TelefonoProveedor.setText("");
            Tf_Codigo.setText("");
            Rb_Credito.setSelected(false);
            Rb_Contado.setSelected(false);
            Tf_Producto.setText("");
            Tf_Valor.setText("0");
            Tf_Cantidad.setText("0");
            Tf_PrecioCompra.setText("0");
            Tf_Total.setText("0.00");
            lote.setText("");
            fechavencimiento.setValue(LocalDate.now());
            lote.setText("");
            medida.getSelectionModel().select(0);
            unidamedida.setText("");
            concentracion.setText("");
            reginvima.setText("");
            medformasfarmaceuticas.setText("");
            presentaciocomercial.setText("");
            principioactivo.setText("");
            laboratorio.setText("");
            marca.setText("");
            fechavencimiento.setValue(LocalDate.now());
            Gr_NumeroDigital.getChildren().remove(numerodigital);
            numerodigital = null;
            numerodigital = new NumeroDigital(Color.WHITE, Color.rgb(50, 50, 50), "0.00");
            numerodigital.setLayoutX(10);
            numerodigital.setLayoutY(5);
            numerodigital.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));
            Gr_NumeroDigital.getChildren().add(numerodigital);
            facturaProveedores.setFacturaDate(new Date());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = df.format(facturaProveedores.getFacturaDate());
            LocalDate ld = LocalDate.parse(fecha);
            Tf_Fecha.setText(fecha);
            Tf_ValorRecibido.setText("0");
            clear_producto_proveedor();
            L_Message.setText("Nueva Factura");
            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
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

    private void guardarFactura() {
//agregamos empleado
        if (validar_guardar()) {
            B_Save.setDisable(true);
            B_New.setDisable(false);
            Proveedores t = new Proveedores();
            t.setNo_ident(Tf_NitProveedor.getText());
            t.setNombre(Tf_NombreProveedor.getText());
            t.setCelular(Tf_TelefonoProveedor.getText());
            t.setDir1(Tf_DireccionProveedor.getText());
            facturaProveedores.setProveedores(t);
            try {
                facturaProveedores.setNo_factura(Long.valueOf(Tf_NoFactura.getText().trim()));
            } catch (Exception e) {
                facturaProveedores.setNo_factura(Long.valueOf("0"));
            }
            facturaProveedores.setCredito(Rb_Credito.isSelected());
           facturaProveedoresControllerClient.guardarFactura();
            L_Message.setText("Factura Guardada");
            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            backgroundThread = new Thread(Task_Message);
            // Terminate the running thread if the application exits
            backgroundThread.setDaemon(true);

            // Start the thread
            backgroundThread.start();
            t = null;
        } else {
            animateMessage();
            L_Message.setText("Algunos Valores no se han digitado");
            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
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

            //stage finproduct
            findProduct.loadData();
            // Return null at the end of a Task of type Void
            return null;
        }
    };

    public void addProduct() {

        //cambiamos las columnas a otro origen de datos
        //verificamos que solo se ejecute solo una vez
        if (SihicApp.s_producto != null && !Tf_NitProveedor.getText().trim().equals("") && !Tf_NombreProveedor.getText().trim().equals("")) {
            if (SihicApp.s_producto.getId() != null) {
                if (proveedor == null) {
                    proveedor = new Proveedores();
                }
                proveedor.setNo_ident(Tf_NitProveedor.getText());
                proveedor.setNombre(Tf_NombreProveedor.getText());
                proveedor.setCelular(Tf_TelefonoProveedor.getText());
                proveedor.setDir1(Tf_DireccionProveedor.getText());
                facturaProveedores.setProveedores(proveedor);
                try {
                    facturaProveedores.setNo_factura(Long.valueOf(Tf_NoFactura.getText().trim()));
                } catch (Exception e) {
                    facturaProveedores.setNo_factura(Long.valueOf("0"));
                }
                facturaProveedores.setCredito(Rb_Credito.isSelected());

                FacturaItemProveedores fip;
                if (!Ch_AddMateriaPrima.isSelected()) {
                    fip = null;
                } else {
                    try {

                        fip = (FacturaItemProveedores) Ta_MateriaPrima.getSelectionModel().getSelectedItem();
                        fip.setCantidad_unidades(Float.valueOf(Tf_Cantidad_Salida.getText().trim()));
                        facturaProveedoresControllerClient.save_ItemMateriaPrima(fip);
                    } catch (Exception e) {
                        fip = null;
                    }
                }

                facturaProveedores.addProduct(SihicApp.s_producto, Float.valueOf(Tf_Cantidad.getText().trim().replace(",", ".")), BigDecimal.valueOf(Double.parseDouble(Tf_Valor.getText().trim().replace(",", "."))), fip);
                facturaProveedores = facturaProveedoresControllerClient.crearFactura(facturaProveedores, lote.getText(), fechavencimiento.getValue());
                facturaProveedores.calculateTotals();
                Tf_Total.setText(facturaProveedores.getTotalAmount().toString().replace(".", ","));
                setNumeroDigital();
                //Cargamos la tabla con el nuevo item
                O_ListFacturaItems = facturaProveedores.getFacturaItems().toArray();
                Ol_FacturaItems.clear();

                Ol_FacturaItems.addAll(O_ListFacturaItems);
                Ta_FacturaItems.setItems(Ol_FacturaItems);
                clear_producto_proveedor();
                L_Message.setText("Item Guardado");
                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            } else {
                Tf_Producto.setStyle("-fx-background-color:#ff1600");
            }
        } else {
            Tf_NitProveedor.setStyle("-fx-background-color:#ff1600");
            Tf_NombreProveedor.setStyle("-fx-background-color:#ff1600");

        }
        animateMessage();

    }

    public void deleteItem() {

        FacturaItemProveedores fi = (FacturaItemProveedores) Ta_FacturaItems.getFocusModel().getFocusedItem();
        facturaProveedores.removeProduct(fi.getProducto());
        facturaProveedoresControllerClient.crearFactura(facturaProveedores, lote.getText(), fechavencimiento.getValue());

        O_ListFacturaItems = facturaProveedores.getFacturaItems().toArray();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(O_ListFacturaItems);
        Ta_FacturaItems.setItems(Ol_FacturaItems);

        Tf_Total.setText(facturaProveedores.getTotalAmount().toString());
        Gr_NumeroDigital.getChildren().remove(numerodigital);
        numerodigital = null;
        numerodigital = new NumeroDigital(Color.WHITE, Color.rgb(50, 50, 50), Tf_Total.getText());
        numerodigital.setLayoutX(10);
        numerodigital.setLayoutY(5);
        numerodigital.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));
        Gr_NumeroDigital.getChildren().add(numerodigital);

    }

    public void modifyItem(String quantity) {
        FacturaItemProveedores fi = (FacturaItemProveedores) Ta_FacturaItems.getFocusModel().getFocusedItem();
        facturaProveedores.modifyItem(fi.getProducto(), Float.valueOf(quantity.trim()));
        facturaProveedoresControllerClient.crearFactura(facturaProveedores, lote.getText(), fechavencimiento.getValue());
        O_ListFacturaItems = facturaProveedores.getFacturaItems().toArray();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(O_ListFacturaItems);
        Ta_FacturaItems.setItems(Ol_FacturaItems);
        Tf_Total.setText(facturaProveedores.getTotalAmount().toString());
        Gr_NumeroDigital.getChildren().remove(numerodigital);
        numerodigital = null;
        numerodigital = new NumeroDigital(Color.WHITESMOKE, Color.rgb(50, 50, 50), Tf_Total.getText());
        numerodigital.setLayoutX(10);
        numerodigital.setLayoutY(5);
        numerodigital.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));
        Gr_NumeroDigital.getChildren().add(numerodigital);
    }

    public void changeColumns() {
        Tc_NoItem = new TableColumn();
        Tc_NoItem.setText("No Item");
        Tc_NoItem.setCellValueFactory(new PropertyValueFactory("position"));
        Tc_Producto = new TableColumn();
        Tc_Producto.setText("Producto");
        Tc_Producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getProducto().getNombre());
            }
        });

        Tc_Cantidad = new TableColumn();
        Tc_Cantidad.setText("Cantidad");
        Tc_Cantidad.setCellValueFactory(new PropertyValueFactory("quantity"));
        Tc_Cantidad.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        Tc_ValorU = new TableColumn();
        Tc_ValorU.setText("Valor/U");
        Tc_ValorU.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getValor_u().toString());
            }
        });

        Tc_ValorT = new TableColumn();
        Tc_ValorT.setText("Valor Total");
        Tc_ValorT.setCellValueFactory(new PropertyValueFactory("valor_total"));

        Ta_FacturaItems.getColumns().clear();
        Ta_FacturaItems.setEditable(true);
        Ta_FacturaItems.getColumns().addAll(Tc_NoItem, Tc_Producto, Tc_Cantidad, Tc_ValorU, Tc_ValorT);
        //Ancho de tabla y ancho de porcentaje de columnas
        //Ancho de tabla y ancho de porcentaje de columnas
        Gp_Factura.autosize();
        Gp_FacturaItems.autosize();
        Ta_FacturaItems.setMinWidth(Gp_Factura.getLayoutBounds().getWidth() + 5);
        Ta_FacturaItems.setMinHeight((Gp_Factura.getLayoutBounds().getWidth() * 0.20) - 50);
        //Ta_FacturaItems.setMinHeight((Gp_Factura.getLayoutBounds().getWidth()*0.20)-50);

        GridPane.setFillWidth(veil, Boolean.TRUE);
        Ta_FacturaItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
                    if (nh.getClass() == HBox.class) {
                        HBox v_gen = (HBox) nh;
                        for (Object nhv : v_gen.getChildren().toArray()) {
                            if (nhv.getClass() == RadioButton.class) {
                                RadioButton rb_gen = (RadioButton) nhv;
                                rb_gen.setDisable(true);
                            }
                            if (nhv.getClass() == TextField.class) {
                                TextField rb_gen = (TextField) nhv;
                                rb_gen.setDisable(true);
                            }
                        }
                    }
                    if (nh.getClass() == TextField.class) {
                        TextField tf_gen = (TextField) nh;
                        tf_gen.setDisable(true);
                    }
                    if (nh.getClass() == ChoiceBox.class) {
                        ChoiceBox tf_gen = (ChoiceBox) nh;
                        tf_gen.setDisable(true);

                    }
                    if (nh.getClass() == SearchBox.class) {
                        SearchBox tf_gen = (SearchBox) nh;
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
                    if (nh.getClass() == HBox.class) {
                        HBox v_gen = (HBox) nh;
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
                    if (nh.getClass() == ChoiceBox.class) {
                        ChoiceBox tf_gen = (ChoiceBox) nh;
                        tf_gen.setDisable(false);

                    }
                    if (nh.getClass() == SearchBox.class) {
                        SearchBox tf_gen = (SearchBox) nh;
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

        if (SihicApp.s_producto != null) {

            setProducto();
        } else {

            Tf_Producto.getSelectedText();
            Tf_Producto.requestFocus();
            //Tf_Producto.setText("");
            Tf_Precio.setText("");
            Tf_Iva.setText("");
            Tf_Iva.setText("");
            //cups.setText("");
            lote.setText("");
            medida.getSelectionModel().select(0);
            unidamedida.setText("");;
            concentracion.setText("");
            reginvima.setText("");
            medformasfarmaceuticas.setText("");;
            presentaciocomercial.setText("");
            principioactivo.setText("");
            laboratorio.setText("");
            marca.setText("");
            fechavencimiento.setValue(LocalDate.now());
            precio2015.setText("0");
            precio2016.setText("0");
            precio2017.setText("0");
            SihicApp.s_producto = new Producto();
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
        Nd_ValorCambio = new NumeroDigital(Color.WHITE, Color.rgb(50, 50, 50), "0.00");
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
        Gp_VlrRecibido.add(B_Print, 2, 1);
        Gp_VlrRecibido.getStyleClass().add("solucion-gridpane-background");

    }

    public void addGp_VlrRecibido() {

        // add background and clock to sample
        if (Gp_FacturaItems.getChildren().indexOf(Gp_VlrRecibido) == -1) {
            Gp_FacturaItems.add(Gp_VlrRecibido, 0, 1);
            Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
            Nd_ValorCambio = null;
            Nd_ValorCambio = new NumeroDigital(Color.WHITE, Color.rgb(50, 50, 50), "0.00");
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

        facturaProveedores.setEfectivo(BigDecimal.valueOf(Double.parseDouble(Tf_ValorRecibido.getText().trim())));
        facturaProveedoresControllerClient.crearFactura(facturaProveedores, lote.getText(), fechavencimiento.getValue());
        Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
        Nd_ValorCambio = null;
        Nd_ValorCambio = new NumeroDigital(Color.WHITE, Color.rgb(50, 50, 50), facturaProveedores.getDevolver().toString());
        Nd_ValorCambio.setLayoutX(10);
        Nd_ValorCambio.setLayoutY(5);
        Nd_ValorCambio.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));

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
        FadeTransition ft = new FadeTransition(Duration.millis(2000), Tf_Producto);
        //ft.setFromValue(0.0);
        // ft.setToValue(1);

        ft.play();

        //success.setOpacity(0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Tf_NoFactura.setStyle(null);
                Tf_NoFactura.getStyleClass().add("text-field");
                Tf_Producto.setStyle(null);
                Tf_Producto.getStyleClass().add("text-field");
                Tf_Codigo.setStyle(null);
                Tf_Codigo.getStyleClass().add("text-field");
                Tf_Precio.setStyle(null);
                cups.getStyleClass().add("text-field");
                cups.setStyle(null);
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
                Ta_FacturaItems.getStyleClass().add("text-field");
                gencategorias.setStyle(null);
                gencategorias.getStyleClass().add("choice-box");

            }
        });

    }

    private void calcularValorTotal() {
        try {
            Tf_Valor.setText(new BigDecimal(Tf_Cantidad.getText().trim().replace(",", ".")).multiply(new BigDecimal(Tf_PrecioCompra.getText().trim().replace(",", "."))).toString().replace(".", ","));

            //Tf_Valor.setText((BigDecimal.valueOf(Double.valueOf(Tf_Cantidad.getText().trim().replace(",", "."))).multiply(BigDecimal.valueOf(Double.valueOf(Tf_PrecioCompra.getText().trim().replace(",", ".")))).toString().replace(".", ",")));
        } catch (Exception e) {
            Tf_Valor.setText(new BigDecimal(Tf_Cantidad.getText().trim().replace(".", ",")).multiply(new BigDecimal(Tf_PrecioCompra.getText().trim().replace(".", ","))).toString().replace(".", ","));

        }

    }

    private void setProducto() {

        Tf_Codigo.setText(SihicApp.s_producto.getCodigo());
        Tf_Producto.setText(SihicApp.s_producto.getNombre());
        Tf_Iva.setText(String.valueOf(SihicApp.s_producto.getIva()).replace(".", ","));
        Tf_Precio.setText(SihicApp.s_producto.getPrecio().toString().replace(".", ","));
        precio2015.setText(SihicApp.s_producto.getPrecio2015().toString().replace(".", ","));
        precio2016.setText(SihicApp.s_producto.getPrecio2016().toString().replace(".", ","));
        precio2017.setText(SihicApp.s_producto.getPrecio2017().toString().replace(".", ","));
        gencategorias.getSelectionModel().select(v_gencategorias.indexOf(SihicApp.s_producto.getGenCategoriasProductos()));
        if (SihicApp.s_producto.getGenCategoriasProductos().getCodigo() == 1) {
            SihicApp.s_hclCups = SihicApp.s_producto.getHclCups();
            if (SihicApp.s_hclCups != null) {
                searchPopover.getSearchBox().setText(SihicApp.s_hclCups.getCodigo() + " " + SihicApp.s_hclCups.getDescripcion());
            }
        } else {
            if (SihicApp.s_producto.getGenCategoriasProductos().getCodigo() == 2) {
                marca.setText(findProduct.getProducto().getMarca());
                laboratorio.setText(findProduct.getProducto().getLaboratorio());
                principioactivo.setText(findProduct.getProducto().getPrincipioactivo());
                concentracion.setText(findProduct.getProducto().getConcentracionmedicamento());
                medida.getSelectionModel().select(findProduct.getProducto().getTipo_medicamento() == 1 ? 0 : findProduct.getProducto().getTipo_medicamento() == 2 ? 1 : 0);
                unidamedida.setText(findProduct.getProducto().getUnidadmedidamedicamento());
                presentaciocomercial.setText(findProduct.getProducto().getPresentacioncomercial());
                medformasfarmaceuticas.setText(findProduct.getProducto().getFormafarmaceutica());
                reginvima.setText(findProduct.getProducto().getReginvima());
            } else {
                if (SihicApp.s_producto.getGenCategoriasProductos().getCodigo() == 3) {
                    marca.setText(findProduct.getProducto().getMarca());
                    laboratorio.setText(findProduct.getProducto().getLaboratorio());
                    medida.getSelectionModel().select(findProduct.getProducto().getTipo_medicamento() == 1 ? 0 : 1);
                    unidamedida.setText(findProduct.getProducto().getUnidadmedidamedicamento());
                    reginvima.setText(findProduct.getProducto().getReginvima());
                }

            }
        }
        Tf_Producto.requestFocus();
    }

    private void guardarProducto() {

        boolean m_BCanSave = true;

        if (Tf_Producto.getText().equals("")) {
            Tf_Producto.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }
        if (cups.getText().equals("")) {
            cups.setStyle("-fx-background-color:#ff1600");
            if (v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()) != null) {
                //  findProduct.getProducto().setEsmateriaprima(Ch_EsMateriaPrima.isSelected());
                if (v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()).getCodigo() == 1) {
                    m_BCanSave = false;
                }
            }
        }
        if (Tf_Codigo.getText().equals("")) {
            Tf_Codigo.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }

        if (m_BCanSave) {
            SihicApp.s_producto.setNombre(Tf_Producto.getText());
            SihicApp.s_producto.setCodigo(Tf_Codigo.getText());
            if (Tf_Iva.getText().trim().equals("")) {
                Tf_Iva.setText("0");
            }
            SihicApp.s_producto.setIva(Integer.valueOf(Tf_Iva.getText().trim()));
            SihicApp.s_producto.setPrecio2015(BigDecimal.valueOf(Double.valueOf(precio2015.getText().trim().replace(",", "."))));
            SihicApp.s_producto.setPrecio2016(BigDecimal.valueOf(Double.valueOf(precio2016.getText().trim().replace(",", "."))));
            SihicApp.s_producto.setPrecio2017(BigDecimal.valueOf(Double.valueOf(precio2017.getText().trim().replace(",", "."))));

            SihicApp.s_producto.setCosto(BigDecimal.valueOf(Double.valueOf(Tf_PrecioCompra.getText().trim().replace(",", "."))));
            if (v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()) != null) {
                SihicApp.s_producto.setGenCategoriasProductos(v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()));
            }
            if (v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()) != null) {
                //  findProduct.getProducto().setEsmateriaprima(Ch_EsMateriaPrima.isSelected());
                if (v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()).getCodigo() == 2) {
                    SihicApp.s_producto.setLaboratorio(laboratorio.getText());
                    SihicApp.s_producto.setMarca(marca.getText());

                    SihicApp.s_producto.setReginvima(reginvima.getText());
                    try {
                        SihicApp.s_producto.setTipo_medicamento(medida.getSelectionModel().getSelectedIndex() + 1);
                    } catch (Exception e) {
                        SihicApp.s_producto.setTipo_medicamento(1);
                    }

                    SihicApp.s_producto.setConcentracionmedicamento(concentracion.getText());
                    SihicApp.s_producto.setPresentacioncomercial(presentaciocomercial.getText());
                    SihicApp.s_producto.setPrincipioactivo(principioactivo.getText());
                    SihicApp.s_producto.setFormafarmaceutica(medformasfarmaceuticas.getText());
                    SihicApp.s_producto.setUnidadmedidamedicamento(unidamedida.getText());

                } else {
                    if (v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()).getCodigo() == 3) {
                        SihicApp.s_producto.setLaboratorio(laboratorio.getText());
                        SihicApp.s_producto.setMarca(marca.getText());

                        SihicApp.s_producto.setReginvima(reginvima.getText());

                        SihicApp.s_producto.setUnidadmedidamedicamento(unidamedida.getText());

                    } else {
                        SihicApp.s_producto.setHclCups(SihicApp.s_hclCups);
                    }
                }

                ProductoJerseyClient productoJerseyClient = new ProductoJerseyClient();
                productoJerseyClient.create_XML(SihicApp.s_producto);
                L_Message.setText("Producto Guardado");
                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            } else {
                animateMessage();

            }

        }

    }

    public void findFacturaProveedores() {
        enabledAllTextField();
        FacturaProveedores fp = new FacturaProveedores();
        fp = facturaProveedores;

        if (facturaProveedores.getId() != null) {
            Tf_NombreProveedor.setText(facturaProveedores.getProveedores().getNombre());
            proveedor = facturaProveedores.getProveedores();
            Tf_NitProveedor.setText(facturaProveedores.getProveedores().getNo_ident());
            Tf_TelefonoProveedor.setText(facturaProveedores.getProveedores().getCelular());
            Tf_DireccionProveedor.setText(facturaProveedores.getProveedores().getDir1());
            Tf_Fecha.setText(DateFormat.getDateInstance().format(facturaProveedores.getFacturaDate()));
            Tf_Total.setText(facturaProveedores.getTotalAmount().toString());
            setNumeroDigital();
            if (facturaProveedores.getCredito()) {
                Rb_Credito.setSelected(true);
            } else {
                Rb_Contado.setSelected(true);
            }
            O_ListFacturaItems = facturaProveedores.getFacturaItems().toArray();
            Ol_FacturaItems.clear();

            Ol_FacturaItems.addAll(O_ListFacturaItems);
            Ta_FacturaItems.setItems(Ol_FacturaItems);
            Tf_Producto.setText("");
            Tf_Valor.setText("0");
            Tf_Cantidad.setText("0");

            Tf_PrecioCompra.setText("0");
            Tf_Codigo.setText("");
            Tf_Precio.setText("0");
            Tf_Iva.setText("0");
            Tf_PrecioCompra.setText("0");
        } else {
            facturaProveedores = fp;
        }
    }

    public void getLastItemsMateriaPrima() {
        O_ListFacturaItemsI = facturaProveedoresControllerClient.getLastItemsmateriaPrima(Tf_NitProveedor.getText().trim()).toArray();
        Ol_FacturaItemsI.clear();
        Ol_FacturaItemsI.addAll(O_ListFacturaItemsI);
        Ta_MateriaPrima.setItems(Ol_FacturaItemsI);
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        findProductEmbbed = null;
        findProductEmbbed = (Parent) clz.getMethod("createContent").invoke(app);
        scene = null;
        scene = new Scene(findProductEmbbed, Color.TRANSPARENT);

        if (stage3.isShowing()) {
            stage3.close();
        }

//set scene to stage
        stage3.sizeToScene();

        //center stage on screen
        stage3.centerOnScreen();
        stage3.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stage3.show();
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

    public void setNumeroDigital() {
        Gr_NumeroDigital.getChildren().remove(numerodigital);
        numerodigital = null;
        numerodigital = new NumeroDigital(Color.WHITE, Color.rgb(50, 50, 50), Tf_Total.getText());
        numerodigital.setLayoutX(10);
        numerodigital.setLayoutY(5);
        numerodigital.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));
        Gr_NumeroDigital.getChildren().add(numerodigital);

    }

    private void clear_producto_proveedor() {
        Tf_Producto.requestFocus();
        Tf_Precio.setDisable(false);
        Tf_Iva.setDisable(false);
        Tf_Iva.setText("0");
        Tf_PrecioCompra.setDisable(false);
        Tf_Codigo.setDisable(false);
        Tf_Producto.setDisable(false);
        Tf_Cantidad.setDisable(false);
        Tf_Valor.setDisable(false);
        Tf_Producto.setText("");
        Tf_Valor.setText("0");
        Tf_Cantidad.setText("0");
        Tf_PrecioCompra.setText("0");
        Tf_Codigo.setText("");
        Tf_Precio.setText("0");
        Tf_Iva.setText("0");
        Tf_PrecioCompra.setText("0");
        precio2015.setText("0");
        precio2016.setText("0");
        precio2017.setText("0");
        Ch_EsMateriaPrima.setSelected(false);
        Ch_AddMateriaPrima.setSelected(false);
        Ch_AddMateriaPrima.getOnMouseClicked();
        SihicApp.s_producto = null;
        SihicApp.s_producto = new Producto();
        SihicApp.s_hclCups = null;
        SihicApp.s_hclCups = new HclCups();
        cups.setText("");
        Tf_Codigo.requestFocus();
        B_Save.setDisable(false);
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

        if (Tf_Precio.getText().equals("")) {
            // Tf_Precio.setStyle("-fx-background-color:#ff1600");
            // m_BCanSave=false;

        }
        if (Tf_Producto.getText().equals("")) {
            // Tf_Producto.setStyle("-fx-background-color:#ff1600");
            // m_BCanSave=false;
        }
        if (Tf_Codigo.getText().equals("")) {
            // Tf_Codigo.setStyle("-fx-background-color:#ff1600");
            // m_BCanSave=false;
        }
        if (gencategorias.getSelectionModel().getSelectedIndex() == -1) {
            gencategorias.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }
        if (Tf_NombreProveedor.getText().equals("")) {
            Tf_NombreProveedor.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }
        if (Tf_NitProveedor.getText().equals("")) {
            Tf_NitProveedor.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }
        if (Tf_TelefonoProveedor.getText().equals("")) {
            Tf_TelefonoProveedor.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }
        if (Ta_FacturaItems.getItems().size() <= 0) {
            Ta_FacturaItems.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }
        return m_BCanSave;

    }

    public void Load_ChoiceBox() {

        if (SihicApp.l_categoriasproductos.size() > 0) {
            gencategorias.getItems().clear();
            v_gencategorias.clear();
            for (GenCategoriasProductos cp : SihicApp.l_categoriasproductos) {
                v_gencategorias.add(cp);
                gencategorias.getItems().add(cp.getNombre());
            }
        }

    }
// A change listener to track the change in selected index

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        if (v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()) != null) {
            if (v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()).getCodigo() == 1)//servicios
            {
                if (Gp_Producto.getChildren().indexOf(la_cups) == -1 && Gp_Producto.getChildren().indexOf(cups) == -1) {
                    if (Gp_Producto.getChildren().indexOf(principioactivo) != -1) {
                        Gp_Producto.getChildren().removeAll(la_gencategorias, gencategorias, la_lote, lote, la_vencimiento, fechavencimiento, la_marca, marca, la_laboratorio, laboratorio, la_principioactivo, principioactivo, la_concentracion, concentracion, la_medida, medida, la_unidadmedida, unidamedida, la_presentacioncomercial, presentaciocomercial, la_medformasfarmaceuticas, medformasfarmaceuticas, la_reginvima, reginvima, B_SaveProd);
                    } else {
                        Gp_Producto.getChildren().removeAll(la_gencategorias, gencategorias, la_lote, lote, la_vencimiento, fechavencimiento, la_marca, marca, la_laboratorio, laboratorio, la_medida, medida, la_unidadmedida, unidamedida, la_reginvima, reginvima, B_SaveProd);
                    }
                    gencategorias.setMinWidth(100);
                    Gp_Producto.add(la_gencategorias, 0, 0, 1, 1);
                    Gp_Producto.add(gencategorias, 0, 1, 1, 1);
                    Gp_Producto.add(la_cups, 1, 0);
                    Gp_Producto.add(cups, 1, 1);
                    Gp_Producto.add(B_SaveProd, 8, 1);

                }

            } else {
                if (v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()).getCodigo() == 2)//medicamentos
                {
                    if (Gp_Producto.getChildren().indexOf(lote) == -1 && Gp_Producto.getChildren().indexOf(fechavencimiento) == -1) {

                        Gp_Producto.getChildren().removeAll(la_gencategorias, gencategorias, la_cups, cups, bu_agregarcups, B_SaveProd);
                        gencategorias.setMinWidth(300);
                        Gp_Producto.add(la_gencategorias, 0, 0, 2, 1);
                        Gp_Producto.add(gencategorias, 0, 1, 2, 1);
                        Gp_Producto.add(la_lote, 0, 2);
                        Gp_Producto.add(lote, 0, 3);
                        Gp_Producto.add(la_vencimiento, 1, 2);
                        Gp_Producto.add(fechavencimiento, 1, 3);
                        Gp_Producto.add(la_marca, 2, 2);
                        Gp_Producto.add(marca, 2, 3);
                        Gp_Producto.add(la_laboratorio, 3, 2);
                        Gp_Producto.add(laboratorio, 3, 3);
                        Gp_Producto.add(la_principioactivo, 4, 2);
                        Gp_Producto.add(principioactivo, 4, 3);
                        Gp_Producto.add(la_concentracion, 5, 2);
                        Gp_Producto.add(concentracion, 5, 3);
                        Gp_Producto.add(la_medida, 0, 4);
                        Gp_Producto.add(medida, 0, 5);
                        Gp_Producto.add(la_unidadmedida, 1, 4);
                        Gp_Producto.add(unidamedida, 1, 5);
                        Gp_Producto.add(la_presentacioncomercial, 2, 4);
                        Gp_Producto.add(presentaciocomercial, 2, 5, 2, 1);
                        Gp_Producto.add(la_medformasfarmaceuticas, 4, 4);
                        Gp_Producto.add(medformasfarmaceuticas, 4, 5);
                        Gp_Producto.add(la_reginvima, 5, 4);
                        Gp_Producto.add(reginvima, 5, 5);
                        Gp_Producto.add(B_SaveProd, 6, 5);
                    } else {
                        Gp_Producto.getChildren().removeAll(la_medida, medida, la_unidadmedida, unidamedida, la_reginvima, reginvima, B_SaveProd);
                        Gp_Producto.add(la_principioactivo, 4, 2);
                        Gp_Producto.add(principioactivo, 4, 3);
                        Gp_Producto.add(la_concentracion, 5, 2);
                        Gp_Producto.add(concentracion, 5, 3);
                        Gp_Producto.add(la_medida, 0, 4);
                        Gp_Producto.add(medida, 0, 5);
                        Gp_Producto.add(la_unidadmedida, 1, 4);
                        Gp_Producto.add(unidamedida, 1, 5);
                        Gp_Producto.add(la_presentacioncomercial, 2, 4);
                        Gp_Producto.add(presentaciocomercial, 2, 5, 2, 1);
                        Gp_Producto.add(la_medformasfarmaceuticas, 4, 4);
                        Gp_Producto.add(medformasfarmaceuticas, 4, 5);
                        Gp_Producto.add(la_reginvima, 5, 4);
                        Gp_Producto.add(reginvima, 5, 5);
                        Gp_Producto.add(B_SaveProd, 6, 5);
                    }

                } else {
                    if (v_gencategorias.get(gencategorias.getSelectionModel().getSelectedIndex()).getCodigo() == 3)//insumos medicamentos
                    {
                        if (Gp_Producto.getChildren().indexOf(medformasfarmaceuticas) == -1) {
                            Gp_Producto.getChildren().removeAll(la_cups, cups, la_gencategorias, gencategorias, bu_agregarcups, B_SaveProd);
                            gencategorias.setMinWidth(300);
                            Gp_Producto.add(la_gencategorias, 0, 0, 2, 1);
                            Gp_Producto.add(gencategorias, 0, 1, 2, 1);
                            Gp_Producto.add(la_lote, 0, 2);
                            Gp_Producto.add(lote, 0, 3);
                            Gp_Producto.add(la_vencimiento, 1, 2);
                            Gp_Producto.add(fechavencimiento, 1, 3);
                            Gp_Producto.add(la_marca, 2, 2);
                            Gp_Producto.add(marca, 2, 3);
                            Gp_Producto.add(la_laboratorio, 3, 2);
                            Gp_Producto.add(laboratorio, 3, 3);
                            Gp_Producto.add(la_medida, 4, 2);
                            Gp_Producto.add(medida, 4, 3);
                            Gp_Producto.add(la_unidadmedida, 5, 2);
                            Gp_Producto.add(unidamedida, 5, 3);
                            Gp_Producto.add(la_reginvima, 6, 2);
                            Gp_Producto.add(reginvima, 6, 3);
                            Gp_Producto.add(B_SaveProd, 7, 3);
                        } else {
                            Gp_Producto.getChildren().removeAll(la_medida, medida, la_unidadmedida, unidamedida, la_reginvima, reginvima, B_SaveProd, la_principioactivo, principioactivo, la_concentracion, concentracion, la_presentacioncomercial, presentaciocomercial, la_medformasfarmaceuticas, medformasfarmaceuticas);
                            Gp_Producto.add(la_medida, 4, 2);
                            Gp_Producto.add(medida, 4, 3);
                            Gp_Producto.add(la_unidadmedida, 5, 2);
                            Gp_Producto.add(unidamedida, 5, 3);
                            Gp_Producto.add(la_reginvima, 6, 2);
                            Gp_Producto.add(reginvima, 6, 3);
                            Gp_Producto.add(B_SaveProd, 7, 3);
                        }

                    }
                }
            }

        }
        enabledAllTextField();
    }

    public void eventos_inputs() {
        gencategorias.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                Tf_Codigo.requestFocus();

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

        Tf_Producto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                Tf_Precio.requestFocus();

            }
        });
        Tf_Precio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                Tf_Iva.requestFocus();

            }
        });

        Tf_NoFactura.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                Tf_NitProveedor.requestFocus();

            }
        });
        Tf_Iva.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                if (gencategorias.getSelectionModel().getSelectedIndex() != 0) {
                    lote.requestFocus();
                } else {
                    cups.requestFocus();
                }
            }
        });

        B_SaveProd.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                guardarProducto();
                Tf_PrecioCompra.requestFocus();
            }
        });
        lote.setOnAction(e -> {

            fechavencimiento.requestFocus();
        });

        marca.setOnAction(e -> {
            laboratorio.requestFocus();
        });
        laboratorio.setOnAction(e -> {
            principioactivo.requestFocus();
        });
        principioactivo.setOnAction(e -> {
            concentracion.requestFocus();
        });
        concentracion.setOnAction(e -> {
            medida.requestFocus();
        });
        medida.setOnAction(e -> {
            unidamedida.requestFocus();
        });
        unidamedida.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                presentaciocomercial.requestFocus();

            }
        });

        presentaciocomercial.setOnAction(e -> {
            medformasfarmaceuticas.requestFocus();
        });
        medformasfarmaceuticas.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                reginvima.requestFocus();

            }
        });
        reginvima.setOnAction(e -> {

            B_SaveProd.requestFocus();

        });
        Tf_Valor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent keyEvent) {

                B_Insertar.requestFocus();

            }
        });
        Tf_PrecioCompra.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent keyEvent) {

                Tf_Cantidad.requestFocus();

            }
        });
        findProduct.getStage().setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    if (findProduct.getProducto() != null) {
                        setProducto();
                        Tf_Cantidad.requestFocus();
                    }
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
                gencategorias.requestFocus();
            }
        });

        fechavencimiento.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                marca.requestFocus();
            }
        });

        lote.setOnAction(e -> {
            fechavencimiento.requestFocus();
        }
        );

        Tf_PrecioCompra.setOnAction(e -> {
            Tf_Cantidad.requestFocus();
        });
        Tf_Cantidad.setOnAction(e -> {
            calcularValorTotal();
            Tf_Valor.requestFocus();
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
                gencategorias.requestFocus();
            }
        });
        B_New.setOnAction(e -> {

            nuevaFactura();

        });

        Tf_NitProveedor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                proveedoresControllerClient = null;
                proveedoresControllerClient = new ProveedoresControllerClient();
                proveedor = proveedoresControllerClient.findbyident(Tf_NitProveedor.getText());
                if (proveedor != null) {
                    Tf_NombreProveedor.setText(proveedor.getNombre());
                    Tf_TelefonoProveedor.setText(proveedor.getCelular());
                    Tf_DireccionProveedor.setText(proveedor.getDir1());
                    Tf_DireccionProveedor.requestFocus();
                } else {
                    Tf_NombreProveedor.requestFocus();
                }

                getLastItemsMateriaPrima();
            }
        });
        Tf_NombreProveedor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                Tf_DireccionProveedor.requestFocus();

            }
        });

    }

}
