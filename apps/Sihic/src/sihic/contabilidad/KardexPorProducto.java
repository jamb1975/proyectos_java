/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

import sihic.SihicApp;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modelo.Kardex;
import sihic.controlador.KardexControllerClient;

/**
 *
 * @author SIMboxDEV8
 */


public class KardexPorProducto extends Application {

    private TableView Ta_KardexProducto;
    private ObservableList Ol_KardexProducto;
    private GridPane gridpaneDatos;
    private GridPane gridpaneRoot;
    private GridPane gridpaneFechas;
    private GridPane gridpane;
    private DatePicker Dp_Date_from;
    private DatePicker Dp_Date_to;
    private KardexControllerClient kardexcontrollerClient;
    private List<Kardex> ar_KerdexProducto;
    private Label cant;
    private Label valor;
    private Text T_Datos_Total_Ventas[];
    private Label L_Producto;
    private TextField Tf_Producto;
    private Text T_Totales;
    private Text T_Valor_total_todo;
    private Text T_Cant_total_todo;
    private Label L_Date_from;
    private Label L_Date_to;
    private TextField Tf_CantEntrada;
    private TextField Tf_CantSalida;
    private TextField Tf_ValorEntrada;
    private TextField Tf_ValorSalida;
    private TextField Tf_CantSaldo;
    private TextField Tf_ValorSaldo;
    private Button B_Consultar;
    private Label L_Nombre;
    private Label L_Codigo;
    private Label L_Metodo;

    private FindInsumosMedicamentos findProduct;
    //graficos barras

