package sihic.historiasclinicas.adminorganizacion;

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
import javafx.geometry.Point2D;
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
import modelo.Puc;

import modelo.GenEapb;
import modelo.GenTiposUsuarios;
import modelo.GenUnidadesOrganizacion;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.controlador.GenEapbControllerClient;
import sihic.controlador.GenTiposUsuariosControllerClient;
import sihic.controlador.HclConsultasControllerClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FGenTiposUsuarios extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GenTiposUsuarios genTiposUsuarios;
    private GridPane gp_generic;
    private Label la_codigo;
    private Label la_nombre;
    private TextField codigo;
    private TextField nombre;
    private Label la_puc;
    private SearchBox puc = new SearchBox();
    private SearchPopover searchPopover;
    private GenTiposUsuariosControllerClient genTiposUsuariosControllerClient;
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
    private Parent parent;

    public Parent createContent() throws IOException {

        genTiposUsuariosControllerClient = new GenTiposUsuariosControllerClient();
        genTiposUsuarios = new GenTiposUsuarios();
        logo = new ImageView();
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();

        la_codigo = new Label("Código:");
        la_nombre = new Label("Tipo Regimen:");
        la_puc = new Label("Cuenta Puc:");

        codigo = new TextField();
        codigo.getProperties().put("requerido", true);
        codigo.getProperties().put("requerido", true);
        nombre = new TextField();
        nombre.getProperties().put("requerido", true);
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
            nuevo();
        });

        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);

// gridpane
        gp_generic = new GridPane();
        gp_generic.add(la_puc, 0, 0);
        gp_generic.add(puc, 1, 0);

        gp_generic.add(la_codigo, 0, 1);
        gp_generic.add(codigo, 1, 1);
        gp_generic.add(la_nombre, 0, 2);
        gp_generic.add(nombre, 1, 2);
        gp_generic.add(hb_botones, 0, 4, 2, 1);
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
        puc.setMinWidth(300);
        searchPopover = new SearchPopover(puc, new PageBrowser(), new Puc(), false);
        gp_generic.add(searchPopover, 1, 0, 1, 5);
        GridPane.setValignment(searchPopover, VPos.TOP);
        GridPane.setHalignment(searchPopover, HPos.LEFT);
        searchPopover.getStylesheets().add(SihicApp.hojaestilos);
        searchPopover.getSearchBox().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                codigo.requestFocus();
            }
        });
        //searchPopover.setLayoutX(500);
        //searchPopover.setLayoutY(150);

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
        genTiposUsuarios = null;
        genTiposUsuarios = new GenTiposUsuarios();
        nombre.setText("");
        codigo.setText("");
        puc.setText("");
    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {

                if (genTiposUsuarios.getId() == null) {
                    genTiposUsuariosControllerClient.create(genTiposUsuarios);
                    L_Message.setText("Registro Almacenado");
                } else {
                    genTiposUsuarios = genTiposUsuariosControllerClient.edit(genTiposUsuarios);
                    L_Message.setText("Registro modificado");
                }
                AdminGenTiposUsuarios.getListGenTiposUsuarios();

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
        genTiposUsuarios.setConPuc(SihicApp.conPuc);
        genTiposUsuarios.setCodigo(codigo.getText().trim());
        genTiposUsuarios.setNombre(nombre.getText());

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
        genTiposUsuarios = AdminGenTiposUsuarios.getGenTiposUsuarios();
        if (genTiposUsuarios != null) {

            codigo.setText(genTiposUsuarios.getCodigo());
            nombre.setText(genTiposUsuarios.getNombre());
            SihicApp.conPuc = genTiposUsuarios.getConPuc();
            puc.setText(SihicApp.conPuc.getCodigo() + " " + SihicApp.conPuc.getNombre());

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
