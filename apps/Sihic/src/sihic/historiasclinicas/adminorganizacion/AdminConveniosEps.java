/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.adminorganizacion;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
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
import modelo.GenConvenios;
import sihic.SihicApp;
import sihic.administracion.FSoluciones;
import sihic.contabilidad.ImpresionFactura;
import sihic.controlador.GenConveniosEapbControllerClient;

/**
 *
 * @author adminlinux
 */
public class AdminConveniosEps extends Application {

    private GridPane gp_generico;
    private static TableView tv_convenioseps;
    private static ObservableList ol_convenioseps = FXCollections.observableArrayList();
    private static List<GenConvenios> li_convenioseps;
    private static GenConveniosEapbControllerClient genConveniosControllerClient;
    private Button bu_buscar;
    private Button bu_nuevo;
    private Label la_search;
    private static TextField search;
    private static GenConvenios genconvenioseapb;
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
    private ImageView img;
    private static DatePicker Dp_Date_from;
    private static DatePicker Dp_Date_to;
    private Label L_Date_from;
    private Label L_Date_to;
    private Label la_tipoconvenio;
    private static ChoiceBox tipoconvenio;

    private static TableColumn cnit;
    private static TableColumn ceps;
    private static TableColumn cfecha;
    private static TableColumn cnocontrato;
    private static TableColumn cporcentaje;
    private static TableColumn cdescripcion;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        L_Date_from = new Label("Desde: ");
        L_Date_to = new Label("A: ");
        Dp_Date_from = new DatePicker();
        Dp_Date_from.setValue(LocalDate.now());
        Dp_Date_to = new DatePicker();
        Dp_Date_to.setValue(LocalDate.now());
        stage.setTitle("Convenio");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            genconvenioseapb = null;
            genconvenioseapb = new GenConvenios();
            try {
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

            }
        });
        la_search = new Label("Buscar: ");
        search = new TextField();
        contextMenu = new ContextMenu(itemnuevo, itemeditar);
        stack = new StackPane();
        appClass = "sihic.historiasclinicas.adminorganizacion.FGenConvenios";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        genConveniosControllerClient = new GenConveniosEapbControllerClient();
        genconvenioseapb = new GenConvenios();
        la_tipoconvenio = new Label();
        tipoconvenio = new ChoiceBox();
        la_tipoconvenio.setText("Tipo Convenio:");
        tipoconvenio.getItems().addAll("Contrato", "Particular");
        tipoconvenio.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        tipoconvenio.getSelectionModel().select(0);
        ImageView imageView;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_nuevo = new Button("", imageView);
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                genconvenioseapb = null;
                genconvenioseapb = new GenConvenios();
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Dp_Date_from.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                if (tipoconvenio.getSelectionModel().getSelectedIndex() == 0) {
                    getListConveniosEps(0);
                } else {
                    getListConveniosEps(1);
                }

            }
        });
        Dp_Date_to.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                if (tipoconvenio.getSelectionModel().getSelectedIndex() == 0) {
                    getListConveniosEps(0);
                } else {
                    getListConveniosEps(1);
                }

            }
        });
        gp_generico = new GridPane();
        tv_convenioseps = new TableView();
        cnit = new TableColumn();
        cnit.setMinWidth(250);
        cnit.setText("Nit");
        cnit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenConvenios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenConvenios, String> genconvenioseapb) {

                return new SimpleStringProperty(String.valueOf(genconvenioseapb.getValue().getGenEapb().getNit()));

            }
        });
        ceps = new TableColumn();
        ceps.setMinWidth(250);
        ceps.setText("Eps");
        ceps.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenConvenios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenConvenios, String> genconvenioseapb) {

                return new SimpleStringProperty(String.valueOf(genconvenioseapb.getValue().getGenEapb().getNombre()));

            }
        });
        cfecha = new TableColumn();
        cfecha.setMinWidth(250);
        cfecha.setText("Fecha Contrato");
        cfecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenConvenios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenConvenios, String> genconvenioseapb) {

                return new SimpleStringProperty(genconvenioseapb.getValue().getFechacontrato() != null ? df.format(genconvenioseapb.getValue().getFechacontrato()) : "");

            }
        });
        cnocontrato = new TableColumn();
        cnocontrato.setMinWidth(250);
        cnocontrato.setText("N° Contrato");
        cnocontrato.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenConvenios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenConvenios, String> genconvenioseapb) {

                return new SimpleStringProperty(String.valueOf(genconvenioseapb.getValue().getNumerocontrato()));

            }
        });
        cporcentaje = new TableColumn();
        cporcentaje.setMinWidth(100);
        cporcentaje.setText("% Descuento");
        cporcentaje.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenConvenios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenConvenios, String> genconvenioseapb) {

                return new SimpleStringProperty(String.valueOf(genconvenioseapb.getValue().getPorcentajedescuento()));

            }
        });
        cdescripcion = new TableColumn();
        cdescripcion.setMinWidth(250);
        cdescripcion.setText("Nombre");
        cdescripcion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenConvenios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenConvenios, String> genconvenioseapb) {

                return new SimpleStringProperty(genconvenioseapb.getValue().getDescripcion());

            }
        });
        tv_convenioseps.setContextMenu(contextMenu);
        tv_convenioseps.getColumns().addAll(cfecha, cnit, ceps, cnocontrato, cporcentaje);
        tv_convenioseps.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        gp_generico.setMinWidth(1010);
        Gp_Message.setMinSize(gp_generico.getLayoutBounds().getHeight(), gp_generico.getLayoutBounds().getWidth());
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
        gp_generico.addRow(0, la_search, search, bu_nuevo, L_Date_from, Dp_Date_from, L_Date_to, Dp_Date_to, la_tipoconvenio, tipoconvenio);
        gp_generico.add(tv_convenioseps, 0, 1, 9, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);
        getListConveniosEps(0);
        return stack;
    }

    public static void getListConveniosEps(int tipo) {
        //colocamos los datos

        try {
            SihicApp.genConveniosEapbControllerClient.getRecords();
            li_convenioseps = genConveniosControllerClient.convenioseps(Dp_Date_from.getValue().toString(), Dp_Date_to.getValue().toString(), search.getText(), tipo);
            ol_convenioseps.clear();
            ol_convenioseps.addAll(li_convenioseps.toArray());
            // tv_convenioseps.getItems().clear();
            System.out.println("opc size->" + li_convenioseps.size());
            if (tipo == 0) {

                if (tv_convenioseps.getColumns().indexOf(cnit) == -1) {
                    tv_convenioseps.autosize();
                    tv_convenioseps.getColumns().removeAll(cnit, ceps, cnocontrato, cdescripcion, cporcentaje);
                    tv_convenioseps.getColumns().addAll(cnit, ceps, cnocontrato, cporcentaje);
                    tv_convenioseps.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                }

            } else {
                if (tv_convenioseps.getColumns().indexOf(cdescripcion) == -1) {
                    tv_convenioseps.autosize();
                    tv_convenioseps.getColumns().removeAll(cnit, ceps, cnocontrato, cporcentaje);
                    tv_convenioseps.getColumns().addAll(cdescripcion, cporcentaje);
                    tv_convenioseps.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                }

            }
            tv_convenioseps.setItems(ol_convenioseps);

        } catch (Exception e) {
            //    e.printStackTrace();
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
        if ((tv_convenioseps.getSelectionModel()) != null) {
            genconvenioseapb = (GenConvenios) tv_convenioseps.getSelectionModel().getSelectedItem();
            show();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static GenConvenios getGenconvenioseapb() {
        return genconvenioseapb;
    }

    public static void setGenconvenioseapb(GenConvenios genconvenioseapb) {
        AdminConveniosEps.genconvenioseapb = genconvenioseapb;
    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        getListConveniosEps(tipoconvenio.getSelectionModel().getSelectedIndex());
    }

}
