package sihic.contabilidad.documentos;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sihic.SearchPopover;
import sihic.control.SearchBox;


public class FFacturaCompra extends Application {

    private static int codigo = 0;
    private static String title = "";
    private static String titlestage = "";
    private static String titlelabel = "";
    private static String appClass;
    private static Class clz;
    Object app;
    Parent parent;
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
    private Button B_Buscar;
    private Button B_Buscarfacturas;
    private Button B_Kardex;
    private Button B_Insertar;
    private TableView Ta_FacturaItems;
    private TableView Ta_MateriaPrima;
    //GridPane row and columns
    private static GridPane Gp_Factura;
    private GridPane Gp_DatosEmpresa;
    private GridPane Gp_Proveedor;
    private GridPane Gp_Total;
    private GridPane Gp_Producto;
    private GridPane Gp_Inventario;
    private GridPane Gp_MateriaPrima;
    private GridPane Gp_FacturaItems;
    private GridPane Gp_Totales;
    private GridPane Gp_VlrRecibido;
 
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
    private Label L_PrecioCompra;
    private Label L_MateriaPrima;
    private Label L_Habilitar;
   

    //Campos de texto datos cliente 
    private TextField Tf_NombreProveedor;
    private TextField Tf_NitProveedor;
    private TextField Tf_DireccionProveedor;
    private TextField Tf_TelefonoProveedor;
    private DatePicker Tf_Fecha;
    private TextField Tf_NoFactura;
    private SearchBox sb_producto = new SearchBox();
    private SearchPopover sp_producto;

