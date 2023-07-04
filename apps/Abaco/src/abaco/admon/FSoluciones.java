package abaco.admon;

import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
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
import message.EstadosMensaje;
import model.Solucion;
import abaco.controlador.SolucionControllerClient;
import com.aquafx_project.AquaFx;
import jfxtras.styles.jmetro8.JMetro;
import org.aerofx.AeroFX;

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
    EstadosMensaje estadosMensaje;
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
        solucion = new Solucion();

        la_nombre = new Label("Solución:");
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
        gp_generic.addRow(1, la_nombre, AbacoApp.cb_soluciones);
        gp_generic.add(hb_botones, 0, 3, 2, 1);

        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.setHgap(2);
        gp_generic.setVgap(2);
        hb_botones.setAlignment(Pos.CENTER);
     //   gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
       // gp_generic.getStyleClass().add("background");
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_generic.getLayoutBounds().getHeight(), gp_generic.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.getChildren().addAll(gp_generic, Gp_Message);
       
       switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gp_generic);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gp_generic);
                     break;              
         }
        if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()!=0)
        {
          nuevo.setStyle("-fx-background-color:#007fff");
          save.setStyle("-fx-background-color:#007fff");
        }
        crearoeditar();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void nuevo() {
        AbacoApp.solucion = null;
        AbacoApp.solucion = new Solucion();
     
       AbacoApp.cb_soluciones.getSelectionModel().select(0);
        descripcion.setText("");
    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {
                if (AbacoApp.solucion.getId() == null) {
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

        AbacoApp.solucion.setSolucion(AbacoApp.cb_soluciones.getValue().toString().substring(2));
        AbacoApp.solucion.setNumeral(Integer.valueOf(AbacoApp.cb_soluciones.getValue().toString().substring(0, 2)));
      

    }

    private void set_datos_for_update() {
        AbacoApp.cb_soluciones.setValue(String.valueOf(AbacoApp.solucion.getNumeral())+" "+AbacoApp.solucion.getSolucion());

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
                        general.getStylesheets().add(0, "/abaco/personal.css");
                        general.getStylesheets().set(0, "/abaco/personal.css");
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
                                general.getStylesheets().set(0, "/abaco/SofackarStylesCommon.css");

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
        if (AbacoApp.solucion != null) {
            set_datos_for_update();
        } else {
            nuevo();
        }
    }
}
