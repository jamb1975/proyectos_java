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
import java.math.BigDecimal;
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
import javafx.scene.control.CheckBox;
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
import modelo.Factura;
import modelo.RptFacturasDTO;
import org.eclipse.persistence.internal.jpa.parsing.jpql.antlr.JPQLLexer;

import sihic.controlador.FacturaControllerClient;

/**
 *
 * @author adminlinux
 */
public class FRptFacturasElectronicas extends Application {

    private GridPane Gp_Generic;
    private TableView ta_factura;
    
    private FacturaControllerClient facturaControllerClient;
    private Button bu_exportar;
    private Button bu_consultar;
    private DatePicker Dp_Date_from;
    private DatePicker Dp_Date_to;
    private Label L_Date_from;
    private Label L_Date_to;
    private ObservableList Ol_Factura = FXCollections.observableArrayList();
    private List<Factura> Li_facturas;
    private CheckBox ch_menoscopago=new CheckBox("- Copago:");
    private CheckBox ch_menoscuotamoderadora=new CheckBox("- Cuota moderadora:");
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
         L_Date_from = new Label("Desde:");
        L_Date_to = new Label("A: ");
        Dp_Date_from = new DatePicker();
        Dp_Date_from.setValue(LocalDate.now());
        Dp_Date_to = new DatePicker();
        Dp_Date_to.setValue(LocalDate.now());
   
