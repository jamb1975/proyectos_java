/*
 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paint;
import java.math.MathContext;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.PolygonBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import menu.MenuMain;
import model.Factura;
import model.Terceros;
import modulos.facturas.FormFacturarMesas;

/**
 *
 * @author karol
 */
public class Mesa extends Group {
    public  Rotate rx = new Rotate(0,Rotate.X_AXIS);
    public Rotate ry = new Rotate(0,Rotate.Y_AXIS);
    public  Rotate rz = new Rotate(0,Rotate.Z_AXIS);
    private int m_iNoMesa;
    private Label m_lNoMesa;
    private ImageView m_ivSearch=new ImageView("/images/search2.jpg");
    private Color m_cColor;
    private double m_dShade;
    private double m_dSize;
    private boolean m_bSelecMesa;
    private boolean m_bFreeMesa;
    private Factura factura;
    private Long m_LIdTempMesa;
    public Label getM_lNoMesa() {
        return m_lNoMesa;
    }

    public void setM_lNoMesa(Label m_lNoMesa) {
        this.m_lNoMesa = m_lNoMesa;
    }
  
     public  Mesa()
     {
     }

    public int getM_iNoMesa() {
        return m_iNoMesa;
    }

    public void setM_iNoMesa(int m_iNoMesa) {
        this.m_iNoMesa = m_iNoMesa;
    }

    public Color getM_cColor() {
        return m_cColor;
    }

    public void setM_cColor(Color m_cColor) {
        this.m_cColor = m_cColor;
    }

    public double getM_dShade() {
        return m_dShade;
    }

    public void setM_dShade(double m_dShade) {
        this.m_dShade = m_dShade;
    }

    public double getM_dSize() {
        return m_dSize;
    }

    public void setM_dSize(double m_dSize) {
        this.m_dSize = m_dSize;
    }

    public boolean isM_bSelecMesa() {
        return m_bSelecMesa;
    }

