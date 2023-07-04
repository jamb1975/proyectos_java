package sihic.historiasclinicas.consultas;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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
import sihic.SihicApp;
import sihic.controlador.ResultadosControllerClient;
import sihic.reportespdf.PlantillasPdf;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FResultados extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private PlantillasPdf plantillasPdf;
    private GridPane gp_generic;
    private Label la_field0;
    private Label la_field1;
    private Label la_field2;
    private Label la_field3;
    private Label la_field4;
    private Label la_field5;
    private Label la_field6;
    private Label la_field7;
    private Label la_field8;
    private Label la_field9;
    private Label la_field10;
    private Label la_field11;
    private Label la_field12;
    private Label la_field13;
    private Label la_field14;
    private Label la_field15;
    private Label la_field16;
    private Label la_field17;
    private Label la_field18;
    private Label la_field19;
    private Label la_field20;
    private Label la_plantilla;
    private TextField field0;
    private TextArea field1;
    private TextArea field2;
    private TextArea field3;
    private TextField field4;
    private TextField field5;
    private TextField field6;
    private TextField field7;
    private TextField field8;
    private TextField field9;
    private TextField field10;
    private TextField field11;
    private TextField field12;
    private TextField field13;
    private TextField field14;
    private TextField field15;
    private TextField field16;
    private TextField field17;
    private TextField field18;
    private TextField field19;
    private TextField field20;
    private TextField field21;
    private ChoiceBox cb_plantilla;

    private ResultadosControllerClient resultadosControllerClient;
    private Button save;
    private Button pdfresultado;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private ImageView logo;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;

    public Parent createContent() throws IOException, ParseException {
        resultadosControllerClient = new ResultadosControllerClient();
        plantillasPdf = new PlantillasPdf();

        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        la_plantilla = new Label("Plantilla:");
        cb_plantilla = new ChoiceBox();
        //cb_plantilla.setMinWidth(550);
        cb_plantilla.getItems().addAll("GENERICA", "ECOGRAFIA  OBSTETRICA II/III TRIMESTRE", "ECOGRAFIA DOPPLER DE ABDOMEN", "ECOGRAFIA DOPPLER TESTICULAR", "ECOGRAFIA DOPPLER RENAL", "DOPPLER ARTERIAS RENALES", "ECOGRAFIA PELVICA MAS DOPPLER", "ECOGRAFIA DOPPLER OBSTETRICO", "FETAL", "ECOGRAFIA DOPPLER ARTERIAL DE MIEMBROS INFERIORES", "ECOGRAFIA DOPPLER VENOSO DE MIEMBROS INFERIORES", "ECOGRAFIA DOPPLER ARTERIAL DE MIEMBROS INFERIORES 2", "ECOGRAFIA DOPPLER VENOSO DE MIEMBROS INFERIORES 2", "TRUCUT MAMARIA", "BIOPSIA DE MAMA (BACAF)", " ECOGRAFIA  TRANSRECTAL DE PROSTATA (HIPERTROFIA)", "BIOPSIA TIROIDES", "ECOGRAFIA OBSTETRICA DE DETALLE 2");
        cb_plantilla.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);

        la_field3 = new Label("Opinión:");
        la_field0 = new Label("Examen Solicitado:");
        la_field1 = new Label();
        la_field2 = new Label();
        la_field4 = new Label();
        la_field5 = new Label();
        la_field6 = new Label();
        la_field7 = new Label();
        la_field8 = new Label();
        la_field9 = new Label();
        la_field10 = new Label();
        la_field11 = new Label();
        la_field12 = new Label();
        la_field13 = new Label();
        la_field14 = new Label();
        la_field15 = new Label();
        la_field16 = new Label();
        la_field17 = new Label();
        la_field18 = new Label();
        la_field19 = new Label();
        la_field20 = new Label();
        field0 = new TextField();
        //field0.setMinWidth(550);
        field1 = new TextArea();
        //field1.setMinWidth(550);
        field2 = new TextArea();
        //field2.setMinWidth(550);
        field3 = new TextArea();
        //field3.setMinWidth(550);
        field4 = new TextField();
        field5 = new TextField();
        field6 = new TextField();
        field7 = new TextField();
        field8 = new TextField();
        field9 = new TextField();
        field10 = new TextField();
        field11 = new TextField();
        field12 = new TextField();
        field13 = new TextField();
        field14 = new TextField();
        field15 = new TextField();
        field16 = new TextField();
        field17 = new TextField();
        field18 = new TextField();
        field19 = new TextField();
        field20 = new TextField();
        field21 = new TextField();

        ImageView imageView = new ImageView("/images/pdf.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        pdfresultado = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        save = new Button("", imageView);
        hb_botones = new HBox(5);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            } catch (ParseException ex) {
                Logger.getLogger(FResultados.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        save.setOnKeyPressed(e -> {

            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();
                    pdfresultado.requestFocus();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FAgnConsultorios.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(FResultados.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        pdfresultado.setOnAction(e -> {

            try {

                plantillasPdf.fplantillas();
            } catch (Exception ex) {
            }
        });

        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, pdfresultado);
        gp_generic = new GridPane();

        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_LEFT);
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
        gp_generic.autosize();
        cb_plantilla.getSelectionModel().select(0);
        crearoeditar();
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic, Gp_Message);
        // stack.autosize();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
            set_datos_segun_Plantilla();
            try {
                if (SihicApp.resultados.getId() == null) {
                    resultadosControllerClient.create(SihicApp.resultados);
                    L_Message.setText("Registro Almacenado");
                } else {
                    resultadosControllerClient.update(SihicApp.resultados);
                    L_Message.setText("Registro modificado");
                }

                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
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
                    if (general.getProperties().get("requerido") != null) {
                        if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                            permitirproceso = false;
                            general.getStylesheets().add(0, "/sihic/personal.css");
                            general.getStylesheets().set(0, "/sihic/personal.css");
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

    public void crearoeditar() throws ParseException {
        interfaz_segun_plantilla();
        if (SihicApp.resultados.getId() != null) {
            datos_segun_Plantilla();
        }
    }

    public void interfaz_segun_plantilla() {
        if (gp_generic.getChildren() != null) {

            if (gp_generic.getChildren().size() > 0) {
                for (int i = 0; i < gp_generic.getChildren().size(); i++) {
                    gp_generic.getChildren().remove(0);
                }
            }
            while (gp_generic.getRowConstraints().size() > 0) {
                gp_generic.getRowConstraints().remove(0);
            }

            while (gp_generic.getColumnConstraints().size() > 0) {
                gp_generic.getColumnConstraints().remove(0);
            }
            gp_generic.getChildren().clear();
        }
        switch (cb_plantilla.getSelectionModel().getSelectedIndex()) {
            case 0:
                la_field1.setText("Descripción:");

                gp_generic.add(la_field0, 0, 0, 1, 1);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1, 1, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field3, 0, 3);
                gp_generic.add(field3, 1, 3, 3, 1);
                gp_generic.add(hb_botones, 1, 4, 3, 1);

                break;
            case 1:
                la_field1.setText("Utero:");
                la_field2.setText("Descripción:");
                la_field3.setText("Opinión:");
                la_field4.setText("Dbp:");
                la_field5.setText("Cc:");
                la_field6.setText("Ca:");
                la_field7.setText("Lf:");
                la_field8.setText("Peso:");
                la_field9.setText("Placenta:");
                la_field10.setText("Liquido Amniotico:");
                la_field11.setText("Cervix:");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field2, 0, 3);
                gp_generic.add(field2, 1, 3, 3, 1);
                gp_generic.add(la_field4, 0, 4, 1, 1);
                gp_generic.add(la_field5, 1, 4, 1, 1);
                gp_generic.add(la_field6, 2, 4, 1, 1);
                gp_generic.add(la_field7, 3, 4, 1, 1);
                gp_generic.add(field4, 0, 5, 1, 1);
                gp_generic.add(field5, 1, 5, 1, 1);
                gp_generic.add(field6, 2, 5, 1, 1);
                gp_generic.add(field7, 3, 5, 1, 1);
                gp_generic.add(la_field8, 0, 7);
                gp_generic.add(field8, 1, 7, 3, 1);
                gp_generic.add(la_field9, 0, 8);
                gp_generic.add(field9, 1, 8, 3, 1);
                gp_generic.add(la_field10, 0, 9);
                gp_generic.add(field10, 1, 9, 3, 1);
                gp_generic.add(la_field11, 0, 10);
                gp_generic.add(field11, 1, 10, 3, 1);
                gp_generic.add(la_field3, 0, 11);
                gp_generic.add(field3, 1, 11, 3, 1);
                gp_generic.add(hb_botones, 1, 12, 3, 1);
                break;
            case 2:
                la_field1.setText("Descripción corta:");
                la_field2.setText("Descripción:");
                la_field3.setText("Opinión:");
                la_field4.setText("Velocidad Maxima");
                la_field5.setText("Velocidad Minima");
                la_field6.setText("Ir:");
                la_field7.setText("Arteria Hepatica");

                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field4, 1, 3);
                gp_generic.add(la_field5, 2, 3);
                gp_generic.add(la_field6, 3, 3);
                gp_generic.add(la_field7, 0, 4, 1, 1);
                gp_generic.add(field4, 1, 4, 1, 1);
                gp_generic.add(field5, 2, 4, 1, 1);
                gp_generic.add(field6, 3, 4, 1, 1);
                gp_generic.add(la_field2, 0, 5);
                gp_generic.add(field2, 1, 5, 3, 1);
                gp_generic.add(la_field3, 0, 6);
                gp_generic.add(field3, 1, 6, 3, 1);
                gp_generic.add(hb_botones, 1, 7, 3, 1);
                break;
            case 3:
                la_field1.setText("Descripción:");
                la_field3.setText("Opinión:");
                la_field4.setText("Testiculo Derecho:");
                la_field5.setText("Testiculo Izquierdo");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field4, 0, 2);
                gp_generic.add(field4, 1, 2, 3, 1);
                gp_generic.add(la_field5, 0, 3);
                gp_generic.add(field5, 1, 3, 3, 1);
                gp_generic.add(la_field1, 0, 4);
                gp_generic.add(field1, 1, 4);
                gp_generic.add(la_field3, 0, 5);
                gp_generic.add(field3, 1, 5);
                gp_generic.add(hb_botones, 1, 6, 3, 1);
                break;
            case 4:
                la_field1.setText("Descripción Corta:");
                la_field2.setText("Descripción:");
                la_field3.setText("Opinión:");
                la_field4.setText("Riñon Derecho:");
                la_field5.setText("Riñon Izquierdo");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field4, 0, 3);
                gp_generic.add(field4, 1, 3, 3, 1);
                gp_generic.add(la_field5, 0, 4);
                gp_generic.add(field5, 1, 4, 3, 1);
                gp_generic.add(la_field2, 0, 5);
                gp_generic.add(field2, 1, 5, 3, 1);
                gp_generic.add(la_field3, 0, 6);
                gp_generic.add(field3, 1, 6, 3, 1);
                gp_generic.add(hb_botones, 1, 7, 3, 1);
                break;
            case 5:
                la_field1.setText("Descripción 1:");
                la_field2.setText("Descripción 2:");
                la_field3.setText("Opinión:");
                la_field4.setText("Velocidad Maxima(Der-Izq)");
                la_field5.setText("Velocidad Minima(Der-Izq)");
                la_field6.setText("Ir(Der-Izq)");
                la_field7.setText("Arteria Arcuata:");
                la_field8.setText("Arteria Segmentaria");
                la_field9.setText("Renal");
                la_field10.setText("Interlobulillar");
                la_field11.setText("Aorta Abdominal");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field4, 1, 3);
                gp_generic.add(field4, 1, 4, 1, 1);
                gp_generic.add(la_field5, 2, 3);
                gp_generic.add(field5, 2, 4, 1, 1);
                gp_generic.add(la_field6, 3, 3);
                gp_generic.add(field6, 3, 4, 1, 1);
                gp_generic.add(la_field7, 0, 4);
                gp_generic.add(la_field8, 0, 5);
                gp_generic.add(la_field9, 0, 6);
                gp_generic.add(la_field10, 0, 7);
                gp_generic.add(la_field11, 0, 8);
                gp_generic.add(field7, 1, 5, 1, 1);
                gp_generic.add(field8, 2, 5, 1, 1);
                gp_generic.add(field9, 3, 5, 1, 1);
                gp_generic.add(field10, 1, 6, 1, 1);
                gp_generic.add(field11, 2, 6, 1, 1);
                gp_generic.add(field12, 3, 6, 1, 1);
                gp_generic.add(field13, 1, 7, 1, 1);
                gp_generic.add(field14, 2, 7, 1, 1);
                gp_generic.add(field15, 3, 7, 1, 1);
                gp_generic.add(field16, 1, 8, 1, 1);
                gp_generic.add(field17, 2, 8, 1, 1);
                gp_generic.add(field18, 3, 8, 1, 1);
                gp_generic.add(la_field2, 0, 9);
                gp_generic.add(field2, 1, 9, 3, 1);

                gp_generic.add(la_field3, 0, 10);
                gp_generic.add(field3, 1, 10, 3, 1);
                gp_generic.add(hb_botones, 1, 11, 3, 1);
                break;
            case 6:
                la_field1.setText("Descripción:");
                la_field4.setText("Utero:");
                la_field3.setText("Opinión:");
                la_field5.setText("Endometrio:");
                la_field6.setText("Ovario Derecho:");
                la_field7.setText("Ovario Izquierdo:");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field4, 0, 2, 1, 1);
                gp_generic.add(field4, 1, 2, 3, 1);
                gp_generic.add(la_field5, 0, 3, 1, 1);
                gp_generic.add(field5, 1, 3, 3, 1);
                gp_generic.add(la_field6, 0, 4, 1, 1);
                gp_generic.add(field6, 1, 4, 3, 1);
                gp_generic.add(la_field7, 0, 5, 1, 1);
                gp_generic.add(field7, 1, 5, 3, 1);
                gp_generic.add(la_field1, 0, 6, 1, 1);
                gp_generic.add(field1, 1, 6, 3, 1);
                gp_generic.add(la_field3, 0, 7, 1, 1);
                gp_generic.add(field3, 1, 7, 3, 1);
                gp_generic.add(hb_botones, 1, 8, 3, 1);
                break;
            case 7:
                la_field1.setText("Descripción:");

                la_field4.setText("Fur:");
                la_field5.setText("Fpp:");
                la_field6.setText("Utero:");
                la_field7.setText("Dbp:");
                la_field8.setText("Cc:");
                la_field9.setText("Ca:");
                la_field10.setText("lf:");
                la_field11.setText("Peso:");
                la_field12.setText("Placenta:");
                la_field13.setText("Liquido Amniotico:");
                la_field14.setText("Cervix:");
                la_field3.setText("Opinión:");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1);
                gp_generic.add(la_field4, 0, 2);
                gp_generic.add(field4, 1, 2, 3, 1);
                gp_generic.add(la_field5, 0, 3);
                gp_generic.add(field5, 1, 3, 3, 1);
                gp_generic.add(la_field6, 0, 4);
                gp_generic.add(field6, 1, 4, 3, 1);
                gp_generic.add(la_field1, 0, 5);
                gp_generic.add(field1, 1, 5, 3, 1);
                gp_generic.add(la_field7, 0, 6);
                gp_generic.add(field7, 0, 7, 1, 1);
                gp_generic.add(la_field8, 1, 6);
                gp_generic.add(field8, 1, 7, 1, 1);
                gp_generic.add(la_field9, 2, 6);
                gp_generic.add(field9, 2, 7, 1, 1);
                gp_generic.add(la_field10, 3, 6);
                gp_generic.add(field10, 3, 7, 1, 1);
                gp_generic.add(la_field11, 0, 8);
                gp_generic.add(field11, 1, 8, 3, 1);
                gp_generic.add(la_field12, 0, 9);
                gp_generic.add(field12, 1, 9, 3, 1);
                gp_generic.add(la_field13, 0, 10);
                gp_generic.add(field13, 1, 10, 3, 1);
                gp_generic.add(la_field14, 0, 11);
                gp_generic.add(field14, 1, 11, 3, 1);
                gp_generic.add(la_field3, 0, 12);
                gp_generic.add(field3, 1, 12, 3, 1);
                gp_generic.add(hb_botones, 1, 13, 3, 1);
                break;

            case 8:
                la_field1.setText("Descripción 1:");
                la_field2.setText("Descripción 2:");
                la_field3.setText("Opinión:");
                la_field4.setText("Índice de resistencia(Der-Izq)");
                la_field5.setText("Índice de Pulsatilidad(Der-Izq)");
                la_field6.setText("Arteria uterina:");
                la_field7.setText("Aorta umbilical:");
                la_field8.setText("Arteria cerebral media:");
                la_field9.setText("Aorta toracica fetal:");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);

                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field4, 1, 3);
                gp_generic.add(la_field5, 2, 3);
                gp_generic.add(la_field6, 0, 4);
                gp_generic.add(la_field7, 0, 5);
                gp_generic.add(la_field8, 0, 6);
                gp_generic.add(la_field9, 0, 7);

                gp_generic.add(field4, 1, 4, 1, 1);
                gp_generic.add(field5, 2, 4, 1, 1);
                gp_generic.add(field6, 1, 5, 1, 1);
                gp_generic.add(field7, 2, 5, 1, 1);
                gp_generic.add(field8, 1, 6, 1, 1);
                gp_generic.add(field9, 2, 6, 1, 1);
                gp_generic.add(field10, 1, 7, 1, 1);
                gp_generic.add(field11, 2, 7, 1, 1);
                gp_generic.add(la_field2, 0, 8);
                gp_generic.add(field2, 1, 8, 3, 1);
                gp_generic.add(la_field3, 0, 9);
                gp_generic.add(field3, 1, 9, 3, 1);
                gp_generic.add(hb_botones, 1, 10, 3, 1);

                break;

            case 9:
                la_field1.setText("Descripción 1:");
                la_field3.setText("Opinión:");
                la_field4.setText("Velocidad Maxima(Der-Izq)");
                la_field5.setText("Velocidad Minima(Der-Izq)");
                la_field6.setText("Indi. Resist:");
                la_field7.setText("Femoral Común:");
                la_field8.setText("Femoral Superficial:");
                la_field9.setText("Poplitea:");
                la_field10.setText("Tibial Anterior:");
                la_field11.setText("Tibial Posterior:");
                la_field12.setText("Pedia:");
                gp_generic.add(la_field0, 0, 0, 1, 1);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1, 1, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);

                gp_generic.add(la_field1, 0, 2, 1, 1);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field4, 1, 3, 1, 1);
                gp_generic.add(la_field5, 2, 3, 1, 1);
                gp_generic.add(la_field6, 3, 3, 1, 1);
                gp_generic.add(la_field7, 0, 4, 1, 1);
                gp_generic.add(la_field8, 0, 5, 1, 1);
                gp_generic.add(la_field9, 0, 6, 1, 1);
                gp_generic.add(la_field10, 0, 7, 1, 1);
                gp_generic.add(la_field11, 0, 8, 1, 1);
                gp_generic.add(la_field12, 0, 9, 1, 1);

                gp_generic.add(field4, 1, 4, 1, 1);
                gp_generic.add(field5, 2, 4, 1, 1);
                gp_generic.add(field6, 3, 4, 1, 1);
                gp_generic.add(field7, 1, 5, 1, 1);
                gp_generic.add(field8, 2, 5, 1, 1);
                gp_generic.add(field9, 3, 5, 1, 1);
                gp_generic.add(field10, 1, 6, 1, 1);
                gp_generic.add(field11, 2, 6, 1, 1);
                gp_generic.add(field12, 3, 6, 1, 1);
                gp_generic.add(field13, 1, 7, 1, 1);
                gp_generic.add(field14, 2, 7, 1, 1);
                gp_generic.add(field15, 3, 7, 1, 1);
                gp_generic.add(field16, 1, 8, 1, 1);
                gp_generic.add(field17, 2, 8, 1, 1);
                gp_generic.add(field18, 3, 8, 1, 1);
                gp_generic.add(field19, 1, 9, 1, 1);
                gp_generic.add(field20, 2, 9, 1, 1);
                gp_generic.add(field21, 3, 9, 1, 1);

                gp_generic.add(la_field3, 0, 10, 1, 1);
                gp_generic.add(field3, 1, 10, 3, 1);
                gp_generic.add(hb_botones, 1, 11, 3, 1);
                break;

            case 10:
                la_field1.setText("Lado Derecho:");
                la_field2.setText("Lado Izquierdo:");
                la_field3.setText("Opinión:");
                la_field4.setText("Sistema Venoso Profundo:");
                la_field5.setText("Sistema Venoso Superficial:");
                la_field6.setText("Sistema Venoso Profundo:");
                la_field7.setText("Sistema Venoso Superficial:");

                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(la_field4, 0, 3);
                gp_generic.add(field4, 1, 3, 3, 1);
                gp_generic.add(la_field5, 0, 4);
                gp_generic.add(field1, 1, 4, 3, 1);

                gp_generic.add(la_field2, 0, 5);
                gp_generic.add(la_field6, 0, 6);
                gp_generic.add(field5, 1, 6, 3, 1);
                gp_generic.add(la_field7, 0, 7);
                gp_generic.add(field2, 1, 7, 3, 1);

                gp_generic.add(la_field3, 0, 8);
                gp_generic.add(field3, 1, 8, 3, 1);
                gp_generic.add(hb_botones, 1, 9, 3, 1);
                break;
            case 11:
                la_field1.setText("Descripción:");
                la_field2.setText("Lado Derecho:");
                la_field3.setText("Opinión:");
                la_field4.setText("Lado Izquierdo:");
                la_field5.setText("Arteria Femoral Común:");
                la_field6.setText("Arteria Femoral Superficial:");
                la_field7.setText("Arteria Poplitea:");
                la_field8.setText("Arteria Tibial Anterior:");
                la_field9.setText("Arteria Tibial posterior:");
                la_field10.setText("Arteria Femoral Común:");
                la_field11.setText("Arteria Femoral Superficial:");
                la_field12.setText("Arteria Poplitea:");
                la_field13.setText("Arteria Tibial Anterior:");
                la_field14.setText("Arteria Tibial posterior:");

                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field2, 0, 3);
                gp_generic.add(la_field5, 0, 4);
                gp_generic.add(field4, 1, 4, 3, 1);
                gp_generic.add(la_field6, 0, 5);
                gp_generic.add(field5, 1, 5, 3, 1);
                gp_generic.add(la_field7, 0, 6);
                gp_generic.add(field6, 1, 6, 3, 1);
                gp_generic.add(la_field8, 0, 7);
                gp_generic.add(field7, 1, 7, 3, 1);
                gp_generic.add(la_field9, 0, 8);
                gp_generic.add(field8, 1, 8, 3, 1);

                gp_generic.add(la_field4, 0, 9, 1, 1);
                gp_generic.add(la_field10, 0, 10, 1, 1);
                gp_generic.add(field9, 1, 10, 3, 1);
                gp_generic.add(la_field11, 0, 11, 1, 1);
                gp_generic.add(field10, 1, 11, 3, 1);
                gp_generic.add(la_field12, 0, 12, 1, 1);
                gp_generic.add(field11, 1, 12, 3, 1);
                gp_generic.add(la_field13, 0, 13, 1, 1);
                gp_generic.add(field12, 1, 13, 3, 1);
                gp_generic.add(la_field14, 0, 14, 1, 1);
                gp_generic.add(field13, 1, 14, 3, 1);

                gp_generic.add(la_field3, 0, 15, 1, 1);
                gp_generic.add(field3, 1, 15, 3, 1);
                gp_generic.add(hb_botones, 1, 16, 3, 1);
                break;

            case 12:
                la_field1.setText("Descripción:");
                la_field2.setText("Miembro Inferior Derecho:");
                la_field3.setText("Opinión:");
                la_field4.setText("Miembro Inferior Izquierdo:");
                la_field5.setText("Vena Femoral Común:");
                la_field6.setText("Vena Femoral Superficial y Profunda:");
                la_field7.setText("Cayado de la Safena Interna:");
                la_field8.setText("Safena Interna:");
                la_field9.setText("Vena Poplitea:");
                la_field10.setText("Cayado de la Safena Externa:");
                la_field11.setText("Safena Externa y Perforantes:");
                la_field12.setText("Tibial Posterior:");

                la_field13.setText("Vena Femoral Común:");
                la_field14.setText("Vena Femoral Superficial y Profunda:");
                la_field15.setText("Cayado de la Safena Interna:");
                la_field16.setText("Safena Interna:");
                la_field17.setText("Vena Poplitea:");
                la_field18.setText("Cayado de la Safena Externa:");
                la_field19.setText("Safena Externa y Perforantes:");
                la_field20.setText("Tibial Posterior:");

                gp_generic.add(la_field0, 0, 0, 1, 1);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1, 1, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2, 1, 1);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field2, 0, 3, 1, 1);
                gp_generic.add(la_field5, 0, 4, 1, 1);
                gp_generic.add(field4, 0, 5, 1, 1);
                gp_generic.add(la_field6, 1, 4, 1, 1);
                gp_generic.add(field5, 1, 5, 1, 1);
                gp_generic.add(la_field7, 2, 4, 1, 1);
                gp_generic.add(field6, 2, 5, 1, 1);
                gp_generic.add(la_field8, 3, 4, 1, 1);
                gp_generic.add(field7, 3, 5, 1, 1);
                gp_generic.add(la_field9, 0, 6, 1, 1);
                gp_generic.add(field8, 0, 7, 1, 1);
                gp_generic.add(la_field10, 1, 6, 1, 1);
                gp_generic.add(field9, 1, 7, 1, 1);
                gp_generic.add(la_field11, 2, 6, 1, 1);
                gp_generic.add(field10, 2, 7, 1, 1);
                gp_generic.add(la_field12, 3, 6, 1, 1);
                gp_generic.add(field11, 3, 7, 1, 1);

                gp_generic.add(la_field4, 0, 8, 1, 1);
                gp_generic.add(la_field13, 0, 9, 1, 1);
                gp_generic.add(field12, 0, 10, 1, 1);
                gp_generic.add(la_field14, 1, 9, 1, 1);
                gp_generic.add(field13, 1, 10, 1, 1);
                gp_generic.add(la_field15, 2, 9, 1, 1);
                gp_generic.add(field14, 2, 10, 1, 1);
                gp_generic.add(la_field16, 3, 9, 1, 1);
                gp_generic.add(field15, 3, 10, 1, 1);
                gp_generic.add(la_field17, 0, 11, 1, 1);
                gp_generic.add(field16, 0, 12, 1, 1);
                gp_generic.add(la_field18, 1, 11, 1, 1);
                gp_generic.add(field17, 1, 12, 1, 1);
                gp_generic.add(la_field19, 2, 11, 1, 1);
                gp_generic.add(field18, 2, 12, 1, 1);
                gp_generic.add(la_field20, 3, 11, 1, 1);
                gp_generic.add(field19, 3, 12, 1, 1);

                gp_generic.add(la_field3, 0, 13, 1, 1);
                gp_generic.add(field3, 1, 13, 3, 1);
                gp_generic.add(hb_botones, 1, 14, 3, 1);
                break;
            case 13:
                la_field1.setText("Procedimiento:");
                la_field3.setText("Opinión:");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);

                gp_generic.add(la_field3, 0, 3);
                gp_generic.add(field3, 1, 3, 3, 1);
                gp_generic.add(hb_botones, 1, 4, 3, 1);
                break;
            case 14:

                la_field1.setText("Descripción:");
                la_field2.setText("Procedimiento:");
                la_field3.setText("Opinión:");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field2, 0, 3);
                gp_generic.add(field2, 1, 3, 3, 1);

                gp_generic.add(la_field3, 0, 4);
                gp_generic.add(field3, 1, 4, 3, 1);
                gp_generic.add(hb_botones, 1, 5, 3, 1);
                break;
            case 15:

                la_field1.setText("Descripción:");
                la_field2.setText("Procedimiento:");
                la_field3.setText("Opinión:");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field3, 0, 3);
                gp_generic.add(field3, 1, 3, 3, 1);

                gp_generic.add(la_field2, 0, 4);
                gp_generic.add(field2, 1, 4, 3, 1);
                gp_generic.add(hb_botones, 1, 5, 3, 1);
                break;
            case 16:

                la_field1.setText("Descripción:");
                la_field2.setText("Procedimiento:");
                la_field3.setText("Opinión:");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field3, 0, 3);
                gp_generic.add(field3, 1, 3, 3, 1);

                gp_generic.add(la_field2, 0, 4);
                gp_generic.add(field2, 1, 4, 3, 1);
                gp_generic.add(hb_botones, 1, 5, 3, 1);
                break;
            case 17:
                la_field1.setText("Utero:");
                la_field2.setText("Descripción:");
                la_field3.setText("Opinión:");
                la_field4.setText("Dbp:");
                la_field5.setText("Cc:");
                la_field6.setText("Ca:");
                la_field7.setText("Lf:");
                la_field8.setText("Peso:");
                la_field9.setText("Placenta:");
                la_field10.setText("Liquido Amniotico:");
                la_field11.setText("Cervix:");
                la_field12.setText("Tono Fetal");
                la_field13.setText("Movimiento Fetal");
                la_field14.setText("Movimiento Respiratorio");
                la_field15.setText("Liquido Amniotico");
                gp_generic.add(la_field0, 0, 0);
                gp_generic.add(field0, 1, 0, 3, 1);
                gp_generic.add(la_plantilla, 0, 1);
                gp_generic.add(cb_plantilla, 1, 1, 3, 1);
                gp_generic.add(la_field1, 0, 2);
                gp_generic.add(field1, 1, 2, 3, 1);
                gp_generic.add(la_field2, 0, 3);
                gp_generic.add(field2, 1, 3, 3, 1);
                gp_generic.add(la_field4, 0, 4);
                gp_generic.add(la_field5, 1, 4);
                gp_generic.add(la_field6, 2, 4);
                gp_generic.add(la_field7, 3, 4);

                gp_generic.add(field4, 0, 5);
                gp_generic.add(field5, 1, 5);
                gp_generic.add(field6, 2, 5);
                gp_generic.add(field7, 3, 5);
                gp_generic.add(la_field8, 0, 7);
                gp_generic.add(field8, 1, 7, 3, 1);
                gp_generic.add(la_field9, 0, 8);
                gp_generic.add(field9, 1, 8, 3, 1);
                gp_generic.add(la_field10, 0, 9, 1, 1);
                gp_generic.add(field10, 1, 9, 3, 1);
                gp_generic.add(la_field11, 0, 10);
                gp_generic.add(field11, 1, 10, 3, 1);
                gp_generic.add(la_field12, 0, 11, 1, 1);
                gp_generic.add(la_field13, 1, 11, 1, 1);
                gp_generic.add(la_field14, 2, 11, 1, 1);
                gp_generic.add(la_field15, 3, 11, 1, 1);
                gp_generic.add(field12, 0, 12, 1, 1);
                gp_generic.add(field13, 1, 12, 1, 1);
                gp_generic.add(field14, 2, 12, 1, 1);
                gp_generic.add(field15, 3, 12, 1, 1);
                gp_generic.add(la_field3, 0, 13);
                gp_generic.add(field3, 1, 13, 3, 1);
                gp_generic.add(hb_botones, 1, 14, 3, 1);
                break;
        }

        //"ECOGRAFIA OBSTETRICA DE DETALLE 2"
    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        System.out.println("new value->" + newValue.intValue());
        interfaz_segun_plantilla();
    }

    public void datos_segun_Plantilla() {
        field0.setText(sihic.SihicApp.resultados.getHclConsultas().getAgnCitas().getServicio().getNombre());

        switch (cb_plantilla.getSelectionModel().getSelectedIndex()) {
            case 0:
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                break;
            case 1:
                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getUtero());
                field2.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getDbp());
                field5.setText(sihic.SihicApp.resultados.getCc());
                field6.setText(sihic.SihicApp.resultados.getCa());
                field7.setText(sihic.SihicApp.resultados.getLf());
                field8.setText(sihic.SihicApp.resultados.getPeso());
                field9.setText(sihic.SihicApp.resultados.getPlacenta());
                field10.setText(sihic.SihicApp.resultados.getLiquidoamniotico());
                field11.setText(sihic.SihicApp.resultados.getCervix());
                break;
            case 2:
                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion_corta());
                field2.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getVelmax());
                field5.setText(sihic.SihicApp.resultados.getVelmin());
                field6.setText(sihic.SihicApp.resultados.getIr());
                break;
            case 3:
                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getTesticuloder());
                field5.setText(sihic.SihicApp.resultados.getTesticuloizq());

                break;
            case 4:

                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion_corta());
                field2.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getRinonder());
                field5.setText(sihic.SihicApp.resultados.getRinosizq());

                break;
            case 5:

                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field2.setText(sihic.SihicApp.resultados.getDescripcion_corta());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getArcuatavelmaxima());
                field5.setText(sihic.SihicApp.resultados.getArcuatavelminima());
                field6.setText(sihic.SihicApp.resultados.getArcuatair());
                field7.setText(sihic.SihicApp.resultados.getSegmentariaelmaxima());
                field8.setText(sihic.SihicApp.resultados.getSegmentariavelminima());
                field9.setText(sihic.SihicApp.resultados.getSegmentariair());
                field10.setText(sihic.SihicApp.resultados.getBulillarvelmaxima());
                field11.setText(sihic.SihicApp.resultados.getBulillarvelminima());
                field12.setText(sihic.SihicApp.resultados.getBulillarir());
                field13.setText(sihic.SihicApp.resultados.getAortavelmaxima());
                field14.setText(sihic.SihicApp.resultados.getAortavelminima());
                field15.setText(sihic.SihicApp.resultados.getAortair());

                break;
            case 6:

                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getUtero());
                field5.setText(sihic.SihicApp.resultados.getPelv_endometrio());
                field6.setText(sihic.SihicApp.resultados.getPelv_ovaderecho());
                field7.setText(sihic.SihicApp.resultados.getPelv_ovaizquierdo());

                break;
            case 7:

                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion_corta());
                field2.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getFur());
                field5.setText(sihic.SihicApp.resultados.getFpp());
                field6.setText(sihic.SihicApp.resultados.getUtero());
                field7.setText(sihic.SihicApp.resultados.getDbp());
                field8.setText(sihic.SihicApp.resultados.getCc());
                field9.setText(sihic.SihicApp.resultados.getCa());
                field10.setText(sihic.SihicApp.resultados.getLf());
                field11.setText(sihic.SihicApp.resultados.getPeso());
                field12.setText(sihic.SihicApp.resultados.getPlacenta());
                field13.setText(sihic.SihicApp.resultados.getLiquidoamniotico());
                field14.setText(sihic.SihicApp.resultados.getCervix());
                break;
            case 8:
                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion_corta());
                field2.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getFet_artuterinaindresist());
                field5.setText(sihic.SihicApp.resultados.getFet_artuterinaindpulsati());
                field6.setText(sihic.SihicApp.resultados.getFet_artumbilicalindresist());
                field7.setText(sihic.SihicApp.resultados.getFet_artumbilicalindpulsati());
                field8.setText(sihic.SihicApp.resultados.getFet_artcerebralindresist());
                field9.setText(sihic.SihicApp.resultados.getFet_artcerebralindpulsati());
                field10.setText(sihic.SihicApp.resultados.getFet_aortoracicaindresist());
                field11.setText(sihic.SihicApp.resultados.getFet_aortoracicaindpulsati());
                break;

            case 9:
                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getDopmieminf_femoralcomun_indresist());
                field5.setText(sihic.SihicApp.resultados.getDopmieminf_femoralcomun_velmin());
                field6.setText(sihic.SihicApp.resultados.getDopmieminf_femoralcomun_indresist());
                field7.setText(sihic.SihicApp.resultados.getDopmieminf_femoralsuperf_velmax());
                field8.setText(sihic.SihicApp.resultados.getDopmieminf_femoralsuperf_velmin());
                field9.setText(sihic.SihicApp.resultados.getDopmieminf_femoralsuperf_indresist());
                field10.setText(sihic.SihicApp.resultados.getDopmieminf_poplitea_velmax());
                field11.setText(sihic.SihicApp.resultados.getDopmieminf_poplitea_velmin());
                field12.setText(sihic.SihicApp.resultados.getDopmieminf_poplitea_indresist());
                field13.setText(sihic.SihicApp.resultados.getDopmieminf_tibialanterior_velmax());
                field14.setText(sihic.SihicApp.resultados.getDopmieminf_tibialanterior_velmin());
                field15.setText(sihic.SihicApp.resultados.getDopmieminf_tibialanterior_indresist());
                field16.setText(sihic.SihicApp.resultados.getDopmieminf_tibialposterior_velmax());
                field17.setText(sihic.SihicApp.resultados.getDopmieminf_tibialposterior_velmin());
                field18.setText(sihic.SihicApp.resultados.getDopmieminf_tibialposterior_indresist());
                field19.setText(sihic.SihicApp.resultados.getDopmieminf_pedia_velmax());
                field20.setText(sihic.SihicApp.resultados.getDopmieminf_pedia_velmin());
                field21.setText(sihic.SihicApp.resultados.getDopmieminf_pedia_indresist());
                break;
            case 10:

                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDopvemieminf_ldersvs());
                field4.setText(sihic.SihicApp.resultados.getDopvemieminf_ldersvp());
                field2.setText(sihic.SihicApp.resultados.getDopvemieminf_lizqsvs());
                field5.setText(sihic.SihicApp.resultados.getDopvemieminf_lizqsvp());
                field3.setText(sihic.SihicApp.resultados.getOpinion());

                break;
            case 11:
                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getDopartmieminf_lderafc());
                field5.setText(sihic.SihicApp.resultados.getDopartmieminf_lderafs());
                field6.setText(sihic.SihicApp.resultados.getDopartmieminf_lderapoplitea());
                field7.setText(sihic.SihicApp.resultados.getDopartmieminf_lderata());
                field8.setText(sihic.SihicApp.resultados.getDopartmieminf_lderatp());
                field9.setText(sihic.SihicApp.resultados.getDopartmieminf_lizqafc());
                field10.setText(sihic.SihicApp.resultados.getDopartmieminf_lizqafs());
                field11.setText(sihic.SihicApp.resultados.getDopartmieminf_lizqapoplitea());
                field12.setText(sihic.SihicApp.resultados.getDopartmieminf_lizqata());
                field13.setText(sihic.SihicApp.resultados.getDopartmieminf_lizqatp());

                break;
            case 12:

                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                field4.setText(sihic.SihicApp.resultados.getDopvemieminf_ldervenafemoralc());
                field5.setText(sihic.SihicApp.resultados.getDopvemieminf_ldervenafemoralsp());
                field6.setText(sihic.SihicApp.resultados.getDopvemieminf_ldercayadosafenainterna());
                field7.setText(sihic.SihicApp.resultados.getDopvemieminf_ldersafena());
                field8.setText(sihic.SihicApp.resultados.getDopvemieminf_ldervenapoplitea());
                field9.setText(sihic.SihicApp.resultados.getDopvemieminf_ldercayadosafenaexterna());
                field10.setText(sihic.SihicApp.resultados.getDopvemieminf_ldercayadosafenaexternaperforantes());
                field11.setText(sihic.SihicApp.resultados.getDopvemieminf_ldertibialposterior());
                field12.setText(sihic.SihicApp.resultados.getDopvemieminf_lizqvenafemoralc());
                field13.setText(sihic.SihicApp.resultados.getDopvemieminf_lizqvenafemoralsp());
                field14.setText(sihic.SihicApp.resultados.getDopvemieminf_lizqcayadosafenainterna());
                field15.setText(sihic.SihicApp.resultados.getDopvemieminf_lizqsafena());
                field16.setText(sihic.SihicApp.resultados.getDopvemieminf_lizqvenapoplitea());
                field17.setText(sihic.SihicApp.resultados.getDopvemieminf_lizqcayadosafenaexterna());
                field18.setText(sihic.SihicApp.resultados.getDopvemieminf_lizqcayadosafenaexternaperforantes());
                field19.setText(sihic.SihicApp.resultados.getDopvemieminf_lizqtibialposterior());
                break;
            case 13:
                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getProcedimiento());
                field3.setText(sihic.SihicApp.resultados.getOpinion());

                break;
            case 14:

                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field2.setText(sihic.SihicApp.resultados.getProcedimiento());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                break;
            case 15:

                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field2.setText(sihic.SihicApp.resultados.getProcedimiento());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                break;
            case 16:

                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field2.setText(sihic.SihicApp.resultados.getProcedimiento());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                break;
            case 17:
                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field1.setText(sihic.SihicApp.resultados.getUtero());
                field2.setText(sihic.SihicApp.resultados.getDescripcion());
                field3.setText(sihic.SihicApp.resultados.getOpinion());
                cb_plantilla.getSelectionModel().select(sihic.SihicApp.resultados.getPlantilla());
                field0.setText(sihic.SihicApp.resultados.getExamensolicitado());
                field1.setText(sihic.SihicApp.resultados.getDescripcion());
                field2.setText(sihic.SihicApp.resultados.getProcedimiento());
                field4.setText(sihic.SihicApp.resultados.getDbp());
                field5.setText(sihic.SihicApp.resultados.getCc());
                field6.setText(sihic.SihicApp.resultados.getCa());
                field7.setText(sihic.SihicApp.resultados.getLf());
                field8.setText(sihic.SihicApp.resultados.getPeso());
                field9.setText(sihic.SihicApp.resultados.getPlacenta());
                field10.setText(sihic.SihicApp.resultados.getLiquidoamniotico());
                field11.setText(sihic.SihicApp.resultados.getCervix());
                field12.setText(sihic.SihicApp.resultados.getTonofetal());
                field13.setText(sihic.SihicApp.resultados.getMovfetal());
                field14.setText(sihic.SihicApp.resultados.getMovrespiratorio());
                field15.setText(sihic.SihicApp.resultados.getLiquidoamniotico2());

                break;
        }

    }

    public void set_datos_segun_Plantilla() throws ParseException {
        sihic.SihicApp.resultados.setFechaCreacion(sihic.util.UtilDate.colocarfechahora(new Date()));
        switch (cb_plantilla.getSelectionModel().getSelectedIndex()) {
            case 0:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setDescripcion(field1.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                break;
            case 1:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setUtero(field1.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion(field2.getText());
                sihic.SihicApp.resultados.setDbp(field4.getText());
                sihic.SihicApp.resultados.setCc(field5.getText());
                sihic.SihicApp.resultados.setCa(field6.getText());
                sihic.SihicApp.resultados.setLf(field7.getText());
                sihic.SihicApp.resultados.setPeso(field8.getText());
                sihic.SihicApp.resultados.setPlacenta(field9.getText());
                sihic.SihicApp.resultados.setLiquidoamniotico(field10.getText());
                sihic.SihicApp.resultados.setCervix(field11.getText());

                break;
            case 2:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion_corta(field1.getText());
                sihic.SihicApp.resultados.setDescripcion(field2.getText());
                sihic.SihicApp.resultados.setVelmax(field4.getText());
                sihic.SihicApp.resultados.setVelmin(field5.getText());
                sihic.SihicApp.resultados.setIr(field6.getText());

                break;
            case 3:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion(field1.getText());
                sihic.SihicApp.resultados.setTesticuloder(field4.getText());
                sihic.SihicApp.resultados.setTesticuloizq(field5.getText());

                break;
            case 4:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion_corta(field1.getText());
                sihic.SihicApp.resultados.setDescripcion(field2.getText());
                sihic.SihicApp.resultados.setRinonder(field4.getText());
                sihic.SihicApp.resultados.setRinosizq(field5.getText());

                break;
            case 5:

                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion(field1.getText());
                sihic.SihicApp.resultados.setDescripcion_corta(field2.getText());
                sihic.SihicApp.resultados.setArcuatavelmaxima(field4.getText());
                sihic.SihicApp.resultados.setArcuatavelminima(field5.getText());
                sihic.SihicApp.resultados.setArcuatair(field6.getText());
                sihic.SihicApp.resultados.setSegmentariaelmaxima(field7.getText());
                sihic.SihicApp.resultados.setSegmentariavelminima(field8.getText());
                sihic.SihicApp.resultados.setSegmentariair(field9.getText());
                sihic.SihicApp.resultados.setBulillarvelmaxima(field10.getText());
                sihic.SihicApp.resultados.setBulillarvelminima(field11.getText());
                sihic.SihicApp.resultados.setBulillarir(field12.getText());
                sihic.SihicApp.resultados.setAortavelmaxima(field13.getText());
                sihic.SihicApp.resultados.setAortavelminima(field14.getText());
                sihic.SihicApp.resultados.setAortair(field15.getText());

                break;
            case 6:

                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion(field1.getText());
                sihic.SihicApp.resultados.setUtero(field4.getText());
                sihic.SihicApp.resultados.setPelv_endometrio(field5.getText());
                sihic.SihicApp.resultados.setPelv_ovaderecho(field6.getText());
                sihic.SihicApp.resultados.setPelv_ovaizquierdo(field7.getText());
                break;
            case 7:

                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion_corta(field1.getText());
                sihic.SihicApp.resultados.setFur(field4.getText());
                sihic.SihicApp.resultados.setFpp(field5.getText());
                sihic.SihicApp.resultados.setUtero(field6.getText());
                sihic.SihicApp.resultados.setDbp(field7.getText());
                sihic.SihicApp.resultados.setCc(field8.getText());
                sihic.SihicApp.resultados.setCa(field9.getText());
                sihic.SihicApp.resultados.setLf(field10.getText());
                sihic.SihicApp.resultados.setPeso(field11.getText());
                sihic.SihicApp.resultados.setPlacenta(field12.getText());
                sihic.SihicApp.resultados.setLiquidoamniotico(field13.getText());
                sihic.SihicApp.resultados.setCervix(field14.getText());

                break;
            case 8:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion_corta(field1.getText());
                sihic.SihicApp.resultados.setDescripcion(field2.getText());
                sihic.SihicApp.resultados.setFet_artuterinaindresist(field4.getText());
                sihic.SihicApp.resultados.setFet_artuterinaindpulsati(field5.getText());
                sihic.SihicApp.resultados.setFet_artumbilicalindresist(field6.getText());
                sihic.SihicApp.resultados.setFet_artumbilicalindpulsati(field7.getText());
                sihic.SihicApp.resultados.setFet_artcerebralindresist(field8.getText());
                sihic.SihicApp.resultados.setFet_artcerebralindpulsati(field9.getText());
                sihic.SihicApp.resultados.setFet_aortoracicaindresist(field10.getText());
                sihic.SihicApp.resultados.setFet_aortoracicaindpulsati(field11.getText());
                break;
            case 9:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion(field1.getText());
                sihic.SihicApp.resultados.setDopmieminf_femoralcomun_indresist(field4.getText());
                sihic.SihicApp.resultados.setDopmieminf_femoralcomun_velmin(field5.getText());
                sihic.SihicApp.resultados.setDopmieminf_femoralcomun_indresist(field6.getText());
                sihic.SihicApp.resultados.setDopmieminf_femoralsuperf_velmax(field7.getText());
                sihic.SihicApp.resultados.setDopmieminf_femoralsuperf_velmin(field8.getText());
                sihic.SihicApp.resultados.setDopmieminf_femoralsuperf_indresist(field9.getText());
                sihic.SihicApp.resultados.setDopmieminf_poplitea_velmax(field10.getText());
                sihic.SihicApp.resultados.setDopmieminf_poplitea_velmin(field11.getText());
                sihic.SihicApp.resultados.setDopmieminf_poplitea_indresist(field12.getText());
                sihic.SihicApp.resultados.setDopmieminf_tibialanterior_velmax(field13.getText());
                sihic.SihicApp.resultados.setDopmieminf_tibialanterior_velmin(field14.getText());
                sihic.SihicApp.resultados.setDopmieminf_tibialanterior_indresist(field15.getText());
                sihic.SihicApp.resultados.setDopmieminf_tibialposterior_velmax(field16.getText());
                sihic.SihicApp.resultados.setDopmieminf_tibialposterior_velmin(field17.getText());
                sihic.SihicApp.resultados.setDopmieminf_tibialposterior_indresist(field18.getText());
                sihic.SihicApp.resultados.setDopmieminf_pedia_velmax(field19.getText());
                sihic.SihicApp.resultados.setDopmieminf_pedia_velmin(field20.getText());
                sihic.SihicApp.resultados.setDopmieminf_pedia_indresist(field21.getText());
                break;
            case 10:

                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDopvemieminf_ldersvs(field1.getText());
                sihic.SihicApp.resultados.setDopvemieminf_ldersvp(field4.getText());
                sihic.SihicApp.resultados.setDopvemieminf_lizqsvs(field2.getText());
                sihic.SihicApp.resultados.setDopvemieminf_lizqsvp(field5.getText());

                break;
            case 11:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion(field1.getText());
                sihic.SihicApp.resultados.setDopartmieminf_lderafc(field4.getText());
                sihic.SihicApp.resultados.setDopartmieminf_lderafs(field5.getText());
                sihic.SihicApp.resultados.setDopartmieminf_lderapoplitea(field6.getText());
                sihic.SihicApp.resultados.setDopartmieminf_lderata(field7.getText());
                sihic.SihicApp.resultados.setDopartmieminf_lderatp(field8.getText());
                sihic.SihicApp.resultados.setDopartmieminf_lizqafc(field9.getText());
                sihic.SihicApp.resultados.setDopartmieminf_lizqafs(field10.getText());
                sihic.SihicApp.resultados.setDopartmieminf_lizqapoplitea(field11.getText());
                sihic.SihicApp.resultados.setDopartmieminf_lizqata(field12.getText());
                sihic.SihicApp.resultados.setDopartmieminf_lizqatp(field13.getText());

                break;
            case 12:

                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setDescripcion(field1.getText());
                sihic.SihicApp.resultados.setDopvemieminf_ldervenafemoralc(field4.getText());
                sihic.SihicApp.resultados.setDopvemieminf_ldervenafemoralsp(field5.getText());
                sihic.SihicApp.resultados.setDopvemieminf_ldercayadosafenainterna(field6.getText());
                sihic.SihicApp.resultados.setDopvemieminf_ldersafena(field7.getText());
                sihic.SihicApp.resultados.setDopvemieminf_ldervenapoplitea(field8.getText());
                sihic.SihicApp.resultados.setDopvemieminf_ldercayadosafenaexterna(field9.getText());
                sihic.SihicApp.resultados.setDopvemieminf_ldercayadosafenaexternaperforantes(field10.getText());
                sihic.SihicApp.resultados.setDopvemieminf_ldertibialposterior(field11.getText());
                sihic.SihicApp.resultados.setDopvemieminf_lizqvenafemoralc(field12.getText());
                sihic.SihicApp.resultados.setDopvemieminf_lizqvenafemoralsp(field13.getText());
                sihic.SihicApp.resultados.setDopvemieminf_lizqcayadosafenainterna(field14.getText());
                sihic.SihicApp.resultados.setDopvemieminf_lizqsafena(field15.getText());
                sihic.SihicApp.resultados.setDopvemieminf_lizqvenapoplitea(field16.getText());
                sihic.SihicApp.resultados.setDopvemieminf_lizqcayadosafenaexterna(field17.getText());
                sihic.SihicApp.resultados.setDopvemieminf_lizqcayadosafenaexternaperforantes(field18.getText());
                sihic.SihicApp.resultados.setDopvemieminf_lizqtibialposterior(field19.getText());

                break;
            case 13:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setProcedimiento(field1.getText());

                break;
            case 14:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setProcedimiento(field2.getText());
                sihic.SihicApp.resultados.setDescripcion(field1.getText());

                break;
            case 15:

                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setProcedimiento(field2.getText());
                sihic.SihicApp.resultados.setDescripcion(field1.getText());

                break;
            case 16:

                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setProcedimiento(field2.getText());
                sihic.SihicApp.resultados.setDescripcion(field1.getText());

                break;
            case 17:
                sihic.SihicApp.resultados.setExamensolicitado(field0.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setPlantilla(cb_plantilla.getSelectionModel().getSelectedIndex());
                sihic.SihicApp.resultados.setUtero(field1.getText());
                sihic.SihicApp.resultados.setDescripcion(field2.getText());
                sihic.SihicApp.resultados.setOpinion(field3.getText());
                sihic.SihicApp.resultados.setDbp(field4.getText());
                sihic.SihicApp.resultados.setCc(field5.getText());
                sihic.SihicApp.resultados.setCa(field6.getText());
                sihic.SihicApp.resultados.setLf(field7.getText());
                sihic.SihicApp.resultados.setPeso(field8.getText());
                sihic.SihicApp.resultados.setPlacenta(field9.getText());
                sihic.SihicApp.resultados.setLiquidoamniotico(field10.getText());
                sihic.SihicApp.resultados.setCervix(field11.getText());
                sihic.SihicApp.resultados.setTonofetal(field12.getText());
                sihic.SihicApp.resultados.setMovfetal(field13.getText());
                sihic.SihicApp.resultados.setMovrespiratorio(field14.getText());
                sihic.SihicApp.resultados.setLiquidoamniotico2(field15.getText());

                break;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
