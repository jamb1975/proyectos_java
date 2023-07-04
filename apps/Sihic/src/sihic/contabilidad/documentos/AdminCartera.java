/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad.documentos;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sihic.controlador.ProductoControllerClient;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import jxl.Workbook;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import modelo.ComprobanteIngreso;
import modelo.Puc;
import modelo.Factura;
import sihic.SearchPopover;
import sihic.SihicApp;
import static sihic.SihicApp.pageBrowser;
import static sihic.contabilidad.documentos.CausarFacturas.buscarnocomprobante;
import static sihic.contabilidad.documentos.CausarFacturas.getDetallesComprobantes;
import static sihic.contabilidad.documentos.CausarFacturas.gettotales;
import sihic.control.SearchBox;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.util.UtilDate;



/**
 *
 * @author karolyani
 */
public class AdminCartera extends Application {
     
    private TableView tv_generic ;
    private ObservableList ol_generic;
    private GridPane gp_generic;
    ProductoControllerClient productoControllerClient;
    private Text T_Totales;
    private TextField T_Valor_total_todo;
    private TextField T_Cant_total_todo;
    private Label la_buscar;
    private TextField Tf_cliente;
    private TextField Tf_total=new TextField();
    private Button B_Exportar;
    private TitledPane tp_generic;
    private ContextMenu contextMenu;
    private MenuItem  itemabonos;
    private ImageView img;
    private  String appClassGeneric;
    private  Class clzGeneric ;
    private  Object appGeneric ;
    private  Parent parentGeneric;
    private  Stage stageGeneric= new Stage(StageStyle.DECORATED);
    Scene sceneFactura =null;
    private static final DatePicker dp_desde=new DatePicker(LocalDate.now());
    private static final DatePicker dp_hasta=new DatePicker(LocalDate.now());
    private static final DatePicker fechapago=new DatePicker(LocalDate.now());
    private static  TextField documento=new TextField();
    private  Button bu_procesaracontabilidad;
    private  Button bu_nuevo;
    private ImageView imageView;
    private static RadioButton rb_pagofactura=new RadioButton();
    private static RadioButton rb_cuotamoderadora=new RadioButton();
    private static RadioButton rb_copago=new RadioButton();
    private ToggleGroup toggleGroup=new ToggleGroup();
    private HBox hb_radiobutton=new HBox();
    private static final SearchBox sb_cuentadebito = new SearchBox();
    private SearchPopover sp_cuentadebito;
    private static final SearchBox sb_cuentacredito = new SearchBox();
    private SearchPopover sp_cuentacredito;
    private static final Label la_cuentadebito = new Label("Cuenta Débito");
    private static final Label la_detalledebito = new Label("Detalle");
    private static final Label la_cuentacredito= new Label("Cuenta Crédito");
    private static final Label la_detallecredito = new Label("Detalle Crédito");
    private static final Label la_prefijo = new Label("Prefijo Factura:");
    private static final Label la_dpdesde = new Label("Desde:");
    private static final Label la_dpto = new Label("a:");
    private static final Label la_noident= new Label("N° Ident:");
    private static final Label la_nofactura= new Label("N° Factura:");
    private static final Label la_tipopago= new Label("Ingreso:");
    private static final Label la_valorespecifico= new Label("Valor Ingreso Específico:");
    private static final Label la_concepto= new Label("Concepto de Ingreso:");
    private static final Label la_fechapago= new Label("Fecha de Pago:");
    private static final TextField detallecredito = new TextField();
    private static final TextField detalledebito = new TextField();
    private static final TextField totaldebito = new TextField();
    private static final TextField totalcredito = new TextField();
    private HBox hbox = new HBox();
    private HBox hbox2 = new HBox();
    private HBox hbox3 = new HBox();
    private HBox hbox4 = new HBox();
    private static Puc conpuc_debito;
    private static Puc conpuc_credito;
    private static GridPane Gp_Message;
    private static Label L_Message;
    private static Runnable Task_Message;
    private static Thread  backgroundThread;
    private static TextField tf_nofactura=new TextField();
    public  static  TextField tf_valorespecifico=new TextField("0");
    public  BigDecimal total=BigDecimal.ZERO;
    private static TextField concepto=new TextField();
    private Button bu_viewcomprobantediario;
    private static  List<Factura> li_facturas=new ArrayList<>();
    private static Label la_nocomprobante=new Label("Buscar No. Comprobante:");
    private static TextField nocomprobante=new TextField("0");
    public Parent createContent() throws IOException, ParseException {
        
          gp_generic=new GridPane();
          gp_generic.getStyleClass().add("category-page");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        
         Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_generic.getLayoutBounds().getWidth(), 40);
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        Gp_Message.setMaxHeight(40);
         LoadChoiceBoxGeneral.cb_sucursales.getSelectionModel().select(0);
        sp_cuentadebito = new SearchPopover(sb_cuentadebito, pageBrowser, SihicApp.conPuc, false);
        sp_cuentadebito.setMaxSize(200, 70);
        sb_cuentadebito.setMaxWidth(200);
        sb_cuentadebito.setMinWidth(200);
        sp_cuentacredito = new SearchPopover(sb_cuentacredito, pageBrowser, SihicApp.conPuc, false);
        sp_cuentacredito.setMaxSize(200, 70);
        sb_cuentacredito.setMaxWidth(200);
        sb_cuentacredito.setMinWidth(200);
        totaldebito.setMaxWidth(150);
        totalcredito.setMaxWidth(150);
        la_cuentadebito.setMaxWidth(200);
        la_cuentadebito.setMinWidth(200);
        la_cuentacredito.setMaxWidth(200);
        la_cuentacredito.setMinWidth(200);
      
