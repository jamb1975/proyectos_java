/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import modelo.AgnCitas;
import modelo.Factura;
import modelo.FacturaItem;
import sihic.SihicApp;
import sihic.controlador.FacturaControllerClient;
import sihic.controlador.FacturaItemControllerClient;
import sihic.util.DatePickerTableCell;

/**
 *
 * @author adminlinux
 */
public class AdminFacturaItem extends Application {

    private GridPane gp_generico;
    private TableView tv_facturas;
    private Label la_fechadesde;
    private DatePicker fechadesde;
    private Label la_fechahasta;
    private Label la_buscar;
    private TextField buscar;
    private DatePicker fechahasta;
    private ObservableList Ol_FacturaItem = FXCollections.observableArrayList();
    ;
    private List<FacturaItem> Li_facturasItem;
    private FacturaItemControllerClient facturaItemControllerClient;
    private Button bu_rips;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private FileChooser fileDialog;
    private FacturaControllerClient facturaControllerClient;
    private ImpresionFactura impresionFactura;
    private Label la_opcion;
    private ChoiceBox opcion;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setHeaderText(null);
        alert.getDialogPane().setContentText("Esta seguro que quiere eliminar este registro?");

        facturaItemControllerClient = new FacturaItemControllerClient();
        facturaControllerClient = new FacturaControllerClient();
        stack = new StackPane();
        la_fechadesde = new Label("Desde: ");
        la_fechadesde.setMinWidth(50);
        la_fechahasta = new Label("Hasta: ");
        la_buscar = new Label("Buscar:");
        la_opcion = new Label("Tipo:");
        opcion = new ChoiceBox();
        opcion.getItems().add(0, "Factura");
        opcion.getItems().add(1, "Comprobante");
        opcion.getSelectionModel().select(0);
        buscar = new TextField();
        la_fechahasta.setMinWidth(50);
        fechadesde = new DatePicker(LocalDate.now());
        fechadesde.setMinWidth(120);
        fechahasta = new DatePicker(LocalDate.now());
        fechahasta.setMinWidth(120);
        ImageView imageView;

        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        //**********Ventana Personas
        gp_generico = new GridPane();
        tv_facturas = new TableView();
        tv_facturas.setMinHeight(550);

