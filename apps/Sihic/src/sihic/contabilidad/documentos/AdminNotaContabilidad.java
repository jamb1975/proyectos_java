/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad.documentos;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TitledPane;
import javafx.stage.Modality;
import modelo.NotaContabilidad;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class AdminNotaContabilidad extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_generic = FXCollections.observableArrayList();
    ;
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
    private Stage stage;
    private static DatePicker dp_from;
    private static DatePicker dp_to;
    Scene scene = null;
    private ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemdelete;
    private ImageView img;
    private TitledPane tp_generic;
    private Button bu_nuevo;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        
        tp_generic = new TitledPane();
        tp_generic.setText("Administrar Notas Contables");
        tp_generic.setCollapsible(false);
        dp_from = new DatePicker(LocalDate.now());
        dp_from.setPromptText(" Fecha Desde");
        dp_to = new DatePicker(LocalDate.now());
        dp_to.setPromptText(" Fecha Hasta");
        
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                SihicApp.notaContabilidad = null;
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
                show();
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

            } catch (Exception ex) {
                  ex.printStackTrace();
            }
        });
        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemdelete);
        stack = new StackPane();
        appClass = "sihic.contabilidad.documentos.FNotaContabilidad";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(100);
        buscar = new TextField();
        buscar.setOnKeyReleased(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {

            }
        });

        buscar.setMinWidth(700);

        ImageView imageView;

        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_nuevo = new Button("", imageView);
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                SihicApp.notaContabilidad = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_generic = new TableView();

        TableColumn norecibo = new TableColumn();
        norecibo.setMinWidth(150);
        norecibo.setText("N° Nota Contabilidad");
        norecibo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NotaContabilidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NotaContabilidad, String> notacontabilidad) {

                return new SimpleStringProperty(notacontabilidad.getValue().getNocomprobantecerosizquierda().toString());

            }
        });

        TableColumn fecha = new TableColumn();
        fecha.setMinWidth(150);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NotaContabilidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NotaContabilidad, String> notacontabilidad) {

                return new SimpleStringProperty(sihic.util.UtilDate.formatodiamesyear(notacontabilidad.getValue().getFechaelaboracion()));

            }
        });

        TableColumn concepto = new TableColumn();
        concepto.setMinWidth(1030);
        concepto.setText("Concepto");
        concepto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NotaContabilidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NotaContabilidad, String> notaContabilidad) {

                return new SimpleStringProperty(notaContabilidad.getValue().getConcepto());

            }
        });

        tv_generic.getColumns().addAll(norecibo, fecha, concepto);
        //ta_librodiario.setMinWidth(650);

        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setMinHeight(577);
        tv_generic.setContextMenu(contextMenu);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinWidth(gp_generico.getLayoutBounds().getWidth());
        Gp_Message.setMaxHeight(40);
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        dp_from.setOnAction(e->{
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminNotaContabilidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        dp_to.setOnAction(e->{
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminNotaContabilidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Gp_Message.setVisible(false);
        gp_generico.getStylesheets().add(SihicApp.hojaestilos);
        gp_generico.getStyleClass().add("background");
        gp_generico.setMinWidth(960);
        gp_generico.addRow(0, dp_from, dp_to, bu_nuevo);
        gp_generico.add(tv_generic, 0, 3, 3, 1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        tp_generic.setMinSize(1345, 650);
        stack.setAlignment(Pos.TOP_CENTER);
        //tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        getRecords();
        return stack;
    }

    public static void getRecords() throws ParseException {
        //colocamos los datos
        try {
            SihicApp.notaContabilidadControllerClient.getrecorsd(dp_from.getValue().toString(), dp_to.getValue().toString(), buscar.getText());
            ol_generic.clear();
            ol_generic.addAll(SihicApp.li_notacontabilidad.toArray());
            tv_generic.setItems(ol_generic);
        } catch (Exception e) {
            
        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
       
        stage=null;
        stage=new Stage(StageStyle.DECORATED);
        stage.setTitle("Nota Contable");
        
        stage.setTitle("Nota Contable");parent = null;
        parent = (Parent) clz.getMethod("createContent").invoke(app);
        scene = null;
        scene = new Scene(parent, Color.TRANSPARENT);
        stage.setOnCloseRequest(    e->{
            if(SihicApp.notaContabilidad.verificarigualdadendebitoycredito())
            {
                
            }
            else
            {
             Alert alert=new Alert(Alert.AlertType.ERROR, "Los totales Debe y Haber deben ser iguales");
             alert.show();
             e.consume();
            }
                
        });
      
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
       
         stage.initModality(Modality.APPLICATION_MODAL);
       //  stage.initOwner(this.stage);

       // stage.setAlwaysOnTop(true);
		stage.showAndWait();
    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            SihicApp.notaContabilidad = (NotaContabilidad) tv_generic.getSelectionModel().getSelectedItem();

        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