        la_prefijo.setMinWidth(100);
        la_prefijo.setMaxWidth(100);
        la_dpdesde.setMinWidth(100);
        la_dpdesde.setMaxWidth(100);
        la_dpto.setMinWidth(50);
        la_dpto.setMaxWidth(50);
        la_noident.setMinWidth(100);
        la_noident.setMaxWidth(100);
        la_nofactura.setMinWidth(100);
        la_nofactura.setMaxWidth(100);
        la_tipopago.setMinWidth(70);
        la_tipopago.setMaxWidth(70);
        la_valorespecifico.setMinWidth(150);
        la_valorespecifico.setMaxWidth(150);
        la_concepto.setMinWidth(150);
        la_concepto.setMaxWidth(150);
        la_fechapago.setMinWidth(100);
        la_fechapago.setMaxWidth(100);
        la_nocomprobante.setMinWidth(100);
        la_nocomprobante.setMaxWidth(100);
    
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.setMinWidth(200);
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.setMaxWidth(200);
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().select(0);
        LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
        tf_nofactura.textProperty().addListener((observable, oldValue, newValue) -> {
              try {
                  getRecords2();
              } catch (ParseException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              }
          });
          documento.textProperty().addListener((observable, oldValue, newValue) -> {
              try {
                  getRecords();
              } catch (ParseException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              }
});
          dp_desde.setOnAction(e->{
              try {
                  getRecords();
              } catch (ParseException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              }
          });
          dp_hasta.setOnAction(e->{
              try {
                  getRecords();
              } catch (ParseException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              }
          });
           nocomprobante.setOnAction(e->{
             try {
                 buscarnocomprobante();
             } catch (ParseException ex) {
                 Logger.getLogger(CausarFacturas.class.getName()).log(Level.SEVERE, null, ex);
             } catch (InterruptedException ex) {
                 Logger.getLogger(CausarFacturas.class.getName()).log(Level.SEVERE, null, ex);
             }
    });
        hbox.getChildren().clear();
        hbox2.getChildren().clear();
        hbox.setSpacing(3);
        hbox2.setSpacing(3);
     
