
package  isoftconta.servicios.configuracion.contabilidad;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfWriter;
import isoftconta.IsoftConta;
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import isoftconta.servicios.ParametrosContabilidadController;
import isoftconta.servicios.ProductoController;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import modelo.EntidadesStatic;
import modelo.Producto;
import org.controlsfx.control.Notifications;





/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FICI_Producto extends  Application {
    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
  
   
   
    private GridPane gp_generic;
    private TextField  codigo ;
    private TextField  codigobarras ;
    private TextField  nombre;
    private TextField  descripcion;
    private TextField  precio;
    private TextField referencia;
    private TextField modoconsumo;
    private TextField topminimo;
    private TextField topmaximo;
    private TextField iva;
    private TextField observaciones=new TextField();
    private Button save;
    private Button nuevo;
    private Stage stage;
    private ImageView logo;
    private ImageView codbarras;
    private Button bu_logo;
    private Button bu_codebar;
    private Button bu_gencodebar;
    private Image loadlogo;
    private GridPane Gp_Message;
    private     Label L_Message;
    private  Runnable Task_Message;
    private boolean permitirproceso=false;
    Thread backgroundThread;
    private File fileLogo;
    private FileChooser fileChooser = new FileChooser();
    private WritableImage wi;
    private BufferedImage bi;
    private static Notifications notificationBuilder = Notifications.create();
    private ToolBar toolBar=new ToolBar();
    public Parent createContent() throws IOException {
    char IM_BOLD = '\uf0c7';
    char IM_NUEVO = '\uf0fe';
    char IM_PDF = '\uf1c1';
        javafx.scene.paint.Color randomColor = new javafx.scene.paint.Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_save=  IsoftConta.icoMoon.create(IM_BOLD).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new javafx.scene.paint.Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_NUEVO=  IsoftConta.icoMoon.create(IM_NUEVO).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new javafx.scene.paint.Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_pdf=  IsoftConta.icoMoon.create(FontAwesome.Glyph.FILE_PDF_ALT.getChar()).sizeFactor(2).color(randomColor).useGradientEffect();
        randomColor = new javafx.scene.paint.Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_logo=  IsoftConta.icoMoon.create(FontAwesome.Glyph.FILE_PICTURE_ALT.getChar()).sizeFactor(2).color(randomColor).useGradientEffect();
        randomColor = new javafx.scene.paint.Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_codebar=  IsoftConta.icoMoon.create(FontAwesome.Glyph.BARCODE.getChar()).sizeFactor(2).color(randomColor).useGradientEffect();

        stack = new StackPane();
        save = new Button("",glyph_save);
        save.setTooltip(new Tooltip("Guardar registro"));
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        nuevo = new Button("",glyph_NUEVO);
        nuevo.setTooltip(new Tooltip("Nuevo registro"));
        nuevo.setOnAction(e -> {
            nuevo();
        });
          
      
        
        
      logo=new ImageView("/images/foto.png");
      logo.setFitHeight(100);
      logo.setFitWidth(100);
      codbarras=new ImageView();
      codbarras.setFitHeight(20);
      codbarras.setFitWidth(100);
      //***********************************************  
        stack = new StackPane();
        codigo=new TextField("");
        codigo.setMinWidth(500);
         
        topminimo=new TextField("");
        topmaximo=new TextField("");
        codigo.getProperties().put("requerido", true);
        iva=new TextField("");
        iva.getProperties().put("requerido", true);
        codigobarras=new TextField("");
        codigobarras.getProperties().put("requerido", true);
        codigobarras.setMinWidth(500);
        
       codigobarras.focusedProperty().addListener(new ChangeListener<Boolean>()
     {
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
        if(codigobarras.getText()!=null)
        {
        if (newPropertyValue)
        {
            System.out.println("new value"+codigobarras.getText());
        }
        else
        {
            
           System.out.println("old value"+codigobarras.getText());
            try {
                if(codigobarras.getText().length()>=13)
                {     
                fcodigobarras(codigobarras.getText());
                }
            } catch (DocumentException ex) {
                Logger.getLogger(FICI_Producto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FICI_Producto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        }
    }
        });
        nombre=new TextField("");
        nombre.getProperties().put("requerido", true);
        nombre.setMinWidth(300);
      
        precio=new TextField("");
        referencia=new TextField("");
        descripcion=new TextField("");
        modoconsumo=new TextField("");
       
        bu_logo =new Button("",glyph_logo);
        bu_logo.setTooltip(new Tooltip("Cargas Imagen de Producto"));
        bu_logo.setOnAction(e->{LoadImageLogo();
        
         });
        
         bu_codebar =new Button("",glyph_pdf);
         bu_codebar.setTooltip(new Tooltip("Imprimir Código de Barras Ean13"));
         bu_codebar.setOnAction(e->{
        try {
            printcodebar();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FICI_Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(FICI_Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FICI_Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
         });
         
        
         bu_gencodebar =new Button("",glyph_codebar);
         bu_gencodebar.setTooltip(new Tooltip("Generar Código de Barras Ean13"));
         bu_gencodebar.setOnAction(e->{
       
            generarcodigobarras();
       
         });
        toolBar.getItems().clear();
        toolBar.getItems().addAll(save,nuevo,bu_logo,bu_gencodebar,bu_codebar);
        gp_generic=new GridPane();
        gp_generic.setPadding(new Insets(5, 5, 5, 5));
        gp_generic.add(toolBar, 0, 0,2,1);
        gp_generic.add(new Label("Referencia:"), 0, 2,1,1);
        gp_generic.add(referencia, 1, 2,1,1);
        gp_generic.add(new Label("Codigo de Barras: "), 0, 3,1,1);
        gp_generic.add(codigobarras, 1, 3,1,1);
        gp_generic.add(new Label("Nombre:"), 0, 4,1,1);
        gp_generic.add(nombre, 1, 4,1,1);
        gp_generic.add(logo, 2, 0,2,5);
        gp_generic.add(codbarras, 2, 5,2,3);
        gp_generic.add(new Label("Precio:"), 0, 5,1,1);
        gp_generic.add(precio, 1, 5,1,1);
        gp_generic.add(new Label("ModoConsumo:"), 0, 6,1,1);
        gp_generic.add(modoconsumo, 1, 6,1,1);
        gp_generic.add(new Label("Ubicación:"), 0, 7,1,1);
        gp_generic.add(descripcion, 1, 7,1,1);
        gp_generic.add(new Label("Stock Mínimo:"), 0, 8,1,1);
        gp_generic.add(topminimo, 1, 8,1,1);
        gp_generic.add(new Label("Stock Máximo:"), 0, 9,1,1);
        gp_generic.add(topmaximo, 1, 9,1,1);
        gp_generic.add(new Label("Iva:"), 0, 10,1,1);
        gp_generic.add(iva, 1, 10,1,1);
        gp_generic.add(new Label("Observaciones:"), 0, 11,1,1);
        gp_generic.add(observaciones, 1, 11,1,1);
        
        
       
        gp_generic.setHgap(2);
        gp_generic.setVgap(2);
       
        gp_generic.setAlignment(Pos.TOP_LEFT);
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        gp_generic.autosize();
       
      
     
      
      
        stack.setAlignment(Pos.TOP_CENTER);
        //stack.setMinWidth(700);
        stack.getChildren().addAll(gp_generic);
     
       crearoeditar();
    return stack;
    }
  
 
   
      @Override
    public void start(Stage primaryStage) throws Exception {
 
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

private void nuevo()
{
    EntidadesStatic.es_producto=null;
    EntidadesStatic.es_producto=new Producto();
    empty_field();
    iva.setText("0");
    topmaximo.setText("0");
    topminimo.setText("0");
    
}
public void save() throws InterruptedException
{ 
    
    validar_formulario();
    set_datos();
  
  
if(EntidadesStatic.es_producto.getId()==null)
      {
       int resultprocess= ProductoController.servicio_crear();
                   if(resultprocess==1)
                   {
                       
                     notificationBuilder.text("Registro guardado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                   }
                   else
                   {
                       notificationBuilder.text("Error en el proceso al guardar")
                                       .position(Pos.TOP_RIGHT)
                                        .showError();
                   }
               }else 
               {
                    ParametrosContabilidadController.servicio_actualizar();
                     notificationBuilder.text("Registro  actualizado satisfactoriamente")
                                            .position(Pos.TOP_RIGHT)
                                            .showConfirm();
                }
}

private void set_datos()
{
      
        EntidadesStatic.es_producto.setCodigobarras(codigobarras.getText().trim());
        EntidadesStatic.es_producto.setNombre(nombre.getText());
        EntidadesStatic.es_producto.setPrecio(new BigDecimal(precio.getText().trim()));
        EntidadesStatic.es_producto.setDescrip(descripcion.getText());
        EntidadesStatic.es_producto.setModoconsumo(modoconsumo.getText());
        EntidadesStatic.es_producto.setReferencia(referencia.getText());
        EntidadesStatic.es_producto.setObservaciones(observaciones.getText());
        try
        {
        EntidadesStatic.es_producto.setTopminimo(Integer.valueOf(topminimo.getText()));
        }catch(Exception e)
        {
           EntidadesStatic.es_producto.setTopminimo(0);  
        }
        try
        {
        EntidadesStatic.es_producto.setTopmaximo(Integer.valueOf(topmaximo.getText()));
        }catch(Exception e)
        {
           EntidadesStatic.es_producto.setTopmaximo(0);  
        }
        try{/*
09+
            54+
¿+'0pi76u5456            [2}54
            */
           EntidadesStatic.es_producto.setIva(Integer.valueOf(iva.getText().trim()));
           }catch(Exception e)
        {
           EntidadesStatic.es_producto.setIva(0);  
        }
       
       
        
               EntidadesStatic.es_producto.setProveedores(null);
           
        //cargamos la imagen
        save_img();
      
       
       
}

public void validar_formulario() throws InterruptedException
{
    permitirproceso=true;
      for(Object n:gp_generic.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            if(general.getProperties().get("requerido")!=null)
            {
            if((general.getLength()<=0) && ((boolean)general.getProperties().get("requerido")==true))
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
        
         if(general.getProperties().get("requerido")!=null)
         {
         if(general.getSelectionModel().getSelectedIndex()<0 &&(boolean)general.getProperties().get("requerido")==true)
         {
                  permitirproceso=false; 
                  general.getStylesheets().add(0,"/abaco/personal.css");
               general.getStylesheets().set(0,"/abaco/personal.css");
         }  
         } 
        }
        }
        }
     FadeTransition ft = new FadeTransition(Duration.millis(2000),save);
        //ft.setFromValue(0.0);
       // ft.setToValue(1);
      
        ft.play();
       
        //success.setOpacity(0);
      
       ft.setOnFinished(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
              for(Object n:gp_generic.getChildren().toArray())
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
         if(general.getSelectionModel().getSelectedIndex()<0 &&(boolean)general.getProperties().get("requerido")==true)
         {
             general.getStylesheets().set(0,"/abaco/SofackarStylesCommon.css");
             
         }
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
// A change listener to track the change in selected index
public   void indexChanged(ObservableValue<? extends Number> observable,
Number oldValue,
Number newValue) { 

} 

public void LoadImageLogo() {
        
       
        fileLogo=fileChooser.showOpenDialog(stage);
         
        if(fileLogo!=null)
        {
            byte[]img2;
            try {
                   bi = ImageIO.read(fileLogo);
                  
                  wi=SwingFXUtils.toFXImage(bi, wi);
                  loadlogo=wi;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
        }
    
        logo.setImage(loadlogo);
    }

public void save_img()
{
        byte[]data;
        InputStream in;
        try{
        in = new FileInputStream(fileLogo);
        data=new byte[in.available()];
	in.read(data);
        EntidadesStatic.es_producto.setImg(data);
        }catch(Exception e)
        {
           
        }
}
public void getDatos()
{
    
       codigobarras.setText(EntidadesStatic.es_producto.getCodigobarras());  
       nombre.setText(EntidadesStatic.es_producto.getNombre());
       referencia.setText(EntidadesStatic.es_producto.getReferencia());
       descripcion.setText(EntidadesStatic.es_producto.getDescrip());
       observaciones.setText(EntidadesStatic.es_producto.getObservaciones());
       precio.setText(EntidadesStatic.es_producto.getPrecio().toString());
       modoconsumo.setText(EntidadesStatic.es_producto.getModoconsumo());
       topminimo.setText(String.valueOf(EntidadesStatic.es_producto.getTopminimo()));
       topmaximo.setText(String.valueOf(EntidadesStatic.es_producto.getTopmaximo()));
       iva.setText(String.valueOf(EntidadesStatic.es_producto.getIva()));
        
       
        
       try{
       InputStream in = new ByteArrayInputStream(EntidadesStatic.es_producto.getImg());
       bi = ImageIO.read(in);
       wi=new WritableImage(1,1);
       wi=SwingFXUtils.toFXImage(bi, wi);
       //img=new ImageView();
       loadlogo=wi;
       logo.setImage(loadlogo);
       }catch(Exception e)
                  {
                  
                  }
       
        
}

public void crearoeditar()
{
   
    if(EntidadesStatic.es_producto!=null)
    {
     if(EntidadesStatic.es_producto.getId()!=null)
     {   
        getDatos();
     }
     else
     {
         nuevo();
     }
        
    }
    else
    {
        EntidadesStatic.es_producto=new Producto();
        nuevo();
    }
   
}
private void fcodigobarras(String codigo) throws FileNotFoundException, DocumentException, IOException
{
   if(codigo==null)
   {
       codigo="9781935182610";
   }
   System.out.println("codigo->"+codigo);
   //Es el tipo de clase 
 /*  BarcodeEAN codeEAN = new BarcodeEAN();
   //Seteo el tipo de codigo
   codeEAN.setCodeType(Barcode.EAN13);
   //Setep el codigo
   codeEAN.setCode("9781935182610");
   //Paso el codigo a imagen
   img = codeEAN.createImageWithBarcode( pdfw.getDirectContent(), BaseColor.GREEN, BaseColor.RED);
       bi = ImageIO.read(in);
       wi=new WritableImage(1,1);
       wi=SwingFXUtils.toFXImage(bi, wi);
       //img=new ImageView();
      Image loadcodbarras=wi;
       codbarras.setImage(loadcodbarras);
       */
   BarcodeEAN codeEAN = new BarcodeEAN();
   //Seteo el tipo de codigo
   codeEAN.setCodeType(Barcode.EAN13);
   //Setep el codigo
   codeEAN.setCode(codigo);  
   codeEAN.setGuardBars(true);
  // codeEAN.setAltText("9781935182610");
   Barcode39 code39ext = new Barcode39();
   code39ext.setCode("1234567");
   code39ext.setStartStopText(false);
   code39ext.setExtended(true);
 Barcode128 code128 = new Barcode128();
 code128.setCode("9781935182610");  
java.awt.Image rawImage = codeEAN.createAwtImage(Color.blue, Color.WHITE);
BufferedImage outImage = new BufferedImage(rawImage.getWidth(null), rawImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
outImage.getGraphics().drawImage(rawImage, 0, 0, null);
ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
ImageIO.write(outImage, "png", bytesOut);
bytesOut.flush();
byte[] pngImageData = bytesOut.toByteArray();
InputStream in = new ByteArrayInputStream(pngImageData);
       bi = ImageIO.read(in);
       wi=new WritableImage(1,1);
       wi=SwingFXUtils.toFXImage(bi, wi);
       //img=new ImageView();
       loadlogo=wi;
       codbarras.setImage(loadlogo);
      
   
}
private void empty_field()
{
               for(Object n:gp_generic.getChildren().toArray())
    {
        if(n.getClass()==TextField.class)
        {
            TextField general=(TextField)n;
            general.setText("");
           
        }  
         else
        {
             if(n.getClass()==ChoiceBox.class)
        {
            ChoiceBox general=(ChoiceBox)n;
           
            general.getSelectionModel().select(-1);
           
        }
              else
        {
             if(n.getClass()==TextArea.class)
        {
            TextArea general=(TextArea)n;
           
            general.setText("");
           
        }
                    else
        {
         if(n.getClass()==RadioButton.class)
        {
            RadioButton general=(RadioButton)n;
           
            general.setSelected(false);
           
        }
        else
        {
         if(n.getClass()==CheckBox.class)
        {
            CheckBox general=(CheckBox)n;
           
            general.setSelected(false);
           
        }
        }
        }
        }
        }
        }
       
}
  
private void printcodebar() throws FileNotFoundException, DocumentException, IOException
{
    /* Tamamaño en Cm de la pagina
    Ejemplo: 216x720 points
    216pt/72 points por inch=3inch
    720pt/72 points por inch=10inch
    El Tamaños de la pagina es 3 inch x 10 inch
    3 inch x 2.54=7.62 cm
    10 inch x 2.54=25.5 cm
    */
    if(codigobarras.getText().length()>=13)
   {
     
      Rectangle one = new Rectangle(84,40);
        Document document = new Document(one,2,2,2,2);
    PdfWriter pw= PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/abaco/codigobarras.pdf"));
           document.open();
        BarcodeEAN codeEAN = new BarcodeEAN();
   //Seteo el tipo de codigo
   codeEAN.setCodeType(Barcode.EAN13);
   //Setep el codigo
   //codeEAN.setSize(16);
    codeEAN.setCode(codigobarras.getText());
    //Paso el codigo a imagen
    com.itextpdf.text.Image img = codeEAN.createImageWithBarcode( pw.getDirectContent(), BaseColor.GREEN, BaseColor.RED);
    document.add(img);
    document.close();
     File file = new File("/home/adminlinux/abaco/codigobarras.pdf");
       
      String os = System.getProperty("os.name"); 
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
      { 
        Runtime r = Runtime.getRuntime(); 
        Process p = r.exec("/usr/bin/evince /home/adminlinux/abaco/codigobarras.pdf"); 
      }
else
 {
      
    Desktop.getDesktop().open(file);
 
 }
   }
    
}
private void generarcodigobarras()
{
    String codigopais="770";
    String codempresa="00000";
    for(int i=1;i<=9999;i++)
    {
       String codebar=codigopais+codempresa+getcerosizquierda(String.valueOf(i))+"7";
       List<Producto> li_prod= EntidadesStatic.li_productos.stream().filter(line ->line.getCodigobarras().toUpperCase().contains(codebar))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());
       if(li_prod.size()<=0)
       {
           codigobarras.setText(codebar);
           break;
       }
    }
}
public String getcerosizquierda(String codigo) {
        
     String cerosizq="";
    
    
    
     for(int i=4;i>codigo.length();i--)
     {
         cerosizq=cerosizq+"0";
     }
      cerosizq=cerosizq+codigo;
        return cerosizq;
    } 
    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}