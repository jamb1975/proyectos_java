/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.Factura;
import sihic.SihicApp;
import sihic.controlador.FacturaControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.UtilDate;

/**
 *
 * @author adminlinux
 */
public class RadicarAceptarFacturas extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_geneapb = FXCollections.observableArrayList();
    ;
    private static List<Factura> li_factura;
    private static FacturaControllerClient facturaControllerClient;
    private Button bu_buscar;
    private Button bu_nuevo;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
    private ImpresionFactura impresionFactura;
    private SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
    private String appClass;
    private Class clz;
    private Object app;
    private Parent parent;
    private Stage stage = new Stage(StageStyle.DECORATED);
    Scene scene = null;
    private ContextMenu contextMenu;
    
    private MenuItem itemeditar;
    
    private ImageView img;
    private Button B_Pdf;
    private static DatePicker dp_fechafrom;
    private static DatePicker dp_fechato;
    private TitledPane tp_generic;
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
       tp_generic = new TitledPane();
        tp_generic.setText("Radicación y Aceptación de Facturas");
        tp_generic.setCollapsible(false);
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        stage.setTitle("Radicar y Aceptar Factura");
        ImageView imageView = new ImageView("/images/diario.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_Pdf = new Button("", imageView);
        B_Pdf.setOnAction(e -> {
            try {
                checkregistroexistente();
            } catch (Exception ex) {
                Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (SihicApp.s_factura != null) {
                if (SihicApp.s_factura.getId() != null) {
                    try {
                        printFactura(1);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
       
        img = null;
        img = new ImageView("/images/editar.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemeditar = new MenuItem("Editar", img);
        itemeditar.setOnAction(e -> {

            try {
               if(checkregistroexistente())
               {  
                
                        show();
               }
                
            } catch (Exception x) {
                x.printStackTrace();
            }
        });
        

        contextMenu = new ContextMenu(itemeditar);
        stack = new StackPane();
        appClass = "sihic.contabilidad.FRadicadaAceptada";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        facturaControllerClient = new FacturaControllerClient();
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(120);
        buscar = new TextField();
        buscar.setMinWidth(50);
        buscar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        dp_fechafrom = new DatePicker();
        dp_fechafrom.setValue(UtilDate.formatoyearmesdia(UtilDate.fechainiciomes2(new Date())));
        dp_fechafrom.setOnAction(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        dp_fechato = new DatePicker();
        dp_fechato.setValue(UtilDate.formatoyearmesdia(UtilDate.fechafinmes2(new Date())));
        dp_fechato.setOnAction(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
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
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        bu_nuevo = new Button("", imageView);
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                SihicApp.s_factura = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_generic = new TableView();
          TableColumn numerofactura = new TableColumn();
          numerofactura.setMinWidth(222);
          numerofactura.setText("N° Factura");
          numerofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
            try
            {
                return new SimpleStringProperty(factura.getValue().getNofacturacerosizquierda());
            }catch(Exception e)
             {
              return new SimpleStringProperty("No Radicada");  
            }

            }
        });
          TableColumn fechaelab = new TableColumn();
        fechaelab.setMinWidth(222);
        fechaelab.setText("Fecha Elaboración");
        fechaelab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
            try
            {
                return new SimpleStringProperty(UtilDate.formatodiamesyear(factura.getValue().getFacturaDate()));
            }catch(Exception e)
             {
              return new SimpleStringProperty("No Radicada");  
            }

            }
        });

        TableColumn fecha = new TableColumn();
        fecha.setMinWidth(222);
        fecha.setText("Fecha Radicación");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
            try
            {
                return new SimpleStringProperty(UtilDate.formatodiamesyear(factura.getValue().getFecharadicacion()));
            }catch(Exception e)
             {
              return new SimpleStringProperty("No Radicada");  
            }

            }
        });
        TableColumn numeroradicado = new TableColumn();
        numeroradicado.setMinWidth(222);
        numeroradicado.setText("N° Radicado");
        numeroradicado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                 
                return new SimpleStringProperty(factura.getValue().getNumeroradicado());
                 

            }
        });
        TableColumn fechaaceptada = new TableColumn();
        fechaaceptada.setMinWidth(222);
        fechaaceptada.setText("Fecha Aceptada");
        fechaaceptada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                 try
                 {
                return new SimpleStringProperty(UtilDate.formatodiamesyear(factura.getValue().getFechaaceptacion()));
                 }catch(Exception e)
                 {
                   return new SimpleStringProperty("No Aceptada");  
                 }

            }
        });
        TableColumn aceptada = new TableColumn();
        aceptada.setMinWidth(222);
        aceptada.setText("Estado");
        aceptada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                 
                return new SimpleStringProperty(factura.getValue().isAceptada()?"Aceptada":"No Aceptada");
                 

            }
        });
      
        tv_generic.setEditable(true);
        tv_generic.getColumns().addAll(numerofactura,fechaelab,fecha, fechaaceptada,numeroradicado,aceptada);
        tv_generic.setMinHeight(577);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setContextMenu(contextMenu);
        Gp_Message = new GridPane();

        Gp_Message.setMaxSize(40, gp_generico.getLayoutBounds().getWidth());
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
        gp_generico.setMinSize(1340,630 );
        gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0, buscar, dp_fechafrom, dp_fechato, LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal);
        gp_generico.add(tv_generic, 0, 3, 6, 1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().select(0);
        getRecords();
        return stack;
    }

    public static void getRecords() throws ParseException {
        try {
            li_factura = facturaControllerClient.getRecords(dp_fechafrom.getValue().toString(), dp_fechato.getValue().toString(), buscar.getText().trim(), LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString());
            ol_geneapb.clear();
            ol_geneapb.addAll(li_factura.toArray());
            tv_generic.setItems(ol_geneapb);
        } catch (Exception e) {
            e.printStackTrace();
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

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            SihicApp.s_factura = (Factura) tv_generic.getSelectionModel().getSelectedItem();
            return true; 
        }
        else
        {
            return false;
        }
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void printFactura(int opc) throws IOException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, IllegalAccessException, NoSuchMethodException, NoSuchMethodException {

        ImpresionFactura impresionFactura = null;
        impresionFactura = new ImpresionFactura(SihicApp.s_factura);
        impresionFactura.freporteffacturageneral(opc);

    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {
            LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().select(newValue.intValue());
            getRecords();
        } catch (ParseException ex) {
            Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambiarconsecutivo(Factura factura, String newconsecutivo) {
        factura.setNo_factura(Long.valueOf(newconsecutivo));
        SihicApp.s_facturaControllerClient.guardarFactura(factura);

        try {
            getRecords();
        } catch (ParseException ex) {
            Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
