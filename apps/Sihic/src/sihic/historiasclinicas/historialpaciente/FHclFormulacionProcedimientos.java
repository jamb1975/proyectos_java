package sihic.historiasclinicas.historialpaciente;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.HclFormulacionProcedimientos;
import sihic.SihicApp;
import sihic.controlador.HclConsultasControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FHclFormulacionProcedimientos extends Application {

    StackPane stack;
    private HclFormulacionProcedimientos hclFormulacionProcedimientos;
    private GridPane gp_hclformulacionprocedimientos;
    private Label la_cups;
    private TextField cups;
    private Label la_recomendacion;
    private TextArea recomendacion;
    private HclConsultasControllerClient hclconsultasControllerClient;
    private HclHistoriasClinicasControllerClient hclHistoriasClinicasControllerClient;
    private Button save;
    private Button nuevo;
    private Button agregarcups;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private ImageView imageView;
    private BuscarCups buscarCups;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;

    public Parent createContent() throws IOException {
        buscarCups = new BuscarCups();
        hclHistoriasClinicasControllerClient = new HclHistoriasClinicasControllerClient();
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
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        agregarcups = new Button("", imageView);
        //agregarcups.setMaxWidth(130);  
        agregarcups.setOnAction(e
                -> {
            try {

                buscarCups.show();
            } catch (Exception ex) {

            }
        });
        buscarCups.getStage().setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    if (buscarCups.getHclCups() != null) {
                        set_datos_hclformulacionprocedimientos();
                    }
                }
            }
        });
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        hclFormulacionProcedimientos = new HclFormulacionProcedimientos();
        hclconsultasControllerClient = new HclConsultasControllerClient();
        la_cups = new Label("Procedimientos(Cups):");
        cups = new TextField();
        la_recomendacion = new Label("Recomendación:");
        recomendacion = new TextArea();
        recomendacion.setMinHeight(70);
        recomendacion.setMaxHeight(70);
        recomendacion.setMinWidth(500);
        recomendacion.setMaxWidth(500);
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
                Logger.getLogger(FHclFormulacionProcedimientos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

// gridpane
        gp_hclformulacionprocedimientos = new GridPane();
        gp_hclformulacionprocedimientos.add(la_cups, 0, 0);
        gp_hclformulacionprocedimientos.add(cups, 1, 0);

        gp_hclformulacionprocedimientos.add(la_recomendacion, 0, 1);
        gp_hclformulacionprocedimientos.add(recomendacion, 1, 1);
        gp_hclformulacionprocedimientos.add(agregarcups, 2, 0);
        gp_hclformulacionprocedimientos.add(hb_botones, 0, 2, 3, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        // gp_hclformulacionprocedimientos.getStylesheets().add(SihicApp.hojaestilos);
        // gp_hclformulacionprocedimientos.getStyleClass().add("background");
        gp_hclformulacionprocedimientos.setHgap(5);
        gp_hclformulacionprocedimientos.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_hclformulacionprocedimientos.getLayoutBounds().getHeight(), gp_hclformulacionprocedimientos.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);

        stack.getChildren().addAll(gp_hclformulacionprocedimientos, Gp_Message);
        gp_hclformulacionprocedimientos.getStylesheets().add(SihicApp.hojaestilos);
        gp_hclformulacionprocedimientos.getStyleClass().add("background");

        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void nuevo() {
        hclFormulacionProcedimientos = null;
        hclFormulacionProcedimientos = new HclFormulacionProcedimientos();
        cups.setText("");
        recomendacion.setText("");
    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
            set_datos_hclformulacionprocedimientos();
            try {
                hclconsultasControllerClient.saveFormulasProcedimientos(hclFormulacionProcedimientos);
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

    private void buscaccups() {

    }

    private void set_datos_hclformulacionprocedimientos() {
        if (buscarCups.getHclCups() != null) {
            cups.setText(buscarCups.getHclCups().getCodigo() + " " + buscarCups.getHclCups().getDescripcion());
            hclFormulacionProcedimientos = new HclFormulacionProcedimientos();
            hclFormulacionProcedimientos.setHclCups(buscarCups.getHclCups());
            hclFormulacionProcedimientos.setRecomendacion(recomendacion.getText());
            hclFormulacionProcedimientos.setHclFormulacionesMedicas(SihicApp.hclFormulacionesMedicas);
        } //

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_hclformulacionprocedimientos.getChildren().toArray()) {
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
                for (Object n : gp_hclformulacionprocedimientos.getChildren().toArray()) {
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

    public void buscacups() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        buscarCups = null;
        buscarCups = new BuscarCups();
        buscarCups.show();
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
