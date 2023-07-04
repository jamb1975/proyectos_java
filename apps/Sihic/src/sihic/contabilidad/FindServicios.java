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
public class FindServicios {

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
    private ObservableList Ol_Productos;
    private List<Kardex> O_ListProductos;
    private boolean esmateriaprima;
    private ProductoControllerClient productoControllerClient;
    Scene scene = null;
    private String search = null;

    public FindServicios(boolean materiaprima) {
        stage.setTitle("Servicios");
        esmateriaprima = materiaprima;
        productoControllerClient = new ProductoControllerClient();
        G_FindProduct = new GridPane();
        G_FindProduct.setVgap(7);
        G_FindProduct.getStylesheets().add(SihicApp.hojaestilos);
        G_FindProduct.getStyleClass().add("background");
        kardex = new Kardex();
        Tf_Search = new TextField();
        L_Search = new Label("Servicio: ");
        Ol_Productos = FXCollections.observableArrayList();
        Ta_Productos = new TableView();
        Tc_Categoria = new TableColumn("Producto");
        Tc_Nombre = new TableColumn("Cantidad");
        Tc_Descrip = new TableColumn("Precio");
        Tc_Aplicacion = new TableColumn("Aplicación");
        Tc_Categoria.setMinWidth(200);
        Tc_Nombre.setCellValueFactory(new PropertyValueFactory("cantidad_saldo"));
        Tc_Nombre.setMinWidth(75);
        Tc_Descrip.setMinWidth(100);
        Tc_Aplicacion.setMinWidth(200);
        Ta_Productos.getColumns().clear();
        Ta_Productos.getColumns().addAll(Tc_Categoria, Tc_Aplicacion, Tc_Nombre, Tc_Descrip);
        Ta_Productos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        G_FindProduct.add(L_Search, 0, 0);
        G_FindProduct.add(Tf_Search, 1, 0);
        G_FindProduct.add(Ta_Productos, 0, 1);
        G_FindProduct.setMaxWidth(580);
        // G_FindProduct.setGridLinesVisible(true);

        GridPane.setColumnSpan(Ta_Productos, 2);
        GridPane.setColumnSpan(Tf_Search, 2);
        GridPane.setHalignment(Ta_Productos, HPos.CENTER);
        G_FindProduct.setAlignment(Pos.CENTER);
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

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stage.show();
    }

    public void loadservicioskardex() {

        O_ListProductos = (List<Kardex>) productoControllerClient.li_findserviciosKardex2(search);
        Ol_Productos.clear();
        Ol_Productos.addAll(O_ListProductos.toArray());
        Ta_Productos.setItems(Ol_Productos);
    }

    public void loadproductoskardexkardex() {

        O_ListProductos = (List<Kardex>) productoControllerClient.li_findproductosKardex2(search);
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
        Tc_Descrip.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getProducto().getPrecio().toString());
            }
        });
        Ta_Productos.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    kardex = ((Kardex) Ta_Productos.getFocusModel().getFocusedItem());
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
                    loadservicioskardex();
                }
            }
        });
    }

    public GridPane EmmbedFindProducto() {
        return G_FindProduct;
    }
}
