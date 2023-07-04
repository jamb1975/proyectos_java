/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.consultas;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import jenum.EnumEstadosCita;
import modelo.AgnCitas;
import modelo.GenPacientes;
import sihic.SihicApp;
import sihic.controlador.AgnCitasControllerClient;
import sihic.controlador.GenPersonasControllerClient;
import sihic.controlador.HclConsultasControllerClient;
import sihic.historiasclinicas.citas.DCitas;

/**
 *
 * @author adminlinux
 */
public class VerConsultas extends Application {

    private GridPane gpbuscarcitasagendadas;
    private StackPane stackPane;
    private TableView ta_buscarcitasagendadas;
    private Label l_buscar;
    private TextField buscar;
    private ObservableList ol_agncitas = FXCollections.observableArrayList();
    ;
    private List<AgnCitas> l_agncitas;
    private GenPersonasControllerClient genPersonasControllerClient;
    private String appPathHclHistoriaClinica;
    private Class clzHclHistoriaClinica;
    private Object appClassHclHistoriaClinica;
    private Stage stageHclHistoriaClinica = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    private Parent P_HclConsultas;
    private DatePicker dp_fechaconsulta;

    private Button consulta;
    public static GenPacientes genPacientesTemp = new GenPacientes();
    private AgnCitasControllerClient agnCitasControllerClient;
    HclConsultasControllerClient hclConsultasControllerClient;
    private TitledPane tp_generic;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        tp_generic = new TitledPane();
        tp_generic.setText("Consultas Médicas");
        tp_generic.setCollapsible(false);
        dp_fechaconsulta = new DatePicker(LocalDate.now());
        dp_fechaconsulta.setPromptText("Fecha de Consulta");
        stackPane = new StackPane();
        ImageView imageView;
        agnCitasControllerClient = new AgnCitasControllerClient();
        hclConsultasControllerClient = new HclConsultasControllerClient();
        stageHclHistoriaClinica.setTitle("Facturar Procedimientos Médicos");
        imageView = new ImageView("/images/consulta.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        consulta = new Button("", imageView);
        consulta.setDisable(true);
        consulta.setOnAction(e -> {
            try {
                iniciarconsulta();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });
        //**********Ventana Historia Clinica
        appPathHclHistoriaClinica = "sihic.historiasclinicas.historialpaciente.FHclHistoriaClinica";
        clzHclHistoriaClinica = Class.forName(appPathHclHistoriaClinica);
        appClassHclHistoriaClinica = clzHclHistoriaClinica.newInstance();

        //**********Historia Clinica
        l_buscar = new Label("Buscar:");
        buscar = new TextField();
        buscar.setOnKeyPressed(e -> {
            try {
                getCitasProceso();
            } catch (Exception e3) {
            }
        });
        dp_fechaconsulta.setOnAction(e -> {
            try {
                getCitasProceso();
            } catch (ParseException ex) {
                Logger.getLogger(VerConsultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        gpbuscarcitasagendadas = new GridPane();
        ta_buscarcitasagendadas = new TableView();
        ta_buscarcitasagendadas.setOnMouseClicked(e -> {
            try {
                checkregistroexistente();
            } catch (Exception e2) {

            }
        });
        TableColumn tipodocumento = new TableColumn();
        tipodocumento.setMinWidth(150);
        tipodocumento.setText("Tipo Documento");
        tipodocumento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty((agnCitas.getValue().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getNombre()));

            }
        });
        TableColumn documento = new TableColumn();
        documento.setMinWidth(150);
        documento.setText("Documento");
        documento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty((agnCitas.getValue().getGenPacientes().getGenPersonas().getDocumento()));

            }
        });
        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(300);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty(agnCitas.getValue().getGenPacientes().getGenPersonas().getPrimerNombre() + " " + agnCitas.getValue().getGenPacientes().getGenPersonas().getPrimerApellido() + " " + agnCitas.getValue().getGenPacientes().getGenPersonas().getSegundoNombre() + " " + agnCitas.getValue().getGenPacientes().getGenPersonas().getSegundoApellido());

            }
        });
        TableColumn telefono = new TableColumn();
        telefono.setMinWidth(150);
        telefono.setText("Telefono");
        telefono.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty(agnCitas.getValue().getGenPacientes().getGenPersonas().getTelefono());

            }
        });
        TableColumn medico = new TableColumn();
        medico.setMinWidth(300);
        medico.setText("Médico");
        medico.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty(agnCitas.getValue().getAgnMedicos().getGenPersonas().getTelefono());

            }
        });
        ta_buscarcitasagendadas.getColumns().addAll(tipodocumento, documento, nombre, telefono, medico);
        ta_buscarcitasagendadas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ta_buscarcitasagendadas.setOnMouseClicked(e -> {
            try {
                checkregistroexistente();
            } catch (Exception ex) {
            }
        });
        stageHclHistoriaClinica.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    terminarconsulta();
                } catch (Exception e) {

                }
            }
        });
        gpbuscarcitasagendadas.getStylesheets().add(SihicApp.hojaestilos);
        gpbuscarcitasagendadas.getStyleClass().add("background");
        gpbuscarcitasagendadas.add(l_buscar, 0, 0);
        gpbuscarcitasagendadas.add(buscar, 1, 0);
        gpbuscarcitasagendadas.add(dp_fechaconsulta, 2, 0);
        gpbuscarcitasagendadas.add(consulta, 3, 0);

        gpbuscarcitasagendadas.add(ta_buscarcitasagendadas, 0, 1, 4, 1);
        gpbuscarcitasagendadas.setVgap(5);
        gpbuscarcitasagendadas.setHgap(5);
        gpbuscarcitasagendadas.setMinWidth(1070);
        tp_generic.setContent(gpbuscarcitasagendadas);
        stackPane.getChildren().add(tp_generic);
        return stackPane;
    }

    private void getCitasProceso() throws ParseException {
        //colocamos los datos

        try {
            l_agncitas = agnCitasControllerClient.getCitasProceso(buscar.getText().trim(), dp_fechaconsulta.getValue().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ol_agncitas.clear();

        ol_agncitas.addAll(l_agncitas.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_buscarcitasagendadas.setItems(ol_agncitas);

    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((AgnCitas) ta_buscarcitasagendadas.getSelectionModel().getSelectedItem()) != null) {
            SihicApp.agnCitasTemp = (AgnCitas) ta_buscarcitasagendadas.getSelectionModel().getSelectedItem();
            consulta.setDisable(false);
        }
    }

    private void showconsulta() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            P_HclConsultas = null;
            P_HclConsultas = (Parent) clzHclHistoriaClinica.getMethod("createContent").invoke(appClassHclHistoriaClinica);

            scene = null;
            scene = new Scene(P_HclConsultas, Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (stageHclHistoriaClinica.isShowing()) {
            stageHclHistoriaClinica.close();
        }

//set scene to stage
        stageHclHistoriaClinica.sizeToScene();
        stageHclHistoriaClinica.setTitle("Historia Médica Paciente " + SihicApp.agnCitasTemp.getGenPacientes().getGenPersonas().getNombres());

        //center stage on screen
        stageHclHistoriaClinica.centerOnScreen();
        stageHclHistoriaClinica.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageHclHistoriaClinica.show();
    }

    public void iniciarconsulta() throws ParseException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DCitas.cambiarestadocitayfechahora(EnumEstadosCita.INICIADA.ordinal());
        getCitasProceso();
        showconsulta();

    }

    public void terminarconsulta() throws ParseException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DCitas.cambiarestadocitayfechahora(EnumEstadosCita.TERMINADA.ordinal());
        getCitasProceso();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
