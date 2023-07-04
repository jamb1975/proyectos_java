package sihic.general;

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
import modelo.GenPacientes;
import modelo.GenPersonas;
import sihic.SihicApp;
import sihic.contabilidad.ImpresionFactura;
import sihic.controlador.GenPersonasControllerClient;

/**
 *
 * @author adminlinux
 */
public class AdminGenPersonas extends Application {

    private GridPane gp_generico;
    private static TableView tv_genpacientes;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_genpacientes = FXCollections.observableArrayList();
    ;
    private static List<GenPacientes> l_genpacientes;
    private static GenPersonasControllerClient genPersonasControllerClient;
    private Button bu_buscar;
    private Button bu_nuevo;
    private static GenPacientes genPacientes;
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
        stage.setTitle("Pacientes");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                genPacientes = null;
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
        appClass = "sihic.general.FGenPersonas";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        genPersonasControllerClient = new GenPersonasControllerClient();
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(120);
        buscar = new TextField();
        buscar.setMinWidth(250);
        buscar.setOnKeyReleased(e -> {
            try {
                getListGenPersonas();
            } catch (ParseException ex) {
                Logger.getLogger(AdminGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
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
                getListGenPersonas();

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
                genPacientes = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_genpacientes = new TableView();

        TableColumn nit = new TableColumn();
        nit.setMinWidth(150);
        nit.setText("Nit o CC");
        nit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenPacientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenPacientes, String> genPacientes) {

                return new SimpleStringProperty(genPacientes.getValue().getGenPersonas().getDocumento());

            }
        });
        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(350);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenPacientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenPacientes, String> genPacientes) {

                return new SimpleStringProperty(genPacientes.getValue().getGenPersonas().getNombres());

            }
        });

        TableColumn telefono = new TableColumn();
        telefono.setMinWidth(200);
        telefono.setText("Telefono");
        telefono.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenPacientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenPacientes, String> genPacientes) {

                return new SimpleStringProperty(genPacientes.getValue().getGenPersonas().getTelefono());

            }
        });
        TableColumn direccion = new TableColumn();
        direccion.setMinWidth(200);
        direccion.setText("Dirección");
        direccion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenPacientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenPacientes, String> genPacientes) {

                return new SimpleStringProperty(genPacientes.getValue().getGenPersonas().getDireccion());

            }
        });
        TableColumn eps = new TableColumn();
        eps.setMinWidth(200);
        eps.setText("Eps");
        eps.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenPacientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenPacientes, String> genPacientes) {

                return new SimpleStringProperty(genPacientes.getValue().getGenEapb() != null ? genPacientes.getValue().getGenEapb().getNombre() : "No tiene");

            }
        });
        TableColumn tipousuario = new TableColumn();
        tipousuario.setMinWidth(200);
        tipousuario.setText("Tipos Usuarios");
        tipousuario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenPacientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenPacientes, String> genPacientes) {

                return new SimpleStringProperty(genPacientes.getValue().getGenTiposUsuarios().getNombre());

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

        gp_generico.add(tv_genpacientes, 0, 3, 7, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);
        gp_generico.setMinSize(1320, 630);
        gp_generico.setMaxSize(1320, 630);
        tv_genpacientes.getColumns().addAll(nit, nombre, telefono, direccion, eps, tipousuario);
        tv_genpacientes.setMinHeight(570);
        tv_genpacientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_genpacientes.setContextMenu(contextMenu);

        getListGenPersonas();

        return stack;
    }

    public static void getListGenPersonas() throws ParseException {
        try {
            l_genpacientes = genPersonasControllerClient.getGenpacientes(buscar.getText().trim());
            ol_genpacientes.clear();
            ol_genpacientes.addAll(l_genpacientes.toArray());
            tv_genpacientes.setItems(ol_genpacientes);
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
        if ((tv_genpacientes.getSelectionModel()) != null) {
            genPacientes = (GenPacientes) tv_genpacientes.getSelectionModel().getSelectedItem();
            show();
        }
    }

    private void eliminar() throws ParseException {
        //genPersonasControllerClient.delete(genPacientes);
        //getListGenPersonas();
    }

    public static GenPacientes getGenPacientes() {
        return genPacientes;
    }

    public static void setGenPacientes() {
        genPacientes = null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
