package administracion;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import modelo.Solucion;
import fxnomina.FXNomina;
import controlador.SolucionControllerClient;

public class FSoluciones extends Application {

    StackPane stack;
    private Solucion solucion;
    private GridPane gp_generic;
    private Label la_nombre;
    private Label la_codigo;
    private Label la_descripcion;
    private Label la_numeral;
    private TextField nombre;
    private TextField descripcion;
    private TextField numeral;

    private SolucionControllerClient solucionControllerClient;

    private Button save;
    private Button nuevo;
    
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;

    private ImageView imageView;
    private ObservableList ol_conpuc = FXCollections.observableArrayList();
    ;
    private List<Solucion> l_solucion;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private static boolean estadoeditar = false;
    Thread backgroundThread;

    public Parent createContent() throws IOException {

        solucionControllerClient = new SolucionControllerClient();
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        nuevo = new Button("", imageView);
        //nuevo.setMaxWidth(130);  
        nuevo.setOnAction(e
                -> {
            try {

                nuevo();
            } catch (Exception ex) {

            }
        });

        //***********************************************  
     
        stack = new StackPane();
        solucion = new Solucion();

        la_nombre = new Label("Nombre:");
        la_descripcion = new Label("Descripción:");
        la_numeral = new Label("Numeral:");
        nombre = new TextField();
        nombre.getProperties().put("requerido", true);
        nombre.setMinWidth(320);
        descripcion = new TextField();
        numeral = new TextField();
        numeral.getProperties().put("requerido", true);
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        save = new Button("", imageView);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);
        save.setOnAction(e -> {
            try {
                try {
                    save();
                } catch (ParseException ex) {
                }
            } catch (InterruptedException ex) {

            }
        });

        // gridpane
        gp_generic = new GridPane();
        gp_generic.setMinSize(300, 250);
        gp_generic.addRow(0, la_numeral, numeral);
        gp_generic.addRow(1, la_nombre, nombre);
        gp_generic.addRow(2, la_descripcion, descripcion);
        gp_generic.add(hb_botones, 0, 3, 2, 1);

        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.setHgap(2);
        gp_generic.setVgap(2);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.getStylesheets().add(FXNomina.hojaestilos);
        gp_generic.getStyleClass().add("background");
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_generic.getLayoutBounds().getHeight(), gp_generic.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(FXNomina.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.getChildren().addAll(gp_generic, Gp_Message);
        crearoeditar();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void nuevo() {
        FXNomina.solucion = null;
        FXNomina.solucion = new Solucion();
        numeral.setText(String.valueOf(FAdminSoluciones.getSolucion().getNumeral()));
        nombre.setText("");
        descripcion.setText("");
    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {
                if (FXNomina.solucion.getId() == null) {
                    solucionControllerClient.create();
                } else {
                    solucion = solucionControllerClient.update();
                }
                L_Message.setText("Registro Almacenado");
                FAdminSoluciones.getSoluciones();
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
        int i = 0;

        FXNomina.solucion.setSolucion(nombre.getText());
        FXNomina.solucion.setDescripcion(descripcion.getText());
        FXNomina.solucion.setNumeral(Integer.valueOf(numeral.getText().trim()));
        if (FXNomina.solucion.getId() == null) {
            FXNomina.solucion.setSolucionPadre(FAdminSoluciones.getSolucion());
        }

    }

    private void set_datos_for_update() {
        nombre.setText(FXNomina.solucion.getSolucion());
        descripcion.setText(FXNomina.solucion.getDescripcion());
        numeral.setText(String.valueOf(FXNomina.solucion.getNumeral()));

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

                            if (general.getSelectionModel().getSelectedIndex() < 0) {
                                general.getStylesheets().set(0, FXNomina.hojaestilos);

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

    private void crearoeditar() {
        if (FXNomina.solucion != null) {
            set_datos_for_update();
        } else {
            nuevo();
        }
    }
}
