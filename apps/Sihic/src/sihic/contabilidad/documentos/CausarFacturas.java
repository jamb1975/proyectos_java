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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
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
import modelo.LibroAuxiliar;
import modelo.Puc;
import modelo.Factura;
import modelo.FacturaItem;
import sihic.SearchPopover;
import sihic.SihicApp;
import static sihic.SihicApp.pageBrowser;
import sihic.contabilidad.ImpresionFactura;
import sihic.control.SearchBox;
import sihic.controlador.ConPucControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.UtilDate;

    public class CausarFacturas extends Application {
    StackPane stack=new StackPane();
    private static final DatePicker fechaelaboracion = new DatePicker(LocalDate.now());
    private static final DatePicker fechaelaboracion2 = new DatePicker(LocalDate.now());
    private Button bu_save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private ImageView imageView;
    private ObservableList ol_conpuc = FXCollections.observableArrayList();
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
    private static final SearchBox sb_cuentadebito = new SearchBox();
    private SearchPopover sp_cuentadebito;
    private static final SearchBox sb_cuentacredito = new SearchBox();
    private SearchPopover sp_cuentacredito;
    private static final Label la_cuentadebito = new Label("Cuenta Débito");
    private static final Label la_detalledebito = new Label("Detalle");
    private static final Label la_cuentacredito= new Label("Cuenta Crédito");
    private static final Label la_detallecredito = new Label("Detalle Crédito");
    private static final Label la_tipofactura = new Label("Tipo Usuario:");
    private static final Label la_fechaelaboracion = new Label("Desde Fecha:");
    private static final Label la_fechaelaboracion2 = new Label(" Hasta Fecha:");
    private static final TextField detallecredito = new TextField();
    private static final TextField detalledebito = new TextField();
    private static final TextField totaldebito = new TextField();
    private static final TextField totalcredito = new TextField();
    private static ChoiceBox cb_tiposusuarios=new ChoiceBox();
    private HBox hbox = new HBox();
    private HBox hbox2 = new HBox();
    private Button bu_asiento;
    private ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemdelete;
    private ImageView img;
    private Puc conpuc_debito;
    private Puc conpuc_credito;
    private static RadioButton rb_facturas=new RadioButton();
    private static RadioButton rb_autoretencion=new RadioButton();
    ToggleGroup toggleGroup=new ToggleGroup();
    private HBox hb_radiobutton=new HBox();
    private TitledPane tp_generic;
    private static Label la_buscar=new Label("Buscar No. Comprobante:");
    private static TextField nocomprobante=new TextField("0");
    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        cb_tiposusuarios.getItems().clear();
        cb_tiposusuarios.getItems().addAll("Contributivo","Subsidiado","Particulares","Soat","Otro");
        tp_generic = new TitledPane();
        tp_generic.setText("Causar Facturas y Autorenteción Renta");
        tp_generic.setCollapsible(false);
        rb_facturas.setToggleGroup(toggleGroup);
        rb_facturas.setText("Facturas");
        rb_autoretencion.setText("Autoretención Renta");
        rb_autoretencion.setToggleGroup(toggleGroup);
        hb_radiobutton.setSpacing(5);
        hb_radiobutton.getChildren().addAll(rb_facturas,rb_autoretencion,la_buscar,nocomprobante);
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
        sp_cuentadebito = new SearchPopover(sb_cuentadebito, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_cuentadebito.setMaxSize(200, 70);
        sb_cuentadebito.setMaxWidth(200);
        sb_cuentadebito.setMinWidth(200);
        sp_cuentacredito = new SearchPopover(sb_cuentacredito, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_cuentacredito.setMaxSize(200, 70);
        sb_cuentacredito.setMaxWidth(200);
        sb_cuentacredito.setMinWidth(200);
        totaldebito.setMaxWidth(150);
        totalcredito.setMaxWidth(150);
        la_cuentadebito.setMaxWidth(200);
        la_cuentadebito.setMinWidth(200);
        la_cuentacredito.setMaxWidth(200);
        la_cuentacredito.setMinWidth(200);
        la_fechaelaboracion.setMinWidth(200);
        fechaelaboracion.setMinWidth(200);
        fechaelaboracion.setMaxWidth(200);
        la_fechaelaboracion.setMaxWidth(200);
        la_fechaelaboracion2.setMinWidth(200);
        fechaelaboracion2.setMinWidth(200);
        fechaelaboracion2.setMaxWidth(200);
        la_fechaelaboracion2.setMaxWidth(200);
        la_tipofactura.setMinWidth(200);
        la_tipofactura.setMaxWidth(200);
        cb_tiposusuarios.setMinWidth(200);
        cb_tiposusuarios.setMaxWidth(200);
        hbox.getChildren().clear();
        hbox2.getChildren().clear();
        hbox.setSpacing(3);
        hbox2.setSpacing(3);
        conPucControllerClient = new ConPucControllerClient();
        sb_cuentadebito.setOnAction(e->
        {   
            conpuc_debito=new Puc();
            conpuc_debito=SihicApp.conPuc;
            sb_cuentacredito.requestFocus();
        });
        sb_cuentacredito.setOnAction(e->
        {   
            conpuc_credito=new Puc();
            conpuc_credito=SihicApp.conPuc;
            cb_tiposusuarios.requestFocus();
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

       

       
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
     
        hb_botones = new HBox(10);
        // hbox=new HBox(4, nofactura);
       
        bu_save.setOnAction(e -> {
            try {
                try {
                    save();
                } catch (ParseException ex) {
                }
            } catch (InterruptedException ex) {

            }
        });
    nocomprobante.setOnAction(e->{
             try {
                 buscarnocomprobante();
             } catch (ParseException ex) {
                 Logger.getLogger(CausarFacturas.class.getName()).log(Level.SEVERE, null, ex);
             } catch (InterruptedException ex) {
                 Logger.getLogger(CausarFacturas.class.getName()).log(Level.SEVERE, null, ex);
             }
    });
        tv_detalles = new TableView();
        tv_detalles.setContextMenu(contextMenu);

        tv_detalles.setOnKeyPressed(e -> {
            try {
                if (e.getCode().equals(e.getCode().DELETE)) {
                    removeDetalle();
                }
            } catch (ParseException ex) {
                Logger.getLogger(CausarFacturas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(CausarFacturas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(CausarFacturas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(CausarFacturas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(CausarFacturas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(CausarFacturas.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        TableColumn nocomprobante2 = new TableColumn();
        nocomprobante2.setMinWidth(140);
        nocomprobante2.setText("N° Comprobante Causación Ingreso");
        nocomprobante2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty("");

            }
        });
        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(130);
        codigo.setText("Codigo");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getCodigo()));

            }
        });
        TableColumn ccuenta = new TableColumn();
        ccuenta.setMinWidth(330);
        ccuenta.setText("Cuenta");
        ccuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getNombre()));

            }
        });
        TableColumn detalles = new TableColumn();
        detalles.setMinWidth(330);
        detalles.setText("Detalle");
        detalles.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getDescripcion()));

            }
        });
        TableColumn cdebito = new TableColumn();
        cdebito.setMinWidth(200);
        cdebito.setText("Débito");
        cdebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getDebe().toString()));

            }
        });
        TableColumn ccredito = new TableColumn();
        ccredito.setMinWidth(200);
        ccredito.setText("Crédito");
        ccredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getHaber().toString()));

            }
        });
        tv_detalles.getColumns().addAll(nocomprobante2,codigo, ccuenta, detalles, cdebito, ccredito);
        tv_detalles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_detalles.setMaxHeight(500);
        GridPane.setHalignment(hbox, HPos.CENTER);
     
        hbox.getChildren().addAll(la_fechaelaboracion,la_fechaelaboracion2,la_cuentadebito, la_cuentacredito, la_tipofactura,new Label("Centro de costos:"));
        hbox2.getChildren().addAll(fechaelaboracion,fechaelaboracion2,sb_cuentadebito, sb_cuentacredito,cb_tiposusuarios,LoadChoiceBoxGeneral.cb_sucursales,bu_save,nuevo);
        Gp_Generic.add(hb_radiobutton, 0, 0, 1, 1);
        
        Gp_Generic.add(hbox, 0, 1, 1, 1);
        Gp_Generic.add(hbox2, 0, 2, 1, 1);
        Gp_Generic.add(tv_detalles, 0, 3, 1, 1);
        Gp_Generic.add(new HBox(2,new Label("Totales:"), totaldebito,totalcredito), 0, 4, 1, 1);
        Gp_Generic.add(sp_cuentadebito, 0, 3, 1, 2);
        Gp_Generic.add(sp_cuentacredito, 0, 3, 1, 2);
        GridPane.setValignment(sp_cuentadebito, VPos.TOP);
        GridPane.setValignment(sp_cuentacredito, VPos.TOP);
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
          tp_generic.setContent(Gp_Generic);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        //Gp_Generic.setStyle("border-width:1;border-color: #0007ff;");
        stack.setAlignment(Pos.TOP_CENTER);
        Gp_Generic.setAlignment(Pos.TOP_CENTER);
        // stack.autosize();
         Gp_Generic.setMinSize(1340, 550);
        
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
      
       
        return stack;
    }

    public void nuevo() throws ParseException {

        
        getDetallesComprobantes();
       
        fechaelaboracion.setValue(LocalDate.now());
        L_Message.setText("Nuevo Comprobante");
        thread_message();

    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
          
            try {
               
                
                L_Message.setText("Registro Almacenado");
               
                thread_message();

            } catch (Exception e) {
                e.printStackTrace();
                L_Message.setText("Error Almacenando");
                thread_message();
            }

        }

       AddDetalle();
     
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
        /*    if (SihicApp.conComprobanteContabilidad != null) {
              
                BigDecimal bdvalor=BigDecimal.ZERO;
                List<Factura> li_facturas;
                li_facturas=SihicApp.s_facturaControllerClient.findfacturasportipousuarioyfecha(fechaelaboracion.getValue().toString(),fechaelaboracion2.getValue().toString(), cb_tiposusuarios.getSelectionModel().getSelectedIndex());
                SihicApp.conComprobanteContabilidad.getDetallesComprobanteContabilidads().clear();
                if(rb_facturas.isSelected())
                {
                    bdvalor=new BigDecimal(1);
                }
                else
                {
                  if(rb_autoretencion.isSelected())
                {
                    bdvalor=new BigDecimal(0.008);
                }  
                }
                for(Factura f:li_facturas)
                {
                    
                  SihicApp.conComprobanteContabilidad.addPuc2(conpuc_debito,f.getNo_factura().toString(),f.getTotalAmount().multiply(bdvalor),true, LoadChoiceBoxGeneral.cb_sucursales.getSelectionModel().getSelectedIndex(),null);
                  SihicApp.conComprobanteContabilidad.addPuc2(conpuc_credito,f.getNo_factura().toString(),f.getTotalAmount().multiply(bdvalor),false, LoadChoiceBoxGeneral.cb_sucursales.getSelectionModel().getSelectedIndex(),null);
              
                }
                getDetallesComprobantes();
                gettotales();
                
            }*/

        }

    }

    public static void getDetallesComprobantes() throws ParseException {
        //colocamos los datos

        try {

            ol_detalles.clear();
       //     ol_detalles.addAll(SihicApp.c.getDetallesComprobanteContabilidads().toArray());
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
          //  SihicApp.conComprobanteContabilidad2.removeDetalle((LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem());
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
              
               /* getDetallesComprobantes();
                SihicApp.conComprobanteContabilidad.calculartotales();
                totaldebito.setText(SihicApp.conComprobanteContabilidad.getTotaldebe().toString());
                totalcredito.setText(SihicApp.conComprobanteContabilidad.getTotalhaber().toString());*/
   }
   public static void  buscarnocomprobante() throws ParseException, InterruptedException
   {
       SihicApp.s_facturaControllerClient.findcomprobanteporno(nocomprobante.getText().trim());
    /*   if(SihicApp.conComprobanteContabilidad!=null)
       {
           fechaelaboracion.setValue(sihic.util.UtilDate.formatoyearmesdia(sihic.util.UtilDate.fechainiciomes2(SihicApp.conComprobanteContabilidad.getFechaelaboracion())));
           fechaelaboracion2.setValue(sihic.util.UtilDate.formatoyearmesdia(sihic.util.UtilDate.fechafinmes2(SihicApp.conComprobanteContabilidad.getFechaelaboracion())));
      
       }*/
       gettotales();
   }
}
