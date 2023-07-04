package sihic.contabilidad;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.Factura;
import modelo.GenEapb;
import modelo.GenPacientes;
import modelo.GenPersonas;
import modelo.GenTiposUsuarios;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.controlador.FacturaControllerClient;
import sihic.controlador.GenEapbControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.UtilDate;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FFactura extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GenPersonas genPersonas;
    private GenPacientes genPacientes;
    private GridPane gp_genpersonas;
    private SearchBox sb_genmunicipios = new SearchBox();

    private FacturaControllerClient facturaControllerClient;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;

    private Button nuevaProfesion;

    private Label la_eapb;
    private Label la_tipoafiliacion;
    private Label la_tipousuario;
    private Label la_nivelusuario;
    private ChoiceBox eapb;
    private ChoiceBox prefijo;
    private DatePicker fechaelaboracion;
    private DatePicker fechaexpiracion;
    private Label nofactura;
    private Vector<GenTiposUsuarios> v_tiposusuarios = new Vector<GenTiposUsuarios>();
    private ChoiceBox tipousuario;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private GenEapb genEapb;
    private SearchPopover sp_geneapb;
    private SearchBox sb_eapb = new SearchBox();
    private SearchBox sb_genprofesiones = new SearchBox();
    private SearchPopover sp_genprofesiones;
    GenEapbControllerClient genEapbControllerClient;

    public Parent createContent() throws IOException, ParseException {
        facturaControllerClient = new FacturaControllerClient();
        sp_geneapb = new SearchPopover(sb_eapb, new PageBrowser(), SihicApp.s_genEapb, true);
        //  sb_eapb.getProperties().put("requerido", true);
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        fechaelaboracion = new DatePicker(UtilDate.formatoyearmesdia(UtilDate.fechainiciomes2(new Date())));
        fechaexpiracion = new DatePicker(UtilDate.formatoyearmesdia(UtilDate.fechafinmes2(new Date())));
        prefijo = new ChoiceBox();
        prefijo.getItems().addAll("A", "E", "T", "P");
        la_eapb = new Label("Empresa adm. planes beneficio: ");
        la_tipousuario = new Label("Tipo usuario: ");
        eapb = new ChoiceBox();
        eapb.setMaxWidth(300);

        tipousuario = new ChoiceBox();
        tipousuario.setMaxWidth(300);
        tipousuario.getProperties().put("requerido", true);
        sb_eapb.setMaxWidth(300);
        sp_geneapb.setMaxHeight(20);
        sb_eapb.getProperties().put("requerido", true);
        nofactura = new Label("");
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        save = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        nuevo = new Button("", imageView);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });

