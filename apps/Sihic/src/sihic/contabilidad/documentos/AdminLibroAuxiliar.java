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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TitledPane;
import modelo.LibroAuxiliar;

import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class AdminLibroAuxiliar extends Application {

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
    private Stage stage = new Stage(StageStyle.DECORATED);
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
    private Label la_tipocomprobante;
    private static ChoiceBox tipocomprobante;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        gp_generico = new GridPane();
        tv_generic = new TableView();
        la_tipocomprobante = new Label("Clase de Comprobante:");
        tipocomprobante = new ChoiceBox();
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.APERTURA0.ordinal(), "Apertura");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.INGRESOS1.ordinal(), "Ingresos");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.EGRESOS2.ordinal(), "Egresos");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.VENTAS3.ordinal(), "Ventas");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.DEPRECIACIONES4.ordinal(), "Depreciaciones");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.AMORTIZACIONES5.ordinal(), "Amortizaciones");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.COMPRAS6.ordinal(), "Compras");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.CONSIGNACIONES7.ordinal(), "Consignaciones");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.ACTIVOSFIJOS8.ordinal(), "Activos Fijos");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.HONORARIOS9.ordinal(), "Honorarios");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.NOMINA10.ordinal(), "Nomina");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.GASTOS11.ordinal(), "Gastos");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.NOTADEB12.ordinal(), "Nota Débito");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.NOTACRED13.ordinal(), "Nota Crédito");
        tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.NOTACONT14.ordinal(), "Nota Contable");
             tipocomprobante.getItems().add(jenum.EnumTiposComprobantes.AUTORETENCIONRENTA15.ordinal(), "Autoretención Renta");
        tipocomprobante.getItems().add(16, "Todos");

        tp_generic = new TitledPane();
        tp_generic.setText("Administrar Comprobantes Diarios de Contabilidad");
        tp_generic.setCollapsible(false);
        dp_from = new DatePicker(LocalDate.now());
        dp_from.setPromptText(" Fecha Desde");
        dp_from.setOnAction(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminLibroAuxiliar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );
        dp_to = new DatePicker(LocalDate.now());
        dp_to.setPromptText(" Fecha Hasta");
        dp_to.setOnAction(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminLibroAuxiliar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        stage.setTitle("Comprobante Diario de Contabilidad");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
              //  SihicApp.conComprobanteContabilidad = null;
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
                if (checkregistroexistente()) {
                    show();
                }
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

            } catch (Exception ex) {

            }
        });
        contextMenu = new ContextMenu(itemeditar, itemdelete);
        stack = new StackPane();

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
                SihicApp.conComprobanteEgreso = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(200);
        nofactura.setText("N° Compte");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> comprobante) {
                try {
                    return new SimpleStringProperty(comprobante.getValue().getNotaContabilidad().toString());
                } catch (Exception e) {
                    return new SimpleStringProperty("000");
                }

            }
        });
        TableColumn fecha = new TableColumn();
        fecha.setMinWidth(200);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> comprobante) {

                return new SimpleStringProperty(sihic.util.UtilDate.formatodiamesyear(comprobante.getValue().getFechaelaboracion()));

            }
        });

        TableColumn observ = new TableColumn();
        observ.setMinWidth(730);
        observ.setText("Concepto");
        observ.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> comprobante) {

                return new SimpleStringProperty(comprobante.getValue().getDescripcion());

            }
        });
        TableColumn valor = new TableColumn();
        valor.setMinWidth(200);
        valor.setText("Clase de Comprobante");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> comprobante) {

                return new SimpleStringProperty(comprobante.getValue().getNotaContabilidad()!=null?"NC":"FV");

            }
        });

        tv_generic.getColumns().addAll(nofactura, fecha, observ, valor);
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
        Gp_Message.setVisible(false);
        gp_generico.getStylesheets().add(SihicApp.hojaestilos);
        gp_generico.getStyleClass().add("background");
        gp_generico.setMinWidth(1345);
        gp_generico.addRow(0, dp_from, dp_to, la_tipocomprobante, tipocomprobante);
        gp_generico.add(tv_generic, 0, 3, 5, 1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.setAlignment(Pos.TOP_CENTER);
        //tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        tipocomprobante.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        tipocomprobante.getSelectionModel().select(10);

        getRecords();
        return stack;
    }

    public static void getRecords() throws ParseException {
        //colocamos los datos
        try {

            ol_generic.clear();
        //    ol_generic.addAll(SihicApp.conComprobanteContabilidadControllerClient.getRecords(dp_from.getValue().toString(), dp_to.getValue().toString(), tipocomprobante.getSelectionModel().getSelectedIndex() == 16 ? -1 : tipocomprobante.getSelectionModel().getSelectedIndex()));
            tv_generic.setItems(ol_generic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        appClass = "sihic.contabilidad.documentos.FComprobanteContabilidad";
        clz = null;
        clz = Class.forName(appClass);
        app = null;
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

        //   stage.setMaxWidth(1000);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stage.show();
    }

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
        //    SihicApp.conComprobanteContabilidad = (ConComprobanteContabilidad) tv_generic.getSelectionModel().getSelectedItem();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {
            System.out.println("Valor pre->" + oldValue + "-" + newValue);
            getRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
