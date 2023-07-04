package sihic.contabilidad;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javafx.animation.FadeTransition;
import javafx.application.Application;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import modelo.FacturaProveedores;
import modelo.Kardex;
import modelo.Producto;
import sihic.SihicApp;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.control.SearchBox;
import sihic.controlador.ProductoControllerClient;
import sihic.controlador.ProveedoresControllerClient;
import sihic.digitos.NumeroDigital;
import sihic.general.LoadChoiceBoxGeneral;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import modelo.GenCategoriasProductos;
import static sihic.SihicApp.s_productoControllerClient;

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
public class InventarioServicios extends Application {

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
    private Button B_VerAbonos;
    private Button B_Buscar;
    private Button B_SaveProd;
    private Button B_Buscarfacturas;
    private Button B_Insertar;
    //Tabla Items de factura
    private TableView Ta_Kardex;
    //GridPane row and columns
    private GridPane Gp_Factura;

    private GridPane Gp_Producto;
    private GridPane Gp_Inventario;

    private GridPane Gp_FacturaItems;
    private GridPane Gp_Totales;
    private GridPane Gp_VlrRecibido;

    private TitledPane Tp_Producto;
    private TitledPane Tp_Inventario;
    private HBox Hb_Totales;
    //TitledPane container solucion factura

    //Texto
    private Text T_NoFactura;
    private Text T_Total;
    //Etiquetas datos cliente
    private Label L_Producto;
    private Label L_Codigo;

    //Campos de texto datos cliente 
    private SearchBox sb_producto = new SearchBox();
    private SearchPopover sp_producto;

    private TextField Tf_Codigo;
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
    private ObservableList Ol_Kardex = FXCollections.observableArrayList();
    private Object[] O_ListKardex;

    //columnas tabla factura
    private TableColumn tc_producto;
    private TableColumn tc_codigo;
    private TableColumn tc_cantidad;
    private TableColumn tc_valor;
    //Objetos de persistencia y modelso de datos

    ProveedoresControllerClient proveedoresControllerClient;
    ProductoControllerClient productoControllerClient;
    //Stage mostrar formulario findproducto

    private Stage stage;
    private static final Stage stage2 = new Stage(StageStyle.TRANSPARENT);
    private Thread Th_Message;
    StackPane stack;

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
    // Create a KeyCombination for Alt + C

    Scene scene = null;
    private TitledPane tp_generic;

