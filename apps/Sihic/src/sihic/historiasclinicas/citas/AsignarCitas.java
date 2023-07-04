package sihic.historiasclinicas.citas;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import jxl.Workbook;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import modelo.AgnCitas;
import sihic.SihicApp;
import sihic.controlador.AgnCitasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

/**
 *
 * @author SIMboxDEV8
 */
public class AsignarCitas extends Application {

    private static TableView Ta_AgnCitas;
    private static ObservableList Ol_AgnCitas = FXCollections.observableArrayList();
    private GridPane gridpaneDatos;
    private GridPane gridpaneRoot;
    private GridPane gridpaneFechas;
    private GridPane gridpane;
    private static DatePicker Dp_Date_current;
    private List<AgnCitas> l_agncitas;
    private AgnCitas agnCitas;
    private TextField Tf_Buscar;
    private Label L_Date_current;
    private Label L_Date_to;
    private Button B_Eliminar;
     private Button bu_nuevo;
    private Label success;
    private Button B_Pdf;
    private AgnCitasControllerClient agnCitasControllerClient;
    private String appPathBuscarPersonas;
    private Class clzBuscarPersonas;
    private Object appClassBuscarPersonas;
    private static Stage stageBuscarPersonas = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    private Parent P_BuscarPersonas;
    private ContextMenu contextMenu;
    private MenuItem itemagregar;
    private MenuItem itemcancelar;
    private Label la_medico;
    private static ChoiceBox cb_estadoscitas;
    private Button cellButtonExcel;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private TitledPane tp_generic;

    public Parent createContent() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        tp_generic = new TitledPane();
        tp_generic.setText("Aisgnación de Citas");
        tp_generic.setCollapsible(false);

        alert.initModality(Modality.APPLICATION_MODAL);
        LoadChoiceBoxGeneral.getCb_agnmedicos().getSelectionModel().select(0);
        LoadChoiceBoxGeneral.getCb_agnmedicos().getSelectionModel().selectedIndexProperty().addListener(this::indexChangedMedicos);
        cb_estadoscitas = new ChoiceBox<Object>();
        cb_estadoscitas.getItems().addAll("No Agendados", "Agendados", "Todos");
        cb_estadoscitas.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        la_medico = new Label("Médico:");
        stageBuscarPersonas.setTitle("Agendar Citas");
        appPathBuscarPersonas = "sihic.historiasclinicas.citas.Agendar";
        clzBuscarPersonas = Class.forName(appPathBuscarPersonas);
        appClassBuscarPersonas = clzBuscarPersonas.newInstance();

        agnCitasControllerClient = new AgnCitasControllerClient();
        agnCitas = new AgnCitas();
        success = new Label();
        success.getStyleClass().add("message");
        Tf_Buscar = new TextField();
        Dp_Date_current = new DatePicker();
        L_Date_current = new Label("Fecha de Citas: ");

        ImageView imageView = null;
        imageView = new ImageView("/images/pdf.jpg");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        B_Pdf = new Button("Imprimir Pdf", imageView);
        B_Pdf.setMaxWidth(130);

