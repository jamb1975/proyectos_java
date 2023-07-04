/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verificarfotohuella;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
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
public class SegBiom extends Application {

private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
private DPFPTemplate template;
private DPFPTemplate template2;
private EnumMap<DPFPFingerIndex, DPFPTemplate> templates=new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
public static String TEMPLATE_PROPERTY = "template";
private ImageView iv_huella=new ImageView();
private ImageView iv_foto=new ImageView();
private Text textArea=new Text();
private ScheduledExecutorService timer;
private ToggleGroup toggleGroup=new ToggleGroup();
private RadioButton rb_indicederecho=new RadioButton("Indice Derecho");
private RadioButton rb_indiceizquierdo=new RadioButton("Indice Izquierdo");
private javafx.scene.image.Image imagetomarfoto= new javafx.scene.image.Image("/images/tomarfoto.png");
private javafx.scene.image.Image imagenube= new javafx.scene.image.Image("/images/nube.png");
private javafx.scene.image.Image imagefoto= new javafx.scene.image.Image("/images/solucion.png");
private javafx.scene.image.Image imagenotverified= new javafx.scene.image.Image("/images/notverified.png");
private Button bu_tomarfoto=new Button();
private Button bu_nube=new Button();
private ImageView currentFrame=new ImageView();
private boolean tomarfoto=false;
private FTPClient ftpClient = new FTPClient();

// the OpenCV object that realizes the video capture
static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
private static  VideoCapture capture = new VideoCapture();
private int canthuellas=1;
private static final Label title=new Label("Tomar Huella Indice Derecho");
private String id="";
private String nit="";
private DPFPVerification verificator = DPFPGlobal.getVerificationFactory().createVerification();
private TextField status = new TextField("[status line]");
private TextArea log = new TextArea();
// a flag to change the button behavior
private WritableImage writableimage;
BufferedImage bufferedImage;
public DPFPFeatureSet featuresinscripcion;
private final ProgressBar pb = new ProgressBar(0);
private static Task<Void> task_subirnube;
private static Thread thread_sibirnube; 
private TitledPane tp_generic;
private ImageView iv_tomarfoto=new ImageView();
private ImageView iv_nube=new ImageView();
private Mat frame=new Mat();
FileOutputStream filefoto = null;  
FileOutputStream filehuellaizq = null;
FileOutputStream filehuellader = null;
private HBox hbox=new HBox();

