package sihic.contabilidad.documentos;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

import modelo.LibroAuxiliar;
import modelo.Puc;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.ModeloTiposDocumentosContables;
import modelo.NotaContabilidad;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import static sihic.SihicApp.pageBrowser;
import sihic.contabilidad.ImpresionFactura;
import sihic.control.SearchBox;
import sihic.controlador.ConPucControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.UtilDate;

public class FNotaContabilidad extends Application {

    StackPane stack;
    private static final Label la_nonotacontabilidad = new Label("N° nota contabilidad:");
    private static final Label la_concepto = new Label("Concepto");
    private static final Label la_fechaelaboracion = new Label("Fecha de Elaboración");
    private static final TextField concepto = new TextField();
    private static final TextField nonotacontabilidad = new TextField();
    private static final TextField totaldebito = new TextField();
    private static final TextField totalcredito = new TextField();
    private static final DatePicker fechaelaboracion = new DatePicker(LocalDate.now());
    
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
    private static GridPane Gp_Message;
    private static Label L_Message;
    private static Runnable Task_Message;
    private static Thread  backgroundThread;
    private GridPane Gp_Generic;
    private static Stage stage = new Stage(StageStyle.DECORATED);
    private Factura factura;
    private FacturaItem facturaitem;
    private ImpresionFactura impresionFactura=new ImpresionFactura();
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
    private static TableView tv_detalles;
    private static ObservableList ol_detalles = FXCollections.observableArrayList();
    private List<LibroAuxiliar> l_detalles;
    private static final SearchBox sb_cuenta = new SearchBox();
    private static final Label la_cuenta = new Label("Cuenta");
    private static final Label la_detalle = new Label("Detalle");
    private static final Label la_tipodoc= new Label("Tipo Documento");
    private static final Label la_documento = new Label("Documento");  
    private static final Label la_telefono = new Label("Teléfono");  
    private static final Label la_direccion = new Label("Dirección");
    private static final Label la_tipomovimiento = new Label("0:Débito o 1 Crédito)");
    private static final Label la_valor = new Label("Valor");
    private SearchPopover sp_cuenta;
    private static final TextField detalle = new TextField();
    private static ChoiceBox tipodoc=new ChoiceBox();
    private static final TextField documento = new TextField();
    private static final TextField telefono = new TextField();
    private static final TextField direccion = new TextField();
    private static final TextField tipomovimiento = new TextField();
    private static final TextField valor = new TextField();
    private HBox hbox = new HBox();
    private HBox hbox2 = new HBox();
    private Button bu_asiento;
    private static final Label la_tercero = new Label("Tercero:");
    SearchBox sb_proveedores=new SearchBox();
    SearchPopover sp_proveedore;
    private static ChoiceBox tiponota=new ChoiceBox();
    private ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemdelete;
    private ImageView img;
    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        stage.setOnHiding(e->{
            try {
                getDetallesComprobantes();
              
            } catch (ParseException ex) {
                Logger.getLogger(FNotaContabilidad.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        img = null;
        img = new ImageView("/images/editar.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemeditar = new MenuItem("Editar", img);
        itemeditar.setOnAction(e -> {

            try {
                if(checkregistroexistente())
                {
                show();
                }
            } catch (Exception x) {
                  x.printStackTrace();
            }
        });
        img = null;
        img = new ImageView("/images/delete.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemdelete = new MenuItem("Eliminar", img);
        itemdelete.setOnAction(e -> {
                try {
                removeDetalle();
            } catch (Exception ex) {
                
            }
        });
        contextMenu = new ContextMenu(itemeditar, itemdelete);
       
        LoadChoiceBoxGeneral.cb_sucursales.getSelectionModel().select(0);
        sp_cuenta = new SearchPopover(sb_cuenta, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_proveedore=new SearchPopover(sb_proveedores, new PageBrowser(), SihicApp.s_proveedores, false);
        sp_cuenta.setMaxSize(200, 70);
        sb_cuenta.setMaxWidth(200);
        sb_cuenta.setMinWidth(200);
         sp_proveedore.setMaxSize(200, 70);
        sb_proveedores.setMaxSize(200, 70);
        sb_proveedores.setMaxWidth(200);
        sb_proveedores.setMinWidth(200);
         la_tercero.setMaxWidth(200);
        la_tercero.setMinWidth(200);
        totaldebito.setMaxWidth(150);
        totalcredito.setMaxWidth(150);
        sb_cuenta.setOnAction(e -> {
            detalle.setText(concepto.getText());
            detalle.requestFocus();

        });
        tiponota.getItems().add("Nota de gastos");
        tiponota.getItems().add("Nota de ajustes");
        tiponota.getItems().add("Nota de depreciación");
        tiponota.getItems().add("Nota de amortización");
        tiponota.getItems().add("Nota de correción");
        detalle.setMaxWidth(200);
        detalle.setMinWidth(200);
        tipomovimiento.setMaxWidth(150);
        tipomovimiento.setMinWidth(150);
        valor.setMaxWidth(100);
        valor.setMinWidth(100);

        la_cuenta.setMaxWidth(200);
        la_cuenta.setMinWidth(200);
        la_detalle.setMaxWidth(200);
        la_detalle.setMinWidth(200);
        la_tipodoc.setMaxWidth(100);
        la_tipodoc.setMinWidth(100);
        tipodoc.setMaxWidth(100);
        tipodoc.setMinWidth(100);
        tipodoc.getItems().add("CC");
        tipodoc.getItems().add("NIT");
        documento.setMaxWidth(100);
        documento.setMinWidth(100);
        la_documento.setMaxWidth(100);
        la_documento.setMinWidth(100);
        la_telefono.setMaxWidth(100);
        la_telefono.setMinWidth(100);
         telefono.setMaxWidth(100);
        telefono.setMinWidth(100);
        la_direccion.setMinWidth(100);
         la_direccion.setMaxWidth(100);
        direccion.setMinWidth(100);
         direccion.setMaxWidth(100);
        la_tipomovimiento.setMaxWidth(150);
        la_tipomovimiento.setMinWidth(150);
        la_valor.setMaxWidth(100);
        la_valor.setMinWidth(100);
          la_nonotacontabilidad.setMaxWidth(300);
        la_nonotacontabilidad.setMinWidth(300);
        la_nonotacontabilidad.getStyleClass().set(0, "labelEspecial");
        la_fechaelaboracion.setMinWidth(120);

        fechaelaboracion.setMinWidth(120);
        
        valor.setOnAction(e -> {
            try {
                AddDetalle();
                 
         
           
            valor.setText("0");
            sb_proveedores.setText("");
            sb_proveedores.requestFocus();
            sp_cuenta.hide();
            } catch (Exception ex) {
               ex.printStackTrace();
            }
        });
        sb_proveedores.setOnAction(e->{
         sp_cuenta.hide();
         sb_cuenta.requestFocus();
        });
         hbox.getChildren().clear();
        hbox2.getChildren().clear();
     
        hbox.setSpacing(3);
        
        hbox2.setSpacing(3);
        conPucControllerClient = new ConPucControllerClient();
        sb_cuenta.setOnAction(e->
        {
            detalle.requestFocus();
        });
        detalle.setOnAction(e->{
            valor.requestFocus();
        });
        tipomovimiento.setOnAction(e->{
            valor.requestFocus();
        });
        
        
    
        Gp_Generic = new GridPane();
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
                    e3.printStackTrace();
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
        bu_asiento.setTooltip(new Tooltip("Agregar Asientos Contables Doc"));
        bu_asiento.setGraphic(imageView);
        bu_asiento.setOnAction(e -> {
            
        });

        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        concepto.getProperties().put("requerido", true);
        concepto.setMinWidth(350);
        concepto.setOnAction(e -> {

        });
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

        tv_detalles = new TableView();
        tv_detalles.setContextMenu(contextMenu);

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
        ccuenta.setMinWidth(300);
        ccuenta.setText("Cuenta");
        ccuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getNombre()));

            }
        });
        TableColumn detalles = new TableColumn();
        detalles.setMinWidth(300);
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

        tv_detalles.setMaxHeight(200);
        Gp_Generic.add(la_fechaelaboracion, 0, 0, 1, 1);
        Gp_Generic.add(fechaelaboracion, 1, 0, 1, 1);
        Gp_Generic.add(la_nonotacontabilidad, 2, 0, 2, 1);
        
        Gp_Generic.add(la_concepto, 0, 1, 1, 1);
        Gp_Generic.add(concepto, 1, 1, 1, 1);
        Gp_Generic.add(new Label("Tipo de nota:"), 2, 1, 1, 1);
        Gp_Generic.add(tiponota, 3, 1, 1, 1);
        Gp_Generic.add(new Label("Centro de costos:"),0 ,2, 1 ,1);
        Gp_Generic.add(LoadChoiceBoxGeneral.cb_sucursales, 1, 2, 1, 1);
         //Gp_Generic.add(la_tercero,2 ,2, 1 ,1);
        //Gp_Generic.add(sb_proveedores, 3, 2, 1, 1);
        GridPane.setHalignment(hbox, HPos.CENTER);
        Gp_Generic.add(hb_botones, 0, 4, 4, 1);
        hbox.getChildren().addAll(la_tercero,la_cuenta, la_detalle, la_valor);
        hbox2.getChildren().addAll(sb_proveedores,sb_cuenta, detalle, valor,bu_asiento);
        Gp_Generic.add(hbox, 0, 5, 5, 1);
        Gp_Generic.add(hbox2, 0, 6, 5, 1);
        Gp_Generic.add(tv_detalles, 0, 7, 5, 1);
        
        Gp_Generic.add(new Label("Totales:"), 0, 8, 1, 1);
       
        Gp_Generic.add(new HBox(2, totaldebito,totalcredito), 4, 8, 1, 1);
        Gp_Generic.add(sp_proveedore, 0, 7, 5, 2);
         Gp_Generic.add(sp_cuenta, 1, 7, 5, 2);
         GridPane.setValignment(sp_proveedore, VPos.TOP);
           GridPane.setValignment(sp_cuenta, VPos.TOP);
           GridPane.setHalignment(totaldebito, HPos.RIGHT);
           GridPane.setHalignment(totalcredito, HPos.RIGHT);
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
        //Gp_Generic.setMinSize(1200, 500);
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

        crearoeditar();
        modelosasientos("18");
        return stack;
    }

    public void nuevo() throws ParseException {

        SihicApp.notaContabilidad = null;
        SihicApp.notaContabilidad = new NotaContabilidad();
     
        getDetallesComprobantes();
        la_nonotacontabilidad.setText("N° nota contabilidad:");
        concepto.setText("");
        fechaelaboracion.setValue(LocalDate.now());
        L_Message.setText("Nuevo Registro");
        thread_message();

    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {
               
               
                if (SihicApp.notaContabilidad.getId() == null) {
                    SihicApp.notaContabilidadControllerClient.create();
                } else {
                    SihicApp.notaContabilidadControllerClient.update();
                }
               la_nonotacontabilidad.setText("N° nota contabilidad: "+SihicApp.notaContabilidad.getNocomprobantecerosizquierda());
                L_Message.setText("Registro Almacenado");
               
                thread_message();

            } catch (Exception e) {
                e.printStackTrace();
                L_Message.setText("Error Almacenando");
                thread_message();
            }

        }

        bu_printcomprobante.setDisable(false);
    }

