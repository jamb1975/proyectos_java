/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad.documentos;

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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import jenum.EnumEstadosCita;
import modelo.AgnCitas;
import modelo.Factura;
import modelo.GenPacientes;
import modelo.HclConsultas;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.controlador.AgnCitasControllerClient;
import sihic.controlador.GenConveniosEapbControllerClient;
import sihic.controlador.GenPersonasControllerClient;
import sihic.controlador.HclConsultasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.historiasclinicas.citas.DCitas;
import sihic.util.UtilDate;

/**
 *
 * @author adminlinux
 */
public class Facturar extends Application {

    private GridPane gp_generic;
    private TableView tv_generic;
    private Label l_buscar;
    private TextField buscar;
    private Label la_nofactura;
    private SearchBox nofactura = new SearchBox();
    private SearchPopover sp_nofactura;
    private Label la_estfacturacion;
    private ChoiceBox estfacturacion;
    private ObservableList ol_agncitas = FXCollections.observableArrayList();
    ;
    private List<AgnCitas> l_agncitas;
    private GenPersonasControllerClient genPersonasControllerClient;
    private GenConveniosEapbControllerClient genConveniosEapbControllerClient;
    private String appPathGenPersonas;
    private Class clzGenPersonas;
    private Object appClassGenPersonas;
    private Stage stageGenPersonas = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    private Parent P_GenPersonas;
    private String appPathFactura;
    private Class clzFactura;
    private Object appClassFactura;
    private Stage stageFactura = new Stage(StageStyle.DECORATED);
    public static Scene sceneFactura = null;
    private Parent P_Factura;