        Dp_Date_current.setValue(LocalDate.now());

        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        itemagregar = new MenuItem("Agregar", imageView);
        itemagregar.setOnAction(e -> {
            try {
                if (checkregistroexistente()) {
                    if (SihicApp.agnCitasTemp.getAgnEstadosCita().getId().equals(Long.valueOf(5))) {
                        showgenpersonas();
                    } else {
                        alert.getDialogPane().setContentText("No se Pueden Agregar Citas Cuando ya ha sido Asignadas a Consultas");
                        alert.showAndWait();
                    }
                }
                else
                {
                    SihicApp.agnCitasTemp=null;
                    showgenpersonas();
                }
            } catch (Exception x) {
                try {
                    showgenpersonas();
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(AsignarCitas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(AsignarCitas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(AsignarCitas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(AsignarCitas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        imageView = null;
        imageView = new ImageView("/images/delete.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        itemcancelar = new MenuItem("Editar", imageView);
        itemcancelar.setOnAction(e -> {

            try {
                if (checkregistroexistente()) {
                    showgenpersonas();
                }
            } catch (Exception x) {

            }
        });
        cellButtonExcel = new Button();
        imageView = null;
        imageView = new ImageView("/images/excel.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        cellButtonExcel.setGraphic(imageView);
        cellButtonExcel.setCursor(Cursor.HAND);
        cellButtonExcel.setTooltip(new Tooltip("Exportar Citas a Excel"));
        cellButtonExcel.setOnAction(e -> {

            try {
                exportar_excel();
            } catch (IOException ex) {
                Logger.getLogger(AsignarCitas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WriteException ex) {
                Logger.getLogger(AsignarCitas.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
         bu_nuevo = new Button();
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_nuevo.setGraphic(imageView);
        bu_nuevo.setCursor(Cursor.HAND);
        bu_nuevo.setTooltip(new Tooltip("Agendar Cita"));
        bu_nuevo.setOnAction(e -> {

            try {
                SihicApp.agnCitasTemp=null;
                showgenpersonas();
            } catch (Exception ex) {
               ex.printStackTrace();
            } 

        });
        contextMenu = new ContextMenu(itemagregar, itemcancelar);
        Dp_Date_current.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                try {
                    setHorasCitas();
                } catch (ParseException ex) {
                    Logger.getLogger(AsignarCitas.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        B_Pdf.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                try {
                    printFactura(1);
                } catch (IOException ex) {

                }

            }
        });

        Tf_Buscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                try {
                    getAgnCitas();
                } catch (ParseException ex) {
                    Logger.getLogger(AgnCitas.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Ta_AgnCitas = new TableView();
        Ta_AgnCitas.setContextMenu(contextMenu);
        TableColumn Fecha = new TableColumn();
        Fecha.setMinWidth(100);
        Fecha.setText("Fecha Cita");
        Fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agncitas) {

                return new SimpleStringProperty(DateFormat.getDateInstance().format(agncitas.getValue().getFechaHora()));

            }
        });

        TableColumn Hora = new TableColumn();
        Hora.setMinWidth(70);
        Hora.setText("Hora");
        Hora.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agncitas) {

                return new SimpleStringProperty(String.valueOf(agncitas.getValue().getGenHorasCita().getHora() + ":" + agncitas.getValue().getGenHorasCita().getMinutos() + " " + (agncitas.getValue().getGenHorasCita().getZona() == 0 ? "AM" : "PM")));

            }
        });
        TableColumn Medico = new TableColumn();
        Medico.setMinWidth(400);
        Medico.setText("Médico");
        Medico.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agncitas) {

                return new SimpleStringProperty(agncitas.getValue().getAgnMedicos().getGenPersonas().getNombres());

            }
        });
        TableColumn Paciente = new TableColumn();
        Paciente.setMinWidth(400);
        Paciente.setText("Paciente");
        Paciente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agncitas) {

                return new SimpleStringProperty(agncitas.getValue().getGenPacientes() != null ? agncitas.getValue().getGenPacientes().getGenPersonas().getPrimerNombre() + " " + agncitas.getValue().getGenPacientes().getGenPersonas().getPrimerApellido() : "");

            }
        });

        TableColumn NoConsultorio = new TableColumn();
        NoConsultorio.setMinWidth(150);
        NoConsultorio.setText("Consultorio");
        NoConsultorio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agncitas) {

                return new SimpleStringProperty(agncitas.getValue().getAgnConsultorios() != null ? String.valueOf(agncitas.getValue().getAgnConsultorios().getNumero()) + ". " + agncitas.getValue().getAgnConsultorios().getDescripcion() : "");

            }
        });
        TableColumn Servicio = new TableColumn();
        Servicio.setMinWidth(150);
        Servicio.setText("Servicio");
        Servicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agncitas) {

                return new SimpleStringProperty(agncitas.getValue().getServicio() != null ? agncitas.getValue().getServicio().getNombre() : "");

            }
        });
        TableColumn Celular = new TableColumn();
        Celular.setMinWidth(70);
        Celular.setText("Telefono");
        Celular.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AgnCitas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<AgnCitas, String> agncitas) {

                return new SimpleStringProperty(agncitas.getValue().getGenPacientes() != null ? agncitas.getValue().getGenPacientes().getGenPersonas().getTelefono() : "");

            }
        });

        //eventos table
        Ta_AgnCitas.setMinHeight(500);
        // Ta_AgnCitas.setMinWidth(900);
        Ta_AgnCitas.setEditable(true);
        Ta_AgnCitas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Ta_AgnCitas.getColumns().addAll(Fecha, Hora, Medico, Paciente, NoConsultorio, Servicio, Celular);
        //Evento

        stageBuscarPersonas.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    try {
                        getCitas2();
                    } catch (ParseException ex) {
                        Logger.getLogger(AsignarCitas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        gridpaneDatos = new GridPane();
        gridpaneRoot = new GridPane();
        gridpaneFechas = new GridPane();
        gridpane = new GridPane();
        gridpane.getStyleClass().add("category-page");
        gridpaneFechas.add(L_Date_current, 0, 0);
        gridpaneFechas.add(Dp_Date_current, 1, 0);
        gridpaneFechas.add(la_medico, 2, 0);
        gridpaneFechas.add(LoadChoiceBoxGeneral.getCb_agnmedicos(), 3, 0);
      //  gridpaneFechas.add(new Label("Estados Cita:"), 4, 0);
       // gridpaneFechas.add(cb_estadoscitas, 5, 0);
        gridpaneFechas.add(cellButtonExcel, 6, 0);
        gridpaneFechas.add(bu_nuevo, 7, 0);
        gridpaneFechas.setMinWidth(430);
        gridpaneFechas.setHgap(5);
        gridpane.setVgap(5);
        gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
        GridPane.setValignment(gridpaneRoot, VPos.TOP);
        GridPane.setHalignment(gridpane, HPos.CENTER);

        gridpane.add(gridpaneFechas, 0, 0);
        gridpane.add(Ta_AgnCitas, 0, 1);

        GridPane.setColumnSpan(Ta_AgnCitas, 5);
        GridPane.setColumnSpan(gridpaneFechas, 5);
        GridPane.setHalignment(Ta_AgnCitas, HPos.CENTER);
        GridPane.setHalignment(gridpaneFechas, HPos.CENTER);
        GridPane.setHalignment(gridpaneDatos, HPos.CENTER);
        gridpaneFechas.setAlignment(Pos.CENTER);
        gridpaneDatos.setAlignment(Pos.CENTER);
        gridpane.setAlignment(Pos.CENTER);
        // gridpaneRoot.add(gridpane, 0, 0);
        gridpane.getStylesheets().add(SihicApp.hojaestilos);
        gridpane.getStyleClass().add("background");
        gridpane.setMinWidth(1350);
        gridpaneRoot.setMinWidth(1350);
        cb_estadoscitas.getSelectionModel().select(0);
        setHorasCitas();

        tp_generic.setContent(gridpane);
        StackPane stack = new StackPane();
        stack.getChildren().add(tp_generic);
        stack.alignmentProperty().setValue(Pos.TOP_CENTER);

        return stack;
    }

    public static Node createIconContent() throws IOException {
        ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
        imageView.setFitHeight(80);
        imageView.setFitWidth(90);
        javafx.scene.Group g
                = new javafx.scene.Group(imageView);

        return g;
    }

    private static void getAgnCitas() throws ParseException {

        //Ol_AgnCitas.addAll(l_agncitas);
        Ta_AgnCitas.setItems(Ol_AgnCitas);

    }
    Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            //insertamos las citas|
            //iniciamos los medicos }
            setHorasCitas();
            return null;
        }
    };

    public static void setHorasCitas() throws ParseException {
        System.out.println("size medicos->" + SihicApp.l_medicos.size());
        System.out.println("size horas cita->" + SihicApp.getGenhorascita().size());
        Ol_AgnCitas.clear();
        Ol_AgnCitas.addAll(SihicApp.agnCitasControllerClient.setHorasCitaMedicos(SihicApp.l_medicos, SihicApp.getGenhorascita(), Dp_Date_current.getValue().toString(), LoadChoiceBoxGeneral.getV_agnmedicos().get(LoadChoiceBoxGeneral.getCb_agnmedicos().getSelectionModel().getSelectedIndex()), cb_estadoscitas.getSelectionModel().getSelectedIndex()).toArray());
        getAgnCitas();
    }

    public void deleteCita() throws ParseException {
        try {
            if (SihicApp.agnCitasTemp.getAgnEstadosCita().getId().equals(Long.valueOf(1))) {
                SihicApp.agnCitasTemp = (AgnCitas) Ta_AgnCitas.getSelectionModel().getSelectedItem();
                SihicApp.agnCitasTemp.setGenPacientes(null);
                SihicApp.agnCitasTemp.setServicio(null);
                DCitas.cambiarestadocitayfechahora(5);
                getCitas2();
            } else {
                alert.getDialogPane().setContentText("No se Pueden Cancelar Citas Cuando ya ha sido Asignadas a Consultas");
                alert.showAndWait();
            }
        } catch (Exception e) {
            alert.getDialogPane().setContentText("Cita No se Puede Cancelar porque no ha sido asignada");
            alert.showAndWait();

        }
    }

    private void animateMessage() {
        success.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);

        ft.play();

        //success.setOpacity(0); 
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                gridpaneFechas.getChildren().remove(success);
                success.setText("");

            }
        });

    }

    public void printFactura(int opc) throws IOException {
        /* if(Ta_AgnCitas.getSelectionModel().getSelectedItem()!=null)
     {
         
     ImpresionFactura impresionFactura=null;
     impresionFactura=new ImpresionFactura((Factura)Ta_AgnCitas.getSelectionModel().getSelectedItem());
     impresionFactura.fprintpdf(opc);
     }*/

    }

    public void ModifyCita() {

    }

    private void showgenpersonas() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        P_BuscarPersonas = null;
        P_BuscarPersonas = (Parent) clzBuscarPersonas.getMethod("createContent").invoke(appClassBuscarPersonas);
        scene = null;

        scene = new Scene(P_BuscarPersonas, Color.TRANSPARENT);

        if (stageBuscarPersonas.isShowing()) {
            stageBuscarPersonas.close();
        }

