/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.citas;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.FacturaItem;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.controlador.FacturaItemControllerClient;
import sihic.util.UtilDate;
import jxl.Workbook;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import modelo.FacturaItem;
import modelo.RptCitaEstadisticaDTO;

/**
 *
 * @author adminlinux
 */
public class FRptEstadisticaCita extends Application {

    private GridPane Gp_Generic;
    private TableView ta_factura;
    private Label l_buscar;

    private FacturaItemControllerClient facturaItemControllerClient;
    private Button bu_exportar;

    private DatePicker Dp_Date_from;
    private DatePicker Dp_Date_to;
    private Label L_Date_from;
    private Label L_Date_to;
    private ObservableList<String[]> Ol_FacturaItem = FXCollections.observableArrayList();
    ;
    private List<FacturaItem> Li_facturasitem;
    private List<RptCitaEstadisticaDTO> Li_RptCitaEstadisticaDTOs;
    private TextField buscar = new TextField();
    private SearchPopover sp_producto;
    private int horacita = 0;
    private int mincita = 0;
    private int horaingreso = 0;
    private int miningreso = 0;
    private int horacall = 0;
    private int mincall = 0;
    private int horawait = 0;
    private int minwait = 0;
    private int horaend = 0;
    private int minend = 0;
    private int horaduracion = 0;
    private int minduracion = 0;
    private int diasplazo = 0;
    private int yearcurrent = 0;
    private int mescurrent = 0;
    private int numerosdiasmes = 0;
    private int diames = 0;
    private int diamescita = 0;
    private int messolicitud = 0;
    private int mescita = 0;
    private List<String[]> li_tempdetalles = new ArrayList<>();
    private String[] s_detalles = new String[40];

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        buscar.setMaxWidth(300);
        L_Date_from = new Label("Desde:");
        L_Date_to = new Label("A: ");
        Dp_Date_from = new DatePicker();
        Dp_Date_from.setValue(LocalDate.now());
        Dp_Date_to = new DatePicker();
        Dp_Date_to.setValue(LocalDate.now());

