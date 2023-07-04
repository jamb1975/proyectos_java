/*
 * Copyright (c) 2014, JAVIER 
 
 */
package modulos.facturas;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controls.ButtonInsertProduct;
import controls.SearchBox;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javafx.application.HostServices;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import menu.MenuMain;
import static menu.MenuMain.m_vMesa;
import menu.Sample;
import model.Categoria;
import model.Factura;
import model.FacturaItem;
import model.Producto;
import model.TempMesasFactura;
import model.Terceros;
import paint.IconosCategoria;
import paint.Mesa;
import rest.CheckMesaJerseyClient;
import rest.FacturaItemJerseyClient;
import rest.FacturaJerseyClient;
import rest.TempFacturarMesasJerseyClient;
import rest.TercerosJerseyClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FormFacturarMesas extends Sample  {
    
    public static final  Label m_lNoFactura=new Label();
    public static final  Label m_lNoMesa=new Label();
    public static  final TempMesasFactura m_TempMesaFactura=new TempMesasFactura();
    private static ObservableList  m_oDatosFacturaItems = FXCollections.observableArrayList();
    private static final TableView _tFacturaItems=new TableView(); 
    private static  CheckMesaJerseyClient checkNoMesaResourceJerseyClient=new CheckMesaJerseyClient();
    private static   final Stage stage = new Stage(StageStyle.DECORATED);
    WebView browser = new WebView();
    WebEngine webEngine = browser.getEngine();
    private  Long m_lInitIdCat;
    private static double initX;
    private static double initY;
    TextField m_tfNoFactura;
    Pane _pPaneFacturarMesas;
    Mesa m,m2;
    ScrollPane sp;
    private static final GridPane gp=new GridPane(),gp2=new GridPane();
    private static final TableView productsTable=new TableView();    
     private static SearchBox m_SearchBox ;
    private static SearchBox m_CantBox ;
    private static GridPane _gpDatosFactura=new GridPane();
        //campos de texto y etiquetas datos factura
        private static Label _lDate=new Label("Fecha:");
        private static Label _lNoFact=new Label("No. Factura:");
        private static Label _lCliente=new Label("Cliente:");
        private static Label _lCelular=new Label("Celular:");
        private static Label _lNoIdent=new Label("No Ident:");
        private static Label _lEmpleado=new Label("Empleado:");
        private static Label _lValorIva=new Label("Valor IVA:");
        private static Label _lTotal=new Label("Total:");
        private static Label _lSubtotal=new Label("Subtotal:");
         //campos de texto datos factura
         private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         private static TextField _tfDate;
         private static TextField _tfNoFact;
         private static TextField _tfCliente;
         private static TextField _tfCelular;
         private static TextField _tfNoIdent;
         private static TextField _tfValorIva;
         private static TextField _tfTotal;
         private static TextField _tfSubtotal; 
         private static Label L_Credito;
         private static CheckBox Ch_Credito;
         private Button bLoadImage;
         private static ChoiceBox m_CBEmpleados;
         private Vector<Long>m_vLId_Emp=new Vector<Long>();
         private static Button m_bPrint=new Button();
         private static Button m_bPdf=new Button();
         private static Button m_bSave=new Button();
         private static Button m_bNew=new Button();
         private HostServices hostServices;
         private TercerosJerseyClient m_sJerseyClient;
         private TempFacturarMesasJerseyClient tempFacturarMesasJerseyClient;
         private FacturaJerseyClient facturaJerseyClient;
         private GridPane _gpIconoscat;
         private static int sum=0;
         Terceros t;
         public FormFacturarMesas() throws IOException {
             gp.getStyleClass().add("background");
             gp2.getStyleClass().add("background");
             gp2.setVgap(3);
             gp2.setHgap(3);
        Ch_Credito=new CheckBox();
         L_Credito=new Label("Credito: ");
        m_CBEmpleados=new ChoiceBox();
         m_CBEmpleados.getItems().clear();
         //   m_CBCategoria.getStyleClass().add("choice-box");
                int i=0;
                if(MenuMain.getM_oDatosEmpleados().size()>0)
                {
                   for(Terceros empleados:(Terceros[])MenuMain.getM_oDatosEmpleados().get(0))
                   {   
                       m_vLId_Emp.add(i,empleados.getId());
                       m_CBEmpleados.getItems().add(i,empleados.getNombre());
                      
       
                   }
                }
          
        gp.getChildren().removeAll();
        gp2.getChildren().removeAll();
        m_TempMesaFactura.setM_iNoMesa(Integer.parseInt(System.getProperty("pNoMesa")));
        tempFacturarMesasJerseyClient=new TempFacturarMesasJerseyClient();
        facturaJerseyClient=new FacturaJerseyClient();
        m_sJerseyClient=new TercerosJerseyClient();
        _tfNoIdent=new TextField();
       _tfNoIdent.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
              t=  m_sJerseyClient.find_XML(Terceros.class,_tfNoIdent.getText());
                 m_TempMesaFactura.getFactura().setCustomer(t);
                 if(t!=null)
                 {
                _tfCliente.setText(t.getNombre());
                _tfNoIdent.setText(t.getNo_ident());
                _tfCelular.setText(t.getCelular());
                 }
            }
        });
        m_bPrint.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>(){
        @Override public void handle(MouseEvent e) {
           
           //  fprintpdf();
             /*DocFlavor flavor =  DocFlavor.INPUT_STREAM.AUTOSENSE; 
             PrintService service = 
             PrintServiceLookup.lookupDefaultPrintService(); 
             //Se crea un trabajo o servicio de impresion 
             DocPrintJob job = service.createPrintJob(); 
             //Se crea los atributos del servicio a crear 
             DocAttributeSet das = new HashDocAttributeSet(); 
             //Se crea el objeto archivo a imprimir 
             FileInputStream fis = new FileInputStream("/facturacion/recibo.pdf"); 
             //Creacion de documento a imprimir) 
             Doc doc = new SimpleDoc(fis,flavor,das); 
             try
             { 
               //Se envia el trabajo o servicio a imprimir 
               job.print(doc,null); 
              }catch(PrintException e)
             {
               e.printStackTrace();
             }
                File file=new File("/facturacion/recibo.pdf");       
      
               try {  
                   Desktop.getDesktop().print(file);  
                        } catch (IOException e2) {  
                               // TODO Auto-generated catch block  
                                e2.printStackTrace();  
                            } 
               
               */
              
        }  
        });
        m_bSave.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>(){
        @Override public void handle(MouseEvent e) {
          
              save_factura(0);
        }  
        });
        _gpDatosFactura.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    ///System.out.println("SearchBox.handle DOWN");
                   save_factura(1);
                }
            }
        });
        gp.setHgap(10);
        gp.setVgap(10);
        gp.autosize();
        //gp.setLayoutX(10);
        //gp.setLayoutY(20);
        //FormFacturarMesas.m_TempMesaFactura.setM_iNoMesa(0);
        addMesas(false,0);
         //Incializamos el id de la primera categoria
        //llamamos a la funcion miembro createiconoscategoria
         m_lInitIdCat=new Long(0);
         createIconoscategorias();
         ImageView _imgview=new ImageView("/images/bs_print.gif");
        _imgview.setFitHeight(25);
        _imgview.setFitWidth(25);
         m_bPrint.setGraphic(_imgview);
        _imgview=null;
         _imgview=new ImageView("/images/bs_save.gif");
         _imgview.setFitHeight(25);
        _imgview.setFitWidth(25);
         m_bSave.setGraphic(_imgview);
     }
    public FormFacturarMesas(Long _lIdCate) throws IOException {
        m_lInitIdCat=_lIdCate;
        m_SearchBox = new SearchBox();
        m_CantBox = new SearchBox("CantBox");
        m_SearchBox.setMaxWidth(150);
       
        m_SearchBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    ///System.out.println("SearchBox.handle DOWN");
                    m_CantBox.getTextBox().requestFocus();
                }
            }
        });
        m_CantBox.setMaxWidth(150);
        m_CantBox.getTextBox().setText("1");
       m_CantBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.LEFT) {
                    ///System.out.println("SearchBox.handle DOWN");
                    m_SearchBox.getTextBox().requestFocus();
                }
            }
        });
        m_CantBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    int  _ICant=0;
                    if(MenuMain.getM_oDatosCategoria().size()>0)
                {
                 ButtonInsertProduct bp[]= (ButtonInsertProduct[])MenuMain.getM_oDatosProducto().get(0);
                 for(ButtonInsertProduct _ButtonProducto:(ButtonInsertProduct[])MenuMain.getM_oDatosProducto().get(0))
                 {   
                    if(_ButtonProducto.getProducto().getCodigo().equals(m_SearchBox.getTextBox().getText()))  
                    {
                      
                       try{
                      _ICant=Integer.parseInt( m_CantBox.getTextBox().getText());
                               }catch(Exception e)
                               {
                                  e.printStackTrace();
                                 _ICant=1; 
                                 System.out.println("Cant Prodcuto:"+_ICant);
                               }
                       FormFacturarMesas.AgregarProducto(_ButtonProducto.getProducto(),_ICant);  
                       
                    }
       
                   }
                }
                    
                }
            }
        });
       
        m_lNoFactura.setText("No. Factura: ");
        m_lNoMesa.setText("No. Mesa:"+System.getProperty("pNoMesa"));
        m_lNoFactura.getStyleClass().add("label");
        m_lNoMesa.getStyleClass().add("label");
        
        
        //m_tfCant.setPrefHeight(24);
        gp2.getChildren().clear();
        gp2.setHgap(1);
        gp2.setVgap(1);
        gp2.setPrefWidth(200);
     //  gp.setStyle("fxmlterceros.css");
       gp2.setLayoutX(0);
       gp2.setLayoutY(1);
       GridPane gp3=new GridPane();
       gp3.getChildren().clear();
       gp3.add(m_SearchBox,0,2);
       gp3.add(m_CantBox,1,2);
       _pPaneFacturarMesas=new Pane();
        gp2.add(m_lNoFactura,0,0);
        gp2.add(m_lNoMesa,0,1);
        gp2.add(gp3, 0, 2);
        gp2.alignmentProperty().setValue(Pos.CENTER);
        _pPaneFacturarMesas.getChildren().addAll(gp2);
        
        //call funcion filtrar x la primera categoria categoria
        ObservableList _oTempDatosProduct=FXCollections.observableArrayList();
        if(MenuMain.getM_oDatosProducto().size()>0)
                               {
                                  ButtonInsertProduct bp[]= (ButtonInsertProduct[])MenuMain.getM_oDatosProducto().get(0);
                                   for(ButtonInsertProduct _ButtonProducto:(ButtonInsertProduct[])MenuMain.getM_oDatosProducto().get(0))
                                   {
                                     if(_ButtonProducto.getProducto().getCategoria().getId().equals(m_lInitIdCat))  
                                      _oTempDatosProduct.add(_ButtonProducto);
                                   }
                               }
                        FormFacturarMesas.filtrarXCategoria(_oTempDatosProduct);
        
        filtrarXCategoria(_oTempDatosProduct);
        gp2.add(productsTable, 0, 3);
        getChildren().addAll(gp2);
       
       
    }
    public  static Node createIconContent() throws IOException {
            ImageView imageView = new ImageView(new Image("/modulos/facturas/Facturarmesas.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
         javafx.scene.Group g =new javafx.scene.Group(imageView);
        return g;
    }
    
    public void addMesas(boolean _bSelectMesa,int _iNoMesa)
    {
        LinearGradient gradient2 = new LinearGradient(0, 0, 0, 0.5,  true, CycleMethod.REFLECT, new Stop[] {
            new Stop(0, Color.DODGERBLUE),
            new Stop(0.1, Color.BLACK),
            new Stop(1, Color.DODGERBLUE)
        });
        Rectangle _rBackGround = new Rectangle(0, 0,700,700);
        _rBackGround.setFill(gradient2);
       
       _pPaneFacturarMesas=null;
       _pPaneFacturarMesas=new Pane();
      // _pPaneFacturarMesas.getChildren().add(_rBackGround);
     
        //_pPaneFacturarMesas.setStyle("-fx-background-image: url('modulos/facturas/fondo4.jpg');-fx-background-image-width:500;-fx-background-image-heigth:500;");
        
       gp.getChildren().removeAll(MenuMain.m_vMesa);
        
         gp.getChildren().clear();
        int _iNoMesas=MenuMain.getDatosempresa().getM_iNoMesas();
        int _iColumns=0;
        int _iFilas=0;
      
        for(int i=0;i<MenuMain.m_vMesa.size();i++)
        {
           
            if(i%5==0)
            {
                _iColumns++;
                _iFilas=1;
            }
            gp.add(MenuMain.m_vMesa.get(i),  _iFilas, _iColumns);
            _iFilas++;
            
           
        }
       // _pPaneFacturarMesas.setMinHeight(700);
         // _pPaneFacturarMesas.setMinWidth(650);
             //create complex linear gradient
         //gp.setMaxSize(800, 400);
        
        
         //gp.setMaxSize(800, 400);
         GridPane.setHalignment(gp, HPos.CENTER);
         gp.alignmentProperty().setValue(Pos.CENTER);
        
         //_pPaneFacturarMesas.getChildren().addAll(gp);
        GridPane _rootGp=new GridPane();
        
        GridPane.setHalignment(_rootGp, HPos.CENTER);
         _rootGp.alignmentProperty().setValue(Pos.CENTER);
        
        _rootGp.setMinSize(630, 570);
          //gp.setMinWidth(630);
        _rootGp.getChildren().clear();
       _rootGp.add(gp, 0, 0);
    //  _pPaneFacturarMesas.setLayoutX(computeAreaInScreen());
      _rootGp.setTranslateY(25);
      
      //_pPaneFacturarMesas.setCenterShape(true);
  //    _rootGp.setStyle("-fx-background-color: #ffffff;");
         getChildren().clear();
     //    setStyle("-fx-background-color: #ffffff;");
         getChildren().addAll(_rootGp);
       
    }
    public void createIconoscategorias()
    {
        _gpIconoscat=new GridPane();
         Tooltip _tooltip=new Tooltip("Prueba");
        

       Label _lCategoria=new Label("Categoria:");
       _lCategoria.getStyleClass().add("label");
     //  _gpIconoscat.setLayoutX(10);
       _gpIconoscat.setLayoutY(0);
       _gpIconoscat.setHgap(10);
       if(MenuMain.getM_oDatosCategoria().size()>0)
       {int _iNUmCol=0;
       int _iNumCat=0;
           for(Categoria _categoria:(Categoria[])MenuMain.getM_oDatosCategoria().get(0))
           {
               //Cube c = new Cube(25,Color.GOLD,0.5);
                IconosCategoria _iconoscaCategoria=new IconosCategoria(_categoria.getM_NombreCat(),Color.SKYBLUE,15,_iNumCat+1,_categoria.getId(),_categoria.getM_NombreCat());
              _iNumCat++;
                if(_iNUmCol==0)
               {
                  _gpIconoscat.add(_lCategoria,_iNUmCol,0);
                  _iNUmCol++;
                  _gpIconoscat.add(_iconoscaCategoria,_iNUmCol,0);
                  m_lInitIdCat=_categoria.getId();
               }
               else
               {
               _gpIconoscat.add(_iconoscaCategoria,_iNUmCol,0);
               }
               _iNUmCol++;
           }
       //_pPaneFacturarMesas.getChildren().add(_gpIconoscat);
        gp.add(_gpIconoscat, 0, 0, 5, 1);
       
       }
               
    }
   

 public static class ImageViewTableCell<S, T> extends TableCell<S, T> {
        private final Button img;
        private ObservableValue<T> ov;
        private Image Loadimg;
        private File file;
        private WritableImage wi;
        private BufferedImage bi;
 
        public ImageViewTableCell() {
            this.img = new Button("Insertar");
            ImageView img2=new ImageView();
           
             
                  
                  
            setGraphic(img);
        } 
 }
 
 //Funciones Estaticas
  public  static void  createFactura()
    {
        
        TempMesasFactura tm=new TempMesasFactura();
        //Averiguamos si ya se creo la factura para el no de mesa
        //si no se creo entonces insertamos una nueva factura y la asociamos al no de mesa
        if(MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).isM_bFreeMesa())
        {
         // checkNoMesaResourceJerseyClient=null;
          checkNoMesaResourceJerseyClient=new CheckMesaJerseyClient();
          tm=checkNoMesaResourceJerseyClient.getXml(TempMesasFactura.class,String.valueOf(m_TempMesaFactura.getM_iNoMesa()));
          //checkNoMesaResourceJerseyClient.close();
          tm.getFactura().setCustomer(new Terceros());
          tm.getFactura().setEmpleado(new Terceros());
          m_TempMesaFactura.setId(tm.getId());
          m_TempMesaFactura.setM_iNoMesa(tm.getM_iNoMesa());
          m_TempMesaFactura.setFactura(tm.getFactura());
          MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).setM_bFreeMesa(false);
          MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).setFactura(m_TempMesaFactura.getFactura());
          sum=1;
        }
        else
        {
            m_TempMesaFactura.setFactura(MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).getFactura());
            m_TempMesaFactura.setId(MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).getM_LIdTempMesa());
            sum=0;
        }
       
        m_lNoFactura.setText("No Factura: "+String.valueOf(m_TempMesaFactura.getFactura().getId()));
       
    }
  public static void AgregarProducto(Producto producto, int _ICant)
          
  { 
       System.out.println("Cant Prodcuto:"+_ICant);
      if(_ICant==0)
      {
         try{
                      _ICant=Integer.parseInt( m_CantBox.getTextBox().getText());
                               }catch(Exception e)
                               {
                                 _ICant=1;  
                               }  
      }
     if(m_TempMesaFactura.getFactura()!=null)
      {
         Factura _factura=new Factura();
        _factura.setId(MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).getFactura().getId());
         FacturaItem _factuFacturaItem=new FacturaItem();
         _factuFacturaItem.setProduct(producto);
         _factuFacturaItem.setQuantity(_ICant);
        _factuFacturaItem.setFactura(_factura);
        _factuFacturaItem.setValor_total((BigDecimal.valueOf(_factuFacturaItem.getQuantity())).multiply(_factuFacturaItem.getProduct().getPrecio()));
        //_factuFacturaItem.setPrecio_compra(pv);
        _factuFacturaItem.setValor_total2((BigDecimal.valueOf(_factuFacturaItem.getQuantity())).multiply(_factuFacturaItem.getPrecio_compra()));
    
        //Averiguamos si ya se creo la factura para el no de mesa
        //si no se creo entonces insertamos una nueva factura y la asociamos al no de mesa
      
          try
          {
          checkNoMesaResourceJerseyClient.create_XML(_factuFacturaItem);
          MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).getFactura().addProduct(producto,_ICant , BigDecimal.ONE, null);
          MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).getFactura().calculateTotals();
          gp.getChildren().remove( m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1));
          m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1).getChildren().clear();
          m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1).createmesa();
          gp.getChildren().add(m_TempMesaFactura.getM_iNoMesa()-1, m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1));
    
          }catch(Exception e)
          {
            e.printStackTrace();
          }
      }
      else
      {
             Group rootGroup = new Group();
                //create scene with set width, height and color
              
 
                // CREATION OF THE DRAGGER (CIRCLE)
           
                //create dragger with desired size
                Circle dragger = new Circle(100, 100, 100);
                //fill the dragger with some nice radial background
                dragger.setFill(new RadialGradient(-0.3, 135, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.DARKGRAY),
                    new Stop(1, Color.BLACK)
                 }));
 
 
              //imagen
                    ImageView _imgAdvertencia = new ImageView(new Image(MenuMain.class.getResourceAsStream("/images/advertencia.jpg")));
                   _imgAdvertencia.setFitHeight(40);
                   _imgAdvertencia.setFitWidth(40);
                // CREATE SIMPLE TEXT NODE
                Text text = new Text("Seleccione\n Una Mesa"); //20, 110,
                text.setFill(Color.YELLOW);
                text.setEffect(new Lighting());
                text.setBoundsType(TextBoundsType.VISUAL);
                text.setFont(Font.font(Font.getDefault().getFamily(), 20));
                text.textAlignmentProperty().setValue(TextAlignment.CENTER);
               
                //button close
                Button close = new Button("Cerrar");
                close.getStyleClass().add("button");
                close.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        //in case we would like to close whole demo
                        //javafx.application.Platform.exit();
 
                        //however we want to close only this instance of stage
                      MenuMain.hideModalMessage();
                    }
                });
                // USE A LAYOUT VBOX FOR EASIER POSITIONING OF THE VISUAL NODES ON SCENE
                VBox vBox = new VBox();
                vBox.setSpacing(10);
                vBox.setPadding(new Insets(30, 0, 0, 20));
                vBox.setAlignment(Pos.TOP_CENTER);
                vBox.getChildren().addAll(_imgAdvertencia,text,close);
                vBox.setLayoutX(30);
                //add all nodes to main root group
                rootGroup.getChildren().addAll(dragger, vBox);
            
          MenuMain.showModalMessage(rootGroup);
      }
  }
   public  static void  filtrarXCategoria(ObservableList _oTempDatosProducto)
    {   
      
        productsTable.setItems(_oTempDatosProducto);
        productsTable.setPrefWidth(200);
        productsTable.setPrefHeight(520);
          //"First Name" column
        TableColumn firstNameCol = new TableColumn();
        firstNameCol.setText("Producto");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("m_bInsertProduct"));
        TableColumn secondNameCol = new TableColumn();
        
        firstNameCol.setPrefWidth(productsTable.getPrefWidth());
         
         productsTable.getColumns().clear();
         productsTable.getColumns().addAll(firstNameCol);
         productsTable.setEditable(true);
         
      

    }
   public static void DetallesFactura(double x,double y)
   {
                 
          //create root node of scene, i.e. group
       /*if (stage.isShowing())
       {
           stage.close();
       }*/
                Group rootGroup = new Group();
                //create scene with set width, height and color
                Scene scene = new Scene(rootGroup,800,500);
                //set scene to stage
                stage.setScene(scene);
                //center stage on screen
                
                stage.centerOnScreen();
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stage.show();
                createContentFactura();
                // CREATION OF THE DRAGGER (CIRCLE)
           
               
 
                //when mouse button is pressed, save the initial position of screen
                rootGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        initX = me.getScreenX() - stage.getX();
                        initY = me.getScreenY() - stage.getY();
                    }
                });
 
                //when screen is dragged, translate it accordingly
                rootGroup.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        stage.setX(me.getScreenX() - initX);
                        stage.setY(me.getScreenY() - initY);
                    }
                });
                
               _gpDatosFactura.setMinSize(stage.getWidth(),stage.getHeight() );
               _gpDatosFactura.setAlignment(Pos.CENTER);
               GridPane.setValignment(_gpDatosFactura, VPos.TOP);
