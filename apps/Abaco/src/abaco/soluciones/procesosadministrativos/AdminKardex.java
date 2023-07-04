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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TitledPane;
import jfxtras.styles.jmetro8.JMetro;
import model.Kardex;
import org.aerofx.AeroFX;




/**
 *
 * @author adminlinux
 */
public class AdminKardex  extends Application{

    private GridPane  gp_generico;
    private static TableView tv_generic;
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
    private  Stage stage= new Stage(StageStyle.DECORATED);
    Scene scene =null;
    private ContextMenu contextMenu;
    private MenuItem  itemnuevo;
    private MenuItem  itemeditar;
    private MenuItem  itemdelete;
    private ImageView img;
    private TitledPane tp_generic;
    private static DatePicker datefrom;
    private static DatePicker dateto;
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException  {
    tp_generic=new TitledPane();
    tp_generic.setText("Administrar Kardex");
    tp_generic.setCollapsible(false);
        stage.setTitle("Kardex");
        img=new ImageView ("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo=new MenuItem("Nuevo",img);
        itemnuevo.setOnAction(e->{
          try{
            AbacoApp.s_kardex=null; 
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
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(AdminKardex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AdminKardex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(AdminKardex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(AdminKardex.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            delete();
        } catch (ParseException ex) {
            Logger.getLogger(AdminKardex.class.getName()).log(Level.SEVERE, null, ex);
        }
       });
    contextMenu=new ContextMenu(itemnuevo,itemeditar,itemdelete);
    stack=new StackPane();
    appClass="abaco.soluciones.informacionadministrativa.FProveedor";
    clz = Class.forName(appClass);
    app = clz.newInstance();
    la_buscar=new Label("Buscar: ");
    la_buscar.setMinWidth(100);
    buscar=new TextField();
    buscar.setOnKeyReleased(e->{
        try {
            getRecords();
        } catch (ParseException ex) {
            Logger.getLogger(AdminKardex.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
    
    buscar.setMinWidth(300);
    datefrom=new DatePicker(LocalDate.now());
    datefrom.setOnAction(e->{
        
        try {
            getRecords();
        } catch (ParseException ex) {
            Logger.getLogger(AdminKardex.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
     
    dateto=new DatePicker(LocalDate.now());
    dateto.setOnAction(e->{
        
        try {
            getRecords();
        } catch (ParseException ex) {
            Logger.getLogger(AdminKardex.class.getName()).log(Level.SEVERE, null, ex);
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
                 AbacoApp.s_kardex=null; 
               show();
             
           } catch (Exception ex) {
               ex.printStackTrace();
           }
          });
        
    gp_generico=new GridPane();
    tv_generic=new TableView(); 
    
    
        TableColumn fecha = new TableColumn();
        fecha.setMinWidth(100);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(abaco.util.UtilDate.formatodiamesyear(kardex.getValue().getFecha()));
                            
            }
        });
     TableColumn codigo = new TableColumn();
        codigo.setMinWidth(100);
        codigo.setText("Codigo");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(kardex.getValue().getProducto().getCodigobarras());
                            
            }
        });
       
       TableColumn nombre = new TableColumn();
        nombre.setMinWidth(150);
        nombre.setText("Producto");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(kardex.getValue().getProducto().getNombre());
                            
            }
        });
    
        TableColumn descripcion = new TableColumn();
        descripcion.setMinWidth(150);
        descripcion.setText("Descripción");
        descripcion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(String.valueOf(kardex.getValue().getDesc_kar()));
                            
            }
        });
        TableColumn cantidadentrada = new TableColumn();
        cantidadentrada.setMinWidth(100);
        cantidadentrada.setText("C/Entrada");
        cantidadentrada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(String.valueOf(kardex.getValue().getCantidad_entrada()));
                            
            }
        });
        cantidadentrada.setCellFactory(column -> {
    return new TableCell<Kardex, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

               if(item == null || empty) 
               {
                   setText("");
                    setStyle("");
               }
            else
            { if(Double.valueOf(item)>0)
             {
               setTextFill(Color.WHITE);
               setStyle("-fx-background-color: green;-fx-text-fill: white;");
               setText(item);     
              } 
              else
            {
                if(Double.valueOf(item)<0)
             {
               setTextFill(Color.WHITE);
               setStyle("-fx-background-color: red;-fx-text-fill: white;");
               setText(item);     
              } 
                else
                {
               
                     setText("0.0");
                    setStyle("");
               
                }
               
            }
               }
            }
        };
    }
);
        TableColumn valorentrada = new TableColumn();
        valorentrada.setMinWidth(90);
        valorentrada.setText("V/Entrada");
        valorentrada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(kardex.getValue().getValor_entrada().toString());
                            
            }
        });
    valorentrada.setCellFactory(column -> {
    return new TableCell<Kardex, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

               if(item == null || empty) 
               {
                     setText("");
                    setStyle("");
               }
            else
            { if(Double.valueOf(item)>0)
             {
               setTextFill(Color.WHITE);
               setStyle("-fx-background-color: green;-fx-text-fill: white;");
               setText(item);     
              } 
              else
            {
             if(Double.valueOf(item)<0)
             {
               setTextFill(Color.WHITE);
               setStyle("-fx-background-color: red;-fx-text-fill: white;");
               setText(item);     
              } 
              else
                {
               
                     setText("0.0");
                    setStyle("");
               
                }
            }
               }
            }
        };
    }
);
        TableColumn cantidadsalida = new TableColumn();
        cantidadsalida.setMinWidth(90);
        cantidadsalida.setText("C/Salida");
        cantidadsalida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(String.valueOf(kardex.getValue().getCantidad_salida()));
                            
            }
        });
        cantidadsalida.setCellFactory(column -> {
    return new TableCell<Kardex, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

               if(item == null || empty) 
               {
                    setText("");
                    setStyle("");
               }
            else
            { if(Double.valueOf(item)>0)
             {
               setTextFill(Color.WHITE);
               setStyle("-fx-background-color: red;-fx-text-fill: white;");
               setText(item);     
              } 
              else
            {
                if(Double.valueOf(item)<0)
             {
               setTextFill(Color.WHITE);
               setStyle("-fx-background-color: green;-fx-text-fill: white;");
               setText(item);     
              }
                 else
                {
              
                     setText("0.0");
                    setStyle("");
               
                }
            }
               }
            }
        };
    }
);
        TableColumn valorsalida = new TableColumn();
        valorsalida.setMinWidth(90);
        valorsalida.setText("V/Salida");
        valorsalida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(kardex.getValue().getValor_salida().toString());
                            
            }
        });
        valorsalida.setCellFactory(column -> {
    return new TableCell<Kardex, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

               if(item == null || empty) 
               {
                    setText("");
                    setStyle("");
               }
            else
            { if(Double.valueOf(item)>0)
             {
               setTextFill(Color.WHITE);
               setStyle("-fx-background-color: red;-fx-text-fill: white;");
               setText(item);     
              } 
              else
            {
                if(Double.valueOf(item)<0)
             {
               setTextFill(Color.WHITE);
               setStyle("-fx-background-color: green;-fx-text-fill: white;");
               setText(item);     
              }
                 else
                {
              
                     setText("0.0");
                    setStyle("");
               
                }
            }
               }
            }
        };
    }
);
        TableColumn valorunitario = new TableColumn();
        valorunitario.setMinWidth(90);
        valorunitario.setText("Valor/U");
        valorunitario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(kardex.getValue().getValor_unidad().toString());
                            
            }
        });
        TableColumn valorsaldo = new TableColumn();
        valorsaldo.setMinWidth(90);
        valorsaldo.setText("V/Saldo");
        valorsaldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(kardex.getValue().getValor_saldo().setScale(0, RoundingMode.HALF_UP).toString());
                            
            }
        });
        TableColumn cantidadsaldo = new TableColumn();
        cantidadsaldo.setMinWidth(90);
        cantidadsaldo.setText("C/Saldo");
        cantidadsaldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(String.valueOf(kardex.getValue().getCantidad_saldo()));
                            
            }
        });
        tv_generic.getColumns().addAll(fecha,codigo,nombre,cantidadentrada,valorentrada,cantidadsalida,valorsalida,cantidadsaldo,valorsaldo,valorunitario);
        //ta_librodiario.setMinWidth(650);
       
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setMinHeight(577);
        //tv_generic.setContextMenu(contextMenu);
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
        gp_generico.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        gp_generico.getStyleClass().add("background");
        Label desde =new Label("Desde:");
        Label hasta =new Label("a:");
        gp_generico.addRow(0,la_buscar,buscar,desde,datefrom,hasta,dateto);
        gp_generico.add(tv_generic, 0, 3,8,1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        tp_generic.setMinWidth(1000);
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
        getRecords();
        return stack;
  }
  
public static void   getRecords() throws ParseException
{
         //colocamos los datos
         try{
         List<Kardex> li_kardex=AbacoApp.kardexControllerClient.getRecords(buscar.getText(),datefrom.getValue().toString(),dateto.getValue().toString());
       
       ol_generic.clear();
       
        ol_generic.addAll(li_kardex.toArray());
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
           AbacoApp.s_kardex=(Kardex)tv_generic.getSelectionModel().getSelectedItem();
        
       }
}

    
 private void delete() throws ParseException
 {
     AbacoApp.kardexControllerClient.delete();
     getRecords();
 }
     @Override
    public void start(Stage primaryStage) throws Exception {
        
        
    }
        
        
    
}
