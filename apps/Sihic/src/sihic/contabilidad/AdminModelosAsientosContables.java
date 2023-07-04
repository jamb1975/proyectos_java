/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TitledPane;
import modelo.ModeloTiposDocumentosContables;

/**
 *
 * @author adminlinux
 */
public class AdminModelosAsientosContables extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic = new TableView();
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_generic = FXCollections.observableArrayList();
    ;
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
        tp_generic.setText("Administrar plantillas de asientos contables");
        tp_generic.setCollapsible(false);
        stage.setTitle("Modelos Asientos Contables");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                SihicApp.modeloTiposDocumentosContables = null;
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
                delete();
            } catch (ParseException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemdelete);
        stack = new StackPane();
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(100);
        buscar = new TextField();
        buscar.setMinWidth(600);
        buscar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminServicios.class.getName()).log(Level.SEVERE, null, ex);
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
                SihicApp.s_producto = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_generic = new TableView();

        TableColumn codigotipodocumentocontable = new TableColumn();
        codigotipodocumentocontable.setMinWidth(300);
        codigotipodocumentocontable.setText("Código Tipo Documento Contable");
        codigotipodocumentocontable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModeloTiposDocumentosContables, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModeloTiposDocumentosContables, String> mtdc) {

                return new SimpleStringProperty(mtdc.getValue().getTiposDocumentosContables().getCodigo());

            }
        });

        TableColumn tipodocumentocontable = new TableColumn();
        tipodocumentocontable.setMinWidth(400);
        tipodocumentocontable.setText("Tipo Documento Contable");
        tipodocumentocontable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModeloTiposDocumentosContables, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModeloTiposDocumentosContables, String> mtdc) {

                return new SimpleStringProperty(mtdc.getValue().getTiposDocumentosContables().getNombre());

            }
        });
        TableColumn decripcion = new TableColumn();
        decripcion.setMinWidth(630);
        decripcion.setText("Descripción");
        decripcion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModeloTiposDocumentosContables, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModeloTiposDocumentosContables, String> mtdc) {

                return new SimpleStringProperty(mtdc.getValue().getDescripcion());

            }
        });
        tv_generic.getColumns().addAll(codigotipodocumentocontable, tipodocumentocontable, decripcion);
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
        gp_generico.setMinSize(1340, 640);
        gp_generico.getStylesheets().add(SihicApp.hojaestilos);
        gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0, la_buscar, buscar, bu_nuevo);

        gp_generico.add(tv_generic, 0, 3, 3, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        getRecords();
        return stack;
    }

    public static void getRecords() throws ParseException {
        SihicApp.modelosTiposDocumentosContablesControllerClient.getRecords(buscar.getText());
        try {
            if (buscar == null) {
                buscar = new TextField();
            }
            ol_generic.clear();
            ol_generic.addAll(SihicApp.li_ModeloTiposDocumentosContableses);
            tv_generic.setItems(ol_generic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        appClass = "sihic.contabilidad.FModelosTiposDocumentosContables";
        clz = null;
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

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        if ((tv_generic.getSelectionModel()) != null) {
            SihicApp.modeloTiposDocumentosContables = (ModeloTiposDocumentosContables) tv_generic.getSelectionModel().getSelectedItem();

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void delete() throws ParseException {
        SihicApp.modelosTiposDocumentosContablesControllerClient.delete();
        getRecords();
    }

}
