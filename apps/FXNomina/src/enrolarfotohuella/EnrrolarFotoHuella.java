/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrolarfotohuella;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.ui.swing.DPFPEnrollmentControl;
import com.digitalpersona.onetouch.verification.DPFPVerification;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.EnumMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nomina.FEmpleado;
import static nomina.FEmpleado.stagefotohuella;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author sion
 */
public class EnrrolarFotoHuella extends Application {

private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
private DPFPTemplate template;
private EnumMap<DPFPFingerIndex, DPFPTemplate> templates=new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
public static String TEMPLATE_PROPERTY = "template";
private ImageView iv_huella1=new ImageView();
private ImageView iv_huella2=new ImageView();
private ImageView iv_huella3=new ImageView();
private ImageView iv_huella4=new ImageView();
private ImageView iv_foto=new ImageView();
private ImageView iv_tomarfoto=new ImageView();
private ImageView iv_nube=new ImageView();
private Image imagehuella1= new Image("/images/huella1.png");
private Image imagehuella2= new Image("/images/huella2.png");
private Image imagehuella3= new Image("/images/huella3.png");
private Image imagehuella4= new Image("/images/huella4.png");
private Image imagetomarfoto= new Image("/images/tomarfoto.png");
private Image imagenube= new Image("/images/nube.png");
private Image imagefoto= new Image("/images/solucion.png");
private static TextField textArea=new TextField();
private static TextField textArea2=new TextField();
private ScheduledExecutorService timer;
private ToggleGroup toggleGroup=new ToggleGroup();
private RadioButton rb_indicederecho=new RadioButton("Indice Derecho");
private RadioButton rb_indiceizquierdo=new RadioButton("Indice Izquierdo");
// the OpenCV object that realizes the video capture
static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
private static  VideoCapture capture = new VideoCapture();
private int canthuellas=1;
private static final Label title=new Label("Tomar Huella Indice Derecho");
private String id="";
private String nit="";
private int conthuellas=0;
private Button bu_tomarfoto=new Button();
private Button bu_nube=new Button();
private HBox hb_imamgeneshuellas=new HBox();
DPFPEnrollmentControl enrollmentControl = new DPFPEnrollmentControl();
private ImageView currentFrame=new ImageView();
private boolean tomarfoto=false;
private FTPClient ftpClient = new FTPClient();
private Mat frame=new Mat();
// a flag to change the button behavior
private final ProgressBar pb = new ProgressBar(0);
private static Task<Void> task_subirnube;
private static Thread thread_sibirnube; 
public DPFPFeatureSet featuresinscripcion;
private TitledPane tp_generic;
   
