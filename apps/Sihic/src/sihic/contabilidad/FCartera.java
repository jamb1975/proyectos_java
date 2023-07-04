/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import modelo.ComprobanteIngreso;
import modelo.LibroAuxiliar;
import modelo.Puc;
import modelo.Factura;
import sihic.SihicApp;

import sihic.controlador.ConPucControllerClient;
import sihic.controlador.FacturaControllerClient;

/**
 *
 * @author adminlinux
 */
public class FCartera extends Application {

    private GridPane Gp_Generic;
    private TableView ta_factura;
    private Label l_buscar;
    private TextField buscar;
    private FacturaControllerClient facturaControllerClient;
    private Button bu_cerrarfactura;
    private FindServicios findProduct;
    private Factura factura;
    private DatePicker Dp_Date_from;
    private DatePicker Dp_Date_to;
    private Label L_Date_from;
    private Label L_Date_to;
    private ObservableList Ol_Factura = FXCollections.observableArrayList();
    ;
    private List<Factura> Li_facturas;
    private Label la_concepto;
    private Label la_codcuenta;
    private Label la_cuenta;
    private Label la_debito;
    ;
    private Label la_credito;
    private Label la_costoventas;
    private TextField codcuenta;
    private TextField cuenta;
    private TextField debito;
    private TextField credito;
    private TextField concepto;
    private TextField costoventas;
    private Puc conPuc;
    private DecimalFormat format = new DecimalFormat("#,0");
    private ConPucControllerClient conPucControllerClient;
    private Button bu_save;
    private Button nuevo;
    private ObservableList ol_detalles = FXCollections.observableArrayList();
    private List<LibroAuxiliar> l_detalles;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private TableView tv_detalles;
    private boolean permitirproceso = false;
    private HBox hb_botones;
    private Label la_fecha;
    private Label la_recibido;
    private Label la_nit;
    private Label la_direccion;
    private Label la_telefono;
    private Label la_norecibo;
    private Label la_valor;
    private Label la_saldo;
    private Label la_nofactura;
    private TextField fecha;
    private TextField recibido;
    private TextField nit;
    private TextField valor;
    private TextField saldo;
    private TextField direccion;
    private TextField telefono;
    private TextField norecibo;
    private TextField nofactura;
    private StackPane stack;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        stack = new StackPane();
        conPucControllerClient = new ConPucControllerClient();
        L_Date_from = new Label("Desde: ");
        L_Date_to = new Label("A: ");
        Dp_Date_from = new DatePicker();
        Dp_Date_from.setValue(LocalDate.now());
        Dp_Date_to = new DatePicker();
        Dp_Date_to.setValue(LocalDate.now());
        la_concepto = new Label("Concepto:");
        la_codcuenta = new Label("Codigo:");
        la_cuenta = new Label("Cuenta:");
        la_debito = new Label("Debito:");
        la_credito = new Label("Credito:");
        la_costoventas = new Label("Costo de Ventas:");
        concepto = new TextField();
        concepto.getProperties().put("requerido", true);
        la_nofactura = new Label("No. Factura:");
        la_direccion = new Label("Dirección:");
        la_fecha = new Label("Fecha:");
        la_nit = new Label("Nit:");
        la_norecibo = new Label("N° Recibo:");
        la_telefono = new Label("Telefono:");
        la_recibido = new Label("Cliente:");
        la_valor = new Label("Valor Factura:");
        la_saldo = new Label("Saldo Factura:");
        direccion = new TextField();
        fecha = new TextField();
        saldo = new TextField();
        nit = new TextField();
        nit.getProperties().put("requerido", true);
        norecibo = new TextField();
        telefono = new TextField();
        recibido = new TextField();
        nofactura = new TextField();
        valor = new TextField();
        costoventas = new TextField();
        recibido.getProperties().put("requerido", true);
        concepto.setOnAction(e -> {
            codcuenta.requestFocus();
        });
        codcuenta = new TextField();
        codcuenta.getProperties().put("requerido", true);
        codcuenta.setOnAction(e -> {
            findConPuc();
            debito.requestFocus();
        });
        debito = new TextField();
        cuenta = new TextField();
        debito.getProperties().put("requerido", true);
        debito.setOnAction(e -> {

            credito.requestFocus();
        });
        debito.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        credito = new TextField();
        credito.getProperties().put("requerido", true);
        credito.setOnAction(e -> {

            bu_save.requestFocus();
        });
        credito.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
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

