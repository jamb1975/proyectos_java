/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.citas;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.GenHorasCita;
import sihic.administracion.FSoluciones;
import sihic.contabilidad.ImpresionFactura;
import sihic.controlador.AgnCitasControllerClient;

/**
 *
 * @author adminlinux
 */
public class AdminGenHorasCita extends Application {

    private GridPane gp_generico;
    private static TableView tv_genhorascita;

    private static ObservableList ol_organizacion = FXCollections.observableArrayList();
    ;
    private static List<GenHorasCita> l_genhorascita;
    private static AgnCitasControllerClient agncitasControllerClient;
    private Button bu_buscar;
    private Button bu_nuevo;
    private static GenHorasCita genHorasCita;

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
        stage.setTitle("Hora");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            genHorasCita = null;
            genHorasCita = new GenHorasCita();
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
        appClass = "sihic.historiasclinicas.citas.FGenHorasCita";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        agncitasControllerClient = new AgnCitasControllerClient();
        genHorasCita = new GenHorasCita();
        ImageView imageView;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_nuevo = new Button("", imageView);
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                show();

            } catch (Exception ex) {

            }
        });

        gp_generico = new GridPane();
        tv_genhorascita = new TableView();

        TableColumn hora = new TableColumn();
        hora.setMinWidth(100);
        hora.setText("Hora");
        hora.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenHorasCita, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenHorasCita, String> genhorascita) {

                return new SimpleStringProperty(String.valueOf(genhorascita.getValue().getHora()));

            }
        });
        TableColumn minuto = new TableColumn();
        minuto.setMinWidth(100);
        minuto.setText("Minuto");
        minuto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenHorasCita, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenHorasCita, String> genhorascita) {

                return new SimpleStringProperty(String.valueOf(genhorascita.getValue().getMinutos()));

            }
        });
        TableColumn husos = new TableColumn();
        husos.setMinWidth(200);
        husos.setText("Husos Horario");
        husos.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenHorasCita, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenHorasCita, String> genhorascita) {

                return new SimpleStringProperty(genhorascita.getValue().getZona() == 0 ? "AM" : "PM");

            }
        });

        tv_genhorascita.getColumns().addAll(hora, minuto, husos);
        tv_genhorascita.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_genhorascita.setContextMenu(contextMenu);

        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        gp_generico.setMinSize(420, 300);
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

        gp_generico.add(tv_genhorascita, 0, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);
        getListHoras();
        return stack;
    }

    public static void getListHoras() throws ParseException {
        //colocamos los datos

        try {
            l_genhorascita = agncitasControllerClient.getHorasCita();

            ol_organizacion.clear();

            ol_organizacion.addAll(l_genhorascita.toArray());
            // Ta_KardexProducto.getItems().clear();
            tv_genhorascita.setItems(ol_organizacion);
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
        if ((tv_genhorascita.getSelectionModel()) != null) {
            genHorasCita = (GenHorasCita) tv_genhorascita.getSelectionModel().getSelectedItem();
            show();
        }
    }

    public static GenHorasCita getGenHorasCita() {
        return genHorasCita;
    }

    public static void setGenHorasCita(GenHorasCita genHorasCita) {
        AdminGenHorasCita.genHorasCita = genHorasCita;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