    public void setM_bSelecMesa(boolean m_bSelecMesa) {
        this.m_bSelecMesa = m_bSelecMesa;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public boolean isM_bFreeMesa() {
        return m_bFreeMesa;
    }

    public void setM_bFreeMesa(boolean m_bFreeMesa) {
        this.m_bFreeMesa = m_bFreeMesa;
    }

    
     
    public  Mesa(double size, Color color, double shade,int _iNoMesa, boolean _bSelectMesa) {
      
       m_dSize=size;
       m_cColor=color;
       m_dShade=shade;
       m_iNoMesa=_iNoMesa;
       m_bSelecMesa=_bSelectMesa;
       m_lNoMesa=new Label("No Mesa:"+String.valueOf(m_iNoMesa));
       m_bSelecMesa=false; 
       m_bFreeMesa=true;
       factura=new Factura();
       
       factura.setCustomer(new Terceros());
       
       m_ivSearch.setCursor(Cursor.OPEN_HAND);
       m_ivSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                           FormFacturarMesas.DetallesFactura(getLayoutX()+getScene().getX()+getScene().getWindow().getX(),getLayoutY()+getScene().getY()+getScene().getWindow().getY());
                }
            });
        
       
       setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    
                    //Capturamos el indice del vector que contiene los objetos mesa que es de la clase Group
                    //Como ya estan en memoria afecta directamente el cambio donde este el objeto
                    //Pero toca hacer una limpieza de los hijos del objeto 
                    if(FormFacturarMesas.m_TempMesaFactura.getM_iNoMesa()>0)
                    {
                      MenuMain.m_vMesa.get(FormFacturarMesas.m_TempMesaFactura.getM_iNoMesa()-1).setM_bSelecMesa(false);
                      MenuMain.m_vMesa.get(FormFacturarMesas.m_TempMesaFactura.getM_iNoMesa()-1).setM_lNoMesa(new Label("No Mesa:"+String.valueOf(m_iNoMesa)));
                      MenuMain.m_vMesa.get(FormFacturarMesas.m_TempMesaFactura.getM_iNoMesa()-1).getChildren().clear();
                      MenuMain.m_vMesa.get(FormFacturarMesas.m_TempMesaFactura.getM_iNoMesa()-1).createmesa();
                    }
                    m_bSelecMesa=true;
                  
                    getChildren().clear();
                    createmesa();
                    FormFacturarMesas.m_TempMesaFactura.setM_iNoMesa(m_iNoMesa);
                    System.setProperty("pNoMesa", String.valueOf(m_iNoMesa));
                    FormFacturarMesas.m_lNoMesa.setText("No Mesa:  "+String.valueOf(m_iNoMesa));
                    FormFacturarMesas.createFactura();
                    //m_lNoMesa=new Label("No Mesa:"+String.valueOf(m_iNoMesa));
                    //llamamos ws(web service) client con el paquete jersey conexion ws servidor
                    
                }
            });
        
    }
       
           // gcreamos la mesa
    public void createmesa()
    {
    
     Circle circleSmall = createCircle("Bluecircle", Color.DODGERBLUE, 15);
     Text _tNoMesa=new Text(); 
     Text _tEmpty=new Text();
     Text _tValorFactura=new Text();
     _tValorFactura.setText("$"+factura.getTotalAmount().round(MathContext.UNLIMITED).longValue());
     _tValorFactura.setLayoutX(0);
     _tValorFactura.setLayoutY(53);
     _tValorFactura.setFill(factura.getTotalAmount().longValue()>0?Color.RED:Color.DARKBLUE);
     _tValorFactura.textAlignmentProperty().setValue(TextAlignment.CENTER);
     _tValorFactura.setFont(Font.font("ARIAL", FontWeight.BOLD,14));
       
     _tEmpty.setText("");
      Text _tEmpty2=new Text();
     _tEmpty2.setText("");
     Circle circleSelect =new Circle(44,33,40);
     circleSelect.setStroke(Color.GOLD);
     circleSelect.setStrokeWidth(10);
     circleSelect.setFill(null); 
      _tNoMesa.setText(String.valueOf(m_iNoMesa));
      _tNoMesa.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD,14));
       double c=_tNoMesa.getScaleX();
      _tNoMesa.setLayoutX(45-(_tNoMesa.getScaleX()*5));
      _tNoMesa.setLayoutY(15+_tNoMesa.getScaleY()*2);
      
      Color color2=Color.LIGHTGRAY;
      circleSmall.setCenterX(45);
      circleSmall.setCenterY(15);
      m_ivSearch.setLayoutX(30);
      m_ivSearch.setLayoutY(80);
      m_ivSearch.setFitHeight(20);
      m_ivSearch.setFitWidth(20);
     
      getChildren().addAll(
      PolygonBuilder.create() // back face
      .points(20.0,10.0,100.0,10.0,80.0,40.0,0.0,40.0)
            .fill(m_cColor.deriveColor(0.0, 1.0, (1 - 0.0*m_dShade), 1.0))
            .build(),
             PolygonBuilder.create() // back face
            .points(0.0,40.0,0.0,60.0,80.0,60.0,80.0,40.0)
            .fill(m_cColor.deriveColor(0.0, 1.0, (1 - 0.1*m_dShade), 1.0))
            .build(),
              PolygonBuilder.create() // back face
            .points(80.0,60.0,100.0,30.0,100.0,10.0,80.0,40.0)
            .fill(m_cColor.deriveColor(0.0, 1.0, (1 - 0.1*m_dShade), 1.0))
            .build(),
             PolygonBuilder.create() // back face
            .points(0.0,60.0,0.0,90.0,15.0,90.0,15.0,60.0)
            .fill(color2.deriveColor(0.0, 1.0, (1 - 0.1*m_dShade), 1.0))
            .build(),
               PolygonBuilder.create() // back face
            .points(65.0,60.0,65.0,90.0,80.0,90.0,80.0,60.0)
            .fill(color2.deriveColor(0.0, 1.0, (1 - 0.1*m_dShade), 1.0))
            .build(),
             PolygonBuilder.create() // back face
            .points(100.0,30.0,100.0,60.0,85.0,60.0,85.0,50.0,100.0,30.0)
            .fill(color2.deriveColor(0.0, 1.0, (1 - 0.1*m_dShade), 1.0))
            .build(),circleSmall,_tNoMesa,m_bSelecMesa?circleSelect:_tEmpty,m_bSelecMesa?m_ivSearch:_tEmpty2,_tValorFactura
            );
      
     
             }
    public void createmesa2()
    {
    
     
      Color color2=Color.BLACK;
      getChildren().addAll(
     PolygonBuilder.create() // back face
            .points(100.0,30.0,100.0,60.0,85.0,60.0,85.0,50.0,100.0,30.0)
            .fill(color2.deriveColor(0.0, 1.0, (1 - 0.5*m_dShade), 1.0))
            .build()
            );
      
     
             }
       
  private Circle createCircle(final String name, final Color color, int radius) {
        //create a circle with desired name,  color and radius
        final Circle circle = new Circle(radius, new RadialGradient(0, 0, 0.2, 0.3, 1, true, CycleMethod.NO_CYCLE, new Stop[] {
            new Stop(0, Color.rgb(250,250,255)),
            new Stop(1, color)
        }));
        //add a shadow effect
        circle.setEffect(new InnerShadow(7, color.darker().darker()));
        //change a cursor when it is over circle
        circle.setCursor(Cursor.HAND);
        return circle;
  }

    public Long getM_LIdTempMesa() {
        return m_LIdTempMesa;
    }

    public void setM_LIdTempMesa(Long m_LIdTempMesa) {
        this.m_LIdTempMesa = m_LIdTempMesa;
    }
  
}
