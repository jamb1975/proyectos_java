package sihic.historiasclinicas.consultas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.AgnEspecialidades;
import sihic.SihicApp;
import sihic.controlador.HclHistoriasClinicasControllerClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FAgnEspecialidades extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;

    private AgnEspecialidades agnEspecialidades;
    private GridPane gp_generic;
    private Label la_codigo;
    private Label la_nombre;

    private TextField codigo;
    private TextField nombre;
    private HclHistoriasClinicasControllerClient agnespecialidadesControllerClient;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private ImageView logo;
    private Button bu_logo;
    private Image loadlogo;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private File fileLogo;
    private FileChooser fileChooser = new FileChooser();
    private WritableImage wi;
    private BufferedImage bi;

    public Parent createContent() throws IOException {
        agnespecialidadesControllerClient = new HclHistoriasClinicasControllerClient();
        agnEspecialidades = new AgnEspecialidades();
        logo = new ImageView();
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        la_codigo = new Label("Código:");
        la_nombre = new Label("Nombre:");
        codigo = new TextField();
        nombre = new TextField();
        codigo.getProperties().put("requerido", true);
        nombre.getProperties().put("requerido", true);
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        save = new Button("", imageView);
        hb_botones = new HBox(5);

        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        save.setOnKeyPressed(e -> {

            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();
                    nuevo.requestFocus();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FAgnEspecialidades.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        nuevo = new Button("", imageView);
        nuevo.setOnKeyPressed(
                e -> {

                    if (e.getCode().equals(e.getCode().ENTER)) {
                        nuevahora();
                        nombre.requestFocus();
                    }

                });

        nuevo.setOnAction(e -> {
            nuevahora();
            nombre.requestFocus();

        });
        nombre.setOnAction(e -> {
            codigo.requestFocus();

        });
        codigo.setOnAction(e -> {
            save.requestFocus();
        });
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);
        gp_generic = new GridPane();
        gp_generic.add(la_nombre, 0, 1);
        gp_generic.add(nombre, 1, 1);
        gp_generic.add(la_codigo, 0, 0);
        gp_generic.add(codigo, 1, 0);
        gp_generic.add(hb_botones, 0, 10, 3, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_generic.getLayoutBounds().getHeight(), gp_generic.getLayoutBounds().getWidth());
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
        stack.getChildren().addAll(gp_generic, Gp_Message);
        crearoeditar();
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
            set_datos_oranizacion();
            try {
                if (agnEspecialidades.getId() == null) {
                    SihicApp.agnEspecialidadesControllerClient.create(agnEspecialidades);
                    L_Message.setText("Registro Almacenado");
                } else {
                    SihicApp.agnEspecialidadesControllerClient.update(agnEspecialidades);
                    L_Message.setText("Registro modificado");
                }
                AdminAgnEspecialidades.getListEspecialidades();
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
                e.printStackTrace();
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

    private void set_datos_oranizacion() {
        agnEspecialidades.setNombre(nombre.getText());
        agnEspecialidades.setCodigo(codigo.getText());
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_generic.getChildren().toArray()) {
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

                    if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
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

                            if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
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

    public void crearoeditar() {
        System.out.println("agnespecialidades->" + AdminAgnEspecialidades.getAgnespecialidades());
        agnEspecialidades = AdminAgnEspecialidades.getAgnespecialidades();
        if (agnEspecialidades.getId() != null) {
            nombre.setText(agnEspecialidades.getNombre());
            codigo.setText(String.valueOf(agnEspecialidades.getCodigo()));

        }

    }

    public void nuevahora() {
        agnEspecialidades = null;
        agnEspecialidades = new AgnEspecialidades();
        nombre.setText("");
        codigo.setText("");

    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
