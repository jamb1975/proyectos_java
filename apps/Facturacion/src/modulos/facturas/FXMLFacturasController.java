/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modulos.facturas;

import controls.ButtonInsertProduct;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;
import menu.MenuMain;
import model.Factura;
import model.FacturaItem;
import model.TempMesasFactura;
import model.Terceros;
import rest.CheckMesaJerseyClient;
import rest.FacturaJerseyClient;
import rest.TercerosJerseyClient;

/**
 * FXML Controller class
 *
 * @author karol
 */
public class FXMLFacturasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TextField m_tfNoFact;
    @FXML TextField m_tfFecha;
    @FXML CheckBox  m_cbCredito;
    @FXML TextField m_tfNoIdent;
    @FXML TextField m_tfNombre;
    @FXML TextArea m_tfDir1;
    @FXML TextField m_tfCelular;
    @FXML TextField codigo;
    @FXML TextField cantidad;
    @FXML TextField producto;
    @FXML Button m_BAddProd;
    @FXML Button B_guardar;
    @FXML Button B_nuevo;
    @FXML TextField m_tfId;
    @FXML TableView facturaitems;
    @FXML Label success;
    private int m_IEstRec;
    private Boolean m_BCanSave;
    private Factura factura;
    private DropShadow shadow ;
    private  ObservableList  m_oDatosFacturaItems = FXCollections.observableArrayList();
    private FacturaJerseyClient facturaJerseyClient;
    private CheckMesaJerseyClient checkNoMesaResourceJerseyClient=new CheckMesaJerseyClient();
    private TercerosJerseyClient m_sJerseyClient;
    private Terceros t;
    @FXML private TextField _tfValorIva;
    @FXML private  TextField _tfTotal;
    @FXML private  TextField _tfSubtotal; 
       
    public void initialize(URL url, ResourceBundle rb) {
         factura=new Factura();
         shadow = new DropShadow();
         shadow.setColor(Color.WHITE);
        Image imageOk = new Image(getClass().getResourceAsStream("/images/iconos/bs_new.gif"));
        final ImageView iv=new ImageView(imageOk);
        iv.setFitHeight(30);
        iv.setFitWidth(30);
        m_BAddProd.setGraphic(iv);
        codigo.setPromptText("Codigo ");
        codigo.setDisable(true);
        producto.setPromptText("Nombre Producto");
        producto.setDisable(true);
        cantidad.setPromptText("Cantidad");
        cantidad.setText("1");
        cantidad.setDisable(true);
        success.setText("");
        ImageView imageView=new ImageView("/images/bs_save.gif");
        B_guardar.setGraphic(imageView);
        B_guardar.setText("Guardar");
        B_guardar.setMaxWidth(100);
        imageView=null;
        imageView=new ImageView("/images/bs_newRecord.gif");
        B_nuevo.setGraphic(imageView);
        B_nuevo.setText("Nuevo");
        B_nuevo.setMaxWidth(100);
        m_BAddProd.addEventHandler(MouseEvent.MOUSE_ENTERED,new EventHandler<MouseEvent>(){
        @Override public void handle(MouseEvent e) {
            
         m_BAddProd.setEffect(shadow);
         m_BAddProd.setTooltip(new Tooltip("Agregar Nuevo Producto a Factura"));
        }  
        });
         m_BAddProd.addEventHandler(MouseEvent.MOUSE_EXITED,new EventHandler<MouseEvent>(){
        @Override public void handle(MouseEvent e) {
          m_BAddProd.setEffect(null);
        }  
        });
         m_BAddProd.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>(){
        @Override public void handle(MouseEvent e) {
            add_product();
         }  
        });
         codigo.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                 add_product();
                }}
        });
    B_nuevo.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                nuevaFactura();
            }});
    B_guardar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
                   guardarFactura();
               
            }});
       
           m_tfNoIdent.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                m_sJerseyClient=null;
                m_sJerseyClient=new TercerosJerseyClient();
                t=m_sJerseyClient.find_XML(Terceros.class,m_tfNoIdent.getText());
                if(t.getId()==null)
                {
                    t=null;
                }
               else
                {
                m_tfNombre.setText(t.getNombre());
                m_tfCelular.setText(t.getCelular());
                m_tfDir1.setText(t.getDir1());
                m_sJerseyClient.close();
                }
            }
        });
    }    
    public void findTercero()
    {
          NewJerseyClient nj=new NewJerseyClient();
                                               
                        Terceros t =nj.getXml(Terceros.class,m_tfNoIdent.getText());
                        nj.close();
                        m_tfNombre.setText(t.getNombre());
                        m_tfDir1.setText(t.getDir1());
                        
           
    }
    public void add_product(){
        
       int  _ICant=0;
                    if(MenuMain.getM_oDatosCategoria().size()>0)
                {
                 ButtonInsertProduct bp[]= (ButtonInsertProduct[])MenuMain.getM_oDatosProducto().get(0);
                 for(ButtonInsertProduct _ButtonProducto:(ButtonInsertProduct[])MenuMain.getM_oDatosProducto().get(0))
                 {   
                    if(_ButtonProducto.getProducto().getCodigo().equals(codigo.getText()))  
                    {
                      
                       try{
                      _ICant=Integer.parseInt( cantidad.getText());
                               }catch(Exception e)
                               {
                                  e.printStackTrace();
                                 _ICant=1; 
                                 System.out.println("Cant Prodcuto:"+_ICant);
                               }
       FacturaItem _factuFacturaItem=new FacturaItem();
       Factura _factura=new Factura();
         _factura.setId(factura.getId());
         _factuFacturaItem.setProduct(_ButtonProducto.getProducto());
         _factuFacturaItem.setQuantity(_ICant);
        _factuFacturaItem.setFactura(_factura);
        _factuFacturaItem.setValor_total((BigDecimal.valueOf(_factuFacturaItem.getQuantity())).multiply(_factuFacturaItem.getProduct().getPrecio()));
        //_factuFacturaItem.setPrecio_compra(pv);
        _factuFacturaItem.setValor_total2((BigDecimal.valueOf(_factuFacturaItem.getQuantity())).multiply(_factuFacturaItem.getPrecio_compra()));
    
        //Averiguamos si ya se creo la factura para el no de mesa
        //si no se creo entonces insertamos una nueva factura y la asociamos al no de mesa
      
          try
          {
          checkNoMesaResourceJerseyClient=null;
          checkNoMesaResourceJerseyClient=new CheckMesaJerseyClient();
          checkNoMesaResourceJerseyClient.create_XML(_factuFacturaItem);
          producto.setText(_ButtonProducto.getProducto().getNombre());
          factura.addProduct(_ButtonProducto.getProducto(),_ICant , BigDecimal.ONE, null); 
          factura.calculateTotals();
          _tfValorIva.setText(factura.getIva().toString());
          _tfSubtotal.setText(factura.getNetAmount().toString());
          _tfTotal.setText(factura.getTotalAmount().toString());
          
          }catch(Exception e)
          {
              success.setText("Error al agregar el producto");
          }
          checkNoMesaResourceJerseyClient.close();
        Object[] _listFacturaItem=(factura.getFacturaItems()).toArray();
        m_oDatosFacturaItems.clear();
         m_oDatosFacturaItems.addAll(_listFacturaItem);
         facturaitems.setItems(m_oDatosFacturaItems);
         facturaitems.setPrefWidth(500);
    
         
        //"First Name" column
   TableColumn firstNameCol = new TableColumn();
        
        firstNameCol.setText("Producto");
        firstNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> oferta) {
                return new SimpleStringProperty(oferta.getValue().getProduct().getNombre());
            }
        });

        firstNameCol.setPrefWidth(323);
  
         
         
       TableColumn secondNameCol = new TableColumn();
        
        secondNameCol.setText("Valor/U");
        secondNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> oferta) {
                return new SimpleStringProperty(oferta.getValue().getProduct().getPrecio().toString());
            }
        });

        secondNameCol.setPrefWidth(100);
  
   
        TableColumn thirdNameCol = new TableColumn();
        Button _b=new Button("");
        _b.setId("_b");
        thirdNameCol.setText("Cantidad");
        thirdNameCol.setCellValueFactory(new PropertyValueFactory("quantity"));
        thirdNameCol.setPrefWidth(facturaitems.getPrefWidth()*0.21);
 
        TableColumn cuartaNameCol = new TableColumn();
        
        cuartaNameCol.setText("Valor Total");
        cuartaNameCol.setCellValueFactory(new PropertyValueFactory("valor_total"));
        cuartaNameCol.setPrefWidth(100);
        //Se agrega la celda modificada con el botón a la tabla
	TableColumn buttonCol = new TableColumn<>("");
        buttonCol.setSortable(false);
        buttonCol.setCellValueFactory(
        new Callback<TableColumn.CellDataFeatures<FacturaItem, Boolean>, 
        ObservableValue<Boolean>>() {
 
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<FacturaItem, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        buttonCol.setCellFactory(
                new Callback<TableColumn<FacturaItem, Boolean>, TableCell<FacturaItem, Boolean>>() {
 
            @Override
            public TableCell<FacturaItem, Boolean> call(TableColumn<FacturaItem, Boolean> p) {
                return new ButtonCell(facturaitems);
            }
         
        });
          buttonCol.setPrefWidth(42); 
          
         facturaitems.getColumns().clear();
         facturaitems.getColumns().addAll(firstNameCol,thirdNameCol,secondNameCol,cuartaNameCol,buttonCol);
         facturaitems.setEditable(true);
         facturaitems.setPrefHeight(211);
         facturaitems.setPrefWidth(692);
         //facturaitems.setLayoutX(0);
         //facturaitems.setLayoutY(10);
                    }
                 }
                }
    }

       
 //Class button
   private   class ButtonCell extends TableCell<FacturaItem, Boolean> {
        
		//boton a mostrar
		final Button cellButton = new Button();
         
        ButtonCell(final TableView tblView){
            cellButton.setPrefWidth(20);
            cellButton.setPrefHeight(15);
                 Image imageOk = new Image(getClass().getResourceAsStream("/images/bs_delete.gif"));
       ImageView iv=new ImageView(imageOk);
        iv.setFitHeight(15);
        iv.setFitWidth(15);
            cellButton.setGraphic(iv);
            cellButton.setCursor(Cursor.HAND);
            cellButton.setTooltip(new Tooltip("Eliminar Item"));
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
          
                @Override
                public void handle(ActionEvent t) {
                    int selectdIndex = getTableRow().getIndex();
                    //borramos el objeto obtenido de la fila
                   // FacturaItemJerseyClient _facFacturaItemJerseyClient=new FacturaItemJerseyClient();
                    factura.removeProduct(((FacturaItem)m_oDatosFacturaItems.get(selectdIndex)).getProduct());
                    factura.calculateTotals();
                    m_oDatosFacturaItems.remove(selectdIndex);
                   
 
             
                    //_gpDatosFactura.getChildren().add(11, _tfValorIva);
                }
            });
        }
 
        //Muestra un boton si la fila no es nula
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }
      
  public void nuevaFactura() {
     boolean _IsGuardado=true;
    factura = new Factura();
    m_oDatosFacturaItems.clear();
    TempMesasFactura tempMesasFactura=new TempMesasFactura();
    m_IEstRec=0;
    B_guardar.setDisable(false);
    B_nuevo.setDisable(true);
    m_tfNoFact.setText("");
    m_tfFecha.setText("");
    m_cbCredito.setText("");
    m_tfNoIdent.setText("");
    m_tfNombre.setText("");
    m_tfDir1.setText("");
    m_tfCelular.setText("");
    m_cbCredito.setSelected(false);
    codigo.setText("");
    cantidad.setText("1");
    producto.setText("");
    codigo.setDisable(false);
    cantidad.setDisable(false);
    producto.setDisable(false);
    _tfValorIva.setText("");
    _tfSubtotal.setText("");
    _tfTotal.setText(""); 
             B_guardar.setDisable(false);
             B_nuevo.setDisable(true);
          try{
           checkNoMesaResourceJerseyClient=null;
           checkNoMesaResourceJerseyClient=new CheckMesaJerseyClient();
           tempMesasFactura=checkNoMesaResourceJerseyClient.getXml(TempMesasFactura.class, "0");
           factura=tempMesasFactura.getFactura();
           m_tfNoFact.setText(factura.getId().toString());
           m_tfFecha.setText(factura.getFacturaDate().toString());
              
              
       
       }catch(Exception e){
           _IsGuardado=false;
           
       }
    if(_IsGuardado)
        {
        success.setText("Factura Creada");
        }
        else
        {
            success.setText("Error al Crear la Factura");
            
        }
    checkNoMesaResourceJerseyClient.close();
        animateMessage();
       }
  private void guardarFactura(){
     B_guardar.setDisable(true);
             B_nuevo.setDisable(false);  
    if(m_tfNoIdent.getText() != null)
              {
                if(t!=null)
                {
                   t.setCelular(m_tfCelular.getText());
                   t.setDir1(m_tfDir1.getText());
                   factura.setCustomer(t);
                }
                else
                {    factura.setCustomer(new Terceros());
                     factura.getCustomer().setNo_ident(m_tfNoIdent.getText());
                     factura.getCustomer().setNombre(m_tfNombre.getText());
                     factura.getCustomer().setCelular(m_tfCelular.getText());
                      factura.getCustomer().setDir1(m_tfDir1.getText()); 
                }
               }
               else
               {
                   factura.setCustomer(null);
               }
                Factura f=new Factura();
                f.setCustomer(factura.getCustomer());
                f.setCredito(m_cbCredito.isSelected());
                f.setId(factura.getId());
                try
                {     
                facturaJerseyClient=null;
                facturaJerseyClient=new FacturaJerseyClient();
                facturaJerseyClient.edit_XML(f,factura.getId().toString());
                 success.setText("Registro Guardado");
                }catch(Exception e)
                {
                     success.setText("Error al Guardar");
                }
              facturaJerseyClient.close();
       
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
               
            }
        });
        
           
       
    }       
}
