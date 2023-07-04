/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inssend.serviciosqr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sun.mail.util.MailSSLSocketFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;



import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import modelo.Recargas;
import modelo.RecargasRequest;



/**
 *
 * @author adminlinux
 */
@ApplicationScoped
@Produces(APPLICATION_JSON)
@Consumes({"application/xml", "application/json"})
@Path("recargas")
public class RecargasEndpoint {
    @Inject
    private ServiciosQRRepositorio serviciosqrRepositorio;
    @GET
    public Response findAll(@QueryParam("start") @DefaultValue("0") int start,
                             @QueryParam("limit") @DefaultValue("0") int limit)
    {
        List<Recargas> li_testimonios=(start>=0 && limit >0)? serviciosqrRepositorio.findRange(start, limit)
                                                               :serviciosqrRepositorio.findAll();
        return Response.ok(li_testimonios).build();
    }
    @POST
    public Response create(RecargasRequest recargasRequest) throws WriterException, IOException, GeneralSecurityException, MessagingException {
        System.out.println("valor:"+recargasRequest.getValor());
         System.out.println("Email:"+recargasRequest.getEmail());
          System.out.println("Cantidad:"+recargasRequest.getCantidad());
        crearCodigoQR(recargasRequest.getCantidad()+"-"+recargasRequest.getValor()+"-"+recargasRequest.getEmail(), APPLICATION_JSON);
        enviarEmail(recargasRequest.getEmail());
        Recargas recargas=new Recargas();
        recargas.setFecha(new Date());
        recargas.setEmail(recargasRequest.getEmail());
        recargas.setCantidad(Integer.valueOf(recargasRequest.getCantidad()));
        recargas.setValor(new BigDecimal(recargasRequest.getValor()));
        serviciosqrRepositorio.createrecargas(recargas);
        return Response.ok(recargas).build();
    }
    public void crearCodigoQR(String informacion, String ruta) throws WriterException, IOException {
        FileOutputStream qrCode = null;
      //  informacion="http://localhost:8080/ServiciosQR-1.0/";
        try {
            BitMatrix bm;
            Writer writer = new QRCodeWriter();
            bm = writer.encode(informacion, BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < 200; y++) {
                for (int x = 0; x < 200; x++) {
                    int grayValue = (bm.get(x, y) ? 1 : 0) & 0xff;
                    image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
                }
            }
            image = invertirColores(image);
            qrCode = new FileOutputStream("/home/adminlinux/qr"+ "." + "jpg");
            ImageIO.write(image, "jpg", qrCode);
            qrCode.close();
        // ESTO ES PARA ABRIR EL CODIGO QR, CON EL VISOR DE IMAGENS despues de averce creado :D
        // Tiene como parametro la ruta del archivo QR
        //Desktop d = Desktop.getDesktop();
        //d.open(new File("Aqui va la ruta del QR"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecargasEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                qrCode.close();
            } catch (IOException ex) {
                Logger.getLogger(RecargasEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    private static BufferedImage invertirColores(BufferedImage imagen) {
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                int rgb = imagen.getRGB(x, y);
                if (rgb == -16777216) {
                    imagen.setRGB(x, y, -1);
                } else {
                    imagen.setRGB(x, y, -16777216);
                }
            }
        }
        return imagen;
    }
     public void  enviarEmail(String toemail) throws GeneralSecurityException, MessagingException
     {
        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.from", "vmgjamb@gmail.com");
        mailProps.put("mail.smtp.host", "aspmx.l.google.com");
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.port", "587");
        mailProps.put("mail.smtp.auth", true);
      //  mailProps.pProperties props = new Properties();
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
props.setProperty("mail.smtp.port","587");
props.setProperty("mail.smtp.user", "anonimopersoneria@gmail.com");
props.setProperty("mail.smtp.auth", "true");
props.put("mail.smtp.socketFactory.port","587");

  props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

  props.put("mail.smtp.socketFactory.fallback", "true");


  MailSSLSocketFactory sf=new MailSSLSocketFactory();

  sf.setTrustAllHosts(true);

  props.put("mail.smtp.ssl.socketFactory", sf);
  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
      //  //mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //mailProps.put("mail.smtp.socketFactory.fallback", "false");
Session mailSession;

File dir = new File("//home//adminlinux//qr.jpg");

try{
  mailSession = Session.getDefaultInstance(props, new Authenticator() {
        
 
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("anonimopersoneria@gmail.com", "JAmbg172*");
            }

        });
}
catch(Exception e){
   mailSession=Session.getInstance(props, new Authenticator() {
        
 
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("anonimopersoneria@gmail.com", "JAmbg172*");
            }

        });
}
  try {
        MimeMessage msg = new MimeMessage(mailSession);
        msg.setFrom();
        msg.setRecipients(Message.RecipientType.TO,toemail);
        msg.setSubject("Envío Recarga Código QR");
        msg.setFrom("anonimopersoneria@gmail.com");
        msg.setSentDate(new Date());
       
       Multipart mailBody = new MimeMultipart();
       BodyPart texto = new MimeBodyPart();
       texto.setText("Felicitaciones Recargaste Agua con QR");
       mailBody.addBodyPart(texto);
MimeBodyPart mimeAttach = new MimeBodyPart();

 FileDataSource fds = new FileDataSource("//home//adminlinux//qr.jpg");
 mimeAttach.setDataHandler(new DataHandler(fds));
 mimeAttach.setFileName(fds.getName()); 
 mailBody.addBodyPart(mimeAttach);
 mimeAttach=null;
 mimeAttach = new MimeBodyPart();
 fds=null;


  msg.setText("Felicitaciones Recargaste Agua con QR");
     msg.setContent(mailBody); 
     
     Transport.send(msg);
    
     mailSession.getTransport().close();
     mailSession.getStore().close();
     msg.getSession().getTransport().close();
     mailSession=null;
    } catch (MessagingException mex) {
        System.out.println("send failed, exception: " + mex);
    }
         
                File fichero = new File("//home//adminlinux//qr.jpg");
                fichero.delete();
              
            }  
           
          
     

}
