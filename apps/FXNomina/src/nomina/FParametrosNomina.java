package nomina;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.stage.StageStyle;
import javafx.util.Duration;

import modelo.ParametrosNomina;
import fxnomina.FXNomina;
import control.SearchBox;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FParametrosNomina extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GridPane gp_generic;
    private static final Label la_salariominimo = new Label("Salario Minimo:");
    private static final TextField salariomunimo = new TextField();
    private static final Label la_auxiliotransporte = new Label("Auxilio de Transporte:");
    private static final TextField auxiliotransporte = new TextField();
    private static final Label la_porc_salud = new Label("% Salud Empleado:");
    private static final TextField porc_salud = new TextField();
    private static final Label la_porc_pension = new Label("% Pensión:");
    private static final TextField porc_pension = new TextField();
    private static final Label la_porc_fondopensiones = new Label("% Fondo Pensión:");
    private static final TextField porc_fondopensiones = new TextField();
    private static final Label la_porc_arl = new Label("% Arl:");
    private static final TextField porc_arl = new TextField();
    private static final Label la_porc_cajacompfam = new Label("% Caja Comp Familiar:");
    private static final TextField porc_cajacompfam = new TextField();
    private static final Label la_porc_icbf = new Label("% ICBF:");
    private static final TextField porc_icbf = new TextField();
    private static final Label la_porc_sena = new Label("% Sena:");
    private static final TextField porc_sena = new TextField();
    private static final Label la_porc_cesantias = new Label("% Cesantías:");
    private static final TextField porc_cesantias= new TextField(); 
    private static final Label la_porc_intcesantias = new Label("% Interés Cesantías:");
    private static final TextField porc_intcesantias = new TextField();
    private static final Label la_porc_prima = new Label("% Prima:");
    private static final TextField porc_prima = new TextField();
    private static final Label la_porc_vacaciones = new Label("% Vacaciones:");
    private static final TextField porc_vacaciones = new TextField();
    private static final Label la_porc_solidaridad = new Label("% Solidaridad:");
    private static final TextField porc_solidaridad = new TextField(); 
    private static final Label la_uvt = new Label("Uvt:");
    private static final TextField uvt = new TextField();
    private static final Label la_porc_retefuente95= new Label("% Retefuente >87 Y<=145:");
    private static final TextField porc_retefuente95 = new TextField();
    private static final Label la_porc_retefuente95y150= new Label("% Retefuente >145 y <=335:");
    private static final TextField porc_retefuente95y150 = new TextField();
    private static final Label la_porc_retefuente150y360= new Label("% Retefuente >335 y <=640:");
    private static final TextField porc_retefuente150y360 = new TextField() ;
    private static final Label la_porc_retefuentemay360= new Label("% Retefuente >640 Y <=945");
    private static final TextField porc_retefuentemay360 = new TextField();
    private static final Label la_year= new Label("Año:");
    private static final TextField año = new TextField() ;
    private static final Label la_porc_saludempleador= new Label("% Salud Empleador:");
    private static final TextField porc_saludempleador = new TextField() ;
    private Button save;
    private Button nuevo;
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
    
        stack = new StackPane();
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
        gp_generic.add(la_auxiliotransporte, 0, 0);
        gp_generic.add(auxiliotransporte, 1, 0);
        gp_generic.add(la_salariominimo, 2, 0);
        gp_generic.add(salariomunimo, 3, 0);
        gp_generic.add(la_porc_salud, 0, 1);
        gp_generic.add(porc_salud, 1, 1);
        gp_generic.add(la_porc_pension, 2, 1);
        gp_generic.add(porc_pension, 3, 1);
        gp_generic.add(la_porc_fondopensiones, 0, 2);
        gp_generic.add(porc_fondopensiones, 1, 2);
        gp_generic.add(la_porc_arl, 2, 2);
        gp_generic.add(porc_arl, 3, 2);
        gp_generic.add(la_porc_cajacompfam, 0, 3);
        gp_generic.add(porc_cajacompfam, 1, 3);
        gp_generic.add(la_porc_cesantias, 2, 3);
        gp_generic.add(porc_cesantias, 3, 3);
        gp_generic.add(la_porc_icbf, 0, 4);
        gp_generic.add(porc_icbf, 1, 4);
        gp_generic.add(la_porc_intcesantias, 2, 4);
        gp_generic.add(porc_intcesantias, 3, 4);
        gp_generic.add(la_porc_sena, 0, 5);
        gp_generic.add(porc_sena, 1, 5);
        gp_generic.add(la_porc_solidaridad, 2, 5);
        gp_generic.add(porc_solidaridad, 3, 5);
        gp_generic.add(la_uvt, 0, 6);
        gp_generic.add(uvt, 1, 6);
        gp_generic.add(la_porc_vacaciones, 2, 6);
        gp_generic.add(porc_vacaciones, 3, 6);
        gp_generic.add(la_porc_prima, 0, 7);
        gp_generic.add(porc_prima, 1, 7);
        gp_generic.add(la_porc_retefuente95, 2, 7);
        gp_generic.add(porc_retefuente95, 3, 7);
        gp_generic.add(la_porc_retefuente95y150, 0, 8);
        gp_generic.add(porc_retefuente95y150, 1, 8);
        gp_generic.add(la_porc_retefuente150y360, 2, 8);
        gp_generic.add(porc_retefuente150y360, 3, 8);
        gp_generic.add(la_porc_retefuentemay360, 0, 9);
        gp_generic.add(porc_retefuentemay360, 1, 9);
        gp_generic.add(la_year, 2, 9);
        gp_generic.add(año, 3, 9);
        gp_generic.add(la_porc_saludempleador, 0, 10);
        gp_generic.add(porc_saludempleador, 1, 10);
       
        
        
        
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.add(hb_botones, 0, 11, 3, 1);
        gp_generic.getStylesheets().add(FXNomina.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.alignmentProperty().setValue(Pos.CENTER);
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

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            try {
                set_datos();
                if (FXNomina.parametrosNomina.getId() == null) {
                    FXNomina.parametrosNominaControllerClient.create();
                } else {
                    FXNomina.parametrosNominaControllerClient.update();
                }

                L_Message.setText("Registro Almacenado");
                Task_Message = () -> {
                    try {

                        SetMessage();
                        AdminParametrosNomina.getRecords();
                    } catch (InterruptedException ex) {

                    } catch (ParseException ex) {
                       
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
        FXNomina.parametrosNomina.setAuxiliotransporte(new BigDecimal(auxiliotransporte.getText()));
        FXNomina.parametrosNomina.setSalariominimo(new BigDecimal(salariomunimo.getText()));
        FXNomina.parametrosNomina.setPorc_pension(new BigDecimal(porc_pension.getText()));
        FXNomina.parametrosNomina.setPorc_arl(new BigDecimal(porc_arl.getText()));
        FXNomina.parametrosNomina.setPorc_cajacompfam(new BigDecimal(porc_cajacompfam.getText()));
        FXNomina.parametrosNomina.setPorc_cesantias(new BigDecimal(porc_cesantias.getText()));
        FXNomina.parametrosNomina.setPorc_fp(new BigDecimal(porc_fondopensiones.getText()));
        FXNomina.parametrosNomina.setPorc_icbf(new BigDecimal(porc_icbf.getText()));
        FXNomina.parametrosNomina.setPorc_intcesantias(new BigDecimal(porc_intcesantias.getText()));
        FXNomina.parametrosNomina.setPorc_prima(new BigDecimal(porc_prima.getText()));
        FXNomina.parametrosNomina.setPorc_retefuente95(new BigDecimal(porc_retefuente95.getText()));
        FXNomina.parametrosNomina.setPorc_retefuentemay95y150(new BigDecimal(porc_retefuente95y150.getText()));
        FXNomina.parametrosNomina.setPorc_retefuentemay150y360(new BigDecimal(porc_retefuente150y360.getText()));
        FXNomina.parametrosNomina.setPorc_retefuentemay360(new BigDecimal(porc_retefuentemay360.getText()));
        FXNomina.parametrosNomina.setPorc_salud(new BigDecimal(porc_salud.getText()));
        FXNomina.parametrosNomina.setPorc_sena(new BigDecimal(porc_sena.getText()));
        FXNomina.parametrosNomina.setPorc_solidaridad(new BigDecimal(porc_solidaridad.getText()));
        FXNomina.parametrosNomina.setPorc_vacaciones(new BigDecimal(porc_vacaciones.getText()));
        FXNomina.parametrosNomina.setPorc_salud_empleador(new BigDecimal(porc_saludempleador.getText()));
        FXNomina.parametrosNomina.setUvt(new BigDecimal(uvt.getText()));
        FXNomina.parametrosNomina.setAño(util.UtilDate.getyearfecha(new Date()));
        
        
       
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true )){
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
                            if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true )){
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
                                general.getStylesheets().set(0, FXNomina.hojaestilos);

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
        FXNomina.parametrosNomina = null;
        FXNomina.parametrosNomina = new ParametrosNomina();
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

        if (FXNomina.parametrosNomina != null) {
            if (FXNomina.parametrosNomina.getId() != null) {
                 getRecord();
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
    
 public void getRecord()
 {
       auxiliotransporte.setText(FXNomina.parametrosNomina.getAuxiliotransporte().toString());
       porc_fondopensiones.setText(FXNomina.parametrosNomina.getPorc_fp().toString());
       porc_arl.setText(FXNomina.parametrosNomina.getPorc_arl().toString());
       porc_cajacompfam.setText(FXNomina.parametrosNomina.getPorc_cajacompfam().toString());
       porc_cesantias.setText(FXNomina.parametrosNomina.getPorc_cesantias().toString());
       porc_pension.setText(FXNomina.parametrosNomina.getPorc_pension().toString());
       porc_icbf.setText(FXNomina.parametrosNomina.getPorc_icbf().toString());
       porc_intcesantias.setText(FXNomina.parametrosNomina.getPorc_intcesantias().toString());
       porc_prima.setText(FXNomina.parametrosNomina.getPorc_prima().toString());
       porc_retefuente95.setText(FXNomina.parametrosNomina.getPorc_retefuente95().toString());
       porc_retefuente95y150.setText(FXNomina.parametrosNomina.getPorc_retefuentemay95y150().toString());
       porc_retefuente150y360.setText(FXNomina.parametrosNomina.getPorc_retefuentemay150y360().toString());
       porc_retefuentemay360.setText(FXNomina.parametrosNomina.getPorc_retefuentemay360().toString());
       porc_salud.setText(FXNomina.parametrosNomina.getPorc_salud().toString());
       porc_sena.setText(FXNomina.parametrosNomina.getPorc_sena().toString());
       porc_solidaridad.setText(FXNomina.parametrosNomina.getPorc_solidaridad().toString());
       porc_vacaciones.setText(FXNomina.parametrosNomina.getPorc_vacaciones().toString());
       año.setText(String.valueOf(FXNomina.parametrosNomina.getAño()));
       salariomunimo.setText(String.valueOf(FXNomina.parametrosNomina.getSalariominimo()));
       porc_saludempleador.setText(FXNomina.parametrosNomina.getPorc_salud_empleador().toString());
       uvt.setText(FXNomina.parametrosNomina.getUvt().toString());
 }
}