    private Button facturar;
    public static GenPacientes genPacientesTemp = new GenPacientes();
    private AgnCitasControllerClient agnCitasControllerClient;
    HclConsultasControllerClient hclConsultasControllerClient;
    private Label la_tipofactura;
    private ChoiceBox tipofactura;
    private Label la_prefijo;
    private Label la_tarifa;
    private Label la_conveiop;
    private ChoiceBox prefijo;
    private ChoiceBox tarifa;
    private CheckBox cb_nuevafactura;
    public static int tipofact;
    public static int prefijofact;
    public static int s_tarifa;
    public static Long numerofactura;
    public static boolean nuevafactura;
    public static boolean facturado;
    private StackPane stack;
    private FacturaApp facturaApp;
    private Alert alert;
    private Label la_noautorizacion;
    private TextField noautorizacion;
    private ImageView imageView;
    Alert alert3 = new Alert(AlertType.INFORMATION);
    private DatePicker dfecha;
    String tipof = "";
    private TitledPane tp_generic;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, NoSuchMethodException, NoSuchMethodException, ParseException {
        SihicApp.s_factura = null;
        SihicApp.s_factura = new Factura();
        tp_generic = new TitledPane();
        tp_generic.setText("Facturación de Convenios, Particulares, Soat e Individual");
        tp_generic.setCollapsible(false);
        sp_nofactura = new SearchPopover(nofactura, new PageBrowser(), SihicApp.s_factura, false);
        nofactura.setOnAction(e -> {
            LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.setValue(SihicApp.s_factura.getPrefijo());

            if (SihicApp.s_factura.getFacturaItems().size() > 0) {
                dfecha.setValue(UtilDate.formatoyearmesdia(SihicApp.s_factura.getFacturaItems().get(0).getHclConsultas().getAgnCitas().getFechaHora()));
                // buscar.setText(SihicApp.s_factura.getHclConsultas().getHclHistoriasClinicas().getGenPacientes().getGenPersonas().getDocumento());
                try {
                    getCitasAgendadas();
                } catch (ParseException ex) {
                    Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        tipofactura = new ChoiceBox();
        tarifa = new ChoiceBox();
        dfecha = new DatePicker(LocalDate.now());
        dfecha.setOnAction(e -> {
            try {
                getCitasAgendadas();
            } catch (ParseException ex) {
                Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        la_noautorizacion = new Label("N° Autorización: ");
        noautorizacion = new TextField();
        noautorizacion.setPromptText("Digite N° Autorización");
        genConveniosEapbControllerClient = new GenConveniosEapbControllerClient();
        stageFactura.setTitle("Factura de Usuarios");
        stack = new StackPane();
        la_tipofactura = new Label("Tipo Factura");
        la_prefijo = new Label("Prefijo:");
        la_tarifa = new Label("Tarifa:");
        la_conveiop = new Label("Convenio Particular:");
        cb_nuevafactura = new CheckBox("Nueva Factura");
        cb_nuevafactura.setStyle("-fx-text-fill: white;");
        prefijo = new ChoiceBox();
        tipofactura.getItems().add(0, "Convenio o Contrato Tipo A");
        tipofactura.getItems().add(1, "Particular Tipo E");
        tipofactura.getItems().add(2, "Soat Tipo T");
        tipofactura.getItems().add(3, "Individual P");
        prefijo.getItems().add(0, "A");
        prefijo.getItems().add(1, "E");
        prefijo.getItems().add(2, "T");

        tarifa.getItems().add(0, "2015");
        tarifa.getItems().add(1, "2016");
        tarifa.getItems().add(2, "2017");
        tarifa.getItems().add(3, "2018");
        tarifa.getItems().add(4, "2001");
        tarifa.getSelectionModel().select(0);
        tipofactura.getSelectionModel().select(0);
        prefijo.getSelectionModel().select(0);
        la_estfacturacion = new Label("Facturados:");
        estfacturacion = new ChoiceBox();
        estfacturacion.getItems().add(0, "No");
        estfacturacion.getItems().add(1, "Si");
        estfacturacion.getSelectionModel().select(0);
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().select(0);
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);

        estfacturacion.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged2);
        la_nofactura = new Label("N° Factura:");

        nofactura.setPromptText("Digitar N° Factura");
        agnCitasControllerClient = new AgnCitasControllerClient();
        hclConsultasControllerClient = new HclConsultasControllerClient();
        stageGenPersonas.setTitle("Facturar Procedimientos Médicos");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText("No Tiene Eps Facturar: tipo E o T o Falta N° Autorización");
        alert.getDialogPane().setHeaderText(null);
        Alert alert2 = new Alert(AlertType.INFORMATION);
        Alert alert7 = new Alert(AlertType.INFORMATION);
        Alert alert8 = new Alert(AlertType.INFORMATION);
        Alert alert9 = new Alert(AlertType.INFORMATION);
        Alert alert10 = new Alert(AlertType.INFORMATION);
        Alert alert11 = new Alert(AlertType.INFORMATION);
        alert2.initModality(Modality.APPLICATION_MODAL);
        alert2.getDialogPane().setContentText("Debe escoger año de tarifa");
        alert2.getDialogPane().setHeaderText(null);
        alert7.initModality(Modality.APPLICATION_MODAL);
        alert7.getDialogPane().setContentText("Eps Factura y Tipo de usuario no coinciden");
        alert7.getDialogPane().setHeaderText(null);
        alert10.initModality(Modality.APPLICATION_MODAL);
        alert10.getDialogPane().setContentText("Debe Escoger una factura");
        alert10.getDialogPane().setHeaderText(null);
        alert11.initModality(Modality.APPLICATION_MODAL);
        alert11.getDialogPane().setContentText(" Prefijo Factura y el Prefijo no coinciden ");
        alert11.getDialogPane().setHeaderText(null);

        alert8.initModality(Modality.APPLICATION_MODAL);
        alert8.getDialogPane().setContentText("Hay alguna Factura con Valor $0  El Sistema No deja Crear Otra Factura");
        alert8.getDialogPane().setHeaderText(null);
        alert9.initModality(Modality.APPLICATION_MODAL);
        alert9.getDialogPane().setContentText("La facturas tipo A deben ser Buscadas");
        alert9.getDialogPane().setHeaderText(null);
        alert3.initModality(Modality.APPLICATION_MODAL);
        alert3.getDialogPane().setHeaderText(null);
        alert3.getDialogPane().setContentText("Esta seguro que quiere facturar con este tipo y tarifa");
        imageView = new ImageView("/images/factura.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        facturar = new Button("", imageView);
        facturar.setMaxWidth(130);
        facturar.setDisable(true);

        facturar.setOnAction(e
                -> {

            tipof = LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString();
            if (tipof.substring(0, 1).equals("A")) {
                SihicApp.s_factura = SihicApp.s_facturaControllerClient.FindLAstFacturaEps(SihicApp.agnCitasTemp, LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString());
            }
            try {

                if ((LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("A") && (SihicApp.agnCitasTemp.getGenPacientes().getGenEapb() == null || noautorizacion.getText().equals("")))) {

                    alert.showAndWait();

                } else {
                    if (tarifa.getSelectionModel().getSelectedIndex() == -1) {

                        alert2.showAndWait();
                    } else {
                        boolean _facturar = true;
                        if (estfacturacion.getSelectionModel().getSelectedIndex() == 1 && SihicApp.s_factura == null) {
                            alert10.showAndWait();
                        } else {
                            if (SihicApp.s_factura == null) {
                                SihicApp.s_factura = new Factura();
                                SihicApp.s_factura.setPrefijo("");
                            } else {
                                if (SihicApp.s_factura.getId() == null) {
                                    SihicApp.s_factura.setPrefijo("");
                                }

                            }

                            if (!SihicApp.s_factura.getPrefijo().equals(tipof) && estfacturacion.getSelectionModel().getSelectedIndex() == 1) {
                                if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("A")) {
                                    alert11.showAndWait();
                                    _facturar = false;
                                }
                            }
                            if (!SihicApp.s_factura.getPrefijo().equals(tipof) || SihicApp.s_factura.getId() == null && estfacturacion.getSelectionModel().getSelectedIndex() == 1) {
                                if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("A")) {
                                    alert11.showAndWait();
                                    _facturar = false;
                                }
                            } else {
                                if (SihicApp.s_factura.getId() != null) {
                                    if (!SihicApp.s_factura.getPrefijo().equals(tipof)) {

                                        alert11.showAndWait();
                                        _facturar = false;
                                    }
                                    if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("P") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A")) {
                                        if (SihicApp.agnCitasTemp.getGenPacientes().getGenEapb() != null && SihicApp.agnCitasTemp.getGenPacientes().getGenTiposUsuarios() != null) {
                                            if (!SihicApp.s_factura.getGenEapb().getId().equals(SihicApp.agnCitasTemp.getGenPacientes().getGenEapb().getId()) || !SihicApp.s_factura.getGenTiposUsuarios().getId().equals(SihicApp.agnCitasTemp.getGenPacientes().getGenTiposUsuarios().getId())) {
                                                alert7.showAndWait();
                                                _facturar = false;
                                            }
                                        } else {
                                            alert7.showAndWait();
                                            _facturar = false;
                                        }
                                    } else {
                                        if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A")) {
                                            if (SihicApp.s_factura.getGenEapb() != SihicApp.agnCitasTemp.getGenPacientes().getGenEapb() || SihicApp.s_factura.getGenTiposUsuarios() != SihicApp.agnCitasTemp.getGenPacientes().getGenTiposUsuarios()) {
                                                alert7.showAndWait();
                                                _facturar = false;
                                            }

                                        }
                                    }
                                }
                            }
                            if (!tipof.substring(0, 1).equals("A") && SihicApp.s_facturaControllerClient.validarfacturatotalcero(tipof) > 0 && SihicApp.s_factura.getId() == null) {
                                _facturar = false;
                                alert8.showAndWait();
                            }
                            if (tipof.substring(0, 1).equals("A") && SihicApp.s_factura.getId() == null) {
                                _facturar = false;
                                alert9.showAndWait();
                            }
                            if (_facturar) {

                                try {
                                    try {
                                        if (estfacturacion.getSelectionModel().getSelectedIndex() == 0) {
                                            if (asignarconsulta()) {
                                                showfactura();
                                            }
                                        } else {
                                            findconsulta();
                                            showfactura();
                                        }

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                    //});
                                } catch (Exception ex) {
                                    Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        });

        //**********Ventana Personas
        appPathGenPersonas = "sihic.general.FGenPersonas";
        clzGenPersonas = Class.forName(appPathGenPersonas);
        appClassGenPersonas = clzGenPersonas.newInstance();
        //**********Ventana Factura
        /*appPathFactura="sihic.contabilidad.FacturaApp";
           clzFactura = Class.forName(appPathFactura);
           appClassFactura=clzFactura.newInstance();*/
        genPersonasControllerClient = new GenPersonasControllerClient();
        l_buscar = new Label("Buscar:");
        buscar = new TextField();
        buscar.setOnKeyReleased(e -> {
            try {
                getCitasAgendadas();
            } catch (Exception e3) {
            }
        });
        buscar.setPromptText("Digite el Nombre o N° Ident Paciente");
        gp_generic = new GridPane();
        tv_generic = new TableView();

        stageFactura.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    try {
                        getCitasAgendadas();
                        SihicApp.s_factura = null;
                        SihicApp.s_factura = new Factura();
                        nofactura.setText("");
                    } catch (ParseException ex) {
                        Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        TableColumn fecha = new TableColumn();
        fecha.setMinWidth(150);
        fecha.setText("Fecha Cita");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty(sihic.util.UtilDate.formatodiamesyear(agnCitas.getValue().getFechaHora()));

            }
        });
        TableColumn hora = new TableColumn();
        hora.setMinWidth(150);
        hora.setText("Hora Cita");
        hora.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty(agnCitas.getValue().getGenHorasCita().getHora() + ":" + agnCitas.getValue().getGenHorasCita().getMinutos() + " " + (agnCitas.getValue().getGenHorasCita().getZona() == 0 ? "AM" : "PM"));

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
        documento.setMinWidth(100);
        documento.setText("Documento");
        documento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty((agnCitas.getValue().getGenPacientes().getGenPersonas().getDocumento()));

            }
        });
        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(200);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty(agnCitas.getValue().getGenPacientes().getGenPersonas().getPrimerNombre() + " " + agnCitas.getValue().getGenPacientes().getGenPersonas().getPrimerApellido() + " " + agnCitas.getValue().getGenPacientes().getGenPersonas().getSegundoNombre() + " " + agnCitas.getValue().getGenPacientes().getGenPersonas().getSegundoApellido());

            }
        });
        TableColumn telefono = new TableColumn();
        telefono.setMinWidth(100);
        telefono.setText("Telefono");
        telefono.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty(agnCitas.getValue().getGenPacientes().getGenPersonas().getTelefono());

            }
        });
        TableColumn medico = new TableColumn();
        medico.setMinWidth(250);
        medico.setText("Médico");
        medico.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {

                return new SimpleStringProperty(agnCitas.getValue().getAgnMedicos().getGenPersonas().getNombres());

            }
        });
        TableColumn nombres = new TableColumn();
        nombres.setMinWidth(250);
        nombres.setText("Eps");
        nombres.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {
                try {
                    return new SimpleStringProperty(agnCitas.getValue().getGenPacientes().getGenEapb().getNombre());

                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }
            }
        });
        TableColumn tipousuario = new TableColumn();
        tipousuario.setMinWidth(200);
        tipousuario.setText("Tipo Usuario");
        tipousuario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agnCitas) {
                try {
                    return new SimpleStringProperty(agnCitas.getValue().getGenPacientes().getGenTiposUsuarios().getNombre());

                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }
            }
        });

        tv_generic.setOnMouseClicked(e -> {
            try {
                checkregistroexistente();
            } catch (Exception ex) {
            }
        });
        tv_generic.setMinHeight(577);
        //Ta_FacturaItems.setMinHeight((Gp_Factura.getLayoutBounds().getWidth()*0.20)-50);

        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.getColumns().addAll(fecha, hora, nombres, tipousuario, tipodocumento, documento, nombre, telefono, medico);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");

        gp_generic.add(buscar, 0, 0);
        gp_generic.add(dfecha, 1, 0);
        gp_generic.add(facturar, 2, 0);
        gp_generic.add(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal, 3, 0);
        gp_generic.add(noautorizacion, 4, 0);
        gp_generic.add(tarifa, 5, 0);
        gp_generic.add(estfacturacion, 6, 0);
        gp_generic.add(nofactura, 7, 0);
        gp_generic.add(tv_generic, 0, 1, 8, 1);
        gp_generic.add(sp_nofactura, 6, 1, 2, 2);
        gp_generic.setVgap(5);
        gp_generic.setHgap(5);
        gp_generic.setMinWidth(1340);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generic);
        stack.getChildren().add(tp_generic);
        getCitasAgendadas();
        return stack;
    }

    private void getCitasAgendadas() throws ParseException {
        //colocamos los datos

        try {
            l_agncitas = agnCitasControllerClient.getCitasAgendadas(buscar.getText().trim(), estfacturacion.getSelectionModel().getSelectedIndex() == 0 ? 1 : 2, dfecha.getValue().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ol_agncitas.clear();

        ol_agncitas.addAll(l_agncitas.toArray());
        // Ta_KardexProducto.getItems().clear();
        tv_generic.setItems(ol_agncitas);

    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((AgnCitas) tv_generic.getSelectionModel().getSelectedItem()) != null) {
            SihicApp.agnCitasTemp = (AgnCitas) tv_generic.getSelectionModel().getSelectedItem();
            facturar.setDisable(false);
            if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("P") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A")) {
                noautorizacion.setText(SihicApp.s_factura.getHclConsultas().getNumeroautorizacion());
            }
        }
    }

    public boolean asignarconsulta() throws ParseException {
        Alert alerveriifca = new Alert(Alert.AlertType.INFORMATION);
        alerveriifca.initModality(Modality.APPLICATION_MODAL);

        facturado = false;
        SihicApp.hclconsultas = null;
        SihicApp.hclconsultas = new HclConsultas();
        s_tarifa = tarifa.getSelectionModel().getSelectedIndex();
        try {
            numerofactura = Long.valueOf(nofactura.getText());
        } catch (Exception e) {
            numerofactura = Long.valueOf(0);
        }
        if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("E")) {
            if (LoadChoiceBoxGeneral.getCb_conveniosp().getSelectionModel().getSelectedIndex() > 0) {
                SihicApp.genConvenios = LoadChoiceBoxGeneral.getV_conveniosp().get(LoadChoiceBoxGeneral.getCb_conveniosp().getSelectionModel().getSelectedIndex());
            } else {
                SihicApp.genConvenios = null;
            }

        }
        if (SihicApp.agnCitasTemp.getId() != null) {
            if (hclConsultasControllerClient.findconsulta(SihicApp.agnCitasTemp) == null) {
                DCitas.cambiarestadocitayfechahora(EnumEstadosCita.PROCESO.ordinal());
                SihicApp.hclconsultas.setAgnCitas(SihicApp.agnCitasTemp);
                if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("A") || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("P")) {
                    SihicApp.hclconsultas.setNumeroautorizacion(noautorizacion.getText().trim());
                }
                hclConsultasControllerClient.save(SihicApp.hclconsultas);
                return true;
            } else {
                alerveriifca.getDialogPane().setContentText("No se Puede Utilizar esta Cita Cuando ya ha sido Asignada a Otra Consulta");
                alerveriifca.showAndWait();
                return false;
            }
        } else {
            return false;
        }
    }

    public void findconsulta() {
        facturado = true;
        s_tarifa = tarifa.getSelectionModel().getSelectedIndex();
        try {
            numerofactura = Long.valueOf(nofactura.getText());
        } catch (Exception e) {
            numerofactura = Long.valueOf(0);
        }
        if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("E")) {
            if (LoadChoiceBoxGeneral.getCb_conveniosp() == null) {
                SihicApp.genConvenios = genConveniosEapbControllerClient.findConvenios(LoadChoiceBoxGeneral.getCb_conveniosp().getValue().toString());
            }

        }
        SihicApp.hclconsultas = hclConsultasControllerClient.findconsulta(SihicApp.agnCitasTemp);
        if (SihicApp.hclconsultas == null) {
            SihicApp.hclconsultas = new HclConsultas();
        }

        SihicApp.hclconsultas.setAgnCitas(SihicApp.agnCitasTemp);

        if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("A") || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("P")) {
            SihicApp.hclconsultas.setNumeroautorizacion(noautorizacion.getText().trim());
        }
        hclConsultasControllerClient.save(SihicApp.hclconsultas);

    }

    private void showfactura() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        facturaApp = null;
        facturaApp = new FacturaApp();
        facturaApp.getStageFactura().setOnHidden(e -> {

            try {
                getCitasAgendadas();
                SihicApp.s_factura = null;
                SihicApp.s_factura = new Factura();
                nofactura.setText("");
            } catch (ParseException ex) {
                SihicApp.s_factura = null;
                SihicApp.s_factura = new Factura();
                nofactura.setText("");
            }
        });
        facturaApp.showfactura();
    }
    // A change listener to track the change in selected index

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

//LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().select(newValue);
        if (newValue.intValue() == 0 || newValue.intValue() == 1) {

            if (gp_generic.getChildren().indexOf(LoadChoiceBoxGeneral.getCb_conveniosp()) != -1) {
                gp_generic.getChildren().removeAll(LoadChoiceBoxGeneral.getCb_conveniosp());

                gp_generic.add(noautorizacion, 4, 0);

            }
        } else {

            if (gp_generic.getChildren().indexOf(la_conveiop) == -1) {
                gp_generic.getChildren().removeAll(la_noautorizacion, noautorizacion, LoadChoiceBoxGeneral.getCb_conveniosp());

                gp_generic.add(LoadChoiceBoxGeneral.getCb_conveniosp(), 4, 0);
            }
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void indexChanged2(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {

            getCitasAgendadas();

        } catch (Exception e) {

        }
    }

}
