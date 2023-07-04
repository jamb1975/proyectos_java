package nomina;

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
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.CargosEntidad;
import fxnomina.FXNomina;

/**
 *
 * @author adminlinux
 */
public class AdminCargosEntidad extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_cargosentidad = FXCollections.observableArrayList();
    ;
    private static List<CargosEntidad> l_cargosentidad;
    private Button bu_buscar;
    private Button bu_nuevo;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
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
        tp_generic.setText("Cargos Entidad");
        tp_generic.setCollapsible(false);
        stage.setTitle("Pacientes");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                FXNomina.cargosEntidad = null;
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
        appClass = "nomina.FCargosEntidad";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(120);
        buscar = new TextField();
        buscar.setMinWidth(250);
        buscar.textProperty().addListener((observable, oldValue, newValue) -> {

            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminCargosEntidad.class.getName()).log(Level.SEVERE, null, ex);
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
                FXNomina.cargosEntidad = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_generic = new TableView();

        TableColumn cargo = new TableColumn();
        cargo.setMinWidth(150);
        cargo.setText("Cargo");
        cargo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CargosEntidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CargosEntidad, String> cargosentidad) {

                return new SimpleStringProperty(cargosentidad.getValue().getNombre());

            }
        });
        TableColumn nivelcargo = new TableColumn();
        nivelcargo.setMinWidth(150);
        nivelcargo.setText("Nivel de Cargo");
        nivelcargo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CargosEntidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CargosEntidad, String> cargosentidad) {

                return new SimpleStringProperty(cargosentidad.getValue().getNivelcargo() == jenum.EnumNivelCargo.AUXILIAR0.ordinal() ? "Auxiliar" : cargosentidad.getValue().getNivelcargo() == jenum.EnumNivelCargo.TECNICO1.ordinal() ? "Técnico" : cargosentidad.getValue().getNivelcargo() == jenum.EnumNivelCargo.TECNOLOGO2.ordinal() ? "Técnologo" : cargosentidad.getValue().getNivelcargo() == jenum.EnumNivelCargo.PROFESIONAL3.ordinal() ? "Profesional" : "Profesional Especializado");

            }

        });
        TableColumn funciones = new TableColumn();
        funciones.setMinWidth(850);
        funciones.setText("Funciones");
        funciones.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CargosEntidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CargosEntidad, String> cargosentidad) {

                return new SimpleStringProperty(cargosentidad.getValue().getFunciones());

            }
        });
        TableColumn sueldo = new TableColumn();
        sueldo.setMinWidth(180);
        sueldo.setText("Sueldo");
        sueldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CargosEntidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CargosEntidad, String> cargosentidad) {

                return new SimpleStringProperty(cargosentidad.getValue().getSueldo().toString());

            }

        });

        Gp_Message = new GridPane();

        Gp_Message.setMaxSize(40, gp_generico.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(FXNomina.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        gp_generico.getStylesheets().add(FXNomina.hojaestilos);
        gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0, la_buscar, buscar, bu_nuevo);

        gp_generico.add(tv_generic, 0, 3, 7, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        gp_generico.setMinSize(1320, 630);
        gp_generico.setMaxSize(1320, 630);
        tv_generic.getColumns().addAll(cargo, nivelcargo, funciones, sueldo);
        tv_generic.setMinHeight(570);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setContextMenu(contextMenu);

        getRecords();

        return stack;
    }

    public static void getRecords() throws ParseException {
        try {
            l_cargosentidad = FXNomina.cargosEntidadControllerClient.getRecords();
            ol_cargosentidad.clear();
            ol_cargosentidad.addAll(l_cargosentidad.toArray());
            tv_generic.setItems(ol_cargosentidad);
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
        if ((tv_generic.getSelectionModel()) != null) {
            FXNomina.cargosEntidad = (CargosEntidad) tv_generic.getSelectionModel().getSelectedItem();
            show();
        }
    }

    private void eliminar() throws ParseException {
        //genPersonasControllerClient.delete(genPacientes);
        //getListGenPersonas();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
