package sihic.contabilidad;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.Factura;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.controlador.FacturaControllerClient;
import sihic.controlador.GenEapbControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.UtilDate;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FRadicadaAceptada extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    
    private GridPane gp_generic;
  
    private FacturaControllerClient facturaControllerClient;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;

    private Button nuevaProfesion;

    private Label la_fecharadicada=new Label("Fecha de Radicación");
    private Label la_fechaaceptada=new Label("Fecha de Aceptación");
    private Label la_numeroradicado=new Label("N° Radicado:");
    private Label la_aceptada=new Label("Aceptada:");
    private DatePicker fecharadicada=new DatePicker(LocalDate.now());
    private DatePicker fechaaceptada=new DatePicker(LocalDate.now());;
    private TextField numeroradicado=new TextField("");
    private CheckBox  aceptada=new CheckBox();
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
   
    GenEapbControllerClient genEapbControllerClient;

    public Parent createContent() throws IOException, ParseException {
        facturaControllerClient = new FacturaControllerClient();
       
        //  sb_eapb.getProperties().put("requerido", true);
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
           ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        save = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        nuevo = new Button("", imageView);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });

// gridpane  sp_geneapb = new SearchPopover(sb_eapb,new PageBrowser());
        gp_generic = new GridPane();
        gp_generic.add(la_fecharadicada, 0, 0);
        gp_generic.add(fecharadicada, 1, 0);
        gp_generic.add(la_numeroradicado, 0, 1);
        gp_generic.add(numeroradicado, 1, 1);
        gp_generic.add(la_fechaaceptada, 0, 2);
        gp_generic.add(fechaaceptada, 1, 2);
        gp_generic.add(la_aceptada, 0, 3);
        gp_generic.add(aceptada, 1, 3);
        gp_generic.add(hb_botones, 0, 4, 2, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
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
        event_inputs();
      

     

        editar();
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
                facturaControllerClient.guardarFactura(SihicApp.s_factura);
                  if(SihicApp.s_factura.isAceptada())
                  {
                       
                        MovimientoCuentas.colocarasientoscontables(jenum.EnumTiposComprobantes.VENTAS3.ordinal());
                   }
                
                    RadicarAceptarFacturas.getRecords();
                    L_Message.setText("Registro Almacenado");
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

    private void set_datos() throws ParseException 
    {

        SihicApp.s_factura.setFecharadicacion(UtilDate.localdatetodate(fecharadicada.getValue()));
        SihicApp.s_factura.setFechaaceptacion(UtilDate.localdatetodate(fechaaceptada.getValue()));
        SihicApp.s_factura.setNumeroradicado(numeroradicado.getText().trim());
        SihicApp.s_factura.setAceptada(aceptada.isSelected());
       
        
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true) && LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0) {
                        permitirproceso = false;
                        general.setStyle("-fx-background-color:#ff1600");

                    }
                }

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    if (general.getProperties().get("requerido") != null) {
                        if (general.getSelectionModel().getSelectedIndex() < 0 && ((boolean) general.getProperties().get("requerido") == true) && (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0 || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == -1)) {
                            permitirproceso = false;
                            general.getStylesheets().add(0, "/sihic/personal.css");
                            general.getStylesheets().set(0, "/sihic/personal.css");
                        }
                    }

                } else {
                    if (n.getClass() == SearchBox.class) {

                        SearchBox general = (SearchBox) n;
                        if (general.getProperties().get("requerido") != null) {
                            if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true) && (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0 )) {
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
                            if (((boolean) general.getProperties().get("requerido") == true)) {

                                general.setStyle(null);
                                general.getStyleClass().add("text-field");
                            }
                        }

                    } else {
                        if (n.getClass() == ChoiceBox.class) {
                            ChoiceBox general = (ChoiceBox) n;
                            if (general.getProperties().get("requerido") != null) {
                                if (general.getSelectionModel().getSelectedIndex() < 0 && ((boolean) general.getProperties().get("requerido") == true)) {
                                    general.getStylesheets().set(0, SihicApp.hojaestilos);

                                }
                            }

                        } else {
                            if (n.getClass() == SearchBox.class) {

                                SearchBox general = (SearchBox) n;
                                if (general.getProperties().get("requerido") != null) {
                                    if (((boolean) general.getProperties().get("requerido") == true)) {

                                        general.setStyle(null);
                                        general.getStyleClass().add("text-field");
                                    }

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

    private void event_inputs() {

    }

    private void nuevo() throws ParseException {
        SihicApp.s_factura = null;
        SihicApp.s_factura = new Factura();
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");
                general.setDisable(false);
            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    general.getSelectionModel().select(-1);
                    general.setDisable(false);
                }

                if (n.getClass() == DatePicker.class) {
                    fecharadicada = new DatePicker(LocalDate.now());
                    fechaaceptada = new DatePicker(LocalDate.now());

                }
                if (n.getClass() == CheckBox.class) {
                    CheckBox general = (CheckBox) n;
                    general.setDisable(false);
                    general.setSelected(false);
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

    public void editar() throws ParseException {
        if (SihicApp.s_factura != null) {
            try{
             fecharadicada.setValue(UtilDate.formatoyearmesdia(SihicApp.s_factura.getFecharadicacion()));
            }catch(Exception e)
            {
                
            }
             try{
            fechaaceptada.setValue(UtilDate.formatoyearmesdia(SihicApp.s_factura.getFechaaceptacion()));
             }catch(Exception e)
            {
                
            }
            numeroradicado.setText(SihicApp.s_factura.getNumeroradicado());
            aceptada.setSelected(SihicApp.s_factura.isAceptada());
          
        } else {
            nuevo();

        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
