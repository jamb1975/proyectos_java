/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.informacionadministrativa;

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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.TitledPane;
import jfxtras.styles.jmetro8.JMetro;
import model.Producto;
import org.aerofx.AeroFX;



/**
 *
 * @author adminlinux
 */
public class AdminServicios  extends Application{

    private GridPane  gp_generico;
    private static TableView tv_Productos=new TableView();
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_Productos=FXCollections.observableArrayList();;
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
    private MenuItem  itemdelete;
    private ImageView img;
    private TitledPane tp_generic;
    private static List<Producto> li_producto=new ArrayList<>();
    private static List<Producto> li_productoTemp=new ArrayList<>();
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException  {
     tp_generic=new TitledPane();
     tp_generic.setText("Administrar Servicios");
     tp_generic.setCollapsible(false);
        stage.setTitle("Servicio");
        img=new ImageView ("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo=new MenuItem("Nuevo",img);
        itemnuevo.setOnAction(e->{
          try{
             AbacoApp.s_producto=null ; 
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
               
           }
       });
       img=null;
        img=new ImageView ("/images/delete.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
       itemdelete=new MenuItem("Eliminar",img);
        itemdelete.setOnAction(e->{
          
            try {
                checkregistroexistente();
                delete();
            } catch (ParseException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(AdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
       });
    contextMenu=new ContextMenu(itemnuevo,itemeditar,itemdelete);
    stack=new StackPane();
      la_buscar=new Label("Buscar: ");
    la_buscar.setMinWidth(100);
    buscar=new TextField();
    buscar.setMinWidth(600);
    buscar.textProperty().addListener((observable, oldValue, newValue) -> {
        buscarproducto();
            
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
                  AbacoApp.s_producto=null;
                  show();
             
           } catch (Exception ex) {
               ex.printStackTrace();
           }
          });
        
    gp_generico=new GridPane();
    tv_Productos=new TableView(); 
    
    
     
   
        TableColumn codigobarras = new TableColumn();
        codigobarras.setMinWidth(200);
        codigobarras.setText("Código de Barras");
        codigobarras.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               
                    return new SimpleStringProperty(Producto.getValue().getCodigobarras());
                            
            }
        });
       
       TableColumn nombre = new TableColumn();
        nombre.setMinWidth(250);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               
                    return new SimpleStringProperty(Producto.getValue().getNombre());
                            
            }
        });
        TableColumn precio = new TableColumn();
        precio.setMinWidth(200);
        precio.setText("Precio");
        precio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               
                    return new SimpleStringProperty(Producto.getValue().getPrecio().toString());
                            
            }
        });
        TableColumn proveedor = new TableColumn();
        proveedor.setMinWidth(200);
        proveedor.setText("Proveedor");
        proveedor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               try{
                    return new SimpleStringProperty(Producto.getValue().getProveedores().getNombre());
               }catch(Exception e)
               {
                 return new SimpleStringProperty("NO");  
               }
                            
            }
        });
        TableColumn proveedorcelular = new TableColumn();
        proveedorcelular.setMinWidth(130);
        proveedorcelular.setText("Teléfono");
        proveedorcelular.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               try{
                    return new SimpleStringProperty(Producto.getValue().getProveedores().getCelular());
               }catch(Exception e)
               {
                 return new SimpleStringProperty("NO");  
               }
                            
            }
        });
        tv_Productos.getColumns().addAll(codigobarras,nombre,precio,proveedor,proveedorcelular);
        //ta_librodiario.setMinWidth(650);
       
        tv_Productos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_Productos.setMinHeight(577);
        tv_Productos.setContextMenu(contextMenu);
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
        gp_generico.addRow(0,la_buscar,buscar,bu_buscar,bu_nuevo);
       
        gp_generico.add(tv_Productos, 0, 3,7,1);
      
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        gp_generico.setMinSize(1000, 610);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic,Gp_Message);
        getRecords();
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
          bu_buscar.setStyle("-fx-background-color:#007fff");
          bu_nuevo.setStyle("-fx-background-color:#007fff");
        }
        return stack;
  }
  
public static void   getRecords() throws ParseException
{
         //colocamos los datos
         try{
            
        
           li_productoTemp=AbacoApp.productoControllerClient.getRecordsServicios(null);
           li_producto.clear();
           li_producto.addAll(li_productoTemp);
       ol_Productos.clear();
       
        ol_Productos.addAll(li_producto.toArray());
        tv_Productos.setItems(ol_Productos);
      } catch(Exception e)
       {
           e.printStackTrace();
       }
    }
 private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {
      appClass="abaco.soluciones.informacionadministrativa.FServicios";
      clz=null;
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
       if ((tv_Productos.getSelectionModel())!=null)
       {
           AbacoApp.s_producto=(Producto)tv_Productos.getSelectionModel().getSelectedItem();
         
       }
   }

    
   
     @Override
    public void start(Stage primaryStage) throws Exception {
        
        
    }
       public void delete() throws ParseException
    {
        AbacoApp.productoControllerClient.delete();
        getRecords();
   } 
   private static void buscarproducto()
   {
                try{
            
          li_producto=li_productoTemp.stream().filter(line ->line.getCodigobarras().trim().toUpperCase().contains(buscar.getText().trim().toUpperCase()) || line.getNombre().toUpperCase().contains(buscar.getText().toUpperCase()))	//filters the line, equals to "mkyong"
	                      .collect(Collectors.toList());
        
       
       ol_Productos.clear();
       
        ol_Productos.addAll(li_producto.toArray());
        tv_Productos.setItems(ol_Productos);
      } catch(Exception e)
       {
           e.printStackTrace();
       }

   }
    
}
