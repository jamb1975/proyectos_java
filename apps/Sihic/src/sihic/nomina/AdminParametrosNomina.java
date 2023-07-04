package sihic.nomina;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.ParametrosNomina;
import sihic.SihicApp;
import sihic.contabilidad.ImpresionFactura;

/**
 *
 * @author adminlinux
 */
public class AdminParametrosNomina extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_parametrosnomina = FXCollections.observableArrayList();
    ;
    private static List<ParametrosNomina> l_parametrosnomina;
    private Button bu_buscar;
    private Button bu_nuevo;
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
    private MenuItem itemdelete;
    private ImageView img;
    private TitledPane tp_generic;
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        tp_generic = new TitledPane();
        tp_generic.setText("Administración Parámetros");
        tp_generic.setCollapsible(false);
        stage.setTitle("Parámetros");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                SihicApp.cargosEntidad = null;
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
            } catch (Exception x) {
                x.printStackTrace();
            }
        });
        img = null;
        img = new ImageView("/images/delete.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemdelete = new MenuItem("Eliminar", img);
        itemdelete.setOnAction(e -> {

            try {
                checkregistroexistente();
                eliminar();
            } catch (Exception x) {

            }
        });

        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemdelete);
        stack = new StackPane();
        appClass = "sihic.nomina.FParametrosNomina";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(120);
        buscar = new TextField();
        buscar.setMinWidth(250);
        buscar.textProperty().addListener((observable, oldValue, newValue) -> {

            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminCargosEntidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
                SihicApp.cargosEntidad = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_generic = new TableView();
        TableColumn caño = new TableColumn();
        caño.setMinWidth(150);
        caño.setText("Año");
        caño.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(String.valueOf(parametrosNomina.getValue().getAño()));

            }
        });
        TableColumn salmin = new TableColumn();
        salmin.setMinWidth(150);
        salmin.setText("Salario Minimo");
        salmin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getSalariominimo().toString());

            }
        });
        TableColumn auxtransp = new TableColumn();
        auxtransp.setMinWidth(150);
        auxtransp.setText("Auxilio Transporte");
        auxtransp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getAuxiliotransporte().toString());

            }

        });
        TableColumn salud = new TableColumn();
        salud.setMinWidth(150);
        salud.setText("% Salud");
        salud.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_salud().toString());

            }
        });
        TableColumn pension = new TableColumn();
        pension.setMinWidth(150);
        pension.setText("% Pensión");
        pension.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_pension().toString());

            }

        });
        TableColumn suelfondopensiones = new TableColumn();
        suelfondopensiones.setMinWidth(150);
        suelfondopensiones.setText("% Fondo Pensión");
        suelfondopensiones.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_fp().toString());

            }

        });
        TableColumn arl = new TableColumn();
        arl.setMinWidth(150);
        arl.setText("% Arl");
        arl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_arl().toString());

            }

        });
        TableColumn cajacompfam = new TableColumn();
        cajacompfam.setMinWidth(150);
        cajacompfam.setText("% Caja Comp. Familiar");
        cajacompfam.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_cajacompfam().toString());

            }

        });
        TableColumn icbf = new TableColumn();
        icbf.setMinWidth(150);
        icbf.setText("% Icbf");
        icbf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_icbf().toString());

            }

        });
        TableColumn sena = new TableColumn();
        sena.setMinWidth(150);
        sena.setText("% Sena");
        sena.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_sena().toString());

            }

        });
        TableColumn cesantias = new TableColumn();
        cesantias.setMinWidth(150);
        cesantias.setText("% Cesantias");
        cesantias.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_cesantias().toString());

            }

        });
        TableColumn porcintcesantias = new TableColumn();
        porcintcesantias.setMinWidth(150);
        porcintcesantias.setText("% Int Cesantias");
        porcintcesantias.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_intcesantias().toString());

            }

        });
        TableColumn prima = new TableColumn();
        prima.setMinWidth(150);
        prima.setText("% Prima");
        prima.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_prima().toString());

            }

        });
        TableColumn vacaciones = new TableColumn();
        vacaciones.setMinWidth(150);
        vacaciones.setText("% Vacaciones");
        vacaciones.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_vacaciones().toString());

            }

        });
        TableColumn solidaridad = new TableColumn();
        solidaridad.setMinWidth(150);
        solidaridad.setText("% Solidaridad");
        solidaridad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_solidaridad().toString());

            }

        });
        TableColumn uvt = new TableColumn();
        uvt.setMinWidth(150);
        uvt.setText("% Uvt");
        uvt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getUvt().toString());

            }

        });
        
        TableColumn porc_retefuente95 = new TableColumn();
        porc_retefuente95.setMinWidth(150);
        porc_retefuente95.setText(" Uvt<=95");
        porc_retefuente95.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_retefuente95().toString());

            }

        });
        TableColumn porc_retefuentemay95y150 = new TableColumn();
        porc_retefuentemay95y150.setMinWidth(150);
        porc_retefuentemay95y150.setText("95>Uvt<=150");
        porc_retefuentemay95y150.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_retefuentemay95y150().toString());

            }

        });
        TableColumn porc_retefuentemay150y360 = new TableColumn();
        porc_retefuentemay150y360.setMinWidth(150);
        porc_retefuentemay150y360.setText("150>Uvt<=360");
        porc_retefuentemay150y360.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_retefuentemay150y360().toString());

            }

        });
        TableColumn porc_retefuentemay360 = new TableColumn();
        porc_retefuentemay360.setMinWidth(150);
        porc_retefuentemay360.setText("Uvt>360");
        porc_retefuentemay360.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(parametrosNomina.getValue().getPorc_retefuentemay360().toString());

            }

        });
        TableColumn maxcantidadsalminimosolidarida = new TableColumn();
        maxcantidadsalminimosolidarida.setMinWidth(150);
        maxcantidadsalminimosolidarida.setText("Max Cant Salarios Min");
        maxcantidadsalminimosolidarida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrosNomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrosNomina, String> parametrosNomina) {

                return new SimpleStringProperty(String.valueOf(parametrosNomina.getValue().getMaxcantidadsalminimosolidarida()));

            }

        });
        Gp_Message = new GridPane();

        Gp_Message.setMaxSize(40, gp_generico.getLayoutBounds().getWidth());
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
        gp_generico.addRow(0, la_buscar, buscar, bu_nuevo);

        gp_generico.add(tv_generic, 0, 3, 7, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        gp_generico.setMinSize(1320, 630);
        gp_generico.setMaxSize(1320, 630);
        tv_generic.getColumns().addAll(caño,salmin, salud, pension, arl,auxtransp,cajacompfam,cesantias,porcintcesantias,icbf,sena,uvt,maxcantidadsalminimosolidarida,prima,porc_retefuente95,porc_retefuentemay95y150,porc_retefuentemay150y360,porc_retefuentemay360);
        tv_generic.setMinHeight(570);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setContextMenu(contextMenu);

        getRecords();

        return stack;
    }

    public static void getRecords() throws ParseException {
        try {
            SihicApp.parametrosNominaControllerClient.getRecords();
            l_parametrosnomina =SihicApp.li_parametrosNomina;
            ol_parametrosnomina.clear();
            ol_parametrosnomina.addAll(l_parametrosnomina.toArray());
            tv_generic.setItems(ol_parametrosnomina);
        } catch (Exception e) {

        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            SihicApp.parametrosNomina = (ParametrosNomina) tv_generic.getSelectionModel().getSelectedItem();
            show();
        }
    }

    private void eliminar() throws ParseException {
        //genPersonasControllerClient.delete(genPacientes);
        //getListGenPersonas();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
