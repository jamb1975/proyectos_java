/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controls;

import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.imageio.ImageIO;
import menu.MenuMain;
import model.Producto;
import modulos.facturas.FormFacturarMesas;

/**
 *
 * @author karol
 */
public class ButtonInsertProduct {
    
    private Button m_bInsertProduct;
    private Producto producto;
public ButtonInsertProduct(Producto _producto) throws IOException 
  {
                producto=_producto;               
                m_bInsertProduct=new Button();    
                     //evento al hacer click agregamos el producto
                   m_bInsertProduct.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>(){
                   @Override public void handle(MouseEvent e) {
                       
                       FormFacturarMesas.AgregarProducto(producto,0);
        
                    }  
                   
                    });
                    AssignImgToButton();
                     
}

    public Button getM_bInsertProduct() {
        return m_bInsertProduct;
    }

    public void setM_bInsertProduct(Button m_bInsertProduct) {
        this.m_bInsertProduct = m_bInsertProduct;
    }

  
   
    public void AssignImgToButton() throws IOException
    {     try
    {
          InputStream in = new ByteArrayInputStream(producto.getImg());
          BufferedImage bi = ImageIO.read(in);
          WritableImage wi=new WritableImage(1,1);
          wi=SwingFXUtils.toFXImage(bi, wi);
          ImageView iv=new ImageView();
          Image image=wi;
          iv.setImage(image);
          iv.setFitHeight(48);
          iv.setFitWidth(48);
          iv.setLayoutX(0);
          iv.setLayoutY(0);
           m_bInsertProduct.setGraphic(iv);
         
          
    }catch(Exception e)
    {
        
        
    }
     m_bInsertProduct.setAlignment(Pos.BASELINE_LEFT);
          
          m_bInsertProduct.setCursor(Cursor.HAND);
          m_bInsertProduct.setFont(Font.font("", FontWeight.BOLD,12));
          m_bInsertProduct.setText(producto.getNombre()+"\n$"+String.valueOf(producto.getPrecio()));
          m_bInsertProduct.setPrefWidth(170);
    }  

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
}
