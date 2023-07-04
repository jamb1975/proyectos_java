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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.AgnConsultorios;
import modelo.AgnEspecialidades;
import modelo.GenHorasCita;
import sihic.SihicApp;
import sihic.administracion.FSoluciones;
import sihic.contabilidad.ImpresionFactura;
import sihic.controlador.HclHistoriasClinicasControllerClient;

/**
 *
 * @author adminlinux
 */
public class AdminAgnConsultorios extends Application {

    private GridPane gp_generico;
    private static TableView tv_agnconsultorios;
    private static ObservableList ol_agnconsultorios = FXCollections.observableArrayList();
    ;
    private static List<AgnConsultorios> l_agnconsultorios;
    private static HclHistoriasClinicasControllerClient genericControllerClient;
    private Button bu_buscar;
    private Button bu_nuevo;
    private static AgnConsultorios agnconsultorios;
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
        stage.setTitle("Consultorio");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            agnconsultorios = null;
            agnconsultorios = new AgnConsultorios();
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

        contextMenu = new ContextMenu(itemnuevo, itemeditar);
        stack = new StackPane();
        appClass = "sihic.historiasclinicas.consultas.FAgnConsultorios";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        genericControllerClient = new HclHistoriasClinicasControllerClient();
        agnconsultorios = new AgnConsultorios();

        ImageView imageView;

        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_nuevo = new Button("", imageView);
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                agnconsultorios = null;
                agnconsultorios = new AgnConsultorios();
                show();

            } catch (Exception ex) {

            }
        });

        gp_generico = new GridPane();
        tv_agnconsultorios = new TableView();

        TableColumn numero = new TableColumn();
        numero.setMinWidth(100);
        numero.setText("Número");
        numero.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnConsultorios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnConsultorios, String> agnConsultorios) {

                return new SimpleStringProperty(String.valueOf(agnConsultorios.getValue().getNumero()));

            }
        });
        TableColumn descripcion = new TableColumn();
        descripcion.setMinWidth(300);
        descripcion.setText("Descripción");
        descripcion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnConsultorios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnConsultorios, String> agnConsultorios) {

                return new SimpleStringProperty(agnConsultorios.getValue().getDescripcion());

            }
        });

        tv_agnconsultorios.getColumns().addAll(numero, descripcion);
        tv_agnconsultorios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_agnconsultorios.setContextMenu(contextMenu);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        gp_generico.setMinSize(320, 300);
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
        gp_generico.addRow(0, bu_nuevo);

        gp_generico.add(tv_agnconsultorios, 0, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);
        getListConsultorios();
        return stack;
    }

    public static void getListConsultorios() throws ParseException {
        //colocamos los datos
        l_agnconsultorios = null;
        l_agnconsultorios = new ArrayList<>();
        genericControllerClient.getListConsultorios();
        try {
            l_agnconsultorios = SihicApp.l_consultorios;

            ol_agnconsultorios.clear();

            ol_agnconsultorios.addAll(l_agnconsultorios.toArray());
            // Ta_KardexProducto.getItems().clear();
            tv_agnconsultorios.setItems(ol_agnconsultorios);
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
        if ((tv_agnconsultorios.getSelectionModel()) != null) {
            agnconsultorios = (AgnConsultorios) tv_agnconsultorios.getSelectionModel().getSelectedItem();
            show();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static AgnConsultorios getAgnconsultorios() {
        return agnconsultorios;
    }

    public static void setAgnconsultorios(AgnConsultorios agnconsultorios) {
        AdminAgnConsultorios.agnconsultorios = agnconsultorios;
    }

}
