/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.administracion;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.Solucion;
import sihic.SihicApp;
import sihic.controlador.SolucionControllerClient;

/**
 *
 * @author adminlinux
 */
public class FAdminSoluciones extends Application {

    private GridPane gpgeneric;
    private Label l_buscar;
    private TextField buscar;
    private ObservableList ol_generic = FXCollections.observableArrayList();
    ;
    private static SolucionControllerClient solucionControllerClient;
    private String appPathSolucion;
    private Class clzSolucion;
    private Object appClassSolucion;
    private Stage stageSolucion = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    private Parent P_GenSolucion;
    private static TreeTableView tv_solucion;
    private static ContextMenu contextMenu;
    private static Solucion solucion;
    private StackPane stack;
// Turn on multiple-selection mode for the TreeTableView
    private static TreeTableViewSelectionModel<Solucion> tsm;
    private static TreeItem<Solucion> rootNode;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemeliminar;
    private Button nuevo;
    private ImageView img;
    private TitledPane tp_generic;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        tp_generic = new TitledPane();
        tp_generic.setText("Administrador Soluciones");
        tp_generic.setCollapsible(false);
        stack = new StackPane();
        gpgeneric = new GridPane();

        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            SihicApp.solucion = null;
            try {
                checkregistroexistentePadre();
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
                if (checkregistroexistente()) {
                    show();

                }
            } catch (Exception x) {

            }
        });
        img = null;
        img = new ImageView("/images/delete.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemeliminar = new MenuItem("Eliminar", img);
        itemeliminar.setOnAction(e -> {

            try {
                if (checkregistroexistente()) {
                    delete();

                }
            } catch (Exception x) {

            }
        });
        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemeliminar);
        tv_solucion = new TreeTableView();
        tv_solucion.setContextMenu(contextMenu);
        TreeTableColumn<Solucion, String> codigo = new TreeTableColumn();
        codigo.setMinWidth(250);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Solucion, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Solucion, String> solucion) {

                return new SimpleStringProperty(String.valueOf(solucion.getValue().getValue().getNumeral()));

            }
        });
        codigo.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        codigo.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Solucion, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Solucion, String> t) {
                int numeral = Integer.valueOf(t.getNewValue());
                SihicApp.solucion = (Solucion) t.getTreeTablePosition().getTreeItem().getValue();
                SihicApp.solucion.setNumeral(numeral);
                try {
                    modificaritem();
                } catch (ParseException ex) {
                    Logger.getLogger(FAdminSoluciones.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        TreeTableColumn nombre = new TreeTableColumn();

        nombre.setMinWidth(1080);
        nombre.setText("Solución");
        nombre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Solucion, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Solucion, String> solucion) {

                return new SimpleStringProperty(String.valueOf(solucion.getValue().getValue().getSolucion()));

            }
        });
        nombre.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        nombre.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Solucion, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Solucion, String> t) {
                String nombre = t.getNewValue();
                SihicApp.solucion = (Solucion) t.getTreeTablePosition().getTreeItem().getValue();
                SihicApp.solucion.setSolucion(nombre);
                try {
                    modificaritem();
                } catch (ParseException ex) {
                    Logger.getLogger(FAdminSoluciones.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        tv_solucion.getColumns().addAll(codigo, nombre);
        tv_solucion.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        tv_solucion.setEditable(true);
        ImageView imageView;
        solucionControllerClient = new SolucionControllerClient();
        stageSolucion.setTitle("Crear Solución");
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        nuevo = new Button("", imageView);
        //nuevo.setMaxWidth(130); 
        nuevo.setDisable(false);
        nuevo.setOnAction(e
                -> {
            try {
                SihicApp.solucion = null;
                checkregistroexistentePadre();
                show();
            } catch (Exception ex) {

            }
        });

        //**********Ventana Personas
        appPathSolucion = "sihic.administracion.FSoluciones";
        clzSolucion = Class.forName(appPathSolucion);
        appClassSolucion = clzSolucion.newInstance();
        //**********Ventana Factura

        l_buscar = new Label("Buscar:");
        l_buscar.setMinWidth(100);
        buscar = new TextField();
        buscar.setOnKeyPressed(e -> {
            try {

                getSoluciones();
            } catch (Exception e3) {
            }
        });

        gpgeneric.getStylesheets().add(SihicApp.hojaestilos);
        gpgeneric.getStyleClass().add("background");
        gpgeneric.add(l_buscar, 0, 0);
        gpgeneric.add(buscar, 1, 0);
        gpgeneric.add(nuevo, 2, 0);
        gpgeneric.add(tv_solucion, 0, 1, 3, 1);
        gpgeneric.setVgap(5);
        gpgeneric.setHgap(5);
        gpgeneric.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gpgeneric);
        stack.getChildren().add(tp_generic);
        gpgeneric.setMinWidth(1340);
        tv_solucion.setMinHeight(550);
        stack.autosize();
        getSoluciones();
        return stack;
    }

    public static void getSoluciones() throws ParseException {
        //colocamos los datos

        init_tretable();
        rootNode = null;
        solucion = null;
        solucion = new Solucion();
        solucionControllerClient = null;

        solucionControllerClient = new SolucionControllerClient();
        try {
            solucion = solucionControllerClient.getSolucion();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (solucion != null) {
            rootNode = new TreeItem<>(solucion);
            createTree(solucion, rootNode);
            tv_solucion.setRoot(rootNode);

        }
        try {
            tv_solucion.getTreeItem(tsm.getSelectedIndex()).setExpanded(true);
        } catch (Exception e) {

        }
        expandTreeView(rootNode);
    }

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tsm.getSelectedItem()) != null) {
            SihicApp.solucion = (Solucion) tsm.getSelectedItem().getValue();
            return true;
        } else {
            return false;
        }
    }

    private boolean checkregistroexistentePadre() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tsm.getSelectedItem()) != null) {
            solucion = (Solucion) tsm.getSelectedItem().getValue();
            return true;

        } else {
            return false;
        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        P_GenSolucion = null;
        P_GenSolucion = (Parent) clzSolucion.getMethod("createContent").invoke(appClassSolucion);
        scene = null;
        scene = new Scene(P_GenSolucion, Color.TRANSPARENT);

        if (stageSolucion.isShowing()) {
            stageSolucion.close();
        }

//set scene to stage
        stageSolucion.sizeToScene();

        //center stage on screen
        stageSolucion.centerOnScreen();
        stageSolucion.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageSolucion.show();
    }

    // A change listener to track the change in selected index
    private static void createTree(Solucion solucion, TreeItem<Solucion> root) {

        for (Solucion s : solucionControllerClient.getSolucionHijos(solucion)) {
            TreeItem<Solucion> node = new TreeItem<>(s);
            root.getChildren().add(node);
            if (solucionControllerClient.getSolucionHijos(s).size() > 0) {
                createTree(s, node);
            }

        }

    }

    public static Solucion getSolucion() {
        return solucion;
    }

    public static void setSolucion(Solucion solucion) {
        FAdminSoluciones.solucion = solucion;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private static void init_tretable() {

        tsm = null;
        tsm = tv_solucion.getSelectionModel();

        //tv_conpuc.autosize();
    }

    private static void expandTreeView(TreeItem<?> item) {
        if (item != null && !item.isLeaf()) {
            item.setExpanded(true);
            for (TreeItem<?> child : item.getChildren()) {
                expandTreeView(child);
            }
        }
    }

    private void modificaritem() throws ParseException {
        solucionControllerClient.update();

    }

    public void delete() throws ParseException {
        solucionControllerClient.delete();
        getSoluciones();
    }

}
