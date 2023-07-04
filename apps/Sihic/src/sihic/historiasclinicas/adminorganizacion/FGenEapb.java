package sihic.historiasclinicas.adminorganizacion;
import sihic.SihicApp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import modelo.GenEapb;
import sihic.controlador.GenEapbControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FGenEapb extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;

    private GenEapb genEapb;
    private GridPane gp_generic;
    private Label la_nit;
    private TextField nit;
    private Label la_codigo;
    private Label la_codigomov;
    private Label la_codigoregmov;
    private Label la_codigoregcont;
    private Label la_nombre;
    private TextField codigo;
    private TextField codigomov;
    private TextField codigoregmov;
    private TextField codigoregcont; 
    private TextField nombre;
    private Label la_telefono;
    private TextField telefono;
    private Label la_direccion;
    private TextField direccion;
    private GenEapbControllerClient genEapbControllerClient;
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
    private Label la_tipousuario;
    private ChoiceBox tipousuario;

    public Parent createContent() throws IOException {
        genEapbControllerClient = new GenEapbControllerClient();
        genEapb = new GenEapb();

        logo = new ImageView();
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();

        la_codigo = new Label("Código:");
        la_codigomov = new Label("Código Movilidad:");
        la_codigoregmov = new Label("Código Regional Mov:");
        la_codigoregcont = new Label("Código Regional Cont:"); 
        la_nit = new Label("Nit:");
        la_nombre = new Label("Nombre:");
        la_telefono = new Label("Telefono:");
        la_direccion = new Label("Dirección:");
        la_tipousuario = new Label("Tipos de Régimen: ");
        tipousuario = new ChoiceBox();
        tipousuario.setMaxWidth(300);
        codigo = new TextField();
        codigo.getProperties().put("requerido", true);
        codigomov = new TextField();
        codigoregcont = new TextField();
        codigoregmov = new TextField();
        nit = new TextField();
        nit.getProperties().put("requerido", true);
        codigo.getProperties().put("requerido", true);
        nombre = new TextField();
        nombre.getProperties().put("requerido", true);
        LoadChoiceBoxGeneral.getCb_gentiposusuarios().getProperties().put("requerido", true);
        direccion = new TextField();
        telefono = new TextField();
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        save = new Button("", imageView);
        hb_botones = new HBox(5);

        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        imageView = null;
        imageView = new ImageView("/images/insert.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        nuevo = new Button("", imageView);

        nuevo.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });

        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);

// gridpane
        gp_generic = new GridPane();
        gp_generic.add(la_tipousuario, 0, 0);
        gp_generic.add(LoadChoiceBoxGeneral.getCb_gentiposusuarios(), 1, 0);
        gp_generic.add(la_codigo, 0, 1);
        gp_generic.add(codigo, 1, 1);
        gp_generic.add(la_codigomov, 0, 2);
        gp_generic.add(codigomov, 1, 2);
        gp_generic.add(la_codigoregcont, 0, 3);
        gp_generic.add(codigoregcont, 1, 3);
        gp_generic.add(la_codigoregmov, 0, 4);
        gp_generic.add(codigoregmov, 1, 4);
       
        gp_generic.add(la_nit, 0, 5);
        gp_generic.add(nit, 1, 5);
        gp_generic.add(la_nombre, 0, 6);
        gp_generic.add(nombre, 1, 6);
        gp_generic.add(la_telefono, 0, 7);
        gp_generic.add(telefono, 1, 7);
        gp_generic.add(la_direccion, 0, 8);
        gp_generic.add(direccion, 1, 8);
        gp_generic.add(hb_botones, 0, 9, 2, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        // gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        // gp_generic.getStyleClass().add("background");
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

    public void nuevo() {
        genEapb = null;
        genEapb = new GenEapb();
        nit.setText("");
        nombre.setText("");
        codigo.setText("");
        telefono.setText("");
        direccion.setText("");
    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            set_datos_oranizacion();
            try {

                if (genEapb.getId() == null) {
                    genEapbControllerClient.create(genEapb);
                    L_Message.setText("Registro Almacenado");
                } else {
                    genEapb = genEapbControllerClient.edit(genEapb);
                    L_Message.setText("Registro modificado");
                }
                AdminGenEapb.getListGenEapb();

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

    private void set_datos_oranizacion() {
        genEapb.setCodigo(codigo.getText().trim());
        genEapb.setCodigomovilidad(codigomov.getText().trim());
        genEapb.setCodigoregionalcont(codigoregcont.getText().trim());
        genEapb.setCodigoregionalmov(codigoregmov.getText().trim());
        genEapb.setNit(nit.getText().trim());
        genEapb.setNombre(nombre.getText());
        genEapb.setTelefono(telefono.getText());
        genEapb.setDireccion(direccion.getText());
        genEapb.setGenTiposUsuarios(LoadChoiceBoxGeneral.getV_gentiposusuarios().get(LoadChoiceBoxGeneral.getCb_gentiposusuarios().getSelectionModel().getSelectedIndex()));

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
        genEapb = AdminGenEapb.getGenEapb();
        if (genEapb != null) {
            nit.setText(genEapb.getNit());
            codigo.setText(genEapb.getCodigo());
            nombre.setText(genEapb.getNombre());
            telefono.setText(genEapb.getTelefono());
            direccion.setText(genEapb.getDireccion());
            LoadChoiceBoxGeneral.getCb_gentiposusuarios().getSelectionModel().select(LoadChoiceBoxGeneral.getV_gentiposusuarios().indexOf(genEapb.getGenTiposUsuarios()));
        } else {
            nuevo();
        }

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
