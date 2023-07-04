package sihic.contabilidad;

import sihic.SearchPopover;
import sihic.control.SearchBox;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.AsientosModelosTiposDocumentosContables;
import modelo.LibroAuxiliar;
import modelo.ModeloTiposDocumentosContables;
import modelo.Producto;
import sihic.PageBrowser;
import sihic.SihicApp;

import sihic.general.LoadChoiceBoxGeneral;
import sihic.search.IndexSearcher;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FModelosTiposDocumentosContables extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GridPane gp_generic;
    private TextField codigo;
    private SearchBox sb_conpuc = new SearchBox();
    private SearchPopover sp_conpuc;
    private TextField descripción = new TextField();
    private TextField porcvalor1 = new TextField("0");
    private TextField porcvalor2 = new TextField("0");
    private TextField porcvalor3 = new TextField("0");
    private TextField descripcion = new TextField();
    private TextField tipomovimiento = new TextField();
    private ChoiceBox centrocosto = new ChoiceBox();
    private Button save;
    private Button nuevo;
    private Button agregarcuenta;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private DecimalFormat format = new DecimalFormat("#.0");
    private TableView tv_detalles;
    private ObservableList ol_detalles = FXCollections.observableArrayList();
    public Parent createContent() throws IOException, ParseException {
        sp_conpuc = new SearchPopover(sb_conpuc, new PageBrowser(), SihicApp.conPuc, false);
        sp_conpuc.setMaxWidth(300);
        sb_conpuc.setMinWidth(300);

        //**********************************************
        tv_detalles = new TableView();
        tv_detalles.setOnKeyPressed(e -> {
            try {
                if (e.getCode().equals(e.getCode().DELETE)) {
                    deletecuenta();
                }
            } catch (Exception ex) {
              
            }
        });
        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(150);
        codigo.setText("Codigo");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsientosModelosTiposDocumentosContables, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AsientosModelosTiposDocumentosContables, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getCodigo()));

            }
        });
        TableColumn ccuenta = new TableColumn();
        ccuenta.setMinWidth(350);
        ccuenta.setText("Cuenta");
        ccuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsientosModelosTiposDocumentosContables, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AsientosModelosTiposDocumentosContables, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getNombre()));

            }
        });
        TableColumn detalles = new TableColumn();
        detalles.setMinWidth(400);
        detalles.setText("Centro de Costo");
        detalles.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsientosModelosTiposDocumentosContables, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AsientosModelosTiposDocumentosContables, String> detalles) {

                return new SimpleStringProperty(detalles.getValue().centrocosto());

            }
        });
        TableColumn cdebito = new TableColumn();
        cdebito.setMinWidth(150);
        cdebito.setText("Tipo Movimiento");
        cdebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsientosModelosTiposDocumentosContables, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AsientosModelosTiposDocumentosContables, String> detalles) {

                return new SimpleStringProperty(detalles.getValue().getTipomovimiento());

            }
        });
        TableColumn ccredito = new TableColumn();
        ccredito.setMinWidth(150);
        ccredito.setText("% Valor Transacción");
        ccredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AsientosModelosTiposDocumentosContables, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AsientosModelosTiposDocumentosContables, String> detalles) {

                return new SimpleStringProperty(detalles.getValue().getPorcentajevalor().toString());

            }
        });
        tv_detalles.getColumns().addAll(codigo, ccuenta, detalles, cdebito, ccredito);
        tv_detalles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tv_detalles.setMaxHeight(200);

        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();

        // porcvalor1.getProperties().put("requerido", true);
        porcvalor2.getProperties().put("requerido", true);
        porcvalor3.getProperties().put("requerido", true);
        sb_conpuc.getProperties().put("requerido", true);
        descripcion.getProperties().put("requerido", true);
        descripcion.setMinWidth(300);
        tipomovimiento.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 1 && (newValue.equals("D") || newValue.equals("d") || newValue.equals("C") || newValue.equals("c"))) {
                agregarcuenta.requestFocus();
                agregarcuenta.setDisable(false);
            } else {
                agregarcuenta.setDisable(true);
            }
        });
        centrocosto.getItems().add(jenum.EnumCentroCostos.COMPRAS0.ordinal(), "COMPRAS");
        centrocosto.getItems().add(jenum.EnumCentroCostos.PRODUCCION1.ordinal(), "PRODUCCION");
        centrocosto.getItems().add(jenum.EnumCentroCostos.VENTAS2.ordinal(), "VENTAS");
        centrocosto.getItems().add(jenum.EnumCentroCostos.ADMINISTRACION3.ordinal(), "ADMINISTRACION");
        centrocosto.getSelectionModel().select(0);
        LoadChoiceBoxGeneral.cb_tiposdocumentoscontables.getSelectionModel().select(0);
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        save = new Button("", imageView);
        save.setTooltip(new Tooltip("Guardar Producto"));
        hb_botones = new HBox(2);

        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            } catch (ParseException ex) {
                Logger.getLogger(FServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        nuevo = new Button("", imageView);
        nuevo.setTooltip(new Tooltip("Nuevo Producto"));
        nuevo.setOnAction(e -> {
            try {
                nuevo();
            } catch (ParseException ex) {
                Logger.getLogger(FModelosTiposDocumentosContables.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        agregarcuenta = new Button("", imageView);
        agregarcuenta.setTooltip(new Tooltip("Agrecar Cuenta"));
        agregarcuenta.setOnAction(e -> {
            try {
                addcuenta();
            } catch (InterruptedException ex) {
                Logger.getLogger(FModelosTiposDocumentosContables.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FModelosTiposDocumentosContables.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);

// gridpane
        gp_generic = new GridPane();
        gp_generic.add(new Label("Tipo Documento Contable:"), 0, 0, 1, 1);
        gp_generic.add(LoadChoiceBoxGeneral.cb_tiposdocumentoscontables, 1, 0, 2, 1);
        gp_generic.add(new Label("Descripción:"), 3, 0, 1, 1);
        gp_generic.add(descripcion, 4, 0, 1, 1);
        gp_generic.add(hb_botones, 5, 0, 1, 1);
        gp_generic.add(new Label("Cuenta"), 0, 1, 1, 1);
      //  gp_generic.add(new Label("Centro de Costo"), 1, 1, 1, 1);
        gp_generic.add(new Label("% Valor"), 1, 1, 1, 1);
        gp_generic.add(new Label("Tipo Movimiento"), 2, 1, 1, 1);

        gp_generic.add(sb_conpuc, 0, 2, 1, 1);
        //gp_generic.add(LoadChoiceBoxGeneral.cb_centrocostos, 1, 2, 1, 1);
        gp_generic.add(porcvalor1, 1, 2, 1, 1);
        gp_generic.add(tipomovimiento, 2, 2, 1, 1);
        gp_generic.add(agregarcuenta, 3, 2, 1, 1);
        gp_generic.add(tv_detalles, 0, 3, 6, 1);
        gp_generic.add(sp_conpuc, 0, 3, 5, 3);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(2);
        gp_generic.setVgap(2);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_LEFT);
        // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        gp_generic.autosize();
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinWidth(gp_generic.getLayoutBounds().getWidth());
        Gp_Message.setMaxHeight(40);
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.setAlignment(Pos.TOP_CENTER);
        //stack.setMinWidth(700);
        stack.getChildren().addAll(gp_generic, Gp_Message);
        crearoeditar();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void nuevo() throws ParseException {
        SihicApp.modeloTiposDocumentosContables = null;
        SihicApp.modeloTiposDocumentosContables = new ModeloTiposDocumentosContables();
        getDetalles();
        empty_field();
    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {

                if (SihicApp.modeloTiposDocumentosContables.getId() == null) {
                    SihicApp.modelosTiposDocumentosContablesControllerClient.create();
                    L_Message.setText("Registro Almacenado");
                } else {
                    SihicApp.modelosTiposDocumentosContablesControllerClient.update();
                    L_Message.setText("Registro modificado");
                }

                getDetalles();
                Task_Message = () -> {
                    try {
                        SetMessage();
                        AdminModelosAsientosContables.getRecords();
                    } catch (InterruptedException ex) {

                    } catch (ParseException ex) {
                        Logger.getLogger(FModelosTiposDocumentosContables.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            } catch (Exception e) {
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
        }

    }

    private void set_datos() {
        SihicApp.modeloTiposDocumentosContables.setTiposDocumentosContables(LoadChoiceBoxGeneral.v_tiposdocumentoscontables.get(LoadChoiceBoxGeneral.cb_tiposdocumentoscontables.getSelectionModel().getSelectedIndex()));
        SihicApp.modeloTiposDocumentosContables.setDescripcion(descripcion.getText());

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getLength() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
                        permitirproceso = false;
                        general.setStyle("-fx-background-color:#ff1600");

                    }
                }

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    if (general.getProperties().get("requerido") != null) {
                        if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                            permitirproceso = false;
                            general.getStylesheets().add(0, "/sihic/personal.css");
                            general.getStylesheets().set(0, "/sihic/personal.css");
                        }
                    }
                }
            }
        }
        FadeTransition ft = new FadeTransition(Duration.millis(2000), save);
        //ft.setFromValue(0.0);
        // ft.setToValue(1);

        ft.play();

        //success.setOpacity(0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Object n : gp_generic.getChildren().toArray()) {
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
                                if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                                    general.getStylesheets().set(0, SihicApp.hojaestilos);

                                }
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
// A change listener to track the change in selected index

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

    }

    public void getDatos() {
        LoadChoiceBoxGeneral.cb_tiposdocumentoscontables.getSelectionModel().select(LoadChoiceBoxGeneral.v_tiposdocumentoscontables.indexOf(SihicApp.modeloTiposDocumentosContables.getTiposDocumentosContables()));
        descripcion.setText(SihicApp.modeloTiposDocumentosContables.getDescripcion());

    }

    public void crearoeditar() throws ParseException {

        if (SihicApp.s_producto != null) {
            getDatos();
            getDetalles();
        } else {
            SihicApp.s_producto = new Producto();
            nuevo();
        }

    }

    private void empty_field() {
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    general.getSelectionModel().select(-1);

                } else {
                    if (n.getClass() == TextArea.class) {
                        TextArea general = (TextArea) n;

                        general.setText("");

                    } else {
                        if (n.getClass() == RadioButton.class) {
                            RadioButton general = (RadioButton) n;

                            general.setSelected(false);

                        } else {
                            if (n.getClass() == CheckBox.class) {
                                CheckBox general = (CheckBox) n;

                                general.setSelected(false);

                            }
                        }
                    }
                }
            }
        }

    }

    public String getcerosizquierda(String codigo) {

        String cerosizq = "";

        for (int i = 4; i > codigo.length(); i--) {
            cerosizq = cerosizq + "0";
        }
        cerosizq = cerosizq + codigo;
        return cerosizq;
    }

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public void addcuenta() throws InterruptedException, ParseException {
        SihicApp.modeloTiposDocumentosContables.addcuenta(SihicApp.conPuc, tipomovimiento.getText(), new BigDecimal(porcvalor1.getText()), new BigDecimal(porcvalor2.getText()), new BigDecimal(porcvalor3.getText()), LoadChoiceBoxGeneral.cb_centrocostos.getSelectionModel().getSelectedIndex());
        save();
    }

    public void deletecuenta() throws InterruptedException, ParseException {
        if (((AsientosModelosTiposDocumentosContables) tv_detalles.getSelectionModel().getSelectedItem()) != null) {
            SihicApp.modeloTiposDocumentosContables.remove((AsientosModelosTiposDocumentosContables) tv_detalles.getSelectionModel().getSelectedItem());
            save();

        }
    }

    private void getDetalles() throws ParseException {
        //colocamos los datos

        try {

            ol_detalles.clear();
            ol_detalles.addAll(SihicApp.modeloTiposDocumentosContables.getAsientosModelosTiposDocumentosContableses().toArray());
            // Ta_KardexProducto.getItems().clear();
            tv_detalles.setItems(ol_detalles);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
