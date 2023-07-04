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
import java.util.List;
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

/**
 *
 * @author adminlinux
 */
public class FRptCitas extends Application {

    private GridPane Gp_Generic;
    private TableView ta_factura;
    private Label l_buscar;
    private Label la_eapb;
    private FacturaItemControllerClient facturaItemControllerClient;
    private Button bu_exportar;

    private DatePicker Dp_Date_from;
    private DatePicker Dp_Date_to;
    private Label L_Date_from;
    private Label L_Date_to;
    private ObservableList Ol_FacturaItem = FXCollections.observableArrayList();
    ;
    private List<FacturaItem> Li_facturasitem;
    private SearchBox buscar = new SearchBox();
    private SearchPopover sp_producto;
    // private SearchPopover sp_geneapb;
    private TextField sb_eapb = new TextField();

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        sb_eapb.setOnKeyPressed(e -> {
            try {
                loadData();
            } catch (ParseException ex) {
                Logger.getLogger(FRptCitas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        sp_producto = new SearchPopover(buscar, new PageBrowser(), SihicApp.s_producto, false);
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

        l_buscar = new Label("Procedimiento:");
        la_eapb = new Label("Entidad Salud:");
        buscar.setOnAction(e -> {
            try {
                buscar.setText(SihicApp.s_producto.getCodigo() + " || " + SihicApp.s_producto.getNombre());
                loadData();
            } catch (Exception e3) {
            }
        });
        Gp_Generic = new GridPane();
        ta_factura = new TableView();
        TableColumn fechaprocedimiento = new TableColumn();
        fechaprocedimiento.setMinWidth(100);
        fechaprocedimiento.setText("Fecha Proc");
        fechaprocedimiento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {

                return new SimpleStringProperty(UtilDate.s_formatoyearmesdia(facturaitem.getValue().getHclConsultas().getAgnCitas().getFechaHora()));

            }
        });
        TableColumn eapb = new TableColumn();
        eapb.setMinWidth(100);
        eapb.setText("Entidad Salud");
        eapb.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                try {
                    return new SimpleStringProperty(facturaitem.getValue().getGenPacientes().getGenEapb().getNombre());
                } catch (Exception ex) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        TableColumn nit = new TableColumn();
        nit.setMinWidth(100);
        nit.setText("Nit");
        nit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                try {
                    return new SimpleStringProperty(facturaitem.getValue().getGenPacientes().getGenEapb().getNit());
                } catch (Exception ex) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        TableColumn tipousuario = new TableColumn();
        tipousuario.setMinWidth(100);
        tipousuario.setText("Tipo Usuario");
        tipousuario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                try {
                    return new SimpleStringProperty(facturaitem.getValue().getGenPacientes().getGenTiposUsuarios().getNombre());
                } catch (Exception ex) {
                    return new SimpleStringProperty("No");
                }

            }
        });

        TableColumn tipoident = new TableColumn();
        tipoident.setMinWidth(100);
        tipoident.setText("Tipo de Ident.");
        tipoident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla());

            }
        });
        TableColumn noident = new TableColumn();
        noident.setMinWidth(100);
        noident.setText("N° de Ident.");
        noident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());

            }
        });
        TableColumn primerapellido = new TableColumn();
        primerapellido.setMinWidth(170);
        primerapellido.setText("Primer Apellido");
        primerapellido.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido());

            }
        });
        TableColumn segundoapellido = new TableColumn();
        segundoapellido.setMinWidth(170);
        segundoapellido.setText("Segundo Apellido");
        segundoapellido.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido());

            }
        });
        TableColumn primernombre = new TableColumn();
        primernombre.setMinWidth(170);
        primernombre.setText("Primer Nombre");
        primernombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre());

            }
        });
        TableColumn segundonombre = new TableColumn();
        segundonombre.setMinWidth(170);
        segundonombre.setText("Segundo Nombre");
        segundonombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {

                return new SimpleStringProperty(facturaitem.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre()
                );

            }
        });
        TableColumn fechanacimiento = new TableColumn();
        fechanacimiento.setMinWidth(100);
        fechanacimiento.setText("Fecha Nac");
        fechanacimiento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {

                return new SimpleStringProperty(UtilDate.s_formatoyearmesdia(facturaitem.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento()));

            }
        });
        TableColumn codproc = new TableColumn();
        codproc.setMinWidth(100);
        codproc.setText("Cod.Cups");
        codproc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                try {
                    return new SimpleStringProperty(facturaitem.getValue().getProducto().getHclCups().getCodigo()
                    );
                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        TableColumn descrip = new TableColumn();
        descrip.setMinWidth(200);
        descrip.setText("Descripción");
        descrip.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaitem) {
                try {
                    return new SimpleStringProperty(facturaitem.getValue().getProducto().getHclCups().getDescripcion()
                    );
                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        ta_factura.getColumns().addAll(fechaprocedimiento, eapb, nit, tipousuario, tipoident, noident, primerapellido, segundoapellido, primernombre, segundonombre, fechanacimiento, codproc, descrip);

        Gp_Generic.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Generic.getStyleClass().add("background");
        Gp_Generic.add(l_buscar, 0, 0);
        Gp_Generic.add(buscar, 1, 0);
        Gp_Generic.add(la_eapb, 2, 0);
        Gp_Generic.add(sb_eapb, 3, 0);
        Gp_Generic.add(L_Date_from, 4, 0);
        Gp_Generic.add(Dp_Date_from, 5, 0);
        Gp_Generic.add(L_Date_to, 6, 0);
        Gp_Generic.add(Dp_Date_to, 7, 0);
        Gp_Generic.add(bu_exportar, 8, 0);
        Gp_Generic.add(ta_factura, 0, 1, 9, 1);
        Gp_Generic.add(sp_producto, 1, 1, 7, 2);

        Gp_Generic.setVgap(5);
        Gp_Generic.setHgap(5);
        Gp_Generic.setMinSize(1350, 650);
        ta_factura.autosize();
        ta_factura.setMinHeight(590);
        ta_factura.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        StackPane stackPane = new StackPane(Gp_Generic);
        stackPane.setAlignment(Pos.TOP_CENTER);
        return stackPane;
    }

    public void loadData() throws ParseException {
        try {
            Li_facturasitem = facturaItemControllerClient.rptprocedimientos(Dp_Date_from.getValue().toString(), Dp_Date_to.getValue().toString(), SihicApp.s_producto, sb_eapb.getText());
            Ol_FacturaItem.clear();
            Ol_FacturaItem.addAll(Li_facturasitem.toArray());
            ta_factura.setItems(Ol_FacturaItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void exportar_excel() throws IOException, WriteException {
        String rutaArchivo = "/home/adminlinux/sihic/rptprocedimientos.xls";
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
        WritableSheet hoja = libro.createSheet("Procedimientos", 0);
        int f = 0;

        for (FacturaItem fi : Li_facturasitem) {

            if (f == 0) {
                hoja.insertRow(f);

                // CellType.LABEL;
                WritableCellFeatures writableCellFeatures = new WritableCellFeatures();
                writableCellFeatures.removeDataValidation();
                label = null;
                label = new jxl.write.Label(f, f, "Fecha Procedimiento");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(1, f, "Entidad Salud");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(2, f, "Nit");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(3, f, "Tipos de Usuario");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(4, f, "Tipo Ident.");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(5, f, "N° Ident.");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(6, f, "Primer Apellido");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(7, f, "Segundo Apellido");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(8, f, "Primer Nombre");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(9, f, "Segundo Nombre");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(10, f, "Fecha Nacimiento");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(11, f, "Cod Cups");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(12, f, "Descripción");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(13, f, "Resultado");
                hoja.addCell(label);
            }
            f++;
            hoja.insertRow(f);
            label = null;
            label = new jxl.write.Label(0, f, UtilDate.s_formatoyearmesdia(fi.getHclConsultas().getAgnCitas().getFechaHora()));
            hoja.addCell(label);

            label = null;
            try {
                label = new jxl.write.Label(1, f, fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenEapb().getNombre());
            } catch (Exception e) {
                label = new jxl.write.Label(1, f, "No");

            }

            hoja.addCell(label);

            label = null;
            try {
                label = new jxl.write.Label(2, f, fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenEapb().getNit());
            } catch (Exception e) {
                label = new jxl.write.Label(2, f, "No");

            }
            hoja.addCell(label);
            label = null;
            label = new jxl.write.Label(3, f, fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre());
            hoja.addCell(label);

            label = null;
            label = new jxl.write.Label(4, f, fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla());
            hoja.addCell(label);

            label = null;
            label = new jxl.write.Label(5, f, fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
            hoja.addCell(label);

            label = null;
            label = new jxl.write.Label(6, f, fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido());
            hoja.addCell(label);
            label = null;
            label = new jxl.write.Label(7, f, fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido());
            hoja.addCell(label);
            label = null;
            label = new jxl.write.Label(8, f, fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre());
            hoja.addCell(label);

            label = null;
            label = new jxl.write.Label(9, f, fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre());
            hoja.addCell(label);

            label = null;
            label = new jxl.write.Label(10, f, UtilDate.s_formatoyearmesdia(fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento()));
            hoja.addCell(label);

            label = null;
            label = new jxl.write.Label(11, f, fi.getProducto().getHclCups().getCodigo());
            hoja.addCell(label);
            label = null;
            label = new jxl.write.Label(12, f, fi.getProducto().getHclCups().getDescripcion());
            hoja.addCell(label);

        }
        libro.write();
        libro.close();
        archivo.close();
        Desktop.getDesktop().open(archivoXLS);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
