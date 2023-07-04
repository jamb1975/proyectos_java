/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.informacionadministrativa;

import abaco.controlador.ConPucControllerClient;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.ConPuc;



/**
 *
 * @author adminlinux
 */
public class AdminPuc  extends Application{

    private GridPane  gpconpuc;
    private Label l_buscar;
    private TextField buscar;
    private ObservableList ol_conpuc=FXCollections.observableArrayList();;
    private ConPucControllerClient conPucControllerClient;
    private String appPathConPuc;
    private Class  clzConPuc;
    private Object appClassConPuc ; 
    private Stage  stageConPuc = new Stage(StageStyle.DECORATED);
    private Scene scene =null;
    private Parent P_GenConPuc ;
    private TreeTableView tv_conpuc;
    private static ConPuc conPuc;

    private TreeTableViewSelectionModel<ConPuc> tsm ;
    TreeItem<ConPuc> rootNode;
    private Button nuevo;
    private ContextMenu contextMenu;
    private MenuItem  itemnuevo;
    private MenuItem  itemeditar; 
    private TitledPane tp_generic;
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException  {
        tp_generic=new TitledPane();
        tp_generic.setText("Administrador Plan Único de Cuentas (PUC)");
        tp_generic.setCollapsible(false);
        ImageView imageView;
        imageView=new ImageView ("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        itemnuevo=new MenuItem("Nuevo",imageView);
        itemnuevo.setOnAction(e->{
           conPuc=null;
           conPuc=new ConPuc();
           try{
             show();
           }catch(Exception x)
           {
               
           }
       });
       imageView=null;
        imageView=new ImageView ("/images/editar.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
       itemeditar=new MenuItem("Editar",imageView);
        itemeditar.setOnAction(e->{
           FPuc.setEstadoeditar(true);
           try{
         checkregistroexistente();
         
           }catch(Exception x)
           {
               x.printStackTrace();
           }
       });
           contextMenu=new ContextMenu(itemnuevo,itemeditar);
           conPucControllerClient=new ConPucControllerClient();
           stageConPuc.setTitle("Plan Unico de Cuentas");
           imageView=new ImageView("/images/new2.png");
           imageView.setFitHeight(20);
           imageView.setFitWidth(20);
            nuevo=new Button("",imageView);
            //nuevo.setMaxWidth(130); 
            nuevo.setDisable(false);
            nuevo.setOnAction(e->
          {
           try {
               conPuc=null;
               conPucControllerClient.setIdPadre();
               show();
           } catch (Exception ex) {
              
           }
          });
   
            //**********Ventana Personas
           appPathConPuc="abaco.soluciones.informacionadministrativa.FPuc";
           clzConPuc = Class.forName(appPathConPuc);
           appClassConPuc=clzConPuc.newInstance();
            //**********Ventana Factura
          
      l_buscar=new Label("Buscar:");
      l_buscar.setMinWidth(100);
      buscar=new TextField();
      buscar.setOnKeyPressed(e->{try 
      {
     
          getPuc();
      } catch(Exception e3)
      {
      }
      });
      
        gpconpuc=new GridPane();
    
       
        getPuc();
        gpconpuc.getStylesheets().add("/sihic/SofackarStylesCommon.css");
        gpconpuc.getStyleClass().add("background");
        gpconpuc.add(l_buscar, 0, 0);
        gpconpuc.add(buscar, 1, 0);
        gpconpuc.add(nuevo, 2, 0);
        gpconpuc.setVgap(5);
        gpconpuc.setHgap(5);
        gpconpuc.setMinWidth(1000);
        tp_generic.setMinWidth(1000);
        tp_generic.setContent(gpconpuc);
        StackPane stack=new StackPane();
        gpconpuc.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().add(tp_generic);
        return stack;
  }
  
   private void  getPuc() throws ParseException
    {
         //colocamos los datos
     
      init_tretable();
      rootNode=null;
       conPuc=null;
        conPuc=new ConPuc();
       conPucControllerClient=null;
       
       conPucControllerClient=new ConPucControllerClient();
       try{
         conPuc=conPucControllerClient.getConPuc();
         
       }catch(Exception e)
       {
           e.printStackTrace();
       }
       if(conPuc!=null)
       {
        rootNode= new TreeItem<>(conPuc);
        createTree(conPuc,rootNode);
        tv_conpuc.setRoot(rootNode);
        tv_conpuc.setMinHeight(577);
       
       }
      try{
         tv_conpuc.getTreeItem(tsm.getSelectedIndex()).setExpanded(true);
      }catch(Exception e)
      {
          
      }
       
    }
   private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
   {
       if ((tsm.getSelectedItem())!=null)
       {
           conPuc=(ConPuc)tsm.getSelectedItem().getValue();
          show();
       }
   }
   
  
    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
  {
      P_GenConPuc=null;
      P_GenConPuc = (Parent) clzConPuc.getMethod("createContent").invoke(appClassConPuc);
      scene=null;
       scene=new Scene(P_GenConPuc, Color.TRANSPARENT);
       
        if (stageConPuc.isShowing())
       {
           stageConPuc.close();
       }      
        
        
//set scene to stage
                stageConPuc.sizeToScene();
                
              
                //center stage on screen
                stageConPuc.centerOnScreen();
                stageConPuc.setScene(scene);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stageConPuc.show();
  }
     
 // A change listener to track the change in selected index
private void createTree(ConPuc conpuc, TreeItem<ConPuc> root)
{
    
    
    for(ConPuc cp: conPucControllerClient.getConPucHijos(conpuc))
    {
         TreeItem<ConPuc> node = new TreeItem<>(cp);
          root.getChildren().add(node);
         if(conPucControllerClient.getConPucHijos(cp).size()>0)
         {
             createTree(cp,node);
         }
      
        
         
        
    }
   
   
}

    public static ConPuc getConPuc() {
        return conPuc;
    }

    public static void setConPuc(ConPuc conPuc) {
        AdminPuc.conPuc = conPuc;
    }
     
     @Override
    public void start(Stage primaryStage) throws Exception {
        
        
    }
        
 private  void init_tretable()
 {
     gpconpuc.getChildren().remove(tv_conpuc);
      tv_conpuc=null;
       tsm=null;
     tv_conpuc=new TreeTableView();
     tv_conpuc.setMinHeight(577);
     tsm = tv_conpuc.getSelectionModel();
     stageConPuc.setOnHidden(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN ) {
                    try {
                        getPuc();
                    } catch (ParseException ex) {
                        
                    }
                }
                }
        });
     TreeTableColumn codigo = new TreeTableColumn();
        codigo.setMinWidth(150);
        codigo.setText("Codigo");
        codigo.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ConPuc, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ConPuc, String> conpuc) {
               
                    return new SimpleStringProperty((conpuc.getValue().valueProperty().getValue().getCodigo()));
                            
            }
        });
        TreeTableColumn nombre = new TreeTableColumn();
        nombre.setMinWidth(250);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ConPuc, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ConPuc, String> conpuc) {
               
                    return new SimpleStringProperty((conpuc.getValue().valueProperty().getValue().getNombre()));
                            
            }
        });
         gpconpuc.autosize();
        tv_conpuc.getColumns().addAll(codigo,nombre);
        tv_conpuc.autosize();
        tv_conpuc.setContextMenu(contextMenu);
        tv_conpuc.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        gpconpuc.add(tv_conpuc, 0, 1,3,1); 
 }
 

    
}