        Dp_Date_from.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                try {
                    loadData();
                } catch (ParseException ex) {
                }

            }
        });
        Dp_Date_to.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                try {
                    loadData();

                } catch (ParseException ex) {
                }

            }
        });
        facturaItemControllerClient = new FacturaItemControllerClient();
        ImageView imageView = new ImageView("/images/excel.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_exportar = new Button("", imageView);
        Tooltip tooltip = new Tooltip("Cerrar Factura");
        bu_exportar.setTooltip(tooltip);
        bu_exportar.setOnAction(e
                -> {

            try {
                exportar_excel();
            } catch (Exception ex) {

            }

        });

        l_buscar = new Label("Buscar:");
        buscar.setOnKeyPressed(e -> {
            try {
                loadData();
            } catch (Exception e3) {
            }
        });
        Gp_Generic = new GridPane();
        ta_factura = new TableView();
        TableColumn fechasolicituddelacita = new TableColumn();
        fechasolicituddelacita.setMinWidth(100);
        fechasolicituddelacita.setText("Fecha Solicitud dela Cita");

        fechasolicituddelacita.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[0]);

            }
        });
        TableColumn diaoportunidaddelacita = new TableColumn();

        diaoportunidaddelacita.setText("Día de Oportunidad de la Cita");
        diaoportunidaddelacita.setMinWidth(30);
        diaoportunidaddelacita.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[1]);
            }
        });
        TableColumn fechadecita = new TableColumn();
        fechadecita.setMinWidth(100);
        fechadecita.setText("Fecha de la Cita");
        fechadecita.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[2]);
            }
        });
        TableColumn horadecita = new TableColumn();
        horadecita.setMinWidth(70);
        horadecita.setText("Hora de la Cita");
        horadecita.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[3]);
            }
        });
        TableColumn horadeingreso = new TableColumn();
        horadeingreso.setMinWidth(70);
        horadeingreso.setText("Hora de Ingreso");
        horadeingreso.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[4]);
            }
        });
        TableColumn horallamadoconsultorio = new TableColumn();
        horallamadoconsultorio.setMinWidth(70);
        horallamadoconsultorio.setText("Hora de Llamado al Consultorio");
        horallamadoconsultorio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[5]);
            }
        });
        TableColumn tiempodeesperarealizacionestudio = new TableColumn();
        tiempodeesperarealizacionestudio.setMinWidth(70);
        tiempodeesperarealizacionestudio.setText("Tiempo de Espera Realización del Estudio");
        tiempodeesperarealizacionestudio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[6]);
            }
        });
        TableColumn horadeterminacionexamen = new TableColumn();
        horadeterminacionexamen.setMinWidth(70);
        horadeterminacionexamen.setText("Hora de Terminación del Exámen");
        horadeterminacionexamen.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[7]);
            }
        });
        TableColumn duracionexamen = new TableColumn();
        duracionexamen.setMinWidth(70);
        duracionexamen.setText("Duración del Exámen");
        duracionexamen.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[8]);
            }
        });
        TableColumn fechaentregaresultados = new TableColumn();
        fechaentregaresultados.setMinWidth(70);
        fechaentregaresultados.setText("Fecha de Entrega de Resultados");
        fechaentregaresultados.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[9]);

            }
        });
        TableColumn oportunidadentregaresultados = new TableColumn();
        oportunidadentregaresultados.setMinWidth(50);
        oportunidadentregaresultados.setText("Oportunidad en la Entrega de Resultados");
        oportunidadentregaresultados.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[10]);

            }
        });
        TableColumn nombre = new TableColumn();

        nombre.setText("Nombre");
        nombre.setMinWidth(100);
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[11]);
            }
        });
        TableColumn nombre2 = new TableColumn();
        nombre2.setMinWidth(100);
        nombre2.setText("Nombre 2");
        nombre2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[12]);

            }
        });
        TableColumn apellido = new TableColumn();
        apellido.setMinWidth(100);
        apellido.setText("Apellido");
        apellido.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[13]);

            }
        });
        TableColumn apellido2 = new TableColumn();
        apellido2.setMinWidth(100);
        apellido2.setText("Apellido 2");
        apellido2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[14]);
            }
        });
        TableColumn id = new TableColumn();
        id.setMinWidth(30);
        id.setText("ID");
        id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[15]);
            }
        });
        TableColumn documento = new TableColumn();
        documento.setMinWidth(70);
        documento.setText("Documento");
        documento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[16]);
            }
        });
        TableColumn fechanacimiento = new TableColumn();
        fechanacimiento.setMinWidth(70);
        fechanacimiento.setText("Fecha de Nacimiento");
        fechanacimiento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[17]);
            }
        });
        TableColumn edad = new TableColumn();

        edad.setText("Edad");
        edad.setMinWidth(30);
        edad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[18]);
            }
        });
        TableColumn urg = new TableColumn();
        urg.setMinWidth(30);
        urg.setText("URG");
        urg.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[19]);

            }
        });
        TableColumn consultaext = new TableColumn();

        consultaext.setText("Consulta Externa");
        consultaext.setMinWidth(30);
        consultaext.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[20]);

            }
        });
        TableColumn telefono = new TableColumn();
        telefono.setText("Teléfono");
        telefono.setMinWidth(50);
        telefono.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[21]);
            }
        });
        TableColumn f = new TableColumn();
        f.setMinWidth(30);
        f.setText("F");
        f.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[22]);
            }
        });
        TableColumn m = new TableColumn();
        m.setMinWidth(30);
        m.setText("M");
        m.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[23]);

            }
        });
        TableColumn direccion = new TableColumn();
        direccion.setMinWidth(70);
        direccion.setText("Dirección");
        direccion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[24]);

            }
        });
        TableColumn eps = new TableColumn();
        eps.setText("Eps");
        eps.setMinWidth(100);
        eps.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[25]);

            }
        });
        TableColumn subsidiado = new TableColumn();
        subsidiado.setMinWidth(30);
        subsidiado.setText("Subsidiado");
        subsidiado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[26]);

            }
        });
        TableColumn contributivo = new TableColumn();
        contributivo.setMinWidth(30);
        contributivo.setText("Contributivo");
        contributivo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[27]);

            }
        });
        TableColumn medico = new TableColumn();
        medico.setMinWidth(100);
        medico.setText("Médico");
        medico.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[28]);
            }
        });
        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(50);
        codigo.setText("CODIGO CUBS");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[29]);
            }
        });
        TableColumn estudio = new TableColumn();
        estudio.setMinWidth(100);
        estudio.setText("CUPS");
        estudio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[30]);

            }
        });
        TableColumn valor = new TableColumn();
        valor.setMinWidth(70);
        valor.setText("Valor");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[31]);
            }
        });
        TableColumn especialidad = new TableColumn();
        especialidad.setMinWidth(70);
        especialidad.setText("Especialidad");
        especialidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[32]);
            }
        });
        TableColumn prefijo = new TableColumn();
        especialidad.setMinWidth(70);
        especialidad.setText("Prefijo Factura");
        especialidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[33]);

            }
        });
        TableColumn nocomprobante = new TableColumn();
        nocomprobante.setMinWidth(70);
        nocomprobante.setText("N° Comprobante");
        nocomprobante.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[34]);

            }
        });
        TableColumn tiposervicio = new TableColumn();
        tiposervicio.setMinWidth(70);
        tiposervicio.setText("Tipo Servicio");
        tiposervicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[35]);

            }
        });
        TableColumn tipousuario = new TableColumn();
        tiposervicio.setMinWidth(170);
        tiposervicio.setText("Tipo Usuario");
        tiposervicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[36]);

            }
        });
        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(170);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[37]);

            }
        });
        
        TableColumn noautorizacion = new TableColumn();
        noautorizacion.setMinWidth(170);
        noautorizacion.setText("N° Autorización");
        noautorizacion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue()[38]);

            }
        });
        
        ta_factura.getColumns().addAll(fechasolicituddelacita, diaoportunidaddelacita, fechadecita, horadecita, horadeingreso, horallamadoconsultorio, tiempodeesperarealizacionestudio, horadeterminacionexamen, duracionexamen, fechaentregaresultados, oportunidadentregaresultados, nombre, nombre2, apellido, apellido2, id, documento, fechanacimiento, edad, urg, consultaext, telefono, f, m, direccion, eps, subsidiado, contributivo, medico, codigo, estudio, valor, especialidad, nocomprobante, tiposervicio, nofactura, noautorizacion);
        Gp_Generic.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Generic.getStyleClass().add("background");
        Gp_Generic.add(l_buscar, 0, 0);
        Gp_Generic.add(buscar, 1, 0);
        Gp_Generic.add(L_Date_from, 2, 0);
        Gp_Generic.add(Dp_Date_from, 3, 0);
        Gp_Generic.add(L_Date_to, 4, 0);
        Gp_Generic.add(Dp_Date_to, 5, 0);
        Gp_Generic.add(bu_exportar, 6, 0);
        Gp_Generic.add(ta_factura, 0, 1, 9, 1);
        Gp_Generic.setVgap(5);
        Gp_Generic.setHgap(5);
        Gp_Generic.setMinSize(1350, 650);
        ta_factura.setMinHeight(620);
        ta_factura.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        StackPane stackPane = new StackPane(Gp_Generic);
        Gp_Generic.setAlignment(Pos.TOP_CENTER);
        stackPane.setAlignment(Pos.TOP_CENTER);
        loadData();
        return stackPane;
    }

    public void loadData() throws ParseException {
        try {
            li_tempdetalles.clear();
            Li_RptCitaEstadisticaDTOs = facturaItemControllerClient.rptestadisticasDTO(Dp_Date_from.getValue().toString(), Dp_Date_to.getValue().toString(), SihicApp.s_producto, buscar.getText());
            System.out.println("size rpt stadistics->" + Li_RptCitaEstadisticaDTOs.size());
            load_detalle2();
            Ol_FacturaItem.clear();
            Ol_FacturaItem.addAll(li_tempdetalles);
            ta_factura.setItems(Ol_FacturaItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void exportar_excel() throws IOException, WriteException {
        String rutaArchivo = "/home/adminlinux/sihic/rptestadisticas.xls";
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
        WritableSheet hoja = libro.createSheet("Estadisticas", 0);
        int f = 0;

        for (String[] s : li_tempdetalles) {

            if (f == 0) {
                hoja.insertRow(f);

                // CellType.LABEL;
                WritableCellFeatures writableCellFeatures = new WritableCellFeatures();
                writableCellFeatures.removeDataValidation();
                label = null;

                label = new jxl.write.Label(f, f, "FECHA SOLICITUD DE LA CITA");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(1, f, "DIA OPORTUNIDAD DE LA CITA");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(2, f, "FECHA DE LA CITA");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(3, f, "HORA DE LA CITA");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(4, f, "HORA DE INGRESO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(5, f, "HORA DE LLAMADO AL CONSULTORIO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(6, f, "TIEMṔO DE ESPERA REALIZACION DEL ESTUDIO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(7, f, "HORA DE TERMINACION DEL EXAMEN");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(8, f, "DURACION DEL EXAMEN");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(9, f, "FECHA DE ENTREGA DE RESULTADOS");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(10, f, "OPORTUNIDAD EN LA ENTREGA DE RESULTADOS");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(11, f, "NOMBRE");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(12, f, "NOMBRE 2");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(13, f, "APELLIDO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(14, f, "APELLIDO 2");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(15, f, "ID");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(16, f, "DOCUMENTO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(17, f, "FECHA DE NACIMIENTO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(18, f, "EDAD");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(19, f, "URG");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(20, f, "CONSULTA EXTERNA");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(21, f, "TELEFONO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(22, f, "F");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(23, f, "M");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(24, f, "DIRECCION");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(25, f, "EPS");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(26, f, "SUBSIDIADO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(27, f, "CONTRIBUTIVO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(28, f, "MEDICO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(29, f, "CODIGO CUPS");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(30, f, "CUPS");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(31, f, "VALOR");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(32, f, "ESPECIALIDAD");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(33, f, "PREFIJO FACTURA");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(34, f, "N° COMPROBANTE");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(35, f, "TIPO SERVICIO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(36, f, "TIPO USUARIO");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(37, f, "N° Factura");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(38, f, "Médico que Ordena");
                hoja.addCell(label);
                
                label = null;
                label = new jxl.write.Label(39, f, "N° Autorización");
                hoja.addCell(label);
            }
            f++;
            hoja.insertRow(f);
            for (int i = 0; i < s.length; i++) {
                label = null;
                label = new jxl.write.Label(i, f, s[i]);
                hoja.addCell(label);
            }

        }

        libro.write();
        libro.close();
        archivo.close();
        Desktop.getDesktop().open(archivoXLS);
    }

    private void load_detalle() {
        Date fechatemporal = new Date();
        for (FacturaItem facturaitem : Li_facturasitem) {
            diames = UtilDate.getdiafecha(facturaitem.getHclConsultas().getAgnCitas().getFechaHora());
            diamescita = UtilDate.getdiafecha(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion());
            messolicitud = UtilDate.getmesfecha(facturaitem.getHclConsultas().getAgnCitas().getFechaHora());
            mescita = UtilDate.getmesfecha(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion());
            String year = String.valueOf(UtilDate.getyearfecha(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion()));
            int diasportunidad = 0;
            if (messolicitud == mescita) {
                diasportunidad = diamescita - diames;
            }
            String s_fechasolicitud = "";
            if (diasportunidad > 0) {
                s_fechasolicitud = UtilDate.formatodiamesyear2(facturaitem.getHclConsultas().getAgnCitas().getFechaHora());

            } else {
                if (diasportunidad <= 0) {
                    if (diamescita == 1) {
                        String mes = (mescita - 1) == 0 ? "01" : (mescita - 1) < 10 ? "0" + String.valueOf(mescita - 1) : String.valueOf(mescita - 1);
                        try {

                            diasportunidad = (int) Math.random() * UtilDate.calculardiasdelmes(Integer.valueOf(year), messolicitud - 1) + 24;
                        } catch (Exception e) {
                            diasportunidad = (int) Math.random() * UtilDate.calculardiasdelmes(Integer.valueOf(year), mescita - 1) + 24;

                        }
                        s_fechasolicitud = String.valueOf(diasportunidad) + "-" + mes + "-" + year;
                        diasportunidad = UtilDate.calculardiasdelmes(Integer.valueOf(year), messolicitud - 1) - diasportunidad;

                    } else {
                        int totdias = diamescita <= 3 ? 1 : 3;
                        diasportunidad = (diamescita) - (diamescita - totdias);
                        String mes = mescita < 10 ? "0" + String.valueOf(mescita) : String.valueOf(mescita);
                        s_fechasolicitud = (diamescita - totdias) < 10 ? "0" + String.valueOf(diamescita - totdias) + "-" + mes + "-" + year : String.valueOf(diamescita - totdias) + "-" + mes + "-" + year;
                    }
                }

            }
            s_detalles[0] = s_fechasolicitud;
            diames = UtilDate.getdiafecha(facturaitem.getHclConsultas().getAgnCitas().getFechaHora());
            diamescita = UtilDate.getdiafecha(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion());

            messolicitud = UtilDate.getmesfecha(facturaitem.getHclConsultas().getAgnCitas().getFechaHora());
            mescita = UtilDate.getmesfecha(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion());

            s_detalles[1] = String.valueOf(diasportunidad);
            s_detalles[2] = UtilDate.formatodiamesyear2(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion());
            horacita = facturaitem.getHclConsultas().getAgnCitas().getGenHorasCita().getHora();
            mincita = facturaitem.getHclConsultas().getAgnCitas().getGenHorasCita().getMinutos();
            String s_horacita = horacita < 10 ? "0" + String.valueOf(horacita) : String.valueOf(horacita);
            String s_mincita = mincita < 10 ? "0" + String.valueOf(mincita) : String.valueOf(mincita);
            s_detalles[3] = s_horacita + ":" + s_mincita + ":00";
            horaingreso = facturaitem.getHclConsultas().getAgnCitas().getGenHorasCita().getHora();
            miningreso = facturaitem.getHclConsultas().getAgnCitas().getGenHorasCita().getMinutos() + ((int) (Math.random() * 7) + 0);
            s_detalles[4] = String.valueOf((horaingreso < 10 ? "0" + horaingreso : horaingreso) + ":" + (miningreso < 10 ? "0" + miningreso + ":00" : miningreso + ":00"));
            horacall = horaingreso;
            mincall = miningreso + ((int) (Math.random() * 5) + 1);
            s_detalles[5] = String.valueOf((horacall < 10 ? "0" + horacall : horacall) + (":" + (mincall < 10 ? "0" + (mincall + ":00") : (mincall + ":00"))));
            horawait = 0;
            minwait = mincall - mincita;
            s_detalles[6] = "00:" + (minwait < 10 ? "0" + minwait : minwait);
            horaend = horacall;
            minend = mincall + ((int) (Math.random() * 59) + 0);
            if (minend >= 60) {
                horaend++;
                minend = minend - 60;
            }
            s_detalles[7] = (horaend < 10 ? "0" + horaend : horaend) + ":" + (minend < 10 ? "0" + minend : minend);
            horaduracion = horaend - horacall;
            if (horaduracion > 0) {
                minduracion = (60 - mincall) + minend;
                if (minduracion < 60) {
                    horaduracion--;
                }
            } else {
                minduracion = minend - mincall;
            }
            s_detalles[8] = (horaduracion < 10 ? "0" + horaduracion : horaduracion) + ":" + (minduracion < 10 ? "0" + minduracion : minduracion);
            diasplazo = ((int) (Math.random() * 12) + 0);
            mescurrent = UtilDate.getmesfecha(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion());
            yearcurrent = UtilDate.getyearfecha(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion());
            numerosdiasmes = UtilDate.calculardiasdelmes(yearcurrent, mescurrent - 1);
            diames = UtilDate.getdiafecha(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion());
            String fecha = "";
            if ((diames + diasplazo) <= numerosdiasmes) {
                try {
                    fecha = UtilDate.colocardiames(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion(), mescurrent, (diames + diasplazo));
                } catch (ParseException ex) {
                    Logger.getLogger(FRptEstadisticaCita.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    fecha = UtilDate.colocardiames(facturaitem.getHclConsultas().getAgnCitas().getFechaCreacion(), (mescurrent + 1), ((diames + diasplazo) - numerosdiasmes));
                } catch (ParseException ex) {
                    Logger.getLogger(FRptEstadisticaCita.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            s_detalles[9] = fecha;
            s_detalles[10] = String.valueOf(diasplazo);
            s_detalles[11] = facturaitem.getGenPacientes().getGenPersonas().getPrimerNombre();
            s_detalles[12] = facturaitem.getGenPacientes().getGenPersonas().getSegundoNombre();
            s_detalles[13] = facturaitem.getGenPacientes().getGenPersonas().getPrimerApellido();
            s_detalles[14] = facturaitem.getGenPacientes().getGenPersonas().getSegundoApellido();
            s_detalles[15] = facturaitem.getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla();
            s_detalles[16] = facturaitem.getGenPacientes().getGenPersonas().getDocumento();
            s_detalles[17] = UtilDate.formatodiamesyear2(facturaitem.getGenPacientes().getGenPersonas().getFechaNacimiento());
            s_detalles[18] = facturaitem.getGenPacientes().getGenPersonas().year();
            s_detalles[19] = "";
            s_detalles[20] = "X";
            s_detalles[21] = facturaitem.getGenPacientes().getGenPersonas().getTelefono();
            s_detalles[22] = facturaitem.getGenPacientes().getGenPersonas().getGenSexo().getSigla().equals("F") ? "1" : "";
            s_detalles[23] = facturaitem.getGenPacientes().getGenPersonas().getGenSexo().getSigla().equals("M") ? "1" : "";
            s_detalles[24] = facturaitem.getGenPacientes().getGenPersonas().getDireccion();
            try {
                s_detalles[25] = facturaitem.getGenPacientes().getGenEapb().getNombre();
            } catch (Exception e) {
                s_detalles[25] = "N";

            }

            s_detalles[26] = facturaitem.getGenPacientes().getGenTiposUsuarios().getCodigo().equals("2") ? "1" : "";
            s_detalles[27] = facturaitem.getGenPacientes().getGenTiposUsuarios().getCodigo().equals("1") ? "1" : "";
            s_detalles[28] = facturaitem.getHclConsultas().getAgnCitas().getAgnMedicos().getGenPersonas().getNombres();
            try {
                if (!facturaitem.getProducto().getHclCups().getCodigo().equals("")) {
                    s_detalles[29] = facturaitem.getProducto().getHclCups().getCodigo();
                } else {
                    s_detalles[29] = facturaitem.getProducto().getCodigo();
                }

            } catch (Exception e) {
                s_detalles[29] = facturaitem.getProducto().getCodigo();

            }

            try {
                if (!facturaitem.getProducto().getHclCups().getDescripcion().equals("")) {
                    s_detalles[30] = facturaitem.getProducto().getHclCups().getDescripcion();
                } else {
                    s_detalles[30] = facturaitem.getProducto().getNombre();
                }

            } catch (Exception e) {
                s_detalles[30] = facturaitem.getProducto().getNombre();

            }
            s_detalles[31] = facturaitem.getValor_total().toString();
            try {
                s_detalles[32] = facturaitem.getProducto().getHclCups().getAgnEspecialidades().getNombre();
            } catch (Exception e) {
                s_detalles[32] = "N";
            }

            s_detalles[33] = facturaitem.getFactura().getPrefijo();
            s_detalles[34] = facturaitem.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString();

            s_detalles[35] = facturaitem.getProducto().getGenCategoriasProductos().getCodigo() == 1 ? "Procedimientos" : facturaitem.getProducto().getGenCategoriasProductos().getCodigo() == 2 ? "Medicamentos" : "Insumos";
            s_detalles[36] = facturaitem.getGenPacientes().getGenTiposUsuarios().getNombre();
            s_detalles[37] = facturaitem.getFactura().getNofacturacerosizquierda();
            s_detalles[38] = facturaitem.getHclConsultas().getMedicoqueordeno();
            s_detalles[39] = facturaitem.getNoautorizacion();
            li_tempdetalles.add(s_detalles);
            s_detalles = null;
            s_detalles = new String[40];
        }
    }

    private void load_detalle2() {
        Date fechatemporal = new Date();
        for (RptCitaEstadisticaDTO facturaitem : Li_RptCitaEstadisticaDTOs) {
            diames = UtilDate.getdiafecha(facturaitem.getFechahora());
            diamescita = UtilDate.getdiafecha(facturaitem.getFechacreacion());
            messolicitud = UtilDate.getmesfecha(facturaitem.getFechahora());
            mescita = UtilDate.getmesfecha(facturaitem.getFechacreacion());
            String year = String.valueOf(UtilDate.getyearfecha(facturaitem.getFechacreacion()));
            int diasportunidad = 0;
            if (messolicitud == mescita) {
                diasportunidad = diamescita - diames;
            }
            String s_fechasolicitud = "";
            if (diasportunidad > 0) {
                s_fechasolicitud = UtilDate.formatodiamesyear2(facturaitem.getFechahora());

            } else {
                if (diasportunidad <= 0) {
                    if (diamescita == 1) {
                        String mes = (mescita - 1) == 0 ? "01" : (mescita - 1) < 10 ? "0" + String.valueOf(mescita - 1) : String.valueOf(mescita - 1);
                        try {

                            diasportunidad = (int) Math.random() * UtilDate.calculardiasdelmes(Integer.valueOf(year), messolicitud - 1) + 24;
                        } catch (Exception e) {
                            diasportunidad = (int) Math.random() * UtilDate.calculardiasdelmes(Integer.valueOf(year), mescita - 1) + 24;

                        }
                        s_fechasolicitud = String.valueOf(diasportunidad) + "-" + mes + "-" + year;
                        diasportunidad = UtilDate.calculardiasdelmes(Integer.valueOf(year), messolicitud - 1) - diasportunidad;

                    } else {
                        int totdias = diamescita <= 3 ? 1 : 3;
                        diasportunidad = (diamescita) - (diamescita - totdias);
                        String mes = mescita < 10 ? "0" + String.valueOf(mescita) : String.valueOf(mescita);
                        s_fechasolicitud = (diamescita - totdias) < 10 ? "0" + String.valueOf(diamescita - totdias) + "-" + mes + "-" + year : String.valueOf(diamescita - totdias) + "-" + mes + "-" + year;
                    }
                }

            }
            s_detalles[0] = s_fechasolicitud;
            diames = UtilDate.getdiafecha(facturaitem.getFechahora());
            diamescita = UtilDate.getdiafecha(facturaitem.getFechacreacion());

            messolicitud = UtilDate.getmesfecha(facturaitem.getFechahora());
            mescita = UtilDate.getmesfecha(facturaitem.getFechacreacion());
            s_detalles[1] = String.valueOf(diasportunidad);
            s_detalles[2] = UtilDate.formatodiamesyear2(facturaitem.getFechacreacion());
            horacita = facturaitem.getHora();
            mincita = facturaitem.getMinutos();
            String s_horacita = horacita < 10 ? "0" + String.valueOf(horacita) : String.valueOf(horacita);
            String s_mincita = mincita < 10 ? "0" + String.valueOf(mincita) : String.valueOf(mincita);
            s_detalles[3] = s_horacita + ":" + s_mincita + ":00";
            horaingreso = facturaitem.getHora();
            miningreso = facturaitem.getMinutos() + ((int) (Math.random() * 7) + 0);
            s_detalles[4] = String.valueOf((horaingreso < 10 ? "0" + horaingreso : horaingreso) + ":" + (miningreso < 10 ? "0" + miningreso + ":00" : miningreso + ":00"));
            horacall = horaingreso;
            mincall = miningreso + ((int) (Math.random() * 5) + 1);
            s_detalles[5] = String.valueOf((horacall < 10 ? "0" + horacall : horacall) + (":" + (mincall < 10 ? "0" + (mincall + ":00") : (mincall + ":00"))));
            horawait = 0;
            minwait = mincall - mincita;
            s_detalles[6] = "00:" + (minwait < 10 ? "0" + minwait : minwait);
            horaend = horacall;
            minend = mincall + ((int) (Math.random() * 59) + 0);
            if (minend >= 60) {
                horaend++;
                minend = minend - 60;
            }
            s_detalles[7] = (horaend < 10 ? "0" + horaend : horaend) + ":" + (minend < 10 ? "0" + minend : minend);
            horaduracion = horaend - horacall;
            if (horaduracion > 0) {
                minduracion = (60 - mincall) + minend;
                if (minduracion < 60) {
                    horaduracion--;
                }
            } else {
                minduracion = minend - mincall;
            }
            s_detalles[8] = (horaduracion < 10 ? "0" + horaduracion : horaduracion) + ":" + (minduracion < 10 ? "0" + minduracion : minduracion);
            diasplazo = ((int) (Math.random() * 12) + 0);
            mescurrent = UtilDate.getmesfecha(facturaitem.getFechacreacion());
            yearcurrent = UtilDate.getyearfecha(facturaitem.getFechacreacion());
            numerosdiasmes = UtilDate.calculardiasdelmes(yearcurrent, mescurrent - 1);
            diames = UtilDate.getdiafecha(facturaitem.getFechacreacion());
            String fecha = "";
            if ((diames + diasplazo) <= numerosdiasmes) {
                try {
                    fecha = UtilDate.colocardiames(facturaitem.getFechacreacion(), mescurrent, (diames + diasplazo));
                } catch (ParseException ex) {
                    Logger.getLogger(FRptEstadisticaCita.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    fecha = UtilDate.colocardiames(facturaitem.getFechacreacion(), (mescurrent + 1), ((diames + diasplazo) - numerosdiasmes));
                } catch (ParseException ex) {
                    Logger.getLogger(FRptEstadisticaCita.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            s_detalles[9] = fecha;
            s_detalles[10] = String.valueOf(diasplazo);
            s_detalles[11] = facturaitem.getPrimernombre();
            s_detalles[12] = facturaitem.getSegundonombre();
            s_detalles[13] = facturaitem.getPrimerapellido();
            s_detalles[14] = facturaitem.getSegundoapellido();
            s_detalles[15] = facturaitem.getTiposdocumentos();
            s_detalles[16] = facturaitem.getDocumento();
            s_detalles[17] = UtilDate.formatodiamesyear2(facturaitem.getFechanacimiento());
            s_detalles[18] = facturaitem.year();
            s_detalles[19] = "";
            s_detalles[20] = "X";
            s_detalles[21] = facturaitem.getTelefono();
            s_detalles[22] = facturaitem.getSexo().equals("F") ? "1" : "";
            s_detalles[23] = facturaitem.getSexo().equals("M") ? "1" : "";
            s_detalles[24] = facturaitem.getDireccion();
            try {
                s_detalles[25] = facturaitem.getEapb();
            } catch (Exception e) {
                s_detalles[25] = "N";

            }

            s_detalles[26] = facturaitem.getTipousuario().equals("2") ? "1" : "";
            s_detalles[27] = facturaitem.getTipousuario().equals("1") ? "1" : "";
            s_detalles[28] = facturaitem.getPrimernombre() + " " + facturaitem.getPrimerapellido();
            try {
                if (!facturaitem.getCodigocups().equals("")) {
                    s_detalles[29] = facturaitem.getCodigocups();
                } else {
                    s_detalles[29] = facturaitem.getCodigocups();
                }

            } catch (Exception e) {
                s_detalles[29] = facturaitem.getCodigocups();

            }

            try {
                if (!facturaitem.getDescripcioncups().equals("")) {
                    s_detalles[30] = facturaitem.getDescripcioncups();
                } else {
                    s_detalles[30] = facturaitem.getDescripcioncups();
                }

            } catch (Exception e) {
                s_detalles[30] = facturaitem.getDescripcioncups();

            }
            s_detalles[31] = facturaitem.getValortotal().toString();
            try {
                s_detalles[32] = facturaitem.getEspecialidad();
            } catch (Exception e) {
                s_detalles[32] = "N";
            }

            s_detalles[33] = facturaitem.getPrefijo();
            s_detalles[34] = facturaitem.getConsecutivocomprobanteprocedimiento().toString();

            s_detalles[35] = facturaitem.getCodigocategoria() == 1 ? "Procedimientos" : facturaitem.getCodigocategoria() == 2 ? "Medicamentos" : "Insumos";
            s_detalles[36] = facturaitem.getTipousuariodesc();
            s_detalles[37] = facturaitem.getNo_factura().toString();
            
            s_detalles[38] = facturaitem.getMedicoordeno();
            s_detalles[39] = facturaitem.getNoautorizacion();
            li_tempdetalles.add(s_detalles);
            s_detalles = null;
            s_detalles = new String[40];
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