    public Parent createContent() {
        final String EXTERNAL_STYLESHEET = "http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600";
         capture.open("/resources/video.MP4");
        capture.set(650 , 640); 
        capture.set(650 , 480); 
        capture.open(0);
        tp_generic=new TitledPane();
        tp_generic.setText("INSSEND VERIFICADOR DE HUELLAS");
        tp_generic.setCollapsible(false);
        pb.setVisible(false);
        rb_indicederecho.setToggleGroup(toggleGroup);
        rb_indicederecho.setSelected(true);
        rb_indiceizquierdo.setToggleGroup(toggleGroup);
        StackPane root = new StackPane();
        iv_huella.setFitHeight(200);
        iv_huella.setFitWidth(200);
        iv_foto.setFitHeight(200);
        iv_foto.setFitWidth(200);
          iv_foto.setFitHeight(200);
        iv_foto.setFitWidth(200);
        iv_foto.setImage(imagefoto);
        iv_tomarfoto.setFitHeight(40);
        iv_tomarfoto.setFitWidth(40);
        currentFrame.setFitHeight(200);
        currentFrame.setFitWidth(200);
        iv_tomarfoto.setImage(imagetomarfoto);
        iv_nube.setFitHeight(40);
        iv_nube.setFitWidth(40);
        iv_nube.setImage(imagenube);
       bu_tomarfoto.setGraphic(iv_tomarfoto);
        bu_tomarfoto.setOnAction(e->{
            capturarfoto();
        });
        bu_nube.setGraphic(iv_nube);
        bu_nube.setOnAction(e->{
            save();
            closedispositivos();
            stagefotohuella.close();
        });
           stagefotohuella.setOnCloseRequest(e->{
            closedispositivos();
           });
        hbox.getChildren().addAll(bu_tomarfoto,bu_nube);
        GridPane gp=new GridPane();
        GridPane.setHalignment(hbox, HPos.CENTER);
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setHalignment(iv_foto, HPos.CENTER);
        GridPane.setHalignment(iv_huella, HPos.CENTER);
        GridPane.setHalignment(gp, HPos.CENTER);
        GridPane.setHalignment(hbox, HPos.CENTER);
        gp.alignmentProperty().set(Pos.CENTER);
       // gp.setMaxWidth(600);
        gp.setHgap(5);
        gp.setVgap(5);
        gp.add(hbox, 0, 0,3,1);
        gp.add(iv_huella,0,1,1,1);
        gp.add(currentFrame,1,1,1,1);
        gp.add(iv_foto, 2, 1,1,1);
        gp.add(log, 0, 2,3,1);
        tp_generic.setContent(gp);
        root.getChildren().add(tp_generic);
        gp.setAlignment(Pos.TOP_CENTER);
          tp_generic.setContent(gp);
        
        gp.getStylesheets().add("/fxnomina/SofackarStylesCommon.css");
        gp.getStyleClass().add("background");
      
        try{
        
        Iniciar();
	start_();
        EstadoHuellas();
        capturar_datos_empleado();
        descargar_huellayfoto();
        onLoadFPT();
        capturar_video();
        disabled();
        }catch(Exception e)
        {
             stop_();
             stopcamera();
        }
     return root;

    }

    /**
     * @param args the command line arguments
     */
    