// gridpane  sp_geneapb = new SearchPopover(sb_eapb,new PageBrowser());
        gp_genpersonas = new GridPane();
        gp_genpersonas.add(new Label("Fecha de Elaboración:"), 0, 0);
        gp_genpersonas.add(nofactura, 2, 0);
        gp_genpersonas.add(fechaelaboracion, 1, 0);
        gp_genpersonas.add(new Label("Fecha de Expiración:"), 0, 1);
        gp_genpersonas.add(fechaexpiracion, 1, 1);
        gp_genpersonas.add(new Label("Prefijo:"), 0, 2);
        gp_genpersonas.add(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal, 1, 2);
        gp_genpersonas.add(la_tipousuario, 0, 3);
        gp_genpersonas.add(tipousuario, 1, 3);
        gp_genpersonas.add(la_eapb, 0, 4);
        gp_genpersonas.add(sb_eapb, 1, 4);
        gp_genpersonas.add(hb_botones, 0, 5, 2, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_genpersonas.getStylesheets().add(SihicApp.hojaestilos);
        gp_genpersonas.getStyleClass().add("background");
        gp_genpersonas.setHgap(5);
        gp_genpersonas.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_genpersonas.alignmentProperty().setValue(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_genpersonas.getLayoutBounds().getHeight(), gp_genpersonas.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.getChildren().addAll(gp_genpersonas, Gp_Message);
        event_inputs();
        Load_ChoiceBox();

        gp_genpersonas.add(sp_geneapb, 1, 1, 2, 5);

        editar();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            try {
                set_datos();
                if (facturaControllerClient.validarfacturatotalcero(SihicApp.s_factura.getPrefijo()) <= 0 || SihicApp.s_factura.getId() != null) {
                    if (SihicApp.s_factura.getId() == null) {
                        facturaControllerClient.crearFactura(SihicApp.s_factura);

                    } else {
                        facturaControllerClient.guardarFactura(SihicApp.s_factura);
                    }
                    nofactura.setText("N° Factura: " + SihicApp.s_factura.getNofacturacerosizquierda());
                    AdminFacturas.getRecords();
                    L_Message.setText("Registro Almacenado");
                    Task_Message = () -> {
                        try {
                            SetMessage();
                        } catch (InterruptedException ex) {

                        }
                    };
                } else {
                    L_Message.setText("Hay alguna Factura con Valor 0 con Prefijo " + SihicApp.s_factura.getPrefijo() + " El Sistema No deja Crear Otra Factura");
                    Task_Message = () -> {
                        try {
                            SetMessage();
                        } catch (InterruptedException ex) {

                        }
                    };
                }
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
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

        }
    }

    private void set_datos() throws ParseException {

        SihicApp.s_factura.setFacturaDate(UtilDate.localdatetodate(fechaelaboracion.getValue()));
        SihicApp.s_factura.setFechavencimiento(UtilDate.localdatetodate(fechaexpiracion.getValue()));
        if (SihicApp.s_factura.getId() == null) {
            SihicApp.s_factura.setPrefijo(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString());

        } else {
            LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.setValue(SihicApp.s_factura.getPrefijo());
        }
        //colocar profesiones
        if (SihicApp.s_genEapb.getId() != null) {
            SihicApp.s_factura.setGenEapb(SihicApp.s_genEapb);
        } else {
            SihicApp.s_factura.setGenEapb(null);
        }
        if (tipousuario.getSelectionModel().getSelectedIndex() >= 0) {
            SihicApp.s_factura.setGenTiposUsuarios(v_tiposusuarios.get(tipousuario.getSelectionModel().getSelectedIndex()));
        }

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_genpersonas.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true) && LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0) {
                        permitirproceso = false;
                        general.setStyle("-fx-background-color:#ff1600");

                    }
                }

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    if (general.getProperties().get("requerido") != null) {
                        if (general.getSelectionModel().getSelectedIndex() < 0 && ((boolean) general.getProperties().get("requerido") == true) && (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0 || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == -1)) {
                            permitirproceso = false;
                            general.getStylesheets().add(0, "/sihic/personal.css");
                            general.getStylesheets().set(0, "/sihic/personal.css");
                        }
                    }

                } else {
                    if (n.getClass() == SearchBox.class) {

                        SearchBox general = (SearchBox) n;
                        if (general.getProperties().get("requerido") != null) {
                            if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true) && (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0 || prefijo.getSelectionModel().getSelectedIndex() == -1)) {
                                permitirproceso = false;
                                general.setStyle("-fx-background-color:#ff1600");
                            }
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
                for (Object n : gp_genpersonas.getChildren().toArray()) {
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
                                if (general.getSelectionModel().getSelectedIndex() < 0 && ((boolean) general.getProperties().get("requerido") == true) && (prefijo.getSelectionModel().getSelectedIndex() == 0 || prefijo.getSelectionModel().getSelectedIndex() == -1)) {
                                    general.getStylesheets().set(0, SihicApp.hojaestilos);

                                }
                            }

                        } else {
                            if (n.getClass() == SearchBox.class) {

                                SearchBox general = (SearchBox) n;
                                if (general.getProperties().get("requerido") != null) {
                                    if (((boolean) general.getProperties().get("requerido") == true) && (prefijo.getSelectionModel().getSelectedIndex() == 0 || prefijo.getSelectionModel().getSelectedIndex() == -1)) {

                                        general.setStyle(null);
                                        general.getStyleClass().add("text-field");
                                    }

                                }
                            }
                        }
                    }
                }
            }
        });

    }

    public void Load_ChoiceBox() {

        //codigos consultas
        if (SihicApp.l_tiposusuarios.size() > 0) {
            tipousuario.getItems().clear();
            v_tiposusuarios.clear();
            for (GenTiposUsuarios cc : SihicApp.l_tiposusuarios) {
                v_tiposusuarios.add(cc);
                tipousuario.getItems().add(cc.getNombre());
            }
        }

        //codigos consultas
    }

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }

    private void event_inputs() {

    }

    private void nuevo() throws ParseException {
        SihicApp.s_factura = null;
        SihicApp.s_factura = new Factura();
        for (Object n : gp_genpersonas.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");
                general.setDisable(false);
            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    general.getSelectionModel().select(-1);
                    general.setDisable(false);
                }

                if (n.getClass() == DatePicker.class) {
                    fechaelaboracion = new DatePicker(UtilDate.formatoyearmesdia(UtilDate.fechainiciomes2(new Date())));
                    fechaexpiracion = new DatePicker(UtilDate.formatoyearmesdia(UtilDate.fechafinmes2(new Date())));

                }
                if (n.getClass() == SearchBox.class) {
                    SearchBox general = (SearchBox) n;
                    general.setDisable(false);
                    general.setText("");
                }
            }

        }

    }

    public void disabled_controles() {
        for (Object n : gp_genpersonas.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setDisable(true);
            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    general.setDisable(true);
                }

                if (n.getClass() == DatePicker.class) {
                    DatePicker general = (DatePicker) n;
                    general.setDisable(true);
                }
                if (n.getClass() == SearchBox.class) {
                    SearchBox general = (SearchBox) n;
                    general.setDisable(true);
                }
            }

        }
    }

    public void editar() throws ParseException {
        if (SihicApp.s_factura != null) {
            if (SihicApp.s_factura.getGenEapb() != null) {
                sb_eapb.setText(SihicApp.s_factura.getGenEapb().getCodigo() + " " + SihicApp.s_factura.getGenEapb().getNombre());
            }

            tipousuario.getSelectionModel().select(v_tiposusuarios.indexOf(SihicApp.s_factura.getGenTiposUsuarios()));
            prefijo.getSelectionModel().select(SihicApp.s_factura.getPrefijo());
            fechaelaboracion.setValue(UtilDate.formatoyearmesdia(SihicApp.s_factura.getFacturaDate()));
            fechaexpiracion.setValue(UtilDate.formatoyearmesdia(SihicApp.s_factura.getFechavencimiento()));
            nofactura.setText(SihicApp.s_factura.getNofacturacerosizquierda());
        } else {
            nuevo();

        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
