/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios.pasivos.cuentasporpagar;



import isoftconta.ContabilidadFXServicios;
import isoftconta.IsoftConta;
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import isoftconta.servicios.FacturaProveedoresController;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import isoftconta.util.ImpresionFactura;
import isoftconta.util.StageGeneric;


import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TitledPane;
import modelo.FacturaProveedores;
import modelo.EntidadesStatic;




/**
 *
 * @author adminlinux
 */
public class FFacturaCompra  extends ContabilidadFXServicios{

    private GridPane  gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private Label la_datefrom;
    private Label la_dateto;
    private static TextField buscar;
    private static ObservableList ol_generic=FXCollections.observableArrayList();;
    private Button bu_buscar;
    private Button bu_nuevo;
    private  GridPane Gp_Message;
    private  Label L_Message;
    private  Runnable Task_Message;
    Thread   backgroundThread;
    private  StackPane stack;
    private  ImpresionFactura impresionFactura;
    private  SimpleDateFormat df=new SimpleDateFormat("MMM-dd");
    private  String appClass;
    private  Class clz ;
    private  Object app ;
    private  Parent parent;
    private  Stage stage= new Stage(StageStyle.DECORATED);
    Scene scene =null;
    private ContextMenu contextMenu;
    private MenuItem  itemnuevo;
    private MenuItem  itemeditar;
     private MenuItem  itemabonos;
    private ImageView img;
    private TitledPane tp_generic;
    private static DatePicker datefrom;
    private static DatePicker dateto;
    private  String appClassAbonos;
    private  Class clzAbonos ;
    private  Object appAbonos ;
    private  Parent parentAbonos;
    private  Stage stageAbonos= new Stage(StageStyle.DECORATED);
    Scene sceneFactura =null;
    private static ChoiceBox cb_formaspago;
    private final StageGeneric stagefacturadecompra=new StageGeneric("isoftconta.servicios.pasivos.cuentasporpagar.FICI_FacturaCompra", "Factura de Compra",true);

