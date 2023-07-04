/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.historialpaciente;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import jxl.Workbook;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import modelo.Factura;
import modelo.FacturaItem;
import sihic.SihicApp;
import sihic.contabilidad.AdminFacturaItem;
import sihic.contabilidad.ImpresionFactura;
import sihic.controlador.FacturaControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.UtilDate;

/**
 *
 * @author adminlinux
 */
public class Rips extends Application {

    private GridPane gp_generico;
    private TableView tv_facturas;
    private Label la_fechadesde;
    private DatePicker fechadesde;
    private Label la_fechahasta;
    private DatePicker fechahasta;
    private Label la_prefijo;
    private ChoiceBox prefijo;
    private Label la_nit;
    private TextField nit;
    private ObservableList Ol_Factura = FXCollections.observableArrayList();
    private List<Factura> Li_facturas;
    private List<FacturaItem> Li_facturasitems;
    private HclHistoriasClinicasControllerClient hclHistoriasClinicasControllerClient;
    private Button buscar;
    private Button bu_rips;
    private Button bu_rips_cuentasmedicas;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private FileChooser fileDialog;
    private FacturaControllerClient facturaControllerClient;

    //Variables para exporrtar a excel
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
    private String[] s_detalles = new String[56];
    HashMap<String, FacturaItem> hm_usuarios = new HashMap<>();
    private String pref="";
    private TextField noregistros=new TextField();
     static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
}
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        hclHistoriasClinicasControllerClient = new HclHistoriasClinicasControllerClient();
        facturaControllerClient = new FacturaControllerClient();
        LoadChoiceBoxGeneral.getCb_gentiposusuarios().getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().select(0);
        pref=LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString();
        fileDialog = new FileChooser();
        fileDialog.setTitle("Guardar");
        stack = new StackPane();
        la_fechadesde = new Label("Desde: ");
        la_fechadesde.setMinWidth(50);
        la_fechahasta = new Label("Hasta: ");
        la_fechahasta.setMinWidth(50);
        fechadesde = new DatePicker(LocalDate.now());
        fechadesde.setMinWidth(120);
        fechahasta = new DatePicker(LocalDate.now());
        fechahasta.setMinWidth(120);
        fechadesde.setOnAction(e -> {
            try {
                getFacturas();
            } catch (ParseException ex) {
                Logger.getLogger(Rips.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fechahasta.setOnAction(e -> {
            try {
                getFacturas();
            } catch (ParseException ex) {
                Logger.getLogger(Rips.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        la_prefijo = new Label("Prefijo:");

       // LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
       
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                     pref=new_val.toString();
                    try {
                        getFacturas();
                    } catch (ParseException ex) {
                        Logger.getLogger(Rips.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
        la_nit = new Label("Nit:");
        nit = new TextField();
        nit.setOnKeyPressed(e -> {
            try {
                getFacturas();
            } catch (ParseException ex) {
                Logger.getLogger(Rips.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ImageView imageView;

        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        buscar = new Button("", imageView);
        buscar.setOnAction(e
                -> {
            try {
                getFacturas();

            } catch (Exception ex) {
            }
        });
        imageView = new ImageView("/images/generarripsp.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_rips = new Button("", imageView);
        bu_rips.setOnAction(e
                -> {
            try {

                exportartxt();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
         bu_rips_cuentasmedicas = new Button("Rips cuentas médicas");
           bu_rips_cuentasmedicas.setOnAction(e
                -> {
            try {

                exportartxt_cuentasmedicas();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        

        //**********Ventana Personas
        gp_generico = new GridPane();
        tv_facturas = new TableView();
        tv_facturas.setMinHeight(550);

        TableColumn nofactura = new TableColumn();
        nofactura.setSortable(false);
        nofactura.setMinWidth(80);
        nofactura.setText("No Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getNofacturacerosizquierda());

            }
        });
        TableColumn valor = new TableColumn();
        valor.setSortable(false);
        valor.setMinWidth(100);
        valor.setText("Valor");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());

            }
        });
        TableColumn saldo = new TableColumn();
        saldo.setSortable(false);
        saldo.setMinWidth(100);
        saldo.setText("Saldo");
        saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getSaldo().toString());

            }
        });

        TableColumn noident = new TableColumn();
        noident.setSortable(false);
        noident.setMinWidth(140);
        noident.setText("Nit");
        noident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                try {
                    return new SimpleStringProperty(factura.getValue().getGenEapb().getNit());
                } catch (Exception e) {
                    try {
                        return new SimpleStringProperty(factura.getValue().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenEapb().getNit());

                    } catch (Exception e2) {
                        return new SimpleStringProperty("No");
                    }

                }

            }
        });
        TableColumn nombres = new TableColumn();
        nombres.setSortable(false);
        nombres.setMinWidth(650);
        if(pref.substring(0,1).equals("P") || pref.substring(0,1).equals("S")){
            nombres.setText("Eps-Paciente");
        }
        
        
        
        else{
             nombres.setText("Eps");
        }
        
        nombres.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                   try {
                        if(pref.substring(0,1).equals("A"))
                        return new SimpleStringProperty(factura.getValue().getGenEapb().getNombre());
                        else
                        if(pref.substring(0,1).equals("P") || pref.substring(0,1).equals("S"))
                         return new SimpleStringProperty(factura.getValue().getGenEapb().getNombre()+" || Paciente: "+factura.getValue().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getNombres()+" || "+factura.getValue().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento()+" || Cod Cups: "+factura.getValue().getFacturaItems().get(0).getProducto().getCodigo());
                        else
                            if(pref.substring(0,1).equals("T"))
                                    
                              return new SimpleStringProperty(factura.getValue().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getNombre());
                         else
                             return new SimpleStringProperty(factura.getValue().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenEapb().getNombre());
                        
                    } catch (Exception e2) {
                        return new SimpleStringProperty("No");
                    }

                
            }
        });
        TableColumn tipousuario = new TableColumn();
        tipousuario.setSortable(false);
        tipousuario.setMinWidth(200);
        tipousuario.setText("Tipo Usuario");
        tipousuario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                try {
                    return new SimpleStringProperty(factura.getValue().getGenTiposUsuarios().getNombre());
                } catch (Exception e) {
                    try {
                        return new SimpleStringProperty(factura.getValue().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre());

                    } catch (Exception e2) {
                        return new SimpleStringProperty("No");
                    }

                }
            }
        });
        TableColumn buttonCol = new TableColumn<>("");
        buttonCol.setSortable(false);
        buttonCol.setMinWidth(50);
        buttonCol.setSortable(false);
        buttonCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FacturaItem, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<FacturaItem, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        buttonCol.setCellFactory(
                new Callback<TableColumn<Factura, Boolean>, TableCell<Factura, Boolean>>() {

            @Override
            public TableCell<Factura, Boolean> call(TableColumn<Factura, Boolean> p) {
                return new ButtonCell(tv_facturas);
            }

        });
        TableColumn buttonColRips = new TableColumn<>("");
        buttonColRips.setMinWidth(50);
        buttonColRips.setSortable(false);
        buttonColRips.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FacturaItem, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<FacturaItem, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        buttonColRips.setCellFactory(
                new Callback<TableColumn<Factura, Boolean>, TableCell<Factura, Boolean>>() {

            @Override
            public TableCell<Factura, Boolean> call(TableColumn<Factura, Boolean> p) {
                return new ButtonCellRips(tv_facturas);
            }

        });
        TableColumn buttonColExcel = new TableColumn<>("");
        buttonColExcel.setMinWidth(50);
        buttonColExcel.setSortable(false);
        buttonColExcel.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FacturaItem, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<FacturaItem, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        buttonColExcel.setCellFactory(
                new Callback<TableColumn<Factura, Boolean>, TableCell<Factura, Boolean>>() {

            @Override
            public TableCell<Factura, Boolean> call(TableColumn<Factura, Boolean> p) {
                return new ButtonCellExcel(tv_facturas);
            }

        });
        tv_facturas.getColumns().addAll(nofactura, noident, nombres, tipousuario,valor, buttonCol, buttonColRips, buttonColExcel);

        tv_facturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_generico.getLayoutBounds().getHeight(), gp_generico.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);

        gp_generico.getStylesheets().add(SihicApp.hojaestilos);
        gp_generico.getStyleClass().add("background");
        gp_generico.setMinSize(1340, 650);
        gp_generico.addRow(0, la_fechadesde, fechadesde, la_fechahasta, fechahasta, la_prefijo, LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal, la_nit, nit, new Label("T. USuario"), LoadChoiceBoxGeneral.getCb_gentiposusuarios(), bu_rips);

        gp_generico.add(tv_facturas, 0, 3, 11, 1);
         gp_generico.addRow(4,new Label("N° Registros:"),noregistros);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);
        LoadChoiceBoxGeneral.getCb_gentiposusuarios().getSelectionModel().select(0);
        getFacturas();
        return stack;
    }

    private void getFacturas() throws ParseException {
        //colocamos los datos
  
        if (pref.substring(0, 1).equals("A") || pref.substring(0, 1).equals("T")) {
            Li_facturas = facturaControllerClient.facturascerradasrips(fechadesde.getValue().toString(), fechahasta.getValue().toString(), buscar.getText(), LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString(), nit.getText(), LoadChoiceBoxGeneral.getV_gentiposusuarios().get(LoadChoiceBoxGeneral.getCb_gentiposusuarios().getSelectionModel().getSelectedIndex()));
            /*for(Factura f: Li_facturas)
            {
                 f.calculateTotals();
                  FacturaControllerClient facturaControllerClient=new FacturaControllerClient();
                  facturaControllerClient.guardarFactura(f);
           }*/
            Ol_Factura.clear();
            Ol_Factura.addAll(Li_facturas.toArray());
            tv_facturas.setItems(Ol_Factura);
        } else {
            if (pref.substring(0, 1).equals("P") || pref.substring(0, 1).equals("S")) {
                Li_facturas = facturaControllerClient.facturascerradasrips(fechadesde.getValue().toString(), fechahasta.getValue().toString(), buscar.getText(),pref, nit.getText(), LoadChoiceBoxGeneral.getV_gentiposusuarios().get(LoadChoiceBoxGeneral.getCb_gentiposusuarios().getSelectionModel().getSelectedIndex()));
            
              //  Li_facturasitems = facturaControllerClient.facturasripsprefijop(fechadesde.getValue().toString(), fechahasta.getValue().toString(), buscar.getText(), pref, nit.getText(), LoadChoiceBoxGeneral.getV_gentiposusuarios().get(LoadChoiceBoxGeneral.getCb_gentiposusuarios().getSelectionModel().getSelectedIndex()));
              //  System.out.println("size"+Li_facturasitems.stream().filter(distinctByKey(b ->b.getFactura().getNo_factura())).collect(Collectors.toList()).size());
              Li_facturasitems=null;
              Li_facturasitems=new ArrayList<>();
              Li_facturasitems.clear();
                for(Factura f: Li_facturas)
                {   
                  for(FacturaItem fi: f.getFacturaItems())
                { 
                  
                   Li_facturasitems.add(fi);
                }
                }
                noregistros.setText(String.valueOf(Li_facturasitems.size()));
                Ol_Factura.clear();
                Ol_Factura.addAll(Li_facturas.toArray());
                tv_facturas.setItems(Ol_Factura);
            }   
            
        }

    }

    public void exportartxt() throws IOException, ParseException {
        int totregaf=0;
        int totregad=0;
        SihicApp.consecutivosContabilidadControllerClient.findlastconsecutivorips();
        Factura _factura = new Factura();
        //solo para factura tipo P
        BigDecimal totalfacturas = BigDecimal.ZERO;
        for (Factura f : Li_facturas) {
            totalfacturas = totalfacturas.setScale(0, RoundingMode.HALF_UP).add(f.getTotalAmount().setScale(0, RoundingMode.HALF_UP));
        }
        if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("A") || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("T")) {
            _factura = SihicApp.s_factura;
            Li_facturasitems = SihicApp.s_factura.getFacturaItems();
        } else {
            if (Li_facturasitems != null) {
                if (Li_facturasitems.size() > 0) {
                    _factura = Li_facturasitems.get(0).getFactura();
                }
            }
        }
        if (_factura != null) {
          
//DirectoryChooser dirDialog = new DirectoryChooser();

// Configure the properties
//dirDialog.setTitle("Seleccione un directorio de destino");
            File folder;
            if (SihicApp.isWindows()) {
                folder = new File("\\home\\adminlinux\\rips\\" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda());
            } else {
                folder = new File("/home/adminlinux/rips/" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda());
            }
            if (!folder.exists()) {
                folder.mkdir();
            }

            //dirDialog.setInitialDirectory(folder);
            // File dir = dirDialog.showDialog(null);
            if (folder != null) {
               
                File archivoct;
                File archivoaf;
                File archivous;
                File archivoac;
                File archivoap;
                File archivoau;
                File archivoah;
                File archivoan;
                File archivoam;
                File archivoat;
                File archivoad;
                
                 File archivoct_ANSI;
                File archivoaf_ANSI;
                File archivous_ANSI;
                File archivoac_ANSI;
                File archivoap_ANSI;
                File archivoau_ANSI;
                File archivoah_ANSI;
                File archivoan_ANSI;
                File archivoam_ANSI;
                File archivoat_ANSI;
                File archivoad_ANSI;
                String filect = "CT" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda()+ ".TXT";//archivo de control
                String fileaf = "AF" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda()+ ".TXT";//archivo de transacciones
                String fileus = "US" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de usuarios de servicios de salud
                String fileac = "AC" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de consulta
                String fileap = "AP" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de procedimientos
                String fileau = "AU" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de urgencias
                String fileah = "AH" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de hospitalizacion
                String filean = "AN" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de recien nacidos
                String fileam = "AM" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de medicamentos
                String fileat = "AT" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de otros servicios
                String filead = "AD" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de usuarios de servicios de salud
                
                String filect_ANSI = "CT" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda()+ "_ANSI.TXT";//archivo de control
                String fileaf_ANSI = "AF" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda()+ "_ANSI.TXT";//archivo de transacciones
                String fileus_ANSI = "US" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de usuarios de servicios de salud
                String fileac_ANSI = "AC" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de consulta
                String fileap_ANSI = "AP" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de procedimientos
                String fileau_ANSI = "AU" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de urgencias
                String fileah_ANSI = "AH" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de hospitalizacion
                String filean_ANSI = "AN" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de recien nacidos
                String fileam_ANSI = "AM" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de medicamentos
                String fileat_ANSI = "AT" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de otros servicios
                String filead_ANSI = "AD" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de usuarios de servicios de salud
          
                if (SihicApp.isWindows()) {
                    archivoct = new File(folder.getAbsolutePath() + "\\" + filect);
                    archivoaf = new File(folder.getAbsolutePath() + "\\" + fileaf);
                    archivous = new File(folder.getAbsolutePath() + "\\" + fileus);
                    archivoac = new File(folder.getAbsolutePath() + "\\" + fileac);
                    archivoap = new File(folder.getAbsolutePath() + "\\" + fileap);
                    archivoau = new File(folder.getAbsolutePath() + "\\" + fileau);
                    archivoah = new File(folder.getAbsolutePath() + "\\" + fileah);
                    archivoan = new File(folder.getAbsolutePath() + "\\" + filean);
                    archivoam = new File(folder.getAbsolutePath() + "\\" + fileam);
                    archivoat = new File(folder.getAbsolutePath() + "\\" + fileat);
                    archivoad = new File(folder.getAbsolutePath() + "\\" + filead);
                    
                    archivoct_ANSI = new File(folder.getAbsolutePath() + "\\" + filect_ANSI);
                    archivoaf_ANSI = new File(folder.getAbsolutePath() + "\\" + fileaf_ANSI);
                    archivous_ANSI = new File(folder.getAbsolutePath() + "\\" + fileus_ANSI);
                    archivoac_ANSI = new File(folder.getAbsolutePath() + "\\" + fileac_ANSI);
                    archivoap_ANSI = new File(folder.getAbsolutePath() + "\\" + fileap_ANSI);
                    archivoau_ANSI = new File(folder.getAbsolutePath() + "\\" + fileau_ANSI);
                    archivoah_ANSI = new File(folder.getAbsolutePath() + "\\" + fileah_ANSI);
                    archivoan_ANSI = new File(folder.getAbsolutePath() + "\\" + filean_ANSI);
                    archivoam_ANSI = new File(folder.getAbsolutePath() + "\\" + fileam_ANSI);
                    archivoat_ANSI = new File(folder.getAbsolutePath() + "\\" + fileat_ANSI);
                    archivoad_ANSI = new File(folder.getAbsolutePath() + "\\" + filead_ANSI);
                } 
                else {
                    archivoct = new File(folder.getAbsolutePath() + "//" + filect);
                    archivoaf = new File(folder.getAbsolutePath() + "//" + fileaf);
                    archivous = new File(folder.getAbsolutePath() + "//" + fileus);
                    archivoac = new File(folder.getAbsolutePath() + "//" + fileac);
                    archivoap = new File(folder.getAbsolutePath() + "//" + fileap);
                    archivoau = new File(folder.getAbsolutePath() + "//" + fileau);
                    archivoah = new File(folder.getAbsolutePath() + "//" + fileah);
                    archivoan = new File(folder.getAbsolutePath() + "//" + filean);
                    archivoam = new File(folder.getAbsolutePath() + "//" + fileam);
                    archivoat = new File(folder.getAbsolutePath() + "//" + fileat);
                    archivoad = new File(folder.getAbsolutePath() + "//" + filead);
                    
                      
                    archivoct_ANSI = new File(folder.getAbsolutePath() + "//" + filect_ANSI);
                    archivoaf_ANSI = new File(folder.getAbsolutePath() + "//" + fileaf_ANSI);
                    archivous_ANSI = new File(folder.getAbsolutePath() + "//" + fileus_ANSI);
                    archivoac_ANSI = new File(folder.getAbsolutePath() + "//" + fileac_ANSI);
                    archivoap_ANSI = new File(folder.getAbsolutePath() + "//" + fileap_ANSI);
                    archivoau_ANSI = new File(folder.getAbsolutePath() + "//" + fileau_ANSI);
                    archivoah_ANSI = new File(folder.getAbsolutePath() + "//" + fileah_ANSI);
                    archivoan_ANSI = new File(folder.getAbsolutePath() + "//" + filean_ANSI);
                    archivoam_ANSI = new File(folder.getAbsolutePath() + "//" + fileam_ANSI);
                    archivoat_ANSI = new File(folder.getAbsolutePath() + "//" + fileat_ANSI);
                    archivoad_ANSI = new File(folder.getAbsolutePath() + "//" + filead_ANSI);
                }
                // FileWriter fw = null;
                //fw=new FileWriter(archivo);
                FileWriter fichero = null;
                PrintWriter pw = null;
                PrintWriter pw_ANSI = null;
                Writer writer_ANSI=null;
                //BufferedWriter bw = new BufferedWriter(fw);
               
                // pw.println("Código Eapb,Código Pss,Número factura,Tipo documento,Número documento,Fecha procedimiento,Código procedimiento,ambito,Finalidad,Personal atiende,Dx principal,Dx relacionado,Complicación,Valor procedimiento");
                //if la factura es tipo A
                //COMENZAMOS A CONTAR TODOS LOS REGISTROS
                //total usuarios
                int totalusuarios = 0;
                int totalprocedimientos = 0;
                int totalmedicamentos = 0;
                int totalinsumos = 0;
                BigDecimal totalp = BigDecimal.ZERO;
                BigDecimal totalm = BigDecimal.ZERO;
                BigDecimal totalim = BigDecimal.ZERO;
                BigDecimal valorup = BigDecimal.ZERO;
                BigDecimal valorum = BigDecimal.ZERO;
                BigDecimal valoruim = BigDecimal.ZERO;
                Charset cs = Charset.forName("ASCII");
                if (_factura.getPrefijo().substring(0, 1).equals("A") || _factura.getPrefijo().substring(0, 1).equals("T") || _factura.getPrefijo().substring(0, 1).equals("P") || _factura.getPrefijo().substring(0, 1).equals("S")) {
                    //contamos el numero de usuarios atendidos
                    
                    for (FacturaItem fi : Li_facturasitems) {
                   
                          if(fi.getAgnCitas()!=null)
                          {    
                            if (hm_usuarios.get(fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento()) == null) 
                            {
                                hm_usuarios.put(fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento(), fi);
                                totalusuarios++;

                            }
                          }else
                          {
                             if (hm_usuarios.get(fi.getGenPacientes().getGenPersonas().getDocumento()) == null) 
                            {
                                hm_usuarios.put(fi.getGenPacientes().getGenPersonas().getDocumento(), fi);
                                totalusuarios++;

                            }  
                          }
                              if (fi.getProducto().getGenCategoriasProductos().getCodigo() == 1) 
                            {
                            totalprocedimientos++;
                            totalp = totalp.setScale(0, RoundingMode.HALF_UP).add(fi.getValor_total().setScale(0, RoundingMode.HALF_UP));
                            
                            }
                            if (fi.getProducto().getGenCategoriasProductos().getCodigo() == 2) 
                            {
                                    totalmedicamentos++;
                                totalm = totalm.setScale(0, RoundingMode.HALF_UP).add(fi.getValor_total().setScale(0, RoundingMode.HALF_UP));
                            } else {
                                if (fi.getProducto().getGenCategoriasProductos().getCodigo() == 3) 
                                {
                                    totalinsumos++;
                                    totalim = totalim.setScale(0, RoundingMode.HALF_UP).add(fi.getValor_total().setScale(0, RoundingMode.HALF_UP));
                                }
                            
                        }
                    }
                   
                    if (totalprocedimientos > 0) {
                        valorup = totalp.setScale(0, RoundingMode.HALF_UP).divide(new BigDecimal(totalprocedimientos), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                    }
                    if (totalmedicamentos > 0) {
                        valorum = totalm.setScale(0, RoundingMode.HALF_UP).divide(new BigDecimal(totalmedicamentos), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                    }
                    if (totalinsumos > 0) {
                        valoruim = totalim.setScale(0, RoundingMode.HALF_UP).divide(new BigDecimal(totalinsumos), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                    }
                                    
                    fichero = null;
                    pw = null;
                     fichero = new FileWriter(archivoaf);
                    pw_ANSI = null;
                    writer_ANSI=null;
                    writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoaf_ANSI), "Cp1252"));

                    String numerocontrato = "";
                    if (_factura.getGenEapb() != null) {
                        numerocontrato = _factura.getGenEapb().findlastconvenio() == null ? "" : _factura.getGenEapb().findlastconvenio().getNumerocontrato();
                    }
                    pw = new PrintWriter(fichero);
                    pw_ANSI = new PrintWriter(writer_ANSI);
                    if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0 || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 2) {
                        totalfacturas = _factura.getTotalAmount().setScale(0, RoundingMode.HALF_UP);
                    }
                    if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0 || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 2) {
                        if(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0)
                        {     
                          pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + _factura.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + UtilDate.fechainiciomes(_factura.getFacturaDate()) + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getCodigo() : "") + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getNombre() : "") + "," + numerocontrato + ",,," + _factura.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + totalfacturas.setScale(0, RoundingMode.HALF_UP).longValue());
                          pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + _factura.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + UtilDate.fechainiciomes(_factura.getFacturaDate()) + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getCodigo() : "") + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getNombre() : "") + "," + numerocontrato + ",,," + _factura.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + totalfacturas.setScale(0, RoundingMode.HALF_UP).longValue());
                    
                        }
                        else
                        {
                                   numerocontrato="";
                                   pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + _factura.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + UtilDate.fechainiciomes(_factura.getFacturaDate()) + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + _factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getCodigo() + "," + _factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getNombre() + "," + numerocontrato + ",,," + _factura.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + totalfacturas.setScale(0, RoundingMode.HALF_UP).longValue());
                                   pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + _factura.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + UtilDate.fechainiciomes(_factura.getFacturaDate()) + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + _factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getCodigo() + "," + _factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getNombre() + "," + numerocontrato + ",,," + _factura.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + totalfacturas.setScale(0, RoundingMode.HALF_UP).longValue());
                        }
                         totregaf++; 
                    }else {
                        for (Factura f : Li_facturas) {
                            f.calculartotalescopagocuotamoeras();
                            if (f.getGenEapb() != null) {
                                numerocontrato = f.getGenEapb().findlastconvenio() == null ? "" : f.getGenEapb().findlastconvenio().getNumerocontrato();
                               
                            }
                            pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + f.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(f.getFacturaDate()) + "," + UtilDate.fechainiciomes(f.getFacturaDate()) + "," + UtilDate.formatodiamesyear(f.getFacturaDate()) + "," + (f.getGenEapb() != null ? _factura.getGenEapb().getCodigo() : "") + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getNombre() : "") + "," + numerocontrato + ",,," + f.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + f.getTotalAmount().setScale(0, RoundingMode.HALF_UP).longValue());
                            pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + f.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(f.getFacturaDate()) + "," + UtilDate.fechainiciomes(f.getFacturaDate()) + "," + UtilDate.formatodiamesyear(f.getFacturaDate()) + "," + (f.getGenEapb() != null ? _factura.getGenEapb().getCodigo() : "") + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getNombre() : "") + "," + numerocontrato + ",,," + f.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + f.getTotalAmount().setScale(0, RoundingMode.HALF_UP).longValue());
                       
                            totregaf++; 
                        }

                    }

                    fichero.close();
                    fichero = null;
                    pw = null;
                    fichero = new FileWriter(archivoad);
                     pw = new PrintWriter(fichero);
                   //********************************
                     writer_ANSI.close();
                    writer_ANSI=null;
                    pw_ANSI = null;
                    writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoad_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                   //********************************************
                    if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0 || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 2) {
                        pw.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "01," + String.valueOf(totalprocedimientos) + "," + valorup.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + (valorup.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalprocedimientos))).setScale(0, RoundingMode.HALF_UP).longValue());
                        pw_ANSI.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "01," + String.valueOf(totalprocedimientos) + "," + valorup.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + (valorup.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalprocedimientos))).setScale(0, RoundingMode.HALF_UP).longValue());
                        pw.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "02," + String.valueOf(totalmedicamentos) + "," + valorum.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + (valorum.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalmedicamentos))).setScale(0, RoundingMode.HALF_UP).longValue());
                        pw_ANSI.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "02," + String.valueOf(totalmedicamentos) + "," + valorum.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + (valorum.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalmedicamentos))).setScale(0, RoundingMode.HALF_UP).longValue());
                        pw.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "03," + String.valueOf(totalinsumos) + "," + valoruim.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + valoruim.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalinsumos)).setScale(0, RoundingMode.HALF_UP).longValue());
                        pw_ANSI.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "03," + String.valueOf(totalinsumos) + "," + valoruim.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + valoruim.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalinsumos)).setScale(0, RoundingMode.HALF_UP).longValue());
                        
                        totregad=totregad+3;
                    } else 
                    {
                        int tusuarios = 0;
                        int tmedicamentos = 0;
                        int tinsumos = 0;
                        BigDecimal vusuarios = BigDecimal.ZERO;
                        BigDecimal vmedicamentos = BigDecimal.ZERO;
                        BigDecimal vinsumos = BigDecimal.ZERO;
                        BigDecimal tvusuarios = BigDecimal.ZERO;
                        BigDecimal tvmedicamentos = BigDecimal.ZERO;
                        BigDecimal tvinsumos = BigDecimal.ZERO;

                        for (Factura f : Li_facturas) {
                            vusuarios = BigDecimal.ZERO;
                            vmedicamentos = BigDecimal.ZERO;
                            vinsumos = BigDecimal.ZERO;
                            tvusuarios = BigDecimal.ZERO;
                            tvmedicamentos = BigDecimal.ZERO;
                            tvinsumos = BigDecimal.ZERO;
                            tinsumos = 0;
                            tmedicamentos = 0;
                            tusuarios = 0;
                            for (FacturaItem fi2 : f.getFacturaItems()) {
                                if (fi2.getProducto().getGenCategoriasProductos().getCodigo() == 1) {
                                    tusuarios++;
                                    tvusuarios = tvusuarios.setScale(0, RoundingMode.HALF_UP).add(fi2.getValor_total().setScale(0, RoundingMode.HALF_UP));
                                } else {
                                    if (fi2.getProducto().getGenCategoriasProductos().getCodigo() == 2) {
                                        tmedicamentos++;
                                        tvmedicamentos = tvmedicamentos.setScale(0, RoundingMode.HALF_UP).add(fi2.getValor_total().setScale(0, RoundingMode.HALF_UP));
                                    } else {
                                        if (fi2.getProducto().getGenCategoriasProductos().getCodigo() == 3) {
                                            tinsumos++;
                                            tvinsumos = tvinsumos.setScale(0, RoundingMode.HALF_UP).add(fi2.getValor_total().setScale(0, RoundingMode.HALF_UP));
                                        }
                                    }

                                }

                            }
                            try {

                                vusuarios = tvusuarios.divide(new BigDecimal(tusuarios), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                            } catch (Exception e) {
                                vusuarios = BigDecimal.ZERO;
                            }
                            try {
                                vmedicamentos = tvmedicamentos.divide(new BigDecimal(tmedicamentos), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                            } catch (Exception e) {
                                vmedicamentos = BigDecimal.ZERO;
                            }
                            try {
                                vinsumos = tvinsumos.divide(new BigDecimal(tinsumos), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                            } catch (Exception e) {
                                vinsumos = BigDecimal.ZERO;
                            }
                            pw.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "01," + String.valueOf(tusuarios) + "," + vusuarios.longValue() + "," + (vusuarios.longValue()*tusuarios));
                            pw_ANSI.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "01," + String.valueOf(tusuarios) + "," + vusuarios.longValue() + "," + (vusuarios.longValue()*tusuarios));
                          
                            totregad++;
                            pw.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "02," + String.valueOf(tmedicamentos) + "," + vmedicamentos.longValue() + "," + (vmedicamentos.longValue()*tmedicamentos));
                            pw_ANSI.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "02," + String.valueOf(tmedicamentos) + "," + vmedicamentos.longValue() + "," + (vmedicamentos.longValue()*tmedicamentos));
                         
                            totregad++;
                            pw.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "03," + String.valueOf(tinsumos) + "," + vinsumos.longValue() + "," + (vinsumos.longValue()*tinsumos));
                            pw_ANSI.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "03," + String.valueOf(tinsumos) + "," + vinsumos.longValue() + "," + (vinsumos.longValue()*tinsumos));
                      
                            totregad++;
                        }
                    }

                    fichero.close();
                    fichero = null;
                    pw = null;
                    fichero = new FileWriter(archivous);
                    pw = new PrintWriter(fichero);
                    
                    //************************
                     writer_ANSI.close();
                     writer_ANSI=null;
                     pw_ANSI = null;
                     writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivous_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                
                    hm_usuarios.clear();
                    for (FacturaItem fi : Li_facturasitems) {
                       if(fi.getAgnCitas()!=null)
                       {
                            if (hm_usuarios.get(fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento()) == null) {
                                hm_usuarios.put(fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento(), fi);
                                String zona = "";
                                if(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia()!=null)
                                {  
                                      if(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getCodigo()!=null)
                                {  
                                if (fi.getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getCodigo().trim().equals("UR")) {
                                    zona = "U";
                                } else {
                                    zona = "R";
                                }
                                }
                                 else
                                      {
                                          zona = "U";
                                      }
                                }
                                else
                                {
                                    zona = "U";
                                }
                                String td = "";
                                
                                if(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos()!=null)
                                {
                                    if(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla()!=null)
                                {
                                if (fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim().equals("TI")) {
                                    if (Integer.valueOf(fi.getAgnCitas().getGenPacientes().getGenPersonas().year()) <= 18) {
                                        td = "TI";
                                    } else {
                                        td = "CC";
                                    }
                                } else {

                                    td = fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim();
                                }
                                } else {

                                  td = "CC";
                                }
                            }
                                else
                                {
                                    td = "CC";
                                }
                                 if(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 2)
                                 {
                                     pw.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + fi.getAgnCitas().getGenPacientes().getAseguradora().getCodigo() + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                     pw_ANSI.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + fi.getAgnCitas().getGenPacientes().getAseguradora().getCodigo() + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                 }
                                else
                            {
                                try
                                {
                                pw.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getAgnCitas().getGenPacientes().getGenEapb() != null ? fi.getAgnCitas().getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                pw_ANSI.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getAgnCitas().getGenPacientes().getGenEapb() != null ? fi.getAgnCitas().getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                }catch(Exception e)
                                {
                                     pw.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getAgnCitas().getGenPacientes().getGenEapb() != null ? fi.getAgnCitas().getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," +(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo():"") + "," + (fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5):"") + "," + zona);
                                     pw_ANSI.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getAgnCitas().getGenPacientes().getGenEapb() != null ? fi.getAgnCitas().getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," +(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo():"") + "," + (fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5):"") + "," + zona);
                              
                                }
                            }
                            }
                        
                    }
                       else
                       {
                           if (hm_usuarios.get(fi.getGenPacientes().getGenPersonas().getDocumento()) == null) {
                                hm_usuarios.put(fi.getGenPacientes().getGenPersonas().getDocumento(), fi);
                                String zona = "";
                                if(fi.getGenPacientes().getGenPersonas().getGen_zona_residencia()!=null)
                                {  
                                      if(fi.getGenPacientes().getGenPersonas().getGen_zona_residencia().getCodigo()!=null)
                                {  
                                if (fi.getGenPacientes().getGenPersonas().getGen_zona_residencia().getCodigo().trim().equals("UR")) {
                                    zona = "U";
                                } else {
                                    zona = "R";
                                }
                                }
                                 else
                                      {
                                          zona = "U";
                                      }
                                }
                                else
                                {
                                    zona = "U";
                                }
                                String td = "";
                                
                                if(fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos()!=null)
                                {
                                    if(fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla()!=null)
                                {
                                if (fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim().equals("TI")) {
                                    if (Integer.valueOf(fi.getGenPacientes().getGenPersonas().year()) <= 18) {
                                        td = "TI";
                                    } else {
                                        td = "CC";
                                    }
                                } else {

                                    td = fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim();
                                }
                                } else {

                                  td = "CC";
                                }
                            }
                                else
                                {
                                    td = "CC";
                                }
                                 if(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 2)
                                 {
                                     pw.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + fi.getGenPacientes().getAseguradora().getCodigo() + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                     pw_ANSI.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + fi.getGenPacientes().getAseguradora().getCodigo() + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                          
                                 }
                                else
                            {
                                try
                                {
                                pw.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getGenPacientes().getGenEapb() != null ? fi.getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                pw_ANSI.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getGenPacientes().getGenEapb() != null ? fi.getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                             
                                }catch(Exception e)
                                {
                                     pw.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getGenPacientes().getGenEapb() != null ? fi.getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," +(fi.getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo():"") + "," + (fi.getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5):"") + "," + zona);
                                     pw_ANSI.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getGenPacientes().getGenEapb() != null ? fi.getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," +(fi.getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo():"") + "," + (fi.getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5):"") + "," + zona);
                             
                                }
                            }
                            }
                       }
                    }
                    fichero.close();
                    fichero = null;
                    pw = null;
                    fichero = new FileWriter(archivoap);
                    pw = new PrintWriter(fichero);
                    //*********************************************************************
                    writer_ANSI.close();
                    writer_ANSI=null;
                    pw_ANSI = null;
                    writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoap_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                
                    BigDecimal total = BigDecimal.ZERO;
                    for (FacturaItem fi : Li_facturasitems) {
                        if (fi.getProducto().getGenCategoriasProductos().getCodigo() == 1) {
                            String codigocups = "";
                           /* if(fi.getProducto().getHclCups()==null)
                            {
                               codigocups = fi.getProducto().getCodigo(); 
                            }    
                            else
                            {
                            if (fi.getProducto().getHclCups().getCodigo() == null) {*/
                                codigocups = fi.getProducto().getCodigo();
                      /*    } else {
                                codigocups = fi.getProducto().getHclCups().getCodigo();
                            }
                            }*/
                            String noautorizacion=fi.getNoautorizacion();
                            if(noautorizacion==null || noautorizacion.equals(""))
                            {
                                noautorizacion="";
                            }
                            try
                            {
                            pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                            pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                        
                            total = total.add(fi.getValor_total());
                            }catch(Exception e)
                            {try
                            {
                             pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "CC" + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                             pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "CC" + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                         
                            }catch(Exception e2)
                            {
                                    pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "CC" + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getHclConsultas().getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                                    pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "CC" + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getHclConsultas().getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                          
                            }
                             System.out.println("->1");
                            }
                        }
                    }
                    
                    fichero.close();
                    pw = null;
                    fichero=null;
                    fichero = new FileWriter(archivoam);
                    pw = new PrintWriter(fichero);
                   //************************************************* 
                     writer_ANSI.close();
                     writer_ANSI=null;
                     pw_ANSI = null;
                     writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoam_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                
                    String formasfarmaceuticas = "";
                    String unidamedida = "";
                    String concentracion = "";
                    total = BigDecimal.ZERO;
                    for (FacturaItem fi : Li_facturasitems) {
                        if (fi.getProducto().getHclCups() == null && fi.getProducto().getGenCategoriasProductos().getCodigo() == 2) {
                            if (fi.getProducto().getFormafarmaceutica() != null) {
                                formasfarmaceuticas = fi.getProducto().getFormafarmaceutica();
                            }
                            if (fi.getProducto().getUnidadmedidamedicamento() != null) {
                                unidamedida = fi.getProducto().getUnidadmedidamedicamento();
                            }
                            if (fi.getProducto().getConcentracionmedicamento() != null) {
                                concentracion = fi.getProducto().getConcentracionmedicamento();
                            }
                            try
                            {
                            pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",," + fi.getProducto().getCodigo() + ",1," + fi.getProducto().getNombre() + "," + formasfarmaceuticas.toUpperCase() + "," + concentracion.toUpperCase() + "," + unidamedida.toUpperCase() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                            pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",," + fi.getProducto().getCodigo() + ",1," + fi.getProducto().getNombre() + "," + formasfarmaceuticas.toUpperCase() + "," + concentracion.toUpperCase() + "," + unidamedida.toUpperCase() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                         
                            }catch(Exception e)
                            {
                             pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",," + fi.getProducto().getCodigo() + ",1," + fi.getProducto().getNombre() + "," + formasfarmaceuticas.toUpperCase() + "," + concentracion.toUpperCase() + "," + unidamedida.toUpperCase() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());   
                             pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",," + fi.getProducto().getCodigo() + ",1," + fi.getProducto().getNombre() + "," + formasfarmaceuticas.toUpperCase() + "," + concentracion.toUpperCase() + "," + unidamedida.toUpperCase() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());   
                         
                            }
                            total = total.setScale(0, RoundingMode.HALF_UP).add(fi.getValor_total().setScale(0, RoundingMode.HALF_UP));
                        }
                    }
                    
                    fichero.close();
                    pw = null;
                    fichero=null;
                    fichero = new FileWriter(archivoat);
                    pw = new PrintWriter(fichero);
                    //***************************************************
                    writer_ANSI.close();
                     writer_ANSI=null;
                     pw_ANSI = null;
                     writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoat_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                
                    total = BigDecimal.ZERO;
                    for (FacturaItem fi : Li_facturasitems) {
                        if (fi.getProducto().getHclCups() == null && fi.getProducto().getGenCategoriasProductos().getCodigo() == 3) {
                           try
                           {
                            pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",,1," + fi.getProducto().getCodigo() + "," + fi.getProducto().getNombre() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                            pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",,1," + fi.getProducto().getCodigo() + "," + fi.getProducto().getNombre() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                     
                           }catch(Exception e)
                           {
                                 pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",,1," + fi.getProducto().getCodigo() + "," + fi.getProducto().getNombre() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                                 pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",,1," + fi.getProducto().getCodigo() + "," + fi.getProducto().getNombre() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                        
                           }
                            total = total.setScale(0, RoundingMode.HALF_UP).add(fi.getValor_total().setScale(0, RoundingMode.HALF_UP));
                        }

                    }
                    fichero.close();
                    fichero=null;
                     pw = null;
                    fichero = new FileWriter(archivoct);
                    pw = new PrintWriter(fichero);
                     //***************************************************
                    writer_ANSI.close();
                     writer_ANSI=null;
                     pw_ANSI = null;
                     writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoct_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileaf.replace(".TXT", "") + ","+totregaf);
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileus.replace(".TXT", "") + "," + String.valueOf(totalusuarios));
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + filead.replace(".TXT", "") + "," + totregad);
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileap.replace(".TXT", "") + "," + String.valueOf(totalprocedimientos));
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileam.replace(".TXT", "") + "," + String.valueOf(totalmedicamentos));
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileat.replace(".TXT", "") + "," + String.valueOf(totalinsumos));

                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileaf.replace(".TXT", "") + ","+totregaf);
                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileus.replace(".TXT", "") + "," + String.valueOf(totalusuarios));
                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + filead.replace(".TXT", "") + "," + totregad);
                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileap.replace(".TXT", "") + "," + String.valueOf(totalprocedimientos));
                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileam.replace(".TXT", "") + "," + String.valueOf(totalmedicamentos));
                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileat.replace(".TXT", "") + "," + String.valueOf(totalinsumos));

                    fichero.close();
                     writer_ANSI.close();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setInitialDirectory(folder);
                    fileChooser.setTitle("Archivos Rips");

                    // Agregar filtros para facilitar la busqueda
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.TXT")
                    );

                    fileChooser.showOpenDialog(new Stage());
                }

            }
        }
    }
 public void exportartxt_cuentasmedicas() throws IOException, ParseException {
        int totregaf=0;
        int totregad=0;
        SihicApp.consecutivosContabilidadControllerClient.findlastconsecutivorips();
        Factura _factura = new Factura();
        //solo para factura tipo P
        BigDecimal totalfacturas = BigDecimal.ZERO;
        for (Factura f : Li_facturas) {
            totalfacturas = totalfacturas.setScale(0, RoundingMode.HALF_UP).add(f.getTotalAmount().setScale(0, RoundingMode.HALF_UP));
        }
        if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("A") || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0, 1).equals("T")) {
            _factura = SihicApp.s_factura;
            Li_facturasitems = SihicApp.s_factura.getFacturaItems();
        } else {
            if (Li_facturasitems != null) {
                if (Li_facturasitems.size() > 0) {
                    _factura = Li_facturasitems.get(0).getFactura();
                }
            }
        }
        if (_factura != null) {
          
//DirectoryChooser dirDialog = new DirectoryChooser();

// Configure the properties
//dirDialog.setTitle("Seleccione un directorio de destino");
            File folder;
            if (SihicApp.isWindows()) {
                folder = new File("\\home\\adminlinux\\rips\\" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda());
            } else {
                folder = new File("/home/adminlinux/rips/" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda());
            }
            if (!folder.exists()) {
                folder.mkdir();
            }

            //dirDialog.setInitialDirectory(folder);
            // File dir = dirDialog.showDialog(null);
            if (folder != null) {
               
                File archivoct;
                File archivoaf;
                File archivous;
                File archivoac;
                File archivoap;
                File archivoau;
                File archivoah;
                File archivoan;
                File archivoam;
                File archivoat;
                File archivoad;
                
                 File archivoct_ANSI;
                File archivoaf_ANSI;
                File archivous_ANSI;
                File archivoac_ANSI;
                File archivoap_ANSI;
                File archivoau_ANSI;
                File archivoah_ANSI;
                File archivoan_ANSI;
                File archivoam_ANSI;
                File archivoat_ANSI;
                File archivoad_ANSI;
                String filect = "CT" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda()+ ".TXT";//archivo de control
                String fileaf = "AF" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda()+ ".TXT";//archivo de transacciones
                String fileus = "US" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de usuarios de servicios de salud
                String fileac = "AC" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de consulta
                String fileap = "AP" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de procedimientos
                String fileau = "AU" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de urgencias
                String fileah = "AH" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de hospitalizacion
                String filean = "AN" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de recien nacidos
                String fileam = "AM" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de medicamentos
                String fileat = "AT" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de otros servicios
                String filead = "AD" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + ".TXT";//archivo de usuarios de servicios de salud
                
                String filect_ANSI = "CT" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda()+ "_ANSI.TXT";//archivo de control
                String fileaf_ANSI = "AF" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda()+ "_ANSI.TXT";//archivo de transacciones
                String fileus_ANSI = "US" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de usuarios de servicios de salud
                String fileac_ANSI = "AC" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de consulta
                String fileap_ANSI = "AP" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de procedimientos
                String fileau_ANSI = "AU" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de urgencias
                String fileah_ANSI = "AH" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de hospitalizacion
                String filean_ANSI = "AN" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de recien nacidos
                String fileam_ANSI = "AM" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de medicamentos
                String fileat_ANSI = "AT" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de otros servicios
                String filead_ANSI = "AD" + SihicApp.consecutivosContabilidad.getconsecutivoripscerosizquierda() + "_ANSI.TXT";//archivo de usuarios de servicios de salud
          
                if (SihicApp.isWindows()) {
                    archivoct = new File(folder.getAbsolutePath() + "\\" + filect);
                    archivoaf = new File(folder.getAbsolutePath() + "\\" + fileaf);
                    archivous = new File(folder.getAbsolutePath() + "\\" + fileus);
                    archivoac = new File(folder.getAbsolutePath() + "\\" + fileac);
                    archivoap = new File(folder.getAbsolutePath() + "\\" + fileap);
                    archivoau = new File(folder.getAbsolutePath() + "\\" + fileau);
                    archivoah = new File(folder.getAbsolutePath() + "\\" + fileah);
                    archivoan = new File(folder.getAbsolutePath() + "\\" + filean);
                    archivoam = new File(folder.getAbsolutePath() + "\\" + fileam);
                    archivoat = new File(folder.getAbsolutePath() + "\\" + fileat);
                    archivoad = new File(folder.getAbsolutePath() + "\\" + filead);
                    
                    archivoct_ANSI = new File(folder.getAbsolutePath() + "\\" + filect_ANSI);
                    archivoaf_ANSI = new File(folder.getAbsolutePath() + "\\" + fileaf_ANSI);
                    archivous_ANSI = new File(folder.getAbsolutePath() + "\\" + fileus_ANSI);
                    archivoac_ANSI = new File(folder.getAbsolutePath() + "\\" + fileac_ANSI);
                    archivoap_ANSI = new File(folder.getAbsolutePath() + "\\" + fileap_ANSI);
                    archivoau_ANSI = new File(folder.getAbsolutePath() + "\\" + fileau_ANSI);
                    archivoah_ANSI = new File(folder.getAbsolutePath() + "\\" + fileah_ANSI);
                    archivoan_ANSI = new File(folder.getAbsolutePath() + "\\" + filean_ANSI);
                    archivoam_ANSI = new File(folder.getAbsolutePath() + "\\" + fileam_ANSI);
                    archivoat_ANSI = new File(folder.getAbsolutePath() + "\\" + fileat_ANSI);
                    archivoad_ANSI = new File(folder.getAbsolutePath() + "\\" + filead_ANSI);
                } 
                else {
                    archivoct = new File(folder.getAbsolutePath() + "//" + filect);
                    archivoaf = new File(folder.getAbsolutePath() + "//" + fileaf);
                    archivous = new File(folder.getAbsolutePath() + "//" + fileus);
                    archivoac = new File(folder.getAbsolutePath() + "//" + fileac);
                    archivoap = new File(folder.getAbsolutePath() + "//" + fileap);
                    archivoau = new File(folder.getAbsolutePath() + "//" + fileau);
                    archivoah = new File(folder.getAbsolutePath() + "//" + fileah);
                    archivoan = new File(folder.getAbsolutePath() + "//" + filean);
                    archivoam = new File(folder.getAbsolutePath() + "//" + fileam);
                    archivoat = new File(folder.getAbsolutePath() + "//" + fileat);
                    archivoad = new File(folder.getAbsolutePath() + "//" + filead);
                    
                      
                    archivoct_ANSI = new File(folder.getAbsolutePath() + "//" + filect_ANSI);
                    archivoaf_ANSI = new File(folder.getAbsolutePath() + "//" + fileaf_ANSI);
                    archivous_ANSI = new File(folder.getAbsolutePath() + "//" + fileus_ANSI);
                    archivoac_ANSI = new File(folder.getAbsolutePath() + "//" + fileac_ANSI);
                    archivoap_ANSI = new File(folder.getAbsolutePath() + "//" + fileap_ANSI);
                    archivoau_ANSI = new File(folder.getAbsolutePath() + "//" + fileau_ANSI);
                    archivoah_ANSI = new File(folder.getAbsolutePath() + "//" + fileah_ANSI);
                    archivoan_ANSI = new File(folder.getAbsolutePath() + "//" + filean_ANSI);
                    archivoam_ANSI = new File(folder.getAbsolutePath() + "//" + fileam_ANSI);
                    archivoat_ANSI = new File(folder.getAbsolutePath() + "//" + fileat_ANSI);
                    archivoad_ANSI = new File(folder.getAbsolutePath() + "//" + filead_ANSI);
                }
                // FileWriter fw = null;
                //fw=new FileWriter(archivo);
                FileWriter fichero = null;
                PrintWriter pw = null;
                PrintWriter pw_ANSI = null;
                Writer writer_ANSI=null;
                //BufferedWriter bw = new BufferedWriter(fw);
               
                // pw.println("Código Eapb,Código Pss,Número factura,Tipo documento,Número documento,Fecha procedimiento,Código procedimiento,ambito,Finalidad,Personal atiende,Dx principal,Dx relacionado,Complicación,Valor procedimiento");
                //if la factura es tipo A
                //COMENZAMOS A CONTAR TODOS LOS REGISTROS
                //total usuarios
                int totalusuarios = 0;
                int totalprocedimientos = 0;
                int totalmedicamentos = 0;
                int totalinsumos = 0;
                BigDecimal totalp = BigDecimal.ZERO;
                BigDecimal totalm = BigDecimal.ZERO;
                BigDecimal totalim = BigDecimal.ZERO;
                BigDecimal valorup = BigDecimal.ZERO;
                BigDecimal valorum = BigDecimal.ZERO;
                BigDecimal valoruim = BigDecimal.ZERO;
                Charset cs = Charset.forName("ASCII");
                if (_factura.getPrefijo().substring(0, 1).equals("A") || _factura.getPrefijo().substring(0, 1).equals("T") || _factura.getPrefijo().substring(0, 1).equals("P") || _factura.getPrefijo().substring(0, 1).equals("S")) {
                    //contamos el numero de usuarios atendidos
                    
                    for (FacturaItem fi : Li_facturasitems) {
                   
                          if(fi.getAgnCitas()!=null)
                          {    
                            if (hm_usuarios.get(fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento()) == null) 
                            {
                                hm_usuarios.put(fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento(), fi);
                                totalusuarios++;

                            }
                          }else
                          {
                             if (hm_usuarios.get(fi.getGenPacientes().getGenPersonas().getDocumento()) == null) 
                            {
                                hm_usuarios.put(fi.getGenPacientes().getGenPersonas().getDocumento(), fi);
                                totalusuarios++;

                            }  
                          }
                              if (fi.getProducto().getGenCategoriasProductos().getCodigo() == 1) 
                            {
                            totalprocedimientos++;
                            totalp = totalp.setScale(0, RoundingMode.HALF_UP).add(fi.getValor_total().setScale(0, RoundingMode.HALF_UP));
                            
                            }
                            if (fi.getProducto().getGenCategoriasProductos().getCodigo() == 2) 
                            {
                                    totalmedicamentos++;
                                totalm = totalm.setScale(0, RoundingMode.HALF_UP).add(fi.getValor_total().setScale(0, RoundingMode.HALF_UP));
                            } else {
                                if (fi.getProducto().getGenCategoriasProductos().getCodigo() == 3) 
                                {
                                    totalinsumos++;
                                    totalim = totalim.setScale(0, RoundingMode.HALF_UP).add(fi.getValor_total().setScale(0, RoundingMode.HALF_UP));
                                }
                            
                        }
                    }
                   
                    if (totalprocedimientos > 0) {
                        valorup = totalp.setScale(0, RoundingMode.HALF_UP).divide(new BigDecimal(totalprocedimientos), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                    }
                    if (totalmedicamentos > 0) {
                        valorum = totalm.setScale(0, RoundingMode.HALF_UP).divide(new BigDecimal(totalmedicamentos), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                    }
                    if (totalinsumos > 0) {
                        valoruim = totalim.setScale(0, RoundingMode.HALF_UP).divide(new BigDecimal(totalinsumos), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                    }
                                    
                    fichero = null;
                    pw = null;
                     fichero = new FileWriter(archivoaf);
                    pw_ANSI = null;
                    writer_ANSI=null;
                    writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoaf_ANSI), "Cp1252"));

                    String numerocontrato = "";
                    if (_factura.getGenEapb() != null) {
                        numerocontrato = _factura.getGenEapb().findlastconvenio() == null ? "" : _factura.getGenEapb().findlastconvenio().getNumerocontrato();
                    }
                    pw = new PrintWriter(fichero);
                    pw_ANSI = new PrintWriter(writer_ANSI);
                    if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0 || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 2) {
                        totalfacturas = _factura.getTotalAmount().setScale(0, RoundingMode.HALF_UP);
                    }
                    if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0 || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 2) {
                        if(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0)
                        {     
                          pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + _factura.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + UtilDate.fechainiciomes(_factura.getFacturaDate()) + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getCodigo() : "") + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getNombre() : "") + "," + numerocontrato + ",,," + _factura.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + totalfacturas.setScale(0, RoundingMode.HALF_UP).longValue());
                          pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + _factura.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + UtilDate.fechainiciomes(_factura.getFacturaDate()) + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getCodigo() : "") + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getNombre() : "") + "," + numerocontrato + ",,," + _factura.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + totalfacturas.setScale(0, RoundingMode.HALF_UP).longValue());
                    
                        }
                        else
                        {
                                   numerocontrato="";
                                   pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + _factura.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + UtilDate.fechainiciomes(_factura.getFacturaDate()) + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + _factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getCodigo() + "," + _factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getNombre() + "," + numerocontrato + ",,," + _factura.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + totalfacturas.setScale(0, RoundingMode.HALF_UP).longValue());
                                   pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + _factura.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + UtilDate.fechainiciomes(_factura.getFacturaDate()) + "," + UtilDate.formatodiamesyear(_factura.getFacturaDate()) + "," + _factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getCodigo() + "," + _factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getNombre() + "," + numerocontrato + ",,," + _factura.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + totalfacturas.setScale(0, RoundingMode.HALF_UP).longValue());
                        }
                         totregaf++; 
                    }else {
                        for (Factura f : Li_facturas) {
                            f.calculartotalescopagocuotamoeras();
                            if (f.getGenEapb() != null) {
                                numerocontrato = f.getGenEapb().findlastconvenio() == null ? "" : f.getGenEapb().findlastconvenio().getNumerocontrato();
                               
                            }
                            pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + f.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(f.getFacturaDate()) + "," + UtilDate.fechainiciomes(f.getFacturaDate()) + "," + UtilDate.formatodiamesyear(f.getFacturaDate()) + "," + (f.getGenEapb() != null ? _factura.getGenEapb().getCodigo() : "") + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getNombre() : "") + "," + numerocontrato + ",,," + f.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + f.getTotalAmount().setScale(0, RoundingMode.HALF_UP).longValue());
                            pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + SihicApp.genUnidadesOrganizacion.getNombre().toUpperCase() + ",NI," + SihicApp.genUnidadesOrganizacion.getNit().substring(0, 9) + "," + f.getNofacturacerosizquierda() + "," + UtilDate.formatodiamesyear(f.getFacturaDate()) + "," + UtilDate.fechainiciomes(f.getFacturaDate()) + "," + UtilDate.formatodiamesyear(f.getFacturaDate()) + "," + (f.getGenEapb() != null ? _factura.getGenEapb().getCodigo() : "") + "," + (_factura.getGenEapb() != null ? _factura.getGenEapb().getNombre() : "") + "," + numerocontrato + ",,," + f.getTotalcopagos().setScale(0, RoundingMode.HALF_UP).longValue() + ",0,0," + f.getTotalAmount().setScale(0, RoundingMode.HALF_UP).longValue());
                       
                            totregaf++; 
                        }

                    }

                    fichero.close();
                    fichero = null;
                    pw = null;
                    fichero = new FileWriter(archivoad);
                     pw = new PrintWriter(fichero);
                   //********************************
                     writer_ANSI.close();
                    writer_ANSI=null;
                    pw_ANSI = null;
                    writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoad_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                   //********************************************
                    if (LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 0 || LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 2) {
                        pw.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "01," + String.valueOf(totalprocedimientos) + "," + valorup.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + (valorup.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalprocedimientos))).setScale(0, RoundingMode.HALF_UP).longValue());
                        pw_ANSI.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "01," + String.valueOf(totalprocedimientos) + "," + valorup.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + (valorup.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalprocedimientos))).setScale(0, RoundingMode.HALF_UP).longValue());
                        pw.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "02," + String.valueOf(totalmedicamentos) + "," + valorum.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + (valorum.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalmedicamentos))).setScale(0, RoundingMode.HALF_UP).longValue());
                        pw_ANSI.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "02," + String.valueOf(totalmedicamentos) + "," + valorum.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + (valorum.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalmedicamentos))).setScale(0, RoundingMode.HALF_UP).longValue());
                        pw.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "03," + String.valueOf(totalinsumos) + "," + valoruim.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + valoruim.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalinsumos)).setScale(0, RoundingMode.HALF_UP).longValue());
                        pw_ANSI.println(_factura.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "03," + String.valueOf(totalinsumos) + "," + valoruim.setScale(0, RoundingMode.HALF_UP).longValue()+ "," + valoruim.setScale(0, RoundingMode.HALF_UP).multiply(new BigDecimal(totalinsumos)).setScale(0, RoundingMode.HALF_UP).longValue());
                        
                        totregad=totregad+3;
                    } else 
                    {
                        int tusuarios = 0;
                        int tmedicamentos = 0;
                        int tinsumos = 0;
                        BigDecimal vusuarios = BigDecimal.ZERO;
                        BigDecimal vmedicamentos = BigDecimal.ZERO;
                        BigDecimal vinsumos = BigDecimal.ZERO;
                        BigDecimal tvusuarios = BigDecimal.ZERO;
                        BigDecimal tvmedicamentos = BigDecimal.ZERO;
                        BigDecimal tvinsumos = BigDecimal.ZERO;

                        for (Factura f : Li_facturas) {
                            vusuarios = BigDecimal.ZERO;
                            vmedicamentos = BigDecimal.ZERO;
                            vinsumos = BigDecimal.ZERO;
                            tvusuarios = BigDecimal.ZERO;
                            tvmedicamentos = BigDecimal.ZERO;
                            tvinsumos = BigDecimal.ZERO;
                            tinsumos = 0;
                            tmedicamentos = 0;
                            tusuarios = 0;
                            for (FacturaItem fi2 : f.getFacturaItems()) {
                                if (fi2.getProducto().getGenCategoriasProductos().getCodigo() == 1) {
                                    tusuarios++;
                                    tvusuarios = tvusuarios.setScale(0, RoundingMode.HALF_UP).add(fi2.getValor_total().setScale(0, RoundingMode.HALF_UP));
                                } else {
                                    if (fi2.getProducto().getGenCategoriasProductos().getCodigo() == 2) {
                                        tmedicamentos++;
                                        tvmedicamentos = tvmedicamentos.setScale(0, RoundingMode.HALF_UP).add(fi2.getValor_total().setScale(0, RoundingMode.HALF_UP));
                                    } else {
                                        if (fi2.getProducto().getGenCategoriasProductos().getCodigo() == 3) {
                                            tinsumos++;
                                            tvinsumos = tvinsumos.setScale(0, RoundingMode.HALF_UP).add(fi2.getValor_total().setScale(0, RoundingMode.HALF_UP));
                                        }
                                    }

                                }

                            }
                            try {

                                vusuarios = tvusuarios.divide(new BigDecimal(tusuarios), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                            } catch (Exception e) {
                                vusuarios = BigDecimal.ZERO;
                            }
                            try {
                                vmedicamentos = tvmedicamentos.divide(new BigDecimal(tmedicamentos), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                            } catch (Exception e) {
                                vmedicamentos = BigDecimal.ZERO;
                            }
                            try {
                                vinsumos = tvinsumos.divide(new BigDecimal(tinsumos), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
                            } catch (Exception e) {
                                vinsumos = BigDecimal.ZERO;
                            }
                            pw.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "01," + String.valueOf(tusuarios) + "," + vusuarios.longValue() + "," + (vusuarios.longValue()*tusuarios));
                            pw_ANSI.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "01," + String.valueOf(tusuarios) + "," + vusuarios.longValue() + "," + (vusuarios.longValue()*tusuarios));
                          
                            totregad++;
                            pw.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "02," + String.valueOf(tmedicamentos) + "," + vmedicamentos.longValue() + "," + (vmedicamentos.longValue()*tmedicamentos));
                            pw_ANSI.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "02," + String.valueOf(tmedicamentos) + "," + vmedicamentos.longValue() + "," + (vmedicamentos.longValue()*tmedicamentos));
                         
                            totregad++;
                            pw.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "03," + String.valueOf(tinsumos) + "," + vinsumos.longValue() + "," + (vinsumos.longValue()*tinsumos));
                            pw_ANSI.println(f.getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "03," + String.valueOf(tinsumos) + "," + vinsumos.longValue() + "," + (vinsumos.longValue()*tinsumos));
                      
                            totregad++;
                        }
                    }

                    fichero.close();
                    fichero = null;
                    pw = null;
                    fichero = new FileWriter(archivous);
                    pw = new PrintWriter(fichero);
                    
                    //************************
                     writer_ANSI.close();
                     writer_ANSI=null;
                     pw_ANSI = null;
                     writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivous_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                
                    hm_usuarios.clear();
                    for (FacturaItem fi : Li_facturasitems) {
                       if(fi.getAgnCitas()!=null)
                       {
                            if (hm_usuarios.get(fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento()) == null) {
                                hm_usuarios.put(fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento(), fi);
                                String zona = "";
                                if(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia()!=null)
                                {  
                                      if(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getCodigo()!=null)
                                {  
                                if (fi.getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getCodigo().trim().equals("UR")) {
                                    zona = "U";
                                } else {
                                    zona = "R";
                                }
                                }
                                 else
                                      {
                                          zona = "U";
                                      }
                                }
                                else
                                {
                                    zona = "U";
                                }
                                String td = "";
                                
                                if(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos()!=null)
                                {
                                    if(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla()!=null)
                                {
                                if (fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim().equals("TI")) {
                                    if (Integer.valueOf(fi.getAgnCitas().getGenPacientes().getGenPersonas().year()) <= 18) {
                                        td = "TI";
                                    } else {
                                        td = "CC";
                                    }
                                } else {

                                    td = fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim();
                                }
                                } else {

                                  td = "CC";
                                }
                            }
                                else
                                {
                                    td = "CC";
                                }
                                 if(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 2)
                                 {
                                     pw.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + fi.getAgnCitas().getGenPacientes().getAseguradora().getCodigo() + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                     pw_ANSI.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + fi.getAgnCitas().getGenPacientes().getAseguradora().getCodigo() + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                 }
                                else
                            {
                                try
                                {
                                pw.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getAgnCitas().getGenPacientes().getGenEapb() != null ? fi.getAgnCitas().getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                pw_ANSI.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getAgnCitas().getGenPacientes().getGenEapb() != null ? fi.getAgnCitas().getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                }catch(Exception e)
                                {
                                     pw.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getAgnCitas().getGenPacientes().getGenEapb() != null ? fi.getAgnCitas().getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," +(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo():"") + "," + (fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5):"") + "," + zona);
                                     pw_ANSI.println(td + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getAgnCitas().getGenPacientes().getGenEapb() != null ? fi.getAgnCitas().getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios() != null ? fi.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().year() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," +(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo():"") + "," + (fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5):"") + "," + zona);
                              
                                }
                            }
                            }
                        
                    }
                       else
                       {
                           if (hm_usuarios.get(fi.getGenPacientes().getGenPersonas().getDocumento()) == null) {
                                hm_usuarios.put(fi.getGenPacientes().getGenPersonas().getDocumento(), fi);
                                String zona = "";
                                if(fi.getGenPacientes().getGenPersonas().getGen_zona_residencia()!=null)
                                {  
                                      if(fi.getGenPacientes().getGenPersonas().getGen_zona_residencia().getCodigo()!=null)
                                {  
                                if (fi.getGenPacientes().getGenPersonas().getGen_zona_residencia().getCodigo().trim().equals("UR")) {
                                    zona = "U";
                                } else {
                                    zona = "R";
                                }
                                }
                                 else
                                      {
                                          zona = "U";
                                      }
                                }
                                else
                                {
                                    zona = "U";
                                }
                                String td = "";
                                
                                if(fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos()!=null)
                                {
                                    if(fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla()!=null)
                                {
                                if (fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim().equals("TI")) {
                                    if (Integer.valueOf(fi.getGenPacientes().getGenPersonas().year()) <= 18) {
                                        td = "TI";
                                    } else {
                                        td = "CC";
                                    }
                                } else {

                                    td = fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim();
                                }
                                } else {

                                  td = "CC";
                                }
                            }
                                else
                                {
                                    td = "CC";
                                }
                                 if(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().getSelectedIndex() == 2)
                                 {
                                     pw.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + fi.getGenPacientes().getAseguradora().getCodigo() + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                     pw_ANSI.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + fi.getGenPacientes().getAseguradora().getCodigo() + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                          
                                 }
                                else
                            {
                                try
                                {
                                pw.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getGenPacientes().getGenEapb() != null ? fi.getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                                pw_ANSI.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getGenPacientes().getGenEapb() != null ? fi.getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo() + "," + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5) + "," + zona);
                             
                                }catch(Exception e)
                                {
                                     pw.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getGenPacientes().getGenEapb() != null ? fi.getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," +(fi.getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo():"") + "," + (fi.getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5):"") + "," + zona);
                                     pw_ANSI.println(td + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + (fi.getGenPacientes().getGenEapb() != null ? fi.getGenPacientes().getGenEapb().getCodigo() : "") + "," + (fi.getGenPacientes().getGenTiposUsuarios() != null ? fi.getGenPacientes().getGenTiposUsuarios().getCodigo() : "") + "," + fi.getGenPacientes().getGenPersonas().getPrimerApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoApellido().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getPrimerNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().getSegundoNombre().toUpperCase() + "," + fi.getGenPacientes().getGenPersonas().year() + "," + fi.getGenPacientes().getGenPersonas().unidad_medida_edad() + "," + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + "," +(fi.getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo():"") + "," + (fi.getGenPacientes().getGenPersonas().getGenMunicipios()!=null?fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5):"") + "," + zona);
                             
                                }
                            }
                            }
                       }
                    }
                    fichero.close();
                    fichero = null;
                    pw = null;
                    fichero = new FileWriter(archivoap);
                    pw = new PrintWriter(fichero);
                    //*********************************************************************
                    writer_ANSI.close();
                    writer_ANSI=null;
                    pw_ANSI = null;
                    writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoap_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                
                    BigDecimal total = BigDecimal.ZERO;
                    for (FacturaItem fi : Li_facturasitems) {
                        if (fi.getProducto().getGenCategoriasProductos().getCodigo() == 1) {
                            String codigocups = "";
                           /* if(fi.getProducto().getHclCups()==null)
                            {
                               codigocups = fi.getProducto().getCodigo(); 
                            }    
                            else
                            {
                            if (fi.getProducto().getHclCups().getCodigo() == null) {*/
                                codigocups = fi.getProducto().getCodigo();
                      /*    } else {
                                codigocups = fi.getProducto().getHclCups().getCodigo();
                            }
                            }*/
                            String noautorizacion=fi.getNoautorizacion();
                            if(noautorizacion==null || noautorizacion.equals(""))
                            {
                                noautorizacion="";
                            }
                            try
                            {
                            pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                            pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().trim().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                        
                            total = total.add(fi.getValor_total());
                            }catch(Exception e)
                            {try
                            {
                             pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "CC" + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                             pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "CC" + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                         
                            }catch(Exception e2)
                            {
                                    pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "CC" + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getHclConsultas().getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                                    pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + "CC" + "," + fi.getGenPacientes().getGenPersonas().getDocumento().trim() + "," + UtilDate.formatodiamesyear(fi.getHclConsultas().getAgnCitas().getFechaHora()) + ","+noautorizacion+"," + codigocups + ",1,1,,,,,," + fi.getValor_total().longValue());
                          
                            }
                             System.out.println("->1");
                            }
                        }
                    }
                    
                    fichero.close();
                    pw = null;
                    fichero=null;
                    fichero = new FileWriter(archivoam);
                    pw = new PrintWriter(fichero);
                   //************************************************* 
                     writer_ANSI.close();
                     writer_ANSI=null;
                     pw_ANSI = null;
                     writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoam_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                
                    String formasfarmaceuticas = "";
                    String unidamedida = "";
                    String concentracion = "";
                    total = BigDecimal.ZERO;
                    for (FacturaItem fi : Li_facturasitems) {
                        if (fi.getProducto().getHclCups() == null && fi.getProducto().getGenCategoriasProductos().getCodigo() == 2) {
                            if (fi.getProducto().getFormafarmaceutica() != null) {
                                formasfarmaceuticas = fi.getProducto().getFormafarmaceutica();
                            }
                            if (fi.getProducto().getUnidadmedidamedicamento() != null) {
                                unidamedida = fi.getProducto().getUnidadmedidamedicamento();
                            }
                            if (fi.getProducto().getConcentracionmedicamento() != null) {
                                concentracion = fi.getProducto().getConcentracionmedicamento();
                            }
                            try
                            {
                            pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",," + fi.getProducto().getCodigo() + ",1," + fi.getProducto().getNombre() + "," + formasfarmaceuticas.toUpperCase() + "," + concentracion.toUpperCase() + "," + unidamedida.toUpperCase() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                            pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",," + fi.getProducto().getCodigo() + ",1," + fi.getProducto().getNombre() + "," + formasfarmaceuticas.toUpperCase() + "," + concentracion.toUpperCase() + "," + unidamedida.toUpperCase() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                         
                            }catch(Exception e)
                            {
                             pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",," + fi.getProducto().getCodigo() + ",1," + fi.getProducto().getNombre() + "," + formasfarmaceuticas.toUpperCase() + "," + concentracion.toUpperCase() + "," + unidamedida.toUpperCase() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());   
                             pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",," + fi.getProducto().getCodigo() + ",1," + fi.getProducto().getNombre() + "," + formasfarmaceuticas.toUpperCase() + "," + concentracion.toUpperCase() + "," + unidamedida.toUpperCase() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());   
                         
                            }
                            total = total.setScale(0, RoundingMode.HALF_UP).add(fi.getValor_total().setScale(0, RoundingMode.HALF_UP));
                        }
                    }
                    
                    fichero.close();
                    pw = null;
                    fichero=null;
                    fichero = new FileWriter(archivoat);
                    pw = new PrintWriter(fichero);
                    //***************************************************
                    writer_ANSI.close();
                     writer_ANSI=null;
                     pw_ANSI = null;
                     writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoat_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                
                    total = BigDecimal.ZERO;
                    for (FacturaItem fi : Li_facturasitems) {
                        if (fi.getProducto().getHclCups() == null && fi.getProducto().getGenCategoriasProductos().getCodigo() == 3) {
                           try
                           {
                            pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",,1," + fi.getProducto().getCodigo() + "," + fi.getProducto().getNombre() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                            pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",,1," + fi.getProducto().getCodigo() + "," + fi.getProducto().getNombre() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                     
                           }catch(Exception e)
                           {
                                 pw.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",,1," + fi.getProducto().getCodigo() + "," + fi.getProducto().getNombre() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                                 pw_ANSI.println(fi.getFactura().getNofacturacerosizquierda() + "," + SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().toUpperCase() + "," + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + ",,1," + fi.getProducto().getCodigo() + "," + fi.getProducto().getNombre() + "," + String.valueOf(fi.getQuantity()) + "," + fi.getValor_u().longValue() + "," + fi.getValor_total().longValue());
                        
                           }
                            total = total.setScale(0, RoundingMode.HALF_UP).add(fi.getValor_total().setScale(0, RoundingMode.HALF_UP));
                        }

                    }
                    fichero.close();
                    fichero=null;
                     pw = null;
                    fichero = new FileWriter(archivoct);
                    pw = new PrintWriter(fichero);
                     //***************************************************
                    writer_ANSI.close();
                     writer_ANSI=null;
                     pw_ANSI = null;
                     writer_ANSI = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoct_ANSI), "Cp1252"));
                     pw_ANSI = new PrintWriter(writer_ANSI);
                
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileaf.replace(".TXT", "") + ","+totregaf);
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileus.replace(".TXT", "") + "," + String.valueOf(totalusuarios));
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + filead.replace(".TXT", "") + "," + totregad);
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileap.replace(".TXT", "") + "," + String.valueOf(totalprocedimientos));
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileam.replace(".TXT", "") + "," + String.valueOf(totalmedicamentos));
                    pw.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileat.replace(".TXT", "") + "," + String.valueOf(totalinsumos));

                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileaf.replace(".TXT", "") + ","+totregaf);
                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileus.replace(".TXT", "") + "," + String.valueOf(totalusuarios));
                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + filead.replace(".TXT", "") + "," + totregad);
                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileap.replace(".TXT", "") + "," + String.valueOf(totalprocedimientos));
                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileam.replace(".TXT", "") + "," + String.valueOf(totalmedicamentos));
                    pw_ANSI.println(SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase() + "," + UtilDate.formatodiamesyear(new Date()) + "," + fileat.replace(".TXT", "") + "," + String.valueOf(totalinsumos));

                    fichero.close();
                     writer_ANSI.close();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setInitialDirectory(folder);
                    fileChooser.setTitle("Archivos Rips");

                    // Agregar filtros para facilitar la busqueda
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Files", "*.TXT")
                    );

                    fileChooser.showOpenDialog(new Stage());
                }

            }
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private class ButtonCell extends TableCell<Factura, Boolean> {

        //boton a mostrar
        final Button cellButton = new Button();
        final Button cellButtonPdf = new Button();

        ButtonCell(final TableView tblView) {

            Image imageOk = null;
            imageOk = new Image(getClass().getResourceAsStream("/images/pdf.png"));
            ImageView iv = null;
            iv = new ImageView(imageOk);
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            cellButtonPdf.setGraphic(iv);
            cellButtonPdf.setCursor(Cursor.HAND);
            cellButtonPdf.setTooltip(new Tooltip("Imprimir en Pdf"));
            cellButtonPdf.setOnAction(e -> {
                ImpresionFactura impresionFactura;
                int selectdIndex = getTableRow().getIndex();
                impresionFactura = null;

                Factura f = ((Factura) Ol_Factura.get(selectdIndex));
                impresionFactura = new ImpresionFactura(f);
                try {
                    impresionFactura.fprintpdffacturageneral(1);
                } catch (IOException ex) {
                    Logger.getLogger(AdminFacturaItem.class.getName()).log(Level.SEVERE, null, ex);
                }

                //_gpDatosFactura.getChildren().add(11, _tfValorIva);
            });
        }
        //Muestra un boton si la fila no es nula

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                try {
                    int selectdIndex = getTableRow().getIndex();

                    setGraphic(cellButtonPdf);
                } catch (Exception e) {

                }

            } else {
                setGraphic(new Label());

            }
        }

    }

    private class ButtonCellRips extends TableCell<Factura, Boolean> {

        //boton a mostrar
        final Button cellButton = new Button();
        final Button cellButtonPdf = new Button();

        ButtonCellRips(final TableView tblView) {

            Image imageOk = null;
            imageOk = new Image(getClass().getResourceAsStream("/images/generarrips.png"));
            ImageView iv = null;
            iv = new ImageView(imageOk);
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            cellButtonPdf.setGraphic(iv);
            cellButtonPdf.setCursor(Cursor.HAND);
            cellButtonPdf.setTooltip(new Tooltip("Generar Rips Tipo A o T"));
            cellButtonPdf.setOnAction(e -> {
                int selectdIndex = getTableRow().getIndex();
                SihicApp.s_factura = ((Factura) Ol_Factura.get(selectdIndex));
                try {
                    exportartxt();

                    //_gpDatosFactura.getChildren().add(11, _tfValorIva);
                } catch (IOException ex) {
                    Logger.getLogger(Rips.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(Rips.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        }
        //Muestra un boton si la fila no es nula

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            try {
                int selectdIndex = getTableRow().getIndex();

                if (!empty && (!((Factura) Ol_Factura.get(selectdIndex)).getPrefijo().equals("P") || !((Factura) Ol_Factura.get(selectdIndex)).getPrefijo().equals("S"))) {

                    setGraphic(cellButtonPdf);

                } else {
                    setGraphic(new Label());

                }
            } catch (Exception e) {

            }
        }

    }

    private class ButtonCellExcel extends TableCell<Factura, Boolean> {

        //boton a mostrar
        final Button cellButton = new Button();
        final Button cellButtonExcel = new Button();

        ButtonCellExcel(final TableView tblView) {

            Image imageOk = null;
            imageOk = new Image(getClass().getResourceAsStream("/images/excel.png"));
            ImageView iv = null;
            iv = new ImageView(imageOk);
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            cellButtonExcel.setGraphic(iv);
            cellButtonExcel.setCursor(Cursor.HAND);
            cellButtonExcel.setTooltip(new Tooltip("Exportar a Excel  Tipo A o T"));
            cellButtonExcel.setOnAction(e -> {
                int selectdIndex = getTableRow().getIndex();
                SihicApp.s_factura = ((Factura) Ol_Factura.get(selectdIndex));
                try {
                    try {
                        load_detalle();
                        exportar_excel();

                        //_gpDatosFactura.getChildren().add(11, _tfValorIva);
                    } catch (WriteException ex) {
                        Logger.getLogger(Rips.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Rips.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        }
        //Muestra un boton si la fila no es nula

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            try {
                int selectdIndex = getTableRow().getIndex();

                if (!empty &&  (!((Factura) Ol_Factura.get(selectdIndex)).getPrefijo().equals("P") || !((Factura) Ol_Factura.get(selectdIndex)).getPrefijo().equals("S"))) {

                    setGraphic(cellButtonExcel);

                } else {
                    setGraphic(new Label());

                }
            } catch (Exception e) {

            }
        }

    }

    private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {
            
            System.out.println("Valor pre->" + observable.getValue() + "-" + newValue);
            
            
            getFacturas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportar_excel() throws IOException, WriteException {
        String rutaArchivo = "/home/adminlinux/sihic/archivoplano.xls";
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

        for (String[] s : li_tempdetalles) {
            System.out.println("size temp2->" + s.length);

            if (f == 0) {
                hoja.insertRow(f);

                // CellType.LABEL;
                WritableCellFeatures writableCellFeatures = new WritableCellFeatures();
                writableCellFeatures.removeDataValidation();
                label = null;

                label = new jxl.write.Label(f, f, "prefijo");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(1, f, "numero de factura");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(2, f, "tipo doc ips");
                hoja.addCell(label);

                label = null;
                label = new jxl.write.Label(3, f, "num doc ips");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(4, f, "codigo habilitacion ips");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(5, f, "codigo eps");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(6, f, "codigo cuenta");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(7, f, "codigo contrato");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(8, f, "fecha de factura");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(9, f, "valor bruto");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(10, f, "valor copago");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(11, f, "valor retefuente");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(12, f, "valor iva");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(13, f, "valor uso futuro");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(14, f, "valor moderadora");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(15, f, "valor descuento");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(16, f, "concepto del descuento");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(17, f, "valor neto");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(18, f, "periodo");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(19, f, "codigo regional");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(20, f, "clasificación de origen");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(21, f, "tipo de servicio");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(22, f, "tipo de paquete");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(23, f, "finalidad de consulta");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(24, f, "dias de tratamiento");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(25, f, "tipo documento paciente");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(26, f, "número de documento");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(27, f, "primer nombre");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(28, f, "segundo nombre");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(29, f, "primer apellido");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(30, f, "segundo apellido");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(31, f, "Edad del paciente");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(32, f, "sexodelpaciente");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(33, f, "estadodelpaciente");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(34, f, "discapacidad");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(35, f, "tipo de prestación");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(36, f, "codigo de prestacion principal");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(37, f, "codigo de prestacion detalle");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(38, f, "Descripción del procedimiento");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(39, f, "Fecha del procedimiento");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(40, f, "Hora del procedimiento");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(41, f, "cantidad");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(42, f, "valor unitario");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(43, f, "valor uso futuro");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(44, f, "valor moderadora paciente");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(45, f, "valor copago paciente");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(46, f, "valor total del servicio");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(47, f, "Número de autorización");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(48, f, "Diagnóstico Principal");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(49, f, "Tipo Diagnóstico");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(50, f, "Código de diagnóstico de ingreso");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(51, f, "Código de diagnóstico secundario");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(52, f, "fecha de entrada");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(53, f, "hora de entrada");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(54, f, "fecha de salida");
                hoja.addCell(label);
                label = null;
                label = new jxl.write.Label(55, f, "hora de salida");
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
        String os = System.getProperty("os.name"); 
        File file = new File("/home/adminlinux/sihic/archivoplano.xls");
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("soffice /home/adminlinux/sihic/archivoplano.xls"); 
  }
else
 {
    Desktop.getDesktop().open(file);
 
    }
    }

    private void load_detalle() {
        li_tempdetalles.clear();
        Li_facturasitems = SihicApp.s_factura.getFacturaItems();
        for (FacturaItem facturaitem : Li_facturasitems) {
            s_detalles[0] = facturaitem.getFactura().getPrefijo();
            s_detalles[1] = facturaitem.getFactura().getNofacturacerosizquierda().substring(1,facturaitem.getFactura().getNofacturacerosizquierda().length());
            s_detalles[2] = "NI";
            String snit=SihicApp.genUnidadesOrganizacion.getNit().replace(".","");
            snit=snit.substring(0, 9);
            s_detalles[3] = snit;
            s_detalles[4] = SihicApp.genUnidadesOrganizacion.getCodigo().toUpperCase();
            s_detalles[5] = facturaitem.getAgnCitas().getGenPacientes().getGenEapb().getCodigo();
            s_detalles[6] = "2";
            s_detalles[7] = facturaitem.getAgnCitas().getGenPacientes().getGenEapb().getConvenioseps().size() > 0 ? String.valueOf(facturaitem.getAgnCitas().getGenPacientes().getGenEapb().getConvenioseps().get(0).getNumerocontrato()) : "";
            s_detalles[8] = sihic.util.UtilDate.formatodiamesyeardivisor(facturaitem.getFactura().getFacturaDate());
            s_detalles[9] =String.valueOf(round(facturaitem.getFactura().getTotalAmount().add(facturaitem.getFactura().getTotalcopagos()).add(facturaitem.getFactura().getTotalcuotasmoderadoras())).longValue());
            facturaitem.getFactura().calculartotalescopagocuotamoeras();
            s_detalles[10] ="0";// String.valueOf(facturaitem.getFactura().getTotalcopagos().longValue());
            s_detalles[11] = "0";
            s_detalles[12] = "0";
            s_detalles[13] = "0";
            s_detalles[14] = "0";//String.valueOf(facturaitem.getFactura().getTotalcuotasmoderadoras().longValue());
            s_detalles[15] = "0";
            s_detalles[16] = "";
            s_detalles[17] = String.valueOf(round(facturaitem.getFactura().getTotalAmount().add(facturaitem.getFactura().getTotalcopagos()).add(facturaitem.getFactura().getTotalcuotasmoderadoras())).longValue());
            int mes=sihic.util.UtilDate.getmesfecha(facturaitem.getFactura().getFacturaDate());
            String smes=mes<10?"0"+mes:String.valueOf(mes);
            s_detalles[18] = String.valueOf(sihic.util.UtilDate.getyearfecha(facturaitem.getFactura().getFacturaDate()))+mes;//String.valueOf(sihic.util.UtilDate.getyearfecha(facturaitem.getFactura().getFacturaDate()))+String.valueOf(sihic.util.UtilDate.getmesfecha(facturaitem.getFactura().getFacturaDate()));
            s_detalles[19] = facturaitem.getAgnCitas().getGenPacientes().getGenEapb().getCodigoregionalcont();
            s_detalles[20] = "1";
            s_detalles[21] = "1";
            s_detalles[22] = "";
            s_detalles[23] = "";
            s_detalles[24] = "";
            s_detalles[25] = facturaitem.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla();
            s_detalles[26] = facturaitem.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento();
            s_detalles[27] = facturaitem.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre();
            s_detalles[28] = facturaitem.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre();
            s_detalles[29] = facturaitem.getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido();
            s_detalles[30] = facturaitem.getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido();
            int iyear=Integer.valueOf(facturaitem.getAgnCitas().getGenPacientes().getGenPersonas().year());
            s_detalles[31] = iyear<10?"00"+iyear:iyear<100?"0"+iyear:String.valueOf(iyear);
            s_detalles[32] =facturaitem.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla();
            s_detalles[33] ="1";
            s_detalles[34] ="N";
            s_detalles[35] =facturaitem.getProducto().getGenCategoriasProductos().getCodigo()==1?"P":facturaitem.getProducto().getGenCategoriasProductos().getCodigo()==1?"M0":"I";
            s_detalles[36] =facturaitem.getProducto().getCodigo();
            s_detalles[37] ="";
            s_detalles[38] = facturaitem.getProducto().getNombre();
            s_detalles[39] = sihic.util.UtilDate.formatodiamesyeardivisor(facturaitem.getAgnCitas().getFechaHora());
            int ihora=facturaitem.getAgnCitas().getGenHorasCita().getHora();
            int minutos=facturaitem.getAgnCitas().getGenHorasCita().getMinutos();
            
            s_detalles[40] = (ihora<10?"0"+ihora:ihora) + ":" + (minutos<10?"0"+minutos:minutos);
            s_detalles[41] = String.valueOf(BigDecimal.valueOf(facturaitem.getQuantity()).longValue());
            s_detalles[42] = facturaitem.getValor_u().toString();
            s_detalles[43] = "0";
            s_detalles[44] = "0";
            s_detalles[45] = "0";
            s_detalles[46] = facturaitem.getValor_u().toString();
            s_detalles[47] = facturaitem.getNoautorizacion();
            s_detalles[48] =facturaitem.getDiagnosticoprincipal()!=null?facturaitem.getDiagnosticoprincipal().getCodigo():"";
            s_detalles[49] ="2";
            s_detalles[50] = facturaitem.getDiagnosticoingreso()!=null?facturaitem.getDiagnosticoingreso().getCodigo():"";
          
            s_detalles[51] =facturaitem.getDiagnosticosecundario()!=null?facturaitem.getDiagnosticosecundario().getCodigo():"";
          
            s_detalles[52] = sihic.util.UtilDate.formatodiamesyeardivisor(facturaitem.getAgnCitas().getFechaHora());
             ihora=facturaitem.getAgnCitas().getGenHorasCita().getHora();
            minutos=facturaitem.getAgnCitas().getGenHorasCita().getMinutos();
          
            s_detalles[53] = (ihora<10?"0"+ihora:ihora) + ":" + (minutos<10?"0"+minutos:minutos);
            s_detalles[54] = sihic.util.UtilDate.formatodiamesyeardivisor(facturaitem.getAgnCitas().getFechaHora());
            ihora=facturaitem.getAgnCitas().getGenHorasCita().getHora();
            minutos=facturaitem.getAgnCitas().getGenHorasCita().getMinutos()+ ((int) (Math.random() * 30) + 15);
            s_detalles[55] = (ihora<10?"0"+ihora:ihora) + ":" + (minutos<10?"0"+minutos:minutos);
        
            li_tempdetalles.add(s_detalles);
            s_detalles = null;
            s_detalles = new String[56];

        }
    }
}