    private void set_datos() {

        SihicApp.notaContabilidad.setFechaelaboracion(UtilDate.localdatetodate(fechaelaboracion.getValue()));
        SihicApp.notaContabilidad.setConcepto(concepto.getText());
        switch(tiponota.getSelectionModel().getSelectedIndex())
        {
            case 0:     SihicApp.notaContabilidad.setTiponota("AJ");
            break;
             case 1:     SihicApp.notaContabilidad.setTiponota("DE");
              break;
              case 2:     SihicApp.notaContabilidad.setTiponota("AM");
              break;
              case 3:     SihicApp.notaContabilidad.setTiponota("CO");
              break;
           
        }

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

    private static void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }

    public void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        appPath = "sihic.contabilidad.FDetallesComprobanteContabilidad";
        clz = Class.forName(appPath);
        appClass= clz.newInstance();
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
        
            try {
                ImpresionFactura.fpdfnotacontable();
            } catch (IOException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(FacturaApp.class.getName()).log(Level.SEVERE, null, ex);
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

        }
    }

    public void disablesAll() {
        bu_printcomprobante.setDisable(true);
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

        }
    }

    public void AddDetalle() throws ParseException, InterruptedException {
        if (SihicApp.conPuc != null) {
            if (SihicApp.notaContabilidad.getId() != null) {
                boolean tipomov=true;
                BigDecimal bdvalor=new BigDecimal(valor.getText());
                if(bdvalor.compareTo(BigDecimal.ZERO)==-1)
                {
                    tipomov=false;
                     bdvalor= bdvalor.multiply(new  BigDecimal(-1));
                }
                if(SihicApp.conPuc.getPorc_base()!=null)
                {
                bdvalor=bdvalor.multiply((SihicApp.conPuc.getPorc_base().multiply(BigDecimal.valueOf(0.01))));
                }
               
               gettotales();
                
            }

        }

    }

    public static void getDetallesComprobantes() throws ParseException {
        //colocamos los datos

        try {

            ol_detalles.clear();
           // ol_detalles.addAll(SihicApp.conComprobanteContabilidad2.getDetallesComprobanteContabilidads().toArray());
            // Ta_KardexProducto.getItems().clear();
            tv_detalles.setItems(ol_detalles);
        } catch (Exception e) {
            e.printStackTrace();
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

    public void removeDetalle() throws ParseException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem()) != null) {
        //    SihicApp.conComprobanteContabilidad2.removeDetalle((LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem());
            gettotales();
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
        String tn="";
        if (SihicApp.notaContabilidad != null) {
            if (SihicApp.notaContabilidad.getId() != null)
            {
                concepto.setText(SihicApp.notaContabilidad.getConcepto());
                fechaelaboracion.setValue(UtilDate.formatoyearmesdia(SihicApp.notaContabilidad.getFechaelaboracion()));
                la_nonotacontabilidad.setText("N° nota contabilidad: "+SihicApp.notaContabilidad.getNocomprobantecerosizquierda());
                 LoadChoiceBoxGeneral.cb_sucursales.getSelectionModel().select((1));
            switch(SihicApp.notaContabilidad.getTiponota())
             {  
            case "AJ":    tn="Nota de ajustes";
            break;
            case "DE":   tn="Nota de depreciación";
            break;
            case "AM":  tn="Nota de amortización";
            break;
            case "CO":  tn="Nota de correción";
            break;
           
        }        
                 tiponota.getSelectionModel().select(tn);
                // SihicApp.conComprobanteContabilidad2 = SihicApp.notaContabilidad.getConComprobanteContabilidad();
                 getDetallesComprobantes();
              //  SihicApp.conComprobanteContabilidad2.calculartotales();
                // totalcredito.setText(SihicApp.conComprobanteContabilidad2.getTotalhaber().toString());
                 // totaldebito.setText(SihicApp.conComprobanteContabilidad2.getTotaldebe().toString());
            } else {
                nuevo();
            }
        } else {

            nuevo();
        }
    }

      

    
    public void thread_message() {
        Task_Message = () -> {
            try {
                SetMessage();
                try {
                    AdminNotaContabilidad.getRecords();
                } catch (ParseException ex) {
                    Logger.getLogger(FNotaContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    
  private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_detalles.getSelectionModel()) != null) {
            SihicApp.conDetallesComprobanteContabilidad= (LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem();
            return true;
        }
        else
        {
            return false;
        }
    }  
   public static void gettotales() throws ParseException, InterruptedException
   {
               // saveComprobanteContabilidad();
                getDetallesComprobantes();
               // SihicApp.conComprobanteContabilidad2.calculartotales();
               // totaldebito.setText(SihicApp.conComprobanteContabilidad2.getTotaldebe().toString());
                //totalcredito.setText(SihicApp.conComprobanteContabilidad2.getTotalhaber().toString());
   }
}
