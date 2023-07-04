package sihic.historiasclinicas.adminorganizacion;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.GenConvenios;
import modelo.GenEapb;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.contabilidad.FindServicios;
import sihic.control.SearchBox;
import sihic.controlador.GenConveniosEapbControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

public class FGenConvenios extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GenConvenios genConveniosEapb;
    private GridPane gp_generic;
    private Label la_fechacontrato;
    private Label la_eps;
    private Label la_nocontrato;
    private Label la_porcentaje;
    private Label la_descripcion;
    private Label la_tipoconvenio;
    private GenConveniosEapbControllerClient genConveniosEapbControllerClient;
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
    private DatePicker fechacontrato;
    private TextField nocontrato;
    private TextArea descripcion;
    private TextField porcentaje;
    private TextField nombre;
    private ChoiceBox tipoconvenio;
    private SearchBox sb_geneps = new SearchBox();
    private SearchPopover searchPopover;
    private DecimalFormat format = new DecimalFormat("#,0");

    public Parent createContent() throws IOException, ParseException {
        searchPopover = new SearchPopover(sb_geneps, new PageBrowser(), SihicApp.s_genEapb, true);
        searchPopover.getSearchBox().setOnAction(e -> {
            nocontrato.requestFocus();
        });
        la_fechacontrato = new Label("Fecha Contrato:");
        la_nocontrato = new Label("N° Contrato:");
        la_porcentaje = new Label("% Descuento:");
        la_descripcion = new Label("Descripción:");
        la_tipoconvenio = new Label("Tipo Convenio:");
        la_eps = new Label("Eps:");
        fechacontrato = new DatePicker();
        nocontrato = new TextField();
        descripcion = new TextArea();
        porcentaje = new TextField();
        tipoconvenio = new ChoiceBox();
        tipoconvenio.getItems().addAll("Contrato", "Particular");
        tipoconvenio.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        tipoconvenio.getProperties().put("requerido", true);
        nombre = new TextField();
        nombre.setMinWidth(500);
        porcentaje.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        fechacontrato.setEditable(false);
        findProduct = new FindServicios(false);

        genConveniosEapbControllerClient = new GenConveniosEapbControllerClient();
        genConveniosEapb = new GenConvenios();
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        LoadChoiceBoxGeneral.getCb_eps().getProperties().put("requerido", true);
        fechacontrato.getProperties().put("requerido", true);
        porcentaje.getProperties().put("requerido", true);
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
                    nuevo();
                } catch (ParseException ex) {
                }
                LoadChoiceBoxGeneral.getCb_agnmedicos().requestFocus();
            }

        });

        nuevo.setOnAction(e -> {
            try {
                nuevo();
            } catch (ParseException ex) {
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
        gp_generic.addRow(0, la_tipoconvenio, tipoconvenio);
        gp_generic.addRow(1, la_fechacontrato, fechacontrato);
        gp_generic.addRow(2, la_eps, sb_geneps);
        gp_generic.addRow(3, la_nocontrato, nocontrato);
        gp_generic.addRow(4, la_porcentaje, porcentaje);
        gp_generic.addRow(5, la_descripcion, descripcion);
        gp_generic.add(hb_botones, 0, 6, 2, 1);
        gp_generic.add(searchPopover, 1, 3, 1, 4);
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

                if (genConveniosEapb.getId() == null) {
                    if (tipoconvenio.getSelectionModel().getSelectedIndex() == 0) {
                        genConveniosEapbControllerClient.create(genConveniosEapb, SihicApp.s_genEapb);
                    } else {
                        genConveniosEapbControllerClient.createParticula(genConveniosEapb);
                    }
                    L_Message.setText("Registro Almacenado");
                } else {
                    genConveniosEapb = genConveniosEapbControllerClient.edit(genConveniosEapb);
                    L_Message.setText("Registro modificado");
                }

                AdminConveniosEps.getListConveniosEps(tipoconvenio.getSelectionModel().getSelectedIndex());
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
        LocalDate localDate = fechacontrato.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));

        if (tipoconvenio.getSelectionModel().getSelectedIndex() == 0) {
            genConveniosEapb.setGenEapb(SihicApp.s_genEapb);
            genConveniosEapb.setFechacontrato(Date.from(instant));
            genConveniosEapb.setDescripcion(tipoconvenio.getSelectionModel().getSelectedIndex() == 0 ? descripcion.getText() : nombre.getText());
            genConveniosEapb.setNumerocontrato(nocontrato.getText());
        } else {
            genConveniosEapb.setFechacontrato(new Date());
            genConveniosEapb.setDescripcion(tipoconvenio.getSelectionModel().getSelectedIndex() == 0 ? descripcion.getText() : nombre.getText());
        }
        genConveniosEapb.setTipoconvenio(tipoconvenio.getSelectionModel().getSelectedIndex() == 0 ? "C" : "P");
        genConveniosEapb.setPorcentajedescuento(Float.valueOf(porcentaje.getText()));

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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        genConveniosEapb = AdminConveniosEps.getGenconvenioseapb();
        if (genConveniosEapb.getId() != null) {

            sb_geneps.setText(genConveniosEapb.getGenEapb().getCodigo() + " " + genConveniosEapb.getGenEapb().getNombre());
            SihicApp.s_genEapb = genConveniosEapb.getGenEapb();
            String fecha = df.format(genConveniosEapb.getFechacontrato());
            LocalDate ld = LocalDate.parse(fecha);
            fechacontrato.setValue(ld);
            nocontrato.setText(genConveniosEapb.getNumerocontrato());
            porcentaje.setText(String.valueOf(genConveniosEapb.getPorcentajedescuento()));
            descripcion.setText(genConveniosEapb.getDescripcion());
            if (genConveniosEapb.getTipoconvenio().equals("C")) {
                tipoconvenio.getSelectionModel().select(0);
                descripcion.setText(genConveniosEapb.getDescripcion());
            } else {
                tipoconvenio.getSelectionModel().select(1);
                nombre.setText(genConveniosEapb.getDescripcion());
            }

        } else {
            nuevo();
        }

    }

    public void nuevo() throws ParseException {
        genConveniosEapb = null;
        genConveniosEapb = new GenConvenios();
        sb_geneps.setText("");
        SihicApp.s_genEapb = null;
        SihicApp.s_genEapb = new GenEapb();
        fechacontrato.setValue(LocalDate.now());
        nocontrato.setText("");
        descripcion.setText("");
        porcentaje.setText("");

    }
    // A change listener to track the change in selected index

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        cambiarseguntipoconvenio();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void cambiarseguntipoconvenio() {
        if (tipoconvenio.getSelectionModel().getSelectedIndex() == 0) {
            if (gp_generic.getChildren().indexOf(fechacontrato) == -1) {
                la_descripcion.setText("Nombre:");
                gp_generic.getChildren().removeAll(la_descripcion, nombre, la_porcentaje, porcentaje, hb_botones);
                gp_generic.addRow(1, la_fechacontrato, fechacontrato);
                gp_generic.addRow(2, la_eps, sb_geneps);
                gp_generic.addRow(3, la_nocontrato, nocontrato);
                gp_generic.addRow(4, la_porcentaje, porcentaje);
                gp_generic.addRow(5, la_descripcion, descripcion);
                gp_generic.add(hb_botones, 0, 6, 2, 1);
            }

        } else {
            la_descripcion.setText("Nombre:");
            gp_generic.getChildren().removeAll(la_fechacontrato, fechacontrato, la_eps, sb_geneps, la_nocontrato, nocontrato, la_descripcion, descripcion, la_porcentaje, porcentaje, hb_botones);
            gp_generic.addRow(1, la_descripcion, nombre);
            gp_generic.addRow(2, la_porcentaje, porcentaje);
            gp_generic.add(hb_botones, 0, 3, 2, 1);

        }

    }
}
