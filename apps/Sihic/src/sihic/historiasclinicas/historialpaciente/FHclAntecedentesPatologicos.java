package sihic.historiasclinicas.historialpaciente;

import java.io.IOException;
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
import modelo.HclAntecedentesPatologicos;
import modelo.HclTiposAntecedentesGenerales;
import modelo.HclTiposAntecedentesPatologicos;
import sihic.SihicApp;
import sihic.controlador.GenPersonasControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FHclAntecedentesPatologicos extends Application {

    HclHistoriasClinicasControllerClient hclHistoriaClinicasControllerClient;
    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private HclAntecedentesPatologicos hclAntecedentesPatologicos;
    private GridPane gp_hclantecedentespatologicos;
    private ChoiceBox hcltiposantecedentespatologicos_id;
    private Vector<HclTiposAntecedentesPatologicos> v_hcltiposantecedentespatologicos_id = new Vector<HclTiposAntecedentesPatologicos>();
    private Label la_hcltiposantecedentespatologicos_id;
    private Label la_observacion;
    private TextArea observacion;
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
        hclAntecedentesPatologicos = new HclAntecedentesPatologicos();
        hcltiposantecedentespatologicos_id = new ChoiceBox();
        hcltiposantecedentespatologicos_id.getProperties().put("requerido", false);
        la_hcltiposantecedentespatologicos_id = new Label("Tipo: ");
        la_observacion = new Label("Observación: ");
        observacion = new TextArea();
        observacion.setMinHeight(70);
        observacion.setMaxHeight(70);
        observacion.setMinWidth(500);
        observacion.setMaxWidth(500);

        observacion.getProperties().put("requerido", true);

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
        gp_hclantecedentespatologicos = new GridPane();
        gp_hclantecedentespatologicos.add(la_hcltiposantecedentespatologicos_id, 0, 0);
        gp_hclantecedentespatologicos.add(hcltiposantecedentespatologicos_id, 1, 0);
        gp_hclantecedentespatologicos.add(la_observacion, 0, 1);
        gp_hclantecedentespatologicos.add(observacion, 1, 1);

        gp_hclantecedentespatologicos.add(hb_botones, 0, 2, 2, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_hclantecedentespatologicos.getStylesheets().add(SihicApp.hojaestilos);
        gp_hclantecedentespatologicos.getStyleClass().add("background");
        gp_hclantecedentespatologicos.setHgap(5);
        gp_hclantecedentespatologicos.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_hclantecedentespatologicos.getLayoutBounds().getHeight(), gp_hclantecedentespatologicos.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);

        stack.getChildren().addAll(gp_hclantecedentespatologicos, Gp_Message);
        Load_TiposAntecedentesPatologicos();
        return stack;
    }

    public void itemChanged(ObservableValue<? extends String> observable,
            String oldValue,
            String newValue) {
        System.out.println("Itemchanged: old = " + oldValue + ",new = " + newValue);
    }
// A change listener to track the change in selected index

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        System.out.println("Indexchanged: old = " + oldValue + ", new = " + newValue);

//gp_genpersonas.getChildren().remove(gen_municipios_id);
//gp_genpersonas.add(gen_municipios_id, 3, 3);
//gp_genpersonas.getChildren().remove(index-1);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void Load_TiposAntecedentesPatologicos() {

        hcltiposantecedentespatologicos_id.setMaxHeight(10);

        if (SihicApp.l_tiposantecedentespatologicos.size() > 0) {
            hcltiposantecedentespatologicos_id.getItems().clear();
            //      v_gen_municipios.clear();
            for (HclTiposAntecedentesPatologicos tap : SihicApp.l_tiposantecedentespatologicos) {

                v_hcltiposantecedentespatologicos_id.add(tap);
                hcltiposantecedentespatologicos_id.getItems().add(tap.getNombre());

            }

        }

    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {
                hclHistoriaClinicasControllerClient.saveAntecedentesPatologicos(hclAntecedentesPatologicos);
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
        hclAntecedentesPatologicos.setHclTiposAntecedentesPatologicos(v_hcltiposantecedentespatologicos_id.get(hcltiposantecedentespatologicos_id.getSelectionModel().getSelectedIndex()));
        hclAntecedentesPatologicos.setObservacion(observacion.getText());
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_hclantecedentespatologicos.getChildren().toArray()) {
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
                for (Object n : gp_hclantecedentespatologicos.getChildren().toArray()) {
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