    /**
     * ** Inicalizamos objetos
     *///
    public Parent createContent() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        tp_generic = new TitledPane();
        tp_generic.setText("Inventario de Servicios");
        tp_generic.setCollapsible(false);
        stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Servicios y Procedimientos");
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
        LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);;
        sb_producto.setMinWidth(300);
        sb_producto.setPromptText("Digite código o Nombre para busqueda general");
        sp_producto = new SearchPopover(sb_producto, new PageBrowser(), SihicApp.s_producto, false);
        stack = new StackPane();

        /**
         * *********************************
         */
        tc_codigo = new TableColumn();
        tc_codigo.setText("Código");
        tc_codigo.setMinWidth(330);
        tc_codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getProducto().getNombre());
            }
        });
        tc_producto = new TableColumn();
        tc_producto.setText("Producto");
        tc_producto.setMinWidth(1000);
        tc_producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getProducto().getCodigo());
            }
        });
        Ta_Kardex = new TableView();
        Ta_Kardex.getColumns().addAll(tc_codigo, tc_producto);
        //ta_librodiario.setMinWidth(650);

        Ta_Kardex.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Ta_Kardex.setMinHeight(577);

        //Objetos gridpane
        Gp_Factura = new GridPane();
        Gp_Producto = new GridPane();
        Gp_Inventario = new GridPane();
        Gp_FacturaItems = new GridPane();
        Gp_Totales = new GridPane();

        //******************************************************************************
        Gp_Producto.autosize();
        //Task

        Thread th = new Thread(task);
        th.setDaemon(false);
        th.start();
        //campos de texto
        //datos cliente
        Tf_Codigo = new TextField();
        Tf_Codigo.setMinWidth(100);

        productoControllerClient = new ProductoControllerClient();

        //Inicializamos objetos de interfaz
        //Cargamos los datos en la tabla Ta_facturaItems
        sc = new StringConverter<Object>() {
            @Override
            public String toString(Object t) {
                return t == null ? null : t.toString();
            }

            @Override
            public Object fromString(String string) {

                return string;
            }
        };

        //botones
        B_Print = new Button();
        ImageView imageView = new ImageView("/images/print.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Print.setGraphic(imageView);

        imageView = null;
        imageView = new ImageView("/images/new.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_New = new Button("", imageView);
        B_New.setMaxWidth(130);
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
                SihicApp.s_producto = null;
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
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_Save = new Button("", imageView);
        B_Save.setDisable(true);
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_SaveProd = new Button();
        B_SaveProd.setGraphic(imageView);
        B_SaveProd.setText("");

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
        B_VerAbonos = new Button("Ver Pedidos y Pagos");
        B_VerAbonos.setMaxWidth(130);

        //acciones de botones y cuadros de texto
        B_Insertar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                try {
                    addInventario();
                } catch (ParseException ex) {
                    Logger.getLogger(InventarioServicios.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        B_Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

            }
        });

        sb_producto.setOnAction(e -> {
            try {
                findByProducto();
            } catch (ParseException ex) {
                Logger.getLogger(InventarioServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        L_Producto = new Label("Producto: ");
        L_Producto.setMinWidth(100);
        L_Codigo = new Label("Código Producto: ");
        L_Codigo.setMinWidth(110);
        T_Total = new Text("TOTAL");
        T_Total.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_Total.setFont(Font.font("ARIAL", FontWeight.BOLD, 30));
        T_Total.setFill(Color.WHITE);

        //Objeto Vbox coloca dentro de una caja objetos verticalmente
        V_box = new HBox();
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
        Gp_Producto.setVgap(3);
        Gp_Inventario.setVgap(5);
        GridPane.setColumnSpan(T_Total, 5);
        GridPane.setRowSpan(T_Total, 3);
        GridPane.setColumnSpan(Ta_Kardex, 2);
        GridPane.setValignment(Gp_Producto, VPos.TOP);//alinea verticalmente
        GridPane.setValignment(Gp_Inventario, VPos.TOP);
        GridPane.setValignment(L_Codigo, VPos.TOP);
        GridPane.setValignment(T_Total, VPos.TOP);
        GridPane.setHalignment(T_Total, HPos.CENTER);
        Gp_Producto.setHgap(2);
        Gp_Inventario.setHgap(2);
        Gp_Factura.setHgap(2);
        // Gp_Inventario.add(L_Codigo, 0, 0);
        //Gp_Inventario.add(Tf_Codigo, 1, 0);
        Gp_Inventario.add(L_Producto, 0, 0);
        Gp_Inventario.add(H_Producto, 1, 0, 2, 1);
        Gp_Inventario.add(B_Insertar, 4, 0, 1, 1);

        // Tp_Producto=new TitledPane("Producto",Gp_Producto);
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
        // Gp_Factura.add(Gp_Producto, 0, 1,2,1);
        Gp_Factura.add(Gp_Inventario, 0, 2, 2, 1);

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
        Gp_Factura.setAlignment(Pos.TOP_CENTER);
        Gp_Factura.autosize();
        Gp_FacturaItems.add(Ta_Kardex, 0, 0, 5, 5);
        Gp_FacturaItems.add(sp_producto, 0, 0, 5, 5);
        Gp_Factura.setMinWidth(1340);
        Gp_Factura.add(Gp_FacturaItems, 0, 3, 2, 1);
        GridPane.setValignment(H_Botones, VPos.BOTTOM);
        H_Botones.setAlignment(Pos.BOTTOM_LEFT);
        Gp_FacturaItems.setVgap(7);
        Gp_FacturaItems.setHgap(3);
        //Ancho de tabla y ancho de porcentaje de columnas

        Ta_Kardex.setMaxHeight(260);
        V_Factura = new VBox();
        V_Factura.setSpacing(5);

        V_Factura.getChildren().addAll(Gp_Factura);
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

        //  Tp_Factura= new TitledPane("Factura de Venta", V_Factura);
        V_Factura.autosize();

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
        tp_generic.setContent(Gp_Factura);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        stack.setAlignment(Pos.TOP_CENTER);

        eventos_inputs();
        KeyCombination kw = new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN);
        Runnable task13 = () -> nuevaFactura();
        KeyCombination kl = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
        Runnable task14 = () -> {

        };
        // Mnemonic mnemonic = new Mnemonic(B_New, kc);
       
        SihicApp.getScene().getAccelerators().put(kw, task13);
        SihicApp.getScene().getAccelerators().put(kl, task14);
        findCategoria();

        return stack;
    }

    public void nuevaFactura() {
        if (!B_New.isDisabled()) {
            enabledAllTextField();
            SihicApp.s_FacturaProveedores = null;
            SihicApp.s_FacturaProveedores = new FacturaProveedores();
            Ol_Kardex.clear();
            B_Save.setDisable(false);
            B_New.setDisable(true);
            Tf_Codigo.setText("");
            Rb_Credito.setSelected(false);
            Rb_Contado.setSelected(false);
            sb_producto.setText("");
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
            nuevo();
            L_Message.setText("Nueva Factura");
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

    public void disabledAllTextField() {
        Ta_Kardex.setDisable(true);
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
        Ta_Kardex.setDisable(false);
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
        List<Producto> li_producto = SihicApp.li_producto.stream().filter(line -> line.getCodigo().toUpperCase().contains(Tf_Codigo.getText().trim())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());
        if (li_producto.size() > 0) {
            SihicApp.s_producto = li_producto.get(0);
            setProducto();
        } else {

            sb_producto.getSelectedText();
            sb_producto.requestFocus();
            sb_producto.setText("");
        }
        B_Insertar.requestFocus();
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
                sb_producto.setStyle(null);
                sb_producto.getStyleClass().add("text-field");
                Tf_Codigo.setStyle(null);
                Tf_Codigo.getStyleClass().add("text-field");
                Ta_Kardex.setStyle(null);

            }
        });

    }

    private void setProducto() {

        sb_producto.setText(SihicApp.s_producto.getNombre());

    }

    private void addInventario() throws ParseException {

        if (findproductokardex()) {
            SihicApp.s_kardex = null;
            SihicApp.s_kardex = new Kardex();
            SihicApp.s_kardex.setCantidad_entrada(1);
            SihicApp.s_kardex.setProducto(SihicApp.s_producto);
            SihicApp.s_kardex.setValor_unidad(SihicApp.s_producto.getPrecio2015());
            SihicApp.s_kardex.setValor_entrada(SihicApp.s_producto.getPrecio2015());
            SihicApp.s_kardex.setValor_saldo(SihicApp.s_producto.getPrecio2015());
            SihicApp.s_kardex.setCantidad_saldo(1);
            SihicApp.kardexControllerClient.create(SihicApp.s_kardex);
            SihicApp.s_productoControllerClient.li_findserviciosKardex(null);
            SihicApp.s_productoControllerClient.li_findproductosKardex(null);
            L_Message.setText("Servicio Agregado");
            getRecordsKardex();
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
        } else {
            L_Message.setText("Servicio ya esta en el inventario");
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

    private void show() {
        String appClass;
        Class clz;
        Object app;
        Parent parent;

        appClass = "sihic.contabilidad.FServicios";
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

    public static void main(String[] args) {
        launch(args);
    }

    private void nuevo() {
        sb_producto.requestFocus();
        sb_producto.setText("");
        Tf_Codigo.setText("");
        SihicApp.s_producto = null;
        SihicApp.s_producto = new Producto();
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

    public void eventos_inputs() {

        Tf_Codigo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                findByCodigo();

            }
        });

        B_Insertar.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    addInventario();
                } catch (ParseException ex) {
                    Logger.getLogger(InventarioServicios.class.getName()).log(Level.SEVERE, null, ex);
                }
                Tf_Codigo.requestFocus();
            }
        });
        B_New.setOnAction(e -> {

            nuevaFactura();

        });

    }

    private void getRecordsKardex() throws ParseException {
        SihicApp.kardexControllerClient.getRecords(SihicApp.s_producto);
        Ol_Kardex.clear();
        Ol_Kardex.addAll(SihicApp.kardexControllerClient.getRecords(SihicApp.s_producto).toArray());
        Ta_Kardex.setItems(Ol_Kardex);
    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        B_SaveProd.requestFocus();
    }

    public void findByProducto() throws ParseException {
        if (SihicApp.s_producto != null) {
            if (SihicApp.s_producto.getId() != null) {
                sb_producto.setText("Código: " + SihicApp.s_producto.getCodigo() + " Nombre: " + SihicApp.s_producto.getNombre());
                sp_producto.hide();
                getRecordsKardex();
                B_Insertar.requestFocus();
            }
        }
    }

    public void findCategoria() {
        List<GenCategoriasProductos> li_categorias = SihicApp.l_categoriasproductos.stream().filter(line -> line.getCodigo() == jenum.EnumCategorias.SERVICIOS1.ordinal()) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());
        if (li_categorias.size() > 0) {
            SihicApp.genCategoriasProductos = li_categorias.get(0);

        }
    }

    public boolean findproductokardex() {

        List<Kardex> li_categorias = SihicApp.li_serviciosKardex.stream().filter(line -> line.getProducto().getCodigo().equals(SihicApp.s_producto.getCodigo())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());
        if (li_categorias.size() > 0) {
            return false;

        } else {
            return true;
        }
    }
}
