package sihic.historiasclinicas.historialpaciente;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.HclAntecedentesGenerales;
import modelo.HclAntecedentesGineco;
import modelo.HclTiposAntecedentesGenerales;
import sihic.SihicApp;
import sihic.controlador.GenPersonasControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FHclAntecedentesGineco extends Application {

    HclHistoriasClinicasControllerClient hclHistoriaClinicasControllerClient;
    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private HclAntecedentesGineco hclAntecedentesGineco;
    private GridPane gp_hclantecedentesgenerales;
    private Label la_fup;
    private DatePicker fup;
    private Label la_fur;
    private DatePicker fur;
    private Label la_descripcion;
    private TextArea descripcion;
    private Label la_g;
    private TextField g;
    private Label la_p;
    private TextField p;
    private Label la_a;
    private TextField a;
    private Label la_c;
    private TextField c;
    private GenPersonasControllerClient hclantecedentesgenerales_idControllerClient;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;

    public Parent createContent() throws IOException {
        hclHistoriaClinicasControllerClient = new HclHistoriasClinicasControllerClient();
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        hclAntecedentesGineco = new HclAntecedentesGineco();
        hclantecedentesgenerales_idControllerClient = new GenPersonasControllerClient();
        la_fur = new Label("FUR: ");
        fur = new DatePicker();
        fur.getProperties().put("requerido", true);
        la_fup = new Label("FUP: ");
        fup = new DatePicker();
        fup.getProperties().put("requerido", true);
        la_descripcion = new Label("Descripción: ");
        descripcion = new TextArea();
        descripcion.setMinHeight(70);
        descripcion.setMaxHeight(70);
        descripcion.setMinWidth(500);
        descripcion.setMaxWidth(500);
        descripcion.getProperties().put("requerido", true);
        la_g = new Label("G: ");
        g = new TextField();
        g.getProperties().put("requerido", true);
        la_p = new Label("P: ");
        p = new TextField();
        p.getProperties().put("requerido", true);

        la_a = new Label("A: ");
        a = new TextField();
        a.getProperties().put("requerido", true);

        la_c = new Label("C: ");
        c = new TextField();
        c.getProperties().put("requerido", true);

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
        hb_botones.getChildren().addAll(save, nuevo);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
// gridpane
        gp_hclantecedentesgenerales = new GridPane();
        gp_hclantecedentesgenerales.add(la_fur, 0, 0);
        gp_hclantecedentesgenerales.add(fur, 1, 0);
        gp_hclantecedentesgenerales.add(la_fup, 0, 1);
        gp_hclantecedentesgenerales.add(fup, 1, 1);
        gp_hclantecedentesgenerales.add(la_descripcion, 0, 2);
        gp_hclantecedentesgenerales.add(descripcion, 1, 2);
        gp_hclantecedentesgenerales.add(la_g, 0, 3);
        gp_hclantecedentesgenerales.add(g, 1, 3);
        gp_hclantecedentesgenerales.add(la_p, 0, 4);
        gp_hclantecedentesgenerales.add(p, 1, 4);
        gp_hclantecedentesgenerales.add(la_a, 0, 5);
        gp_hclantecedentesgenerales.add(a, 1, 5);
        gp_hclantecedentesgenerales.add(la_c, 0, 6);
        gp_hclantecedentesgenerales.add(c, 1, 6);

        gp_hclantecedentesgenerales.add(hb_botones, 0, 8, 2, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_hclantecedentesgenerales.getStylesheets().add(SihicApp.hojaestilos);
        gp_hclantecedentesgenerales.getStyleClass().add("background");
        gp_hclantecedentesgenerales.setHgap(5);
        gp_hclantecedentesgenerales.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_hclantecedentesgenerales.getLayoutBounds().getHeight(), gp_hclantecedentesgenerales.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);

        stack.getChildren().addAll(gp_hclantecedentesgenerales, Gp_Message);
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
            set_datos();
            try {
                hclHistoriaClinicasControllerClient.saveAntecedentesGineco(hclAntecedentesGineco);
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

    private void set_datos() {

        //colocar fecha de datepicker
        LocalDate localDate = fur.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        hclAntecedentesGineco.setFUR(Date.from(instant));
        localDate = fup.getValue();
        instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        hclAntecedentesGineco.setFUP(Date.from(instant));
        hclAntecedentesGineco.setA(a.getText());
        hclAntecedentesGineco.setC(c.getText());
        hclAntecedentesGineco.setG(g.getText());
        hclAntecedentesGineco.setP(p.getText());
        hclAntecedentesGineco.setDescripcion(descripcion.getText());
        hclAntecedentesGineco.setHclConsultas(SihicApp.hclconsultas);
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_hclantecedentesgenerales.getChildren().toArray()) {
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
                for (Object n : gp_hclantecedentesgenerales.getChildren().toArray()) {
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
                                System.out.println("propiedad-->" + general.getProperties().get("requerido"));
                                if (((boolean) general.getProperties().get("requerido") == true)) {
                                    if (general.getSelectionModel().getSelectedIndex() < 0) {
                                        general.getStylesheets().set(0, SihicApp.hojaestilos);

                                    }
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

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