        sb_cuentadebito.setOnAction(e->
        {   
            conpuc_debito=new Puc();
            conpuc_debito=SihicApp.conPuc;
            sb_cuentacredito.requestFocus();
        });
        sb_cuentacredito.setOnAction(e->
        {   
            conpuc_credito=new Puc();
            conpuc_credito=SihicApp.conPuc;
            LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.requestFocus();
        });
        imageView=null;
        imageView=new ImageView("/images/procesar.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_procesaracontabilidad=new Button("",imageView);
        bu_procesaracontabilidad.setOnAction(e->{
              try {
                  AddDetalle();
              } catch (ParseException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              } catch (InterruptedException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              }
        });
        imageView=null;
        imageView=new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_nuevo=new Button("",imageView);
        bu_nuevo.setOnAction(e->{
          //  nuevocomprobantediario();
        });
        imageView=null;
        imageView=new ImageView("/images/comprobantediario.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        bu_viewcomprobantediario=new Button("",imageView);
        bu_viewcomprobantediario.setOnAction(e->
        {
              try {
                  showcomprobantediario();
              } catch (NoSuchMethodException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IllegalAccessException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IllegalArgumentException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              } catch (InvocationTargetException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              } catch (ClassNotFoundException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              } catch (InstantiationException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              }
        });
        rb_pagofactura.setToggleGroup(toggleGroup);
        rb_pagofactura.setText("Factura");
        rb_cuotamoderadora.setText("C. Moderadora");
        rb_cuotamoderadora.setToggleGroup(toggleGroup);
        rb_copago.setText("Copago");
        rb_copago.setToggleGroup(toggleGroup);
        hb_radiobutton.setSpacing(5);
        hb_radiobutton.getChildren().addAll(rb_pagofactura,rb_cuotamoderadora,rb_copago);
        hbox.getChildren().addAll(la_cuentadebito, la_cuentacredito,new Label("Centro de costos:"));
        hbox2.getChildren().addAll(sb_cuentadebito, sb_cuentacredito,LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal,LoadChoiceBoxGeneral.cb_sucursales,bu_nuevo,bu_procesaracontabilidad,bu_viewcomprobantediario);
     
        stageGeneric.setTitle("Pagos A Factura");
          contextMenu=null;
          contextMenu=new ContextMenu(); 
         img=null;
         img=new ImageView ("/images/abonos.png");
         img.setFitHeight(20);
         img.setFitWidth(20);
         itemabonos=new MenuItem("Abonos a factura",img);
         itemabonos.setOnAction(e->{
         
           try{
         checkregistroexistente();
         showAbonosFactura();
           }catch(Exception x)
           {
               x.printStackTrace();
           }
       });
         contextMenu.getItems().addAll(itemabonos);
          productoControllerClient=new ProductoControllerClient();
          la_buscar=new Label("N° Factura: ");
          Tf_cliente=new TextField();
          Tf_cliente.setMinWidth(200);
          Tf_cliente.setPromptText("Encontrar por codigo o  nombre ");
          Tf_cliente.textProperty().addListener((observable, oldValue, newValue) -> {
              try {
                  getRecords();
              } catch (ParseException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              }
});
          
         tp_generic=new TitledPane();
           tp_generic.setText("Cartera");
        imageView=null;
        imageView=new ImageView("/images/excel.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        B_Exportar=new Button("Exportar",imageView);
         B_Exportar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    exportar_excel();
                } catch (IOException ex) {
                 
                } catch (WriteException ex) {
                   
                } catch (ParseException ex) {
                   
                }
            }});
       
      
      
       
         T_Totales=new Text("Total: ");
         T_Totales.setFill(Color.BLACK);
         T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
         T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD,20));
        
         T_Valor_total_todo=new TextField();
         T_Valor_total_todo.setMaxWidth(130);
         T_Cant_total_todo=new TextField();
         T_Cant_total_todo.setMaxWidth(130);
         
        ol_generic= FXCollections.observableArrayList();
      
      
        //Creamos la tabla para ver existencias
       stageGeneric.setOnHiding(e->{
              try {
                  getRecords();
              } catch (ParseException ex) {
                  Logger.getLogger(AdminCartera.class.getName()).log(Level.SEVERE, null, ex);
              }
       });
        tv_generic=new TableView();
        tv_generic.setContextMenu(contextMenu);
        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(100);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getNofacturacerosizquierda());
                            
            }});
        
        TableColumn noident = new TableColumn();
        noident.setMinWidth(150);
        noident.setText("N° Identificación");
        noident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
                 if(factura.getValue().getPrefijo().substring(0,1).equals("A") || factura.getValue().getPrefijo().substring(0,1).equals("P"))
                 {
                     return new SimpleStringProperty(factura.getValue().getGenEapb().getNit());
                 }
                 else
                 {
                    return new SimpleStringProperty(factura.getValue().getGenPersonas().getDocumento()); 
                 }
                            
            }});
       
       TableColumn nombre = new TableColumn();
        nombre.setMinWidth(320);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                     if(factura.getValue().getPrefijo().substring(0,1).equals("A") || factura.getValue().getPrefijo().substring(0,1).equals("P"))
                 {
                     return new SimpleStringProperty(factura.getValue().getGenEapb().getNombre());
                 }
                 else
                 {
                    return new SimpleStringProperty(factura.getValue().getGenPersonas().getNombres()); 
                 }
                            
            }});
         TableColumn celular = new TableColumn();
        celular.setMinWidth(130);
        celular.setText("Celular");
        celular.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                   if(factura.getValue().getPrefijo().substring(0,1).equals("A") || factura.getValue().getPrefijo().substring(0,1).equals("P"))
                 {
                     return new SimpleStringProperty(factura.getValue().getGenEapb().getTelefono());
                 }
                 else
                 {
                    return new SimpleStringProperty(factura.getValue().getGenPersonas().getTelefono()); 
                 }
                            
            }});
        
         TableColumn valor_factura = new TableColumn();
         valor_factura.setMinWidth(150);
         valor_factura.setText("Valor Factura");
         valor_factura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());
                            
            }});
         TableColumn abonos_factura = new TableColumn();
         abonos_factura.setMinWidth(150);
         abonos_factura.setText("Total Abonos");
         abonos_factura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getValor_abonos().toString());
                            
            }});
         TableColumn saldo = new TableColumn();
         saldo.setMinWidth(100);
         saldo.setText("Saldo");
         saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty((factura.getValue().getTotalAmount().toBigInteger().subtract(factura.getValue().getValor_abonos().toBigInteger())).toString());
                            
            }});
         TableColumn fecha = new TableColumn();
         fecha.setMinWidth(100);
         fecha.setText("Fecha");
         fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(sihic.util.UtilDate.formatodiamesyear(factura.getValue().getFacturaDate()));
                            
            }});
          TableColumn tiempoendias = new TableColumn();
          tiempoendias.setMinWidth(130);
          tiempoendias.setText("Días de Mora");
          tiempoendias.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(String.valueOf((new Date().getTime()-factura.getValue().getFacturaDate().getTime())/86400000));
                            
            }});

    
      tiempoendias.setCellFactory(column -> {
            return new TableCell<Factura, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                     
                    if (item == null || empty) {
                          System.out.println("item->"+item);
                          setText(null);
                           getTableRow().setStyle("");
                      
                    } else {
                        long totdias=0;
                        Factura f= ((Factura)getTableRow().getItem());
                        try{
                         totdias= (new Date().getTime()-f.getFechaaceptacion().getTime())/86400000;
                        }catch(Exception e)
                        {
                            
                        }
                        if(totdias>60)
                        {    
                        setTextFill(Color.WHITE);
                       getTableRow().setStyle("-fx-background-color: red;-fx-text-fill: white;");
                        setStyle("-fx-text-fill: white;");
                        }
                        else
                        {
                          if(totdias<60 && totdias >30)
                        {    
                        setTextFill(Color.BLACK);
                        getTableRow().setStyle("-fx-background-color: yellow;-fx-text-fill: black;");
                        } 
                          else
                         {
                        if(totdias <30)
                        {  
                             setTextFill(Color.WHITE);
                                  setStyle("-fx-text-fill: white;");
                                 getTableRow().setStyle("-fx-background-color: green;-fx-text-fill: white;"); 
                        }
                            
                         }
                        }
                   
                            setText(String.valueOf(item));
                       

                    }
                }
            };
        }
        );
        tv_generic.setMinHeight(500);
        tv_generic.setMaxHeight(500);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        hbox.setSpacing(3);
        hbox2.setSpacing(2);
        hbox3.setSpacing(3);
        hbox4.setSpacing(4);
        hbox.setMinWidth(1200);
        hbox.setMaxWidth(1200);
        hbox2.setMinWidth(1200);
        hbox2.setMaxWidth(1200);
        hbox3.setMinWidth(1200);
        hbox3.setMaxWidth(1200);
        hbox4.setMinWidth(1300);
        hbox4.setMaxWidth(1300);
       
        hbox3.getChildren().addAll(la_prefijo,LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal,la_dpdesde,dp_desde,la_dpto,dp_hasta,la_noident,documento,la_nofactura,tf_nofactura,la_nocomprobante,nocomprobante);
        hbox4.getChildren().addAll(la_tipopago,hb_radiobutton,la_valorespecifico,tf_valorespecifico,la_concepto,concepto,la_fechapago,fechapago);
        tv_generic.getColumns().addAll(nofactura,noident,nombre,celular,valor_factura,abonos_factura,saldo,fecha,tiempoendias );
        gp_generic.addRow(0,hbox3);
        gp_generic.addRow(1,hbox4);
        GridPane.setColumnSpan(hbox, 2);
        GridPane.setColumnSpan(hbox2, 2);
        GridPane.setColumnSpan(hbox3, 2);
        GridPane.setColumnSpan(hbox4, 2);
        gp_generic.addRow(2, hbox);
        gp_generic.addRow(3, hbox2);
        gp_generic.add(tv_generic, 0, 4,2,1);
        Tf_cliente.setAlignment(Pos.CENTER_LEFT);
        gp_generic.add(new Label("Total a Pagar:"), 0, 5,1,1);
        gp_generic.add(Tf_total, 1, 5,1,1);
        gp_generic.add(sp_cuentadebito, 0, 4, 1, 2);
        gp_generic.add(sp_cuentacredito, 1, 4, 1, 2);
        GridPane.setValignment(sp_cuentadebito, VPos.TOP);
        GridPane.setValignment(sp_cuentacredito, VPos.TOP);
        GridPane.setHalignment(totaldebito, HPos.RIGHT);
        GridPane.setHalignment(totalcredito, HPos.RIGHT);
  
        GridPane.setHalignment(Tf_cliente, HPos.LEFT);
        GridPane.setHalignment(la_buscar, HPos.CENTER);
        GridPane.setColumnSpan(tv_generic, 3);
        
       
       //Traer   datos desde la BD
        getRecords();
       
        gp_generic.setMinSize(1340, 630);
       
             //agregamos el formulario y la tabla
        gp_generic.getStyleClass().add("background");
       
       gp_generic.getStylesheets().add(SihicApp.hojaestilos);
      StackPane stack=new StackPane();
        
          tp_generic.setContent(gp_generic);
        stack.setAlignment(Pos.TOP_CENTER);
       
           stack.getChildren().addAll(tp_generic,Gp_Message);
        return stack;
       }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
          javafx.scene.Group g =
                new javafx.scene.Group(imageView);
   
        return g;
    }
 
 private void getRecords() throws ParseException
 {
        //colocamos los datos
     li_facturas=SihicApp.s_facturaControllerClient.cartera(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString(),dp_desde.getValue().toString(),dp_hasta.getValue().toString(),documento.getText().trim(),tf_nofactura.getText().trim());
    System.out.println("size li facturas->"+li_facturas.size());
     ol_generic.clear();
     total=BigDecimal.ZERO;
     ol_generic.addAll(li_facturas );
     tv_generic.setItems(ol_generic);
       
      
      for(Factura f:  li_facturas)
      {
          total=total.add(f.getTotalAmount().subtract(f.getValor_abonos()));
      }
      
      Tf_total.setText(String.valueOf(total.toBigInteger()));
      
 }
 private void getRecords2() throws ParseException
 {
        //colocamos los datos
     li_facturas=SihicApp.s_facturaControllerClient.cartera(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString(),dp_desde.getValue().toString(),dp_hasta.getValue().toString(),"xxxxxxx",tf_nofactura.getText().trim());
    System.out.println("size li facturas->"+li_facturas.size());
     ol_generic.clear();
     total=BigDecimal.ZERO;
     ol_generic.addAll(li_facturas );
     tv_generic.setItems(ol_generic);
       
      
      for(Factura f:  li_facturas)
      {
          total=total.add(f.getTotalAmount().subtract(f.getValor_abonos()));
      }
      
      Tf_total.setText(String.valueOf(total.toBigInteger()));
      
 }
   @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