//set scene to stage
        stageBuscarPersonas.sizeToScene();
        stageBuscarPersonas.setTitle("Agendar Citas Pacientes");

        //center stage on screen
        stageBuscarPersonas.centerOnScreen();
        stageBuscarPersonas.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageBuscarPersonas.show();
    }

    public static void closeStage() {
        stageBuscarPersonas.close();
    }

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((AgnCitas) Ta_AgnCitas.getSelectionModel().getSelectedItem()) != null) {
            SihicApp.agnCitasTemp = (AgnCitas) Ta_AgnCitas.getSelectionModel().getSelectedItem();

            return true;
        } else {
            return false;
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    //eventos
    public void indexChangedMedicos(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {
            setHorasCitas();
        } catch (ParseException ex) {
            Logger.getLogger(AsignarCitas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {
            setHorasCitas();
        } catch (ParseException ex) {
            Logger.getLogger(AsignarCitas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportar_excel() throws IOException, WriteException {
        String rutaArchivo = "/home/adminlinux/sihic/citas.xls";
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
            archivoXLS.createNewFile();
        }
        WritableWorkbook libro;
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        libro = Workbook.createWorkbook(archivo);
        WritableSheet hoja = libro.createSheet("Factura", 0);
        int f = 0;

        for (AgnCitas ac : l_agncitas) {

            if (f == 0) {
                hoja.insertRow(f);

                // CellType.LABEL;
                WritableCellFeatures writableCellFeatures = new WritableCellFeatures();
                writableCellFeatures.removeDataValidation();
                label = null;

                label = new jxl.write.Label(f, f, "Fecha Cita");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(1, f, "Hora de Cita");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(2, f, "Médico");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(3, f, "N° Ident");
                hoja.addCell(label);
                label = null;

                label = null;
                label = new jxl.write.Label(4, f, "Paciente");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(5, f, "Consultorio");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(6, f, "Procedimiento");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(7, f, "Teléfono");
                hoja.addCell(label);
            }
            f++;
            hoja.insertRow(f);

            label = null;
            label = new jxl.write.Label(0, f, sihic.util.UtilDate.formatodiamesyear(ac.getFechaHora()));
            hoja.addCell(label);
            label = null;
            label = new jxl.write.Label(1, f, ac.getGenHorasCita().getHora() + ":" + ac.getGenHorasCita().getMinutos() + " " + (ac.getGenHorasCita().getZona() == 0 ? "AM" : "PM"));
            hoja.addCell(label);
            label = null;
            label = new jxl.write.Label(2, f, ac.getAgnMedicos().getGenPersonas().getNombres());
            hoja.addCell(label);
            label = null;
            if (ac.getGenPacientes() != null) {
                label = new jxl.write.Label(3, f, ac.getGenPacientes().getGenPersonas().getDocumento());
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(4, f, ac.getGenPacientes().getGenPersonas().getNombres());
                hoja.addCell(label);
                label = null;

                label = new jxl.write.Label(5, f, ac.getAgnConsultorios() != null ? ac.getAgnConsultorios().getNumero() + " " + ac.getAgnConsultorios().getDescripcion() : "No Asignado");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(6, f, ac.getServicio().getNombre());
                hoja.addCell(label);
                label = null;

                label = new jxl.write.Label(7, f, ac.getGenPacientes().getGenPersonas().getTelefono());

                hoja.addCell(label);
            }
        }

        libro.write();
        libro.close();
        archivo.close();
        Desktop.getDesktop().open(archivoXLS);
    }

    public void getCitas2() throws ParseException {
        l_agncitas = agnCitasControllerClient.getAgnCitas(Dp_Date_current.getValue().toString(), LoadChoiceBoxGeneral.getV_agnmedicos().get(LoadChoiceBoxGeneral.getCb_agnmedicos().getSelectionModel().getSelectedIndex()), cb_estadoscitas.getSelectionModel().getSelectedIndex());
        Ol_AgnCitas.clear();
        Ol_AgnCitas.addAll(l_agncitas.toArray());
        Ta_AgnCitas.setItems(Ol_AgnCitas);

    }
}
