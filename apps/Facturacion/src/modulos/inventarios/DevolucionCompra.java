/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulos.inventarios;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import jenum.EstadoRegistroEnum;
import menu.Sample;
import model.Kardex;
import modulos.FindBy.FindProduct;
import persistence.InitialLoadEntityManagerProxy;
import rest.CategoriaJerseyClient;
import rest.KardexJerseyClient;

/**
 *
 * @author karolyani
 */
public class DevolucionCompra extends Sample {
     
    private FindProduct findProduct;
     private Kardex kardex;
    private int m_IEstRec;
    private  Label L_Fecha;
    private  TextField Tf_Fecha;
    private  Label L_Producto;
    private  TextField Tf_Producto;
    private  Label L_NoFactura;
    private  TextField Tf_NoFactura;
    private  Label L_Descripcion;
    private  TextArea Ta_Descripcion;
    private  Label L_CantidadEntrada;
    private  TextField Tf_CantidadEntrada;
    private Button save;
    private Button nuevo;
    private Label success;
    private InitialLoadEntityManagerProxy iEm;
    private KardexJerseyClient kardexJerseyClient;    
    private Pane myPane;
    private GridPane gridpane;
    private GridPane gridpaneRoot;
    private HBox hbox;
    private HBox hboxButton;
    private Boolean m_BCanSave;
    
