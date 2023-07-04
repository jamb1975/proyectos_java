/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.procesosadministrativos;

import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import com.aquafx_project.AquaFx;
import com.sun.mail.util.MailSSLSocketFactory;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import jfxtras.styles.jmetro8.JMetro;
import model.DetallesPedido;
import org.aerofx.AeroFX;
import org.controlsfx.control.PopOver;

/**
 cliente
 * @author isoft
 */
public class FDetallesPedido extends Application{
      private static TableView tv_detallespedido=new TableView();
      private ObservableList ol_detallespedido = FXCollections.observableArrayList();
      private GridPane gp_generic=new GridPane();
      private Label fechapedido=new Label("Fecha");
      private Label numeropedido=new Label("N° pedido");
      private Label cliente=new Label("Cliente");
      private Label noident=new Label("N° identificación");
      private Label email=new Label("E-mail");
      private Label celular=new Label("Celular");
      private Label dir=new Label("Dirección");
      private TextField tf_fechapedido=new TextField("");
      private TextField tf_numeropedido=new TextField("");
      private TextField tf_cliente=new TextField("");
      private TextField tf_noident=new TextField("");
      private TextField tf_email=new TextField("");
      private TextField tf_celular=new TextField("");
      private TextField tf_dir=new TextField("");
      private TableColumn producto = new TableColumn(); 
      private TableColumn valor_uni = new TableColumn();
      private TableColumn valor_total = new TableColumn();
      private TableColumn cantidad = new TableColumn();
      private Button bu_cambiarestado=new Button("");
      private PopOver popover;
      private  Task<Void>   task_generic;
      private  Thread  thimport_generic;
      private ProgressBar pb ;
      private ProgressIndicator pi;
      private GridPane gp=new GridPane();
      public static double  avance_progress=0;
      
      public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
          gp_generic.setHgap(5);
          gp_generic.setVgap(5);
          gp_generic.setPadding(new Insets(5,5,5,5));
          popover=new PopOver(gp);
           bu_cambiarestado.setDisable(false);
          if(AbacoApp.pedidos.getEstado()==0)
         {
            bu_cambiarestado.setText("Confirmar y preparar");
         }
         else
         {
         if(AbacoApp.pedidos.getEstado()==1)
         {
             bu_cambiarestado.setText("Despachar");
         }
         else
         {
             bu_cambiarestado.setText("Despachado");
             bu_cambiarestado.setDisable(true);
         }
         }
          bu_cambiarestado.setOnAction(e->{
           bu_cambiarestado.setDisable(true);
                 guardar();
          });
          iniciarcolumnastablas();
          tv_detallespedido.getColumns().addAll(producto, cantidad, valor_uni,valor_total);
          tv_detallespedido.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
          gp_generic.addRow(0,numeropedido,fechapedido,noident,cliente);
          gp_generic.addRow(1,tf_numeropedido,tf_fechapedido,tf_noident,tf_cliente);
          gp_generic.addRow(2,email,celular,dir,new Label("Despachar pedido"));
          gp_generic.addRow(3,tf_email,tf_celular,tf_dir,bu_cambiarestado);
          GridPane.setColumnSpan(tv_detallespedido, 4);
          gp_generic.addRow(4,tv_detallespedido);
          switch(cb_temas.getSelectionModel().getSelectedIndex())
          {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(gp_generic);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(gp_generic);
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
          bu_cambiarestado.setStyle("-fx-background-color:#007fff");
        
        }
          getRecords();
          return gp_generic;
       }
       public  void   getRecords() throws ParseException
{
         //colocamos los datos
         try{
             tf_numeropedido.setText(AbacoApp.pedidos.getNo_pedido());
             tf_fechapedido.setText(abaco.util.UtilDate.s_formatoyearmesdia(AbacoApp.pedidos.getFecha()));
             tf_noident.setText(AbacoApp.pedidos.getCliente().getNo_ident());
             tf_cliente.setText(AbacoApp.pedidos.getCliente().getNombre());
             tf_email.setText(AbacoApp.pedidos.getCliente().getEmail());
             tf_celular.setText(AbacoApp.pedidos.getCliente().getCelular());
             tf_dir.setText(AbacoApp.pedidos.getCliente().getDir1());
             
       
       
       ol_detallespedido.clear();
       
        ol_detallespedido.addAll(AbacoApp.pedidos.getLi_detallespedido().toArray());
        tv_detallespedido.setItems(ol_detallespedido);
      } catch(Exception e)
       {
           e.printStackTrace();
       }
    }
        private void iniciarcolumnastablas()
      {
         tv_detallespedido.getColumns().clear();
        producto.setMinWidth(200);
        producto.setText("Producto");
        producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetallesPedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DetallesPedido, String> detallespedido) {
               
                    return new SimpleStringProperty(detallespedido.getValue().getProducto().getNombre());
                            
            }
        });  
        cantidad.setMinWidth(100);
        cantidad.setText("Cantidad");
        cantidad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetallesPedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DetallesPedido, String> detallespedido) {
               
                    return new SimpleStringProperty(String.valueOf(detallespedido.getValue().getCantidad()));
                            
            }
        });  
        valor_uni.setMinWidth(100);
        valor_uni.setText("Valor/u");
        valor_uni.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetallesPedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DetallesPedido, String> detallespedido) {
               
                    return new SimpleStringProperty(detallespedido.getValue().getValor_uni().toString());
                            
            }
        });  
        valor_total.setMinWidth(100);
        valor_total.setText("Valor total");
        valor_total.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DetallesPedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DetallesPedido, String> detallespedido) {
               
                    return new SimpleStringProperty(detallespedido.getValue().getValor_total().toString());
                            
            }
        }); 
     
  }