   /* private TextField Tf_Valor;
    private TextField Tf_Cantidad;
    private TextField Tf_Total;
    private TextField Tf_ValorRecibido;
    private TextField Tf_Codigo;
    private TextField Tf_Precio;
    private TextField Tf_PrecioCompra;
    private TextField Tf_Iva;
    private Label la_lote;
    private TextField lote;
    private Label la_vencimiento;
    private DatePicker fechavencimiento;
    private Label la_descripcion;
    private TextArea descripcion;
    private Label la_referencia;
    private TextField referencia;
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
    // Create a KeyCombination for Alt + C
    Scene scene = null;
    private static final SearchBox sb_cuenta = new SearchBox();
    private static final Label la_cuenta = new Label("Cuenta");
    private static final Label la_detalle = new Label("Detalle");
    private static final Label la_tipomovimiento = new Label("Tipo Movimiento");
    private static final Label la_valor = new Label("Valor");
    private static final Label la_centrocostos = new Label("Centro de Costos");
    private SearchPopover sp_cuenta;
    private static final TextField detalle = new TextField();
    private static final TextField valor = new TextField();
    private static final ChoiceBox tipomovimiento = new ChoiceBox();
    private Label la_fechaelaboracion;
    private DatePicker fechaelaboracion;
    private TableView ta_detallescomprobantecontabilidad;
    private ObservableList ol_detallescomprobante = FXCollections.observableArrayList();
    private DecimalFormat format = new DecimalFormat("#.0");
    HBox hbox = new HBox();
    HBox hbox2 = new HBox();
    private Button bu_asiento;
    private static final TextField centrodecostos = new TextField();
private static String concepto="";
    public Parent createContent() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        findByCodigocategoria();
        la_fechaelaboracion = new Label("Fecha: ");
        la_fechaelaboracion.setMinWidth(50);
        fechaelaboracion = new DatePicker(LocalDate.now());
        fechaelaboracion.setMinWidth(120);
        stage = new Stage(StageStyle.DECORATED);
        stage.setTitle(titlestage);
        stage.setOnHidden(e -> {
            if (e.getEventType() == e.WINDOW_HIDDEN) {

                try {

                    Tf_Codigo.setText(SihicApp.s_producto.getCodigo());

                    findByCodigo();
                    sp_producto.hide();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        sb_producto.setMinWidth(370);
        sb_producto.setPromptText("Digite código o Nombre para busqueda general");
        sp_producto = new SearchPopover(sb_producto, new PageBrowser(), SihicApp.s_producto, false);
        stack = new StackPane();

        /**
         * *********************************
         
        L_Cantidad_Salida = new Label("Cantidad Salida(Unidades): ");
        Tf_Cantidad_Salida = new TextField("0");
       
        
        Tc_ProductoI = new TableColumn();
        Tc_ProductoI.setText("Producto");
        sp_cuenta = new SearchPopover(sb_cuenta, pageBrowser, SihicApp.conPuc, false);
        sp_cuenta.setMaxSize(310, 70);
        sb_cuenta.setMaxWidth(310);
        sb_cuenta.setMinWidth(310);
        sb_cuenta.setOnAction(e -> {
            detalle.setText(concepto + SihicApp.s_FacturaProveedores.getNo_factura());
            detalle.requestFocus();

        });
        detalle.setMaxWidth(400);
        detalle.setMinWidth(400);
        tipomovimiento.setMaxWidth(200);
        tipomovimiento.setMinWidth(200);
        LoadChoiceBoxGeneral.cb_centrocostos.setMaxWidth(200);
        LoadChoiceBoxGeneral.cb_centrocostos.setMinWidth(200);
        valor.setMaxWidth(200);
        valor.setMinWidth(200);
        valor.setTextFormatter(new TextFormatter<>(c
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
        valor.setOnAction(e -> {
            try {
                AddDetalle();
            } catch (Exception ex) {
              
            }
        });

        la_cuenta.setMaxWidth(310);
        la_cuenta.setMinWidth(310);
        la_detalle.setMaxWidth(400);
        la_detalle.setMinWidth(400);
        la_tipomovimiento.setMaxWidth(200);
        la_tipomovimiento.setMinWidth(200);
        la_valor.setMaxWidth(200);
        la_valor.setMinWidth(200);
        la_centrocostos.setMaxWidth(100);
        la_centrocostos.setMinWidth(100);
        tipomovimiento.getItems().addAll("Débito", "Crédito");
        la_fechaelaboracion = new Label("Fecha: ");
        la_fechaelaboracion.setMinWidth(50);
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
        Tc_ValorTI.setCellValueFactory(new PropertyValueFactory("valor_total"));
       

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

        Tf_Valor = new TextField("0");
        Tf_Cantidad = new TextField("0");
        Tf_Cantidad.setMaxWidth(100);
        Tf_Codigo = new TextField();
        Tf_Codigo.setMinWidth(100);
        Tf_Precio = new TextField("0");
        Tf_Iva = new TextField("0");
        Tf_Total = new TextField("0");
        Tf_Total.setMaxWidth(150);
        Tf_TelefonoProveedor = new TextField("");
        Tf_ValorRecibido = new TextField("0");
        Tf_ValorRecibido.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        Tf_ValorRecibido.setMinHeight(30);
        Tf_PrecioCompra = new TextField("0");
        la_lote = new Label("Lote:");
        lote = new TextField();
        lote.setMaxWidth(100);
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
        ImageView imageView = new ImageView("/images/print.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_Print.setGraphic(imageView);

        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(25);
        imageView.setFitWidth(25
        );
        B_New = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
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

        imageView = null;
        imageView = new ImageView("/images/existencias.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        imageView = null;
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        B_Save = new Button("", imageView);

        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_Insertar = new Button();
        B_Insertar.setGraphic(imageView);
        B_Insertar.setText("");

        imageView = null;
        imageView = new ImageView("/images/bs_excel.jpg");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        //acciones de botones y cuadros de texto
        B_Insertar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    addProduct();
                } catch (ParseException ex) {
                    Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
                Tf_Codigo.requestFocus();

            }
        });
        B_Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    guardarFactura();
                } catch (Exception ex) {
                    Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        imageView = null;
        imageView = new ImageView("/images/asientos.png");
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        bu_asiento = new Button();
        bu_asiento.setGraphic(imageView);
        bu_asiento.setTooltip(new Tooltip("Agregar asientos contables"));
        bu_asiento.setOnAction(e -> {
            try {
                colocarasientoscontables();
            } catch (ParseException ex) {
                Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
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

                }
                Tf_NombreProveedor.requestFocus();

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
        L_NoFactura = new Label("No. Factura o Doc Equiv: ");
        L_NoFactura.setMinWidth(80);
        L_FormaPago = new Label("Forma de pago:   ");
        L_FormaPago.setMinWidth(108);
        L_NombreProveedor = new Label("Proveedor: ");
        L_NitProveedor = new Label("Nit: ");
        L_DireccionProveedor = new Label("Dirección:");
        L_TelefonoProveedor = new Label("Teléfono:");
        L_Producto = new Label(titlelabel);
        L_Producto.setMinWidth(100);
        L_Cantidad = new Label("Cantidad: ");
        L_Cantidad.setMinWidth(70);
        L_Codigo = new Label("Código: ");
        L_Codigo.setMinWidth(110);
       
        L_Valor = new Label("Valor Total:");
        L_Total = new Label("Total: ");
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
        L_PrecioCompra = new Label("Valor/U: ");
        L_PrecioCompra.setMinWidth(115);
       
       

        //Objeto Vbox coloca dentro de una caja objetos verticalmente
       /* V_box = new HBox();
        V_box.setSpacing(2);//espacio verticalmente entre objetos
        V_box.getChildren().addAll(Rb_Contado, Rb_Credito);
        H_Botones = new HBox();
        H_Botones.setSpacing(2);//espacio verticalmente entre objetos
        H_Botones.getChildren().addAll(B_New, B_Save);
        H_Producto = new HBox();
        H_Producto.setSpacing(2);//espacio verticalmente entre objetos
        H_Producto.getChildren().addAll(sb_producto, B_Buscar);
        H_BotonesInv = new HBox();
        H_BotonesInv.setSpacing(2);//espacio verticalmente entre objetos
        hbox.getChildren().addAll(sb_cuenta, detalle, tipomovimiento, valor);
        hbox.setSpacing(3);
        hbox2.getChildren().addAll(la_cuenta, la_detalle, la_tipomovimiento, la_valor);
        hbox2.setSpacing(3);
        ta_detallescomprobantecontabilidad = new TableView();
        ta_detallescomprobantecontabilidad.setMinHeight(150);
        ta_detallescomprobantecontabilidad.setMaxHeight(150);
        ta_detallescomprobantecontabilidad.setOnMouseClicked(e -> {

        });
        TableColumn cd = new TableColumn();
        cd.setMinWidth(130);
        cd.setText("CD");
        cd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConComprobanteContabilidad().getConsecutivoComprobanteContabilidad().toString()));

            }
        });
        TableColumn ccodigo = new TableColumn();
        ccodigo.setMinWidth(130);
        ccodigo.setText("Código");
        ccodigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getCodigo()));

            }
        });
        TableColumn ccuenta = new TableColumn();
        ccuenta.setMinWidth(300);
        ccuenta.setText("Cuenta");
        ccuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty(detalles.getValue().getConPuc().getNombre());

            }
        });
        TableColumn cdetalle = new TableColumn();
        cdetalle.setMinWidth(500);
        cdetalle.setText("Detalle");
        cdetalle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty(detalles.getValue().getDescripcion());

            }
        });
        TableColumn cparcialesdebe = new TableColumn();
        cparcialesdebe.setMinWidth(200);
        cparcialesdebe.setText("Debe");
        cparcialesdebe.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty(detalles.getValue().getParcialdebe().toString());

            }
        });
        TableColumn cparcialeshaber = new TableColumn();
        cparcialeshaber.setMinWidth(200);
        cparcialeshaber.setText("Haber");
        cparcialeshaber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty(detalles.getValue().getParcialhaber().toString());

            }
        });

        ta_detallescomprobantecontabilidad.getColumns().addAll(ccodigo, ccuenta, cdetalle, cparcialesdebe, cparcialeshaber);
        ta_detallescomprobantecontabilidad.setOnKeyPressed(e -> {
            try {
                removeDetalle();
            } catch (ParseException ex) {
                Logger.getLogger(FComprobanteContabilidad.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(FComprobanteContabilidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
        GridPane.setValignment(Gp_Proveedor, VPos.TOP);
        Gp_Proveedor.setHgap(2);
        Gp_Producto.setHgap(2);
        Gp_Inventario.setHgap(2);
        Gp_Factura.setHgap(4);
        Gp_Factura.setVgap(4);
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
        Gp_Proveedor.add(Tf_TelefonoProveedor, 3, 2);*/
        // Gp_Proveedor.add(L_FormaPago, 0, 3);
        // Gp_Proveedor.add(V_box, 1, 3);
      /*  Gp_Inventario.add(la_lote, 0, 0);
        Gp_Inventario.add(lote, 0, 1);
        Gp_Inventario.add(la_vencimiento, 1, 0);
        Gp_Inventario.add(fechavencimiento, 1, 1);
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
     

        // Tp_Producto=new TitledPane("Producto",Gp_Producto);
        Gp_Total.add(T_Total, 1, 1);
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
        Gp_Factura.add(Gp_Total, 1, 0);

        // Gp_Factura.add(H_BotonesInv, 1, 2);
        GridPane.setValignment(H_BotonesInv, VPos.BOTTOM);
        H_BotonesInv.setAlignment(Pos.CENTER);
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
        GridPane.setRowSpan(Gr_NumeroDigital, 2);
        GridPane.setRowSpan(Gp_Total, 2);
        GridPane.setColumnSpan(Gr_NumeroDigital, 5);
        GridPane.setHalignment(Gr_NumeroDigital, HPos.CENTER);
        GridPane.setValignment(numerodigital, VPos.CENTER);
        GridPane.setValignment(B_Buscarfacturas, VPos.TOP);
        Gp_Total.add(Gr_NumeroDigital, 1, 4);
        Gp_Factura.setAlignment(Pos.TOP_CENTER);
        Gp_Factura.autosize();
        Gp_FacturaItems.add(Ta_FacturaItems, 0, 0, 5, 5);
        Gp_Factura.add(Gp_FacturaItems, 0, 3, 2, 1);
        V_Factura = new VBox();
        V_Factura.setSpacing(5);
        /*
        Gp_Factura.add(hbox2, 0, 5, 2, 1);
        Gp_Factura.add(hbox, 0, 6, 2, 1);
        //Gp_Factura.add(hBox3,0,4,1,1);
        Gp_Factura.add(ta_detallescomprobantecontabilidad, 0, 7, 2, 2);
        GridPane.setValignment(sp_producto, VPos.TOP);
        Gp_Factura.add(sp_producto, 0, 3, 2, 5);
        Gp_Factura.setHgap(4);
        GridPane.setValignment(sp_cuenta, VPos.TOP);
        GridPane.setValignment(bu_asiento, VPos.TOP);
        bu_asiento.setAlignment(Pos.TOP_CENTER);
        Gp_Factura.add(sp_cuenta, 0, 7, 2, 2);
        Hb_Totales = new HBox(L_Total, Tf_Total);
        Hb_Totales.setSpacing(5);
        Hb_Totales.setAlignment(Pos.CENTER_RIGHT);
        Gp_FacturaItems.add(Hb_Totales, 4, 5);
        GridPane.setValignment(H_Botones, VPos.BOTTOM);
     
        Gp_FacturaItems.add(H_Botones, 1, 5);
        HBox hBox3 = new HBox(new Label("Plantilla de asiento contable:"), LoadChoiceBoxGeneral.cb_ModelosTiposAsientosContable, bu_asiento,la_centrocostos,LoadChoiceBoxGeneral.cb_sucursales);

        Gp_FacturaItems.add(hBox3, 2, 5);
        H_Botones.setAlignment(Pos.BOTTOM_LEFT);

        Gp_FacturaItems.setVgap(7);
        Gp_FacturaItems.setHgap(3);
        //Ancho de tabla y ancho de porcentaje de columnas

        Ta_FacturaItems.setMaxHeight(150);

        //V_Factura.getChildren().addAll(Gp_Factura);
        //V_Factura.setMinSize(900, 1000);
        Gp_Factura.autosize();
        Gp_FacturaItems.autosize();
        Gp_Factura.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Factura.getStyleClass().add("background");

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
        stack.getChildren().addAll(Gp_Factura, Gp_Message);
        stack.setAlignment(Pos.TOP_CENTER);

      
        Tf_Cantidad_Salida.setDisable(true);
        changeColumns();
        eventos_inputs();
        KeyCombination kc = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        Runnable task = () -> nuevaFactura();
        KeyCombination kg = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
        Runnable task2 = () -> {
            try {
                guardarFactura();
            } catch (Exception ex) {
                Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        // Mnemonic mnemonic = new Mnemonic(B_New, kc);
        SihicApp.getScene().getAccelerators().clear();
        SihicApp.getScene().getAccelerators().put(kc, task);
        SihicApp.getScene().getAccelerators().put(kg, task2);

        crearoeditar();

        Object[] _listFacturaItem = (SihicApp.s_FacturaProveedores.getFacturaItems()).toArray();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(_listFacturaItem);
        Ta_FacturaItems.setItems(Ol_FacturaItems);

        return stack;
    }

  /*  public void nuevaFactura() {
        if (!B_New.isDisabled()) {
            enabledAllTextField();
            SihicApp.s_FacturaProveedores = null;
            SihicApp.s_FacturaProveedores = new FacturaProveedores();
            SihicApp.conComprobanteContabilidad = null;
            SihicApp.conComprobanteContabilidad = new ConComprobanteContabilidad();

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
            Tf_Total.setText("0.00");
            lote.setText("");
            fechavencimiento.setValue(LocalDate.now());
            referencia.setText("");
            descripcion.setText("");
            LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().select(-1);
            Gr_NumeroDigital.getChildren().remove(numerodigital);
            numerodigital = null;
            numerodigital = new NumeroDigital(Color.WHITE, Color.rgb(50, 50, 50), "0.00");
            numerodigital.setLayoutX(10);
            numerodigital.setLayoutY(5);
            numerodigital.getTransforms().add(new Scale(0.43f, 0.43f, 0, 0));
            Gr_NumeroDigital.getChildren().add(numerodigital);
            SihicApp.s_FacturaProveedores.setFacturaDate(new Date());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = df.format(SihicApp.s_FacturaProveedores.getFacturaDate());
            LocalDate ld = LocalDate.parse(fecha);
            Tf_Fecha.setValue(ld);

            Tf_ValorRecibido.setText("0");
            nuevo();
            L_Message.setText("Nueva Factura");
            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            backgroundThread = new Thread(Task_Message);*/
            // Terminate the running thread if the application exits
           /* backgroundThread.setDaemon(true);

            // Start the thread
            backgroundThread.start();
            Tf_Fecha.requestFocus();
        }

    }

    private void guardarFactura() throws ParseException, ParseException, ParseException, ParseException, ParseException, InterruptedException, InterruptedException, InterruptedException, InterruptedException, InterruptedException, InterruptedException {

        //agregamos empleado
        if (validar_guardar()) {

            Proveedores t = new Proveedores();
            t.setNo_ident(Tf_NitProveedor.getText());
            t.setNombre(Tf_NombreProveedor.getText());
            t.setCelular(Tf_TelefonoProveedor.getText());
            t.setDir1(Tf_DireccionProveedor.getText());
            SihicApp.s_FacturaProveedores.setProveedores(t);
            try {
                SihicApp.s_FacturaProveedores.setNo_factura(Long.valueOf(Tf_NoFactura.getText().trim()));
            } catch (Exception e) {
                SihicApp.s_FacturaProveedores.setNo_factura(Long.valueOf("0"));
            }

            try {
                save();
            } catch (InterruptedException ex) {
                Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
          SihicApp.facturaProveedoresControllerClient.guardarFactura();
            LocalDate localDate = Tf_Fecha.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            SihicApp.s_FacturaProveedores.setFacturaDate(Date.from(instant));

            AdminCompras.getRecords();
            L_Message.setText("Factura Guardada");
            mensajes_procesos();

        } else {

            L_Message.setText("Algunos Valores no se han digitado");
            mensajes_procesos();

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

    public void addProduct() throws ParseException, InterruptedException {

        if (SihicApp.s_producto != null && !Tf_NitProveedor.getText().trim().equals("") && !Tf_NombreProveedor.getText().trim().equals("")) {

            if (proveedor == null) {
                proveedor = new Proveedores();
            }
            proveedor.setNo_ident(Tf_NitProveedor.getText());
            proveedor.setNombre(Tf_NombreProveedor.getText());
            proveedor.setCelular(Tf_TelefonoProveedor.getText());
            proveedor.setDir1(Tf_DireccionProveedor.getText());
            SihicApp.s_FacturaProveedores.setProveedores(proveedor);
            try {
                SihicApp.s_FacturaProveedores.setNo_factura(Long.valueOf(Tf_NoFactura.getText().trim()));
            } catch (Exception e) {
                SihicApp.s_FacturaProveedores.setNo_factura(Long.valueOf("0"));
            }
            SihicApp.s_FacturaProveedores.setCredito(Rb_Credito.isSelected());

            FacturaItemProveedores fip=null;
           

            SihicApp.s_FacturaProveedores.addProduct(SihicApp.s_producto, Float.valueOf(Tf_Cantidad.getText().trim()), BigDecimal.valueOf(Double.parseDouble(Tf_Valor.getText().trim())), fip);
            save();
            SihicApp.s_FacturaProveedores = SihicApp.facturaProveedoresControllerClient.crearFactura(SihicApp.s_FacturaProveedores, lote.getText(), fechavencimiento.getValue());
            L_Message.setText("Item Agregado");
            mensajes_procesos();
            Tf_Total.setText(SihicApp.s_FacturaProveedores.getTotalAmount().toString());
            setNumeroDigital();
            //Cargamos la tabla con el nuevo item
            O_ListFacturaItems = SihicApp.s_FacturaProveedores.getFacturaItems().toArray();
            Ol_FacturaItems.clear();

            Ol_FacturaItems.addAll(O_ListFacturaItems);
            Ta_FacturaItems.setItems(Ol_FacturaItems);
            nuevo();
            SihicApp.s_productoControllerClient.li_findproductosKardex(null);
            AdminCompras.getRecords();
        } else {
            Tf_NitProveedor.setStyle("-fx-background-color:#ff1600");
            Tf_NombreProveedor.setStyle("-fx-background-color:#ff1600");
        }

    }

    public void deleteItem() {

        FacturaItemProveedores fi = (FacturaItemProveedores) Ta_FacturaItems.getFocusModel().getFocusedItem();
        SihicApp.s_FacturaProveedores.removeProduct(fi.getProducto());
        SihicApp.facturaProveedoresControllerClient.crearFactura(SihicApp.s_FacturaProveedores, lote.getText(), fechavencimiento.getValue());
        O_ListFacturaItems = SihicApp.s_FacturaProveedores.getFacturaItems().toArray();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(O_ListFacturaItems);
        Ta_FacturaItems.setItems(Ol_FacturaItems);

        Tf_Total.setText(SihicApp.s_FacturaProveedores.getTotalAmount().toString());
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
        SihicApp.s_FacturaProveedores.modifyItem(fi.getProducto(), Float.valueOf(quantity.trim()));
        SihicApp.facturaProveedoresControllerClient.crearFactura(SihicApp.s_FacturaProveedores, lote.getText(), fechavencimiento.getValue());

        O_ListFacturaItems = SihicApp.s_FacturaProveedores.getFacturaItems().toArray();
        Ol_FacturaItems.clear();
        Ol_FacturaItems.addAll(O_ListFacturaItems);
        Ta_FacturaItems.setItems(Ol_FacturaItems);
        Tf_Total.setText(SihicApp.s_FacturaProveedores.getTotalAmount().toString());
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
        Tc_NoItem.setMinWidth(200);
        Tc_NoItem.setCellValueFactory(new PropertyValueFactory("position"));
        Tc_Producto = new TableColumn();
        Tc_Producto.setText("Producto");
        Tc_Producto.setMinWidth(530);
        Tc_Producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItemProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItemProveedores, String> facturaitem) {
                return new SimpleStringProperty(facturaitem.getValue().getProducto().getNombre());
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
        Ta_FacturaItems.getColumns().addAll(Tc_NoItem, Tc_Producto, Tc_Cantidad, Tc_ValorU, Tc_ValorT);
        //Ancho de tabla y ancho de porcentaje de columnas

        Ta_FacturaItems.setMinHeight(150);
        // GridPane.setFillWidth(veil, Boolean.TRUE);
        Ta_FacturaItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }*/

