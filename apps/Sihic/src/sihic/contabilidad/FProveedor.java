package sihic.contabilidad;

import sihic.contabilidad.AdminProveedores;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.Proveedores;
import sihic.SihicApp;
import sihic.general.FGenPersonas;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FProveedor extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GridPane gp_generic = new GridPane();
    private TextField no_documento = new TextField();
    private TextField digitoverificacion = new TextField();
    private TextField nombre = new TextField();
    private TextField primer_apellido = new TextField();
    private TextField segundo_apellido = new TextField();
    private TextField razonsocial = new TextField();
    private ChoiceBox cb_tipopersona=new ChoiceBox();
    private ChoiceBox cb_tipodocumento=new ChoiceBox();
    private CheckBox responsableiva=new CheckBox();
    private TextField telefono = new TextField();
    private TextField dir = new TextField();
    private TextField email = new TextField();
    

    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private Button bu_tributaria;
    private ImageView imageView;
    private String appPathGeneric;
    private Class clzGeneric;
    private Object appClassGeneric;
    private Stage stageGeneric = new Stage(StageStyle.DECORATED);
    private Scene sceneGeneric = null;
    private Parent P_Generic;
    public Parent createContent() throws IOException {
        //***********************************************  
        cb_tipodocumento.getItems().addAll("Nit","Cédula de Ciudadanía");
        cb_tipopersona.getItems().addAll("Natural","Persona Jurídica");
        imageView = null;
        imageView = new ImageView("/images/tributaria.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        bu_tributaria = new Button("", imageView);
        bu_tributaria.setOnAction(e -> {
            try {
                showinformaciontributaria();
            } catch (Exception ex) {
                Logger.getLogger(FGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        no_documento = new TextField("");
        no_documento.getProperties().put("requerido", true);
        nombre = new TextField("");
        nombre.getProperties().put("requerido", true);
        nombre.setMinWidth(300);
        dir = new TextField("");
        telefono = new TextField("");
        telefono.getProperties().put("requerido", true);
        email = new TextField("");
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        save = new Button("", imageView);
        save.setTooltip(new Tooltip("Guardar Proveedor"));
        hb_botones = new HBox(2);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        nuevo = new Button("", imageView);
        nuevo.setTooltip(new Tooltip("Nuevo Proveedor"));
        nuevo.setOnAction(e -> {
            nuevo();
        });
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo,bu_tributaria);

// gridpane
        gp_generic = new GridPane();
        gp_generic.add(new Label("Tipo Documento: "), 0, 0);
        gp_generic.add(cb_tipodocumento, 1, 0);
        gp_generic.add(new Label("N° Documento: "), 0, 0);
        gp_generic.add(no_documento, 1, 0);
        gp_generic.add(new Label("Digito de  Verificación: "), 0, 0);
        gp_generic.add(digitoverificacion, 1, 0);
        gp_generic.add(new Label("Primer Apellido: "), 0, 1);
        gp_generic.add(primer_apellido, 1, 1);
        gp_generic.add(new Label("Segundo Apellido: "), 0, 1);
        gp_generic.add(segundo_apellido, 1, 1);
        gp_generic.add(new Label("Nombres: "), 0, 1);
        gp_generic.add(nombre, 1, 1);
        gp_generic.add(new Label("Razón Social: "), 0, 1);
        gp_generic.add(razonsocial, 1, 1);
        gp_generic.add(new Label("Responsable Iva: "), 0, 1);
        gp_generic.add(responsableiva, 1, 1);
        gp_generic.add(new Label("Dirección: "), 0, 2);
        gp_generic.add(dir, 1, 2);
        gp_generic.add(new Label("Telefono: "), 0, 3);
        gp_generic.add(telefono, 1, 3);
        gp_generic.add(new Label("Email: "), 0, 4);
        gp_generic.add(email, 1, 4);
        gp_generic.add(hb_botones, 0, 5, 2, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinWidth(gp_generic.getLayoutBounds().getWidth());
        Gp_Message.setMaxHeight(40);
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

    private void nuevo() {
        SihicApp.s_proveedores = null;
        SihicApp.s_proveedores = new Proveedores();
        empty_field();

    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {

                if (SihicApp.s_proveedores.getId() == null) {
                    SihicApp.proveedoresControllerClient.create();
                    L_Message.setText("Registro Almacenado");
                } else {
                    SihicApp.proveedoresControllerClient.update();
                    L_Message.setText("Registro modificado");
                }
                AdminProveedores.getRecords();
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

        SihicApp.s_proveedores.setNo_ident(no_documento.getText().trim());
        SihicApp.s_proveedores.setNombre(nombre.getText());
        SihicApp.s_proveedores.setDir1(dir.getText());
        SihicApp.s_proveedores.setCelular(telefono.getText());
        SihicApp.s_proveedores.setEmail(email.getText());
        //cargamos la imagen

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getLength() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
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
// A change listener to track the change in selected index

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

    }

    public void crearoeditar() {

        if (SihicApp.s_proveedores != null) {

            getDatos();
        } else {
            SihicApp.s_proveedores = new Proveedores();
            nuevo();
        }

    }

    private void empty_field() {
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    general.getSelectionModel().select(-1);

                } else {
                    if (n.getClass() == TextArea.class) {
                        TextArea general = (TextArea) n;

                        general.setText("");

                    } else {
                        if (n.getClass() == RadioButton.class) {
                            RadioButton general = (RadioButton) n;

                            general.setSelected(false);

                        } else {
                            if (n.getClass() == CheckBox.class) {
                                CheckBox general = (CheckBox) n;

                                general.setSelected(false);

                            }
                        }
                    }
                }
            }
        }

    }

    private void getDatos() {
        no_documento.setText(SihicApp.s_proveedores.getNo_ident());
        nombre.setText(SihicApp.s_proveedores.getNombre());
        dir.setText(SihicApp.s_proveedores.getDir1());
        telefono.setText(SihicApp.s_proveedores.getCelular());
        email.setText(SihicApp.s_proveedores.getEmail());
    }
 private void showinformaciontributaria() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        if (SihicApp.s_proveedores.getId() != null) {
            SihicApp.s_informacionTributaria = SihicApp.s_proveedores.getInformacionTributaria();
        } 
        else {
            SihicApp.s_informacionTributaria = null;
        }
        appPathGeneric = "sihic.contabilidad.FInformacionTributaria";
        clzGeneric = null;
        clzGeneric = Class.forName(appPathGeneric);
        appClassGeneric = clzGeneric.newInstance();
        P_Generic = null;
        P_Generic = (Parent) clzGeneric.getMethod("createContent").invoke(appClassGeneric);
        sceneGeneric = null;
        sceneGeneric = new Scene(P_Generic, Color.TRANSPARENT);
        stageGeneric.setTitle("Información Tributaria");
        if (stageGeneric.isShowing()) {
            stageGeneric.close();
        }

//set scene to stage
        stageGeneric.sizeToScene();

        //center stage on screen
        stageGeneric.centerOnScreen();
        stageGeneric.setScene(sceneGeneric);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageGeneric.show();
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
