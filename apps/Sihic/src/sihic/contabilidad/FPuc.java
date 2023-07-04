package sihic.contabilidad;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.Puc;
import sihic.SihicApp;
import sihic.controlador.ConPucControllerClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FPuc extends Application {

    StackPane stack;
    private Puc conPuc;
    private GridPane gp_conpuc;
    private Label la_nombre;
    private Label la_codigo;
    private Label la_descripcion;
    private TextField codigo;
    private TextField codigopadre = new TextField();
    private TextField nombre;
    private TextField descripcion;
    private Label la_porc_base=new Label("% Sobre Base:");
    private TextField porc_base=new TextField() ;
    private ConPucControllerClient conpucControllerClient;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private ImageView imageView;
    private ObservableList ol_conpuc = FXCollections.observableArrayList();
    private List<Puc> l_conpuc;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private ChoiceBox cb_natcuenta=new ChoiceBox();
    private ChoiceBox cb_tipocuenta=new ChoiceBox();
    public Parent createContent() throws IOException {
        cb_natcuenta.getItems().add("Debito");
        cb_natcuenta.getItems().add("Crédito");
        cb_tipocuenta.getItems().add("Corriente");
        cb_tipocuenta.getItems().add("No Corriente");
        cb_tipocuenta.getItems().add("No definida");
        conpucControllerClient = new ConPucControllerClient();
        imageView = new ImageView("/images/new.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
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
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        conPuc = new Puc();
        la_codigo = new Label("Código(Agregue los Digitos del Hijo:");
        la_nombre = new Label("Nombre:");
        la_descripcion = new Label("Descripción:");
        codigo = new TextField();
        nombre = new TextField();
        nombre.setMinWidth(500);
        descripcion = new TextField();
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
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
        gp_conpuc = new GridPane();
        gp_conpuc.setMinSize(510, 200);
        gp_conpuc.addRow(0, la_codigo, codigo);
        gp_conpuc.addRow(1, la_nombre, nombre);
        gp_conpuc.addRow(2, la_descripcion, descripcion);
        gp_conpuc.addRow(3, la_porc_base, porc_base);
        gp_conpuc.addRow(4, new Label("Nat. Cuenta:"), cb_natcuenta);
        gp_conpuc.addRow(5, new Label("Tipo Cuenta:"), cb_tipocuenta);
        gp_conpuc.add(hb_botones, 0, 6, 2, 1);

        GridPane.setHalignment(hb_botones, HPos.CENTER);
        //gp_hclformulacionesmedicas.getStylesheets().add(SihicApp.hojaestilos);
        //gp_hclformulacionesmedicas.getStyleClass().add("background");
        gp_conpuc.setHgap(5);
        gp_conpuc.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_conpuc.getStylesheets().add(SihicApp.hojaestilos);
        gp_conpuc.getStyleClass().add("background");
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_conpuc.getLayoutBounds().getHeight(), gp_conpuc.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.getChildren().addAll(gp_conpuc, Gp_Message);
        crearoeditar();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void nuevo() {
        SihicApp.conPuc = null;
        SihicApp.conPuc = new Puc();
        codigo.setText(AdminPuc.getConPuc().getCodigo());
        nombre.setText("");
        descripcion.setText("");
        porc_base.setText("0");
    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {
                if (SihicApp.conPuc.getId() == null) {
                    conpucControllerClient.create();
                } else {
                    conpucControllerClient.update();
                }
                L_Message.setText("Registro Almacenado");
                AdminPuc.getPuc();
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

        SihicApp.conPuc.setCodigo(codigo.getText());
        SihicApp.conPuc.setDescripcion(descripcion.getText());
        SihicApp.conPuc.setNombre(nombre.getText());
         SihicApp.conPuc.setPorc_base(new BigDecimal(porc_base.getText()));
        
        if (SihicApp.conPuc.getId() == null) {
            SihicApp.conPuc.setConpuc_id(AdminPuc.getConPuc());
        }

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_conpuc.getChildren().toArray()) {
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
                for (Object n : gp_conpuc.getChildren().toArray()) {
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

        if (SihicApp.conPuc != null) {
            codigo.setText(String.valueOf(SihicApp.conPuc.getCodigo()));
            nombre.setText(String.valueOf(SihicApp.conPuc.getNombre()));
            descripcion.setText(conPuc.getDescripcion());
            try{
            porc_base.setText(SihicApp.conPuc.getPorc_base().toString());
            }catch(Exception e)
            {
               porc_base.setText("0");  
            }
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