    public Parent createContent() throws ParseException {
        Tf_CantEntrada = new TextField();
        Tf_CantEntrada.setMinWidth(90);
        Tf_CantEntrada.setMaxWidth(90);
        Tf_CantSalida = new TextField();
        Tf_CantSalida.setMinWidth(90);
        Tf_CantSalida.setMaxWidth(90);
        Tf_ValorEntrada = new TextField();
        Tf_ValorEntrada.setMinWidth(90);
        Tf_ValorEntrada.setMaxWidth(90);
        Tf_ValorSalida = new TextField();
        Tf_ValorSalida.setMinWidth(90);
        Tf_ValorSalida.setMaxWidth(90);
        Tf_CantSaldo = new TextField();
        Tf_CantSaldo.setMinWidth(90);
        Tf_CantSaldo.setMaxWidth(90);
        Tf_ValorSaldo = new TextField();
        Tf_ValorSaldo.setMinWidth(90);
        Tf_ValorSaldo.setMaxWidth(90);
        L_Nombre = new Label();
        L_Codigo = new Label();
        L_Metodo = new Label();
        L_Producto = new Label("Producto: ");

        Tf_Producto = new TextField();
        T_Totales = new Text("");
        T_Totales.setFill(Color.BLACK);
        T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD, 15));
        Dp_Date_from = new DatePicker();
        Dp_Date_to = new DatePicker();
        L_Date_from = new Label("Desde: ");
        L_Date_to = new Label("A: ");
        Ol_KardexProducto = FXCollections.observableArrayList();
        ar_KerdexProducto = null;
        kardexcontrollerClient = null;
        kardexcontrollerClient = new KardexControllerClient();
        Dp_Date_from.setValue(LocalDate.now());
        Dp_Date_to.setValue(LocalDate.now());
        findProduct = new FindInsumosMedicamentos(false);
        Thread th = new Thread(task);
        th.setDaemon(false);
        th.start();
        //Creamos la tabla

        B_Consultar = new Button("Consultar");
        B_Consultar.setOnMouseClicked((MouseEvent ke) -> {
            try {
                load_kardex_product();
            } catch (ParseException ex) {
                Logger.getLogger(KardexPorProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        B_Consultar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    load_kardex_product();
                } catch (ParseException ex) {
                    Logger.getLogger(KardexPorProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Tf_Producto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                findProduct.show();
                findProduct.getTf_Search().requestFocus();

            }
        });
        findProduct.getStage().setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    if (findProduct.getKardex().getProducto() != null) {
                        Tf_Producto.setText(findProduct.getKardex().getProducto().getNombre());

                    }
                }
            }
        });
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Ta_KardexProducto = new TableView();
        TableColumn Fecha = new TableColumn();
        Fecha.setMinWidth(70);
        Fecha.setText("Fecha");
        Fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {

                return new SimpleStringProperty(DateFormat.getDateInstance().format(kardex.getValue().getFecha()));

            }
        });

        TableColumn Descripcion = new TableColumn();

        Descripcion.setText("Descripción");
        Descripcion.setCellValueFactory(new PropertyValueFactory("desc_kar"));
        Descripcion.setMinWidth(250);
        TableColumn CantidadEntrada = new TableColumn();

        CantidadEntrada.setText("Cant. Entrada");
        CantidadEntrada.setMinWidth(120);
        CantidadEntrada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getTipo().equals("dc") ? "(" + String.valueOf(kardex.getValue().getCantidad_entrada()) + ")" : String.valueOf(kardex.getValue().getCantidad_entrada()));
            }
        });

        TableColumn ValorEntrada = new TableColumn();

        ValorEntrada.setText("Valor Entrada");
        ValorEntrada.setMinWidth(120);
        ValorEntrada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getTipo().equals("dc") ? "(" + String.valueOf(kardex.getValue().getValor_entrada()) + ")" : String.valueOf(kardex.getValue().getValor_entrada()));
            }
        });

        TableColumn CantidadSalida = new TableColumn();

        CantidadSalida.setText("Cant. Salida");
        CantidadSalida.setMinWidth(100);
        CantidadSalida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getTipo().equals("dv") ? "(" + String.valueOf(kardex.getValue().getCantidad_salida()) + ")" : String.valueOf(kardex.getValue().getCantidad_salida()));
            }
        });

        TableColumn ValorSalida = new TableColumn();

        ValorSalida.setText("Valor Salida");
        ValorSalida.setMinWidth(100);
        ValorSalida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getTipo().equals("dv") ? "(" + String.valueOf(kardex.getValue().getValor_salida()) + ")" : String.valueOf(kardex.getValue().getValor_salida()));
            }
        });
        TableColumn Cantidad_saldo = new TableColumn();

        Cantidad_saldo.setText("Cant. Saldo");
        Cantidad_saldo.setMinWidth(100);
        Cantidad_saldo.setCellValueFactory(new PropertyValueFactory("cantidad_saldo"));
        TableColumn Valor_saldo = new TableColumn();

        Valor_saldo.setText("Valor. Saldo");
        Valor_saldo.setMinWidth(100);
        Valor_saldo.setCellValueFactory(new PropertyValueFactory("valor_saldo"));

        TableColumn ValorUnidad = new TableColumn();

        ValorUnidad.setText("Valor/U");

        ValorUnidad.setCellValueFactory(new PropertyValueFactory("valor_unidad"));

        ValorUnidad.setMinWidth(100);
        Ta_KardexProducto.setMinHeight(500);
        Ta_KardexProducto.autosize();
        Ta_KardexProducto.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Ta_KardexProducto.getColumns().addAll(Fecha, Descripcion, CantidadEntrada, ValorEntrada, CantidadSalida, ValorSalida, Cantidad_saldo, Valor_saldo, ValorUnidad);

        gridpaneDatos = new GridPane();
        gridpaneRoot = new GridPane();
        gridpaneFechas = new GridPane();
        gridpane = new GridPane();
        gridpane.getStyleClass().add("category-page");

        gridpaneFechas.add(L_Date_from, 0, 0);
        gridpaneFechas.add(Dp_Date_from, 1, 0);
        gridpaneFechas.add(L_Date_to, 2, 0);
        gridpaneFechas.add(Dp_Date_to, 3, 0);
        gridpaneFechas.add(L_Producto, 4, 0);
        gridpaneFechas.add(Tf_Producto, 5, 0);
        gridpaneFechas.add(B_Consultar, 6, 0);
        gridpaneFechas.setMinWidth(430);
        gridpaneFechas.setHgap(5);

        gridpane.setVgap(5);
        gridpane.alignmentProperty().setValue(Pos.TOP_CENTER);
        GridPane.setValignment(gridpaneRoot, VPos.TOP);

        GridPane.setValignment(T_Totales, VPos.TOP);

        gridpane.add(gridpaneFechas, 0, 0);
        gridpane.add(Ta_KardexProducto, 0, 1, 7, 1);
        gridpaneDatos.add(T_Totales, 0, 0);
        gridpaneDatos.add(Tf_CantEntrada, 14, 0);
        gridpaneDatos.add(Tf_ValorEntrada, 15, 0);
        gridpaneDatos.add(Tf_CantSalida, 16, 0);
        gridpaneDatos.add(Tf_ValorSalida, 17, 0);
        gridpaneDatos.add(Tf_CantSaldo, 18, 0);
        gridpaneDatos.add(Tf_ValorSaldo, 19, 0);
        gridpane.add(gridpaneDatos, 0, 2);
        gridpaneDatos.setHgap(5);
        gridpane.autosize();

        GridPane.setColumnSpan(gridpaneFechas, 5);
        GridPane.setHalignment(gridpaneFechas, HPos.CENTER);
        GridPane.setHalignment(gridpaneDatos, HPos.CENTER);
        gridpaneFechas.setAlignment(Pos.CENTER);
        gridpaneDatos.setAlignment(Pos.CENTER);
        gridpane.getStylesheets().add(SihicApp.hojaestilos);
        gridpane.getStyleClass().add("background");

        load_kardex_product();
        return gridpane;
    }

    public static Node createIconContent() throws IOException {
        ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
        imageView.setFitHeight(80);
        imageView.setFitWidth(90);
        javafx.scene.Group g
                = new javafx.scene.Group(imageView);

        return g;
    }

    private void load_kardex_product() throws ParseException {
        //colocamos los datos

        BigDecimal valor_entrada = BigDecimal.ZERO;
        float cant_entrada = 0;
        BigDecimal valor_salida = BigDecimal.ZERO;
        float cant_salida = 0;
        Long id;
        if (findProduct.getKardex().getId() != null) {
            id = findProduct.getKardex().getProducto().getId();
        } else {
            id = new Long("1");
        }
        kardexcontrollerClient = new KardexControllerClient();
        ar_KerdexProducto = kardexcontrollerClient.findRange_XML(Dp_Date_from.getValue().toString(), Dp_Date_to.getValue().toString(), id.toString(), "yes");
        if (ar_KerdexProducto.size() > 0) {
            Tf_Producto.setText(ar_KerdexProducto.get(0).getProducto().getNombre());
        }
        Ol_KardexProducto.clear();

        Ol_KardexProducto.addAll(ar_KerdexProducto);
        // Ta_KardexProducto.getItems().clear();
        Ta_KardexProducto.setItems(Ol_KardexProducto);
        for (Kardex vd : ar_KerdexProducto) {
            if (vd.getTipo() == null) {
                vd.setTipo("ep");
            }
            if (vd.getTipo().equals("ep")) {
                cant_entrada = cant_entrada + vd.getCantidad_entrada();
                valor_entrada = valor_entrada.add(vd.getValor_entrada());

            } else {
                if (vd.getTipo().equals("dc")) {
                    cant_entrada = cant_entrada - vd.getCantidad_entrada();
                    valor_entrada = valor_entrada.subtract(vd.getValor_entrada());

                } else {
                    if (vd.getTipo().equals("sp")) {
                        cant_salida = cant_salida + vd.getCantidad_salida();
                        valor_salida = valor_salida.add(vd.getValor_salida());

                    } else {
                        if (vd.getTipo().equals("dv")) {
                            cant_salida = cant_salida - vd.getCantidad_salida();
                            valor_salida = valor_salida.subtract(vd.getValor_salida());

                        }
                    }
                }
            }

        }
        if (ar_KerdexProducto.size() > 0) {
            Tf_CantSaldo.setText(String.valueOf(ar_KerdexProducto.get(ar_KerdexProducto.size() - 1).getCantidad_saldo()));
            Tf_ValorSaldo.setText(String.valueOf(String.valueOf(ar_KerdexProducto.get(ar_KerdexProducto.size() - 1).getValor_saldo())));

        }
        T_Totales.setText("Saldo final: ");
        Tf_CantEntrada.setText(String.valueOf(cant_entrada));
        Tf_ValorEntrada.setText(String.valueOf(valor_entrada));
        Tf_CantSalida.setText(String.valueOf(cant_salida));
        Tf_ValorSalida.setText(String.valueOf(valor_salida));
        kardexcontrollerClient.close();
        kardexcontrollerClient = null;
    }
    Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {

            //stage finproduct
            findProduct.loadData();
            // Return null at the end of a Task of type Void
            return null;
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
