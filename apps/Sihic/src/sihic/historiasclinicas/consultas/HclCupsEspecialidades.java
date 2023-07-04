package sihic.historiasclinicas.consultas;

import java.io.IOException;
import java.text.ParseException;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.AgnConsultoriosProcedimientos;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.contabilidad.FindServicios;
import sihic.control.SearchBox;
import sihic.controlador.HclCupsControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

public class HclCupsEspecialidades extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;

    private GridPane gp_generic;

    private Label la_servicio;
    private Label la_especialidad;
    private HclCupsControllerClient hclCupsControllerClient;
    private Button save;

    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private ImageView imageView;
    private FindServicios findProduct;
    private SearchBox sb_servicio = new SearchBox();
    private SearchPopover sp_servicio;

    public Parent createContent() throws IOException, ParseException {
        sp_servicio = new SearchPopover(sb_servicio, new PageBrowser(), SihicApp.s_kardex, true);
        LoadChoiceBoxGeneral.getCb_agnmedicos().getSelectionModel().selectedIndexProperty().addListener(this::indexChangedMedicos);
        LoadChoiceBoxGeneral.getCb_agnespecialidades().setMinWidth(300);
        sb_servicio.setMaxWidth(400);
        sb_servicio.setPromptText("Procedimiento a solicitar");
        sb_servicio.setOnAction(e -> {
            LoadChoiceBoxGeneral.getCb_agnconsultorios().requestFocus();
        });
        hclCupsControllerClient = new HclCupsControllerClient();

        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();

        la_servicio = new Label("Servicio:");
        la_especialidad = new Label("Especialidad:");
        LoadChoiceBoxGeneral.getCb_agnespecialidades().getProperties().put("requerido", true);
        LoadChoiceBoxGeneral.getCb_agnespecialidades().setMaxWidth(400);
        sb_servicio.getProperties().put("requerido", true);
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        save = new Button("", imageView);
        hb_botones = new HBox(5);

        save.setOnKeyPressed(e -> {

            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();

                } catch (InterruptedException ex) {
                }
            }

        });
        save.setOnAction(e -> {

            try {
                save();

            } catch (InterruptedException ex) {
                Logger.getLogger(FAgnConsultoriosProcedimientos.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save);
        gp_generic = new GridPane();
        gp_generic.addRow(0, la_servicio, sb_servicio);
        gp_generic.addRow(1, la_especialidad, LoadChoiceBoxGeneral.getCb_agnespecialidades());
        gp_generic.add(hb_botones, 0, 2, 2, 1);
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
        Gp_Message.setMaxSize(300, 30);
        L_Message = new Label();
        gp_generic.add(sp_servicio, 0, 2, 2, 3);

        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        //gp_generic.setMaxWidth(650);
        //gp_generic.setMaxHeight(50);
        //stack.setMaxWidth(650);
        //stack.setMaxHeight(50);
        gp_generic.autosize();
        stack.autosize();
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic, Gp_Message);

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

                SihicApp.productoControllerClient.update();

                L_Message.setText("Especialida Agregada");

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

    private void set_datos() {
        SihicApp.s_producto = SihicApp.s_kardex.getProducto();
        SihicApp.s_producto.setAgnEspecialidades(LoadChoiceBoxGeneral.getV_agnespecialidades().get(LoadChoiceBoxGeneral.getCb_agnespecialidades().getSelectionModel().getSelectedIndex()));

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

                    if ((general.getSelectionModel().getSelectedIndex() < 0) && (boolean) general.getProperties().get("requerido") == true) {
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

    public void indexChangedMedicos(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

    }

    public static void main(String[] args) {
        launch(args);
    }

}
