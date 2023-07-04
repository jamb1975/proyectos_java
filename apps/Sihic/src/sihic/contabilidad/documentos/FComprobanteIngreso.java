package sihic.contabilidad.documentos;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.AsientosModelosTiposDocumentosContables;
import modelo.ComprobanteEgreso;
import modelo.ComprobanteCausacionEgreso;
import modelo.LibroAuxiliar;
import modelo.Puc;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.ModeloTiposDocumentosContables;
import sihic.SearchPopover;
import sihic.SihicApp;
import static sihic.SihicApp.pageBrowser;
import sihic.contabilidad.ImpresionFactura;
import sihic.control.SearchBox;
import sihic.controlador.ConComprobanteEgresoControllerClient;
import sihic.controlador.ConComprobanteGastosControllerClient;
import sihic.controlador.ConPucControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.NumberToLetterConverter;
import sihic.util.UtilDate;

public class FComprobanteIngreso extends Application {

    StackPane stack;

    private Label la_nit;
    private Label la_direccion;
    private Label la_telefono;
    private Label la_norecibo;
    private Label la_valoregreso;
    private Label la_concepto;
    private Label la_nofactura;
    private Label la_nocheque;
    private Label la_notargeta;
    private Label la_tipotargeta;
    private Label la_banco;
    private Label la_nombre;
    private Label la_valorletras;
    private TextField codcuenta;
    private TextField cuenta;
    private TextField debito;
    private TextField credito;
    private TextField nombre;
    private TextField concepto;
    private TextField valoregreso;
    private TextField valorletras;
    private TextField direccion;
    private TextField telefono;
    private TextField norecibo;
    private SearchBox sb_docfactura = new SearchBox();
    private SearchPopover sp_factura;
    private TextField nocheque;
    private TextField notargeta;
    private ChoiceBox tipotargeta;
    private TextField banco;
    private ConComprobanteEgresoControllerClient conComprobanteEgresoControllerClient;
    private ConComprobanteGastosControllerClient conComprobanteGastosControllerClient;
    private ComprobanteEgreso conComprobanteEgreso;
    private ComprobanteCausacionEgreso conComprobanteGastos;
    private Button bu_save;
    private Button nuevo;
    private Button findfactura;
    EstadosMensaje estadosMensaje;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private ImageView imageView;
    private ObservableList ol_conpuc = FXCollections.observableArrayList();
    ;
    private List<Puc> l_conpuc;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private GridPane Gp_Generic;
    private static Stage stage = new Stage(StageStyle.DECORATED);
    private Factura factura;
    private FacturaItem facturaitem;
    private ImpresionFactura impresionFactura;
    private String cerosizq = "";
    private String cerosizqcomprobante = "";
    private Long no_factura = Long.MIN_VALUE;
    private Long nocomprobante = Long.MIN_VALUE;
    private Scene scene;
    private Button bu_printcomprobante;
    private Parent parent;
    private String appPath;
    private Class clz;
    private Object appClass;

    private Puc conPuc;
    private Puc conPuc_;
   