    @Override
    public Node getPanel(Stage stage) {  stack=new StackPane();
      char IM_NUEVO = '\uf0fe';
        
       
     tp_generic=new TitledPane();
     tp_generic.setCollapsible(false);
     tp_generic.setText("Administrar Facturas de Proveedores");
     stageAbonos.setTitle("Abonos a Factura ");  
     stage.setTitle("Facturas");
     img=new ImageView ("/images/new2.png");
     img.setFitHeight(20);
     img.setFitWidth(20);
     itemnuevo=new MenuItem("Nuevo",img);
     itemnuevo.setOnAction(e->{
          try{
            EntidadesStatic.es_facturaproveedores=null;  
             show();
           }catch(Exception x)
           {
               
           }
       });
       img=null;
        img=new ImageView ("/images/editar.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
       itemeditar=new MenuItem("Editar",img);
        itemeditar.setOnAction(e->{
         
           try{
         checkregistroexistente();
         show();
           }catch(Exception x)
           {
               x.printStackTrace();
           }
       });
      img=null;
         img=new ImageView ("/images/abonos.png");
         img.setFitHeight(20);
         img.setFitWidth(20);
         itemabonos=new MenuItem("Abonos a factura",img);
         itemabonos.setOnAction(e->{
         
           try{
         checkregistroexistente();
         showAbonosFactura();
           }catch(Exception x)
           {
               x.printStackTrace();
           }
       });
    contextMenu=new ContextMenu(itemnuevo,itemeditar);
    la_buscar=new Label("Buscar: ");
    la_buscar.setMinWidth(45);
    buscar=new TextField();
    buscar.setMinWidth(150);
    buscar.setOnKeyReleased(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
         }
    });
    la_datefrom=new Label("Desde:");
    datefrom=new DatePicker(LocalDate.now());
   datefrom.setMinWidth(100);
    datefrom.setOnAction(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
         }
    });
    dateto=new DatePicker(LocalDate.now());
    la_dateto=new Label("Hasta:");
    la_datefrom.setMinWidth(40);
    la_dateto.setMinWidth(40);
    dateto.setMinWidth(100);
     dateto.setOnAction(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
         }
    });
    cb_formaspago=new ChoiceBox<Object>();
    cb_formaspago.getItems().addAll("Todos","Contado","Crédito","Saldo > 0");
    cb_formaspago.getSelectionModel().getSelectedIndex();
    cb_formaspago.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
     Label la_formaspago=new Label("Formas de Pago:");
        la_formaspago.setMinWidth(90);
        cb_formaspago.setMinWidth(90);
      
     
           
           
          Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
          Glyph glyph_nuevo=  IsoftConta.icoMoon.create(IM_NUEVO).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
          Glyph glyph_find=  IsoftConta.icoMoon.create(FontAwesome.Glyph.SEARCH.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
          bu_nuevo=new Button("", glyph_nuevo);
          bu_nuevo.setDisable(false);
          bu_nuevo.setOnAction(e->
          {
           try {
               EntidadesStatic.es_facturaproveedores=null;  
               show();
             
           } catch (Exception ex) {
               ex.printStackTrace();
           }
          });
        bu_buscar=new Button("",glyph_find);
           bu_buscar.setDisable(false);
           bu_buscar.setOnAction(e->
          {
           try {
              getRecords();
             
           } catch (Exception ex) {
              
           }
          });
    gp_generico=new GridPane();
    tv_generic=new TableView(); 
    
    tv_generic.setOnContextMenuRequested(e->{
         try {
             checkregistroexistente();
         } catch (Exception ex) {
             
         } 
         setItemMenu();
    });
     stage.setOnHiding(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
         }
     });
     stageAbonos.setOnHiding(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
         }
     });
     TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(100);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getNo_factura().toString());
                            
            }
        });
       
       TableColumn noident = new TableColumn();
        noident.setMinWidth(100);
        noident.setText("N° Ident");
        noident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
               try{
                    return new SimpleStringProperty(factura.getValue().getProveedores().getNo_ident());
               }catch(Exception e)
               { return new SimpleStringProperty("NO");  
               }
            }
        });
        
       TableColumn nombre = new TableColumn();
        nombre.setMinWidth(300);
        nombre.setText("Proveedor");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
               
                  try{
                    return new SimpleStringProperty(factura.getValue().getProveedores().getNombres());
               }catch(Exception e)
               { return new SimpleStringProperty("NO");  
               }
                            
            }
        });
         TableColumn fecha = new TableColumn();
        fecha.setMinWidth(100);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
               
                    return new SimpleStringProperty(isoftconta.util.UtilDate.formatodiamesyear(factura.getValue().getFacturaDate()));
                            
            }
        });
         TableColumn valor = new TableColumn();
        valor.setMinWidth(100);
        valor.setText("valor");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());
                            
            }
        });
        tv_generic.getColumns().addAll(fecha,nofactura,noident,nombre,valor);
     
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        tv_generic.setContextMenu(contextMenu);
        
        gp_generico.addRow(0,la_buscar,buscar,la_formaspago,cb_formaspago,la_datefrom,datefrom,la_dateto,dateto,bu_nuevo);
        gp_generico.add(tv_generic, 0, 3,9,1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic);
        
        try {
            getRecords();
        } catch (ParseException ex) {
            Logger.getLogger(FFacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stack;
  }
  
public static void   getRecords() throws ParseException
{
         //colocamos los datos
         try{
         EntidadesStatic.li_facturaproveedores=FacturaProveedoresController.getRecords(datefrom.getValue().toString(),dateto.getValue().toString(),buscar.getText(),cb_formaspago.getSelectionModel().getSelectedIndex()>=0?cb_formaspago.getSelectionModel().getSelectedIndex():0);
         ol_generic.clear();
         ol_generic.addAll(EntidadesStatic.li_facturaproveedores.toArray());
         tv_generic.setItems(ol_generic);
      } catch(Exception e)
       {
           e.printStackTrace();
       }
    }
 private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {
      stagefacturadecompra.showmodal();
    
  }  
   
private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
{
       if ((tv_generic.getSelectionModel())!=null)
       {
           EntidadesStatic.es_facturaproveedores=(FacturaProveedores)tv_generic.getSelectionModel().getSelectedItem();
           
       }
}

   private void showAbonosFactura() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  appClassAbonos="abaco.soluciones.documentos.AdminAbonosFacturaProveedor";
    clzAbonos = Class.forName(appClassAbonos);
    appAbonos = clzAbonos.newInstance();
   
         
      parentAbonos=null;
      parentAbonos = (Parent) clzAbonos.getMethod("createContent").invoke(appAbonos);
      sceneFactura=null;
       sceneFactura=new Scene(parentAbonos, Color.TRANSPARENT);
       
        if (stageAbonos.isShowing())
       {
           stageAbonos.close();
       }      
        
        
//set scene to stage
                stageAbonos.sizeToScene();
                
              
                //center stage on screen
                stageAbonos.centerOnScreen();
                stageAbonos.setScene(sceneFactura);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stageAbonos.show();
  }    


 public void setItemMenu()
 {
     
     contextMenu.getItems().clear();
     int opcion=EntidadesStatic.es_facturaproveedores.getCredito()==null?1:EntidadesStatic.es_facturaproveedores.getCredito()==true?0:1;
     switch(opcion)
     {
         case 0: contextMenu.getItems().addAll(itemnuevo,itemeditar,itemabonos);
         break;
         case 1: contextMenu.getItems().addAll(itemnuevo,itemeditar);
         break;
       
     }
     
 }
   
 public void indexChanged(ObservableValue<? extends Number> observable,Number oldValue,Number newValue) 
    {
        try{
          
     getRecords();
        }catch(Exception e)
        {
           
        }
    }    
  
   
     @Override
    public void start(Stage primaryStage) throws Exception {
        
        
    }
  @Override
    public String getServicioNombre() {
        return "Compra Mercancias para la Venta";
    }   
}
