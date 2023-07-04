/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.nomina;

/**
 *
 * @author adminlinux
 */
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import modelo.LibroAuxiliar;
import modelo.Puc;
import modelo.Empleados;
import modelo.Nomina;
import modelo.NominaEmpleados;
import modelo.NotaContabilidad;
import modelo.ParametrosNomina;
import sihic.SearchPopover;
import sihic.SihicApp;
import static sihic.SihicApp.pageBrowser;
import sihic.contabilidad.ImpresionFactura;
import sihic.contabilidad.ImprimirPdf;


import sihic.control.SearchBox;

/**
 *
 * @author adminlinux
 */
public class FNomina extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_nomina = FXCollections.observableArrayList();
    private static List<NominaEmpleados> l_nominaempleados;
    private Button bu_buscar;
    private Button bu_insert;
     private Button bu_pdf;
    private static GridPane Gp_Message;
    private static Label L_Message;
    private static Runnable Task_Message;
    private static Thread backgroundThread;
    private StackPane stack;
    private ImpresionFactura impresionFactura;
    private SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
    private String appClass;
    private Class clz;
    private Object app;
    private Parent parent;
    private Stage stage = new Stage(StageStyle.DECORATED);
    private String appClassNotaContable;
    private Class clzNotaContable;
    private Object appNotaContable;
    private Parent parentNotaContable;
    private Stage stageNotaContable = new Stage(StageStyle.DECORATED);
    Scene scene = null;
    private ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemdelete;
    private ImageView img;
    private static DatePicker dp_periodoinicio;
    private static DatePicker dp_periodofin;
    private static TextField noconsecutivo=new TextField();
    private static TextField ss_salud=new TextField();
    private static TextField ss_fondopensiones=new TextField();
    private static TextField ss_arl=new TextField();
    private static TextField pf_cajacf=new TextField();
    private static TextField pf_icbf=new TextField();
    private static TextField pf_sena=new TextField();
    private static TextField pp_cesantias=new TextField();
    private static TextField pp_intcesantias=new TextField();
    private static TextField pp_prima=new TextField();
    private static TextField pp_vacaciones=new TextField();
    private static TextField totalbasico=new TextField();
    private static TextField totaldevengado=new TextField();
    private static TextField totalauxtransporte=new TextField();
    private static TextField exonerado_pfsalud=new TextField();
    private static TextField horasdiarias=new TextField();
    private static TextField observaciones=new TextField();
    private static TextField no_consecutivo=new TextField();
    private static TextField totalotrosingresos=new TextField();
    private static TextField totalsalud=new TextField();
    private static TextField totalpension=new TextField();
    private static TextField totalotrasdeducciones=new TextField(); 
    private static TextField totaldeducciones=new TextField(); 
    private static TextField totalretefuente=new TextField();
    private static TextField totalsueldoneto=new TextField();
    private static TextField totalseguridadsocial=new TextField();
    private static TextField totalparafiscales=new TextField();
    private static TextField totalprestacionessociales=new TextField();
    private  TitledPane tp_seguridadsocial;
    private  GridPane gp_seguridadsocial=new GridPane();
    private  TitledPane tp_parafiscales;
    private  GridPane gp_parafiscales=new GridPane();
    private  TitledPane tp_prestacionessociales;
    private  GridPane gp_prestacionessociales=new GridPane();
    private static int añonomina=0;
    private static Label la_cesantias=new Label("");
    private static Label la_intcesantias=new Label("");
    private static Label la_prima=new Label("");
    private static Label la_vacaciones=new Label("");
    private static Label cajafamiliar=new Label("");
    private static Label icbf=new Label("");
    private static Label sena=new Label("");
    private static final Label la_salud=new Label("");
    private static final Label la_fondopensiones=new Label("");
    private static final Label la_arl=new Label("");
    private static final SearchBox sb_cuenta = new SearchBox();
    private static final Label la_cuenta = new Label("Cuenta");
    private static final Label la_detalle = new Label("Detalle");
    private static final Label la_tipomovimiento = new Label("Tipo Movimiento");
    private static final Label la_valor = new Label("Valor");
    private SearchPopover sp_cuenta;
    private static final TextField detalle = new TextField();
    private static final TextField valor = new TextField();
    private static final ChoiceBox tipomovimiento = new ChoiceBox();
    private static  TableView tv_detalles;
    private static ObservableList ol_detalles = FXCollections.observableArrayList();
    private List<LibroAuxiliar> l_detalles;
    private DecimalFormat format = new DecimalFormat("#,0");
    private Button bu_asiento;
    private ImageView imageView;
    private Button bu_notacontable;
    private static TextField totaldebito=new TextField("0");
    private static TextField totalcredito=new TextField("0");
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
            sp_cuenta = new SearchPopover(sb_cuenta, pageBrowser, SihicApp.conPuc, false);
            sp_cuenta.setMaxSize(300, 70);
            sb_cuenta.setMaxWidth(410);
            sb_cuenta.setMinWidth(410);
            sb_cuenta.setOnAction(e -> {
             detalle.requestFocus();

        });
        sp_cuenta.getSearchResultPopoverList().setOnMouseClicked(e -> {

            sb_cuenta.setText(SihicApp.conPuc.getCodigo() + " " + SihicApp.conPuc.getNombre());

        });
        detalle.setMaxWidth(400);
        detalle.setMinWidth(400);
        tipomovimiento.setMaxWidth(200);
        tipomovimiento.setMinWidth(200);
        valor.setMaxWidth(200);
        valor.setMinWidth(200);
        la_cuenta.setMaxWidth(410);
        la_cuenta.setMinWidth(410);
        la_detalle.setMaxWidth(400);
        la_detalle.setMinWidth(400);
        la_tipomovimiento.setMaxWidth(200);
        la_tipomovimiento.setMinWidth(200);
        la_valor.setMaxWidth(200);
        la_valor.setMinWidth(200);
        tipomovimiento.getItems().addAll("Débito", "Crédito");
        valor.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        valor.setOnAction(e -> {
            try {
                AddDetalle();
            } catch (Exception ex) {
                
            }
        });
 tv_detalles = new TableView();

        tv_detalles.setOnKeyPressed(e -> {
            try {
                if (e.getCode().equals(e.getCode().DELETE)) {
                    removeDetalle();
                }
            } catch (Exception ex) {
               
            }
        });
        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(150);
        codigo.setText("Codigo");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getCodigo()));

            }
        });
        TableColumn ccuenta = new TableColumn();
        ccuenta.setMinWidth(350);
        ccuenta.setText("Cuenta");
        ccuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getNombre()));

            }
        });
        TableColumn detalles = new TableColumn();
        detalles.setMinWidth(400);
        detalles.setText("Detalle");
        detalles.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getDescripcion()));

            }
        });
        TableColumn cdebito = new TableColumn();
        cdebito.setMinWidth(150);
        cdebito.setText("Débito");
        cdebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getDebe().toString()));

            }
        });
        TableColumn ccredito = new TableColumn();
        ccredito.setMinWidth(150);
        ccredito.setText("Crédito");
        ccredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getHaber().toString()));

            }
        });
        tv_detalles.getColumns().addAll(codigo, ccuenta, detalles, cdebito, ccredito);
        tv_detalles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_detalles.setMaxHeight(80);
        imageView = null;
        imageView = new ImageView("/images/asientos.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_asiento = new Button();
        bu_asiento.setTooltip(new Tooltip("Agregar Asientos Contables Doc. Comprobante Nomina"));
        bu_asiento.setGraphic(imageView);
        bu_asiento.setOnAction(e -> {
              try {
                  addasientoscontables();
              } catch (ParseException ex) {
                  Logger.getLogger(FNomina.class.getName()).log(Level.SEVERE, null, ex);
              } catch (InterruptedException ex) {
                  Logger.getLogger(FNomina.class.getName()).log(Level.SEVERE, null, ex);
              }
        });
         imageView = new ImageView("/images/notacontable.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
         bu_notacontable = new Button();
         bu_notacontable.setGraphic(imageView);
         bu_notacontable.setTooltip(new Tooltip("Agregar Asientos Contables Doc. Nota Contable"));
      
         bu_notacontable.setOnAction(e->{
                try {
                    saveComprobanteContabilidad();
                    show2();
                } catch(Exception e2){
                    e2.printStackTrace();
                }
         });
        tp_seguridadsocial = new TitledPane();
        tp_seguridadsocial.setText("Seguridad Social");
        tp_seguridadsocial.setCollapsible(false);
        tp_parafiscales = new TitledPane();
        tp_parafiscales.setText("Parafiscales");
        tp_parafiscales.setCollapsible(false);
        tp_prestacionessociales = new TitledPane();
        tp_prestacionessociales.setText("Prestaciones Sociales");
        tp_prestacionessociales.setCollapsible(false);
        dp_periodoinicio=new DatePicker(sihic.util.UtilDate.primerdiaprimermes(new Date()));
        dp_periodofin=new DatePicker(sihic.util.UtilDate.ultimodiaultimomes(new Date()));
        stage.setTitle("Novedades Empleados");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Novedades", img);
        itemnuevo.setOnAction(e -> {
            try {
                checkregistroexistente();
            } catch (Exception x) {
                x.printStackTrace();
            }
       
        });
        img = null;
        img = new ImageView("/images/editar.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemeditar = new MenuItem("Editar", img);
        itemeditar.setOnAction(e -> {

            try {
                checkregistroexistente();
            } catch (Exception x) {
                x.printStackTrace();
            }
        });
        img = null;
        img = new ImageView("/images/delete.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemdelete = new MenuItem("Eliminar", img);
        itemdelete.setOnAction(e -> {

            try {
                checkregistroexistente();
                eliminar();
            } catch (Exception x) {

            }
        });

        contextMenu = new ContextMenu(itemnuevo);
        stack = new StackPane();
        appClass = "sihic.nomina.AdminNovedades";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(120);
        buscar = new TextField();
        buscar.setMinWidth(250);
        buscar.setOnKeyReleased(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {
            }
        });
        

        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_buscar = new Button("", imageView);
        bu_buscar.setDisable(false);
        bu_buscar.setOnAction(e
                -> {
            try {
                getRecords();

            } catch (Exception ex) {

            }
        });
        imageView = new ImageView("/images/insert.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        bu_insert = new Button("", imageView);
        bu_insert.setDisable(SihicApp.nomina==null);
        bu_insert.setTooltip(new Tooltip("Crear Nomina"));
        bu_insert.setDisable(false);
        bu_insert.setOnAction(e
                -> {
            try {
               crearnomina();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
 imageView = new ImageView("/images/pdf.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        bu_pdf = new Button("", imageView);
        bu_pdf.setDisable(SihicApp.nomina==null);
        bu_pdf.setTooltip(new Tooltip("Imprimir Comprobante de Nomina"));
        bu_pdf.setDisable(false);
        bu_pdf.setOnAction(e
                -> {
            try {
               ImprimirPdf.fpdfcomprobantenomina();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gp_generico = new GridPane();
        gp_generico.getStylesheets().add(SihicApp.hojaestilos);
        gp_generico.getStyleClass().add("background");
        tv_generic = new TableView();
        TableColumn consecutivo= new TableColumn();
        consecutivo.setMinWidth(100);
        consecutivo.setText("N° Comprobante");
        consecutivo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {
                       
                return new SimpleStringProperty(nominaempleado.getValue().getNo_consecutivo()==null?"N/A":nominaempleado.getValue().getNo_consecutivo().toString());

            }
        });
        
        TableColumn noident= new TableColumn();
        noident.setMinWidth(150);
        noident.setText("N° Identificación");
        noident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(nominaempleado.getValue().getEmpleados().getGenPersonas().getDocumento());

            }
        });
        TableColumn nombre= new TableColumn();
        nombre.setMinWidth(200);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(nominaempleado.getValue().getEmpleados().getGenPersonas().getNombres());

            }
        });
          TableColumn sueldobasico= new TableColumn();
        sueldobasico.setMinWidth(150);
        sueldobasico.setText("Sueldo Básico");
        sueldobasico.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(nominaempleado.getValue().getEmpleados().getCargosEntidad().getSueldo().toBigInteger().toString());

            }
        });
         TableColumn cantdiastrabajados= new TableColumn();
        cantdiastrabajados.setMinWidth(150);
        cantdiastrabajados.setText("Cant Días Trabajados");
        cantdiastrabajados.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(String.valueOf(nominaempleado.getValue().getCantdiastrabajados()));

            }
        });
        TableColumn totalsueldo= new TableColumn();
        totalsueldo.setMinWidth(150);
        totalsueldo.setText("Sueldo Básico Total");
        totalsueldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(String.valueOf(nominaempleado.getValue().getTotalbasico().longValue()));

            }
        });
        TableColumn auxtransporte= new TableColumn();
        auxtransporte.setMinWidth(150);
        auxtransporte.setText("Aux. Transporte");
        auxtransporte.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(nominaempleado.getValue().getAuxiliotransporte().toString());

            }
        });
        TableColumn otrosingresos= new TableColumn();
        otrosingresos.setMinWidth(150);
        otrosingresos.setText("Horas Extras,Comisiones,Etc");
        otrosingresos.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(String.valueOf(nominaempleado.getValue().getValorcomisiones().add(nominaempleado.getValue().getValorhorasextras()).longValue()));

            }
        });
         TableColumn ctotaldevengado= new TableColumn();
        ctotaldevengado.setMinWidth(150);
        ctotaldevengado.setText("Total Devengado");
        ctotaldevengado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(String.valueOf(sihic.util.Utils.round(nominaempleado.getValue().getTotaldevengado()).longValue()));

            }
        });
         TableColumn Salud= new TableColumn();
        Salud.setMinWidth(150);
        Salud.setText("Salud");
        Salud.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(String.valueOf(sihic.util.Utils.round(nominaempleado.getValue().getValorsalud()).longValue()));

            }
        });
         TableColumn pension= new TableColumn();
        pension.setMinWidth(150);
        pension.setText("Pensión");
        pension.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(String.valueOf(sihic.util.Utils.round(nominaempleado.getValue().getValorpension()).longValue()));

            }
        });
         TableColumn otrosegresos= new TableColumn();
        otrosegresos.setMinWidth(150);
        otrosegresos.setText("Deudas, Etc");
        otrosegresos.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(String.valueOf(sihic.util.Utils.round(nominaempleado.getValue().getValordeudaentidadfinancieras().add(nominaempleado.getValue().getValorotrasdeudas().add(nominaempleado.getValue().getValordeudaterceros()))).longValueExact()));

            }
        });
         TableColumn retefuente= new TableColumn();
        retefuente.setMinWidth(150);
        retefuente.setText("ReteFuente");
        retefuente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(String.valueOf(nominaempleado.getValue().getValorretefuente().longValue()));

            }
        });
          TableColumn ctotaldeducciones= new TableColumn();
        ctotaldeducciones.setMinWidth(150);
        ctotaldeducciones.setText("Total Deducciones");
        ctotaldeducciones.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(String.valueOf(sihic.util.Utils.round(nominaempleado.getValue().getTotaldeducciones()).longValue()));

            }
        });
        TableColumn sueldoneto= new TableColumn();
        sueldoneto.setMinWidth(150);
        sueldoneto.setText("Sueldo Neto");
        sueldoneto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NominaEmpleados, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NominaEmpleados, String> nominaempleado) {

                return new SimpleStringProperty(String.valueOf(sihic.util.Utils.round(nominaempleado.getValue().getNetopagado()).longValue()));

            }
        });
         tv_generic.getColumns().addAll(consecutivo,noident,nombre,sueldobasico,cantdiastrabajados,totalsueldo,auxtransporte,otrosingresos,ctotaldevengado,Salud,pension,otrosegresos,retefuente,ctotaldeducciones,sueldoneto);
         tv_generic.setMinHeight(200);
         tv_generic.setMaxHeight(200);
         tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
         tv_generic.setContextMenu(contextMenu);
         Gp_Message = new GridPane();
         Gp_Message.setMaxSize(40, 500);
         L_Message = new Label();
         L_Message.setMinWidth(600);
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
         Label la_periodoinicio=new Label(" Periodo Inicio:");
         la_periodoinicio.setMinWidth(100);
           Label la_periodofin=new Label(" Periodo Fin:");
         la_periodofin.setMinWidth(100);
         gp_generico.addRow(0,la_periodoinicio,dp_periodoinicio , la_periodofin,dp_periodofin,new Label("Horas Trabajo:"),horasdiarias,bu_insert,bu_pdf);
         gp_generico.add(tv_generic, 0, 1, 14, 1);
         gp_generico.add(new Label("Totales:"), 0, 2, 5, 1);
         gp_generico.add(new Label("Total Básico"), 4, 2, 1, 1);
         gp_generico.add(totalbasico, 4, 3, 1, 1);
         gp_generico.add(new Label("Total Aux. Trasnposte"), 5, 2, 1, 1);
         gp_generico.add(totalauxtransporte, 5, 3, 1, 1);
         gp_generico.add(new Label("Total Otros Ingresos"), 6, 2, 1, 1);
         gp_generico.add(totalotrosingresos, 6, 3, 1, 1);
         gp_generico.add(new Label("Total Devengado"), 7, 2, 1, 1);
         gp_generico.add(totaldevengado, 7, 3, 1, 1);
         gp_generico.add(new Label("Total Salud"), 8, 2, 1, 1);
         gp_generico.add(totalsalud, 8, 3, 1, 1);
         gp_generico.add(new Label("Total Pensión"), 9, 2, 1, 1);
         gp_generico.add(totalpension, 9, 3, 1, 1);
         gp_generico.add(new Label("Total Otras Deducciones"), 10, 2, 1, 1);
         gp_generico.add(totalotrasdeducciones, 10, 3, 1, 1);
         gp_generico.add(new Label("Total Rte. Fuente"), 11, 2, 1, 1);
         gp_generico.add(totalretefuente, 11, 3, 1, 1);
         gp_generico.add(new Label("Total Deducciones"), 12, 2, 1, 1);
         gp_generico.add(totaldeducciones, 12, 3, 1, 1);
         gp_generico.add(new Label("Total Sueldo Neto"), 13, 2, 1, 1);
         gp_generico.add(FNomina.totalsueldoneto, 13, 3, 1, 1);
         gp_generico.add(tp_seguridadsocial, 0, 4, 4, 1);
         gp_generico.add(tp_parafiscales, 4, 4, 5, 1);
         gp_generico.add(tp_prestacionessociales, 9, 4, 5, 1);
          
      
        gp_generico.add(new HBox(2, la_cuenta, la_detalle, la_tipomovimiento, la_valor), 0, 5, 14, 1);
        gp_generico.add(new HBox(2, sb_cuenta, detalle, tipomovimiento, valor,bu_asiento,bu_notacontable), 0, 6, 14, 1);
       
        gp_generico.add(tv_detalles, 0, 7, 14, 1);
         gp_generico.add(totaldebito, 12,8,1,1 );
          gp_generico.add(totalcredito, 13,8,1,1 );
        gp_generico.add(sp_cuenta, 0, 6, 5, 2);
        GridPane.setValignment(sp_cuenta, VPos.TOP);
      
         gp_generico.setVgap(5);
         gp_generico.setHgap(5);
         gp_generico.setAlignment(Pos.TOP_CENTER);
        GridPane.setValignment(tp_seguridadsocial, VPos.TOP);
        GridPane.setValignment(tp_parafiscales, VPos.TOP);
        GridPane.setValignment(tp_prestacionessociales, VPos.TOP);
        GridPane.setHalignment(tp_seguridadsocial, HPos.CENTER);
        GridPane.setHalignment(tp_parafiscales, HPos.CENTER);
        GridPane.setHalignment(tp_prestacionessociales, HPos.CENTER);
        gp_seguridadsocial.setAlignment(Pos.TOP_CENTER);
        gp_parafiscales.setAlignment(Pos.TOP_CENTER);
        gp_prestacionessociales.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        gp_seguridadsocial.getStylesheets().add(SihicApp.hojaestilos);
        gp_seguridadsocial.getStyleClass().add("background");
        gp_seguridadsocial.addRow(0,new Label("Descripción"),new Label("%"),new Label("Valor"));
        
        gp_seguridadsocial.addRow(1,new Label("Salud"),la_salud,ss_salud);
        gp_seguridadsocial.addRow(2,new Label("Fondo de Pensiones"),la_fondopensiones,ss_fondopensiones);
        gp_seguridadsocial.addRow(3,new Label("ARL"),la_arl,ss_arl);
        gp_seguridadsocial.add(new Label("Total Seguridad Social"),0,4,2,1);
        gp_seguridadsocial.add(totalseguridadsocial,2,4,1,1);
        gp_seguridadsocial.setHgap(3);
        gp_seguridadsocial.setVgap(3);
        gp_seguridadsocial.autosize();
        tp_seguridadsocial.setContent(gp_seguridadsocial);
        gp_parafiscales.getStylesheets().add(SihicApp.hojaestilos);
         gp_parafiscales.getStyleClass().add("background");
        gp_parafiscales.addRow(0,new Label("Descripción"),new Label("%"),new Label("Valor"));
       
        gp_parafiscales.addRow(1,new Label("Caja Compensación Familiar"),cajafamiliar,pf_cajacf);
        gp_parafiscales.addRow(2,new Label("ICBF"),icbf,pf_icbf);
        gp_parafiscales.addRow(3,new Label("SENA"),sena,pf_sena);
        gp_parafiscales.add(new Label("Total ParaFiscales"),0,4,2,1);
        gp_parafiscales.add(totalparafiscales,2,4,1,1);
        gp_parafiscales.setHgap(3);
        gp_parafiscales.setVgap(3);
        gp_parafiscales.autosize();
        tp_parafiscales.setContent(gp_parafiscales);
        gp_prestacionessociales.getStylesheets().add(SihicApp.hojaestilos);
        gp_prestacionessociales.getStyleClass().add("background");
        gp_prestacionessociales.addRow(0,new Label("Descripción"),new Label("%"),new Label("Valor"));
      
       
        gp_prestacionessociales.addRow(1,new Label("Cesantías"),la_cesantias,pp_cesantias);
        gp_prestacionessociales.addRow(2,new Label("Intereses sobre cesantías"),la_intcesantias,pp_intcesantias);
        gp_prestacionessociales.addRow(3,new Label("Prima"),la_prima,pp_prima);
        gp_prestacionessociales.addRow(4,new Label("Vacaciones"),la_vacaciones,pp_vacaciones);
        gp_prestacionessociales.add(new Label("Total Prestaciones Sociales"),0,5,2,1);
        gp_prestacionessociales.add(totalprestacionessociales,2,5,1,1);
        gp_prestacionessociales.setHgap(3);
        gp_prestacionessociales.setVgap(3);
        gp_prestacionessociales.autosize();
        tp_prestacionessociales.setContent(gp_prestacionessociales);
      
        stack.getChildren().addAll(gp_generico, Gp_Message);
        gp_generico.setMinSize(1320, 730);
        gp_generico.setMaxSize(1320, 730);
       

       crearoeditar();
        
        return stack;
    }

    public static void getRecords() throws ParseException {
        try {
          l_nominaempleados=SihicApp.nomina.getNominaempleadositems();
            ol_nomina.clear();
            ol_nomina.addAll(l_nominaempleados.toArray());
            tv_generic.setItems(ol_nomina);
        } catch (Exception e) {

        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        parent = null;
        parent = (Parent) clz.getMethod("createContent").invoke(app);
        scene = null;
        scene = new Scene(parent, Color.TRANSPARENT);

        if (stage.isShowing()) {
            stage.close();
        }

//set scene to stage
        stage.sizeToScene();

        //center stage on screen
        stage.centerOnScreen();
        stage.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stage.show();
    }
  private void show2() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        appClassNotaContable = "sihic.contabilidad.documentos.FNotaContabilidad";
        clzNotaContable = Class.forName(appClassNotaContable);
        appNotaContable = clzNotaContable.newInstance();
        parentNotaContable = null;
        parentNotaContable = (Parent) clzNotaContable.getMethod("createContent").invoke(appNotaContable);
        scene = null;
        scene = new Scene(parentNotaContable, Color.TRANSPARENT);

        if (stageNotaContable.isShowing()) {
            stageNotaContable.close();
        }

//set scene to stage
        stage.sizeToScene();

        //center stage on screen
        stageNotaContable.centerOnScreen();
        stageNotaContable.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageNotaContable.showAndWait();
    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            SihicApp.nominaEmpleados= (NominaEmpleados) tv_generic.getSelectionModel().getSelectedItem();
            show();
        }
    }

    private void eliminar() throws ParseException {
        //genPersonasControllerClient.delete(genPacientes);
        //getListGenPersonas();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    private static void set_datos()
    {
      horasdiarias.setText(String.valueOf(SihicApp.nomina.getHorasdiarias()));
      try{
      dp_periodoinicio.setValue(sihic.util.UtilDate.formatoyearmesdia(SihicApp.nomina.getPeriodoinicio()));
      }catch(Exception e)
      {
           dp_periodoinicio.setValue(sihic.util.UtilDate.formatoyearmesdia(new Date()));
      }
      try
      {
      dp_periodofin.setValue(sihic.util.UtilDate.formatoyearmesdia(SihicApp.nomina.getPeriodofin()));
      }catch(Exception e)
      {
           dp_periodofin.setValue(sihic.util.UtilDate.formatoyearmesdia(new Date()));
      }
      no_consecutivo.setText(SihicApp.nomina.getNocomprobantecerosizquierda());
      pf_cajacf.setText(SihicApp.nomina.getPf_cajacf().toBigInteger().toString());
      pf_icbf.setText(SihicApp.nomina.getPf_icbf().toBigInteger().toString());
      pf_sena.setText(SihicApp.nomina.getPf_sena().toBigInteger().toString());
      ss_arl.setText(SihicApp.nomina.getSs_arl().toBigInteger().toString());
      ss_fondopensiones.setText(SihicApp.nomina.getSs_fondopensiones().toBigInteger().toString());
      ss_salud.setText(SihicApp.nomina.getSs_salud().toBigInteger().toString());
      pp_cesantias.setText(SihicApp.nomina.getPp_cesantias().toBigInteger().toString());
      pp_prima.setText(SihicApp.nomina.getPp_prima().toBigInteger().toString()); 
      pp_vacaciones.setText(SihicApp.nomina.getPp_vacaciones().toBigInteger().toString());
      pp_intcesantias.setText(SihicApp.nomina.getPp_intcesantias().toBigInteger().toString());
      totalauxtransporte.setText(SihicApp.nomina.getTotalauxtransporte().toBigInteger().toString());
      totalbasico.setText(SihicApp.nomina.getTotalbasico().toBigInteger().toString());
      totaldeducciones.setText(SihicApp.nomina.getTotaldeducciones().toBigInteger().toString());
      totaldevengado.setText(SihicApp.nomina.getTotaldevengado().toBigInteger().toString()); 
      totalotrasdeducciones.setText(SihicApp.nomina.getTotalotrasdeducciones().toBigInteger().toString()); 
      totalotrosingresos.setText(SihicApp.nomina.getTotalotrosingresos().toBigInteger().toString()); 
      totalpension.setText(SihicApp.nomina.getTotalpension().toBigInteger().toString()); 
      totalretefuente.setText(SihicApp.nomina.getTotalretefuente().toBigInteger().toString()); 
      totalsalud.setText(SihicApp.nomina.getTotalsalud().toBigInteger().toString()); 
      totalsueldoneto.setText(SihicApp.nomina.getTotalsueldoneto().toBigInteger().toString()); 
      totalseguridadsocial.setText(SihicApp.nomina.getSs_salud().add(SihicApp.nomina.getSs_fondopensiones().add(SihicApp.nomina.getSs_arl())).toBigInteger().toString());
      totalparafiscales.setText(SihicApp.nomina.getPf_cajacf().add(SihicApp.nomina.getPf_icbf().add(SihicApp.nomina.getPf_sena())).toString());
      totalprestacionessociales.setText(SihicApp.nomina.getPp_cesantias().add(SihicApp.nomina.getPp_intcesantias().add(SihicApp.nomina.getPp_prima()).add(SihicApp.nomina.getPp_vacaciones())).toBigInteger().toString());
  
    }  
    private static void nuevoRecord()
    {
        
           dp_periodoinicio.setValue(sihic.util.UtilDate.formatoyearmesdia(new Date()));
           dp_periodofin.setValue(sihic.util.UtilDate.formatoyearmesdia(new Date()));
           horasdiarias.setText("0");
           SihicApp.nomina=new Nomina();
           SihicApp.notaContabilidad=null;
    }
   public static void crearoeditar() throws ParseException {

        if (SihicApp.nomina != null) {
            if (SihicApp.nomina.getId() != null) {
                 set_datos();
                 añonomina=sihic.util.UtilDate.getyearfecha(SihicApp.nomina.getPeriodofin());
                
                 SihicApp.notaContabilidadControllerClient.getRecord();
                 if(SihicApp.notaContabilidad!=null){
              
                 }
                 findAñoparametrosdenomina();
                 reload();
                 getRecords();
                 getDetallesComprobantes();
                
            
            } else {
                nuevoRecord();
            }
        } else {
            nuevoRecord();
        }
        
    }
private static void findAñoparametrosdenomina()
{
    List<ParametrosNomina> li_parametrosnomina=SihicApp.li_parametrosNomina.stream().filter(line ->line.getAño()==añonomina) 	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());
    if(li_parametrosnomina.size()>0)
    { 
      SihicApp.parametrosNomina= li_parametrosnomina.get(0);
           
    }
} 
public static void crearnomina() throws ParseException, InterruptedException
{
    SihicApp.nomina.setPeriodoinicio(sihic.util.UtilDate.fechainiciomes2(sihic.util.UtilDate.localdatetodate(dp_periodoinicio.getValue())));
    SihicApp.nomina.setPeriodofin(sihic.util.UtilDate.fechafinmes2(sihic.util.UtilDate.localdatetodate(dp_periodofin.getValue())));
    SihicApp.nomina.setHorasdiarias(Integer.valueOf(horasdiarias.getText()));
    añonomina=sihic.util.UtilDate.getyearfecha(SihicApp.nomina.getPeriodofin());
    findAñoparametrosdenomina();
    SihicApp.nomina.setParametrosNomina(SihicApp.parametrosNomina);
    for(Empleados e: SihicApp.li_empleados)
    {
        NominaEmpleados ne=new NominaEmpleados();
        if(e.isActivo())
        {
          
            SihicApp.consecutivosContabilidadControllerClient.createconsecutivonominaempleado();
            ne.setEmpleados(e);
            ne.setNo_consecutivo(SihicApp.consecutivosContabilidad.getConsecutivonominaempleado());
            SihicApp.nomina.addempleado(ne);
                      
         }
        else
        {
             SihicApp.nomina.removeempleado(e);
        }
          
           
        
    }
    if( SihicApp.nomina.getId()!=null)
    {
        SihicApp.nominaControllerClient.update();
    }
    else
    {
    SihicApp.nominaControllerClient.create();
    }
    saveComprobanteContabilidad();
    AdminNomina.getRecords();
    getRecords();
    reload();
    set_datos();
}
public static  void reload()
{
     la_cesantias.setText(SihicApp.parametrosNomina.getPorc_cesantias().toString());
     la_intcesantias.setText(SihicApp.parametrosNomina.getPorc_intcesantias().toString());
     la_prima.setText(SihicApp.parametrosNomina.getPorc_prima().toString());
     la_vacaciones.setText(SihicApp.parametrosNomina.getPorc_vacaciones().toString());
     cajafamiliar.setText(SihicApp.parametrosNomina.getPorc_cajacompfam().toBigInteger().toString());
     icbf.setText(SihicApp.parametrosNomina.getPorc_icbf().toBigInteger().toString());
     sena.setText(SihicApp.parametrosNomina.getPorc_sena().toBigInteger().toString());
     la_salud.setText(SihicApp.parametrosNomina.getPorc_salud_empleador().toString());
     la_fondopensiones.setText(SihicApp.parametrosNomina.getPorc_fp().toString());
     la_arl.setText(SihicApp.parametrosNomina.getPorc_arl().toString());
}
public void AddDetalle() throws ParseException, InterruptedException {
        saveComprobanteContabilidad();
        if (SihicApp.conPuc != null) {
       //     if (SihicApp.conComprobanteEgreso.getId() != null && SihicApp.conComprobanteContabilidad.getId() != null) {
               // SihicApp.conComprobanteContabilidad.addPuc(SihicApp.conPuc, detalle.getText(), new BigDecimal(valor.getText().trim()), tipomovimiento.getSelectionModel().getSelectedIndex() == 0 ? true : false, jenum.EnumCentroCostos.ADMINISTRACION3.ordinal());
               // SihicApp.conComprobanteContabilidad = SihicApp.conPucControllerClient.save(SihicApp.conComprobanteContabilidad);
                getDetallesComprobantes();
                 sb_cuenta.setText("");
                detalle.setText("");
                valor.setText("");
                tipomovimiento.getSelectionModel().select(-1);
         //   }

        }

    }

    private static void getDetallesComprobantes() throws ParseException {
        //colocamos los datos

        try {

            ol_detalles.clear();
            //ol_detalles.addAll(SihicApp.conComprobanteContabilidad.getDetallesComprobanteContabilidads().toArray());
            // Ta_KardexProducto.getItems().clear();
            tv_detalles.setItems(ol_detalles);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
     public static void removeDetalle() throws ParseException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem()) != null) {
          //  SihicApp.conComprobanteContabilidad.removeDetalle((LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem());
            saveComprobanteContabilidad();
            getDetallesComprobantes();
        }
    }
     public static void saveComprobanteContabilidad() throws ParseException, InterruptedException {
         
        try {
            if(SihicApp.notaContabilidad==null)
            {
                SihicApp.notaContabilidad=new NotaContabilidad();
                SihicApp.notaContabilidad.setNomina(SihicApp.nomina);
                SihicApp.notaContabilidad.setConcepto("Apropiaciones Nomina ");
                SihicApp.notaContabilidad.setFechaelaboracion(SihicApp.nomina.getPeriodofin());
          
               
                SihicApp.conComprobanteContabilidadControllerClient.create2();
               
                SihicApp.notaContabilidad.setTiponota("AJ");
                SihicApp.notaContabilidadControllerClient.create();
                
            }
            else
            {
            SihicApp.conComprobanteContabilidadControllerClient.update2();
            
              SihicApp.notaContabilidad.setTiponota("AJ");
               SihicApp.notaContabilidad.setFechaelaboracion(SihicApp.nomina.getPeriodofin());
               SihicApp.notaContabilidadControllerClient.update();
              
             
            }
         
         
             L_Message.setText("Comprobante Diario Creado");
            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {

                }
            };
            backgroundThread = new Thread(Task_Message);
            // Terminate the running thread if the application exits
            backgroundThread.setDaemon(true);

            // Start the thread
            backgroundThread.start();
        } catch (Exception e) {
            L_Message.setText("Error en el proceso");
            e.printStackTrace();
            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {

                }
            };
            backgroundThread = new Thread(Task_Message);
            // Terminate the running thread if the application exits
            backgroundThread.setDaemon(true);

            // Start the thread
            backgroundThread.start();
        }

    }

   