        TableColumn FechaAtencion = new TableColumn();
        FechaAtencion.setMinWidth(100);
        FechaAtencion.setText("Fecha de Atención");
        FechaAtencion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(sihic.util.UtilDate.formatodiamesyear2(facturaItem.getValue().getHclConsultas().getAgnCitas().getFechaHora()));

            }
        });
         StringConverter converter = new LocalDateStringConverter();
        FechaAtencion.setCellFactory(DatePickerTableCell.forTableColumn(converter));
        FechaAtencion.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FacturaItem, LocalDate>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<FacturaItem, LocalDate> t) {
           
                            ((FacturaItem) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).getHclConsultas().getAgnCitas().setFechahora(t.getNewValue());
                       LocalDate localDate = t.getNewValue();
                        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                        SihicApp.agnCitasTemp=((AgnCitas) t.getTableView().getItems().get(t.getTablePosition().getRow()).getHclConsultas().getAgnCitas());
                       
                        SihicApp.agnCitasTemp.setFechaHora(Date.from(instant));
                       // ModifyFactura();
                    }
                });
        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(100);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(facturaItem.getValue().getFactura().getNofacturacerosizquierda());

            }
        });
        TableColumn nocomp = new TableColumn();
        nocomp.setMinWidth(100);
        nocomp.setText("N° Comp");
        nocomp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(facturaItem.getValue().getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString());

            }
        });
        TableColumn eapb = new TableColumn();
        eapb.setMinWidth(200);
        eapb.setText("Eapb");
        eapb.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {
                try {
                    return new SimpleStringProperty(facturaItem.getValue().getGenPacientes().getGenEapb().getNombre());
                } catch (Exception e) {
                    return new SimpleStringProperty("No");

                }
            }
        });
        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(100);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(facturaItem.getValue().getProducto().getCodigo());

            }
        });
        TableColumn producto = new TableColumn();
        producto.setMinWidth(200);
        producto.setText("Producto");
        producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(facturaItem.getValue().getProducto().getNombre());

            }
        });
        TableColumn cant = new TableColumn();
        cant.setMinWidth(100);
        cant.setText("Cantidad");
        cant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(String.valueOf(facturaItem.getValue().getQuantity()));

            }
        });
        cant.setCellFactory(TextFieldTableCell.forTableColumn());
        cant.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FacturaItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FacturaItem, String> t) {

                ((FacturaItem) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setQuantity(Float.valueOf(t.getOldValue()));
                modifyCantidad(Float.valueOf(t.getNewValue()));
                //  factura=((Factura) t.getTableView().getItems().get(t.getTablePosition().getRow()));

            }
        });
        TableColumn valoru = new TableColumn();
        valoru.setMinWidth(100);
        valoru.setText("Valor/u");
        valoru.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(String.valueOf(facturaItem.getValue().getValor_u().toString()));

            }
        });

        TableColumn copago = new TableColumn();
        copago.setMinWidth(100);
        copago.setText("Copago");
        copago.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(String.valueOf((facturaItem.getValue().getCopago()).toString()));

            }
        });
        copago.setCellFactory(TextFieldTableCell.forTableColumn());
        copago.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FacturaItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FacturaItem, String> t) {

                /* ((FacturaItem) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setCopago(new BigDecimal(t.getNewValue()));*/
                BigDecimal cop = new BigDecimal(t.getNewValue());
                try {
                    if (((FacturaItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).getProducto().getHclCups() != null) {
                        modifyCopago(cop);
                    } else {
                        getFacturasItems();
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(AdminFacturaItem.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        TableColumn cuota = new TableColumn();
        cuota.setMinWidth(100);
        cuota.setText("Cuota");
        cuota.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(String.valueOf(facturaItem.getValue().getCuotamoderadora().toString()));

            }
        });
        cuota.setCellFactory(TextFieldTableCell.forTableColumn());
        cuota.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<FacturaItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<FacturaItem, String> t) {

                /*  ((FacturaItem) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setCuotamoderadora(new BigDecimal(t.getNewValue()));*/
                BigDecimal cmod = new BigDecimal(t.getNewValue());
                try {
                    if (((FacturaItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).getProducto().getHclCups() != null) {
                        modifyCuota(cmod);
                    } else {
                        getFacturasItems();
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(AdminFacturaItem.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        TableColumn valort = new TableColumn();
        valort.setMinWidth(100);
        valort.setText("Valor Total");
        valort.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(String.valueOf(facturaItem.getValue().getValor_total().toString()));

            }
        });
        //Se agrega la celda modificada con el botón a la tabla
        TableColumn buttonCol = new TableColumn<>("");
        buttonCol.setMinWidth(50);
        buttonCol.setSortable(false);
        buttonCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FacturaItem, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<FacturaItem, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        buttonCol.setCellFactory(
                new Callback<TableColumn<FacturaItem, Boolean>, TableCell<FacturaItem, Boolean>>() {

            @Override
            public TableCell<FacturaItem, Boolean> call(TableColumn<FacturaItem, Boolean> p) {
                return new ButtonCell(tv_facturas);
            }

        });
        TableColumn buttonColdelete = new TableColumn<>("");
        buttonColdelete.setMinWidth(50);
        buttonColdelete.setSortable(false);
        buttonColdelete.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FacturaItem, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<FacturaItem, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        buttonColdelete.setCellFactory(
                new Callback<TableColumn<FacturaItem, Boolean>, TableCell<FacturaItem, Boolean>>() {

            @Override
            public TableCell<FacturaItem, Boolean> call(TableColumn<FacturaItem, Boolean> p) {
                return new ButtonCellDelete(tv_facturas);
            }

        });
        tv_facturas.getColumns().addAll(nofactura, nocomp, eapb, codigo, producto, cant, valoru, copago, cuota, valort, buttonCol, buttonColdelete);

        tv_facturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_facturas.setEditable(true);

        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_generico.getLayoutBounds().getHeight(), gp_generico.getLayoutBounds().getWidth());
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
        gp_generico.setMinSize(1350, 620);
        gp_generico.addRow(0, la_buscar, buscar, la_opcion, opcion);

        gp_generico.add(tv_facturas, 0, 3, 7, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);

        buscar.setOnAction(e -> {
            try {
                getFacturasItems();
            } catch (ParseException ex) {
                Logger.getLogger(AdminFacturaItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        getFacturasItems();
        return stack;
    }

    private void getFacturasItems() throws ParseException {
        //colocamos los datos

        Li_facturasItem = facturaItemControllerClient.getLi_facturaitemsProcedimiento(buscar.getText().trim().equals("") ? Long.valueOf(47) : Long.valueOf(buscar.getText()), opcion.getSelectionModel().getSelectedIndex());
        Ol_FacturaItem.clear();
        Ol_FacturaItem.addAll(Li_facturasItem.toArray());
        tv_facturas.setItems(Ol_FacturaItem);

    }

    public void modifyCantidad(float quantity) {
        FacturaItem fi = (FacturaItem) tv_facturas.getFocusModel().getFocusedItem();
        fi.getFactura().modifyItem(fi, quantity);
        facturaControllerClient.crearFactura(fi.getFactura());
        try {
            getFacturasItems();
        } catch (ParseException ex) {
            Logger.getLogger(AdminFacturaItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modifyCopago(BigDecimal copago) throws ParseException {
        FacturaItem fi = (FacturaItem) tv_facturas.getFocusModel().getFocusedItem();
        fi.getFactura().modifyCopago(fi, copago);
        facturaControllerClient.crearFactura(fi.getFactura());
        getFacturasItems();
    }

    public void modifyCuota(BigDecimal cuota) throws ParseException {
        FacturaItem fi = (FacturaItem) tv_facturas.getFocusModel().getFocusedItem();
        fi.getFactura().modifyCuota(fi, cuota);
        facturaControllerClient.crearFactura(fi.getFactura());
        getFacturasItems();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private class ButtonCell extends TableCell<FacturaItem, Boolean> {

        //boton a mostrar
        final Button cellButton = new Button();
        final Button cellButtonPdf = new Button();

        ButtonCell(final TableView tblView) {

            Image imageOk = null;
            imageOk = new Image(getClass().getResourceAsStream("/images/pdf.png"));
            ImageView iv = null;
            iv = new ImageView(imageOk);
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            cellButtonPdf.setGraphic(iv);
            cellButtonPdf.setCursor(Cursor.HAND);
            cellButtonPdf.setTooltip(new Tooltip("Imprimir en Pdf"));
            cellButtonPdf.setOnAction(e -> {
                ImpresionFactura impresionFactura;
                int selectdIndex = getTableRow().getIndex();
                impresionFactura = null;

                FacturaItem fi = ((FacturaItem) Ol_FacturaItem.get(selectdIndex));
                impresionFactura = new ImpresionFactura(fi.getFactura());
                try {
                    impresionFactura.fpdfcomprobanteprocedimiento(fi);
                } catch (IOException ex) {
                    Logger.getLogger(AdminFacturaItem.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(AdminFacturaItem.class.getName()).log(Level.SEVERE, null, ex);
                }

                //_gpDatosFactura.getChildren().add(11, _tfValorIva);
            });
        }

        //Muestra un boton si la fila no es nula
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                int selectdIndex = getTableRow().getIndex();

                setGraphic(cellButtonPdf);

            } else {
                setGraphic(new Label());

            }
        }

    }

    private class ButtonCellDelete extends TableCell<FacturaItem, Boolean> {

        //boton a mostrar
        final Button cellButton = new Button();

        ButtonCellDelete(final TableView tblView) {
            cellButton.setPrefWidth(12);
            cellButton.setPrefHeight(12);
            Image imageOk = new Image(getClass().getResourceAsStream("/images/delete.png"));
            ImageView iv = new ImageView(imageOk);
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            cellButton.setGraphic(iv);
            cellButton.setCursor(Cursor.HAND);
            cellButton.setTooltip(new Tooltip("Eliminar Comprobante"));
            cellButton.setPrefWidth(16);
            cellButton.setPrefHeight(16);
            cellButton.setOnAction(e -> {
                alert = null;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.getDialogPane().setHeaderText(null);
                alert.getDialogPane().setContentText("Esta seguro que quiere eliminar este registro?");

                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            int selectdIndex = getTableRow().getIndex();
                            //borramos el objeto obtenido de la fila
                            Factura f = ((FacturaItem) Ol_FacturaItem.get(selectdIndex)).getFactura();

                            FacturaItem fi = ((FacturaItem) Ol_FacturaItem.get(selectdIndex));
                            f.removeProduct(fi);
                            facturaControllerClient.crearFactura(f);
                            try {
                                getFacturasItems();
                            } catch (ParseException ex) {
                                Logger.getLogger(AdminFacturaItem.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

            });

        }

        //Muestra un boton si la fila no es nula
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {

                setGraphic(cellButton);

            } else {
                setGraphic(new Label());

            }
        }

    }

}
