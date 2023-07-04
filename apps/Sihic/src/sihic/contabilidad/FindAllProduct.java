/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;
import sihic.SihicApp;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
import sihic.controlador.ProductoControllerClient;
import sihic.controlador.ProductoJerseyClient;

/**
 *
 * @author karolyani
 */
public class FindAllProduct {

    private Producto producto;
    private TextField Tf_Search;
    private Label L_Search;
    private Label L_Message;
    private GridPane G_FindProduct;
    private Stage stage = new Stage(StageStyle.DECORATED);
    private TableView Ta_Productos;
    private TableColumn Tc_Categoria;

    private TableColumn Tc_Descrip;
    private ObservableList Ol_Productos;
    private List<Producto> O_ListProductos;
    private boolean esmateriaprima;
    private ProductoControllerClient productoControllerClient;
    Scene scene = null;
    private String search = null;

    public FindAllProduct() {
        stage.setTitle("Buscar Productos");
        productoControllerClient = new ProductoControllerClient();
        G_FindProduct = new GridPane();
        G_FindProduct.setVgap(7);
        G_FindProduct.setStyle("-fx-padding:10px");
        G_FindProduct.getStylesheets().add(SihicApp.hojaestilos);
        G_FindProduct.getStyleClass().add("background");
        producto = new Producto();
        Tf_Search = new TextField();
        L_Search = new Label("Producto: ");
        L_Message = new Label("Escape para Cancelar o Escoja un producto y enter para agregarlo");
        Ol_Productos = FXCollections.observableArrayList();
        Ta_Productos = new TableView();
        Ta_Productos.setMinWidth(400);
        Tc_Categoria = new TableColumn("Producto");

        Tc_Descrip = new TableColumn("Precio");
        Tc_Categoria.setMinWidth(200);

        Tc_Descrip.setMinWidth(200);
        Ta_Productos.getColumns().clear();
        Ta_Productos.getColumns().addAll(Tc_Categoria, Tc_Descrip);

        G_FindProduct.add(L_Search, 0, 1);
        G_FindProduct.add(Tf_Search, 1, 1);
        G_FindProduct.add(L_Message, 0, 0);
        G_FindProduct.add(Ta_Productos, 0, 2);
        G_FindProduct.autosize();
        // G_FindProduct.setGridLinesVisible(true);
        GridPane.setColumnSpan(L_Message, 2);
        GridPane.setColumnSpan(Ta_Productos, 2);
        GridPane.setColumnSpan(Tf_Search, 2);
        GridPane.setHalignment(Ta_Productos, HPos.CENTER);
        G_FindProduct.setAlignment(Pos.CENTER);
        G_FindProduct.autosize();

        Ta_Productos.setMinWidth(G_FindProduct.getLayoutBounds().getWidth() + 5);
        Ta_Productos.setMinHeight((G_FindProduct.getLayoutBounds().getWidth() * 0.20) - 50);
        Ta_Productos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        scene = new Scene(G_FindProduct, Color.TRANSPARENT);
        init_eventos();
    }

    public void show() {
        if (stage.isShowing()) {
            stage.close();
        }

//set scene to stage
        stage.sizeToScene();

        //center stage on screen
        stage.centerOnScreen();
        stage.setScene(scene);

        // stage.setMaxWidth(450);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stage.show();
    }

    public void loadData() {

        O_ListProductos = (List<Producto>) productoControllerClient.findproductosall2(search);
        Ol_Productos.clear();
        Ol_Productos.addAll(O_ListProductos.toArray());
        Ta_Productos.setItems(Ol_Productos);
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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

        Tc_Categoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> producto) {
                return new SimpleStringProperty(producto.getValue().getNombre());
            }
        });

        Tc_Descrip.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> producto) {
                return new SimpleStringProperty(producto.getValue().getPrecio().toString());
            }
        });
        Ta_Productos.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    producto = ((Producto) Ta_Productos.getFocusModel().getFocusedItem());
                    stage.close();
                } else {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        stage.close();

                    } else {
                        if (keyEvent.getCode() == KeyCode.SPACE) {
                            Tf_Search.requestFocus();

                        }
                    }
                }
            }
        });
        G_FindProduct.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    stage.close();

                }

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
}