private static void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }
  private static void addasientoscontables() throws ParseException, InterruptedException
  {
      
    
      colocarasientoscontables("510101", "Causación de nómina Sueldos", true, SihicApp.nomina.getTotalbasico());
      if(SihicApp.nomina.getTotalauxtransporte().compareTo(BigDecimal.ZERO)>0)
      {
        colocarasientoscontables("510108", "Causación de nómina Auxilio de Transporte", true, SihicApp.nomina.getTotalauxtransporte());  
      }
      colocarasientoscontables("23701002", "Causación de nómina Aportes a EPS", false, SihicApp.nomina.getTotalsalud());
      colocarasientoscontables("23700503", "Causación de nómina Aportes a Pensión", false, SihicApp.nomina.getTotalpension());
       colocarasientoscontables("23700504", "Causación de nómina Aportes a Fondo Solidaridad", false, SihicApp.nomina.getTotalfondosolidaridad());
    
      colocarasientoscontables("236505", "Causación de nómina Retención por salario", false, SihicApp.nomina.getTotalotrasdeducciones());
      colocarasientoscontables("250501", "Causación de nómina Salarios por pagar", false, SihicApp.nomina.getTotalsueldoneto());
      colocarasientoscontables("230102", "Causación de nómina Cesantías", false, SihicApp.nomina.getPp_cesantias());
      colocarasientoscontables("230103", "Causación de nómina Intereses sobre cesantías", false, SihicApp.nomina.getPp_intcesantias());
      colocarasientoscontables("230106", "Causación de nómina Prima de servicios", false, SihicApp.nomina.getPp_prima());
      colocarasientoscontables("230104", "Causación de nómina Vacaciones", false, SihicApp.nomina.getPp_vacaciones());
      colocarasientoscontables("510501", "Causación de nómina Cesantías", true, SihicApp.nomina.getPp_cesantias());
      colocarasientoscontables("510502", "Causación de nómina Intereses sobre cesantías", true, SihicApp.nomina.getPp_intcesantias());
      colocarasientoscontables("510505", "Causación de nómina Prima de servicios", true, SihicApp.nomina.getPp_prima());
      colocarasientoscontables("510503", "Causación de nómina Vacaciones", true, SihicApp.nomina.getPp_vacaciones());
      addasientoscontablesnotacontabilidad();

  }
  private static void addasientoscontablesnotacontabilidad() throws ParseException, InterruptedException
  {
      
      colocarasientoscontables2("23701002", "Causación de nómina - Apropiaciones Aportes a EPS", false, SihicApp.nomina.getSs_salud());
      colocarasientoscontables2("23700503", "Causación de nómina - Apropiaciones Aportes a Pensión", false, SihicApp.nomina.getSs_fondopensiones());
      colocarasientoscontables2("230110", "Causación de nómina - Apropiaciones Aportes a ARL", false, SihicApp.nomina.getSs_arl());
      colocarasientoscontables2("237010", "Causación de nómina - Apropiaciones AAportes a CajaCFamiliar", false, SihicApp.nomina.getPf_cajacf());
      colocarasientoscontables2("510302", "Causación de nómina - Apropiaciones Aportes a EPS", true, SihicApp.nomina.getSs_salud());
      colocarasientoscontables2("510303", "Causación de nómina - Apropiaciones Aportes a Pensión", true, SihicApp.nomina.getSs_fondopensiones());
      colocarasientoscontables2("510301", "Causación de nómina - Apropiaciones Aportes a ARL", true, SihicApp.nomina.getSs_arl());
      colocarasientoscontables2("510304", "Causación de nómina - Apropiaciones AAportes a CajaCFamiliar", true, SihicApp.nomina.getPf_cajacf());
      colocarasientoscontables2("23700504", "Causación de nómina- Apropiaciones Aportes a Fondo Solidaridad", false, SihicApp.nomina.getTotalfondosolidaridad());
      colocarasientoscontables2("51030802", "Causación de nómina -Apropiaciones  Aportes a Fondo Solidaridad", true, SihicApp.nomina.getTotalfondosolidaridad());
    
     
              
      if(!sihic.util.PruebaSumasDebitoCredito.probarsumariguales())
      {
           L_Message.setText(sihic.util.PruebaSumasDebitoCredito.message);
           L_Message.getStyleClass().set(0,"labelError");
            Task_Message = () -> {
                try {
                    SetMessage();
                } catch (InterruptedException ex) {

                }
            };
            backgroundThread = new Thread(Task_Message);
            // Terminate the running thread if the application exits
            backgroundThread.setDaemon(true);
      }

  }
   private static void colocarasientoscontables(String _codigo,String _detalle, boolean _movimiento,BigDecimal _valor) throws ParseException, InterruptedException {
       findpuc(_codigo);
         if (SihicApp.conPuc != null) {
       //     if (SihicApp.nomina.getId() != null && SihicApp.conComprobanteContabilidad.getId() != null) {
        //       SihicApp.conComprobanteContabilidad.addPuc(SihicApp.conPuc, _detalle, _valor, _movimiento, jenum.EnumCentroCostos.ADMINISTRACION3.ordinal());
           //    SihicApp.conComprobanteContabilidad = SihicApp.conPucControllerClient.save(SihicApp.conComprobanteContabilidad);
                getDetallesComprobantes();
               
           // }

        }
   }
   private static void colocarasientoscontables2(String _codigo,String _detalle, boolean _movimiento,BigDecimal _valor) throws ParseException, InterruptedException {
       findpuc(_codigo);
         if (SihicApp.conPuc != null) {
          //  if (SihicApp.nomina.getId() != null && SihicApp.conComprobanteContabilidad2.getId() != null) {
           //    SihicApp.conComprobanteContabilidad2.addPuc(SihicApp.conPuc, _detalle, _valor, _movimiento, jenum.EnumCentroCostos.ADMINISTRACION3.ordinal());
           //    SihicApp.conComprobanteContabilidad2 = SihicApp.conPucControllerClient.save(SihicApp.conComprobanteContabilidad2);
                
               
          //  }

        }
   }
    public static void findpuc(String codigo)
 {
      List<Puc> li_conpuc=SihicApp.li_conpuc.stream().filter(line ->line.getCodigo().trim().toUpperCase().equals(codigo.trim()) )	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());
    if(li_conpuc.size()>0)
    { 
      SihicApp.conPuc= li_conpuc.get(0);
          
    }
    else
    {
      SihicApp.conPuc=null;   
    }
    
                   
                   
 }
    
 
}
