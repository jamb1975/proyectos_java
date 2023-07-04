/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.consultas;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import modelo.AgnMedicos;
import sihic.SihicApp;
import sihic.administracion.FSoluciones;
import sihic.contabilidad.ImpresionFactura;
import sihic.controlador.HclHistoriasClinicasControllerClient;

/**
 *
 * @author adminlinux
 */
public class AdminAgnMedicos extends Application {

    private GridPane gp_generico;
    private static TableView tv_agnmedicos;
    private static ObservableList ol_agnmedicos = FXCollections.observableArrayList();
    private static List<AgnMedicos> l_agnmedicos;
    private static HclHistoriasClinicasControllerClient genericControllerClient;
    private Button bu_buscar;
    private Button bu_nuevo;
    private Label la_search;
    private static TextField search;
    private static AgnMedicos agnmedicos;
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

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        stage.setTitle("Médicos");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            agnmedicos = null;
            agnmedicos = new AgnMedicos();
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
        appClass = "sihic.historiasclinicas.consultas.FAgnMedicos";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        genericControllerClient = new HclHistoriasClinicasControllerClient();
        agnmedicos = new AgnMedicos();

        ImageView imageView;

        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_nuevo = new Button("", imageView);
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                agnmedicos = null;
                agnmedicos = new AgnMedicos();
                show();

            } catch (Exception ex) {

            }
        });

        gp_generico = new GridPane();
        tv_agnmedicos = new TableView();
        TableColumn cedula = new TableColumn();
        cedula.setMinWidth(100);
        cedula.setText("Cédula");
        cedula.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnMedicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnMedicos, String> agnMedicos) {

                return new SimpleStringProperty(String.valueOf(agnMedicos.getValue().getGenPersonas().getDocumento()));

            }
        });
        TableColumn noregistro = new TableColumn();
        noregistro.setMinWidth(100);
        noregistro.setText("N° Registro");
        noregistro.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnMedicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnMedicos, String> agnMedicos) {

                return new SimpleStringProperty(String.valueOf(agnMedicos.getValue().getNumeroRegistro()));

            }
        });

        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(300);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnMedicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnMedicos, String> agnMedicos) {

                return new SimpleStringProperty(String.valueOf(agnMedicos.getValue().getGenPersonas().getNombres()));

            }
        });
        TableColumn celular = new TableColumn();
        celular.setMinWidth(150);
        celular.setText("Celular");
        celular.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnMedicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnMedicos, String> agnMedicos) {

                return new SimpleStringProperty(String.valueOf(agnMedicos.getValue().getGenPersonas().getTelefono()));

            }
        });
        TableColumn direccion = new TableColumn();
        direccion.setMinWidth(150);
        direccion.setText("Dirección");
        direccion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnMedicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnMedicos, String> agnMedicos) {

                return new SimpleStringProperty(String.valueOf(agnMedicos.getValue().getGenPersonas().getDireccion()));

            }
        });
        TableColumn email = new TableColumn();
        email.setMinWidth(150);
        email.setText("Email");
        email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnMedicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnMedicos, String> agnMedicos) {

                return new SimpleStringProperty(String.valueOf(agnMedicos.getValue().getGenPersonas().getEMail()));

            }
        });
        tv_agnmedicos.getColumns().addAll(cedula, noregistro, nombre, celular, direccion, email);
        tv_agnmedicos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_agnmedicos.setContextMenu(contextMenu);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        gp_generico.setMinWidth(970);
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
        gp_generico.addRow(0, la_search, search, bu_nuevo);

        gp_generico.add(tv_agnmedicos, 0, 1, 3, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);
        getListMedicos();
        return stack;
    }

    public static void getListMedicos() throws ParseException {
        //colocamos los datos
        l_agnmedicos = null;
        l_agnmedicos = new ArrayList<>();
        genericControllerClient.getListMedicos(search.getText());
        try {
            l_agnmedicos = SihicApp.l_medicos;

            ol_agnmedicos.clear();

            ol_agnmedicos.addAll(l_agnmedicos.toArray());
            // Ta_KardexProducto.getItems().clear();
            tv_agnmedicos.setItems(ol_agnmedicos);
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
        if ((tv_agnmedicos.getSelectionModel()) != null) {
            agnmedicos = (AgnMedicos) tv_agnmedicos.getSelectionModel().getSelectedItem();
            show();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static AgnMedicos getAgnmedicos() {
        return agnmedicos;
    }

    public static void setAgnmedicos(AgnMedicos agnmedicos) {
        AdminAgnMedicos.agnmedicos = agnmedicos;
    }

}
