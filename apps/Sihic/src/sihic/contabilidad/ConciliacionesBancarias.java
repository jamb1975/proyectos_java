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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.LibroAuxiliar;
import modelo.Factura;
import sihic.SearchPopover;
import sihic.SihicApp;
import static sihic.SihicApp.pageBrowser;
import sihic.control.SearchBox;
import sihic.controlador.FacturaControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.UtilDate;

/**
 *
 * @author adminlinux
 */
public class ConciliacionesBancarias extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_cuenta;
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
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemdelete;
    private ImageView img;
    private Button B_Pdf;
    private static DatePicker dp_fechafrom;
    private static DatePicker dp_fechato;
    private SearchPopover sp_cuenta;
    private static final SearchBox sb_cuenta = new SearchBox();
    private TitledPane tp_generic;
    private String pathdocumento="";
    private String titledocumento="";
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        tp_generic = new TitledPane();
        tp_generic.setText("Conciliaciones Bancarias");
        tp_generic.setCollapsible(false);
        sp_cuenta = new SearchPopover(sb_cuenta, pageBrowser, SihicApp.conPuc, false);
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        stage.setTitle("Documento Co");
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
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                show();
            } catch (Exception x) {
                x.printStackTrace();
            }
        });
        img = null;
        img = new ImageView("/images/editar.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemeditar = new MenuItem("Editar Documento Contable", img);
        itemeditar.setOnAction(e -> {
            try {
               if(checkregistroexistente())
               {
                   show();
               }    
                
            } catch (Exception x) {

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
                if (SihicApp.s_factura != null) {
                    if (SihicApp.s_factura.getId() != null) {
                        show();
                    }
                }
            } catch (Exception x) {

            }
        });
        
    
        contextMenu = new ContextMenu(itemnuevo, itemeditar);
        stack = new StackPane();
        appClass = "sihic.contabilidad.FFactura";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        facturaControllerClient = new FacturaControllerClient();
        la_cuenta = new Label("Buscar: ");
        la_cuenta.setMinWidth(120);
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

        TableColumn fecha = new TableColumn();
        fecha.setMinWidth(150);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty(UtilDate.formatodiamesyear(detalles.getValue().getFechaelaboracion()));

            }
        });
        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(150);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {
                try {
                    return new SimpleStringProperty(detalles.getValue().getConPuc().getCodigo());
                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        TableColumn cuenta = new TableColumn();
        cuenta.setMinWidth(300);
        cuenta.setText("Cuenta");
        cuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {
                try {
                    return new SimpleStringProperty(detalles.getValue().getConPuc().getNombre());
                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }

            }
        });
      TableColumn detalle = new TableColumn();
        detalle.setMinWidth(300);
        detalle.setText("Descripción");
        detalle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {
                try {
                    return new SimpleStringProperty(detalles.getValue().getDescripcion());
                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        TableColumn debe = new TableColumn();
        debe.setMinWidth(215);
        debe.setText("Debe");
        debe.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {
                try {
                    return new SimpleStringProperty(detalles.getValue().getDebe().toString());
                } catch (Exception e) {
                    return new SimpleStringProperty("No");
                }

            }
        });
        TableColumn haber = new TableColumn();
        haber.setMinWidth(215);
        haber.setText("Haber");
        haber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {
                try {
                    return new SimpleStringProperty(detalles.getValue().getHaber().toString());
                }   catch (Exception e) {
                     return new SimpleStringProperty("No");
                }

            }
        });
        gp_generico.setMinWidth(1340);
        gp_generico.setMaxWidth(1340);
        tv_generic.setEditable(true);
        tv_generic.getColumns().addAll(fecha, codigo, cuenta, detalle, debe,haber);
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
        gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0, la_cuenta,sb_cuenta, dp_fechafrom, dp_fechato);
        gp_generico.add(tv_generic, 0, 2, 4, 1);
        gp_generico.add(sp_cuenta, 1, 2, 4, 1);
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
            SihicApp.conComprobanteContabilidadControllerClient.getRecordsDetalles(dp_fechafrom.getValue().toString(), dp_fechato.getValue().toString(),SihicApp.conPuc.getCodigo());            ol_geneapb.clear();
            ol_geneapb.addAll(SihicApp.li_conConDetallesComprobanteContabilidads.toArray());
            tv_generic.setItems(ol_geneapb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
         stage.setTitle("Documento Co");
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
            
     //       SihicApp.conComprobanteContabilidad = ((LibroAuxiliar) tv_generic.getSelectionModel().getSelectedItem()).getConComprobanteContabilidad();
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
    public void printFacturaglobal() throws IOException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, NoSuchMethodException, IllegalAccessException, NoSuchMethodException, NoSuchMethodException {

         ImpresionFactura impresionFactura = null;
         impresionFactura = new ImpresionFactura(SihicApp.s_factura);
         impresionFactura.fprintpdffacturaglobal();

    }
    
    public void escogerdocumentocontable() throws ParseException
    {
       /* switch(SihicApp.conComprobanteContabilidad.getTipo())
        {    
            case 0: pathdocumento="sihic.contabilidad.documentos.FComprobanteApertura";
                    titledocumento="Comprobante Apertura";
                    break;
            case 6: pathdocumento="sihic.contabilidad.documentos.FComprobanteApertura";
                    titledocumento="Comprobante Apertura";
                    SihicApp.facturaProveedoresControllerClient.getRecord();
                      break;   
            case 7: pathdocumento="sihic.contabilidad.documentos.FConsignaciones";
                    titledocumento="Consignaciones";
                    SihicApp.consignacionesControllerClient.getRecord();
                    break;
            case 2: pathdocumento="sihic.contabilidad.documentos.FComprobanteEgreso";
                    titledocumento="Comprobante de Egreso";
                    SihicApp.conComprobanteEgresoControllerClient.getRecord();
                    break;
            case 1: pathdocumento="sihic.contabilidad.documentos.FComprobanteIngreso";
                    titledocumento="Comprobante de Ingreso";
                    SihicApp.conComprobanteIngresoControllerClient.getRecord();
                    break;         
            case 14: pathdocumento="sihic.contabilidad.documentos.FNotaContabilidad";
                    titledocumento="Nota Contable";
                    SihicApp.notaContabilidadControllerClient.getRecord();
                    break;
            case 13: pathdocumento="sihic.contabilidad.documentos.FNotaCredito";
                    titledocumento="Nota Crédito";
                    SihicApp.notaCreditoControllerClient.getRecord();
                    break;
           case 12: pathdocumento="sihic.contabilidad.documentos.FNotaDebito";
                    titledocumento="Nota Débito";
                    SihicApp.notaDebitoControllerClient.getRecord();
                    break;        
        }  */
            
    }
}
