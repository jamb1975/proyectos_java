        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paint;

import controls.ButtonInsertProduct;
import java.util.List;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import menu.MenuMain;
import modulos.facturas.FormFacturarMesas;

/**
 *
 * @author karol
 */
public class IconosCategoria extends Group {
    private String m_sName;
    private Color  m_Color;
    private int    m_iRadius;
    private int    m_iNumCat;
    private Long   m_lIdCat;
    private String m_sNameCat;
     private Tooltip m_ttNameCat = new Tooltip();
    private Timeline m_tlToolTip = null;
    private  Circle circleSmall;
    public IconosCategoria(final String name, final Color color, int radius,int _NumCat,Long _lIdCat,String _sNameCat)
    {
       m_sName=name;
       m_Color=color;
       m_iRadius=radius;
       m_iNumCat=_NumCat;
       m_lIdCat=_lIdCat;
       m_sNameCat=_sNameCat;
       
       
        Text _tTextNameCat=new Text(name);
       _tTextNameCat.setFill(Color.BLACK);
       _tTextNameCat.textAlignmentProperty().setValue(TextAlignment.CENTER);
       _tTextNameCat.setFont(Font.font("ARIAL", FontWeight.BOLD,14));
       m_ttNameCat.setGraphic(_tTextNameCat);
       m_ttNameCat.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);setOpacity(1);
      _tTextNameCat.setFill(Color.WHITE);
       setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    if (m_tlToolTip != null) m_tlToolTip.stop();
                     Point2D toolTipPos = circleSmall.localToScene(0, circleSmall.getLayoutBounds().getHeight());
                     double x = toolTipPos.getX() + circleSmall.getScene().getX() + circleSmall.getScene().getWindow().getX();
                     double y = toolTipPos.getY() + circleSmall.getScene().getY() + circleSmall.getScene().getWindow().getY();
                     m_Color=Color.GOLD;
                      getChildren().clear();
                     createIcono();
                     m_ttNameCat.show( circleSmall.getScene().getWindow(),x, y-20);   
                }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                              m_ttNameCat.hide();
                              m_Color=Color.SKYBLUE;
                              
                             getChildren().clear();
                     createIcono();
                        }
                  }
        );
        
        //llamamos a la funcion estatica para filtra la tabla por categorias
        setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                              //Creamos Obsevable list
                               ObservableList _oTempDatosProduct=FXCollections.observableArrayList();
                               if(MenuMain.getM_oDatosProducto().size()>0)
                               {
                                  ButtonInsertProduct bp[]= (ButtonInsertProduct[])MenuMain.getM_oDatosProducto().get(0);
                                   for(ButtonInsertProduct _ButtonProducto:(ButtonInsertProduct[])MenuMain.getM_oDatosProducto().get(0))
                                   {
                                     if(_ButtonProducto.getProducto().getCategoria().getId().equals(m_lIdCat))  
                                      _oTempDatosProduct.add(_ButtonProducto);
                                   }
                               }
                               FormFacturarMesas.filtrarXCategoria(_oTempDatosProduct);
                        }
                  }
        );
       createIcono();
        setCursor(Cursor.HAND);
    }
    public void createIcono()
    {
        
      circleSmall = createCircle();
     Text _tNumCat=new Text(String.valueOf(m_iNumCat));
     _tNumCat.setFill(Color.DARKBLUE);
     _tNumCat.textAlignmentProperty().setValue(TextAlignment.CENTER);
     _tNumCat.setFont(Font.font("ARIAL", FontWeight.BOLD,14));
     _tNumCat.setLayoutX(circleSmall.getRadius()*(-0.25));
     _tNumCat.setLayoutY(circleSmall.getRadius()*0.3);
        
      getChildren().addAll(
      circleSmall,_tNumCat
            );
      
     
             }
    
        private Circle createCircle() {
        //create a circle with desired name,  color and radius
        Circle circle = new Circle(m_iRadius, new RadialGradient(0, 0, 0.2, 0.3, 1, true, CycleMethod.NO_CYCLE, new Stop[] {
            new Stop(0, Color.rgb(250,250,255)),
            new Stop(1, m_Color)
        }));
        //add a shadow effect
        circle.setEffect(new InnerShadow(7, m_Color.darker().darker()));
        //change a cursor when it is over circle
        return circle;
  }
};
