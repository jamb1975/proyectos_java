/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.consultas;
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
import modelo.AgnConsultoriosProcedimientos;
import sihic.administracion.FSoluciones;
import sihic.contabilidad.ImpresionFactura;
import sihic.controlador.HclHistoriasClinicasControllerClient;

/**
 *
 * @author adminlinux
 */
public class AdminAgnConsultoriosProcedimientos extends Application {

    private GridPane gp_generico;
    private static TableView tv_convenioseps;
    private static ObservableList ol_agnconsultoriosprocedimientos = FXCollections.observableArrayList();
    private static List<AgnConsultoriosProcedimientos> l_agnconsultoriosprocedimientos;
    private static HclHistoriasClinicasControllerClient genericControllerClient;
    private Button bu_buscar;
    private Button bu_nuevo;
    private Label la_search;
    private static TextField search;
    private static AgnConsultoriosProcedimientos agnconsultoriosprocedimientos;
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
        stage.setTitle("Servicios");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            agnconsultoriosprocedimientos = null;
            agnconsultoriosprocedimientos = new AgnConsultoriosProcedimientos();
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
        appClass = "sihic.historiasclinicas.consultas.FAgnConsultoriosProcedimientos";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        genericControllerClient = new HclHistoriasClinicasControllerClient();
        agnconsultoriosprocedimientos = new AgnConsultoriosProcedimientos();

        ImageView imageView;

        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_nuevo = new Button("", imageView);
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                agnconsultoriosprocedimientos = null;
                agnconsultoriosprocedimientos = new AgnConsultoriosProcedimientos();
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_convenioseps = new TableView();
        TableColumn medico = new TableColumn();
        medico.setMinWidth(250);
        medico.setText("Médico");
        medico.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnConsultoriosProcedimientos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnConsultoriosProcedimientos, String> agnConsultoriosProcedimientos) {

                return new SimpleStringProperty(agnConsultoriosProcedimientos.getValue().getAgnMedicos().getGenPersonas().getDocumento() + " " + agnConsultoriosProcedimientos.getValue().getAgnMedicos().getGenPersonas().getNombres());

            }
        });
        TableColumn especialidad = new TableColumn();
        especialidad.setMinWidth(300);
        especialidad.setText("Especialidades");
        especialidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnConsultoriosProcedimientos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnConsultoriosProcedimientos, String> agnConsultoriosProcedimientos) {

                return new SimpleStringProperty(String.valueOf(agnConsultoriosProcedimientos.getValue().getAgnMedicos().getEspecialidades()));

            }
        });

        TableColumn servicio = new TableColumn();
        servicio.setMinWidth(300);
        servicio.setText("Servicio");
        servicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnConsultoriosProcedimientos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnConsultoriosProcedimientos, String> agnConsultoriosProcedimientos) {
                try {
                    return new SimpleStringProperty(String.valueOf(agnConsultoriosProcedimientos.getValue().getServicios_id().getHclCups().getDescripcion()));
                } catch (Exception e) {
                    return new SimpleStringProperty(String.valueOf(""));

                }

            }
        });
        TableColumn consultorio = new TableColumn();
        consultorio.setMinWidth(150);
        consultorio.setText("Consultorio");
        consultorio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnConsultoriosProcedimientos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnConsultoriosProcedimientos, String> agnConsultoriosProcedimientos) {

                return new SimpleStringProperty(String.valueOf(agnConsultoriosProcedimientos.getValue().getAgnConsultorios().getNumero()) + " " + agnConsultoriosProcedimientos.getValue().getAgnConsultorios().getDescripcion());

            }
        });

        tv_convenioseps.getColumns().addAll(medico, especialidad, servicio, consultorio);
        tv_convenioseps.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_convenioseps.setContextMenu(contextMenu);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        gp_generico.setMinWidth(1010);
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

        gp_generico.add(tv_convenioseps, 0, 1, 3, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);
        getListConsultoriosProcedimientos();
        return stack;
    }

    public static void getListConsultoriosProcedimientos() throws ParseException {
        //colocamos los datos

        try {
            l_agnconsultoriosprocedimientos = genericControllerClient.getListConsultoriosProcedimientos(search.getText());

            ol_agnconsultoriosprocedimientos.clear();

            ol_agnconsultoriosprocedimientos.addAll(l_agnconsultoriosprocedimientos.toArray());
            // Ta_KardexProducto.getItems().clear();
            tv_convenioseps.setItems(ol_agnconsultoriosprocedimientos);
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
        if ((tv_convenioseps.getSelectionModel()) != null) {
            agnconsultoriosprocedimientos = (AgnConsultoriosProcedimientos) tv_convenioseps.getSelectionModel().getSelectedItem();
            show();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static AgnConsultoriosProcedimientos getAgnconsultoriosprocedimientos() {
        return agnconsultoriosprocedimientos;
    }

    public static void setAgnconsultoriosprocedimientos(AgnConsultoriosProcedimientos agnconsultoriosprocedimientos) {
        AdminAgnConsultoriosProcedimientos.agnconsultoriosprocedimientos = agnconsultoriosprocedimientos;
    }

}