              Dp_Date_from.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent ke) {
                
                    //loadData();
                
            }
        });
             Dp_Date_to.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent ke) {
               
                  //  loadData();
  

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

        bu_consultar = new Button("Consultar");
       
      
        bu_consultar.setOnAction(e
                -> {

            try {
              loadData();
            } catch (Exception ex) {

            }

        });
      
        Gp_Generic = new GridPane();
        ta_factura = new TableView();
         TableColumn eapb = new TableColumn();
        eapb.setMinWidth(100);
        eapb.setText("Empresa");
        eapb.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                try {
                    return new SimpleStringProperty(SihicApp.genUnidadesOrganizacion.getNombre());
                } catch (Exception ex) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        TableColumn prefijo = new TableColumn();
        prefijo.setMinWidth(100);
        prefijo.setText("Prefijo");
        prefijo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                try {
                    return new SimpleStringProperty(factura.getValue().getPrefijo());
                } catch (Exception ex) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(100);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                try {
                    return new SimpleStringProperty(factura.getValue().getNo_factura().toString());
                } catch (Exception ex) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        TableColumn fechaprocedimiento = new TableColumn();
        fechaprocedimiento.setMinWidth(100);
        fechaprocedimiento.setText("Fecha");
        fechaprocedimiento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(sihic.util.UtilDate.formatodiamesyear(factura.getValue().getFacturaDate()));

            }
        });
       
        TableColumn nitinterno = new TableColumn();
        nitinterno.setMinWidth(100);
        nitinterno.setText("Tercero Interno");
        nitinterno.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                try {
                    return new SimpleStringProperty((factura.getValue().getPrefijo().equals("A") || factura.getValue().getPrefijo().equals("P")) ? factura.getValue().getFacturaItems().get(0).getGenEapb().getNit() : factura.getValue().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
                } catch (Exception ex) {
                       return new SimpleStringProperty("No");

                    
                }

            }
        });
         TableColumn nitexterno = new TableColumn();
         nitexterno.setMinWidth(100);
         nitexterno.setText("Tercero Externo");
         nitexterno.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                try {
                    return new SimpleStringProperty((factura.getValue().getPrefijo().substring(0, 1).equals("A") || factura.getValue().getPrefijo().substring(0, 1).equals("P")) ? factura.getValue().getFacturaItems().get(0).getGenEapb().getNit() : factura.getValue().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
                } catch (Exception ex) {
                       return new SimpleStringProperty("No");

                    
                }

            }
        });
        TableColumn concepto = new TableColumn();
        concepto.setMinWidth(200);
        concepto.setText("Concepto");
        concepto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    try {
                    return new SimpleStringProperty((factura.getValue().getPrefijo().substring(0, 1).equals("A") || factura.getValue().getPrefijo().substring(0, 1).equals("P")) ? factura.getValue().getFacturaItems().get(0).getProducto().getCodigo()+" "+ factura.getValue().getFacturaItems().get(0).getProducto().getNombre(): factura.getValue().getFacturaItems().get(0).getProducto().getCodigo()+" "+ factura.getValue().getFacturaItems().get(0).getProducto().getNombre());
                } catch (Exception ex) {
                       return new SimpleStringProperty("Factura de Venta");

                    
                }

              
                

            }
        });

        TableColumn formadepago = new TableColumn();
        formadepago.setMinWidth(100);
        formadepago.setText("Forma de Pago");
        formadepago.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                
                    return new SimpleStringProperty(factura.getValue().getCredito()==null?"Credito":factura.getValue().getCredito()==true?"Credito":"Contado");
              
            }
        });
        TableColumn copagos = new TableColumn();
        copagos.setMinWidth(100);
        copagos.setText("Copagos");
        copagos.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(String.valueOf(factura.getValue().calculartotalescopagocuotamoeras2()));
              
            }
        });
         TableColumn cuotamoderadora = new TableColumn();
        cuotamoderadora.setMinWidth(100);
        cuotamoderadora.setText("Cuota Moderadora");
        cuotamoderadora.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(String.valueOf(factura.getValue().calculartotalescuotamoeras()));
              
            }
        });
         TableColumn producto = new TableColumn();
        producto.setMinWidth(100);
        producto.setText("Producto");
        producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                
                    return new SimpleStringProperty(factura.getValue().getGenTiposUsuarios()!=null?factura.getValue().getGenTiposUsuarios().getNombre():"N/A");
              
            }
        });
        TableColumn bodega = new TableColumn();
        bodega.setMinWidth(100);
        bodega.setText("Bodega");
        bodega.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                
                    return new SimpleStringProperty(SihicApp.sucursales.getNombre());
              
            }
        });
        TableColumn valorfactura = new TableColumn();
        valorfactura.setMinWidth(100);
        valorfactura.setText("Valor Factura");
        valorfactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                               
                    return new SimpleStringProperty(String.valueOf(factura.getValue().getNetAmount().add(totalmenoscopagosycuotas(factura.getValue())).longValue()));
            }
        });
        TableColumn descuento = new TableColumn();
        descuento.setMinWidth(100);
        descuento.setText("Descuento");
        descuento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                
                    return new SimpleStringProperty("0");
              
            }
        });
         TableColumn vencimiento = new TableColumn();
        vencimiento.setMinWidth(100);
        vencimiento.setText("Fecha Vencimiento");
        vencimiento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                
                    return new SimpleStringProperty(sihic.util.UtilDate.formatodiamesyear(factura.getValue().getFechavencimiento()));
              
            }
        });
        ta_factura.getColumns().addAll(eapb, prefijo, nofactura, fechaprocedimiento,nitinterno , nitexterno, concepto, formadepago, copagos,cuotamoderadora,producto,bodega,valorfactura, descuento, vencimiento);

        Gp_Generic.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Generic.getStyleClass().add("background");

        Gp_Generic.add(L_Date_from, 0, 0);
        Gp_Generic.add(Dp_Date_from, 1, 0);
        Gp_Generic.add(L_Date_to, 2, 0);
        Gp_Generic.add(Dp_Date_to, 3, 0);
        Gp_Generic.add(ch_menoscopago, 4, 0);
        Gp_Generic.add(ch_menoscuotamoderadora, 5, 0);
        Gp_Generic.add(bu_exportar, 6, 0);
        Gp_Generic.add(bu_consultar, 7, 0);
        //Gp_Generic.add(bu_importar, 6, 0);
        Gp_Generic.add(ta_factura, 0, 1, 8, 1);

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
                    Li_facturas = facturaControllerClient.facturaselectronicas(Dp_Date_from.getValue().toString(), Dp_Date_to.getValue().toString());
                   // List<Factura> l_facturas= Li_facturas.stream().filter(distinctByKey(b ->b.getNo_factura().toString()+b.getPrefijo())).collect(Collectors.toList());//.distinct().collect(Collectors.toList());
                  // Li_facturas=l_facturas;
                    Ol_Factura.clear();
                    Ol_Factura.addAll(Li_facturas.toArray());
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
            String rutaArchivo = "/home/adminlinux/rptfacturaselectronicas.xls";
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
            WritableSheet hoja = libro.createSheet("Facturas", 0);
            int f = 0;

            for (Factura fa : Li_facturas) {

                if (f == 0) {
                    hoja.insertRow(f);

                    // CellType.LABEL;
                    WritableCellFeatures writableCellFeatures = new WritableCellFeatures();
                    writableCellFeatures.removeDataValidation();
                    label = null;
                    label = new jxl.write.Label(f, f, "Empresa");
                    hoja.addCell(label);

                    label = null;
                    label = new jxl.write.Label(1, f, "Prefijo");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(2, f, "Documento Número");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(3, f, "Fecha");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(4, f, "Tercero Interno");
                    hoja.addCell(label);

                    label = null;
                    label = new jxl.write.Label(5, f, "Tercero Externo");
                    hoja.addCell(label);

                    label = null;
                    label = new jxl.write.Label(6, f, "Concepto");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(7, f, "Forma de Pago");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(8, f, "Copagos");
                    hoja.addCell(label);
                    
                    label = null;
                    label = new jxl.write.Label(9, f, "Cuota Moderadora");
                    hoja.addCell(label);
                    
                    label = null;
                    label = new jxl.write.Label(10, f, "Producto");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(11, f, "Bodega");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(12, f, "IVA");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(13, f, "Valor Factura");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(14, f, "Descuento");
                    hoja.addCell(label);
                    label = null;
                    label = new jxl.write.Label(15, f, "Fecha Vencimiento");
                    hoja.addCell(label);
                }
                f++;
                hoja.insertRow(f);
                label = null;
                label = new jxl.write.Label(0, f, SihicApp.genUnidadesOrganizacion.getNombre());
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(1, f, fa.getPrefijo());
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(2, f, fa.getNo_factura().toString());
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(3, f, sihic.util.UtilDate.formatodiamesyear(fa.getFacturaDate()));
                hoja.addCell(label);
                label = null;
                try
                {
                label = new jxl.write.Label(4, f, (fa.getPrefijo().substring(0, 1).equals("A") || fa.getPrefijo().substring(0, 1).equals("P")) ? fa.getFacturaItems().size()>0?fa.getFacturaItems().get(0).getGenEapb().getNit():"NO": fa.getFacturaItems().size()>0?fa.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento():"NO");
                }catch(Exception e)
                {
                    label = new jxl.write.Label(4, f, "NO");
                
                }
                hoja.addCell(label);
                label = null;
                try
                {
                label = new jxl.write.Label(5, f, (fa.getPrefijo().substring(0, 1).equals("A") || fa.getPrefijo().substring(0, 1).equals("P")) ? fa.getFacturaItems().size()>0?fa.getFacturaItems().get(0).getGenEapb().getNit():"NO": fa.getFacturaItems().size()>0?fa.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento():"NO");
               }catch(Exception e)
                {
                    label = new jxl.write.Label(5, f, "NO");
                
                }
                hoja.addCell(label);
                label = null;
                try
                {
                label = new jxl.write.Label(6, f, fa.getFacturaItems().get(0).getProducto().getCodigo()+" "+ fa.getFacturaItems().get(0).getProducto().getNombre());
                
            }catch(Exception e)
            {
                          label = new jxl.write.Label(6, f, "");
            }
                
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(7, f, fa.getCredito()==null?"Crédito":fa.getCredito()==true?"Crédito":"Contado");
                hoja.addCell(label);
                 label = null;
                label = new jxl.write.Label(8, f, String.valueOf(fa.calculartotalescopagocuotamoeras2()));
                hoja.addCell(label);
                
                label = null;
                label = new jxl.write.Label(9, f, String.valueOf(fa.calculartotalescuotamoeras()));
                hoja.addCell(label);
                
                label = null;
                label = new jxl.write.Label(10, f, fa.getGenTiposUsuarios()!=null?fa.getGenTiposUsuarios().getNombre():"N/A");
                hoja.addCell(label); 
                label = null;
                label = new jxl.write.Label(11, f, SihicApp.sucursales.getNombre());
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(12, f, "0");
                hoja.addCell(label);
                 label = null;
                label = new jxl.write.Label(13, f, String.valueOf(fa.getNetAmount().add(totalmenoscopagosycuotas(fa)).longValue()));
                hoja.addCell(label);
                 label = null;
                label = new jxl.write.Label(14, f, "0");
                hoja.addCell(label);
                 label = null;
                label = new jxl.write.Label(15, f, sihic.util.UtilDate.formatodiamesyear(fa.getFacturaDate()));
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
 private BigDecimal totalmenoscopagosycuotas(Factura factura)
 {
    BigDecimal total=BigDecimal.ZERO; 
    if(ch_menoscopago.isSelected())
    {
        total=factura.getTotalcopagos();
    }
    if(ch_menoscuotamoderadora.isSelected())
    {
        total=total.add(factura.getTotalcuotasmoderadoras());
    }
    return total;
 }
}
