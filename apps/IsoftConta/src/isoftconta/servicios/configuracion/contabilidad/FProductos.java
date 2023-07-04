/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios.configuracion.contabilidad;



import isoftconta.ContabilidadFXServicios;
import isoftconta.servicios.ProductoController;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import modelo.EntidadesStatic;
import modelo.Producto;




/**
 *
 * @author adminlinux
 */
public class FProductos  extends ContabilidadFXServicios{

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
      @Override
    public String getServicioNombre() {
        return "Productos";
    }
    @Override
    public Node getPanel(Stage stage){
     tp_generic=new TitledPane();
     tp_generic.setText("Administrar Productos");
     tp_generic.setCollapsible(false);
        stage.setTitle("Producto");
        img=new ImageView ("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo=new MenuItem("Nuevo",img);
        itemnuevo.setOnAction(e->{
          try{
             EntidadesStatic.es_producto=null ; 
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
                Logger.getLogger(FProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(FProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(FProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(FProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FProductos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(FProductos.class.getName()).log(Level.SEVERE, null, ex);
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
              EntidadesStatic.es_producto=null;
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
        nombre.setMinWidth(200);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> producto) {
               
                    return new SimpleStringProperty(producto.getValue().getNombre());
                            
            }
        });
        TableColumn precio = new TableColumn();
        precio.setMinWidth(150);
        precio.setText("Precio");
        precio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> Producto) {
               
                    return new SimpleStringProperty(Producto.getValue().getPrecio().toString());
                            
            }
        });
       
        tv_Productos.getColumns().addAll(codigobarras,nombre,precio);
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
        //gp_generico.getStylesheets().add("/abaco/SofackarStylesCommon.css");
      //  gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0,la_buscar,buscar,bu_buscar,bu_nuevo);
       
        gp_generico.add(tv_Productos, 0, 3,7,1);
      
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        gp_generico.setMinSize(1345, 640);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic,Gp_Message);
        
        try {
            getRecords();
        } catch (ParseException ex) {
            Logger.getLogger(FProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stack;
  }
  
public static void   getRecords() throws ParseException
{
         //colocamos los datos
         try{
            
          li_producto=EntidadesStatic.li_productos.stream().filter(line ->line.getCodigobarras().trim().toUpperCase().contains(buscar.getText().trim().toUpperCase()) || line.getNombre().toUpperCase().contains(buscar.getText().toUpperCase()))	//filters the line, equals to "mkyong"
	                      .collect(Collectors.toList());
        
         EntidadesStatic.li_productos=ProductoController.getRecords(null);
       
       ol_Productos.clear();
       
        ol_Productos.addAll(EntidadesStatic.li_productos.toArray());
        tv_Productos.setItems(ol_Productos);
      } catch(Exception e)
       {
           e.printStackTrace();
       }
    }
 private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {
      appClass="abaco.soluciones.informacionadministrativa.FProducto";
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
           EntidadesStatic.es_producto=(Producto)tv_Productos.getSelectionModel().getSelectedItem();
         
       }
   }

    
   
     @Override
    public void start(Stage primaryStage) throws Exception {
        
        
    }
       public void delete() throws ParseException
    {
        ProductoController.servicio_eliminar();
        getRecords();
   } 
   private static void buscarproducto()
   {
                try{
            
          li_producto=EntidadesStatic.li_productos.stream().filter(line ->line.getCodigobarras().trim().toUpperCase().contains(buscar.getText().trim().toUpperCase()) || line.getNombre().toUpperCase().contains(buscar.getText().toUpperCase()))	//filters the line, equals to "mkyong"
	                      .collect(Collectors.toList());
        
       
       ol_Productos.clear();
       
        ol_Productos.addAll(li_producto.toArray());
        tv_Productos.setItems(ol_Productos);
      } catch(Exception e)
       {
           
       }

   }
    
}