   protected void Iniciar(){
   Lector.addDataListener(new DPFPDataAdapter() {
    @Override
    public void dataAcquired(final DPFPDataEvent e) {
    Platform.runLater(new Runnable() {    public void run() {
    EnviarTexto("La Huella Digital ha sido Capturada");
        try {
            ProcesarVerificacion(e.getSample());
        } catch (IOException ex) {
            
        }
    }});}
   });

   Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
    @Override
    public void readerConnected(final DPFPReaderStatusEvent e) {
    Platform.runLater(new Runnable() {    public void run() {
    EnviarTexto("El Sensor de Huella Digital esta Activado o Conectado");
    }});}
    @Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
    Platform.runLater(new Runnable() {    public void run() {
    EnviarTexto("El Sensor de Huella Digital esta Desactivado o no Conecatado");
    }});}
   });

   Lector.addSensorListener(new DPFPSensorAdapter() {
    @Override public void fingerTouched(final DPFPSensorEvent e) {
    Platform.runLater(new Runnable() {    public void run() {
    EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella");
    }});}
    @Override public void fingerGone(final DPFPSensorEvent e) {
    Platform.runLater(new Runnable() {    public void run() {
    EnviarTexto("El dedo ha sido quitado del Lector de Huella");
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
public  Image CrearImagenHuella(DPFPSample sample) {
    return DPFPGlobal.getSampleConversionFactory().createImage(sample);
}

public void EnviarTexto(String string) {
       textArea.setText(string + "\n");
}
public  void EstadoHuellas(){
   // EnviarTexto("Muestra de Huellas Necesarias para Guardar Template "+ Reclutador.getFeaturesNeeded());
}
public  void start_(){
    Lector.startCapture();
    EnviarTexto("Utilizando el Lector de Huella Dactilar ");
}

public  void stop_(){
        Lector.stopCapture();
        EnviarTexto("No se está usando el Lector de Huella Dactilar ");
} 

    public DPFPTemplate getTemplate() {
        return template;
    }

    public void setTemplate(DPFPTemplate template) {
     //    DPFPTemplate old = this.template;
   this.template = template;
   //firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }
public void ProcesarVerificacion(DPFPSample sample) throws IOException
{
    
DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

		// Check quality of the sample and start verification if it's good
		if (features != null)
		{
			// Compare the feature set with our template
			DPFPVerificationResult result = 
				verificator.verify(features, getTemplate2());
			updateStatus(result.getFalseAcceptRate());
			if (result.isVerified())
                        {
				makeReport("Huella Dactilar Verificada");
                            
                                Image image=CrearImagenHuella(sample);
                                 DibujarHuella(image);
                                LoadFoto();
                                enabled();
                        }
			else
                        {
				makeReport("Huella Dactilar No Verificada");
                                iv_huella.setImage(imagenotverified);
                                iv_foto.setImage(imagefoto);
                                disabled();
                        }
		}
}
private void capturar_video()
{
     Runnable frameGrabber = new Runnable() {
					
					@Override
					public void run()
					{
						javafx.scene.image.Image imageToShow = grabFrame();
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
                                                   }
                                                    try {
                                                        out.write(buffer.toArray());
                                                    } catch (IOException ex) {
                                                    }
                                                    try { 
                                                        out.close();
                                                    } catch (IOException ex) {
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
public static void main(String[] args) {
     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
      Mat m = new Mat(5, 10, CvType.CV_16S, new Scalar(0));//CV_8UC1
    System.out.println("OpenCV Mat: " + m);
    Mat mr1 = m.row(1);
    mr1.setTo(new Scalar(1));
    Mat mc5 = m.col(5);
    mc5.setTo(new Scalar(5));
    System.out.println("m = " + m.dump());
    // capture = new VideoCapture();
    //capture.open("/resources/video.MP4");
    //capture.set(200 , 640); 
    //capture.set(650 , 480); 
    capture.open(0);
    launch(args);
    }
  

protected DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose)
	{
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}

private void updateStatus(int FAR)
	{
		// Show "False accept rate" value
		setStatus(String.format("False Accept Rate (FAR) = %1$s", FAR));
	}
public void setStatus(String string) {
		status.setText(string);
	}

public void makeReport(String string) {
		log.appendText(string + "\n");
	}
private void onLoadFPT() {
			try {
				FileInputStream stream = new FileInputStream("c:/fotohuella/"+nit+"/"+id+"_indexleft.fpt");
				byte[] data = new byte[stream.available()];
				stream.read(data);
				stream.close();
				DPFPTemplate t = DPFPGlobal.getTemplateFactory().createTemplate();
				t.deserialize(data);
				setTemplate2(t);
			} catch (Exception ex) {
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(ex.getLocalizedMessage());
				
			}
		
	}

    public DPFPTemplate getTemplate2() {
        return template2;
    }

    public void setTemplate2(DPFPTemplate template2) {
        this.template2 = template2;
    }

public void LoadFoto() throws FileNotFoundException, IOException
{
    FileInputStream stream = new FileInputStream("c:\\fotohuella\\"+nit+"\\"+id+".png");
    bufferedImage=ImageIO.read(stream);
    writableimage=SwingFXUtils.toFXImage(bufferedImage, writableimage);
    iv_foto.setImage(writableimage);
				
}  
public void DibujarHuella(Image image) {
    BufferedImage buffered = (BufferedImage) image;
   
            javafx.scene.image.Image img2 = SwingFXUtils.toFXImage(buffered, null);
            iv_huella.setImage(img2);
     
 }
private javafx.scene.image.Image ByteToImageFx(byte[] img)
{
   
   InputStream in=new ByteArrayInputStream(img);
   javafx.scene.image.Image image=new javafx.scene.image.Image(in);
  
 

        
  return image;      
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
            buffIn = new BufferedInputStream(new FileInputStream("c:\\fotohuella\\"+nit+"\\"+id+"_indexleft.fpt"));//Ruta del archivo para enviar
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(id+"_indexleft.fpt", buffIn);//Ruta completa de alojamiento en el FTP
             updateMessage("Huella Indice Izquierdo con ID "+id+" cargado en la nube satisfactoriamente");
              updateProgress(1, 3);
           
            }catch(Exception e)
            {
                
            }
            buffIn=null;
            try{
            buffIn = new BufferedInputStream(new FileInputStream("c:\\fotohuella\\"+nit+"\\"+id+"_indexright.fpt"));//Ruta del archivo para enviar
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(id+"_indexright.fpt", buffIn);
             updateMessage("Huella Indice Derecho con ID "+id+" cargado en la nube satisfactoriamente");
             updateProgress(2, 3);
         
            }catch(Exception e)
            {
                
            }
            buffIn=null;
            try{
            ftpClient.changeWorkingDirectory("/"+dirfoto);//Cambiar directorio de trabajo
            System.out.println("Se cambió satisfactoriamente el directorio");
            buffIn = new BufferedInputStream(new FileInputStream("c:\\fotohuella\\"+nit+"\\"+id+".png"));//Ruta del archivo para enviar
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(id+".png", buffIn);
            updateMessage("Foto con ID "+id+" cargado en la nube satisfactoriamente");
            updateProgress(3, 3);
        
            }catch(Exception e)
            {
                
            }
            buffIn.close(); //Cerrar envio de arcivos al FTP
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
                 
            }
        
}

  private void descargar_nube() 
  {
  // get an ftpClient object  
       
  FileOutputStream fos = null;  

  try {  
    ftpClient.connect(InetAddress.getByName("motofacil.com.co"));
       String dir= "public_html";
     boolean login = ftpClient.login("sdnc8ew7d6fb","Yamithg2017*");  

   if (login) {  
    System.out.println("Connection established...");  
     ftpClient.enterLocalPassiveMode();
    fos = new FileOutputStream("c:/fotohuella/datos_form.txt");  
    boolean download = ftpClient.retrieveFile("/public_html/datos_form.txt",fos);  
    if (download) {  
     System.out.println("File downloaded successfully !");  
    } else 
    {  
           System.out.println("Error in downloading file !");  
    }  
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
  
 private void descargar_huellayfoto() 
 {
  // get an ftpClient object  
       
   filefoto = null;  
   filehuellaizq = null;
   filehuellader = null;
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
    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    /*filefoto = new FileOutputStream("c:\\fotohuella\\"+nit+"\\"+id+"_remote.png");
    filehuellader = new FileOutputStream("c:\\fotohuella\\"+nit+"\\"+id+"_indexright.fpt");
    filehuellaizq = new FileOutputStream("c:\\fotohuella\\"+nit+"\\"+id+"_indexleft.fpt");
    boolean download = ftpClient.retrieveFile("/public_html/cia-sicov/sicov/archivos/infractores/fotos/"+id+".png",filefoto);  
   // boolean download = ftpClient.retrieveFile("/public_ftp/"+id+".png",filefoto);  
   
    if (download) {  
     System.out.println("File Foto downloaded successfully !");  
    } else 
    {  
           System.out.println("Error in downloading file !");  
    }
     filefoto.close();
    download = ftpClient.retrieveFile("/public_html/cia-sicov/sicov/archivos/infractores/huellas/"+id+"_indexleft.fpt",filehuellaizq);  
   //download = ftpClient.retrieveFile("/public_ftp/huellas/"+id+"_indexleft.fpt",filehuellaizq);  
   
    if (download) {  
     System.out.println("File huella Izq downloaded successfully !");  
    } else 
    {  
           System.out.println("Error in downloading file !");  
    }
     filehuellaizq.close();
    download = ftpClient.retrieveFile("/public_html/cia-sicov/sicov/archivos/infractores/huellas/"+id+"_indexright.fpt",filehuellader);  
    // download = ftpClient.retrieveFile("/public_ftp/huellas/"+id+"_indexright.fpt",filehuellader);  
    
   if (download) {  
     System.out.println("File huella der downloaded successfully !");  
    } else 
    {  
           System.out.println("Error in downloading file !");  
    }
     filehuellader.close();*/
     
     //**********************************************************************
     //Metodo Inputstresm retrievefilestreasm
            String remoteFileFoto = "/public_html/cia-sicov/sicov/archivos/infractores/fotos/"+id+".png";
            File downloadFileFoto = new File("c:/fotohuella/"+nit+"/"+id+"_remote.png");
            OutputStream outputStreamFoto = new BufferedOutputStream(new FileOutputStream(downloadFileFoto));
            InputStream inputStreamFoto = ftpClient.retrieveFileStream(remoteFileFoto);
            byte[] bytesArrayFoto = new byte[4096];
            int bytesReadFoto = -1;
            while ((bytesReadFoto = inputStreamFoto.read(bytesArrayFoto)) != -1) {
                outputStreamFoto.write(bytesArrayFoto, 0, bytesReadFoto);
            }
 
            boolean success = ftpClient.completePendingCommand();
            if (success) {
                System.out.println("File #Foto has been downloaded successfully.");
            }
            outputStreamFoto.close();
            inputStreamFoto.close();
 
            String remoteFileHuellaRight = "/public_html/cia-sicov/sicov/archivos/infractores/huellas/"+id+"_indexright.fpt";
            File downloadFileHuellaRight = new File("c:/fotohuella/"+nit+"/"+id+"_indexright.fpt");
            OutputStream outputStreamHuellaRight = new BufferedOutputStream(new FileOutputStream(downloadFileHuellaRight));
            InputStream inputStreamHuellaRight = ftpClient.retrieveFileStream(remoteFileHuellaRight);
            byte[] bytesArrayHuellaRight = new byte[4096];
            int bytesReadHuellaRight = -1;
            while ((bytesReadHuellaRight = inputStreamHuellaRight.read(bytesArrayHuellaRight)) != -1) {
                outputStreamHuellaRight.write(bytesArrayHuellaRight, 0, bytesReadHuellaRight);
            }
 
            boolean successHuellaRight = ftpClient.completePendingCommand();
            if (successHuellaRight) {
                System.out.println("File #HuellaRight has been downloaded successfully.");
            }
            outputStreamHuellaRight.close();
            inputStreamHuellaRight.close();
              String remoteFileHuellaLeft = "/public_html/cia-sicov/sicov/archivos/infractores/huellas/"+id+"_indexleft.fpt";
            File downloadFileHuellaLeft = new File("c:/fotohuella/"+nit+"/"+id+"_indexleft.fpt");
            OutputStream outputStreamHuellaLeft = new BufferedOutputStream(new FileOutputStream(downloadFileHuellaLeft));
            InputStream inputStreamHuellaLeft = ftpClient.retrieveFileStream(remoteFileHuellaLeft);
            byte[] bytesArrayHuellaLeft = new byte[4096];
            int bytesReadHuellaLeft = -1;
            while ((bytesReadHuellaLeft = inputStreamHuellaLeft.read(bytesArrayHuellaLeft)) != -1) {
                outputStreamHuellaLeft.write(bytesArrayHuellaLeft, 0, bytesReadHuellaLeft);
            }
 
            boolean successHuellaLeft = ftpClient.completePendingCommand();
            if (successHuellaLeft) {
                System.out.println("File #HuellaLeft has been downloaded successfully.");
            }
            outputStreamHuellaLeft.close();
            inputStreamHuellaLeft.close();
     //**********************************************************************
    // logout the user, returned true if logout successfully  
    boolean logout = ftpClient.logout();  
    if (logout) {  
     System.out.println("Connection close...");  
    ftpClient.disconnect();  
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
private void disabled()
{
    bu_nube.setDisable(true);
    bu_nube.setVisible(false);
    bu_tomarfoto.setDisable(true);
    bu_tomarfoto.setVisible(false);
}
private void enabled()
{
    bu_nube.setDisable(false);
    bu_nube.setVisible(true);
    bu_tomarfoto.setDisable(false);
    bu_tomarfoto.setVisible(true);
}
public void save()
{
    FEmpleado.LoadImageFoto2();
}   
    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}