    /*public void disabledAllTextField() {
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
        List<Producto> li_producto = SihicApp.li_producto.stream().filter(line -> line.getCodigo().toUpperCase().equals(Tf_Codigo.getText().trim()) && line.getGenCategoriasProductos().getId().equals(SihicApp.genCategoriasProductos.getId())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());
        if (li_producto.size() > 0) {
            SihicApp.s_producto = li_producto.get(0);
            setProducto();
        } else {

            sb_producto.getSelectedText();
            sb_producto.requestFocus();
            sb_producto.setText("");
            Tf_Precio.setText("");
            Tf_Iva.setText("");
            referencia.setText("");
            descripcion.setText("");
            LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().select(-1);
        }
        Tf_Cantidad.requestFocus();
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

        SihicApp.s_FacturaProveedores.setEfectivo(BigDecimal.valueOf(Double.parseDouble(Tf_ValorRecibido.getText().trim())));
        SihicApp.facturaProveedoresControllerClient.crearFactura(SihicApp.s_FacturaProveedores, lote.getText(), fechavencimiento.getValue());

        Gr_ValorCambio.getChildren().remove(Nd_ValorCambio);
        Nd_ValorCambio = null;
        Nd_ValorCambio = new NumeroDigital(Color.WHITE, Color.rgb(50, 50, 50), SihicApp.s_FacturaProveedores.getDevolver().toString());
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

    private void calcularValorTotal() {
        Tf_Valor.setText((BigDecimal.valueOf(Double.valueOf(Tf_Cantidad.getText().trim())).multiply(new BigDecimal(Tf_PrecioCompra.getText().trim()))).toString());

    }

    private void setProducto() {

        sb_producto.setText(SihicApp.s_producto.getNombre());
        Tf_PrecioCompra.setText(SihicApp.s_producto.getPrecio2015().toString());

    }

  /*  public void crearoeditar() throws ParseException {
        enabledAllTextField();
        if (SihicApp.s_FacturaProveedores != null) {

            Tf_NombreProveedor.setText(SihicApp.s_FacturaProveedores.getProveedores().getNombre());
            proveedor = SihicApp.s_FacturaProveedores.getProveedores();
            Tf_NitProveedor.setText(SihicApp.s_FacturaProveedores.getProveedores().getNo_ident());
            Tf_TelefonoProveedor.setText(SihicApp.s_FacturaProveedores.getProveedores().getCelular());
            Tf_DireccionProveedor.setText(SihicApp.s_FacturaProveedores.getProveedores().getDir1());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFab = df.format(SihicApp.s_FacturaProveedores.getFacturaDate());
            LocalDate ld = LocalDate.parse(fechaFab);
            Tf_Fecha.setValue(ld);
            Tf_Total.setText(SihicApp.s_FacturaProveedores.getTotalAmount().toString());
            SihicApp.conComprobanteContabilidad = SihicApp.s_FacturaProveedores.getConComprobanteContabilidad();
            setNumeroDigital();
            if (SihicApp.s_FacturaProveedores.getCredito()) {
                Rb_Credito.setSelected(true);
            } else {
                Rb_Contado.setSelected(true);
            }
            O_ListFacturaItems = SihicApp.s_FacturaProveedores.getFacturaItems().toArray();
            Ol_FacturaItems.clear();

            Ol_FacturaItems.addAll(O_ListFacturaItems);
            Ta_FacturaItems.setItems(Ol_FacturaItems);
            sb_producto.setText("");
            Tf_Valor.setText("0");
            Tf_Cantidad.setText("0");
            Tf_PrecioCompra.setText("0");
            Tf_Codigo.setText("");
            lote.setText("");
            fechavencimiento.setValue(LocalDate.now());
            referencia.setText("");
            descripcion.setText("");
            LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().select(-1);
            Tf_Precio.setText("0");
            Tf_Iva.setText("0");
            Tf_PrecioCompra.setText("0");
            getDetallesComprobantes();
        } else {

            nuevaFactura();
        }
    }

    private void show() {

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

    private void nuevo() {
        sb_producto.requestFocus();
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
        
        SihicApp.s_producto = null;
        SihicApp.s_producto = new Producto();
        lote.setText("");
        fechavencimiento.setValue(LocalDate.now());
        referencia.setText("");
        descripcion.setText("");
        LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().select(-1);
        Tf_Fecha.requestFocus();
        B_Save.setDisable(false);
    }

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;
       
            Tf_NombreProveedor.setStyle("-fx-background-color:#000000");
            Tf_NitProveedor.setStyle("-fx-background-color:#000000");
            Tf_TelefonoProveedor.setStyle("-fx-background-color:#000000");
           
        

        //ft.setFromValue(0.0);
        // ft.setToValue(1);

    }

    public boolean validar_guardar() {
        boolean m_BCanSave = true;

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
                findByCodigo();

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
       
        descripcion.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                Tf_Iva.requestFocus();
            }
        });

        lote.setOnAction(e -> {
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
                try {
                    addProduct();
                } catch (ParseException ex) {
                    Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
                Tf_Codigo.requestFocus();
            }
        });
        B_New.setOnAction(e -> {

            nuevaFactura();

        });

    }

    public void findByProducto() {
        if (SihicApp.s_producto != null) {
            if (SihicApp.s_producto.getId() != null) {
                sb_producto.setText(SihicApp.s_producto.getNombre());
                Tf_Codigo.setText(SihicApp.s_producto.getCodigo());
                sp_producto.hide();
                Tf_PrecioCompra.setText(SihicApp.s_producto.getPrecio2015().toString());
                Tf_Cantidad.requestFocus();
            }
        }
    }

    public void removeDetalle() throws ParseException, InterruptedException {
        SihicApp.conComprobanteContabilidad.removeDetalle((LibroAuxiliar) ta_detallescomprobantecontabilidad.getSelectionModel().getSelectedItem());
        save();
        getDetallesComprobantes();
    }

    public void save() throws ParseException, InterruptedException {

        set_datos_comprobante();
        if (SihicApp.conComprobanteContabilidad.getId() == null) {
            SihicApp.conComprobanteContabilidadControllerClient.create();
        } else {
            SihicApp.conComprobanteContabilidadControllerClient.update();
        }
        SihicApp.s_FacturaProveedores.setConComprobanteContabilidad(SihicApp.conComprobanteContabilidad);
        L_Message.setText("Comprobante Diario Guardado");
        mensajes_procesos();

    }

    private void getDetallesComprobantes() throws ParseException {
        //colocamos los datos

        try {

            ol_detallescomprobante.clear();
            ol_detallescomprobante.addAll(SihicApp.conComprobanteContabilidad.getDetallesComprobanteContabilidads().toArray());
            // Ta_KardexProducto.getItems().clear();
            ta_detallescomprobantecontabilidad.setItems(ol_detallescomprobante);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void set_datos_comprobante() {
        //colocar fecha de datepicker
         SihicApp.conComprobanteContabilidad.setFechaelaboracion(UtilDate.localdatetodate(fechaelaboracion.getValue()));
         SihicApp.conComprobanteContabilidad.setTipo(jenum.EnumTiposComprobantes.COMPRAS6.ordinal());
         SihicApp.sucursales=LoadChoiceBoxGeneral.v_sucursales.get(LoadChoiceBoxGeneral.cb_sucursales.getSelectionModel().getSelectedIndex());
         SihicApp.conComprobanteContabilidad.setSucursales(SihicApp.sucursales);
    }

    public void AddDetalle() throws ParseException, InterruptedException {
        if (SihicApp.s_FacturaProveedores.getId() != null) {
            if (SihicApp.conPuc != null) {

                SihicApp.conComprobanteContabilidad.addPuc(SihicApp.conPuc, detalle.getText(), new BigDecimal(valor.getText().trim()), tipomovimiento.getSelectionModel().getSelectedIndex() == 0 ? true : false, LoadChoiceBoxGeneral.cb_centrocostos.getSelectionModel().getSelectedIndex());
                SihicApp.conComprobanteContabilidad = SihicApp.conPucControllerClient.save(SihicApp.conComprobanteContabilidad);
                getDetallesComprobantes();
                sb_cuenta.setText("");
                detalle.setText("");
                valor.setText("");
                tipomovimiento.getSelectionModel().select(-1);
            }
        }

    }

    public static void findByCodigocategoria() {

        switch (SihicApp.nombresolucion) {
            case "Compra. Planta Equipo":
                codigo = jenum.EnumCategorias.PLANTAYEQUIPO4.ordinal();
                titlelabel = "Propiedad Planta y Equipo";
                titlestage = "Compra de Planta y Equipo";
                appClass = "sihic.contabilidad.FProducto";
                concepto="Concepto de Compra Planta y Equipo N° Fact o Doc:";

                modelosasientos("1");
                break;
            case "Compra. Medtos":
                codigo = jenum.EnumCategorias.MEDICAMENTOS2.ordinal();
                titlelabel = "Médicamento";
                titlestage = "Compra de Médicamentos";
                appClass = "sihic.contabilidad.FMedicamento";
                concepto="Concepto de Compra de Medicamentos N° Fact o Doc:";

                modelosasientos("2");
                break;
            case "Compra. Insumos":
                codigo = jenum.EnumCategorias.INSUMOSMEDICOS3.ordinal();
                titlelabel = "Insumo Médico";
                titlestage = "Compra de Insumos Médicos";
                appClass = "sihic.contabilidad.FInsumos";
                concepto="Concepto de Compra de Insumos:";

                modelosasientos("3");
                break;
            case "Gastos Fijos":
                codigo = jenum.EnumCategorias.GASTOSFIJOS6.ordinal();
                titlelabel = "Concepto de Gasto";
                titlestage = "Gastos";
                appClass = "sihic.contabilidad.FProducto";
                concepto="Concepto de Gastos N° Fact o Doc:";
                modelosasientos("10");
                break;
            case "Honorarios y Serv.":
                codigo = jenum.EnumCategorias.HONORARIOSSERVICIOS7.ordinal();
                titlelabel = "Honorario o Servicio";
                titlestage = "Honorarios y Servicios";
                appClass = "sihic.contabilidad.FProducto";
                concepto="Concepto de Honorarios y Servicios N° Fact o Doc:";

                modelosasientos("11");
                break;
        }

    }

    private void colocarasientoscontables() throws ParseException, InterruptedException {
        SihicApp.modeloTiposDocumentosContables = LoadChoiceBoxGeneral.v_ModelosTiposAsientosContables.get(LoadChoiceBoxGeneral.cb_ModelosTiposAsientosContable.getSelectionModel().getSelectedIndex());
             for (AsientosModelosTiposDocumentosContables amtc : SihicApp.modeloTiposDocumentosContables.getAsientosModelosTiposDocumentosContableses()) {
              SihicApp.conPuc = amtc.getConPuc();
              AddAsientosSegumModelo(amtc.porcentajereal().multiply(SihicApp.s_FacturaProveedores.getTotalAmount()), amtc.getTipomovimiento().toUpperCase().equals("D") ? true : false, amtc.getCentrocostos());
        }
    }

    private static void modelosasientos(String codigo) {
        LoadChoiceBoxGeneral.cb_ModelosTiposAsientosContable.getItems().clear();
        if (SihicApp.li_TiposDocumentosContableses.size() > 0) {
            for (ModeloTiposDocumentosContables mtdc : SihicApp.li_ModeloTiposDocumentosContableses.stream().filter(line -> line.getTiposDocumentosContables().getCodigo().equals(codigo)) //filters the line, equals to "mkyong"
                    .collect(Collectors.toList())) {
                LoadChoiceBoxGeneral.v_ModelosTiposAsientosContables.add(mtdc);
                LoadChoiceBoxGeneral.cb_ModelosTiposAsientosContable.getItems().add(mtdc.getTiposDocumentosContables().getCodigo() + " - " + mtdc.getDescripcion());

            }
        }
    }

    public void AddAsientosSegumModelo(BigDecimal valor, boolean movimiento, int centrocostos) throws ParseException, InterruptedException {
        if (SihicApp.conComprobanteContabilidad.getId() != null) {
            if (SihicApp.conPuc != null) {
                SihicApp.conComprobanteContabilidad.addPuc(SihicApp.conPuc, titlestage + " a proveedor: " + SihicApp.s_FacturaProveedores.getProveedores().getNombre() + " N° Factura oDoc Equiv: " + SihicApp.s_FacturaProveedores.getNo_factura().toString(), valor, movimiento, centrocostos);
                save();
                getDetallesComprobantes();
            }
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

    }*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
