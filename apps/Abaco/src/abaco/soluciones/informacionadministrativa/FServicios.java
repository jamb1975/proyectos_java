
package  abaco.soluciones.informacionadministrativa;

import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import abaco.PageBrowser;
import abaco.SearchPopover;
import abaco.control.SearchBox;
import abaco.generic.LoadChoiceBoxGeneral;
import com.aquafx_project.AquaFx;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.text.ParseException;
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
import jfxtras.styles.jmetro8.JMetro;
import message.EstadosMensaje;
import model.Producto;
import org.aerofx.AeroFX;





/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FServicios extends Application {
    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
  
   
   
    private GridPane gp_generic;
   
    private TextField  codigo ;
    private TextField  codigobarras ;
    private SearchBox  sb_proveedor=new SearchBox();
    private SearchPopover sp_proveedor;
    private SearchBox  sb_categoria=new SearchBox();
    private SearchPopover sp_categoria;
    private TextField  nombre;
    private TextArea  descripcion;
    private TextField  precio;
    private TextField referencia;
    private TextField modoconsumo;
    private TextField topminimo;
    private TextField topmaximo;
    private TextField iva;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
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
    public Parent createContent() throws IOException {
    sp_proveedor = new SearchPopover(sb_proveedor,new PageBrowser(),AbacoApp.s_proveedores,false);
    sp_proveedor.setMaxWidth(300);
    sp_categoria = new SearchPopover(sb_categoria,new PageBrowser(),AbacoApp.s_categoria,false);
    sp_categoria.setMaxWidth(300);    
        sb_categoria.setMinWidth(300);
         sb_proveedor.setMinWidth(300);
      logo=new ImageView("/images/foto.png");
      logo.setFitHeight(100);
      logo.setFitWidth(100);
      codbarras=new ImageView();
      codbarras.setFitHeight(20);
      codbarras.setFitWidth(100);
      //***********************************************  
        estadosMensaje=new EstadosMensaje();
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
                Logger.getLogger(FProducto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FProducto.class.getName()).log(Level.SEVERE, null, ex);
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
        descripcion=new TextArea("");
        modoconsumo=new TextField("");
        ImageView imageView=new ImageView("/images/save.png");
         imageView.setFitHeight(30);
         imageView.setFitWidth(30);
         
        save =new Button("",imageView);
        save.setTooltip(new Tooltip("Guardar Producto"));
        hb_botones=new HBox(2);
       
        save.setOnAction(e->{try {
            save();
              } catch (InterruptedException ex) {
                 
              }
         });
        imageView=null;
        imageView=new ImageView("/images/new2.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
     
        nuevo =new Button("",imageView);
         nuevo.setTooltip(new Tooltip("Nuevo Producto"));
       
        nuevo.setOnAction(e->{
            nuevo();
              
         });
        imageView=null;
        imageView=new ImageView("/images/img.png");
         imageView.setFitHeight(30);
         imageView.setFitWidth(30);
        
         bu_logo =new Button("",imageView);
      
        bu_logo.setTooltip(new Tooltip("Cargas Imagen de Producto"));
        bu_logo.setOnAction(e->{LoadImageLogo();
        
         });
         imageView=null;
        imageView=new ImageView("/images/pdf.png");
         imageView.setFitHeight(30);
         imageView.setFitWidth(30);
         bu_codebar =new Button("",imageView);
         bu_codebar.setTooltip(new Tooltip("Imprimir Código de Barras Ean13"));
         bu_codebar.setOnAction(e->{
        try {
            printcodebar();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(FProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
         });
         
          imageView=null;
        imageView=new ImageView("/images/codebar.png");
         imageView.setFitHeight(30);
         imageView.setFitWidth(30);
         bu_gencodebar =new Button("",imageView);
         bu_gencodebar.setTooltip(new Tooltip("Generar Código de Barras Ean13"));
         bu_gencodebar.setOnAction(e->{
       
            generarcodigobarras();
       
         });
          hb_botones=new HBox(10);
         hb_botones.getChildren().addAll(save,nuevo,bu_logo,bu_codebar,bu_gencodebar);
        
// gridpane
        gp_generic=new GridPane();
        HBox hbox;
        hbox=new HBox(new Label("Categoria: "),sb_categoria,new Label("Proveedor: "),sb_proveedor);
        GridPane.setColumnSpan(hbox,2);
       
     
       
        gp_generic.add(new Label("Codigo de Barras: "), 0, 0,1,1);
        gp_generic.add(new Label("Nombre:"), 0, 1,1,1);
      
        gp_generic.add(codigobarras, 1, 0,1,1);
        gp_generic.add(nombre, 1, 1,1,1);
        gp_generic.add(logo, 2, 0,2,5);
        gp_generic.add(codbarras, 2, 5,2,3);
      
        gp_generic.add(new Label("Precio:"), 0, 2,1,1);
    
        gp_generic.add(precio, 1, 2,1,1);
      
        gp_generic.add(new Label("Descripción:"), 0, 3,1,1);
        gp_generic.add(descripcion, 1, 3,1,1);
       gp_generic.add(hb_botones, 0, 4,4,1);
      
        GridPane.setHalignment(hb_botones, HPos.CENTER);
       // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
       // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(2);
        gp_generic.setVgap(2);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_LEFT);
       // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
       // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
      gp_generic.autosize();
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message=new GridPane();
        Gp_Message=new GridPane();
        Gp_Message.setMinWidth(gp_generic.getLayoutBounds().getWidth());
        Gp_Message.setMaxHeight(40);
        L_Message=new Label();
        L_Message.getStylesheets().add("/abaco/SofackarStylesCommon.css"); 
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
       GridPane.setValignment(L_Message, VPos.TOP);
       GridPane.setValignment(Gp_Message, VPos.TOP);
       Gp_Message.setVisible(false);
       stack.setAlignment(Pos.TOP_CENTER);
       //stack.setMinWidth(700);
       stack.getChildren().addAll(gp_generic,Gp_Message);
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
          nuevo.setStyle("-fx-background-color:#007fff");
          save.setStyle("-fx-background-color:#007fff");
          bu_codebar.setStyle("-fx-background-color:#007fff");
          bu_gencodebar.setStyle("-fx-background-color:#007fff");
          bu_logo.setStyle("-fx-background-color:#007fff");
                  
        }
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
    AbacoApp.s_producto=null;
    AbacoApp.s_producto=new Producto();
    empty_field();
    iva.setText("0");
    topmaximo.setText("0");
    topminimo.setText("0");
    
}
public void save() throws InterruptedException
{ validar_formulario();
  if(permitirproceso)
  {
  set_datos();
  try{
  
if(AbacoApp.s_producto.getId()==null)
      {
        AbacoApp.productoControllerClient.create();
         L_Message.setText("Registro Almacenado"); 
      }
      else
      {
        AbacoApp.productoControllerClient.update();
         L_Message.setText("Registro modificado"); 
      }

    Task_Message = () -> {
         try {
             
             SetMessage();
             if(AbacoApp.nombresolucion.equals("Servicios"))
             {   
              AdminServicios.getRecords();
              AbacoApp.li_producto=AbacoApp.productoControllerClient.getRecords(null);
             }
           
         } catch (InterruptedException ex) {
             
         } catch (ParseException ex) {
        Logger.getLogger(FProducto.class.getName()).log(Level.SEVERE, null, ex);
    }
     };
     backgroundThread = new Thread(Task_Message);
      // Terminate the running thread if the application exits
       backgroundThread.setDaemon(true);
 
       // Start the thread
      backgroundThread.start();
  }catch(Exception e)
  {
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
      
        AbacoApp.s_producto.setCodigobarras(codigobarras.getText().trim());
        AbacoApp.s_producto.setNombre(nombre.getText());
        AbacoApp.s_producto.setPrecio(new BigDecimal(precio.getText().trim()));
        AbacoApp.s_producto.setDescrip(descripcion.getText());
        AbacoApp.s_producto.setModoconsumo(modoconsumo.getText());
        AbacoApp.s_producto.setReferencia(referencia.getText());
        AbacoApp.s_producto.setTipo("1");
        try
        {
        AbacoApp.s_producto.setTopminimo(Integer.valueOf(topminimo.getText()));
        }catch(Exception e)
        {
           AbacoApp.s_producto.setTopminimo(0);  
        }
        try
        {
        AbacoApp.s_producto.setTopmaximo(Integer.valueOf(topmaximo.getText()));
        }catch(Exception e)
        {
           AbacoApp.s_producto.setTopmaximo(0);  
        }
        try{
           AbacoApp.s_producto.setIva(Integer.valueOf(iva.getText().trim()));
           }catch(Exception e)
        {
           AbacoApp.s_producto.setIva(0);  
        }
        if(LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().getSelectedIndex()>=0)
        {
            AbacoApp.s_producto.setUnidadesmedida(LoadChoiceBoxGeneral.getV_medunidadmedida().get(LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().getSelectedIndex()));
        }
        if(AbacoApp.s_categoria!=null)
        {
           if(AbacoApp.s_categoria.getId()!=null)
           {
             AbacoApp.s_producto.setCategoria(AbacoApp.s_categoria);
           }
           else
           {
               AbacoApp.s_producto.setCategoria(null);
           }
        }
        else
           {
               AbacoApp.s_producto.setCategoria(null);
           }
         if(AbacoApp.s_proveedores!=null)
        {
           if(AbacoApp.s_proveedores.getId()!=null)
           {
             AbacoApp.s_producto.setProveedores(AbacoApp.s_proveedores);
           }
           else
           {
               AbacoApp.s_producto.setProveedores(null);
           }
        }
        else
           {
               AbacoApp.s_producto.setProveedores(null);
           }
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
        AbacoApp.s_producto.setImg(data);
        }catch(Exception e)
        {
           
        }
}
public void getDatos()
{
    
       codigobarras.setText(AbacoApp.s_producto.getCodigobarras());  
       nombre.setText(AbacoApp.s_producto.getNombre());
       referencia.setText(AbacoApp.s_producto.getReferencia());
       descripcion.setText(AbacoApp.s_producto.getDescrip());
       precio.setText(AbacoApp.s_producto.getPrecio().toString());
       modoconsumo.setText(AbacoApp.s_producto.getModoconsumo());
       topminimo.setText(String.valueOf(AbacoApp.s_producto.getTopminimo()));
       topmaximo.setText(String.valueOf(AbacoApp.s_producto.getTopmaximo()));
       iva.setText(String.valueOf(AbacoApp.s_producto.getIva()));
        if(AbacoApp.s_producto.getUnidadesmedida()!=null)
        {
            LoadChoiceBoxGeneral.getCb_unidadesmedida().getSelectionModel().select(LoadChoiceBoxGeneral.getV_medunidadmedida().indexOf(AbacoApp.s_producto.getUnidadesmedida()));
        }
       if(AbacoApp.s_producto.getCategoria()!=null)
        {
            AbacoApp.s_categoria=AbacoApp.s_producto.getCategoria();
         sb_categoria.setText(AbacoApp.s_producto.getCategoria().getM_NombreCat());
         sp_categoria.hide();
        }
       if(AbacoApp.s_producto.getProveedores()!=null)
        {
            AbacoApp.s_proveedores=  AbacoApp.s_producto.getProveedores(); 
         sb_proveedor.setText(AbacoApp.s_producto.getProveedores().getNombre());
         sp_proveedor.hide();
        }
        
       try{
       InputStream in = new ByteArrayInputStream(AbacoApp.s_producto.getImg());
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
   
    if(AbacoApp.s_producto!=null)
    {
     if(AbacoApp.s_producto.getId()!=null)
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
        AbacoApp.s_producto=new Producto();
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
       List<Producto> li_prod= AbacoApp.li_producto.stream().filter(line ->line.getCodigobarras().toUpperCase().contains(codebar))	//filters the line, equals to "mkyong"
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