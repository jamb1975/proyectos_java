/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.general;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modelo.GenPacientes;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.contabilidad.FindServicios;
import sihic.control.SearchBox;
import sihic.controlador.AgnCitasControllerClient;
import sihic.controlador.GenPersonasControllerClient;
import sihic.historiasclinicas.citas.DCitas;
import sihic.historiasclinicas.citas.AsignarCitas;

/**
 *
 * @author adminlinux
 */
public class BuscarPersonas extends Application {

    private GridPane gpbuscarpersonas;
    private TableView ta_buscarpersonas;
    private Label l_buscar;
    private TextField buscar;
    private ObservableList ol_genpersonas = FXCollections.observableArrayList();
    ;
     private List<GenPacientes> l_genpersonas;
    private GenPersonasControllerClient genPersonasControllerClient;
    private String appPathGenPersonas;
    private Class clzGenPersonas;
    private Object appClassGenPersonas;
    private Stage stageGenPersonas = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    private Parent P_GenPersonas;
    private Button asignar;
    private Button personas;
    private FindServicios findProduct;
    private SearchBox servicio = new SearchBox();
    private SearchPopover sp_servicio;
    private Button bu_servicio;
    private AgnCitasControllerClient agnCitasControllerClient;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        servicio.setMaxWidth(300);
        servicio.setMinWidth(300);
        sp_servicio = new SearchPopover(servicio, new PageBrowser(), SihicApp.s_kardex, true);
        sp_servicio.getStylesheets().add(SihicApp.hojaestilos);
        sp_servicio.getSearchBox().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                asignar.requestFocus();
            }
        });
        servicio.setPromptText("Servicio a solicitar");
        ImageView imageView;
        agnCitasControllerClient = new AgnCitasControllerClient();
        stageGenPersonas.setTitle("Datos Personales");
        imageView = new ImageView("/images/asignar.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        asignar = new Button("", imageView);
        asignar.setOnAction(e
                -> {
            try {
                asignarcitapaciente();
            } catch (ParseException ex) {

            }
        });
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        personas = new Button("", imageView);
        personas.setMaxWidth(130);
        personas.setOnAction(e -> {
            try {
                AdminGenPersonas.setGenPacientes();
                showgenpersonas();
            } catch (Exception e2) {

            }
        });
        appPathGenPersonas = "sihic.general.FGenPersonas";
        clzGenPersonas = Class.forName(appPathGenPersonas);
        appClassGenPersonas = clzGenPersonas.newInstance();
        genPersonasControllerClient = new GenPersonasControllerClient();
        l_buscar = new Label("Buscar:");
        buscar = new TextField();
        buscar.setOnKeyReleased(e -> {
            try {
                getGenPersonas();
            } catch (Exception e3) {
            }
        });
        gpbuscarpersonas = new GridPane();
        ta_buscarpersonas = new TableView();
        TableColumn tipodocumento = new TableColumn();
        tipodocumento.setMinWidth(150);
        tipodocumento.setText("Tipo Documento");
        tipodocumento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenPacientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenPacientes, String> genpacientes) {

                return new SimpleStringProperty((genpacientes.getValue().getGenPersonas().getGenTiposDocumentos().getNombre()));

            }
        });
        TableColumn documento = new TableColumn();
        documento.setMinWidth(150);
        documento.setText("Documento");
        documento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenPacientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenPacientes, String> genpacientes) {

                return new SimpleStringProperty((genpacientes.getValue().getGenPersonas().getDocumento()));

            }
        });
        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(250);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenPacientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenPacientes, String> genpacientes) {

                return new SimpleStringProperty(genpacientes.getValue().getGenPersonas().getPrimerNombre() + " " + genpacientes.getValue().getGenPersonas().getPrimerApellido() + " " + genpacientes.getValue().getGenPersonas().getSegundoNombre() + " " + genpacientes.getValue().getGenPersonas().getSegundoApellido());

            }
        });
        TableColumn telefono = new TableColumn();
        telefono.setMinWidth(100);
        telefono.setText("Telefono");
        telefono.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenPacientes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenPacientes, String> genpacientes) {

                return new SimpleStringProperty(genpacientes.getValue().getGenPersonas().getTelefono());

            }
        });
        ta_buscarpersonas.getColumns().addAll(tipodocumento, documento, nombre, telefono);
        ta_buscarpersonas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ta_buscarpersonas.setOnMouseClicked(e -> {
            try {
                checkregistroexistente();
            } catch (Exception ex) {
            }
        });
        stageGenPersonas.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    if (SihicApp.genPacientesTemp != null) {

                        if (SihicApp.genPacientesTemp.getGenPersonas() != null) {
                            buscar.setText(SihicApp.genPacientesTemp.getGenPersonas().getDocumento());
                            try {
                                getGenPersonas();
                            } catch (ParseException ex) {
                                Logger.getLogger(BuscarPersonas.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        });

        gpbuscarpersonas.getStylesheets().add(SihicApp.hojaestilos);
        gpbuscarpersonas.getStyleClass().add("background");
        gpbuscarpersonas.add(l_buscar, 0, 0);
        gpbuscarpersonas.add(buscar, 1, 0);
        gpbuscarpersonas.add(asignar, 2, 0);
        gpbuscarpersonas.add(personas, 3, 0);
        gpbuscarpersonas.add(servicio, 4, 0);
        gpbuscarpersonas.add(new Label("Consultorio: "), 5, 0);
        gpbuscarpersonas.add(LoadChoiceBoxGeneral.getCb_agnconsultorios(), 6, 0);
        gpbuscarpersonas.add(ta_buscarpersonas, 0, 1, 7, 1);
        gpbuscarpersonas.add(sp_servicio, 0, 0, 5, 2);
        gpbuscarpersonas.setVgap(5);
        gpbuscarpersonas.setHgap(5);
        return gpbuscarpersonas;
    }

    private void getGenPersonas() throws ParseException {
        //colocamos los datos

        l_genpersonas = genPersonasControllerClient.getGenpacientes(buscar.getText().trim());

        ol_genpersonas.clear();

        ol_genpersonas.addAll(l_genpersonas.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_buscarpersonas.setItems(ol_genpersonas);

    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((GenPacientes) ta_buscarpersonas.getSelectionModel().getSelectedItem()) != null) {
            SihicApp.genPacientesTemp = (GenPacientes) ta_buscarpersonas.getSelectionModel().getSelectedItem();

        }
    }

    private void showgenpersonas() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        P_GenPersonas = null;
        P_GenPersonas = (Parent) clzGenPersonas.getMethod("createContent").invoke(appClassGenPersonas);
        scene = null;
        scene = new Scene(P_GenPersonas, Color.TRANSPARENT);

        if (stageGenPersonas.isShowing()) {
            stageGenPersonas.close();
        }

//set scene to stage
        stageGenPersonas.sizeToScene();

        //center stage on screen
        stageGenPersonas.centerOnScreen();
        stageGenPersonas.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageGenPersonas.show();
    }

    public void asignarcitapaciente() throws ParseException {
        if (SihicApp.genPacientesTemp != null) {
            if (SihicApp.genPacientesTemp.getGenPersonas() != null) {
                if (SihicApp.s_kardex.getProducto() != null) {

                    SihicApp.agnCitasTemp.setGenPacientes(SihicApp.genPacientesTemp);
                    SihicApp.agnCitasTemp.setServicio(SihicApp.s_kardex.getProducto());
                    SihicApp.agnCitasTemp.setAgnConsultorios(LoadChoiceBoxGeneral.getV_agnconsultorios().get(LoadChoiceBoxGeneral.getCb_agnconsultorios().getSelectionModel().getSelectedIndex()));
                    if (LoadChoiceBoxGeneral.getCb_agnconsultorios().getSelectionModel().getSelectedIndex() < 0) {
                        SihicApp.agnCitasTemp.setAgnConsultorios(LoadChoiceBoxGeneral.getV_agnconsultorios().get(LoadChoiceBoxGeneral.getCb_agnconsultorios().getSelectionModel().getSelectedIndex()));
                    }
                    DCitas.cambiarestadocitayfechahora(1);
                    AsignarCitas.closeStage();
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
