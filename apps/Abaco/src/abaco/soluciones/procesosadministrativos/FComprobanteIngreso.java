package  abaco.soluciones.procesosadministrativos;
import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import static abaco.AbacoApp.s_factura;
import abaco.PageBrowser;
import abaco.SearchPopover;
import abaco.control.SearchBox;
import abaco.soluciones.administrador.ImpresionFactura;
import abaco.soluciones.reportes.ImpresoraTermica;
import abaco.util.UtilDate;
import com.aquafx_project.AquaFx;
import fields.FieldNumero;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import jfxtras.styles.jmetro8.JMetro;
import message.EstadosMensaje;
import model.ConComprobanteIngreso;
import model.ConDetallesComprobanteContabilidad;

import model.ConPuc;
import model.Factura;
import model.FacturaItem;
import org.aerofx.AeroFX;



/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FComprobanteIngreso extends Application{
    StackPane stack;
   
    private  Label la_fechaelaboracion;
    private  Label la_recibido;
    private  Label la_nit;
    private  Label la_direccion;
    private  Label  la_telefono;
    private  Label la_norecibo;
    private  Label la_valor;
    private  Label la_concepto;
    private  Label la_nofactura;
    private  Label la_nocheque;
    private  Label la_notargeta;
    private  Label la_tipotargeta;
    private  Label la_banco;
    private  Label la_codcuenta;
    private  Label la_cuenta;
    private  Label la_debito;;
    private  Label la_credito;
    private  TextField codcuenta;
    private  TextField cuenta;
    private  TextField debito;
    private  TextField credito;
    private  DatePicker fechaelaboracion=new DatePicker(LocalDate.now());
    private  TextField recibido;
    private  TextField nit;
    private  TextField concepto;
    private  FieldNumero valor;
    private  TextField direccion;
    private  TextField telefono;
    private  TextField norecibo;
    private  TextField nofactura;
    private  TextField nocheque;
    private  TextField notargeta;
    private ChoiceBox  tipotargeta;
    private  TextField banco;
    private Button bu_save;
    private Button bu_nuevo;
    private Button findfactura;
    private Button print;
    EstadosMensaje estadosMensaje;
    private HBox hb_botones;
    private boolean permitirproceso=false;
    private ImageView imageView;
    private ObservableList ol_conpuc=FXCollections.observableArrayList();;
    private List<ConPuc>  l_conpuc;
    private GridPane Gp_Message;
    private     Label L_Message;
    private  Runnable Task_Message;
    Thread backgroundThread;
    private GridPane Gp_Generic;
     private TitledPane tp_generic;
    private static Stage  stage = new Stage(StageStyle.DECORATED);

    private FacturaItem facturaitem;
    private ImpresionFactura impresionFactura;
    private String cerosizq="";
    private String cerosizqcomprobante="";
    private Long no_factura=Long.MIN_VALUE;
    private Long nocomprobante=Long.MIN_VALUE;
    private Scene scene;
    private Button bu_printcomprobante;
    private Parent parent ;
    private String appPath;
    private Class  clz;
    private Object appClass;
    private HBox hbox;
    private DecimalFormat format = new DecimalFormat( "#,0" );
    private String caja="11050500001";
    private String clienteapagar="13050500001";
    private ObservableList ol_detalles=FXCollections.observableArrayList();;
    private List<ConDetallesComprobanteContabilidad>  l_detalles;
    private SearchBox sb_factura=new SearchBox();
    private SearchPopover sp_factura;
    private RadioButton Rb_Factura;
    private RadioButton Rb_Comprobante;
    private ToggleGroup toggleGroup;
    HBox hb_tipopago;
    private TableView tv_detallescomprobantecontabilidad;
    private ObservableList ol_detallescomprobante=FXCollections.observableArrayList();;
    private List<ConDetallesComprobanteContabilidad>  l_detallescomprobante;
    private List<ConPuc> li_conPucs;
    private ImpresoraTermica impresoraTermica=new ImpresoraTermica();
    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException { 
    tv_detallescomprobantecontabilidad=new TableView();
    if(abaco.AbacoApp.s_conComprobanteIngreso==null)
    {
    abaco.AbacoApp.s_conComprobanteIngreso=new ConComprobanteIngreso();
    }
    TableColumn ccodigo = new TableColumn();
        ccodigo.setMinWidth(100);
        ccodigo.setText("Código");
        ccodigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String> detalles) {
               
                    return new SimpleStringProperty((detalles.getValue().getConPuc().getCodigo()));
                            
            }
        });
        TableColumn ccuenta = new TableColumn();
        ccuenta.setMinWidth(250);
        ccuenta.setText("Cuenta");
        ccuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String> detalles) {
               
                    return new SimpleStringProperty(detalles.getValue().getConPuc().getNombre());
                            
            }
        });
         TableColumn cdetalle = new TableColumn();
        cdetalle.setMinWidth(300);
        cdetalle.setText("Detalle");
        cdetalle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String> detalles) {
               
                    return new SimpleStringProperty(detalles.getValue().getDescripcion());
                            
            }
        });
        TableColumn cparcialesdebe = new TableColumn();
        cparcialesdebe.setMinWidth(100);
        cparcialesdebe.setText("Debe");
        cparcialesdebe.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String> detalles) {
               
                    return new SimpleStringProperty(detalles.getValue().getParcialdebe().toString());
                            
            }
        });
        TableColumn cparcialeshaber = new TableColumn();
        cparcialeshaber.setMinWidth(100);
        cparcialeshaber.setText("Haber");
        cparcialeshaber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String> detalles) {
               
                    return new SimpleStringProperty(detalles.getValue().getParcialhaber().toString());
                            
            }
        });
        tv_detallescomprobantecontabilidad.getColumns().addAll(ccodigo,ccuenta,cdetalle,cparcialesdebe,cparcialeshaber);
        tv_detallescomprobantecontabilidad.setMaxHeight(150);
        tv_detallescomprobantecontabilidad.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
   
       toggleGroup=new ToggleGroup();
       Rb_Factura=new RadioButton("Factura");
       Rb_Factura.setMinWidth(100);
       Rb_Factura.setToggleGroup(toggleGroup);
       Rb_Factura.setStyle("-fx-text-fill: white;");
       Rb_Comprobante=new RadioButton("Copagos y Cuota Mod");
       Rb_Comprobante.setMinWidth(100);
       Rb_Comprobante.setToggleGroup(toggleGroup);
       Rb_Comprobante.setStyle("-fx-text-fill: white;");
       hb_tipopago=new HBox();
       hb_tipopago.setSpacing(5);//espacio verticalmente entre objetos
       hb_tipopago.getChildren().addAll(Rb_Factura,Rb_Comprobante);
       sp_factura=new SearchPopover(sb_factura,new PageBrowser(), AbacoApp.s_factura,false);
       sb_factura.setMinWidth(200);
       sb_factura.setMaxWidth(200);
       sp_factura.setMaxWidth(200);
       sp_factura.setMinWidth(200);
       impresionFactura=new ImpresionFactura(s_factura);
               sb_factura.setOnAction(e->{
              if(s_factura.getId()!=null)
                    {
                    colocardatosfactura();
                    concepto.requestFocus();
                    }  
              
              });
            
             
     Gp_Generic=new GridPane();
     imageView=new ImageView("/images/new2.png");
     imageView.setFitHeight(40);
     imageView.setFitWidth(40);
    imageView=new ImageView("/images/save.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        bu_save =new Button("",imageView);
        bu_save.setOnKeyPressed(e->{
           if(e.getCode().equals(e.getCode().ENTER))
           {
               try{
              save();
               }catch(Exception e3)
               {
                   
               }
           }
        });
         imageView=null;
        imageView=new ImageView("/images/new2.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        bu_nuevo=new Button("",imageView);
        bu_nuevo.setOnAction(e->{
         nuevo();
        });
        bu_nuevo.setOnKeyPressed(e->{
          if(e.getCode().equals(e.getCode().ENTER))
          {
              nuevo();
          }
        });
         imageView=null;
        
        imageView=null;
        imageView=new ImageView("/images/print.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        bu_printcomprobante=new Button("",imageView);
        bu_printcomprobante.setOnAction(e->
          {
           try {
              
                impresoraTermica.imprimirreciboingreso();
              
             
             
           } catch (Exception ex) {
             ex.printStackTrace();
           }
          });
        
        imageView=new ImageView("/images/find.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        findfactura=new Button("",imageView);
        findfactura.setOnAction(e->
          {
           try {
              
            show();
           } catch (Exception ex) {
             
           }
          });
        findfactura.setOnKeyPressed(e->{
          if(e.getCode().equals(e.getCode().ENTER))
          {
              try {
                  show();
              } catch(Exception e2){
                  
              }
          }
        });
              
          
      //***********************************************  
        estadosMensaje=new EstadosMensaje();
        stack = new StackPane();
       
        la_direccion=new Label("Dirección:");
        la_fechaelaboracion=new Label("Fecha:");
        la_nit=new Label("Nit:");
        la_norecibo=new Label("N° Recibo:");
        la_telefono=new Label("Telefono:");
        la_concepto=new Label("Concepto:");
        la_nocheque=new Label("Cheque:");
        la_nofactura=new Label("N° Factura:");
        la_tipotargeta=new Label("Tipo Targeta:");
        la_banco=new Label("Banco:");
        la_valor=new Label("Valor:");
        la_recibido=new Label("Recibido:");
        la_notargeta=new Label("Targeta:");
        la_codcuenta=new Label("Codigo:");
        la_cuenta=new Label("Cuenta:");
        la_debito=new Label("Debito:");
        la_credito=new Label("Credito:");
       
       
        direccion=new TextField();
        
       
        nit=new TextField();
        nit.getProperties().put("requerido", true);
        norecibo=new TextField();
        norecibo.setDisable(true);
        telefono=new TextField();
        concepto=new TextField();
        concepto.getProperties().put("requerido", true);
        concepto.setOnAction(e->{
           codcuenta.requestFocus();
        });
        nocheque=new TextField();
        nofactura=new TextField();
        nofactura.getProperties().put("requerido", true);
        notargeta=new TextField();
        tipotargeta=new ChoiceBox();
        tipotargeta.setMinWidth(160);
        tipotargeta.getItems().add(0, "Débito");
        tipotargeta.getItems().add(1, "Crédito");
        tipotargeta.getSelectionModel().select(-1);
        banco=new TextField();
        valor=new FieldNumero(10);
        debito=new TextField();
        cuenta=new TextField();
        debito.getProperties().put("requerido", true);
        debito.setOnAction(e->{
                
                credito.requestFocus();
        });
        debito.setTextFormatter( new TextFormatter<>(c ->
        {
      if ( c.getControlNewText().isEmpty() )
       {
        return c;
    }

    ParsePosition parsePosition = new ParsePosition( 0 );
    Object object = format.parse( c.getControlNewText(), parsePosition );

    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
    {
        return null;
    }
    else
    {
        return c;
    }
}));
        credito=new TextField();
        credito.getProperties().put("requerido", true);
        credito.setOnAction(e->{
                
                bu_save.requestFocus();
        });
        credito.setTextFormatter( new TextFormatter<>(c ->
        {
      if ( c.getControlNewText().isEmpty() )
       {
        return c;
    }

    ParsePosition parsePosition = new ParsePosition( 0 );
    Object object = format.parse( c.getControlNewText(), parsePosition );

    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
    {
        return null;
    }
    else
    {
        return c;
    }
}));
        codcuenta=new TextField();
        codcuenta.getProperties().put("requerido", true);
        codcuenta.setOnAction(e->{
                findConPuc();
                debito.requestFocus();
        });
       
        recibido=new TextField();
        recibido.getProperties().put("requerido", true);
       
       
        hb_botones=new HBox(10);
        hbox=new HBox(4, nofactura,findfactura);
        hb_botones.getChildren().addAll(bu_save,bu_nuevo,bu_printcomprobante);
        bu_save.setOnAction(e->{try {
            try {
                save();
            } catch (ParseException ex) {
            }
              } catch (InterruptedException ex) {
                 
              }
});
        Gp_Generic.add(la_norecibo,0,0,1,1);
        Gp_Generic.add(norecibo,1,0,3,1);
        Gp_Generic.add(la_nofactura,0,1,1,1);
        Gp_Generic.add(sb_factura,1,1,1,1);
        Gp_Generic.addRow(2,la_fechaelaboracion,fechaelaboracion);
        Gp_Generic.addRow(3,la_nit,nit);
        Gp_Generic.addRow(4,la_telefono, telefono);
       
        Gp_Generic.addRow(5,la_recibido,recibido);
         Gp_Generic.addRow(6,la_valor,valor);
     
      
        Gp_Generic.addRow(7,la_concepto,concepto); 
        Gp_Generic.add(hb_botones,0,8,2,1); 
      //  Gp_Generic.add(tv_detallescomprobantecontabilidad,0,11,4,1); 
        Gp_Generic.add(sp_factura, 1, 2,1,8);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
         GridPane.setHalignment(tv_detallescomprobantecontabilidad, HPos.LEFT);
        Gp_Generic.setHgap(5);
        Gp_Generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
       // Gp_Generic.getStylesheets().add("/abaco/SofackarStylesCommon.css"); 
      //  Gp_Generic.getStyleClass().add("background");
        Gp_Message=new GridPane();
       
        Gp_Message.setMinSize(Gp_Generic.getLayoutBounds().getWidth(),40);
        L_Message=new Label();
        L_Message.getStylesheets().add("/abaco/SofackarStylesCommon.css"); 
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setMaxSize(150, 25);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
       
       Gp_Generic.setMaxWidth(870);
        //stack.setMinSize(870, 510);
        //Gp_Generic.setMaxSize(870, 510);
        //stack.setMaxSize(870, 510);
        stack.getChildren().addAll(Gp_Generic,Gp_Message);
       switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(Gp_Generic);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(Gp_Generic);
                     break;              
         }
       if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()!=0)
        {
          bu_nuevo.setStyle("-fx-background-color:#007fff");
          bu_printcomprobante.setStyle("-fx-background-color:#007fff");
          bu_save.setStyle("-fx-background-color:#007fff");
                 
        }
        stack.setAlignment(Pos.TOP_CENTER);
        Gp_Generic.setAlignment(Pos.TOP_CENTER);
       // stack.autosize();
      
      // disablesAll();
      
        //  disablesAll();
           KeyCombination kn = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
           Runnable task = () ->nuevo();
            KeyCombination kg = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
           Runnable task2 = () ->{
          try {
              save();
          } catch (InterruptedException ex) {
              Logger.getLogger(FComprobanteIngreso.class.getName()).log(Level.SEVERE, null, ex);
          } catch (ParseException ex) {
              Logger.getLogger(FComprobanteIngreso.class.getName()).log(Level.SEVERE, null, ex);
          }
      };
            KeyCombination kc = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
            Runnable task3 = () ->cerrarcomprobante();
        // Mnemonic mnemonic = new Mnemonic(B_New, kc);
           AbacoApp.getScene().getAccelerators().clear();
           AbacoApp.getScene().getAccelerators().put(kn, task);
           AbacoApp.getScene().getAccelerators().put(kg, task2);
           AbacoApp.getScene().getAccelerators().put(kc, task3);
         
          setDatosFactura();
        return stack;
    }
  
 
   

public void nuevo()
{
    
    
    direccion.setText("");
    fechaelaboracion.setValue(LocalDate.now());
    AbacoApp.s_conComprobanteIngreso=null;
    AbacoApp.s_conComprobanteIngreso=new ConComprobanteIngreso();
    nit.setText("");
    norecibo.setText("");
    telefono.setText("");
    concepto.setText("");
    nocheque.setText("");
    nofactura.setText("");
    tipotargeta.getSelectionModel().select(-1);
    banco.setText("");
    valor.setText("0");
    recibido.setText("");
    SimpleDateFormat  df=new SimpleDateFormat("yyyy-MM-dd");
    l_detalles=null;
    l_detalles=new ArrayList<>();
    ol_detalles.clear();
    ol_detalles.addAll(l_detalles.toArray());
  
    findfactura.requestFocus();
}
public void save() throws InterruptedException, ParseException
{ validar_formulario();
  if(permitirproceso)
  {
  set_datos();
  try{
  
     AbacoApp.s_conComprobanteIngreso=AbacoApp.conComprobanteIngresoControllerClient.edit(AbacoApp.s_conComprobanteIngreso,AbacoApp.s_factura);
     
 
   if(AbacoApp.s_factura!=null)
{
 if(AbacoApp.s_factura.getId()!=null)
 {
     abaco.AbacoApp.s_factura.addAbono(AbacoApp.s_conComprobanteIngreso);
     AbacoApp.facturaControllerClient.update();
 }
 else
 {
   abaco.AbacoApp.s_conComprobanteIngreso.setFactura(null);  
 }
}
 else
 {
       abaco.AbacoApp.s_conComprobanteIngreso.setFactura(null);
 }
    
    norecibo.setText(AbacoApp.s_conComprobanteIngreso.getNocomprobantecerosizquierda());
    
    L_Message.setText("Registro Almacenado"); 
    Task_Message = () -> {
         try {
             System.out.println("solucion_>"+AbacoApp.nombresolucion);
             if(AbacoApp.nombresolucion.equals("Recibo Ingreso"))
             {
                 AdminComprobanteIngreso.getRecords();
             }
             SetMessage();
         } catch (InterruptedException ex) {
             ex.printStackTrace();
         } catch (ParseException ex) {
            Logger.getLogger(FComprobanteIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }
     };
       backgroundThread = new Thread(Task_Message);
       backgroundThread.setDaemon(true);
       backgroundThread.start();
  }catch(Exception e)
  {
      e.printStackTrace();
     L_Message.setText("Error Almacenando"); 
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
  
  
 
} 

private void set_datos()
{
 int i=0;
 
 abaco.AbacoApp.s_conComprobanteIngreso.setFechaelaboracion(UtilDate.localdatetodate(fechaelaboracion.getValue()));
 abaco.AbacoApp.s_conComprobanteIngreso.setBanco(banco.getText());
 abaco.AbacoApp.s_conComprobanteIngreso.setObservaciones(recibido.getText());
 abaco.AbacoApp.s_conComprobanteIngreso.setUsuarioModificador(nit.getText());
 abaco.AbacoApp.s_conComprobanteIngreso.setConcepto(concepto.getText());
 abaco.AbacoApp.s_conComprobanteIngreso.setValor(new BigDecimal(valor.getText().trim()));
 abaco.AbacoApp.s_conComprobanteIngreso.setNocheque(nocheque.getText());
 abaco.AbacoApp.s_conComprobanteIngreso.setNotargeta(notargeta.getText());
 abaco.AbacoApp.s_conComprobanteIngreso.setTipotargeta(tipotargeta.getSelectionModel().getSelectedIndex());
  
 
 }

public void validar_formulario() throws InterruptedException
{
    permitirproceso=true;
      for(Object n:Gp_Generic.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            if(general.getProperties().get("requerido")!=null)
            {
            if(general.getProperties()!=null)
            {    
            if((general.getText().length()<=0) && ((boolean)general.getProperties().get("requerido")==true))
            {
                  permitirproceso=false; 
                  general.setStyle("-fx-background-color:#ff1600");
                  
            }
            }
            }
           
        }
        else
        {
             if(n.getClass()==ChoiceBox.class)
        {
            ChoiceBox general=(ChoiceBox)n;
           
         if(general.getProperties().get("requerido")!=null)
         {
                  permitirproceso=false; 
                  general.getStylesheets().add(0,"/sihic/personal.css");
               general.getStylesheets().set(0,"/sihic/personal.css");
         }  
           
        }
        }
        }
     FadeTransition ft = new FadeTransition(Duration.millis(2000),bu_save);
        //ft.setFromValue(0.0);
       // ft.setToValue(1);
      
        ft.play();
       
        //success.setOpacity(0);
      
       ft.setOnFinished(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
              for(Object n:Gp_Generic.getChildren().toArray())
              {
             if(n.getClass()==TextField.class)
             {
            TextField general=(TextField)n;
            if(general.getProperties().get("requerido")!=null)
            {
                System.out.println("propiedad-->"+general.getProperties().get("requerido"));
            if(((boolean)general.getProperties().get("requerido")==true))
            {
                 
                  general.setStyle(null);
                  general.getStyleClass().add("text-field");
            }
            }
           
        }  
         else
        {
             if(n.getClass()==ChoiceBox.class)
        {
            ChoiceBox general=(ChoiceBox)n;
           
         if(general.getProperties().get("requerido")!=null)
         {
             general.getStylesheets().set(0,"/sihic/SofackarStylesCommon.css");
             
         }
           
        }
        }
        }
        }});
        
         
}


 private void   SetMessage() throws InterruptedException
    {
       
       Gp_Message.setVisible(true);
       //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
         Thread.sleep(3000);
         Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
         Task_Message=null;
       
    }
 public void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
  {
     
       scene=null;
       parent=null;
       parent = (Parent) clz.getMethod("createContent").invoke(appClass);
     
      scene=new Scene(parent, Color.TRANSPARENT);
       

        if (stage.isShowing())
       {
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

  
   public void setDatosFactura()
   {
       
        if(AbacoApp.s_conComprobanteIngreso!=null)
       {
       if(AbacoApp.s_conComprobanteIngreso.getId()!=null)
       {
        fechaelaboracion.setValue(UtilDate.formatoyearmesdia(abaco.AbacoApp.s_conComprobanteIngreso.getFechaelaboracion()));
        banco.setText(abaco.AbacoApp.s_conComprobanteIngreso.getBanco());
        concepto.setText(abaco.AbacoApp.s_conComprobanteIngreso.getConcepto());
        valor.setText(abaco.AbacoApp.s_conComprobanteIngreso.getValor().toString());
        nocheque.setText(abaco.AbacoApp.s_conComprobanteIngreso.getNocheque());
        notargeta.setText(abaco.AbacoApp.s_conComprobanteIngreso.getNotargeta());
        norecibo.setText(abaco.AbacoApp.s_conComprobanteIngreso.getNocomprobantecerosizquierda());
        tipotargeta.getSelectionModel().select(abaco.AbacoApp.s_conComprobanteIngreso.getTipotargeta());
        recibido.setText(abaco.AbacoApp.s_conComprobanteIngreso.getObservaciones());
        nit.setText(abaco.AbacoApp.s_conComprobanteIngreso.getUsuarioModificador());
        norecibo.setText(abaco.AbacoApp.s_conComprobanteIngreso.getNocomprobantecerosizquierda());
        if(abaco.AbacoApp.s_conComprobanteIngreso.getFactura()!=null)
        {
          if(abaco.AbacoApp.s_conComprobanteIngreso.getFactura().getLastNumberNoFactura()!=null)
        {  
            colocardatosfactura();
        }
        }    
                
       
       }
       
       else
       {
           nuevo();
       }
       }
        else
        {
            nuevo();
        }
     if(AbacoApp.nombresolucion.equals("Admin. Factura") || AbacoApp.nombresolucion.equals("Cartera"))  
     {    
      if(AbacoApp.s_factura!=null)
       {
        if(AbacoApp.s_factura.getId()!=null)
       { 
           if(AbacoApp.s_factura.getNo_factura()!=null)
           {     
              colocardatosfactura();
             
           }                 
       }
       }
     }
   }
   public void enableAll()
   {
     for(Object n:Gp_Generic.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            general.setDisable(false);
        }
        if(n.getClass()==ChoiceBox.class)
        {
            ChoiceBox general=(ChoiceBox)n;
            general.setDisable(false);
        }
        if(n.getClass()==Button.class)
        {
            Button general=(Button)n;
            general.setDisable(false);
        }
        if(n.getClass()==SearchBox.class)
        {
            SearchBox general=(SearchBox)n;
            general.setDisable(false);
        }
         findfactura.setDisable(false);
    }
}
   public void disablesAll()
   {
       bu_printcomprobante.setDisable(true);
       bu_save.setDisable(true);
 
     for(Object n:Gp_Generic.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            general.setDisable(true);
        }
        if(n.getClass()==ChoiceBox.class)
        {
            ChoiceBox general=(ChoiceBox)n;
            general.setDisable(true);
        }
        if(n.getClass()==Button.class)
        {
            Button general=(Button)n;
            general.setDisable(true);
        }
         if(n.getClass()==SearchBox.class)
        {
            SearchBox general=(SearchBox)n;
            general.setDisable(true);
        }
         findfactura.setDisable(true);
    }
}
   public void AddDetalle() throws ParseException, InterruptedException
  {
      AbacoApp.conComprobanteContabilidad.getDetallesComprobanteContabilidads().clear();
      // AbacoApp.conComprobanteContabilidad.getDetallesComprobanteContabilidads().clear();
       searchPuc("110505");
       AbacoApp.s_conPuc=li_conPucs.size()>0?li_conPucs.get(0):null;
      if(AbacoApp.s_conPuc!=null)
      {
       try{
        AbacoApp.conComprobanteContabilidad.addPuc(AbacoApp.s_conPuc,"Abona a N° Factura:"+AbacoApp.s_factura.getNofacturacerosizquierda(),new BigDecimal(valor.getText().trim()),true);
       }catch(Exception e)
                {
                 AbacoApp.conComprobanteContabilidad.addPuc(AbacoApp.s_conPuc,"Abona a N° Factura Antigua que no registra en el sistemas",new BigDecimal(valor.getText().trim()),true);
       
                }
     
      }
         searchPuc("130505");
       AbacoApp.s_conPuc=li_conPucs.size()>0?li_conPucs.get(0):null;
      if(AbacoApp.s_conPuc!=null)
      {
          try{  
            AbacoApp.conComprobanteContabilidad.addPuc(AbacoApp.s_conPuc,"Abona a N° Factura:"+AbacoApp.s_factura.getNofacturacerosizquierda(),new BigDecimal(valor.getText().trim()),false);
 }catch(Exception e)
                {
                 AbacoApp.conComprobanteContabilidad.addPuc(AbacoApp.s_conPuc,"Abona a N° Factura Antigua que no registra en el sistemas",new BigDecimal(valor.getText().trim()),true);
       
                }
      }
     
      
  }
  public void findConPuc()
  {
     AbacoApp.s_conPuc=AbacoApp.conPucControllerClient.findConPuc(codcuenta.getText().trim());
     if(AbacoApp.s_conPuc!=null)
     {
         cuenta.setText(AbacoApp.s_conPuc.getNombre());
     }
  }
  private void refreshdetalles()
  {
        l_detalles=AbacoApp.s_conComprobanteIngreso.getConComprobanteContabilidad().getDetallesComprobanteContabilidads();
        ol_detalles.clear();
        ol_detalles.addAll(l_detalles.toArray());
        tv_detallescomprobantecontabilidad.setItems(ol_detalles);
      
       
  }
  private void cerrarcomprobante()
  {
      bu_save.setDisable(true);
      disablesAll();
      bu_nuevo.setDisable(false);
      bu_nuevo.requestFocus();
       l_detalles=null;
       l_detalles=new ArrayList<>();
       ol_detalles.clear();
       ol_detalles.addAll(l_detalles.toArray());
       
  }
  
  
    public static void main(String[] args) {
        launch(args);
    }
public static void closeStage()
{
   stage.close();
}
    @Override
    public void start(Stage primaryStage) throws Exception {
    primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();  }
    
  public void searchPuc(String searchString) throws ParseException {
     
      li_conPucs= AbacoApp.li_conpuc.stream().filter(line -> line.getCodigo().toUpperCase().equals(searchString))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());	
      
   }
  public void colocardatosfactura()
  {
            la_nofactura.setText("N° Factura: ");
            nofactura.setText(AbacoApp.s_factura.getNofacturacerosizquierda());
            recibido.setText(AbacoApp.s_factura.getCustomer().getNombre());
            nit.setText(AbacoApp.s_factura.getCustomer().getNo_ident());
            concepto.setText("Pago a Factura:"+nofactura.getText());
            telefono.setText(AbacoApp.s_factura.getCustomer().getCelular());
            direccion.setText(AbacoApp.s_factura.getCustomer().getDir1());
            sb_factura.setText(AbacoApp.s_factura.getNofacturacerosizquierda()+"||"+AbacoApp.s_factura.getCustomer().getNombre());
      
  }
  
}