    private ConPucControllerClient conPucControllerClient;
    private DecimalFormat format = new DecimalFormat("#,0");
    private String caja = "11050500001";
    private String eps = "13050500001";
    private String particulares = "13052500001";
    private TableView tv_detalles;
    private ObservableList ol_detalles = FXCollections.observableArrayList();
    private List<LibroAuxiliar> l_detalles;
    private NumberToLetterConverter numberToLetterConverter;
    private static final SearchBox sb_cuenta = new SearchBox();
    private static final Label la_cuenta = new Label("Cuenta");
    private static final Label la_detalle = new Label("Detalle");
    private static final Label la_tipomovimiento = new Label("Tipo Movimiento");
    private static final Label la_valor = new Label("Valor");
    private static final Label la_centrocostos=new Label("Centro Costos");
    private SearchPopover sp_cuenta;
    private static final TextField detalle = new TextField();
    private static final TextField valor = new TextField();
    private static final ChoiceBox tipomovimiento = new ChoiceBox();
    private static final TextField nit = new TextField();
    private Label la_fechaelaboracion;
    private DatePicker fechaelaboracion;
    private Label la_tipocomprobante;
    private TextField tipocomprobante = new TextField();
    private HBox hbox = new HBox();
    private HBox hbox2 = new HBox();
    private Button bu_asiento;
     TableColumn<Puc, Boolean> deshabilitar = new TableColumn<Puc, Boolean>("Deshabilitar");
    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        sp_cuenta = new SearchPopover(sb_cuenta, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_cuenta.setMaxSize(300, 70);
        sb_cuenta.setMaxWidth(300);
        sb_cuenta.setMinWidth(300);
        sb_cuenta.setOnAction(e -> {
            detalle.setText("Concepto de Pago de Medicamentos N° Fact o Doc:" + SihicApp.s_FacturaProveedores.getNo_factura());
            detalle.requestFocus();

        });
        sp_cuenta.getSearchResultPopoverList().setOnMouseClicked(e -> {

            sb_cuenta.setText(SihicApp.conPuc.getCodigo() + " " + SihicApp.conPuc.getNombre());

        });
        sp_factura = new SearchPopover(sb_docfactura, pageBrowser, SihicApp.s_FacturaProveedores, permitirproceso);
        sp_factura.setMaxHeight(70);
        sb_docfactura.setMinWidth(210);

        detalle.setMaxWidth(300);
        detalle.setMinWidth(300);
        tipomovimiento.setMaxWidth(150);
        tipomovimiento.setMinWidth(150);
        //valor.setMaxWidth(200);
        valor.setMinWidth(257);

        la_cuenta.setMaxWidth(300);
        la_cuenta.setMinWidth(300);
        la_detalle.setMaxWidth(300);
        la_detalle.setMinWidth(300);
        la_tipomovimiento.setMaxWidth(150);
        la_tipomovimiento.setMinWidth(150);
        la_valor.setMaxWidth(200);
        la_valor.setMinWidth(200);
        la_centrocostos.setMaxWidth(150);
        la_centrocostos.setMinWidth(150);
        LoadChoiceBoxGeneral.cb_centrocostos.setMaxWidth(150);
        LoadChoiceBoxGeneral.cb_centrocostos.setMinWidth(150);
        tipomovimiento.getItems().addAll("Débito", "Crédito");
        la_tipocomprobante = new Label("Tipo Comprobante: ");
        la_tipocomprobante.setMinWidth(130);
        tipocomprobante.setText("Comprobante Diario Egreso");
        la_fechaelaboracion = new Label("Fecha: ");
        la_fechaelaboracion.setMinWidth(50);
        fechaelaboracion = new DatePicker(LocalDate.now());
        fechaelaboracion.setMinWidth(120);
        LoadChoiceBoxGeneral.cb_sucursales.getSelectionModel().select(0);
        valor.setTextFormatter(new TextFormatter<>(c
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
        valor.setOnAction(e -> {
            try {
                AddDetalle();
            } catch (Exception ex) {
                
            } 
        });

        hbox.getChildren().addAll(sb_cuenta, detalle, tipomovimiento, valor);
        hbox.setSpacing(3);
        hbox2.getChildren().addAll(la_cuenta, la_detalle, la_tipomovimiento, la_valor);
        hbox2.setSpacing(3);
        conPucControllerClient = new ConPucControllerClient();
        conComprobanteGastosControllerClient = new ConComprobanteGastosControllerClient();
        conComprobanteGastos = new ComprobanteCausacionEgreso();

        Gp_Generic = new GridPane();
        conComprobanteEgresoControllerClient = new ConComprobanteEgresoControllerClient();
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_save = new Button("", imageView);
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
            try {
                nuevo();
            } catch (ParseException ex) {
                Logger.getLogger(FComprobanteEgreso.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        nuevo.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    nuevo();
                } catch (ParseException ex) {
                    Logger.getLogger(FComprobanteEgreso.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        imageView = null;

        imageView = null;
        imageView = new ImageView("/images/pdf.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_printcomprobante = new Button("", imageView);
        bu_printcomprobante.setOnAction(e
                -> {
            try {

                pdfComprobante();
            } catch (Exception ex) {

            }
        });

        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        findfactura = new Button("", imageView);
        findfactura.setOnAction(e
                -> {
            try {

                show();
            } catch (Exception ex) {

            }
        });
        findfactura.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    show();
                } catch (Exception e2) {

                }
            }
        });

        imageView = null;
        imageView = new ImageView("/images/asientos.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_asiento = new Button();
        bu_asiento.setGraphic(imageView);
        bu_asiento.setOnAction(e -> {
            
        });
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        conComprobanteEgreso = new ComprobanteEgreso();
        la_direccion = new Label("Dirección:");

        la_nit = new Label("N° Identificación:");
        la_norecibo = new Label("N° Comprobante:");
        la_telefono = new Label("Telefono:");
        la_concepto = new Label("Concepto:");
        la_nocheque = new Label("Cheque:");
        la_nofactura = new Label("Factura o Doc Equiv:");
        la_tipotargeta = new Label("Tipo Targeta:");
        la_banco = new Label("Banco:");
        la_valoregreso = new Label("Valor:");
        la_notargeta = new Label("Targeta:");
        la_nombre = new Label("Paguese a la orden de:");
        la_valorletras = new Label("La Suma de:");
        direccion = new TextField();
        norecibo = new TextField();
        norecibo.setDisable(true);
        telefono = new TextField();
        concepto = new TextField();
        concepto.getProperties().put("requerido", true);
        concepto.setOnAction(e -> {
            codcuenta.requestFocus();
        });
        nocheque = new TextField();
        sb_docfactura.getProperties().put("requerido", true);
        notargeta = new TextField();
        tipotargeta = new ChoiceBox();
        tipotargeta.setMinWidth(390);
        tipotargeta.getItems().add(0, "Débito");
        tipotargeta.getItems().add(1, "Crédito");
        tipotargeta.getSelectionModel().select(-1);
        banco = new TextField();
        valoregreso = new TextField();
        debito = new TextField();
        cuenta = new TextField();

        valorletras = new TextField();
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
        codcuenta = new TextField();
        codcuenta.getProperties().put("requerido", true);
        codcuenta.setOnAction(e -> {
            findConPuc();
            debito.requestFocus();
        });

        nombre = new TextField();
        nombre.getProperties().put("requerido", true);

        hb_botones = new HBox(10);
        // hbox=new HBox(4, nofactura);
        hb_botones.getChildren().addAll(bu_save, nuevo, bu_printcomprobante);
        bu_save.setOnAction(e -> {
            try {
                try {
                    save();
                } catch (ParseException ex) {
                }
            } catch (InterruptedException ex) {

            }
        });

        direccion.setOnAction(e -> {
            telefono.requestFocus();
        });
        telefono.setOnAction(e -> {
            nocheque.requestFocus();
        });
        nombre.setOnAction(e -> {
            direccion.requestFocus();
        });
        sb_docfactura.setOnAction(e -> {
            nombre.setText(SihicApp.s_FacturaProveedores.getProveedores().getNombre());
            nit.setText(SihicApp.s_FacturaProveedores.getProveedores().getNo_ident());
            direccion.setText(SihicApp.s_FacturaProveedores.getProveedores().getDir1());
            telefono.setText(SihicApp.s_FacturaProveedores.getProveedores().getCelular());
            concepto.setText("Pago de Medicamentos N° Fact o Doc:" + SihicApp.s_FacturaProveedores.getNo_factura());
            nombre.requestFocus();

        });
        nocheque.setOnAction(e -> {
            banco.requestFocus();
        });
        banco.setOnAction(e -> {
            tipotargeta.requestFocus();
        });
        tipotargeta.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                notargeta.requestFocus();
            }
        });
        notargeta.setOnAction(e -> {
            valoregreso.requestFocus();
        });
        valoregreso.setOnAction(e -> {
            convertirnumeroaletra();
            concepto.requestFocus();
        });
        valoregreso.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            convertirnumeroaletra();
        });
        valorletras.setOnAction(e -> {
            codcuenta.requestFocus();
        });
        concepto.setOnAction(e -> {
            valorletras.requestFocus();
        });
        valorletras.setOnAction(e -> {
            bu_save.requestFocus();
        });
        tv_detalles = new TableView();

        tv_detalles.setOnKeyPressed(e -> {
            try {
                if (e.getCode().equals(e.getCode().DELETE)) {
                    removeDetalle();
                }
            } catch (Exception ex) {
                
            }
        });
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
        ccuenta.setMinWidth(350);
        ccuenta.setText("Cuenta");
        ccuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getNombre()));

            }
        });
        TableColumn detalles = new TableColumn();
        detalles.setMinWidth(400);
        detalles.setText("Detalle");
        detalles.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getDescripcion()));

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
        
        
 
        tv_detalles.getColumns().addAll(codigo, ccuenta, detalles, cdebito, ccredito);
        tv_detalles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setValignment(nombre, VPos.TOP);
        tv_detalles.setMaxHeight(200);
        Gp_Generic.add(la_norecibo, 0, 0, 1, 1);
        Gp_Generic.add(norecibo, 1, 0, 1, 1);
        Gp_Generic.add(la_fechaelaboracion, 2, 0, 1, 1);
        Gp_Generic.add(fechaelaboracion, 3, 0, 1, 1);
        Gp_Generic.add(la_nofactura, 4, 0, 1, 1);
        Gp_Generic.add(sb_docfactura, 5, 0, 1, 1);
        Gp_Generic.add(la_nit, 0, 1, 1, 1);
        Gp_Generic.add(nit, 1, 1, 1, 1);
        Gp_Generic.add(la_nombre, 2, 1, 1, 1);
        Gp_Generic.add(nombre, 3, 1, 1, 1);
        Gp_Generic.add(la_telefono, 4, 1, 1, 1);
        Gp_Generic.add(telefono, 5, 1, 1, 1);
        Gp_Generic.add(la_direccion, 0, 2, 1, 1);
        Gp_Generic.add(direccion, 1, 2, 3, 1);
        Gp_Generic.add(la_centrocostos, 4, 2, 1, 1);
        Gp_Generic.add(LoadChoiceBoxGeneral.cb_sucursales, 5, 2, 3, 1);
        Gp_Generic.add(la_nocheque, 0, 3, 1, 1);
        Gp_Generic.add(nocheque, 1, 3, 1, 1);
        Gp_Generic.add(la_banco, 2, 3, 1, 1);
        Gp_Generic.add(banco, 3, 3, 1, 1);
        Gp_Generic.add(la_tipotargeta, 4, 3, 1, 1);
        Gp_Generic.add(tipotargeta, 5, 3, 1, 1);
        Gp_Generic.add(la_notargeta, 0, 4, 1, 1);
        Gp_Generic.add(notargeta, 1, 4, 1, 1);
        Gp_Generic.add(la_valoregreso, 2, 4, 1, 1);
        Gp_Generic.add(valoregreso, 3, 4, 1, 1);
        Gp_Generic.add(la_valorletras, 4, 4, 1, 1);
        Gp_Generic.add(valorletras, 5, 4, 1, 1);
        
        
        Gp_Generic.add(la_concepto, 0, 5, 1, 1);
        Gp_Generic.add(concepto, 1, 5, 5, 1);
      
       
       
      
        Gp_Generic.add(hbox2, 0, 6, 6, 1);
        Gp_Generic.add(hbox, 0, 7, 6, 1);
        Gp_Generic.add(hb_botones, 0, 8, 6, 1);
        Gp_Generic.add(tv_detalles, 0, 9, 6, 1);
        Gp_Generic.add(sp_cuenta, 0, 9, 3, 1);
        GridPane.setValignment(sp_cuenta, VPos.TOP);
        GridPane.setValignment(sp_factura, VPos.TOP);
        GridPane.setValignment(tv_detalles, VPos.TOP);
        Gp_Generic.add(sp_factura, 2, 1, 5, 5);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        Gp_Generic.setHgap(5);
        Gp_Generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Generic.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Generic.getStyleClass().add("background");
        Gp_Message = new GridPane();
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
        Gp_Message.setMaxHeight(40);
        stack.getChildren().addAll(Gp_Generic, Gp_Message);
        //Gp_Generic.setStyle("border-width:1;border-color: #0007ff;");
        stack.setAlignment(Pos.TOP_CENTER);
        Gp_Generic.setAlignment(Pos.TOP_CENTER);
        // stack.autosize();
        // Gp_Generic.setMaxSize(1200, 500);
        // stack.setMaxSize(1200, 500);
        Gp_Generic.setMinSize(1200, 500);
        Gp_Generic.setAlignment(Pos.TOP_CENTER);
        // stack.setMinSize(1200, 500);   
        KeyCombination kn = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        Runnable task = () -> {
            try {
                nuevo();
            } catch (ParseException ex) {
                Logger.getLogger(FComprobanteEgreso.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        KeyCombination kg = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
        Runnable task2 = () -> {
            try {
                save();
            } catch (InterruptedException ex) {
                Logger.getLogger(FComprobanteIngreso.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FComprobanteIngreso.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        KeyCombination kc = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        Runnable task3 = () -> cerrarcomprobante();
        // Mnemonic mnemonic = new Mnemonic(B_New, kc);
        SihicApp.getScene().getAccelerators().clear();
        SihicApp.getScene().getAccelerators().put(kn, task);
        SihicApp.getScene().getAccelerators().put(kg, task2);
        SihicApp.getScene().getAccelerators().put(kc, task3);
        nit.setMinWidth(200);

        crearoeditar();
        modelosasientos("13");
        return stack;
    }

    public void nuevo() throws ParseException {

        SihicApp.conComprobanteEgreso = null;
        SihicApp.conComprobanteEgreso = new ComprobanteEgreso();
       
        getDetallesComprobantes();
        direccion.setText("");
        nit.setText("");
        norecibo.setText("");
        telefono.setText("");
        concepto.setText("");
        nocheque.setText("");
        sb_docfactura.setText("");
        tipotargeta.getSelectionModel().select(-1);
        banco.setText("");
        valoregreso.setText("0");
        debito.setText("0");
        credito.setText("0");
        nombre.setText("");
        valorletras.setText("");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        fechaelaboracion.setValue(LocalDate.now());
        enableAll();
        bu_save.setDisable(false);
        //nuevo.setDisable(true);
        bu_printcomprobante.setDisable(true);

        nit.requestFocus();
        L_Message.setText("Nuevo Registro");
        thread_message();

    }

    public void save() throws InterruptedException, ParseException {

        validar_formulario();

        if (permitirproceso) {
            set_datos();
            System.out.println("saldo->" + SihicApp.s_FacturaProveedores.getTotalAmount().subtract(SihicApp.s_FacturaProveedores.getValor_egresos()).subtract(SihicApp.conComprobanteEgreso.getValor()).doubleValue());
            if ((SihicApp.s_FacturaProveedores.getTotalAmount().subtract(SihicApp.s_FacturaProveedores.getValor_egresos()).subtract(SihicApp.conComprobanteEgreso.getValor())).doubleValue() >= 0.0) {

                try {

                   
                 

                    SihicApp.conComprobanteEgreso = conComprobanteEgresoControllerClient.edit(SihicApp.conComprobanteEgreso);
                    //  SihicApp.conComprobanteEgreso.getFacturaProveedores().gettotalegresos();
                    SihicApp.s_FacturaProveedores.add(SihicApp.conComprobanteEgreso);
                    SihicApp.s_FacturaProveedores.gettotalegresos();
                 SihicApp.facturaProveedoresControllerClient.guardarFactura();

                    AdminComprobanteEgreso.getRecords();
                    norecibo.setText(SihicApp.conComprobanteEgreso.getNocomprobantecerosizquierda());
                    L_Message.setText("Registro Almacenado");
                    thread_message();

                } catch (Exception e) {
                    L_Message.setText("Error Almacenando");
                    thread_message();
                }

            } else {
                L_Message.setText("El total de Valor de Egresos es Mayor al Valor de la deuda");
                thread_message();
            }
        }

        bu_printcomprobante.setDisable(false);
    }

    private void set_datos() {
        int i = 0;

        SihicApp.conComprobanteEgreso.setFechaelaboracion(new Date());
        SihicApp.conComprobanteEgreso.setBanco(banco.getText());
        SihicApp.conComprobanteEgreso.setConcepto(concepto.getText());
        SihicApp.conComprobanteEgreso.setValor(BigDecimal.valueOf(Double.valueOf(valoregreso.getText())));
        SihicApp.conComprobanteEgreso.setNocheque(nocheque.getText());
        SihicApp.conComprobanteEgreso.setNotargeta(notargeta.getText());
        SihicApp.conComprobanteEgreso.setTipotargeta(tipotargeta.getSelectionModel().getSelectedIndex());
        SihicApp.conComprobanteEgreso.setAfavorde(nombre.getText());
        SihicApp.s_FacturaProveedores.getProveedores().setCelular(telefono.getText());
        SihicApp.s_FacturaProveedores.getProveedores().setDir1(direccion.getText());

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
                            System.out.println("propiedad-->" + general.getProperties().get("requerido"));
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

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }

    public void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        scene = null;
        parent = null;
        parent = (Parent) clz.getMethod("createContent").invoke(appClass);

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

    private void pdfComprobante() {
        if (facturaitem != null) {
            try {
                impresionFactura.fpdfcomprobanteprocedimiento(facturaitem);
            } catch (IOException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setDatosFactura() {

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
            sb_docfactura.setDisable(false);
            findfactura.setDisable(false);
            nit.setDisable(false);
        }
    }

    public void disablesAll() {
        bu_printcomprobante.setDisable(true);
        bu_save.setDisable(true);
        nit.setDisable(true);
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
            sb_docfactura.setDisable(true);

        }
    }

    public void AddDetalle() throws ParseException, InterruptedException {
        if (SihicApp.conPuc != null) {
            //if (SihicApp.conComprobanteEgreso.getId() != null && SihicApp.conComprobanteContabilidad.getId() != null) {
            //    SihicApp.conComprobanteContabilidad.addPuc(SihicApp.conPuc, detalle.getText(), new BigDecimal(valor.getText().trim()), tipomovimiento.getSelectionModel().getSelectedIndex() == 0 ? true : false, LoadChoiceBoxGeneral.cb_centrocostos.getSelectionModel().getSelectedIndex());
              //  SihicApp.conComprobanteContabilidad = conPucControllerClient.save(SihicApp.conComprobanteContabilidad);
              //  getDetallesComprobantes();
                sb_cuenta.setText("");
                detalle.setText("");
                valor.setText("");
                tipomovimiento.getSelectionModel().select(-1);
            //}

        }

    }

    private void getDetallesComprobantes() throws ParseException {
        //colocamos los datos

        try {

            ol_detalles.clear();
           // ol_detalles.addAll(SihicApp.conComprobanteContabilidad.getDetallesComprobanteContabilidads().toArray());
            // Ta_KardexProducto.getItems().clear();
            tv_detalles.setItems(ol_detalles);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void findConPuc() {
        conPuc = conPucControllerClient.findConPuc(codcuenta.getText().trim(),0);
        if (conPuc != null) {
            cuenta.setText(conPuc.getNombre());
        }
    }

    private void cerrarcomprobante() {
        bu_save.setDisable(true);
        disablesAll();
        nuevo.setDisable(false);
        nuevo.requestFocus();
        l_detalles = null;
        l_detalles = new ArrayList<>();
        ol_detalles.clear();
        ol_detalles.addAll(l_detalles.toArray());
        tv_detalles.setItems(ol_detalles);
    }

    public void convertirnumeroaletra() {
        valorletras.setText(NumberToLetterConverter.convertNumberToLetter(valoregreso.getText()));
    }

    public void removeDetalle() throws ParseException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem()) != null) {
         
            getDetallesComprobantes();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void closeStage() {
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void crearoeditar() throws ParseException {

        if (SihicApp.conComprobanteEgreso != null) {
            if (SihicApp.conComprobanteEgreso.getId() != null) {
                norecibo.setText(SihicApp.conComprobanteEgreso.getNocomprobantecerosizquierda());
               // nit.setText(SihicApp.conComprobanteEgreso.getFacturaProveedores().getProveedores().getNo_ident());
               //telefono.setText(SihicApp.conComprobanteEgreso.getFacturaProveedores().getProveedores().getCelular());
               // direccion.setText(SihicApp.conComprobanteEgreso.getFacturaProveedores().getProveedores().getDir1());
                banco.setText(SihicApp.conComprobanteEgreso.getBanco());
                concepto.setText(SihicApp.conComprobanteEgreso.getConcepto());
                valoregreso.setText(SihicApp.conComprobanteEgreso.getValor().toString());
                convertirnumeroaletra();
                nombre.setText(SihicApp.conComprobanteEgreso.getAfavorde());
                nocheque.setText(SihicApp.conComprobanteEgreso.getNocheque());
                fechaelaboracion.setValue(UtilDate.formatoyearmesdia(SihicApp.conComprobanteEgreso.getFechaelaboracion()));
               // SihicApp.conComprobanteContabilidad = SihicApp.conComprobanteEgreso.getConComprobanteContabilidad();
                getDetallesComprobantes();
            } else {
                nuevo();
            }
        } else {

            nuevo();
        }
    }

   

    public void set_datos_comprobante() {
        //colocar fecha de datepicker
        LocalDate localDate = fechaelaboracion.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
          SihicApp.sucursales=LoadChoiceBoxGeneral.v_sucursales.get(LoadChoiceBoxGeneral.cb_sucursales.getSelectionModel().getSelectedIndex());
    }

    public void thread_message() {
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

    

    private static void modelosasientos(String codigo) {
        if (SihicApp.li_TiposDocumentosContableses.size() > 0) {
            for (ModeloTiposDocumentosContables mtdc : SihicApp.li_ModeloTiposDocumentosContableses.stream().filter(line -> String.valueOf(line.getAsientosModelosTiposDocumentosContableses()).equals(codigo)) //filters the line, equals to "mkyong"
                    .collect(Collectors.toList())) {
                LoadChoiceBoxGeneral.v_ModelosTiposAsientosContables.add(mtdc);
                LoadChoiceBoxGeneral.cb_ModelosTiposAsientosContable.getItems().add(mtdc.getTiposDocumentosContables().getCodigo() + " - " + mtdc.getDescripcion());

            }
        }
    }

    
}
