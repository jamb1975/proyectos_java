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
import modelo.Proveedores;

/**
 *
 * @author adminlinux
 */
public class AdminProveedores extends Application {

    private GridPane gp_generico;
    private static TableView tv_proveedores;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_proveedoress = FXCollections.observableArrayList();
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
        tp_generic.setText("Administrar Proveedores");
        tp_generic.setCollapsible(false);
        stage.setTitle("Proveedores");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                SihicApp.s_proveedores = null;
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
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(AdminProveedores.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AdminProveedores.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(AdminProveedores.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(AdminProveedores.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                delete();
            } catch (ParseException ex) {
                Logger.getLogger(AdminProveedores.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemdelete);
        stack = new StackPane();
        appClass = "sihic.contabilidad.FProveedor";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(100);
        buscar = new TextField();
        buscar.setOnKeyReleased(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminProveedores.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        buscar.setMinWidth(700);

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
                SihicApp.s_proveedores = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_proveedores = new TableView();

        TableColumn nit = new TableColumn();
        nit.setMinWidth(150);
        nit.setText("Nit");
        nit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Proveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Proveedores, String> proveedor1) {

                return new SimpleStringProperty(proveedor1.getValue().getNo_ident());

            }
        });

        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(400);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Proveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Proveedores, String> proveedores) {

                return new SimpleStringProperty(proveedores.getValue().getNombre());

            }
        });
        TableColumn dir = new TableColumn();
        dir.setMinWidth(400);
        dir.setText("Dirección");
        dir.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Proveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Proveedores, String> proveedores) {

                return new SimpleStringProperty(proveedores.getValue().getDir1());

            }
        });
        TableColumn tel = new TableColumn();
        tel.setMinWidth(200);
        tel.setText("Teléfono");
        tel.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Proveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Proveedores, String> proveedores) {

                return new SimpleStringProperty(proveedores.getValue().getCelular());

            }
        });
        TableColumn email = new TableColumn();
        email.setMinWidth(180);
        email.setText("Email");
        email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Proveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Proveedores, String> proveedores) {

                return new SimpleStringProperty(proveedores.getValue().getEmail());

            }
        });
        tv_proveedores.getColumns().addAll(nit, nombre, tel, dir, email);
        //ta_librodiario.setMinWidth(650);

        tv_proveedores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_proveedores.setMinHeight(577);
        tv_proveedores.setContextMenu(contextMenu);
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
        gp_generico.addRow(0, la_buscar, buscar, bu_buscar, bu_nuevo);
        gp_generico.add(tv_proveedores, 0, 3, 7, 1);
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
        //colocamos los datos
        try {
            SihicApp.li_proveedores = SihicApp.proveedoresControllerClient.getRecords(null);

            ol_proveedoress.clear();

            ol_proveedoress.addAll(SihicApp.proveedoresControllerClient.getRecords(buscar.getText().trim()).toArray());
            tv_proveedores.setItems(ol_proveedoress);
        } catch (Exception e) {
            e.printStackTrace();
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
        if ((tv_proveedores.getSelectionModel()) != null) {
            SihicApp.s_proveedores = (Proveedores) tv_proveedores.getSelectionModel().getSelectedItem();

        }
    }

    private void delete() throws ParseException {
        SihicApp.proveedoresControllerClient.delete();
        getRecords();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
