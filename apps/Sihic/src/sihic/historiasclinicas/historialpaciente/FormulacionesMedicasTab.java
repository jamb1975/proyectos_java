package sihic.historiasclinicas.historialpaciente;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modelo.HclFormulacionMedicamentos;
import modelo.HclFormulacionProcedimientos;
import modelo.HclFormulacionesMedicas;
import sihic.SihicApp;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.BuscarPersonas;

public class FormulacionesMedicasTab extends Tab {

    private String appPathHclGeneric;
    private Class ClzHclGeneric;
    private Object appClassHclGeneric;
    private Stage stageHclGeneric = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    private Parent P_Generic;
    private static TableView ta_hclformulacionesmedicas;
    private static TableView ta_formulacionprocedimientos;
    private static TableView ta_hclformulacionmedicamentos;

    private TableColumn codigofmed;
    private TableColumn descripcionfmed;
    private TableColumn codigofpro;
    private TableColumn descripcionfpro;
    private TableColumn fechaformulacion;
    private TableColumn ordenmedica;
    private TableColumn ordenterapeutica;

//procesos datos
    private static ObservableList ol_hclformulasmedicas = FXCollections.observableArrayList();
    private static List<HclFormulacionesMedicas> l_formulasmedicas;
    private static ObservableList ol_hclformulacionmedicamentos = FXCollections.observableArrayList();
    ;
  private static List<HclFormulacionMedicamentos> l_hclformulacionmedicamentos;
    private static ObservableList ol_hclformulacionprocedimientos = FXCollections.observableArrayList();
    private static List<HclFormulacionProcedimientos> l_hclformulacionprocedimientos;
    private static HclHistoriasClinicasControllerClient hclHistoriasClinicasControllerClient;
    private Button Nuevofm;
    private Button Nuevofmed;
    private Button Nuevofp;

    private ImageView imageView;
    private static int opc;

