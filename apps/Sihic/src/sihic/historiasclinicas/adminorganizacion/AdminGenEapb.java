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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.GenEapb;
import sihic.contabilidad.ImpresionFactura;
import sihic.controlador.GenEapbControllerClient;

/**
 *
 * @author adminlinux
 */
public class AdminGenEapb extends Application {

    private GridPane gp_generico;
    private static TableView tv_geneapb;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_geneapb = FXCollections.observableArrayList();
    ;
    private static List<GenEapb> l_geneapb;
    private static GenEapbControllerClient genEapbControllerClient;
    private Button bu_buscar;
    private Button bu_nuevo;
    private static GenEapb genEapb;
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
        stage.setTitle("Empresa Administradoras de Beneficios(Eapb)");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                genEapb=null;
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

        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemdelete);
        stack = new StackPane();
        appClass = "sihic.historiasclinicas.adminorganizacion.FGenEapb";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        genEapbControllerClient = new GenEapbControllerClient();
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(120);
        buscar = new TextField();
        buscar.setMinWidth(50);
        buscar.setOnKeyReleased(e -> {
            try {
                getListGenEapb();
            } catch (ParseException ex) {
                Logger.getLogger(AdminGenEapb.class.getName()).log(Level.SEVERE, null, ex);
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
                getListGenEapb();

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
                genEapb=null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_geneapb = new TableView();

        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(150);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenEapb, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenEapb, String> genEapb) {

                return new SimpleStringProperty(genEapb.getValue().getCodigo());

            }
        });
        TableColumn nit = new TableColumn();
        nit.setMinWidth(150);
        nit.setText("Nit");
        nit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenEapb, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenEapb, String> genEapb) {

                return new SimpleStringProperty(genEapb.getValue().getNit());

            }
        });
        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(200);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenEapb, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenEapb, String> genEapb) {

                return new SimpleStringProperty(genEapb.getValue().getNombre());

            }
        });

        TableColumn telefono = new TableColumn();
        telefono.setMinWidth(200);
        telefono.setText("Telefono");
        telefono.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenEapb, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenEapb, String> genEapb) {

                return new SimpleStringProperty(genEapb.getValue().getTelefono());

            }
        });
        TableColumn direccion = new TableColumn();
        direccion.setMinWidth(200);
        direccion.setText("Dirección");
        direccion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenEapb, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenEapb, String> genEapb) {

                return new SimpleStringProperty(genEapb.getValue().getDireccion());

            }
        });
        gp_generico.setMinSize(900, 450);
        tv_geneapb.getColumns().addAll(codigo, nit, nombre, telefono, direccion);
        tv_geneapb.setMinHeight(gp_generico.getLayoutBounds().getHeight());
        tv_geneapb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_geneapb.setContextMenu(contextMenu);
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

        gp_generico.add(tv_geneapb, 0, 3, 7, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);
        getListGenEapb();
        return stack;
    }

    public static void getListGenEapb() throws ParseException {
        try {
            l_geneapb = genEapbControllerClient.li_geneapb(buscar.getText().trim());
            ol_geneapb.clear();
            ol_geneapb.addAll(l_geneapb.toArray());
            tv_geneapb.setItems(ol_geneapb);
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
        if ((tv_geneapb.getSelectionModel()) != null) {
            genEapb = (GenEapb) tv_geneapb.getSelectionModel().getSelectedItem();
            show();
        }
    }

    private void eliminar() throws ParseException {
        genEapbControllerClient.delete(genEapb);
        getListGenEapb();
    }

    public static GenEapb getGenEapb() {
        return genEapb;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
