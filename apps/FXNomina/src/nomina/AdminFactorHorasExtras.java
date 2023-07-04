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
import modelo.FactorHorasExtras;
import fxnomina.FXNomina;


/**
 *
 * @author adminlinux
 */
public class AdminFactorHorasExtras extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_generic = FXCollections.observableArrayList();
    ;
    private static List<FactorHorasExtras> l_factorhorasextras;
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
        tp_generic.setText("Administración Factor Horas Extras");
        tp_generic.setCollapsible(false);
        stage.setTitle("FACTOR DE HORA EXTRA");
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
        appClass = "nomina.FFactorHorasExtras";
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

        TableColumn fdf = new TableColumn();
        fdf.setMinWidth(250);
        fdf.setText("Factor Dominical y Festivo");
        fdf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FactorHorasExtras, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FactorHorasExtras, String> fhe) {

                return new SimpleStringProperty(fhe.getValue().getFactor_dominicalyfestivo().toString());

            }
        });
        TableColumn fdfn = new TableColumn();
        fdfn.setMinWidth(250);
        fdfn.setText("Factor Dominical y Festivo Noc");
        fdfn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FactorHorasExtras, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FactorHorasExtras, String> fhe) {

                return new SimpleStringProperty(fhe.getValue().getFactor_dominicalyfestivonocturno().toString());

            }
        });
        TableColumn edf = new TableColumn();
        edf.setMinWidth(250);
        edf.setText("Factor Extra Diurno Festivo");
        edf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FactorHorasExtras, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FactorHorasExtras, String> fhe) {

                return new SimpleStringProperty(fhe.getValue().getFactor_extradiurnofestivo().toString());

            }
        });
        TableColumn edo = new TableColumn();
        edo.setMinWidth(250);
        edo.setText("Factor Extra Diurno Ordinario");
        edo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FactorHorasExtras, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FactorHorasExtras, String> fhe) {

                return new SimpleStringProperty(fhe.getValue().getFactor_extradiurnoordinario().toString());

            }
        });
        TableColumn enf = new TableColumn();
        enf.setMinWidth(250);
        enf.setText("Factor Extra Nocturno Festivo");
        enf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FactorHorasExtras, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FactorHorasExtras, String> fhe) {

                return new SimpleStringProperty(fhe.getValue().getFactor_extranocturnofestivo().toString());

            }
        });
        TableColumn eno = new TableColumn();
        eno.setMinWidth(250);
        eno.setText("Factor Extra Nocturno Ordinario");
        eno.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FactorHorasExtras, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FactorHorasExtras, String> fhe) {

                return new SimpleStringProperty(fhe.getValue().getFactor_extranocturnoordinario().toString());

            }
        });
        TableColumn rno = new TableColumn();
        rno.setMinWidth(250);
        rno.setText("Factor Recargo Nocturno");
        rno.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FactorHorasExtras, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FactorHorasExtras, String> fhe) {

                return new SimpleStringProperty(fhe.getValue().getFactor_recargonocturno().toString());

            }
        });

        Gp_Message = new GridPane();

        tv_generic.getColumns().addAll(fdf, fdfn, edf, edo, enf, eno, rno);
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
        
        tv_generic.setMinHeight(570);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setContextMenu(contextMenu);

        getRecords();

        return stack;
    }

    public static void getRecords() throws ParseException {
        try {
            FXNomina.factorHorasExtrasControllerClient.getRecords();
            ol_generic.clear();
            ol_generic.addAll(FXNomina.li_facFactorHorasExtras);
            tv_generic.setItems(ol_generic);
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
            FXNomina.factorHorasExtras= (FactorHorasExtras) tv_generic.getSelectionModel().getSelectedItem();
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
