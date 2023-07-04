/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad.documentos;

import sihic.SihicApp;
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
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TitledPane;
import modelo.FacturaProveedores;
import modelo.GenCategoriasProductos;
import sihic.contabilidad.ImpresionFactura;

/**
 *
 * @author adminlinux
 */
public class AdminCompras extends Application {

    private static int codigo = 0;
    private static String title = "";
    private static String titlestage = "";
    private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private Label la_datefrom;
    private Label la_dateto;
    private static TextField buscar;
    private static ObservableList ol_generic = FXCollections.observableArrayList();
    ;
    private Button bu_buscar;
    private Button bu_nuevo;
    private Button bu_configuraciones;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
    private ImpresionFactura impresionFactura;
    private SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
    private String appClass;
    private Class clz;
    private Object app;
    private Parent parent;
    private Stage stage = new Stage(StageStyle.DECORATED);
    Scene scene = null;
    private ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemabonos;
    private ImageView img;
    private TitledPane tp_generic;
    private static DatePicker datefrom;
    private static DatePicker dateto;

    private Stage stageAbonos = new Stage(StageStyle.DECORATED);
    private String appClassConfiguraciones;
    private Class clzConfiguraciones;
    private Object appConfiguraciones;
    private Parent parentConfiguraciones;
    private Stage stageConfiguraciones = new Stage(StageStyle.DECORATED);
    Scene sceneFactura = null;
    Scene sceneConfiguraciones = null;
    private static ChoiceBox cb_formaspago;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        stack = new StackPane();
        findByCodigo();
        stageConfiguraciones.setTitle("Configuración Cuentas Puc e Impuestos");
        tp_generic = new TitledPane();
        tp_generic.setCollapsible(false);
        tp_generic.setText(title);
        stageAbonos.setTitle("Abonos a Factura ");
        stage.setTitle(titlestage);
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                SihicApp.s_FacturaProveedores = null;
                show();
            } catch (Exception x) {

            }
        });
        img = null;
        img = new ImageView("/images/editar.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemeditar = new MenuItem("Editar", img);
        itemeditar.setOnAction(e -> {

            try {
                checkregistroexistente();
                show();
            } catch (Exception x) {
                x.printStackTrace();
            }
        });
        img = null;
        img = new ImageView("/images/abonos.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemabonos = new MenuItem("Abonos a factura", img);
        itemabonos.setOnAction(e -> {

        });
        contextMenu = new ContextMenu(itemnuevo, itemeditar);
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(70);
        buscar = new TextField();
        buscar.setMinWidth(300);
        buscar.setOnKeyReleased(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminCompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        la_datefrom = new Label("Desde:");
        datefrom = new DatePicker(LocalDate.now());
        datefrom.setMinWidth(150);
        datefrom.setOnAction(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminCompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        dateto = new DatePicker(LocalDate.now());
        la_dateto = new Label("Hasta:");
        la_datefrom.setMinWidth(70);
        la_dateto.setMinWidth(70);
        dateto.setMinWidth(150);
        dateto.setOnAction(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminCompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        cb_formaspago = new ChoiceBox<Object>();
        cb_formaspago.getItems().addAll("Todos", "Contado", "Crédito", "Saldo > 0");
        cb_formaspago.getSelectionModel().getSelectedIndex();
        cb_formaspago.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        Label la_formaspago = new Label("Formas de Pago:");
        la_formaspago.setMinWidth(150);
        cb_formaspago.setMinWidth(150);

        ImageView imageView;

        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_buscar = new Button("", imageView);
        bu_buscar.setDisable(false);
        bu_buscar.setOnAction(e
                -> {
            try {
                getRecords();

            } catch (Exception ex) {

            }
        });
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_nuevo = new Button("", imageView);
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                SihicApp.s_FacturaProveedores = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        imageView = new ImageView("/images/configuraciones.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_configuraciones = new Button("", imageView);
        bu_configuraciones.setDisable(false);
        bu_configuraciones.setOnAction(e -> {

            try {
                showConfiguraciones();
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(AdminCompras.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AdminCompras.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(AdminCompras.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(AdminCompras.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminCompras.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(AdminCompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        gp_generico = new GridPane();
        tv_generic = new TableView();

        tv_generic.setOnContextMenuRequested(e -> {
            try {
                checkregistroexistente();
            } catch (Exception ex) {

            }

        });

        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(150);
        nofactura.setText("N° Doc o N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {

                return new SimpleStringProperty(factura.getValue().getNo_factura().toString());

            }
        });

        TableColumn noident = new TableColumn();
        noident.setMinWidth(150);
        noident.setText("N° Ident");
        noident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {

                return new SimpleStringProperty(factura.getValue().getProveedores().getNo_ident());

            }
        });

        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(470);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {

                return new SimpleStringProperty(factura.getValue().getProveedores().getNombre());

            }
        });
        TableColumn fecha = new TableColumn();
        fecha.setMinWidth(140);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {

                return new SimpleStringProperty(sihic.util.UtilDate.formatodiamesyear(factura.getValue().getFacturaDate()));

            }
        });
        TableColumn valor = new TableColumn();
        valor.setMinWidth(100);
        valor.setText("valor");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {

                return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());

            }
        });
        TableColumn abonos = new TableColumn();
        abonos.setMinWidth(150);
        abonos.setText("Total Pagos");
        abonos.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {

                return new SimpleStringProperty(factura.getValue().getValor_egresos().toString());

            }
        });
        TableColumn saldo = new TableColumn();
        saldo.setMinWidth(150);
        saldo.setText("Saldo");
        saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {

                return new SimpleStringProperty((factura.getValue().getTotalAmount().subtract(factura.getValue().getValor_abonos())).toString());

            }
        });
        tv_generic.getColumns().addAll(fecha, nofactura, noident, nombre, valor, abonos, saldo);
        //ta_librodiario.setMinWidth(650);

        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setMinHeight(577);
        tv_generic.setContextMenu(contextMenu);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinWidth(gp_generico.getLayoutBounds().getWidth());
        Gp_Message.setMaxHeight(40);
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        gp_generico.getStylesheets().add(SihicApp.hojaestilos);
        gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0, la_buscar, buscar, la_datefrom, datefrom, la_dateto, dateto, bu_nuevo);
        gp_generico.add(tv_generic, 0, 3, 7, 1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        gp_generico.setMinWidth(1340);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic);
        // stack.minWidth(1340);
        getRecords();
        return stack;
    }

    public static void getRecords() throws ParseException {
        //colocamos los datos
        try {
            findByCodigo();
            SihicApp.li_facturasproveedores = SihicApp.facturaProveedoresControllerClient.getRecords(datefrom.getValue().toString(), dateto.getValue().toString(), buscar.getText(), SihicApp.genCategoriasProductos);
            System.out.println("size fac prov->" + SihicApp.li_facturasproveedores.size());
            ol_generic.clear();
            ol_generic.addAll(SihicApp.li_facturasproveedores.toArray());
            tv_generic.setItems(ol_generic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {

        appClass = "sihic.contabilidad.documentos.FFacturaCompra";
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
    }

    private void showConfiguraciones() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {

        appClassConfiguraciones = "sihic.contabilidad.FConfiguracionesCompra";
        clzConfiguraciones = Class.forName(appClassConfiguraciones);
        appConfiguraciones = clzConfiguraciones.newInstance();

        parentConfiguraciones = null;
        parentConfiguraciones = (Parent) clz.getMethod("createContent").invoke(appConfiguraciones);
        scene = null;
        scene = new Scene(parent, Color.TRANSPARENT);

        if (stageConfiguraciones.isShowing()) {
            stageConfiguraciones.close();
        }

//set scene to stage
        stageConfiguraciones.sizeToScene();

        //center stage on screen
        stageConfiguraciones.centerOnScreen();
        stageConfiguraciones.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageConfiguraciones.show();
    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        if ((tv_generic.getSelectionModel()) != null) {
            SihicApp.s_FacturaProveedores = (FacturaProveedores) tv_generic.getSelectionModel().getSelectedItem();

        }
    }

    public void setItemMenu() {

        contextMenu.getItems().clear();
        int opcion = SihicApp.s_FacturaProveedores.getCredito() == null ? 1 : SihicApp.s_FacturaProveedores.getCredito() == true ? 0 : 1;
        switch (opcion) {
            case 0:
                contextMenu.getItems().addAll(itemnuevo, itemeditar, itemabonos);
                break;
            case 1:
                contextMenu.getItems().addAll(itemnuevo, itemeditar);
                break;

        }

    }

    public void indexChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        try {

            getRecords();
        } catch (Exception e) {

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static void findByCodigo() {
        switch (SihicApp.nombresolucion) {
            case "Compra. Planta Equipo":

                codigo = jenum.EnumCategorias.PLANTAYEQUIPO4.ordinal();
                title = "Administrar Compra de Planta y Equipo";
                titlestage = "Compra de Planta y Equipo";
                break;
            case "Compra. Medtos":
                codigo = jenum.EnumCategorias.MEDICAMENTOS2.ordinal();
                title = "Administrar Compra de Medicamentos";
                titlestage = "Compra de Médicamentos";
                break;
            case "Compra. Insumos":
                codigo = jenum.EnumCategorias.INSUMOSMEDICOS3.ordinal();
                title = "Administrar Compra de Insumos Médicos";
                titlestage = "Compra de Insumos Médicos";
                break;
            case "Gastos Fijos":
                codigo = jenum.EnumCategorias.GASTOSFIJOS6.ordinal();
                title = "Administrar Gastos";
                titlestage = "Gastos";
                break;
            case "Honorarios y Serv.":
                codigo = jenum.EnumCategorias.HONORARIOSSERVICIOS7.ordinal();
                title = "Administrar Honorarios y Servicios";
                titlestage = "Honorarios y Servicios";
                break;
        }
        List<GenCategoriasProductos> li_categorias = SihicApp.l_categoriasproductos.stream().filter(line -> line.getCodigo() == codigo) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());
        if (li_categorias.size() > 0) {
            SihicApp.genCategoriasProductos = li_categorias.get(0);

        } else {
            SihicApp.genCategoriasProductos = null;
        }

    }
}
