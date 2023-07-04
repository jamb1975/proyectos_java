/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.procesosadministrativos;

import static abaco.AbacoApp.cb_temas;
import abaco.soluciones.administrador.ImpresionFactura;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.ConComprobanteContabilidad;
import model.ConDetallesComprobanteContabilidad;
import model.ConPuc;
import abaco.controlador.ConPucControllerClient;
import com.aquafx_project.AquaFx;
import jfxtras.styles.jmetro8.JMetro;
import org.aerofx.AeroFX;



/**
 *
 * @author adminlinux
 */
public class FComprobanteContabilidad  extends Application{

    private GridPane  gp_generico;
    private TableView ta_detallescomprobantecontabilidad;
    private Label la_nocomprobante;
    private TextField nocomprobante;
    private Label la_tipocomprobante;
    private ChoiceBox tipocomprobante;
    private Label la_fechaelaboracion;
    private DatePicker fechaelaboracion;
    private ObservableList ol_detallescomprobante=FXCollections.observableArrayList();
    private List<ConDetallesComprobanteContabilidad>  l_detallescomprobante;
    private ConPucControllerClient conPucControllerClient;
    private Button buscar;
    private Button trasladar;
    private Button bu_printcomprobante;
    private Button puc;
    private Button eliminarDet;
    private HBox hb_botones;
    private ConComprobanteContabilidad conComprobanteContabilidad;
    private ConPuc conPuc; 
    private boolean permitirproceso=false;
    private GridPane Gp_Message;
    private     Label L_Message;
    private  Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
    private ImpresionFactura impresionFactura;
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException  {
        stack=new StackPane();
        conPucControllerClient=new ConPucControllerClient();
       conComprobanteContabilidad=new ConComprobanteContabilidad();
      
       la_tipocomprobante=new Label("Tipo Comprobante: ");
       la_tipocomprobante.setMinWidth(130);
       la_fechaelaboracion=new Label("Fecha: ");
       la_fechaelaboracion.setMinWidth(50);
       
       
       
       tipocomprobante=new ChoiceBox();
       tipocomprobante.getItems().addAll("De Apertura o Inicial","De Ingresos","De Egresos","De Ventas","De Depreciaciones","De Amortizaciones","Compras y Gastos","Consignaciones","Activos Fjos","Honorarios");
       fechaelaboracion=new DatePicker(LocalDate.now());
       fechaelaboracion.setMinWidth(120);
       ImageView imageView;
       imageView=new ImageView("/images/pdf.png");
       imageView.setFitHeight(20);
       imageView.setFitWidth(20);
       bu_printcomprobante=new Button("",imageView);
       imageView=new ImageView("/images/find.png");
       imageView.setFitHeight(16);
       imageView.setFitWidth(16);
       bu_printcomprobante.setOnAction(e->{
       if(conComprobanteContabilidad.getId()!=null)
       {
               
                   
               }
           
           });
            imageView=new ImageView("/images/items.gif");
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            puc=new Button("",imageView);
            puc.setOnAction(e->{
                  //conPucControllerClient.setConPuc();
            });
           imageView=new ImageView("/images/find.png");
           imageView.setFitHeight(20);
           imageView.setFitWidth(20);
           buscar=new Button("",imageView);
           buscar.setDisable(false);
           buscar.setOnAction(e->
          {
           try {
            findcomprobante();
             
           } catch (Exception ex) {
           }
          });
          
            imageView=new ImageView("/images/trasladar.png");
           imageView.setFitHeight(20);
           imageView.setFitWidth(20);
             trasladar=new Button("",imageView);
             trasladar.setDisable(false);
             Tooltip tooltip=new Tooltip("Trasladar a Libro Auxiliar y diario");
                     
             trasladar.setTooltip(tooltip);
             trasladar.setOnAction(e->
          {
           try {
              if(conComprobanteContabilidad!=null)
              {
                  if(!conComprobanteContabilidad.getDetallesComprobanteContabilidads().isEmpty())
                  {
                      conPucControllerClient.trasladolibrosauxiliardiario(conComprobanteContabilidad);
                      trasladar.setDisable(true);
                     
                  }
              }
           } catch (Exception ex) {
           }
          });
            imageView=new ImageView("/images/delete.png");
           imageView.setFitHeight(16);
           imageView.setFitWidth(16);
           eliminarDet=new Button("",imageView);
           eliminarDet.setDisable(false);
           eliminarDet.setOnAction(e->
          {
           try {
               if(ta_detallescomprobantecontabilidad.getSelectionModel().getSelectedItem()!=null)
               {
                   removeDetalle();
               }
           } catch (Exception ex) {
              
           }
          });
            
            
            hb_botones=new HBox(buscar,trasladar,bu_printcomprobante);
            hb_botones.setSpacing(5);
            //**********Ventana Personas
       
    gp_generico=new GridPane();
    ta_detallescomprobantecontabilidad=new TableView(); 
    ta_detallescomprobantecontabilidad.setMinHeight(500);
    ta_detallescomprobantecontabilidad.setOnMouseClicked(e->{
          
    });
     
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
          TableColumn  titulotabla = new TableColumn<>("Parciales");
        titulotabla.getColumns().addAll(cparcialesdebe,cparcialeshaber);
       
        TableColumn cdebe = new TableColumn();
        cdebe.setMinWidth(100);
        cdebe.setText("Debe");
        cdebe.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String> detalles) {
               
                    return new SimpleStringProperty(detalles.getValue().getDebe().toString());
                            
            }
        });
        TableColumn chaber = new TableColumn();
        chaber.setMinWidth(100);
        chaber.setText("Haber");
        chaber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConDetallesComprobanteContabilidad, String> detalles) {
               
                    return new SimpleStringProperty(detalles.getValue().getHaber().toString());
                            
            }
        });
        ta_detallescomprobantecontabilidad.getColumns().addAll(ccodigo,ccuenta,cdetalle,titulotabla,cdebe,chaber);
        //ta_detallescomprobantecontabilidad.setMinWidth(650);
        ta_detallescomprobantecontabilidad.setOnMouseClicked(e->{
            try {
        checkregistroexistente();
           } catch (Exception ex) {
           }
});
         Gp_Message=new GridPane();
        Gp_Message=new GridPane();
        Gp_Message.setMinSize(gp_generico.getLayoutBounds().getHeight(),gp_generico.getLayoutBounds().getWidth());
        L_Message=new Label();
        L_Message.getStylesheets().add("/sihic/SofackarStylesCommon.css"); 
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
         L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
         GridPane.setValignment(L_Message, VPos.TOP);
         GridPane.setValignment(Gp_Message, VPos.TOP);
         Gp_Message.setVisible(false);
  
      //  gp_generico.getStylesheets().add("/sihic/SofackarStylesCommon.css");
      //  gp_generico.getStyleClass().add("background");
        gp_generico.setMinSize(1070,615);
        gp_generico.addRow(0,la_fechaelaboracion,fechaelaboracion,la_tipocomprobante,tipocomprobante,hb_botones);
        
        gp_generico.add(ta_detallescomprobantecontabilidad, 0, 3,7,1);
        
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
              gp_generico.autosize();
         
            
          ta_detallescomprobantecontabilidad.setMinHeight((gp_generico.getLayoutBounds().getHeight()-58));
                   
          
          
             ta_detallescomprobantecontabilidad.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico,Gp_Message);
         switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gp_generico);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gp_generico);
                     break;              
         }
        return stack;
  }
  
   private void  getDetallesComprobantes() throws ParseException
    {
         //colocamos los datos
       
       try{
      //   l_detallescomprobante=agnCitasControllerClient.getCitasAgendadas(buscar.getText().trim());
       }catch(Exception e)
       {
           e.printStackTrace();
       }
       ol_detallescomprobante.clear();
       
        ol_detallescomprobante.addAll(l_detallescomprobante.toArray());
       // Ta_KardexProducto.getItems().clear();
       ta_detallescomprobantecontabilidad.setItems(ol_detallescomprobante);
       
    }
   private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
   {
      
   }
  
   public void save() throws ParseException, InterruptedException
   {
       validar_formulario();
  if(permitirproceso)
  {
      try
      {
  set_datos_comprobante();
  conComprobanteContabilidad=conPucControllerClient.save(conComprobanteContabilidad);
  System.out.println("id-->"+conComprobanteContabilidad.getId());
   L_Message.setText("Proceso correcto"); 
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
  }catch(Exception e)
  {
     L_Message.setText("Error en el proceso"); 
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
  
  
  public void removeDetalle() throws ParseException, InterruptedException
  {
    conComprobanteContabilidad.removeDetalle((ConDetallesComprobanteContabilidad)ta_detallescomprobantecontabilidad.getSelectionModel().getSelectedItem());
    save();
    refreshdetalles();
  }
          
 // A change listener to track the change in selected index

  public void validar_formulario() throws InterruptedException
{
    permitirproceso=true;
      for(Object n:gp_generico.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            if(general.getProperties().get("requerido")!=null)
            {
            if((general.getText().length()<=0) && ((boolean)general.getProperties().get("requerido")==true))
            {
                  permitirproceso=false; 
                  general.setStyle("-fx-background-color:#ff1600");
                  
            }
            }
           
        }
        else
        {
             if(n.getClass()==ChoiceBox.class)
        {
            ChoiceBox general=(ChoiceBox)n;
           
         if(general.getSelectionModel().getSelectedIndex()<0)
         {
                  permitirproceso=false; 
                  general.getStylesheets().add(0,"/sihic/personal.css");
               general.getStylesheets().set(0,"/sihic/personal.css");
         }  
           
        }
        }
        }
     FadeTransition ft = new FadeTransition(Duration.millis(2000),buscar);
        //ft.setFromValue(0.0);
       // ft.setToValue(1);
      
        ft.play();
       
        //success.setOpacity(0);
      
       ft.setOnFinished(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
              for(Object n:gp_generico.getChildren().toArray())
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
           
         if(general.getSelectionModel().getSelectedIndex()<0)
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
   public void set_datos_comprobante()
   {
       //colocar fecha de datepicker
       LocalDate localDate = fechaelaboracion.getValue();
       Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
       conComprobanteContabilidad.setFechaelaboracion(Date.from(instant));
       conComprobanteContabilidad.setTipo(tipocomprobante.getSelectionModel().getSelectedIndex());
   
       
   }
    
public void set_datos_formulario()
{
    nocomprobante.setText(conComprobanteContabilidad.getConsecutivoComprobanteContabilidad().getId().toString());
}

public void refreshdetalles()
{
   ol_detallescomprobante.clear();
   ol_detallescomprobante.addAll(l_detallescomprobante.toArray());
   ta_detallescomprobantecontabilidad.setItems(ol_detallescomprobante);
}
 
public void findcomprobante() throws ParseException
{ 
    System.out.println("Fecha->"+fechaelaboracion.getValue().toString());
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
   Date datefind =df.parse(fechaelaboracion.getValue().toString());
   l_detallescomprobante=conPucControllerClient.findcomprobante(tipocomprobante.getSelectionModel().getSelectedIndex(), datefind);
  
   refreshdetalles();
}
     @Override
    public void start(Stage primaryStage) throws Exception {
        
        
    }
        
        
    
}
