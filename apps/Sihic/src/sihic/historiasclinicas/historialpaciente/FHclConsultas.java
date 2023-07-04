package sihic.historiasclinicas.historialpaciente;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.HclCausaExterna;
import modelo.HclCodigosConsultas;
import modelo.HclConsultas;
import modelo.HclDiagnostico;
import modelo.HclDiagnosticosRelacionados;
import modelo.HclFinalidad;
import sihic.SihicApp;
import sihic.controlador.HclConsultasControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FHclConsultas extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;

    private HclDiagnostico hclDiagnostico;
    private HclDiagnosticosRelacionados hclDiagnosticosRelacionados;
    private GridPane gp_hclconsultas;
    private ChoiceBox hclfinalidad_id;
    private Vector<HclFinalidad> v_hclfinalidad = new Vector<HclFinalidad>();
    private ChoiceBox hclcausaexterna_id;
    private Vector<HclCausaExterna> v_hclconsultaexterna = new Vector<HclCausaExterna>();
    private ChoiceBox hclcodigosconsultas_id;
    private Vector<HclCodigosConsultas> v_hclcodigosconsultas = new Vector<HclCodigosConsultas>();
    private Label la_numeroautorizacion;
    private TextField numero_autorizacion;
    private Label la_concepto;
    private TextField concepto;
    private Label la_anamnesis;
    private TextArea anamnesis;
    private Label la_motivo;
    private TextArea motivo;
    private Label la_fechaevolucion;
    private DatePicker fechaevolucion;
    private Label la_codigosconsultas;
    private Label la_finalidad;
    private Label la_causaexterna;
    private Label la_diagnosticoprincipal;
    private TextField diagnosticoprincipal;
    private HclConsultasControllerClient hclconsultasControllerClient;
    private HclHistoriasClinicasControllerClient hclHistoriasClinicasControllerClient;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private TableView ta_codigosrelacionados;
    private TableColumn tipoantecedentegeneral;
    private TableColumn codigo;
    private TableColumn descripcion;
    private Button nuevodr;
    private ImageView imageView;
    private BuscarCie10 buscarCie10;
    private int opc;
    private ObservableList ol_hcldiagnosticosrelacionados = FXCollections.observableArrayList();
    private List<HclDiagnosticosRelacionados> l_HclDiagnosticosRelacionadoses;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;

    public Parent createContent() throws IOException {
        hclconsultasControllerClient = new HclConsultasControllerClient();
        SihicApp.hclconsultas = hclconsultasControllerClient.getHclConsulta(SihicApp.agnCitasTemp);
        buscarCie10 = new BuscarCie10();
        hclHistoriasClinicasControllerClient = new HclHistoriasClinicasControllerClient();
        ta_codigosrelacionados = new TableView();
        codigo = new TableColumn();

        descripcion = new TableColumn();

        codigo.setMinWidth(100);
        codigo.setMaxWidth(300);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclDiagnosticosRelacionados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclDiagnosticosRelacionados, String> hclDiagnosticosRelacionados) {

                return new SimpleStringProperty((hclDiagnosticosRelacionados.getValue().getHclcie10_id().getCodigo()));

            }
        });
        descripcion.setMinWidth(600);
        descripcion.setMaxWidth(600);
        descripcion.setText("Descripción");
        descripcion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclDiagnosticosRelacionados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclDiagnosticosRelacionados, String> hclDiagnosticosRelacionados) {

                return new SimpleStringProperty((hclDiagnosticosRelacionados.getValue().getHclcie10_id().getDescripcion()));

            }
        });
        TableColumn titulotabla = new TableColumn<>("Diagnósticos Relacionados");
        titulotabla.getColumns().addAll(codigo, descripcion);
        ta_codigosrelacionados.getColumns().addAll(titulotabla);
        ta_codigosrelacionados.setMaxWidth(700);
        ta_codigosrelacionados.setMaxHeight(150);
        ta_codigosrelacionados.setOnMouseClicked(e -> {
            try {

            } catch (Exception ex) {
            }
        });

        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        nuevodr = new Button("", imageView);
        nuevodr.setMaxWidth(130);
        nuevodr.setOnAction(e
                -> {
            try {
                opc = 2;
                buscacie10paradiagnosticorelacionado();
            } catch (Exception ex) {

            }
        });
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        nuevo = new Button("", imageView);
        nuevo.setMaxWidth(130);
        nuevo.setOnAction(e
                -> {
            try {
                opc = 1;
                buscacie10paradiagnostico();
            } catch (Exception ex) {

            }
        });

        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        hclfinalidad_id = new ChoiceBox();
        hclfinalidad_id.setMaxWidth(300);
        hclcodigosconsultas_id = new ChoiceBox();
        hclcodigosconsultas_id.setMaxWidth(300);
        hclcausaexterna_id = new ChoiceBox();
        hclcausaexterna_id.setMaxWidth(300);
        la_codigosconsultas = new Label("Codigo Consulta:");
        la_finalidad = new Label("Finalidad:");
        la_anamnesis = new Label("Anamnesis:");
        la_motivo = new Label("Motivo:");
        la_causaexterna = new Label("Causa Externa:");
        la_numeroautorizacion = new Label("Número Autorización:");
        la_concepto = new Label("Concepto:");
        la_fechaevolucion = new Label("Fecha Consulta:");
        la_diagnosticoprincipal = new Label("Diagnóstico Principal:");
        diagnosticoprincipal = new TextField();
        anamnesis = new TextArea();
        anamnesis.getProperties().put("requerido", true);
        anamnesis.setMinHeight(70);
        anamnesis.setMaxHeight(70);
        motivo = new TextArea();
        motivo.setMinHeight(70);
        motivo.setMaxHeight(70);
        numero_autorizacion = new TextField();
        numero_autorizacion.getProperties().put("requerido", true);
        concepto = new TextField();

        fechaevolucion = new DatePicker(LocalDate.now());

        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        save = new Button("", imageView);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });

        buscarCie10.getStage().setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    if (buscarCie10.getHclCie10() != null) {
                        if (opc == 1) {
                            try {
                                saveDiagnostico();
                            } catch (ParseException ex) {
                                Logger.getLogger(FHclConsultas.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else {

                            if (opc == 2) {
                                try {
                                    saveDiagnosticoRelacionado();
                                } catch (ParseException ex) {
                                    Logger.getLogger(FHclConsultas.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }
                    }
                }
            }
        });

// gridpane
        gp_hclconsultas = new GridPane();
        gp_hclconsultas.add(la_fechaevolucion, 0, 0);
        gp_hclconsultas.add(fechaevolucion, 1, 0);
        gp_hclconsultas.add(la_numeroautorizacion, 0, 1);
        gp_hclconsultas.add(numero_autorizacion, 1, 1);
        gp_hclconsultas.add(la_codigosconsultas, 0, 2);
        gp_hclconsultas.add(hclcodigosconsultas_id, 1, 2);
        gp_hclconsultas.add(la_finalidad, 0, 3);
        gp_hclconsultas.add(hclfinalidad_id, 1, 3);
        gp_hclconsultas.add(la_causaexterna, 0, 4);
        gp_hclconsultas.add(hclcausaexterna_id, 1, 4);
        gp_hclconsultas.add(la_concepto, 0, 5);
        gp_hclconsultas.add(concepto, 1, 5);
        gp_hclconsultas.add(la_motivo, 0, 6);
        gp_hclconsultas.add(motivo, 1, 6);
        gp_hclconsultas.add(la_anamnesis, 0, 7);
        gp_hclconsultas.add(anamnesis, 1, 7);
        gp_hclconsultas.add(la_diagnosticoprincipal, 0, 8);
        gp_hclconsultas.add(diagnosticoprincipal, 1, 8);
        gp_hclconsultas.add(nuevo, 2, 8);
        gp_hclconsultas.add(ta_codigosrelacionados, 0, 9, 2, 1);
        gp_hclconsultas.add(nuevodr, 2, 9);
        gp_hclconsultas.add(hb_botones, 0, 10, 3, 1);
        GridPane.setValignment(nuevodr, VPos.TOP);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        // gp_hclconsultas.getStylesheets().add(SihicApp.hojaestilos);
        // gp_hclconsultas.getStyleClass().add("background");
        gp_hclconsultas.setHgap(5);
        gp_hclconsultas.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_hclconsultas.getLayoutBounds().getHeight(), gp_hclconsultas.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);

        stack.getChildren().addAll(gp_hclconsultas, Gp_Message);
        Load_ChoiceBox();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void Load_ChoiceBox() {

        if (SihicApp.l_hclfinalidad.size() > 0) {
            hclfinalidad_id.getItems().clear();
            v_hclfinalidad.clear();
            for (HclFinalidad f : SihicApp.l_hclfinalidad) {
                v_hclfinalidad.add(f);
                hclfinalidad_id.getItems().add(f.getDescripcion());
            }
        }

        //hcl causaexterna
        if (SihicApp.l_hclcausaexterna.size() > 0) {
            hclcausaexterna_id.getItems().clear();
            v_hclconsultaexterna.clear();
            for (HclCausaExterna ce : SihicApp.l_hclcausaexterna) {
                v_hclconsultaexterna.add(ce);
                hclcausaexterna_id.getItems().add(ce.getDescripcion());
            }
        }
        //codigos consultas
        if (SihicApp.l_hclcodigosconsultas.size() > 0) {
            hclcodigosconsultas_id.getItems().clear();
            v_hclcodigosconsultas.clear();
            for (HclCodigosConsultas cc : SihicApp.l_hclcodigosconsultas) {
                v_hclcodigosconsultas.add(cc);
                hclcodigosconsultas_id.getItems().add(cc.getDescripcion());
            }
        }

    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            set_datos_hclconsultas();
            try {
                hclconsultasControllerClient.edit(SihicApp.hclconsultas);
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

    private void buscacie10() {

    }

    private void set_datos_hclconsultas() {

        SihicApp.hclconsultas.setNumeroautorizacion(numero_autorizacion.getText());
        SihicApp.hclconsultas.setAnamnesis(anamnesis.getText());
        SihicApp.hclconsultas.setMotivo(motivo.getText());
        SihicApp.hclconsultas.setConcepto(concepto.getText());
        SihicApp.hclconsultas.setFechaCreacion(new Date());
        SihicApp.hclconsultas.setFechaevolucion(new Date());

        //colocar finalidad
        SihicApp.hclconsultas.setHclFinalidad(v_hclfinalidad.get(hclfinalidad_id.getSelectionModel().getSelectedIndex()));
        //colocar causa externa
        SihicApp.hclconsultas.setCausaExterna(v_hclconsultaexterna.get(hclcausaexterna_id.getSelectionModel().getSelectedIndex()));

        //colocar codigos consultas
        SihicApp.hclconsultas.setCodigosConsultas(v_hclcodigosconsultas.get(hclcodigosconsultas_id.getSelectionModel().getSelectedIndex()));
        //

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_hclconsultas.getChildren().toArray()) {
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
                for (Object n : gp_hclconsultas.getChildren().toArray()) {
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

    public void buscacie10paradiagnostico() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        buscarCie10.showhclcie10();
    }

    public void buscacie10paradiagnosticorelacionado() {
        try {
            buscarCie10.showhclcie10();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDiagnostico() throws ParseException {
        diagnosticoprincipal.setText(buscarCie10.getHclCie10().getCodigo() + " " + buscarCie10.getHclCie10().getDescripcion());
        hclDiagnostico = new HclDiagnostico();
        hclDiagnostico.setHclConsultas_id(SihicApp.hclconsultas);
        hclDiagnostico.setHclcie10_id(buscarCie10.getHclCie10());
        hclconsultasControllerClient.saveDiagnostico(hclDiagnostico);
        getDiagnosticoCodigosRelacionados();

    }

    public void saveDiagnosticoRelacionado() throws ParseException {
        if (hclDiagnostico.getId() != null) {
            hclDiagnosticosRelacionados = new HclDiagnosticosRelacionados();
            hclDiagnosticosRelacionados.setHclcie10_id(buscarCie10.getHclCie10());
            hclDiagnosticosRelacionados.setHcldiagnostico_id(hclDiagnostico);
            hclconsultasControllerClient.saveDiagnosticoRelacionado(hclDiagnosticosRelacionados);
            getDiagnosticoCodigosRelacionados();
        }

    }

    private void getDiagnosticoCodigosRelacionados() throws ParseException {
        l_HclDiagnosticosRelacionadoses = hclHistoriasClinicasControllerClient.getDiagnosticosRelacionados(hclDiagnostico);

        ol_hcldiagnosticosrelacionados.clear();

        ol_hcldiagnosticosrelacionados.addAll(l_HclDiagnosticosRelacionadoses.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_codigosrelacionados.setItems(ol_hcldiagnosticosrelacionados);

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
}