    public DevolucionCompra() throws IOException {
        m_BCanSave=true;
        
        m_IEstRec=0;
        kardex = new Kardex();
        kardexJerseyClient=new KardexJerseyClient();
        myPane=new Pane();
        gridpane=new GridPane();
        gridpaneRoot=new GridPane();
       
        hbox=new HBox();
        hboxButton=new HBox(10);
        gridpane.getStyleClass().add("category-page");
        //getChildren().add(myPane);
        L_Fecha=new Label("Fecha: ");
        L_Producto=new Label("Producto: ");
        L_Descripcion=new Label("Descripción: ");
        L_NoFactura=new Label("No Factura: ");
        L_CantidadEntrada=new Label("Cantidad devuelta: ");
        
        Tf_Fecha=new TextField();
        Tf_Producto=new TextField();
        Ta_Descripcion=new TextArea();
        Ta_Descripcion.setPrefColumnCount(4);
         Ta_Descripcion.setPrefRowCount(4);
        Tf_NoFactura=new TextField();
        Tf_CantidadEntrada=new TextField();
       
        ImageView imageView=new ImageView("/images/bs_save.gif");
        save =new Button("Guardar",imageView);
        imageView=null;
        imageView=new ImageView("/images/bs_newRecord.gif");
        nuevo =new Button("Nuevo",imageView);
        success=new Label();
        hboxButton.getChildren().addAll(save,nuevo);
       
        //Tf_Producto.setPrefWidth(150);
          Text _tTexthebreo=new Text("  Devolución en compra   ");
       _tTexthebreo.setFill(Color.WHITESMOKE);
       _tTexthebreo.textAlignmentProperty().setValue(TextAlignment.CENTER);
       _tTexthebreo.setFont(Font.font("WP Hebrew David", FontWeight.BOLD,32));
       GridPane.setHalignment(_tTexthebreo, HPos.CENTER);
       gridpane.setHgap(5);
       gridpane.setVgap(5);
       gridpane.getStyleClass().add("background");
       gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
       gridpaneRoot.getStyleClass().add("background");
       GridPane.setValignment(gridpaneRoot, VPos.TOP);
       GridPane.setHalignment(success, HPos.CENTER);
      
      
       gridpane.add(L_Fecha, 0, 3);
       gridpane.add(Tf_Fecha, 1, 3);
       gridpane.add(L_Producto, 0, 4);
       gridpane.add(Tf_Producto, 1, 4,7,1);
       gridpane.add(L_Descripcion, 0, 5);
       gridpane.add(Ta_Descripcion, 1, 5);
       gridpane.add(L_NoFactura, 0, 6);
       gridpane.add(Tf_NoFactura, 1, 6);
       gridpane.add(L_CantidadEntrada, 0, 7);
       gridpane.add(Tf_CantidadEntrada, 1, 7);
     
       gridpane.add(success, 0, 9,4,1);
       gridpane.add(hboxButton, 1, 11,4,1);
       gridpaneRoot.setMaxSize(370, 370);
       gridpane.setMinSize(370, 300);
       findProduct=new FindProduct(false);
     nuevo.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                nuevoEntrada();
            }});
      save.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                saveEntrada();
            }});
      
      
       Tf_Producto.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
                  findProduct.show();
                 findProduct.getTf_Search().requestFocus();
               
            }});
      Tf_NoFactura.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                  
                try{
                    Float.valueOf(Tf_NoFactura.getText());
                    
               
                }  catch(Exception e)
                {
                    keyEvent.consume(); 
                    Tf_NoFactura.backward();
                    Tf_NoFactura.deleteNextChar();
                    
                }
                }
        });
      Tf_CantidadEntrada.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                  
                try{
                    Float.valueOf(Tf_CantidadEntrada.getText());
                    
               
                }  catch(Exception e)
                {
                    keyEvent.consume(); 
                    Tf_CantidadEntrada.backward();
                    Tf_CantidadEntrada.deleteNextChar();
                    
                }
                }
        });
      
      
       findProduct.getStage().setOnHidden(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN ) {
                    if(findProduct.getProducto()!=null)
                    {
                  Tf_Producto.setText(findProduct.getProducto().getNombre());
                  Tf_NoFactura.requestFocus();
                    }
                }
                }
        });
        //agregamos el formulario y la tabla
         gridpaneRoot.add(gridpane, 0, 0);
         Tf_Fecha.setText(Calendar.getInstance().getTime().toString());
         Tf_Fecha.setEditable(false);
         Tf_Producto.requestFocus();
        getChildren().add(gridpaneRoot);
       }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
          javafx.scene.Group g =
                new javafx.scene.Group(imageView);
   
        return g;
    }
    
     public void saveEntrada() {
       boolean _IsGuardado=true;
       
       
     
        kardex.setFecha(Calendar.getInstance(Locale.getDefault()).getTime());
        kardex.setDesc_kar(Ta_Descripcion.getText());
          if(Tf_Producto.getText().equals("") || Tf_CantidadEntrada.getText().equals("") || Tf_NoFactura.getText().equals(""))
        {
            Tf_Producto.setStyle("-fx-background-color:#ff1600");
            Tf_NoFactura.setStyle("-fx-background-color:#ff1600");
            Tf_CantidadEntrada.setStyle("-fx-background-color:#ff1600");
          
            m_BCanSave=false;
        }
          else
          {
              kardex.setProducto(findProduct.getProducto());
              kardex.setNo_fact_kar(Long.valueOf(Tf_NoFactura.getText().trim()));
              kardex.setCantidad_entrada(Float.parseFloat(Tf_CantidadEntrada.getText().trim()));
              kardex.setTipo("dc");
          }
        
         if(m_BCanSave)
         {
             
             save.setDisable(true);
             nuevo.setDisable(false);
          try{
              switch(m_IEstRec){
                  case 0:
                      kardexJerseyClient.create_XML(kardex);
                      break;
                  case 1:
                      kardexJerseyClient.edit_XML(kardex,Kardex.class,kardex.getId().toString());
                      break;
                      
              }
              
              
       
       }catch(Exception e){
           _IsGuardado=false;
           
       }
     success.setOpacity(0);
        if(_IsGuardado)
        {
        success.setText(m_IEstRec==EstadoRegistroEnum.NUEVO.ordinal()?"Registro Guardado":"Registro Modificado");
        }
        else
        {
            success.setText("Error al Almacenar");
            
        }
         }
         else
         {
             m_BCanSave=true;
             success.setText("Los Campos de Texto en Rojo son Obligatorios ");
         }
        animateMessage();
        
  
    }
      public void nuevoEntrada() {
       kardex = new Kardex();
       m_IEstRec=0;
       save.setDisable(false);
       nuevo.setDisable(true);
     
       Tf_Producto.setText("");
       Tf_Fecha.setText(Calendar.getInstance().getTime().toString());
       Tf_NoFactura.setText("");
       Ta_Descripcion.setText("");
       Tf_CantidadEntrada.setText("0");
      
        success.setOpacity(0);
        success.setText("Registro Nuevo");
        animateMessage();
       }
    private void animateMessage() {
         success.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(2000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);
      
        ft.play();
       
        //success.setOpacity(0);
      
       ft.setOnFinished(new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent event) {
                success.setText("");
                   Tf_Producto.setStyle(null);
                   Tf_Producto.getStyleClass().add("text-field");
                   Tf_CantidadEntrada.setStyle(null);
                   Tf_CantidadEntrada.getStyleClass().add("text-field");
                 
                   Tf_NoFactura.setStyle(null);
                   Tf_NoFactura.getStyleClass().add("text-field");
                   
            }
        });
        
           
       
    }
   
    public void validar_entrada_numerica(TextField num,KeyCode caracter)
            
    { if(caracter!=KeyCode.RIGHT && caracter!=KeyCode.LEFT && caracter!=KeyCode.ENTER  )
        try{
         System.out.println(Float.valueOf(num.getText()));
        }
        catch(Exception e)
       {
           // System.out.println(num.getText().substring(num.getText().length()-1));
            try
            {
              num.setText((num.getText()).replaceAll(num.getText().substring(num.getText().length()-1), ""));
            }catch(Exception e2)
            {
                System.out.println(num.getText().substring(num.getText().length()-1));
                num.selectAll();
                num.deselect();
            }
       
       num.end();
        
      }
    }
public boolean validarDni(String dni)
{
boolean cumplePatron = Pattern.matches("[0-9]*.[0-9]*", dni);
System.out.println(dni+cumplePatron);
return cumplePatron;
}

}