public void  enviarEmail() throws GeneralSecurityException, MessagingException
 {
      gp.setAlignment(Pos.CENTER);
      avance_progress=0.0;
      task_generic=new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                
                // borraremaillbdproductos();
                 pb.setProgress(0.6);
                 pi.setProgress(0.6);
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.user", "abaco7775@gmail.com");
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port","587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "true");
        MailSSLSocketFactory sf=new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.socketFactory", sf);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session mailSession;
try{
  mailSession = Session.getDefaultInstance(props, new Authenticator() {
           @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AbacoApp.s_organizacion.getEmail(),AbacoApp.s_organizacion.getPasswordemail());
            }

        });
}
catch(Exception e){
   mailSession=Session.getInstance(props, new Authenticator() {
        
 
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AbacoApp.s_organizacion.getEmail(), AbacoApp.s_organizacion.getPasswordemail());
            }

        });
}
  try {
        MimeMessage msg = new MimeMessage(mailSession);
        msg.setRecipients(Message.RecipientType.TO,AbacoApp.pedidos.getCliente().getEmail());
        msg.setSubject("Confirmación de pedido N° "+AbacoApp.pedidos.getNo_pedido() );
        msg.setFrom(AbacoApp.s_organizacion.getEmail());
        msg.setSentDate(new Date());
        Multipart mailBody = new MimeMultipart();
       BodyPart texto = new MimeBodyPart();
       texto.setText("Pedido recibido y preparandolo...");
       mailBody.addBodyPart(texto);
       msg.setContent(mailBody); 
       Transport.send(msg);
    
     mailSession.getTransport().close();
     mailSession.getStore().close();
     msg.getSession().getTransport().close();
     mailSession=null;
    } catch (MessagingException mex) {
        System.out.println("send failed, exception: " + mex);
    }
     pb.setProgress(1);
     pi.setProgress(1);
     return null;
           
             }
         };
            
        thimport_generic = new Thread(task_generic);
        thimport_generic.setDaemon(true);
        thimport_generic.start();
  }
 private void showAvancerestaurar() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  
 
  gp.getChildren().clear();
  pb=null;
  pi=null;
  pb=new ProgressBar(0);
  pi=new ProgressIndicator(1);
  gp.addRow(0, pb,pi);
  popover.setArrowLocation(PopOver.ArrowLocation.LEFT_CENTER);
  popover.setCornerRadius(4);
  popover.setDetached(true);
  popover.setDetachedTitle(AbacoApp.pedidos.getEstado()==0?"     Enviando confirmación de recibido..":"     Enviando pedido despachado..");
  double clickX = bu_cambiarestado.localToScreen(bu_cambiarestado.getBoundsInLocal()).getMinX();
  double clickY = (bu_cambiarestado.localToScreen(bu_cambiarestado.getBoundsInLocal()).getMinY() +
                bu_cambiarestado.localToScreen(bu_cambiarestado.getBoundsInLocal()).getMaxY()) / 2;

        popover.show(bu_cambiarestado.getScene().getWindow());
        // Show on left
//        popOver.setX(clickX - popOver.getWidth());
//        popOver.setY(clickY - popOver.getHeight() / 2);
        // Show on right
        popover.setX(clickX + 40.0);
        popover.setY(clickY - popover.getHeight() / 2);
  
 }  
    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void guardar()
    {
        try
        {
                 showAvancerestaurar();
                 
        } catch (Exception ex) {
                  Logger.getLogger(FDetallesPedido.class.getName()).log(Level.SEVERE, null, ex);
              } 
         if(AbacoApp.pedidos.getEstado()==0)
         {
           AbacoApp.pedidos.setEstado(1);//estado confirmado y preparando
         }
         else
         {
             AbacoApp.pedidos.setEstado(2);//estado despachar 
         }  
              AbacoApp.pedidosControllerclient.update();
              pb.setProgress(0.5);
              pi.setProgress(0.5);
              try {
                  enviarEmail();
                   pb.setProgress(1);
              pi.setProgress(1);
              } catch (Exception ex) {
                  Logger.getLogger(FDetallesPedido.class.getName()).log(Level.SEVERE, null, ex);
              }
              
              AbacoApp.actualizarlistadepedidos();
    }
}
