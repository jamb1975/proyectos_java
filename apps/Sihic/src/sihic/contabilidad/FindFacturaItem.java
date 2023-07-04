/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

import sihic.contabilidad.documentos.FComprobanteIngreso;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
import modelo.FacturaItem;
import sihic.SihicApp;
import sihic.contabilidad.FindServicios;
import sihic.controlador.ConComprobanteIngresoControllerClient;

/**
 *
 * @author adminlinux
 */
public class FindFacturaItem extends Application {

    private GridPane Gp_Generic;
    private TableView ta_facturaitem;
    private Label l_buscar;
    private TextField buscar;
    private ObservableList ol_facturaitem = FXCollections.observableArrayList();
    ;
    private List<Factura> li_facturaitems;
    private ConComprobanteIngresoControllerClient conComprobanteIngresoControllerClient;
    private Button asignar;
    private FindServicios findProduct;
    private FacturaItem facturaItem;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        conComprobanteIngresoControllerClient = new ConComprobanteIngresoControllerClient();
        ImageView imageView = new ImageView("/images/select.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        asignar = new Button("", imageView);
        Tooltip tooltip = new Tooltip("Asignar Item de Factura");
        asignar.setTooltip(tooltip);
        asignar.setOnAction(e
                -> {

            try {
                asignarfacturaitem();
            } catch (ParseException ex) {

            }
        });

        l_buscar = new Label("Buscar:");
        buscar = new TextField();
        buscar.setOnKeyPressed(e -> {
            try {
                getFacturaItem();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        });
        Gp_Generic = new GridPane();
        ta_facturaitem = new TableView();
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
        noident.setMinWidth(250);
        noident.setText("N° Identificación");
        noident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getPrefijo().equals("A") ? factura.getValue().getGenEapb().getNit() : factura.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());

            }
        });
        TableColumn nombres = new TableColumn();
        nombres.setMinWidth(350);
        nombres.setText("Adquiriente");
        nombres.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getPrefijo().equals("A") ? factura.getValue().getGenEapb().getNombre() : factura.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getNombres());

            }
        });
        ta_facturaitem.getColumns().addAll(nofactura, noident, nombres, valor, saldo);
        ta_facturaitem.autosize();
        ta_facturaitem.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ta_facturaitem.setOnMouseClicked(e -> {
            try {
                checkregistroexistente();
            } catch (Exception ex) {
            }
        });
        ta_facturaitem.setOnKeyPressed(e -> {
            try {
                if (e.getCode().equals(e.getCode().ENTER)) {
                    checkregistroexistente();
                    asignarfacturaitem();
                }
            } catch (Exception ex) {
            }
        });
        Gp_Generic.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Generic.getStyleClass().add("background");
        Gp_Generic.add(l_buscar, 0, 0);
        Gp_Generic.add(buscar, 1, 0);
        Gp_Generic.add(asignar, 2, 0);
        Gp_Generic.add(ta_facturaitem, 0, 1, 6, 1);
        Gp_Generic.setVgap(5);
        Gp_Generic.setHgap(5);
        return Gp_Generic;
    }

    private void getFacturaItem() throws ParseException {
        //colocamos los datos

        // li_facturaitems=conComprobanteIngresoControllerClient.findfacturaitem(buscar.getText().trim());
        ol_facturaitem.clear();

        ol_facturaitem.addAll(li_facturaitems.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_facturaitem.setItems(ol_facturaitem);

    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((Factura) ta_facturaitem.getSelectionModel().getSelectedItem()) != null) {
            SihicApp.s_factura = (Factura) ta_facturaitem.getSelectionModel().getSelectedItem();

        }
    }

    public void asignarfacturaitem() throws ParseException {
        if (SihicApp.s_factura.getId() != null) {

            FComprobanteIngreso.closeStage();

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
