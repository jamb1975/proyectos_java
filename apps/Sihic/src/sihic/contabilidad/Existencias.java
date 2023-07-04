/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;
import sihic.SihicApp;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import modelo.Inf_Existencias;
import sihic.controlador.Inf_ExistenciasJerseyClient;
import sihic.controlador.ProductoControllerClient;

/**
 *
 * @author karolyani
 */
public class Existencias extends Application {

    private FindInsumosMedicamentos findProduct;
    private TableView Ta_Existencias;
    private ObservableList Ol_Inf_existencias;

    private GridPane gridpane;
    private GridPane gridpaneRoot;
    private Inf_ExistenciasJerseyClient inf_ExistenciasJerseyClient;
    ProductoControllerClient productoControllerClient;
    private Text T_Totales;
    private TextField T_Valor_total_todo;
    private TextField T_Cant_total_todo;
    private List<Inf_Existencias> inf_Existencias;
    private Label L_Producto;
    private TextField Tf_Producto;
    private Button B_Consultar;

    public Parent createContent() throws IOException {
        productoControllerClient = new ProductoControllerClient();
        findProduct = new FindInsumosMedicamentos(false);
        L_Producto = new Label("Producto: ");
        Tf_Producto = new TextField();
        Tf_Producto.setMinWidth(200);
        Tf_Producto.setPromptText("Encontrar por codigo o  nombre ");
        B_Consultar = new Button("Consultar");
        B_Consultar.setOnMouseClicked((MouseEvent ke) -> {
            getDatosExistencias();
        });
        B_Consultar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                getDatosExistencias();
            }
        });
        Tf_Producto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                findProduct.show();
                findProduct.getTf_Search().requestFocus();

            }
        });
        Tf_Producto.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                getDatosExistencias();
            }
        });
        findProduct.getStage().setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    if (findProduct.getKardex().getProducto() != null) {
                        Tf_Producto.setText(findProduct.getKardex().getProducto().getNombre());

                    }
                    getDatosExistencias();
                }
            }
        });
        T_Totales = new Text("Total: ");
        T_Totales.setFill(Color.BLACK);
        T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD, 20));

        T_Valor_total_todo = new TextField();
        T_Valor_total_todo.setMaxWidth(130);
        T_Cant_total_todo = new TextField();
        T_Cant_total_todo.setMaxWidth(130);
        inf_ExistenciasJerseyClient = new Inf_ExistenciasJerseyClient();
        Ol_Inf_existencias = FXCollections.observableArrayList();
        gridpane = new GridPane();
        gridpaneRoot = new GridPane();
        gridpane.getStyleClass().add("category-page");
        gridpane.setHgap(5);
        gridpane.setVgap(5);

        gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
        GridPane.setValignment(gridpaneRoot, VPos.TOP);
        //Creamos la tabla para ver existencias

        Ta_Existencias = new TableView();
        TableColumn Codigo = new TableColumn();
        Codigo.setMinWidth(130);
        Codigo.setText("Código");
        Codigo.setCellValueFactory(new PropertyValueFactory("codigo"));

        TableColumn Nombre = new TableColumn();
        Nombre.setMinWidth(130);
        Nombre.setText("Producto");
        Nombre.setCellValueFactory(new PropertyValueFactory("nombre"));

        TableColumn Cantidad_saldo = new TableColumn();
        Cantidad_saldo.setMinWidth(130);
        Cantidad_saldo.setText("Cantidad Saldo");
        Cantidad_saldo.setCellValueFactory(new PropertyValueFactory("cantidad_saldo"));

        TableColumn Valor_saldo = new TableColumn();
        Valor_saldo.setMinWidth(130);
        Valor_saldo.setText("Valor Saldo");
        Valor_saldo.setCellValueFactory(new PropertyValueFactory("valor_saldo"));

        Ta_Existencias.setMinHeight(500);
        Ta_Existencias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Ta_Existencias.getColumns().addAll(Codigo, Nombre, Cantidad_saldo, Valor_saldo);
        gridpane.add(L_Producto, 0, 0);
        gridpane.add(Tf_Producto, 1, 0);
        Tf_Producto.setAlignment(Pos.CENTER_LEFT);
        gridpane.add(Ta_Existencias, 0, 1);
        GridPane.setHalignment(Tf_Producto, HPos.LEFT);
        GridPane.setHalignment(L_Producto, HPos.CENTER);
        GridPane.setColumnSpan(Ta_Existencias, 4);
        gridpane.add(T_Totales, 1, 2);
        gridpane.add(T_Cant_total_todo, 2, 2);
        gridpane.add(T_Valor_total_todo, 3, 2);
        //Traer   datos desde la BD
        getDatosExistencias();

        gridpane.autosize();

        //agregamos el formulario y la tabla
        gridpane.getStyleClass().add("background");
        gridpane.setAlignment(Pos.CENTER);
        gridpane.getStylesheets().add(SihicApp.hojaestilos);

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

    public void findByCodigo() {

        findProduct.setKardex(productoControllerClient.findByCodigoLastKardex(Tf_Producto.getText()));
        if (findProduct.getKardex().getProducto() != null) {

            Tf_Producto.setText(findProduct.getKardex().getProducto().getNombre());

        }
        getDatosExistencias();
    }

    private void getDatosExistencias() {
        //colocamos los datos
        Ol_Inf_existencias.clear();
        inf_Existencias = inf_ExistenciasJerseyClient.findAll_XML(Tf_Producto.getText());
        Ol_Inf_existencias.addAll(inf_Existencias);
        Ta_Existencias.setItems(Ol_Inf_existencias);
        float cant_total = 0;
        BigDecimal valor_total = BigDecimal.ZERO;
        for (Inf_Existencias infe : inf_Existencias) {
            cant_total = cant_total + infe.getCantidad_saldo();
            valor_total = valor_total.add(infe.getValor_saldo());
        }

        T_Cant_total_todo.setText(String.valueOf(cant_total));
        T_Valor_total_todo.setText(valor_total.toString());

    }

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
