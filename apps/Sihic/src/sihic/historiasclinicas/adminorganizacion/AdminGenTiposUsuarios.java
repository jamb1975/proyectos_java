/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.adminorganizacion;
import sihic.SihicApp;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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
import modelo.GenTiposUsuarios;
import sihic.contabilidad.ImpresionFactura;
import sihic.controlador.GenTiposUsuariosControllerClient;

/**
 *
 * @author adminlinux
 */
public class AdminGenTiposUsuarios extends Application {

    private GridPane gp_generico;
    private static TableView tv_gentiposusuarios;
    private Label la_buscar;
    private static TextField buscar;

    private static ObservableList ol_gentiposusuarios = FXCollections.observableArrayList();
    ;
    private static List<GenTiposUsuarios> l_gentiposusuarios;
    private static GenTiposUsuariosControllerClient genTiposUsuariosControllerClient;
    private Button bu_buscar;
    private Button bu_nuevo;
    private static GenTiposUsuarios genTiposUsuarios;
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

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        stage.setTitle("Tipos Usuarios");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
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

        contextMenu = new ContextMenu(itemnuevo, itemeditar);
        stack = new StackPane();
        appClass = "sihic.historiasclinicas.adminorganizacion.FGenTiposUsuarios";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        genTiposUsuariosControllerClient = new GenTiposUsuariosControllerClient();

        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(120);

        buscar = new TextField();
        buscar.setMinWidth(50);

        ImageView imageView;

        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_buscar = new Button("", imageView);
        bu_buscar.setDisable(false);
        bu_buscar.setOnAction(e
                -> {
            try {
                getListGenTiposUsuarios();

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
                genTiposUsuarios = null;
                show();

            } catch (Exception ex) {

            }
        });

        gp_generico = new GridPane();
        tv_gentiposusuarios = new TableView();
        tv_gentiposusuarios.setMinHeight(550);

        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(150);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenTiposUsuarios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenTiposUsuarios, String> genTiposUsuarios) {

                return new SimpleStringProperty(genTiposUsuarios.getValue().getCodigo());

            }
        });

        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(200);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenTiposUsuarios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenTiposUsuarios, String> genTiposUsuarios) {

                return new SimpleStringProperty(genTiposUsuarios.getValue().getNombre());

            }
        });

        tv_gentiposusuarios.getColumns().addAll(codigo, nombre);
        tv_gentiposusuarios.setMinHeight(gp_generico.getLayoutBounds().getHeight());
        tv_gentiposusuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_gentiposusuarios.setContextMenu(contextMenu);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        gp_generico.setMinSize(400, 300);
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
        gp_generico.addRow(0, bu_nuevo);

        gp_generico.add(tv_gentiposusuarios, 0, 2, 2, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);
        getListGenTiposUsuarios();
        return stack;
    }

    public static void getListGenTiposUsuarios() throws ParseException {
        try {
            l_gentiposusuarios = genTiposUsuariosControllerClient.li_genTiposUsuarios();
            ol_gentiposusuarios.clear();
            ol_gentiposusuarios.addAll(l_gentiposusuarios.toArray());
            tv_gentiposusuarios.setItems(ol_gentiposusuarios);
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
        if ((tv_gentiposusuarios.getSelectionModel()) != null) {
            genTiposUsuarios = (GenTiposUsuarios) tv_gentiposusuarios.getSelectionModel().getSelectedItem();
            show();
        }
    }

    private void eliminar() throws ParseException {
        genTiposUsuariosControllerClient.delete(genTiposUsuarios);
        getListGenTiposUsuarios();
    }

    public static GenTiposUsuarios getGenTiposUsuarios() {
        return genTiposUsuarios;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
