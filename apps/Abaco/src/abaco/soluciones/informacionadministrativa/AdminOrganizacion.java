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
import java.util.List;
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
import abaco.admon.FSoluciones;
import abaco.soluciones.administrador.ImpresionFactura;
import com.aquafx_project.AquaFx;
import javafx.scene.control.TitledPane;
import jfxtras.styles.jmetro8.JMetro;
import model.Organizacion;
import org.aerofx.AeroFX;



/**
 *
 * @author adminlinux
 */
public class AdminOrganizacion  extends Application{

    private GridPane  gp_generico;
    private static TableView ta_organizacion;
    private Label la_buscar;
    private static TextField buscar;
    
    private static ObservableList ol_organizacion=FXCollections.observableArrayList();;
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
    private ImageView img;
       private TitledPane tp_generic;
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException  {
        tp_generic=new TitledPane();
        tp_generic.setText("Administrar Organización");
       tp_generic.setCollapsible(false);
        stage.setTitle("Organización");
        img=new ImageView ("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo=new MenuItem("Nuevo",img);
        itemnuevo.setOnAction(e->{
          try{
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
           }catch(Exception x)
           {
               
           }
       });
  
    contextMenu=new ContextMenu(itemnuevo,itemeditar);
    stack=new StackPane();
    appClass="abaco.soluciones.informacionadministrativa.FOrganizacion";
    clz = Class.forName(appClass);
    app = clz.newInstance();
    la_buscar=new Label("Buscar: ");
    la_buscar.setMinWidth(120);
    buscar=new TextField();
    buscar.setMinWidth(50);
    
     ImageView imageView;
       
         imageView=new ImageView("/images/find.png");
         imageView.setFitHeight(20);
         imageView.setFitWidth(20);
           
           bu_buscar=new Button("",imageView);
           bu_buscar.setDisable(false);
           bu_buscar.setOnAction(e->
          {
           try {
              getOrganizacion();
             
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
               show();
             
           } catch (Exception ex) {
               ex.printStackTrace();
           }
          });
        
    gp_generico=new GridPane();
    ta_organizacion=new TableView(); 
    
    
     
     TableColumn codigo = new TableColumn();
        codigo.setMinWidth(100);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Organizacion, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Organizacion, String> organizacion) {
               
                    return new SimpleStringProperty(organizacion.getValue().getCodigo());
                            
            }
        });
        TableColumn nit = new TableColumn();
        nit.setMinWidth(100);
        nit.setText("Nit");
        nit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Organizacion, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Organizacion, String> unidadorganizacion) {
               
                    return new SimpleStringProperty(unidadorganizacion.getValue().getNit());
                            
            }
        });
       TableColumn nombre = new TableColumn();
        nombre.setMinWidth(200);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Organizacion, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Organizacion, String> unidadorganizacion) {
               
                    return new SimpleStringProperty(unidadorganizacion.getValue().getNombre());
                            
            }
        });
        TableColumn sigla = new TableColumn();
        sigla.setMinWidth(100);
        sigla.setText("Sigla");
        sigla.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Organizacion, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Organizacion, String> unidadorganizacion) {
               
                    return new SimpleStringProperty(unidadorganizacion.getValue().getSigla());
                            
            }
        });
        TableColumn Ciudad = new TableColumn();
        Ciudad.setMinWidth(200);
        Ciudad.setText("Ciudad");
        Ciudad.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Organizacion, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Organizacion, String> unidadorganizacion) {
               try{
                    return new SimpleStringProperty(unidadorganizacion.getValue().getGenMunicipios().getNombre());
               }catch(Exception e)
               {
                           return new SimpleStringProperty("NO");
            
               }
            }
        });
        TableColumn telefono = new TableColumn();
        telefono.setMinWidth(140);
        telefono.setText("Telefono");
        telefono.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Organizacion, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Organizacion, String> genUnidadesOrganizacion) {
               
                    return new SimpleStringProperty(genUnidadesOrganizacion.getValue().getCelular());
                            
            }
        });
        TableColumn direccion = new TableColumn();
        direccion.setMinWidth(150);
        direccion.setText("Dirección");
        direccion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Organizacion, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Organizacion, String> genUnidadesOrganizacion) {
               
                    return new SimpleStringProperty(genUnidadesOrganizacion.getValue().getDir());
                            
            }
        });
        ta_organizacion.getColumns().addAll(codigo,nit,nombre,sigla,Ciudad,telefono,direccion);
        //ta_librodiario.setMinWidth(650);
       
        ta_organizacion.setMinWidth(gp_generico.getLayoutBounds().getWidth());
         ta_organizacion.setMinHeight(577);
        ta_organizacion.setContextMenu(contextMenu);
        Gp_Message=new GridPane();
        Gp_Message=new GridPane();
        gp_generico.setMinWidth(1000);
        Gp_Message.setMinSize(gp_generico.getLayoutBounds().getHeight(),gp_generico.getLayoutBounds().getWidth());
        L_Message=new Label();
        L_Message.getStylesheets().add("/sihic/SofackarStylesCommon.css"); 
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
         L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
         GridPane.setValignment(L_Message, VPos.TOP);
         GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
       //gp_generico.getStylesheets().add("/abaco/SofackarStylesCommon.css");
       // gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0,la_buscar,buscar,bu_buscar,bu_nuevo);
       
        gp_generico.add(ta_organizacion, 0, 3,7,1);
      
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
          bu_buscar.setStyle("-fx-background-color:#007fff");
          bu_nuevo.setStyle("-fx-background-color:#007fff");
        }
        getOrganizacion();
        return stack;
  }
  
public static void   getOrganizacion() throws ParseException
{
         //colocamos los datos
       
       try{
         AbacoApp.li_organizacion=AbacoApp.organizacionControllerClient.getRecords(buscar.getText().trim());
       
       ol_organizacion.clear();
       
        ol_organizacion.addAll(AbacoApp.li_organizacion.toArray());
       // Ta_KardexProducto.getItems().clear();
       ta_organizacion.setItems(ol_organizacion);
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
       if ((ta_organizacion.getSelectionModel())!=null)
       {
           AbacoApp.s_organizacion=(Organizacion)ta_organizacion.getSelectionModel().getSelectedItem();
          show();
       }
   }

    
   
     @Override
    public void start(Stage primaryStage) throws Exception {
        
        
    }
        
        
    
}
