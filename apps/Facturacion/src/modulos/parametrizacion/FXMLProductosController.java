/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modulos.parametrizacion;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import menu.MenuMain;
import model.Categoria;
import model.Producto;
import rest.ProductoJerseyClient;

/**
 * FXML Controller class
 *
 * @author karol
 */
public class FXMLProductosController implements Initializable {
  private ProductoJerseyClient m_sProdJerseyClient;
  private Vector<Long>m_vlIdCat=new Vector<Long>();
  FileChooser fileChooser = new FileChooser();
  Stage stage;
  @FXML TextField nombre;
  @FXML TextField codigo;
  @FXML TextField costo;
  @FXML TextField precio;
  @FXML TextArea descrip;
  @FXML Button bLoadImage;
  @FXML ImageView img;
  @FXML Button save;
  @FXML Button nuevo;
  @FXML ChoiceBox m_cbCategoria;
  @FXML Label success;
  private final Desktop desktop = Desktop.getDesktop();
  Image Loadimg;
  File file;
  WritableImage wi;
  BufferedImage bi;
  ObservableList oList;
  Producto productos;
    /**
     * Initializes the controller class.
     */
   
    public void initialize(URL url, ResourceBundle rb) {
         m_sProdJerseyClient=new ProductoJerseyClient();
         fileChooser.setTitle("Abrir Recursos de Imagen");
         fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
         
            m_cbCategoria.getItems().clear();
            m_cbCategoria.getStyleClass().add("choice-box");
            int d=MenuMain.getM_oDatosCategoria().size();
                int i=0;
                if(MenuMain.getM_oDatosCategoria().size()>0)
                {
                   for(Categoria categoria:(Categoria[])MenuMain.getM_oDatosCategoria().get(0))
                   {   
                       m_vlIdCat.add(i,categoria.getId());
                       m_cbCategoria.getItems().add(i,categoria.getM_NombreCat());
                      
       
                   }
                }
          
          save.setCursor(Cursor.HAND);
          nuevo.setCursor(Cursor.HAND);
   
    }  
    public void LoadImage(ActionEvent event) {
        
       
        file=fileChooser.showOpenDialog(stage);
         
        if(file!=null)
        {
            byte[]img2;
            try {
                   bi = ImageIO.read(file);
                  
                  wi=SwingFXUtils.toFXImage(bi, wi);
                  Loadimg=wi;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
        }
    
        img.setImage(Loadimg);
    }
     
   private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                FXMLProductosController.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }  
   
    
    public void saveProducto(ActionEvent event) throws FileNotFoundException, IOException {
       
        productos = new Producto();
        productos.setNombre(nombre.getText());
        productos.setCodigo(codigo.getText());
        productos.setDescrip(descrip.getText());
        int index=m_cbCategoria.getSelectionModel().getSelectedIndex();
        productos.setCategoria(new Categoria(m_vlIdCat.get(index)));
        productos.setTipo(String.valueOf(index));
        productos.setCosto(BigDecimal.valueOf(Double.parseDouble(costo.getText())));
        productos.setPrecio(BigDecimal.valueOf(Double.parseDouble(precio.getText())));
        
        
        //guardando imagen png o jpg en bases de datos
        byte[]data;
        try{
         InputStream in = new FileInputStream(file);
		 data=new byte[in.available()];
	     in.read(data);
             productos.setImg(data);
             m_sProdJerseyClient.create_XML(productos);
             success.setOpacity(0);;
              success.setText("Registro Guardado");
        }catch(Exception e)
        {
            success.setText("No Ha Cargado la Imagen del Producto");
        }
        animateMessage();
    }
      public void nuevoProducto(ActionEvent event) {
       
        
       nombre.setText("");
       codigo.setText("");
       descrip.setText("");
       precio.setText("");
       costo.setText("");
     
       img.setImage(null);
       success.setOpacity(0);;
       success.setText("Registro Nuevo");
       animateMessage();
    }
    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();
        success.setOpacity(0);
        //success.setText("");
    }
}
