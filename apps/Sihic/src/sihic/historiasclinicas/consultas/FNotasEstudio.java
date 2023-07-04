package sihic.historiasclinicas.consultas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.HclCie10;
import modelo.NotasEstudio;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.contabilidad.ImpresionFactura;
import sihic.control.SearchBox;
import sihic.controlador.NotasEstudioControllerClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FNotasEstudio extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;

    private GridPane gp_generic;
    private Label la_detalle;
    private Label la_elaboro;
    private Label la_horaingreso;
    private Label la_horaegreso;
    private Label la_horaatencion;
    private Label la_fecha;
    private Label la_embarazada;
    private Label la_pacientellego;
    private Label la_estadosalida;
    private Label la_taingreso;
    private Label la_fechaingreso;
    private Label la_fcingreso;
    private Label la_fcegreso;
    private Label la_spo2ingreso;
    private Label la_taegreso;
    private Label la_fechaegreso;
    private Label la_spo2egreso;
    private Label la_peso;
    private Label la_conclusion;
    private Label la_acompanante;
    private Label la_tiponotaestudio;
    private Label la_miembrosuperior;
    private Label la_paciente;
    private TextField detalle;
    private TextField elaboro;
    private TextField horaingreso;
    private TextField horaegreso;
    private TextField horaatencion;
    private TextField minutosingreso;
    private TextField minutosegreso;
    private TextField minutosatencion;
    private TextField fecha;
    private CheckBox embarazada;
    private TextField taingreso;
    private TextField fcingreso;
    private TextField fechaingreso;
    private TextField spo2ingreso;
    private TextField taegreso;
    private TextField fcegreso;
    private TextField fechaegreso;
    private TextField spo2egreso;
    private TextField peso;
    private TextField conclusion;
    private TextField acompanante;
    private ChoiceBox pacientellego;
    private ChoiceBox estadosalida;
    private ChoiceBox tiponotaestudio;
    private TextField miembrosuperior;
    private TextField paciente;
    private TextField cantiopramida;
    private TextField jelco;
    private final TextArea notaenfermeria=new TextArea();
    private ChoiceBox af;
    private Label la_hclcie10;
    private SearchBox sb_hclcie10 = new SearchBox();
    private SearchPopover sp_hclcie10;
    private NotasEstudioControllerClient notasEstudioControllerClient;
    private Button save;
    private Button delete;
    private Button pdfnotaestudio;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private ImageView logo;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private File fileLogo;
    private FileChooser fileChooser = new FileChooser();
    private WritableImage wi;
    private BufferedImage bi;

    public Parent createContent() throws IOException {
        notasEstudioControllerClient = new NotasEstudioControllerClient();

        logo = new ImageView();
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        la_fechaingreso = new Label("Fecha Ingreso:");
        fechaingreso = new TextField();
        la_fechaegreso = new Label("Fecha Egreso:");
        fechaegreso = new TextField();
        la_horaingreso = new Label("Hora Ingreso:");
        horaingreso = new TextField();
        horaingreso.setMaxWidth(50);
        minutosingreso = new TextField();
        minutosingreso.setMaxWidth(50);
        la_horaatencion = new Label("Hora Atención:");
        horaatencion = new TextField();
        horaatencion.setMaxWidth(50);
        minutosatencion = new TextField();
        minutosatencion.setMaxWidth(50);
        la_horaegreso = new Label("Hora Egreso:");
        horaegreso = new TextField();
        horaegreso.setMaxWidth(50);
        minutosegreso = new TextField();
        minutosegreso.setMaxWidth(50);
        la_detalle = new Label("Detalle:");
        detalle = new TextField();
        la_elaboro = new Label("Elaboró:");
        elaboro = new TextField();
        la_acompanante = new Label("Acompañante:");
        acompanante = new TextField();
        la_pacientellego = new Label("Paciente Llego:");
        pacientellego = new ChoiceBox();
        pacientellego.getItems().addAll("Consciente", "Incosciente", "Muerto");
        pacientellego.setMinWidth(300);
        estadosalida = new ChoiceBox();
        estadosalida.getItems().addAll("Vivo", "Muerto", "Remitido");
        estadosalida.setMinWidth(300);
        la_embarazada = new Label("Embarazada");
        embarazada = new CheckBox();
        la_taingreso = new Label("T/A(MMHG)");
        taingreso = new TextField();
        la_fcingreso = new Label("FC(LPM)");
        fcingreso = new TextField();
        la_spo2ingreso = new Label("SpO2(%)");
        spo2ingreso = new TextField();
        la_estadosalida = new Label("Estado Salida:");
        la_taegreso = new Label("T/A(MMHG)");
        taegreso = new TextField();
        la_fcegreso = new Label("FC(LPM)");
        fcegreso = new TextField();
        la_spo2egreso = new Label("SpO2(%)");
        spo2egreso = new TextField();
        la_peso = new Label("Peso(Kg");
        peso = new TextField();
        la_tiponotaestudio = new Label("Tipo Nota Estudio:");
        tiponotaestudio = new ChoiceBox();
        tiponotaestudio.getItems().addAll("Contrastado", "Simple", "Mamografía");
        tiponotaestudio.getProperties().put("requerido", true);
        la_miembrosuperior = new Label("Miembro Superior:");
        la_paciente = new Label("Paciente: ");
        paciente = new TextField();
        miembrosuperior = new TextField();
        la_hclcie10 = new Label("Dx Principal");
        sp_hclcie10 = new SearchPopover(sb_hclcie10, new PageBrowser(), SihicApp.hclCie10, false);
        cantiopramida = new TextField("");
        jelco = new TextField("");
        af = new ChoiceBox();
        af.getItems().addAll("+", "-");
        af.getProperties().put("requerido", false);
        
        ImageView imageView = new ImageView("/images/pdf.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        pdfnotaestudio = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/save.png");

        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        save = new Button("", imageView);
        hb_botones = new HBox(5);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        save.setOnKeyPressed(e -> {

            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();
                    pdfnotaestudio.requestFocus();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FAgnConsultorios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        imageView = null;
        imageView = new ImageView("/images/delete.png");

        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        delete = new Button("", imageView);

        delete.setOnAction(e -> {
            delete();
        });
        pdfnotaestudio.setOnAction(e -> {
            ImpresionFactura impresionFactura = new ImpresionFactura();
            try {

                impresionFactura.fpdfnotasestudio(SihicApp.notasEstudio, tiponotaestudio.getSelectionModel().getSelectedIndex());

            } catch (Exception ex) {
            }
        });
        HBox hb_horain = new HBox();
        hb_horain.setSpacing(2);
        HBox hb_horaaten = new HBox();
        hb_horaaten.setSpacing(2);
        HBox hb_horaeg = new HBox();
        hb_horaeg.setSpacing(2);
        Label sephorain = new Label(":");
        Label sephoraeg = new Label(":");
        Label sephoraat = new Label(":");
        hb_horain.getChildren().addAll(horaingreso, sephorain, minutosingreso);
        hb_horaaten.getChildren().addAll(horaatencion, sephoraat, minutosatencion);
        hb_horaeg.getChildren().addAll(horaegreso, sephoraeg, minutosegreso);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, delete, pdfnotaestudio);
        gp_generic = new GridPane();
        gp_generic.add(la_paciente, 0, 0, 6, 1);
        gp_generic.add(paciente, 0, 1, 6, 1);
        gp_generic.add(la_tiponotaestudio, 0, 2);
        gp_generic.add(la_fechaingreso, 1, 2);
        gp_generic.add(la_fechaegreso, 2, 2);
        gp_generic.add(la_horaingreso, 3, 2);
        gp_generic.add(la_horaatencion, 4, 2);
        gp_generic.add(la_horaegreso, 5, 2);
        gp_generic.add(tiponotaestudio, 0, 3);
        gp_generic.add(fechaingreso, 1, 3);
        gp_generic.add(fechaegreso, 2, 3);
        gp_generic.add(hb_horain, 3, 3);
        gp_generic.add(hb_horaaten, 4, 3);
        gp_generic.add(hb_horaeg, 5, 3);
        gp_generic.add(la_detalle, 0, 4, 5, 1);
        gp_generic.add(la_miembrosuperior, 5, 4);
        gp_generic.add(detalle, 0, 5, 5, 1);
        gp_generic.add(miembrosuperior, 5, 5);
        gp_generic.add(la_elaboro, 0, 12, 2, 1);
        gp_generic.add(la_peso, 2, 12, 1, 1);
        gp_generic.add(new Label("Cant. Iopramida"), 3, 12);
        gp_generic.add(new Label("Jelco"), 4, 12);
        gp_generic.add(la_embarazada, 5, 12);
        gp_generic.add(elaboro, 0, 13, 2, 1);
        gp_generic.add(peso, 2, 13, 1, 1);
        gp_generic.add(cantiopramida, 3, 13, 1, 1);
        gp_generic.add(jelco, 4, 13, 1, 1);
        gp_generic.add(embarazada, 5, 13, 1, 1);
        gp_generic.add(la_pacientellego, 0, 8);
        gp_generic.add(la_taingreso, 2, 8);
        gp_generic.add(la_fcingreso, 3, 8);
        gp_generic.add(la_spo2ingreso, 4, 8, 2, 1);
        gp_generic.add(pacientellego, 0, 9, 2, 1);
        gp_generic.add(taingreso, 2, 9);
        gp_generic.add(fcingreso, 3, 9);
        gp_generic.add(spo2ingreso, 4, 9, 2, 1);
        gp_generic.add(la_estadosalida, 0, 10);
        gp_generic.add(la_taegreso, 2, 10);
        gp_generic.add(la_fcegreso, 3, 10);
        gp_generic.add(la_spo2egreso, 4, 10, 2, 1);
        gp_generic.add(estadosalida, 0, 11, 2, 1);
        gp_generic.add(taegreso, 2, 11);
        gp_generic.add(fcegreso, 3, 11);
        gp_generic.add(spo2egreso, 4, 11, 2, 1);
        gp_generic.add(la_hclcie10, 0, 6, 5, 1);
        gp_generic.add(sb_hclcie10, 0, 7, 5, 1);
        gp_generic.add(new Label("Af"), 5, 6, 1, 1);
        gp_generic.add(af, 5, 7, 1, 1);
        gp_generic.add(la_acompanante, 0, 14, 2, 1);
        gp_generic.add(acompanante, 0, 15, 2, 1);
         gp_generic.add(new Label("Nota de Enfermería:"), 2, 14, 4, 1);
        gp_generic.add(notaenfermeria, 2, 15, 4, 4);
        gp_generic.add(hb_botones, 0, 19, 6, 1);
        gp_generic.add(sp_hclcie10, 0, 8, 6, 8);
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
        Gp_Message.setMinSize(600, 40);
        Gp_Message.setMaxSize(600, 40);
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
            set_datos_oranizacion();
            try {
                if (SihicApp.notasEstudio.getId() == null) {
                    notasEstudioControllerClient.create(SihicApp.notasEstudio);
                    L_Message.setText("Registro Almacenado");
                } else {
                    notasEstudioControllerClient.update(SihicApp.notasEstudio);
                    L_Message.setText("Registro modificado");
                }

                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
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

    private void set_datos_oranizacion() {
        SihicApp.notasEstudio.setHoraingreso(Integer.valueOf(horaingreso.getText()));
        SihicApp.notasEstudio.setMinutosingreso(Integer.valueOf(minutosingreso.getText()));
        SihicApp.notasEstudio.setHoraatencion(Integer.valueOf(horaatencion.getText()));
        SihicApp.notasEstudio.setMinutosatencion(Integer.valueOf(minutosatencion.getText()));
        SihicApp.notasEstudio.setHoraegreso(Integer.valueOf(horaegreso.getText()));
        SihicApp.notasEstudio.setMinutosegreso(Integer.valueOf(minutosegreso.getText()));
        SihicApp.notasEstudio.setAcompanante(acompanante.getText());
        SihicApp.notasEstudio.setDetalle(detalle.getText());
        SihicApp.notasEstudio.setElaboro(elaboro.getText());
        SihicApp.notasEstudio.setPacientellego(pacientellego.getSelectionModel().getSelectedIndex());
        SihicApp.notasEstudio.setEstadosalida(estadosalida.getSelectionModel().getSelectedIndex());
        SihicApp.notasEstudio.setNotaenfermeria(notaenfermeria.getText());
        try {
            SihicApp.notasEstudio.setAf(af.getValue().toString());
        } catch (Exception e) {
            SihicApp.notasEstudio.setAf("+");
        }
        SihicApp.notasEstudio.setSvingresota(taingreso.getText());

        SihicApp.notasEstudio.setSvingresofc(fcingreso.getText());

        SihicApp.notasEstudio.setSvingresospo2(spo2ingreso.getText());

        SihicApp.notasEstudio.setSvegresota(taegreso.getText().replace(",", "."));

        SihicApp.notasEstudio.setSvegresofc(fcegreso.getText());
        SihicApp.notasEstudio.setSvegresospo2(spo2egreso.getText());
        try {
            SihicApp.notasEstudio.setPeso(Float.valueOf(peso.getText().replace(",", ".")));
        } catch (Exception e) {
            SihicApp.notasEstudio.setPeso(Float.valueOf(0));
        }
        try {
            SihicApp.notasEstudio.setCantiopramida(Float.valueOf(cantiopramida.getText().replace(",", ".")));
        } catch (Exception e) {
            SihicApp.notasEstudio.setCantiopramida(Float.valueOf(0));
        }
        SihicApp.notasEstudio.setMiembrosuperior(miembrosuperior.getText());
        SihicApp.notasEstudio.setEmbarazada(embarazada.isSelected());
        SihicApp.notasEstudio.setHclCie10(SihicApp.hclCie10);
        SihicApp.notasEstudio.setTipo(tiponotaestudio.getSelectionModel().getSelectedIndex());
        SihicApp.notasEstudio.setJelco(jelco.getText());
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
                    if (general.getProperties().get("requerido") != null) {
                        if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                            permitirproceso = false;
                            general.getStylesheets().add(0, "/sihic/personal.css");
                            general.getStylesheets().set(0, "/sihic/personal.css");
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
                            System.out.println("propiedad-->" + general.getProperties().get("requerido"));
                            if (((boolean) general.getProperties().get("requerido") == true)) {

                                general.setStyle(null);
                                general.getStyleClass().add("text-field");
                            }
                        }

                    } else {
                        if (n.getClass() == ChoiceBox.class) {
                            ChoiceBox general = (ChoiceBox) n;
                            if (general.getProperties().get("requerido") != null) {
                                if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                                    general.getStylesheets().set(0, SihicApp.hojaestilos);

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

    public void crearoeditar() {
          int horain;
        int minin;
        int horaat;
        int minat;
        int horafin;
        int minfin ;
        try
        {
        paciente.setText(SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getNombres() + " " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
        
        fechaingreso.setText(sihic.util.UtilDate.formatodiamesyear(SihicApp.facturaItem.getAgnCitas().getFechaIngreso()));
        fechaegreso.setText(sihic.util.UtilDate.formatodiamesyear(SihicApp.facturaItem.getAgnCitas().getFechaIngreso()));
        horain = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
        minin = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
        horaat = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
        minat = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
        horafin = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
        minfin = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
     }catch(Exception e)
     {
         try
         {
           paciente.setText(SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getNombres() + " " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
          
      fechaingreso.setText(sihic.util.UtilDate.formatodiamesyear(SihicApp.facturaItem.getAgnCitas().getFechaHora()));
      fechaegreso.setText(sihic.util.UtilDate.formatodiamesyear(SihicApp.facturaItem.getAgnCitas().getFechaHora()));
      horain = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
      minin = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
      horaat = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
      minat = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
      horafin = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
      minfin = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
         }catch(Exception e3)
         {
            paciente.setText(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getNombres() + " " + SihicApp.facturaItem.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
            fechaingreso.setText(sihic.util.UtilDate.formatodiamesyear(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso()));
            fechaegreso.setText(sihic.util.UtilDate.formatodiamesyear(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso()));
            horain = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
            minin = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
            horaat = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
            minat = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
            horafin = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
            minfin = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
         }
          
      
   
    }
        if (SihicApp.notasEstudio.getId() == null) {
           
            SihicApp.notasEstudio.setHoraingreso(horain);
            SihicApp.notasEstudio.setMinutosingreso(minin);
            SihicApp.notasEstudio.setFacturaItem(SihicApp.facturaItem);
           
            SihicApp.notasEstudio.setHoraingreso(horain);
            SihicApp.notasEstudio.setMinutosingreso(minin);
            SihicApp.notasEstudio.setFacturaItem(SihicApp.facturaItem);
            try
            {
            horaingreso.setText(String.valueOf(SihicApp.facturaItem.getAgnCitas().getGenHorasCita().getHora()));
            minutosingreso.setText(String.valueOf(SihicApp.facturaItem.getAgnCitas().getGenHorasCita().getMinutos() + (int) (Math.random() * 2) + 0));
            horaatencion.setText(String.valueOf(SihicApp.facturaItem.getAgnCitas().getGenHorasCita().getHora()));
            minutosatencion.setText(String.valueOf(SihicApp.facturaItem.getAgnCitas().getGenHorasCita().getMinutos() + (int) (Math.random() * 10) + 2));
            horaegreso.setText(String.valueOf(SihicApp.facturaItem.getAgnCitas().getGenHorasCita().getHora()));
            minutosegreso.setText(String.valueOf(SihicApp.facturaItem.getAgnCitas().getGenHorasCita().getMinutos() + (int) (Math.random() * 30) + 20));
            }catch(Exception e)
            {
                    horaingreso.setText(String.valueOf(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getGenHorasCita().getHora()));
            minutosingreso.setText(String.valueOf(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getGenHorasCita().getMinutos() + (int) (Math.random() * 2) + 0));
            horaatencion.setText(String.valueOf(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getGenHorasCita().getHora()));
            minutosatencion.setText(String.valueOf(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getGenHorasCita().getMinutos() + (int) (Math.random() * 10) + 2));
            horaegreso.setText(String.valueOf(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getGenHorasCita().getHora()));
            minutosegreso.setText(String.valueOf(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getGenHorasCita().getMinutos() + (int) (Math.random() * 30) + 20));
      
            }
        } else {
            horaingreso.setText(String.valueOf(SihicApp.notasEstudio.getHoraingreso()));
            minutosingreso.setText(String.valueOf(SihicApp.notasEstudio.getMinutosingreso()));
            horaatencion.setText(String.valueOf(SihicApp.notasEstudio.getHoraatencion()));
            minutosatencion.setText(String.valueOf(SihicApp.notasEstudio.getMinutosatencion()));
            horaegreso.setText(String.valueOf(SihicApp.notasEstudio.getHoraegreso()));
            minutosegreso.setText(String.valueOf(SihicApp.notasEstudio.getMinutosegreso()));
            acompanante.setText(SihicApp.notasEstudio.getAcompanante());
            detalle.setText(SihicApp.notasEstudio.getDetalle());
            elaboro.setText(SihicApp.notasEstudio.getElaboro());
            pacientellego.getSelectionModel().select(SihicApp.notasEstudio.getPacientellego());
            estadosalida.getSelectionModel().select(SihicApp.notasEstudio.getEstadosalida());
            taingreso.setText(String.valueOf(SihicApp.notasEstudio.getSvingresota()));
            fcingreso.setText(String.valueOf(SihicApp.notasEstudio.getSvingresofc()));
            spo2ingreso.setText(String.valueOf(SihicApp.notasEstudio.getSvingresospo2()));
            taegreso.setText(String.valueOf(SihicApp.notasEstudio.getSvegresota()));
            fcegreso.setText(String.valueOf(SihicApp.notasEstudio.getSvegresofc()));
            spo2egreso.setText(String.valueOf(SihicApp.notasEstudio.getSvegresospo2()));
            peso.setText(String.valueOf(SihicApp.notasEstudio.getPeso()));
            miembrosuperior.setText(SihicApp.notasEstudio.getMiembrosuperior());
            embarazada.setSelected(SihicApp.notasEstudio.isEmbarazada());
            cantiopramida.setText(String.valueOf(SihicApp.notasEstudio.getCantiopramida()));
            jelco.setText(SihicApp.notasEstudio.getJelco());
            af.getSelectionModel().select(SihicApp.notasEstudio.getAf());
            if (SihicApp.notasEstudio.getHclCie10() != null) {
                sb_hclcie10.setText(SihicApp.notasEstudio.getHclCie10().getCodigo() + " || " + SihicApp.notasEstudio.getHclCie10().getDescripcion());
                sp_hclcie10.hide();
                SihicApp.hclCie10 = SihicApp.notasEstudio.getHclCie10();
            } else {
                SihicApp.hclCie10 = null;
                SihicApp.hclCie10 = new HclCie10();
            }

            tiponotaestudio.getSelectionModel().select(SihicApp.notasEstudio.getTipo());
            notaenfermeria.setText(SihicApp.notasEstudio.getNotaenfermeria());
        }
       
    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

    }

    private void delete() {
        if (SihicApp.notasEstudio.getId() != null) {
            notasEstudioControllerClient.deleteNotasEstudio(SihicApp.notasEstudio);
            SihicApp.notasEstudio = null;
            SihicApp.notasEstudio = new NotasEstudio();
            crearoeditar();
            L_Message.setText("Registro Eliminado");

            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            };
            backgroundThread = new Thread(Task_Message);
            // Terminate the running thread if the application exits
            backgroundThread.setDaemon(true);

            // Start the thread
            backgroundThread.start();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
