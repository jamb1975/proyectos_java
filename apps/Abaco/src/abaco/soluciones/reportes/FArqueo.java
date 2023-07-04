
package  abaco.soluciones.reportes;

import java.io.IOException;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import abaco.controlador.SolucionControllerClient;
import abaco.controlador.UsuarioSolucionesControllerClient;
import abaco.util.UtilDate;
import com.aquafx_project.AquaFx;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javax.print.PrintException;
import jfxtras.styles.jmetro8.JMetro;
import model.Arqueo;
import org.aerofx.AeroFX;

public class FArqueo extends Application
{
    StackPane stack;
   private GridPane Gp_Message;
    private     Label L_Message;
    private  Runnable Task_Message;
    Thread backgroundThread;
   public static GridPane gp_generic;
   public static final   Label la_fechahora=new Label("Fecha y Hora:");
   public static final   Label la_totalcontado=new Label("Total Contado: ");
   public static final   Label la_totalcredito=new Label("Total Crédito: ");
   public static final   Label la_inicialcaja=new Label("Inicial en Caja: ");
   public static final   Label la_entrega=new Label("Quien Entrega: ");
   public static final   Label la_recibe=new Label("Quien Recibe: ");
   public static final   Label la_totalcaja=new Label("Total Efectivo Caja: ");
   public static final   Label la_observaciones=new Label("Observaciones: ");
   public static final  Label la_ventas=new Label("Ventas: ");
   public static final  Label la_abonos=new Label("Abonos: ");
   public static final  Label la_creditos=new Label("Créditos: ");
   public static final  Label la_abonossinfactura=new Label("Abonos Sin Factura: ");
  
