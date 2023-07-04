/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.procesosadministrativos;

import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
import abaco.soluciones.administrador.ImpresionFactura;
import com.aquafx_project.AquaFx;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TitledPane;
import jfxtras.styles.jmetro8.JMetro;
import model.Factura;
import org.aerofx.AeroFX;




/**
 *
 * @author adminlinux
 */
public class AdminFacturas  extends Application{

    private GridPane  gp_generico;
    private static TableView tv_facturas;
    private Label la_buscar;
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
    public static  Stage stagefactura;
    private  String appClassAbonos;
    private  Class clzAbonos ;
    private  Object appAbonos ;
    private  Parent parentAbonos;
    private  Stage stageAbonos= new Stage(StageStyle.DECORATED);
    public static Scene scene =null;
    Scene sceneFactura =null;
    private ContextMenu contextMenu;
    private MenuItem  itemnuevo;
    private MenuItem  itemeditar;
    private MenuItem  itemabonos;
    private ImageView img;
    private TitledPane tp_generic;
    private static DatePicker datefrom;
    private static DatePicker dateto;
    private static ChoiceBox cb_formaspago;
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException  {
     stagefactura= new Stage(StageStyle.DECORATED);
     
     stack=new StackPane();
     tp_generic=new TitledPane();
     tp_generic.setText("Administrar Facturas");
     tp_generic.setCollapsible(false);
     stageAbonos.setTitle("Abonos a Factura ");
   
     contextMenu=null;
     contextMenu=new ContextMenu();   
        img=new ImageView ("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo=new MenuItem("Nuevo",img);
        itemnuevo.setOnAction(e->{
          try{
             AbacoApp.s_factura=null; 
             showfactura();
           }catch(Exception x)
           {
               x.printStackTrace();
           }
       });
        img=null;
        img=new ImageView ("/images/editar.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
       itemeditar=new MenuItem("Editar Factura",img);
        itemeditar.setOnAction(e->{
         
           try{
         checkregistroexistente();
         showfactura();
           }
           catch(Exception x)
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
  
    
    la_buscar=new Label("Buscar: ");
    la_buscar.setMinWidth(100);
    buscar=new TextField();
    buscar.setMinWidth(300);
    buscar.setOnKeyReleased(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
         }
    });
     ImageView imageView;
       
         imageView=new ImageView("/images/find.png");
         imageView.setFitHeight(20);
         imageView.setFitWidth(20);
           
           bu_buscar=new Button("",imageView);
           bu_buscar.setDisable(false);
           bu_buscar.setOnAction(e->
          {
           try {
              getRecords();
             
           } catch (Exception ex) {
              
           }
          });
            imageView=new ImageView("/images/new2.png");
         imageView.setFitHeight(20);
         imageView.setFitWidth(20);
           
           bu_nuevo=new Button("",imageView);
           bu_nuevo.setDisable(false);
           bu_nuevo.setOnAction(e->
          {
           try {
               AbacoApp.s_factura=null;
               showfactura();
             
           } catch (Exception ex) {
               ex.printStackTrace();
           }
          });
     datefrom=new DatePicker(LocalDate.now());
     datefrom.setMinWidth(150);
     datefrom.setOnAction(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(AdminFacturaProveedores.class.getName()).log(Level.SEVERE, null, ex);
         }
    });
    dateto=new DatePicker(LocalDate.now());
    dateto.setMinWidth(150);
     dateto.setOnAction(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(AdminFacturaProveedores.class.getName()).log(Level.SEVERE, null, ex);
         }
    });
    cb_formaspago=new ChoiceBox<Object>();
    cb_formaspago.getItems().addAll("Todos","Contado","Crédito","Saldo > 0");
    cb_formaspago.getSelectionModel().select(0);
    cb_formaspago.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
    gp_generico=new GridPane();
    tv_facturas=new TableView(); 
    
    tv_facturas.setOnContextMenuRequested(e->{
         try {
             checkregistroexistente();
         } catch (Exception ex) {
             Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
         } 
         setItemMenu();
    });
   
     stageAbonos.setOnHiding(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
         }
     });
     TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(140);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               try{
                    return new SimpleStringProperty(factura.getValue().getNofacturacerosizquierda());
               }catch(Exception e)
               {
                    return new SimpleStringProperty("N/A");
               }
            }
        });
       
       TableColumn noident = new TableColumn();
        noident.setMinWidth(120);
        noident.setText("N° Ident");
        noident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               try{
                    return new SimpleStringProperty(factura.getValue().getCustomer().getNo_ident());
               }catch(Exception e)
                 {
                             return new SimpleStringProperty("No");
                 }
                            
            }
        });
        
       TableColumn nombre = new TableColumn();
        nombre.setMinWidth(180);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               try{
                    return new SimpleStringProperty(factura.getValue().getCustomer().getNombre());
                     }catch(Exception e)
                            {
                             return new SimpleStringProperty("No");
                            }
                            
            }
        });
         TableColumn fecha = new TableColumn();
        fecha.setMinWidth(120);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(abaco.util.UtilDate.formatodiamesyear(factura.getValue().getFacturaDate()));
                            
            }
        });
         TableColumn valor = new TableColumn();
        valor.setMinWidth(100);
        valor.setText("valor");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());
                            
            }
        });
        TableColumn formadepago = new TableColumn();
        formadepago.setMinWidth(100);
        formadepago.setText("F/Pago");
        formadepago.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
              try{
                    return new SimpleStringProperty(factura.getValue().getCredito()?"Crédito":"Contado");
              }catch(Exception e)
              {
                             return new SimpleStringProperty("No");
               }  
                            
            }
        });
        
        TableColumn abonos = new TableColumn();
        abonos.setMinWidth(140);
        abonos.setText("Total Abonos");
        abonos.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
              
                    return new SimpleStringProperty(factura.getValue().getValor_abonos().toString());
                    
                            
            }
        });
        TableColumn saldo = new TableColumn();
        saldo.setMinWidth(100);
        saldo.setText("Saldo");
        saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
              
                    return new SimpleStringProperty((factura.getValue().getTotalAmount().subtract(factura.getValue().getValor_abonos())).toString());
                    
                            
            }
        });
     tv_facturas.setMinHeight(550);
        tv_facturas.setContextMenu(contextMenu);
        Gp_Message=new GridPane();
        Gp_Message=new GridPane();
        Gp_Message.setMinWidth(gp_generico.getLayoutBounds().getWidth());
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
       // gp_generico.getStylesheets().add("/abaco/SofackarStylesCommon.css");
     //   gp_generico.getStyleClass().add("background");
        Label la_formaspago=new Label("Formas de Pago:");
        la_formaspago.setMinWidth(150);
        Label la_desde=new Label("Desde:");
        la_desde.setMinWidth(100);
        Label la_a=new Label("A:");
        la_a.setMinWidth(70);
        cb_formaspago.setMinWidth(150);
        gp_generico.addRow(0,la_buscar,buscar,bu_nuevo);
        gp_generico.add(tv_facturas, 0, 1,3,1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
      
         
      
        stack.setAlignment(Pos.TOP_CENTER);
         tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic,Gp_Message);
        switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gp_generico);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gp_generico);
                     break;              
         }
        if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()!=0)
        {
          bu_nuevo.setStyle("-fx-background-color:#007fff");
         
                 
        }
         gp_generico.setMinSize(1000, 610);
        gp_generico.setMaxSize(1000, 610);
             tv_facturas.getColumns().addAll(fecha,nofactura,noident,nombre,formadepago,valor,abonos,saldo);
        //ta_librodiario.setMinWidth(650);
       
        tv_facturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        getRecords();
         System.out.println("name solucion->"+AbacoApp.nombresolucion);
        return stack;
  }
  
