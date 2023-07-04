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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TitledPane;
import jfxtras.styles.jmetro8.JMetro;
import model.ConComprobanteEgreso;
import model.ConComprobanteIngreso;
import org.aerofx.AeroFX;



/**
 *
 * @author adminlinux
 */
public class AdminComprobanteEgreso  extends Application{

    private GridPane  gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_generic=FXCollections.observableArrayList();;
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
    private static DatePicker dp_from;
    private static DatePicker dp_to;
    Scene scene =null;
    private ContextMenu contextMenu;
    private MenuItem  itemnuevo;
    private MenuItem  itemeditar;
    private MenuItem  itemdelete;
    private ImageView img;
    private TitledPane tp_generic;
    private Button bu_nuevo;
    
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException  {
    tp_generic=new TitledPane();
    tp_generic.setText("Administrar Comprobantes de Egreso");
    dp_from=new DatePicker(LocalDate.now());
    dp_from.setPromptText(" Fecha Desde");
    dp_to=new DatePicker(LocalDate.now());
    dp_to.setPromptText(" Fecha Hasta");
    stage.setTitle("Comprobantes de Egreso");
    img=new ImageView ("/images/new2.png");
    img.setFitHeight(20);
    img.setFitWidth(20);
    itemnuevo=new MenuItem("Nuevo",img);
    itemnuevo.setOnAction(e->{
          try{
            AbacoApp.s_abono=null; 
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
            
        } catch (Exception ex) {
            
        }
       });
    contextMenu=new ContextMenu(itemnuevo,itemeditar,itemdelete);
    stack=new StackPane();
    appClass="abaco.soluciones.procesosadministrativos.FAbonoFacturaProveedor";
    clz = Class.forName(appClass);
    app = clz.newInstance();
    la_buscar=new Label("Buscar: ");
    la_buscar.setMinWidth(100);
    buscar=new TextField();
    buscar.setOnKeyReleased(e->{
        try {
            getRecords();
        } catch (ParseException ex) {
            
        }
    });
    
    buscar.setMinWidth(700);
    
     ImageView imageView;
       
         imageView=new ImageView("/images/find.png");
         imageView.setFitHeight(20);
         imageView.setFitWidth(20);
           
          
            imageView=new ImageView("/images/new2.png");
         imageView.setFitHeight(20);
         imageView.setFitWidth(20);
           
           bu_nuevo=new Button("",imageView);
           bu_nuevo.setDisable(false);
           bu_nuevo.setOnAction(e->
          {
           try {
                 AbacoApp.conComprobanteEgreso=null; 
               show();
             
           } catch (Exception ex) {
               ex.printStackTrace();
           }
          });
        
    gp_generico=new GridPane();
    tv_generic=new TableView(); 
    
    
      TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(150);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConComprobanteEgreso, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConComprobanteEgreso, String> abono) {
               try
               {
                    return new SimpleStringProperty(abono.getValue().getFacturaproveedores().getNo_factura().toString());
               }
               catch(Exception e)
               {
                         return new SimpleStringProperty("N/A");
             
               }
            }
        });
         TableColumn norecibo = new TableColumn();
         norecibo.setMinWidth(130);
         norecibo.setText("N° Recibo");
         norecibo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConComprobanteEgreso, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConComprobanteEgreso, String> abono) {
               
                    return new SimpleStringProperty(abono.getValue().getNocomprobantecerosizquierda());
                            
            }
        });
     TableColumn fecha = new TableColumn();
        fecha.setMinWidth(150);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConComprobanteEgreso, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConComprobanteEgreso, String> abono) {
               
                    return new SimpleStringProperty(abaco.util.UtilDate.formatodiamesyear(abono.getValue().getFechaelaboracion()));
                            
            }
        });
        TableColumn observ = new TableColumn();
        observ.setMinWidth(250);
        observ.setText("Observaciones");
        observ.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConComprobanteEgreso, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConComprobanteEgreso, String> abono) {
               
                    return new SimpleStringProperty(abono.getValue().getConcepto());
                            
            }
        });
       
        TableColumn proveedor = new TableColumn();
        proveedor.setMinWidth(250);
        proveedor.setText("Proveedor");
        proveedor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConComprobanteEgreso, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConComprobanteEgreso, String> abono) {
                 try
                 {
                    return new SimpleStringProperty(abono.getValue().getFacturaproveedores().getProveedores().getNombre());
                 }  
                 catch(Exception e)
                 {
                       return new SimpleStringProperty("N/A");
                
                 }
            }
        });
        TableColumn valor = new TableColumn();
        valor.setMinWidth(150);
        valor.setText("Valor");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConComprobanteEgreso, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ConComprobanteEgreso, String> abono) {
               
                    return new SimpleStringProperty(abono.getValue().getValor().toString());
                            
            }
        });
      
        tv_generic.getColumns().addAll(norecibo,nofactura,fecha,proveedor,observ,valor);
        //ta_librodiario.setMinWidth(650);
       
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setMinHeight(577);
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
      //  gp_generico.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        //gp_generico.getStyleClass().add("background");
        gp_generico.setMinWidth(1000);
        gp_generico.addRow(0,dp_from,dp_to,bu_nuevo);
        gp_generico.add(tv_generic, 0, 3,3,1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        //tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(gp_generico,Gp_Message);
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
        getRecords();
        return stack;
  }
  
public static void   getRecords() throws ParseException
{
         //colocamos los datos
         try{
       
       ol_generic.clear();
       ol_generic.addAll(AbacoApp.conComprobanteEgresoControllerClient.getRecords(dp_from.getValue().toString(),dp_to.getValue().toString(),buscar.getText()).toArray());
       tv_generic.setItems(ol_generic);
      } catch(Exception e)
       {
           e.printStackTrace();
       }
    }
 private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
  {
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
   
private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
{
       if ((tv_generic.getSelectionModel())!=null)
       {
           AbacoApp.conComprobanteEgreso=(ConComprobanteEgreso)tv_generic.getSelectionModel().getSelectedItem();
        
       }
}

    
 
     @Override
    public void start(Stage primaryStage) throws Exception {
        
        
    }
        
        
    
}
