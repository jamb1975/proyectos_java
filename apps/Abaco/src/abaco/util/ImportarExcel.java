
package  abaco.util;

import abaco.AbacoApp;
import abaco.controlador.CategoriaControllerClient;
import abaco.controlador.ProductoControllerClient;
import abaco.controlador.ProveedoresControllerClient;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.CountryCode;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Categoria;
import model.Producto;
import model.Proveedores;
import service.EntityManagerGeneric;

public class ImportarExcel extends Application
{
    StackPane stack;
   private static GridPane gp_progreso;
   public static GridPane Gp_ValidarUsuario;
   
   public static   Button Bu_importaractividades=new Button("Importar Actividades Economicas");
   public static   Button Bu_importarretefuente=new Button("Importar Retefuente");
  
   private static    final ProgressIndicator p5 = new ProgressIndicator();
   private static Timeline timeline = new Timeline();
    private static Thread thimport_77;
    public Parent createContent() throws IOException 
    {
     
     Bu_importarretefuente.setOnAction(e->{
         try {
             importar_excel_retefuente();
         } catch (IOException ex) {
             Logger.getLogger(ImportarExcel.class.getName()).log(Level.SEVERE, null, ex);
         } catch (WriteException ex) {
             Logger.getLogger(ImportarExcel.class.getName()).log(Level.SEVERE, null, ex);
         } catch (BiffException ex) {
             Logger.getLogger(ImportarExcel.class.getName()).log(Level.SEVERE, null, ex);
         }
     });
      stack = new StackPane();
      Gp_ValidarUsuario=new GridPane();
      Gp_ValidarUsuario.add(Bu_importarretefuente,0,0);
      Gp_ValidarUsuario.add(Bu_importaractividades,1,0);
    
      Gp_ValidarUsuario.setVgap(7);
      Gp_ValidarUsuario.getStylesheets().add("/sihic/SofackarStylesCommon.css"); 
      Gp_ValidarUsuario.getStyleClass().add("background");
     
            //  showprogreso(p);
         
      stack.getChildren().addAll(Gp_ValidarUsuario);
       
    return stack;
    }
  
 
   
      @Override
    public void start(Stage primaryStage) throws Exception {
 
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
public void importar_excel_retefuente() throws IOException, WriteException, BiffException
    {
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setEncoding("ISO-8859-1");
        wbSettings.setLocale(new Locale("es", "ES"));
        wbSettings.setExcelDisplayLanguage("ES"); wbSettings.setExcelRegionalSettings("ES"); wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());
        String rutaArchivo = "/home/adminlinux/contabilidad/RELACIONDEPRODUCTOS.xls"; 
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        Workbook libro;
        libro = Workbook.getWorkbook(archivoXLS,wbSettings);
        Sheet sheet = libro.getSheet(1);
         int f=0;
         
             Producto producto;
             Proveedores proveedores;
             Categoria categoria;
             ProductoControllerClient productoControllerClient=new ProductoControllerClient();
             ProveedoresControllerClient proveedoresControllerClient=new ProveedoresControllerClient();
             CategoriaControllerClient categoriaControllerClient=new CategoriaControllerClient();
              HashMap<String,Proveedores>   hasmap=new HashMap<>();
              HashMap<String,Categoria>   hasmap2=new HashMap<>();
         for(int i=6;i<72;i++)
         {
          Cell nombre = sheet.getCell(1, i);
          Cell descripcion = sheet.getCell(2, i);
          Cell proveedor = sheet.getCell(4, i);
          Cell precio = sheet.getCell(5, i);
          Cell categoriae = sheet.getCell(3, i);
          //Cell tarifapormil = sheet.getCell(4, i);
          AbacoApp.s_producto=null;
          AbacoApp.s_producto=new Producto();
          
          
          try{
           
            AbacoApp.s_producto.setNombre(nombre.getContents());
            AbacoApp.s_producto.setDescrip(descripcion.getContents());
            AbacoApp.s_producto.setPrecio(new BigDecimal(precio.getContents()));
         
             AbacoApp.s_proveedores.setNombre(proveedor.getContents());
            AbacoApp.s_categoria.setM_NombreCat(categoriae.getContents());
            
            categoriaControllerClient.save();
             if(hasmap.get(proveedor.getContents())==null)
             {      
              
               proveedoresControllerClient.create();
               hasmap.put(proveedor.getContents(), AbacoApp.s_proveedores);
             }
             else
             {
               AbacoApp.s_proveedores= hasmap.get(proveedor.getContents()); 
             }
             if(hasmap2.get(categoriae.getContents())==null)
             {      
              
               categoriaControllerClient.save();
               hasmap2.put(categoriae.getContents(), AbacoApp.s_categoria);
             }
             else
             {
               AbacoApp.s_categoria= hasmap2.get(categoriae.getContents()); 
             }
             AbacoApp.s_producto.setCategoria(AbacoApp.s_categoria);
             AbacoApp.s_producto.setProveedores(AbacoApp.s_proveedores);
             productoControllerClient.create();
          }catch(Exception e)
          {
          
          }
           
         }
         libro.close();
         
        
    
}
   

}