public static void   getRecords() throws ParseException
{
         //colocamos los datos
         try{
         AbacoApp.li_facturas=AbacoApp.facturaControllerClient.getRecords(buscar.getText());
         ol_generic.clear();
         System.out.println("size->"+ AbacoApp.li_facturas.size());
         ol_generic.addAll(AbacoApp.li_facturas.toArray());
         tv_facturas.setItems(ol_generic);
      } catch(Exception e)
       {
           e.printStackTrace();
       }
    }
 private void showfactura()  
  {
    
        System.out.println("Editando factura3");
     String appClassfactura="abaco.soluciones.procesosadministrativos.FFactura";
     Class clzfactura=null;
      try{
      clzfactura = Class.forName(appClassfactura);
    Object  appfactura = clzfactura.newInstance();
  
     Parent parentfactura=null;
      parentfactura = (Parent) clzfactura.getMethod("createContent").invoke(appfactura);
     Scene scenefactura=null;
       scenefactura=new Scene(parentfactura, Color.TRANSPARENT);
             stagefactura.setTitle("Factura Cliente");
        if (stagefactura.isShowing())
       {
           stagefactura.close();
       }      
        
        
//set scene to stage
                stagefactura.sizeToScene();
    
                stagefactura.centerOnScreen();
                stagefactura.setScene(scenefactura);
                 
            
                stagefactura.show();
                   System.out.println("Editando factura4");
      }catch(Exception e)
      {
         e.printStackTrace();
      }
  } 
  private void showAbonosFactura() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  
    appClassAbonos="abaco.soluciones.procesosadministrativos.AdminAbonosFacturaCliente";
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
private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
   {
       if ((tv_facturas.getSelectionModel())!=null)
       {
           AbacoApp.s_factura=(Factura)tv_facturas.getSelectionModel().getSelectedItem();
          
         
       }
   }

 public void setItemMenu()
 {
     
     contextMenu.getItems().clear();
     int opcion=AbacoApp.s_factura.getCredito()==null?1:AbacoApp.s_factura.getCredito()==true?0:1;
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
    
     
}