        l_buscar = new Label("Buscar:");
        buscar = new TextField();
        buscar.setOnKeyPressed(e -> {
            try {
                loadData();
            } catch (Exception e3) {
            }
        });
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_save = new Button("", imageView);
        bu_save.setOnAction(e -> {
            try {
                try {
                    save();
                } catch (ParseException ex) {
                }
            } catch (InterruptedException ex) {

            }
        });
        bu_save.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();
                } catch (Exception e3) {

                }
            }
        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        nuevo = new Button("", imageView);
        nuevo.setOnAction(e -> {
            nuevo();
        });
        nuevo.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                nuevo();
            }
        });
        hb_botones = new HBox(10);

        hb_botones.getChildren().addAll(bu_save, nuevo);
        Gp_Generic = new GridPane();
        ta_factura = new TableView();
        TableColumn cnofactura = new TableColumn();
        cnofactura.setMinWidth(100);
        cnofactura.setText("No Factura");
        cnofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getNofacturacerosizquierda());

            }
        });
        TableColumn cvalor = new TableColumn();
        cvalor.setMinWidth(100);
        cvalor.setText("Valor");
        cvalor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());

            }
        });
        TableColumn csaldo = new TableColumn();
        csaldo.setMinWidth(100);
        csaldo.setText("Saldo");
        csaldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
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

                return new SimpleStringProperty(factura.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenEapb().getNit());

            }
        });
        TableColumn nombres = new TableColumn();
        nombres.setMinWidth(300);
        nombres.setText("Eps");
        nombres.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenEapb().getNombre());

            }
        });
        ta_factura.getColumns().addAll(cnofactura, noident, nombres, cvalor, csaldo);

        tv_detalles = new TableView();
        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(150);
        codigo.setText("Codigo");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getCodigo()));

            }
        });
        TableColumn ccuenta = new TableColumn();
        ccuenta.setMinWidth(300);
        ccuenta.setText("Cuenta");
        ccuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getNombre()));

            }
        });
        TableColumn cdebito = new TableColumn();
        cdebito.setMinWidth(150);
        cdebito.setText("Débito");
        cdebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getDebe().toString()));

            }
        });
        TableColumn ccredito = new TableColumn();
        ccredito.setMinWidth(150);
        ccredito.setText("Crédito");
        ccredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getHaber().toString()));

            }
        });
        ta_factura.setOnMouseClicked(e -> {
            setDatosFactura();
        });
        ta_factura.setOnKeyReleased(e -> {
            if (e.getCode().equals(e.getCode().DOWN) || e.getCode().equals(e.getCode().UP)) {
                setDatosFactura();
            }
        });
        tv_detalles.getColumns().addAll(codigo, ccuenta, cdebito, ccredito);
        Gp_Generic.setAlignment(Pos.CENTER);
        Gp_Generic.addRow(0, l_buscar, L_Date_from, L_Date_to);
        Gp_Generic.addRow(1, buscar, Dp_Date_from, Dp_Date_to);
        Gp_Generic.add(ta_factura, 0, 2, 7, 1);
        Gp_Generic.addRow(3, la_fecha, la_nofactura, la_valor, la_saldo);
        Gp_Generic.addRow(4, fecha, nofactura, valor, saldo);
        Gp_Generic.addRow(5, la_nit, la_recibido, la_direccion, la_telefono);
        Gp_Generic.addRow(6, nit, recibido, direccion, telefono);
        GridPane.setColumnSpan(la_concepto, 3);
        Gp_Generic.addRow(7, la_concepto);
        Gp_Generic.addRow(7, la_costoventas);
        GridPane.setColumnSpan(concepto, 3);
        Gp_Generic.addRow(8, concepto);
        Gp_Generic.addRow(8, costoventas);
        Gp_Generic.addRow(9, la_codcuenta, la_cuenta, la_debito, la_credito);
        Gp_Generic.addRow(10, codcuenta, cuenta, debito, credito, hb_botones);
        GridPane.setColumnSpan(tv_detalles, 6);
        Gp_Generic.addRow(11, tv_detalles);
        Gp_Generic.setVgap(3);
        Gp_Generic.setHgap(3);
        ta_factura.autosize();
        ta_factura.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_detalles.autosize();
        tv_detalles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(Gp_Generic.getLayoutBounds().getWidth(), 40);
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);

        Gp_Generic.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Generic.getStyleClass().add("background");
        stack.getChildren().addAll(Gp_Generic, Gp_Message);
        stack.setStyle("border-width:1;border-color: #0007ff;");
        stack.setAlignment(Pos.TOP_CENTER);
        Gp_Generic.setAlignment(Pos.TOP_CENTER);
        // stack.autosize();
        Gp_Generic.setMinSize(820, 500);
        stack.setMinSize(820, 500);
        disablesAll();
        loadData();
        return stack;
    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((Factura) ta_factura.getSelectionModel().getSelectedItem()) != null) {
            factura = (Factura) ta_factura.getSelectionModel().getSelectedItem();

        }
    }

    public void loadData() throws ParseException {
        try {
            Li_facturas = null;
            Li_facturas = facturaControllerClient.facturasacartera(Dp_Date_from.getValue().toString(), Dp_Date_to.getValue().toString(), buscar.getText());
            Ol_Factura.clear();
            Ol_Factura.addAll(Li_facturas.toArray());
            ta_factura.setItems(Ol_Factura);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cerrarfactura() throws ParseException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        checkregistroexistente();
        if (factura != null) {
            facturaControllerClient.cerrarfactura(factura);
            loadData();
        }
    }

    public void findConPuc() {
        conPuc = conPucControllerClient.findConPuc(codcuenta.getText().trim(),0);
        if (conPuc != null) {
            cuenta.setText(conPuc.getNombre());
        }
    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
            setDatos();
            try {

                AddDetalle();
            
                factura.setCausadaacontabilidad(true);
                facturaControllerClient.guardarFactura(factura);
                loadData();
                refreshdetalles();

                L_Message.setText("Registro Almacenado");
                SihicApp.facturaItem = null;
                codcuenta.setText("");
                cuenta.setText("");
                debito.setText("0");
                credito.setText("0");
                codcuenta.requestFocus();
                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                };
                backgroundThread = new Thread(Task_Message);
                backgroundThread.setDaemon(true);
                backgroundThread.start();
            } catch (Exception e) {
                e.printStackTrace();
                L_Message.setText("Error Almacenando");
                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {

                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            }
            refreshdetalles();
            codcuenta.requestFocus();
        }

    }

    public void nuevo() {

        direccion.setText("");
        fecha.setText("");
        nit.setText("");
        norecibo.setText("");
        telefono.setText("");
        concepto.setText("");
        nofactura.setText("");
        valor.setText("0");
        saldo.setText("0");
        debito.setText("0");
        credito.setText("0");
        recibido.setText("");
        costoventas.setText("0");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        fecha.setText(df.format(new Date()));
        enableAll();

        bu_save.setDisable(false);
        //nuevo.setDisable(true);

        l_detalles = null;
        l_detalles = new ArrayList<>();
        ol_detalles.clear();
        ol_detalles.addAll(l_detalles.toArray());
        tv_detalles.setItems(ol_detalles);
        tv_detalles.requestFocus();

    }

    public void AddDetalle() throws ParseException, InterruptedException {
        if (conPuc != null) {

        }
    }

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : Gp_Generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
                        permitirproceso = false;
                        general.setStyle("-fx-background-color:#ff1600");

                    }
                }

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    if (general.getProperties().get("requerido") != null) {
                        permitirproceso = false;
                        general.getStylesheets().add(0, "/sihic/personal.css");
                        general.getStylesheets().set(0, "/sihic/personal.css");
                    }

                }
            }
        }
        FadeTransition ft = new FadeTransition(Duration.millis(2000), bu_save);
        //ft.setFromValue(0.0);
        // ft.setToValue(1);

        ft.play();

        //success.setOpacity(0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Object n : Gp_Generic.getChildren().toArray()) {
                    if (n.getClass() == TextField.class) {
                        TextField general = (TextField) n;
                        if (general.getProperties().get("requerido") != null) {
                            if (((boolean) general.getProperties().get("requerido") == true)) {

                                general.setStyle(null);
                                general.getStyleClass().add("text-field");
                            }
                        }

                    } else {
                        if (n.getClass() == ChoiceBox.class) {
                            ChoiceBox general = (ChoiceBox) n;

                            if (general.getProperties().get("requerido") != null) {
                                general.getStylesheets().set(0, SihicApp.hojaestilos);

                            }

                        }
                    }
                }
            }
        });

    }

    private void refreshdetalles() {

    }

    public void setDatos() {
        try {
            checkregistroexistente();
        } catch (Exception e) {

        }

    }

    public void setDatosFactura() {
        try {
            checkregistroexistente();
            if (factura != null) {
                concepto.setText("Compra a Crédito Factura N° " + factura.getNofacturacerosizquierda());
                valor.setText(factura.getTotalAmount().toString());
                saldo.setText(factura.getSaldo().toString());
                nit.setText(factura.getGenEapb().getNit());
                recibido.setText(factura.getGenEapb().getNombre());
                direccion.setText(factura.getGenEapb().getDireccion());
                telefono.setText(factura.getGenEapb().getTelefono());
                nofactura.setText(factura.getNofacturacerosizquierda());
                costoventas.setText(costodeventas().toString());
            }
        } catch (Exception e) {

        }
    }

    public void enableAll() {
        for (Object n : Gp_Generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setDisable(false);
            }
            if (n.getClass() == ChoiceBox.class) {
                ChoiceBox general = (ChoiceBox) n;
                general.setDisable(false);
            }
            if (n.getClass() == Button.class) {
                Button general = (Button) n;
                general.setDisable(false);
            }
            if (n.getClass() == DatePicker.class) {
                DatePicker general = (DatePicker) n;
                general.setDisable(false);
            }

        }
    }

    public void disablesAll() {

        bu_save.setDisable(true);

        for (Object n : Gp_Generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setDisable(true);
            }
            if (n.getClass() == ChoiceBox.class) {
                ChoiceBox general = (ChoiceBox) n;
                general.setDisable(true);
            }
            if (n.getClass() == Button.class) {
                Button general = (Button) n;
                general.setDisable(true);
            }
            if (n.getClass() == DatePicker.class) {
                DatePicker general = (DatePicker) n;
                general.setDisable(true);
            }

        }
        nuevo.setDisable(false);
        nuevo.requestFocus();
    }

    private BigDecimal costodeventas() {

        return BigDecimal.ZERO;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
