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
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

public class FAgnConsultoriosProcedimientos extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private AgnConsultoriosProcedimientos agnConsultoriosProcedimientos;
    private GridPane gp_generic;
    private Label la_medico;
    private Label la_servicio;
    private Label la_consultorio;
    private HclHistoriasClinicasControllerClient agnconsultoriosprocedimientosControllerClient;
    private Button save;
    private Button nuevo;
    private Button especialidad;
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
        LoadChoiceBoxGeneral.getCb_agnconsultorios().getSelectionModel().selectedIndexProperty().addListener(this::indexChangedConsultorios);
        LoadChoiceBoxGeneral.getCb_agnmedicos().setMinWidth(300);
        LoadChoiceBoxGeneral.getCb_agnconsultorios().setMinWidth(300);
        sb_servicio.setMinWidth(400);
        sb_servicio.setPromptText("Procedimiento a solicitar");

        sb_servicio.setOnAction(e -> {
            LoadChoiceBoxGeneral.getCb_agnconsultorios().requestFocus();
        });
        agnconsultoriosprocedimientosControllerClient = new HclHistoriasClinicasControllerClient();
        agnConsultoriosProcedimientos = new AgnConsultoriosProcedimientos();
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        la_medico = new Label("Médico:");
        la_servicio = new Label("Servicio:");
        la_consultorio = new Label("Consultorio:");
        LoadChoiceBoxGeneral.getCb_agnmedicos().getProperties().put("requerido", true);
        LoadChoiceBoxGeneral.getCb_agnconsultorios().getProperties().put("requerido", true);
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
                    nuevo.requestFocus();
                } catch (InterruptedException ex) {
                }
            }

        });
        save.setOnAction(e -> {

            try {
                save();
                nuevo.requestFocus();
            } catch (InterruptedException ex) {
                Logger.getLogger(FAgnConsultoriosProcedimientos.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        nuevo = new Button("", imageView);
        nuevo.setOnKeyPressed(e -> {

            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    nuevahora();
                } catch (ParseException ex) {
                    Logger.getLogger(FAgnConsultoriosProcedimientos.class.getName()).log(Level.SEVERE, null, ex);
                }
                LoadChoiceBoxGeneral.getCb_agnmedicos().requestFocus();
            }

        });

        nuevo.setOnAction(e -> {
            try {
                nuevahora();
            } catch (ParseException ex) {
                Logger.getLogger(FAgnConsultoriosProcedimientos.class.getName()).log(Level.SEVERE, null, ex);
            }
            LoadChoiceBoxGeneral.getCb_agnmedicos().requestFocus();

        });

        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);
        gp_generic = new GridPane();
        gp_generic.addRow(0, la_medico, LoadChoiceBoxGeneral.getCb_agnmedicos());
        gp_generic.addRow(1, la_servicio, sb_servicio);
        gp_generic.addRow(2, la_consultorio, LoadChoiceBoxGeneral.getCb_agnconsultorios());
        gp_generic.add(hb_botones, 0, 4, 2, 1);
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
            set_datos();
            try {
                if (agnConsultoriosProcedimientos.getId() == null) {
                    agnconsultoriosprocedimientosControllerClient.saveConsultoriosProcedimientos(agnConsultoriosProcedimientos);

                    L_Message.setText("Registro Almacenado");
                } else {
                    agnConsultoriosProcedimientos = agnconsultoriosprocedimientosControllerClient.updateConsultoriosProcedimientos(agnConsultoriosProcedimientos);
                    L_Message.setText("Registro modificado");
                }

                AdminAgnConsultoriosProcedimientos.getListConsultoriosProcedimientos();
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
        agnConsultoriosProcedimientos.setAgnMedicos(LoadChoiceBoxGeneral.getV_agnmedicos().get(LoadChoiceBoxGeneral.getCb_agnmedicos().getSelectionModel().getSelectedIndex()));
        agnConsultoriosProcedimientos.setAgnConsultorios(LoadChoiceBoxGeneral.getV_agnconsultorios().get(LoadChoiceBoxGeneral.getCb_agnconsultorios().getSelectionModel().getSelectedIndex()));
        agnConsultoriosProcedimientos.setServicios_id(SihicApp.s_kardex.getProducto());

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

    public void crearoeditar() throws ParseException {
        agnConsultoriosProcedimientos = AdminAgnConsultoriosProcedimientos.getAgnconsultoriosprocedimientos();
        if (agnConsultoriosProcedimientos.getId() != null) {
            LoadChoiceBoxGeneral.getCb_agnmedicos().getSelectionModel().select(LoadChoiceBoxGeneral.getV_agnmedicos().indexOf(agnConsultoriosProcedimientos.getAgnMedicos()));
            LoadChoiceBoxGeneral.getCb_agnconsultorios().getSelectionModel().select(LoadChoiceBoxGeneral.getV_agnconsultorios().indexOf(agnConsultoriosProcedimientos.getAgnConsultorios()));
            SihicApp.s_kardex.setProducto(agnConsultoriosProcedimientos.getServicios_id());
            sb_servicio.setText(SihicApp.s_kardex.getProducto().getHclCups().getCodigo() + " " + SihicApp.s_kardex.getProducto().getNombre());

        } else {
            nuevahora();
        }

    }

    public void nuevahora() throws ParseException {
        agnConsultoriosProcedimientos = null;
        agnConsultoriosProcedimientos = new AgnConsultoriosProcedimientos();
        LoadChoiceBoxGeneral.getCb_agnmedicos().getSelectionModel().select(-1);
        LoadChoiceBoxGeneral.getCb_agnconsultorios().getSelectionModel().select(-1);
        sb_servicio.setText("");

    }

    public void indexChangedMedicos(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void indexChangedConsultorios(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        nuevo.requestFocus();
    }
}