   public static final  TextField fechahora=new TextField("");
   public static final   TextField totalcontado=new TextField("");
   public static final   TextField totalcredito=new TextField("");
   public static final   TextField inicialcaja=new TextField("");
   public static final   TextField entrega=new TextField("");
   public static final   TextField recibe=new TextField("");
   public static final   TextField totalcaja=new TextField("");
   private static final TextArea  observaciones=new TextArea();
   public static   Button Bu_guardar=new Button("");
   public static   Button Bu_imprimir=new Button("");
   private UsuarioSolucionesControllerClient usuarioSolucionesControllerClient=new UsuarioSolucionesControllerClient();
   private  SolucionControllerClient solucionControllerClient;
   private ImageView imageView;
   private static    final ProgressIndicator p5 = new ProgressIndicator();
   private static Timeline timeline = new Timeline();
   private static Thread thimport_77;
   private BigDecimal bd_totalcontado=BigDecimal.ZERO;
   private BigDecimal bd_totalcredito=BigDecimal.ZERO;
   private BigDecimal bd_totalabonos=BigDecimal.ZERO;
   private BigDecimal bd_abonossinfactura=BigDecimal.ZERO;
   private ImpresoraTermica impresoraTermica=new ImpresoraTermica();
   private TitledPane tp_generic;
   private HBox hbox=new HBox();
   public Parent createContent() throws IOException 
    {
     tp_generic=new TitledPane();
     tp_generic.setText("Arqueo Caja");   
    // totalcaja.setEditable(false);
    // totalcontado.setEditable(false);
   //  totalcredito.setEditable(false);
     // gp_progreso.setStyle("background: white");
      stack = new StackPane();
      gp_generic=new GridPane();
     Gp_Message=new GridPane();
        Gp_Message.setMinSize(gp_generic.getLayoutBounds().getWidth(),40);
        L_Message=new Label();
        L_Message.getStylesheets().add("/abaco/SofackarStylesCommon.css"); 
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
      imageView=new ImageView("/images/save.png");
      imageView.setFitHeight(30);
      imageView.setFitWidth(30);
      Bu_guardar.setGraphic(imageView);
      Bu_guardar.setOnAction(e->{
         try {
             save();
         } catch (ParseException ex) {
             Logger.getLogger(FArqueo.class.getName()).log(Level.SEVERE, null, ex);
         }
            
        });
      imageView=null;
      imageView=new ImageView("/images/print.png");
      imageView.setFitHeight(30);
      imageView.setFitWidth(30);
      Bu_imprimir.setGraphic(imageView);
      Bu_imprimir.setOnAction(e->{
           
         try {
             imprimir();
         } catch (IOException ex) {
             Logger.getLogger(FArqueo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (PrintException ex) {
             Logger.getLogger(FArqueo.class.getName()).log(Level.SEVERE, null, ex);
         }
        });
      hbox.getChildren().addAll(Bu_guardar,Bu_imprimir);
      inicialcaja.textProperty().addListener((observable, oldValue, newValue) -> {
          try
              { 
                 totalcaja.setText(bd_totalcontado.toBigInteger().add(bd_totalabonos.toBigInteger()).add(bd_abonossinfactura.toBigInteger()).add(new BigDecimal(inicialcaja.getText()).toBigInteger()).toString());
              }catch(Exception e)
              {
            
              }

});
    
      hbox.setAlignment(Pos.CENTER);
      GridPane.setValignment(gp_generic, VPos.CENTER);
      gp_generic.add(la_fechahora,0,0);
      gp_generic.add(new Label(UtilDate.formatodiamesyear(new Date())+" "+ UtilDate.gethorafecha(new Date())+":"+ UtilDate.getminutofecha(new Date())),1,0);
      gp_generic.add(la_totalcontado,0,1);
      gp_generic.add(totalcontado,1,1);
      gp_generic.add(la_totalcredito,0,2);
      gp_generic.add(la_ventas,2,2);
      gp_generic.add(la_abonos,2,3);
      gp_generic.add(la_abonossinfactura,2,4);
      gp_generic.add(la_creditos,2,5);
      gp_generic.add(totalcredito,1,2);
      gp_generic.add(la_inicialcaja,0,3);
      gp_generic.add(inicialcaja,1,3);
      gp_generic.add(la_totalcaja,0,4);
      gp_generic.add(totalcaja,1,4);
      gp_generic.add(la_entrega,0,5);
      gp_generic.add(entrega,1,5);
      gp_generic.add(la_recibe,0,6);
      gp_generic.add(recibe,1,6);
      gp_generic.add(la_observaciones,0,7);
      gp_generic.add(observaciones,1,7);
      gp_generic.add(hbox,0,8,2,1);
      gp_generic.setVgap(3);
      gp_generic.setMinSize(1000, 610);
     // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css"); 
      //gp_generic.getStyleClass().add("background");
      stack.getChildren().addAll(gp_generic);
    switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gp_generic);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gp_generic);
                     break;              
         }
     if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()!=0)
        {
         Bu_guardar.setStyle("-fx-background-color:#007fff");
         Bu_imprimir.setStyle("-fx-background-color:#007fff");
                 
        }
      getdatosarqueo();
      StackPane stack=new StackPane();
      gp_generic.setAlignment(Pos.CENTER);
      tp_generic.setContent(gp_generic);
      stack.setAlignment(Pos.TOP_CENTER);
      stack.getChildren().addAll(tp_generic,Gp_Message);
        return stack;
      
    
    }
  
 
   
      @Override
    public void start(Stage primaryStage) throws Exception {
 
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }






    public static void main(String[] args) {
        launch(args);
    }
    
    private void getdatosarqueo()
    {
        bd_abonossinfactura=AbacoApp.arqueoControllerClient.totalrecibossinfactura();
        for(Object[] obj:AbacoApp.arqueoControllerClient.TotalCreditoContado())
        {
            bd_totalcontado=(BigDecimal)obj[1];
            if(bd_totalcontado==null)
            {
                bd_totalcontado=BigDecimal.ZERO;
            }
            bd_totalcredito=(BigDecimal)obj[2];
            if(bd_totalcredito==null)
            {
                bd_totalcredito=BigDecimal.ZERO;
            }
             bd_totalabonos=(BigDecimal)obj[3];
             if(bd_totalabonos==null)
            {
                bd_totalabonos=BigDecimal.ZERO;
            }
        }
        AbacoApp.arqueoControllerClient.getlastrecord();
        if(AbacoApp.s_arqueo!=null)
        {
            
               inicialcaja.setText(AbacoApp.s_arqueo.getValor_caja().toString());
             
        }
        else
        {
          inicialcaja.setText("0");  
        }
        totalcontado.setText(bd_totalcontado.toBigInteger().add(bd_totalabonos.toBigInteger()).add(bd_abonossinfactura.toBigInteger()).toString());
        totalcredito.setText(bd_totalcredito.toBigInteger().toString());
        totalcaja.setText(bd_totalcontado.toBigInteger().add(bd_totalabonos.toBigInteger()).add(bd_abonossinfactura.toBigInteger()).add(new BigDecimal(inicialcaja.getText()).toBigInteger()).toString());
        la_ventas.setText("Total de Ventas: "+ bd_totalcontado.toBigInteger().toString());
        la_abonos.setText("Total de Abonos: "+bd_totalabonos.toBigInteger().toString());
        la_creditos.setText("Total de Créditos: "+bd_totalcredito.toBigInteger());
        la_abonossinfactura.setText("Abonos sin Facturas: "+bd_abonossinfactura.toString());
        observaciones.setText("");
        
    } 
    private void save() throws ParseException
    {try{
        setdatos();
        AbacoApp.arqueoControllerClient.create();
        L_Message.setText("Registro Almacenado"); 
         Task_Message = () -> {
         try {
             SetMessage();
         } catch (InterruptedException ex) {
             ex.printStackTrace();
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
    private void setdatos() throws ParseException
    {
        AbacoApp.s_arqueo=null;
        
           AbacoApp.s_arqueo=new Arqueo();
        
        AbacoApp.s_arqueo.setEntrega(entrega.getText());
        AbacoApp.s_arqueo.setFecha(UtilDate.colocarfechahora(new  Date()));
        AbacoApp.s_arqueo.setObservaciones(observaciones.getText());
        AbacoApp.s_arqueo.setRecibe(recibe.getText());
        AbacoApp.s_arqueo.setValor_caja(new BigDecimal(inicialcaja.getText()));
        AbacoApp.s_arqueo.setValor_contado(new BigDecimal(totalcontado.getText()));
        AbacoApp.s_arqueo.setValor_credito(new BigDecimal(totalcredito.getText()));
        
         
    }  
    private void imprimir() throws IOException, PrintException
    {
        impresoraTermica.imprimirarqueo();
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
}