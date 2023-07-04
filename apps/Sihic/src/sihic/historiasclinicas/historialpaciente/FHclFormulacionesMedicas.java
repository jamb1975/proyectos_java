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
import modelo.HclFormulacionesMedicas;
import modelo.HclTiposFormulas;
import sihic.SihicApp;
import sihic.controlador.HclConsultasControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FHclFormulacionesMedicas extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private HclFormulacionesMedicas hclFormulacionesMedicas;
    private GridPane gp_hclformulacionesmedicas;
    private Label la_tiposformula;
    private ChoiceBox hcltiposformula_id;
    private Vector<HclTiposFormulas> v_hcltiposformula = new Vector<HclTiposFormulas>();
    private Label la_ordenmedica;
    private TextArea ordenmedica;
    private Label la_ordenterapeutica;
    private TextArea ordenterapeutica;
    private Label la_fechaformulacion;
    private DatePicker fechaformulacion;
    private HclConsultasControllerClient hclconsultasControllerClient;
    private HclHistoriasClinicasControllerClient hclHistoriasClinicasControllerClient;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;

    private ImageView imageView;
    private ObservableList ol_hclformulacionesmedicas = FXCollections.observableArrayList();
    ;
    private List<HclFormulacionesMedicas> l_hclformulacionesmedicas;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;

    public Parent createContent() throws IOException {

        hclHistoriasClinicasControllerClient = new HclHistoriasClinicasControllerClient();

        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        nuevo = new Button("Nuevo", imageView);

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
        hclFormulacionesMedicas = new HclFormulacionesMedicas();
        hclconsultasControllerClient = new HclConsultasControllerClient();
        hcltiposformula_id = new ChoiceBox();
        la_ordenterapeutica = new Label("Orden Terapeutica:");
        la_ordenmedica = new Label("Orden Médica:");
        la_fechaformulacion = new Label("Fecha formulación:");
        la_tiposformula = new Label("Tipo de formula:");
        ordenterapeutica = new TextArea();
        ordenterapeutica.getProperties().put("requerido", true);
        ordenterapeutica.setMinHeight(70);
        ordenterapeutica.setMaxHeight(70);
        ordenmedica = new TextArea();
        ordenmedica.setMinHeight(70);
        ordenmedica.setMaxHeight(70);
        ordenmedica.getProperties().put("requerido", true);

        fechaformulacion = new DatePicker(LocalDate.now());

        ImageView imageView = new ImageView("/images/save.png");
        save = new Button("", imageView);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);
        save.setOnAction(e -> {
            try {
                try {
                    save();
                } catch (ParseException ex) {
                    Logger.getLogger(FHclFormulacionesMedicas.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InterruptedException ex) {

            }
        });

        // gridpane
        gp_hclformulacionesmedicas = new GridPane();
        gp_hclformulacionesmedicas.add(la_fechaformulacion, 0, 0);
        gp_hclformulacionesmedicas.add(fechaformulacion, 1, 0);
        gp_hclformulacionesmedicas.add(la_tiposformula, 0, 1);
        gp_hclformulacionesmedicas.add(hcltiposformula_id, 1, 1);
        gp_hclformulacionesmedicas.add(la_ordenmedica, 0, 2);
        gp_hclformulacionesmedicas.add(ordenmedica, 1, 2);
        gp_hclformulacionesmedicas.add(la_ordenterapeutica, 0, 3);
        gp_hclformulacionesmedicas.add(ordenterapeutica, 1, 3);
        gp_hclformulacionesmedicas.add(hb_botones, 0, 5, 2, 1);

        GridPane.setHalignment(hb_botones, HPos.CENTER);
        //gp_hclformulacionesmedicas.getStylesheets().add(SihicApp.hojaestilos);
        //gp_hclformulacionesmedicas.getStyleClass().add("background");
        gp_hclformulacionesmedicas.setHgap(5);
        gp_hclformulacionesmedicas.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_hclformulacionesmedicas.getStylesheets().add(SihicApp.hojaestilos);
        gp_hclformulacionesmedicas.getStyleClass().add("background");
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_hclformulacionesmedicas.getLayoutBounds().getHeight(), gp_hclformulacionesmedicas.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);

        stack.getChildren().addAll(gp_hclformulacionesmedicas, Gp_Message);
        Load_ChoiceBox();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void Load_ChoiceBox() {

        if (SihicApp.l_hclfinalidad.size() > 0) {
            hcltiposformula_id.getItems().clear();
            v_hcltiposformula.clear();
            for (HclTiposFormulas tf : SihicApp.l_tiposformulas) {
                v_hcltiposformula.add(tf);
                hcltiposformula_id.getItems().add(tf.getDescripcion());
            }
        }

    }

    public void nuevo() {
        hclFormulacionesMedicas = null;
        hclFormulacionesMedicas = new HclFormulacionesMedicas();
    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
            set_datos_hclformulacionesmedicas();
            try {
                hclconsultasControllerClient.saveFormulasMedicas(hclFormulacionesMedicas);
                SihicApp.hclFormulacionesMedicas = null;
                SihicApp.hclFormulacionesMedicas = new HclFormulacionesMedicas();
                SihicApp.hclFormulacionesMedicas = hclFormulacionesMedicas;
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

        hclFormulacionesMedicas.setOrdenmedica(ordenmedica.getText());
        hclFormulacionesMedicas.setOrdenterapeutica(ordenterapeutica.getText());
        hclFormulacionesMedicas.setHclConsultas(SihicApp.hclconsultas);
        hclFormulacionesMedicas.setFechaformulacion(new Date());
        //colocar finalidad
        hclFormulacionesMedicas.setHclTiposFormulas(v_hcltiposformula.get(hcltiposformula_id.getSelectionModel().getSelectedIndex()));
        //colocar causa externa

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_hclformulacionesmedicas.getChildren().toArray()) {
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
                for (Object n : gp_hclformulacionesmedicas.getChildren().toArray()) {
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