   public Parent createContent() {
        tp_generic=new TitledPane();
        tp_generic.setText("INSSEND CAPTURA DE HUELLAS Y FOTOS");
        tp_generic.setCollapsible(false);
     pb.setVisible(false);
     String libName = "";
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));//CV_8UC1
    System.out.println("OpenCV Mat: " + m);
    Mat mr1 = m.row(1);
    mr1.setTo(new Scalar(1));
    Mat mc5 = m.col(5);
    mc5.setTo(new Scalar(5));
    System.out.println("m = " + m.dump());
    textArea2.setText(m.toString());
   
        capture = new VideoCapture();
        capture.open("/resources/video.MP4");
        capture.set(200 , 200); 
        //capture.set(650 , 480); 
        capture.open(0);
        final String EXTERNAL_STYLESHEET = "http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600";
         Lector.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_HIGH.CAPTURE_PRIORITY_LOW);
        rb_indicederecho.setToggleGroup(toggleGroup);
        rb_indicederecho.setSelected(true);
        rb_indiceizquierdo.setToggleGroup(toggleGroup);
        StackPane root = new StackPane();
        iv_huella1.setFitHeight(70);
        iv_huella1.setFitWidth(70);
        iv_huella1.setImage(imagehuella1);
        iv_huella2.setFitHeight(70);
        iv_huella2.setFitWidth(70);
        iv_huella2.setImage(imagehuella2);
        iv_huella3.setFitHeight(70);
        iv_huella3.setFitWidth(70);
        iv_huella3.setImage(imagehuella3);
        iv_huella4.setFitHeight(70);
        iv_huella4.setFitWidth(70);
        iv_huella4.setImage(imagehuella4);
        iv_foto.setFitHeight(100);
        iv_foto.setFitWidth(100);
        iv_foto.setImage(imagefoto);
        iv_tomarfoto.setFitHeight(40);
        iv_tomarfoto.setFitWidth(40);
        currentFrame.setFitHeight(100);
        currentFrame.setFitWidth(100);
        iv_tomarfoto.setImage(imagetomarfoto);
        iv_nube.setFitHeight(40);
        iv_nube.setFitWidth(40);
        iv_nube.setImage(imagenube);
        
        hb_imamgeneshuellas.setSpacing(5);
        hb_imamgeneshuellas.getChildren().addAll(iv_huella1,iv_huella2,iv_huella3,iv_huella4);
        bu_tomarfoto.setGraphic(iv_tomarfoto);
        bu_tomarfoto.setOnAction(e->{
            capturarfoto();
        });
        bu_nube.setGraphic(iv_nube);
        bu_nube.setOnAction(e->{
            try {
                save();
                closedispositivos();
            } catch (Exception ex) {
                Logger.getLogger(EnrrolarFotoHuella.class.getName()).log(Level.SEVERE, null, ex);
            }
            closedispositivos();
            stagefotohuella.close();
        });
         stagefotohuella.setOnCloseRequest(e->{
            closedispositivos();
           });
        GridPane gp=new GridPane();
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setHalignment(iv_foto, HPos.CENTER);
        GridPane.setHalignment(hb_imamgeneshuellas, HPos.CENTER);
        GridPane.setValignment(hb_imamgeneshuellas, VPos.BOTTOM);
        GridPane.setHalignment(rb_indicederecho, HPos.CENTER);
        GridPane.setHalignment(rb_indiceizquierdo, HPos.CENTER);
        GridPane.setHalignment(gp, HPos.CENTER);
        gp.alignmentProperty().set(Pos.CENTER);
        gp.setMaxWidth(550);
        gp.setHgap(5);
        gp.setVgap(5);
        hb_imamgeneshuellas.setAlignment(Pos.TOP_CENTER);
        gp.addRow(0,rb_indicederecho,rb_indiceizquierdo);
        gp.add(title,0,1,7,1);
        gp.add(bu_tomarfoto, 4, 2);
        gp.add(bu_nube, 5, 2);
        gp.add(pb, 6, 2,2,1);
        GridPane.setColumnSpan(hb_imamgeneshuellas,4);
        gp.addRow(3,hb_imamgeneshuellas);
        gp.add(currentFrame,4,3,2,1);
        gp.add(iv_foto, 6, 3);
        gp.add(textArea,0,4,7,1);
        gp.add(textArea2,0,5,7,1);
        tp_generic.setContent(gp);
        root.getChildren().add(tp_generic);
        root.getStylesheets().setAll("/fxnomina/SofackarStylesCommon.css");
        gp.getStylesheets().add("/fxnomina/SofackarStylesCommon.css");
        gp.getStyleClass().add("background");
     
      
       
        try{
        Iniciar();
	start_();
        EstadoHuellas();
        capturar_datos_empleado();
        capturar_video();
        
        }catch(Exception e)
        {
             stop_();
             stopcamera();
        }
      return root;

    }  /**
     * @param args the command line arguments
     */
    
   protected void Iniciar(){
   Lector.addDataListener(new DPFPDataAdapter() {
    @Override
    public void dataAcquired(final DPFPDataEvent e) {
    Platform.runLater(new Runnable() {    public void run() {
        
    EnviarTexto
        ("La Huella Digital ha sido Capturada");
        try {
            ProcesarCaptura(e.getSample());
        } catch (IOException ex) {
            Logger.getLogger(EnrrolarFotoHuella.class.getName()).log(Level.SEVERE, null, ex);
        }
    }           });}
   });

   Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
    @Override
    public void readerConnected(final DPFPReaderStatusEvent e) {
    Platform.runLater(new Runnable() {    public void run() {
  //  EnviarTexto("El Sensor de Huella Digital esta Activado o Conectado");
    }});}
    @Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
    Platform.runLater(new Runnable() {    public void run() {
    EnviarTexto("El Sensor de Huella Digital esta Desactivado o no Conectado");
    }});}
   });

   Lector.addSensorListener(new DPFPSensorAdapter() {
    @Override public void fingerTouched(final DPFPSensorEvent e) {
    Platform.runLater(new Runnable() {    public void run() {
    EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella");
    }});}
    @Override public void fingerGone(final DPFPSensorEvent e) {
    Platform.runLater(new Runnable() {    public void run() {
    //EnviarTexto("El dedo ha sido quitado del Lector de Huella");
    }});}
   });
   }
   public  DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
     DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
     
    try {
    return extractor.createFeatureSet(sample, purpose);
    } catch (DPFPImageQualityException e) {
    return null;
    }
}
   public  java.awt.Image CrearImagenHuella(DPFPSample sample) {
    return DPFPGlobal.getSampleConversionFactory().createImage(sample);
}
   public void DibujarHuella(java.awt.Image image) {
    BufferedImage buffered = (BufferedImage) image;
    javafx.scene.image.Image img2=null;
    img2 = SwingFXUtils.toFXImage(buffered, null);
            switch(conthuellas)
            {
                case 1:
                    iv_huella1.setImage(img2);
                    break;
                case 2:
                    iv_huella2.setImage(img2);
                    break;
                case 3:
                    iv_huella3.setImage(img2);
                    break;
                case 4:
                    iv_huella4.setImage(img2);
                    break;    
            }
            
     
 }
   public void EnviarTexto(String string) {
       textArea.setText(string + "\n");
}
   public  void EstadoHuellas(){
    //EnviarTexto("Muestra de Huellas Necesarias para Guardar Template "+ Reclutador.getFeaturesNeeded());
}
   public  void start_(){
    Lector.startCapture();
   // EnviarTexto("Utilizando el Lector de Huella Dactilar ");
}

