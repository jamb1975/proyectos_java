/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.Kardex;
import modelo.Producto;
import sihic.controlador.ProductoJerseyClient;

/**
 *
 * @author karolyani
 */
public class FindProductEmbbed extends Application {

    private Kardex kardex;
    private TextField Tf_Search;
    private Label L_Search;
    private Label L_Message;
    private GridPane G_FindProduct;
    private Stage stage = new Stage(StageStyle.DECORATED);
    private TableView Ta_Productos;
    private TableColumn Tc_Categoria;
    private TableColumn Tc_Nombre;
    private TableColumn Tc_Descrip;
    private TableColumn Tc_Aplicacion;
    private TableColumn Tc_CodigosRelacionados;
    private ObservableList Ol_Productos;
    private List<Kardex> O_ListProductos;
    private boolean esmateriaprima;
    private ProductoJerseyClient productoJerseyClient;
    Scene scene = null;
    private String search = null;

    public Parent createContent() {
        esmateriaprima = false;
        productoJerseyClient = new ProductoJerseyClient();
        G_FindProduct = new GridPane();
        G_FindProduct.setVgap(7);
        G_FindProduct.setStyle("-fx-padding:10px");
        G_FindProduct.getStylesheets().add("/sofackar/SofackarStylesCommon.css");
        G_FindProduct.getStyleClass().add("background");
        kardex = new Kardex();
        Tf_Search = new TextField();
        L_Search = new Label("Producto: ");
        //  L_Message=new Label("Escape para Cancelar o Escoja un producto y enter para agregarlo");
        Ol_Productos = FXCollections.observableArrayList();
        Ta_Productos = new TableView();
        Ta_Productos.setMinSize(480, 70);
        Tc_Categoria = new TableColumn("Producto");
        Tc_Nombre = new TableColumn("Cantidad");
        Tc_Descrip = new TableColumn("Precio");
        Tc_Aplicacion = new TableColumn("Aplicación");
        Tc_CodigosRelacionados = new TableColumn("Codigos Relacionados");
        Tc_Categoria.setMinWidth(100);
        Tc_Nombre.setCellValueFactory(new PropertyValueFactory("cantidad_saldo"));
        Tc_Nombre.setMinWidth(90);
        Tc_Descrip.setMinWidth(90);
        Tc_Aplicacion.setMinWidth(100);
        Tc_CodigosRelacionados.setMinWidth(100);
        Ta_Productos.getColumns().clear();
        Ta_Productos.getColumns().addAll(Tc_Categoria, Tc_Aplicacion, Tc_Nombre, Tc_Descrip, Tc_CodigosRelacionados);
        G_FindProduct.add(L_Search, 0, 0);
        G_FindProduct.add(Tf_Search, 1, 0);
        //  G_FindProduct.add(L_Message, 0, 0);
        G_FindProduct.add(Ta_Productos, 0, 1);
        G_FindProduct.setMaxSize(500, 150);
        // G_FindProduct.setGridLinesVisible(true);
        //GridPane.setColumnSpan(L_Message, 2);
        GridPane.setColumnSpan(Ta_Productos, 2);
        GridPane.setColumnSpan(Tf_Search, 2);
        GridPane.setHalignment(Ta_Productos, HPos.CENTER);
        G_FindProduct.setAlignment(Pos.CENTER);
        G_FindProduct.getStyleClass().add("background");

        init_eventos();
        return G_FindProduct;
    }

    public void loadData() {

        O_ListProductos = (List<Kardex>) productoJerseyClient.findCriteria(search, esmateriaprima);
        Ol_Productos.clear();
        Ol_Productos.addAll(O_ListProductos.toArray());
        Ta_Productos.setItems(Ol_Productos);
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }

    public Stage getStage() {
        return stage;
    }

    public TextField getTf_Search() {
        return Tf_Search;
    }

    public boolean isEsmateriaprima() {
        return esmateriaprima;
    }

    public void setEsmateriaprima(boolean esmateriaprima) {
        this.esmateriaprima = esmateriaprima;
    }

    private void init_eventos() {

        Tc_Categoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getProducto().getNombre());
            }
        });
        Tc_Aplicacion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getProducto().getDescrip());
            }
        });
        Tc_CodigosRelacionados.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getProducto().getCodigosrelacionados());
            }
        });
        Tc_Descrip.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getProducto().getPrecio().toString());
            }
        });

        Tf_Search.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    Ta_Productos.requestFocus();
                } else {
                    search = Tf_Search.getText();
                    loadData();
                }
            }
        });
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
