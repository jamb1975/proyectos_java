package sihic.nomina;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.CargosEntidad;
import sihic.SihicApp;
import sihic.control.SearchBox;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FCargosEntidad extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GridPane gp_generic;
    private static final Label la_cargo = new Label("Cargo:");
    private static final TextField cargo = new TextField();
    private static final Label la_nivelcargo = new Label("Nivel Cargo:");
    private static final ChoiceBox nivelcargo = new ChoiceBox();
    private static final Label la_funciones = new Label("Funciones:");
    private static final TextArea funciones = new TextArea();
    private static final Label la_sueldo = new Label("Sueldo:");
    private static final Label la_porcarl = new Label("%Arl:");
    private static final TextField porcarl=new TextField();
    private static final TextField sueldo = new TextField();
    private Button save;
    private Button nuevo;

    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;

    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;

    private String appPathGeneric;
    private Class clzGeneric;
    private Object appClassGeneric;
    private Stage stageGeneric = new Stage(StageStyle.DECORATED);
    private Scene sceneGeneric = null;
    private Parent P_Generic;
    private static ChoiceBox cb_rh = new ChoiceBox();

    public Parent createContent() throws IOException {
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        nivelcargo.getItems().clear();
        nivelcargo.getItems().add(jenum.EnumNivelCargo.AUXILIAR0.ordinal(), "Auxiliar");
        nivelcargo.getItems().add(jenum.EnumNivelCargo.TECNICO1.ordinal(), "Técnico");
        nivelcargo.getItems().add(jenum.EnumNivelCargo.TECNOLOGO2.ordinal(), "Tecnólogo");
        nivelcargo.getItems().add(jenum.EnumNivelCargo.PROFESIONAL3.ordinal(), "Profesional");
        nivelcargo.getItems().add(jenum.EnumNivelCargo.PROFESIONALESPECIALIZADO4.ordinal(), "Profesional Especializado");
        nivelcargo.getSelectionModel().select(0);
        sihic.util.ValidacionesCampo.ValidarTextField(sueldo);
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        save = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        nuevo = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/tributaria.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        gp_generic = new GridPane();
        gp_generic.add(la_nivelcargo, 0, 0);
        gp_generic.add(nivelcargo, 1, 0);
        gp_generic.add(la_cargo, 0, 1);
        gp_generic.add(cargo, 1, 1);
        gp_generic.add(la_funciones, 0, 2);
        gp_generic.add(funciones, 1, 2);
        gp_generic.add(la_sueldo, 0, 3);
        gp_generic.add(sueldo, 1, 3);
         gp_generic.add(la_porcarl, 0, 4);
        gp_generic.add(porcarl, 1, 4);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.add(hb_botones, 0, 5, 3, 1);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.alignmentProperty().setValue(Pos.CENTER);
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
            try {
                set_datos();
                if (SihicApp.cargosEntidad.getId() == null) {
                    SihicApp.cargosEntidadControllerClient.create();
                } else {
                    SihicApp.cargosEntidadControllerClient.update();
                }

                L_Message.setText("Registro Almacenado");
                Task_Message = () -> {
                    try {

                        SetMessage();
                        AdminCargosEntidad.getRecords();
                    } catch (InterruptedException ex) {

                    } catch (ParseException ex) {
                        Logger.getLogger(FEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
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

    private void set_datos() {
        int i = 0;

        SihicApp.cargosEntidad.setNivelcargo(nivelcargo.getSelectionModel().getSelectedIndex());
        SihicApp.cargosEntidad.setNombre(cargo.getText());
        SihicApp.cargosEntidad.setFunciones(funciones.getText());
          try {
        SihicApp.cargosEntidad.setPorc_arl(new BigDecimal(porcarl.getText()));
           } catch (Exception e) {
            SihicApp.cargosEntidad.setPorc_arl(new BigDecimal(porcarl.getText().replace(",", ".")));
        }
        try {
            SihicApp.cargosEntidad.setSueldo(new BigDecimal(sueldo.getText()));
        } catch (Exception e) {
            SihicApp.cargosEntidad.setSueldo(new BigDecimal(sueldo.getText().replace(",", ".")));
        }

    }
 private void getdatos() {
        int i = 0;

        nivelcargo.getSelectionModel().select(SihicApp.cargosEntidad.getNivelcargo());
        cargo.setText(SihicApp.cargosEntidad.getNombre());
        funciones.setText(SihicApp.cargosEntidad.getFunciones());
        try
        {
        porcarl.setText(SihicApp.cargosEntidad.getPorc_arl().toString());
        }catch(Exception e)
        {
            
        }
        sueldo.setText(SihicApp.cargosEntidad.getSueldo().toString());
      

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

                    if (general.getSelectionModel().getSelectedIndex() < 0 && general.getProperties().get("requerido") == null) {
                        permitirproceso = false;
                        general.getStylesheets().add(0, "/sihic/personal.css");
                        general.getStylesheets().set(0, "/sihic/personal.css");
                    }

                } else {
                    if (n.getClass() == SearchBox.class) {

                        SearchBox general = (SearchBox) n;
                        if (general.getProperties().get("requerido") != null) {
                            if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
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

                            if (general.getSelectionModel().getSelectedIndex() < 0 && general.getProperties().get("requerido") == null) {
                                general.getStylesheets().set(0, SihicApp.hojaestilos);

                            }

                        } else {
                            if (n.getClass() == SearchBox.class) {

                                SearchBox general = (SearchBox) n;
                                if (general.getProperties().get("requerido") != null) {

                                    general.setStyle(null);
                                    general.getStyleClass().add("text-field");
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

    private void nuevoRecord() {
        SihicApp.cargosEntidad = null;
        SihicApp.cargosEntidad = new CargosEntidad();
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");
                general.setDisable(false);
            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    general.getSelectionModel().select(0);
                    general.setDisable(false);
                }

                if (n.getClass() == DatePicker.class) {
                    DatePicker general = (DatePicker) n;
                    general.setValue(LocalDate.now());
                    general.setDisable(false);
                }
                if (n.getClass() == SearchBox.class) {
                    SearchBox general = (SearchBox) n;
                    general.setDisable(false);
                }
            }

        }

    }

    public void disabled_controles() {
        for (Object n : gp_generic.getChildren().toArray()) {
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

    public void crearoeditar() {

        if (SihicApp.cargosEntidad != null) {
            if (SihicApp.cargosEntidad.getId() != null) {
                   getdatos();
            } else {
                
               nuevoRecord();
            }
        } else {
            nuevoRecord();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