    public FormulacionesMedicasTab(String text, Node graphic) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        this.setText(text);
        this.setGraphic(graphic);
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        Nuevofm = new Button("", imageView);
        Nuevofm.setMaxWidth(150);
        Nuevofm.setOnAction(e
                -> {
            try {
                opc = 1;
                show();
            } catch (Exception ex) {
                Logger.getLogger(BuscarPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        Nuevofmed = new Button("", imageView);
        Nuevofmed.setMaxWidth(150);
        Nuevofmed.setOnAction(e
                -> {
            try {
                opc = 2;
                show();
            } catch (Exception ex) {
                Logger.getLogger(BuscarPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        Nuevofp = new Button("", imageView);
        Nuevofp.setMaxWidth(150);
        Nuevofp.setOnAction(e
                -> {
            try {
                opc = 3;
                show();
            } catch (Exception ex) {
                Logger.getLogger(BuscarPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ta_formulacionprocedimientos = new TableView();
        ta_hclformulacionesmedicas = new TableView();
        ta_hclformulacionmedicamentos = new TableView();
        codigofmed = new TableColumn();
        codigofpro = new TableColumn();
        descripcionfmed = new TableColumn();
        descripcionfpro = new TableColumn();
        fechaformulacion = new TableColumn();
        ordenmedica = new TableColumn();
        ordenterapeutica = new TableColumn();
        stageHclGeneric.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    switch (opc) {
                        case 1: {
                            try {
                                getFormulasMedicas();
                            } catch (ParseException ex) {
                            }
                        }
                        break;
                        case 2: {
                            try {
                                getFormulacionMedicamentos();
                            } catch (ParseException ex) {
                            }
                        }
                        break;
                        case 3: {
                            try {
                                getFormulacionProcedimientos();
                            } catch (ParseException ex) {
                            }
                        }
                        break;

                    }
                }
            }
        });
        init();
    }

    public void init() throws ParseException {

        GridPane grid = new GridPane();
        grid.setStyle("padding-top:15px;");
        GridPane.setValignment(Nuevofm, VPos.TOP);
        GridPane.setValignment(Nuevofmed, VPos.TOP);
        GridPane.setValignment(Nuevofp, VPos.TOP);

        grid.addRow(1, ta_hclformulacionesmedicas, Nuevofm);
        grid.addRow(2, ta_hclformulacionmedicamentos, Nuevofmed);
        grid.addRow(3, ta_formulacionprocedimientos, Nuevofp);

        grid.setVgap(5);
        grid.setHgap(5);
        hclHistoriasClinicasControllerClient = new HclHistoriasClinicasControllerClient();
        columnasTable();
        this.setContent(grid);

    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        P_Generic = null;
        switch (opc) {
            case 1:
                stageHclGeneric.setTitle("Formulaciones Médicas");
                appPathHclGeneric = "sihic.historiasclinicas.historialpaciente.FHclFormulacionesMedicas";
                ClzHclGeneric = Class.forName(appPathHclGeneric);
                appClassHclGeneric = ClzHclGeneric.newInstance();
                P_Generic = (Parent) ClzHclGeneric.getMethod("createContent").invoke(appClassHclGeneric);
                break;

            case 2:
                stageHclGeneric.setTitle("Formular Medicamentos");
                appPathHclGeneric = "sihic.historiasclinicas.historialpaciente.FHclFormulacionMedicamentos";
                ClzHclGeneric = Class.forName(appPathHclGeneric);
                appClassHclGeneric = ClzHclGeneric.newInstance();
                P_Generic = (Parent) ClzHclGeneric.getMethod("createContent").invoke(appClassHclGeneric);
                break;

            case 3:
                stageHclGeneric.setTitle("Formular Cups");
                appPathHclGeneric = "sihic.historiasclinicas.historialpaciente.FHclFormulacionProcedimientos";
                ClzHclGeneric = Class.forName(appPathHclGeneric);
                appClassHclGeneric = ClzHclGeneric.newInstance();
                P_Generic = (Parent) ClzHclGeneric.getMethod("createContent").invoke(appClassHclGeneric);
                break;

        }
        scene = null;
        scene = new Scene(P_Generic, Color.TRANSPARENT);

        if (stageHclGeneric.isShowing()) {
            stageHclGeneric.close();
        }

//set scene to stage
        stageHclGeneric.sizeToScene();

        //center stage on screen
        stageHclGeneric.centerOnScreen();
        stageHclGeneric.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageHclGeneric.show();
    }

    public void columnasTable() {

        fechaformulacion.setMinWidth(100);
        fechaformulacion.setMaxWidth(100);
        fechaformulacion.setText("Fecha Formulación");
        fechaformulacion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclFormulacionesMedicas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclFormulacionesMedicas, String> hclformulacionesmedicas) {

                return new SimpleStringProperty(DateFormat.getDateInstance().format(hclformulacionesmedicas.getValue().getFechaformulacion()));

            }
        });
        ordenterapeutica.setMinWidth(300);
        ordenterapeutica.setText("Órden médica");
        ordenterapeutica.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclFormulacionesMedicas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclFormulacionesMedicas, String> hclformulacionesmedicas) {

                return new SimpleStringProperty((hclformulacionesmedicas.getValue().getOrdenmedica()));

            }
        });
        ordenmedica.setMinWidth(300);
        ordenmedica.setText("Órden terapeutica");
        ordenmedica.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclFormulacionesMedicas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclFormulacionesMedicas, String> hclformulacionesmedicas) {

                return new SimpleStringProperty((hclformulacionesmedicas.getValue().getOrdenterapeutica()));

            }
        });
        TableColumn titulotabla = new TableColumn<>("Formulaciones médicas");
        titulotabla.getColumns().addAll(fechaformulacion, ordenmedica, ordenterapeutica);
        ta_hclformulacionesmedicas.getColumns().addAll(titulotabla);
        ta_hclformulacionesmedicas.setMaxWidth(800);
        ta_hclformulacionesmedicas.setMaxHeight(150);
        ta_hclformulacionesmedicas.setOnMouseClicked(e -> {
            try {

            } catch (Exception ex) {
            }
        });

        //formulacion medicamentos
        codigofmed.setMinWidth(100);
        codigofmed.setText("Código");
        codigofmed.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclFormulacionMedicamentos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclFormulacionMedicamentos, String> hclformulacionmedicamentos) {

                return new SimpleStringProperty(hclformulacionmedicamentos.getValue().getCodigomedicamento());

            }
        });

        descripcionfmed.setMinWidth(700);
        descripcionfmed.setText("Descripción");
        descripcionfmed.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclFormulacionMedicamentos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclFormulacionMedicamentos, String> hclformulacionmedicamentos) {

                return new SimpleStringProperty("Uso: " + hclformulacionmedicamentos.getValue().getMedViasAdministracion().getDescripcion() + " " + hclformulacionmedicamentos.getValue().getMedicamento() + " N° " + hclformulacionmedicamentos.getValue().getCantidad() + " " + hclformulacionmedicamentos.getValue().getDosis() + " " + hclformulacionmedicamentos.getValue().getMedUnidadDosis().getDescripcion() + " cada " + hclformulacionmedicamentos.getValue().getFrecuencia() + " " + hclformulacionmedicamentos.getValue().getMedUnidadFrecuencia().getDescripcion() + " durante " + hclformulacionmedicamentos.getValue().getTiempotratamiento() + " " + hclformulacionmedicamentos.getValue().getMedUnidadTratamiento().getDescripcion());

            }
        });

        TableColumn tituloformulacionmedicamentos = new TableColumn<>("Formulación medicamentos");
        tituloformulacionmedicamentos.getColumns().addAll(descripcionfmed);
        ta_hclformulacionmedicamentos.getColumns().addAll(tituloformulacionmedicamentos);
        ta_hclformulacionmedicamentos.setMaxWidth(700);
        ta_hclformulacionmedicamentos.setMaxHeight(150);
        ta_hclformulacionmedicamentos.setOnMouseClicked(e -> {
            try {

            } catch (Exception ex) {
            }
        });

        codigofpro.setMinWidth(100);
        codigofpro.setMaxWidth(100);
        codigofpro.setText("Código");
        codigofpro.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclFormulacionProcedimientos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclFormulacionProcedimientos, String> hclFormulacionProcedimientos) {

                return new SimpleStringProperty((hclFormulacionProcedimientos.getValue().getHclCups().getCodigo()));

            }
        });
        descripcionfpro.setMinWidth(600);
        descripcionfpro.setText("Descripción");
        descripcionfpro.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclFormulacionProcedimientos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclFormulacionProcedimientos, String> hclFormulacionProcedimientos) {

                return new SimpleStringProperty((hclFormulacionProcedimientos.getValue().getHclCups().getDescripcion()));

            }
        });

        TableColumn tituloformulacionesprocedimientos = new TableColumn<>("Formulación de procedimientos");
        tituloformulacionesprocedimientos.getColumns().addAll(codigofpro, descripcionfpro);
        ta_formulacionprocedimientos.getColumns().addAll(tituloformulacionesprocedimientos);
        ta_formulacionprocedimientos.setMaxWidth(700);
        ta_formulacionprocedimientos.setMaxHeight(150);
        ta_formulacionprocedimientos.setOnMouseClicked(e -> {
            try {

            } catch (Exception ex) {
            }
        });

    }

    private static void getFormulasMedicas() throws ParseException {
        //colocamos los datos

        l_formulasmedicas = hclHistoriasClinicasControllerClient.getHclFormulacionesMedicas(SihicApp.hclconsultas);

        ol_hclformulasmedicas.clear();

        ol_hclformulasmedicas.addAll(l_formulasmedicas.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_hclformulacionesmedicas.setItems(ol_hclformulasmedicas);

    }

    private static void getFormulacionMedicamentos() throws ParseException {
        //colocamos los datos

        l_hclformulacionmedicamentos = hclHistoriasClinicasControllerClient.getHclFormulacionesMedicamentos(SihicApp.hclFormulacionesMedicas);
        ol_hclformulacionmedicamentos.clear();
        ol_hclformulacionmedicamentos.addAll(l_hclformulacionmedicamentos.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_hclformulacionmedicamentos.setItems(ol_hclformulacionmedicamentos);

    }

    private static void getFormulacionProcedimientos() throws ParseException {
        //colocamos los datos

        l_hclformulacionprocedimientos = hclHistoriasClinicasControllerClient.getHclFormulacionProcedimientos(SihicApp.hclFormulacionesMedicas);
        ol_hclformulacionprocedimientos.clear();
        ol_hclformulacionprocedimientos.addAll(l_hclformulacionprocedimientos.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_formulacionprocedimientos.setItems(ol_hclformulacionprocedimientos);

    }

    public static void refreshTable() throws ParseException {
        switch (opc) {
            case 1:
                getFormulasMedicas();
                break;

            case 2:
                getFormulacionMedicamentos();
                break;

            case 3:
                getFormulacionProcedimientos();
                break;

        }
    }
}
