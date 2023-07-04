/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;
import sihic.SihicApp;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.Factura;
import sihic.controlador.FacturaControllerClient;

/**
 *
 * @author adminlinux
 */
public class FindFacturaClose extends Application {

    private GridPane Gp_Generic;
    private TableView ta_factura;
    private Label l_buscar;
    private TextField buscar;
    private FacturaControllerClient facturaControllerClient;
    private Button bu_cerrarfactura;
    private Button bu_abrirfactura;
    private FindServicios findProduct;
    private Factura factura;
    private DatePicker Dp_Date_from;
    private DatePicker Dp_Date_to;
    private Label L_Date_from;
    private Label L_Date_to;
    private Label la_estado;
    private ChoiceBox estado;
    private ObservableList Ol_Factura = FXCollections.observableArrayList();
    ;
    private List<Factura> Li_facturas;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        L_Date_from = new Label("Desde: ");
        L_Date_to = new Label("A: ");
        Dp_Date_from = new DatePicker();
        Dp_Date_from.setValue(LocalDate.now());
        Dp_Date_to = new DatePicker();
        Dp_Date_to.setValue(LocalDate.now());
        Dp_Date_from.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                try {
                    loadData();
                } catch (ParseException ex) {
                }

            }
        });
        Dp_Date_to.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                try {
                    loadData();

                } catch (ParseException ex) {
                }

            }
        });
        la_estado = new Label("Estado:");
        estado = new ChoiceBox();
        estado.getItems().add(0, "Abiertas");
        estado.getItems().add(1, "Cerradas");
        estado.getSelectionModel().select(0);
        estado.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        facturaControllerClient = new FacturaControllerClient();
        ImageView imageView = new ImageView("/images/cerrarfactura_40.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_cerrarfactura = new Button("", imageView);
        Tooltip tooltip = new Tooltip("Cerrar Factura");
        bu_cerrarfactura.setTooltip(tooltip);
        bu_cerrarfactura.setOnAction(e
                -> {

            try {
                cerrarfactura();
            } catch (Exception ex) {

            }

        });
        imageView = new ImageView("/images/abrirfactura.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_abrirfactura = new Button("", imageView);
        Tooltip tooltip2 = new Tooltip("Abrir Factura");
        bu_abrirfactura.setTooltip(tooltip2);
        bu_abrirfactura.setOnAction(e
                -> {

            try {
                abrirfactura();
            } catch (Exception ex) {

            }

        });

        l_buscar = new Label("Buscar:");
        buscar = new TextField();
        buscar.setOnKeyPressed(e -> {
            try {
                loadData();
            } catch (Exception e3) {
            }
        });
        Gp_Generic = new GridPane();
        ta_factura = new TableView();
        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(100);
        nofactura.setText("No Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getNofacturacerosizquierda());

            }
        });
        TableColumn valor = new TableColumn();
        valor.setMinWidth(100);
        valor.setText("Valor");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());

            }
        });
        TableColumn saldo = new TableColumn();
        saldo.setMinWidth(100);
        saldo.setText("Saldo");
        saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getSaldo().toString());

            }
        });

        TableColumn noident = new TableColumn();
        noident.setMinWidth(170);
        noident.setText("Nit");
        noident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                try {
                    return new SimpleStringProperty(factura.getValue().getGenEapb().getNit());
                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }
            }
        });
        TableColumn nombres = new TableColumn();
        nombres.setMinWidth(250);
        nombres.setText("Eps");
        nombres.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                try {
                    return new SimpleStringProperty(factura.getValue().getGenEapb().getNombre());

                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }
            }
        });
        TableColumn tipousuario = new TableColumn();
        tipousuario.setMinWidth(200);
        tipousuario.setText("Tipo Usuario");
        tipousuario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                try {
                    return new SimpleStringProperty(factura.getValue().getGenTiposUsuarios().getNombre());

                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }
            }
        });
        ta_factura.getColumns().addAll(nofactura, noident, nombres, tipousuario, valor, saldo);

        Gp_Generic.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Generic.getStyleClass().add("background");
        Gp_Generic.add(la_estado, 0, 0);
        Gp_Generic.add(estado, 1, 0);
        Gp_Generic.add(l_buscar, 2, 0);
        Gp_Generic.add(buscar, 3, 0);
        Gp_Generic.add(L_Date_from, 4, 0);
        Gp_Generic.add(Dp_Date_from, 5, 0);
        Gp_Generic.add(L_Date_to, 6, 0);
        Gp_Generic.add(Dp_Date_to, 7, 0);
        Gp_Generic.add(bu_cerrarfactura, 8, 0);
        Gp_Generic.add(bu_abrirfactura, 9, 0);
        Gp_Generic.add(ta_factura, 0, 1, 10, 1);
        Gp_Generic.setVgap(5);
        Gp_Generic.setHgap(5);
        Gp_Generic.setMinSize(1420, 500);
        ta_factura.autosize();
        ta_factura.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loadData();
        return Gp_Generic;
    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((Factura) ta_factura.getSelectionModel().getSelectedItem()) != null) {
            factura = (Factura) ta_factura.getSelectionModel().getSelectedItem();

        }
    }

    public void loadData() throws ParseException {
        try {
            Li_facturas = facturaControllerClient.facturasacerrar(Dp_Date_from.getValue().toString(), Dp_Date_to.getValue().toString(), buscar.getText(), estado.getSelectionModel().getSelectedIndex() == 0 ? false : true);
            Ol_Factura.clear();
            Ol_Factura.addAll(Li_facturas.toArray());
            ta_factura.setItems(Ol_Factura);
        } catch (Exception e) {

        }
        System.out.println("size factura-->" + Li_facturas.size());
    }

    public void cerrarfactura() throws ParseException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        checkregistroexistente();
        if (factura != null) {
            facturaControllerClient.cerrarfactura(factura);
            loadData();
        }
    }

    public void abrirfactura() throws ParseException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        checkregistroexistente();
        if (factura != null) {
            facturaControllerClient.abrirfactura(factura);
            loadData();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {
            System.out.println("new value->" + newValue.intValue());
            loadData();
        } catch (Exception e) {

        }
    }

}