//add all nodes to main root group
rootGroup.getChildren().addAll(_gpDatosFactura);
            
        
        
   }

    public Long getM_lInitIdCat() {
        return m_lInitIdCat;
    }

    public void setM_lInitIdCat(Long m_lInitIdCat) {
        this.m_lInitIdCat = m_lInitIdCat;
    }
    
   public static void createContentFactura()
   {
          //create stage which has set stage style transparent
          GridPane _gpDatosFacturaItems=new GridPane();
          //campos de texto datos factura
         _tfDate=new TextField(dateFormat.format(m_TempMesaFactura.getFactura().getFacturaDate()));
         _tfDate.setPrefWidth(100);
         _tfNoFact=new TextField(m_TempMesaFactura.getFactura().getId().toString());
         _tfCliente=new TextField(m_TempMesaFactura.getFactura().getCustomer().getNombre());
         _tfCelular=new TextField(m_TempMesaFactura.getFactura().getCustomer().getCelular());
         _tfNoIdent.setText(m_TempMesaFactura.getFactura().getCustomer().getNo_ident());
                
          
         Ch_Credito.setSelected(m_TempMesaFactura.getFactura().isCredito());
         _tfValorIva=new TextField(m_TempMesaFactura.getFactura().getIva().toString());
         _tfValorIva.setEditable(false);
         _tfValorIva.getStyleClass().add("text-field2");
         _tfTotal=new TextField(m_TempMesaFactura.getFactura().getTotalAmount().toString());
           _tfTotal.setEditable(false);
         _tfTotal.getStyleClass().add("text-field2");
        
          _tfSubtotal=new TextField(m_TempMesaFactura.getFactura().getNetAmount().toString());
         _tfSubtotal.setEditable(false);
         _tfSubtotal.getStyleClass().add("text-field2");
        
        
         
        Object[] _listFacturaItem=(m_TempMesaFactura.getFactura().getFacturaItems()).toArray();
        m_oDatosFacturaItems.clear();
        m_oDatosFacturaItems.addAll(_listFacturaItem);
        _tFacturaItems.setItems(m_oDatosFacturaItems);
        _tFacturaItems.setPrefWidth(500);
      
         
        //"First Name" column
   TableColumn firstNameCol = new TableColumn();
        
        firstNameCol.setText("Producto");
        firstNameCol.setCellValueFactory(new Callback<CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<FacturaItem, String> oferta) {
                return new SimpleStringProperty(oferta.getValue().getProduct().getNombre());
            }
        });

        firstNameCol.setPrefWidth(100);
  
         
         
       TableColumn secondNameCol = new TableColumn();
        
        secondNameCol.setText("Valor/U");
        secondNameCol.setCellValueFactory(new Callback<CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<FacturaItem, String> oferta) {
                return new SimpleStringProperty(oferta.getValue().getProduct().getPrecio().toString());
            }
        });

        secondNameCol.setPrefWidth(100);
  
   
        TableColumn thirdNameCol = new TableColumn();
        Button _b=new Button("");
        _b.setId("_b");
        thirdNameCol.setText("Cantidad");
        thirdNameCol.setCellValueFactory(new PropertyValueFactory("quantity"));
        thirdNameCol.setPrefWidth(_tFacturaItems.getPrefWidth()*0.21);
 
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
                return new ButtonCell(_tFacturaItems);
            }
         
        });
          buttonCol.setPrefWidth(42); 
          
         _tFacturaItems.getColumns().clear();
         _tFacturaItems.getColumns().addAll(firstNameCol,thirdNameCol,secondNameCol,cuartaNameCol,buttonCol);
         _tFacturaItems.setEditable(true);
         _tFacturaItems.setPrefHeight(400);
         _tFacturaItems.setLayoutX(0);
         _tFacturaItems.setLayoutY(10);
         
 
  _gpDatosFacturaItems=null;
  _gpDatosFacturaItems=new GridPane();
   HBox vBox = new HBox();
                vBox.setSpacing(5);
               // vBox.setPrefWidth(70);
                //vBox.setPadding(new Insets(30, 0, 0, 20));
                vBox.setAlignment(Pos.CENTER_LEFT);
                vBox.getChildren().addAll(m_bPrint,m_bSave);
               // vBox.setLayoutX(30);
              
 _gpDatosFactura.getStylesheets().add("/menu/EnsembleStylesCommon.css");
 _gpDatosFactura.getStyleClass().add("background");
 _gpDatosFactura.setVgap(5);
 _gpDatosFactura.setHgap(5);
 _gpDatosFactura.getChildren().clear();
 GridPane.setValignment(_lDate, VPos.TOP);
 GridPane.setValignment(_tfDate, VPos.TOP);
 GridPane.setValignment(_lNoFact, VPos.TOP);
 GridPane.setValignment(_tfNoFact, VPos.TOP);
 GridPane.setValignment(_lCliente, VPos.TOP);
 GridPane.setValignment(_tfCliente, VPos.TOP);
 GridPane.setValignment(_lNoIdent, VPos.TOP);
 GridPane.setValignment(_tfNoIdent, VPos.TOP);
 GridPane.setValignment(_lCelular, VPos.TOP);
 GridPane.setValignment(_tfCelular, VPos.TOP);
 GridPane.setRowSpan(_gpDatosFacturaItems, 16);
 
 _gpDatosFacturaItems.add(_tFacturaItems, 0, 0);
 _gpDatosFactura.add(_gpDatosFacturaItems, 0, 0);
 _gpDatosFactura.add(_lDate, 1, 0);
 _gpDatosFactura.add(_tfDate, 2, 0);
 _gpDatosFactura.add(_lNoFact, 1, 1);
 _gpDatosFactura.add(_tfNoFact, 2, 1);
 _gpDatosFactura.add(_lCliente, 1, 2);
 _gpDatosFactura.add(_tfCliente, 2, 2);
 _gpDatosFactura.add(_lNoIdent, 1, 3);
 _gpDatosFactura.add(_tfNoIdent, 2, 3);
 _gpDatosFactura.add(_lCelular, 1, 4);
 _gpDatosFactura.add(_tfCelular, 2, 4);
 _gpDatosFactura.add(_lEmpleado, 1, 5);
 _gpDatosFactura.add(m_CBEmpleados, 2, 5);
 _gpDatosFactura.add(L_Credito, 1, 6);
 _gpDatosFactura.add(Ch_Credito, 2, 6);
 _gpDatosFactura.add(vBox, 1, 7);
 _gpDatosFactura.add(_lValorIva, 1, 8);
 _gpDatosFactura.add(_tfValorIva, 2, 8);
 _gpDatosFactura.add(_lSubtotal, 1, 9);
 _gpDatosFactura.add(_tfSubtotal, 2, 9);
 _gpDatosFactura.add(_lTotal, 1, 10);
 _gpDatosFactura.add(_tfTotal, 2, 10);
 GridPane.setColumnSpan(vBox, 3);

   }
   
   //Class button
   private static  class ButtonCell extends TableCell<FacturaItem, Boolean> {
        
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
                    FacturaItemJerseyClient _facFacturaItemJerseyClient=new FacturaItemJerseyClient();
                    _facFacturaItemJerseyClient.remove(((FacturaItem)m_oDatosFacturaItems.get(selectdIndex)).getFactura().getId().toString(),((FacturaItem)m_oDatosFacturaItems.get(selectdIndex)).getProduct().getId().toString());
                    MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).getFactura().removeProduct(((FacturaItem)m_oDatosFacturaItems.get(selectdIndex)).getProduct());
                    MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).getFactura().calculateTotals();
                    m_TempMesaFactura.setFactura( MenuMain.getM_vMesa().get(m_TempMesaFactura.getM_iNoMesa()-1).getFactura());
                    m_oDatosFacturaItems.remove(selectdIndex);
                    _tfValorIva=new TextField(m_TempMesaFactura.getFactura().getIva().toString());
                    _tfTotal=new TextField(m_TempMesaFactura.getFactura().getTotalAmount().toString());
                    _tfSubtotal=new TextField(m_TempMesaFactura.getFactura().getNetAmount().toString());
                    _gpDatosFactura.getChildren().remove(12);
                    _gpDatosFactura.getChildren().remove(13);
                    _gpDatosFactura.getChildren().remove(14);
                    _gpDatosFactura.add(_tfValorIva, 2, 12);
                    _gpDatosFactura.add(_tfSubtotal, 2, 13);
                    _gpDatosFactura.add(_tfTotal, 2, 14);
 
             
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
   public void fprintpdf()
   {
        Document document = new Document();
        
      try {
          PdfWriter.getInstance(document, new FileOutputStream("/facturacion/recibo.pdf"));
          document.open();
           PdfPTable table = new PdfPTable(new float[] { 1, 7});
           table.setWidthPercentage(100f);
           
           table.getDefaultCell().setUseAscender(true);
           table.getDefaultCell().setUseDescender(true);
           table.getDefaultCell().setColspan(1);
           table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
           table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
           table.getDefaultCell().setFixedHeight(60f);
          // log.info("heigth:"+img.getHeight());
           //table.addCell(img);
           table.getDefaultCell().setPadding(15);
           table.addCell(MenuMain.getDatosempresa().getM_sNombre()+"\n       Factura No: "+m_TempMesaFactura.getFactura().getId());
           table.getDefaultCell().setFixedHeight(45);
           table.getDefaultCell().setPadding(15);
          
           table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
           table.getDefaultCell().setColspan(2);
           table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
           table.addCell("Fecha: "+m_TempMesaFactura.getFactura().getFacturaDate());
           table.addCell("Recibido:"+m_TempMesaFactura.getFactura().getCustomer().getNombre()+" Cc: "+m_TempMesaFactura.getFactura().getCustomer().getNo_ident());
           table.addCell("Valor: "+m_TempMesaFactura.getFactura().getTotalAmount());
           table.addCell("Elaboro:________ Reviso:________ Contabilizo:________ Firma y Sello:________");
           
           document.add(table);
           
            // step 5
          document.close();
        } catch (DocumentException de) {
          System.err.println(de.getMessage());
      } catch (IOException ioe) {
          System.err.println(ioe.getMessage());
      }
       
   }
   
   private void save_factura(int opc)
   {
       int index=m_CBEmpleados.getSelectionModel().getSelectedIndex();
            m_TempMesaFactura.getFactura().setCredito(Ch_Credito.isSelected());
            if(index>=0)
            {
                Terceros empleado=new Terceros();
                empleado.setId(m_vLId_Emp.get(index));
                empleado.setTipo_terc(0);
                m_TempMesaFactura.getFactura().setEmpleado(empleado);
                    
             }
            else
            {
                m_TempMesaFactura.getFactura().setEmpleado(null);
            }
             
              if(_tfNoIdent.getText() != null)
              {
                if(t!=null)
                {
                   t.setCelular(_tfCelular.getText());
                   m_TempMesaFactura.getFactura().setCustomer(t);
                }
                else
                {
                      m_TempMesaFactura.getFactura().setCustomer(new Terceros());
                      m_TempMesaFactura.getFactura().getCustomer().setNo_ident(_tfNoIdent.getText());
                      m_TempMesaFactura.getFactura().getCustomer().setNombre(_tfCliente.getText());
                      m_TempMesaFactura.getFactura().getCustomer().setCelular(_tfCelular.getText());
                }
               }
               else
               {
                   m_TempMesaFactura.getFactura().setCustomer(null);
               }
                Factura f=new Factura();
                f.setCustomer(m_TempMesaFactura.getFactura().getCustomer());
                f.setEmpleado(m_TempMesaFactura.getFactura().getEmpleado());
                f.setId(m_TempMesaFactura.getFactura().getId());
                f.setCredito(Ch_Credito.isSelected());
                facturaJerseyClient.edit_XML(f,m_TempMesaFactura.getFactura().getId().toString());
                if(m_TempMesaFactura.getFactura().getCustomer()==null)
                {
                    m_TempMesaFactura.getFactura().setCustomer(new Terceros());
                }
                if(opc==0)
                {
                tempFacturarMesasJerseyClient.remove(String.valueOf(m_TempMesaFactura.getM_iNoMesa()));
                m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1).setFactura(null);
                m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1).setFactura(new Factura());
                m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1).getFactura().setCustomer(new Terceros());
                m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1).setM_bFreeMesa(true);
                m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1).setM_bSelecMesa(false);
              
                gp.getChildren().remove( m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1));
                m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1).getChildren().clear();
                m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1).createmesa();
                gp.getChildren().add(m_TempMesaFactura.getM_iNoMesa()-1, m_vMesa.get(m_TempMesaFactura.getM_iNoMesa()-1));
                }
               // stage.hide();
                stage.close();
            
   }
   
}