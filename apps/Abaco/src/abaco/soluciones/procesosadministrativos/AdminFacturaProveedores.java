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
import model.FacturaProveedores;
import org.aerofx.AeroFX;




/**
 *
 * @author adminlinux
 */
public class AdminFacturaProveedores  extends Application{

    private GridPane  gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private Label la_datefrom;
    private Label la_dateto;
    private static TextField buscar;
    private static ObservableList ol_generic=FXCollections.observableArrayList();;
    private Button bu_buscar;
    private Button bu_nuevo;
    private Button bu_configuraciones;
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
    private MenuItem  itemeliminar;
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
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException  {
     stack=new StackPane();
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
            AbacoApp.s_facturaproveedores=null;  
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
        img=new ImageView ("/images/delete.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemeliminar=new MenuItem("Eliminar",img);
        itemeliminar.setOnAction(e->{
         
           try{
         checkregistroexistente();
          AbacoApp.facturaProveedoresControllerClient.delete();
          getRecords();
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
    contextMenu=new ContextMenu(itemnuevo,itemeditar,itemeliminar);
    la_buscar=new Label("Buscar: ");
    la_buscar.setMinWidth(60);
    buscar=new TextField();
    buscar.setMinWidth(100);
    buscar.setOnKeyReleased(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(AdminFacturaProveedores.class.getName()).log(Level.SEVERE, null, ex);
         }
    });
    la_datefrom=new Label("Desde:");
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
    la_dateto=new Label("Hasta:");
    la_datefrom.setMinWidth(60);
    la_dateto.setMinWidth(60);
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
    cb_formaspago.getSelectionModel().getSelectedIndex();
    cb_formaspago.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
     Label la_formaspago=new Label("Formas de Pago:");
        la_formaspago.setMinWidth(100);
        cb_formaspago.setMinWidth(100);
      
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
               AbacoApp.s_facturaproveedores=null;  
               show();
             
           } catch (Exception ex) {
               ex.printStackTrace();
           }
          });
         imageView=new ImageView("/images/configuraciones.png");
         imageView.setFitHeight(20);
         imageView.setFitWidth(20);
           
           bu_configuraciones=new Button("",imageView);
           bu_configuraciones.setDisable(false);
    gp_generico=new GridPane();
    tv_generic=new TableView(); 
    
    tv_generic.setOnContextMenuRequested(e->{
         try {
             checkregistroexistente();
         } catch (Exception ex) {
             Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
         } 
         setItemMenu();
    });
     stage.setOnHiding(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(AdminFacturaProveedores.class.getName()).log(Level.SEVERE, null, ex);
         }
     });
     stageAbonos.setOnHiding(e->{
         try {
             getRecords();
         } catch (ParseException ex) {
             Logger.getLogger(AdminFacturaProveedores.class.getName()).log(Level.SEVERE, null, ex);
         }
     });
     TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(140);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getNo_factura()!=null?factura.getValue().getNo_factura().toString():"N/A");
                            
            }
        });
       
       TableColumn noident = new TableColumn();
        noident.setMinWidth(120);
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
        nombre.setMinWidth(250);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
               
                  try{
                    return new SimpleStringProperty(factura.getValue().getProveedores().getNombre());
               }catch(Exception e)
               { return new SimpleStringProperty("NO");  
               }
                            
            }
        });
         TableColumn fecha = new TableColumn();
        fecha.setMinWidth(120);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
               
                    return new SimpleStringProperty(abaco.util.UtilDate.formatodiamesyear(factura.getValue().getFacturaDate()));
                            
            }
        });
         TableColumn valor = new TableColumn();
        valor.setMinWidth(120);
        valor.setText("valor");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());
                            
            }
        });
        
        TableColumn abonos = new TableColumn();
        abonos.setMinWidth(120);
        abonos.setText("Abonos");
        abonos.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
              
                    return new SimpleStringProperty(factura.getValue().getValor_abonos().toString());
                    
                            
            }
        });
        TableColumn saldo = new TableColumn();
        saldo.setMinWidth(120);
        saldo.setText("Saldo");
        saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaProveedores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaProveedores, String> factura) {
              
                    return new SimpleStringProperty((factura.getValue().getTotalAmount().toBigInteger().subtract(factura.getValue().getValor_abonos().toBigInteger())).toString());
                    
                            
            }
        });
        tv_generic.getColumns().addAll(fecha,nofactura,noident,nombre,valor,abonos,saldo);
        //ta_librodiario.setMinWidth(650);
       
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setMinHeight(550);
        tv_generic.setContextMenu(contextMenu);
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
       // gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0,la_buscar,buscar,la_formaspago,cb_formaspago,la_datefrom,datefrom,la_dateto,dateto,bu_nuevo);
        gp_generico.add(tv_generic, 0, 3,9,1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        gp_generico.minWidth(1000);
        gp_generico.maxHeight(610);
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
        stack.minWidth(1000);
        getRecords();
        return stack;
  }
  
public static void   getRecords() throws ParseException
{
         //colocamos los datos
         try{
         AbacoApp.li_facturasproveedores=AbacoApp.facturaProveedoresControllerClient.getRecords(datefrom.getValue().toString(),dateto.getValue().toString(),buscar.getText(),cb_formaspago.getSelectionModel().getSelectedIndex()>=0?cb_formaspago.getSelectionModel().getSelectedIndex():0);
         ol_generic.clear();
         ol_generic.addAll(AbacoApp.li_facturasproveedores.toArray());
         tv_generic.setItems(ol_generic);
      } catch(Exception e)
       {
           e.printStackTrace();
       }
    }
 private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {
      
    appClass="abaco.soluciones.procesosadministrativos.FFacturaProveedor";
    clz = Class.forName(appClass);
    app = clz.newInstance();
   
      parent=null;
      parent = (Parent) clz.getMethod("createContent").invoke(app);
      scene=null;
       scene=new Scene(parent, Color.TRANSPARENT);
       
        if (stage.isShowing())
       {
           stage.close();
       }      
        
        
//set scene to stage
                stage.sizeToScene();
                
              
                //center stage on screen
                stage.centerOnScreen();
                stage.setScene(scene);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stage.show();
  }  
   
private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
{
       if ((tv_generic.getSelectionModel())!=null)
       {
           AbacoApp.s_facturaproveedores=(FacturaProveedores)tv_generic.getSelectionModel().getSelectedItem();
           
       }
}

   private void showAbonosFactura() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  appClassAbonos="abaco.soluciones.procesosadministrativos.AdminAbonosFacturaProveedor";
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
     int opcion=AbacoApp.s_facturaproveedores.getCredito()==null?1:AbacoApp.s_facturaproveedores.getCredito()==true?0:1;
     switch(opcion)
     {
         case 0: contextMenu.getItems().addAll(itemnuevo,itemeditar,itemeliminar,itemabonos);
         break;
         case 1: contextMenu.getItems().addAll(itemnuevo,itemeditar,itemeliminar);
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
