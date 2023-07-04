/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.control.SearchBox;
import jxl.Workbook;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import modelo.RptFacturasDTO;

import sihic.controlador.FacturaControllerClient;

/**
 *
 * @author adminlinux
 */
public class FRptFacturas extends Application {

    private GridPane Gp_Generic;
    private TableView ta_factura;
    private Label l_buscar;
    private Label la_eapb;
    private FacturaControllerClient facturaControllerClient;
    private Button bu_exportar;
    private Button bu_importar;
    private DatePicker Dp_Date_from;
    private DatePicker Dp_Date_to;
    private Label L_Date_from;
    private Label L_Date_to;
    private ObservableList Ol_Factura = FXCollections.observableArrayList();
    ;
    private List<RptFacturasDTO> Li_facturas;
    private SearchBox buscar = new SearchBox();
    private SearchPopover sp_producto;
    private ChoiceBox cb_valor;
    // private SearchPopover sp_geneapb;
    private TextField sb_eapb = new TextField();
   static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
} 
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        sb_eapb.setOnKeyPressed(e -> {
            try {
                loadData();
            } catch (ParseException ex) {
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
        cb_valor = new ChoiceBox();
        cb_valor.getItems().addAll("Facturas Con Valor 0", "Facturas con Valor >0", "Todas las facturas");
        cb_valor.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        cb_valor.getSelectionModel().select(0);
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
        facturaControllerClient = new FacturaControllerClient();
        ImageView imageView = new ImageView("/images/excel.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_exportar = new Button("", imageView);
        Tooltip tooltip = new Tooltip("Exportar Facturas");
        bu_exportar.setTooltip(tooltip);
        bu_exportar.setOnAction(e
                -> {

            try {
                exportar_excel();
            } catch (Exception ex) {

            }

        });
        imageView = new ImageView("/images/excel.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_importar = new Button("", imageView);
        Tooltip tooltip2 = new Tooltip("Importar Archivo Ciiu");
        bu_importar.setTooltip(tooltip2);
        bu_importar.setOnAction(e
                -> {

            try {

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
        fechaprocedimiento.setText("Fecha RptFacturasDTO");
        fechaprocedimiento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RptFacturasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RptFacturasDTO, String> factura) {

                return new SimpleStringProperty(factura.getValue().getFacturaDate());

            }
        });
        TableColumn eapb = new TableColumn();
        eapb.setMinWidth(100);
        eapb.setText("N° Factura");
        eapb.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RptFacturasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RptFacturasDTO, String> factura) {
                try {
                    return new SimpleStringProperty(factura.getValue().getNo_factura().toString());
                } catch (Exception ex) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        TableColumn nit = new TableColumn();
        nit.setMinWidth(100);
        nit.setText("Nit o Id");
        nit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RptFacturasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RptFacturasDTO, String> factura) {
                try {
                    return new SimpleStringProperty((factura.getValue().getPrefijo().equals("A") || factura.getValue().getPrefijo().equals("P")) ? factura.getValue().getEapb() : factura.getValue().getDocumento());
                } catch (Exception ex) {
                    try {
                        return new SimpleStringProperty(factura.getValue().getDocumento());
                    } catch (Exception e) {
                        return new SimpleStringProperty("No");

                    }
                }

            }
        });
        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(300);
        nombre.setText("Nombres");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RptFacturasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RptFacturasDTO, String> factura) {
                try {
                    return new SimpleStringProperty((factura.getValue().getPrefijo().substring(0, 1).equals("A") || factura.getValue().getPrefijo().substring(0, 1).equals("P")) ? factura.getValue().getEapb() : factura.getValue().getNombre());
                } catch (Exception ex) {
                    try {
                        return new SimpleStringProperty(factura.getValue().getNombre());
                    } catch (Exception e) {
                        return new SimpleStringProperty("No");

                    }
                }

            }
        });

        TableColumn telefono = new TableColumn();
        telefono.setMinWidth(100);
        telefono.setText("Telefono");
        telefono.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RptFacturasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RptFacturasDTO, String> factura) {
                try {
                    return new SimpleStringProperty((factura.getValue().getPrefijo().substring(0, 1).equals("A") || factura.getValue().getPrefijo().substring(0, 1).equals("P")) ? factura.getValue().getEapb() : factura.getValue().getTelefono());
                } catch (Exception ex) {
                    try {
                        return new SimpleStringProperty(factura.getValue().getTelefono());
                    } catch (Exception e) {
                        return new SimpleStringProperty("No");

                    }
                }

            }
        });
        TableColumn dir = new TableColumn();
        dir.setMinWidth(300);
        dir.setText("Dirección");
        dir.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RptFacturasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RptFacturasDTO, String> factura) {
                try {
                    return new SimpleStringProperty((factura.getValue().getPrefijo().substring(0, 1).equals("A") || factura.getValue().getPrefijo().substring(0, 1).equals("P")) ? factura.getValue().getDireccioneapb() : factura.getValue().getDireccion());
                } catch (Exception ex) {
                    try {
                        return new SimpleStringProperty(factura.getValue().getDireccion());
                    } catch (Exception e) {
                        return new SimpleStringProperty("No");

                    }
                }

            }
        });
        TableColumn valor = new TableColumn();
        valor.setMinWidth(150);
        valor.setText("Total ");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RptFacturasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RptFacturasDTO, String> factura) {

                return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());

            }
        });
        TableColumn prefijo = new TableColumn();
        prefijo.setMinWidth(100);
        prefijo.setText("Prefijo");
        prefijo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RptFacturasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RptFacturasDTO, String> factura) {

                return new SimpleStringProperty(factura.getValue().getPrefijo());

            }
        });
        TableColumn esp = new TableColumn();
        esp.setMinWidth(150);
        esp.setText("Esp");
        esp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RptFacturasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RptFacturasDTO, String> factura) {
                try {
                    return new SimpleStringProperty(factura.getValue().getEapb());
                } catch (Exception e3) {
                    try {
                        return new SimpleStringProperty(factura.getValue().getGenPacientes().getGenEapb().getNombre());
                    } catch (Exception e) {
                        return new SimpleStringProperty("No");

                    }
                }

            }
        });
        TableColumn niteps = new TableColumn();
        niteps.setMinWidth(150);
        niteps.setText("Nit Esp");
        niteps.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RptFacturasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RptFacturasDTO, String> factura) {
                try {
                    return new SimpleStringProperty(factura.getValue().getNit());
                } catch (Exception e3) {
                    try {
                        return new SimpleStringProperty(factura.getValue().getGenPacientes().getGenEapb().getNit());
                    } catch (Exception e) {
                        return new SimpleStringProperty("No");

                    }
                }

            }
        });
        ta_factura.getColumns().addAll(fechaprocedimiento, eapb, nit, nombre, telefono, dir, valor, prefijo, esp, niteps);

        Gp_Generic.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Generic.getStyleClass().add("background");

        Gp_Generic.add(L_Date_from, 0, 0);
        Gp_Generic.add(Dp_Date_from, 1, 0);
        Gp_Generic.add(L_Date_to, 2, 0);
        Gp_Generic.add(Dp_Date_to, 3, 0);
        Gp_Generic.add(cb_valor, 4, 0);
        Gp_Generic.add(bu_exportar, 5, 0);
        //Gp_Generic.add(bu_importar, 6, 0);
        Gp_Generic.add(ta_factura, 0, 1, 9, 1);

        Gp_Generic.setVgap(5);
        Gp_Generic.setHgap(5);
        Gp_Generic.setMinSize(1150, 650);
        ta_factura.autosize();
        ta_factura.setMinHeight(590);
        ta_factura.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        StackPane stackPane = new StackPane(Gp_Generic);
        stackPane.setAlignment(Pos.TOP_CENTER);
        return stackPane;
    }

    public void loadData() throws ParseException {
        Task task_serviciosproducto = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Li_facturas = facturaControllerClient.facturas(Dp_Date_from.getValue().toString(), Dp_Date_to.getValue().toString(), cb_valor.getSelectionModel().getSelectedIndex());
                    List<RptFacturasDTO> l_facturas= Li_facturas.stream().filter(distinctByKey(b ->b.getNo_factura().toString()+b.getPrefijo())).collect(Collectors.toList());//.distinct().collect(Collectors.toList());
                    Li_facturas=l_facturas;
                    Ol_Factura.clear();
                    Ol_Factura.addAll(l_facturas.toArray());
                    ta_factura.setItems(Ol_Factura);
                } catch (Exception e) {

                }
             
                return null;
            }
        };
        Thread thimport_serviciosproducto = new Thread(task_serviciosproducto);
        thimport_serviciosproducto.setDaemon(true);
        thimport_serviciosproducto.start();

    }

    public void exportar_excel() throws IOException, WriteException {
        try {
            String rutaArchivo = "/home/adminlinux/sihic/rptfacturas.xls";
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

            for (RptFacturasDTO fa : Li_facturas) {

                if (f == 0) {
                    hoja.insertRow(f);

                    // CellType.LABEL;
                    WritableCellFeatures writableCellFeatures = new WritableCellFeatures();
                    writableCellFeatures.removeDataValidation();
                    label = null;
                    label = new jxl.write.Label(f, f, "Fecha Factura");
                    hoja.addCell(label);

                    label = null;
                    label = new jxl.write.Label(1, f, "N° Factura");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(2, f, "Nit o Id");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(3, f, "Nombres");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(4, f, "Telefono");
                    hoja.addCell(label);

                    label = null;
                    label = new jxl.write.Label(5, f, "Dirección");
                    hoja.addCell(label);

                    label = null;
                    label = new jxl.write.Label(6, f, "Valor Neto Factura");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(7, f, "Prefijo Factura");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(8, f, "Eps");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(9, f, "Nit Eps");
                    hoja.addCell(label);

                    label = new jxl.write.Label(10, f, "Tipo Usuario");
                    hoja.addCell(label);
                }
                f++;
                hoja.insertRow(f);
                label = null;
                label = new jxl.write.Label(0, f, fa.getFacturaDate());
                hoja.addCell(label);

                label = null;
                try {
                    label = new jxl.write.Label(1, f, fa.getNo_factura().toString());
                } catch (Exception e) {
                    label = new jxl.write.Label(1, f, "No");

                }

                hoja.addCell(label);

                label = null;
                try {
                    label = new jxl.write.Label(2, f, fa.getPrefijo().substring(0, 1).equals("A") || fa.getPrefijo().substring(0, 1).equals("P") ? fa.getNit() : fa.getDocumento());
                } catch (Exception e) {
                    try {
                        label = new jxl.write.Label(2, f, fa.getDocumento());
                    } catch (Exception e2) {
                        label = new jxl.write.Label(2, f, "No");
                    }

                }
                hoja.addCell(label);
                label = null;
                try {
                    label = new jxl.write.Label(3, f, fa.getPrefijo().substring(0, 1).equals("A") || fa.getPrefijo().substring(0, 1).equals("P") ? fa.getEapb() : fa.getNombre());
                } catch (Exception e) {
                    try {
                        label = new jxl.write.Label(3, f, fa.getNombre());
                    } catch (Exception e2) {

                        label = new jxl.write.Label(3, f, "No");
                    }
                }
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(4, f, fa.getPrefijo().substring(0, 1).equals("A") || fa.getPrefijo().substring(0, 1).equals("P") ? fa.getTelefonoeapb() : fa.getTelefono());
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(5, f, fa.getPrefijo().substring(0, 1).equals("A") || fa.getPrefijo().substring(0, 1).equals("P") ? fa.getDireccioneapb() : fa.getDireccion());
                hoja.addCell(label);
                double valor2 = Double.valueOf(fa.getTotalAmount().toString());
                jxl.write.Number valor = new jxl.write.Number(6, f, valor2);
                hoja.addCell(valor);

                label = null;
                label = new jxl.write.Label(7, f, fa.getPrefijo());
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(8, f, fa.getEapb());

                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(9, f, fa.getNit());
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(10, f, fa.getTipousuario());
                hoja.addCell(label);

            }
            libro.write();
            libro.close();
            archivo.close();
            Desktop.getDesktop().open(archivoXLS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {
            loadData();
        } catch (Exception e) {

        }

    }

}