public  void exportar_excel() throws IOException, WriteException, ParseException
  {
     
        String rutaArchivo = System.getProperty("user.home")+"/Cartera.xls"; 
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        if(archivoXLS.exists()) 
        {
            archivoXLS.delete(); 
             archivoXLS.createNewFile();
        }
        WritableWorkbook libro;
         FileOutputStream archivo = new FileOutputStream(archivoXLS);
        libro = Workbook.createWorkbook(archivo);
        WritableSheet hoja = libro.createSheet("Mi hoja de trabajo 1",0);
         int f=0;
         
                
         for(Factura factura: li_facturas)
         {
           
           
            
             
            
              if(f==0)
             {
                  
                
                 
                // CellType.LABEL;
                 WritableCellFeatures writableCellFeatures=new WritableCellFeatures();
                 writableCellFeatures.removeDataValidation();
                 label=null;
                 label=new jxl.write.Label(f, f, "N° Factura");
                 hoja.addCell(label);
              
               
               
                 
                 label=null;
                 label=new jxl.write.Label(1, f, "No Ident");
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(2, f, "Nombre");
                 hoja.addCell(label);
               
              
                 label=null;
                 label=new jxl.write.Label(3, f, "Valor Factura");
                 hoja.addCell(label);
                 label=null;
                 label=new jxl.write.Label(4, f, "Valor Abonos");
                 hoja.addCell(label);
                 label=null;
                 label=new jxl.write.Label(5, f, "Valor Saldo");
                 hoja.addCell(label);
                 label=null;
                 label=new jxl.write.Label(6, f, "Fecha Elab Factura");
                 hoja.addCell(label);
                 label=null;
                 label=new jxl.write.Label(7, f, "Días de Mora");
                 hoja.addCell(label);
                  label=null;
                 label=new jxl.write.Label(8, f, "Celular");
                 hoja.addCell(label);
             }
               f++;
                 hoja.insertRow(f);
                 label=null;
                 label=new jxl.write.Label(0, f, factura.getNofacturacerosizquierda());
                 hoja.addCell(label);
               try{
                 
                 label=null;
                 label=new jxl.write.Label(1, f, factura.getFacturaItems().get(0).getGenPacientes().getGenEapb().getNit());
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(2, f, factura.getFacturaItems().get(0).getGenPacientes().getGenEapb().getNombre());
                 hoja.addCell(label);
               }catch(Exception e)
               {
                 
                   label=null;
                 label=new jxl.write.Label(1, f, factura.getFacturaItems().get(0).getGenPacientes().getGenPersonas().getDocumento());
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(2, f, factura.getFacturaItems().get(0).getGenPacientes().getGenPersonas().getNombres());
                 hoja.addCell(label);
               }
                 
                number=null;
                 number=new jxl.write.Number(3, f, factura.getTotalAmount().floatValue());
                 hoja.addCell(number);
               number=null;
                 number=new jxl.write.Number(4, f, factura.getValor_abonos().floatValue());
                 hoja.addCell(number);
             
                number=null;
                 number=new jxl.write.Number(5, f, (factura.getTotalAmount().subtract(factura.getValor_abonos())).floatValue());
                 hoja.addCell(number);
              
                 label=null;
                 label=new jxl.write.Label(6, f,UtilDate.formatodiamesyear2(factura.getFacturaDate()));
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(7, f, String.valueOf(((new Date().getTime()-factura.getFacturaDate().getTime())/86400000)));
                 hoja.addCell(label);
                 try{
                 label=null;
                 label=new jxl.write.Label(8, f, factura.getFacturaItems().get(0).getGenPacientes().getGenEapb().getTelefono());
                 hoja.addCell(label);
                 }catch(Exception e)
                 {
                 label=null;
                 label=new jxl.write.Label(8, f, factura.getFacturaItems().get(0).getGenPacientes().getGenPersonas().getTelefono());
                 hoja.addCell(label);
                 
                 }
         }
         libro.write();
         libro.close();
         archivo.close();
         Desktop.getDesktop().open(archivoXLS);
    } 
