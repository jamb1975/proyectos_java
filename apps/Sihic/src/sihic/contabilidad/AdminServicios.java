/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

import sihic.SihicApp;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TitledPane;
import modelo.Producto;

/**
 *
 * @author adminlinux
 */
public class AdminServicios extends Application {

    private GridPane gp_generico;
    private static TableView tv_Productos = new TableView();
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_Productos = FXCollections.observableArrayList();
    ;
    private Button bu_buscar;
    private Button bu_nuevo;
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
    private TitledPane tp_generic;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        tp_generic = new TitledPane();
        tp_generic.setText("Administrar Servicios");
        tp_generic.setCollapsible(false);
        stage.setTitle("Servicios");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                SihicApp.s_producto = null;
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
                delete();
            } catch (ParseException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(AdminMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemdelete);
        stack = new StackPane();
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(100);
        buscar = new TextField();
        buscar.setMinWidth(600);
        buscar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminServicios.class.getName()).log(Level.SEVERE, null, ex);
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
                SihicApp.s_producto = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_Productos = new TableView();

        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(100);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {

                return new SimpleStringProperty(Producto.getValue().getCodigo());

            }
        });
        TableColumn codigobarras = new TableColumn();
        codigobarras.setMinWidth(200);
        codigobarras.setText("Código");
        codigobarras.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {

                return new SimpleStringProperty(Producto.getValue().getCodigo());

            }
        });

        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(500);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {

                return new SimpleStringProperty(Producto.getValue().getNombre());

            }
        });
        TableColumn precio = new TableColumn();
        precio.setMinWidth(200);
        precio.setText("Precio 2015");
        precio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {

                return new SimpleStringProperty(Producto.getValue().getPrecio2015().toString());

            }
        });
        TableColumn precio2016 = new TableColumn();
        precio2016.setMinWidth(200);
        precio2016.setText("Precio 2016");
        precio2016.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {

                return new SimpleStringProperty(Producto.getValue().getPrecio2016().toString());

            }
        });
        TableColumn precio2017 = new TableColumn();
        precio2017.setMinWidth(200);
        precio2017.setText("Precio 2017");
        precio2017.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {

                return new SimpleStringProperty(Producto.getValue().getPrecio2017().toString());

            }
        });
        TableColumn precio2018 = new TableColumn();
        precio2018.setMinWidth(200);
        precio2018.setText("Precio 2018");
        precio2018.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
                try {
                    return new SimpleStringProperty(Producto.getValue().getPrecio2018().toString());
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }

            }
        });
        TableColumn precio2020 = new TableColumn();
        precio2020.setMinWidth(200);
        precio2020.setText("Precio 2020");
        precio2020.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
                try {
                    return new SimpleStringProperty(Producto.getValue().getPrecio2020().toString());
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }

            }
        });
         TableColumn precio2001_soat = new TableColumn();
        precio2001_soat.setMinWidth(200);
        precio2001_soat.setText("Precio Soat 2001");
        precio2001_soat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
                try {
                    return new SimpleStringProperty(Producto.getValue().getPrecio2001_soat().toString());
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }

            }
        });
         TableColumn precio2015_soat = new TableColumn();
        precio2015_soat.setMinWidth(200);
        precio2015_soat.setText("Precio Soat 2015");
        precio2015_soat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
                try {
                    return new SimpleStringProperty(Producto.getValue().getPrecio2015_soat().toString());
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }

            }
        });
         TableColumn precio2016soat= new TableColumn();
        precio2016soat.setMinWidth(200);
        precio2016soat.setText("Precio Soat 2016");
        precio2016soat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
                try {
                    return new SimpleStringProperty(Producto.getValue().getPrecio2016_soat().toString());
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }

            }
        });
         TableColumn precio2017soat = new TableColumn();
        precio2017soat.setMinWidth(200);
        precio2017soat.setText("Precio Soat 2017");
        precio2017soat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
                try {
                    return new SimpleStringProperty(Producto.getValue().getPrecio2017_soat().toString());
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }

            }
        });
         TableColumn precio2018soat = new TableColumn();
        precio2018soat.setMinWidth(200);
        precio2018soat.setText("Precio Soat 2018");
        precio2018soat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
                try {
                    return new SimpleStringProperty(Producto.getValue().getPrecio2018_soat().toString());
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }

            }
        });
         TableColumn precio2020soat = new TableColumn();
        precio2018soat.setMinWidth(200);
        precio2018soat.setText("Precio Soat 2020");
        precio2018soat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
                try {
                    return new SimpleStringProperty(Producto.getValue().getPrecio2020_soat().toString());
                } catch (Exception e) {
                    return new SimpleStringProperty("0");
                }

            }
        });
        tv_Productos.getColumns().addAll(codigobarras, nombre, precio, precio2016, precio2017, precio2018,precio2020,precio2001_soat,precio2015_soat,precio2016soat,precio2017soat,precio2018soat,precio2020soat);
        //ta_librodiario.setMinWidth(650);

        tv_Productos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_Productos.setMinHeight(577);
        tv_Productos.setContextMenu(contextMenu);
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
        gp_generico.setMinSize(1340, 640);
        gp_generico.getStylesheets().add(SihicApp.hojaestilos);
        gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0, la_buscar, buscar, bu_nuevo);

        gp_generico.add(tv_Productos, 0, 3, 3, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        getRecords();
        return stack;
    }

    public static void getRecords() throws ParseException {
        //colocamos los datos
        try {
            if (buscar == null) {
                buscar = new TextField();
            }
            ol_Productos.clear();
            ol_Productos.addAll(SihicApp.productoControllerClient.findserviciosall(buscar.getText()).toArray());
            tv_Productos.setItems(ol_Productos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        appClass = "sihic.contabilidad.FServicios";
        clz = null;
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

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        if ((tv_Productos.getSelectionModel()) != null) {
            SihicApp.s_producto = (Producto) tv_Productos.getSelectionModel().getSelectedItem();

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void delete() throws ParseException {

        getRecords();
    }

}
