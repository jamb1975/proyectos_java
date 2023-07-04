/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina;

/**
 *
 * @author adminlinux
 */
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
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
import modelo.Nomina;
import fxnomina.FXNomina;


/**
 *
 * @author adminlinux
 */
public class AdminNomina extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_nomina = FXCollections.observableArrayList();
    private static List<Nomina> l_nomina;
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
    private static DatePicker datefrom;
    private static DatePicker dateto;
    private TitledPane tp_generic;
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        tp_generic = new TitledPane();
        tp_generic.setText("Administración Nómina");
        tp_generic.setCollapsible(false);
        datefrom=new DatePicker(util.UtilDate.primerdiaprimermes(new Date()));
        dateto=new DatePicker(util.UtilDate.ultimodiaultimomes(new Date()));
        stage.setTitle("Nomina Empleados");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                FXNomina.empleados = null;
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
               if(checkregistroexistente())
               {
                   show();
               }
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
              if( checkregistroexistente())
                eliminar();
            } catch (Exception x) {

            }
        });

        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemdelete);
        stack = new StackPane();
       
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(120);
        buscar = new TextField();
        buscar.setMinWidth(250);
        buscar.setOnKeyReleased(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {
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
                FXNomina.empleados = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_generic = new TableView();
        
        TableColumn nomomina = new TableColumn();
        nomomina.setMinWidth(200);
        nomomina.setText("N° Nomina");
        nomomina.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Nomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Nomina, String> nomina) {

                return new SimpleStringProperty(nomina.getValue().getNocomprobantecerosizquierda());

            }
        });
        TableColumn fechainicio = new TableColumn();
        fechainicio.setMinWidth(200);
        fechainicio.setText("Periodo Inicio");
        fechainicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Nomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Nomina, String> nomina) {

                return new SimpleStringProperty(util.UtilDate.s_formatoyearmesdia(nomina.getValue().getPeriodoinicio()));

            }
        });
        TableColumn fechafin = new TableColumn();
        fechafin.setMinWidth(200);
        fechafin.setText("Periodo Fin");
        fechafin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Nomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Nomina, String> nomina) {

                return new SimpleStringProperty(util.UtilDate.s_formatoyearmesdia(nomina.getValue().getPeriodofin()));

            }
        });
        TableColumn totalbasico = new TableColumn();
        totalbasico.setMinWidth(200);
        totalbasico.setText("Total Básico");
        totalbasico.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Nomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Nomina, String> nomina) {

                return new SimpleStringProperty(nomina.getValue().getTotalbasico().toString());

            }
        });
         TableColumn totaldevengado = new TableColumn();
         totaldevengado.setMinWidth(200);
         totaldevengado.setText("Total Devengado");
         totaldevengado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Nomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Nomina, String> nomina) {

                return new SimpleStringProperty(nomina.getValue().getTotaldevengado().toString());

            }
        });

        TableColumn observaciones = new TableColumn();
         observaciones.setMinWidth(330);
         observaciones.setText("Observaciones");
         observaciones.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Nomina, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Nomina, String> nomina) {

                return new SimpleStringProperty(nomina.getValue().getObservaciones());

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
        gp_generico.addRow(0, la_buscar,buscar,datefrom,dateto, bu_nuevo);

        gp_generico.add(tv_generic, 0, 3, 7, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        gp_generico.setMinSize(1340, 630);
        gp_generico.setMaxSize(1340, 630);
        tv_generic.getColumns().addAll(nomomina, fechainicio, fechafin, totalbasico, totaldevengado,observaciones);
        tv_generic.setMinHeight(570);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setContextMenu(contextMenu);

        getRecords();

        return stack;
    }

    public static void getRecords() throws ParseException {
        try {
            FXNomina.nominaControllerClient.getRecords(datefrom.getValue().toString(), dateto.getValue().toString());
            ol_nomina.clear();
            ol_nomina.addAll(FXNomina.li_nomina.toArray());
            tv_generic.setItems(ol_nomina);
        } catch (Exception e) {

        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        appClass = "nomina.FNomina";
        clz=null;
        clz = Class.forName(appClass);
        app = clz.newInstance();
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

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            FXNomina.nomina = (Nomina) tv_generic.getSelectionModel().getSelectedItem();
            return true;
        }
        else
        {
            return false;
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