public  void stop_(){
        Lector.stopCapture();
        EnviarTexto("No se está usando el Lector de Huella Dactilar ");
} 

    public DPFPTemplate getTemplate() {
        return template;
    }

public void setTemplate(DPFPTemplate template) {
         DPFPTemplate old = this.template;
   this.template = template;
   //firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }
public void ProcesarCaptura(DPFPSample sample) throws IOException
{
featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
if (featuresinscripcion != null)
try{
System.out.println("Las Caracteristicas de la Huella han sido creada");
Reclutador.addFeatures(featuresinscripcion);// Agregar las caracteristicas de la huella a la plantilla a crear
conthuellas++;

java.awt.Image image=CrearImagenHuella(sample);
DibujarHuella(image);



}catch (DPFPImageQualityException ex) {
System.err.println("Error: "+ex.getMessage());
}

finally {
EstadoHuellas();
// Comprueba si la plantilla se ha creado.
switch(Reclutador.getTemplateStatus())
{
case TEMPLATE_STATUS_READY: // informe de Ã©xito y detiene la captura de huellas
stop_();
setTemplate(Reclutador.getTemplate());
if(rb_indicederecho.isSelected())
{
 templates.put(DPFPFingerIndex.RIGHT_INDEX, Reclutador.getTemplate());
}
else
{
   templates.put(DPFPFingerIndex.LEFT_INDEX, Reclutador.getTemplate()); 
}
EnviarTexto(rb_indicederecho.isSelected()?"Huella Indice derecho ha Sido Creada y Guadada":"Huella Indice izquierdo ha Sido Creada y Guardada");
conthuellas=0;
guardar();

break;

case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
Reclutador.clear();
stop_();
EstadoHuellas();
setTemplate(null);
start_();
break;
}
}
}
private void capturar_video()
{
     Runnable frameGrabber = new Runnable() {
					
					@Override
					public void run()
					{
						Image imageToShow = grabFrame();
						currentFrame.setImage(imageToShow);
                                        if(tomarfoto)
                                        {
                                            iv_foto.setImage(imageToShow);
                                            tomarfoto=false;
                                            OutputStream out=null;
                
                                             MatOfByte buffer = new MatOfByte();
		                             // encode the frame in the buffer
		                             Imgcodecs.imencode(".png", frame, buffer);
                                             
                                                    try {
                                                        out = new FileOutputStream("c:/fotohuella/"+nit+"/"+id+".png");
                                                    } catch (FileNotFoundException ex) {
                                                        Logger.getLogger(EnrrolarFotoHuella.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                    try {
                                                        out.write(buffer.toArray());
                                                    } catch (IOException ex) {
                                                        Logger.getLogger(EnrrolarFotoHuella.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                    try { 
                                                        out.close();
                                                    } catch (IOException ex) {
                                                        Logger.getLogger(EnrrolarFotoHuella.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                        }
					}
				};
                               Platform.runLater(frameGrabber);
				this.timer = Executors.newSingleThreadScheduledExecutor();
				this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
				
}
private void capturarfoto()
{
 tomarfoto=true;
 title.setText("Foto Guardada Satisfactoriamente");			
    
} 
private javafx.scene.image.Image grabFrame()
{
		// init everything
		javafx.scene.image.Image imageToShow = null;
		 frame = new Mat();
		
                
                
                
		{
			try
			{
				// read the current frame
                               //textArea2.setText("frame reader");
				this.capture.read(frame);
				
				// if the frame is not empty, process it
				if (!frame.empty())
				{
					// convert the image to gray scale
				//	Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2HLS_FULL);
					// convert the Mat object (OpenCV) to Image (JavaFX)
					imageToShow = mat2Image(frame);
                                      // textArea2.setText("frame no vacio");
				}
				
			}
			catch (Exception e)
			{
				// log the error
				System.err.println("Exception during the image elaboration: " + e);
                                textArea2.setText(e.getMessage());
			}
		}
		
		return imageToShow;
}
private javafx.scene.image.Image mat2Image(Mat frame) throws FileNotFoundException, IOException
	{
		// create a temporary buffer
		MatOfByte buffer = new MatOfByte();
		// encode the frame in the buffer
		Imgcodecs.imencode(".png", frame, buffer);
		// build and return an Image created from the image encoded in the
		// buffer
                String ruta="";
                
                   
                 
                   
                
                
                 
		return new javafx.scene.image.Image(new ByteArrayInputStream(buffer.toArray()));
}
public void stopcamera()
{
    this.capture.release();
			// clean the frame
  this.iv_foto.setImage(null);
}
public void guardar() throws IOException
{
   
    File file=null;
    FileOutputStream stream=null;
   if(rb_indicederecho.isSelected())
   {
      
       file=new File("c:\\fotohuella\\"+nit+"\\"+id+"_indexright.fpt");
      
     
       
   }
   else
   {
   if(rb_indiceizquierdo.isSelected())
   {
       
       file=new File("c:\\fotohuella\\"+nit+"\\"+id+"_indexleft.fpt");
    }
   }
   stream = new FileOutputStream(file);
   stream.write(getTemplate().serialize());
   stream.close();
    if(rb_indiceizquierdo.isSelected())
   {
       title.setText("Huellas Guardadas Satisfactoriamente");
       reiniciar_tomahuellas();
       iv_huella1.setImage(imagehuella1);
       iv_huella2.setImage(imagehuella2);
       iv_huella3.setImage(imagehuella3);
       iv_huella4.setImage(imagehuella4);
   }
   if(rb_indicederecho.isSelected())
   {
       rb_indiceizquierdo.setSelected(true);
       title.setText("Tomar Huella Indice Izquierdo");
       reiniciar_tomahuellas();
       iv_huella1.setImage(imagehuella1);
       iv_huella2.setImage(imagehuella2);
       iv_huella3.setImage(imagehuella3);
       iv_huella4.setImage(imagehuella4);
   }
   
   
  
	
}
private void capturar_datos_empleado() throws FileNotFoundException, IOException
{
    try{
        if(fxnomina.FXNomina.genPersonas!=null)
        {
              id=fxnomina.FXNomina.genPersonas.getId().toString();
      
                nit=fxnomina.FXNomina.genPersonas.getDocumento();
            
        }
 
     
      File dirnit=new File("c:/fotohuella/"+nit);
                    if(!dirnit.exists())
                    {
                     dirnit.mkdir(); 
                    }
    }catch(Exception e)
    {
        
    }
}
public void save()
{
    FEmpleado.LoadImageFoto2();
}        
public static void main(String[] args) {
    // System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    
    launch(args);
    }
  private  void reiniciar_tomahuellas()
  {
       Reclutador.clear();
       stop_();
       EstadoHuellas();
       setTemplate(null);
       start_();
  }
  private void subirnube()
  {
      task_subirnube=new Task<Void>() {
          @Override
          protected Void call() throws Exception {
           
      try
        {
           
            //Verificar conexión con el servidor.
         ftpClient.connect(InetAddress.getByName("motofacil.com.co"),21);
       boolean login = ftpClient.login("sdnc8ew7d6fb","Yamithg2017*");  

            int reply = ftpClient.getReplyCode();
           
            System.out.println("Respuesta recibida de conexión FTP:" + reply);
           
            if(FTPReply.isPositiveCompletion(reply))
            {
                System.out.println("Conectado Satisfactoriamente");    
            }
            else
                {
                    System.out.println("Imposible conectarse al servidor");
                }
           
            //Verificar si se cambia de direcotirio de trabajo
            String dirfoto = "/public_html/cia-sicov/sicov/archivos/infractores/fotos";
            String dirhuella = "/public_html/cia-sicov/sicov/archivos/infractores/huellas";
            
            ftpClient.makeDirectory(dirhuella);
            ftpClient.makeDirectory(dirfoto);
            ftpClient.changeWorkingDirectory("/"+dirhuella);//Cambiar directorio de trabajo
            System.out.println("Se cambió satisfactoriamente el directorio");
            String dirToCreate = nit;
            ftpClient.makeDirectory(nit);
            ftpClient.changeWorkingDirectory("/"+dirhuella);//Cambiar directorio de trabajo

            //Activar que se envie cualquier tipo de archivo
           
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
           
            BufferedInputStream buffIn = null;
            try{
            buffIn = new BufferedInputStream(new FileInputStream("c://fotohuella/"+nit+"/"+id+"_indexleft.fpt"));//Ruta del archivo para enviar
           // ftpClient.enterLocalPassiveMode();
          ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(id+"_indexleft.fpt", buffIn);//Ruta completa de alojamiento en el FTP
             updateMessage("Huella Indice Izquierdo con ID "+id+" cargado en la nube satisfactoriamente");
            //  ftpClient.completePendingCommand();
            buffIn.close(); //Cerrar envio de arcivos al FTP
             updateProgress(1, 3);
           
            }catch(Exception e)
            {
                
            }
         BufferedInputStream   buffIn2=null;
            try{
            buffIn2 = new BufferedInputStream(new FileInputStream("c://fotohuella/"+nit+"/"+id+"_indexright.fpt"));//Ruta del archivo para enviar
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(id+"_indexright.fpt", buffIn2);
             updateMessage("Huella Indice Derecho con ID "+id+" cargado en la nube satisfactoriamente");
         //    ftpClient.completePendingCommand();
            buffIn2.close(); //Cerrar envio de arcivos al FTP
             updateProgress(2, 3);
         
            }catch(Exception e)
            {
                
            }
            BufferedInputStream buffIn3=null;
            try{
            ftpClient.changeWorkingDirectory("/"+dirfoto);//Cambiar directorio de trabajo
            System.out.println("Se cambió satisfactoriamente el directorio");
            buffIn3 = new BufferedInputStream(new FileInputStream("c://fotohuella/"+nit+"/"+id+".png"));//Ruta del archivo para enviar
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(id+".png", buffIn3);
            
            updateMessage("Foto con ID "+id+" cargado en la nube satisfactoriamente");
           //  ftpClient.completePendingCommand();
            buffIn3.close(); //Cerrar envio de arcivos al FTP
            updateProgress(3, 3);
        
            }catch(Exception e)
            {
                
            }
            ftpClient.logout(); //Cerrar sesión
            ftpClient.disconnect();//Desconectarse del servidor
            updateMessage("Archivos con ID "+id+" cargados en la nube satisfactoriamente");
          
        }
        catch(Exception e)
                {
                    System.out.println(e.getMessage());
                    System.out.println("Algo malo sucedió");
                }
     
      task_subirnube=null;
      return null;
          }
      };
      title.textProperty().bind(task_subirnube.messageProperty());

    // java 8 construct, replace with java 7 code if using java 7.
    task_subirnube.setOnSucceeded(e -> {
      title.textProperty().unbind();
      // this message will be seen.
       closedispositivos();
       System.exit(0);
    });

       pb.progressProperty().bind(task_subirnube.progressProperty());
       pb.visibleProperty().bind(task_subirnube.runningProperty());
      thread_sibirnube=new Thread(task_subirnube);
      thread_sibirnube.setDaemon(true);
      thread_sibirnube.start();
      
    }
  private void descargar_nube() 
  {
  // get an ftpClient object  
       
  FileOutputStream fos = null;  

  try {  
   // pass directory path on server to connect  
    ftpClient.connect(InetAddress.getByName("motofacil.com.co"));
    
        String dir= "public_html";
            
      //      ftpClient.makeDirectory(dir);
        //    ftpClient.changeWorkingDirectory(dir);//Cambiar directorio de trabajo
           

   // pass username and password, returned true if authentication is  
   // successful  
   boolean login = ftpClient.login("sdnc8ew7d6fb","Yamithg2017*");  

   if (login) {  
    System.out.println("Connection established...");  
     ftpClient.enterLocalPassiveMode();
    fos = new FileOutputStream("c://fotohuella/datos_form.txt");  
    boolean download = ftpClient.retrieveFile("/public_html/datos_form.txt",fos);  
    if (download) {  
     System.out.println("File downloaded successfully !");  
    } else {  
     System.out.println("Error in downloading file !");  
    }  

    // logout the user, returned true if logout successfully  
    boolean logout = ftpClient.logout();  
    if (logout) {  
     System.out.println("Connection close...");  
     fos.close();
    }  
   } else {  
    System.out.println("Connection fail...");  
   }  

  } catch (SocketException e) {  
   e.printStackTrace();  
  } catch (IOException e) {  
   e.printStackTrace();  
  } finally {  
   try {  
    ftpClient.disconnect();  
   } catch (IOException e) {  
    e.printStackTrace();  
   }  
  }  
 } 

private void closedispositivos()
{
        stop_();
        stopcamera();
        Lector.stopCapture();
            try {
                if( this.timer!=null)
                {    
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(EnrrolarFotoHuella.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        
}

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