private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
   {
       if ((tv_generic.getSelectionModel())!=null)
       {
           SihicApp.s_factura=(Factura)tv_generic.getSelectionModel().getSelectedItem();
         
       }
   }
 private void showAbonosFactura() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  
    appClassGeneric="sihic.contabilidad.documentos.AdminAbonosFacturaCliente";
    clzGeneric = Class.forName(appClassGeneric);
    appGeneric = clzGeneric.newInstance();
    parentGeneric=null;
    parentGeneric = (Parent) clzGeneric.getMethod("createContent").invoke(appGeneric);
    sceneFactura=null;
    sceneFactura=new Scene(parentGeneric, Color.TRANSPARENT);
      
        if (stageGeneric.isShowing())
       {
           stageGeneric.close();
       }      
        
        
//set scene to stage
                stageGeneric.sizeToScene();
                
              
                //center stage on screen
                stageGeneric.centerOnScreen();
                stageGeneric.setScene(sceneFactura);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stageGeneric.show();
  }  
 private void showcomprobantediario() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  
    appClassGeneric="sihic.contabilidad.documentos.FComprobanteContabilidad";
    clzGeneric = Class.forName(appClassGeneric);
    appGeneric = clzGeneric.newInstance();
    parentGeneric=null;
    parentGeneric = (Parent) clzGeneric.getMethod("createContent").invoke(appGeneric);
    sceneFactura=null;
    sceneFactura=new Scene(parentGeneric, Color.TRANSPARENT);
      
        if (stageGeneric.isShowing())
       {
           stageGeneric.close();
       }      
        
        
//set scene to stage
                stageGeneric.sizeToScene();
                
              
                //center stage on screen
                stageGeneric.centerOnScreen();
                stageGeneric.setScene(sceneFactura);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stageGeneric.show();
  }   
    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
 
 private static void SetMessage() throws InterruptedException
 {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }
   private static void AddDetalle() throws ParseException, InterruptedException {
       
    }
 public void procesaracontabilidadcomprobantesingresofacturas()
 {
     
 } 
  public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {
            
            System.out.println("Valor pre->" + observable.getValue() + "-" + newValue);
            
            
            getRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void  buscarnocomprobante() throws ParseException, InterruptedException
   {
       SihicApp.s_facturaControllerClient.findcomprobanteporno(nocomprobante.getText().trim());
       
       
   }

}   

