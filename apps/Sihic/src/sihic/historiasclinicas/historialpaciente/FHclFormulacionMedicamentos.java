package sihic.historiasclinicas.historialpaciente;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Vector;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.HclCausaExterna;
import modelo.HclCodigosConsultas;
import modelo.HclConsultas;
import modelo.HclDiagnostico;
import modelo.HclDiagnosticosRelacionados;
import modelo.HclFinalidad;
import modelo.HclFormulacionMedicamentos;
import modelo.HclFormulacionesMedicas;
import modelo.HclTiposFormulas;
import modelo.MedPresentacionMedicamentos;
import modelo.MedUnidadDosis;
import modelo.MedUnidadFrecuencia;
import modelo.MedUnidadTratamiento;
import modelo.MedViasAdministracion;
import sihic.SihicApp;
import sihic.controlador.HclConsultasControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FHclFormulacionMedicamentos extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private HclFormulacionesMedicas hclFormulacionesMedicas;
    private HclFormulacionMedicamentos hclFormulacionMedicamentos;
    private GridPane gp_hclformulacionmedicamentos;
    private Label la_codigomedicamento;
    private TextField codigomedicamento;
    private Label la_presentacionmedicamento;
    private ChoiceBox medpresentacionesmedicamentos;
    private Vector<MedPresentacionMedicamentos> v_medpresentacionesmedicamentos = new Vector<MedPresentacionMedicamentos>();
    private Label la_viasadministracion;
    private ChoiceBox viasadministracion;
    private Vector<MedViasAdministracion> v_medViasAdministracion = new Vector<MedViasAdministracion>();
    private Label la_unidaddosis;
    private ChoiceBox unidaddosis;
    private Vector<MedUnidadDosis> v_unidaddosis = new Vector<MedUnidadDosis>();
    private Label la_unidadfrecuencia;
    private ChoiceBox unidadfrecuencia;
    private Vector<MedUnidadFrecuencia> v_unidadfrecuencia = new Vector<MedUnidadFrecuencia>();
    private Label la_unidadtratamiento;
    private ChoiceBox unidadtratamiento;
    private Vector<MedUnidadTratamiento> v_unidadtratamiento = new Vector<MedUnidadTratamiento>();
    private Label la_frecuencia;
    private TextField frecuencia;
    private Label la_tiempotratamiento;
    private TextField tiempotratamiento;
    private Label la_dosis;
    private TextField dosis;
    private Label la_cantidad;
    private TextField cantidad;
    private Label la_medicamento;
    private TextField medicamento;
    private Label la_justificacion;
    private TextArea justificacion;
    private Label la_recomendacion;
    private TextArea recomendacion;
    private HclConsultasControllerClient hclconsultasControllerClient;
    private HclHistoriasClinicasControllerClient hclHistoriasClinicasControllerClient;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private Button nuevodr;
    private ImageView imageView;
    private BuscarCie10 buscarCie10;
    private ObservableList ol_hclformulacionesmedicas = FXCollections.observableArrayList();
    ;
    private List<HclFormulacionesMedicas> l_hclformulacionesmedicas;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;

    public Parent createContent() throws IOException {
        buscarCie10 = new BuscarCie10();
        hclHistoriasClinicasControllerClient = new HclHistoriasClinicasControllerClient();
        hclFormulacionMedicamentos = new HclFormulacionMedicamentos();

        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        nuevo = new Button("", imageView);

        nuevo.setOnAction(e
                -> {
            try {

                nuevo();
            } catch (Exception ex) {

            }
        });

        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();

        hclconsultasControllerClient = new HclConsultasControllerClient();
        medpresentacionesmedicamentos = new ChoiceBox();
        viasadministracion = new ChoiceBox();
        la_viasadministracion = new Label("Vía de administración:");
        la_cantidad = new Label("Cantidad:");
        la_medicamento = new Label("Medicamento:");
        medicamento = new TextField();
        la_dosis = new Label("Dosis:");
        dosis = new TextField();
        la_presentacionmedicamento = new Label("Presentación:");
        la_frecuencia = new Label("Frecuencia:");
        frecuencia = new TextField();
        la_tiempotratamiento = new Label("Tiempo Tratamiento:");
        la_unidaddosis = new Label("Unidad Dosis:");
        la_unidadfrecuencia = new Label("Unidad Frecuencia:");
        la_unidadtratamiento = new Label("Unidad tiempo tratamiento:");
        la_codigomedicamento = new Label("Codigo Medicamento:");
        la_recomendacion = new Label("Recomendación:");
        la_justificacion = new Label("Justificación:");
        la_medicamento = new Label("Medicamento:");
        tiempotratamiento = new TextField();
        recomendacion = new TextArea();
        codigomedicamento = new TextField();
        cantidad = new TextField();
        recomendacion.getProperties().put("requerido", false);
        recomendacion.setMinHeight(70);
        recomendacion.setMaxHeight(70);
        recomendacion.setMaxWidth(300);
        recomendacion.setMinWidth(300);
        justificacion = new TextArea();
        justificacion.setMinHeight(70);
        justificacion.setMaxHeight(70);
        justificacion.setMaxWidth(300);
        justificacion.setMinWidth(300);
        justificacion.getProperties().put("requerido", false);

        unidaddosis = new ChoiceBox();
        unidadfrecuencia = new ChoiceBox();
        unidadtratamiento = new ChoiceBox();

        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        save = new Button("", imageView);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            } catch (ParseException ex) {
                Logger.getLogger(FHclFormulacionMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

// gridpane
        gp_hclformulacionmedicamentos = new GridPane();
        gp_hclformulacionmedicamentos.add(la_cantidad, 0, 0);
        gp_hclformulacionmedicamentos.add(la_medicamento, 1, 0);
        gp_hclformulacionmedicamentos.add(cantidad, 0, 1);
        gp_hclformulacionmedicamentos.add(medicamento, 1, 1);
        gp_hclformulacionmedicamentos.add(la_presentacionmedicamento, 0, 2);
        gp_hclformulacionmedicamentos.add(la_viasadministracion, 1, 2);
        gp_hclformulacionmedicamentos.add(medpresentacionesmedicamentos, 0, 3);
        gp_hclformulacionmedicamentos.add(viasadministracion, 1, 3);
        gp_hclformulacionmedicamentos.add(la_dosis, 0, 4);
        gp_hclformulacionmedicamentos.add(la_unidaddosis, 1, 4);
        gp_hclformulacionmedicamentos.add(dosis, 0, 5);
        gp_hclformulacionmedicamentos.add(unidaddosis, 1, 5);
        gp_hclformulacionmedicamentos.add(la_frecuencia, 0, 6);
        gp_hclformulacionmedicamentos.add(la_unidadfrecuencia, 1, 6);
        gp_hclformulacionmedicamentos.add(frecuencia, 0, 7);
        gp_hclformulacionmedicamentos.add(unidadfrecuencia, 1, 7);
        gp_hclformulacionmedicamentos.add(la_tiempotratamiento, 0, 8);
        gp_hclformulacionmedicamentos.add(la_unidadtratamiento, 1, 8);
        gp_hclformulacionmedicamentos.add(tiempotratamiento, 0, 9);
        gp_hclformulacionmedicamentos.add(unidadtratamiento, 1, 9);
        gp_hclformulacionmedicamentos.add(la_recomendacion, 0, 10);
        gp_hclformulacionmedicamentos.add(la_justificacion, 1, 10);
        gp_hclformulacionmedicamentos.add(recomendacion, 0, 11);
        gp_hclformulacionmedicamentos.add(justificacion, 1, 11);

        gp_hclformulacionmedicamentos.add(hb_botones, 0, 13, 2, 1);

        GridPane.setHalignment(hb_botones, HPos.CENTER);
        // gp_hclformulacionmedicamentos.getStylesheets().add(SihicApp.hojaestilos);
        // gp_hclformulacionmedicamentos.getStyleClass().add("background");
        gp_hclformulacionmedicamentos.setHgap(5);
        gp_hclformulacionmedicamentos.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_hclformulacionmedicamentos.getStylesheets().add(SihicApp.hojaestilos);
        gp_hclformulacionmedicamentos.getStyleClass().add("background");
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_hclformulacionmedicamentos.getLayoutBounds().getHeight(), gp_hclformulacionmedicamentos.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);

        stack.getChildren().addAll(gp_hclformulacionmedicamentos, Gp_Message);
        Load_ChoiceBox();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void Load_ChoiceBox() {

        if (SihicApp.l_medpresentacionmedicamentos.size() > 0) {
            medpresentacionesmedicamentos.getItems().clear();
            v_medpresentacionesmedicamentos.clear();
            for (MedPresentacionMedicamentos pm : SihicApp.l_medpresentacionmedicamentos) {
                v_medpresentacionesmedicamentos.add(pm);
                medpresentacionesmedicamentos.getItems().add(pm.getDescripcion());
            }
        }
        if (SihicApp.l_viaadministracion.size() > 0) {
            viasadministracion.getItems().clear();
            v_medViasAdministracion.clear();
            for (MedViasAdministracion va : SihicApp.l_viaadministracion) {
                v_medViasAdministracion.add(va);
                viasadministracion.getItems().add(va.getDescripcion());
            }
        }
        if (SihicApp.l_unidaddosis.size() > 0) {
            unidaddosis.getItems().clear();
            v_unidaddosis.clear();
            for (MedUnidadDosis ud : SihicApp.l_unidaddosis) {
                v_unidaddosis.add(ud);
                unidaddosis.getItems().add(ud.getDescripcion());
            }
        }
        if (SihicApp.l_unidadfrecuencia.size() > 0) {
            unidadfrecuencia.getItems().clear();
            v_unidadfrecuencia.clear();
            for (MedUnidadFrecuencia uf : SihicApp.l_unidadfrecuencia) {
                v_unidadfrecuencia.add(uf);
                unidadfrecuencia.getItems().add(uf.getDescripcion());
            }
        }
        if (SihicApp.l_unidadtratamiento.size() > 0) {
            unidadtratamiento.getItems().clear();
            v_unidadtratamiento.clear();
            for (MedUnidadTratamiento ut : SihicApp.l_unidadtratamiento) {
                v_unidadtratamiento.add(ut);
                unidadtratamiento.getItems().add(ut.getDescripcion());
            }
        }

    }

    public void nuevo() {
        hclFormulacionMedicamentos = null;
        hclFormulacionMedicamentos = new HclFormulacionMedicamentos();
    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
            set_datos_hclformulacionesmedicas();
            try {
                hclconsultasControllerClient.saveFormulasMedicaMentos(hclFormulacionMedicamentos);
                FormulacionesMedicasTab.refreshTable();
                L_Message.setText("Registro Almacenado");
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

    private void set_datos_hclformulacionesmedicas() {
        int i = 0;

        hclFormulacionMedicamentos.setCantidad(Integer.valueOf(cantidad.getText()));
        hclFormulacionMedicamentos.setCodigomedicamento(codigomedicamento.getText());
        hclFormulacionMedicamentos.setRecomendacion(recomendacion.getText());
        hclFormulacionMedicamentos.setJustificacion(justificacion.getText());
        hclFormulacionMedicamentos.setDosis(Float.valueOf(dosis.getText()));
        hclFormulacionMedicamentos.setFrecuencia(Integer.valueOf(frecuencia.getText()));
        hclFormulacionMedicamentos.setTiempotratamiento(Integer.valueOf(tiempotratamiento.getText()));
        hclFormulacionMedicamentos.setMedicamento(medicamento.getText());
        hclFormulacionMedicamentos.setMedPresentacionMedicamentos(v_medpresentacionesmedicamentos.get(medpresentacionesmedicamentos.getSelectionModel().getSelectedIndex()));
        hclFormulacionMedicamentos.setMedViasAdministracion(v_medViasAdministracion.get(viasadministracion.getSelectionModel().getSelectedIndex()));
        hclFormulacionMedicamentos.setMedUnidadDosis(v_unidaddosis.get(unidaddosis.getSelectionModel().getSelectedIndex()));
        hclFormulacionMedicamentos.setMedUnidadFrecuencia(v_unidadfrecuencia.get(unidadfrecuencia.getSelectionModel().getSelectedIndex()));
        hclFormulacionMedicamentos.setMedUnidadTratamiento(v_unidadtratamiento.get(unidadtratamiento.getSelectionModel().getSelectedIndex()));
        hclFormulacionMedicamentos.setHclFormulacionesMedicas(SihicApp.hclFormulacionesMedicas);
//colocar causa externa

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_hclformulacionmedicamentos.getChildren().toArray()) {
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

                    if (general.getSelectionModel().getSelectedIndex() < 0) {
                        permitirproceso = false;
                        general.getStylesheets().add(0, "/sihic/personal.css");
                        general.getStylesheets().set(0, "/sihic/personal.css");
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
                for (Object n : gp_hclformulacionmedicamentos.getChildren().toArray()) {
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

                            if (general.getSelectionModel().getSelectedIndex() < 0) {
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

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
