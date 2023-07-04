/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.reportes;

import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import static abaco.AbacoApp.kardexControllerClient;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Inf_Existencias;
import abaco.controlador.Inf_ExistenciasJerseyClient;
import abaco.controlador.ProductoControllerClient;
import com.aquafx_project.AquaFx;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import jfxtras.styles.jmetro8.JMetro;
import jxl.Workbook;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import model.Factura;
import model.Kardex;
import org.aerofx.AeroFX;


/**
 *
 * @author karolyani
 */
public class Existencias extends Application {
     
    private TableView Ta_Existencias ;
   private ObservableList Ol_Inf_existencias;
     private TitledPane tp_generic;
   
    private GridPane gridpane;
    private GridPane gridpaneRoot;
    private Inf_ExistenciasJerseyClient inf_ExistenciasJerseyClient;
    ProductoControllerClient productoControllerClient;
    private Text T_Totales;
    private TextField T_Valor_total_todo;
    private TextField T_Cant_total_todo;
    private List<Inf_Existencias> inf_Existencias;
    private Label L_Producto;
    private TextField Tf_Producto;
    private List<Kardex> li_kardex=new ArrayList<>();
    private Button B_Exportar;
     static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
} 
     String appClass2;
           Class clz2 ;
           Object app2;
           private Stage stage2;
           private Scene scene2 =null;
           private Parent parent2;
           private ContextMenu contextMenu;
    private MenuItem  itemcargarinventario;
    private HBox hb_totales=new HBox();
    public Parent createContent() throws IOException {
        contextMenu=null;
          contextMenu=new ContextMenu(); 
       ImageView  img=null;
         img=new ImageView ("/images/new2.png");
         img.setFitHeight(20);
         img.setFitWidth(20);
         itemcargarinventario=new MenuItem("Cargar Producto a Inventario",img);
         itemcargarinventario.setOnAction(e->{
         
           try{
         if(checkregistroexistente())
                 {
                show2();
                 }
           }catch(Exception x)
           {
               x.printStackTrace();
           }
       });
         contextMenu.getItems().addAll(itemcargarinventario);
          
         stage2= new Stage(StageStyle.DECORATED);
         stage2.setTitle("Agregar Producto a Inventarios");
         stage2.setOnHidden(e->{
             if (e.getEventType() == e.WINDOW_HIDDEN ) {
               
            try{
                
             getRecords();
          
            }catch(Exception e2)
            {
                e2.printStackTrace();
            }
             }
        });
          productoControllerClient=new ProductoControllerClient();
          L_Producto=new Label("Producto: ");
          Tf_Producto=new TextField();
          Tf_Producto.setMinWidth(200);
          Tf_Producto.setPromptText("Encontrar por codigo o  nombre ");
           tp_generic=new TitledPane();
           tp_generic.setText("Existencias Por Producto");
          ImageView imageView;
      
        imageView=null;
        imageView=new ImageView("/images/excel.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Exportar=new Button("Exportar",imageView);
         B_Exportar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    exportar_excel();
                } catch (IOException ex) {
                    Logger.getLogger(VentasPorProducto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (WriteException ex) {
                    Logger.getLogger(VentasPorProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }});
       Tf_Producto.textProperty().addListener((observable, oldValue, newValue) -> {
           getDatosExistencias();
});
       
        T_Totales=new Text("Total: ");
         T_Totales.setFill(Color.WHITE);
          T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD,20));
        
          T_Valor_total_todo=new TextField();
           
          T_Cant_total_todo=new TextField();
       
          hb_totales.getChildren().addAll(T_Totales,T_Cant_total_todo,T_Valor_total_todo);
          hb_totales.setSpacing(3);
          inf_ExistenciasJerseyClient=new Inf_ExistenciasJerseyClient();
        Ol_Inf_existencias= FXCollections.observableArrayList();
        gridpane=new GridPane();
        gridpaneRoot=new GridPane();
      //  gridpane.getStyleClass().add("category-page");
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        
        gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
        GridPane.setValignment(gridpaneRoot, VPos.TOP);
        //Creamos la tabla para ver existencias
       
        Ta_Existencias=new TableView();
        Ta_Existencias.setContextMenu(contextMenu);
        TableColumn Codigo = new TableColumn();
        Codigo.setMinWidth(130);
        Codigo.setText("Código");
        Codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                    return new SimpleStringProperty(kardex.getValue().getProducto().getCodigobarras().replace(" ", ""));
               }catch(Exception e)
               {
                 return new SimpleStringProperty("0");
               
               }
                            
            }
        });
         Codigo.setCellFactory(tc -> {
    TableCell<Factura.Status, String> cell = new TableCell<>();
    Text text = new Text();
    cell.setGraphic(text);
    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
    text.wrappingWidthProperty().bind(Codigo.widthProperty());
    text.textProperty().bind(cell.itemProperty());
   switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: text.setFill(Color.WHITE);
                      break;
                    
         }
    return cell ;
});
        TableColumn Nombre = new TableColumn();
        Nombre.setMinWidth(190);
        Nombre.setText("Producto");
        Nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                    return new SimpleStringProperty(kardex.getValue().getProducto().getNombre());
               }catch(Exception e)
               {
                 return new SimpleStringProperty("NT");
               
               }
                            
            }
        });
             Nombre.setCellFactory(tc -> {
    TableCell<Factura.Status, String> cell = new TableCell<>();
    Text text = new Text();
    cell.setGraphic(text);
    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
    text.wrappingWidthProperty().bind(Nombre.widthProperty());
    text.textProperty().bind(cell.itemProperty());
   switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: text.setFill(Color.WHITE);
                      break;
                    
         }
    return cell ;
});
        TableColumn Cantidad_saldo = new TableColumn();
        Cantidad_saldo.setMinWidth(150);
        Cantidad_saldo.setText("C/Saldo");
        Cantidad_saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                    return new SimpleStringProperty(String.valueOf(kardex.getValue().getCantidad_saldo()));
               }catch(Exception e)
               {
                 return new SimpleStringProperty("0");
               
               }
                            
            }
        });
 
        TableColumn Valor_saldo = new TableColumn();
        Valor_saldo.setMinWidth(150);
        Valor_saldo.setText("V/Saldo");
        Valor_saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                   
                    return new SimpleStringProperty(String.valueOf(kardex.getValue().getValor_saldo().setScale(0, RoundingMode.HALF_UP)));
               }catch(Exception e)
               {
                 return new SimpleStringProperty("0");
               
               }
                            
            }
        });
         TableColumn precioventa = new TableColumn();
        precioventa.setMinWidth(150);
        precioventa.setText("P/Venta");
        precioventa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                    
                    return new SimpleStringProperty(String.valueOf(kardex.getValue().getProducto().getPrecio().setScale(0, RoundingMode.HALF_UP)));
               }catch(Exception e)
               {
                 return new SimpleStringProperty("0");
               
               }
                            
            }
        });
        TableColumn ubicacion = new TableColumn();
        ubicacion.setMinWidth(190);
        ubicacion.setText("Ubicación");
        ubicacion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                    
                    return new SimpleStringProperty(kardex.getValue().getProducto().getDescrip());
               }catch(Exception e)
               {
                 return new SimpleStringProperty("0");
               
               }
                            
            }
        });
                 ubicacion.setCellFactory(tc -> {
    TableCell<Factura.Status, String> cell = new TableCell<>();
    Text text = new Text();
    cell.setGraphic(text);
    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
    text.wrappingWidthProperty().bind(ubicacion.widthProperty());
    text.textProperty().bind(cell.itemProperty());
   switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: text.setFill(Color.WHITE);
                      break;
                    
         }
    return cell ;
});
         TableColumn observaciones = new TableColumn();
        observaciones.setMinWidth(190);
        observaciones.setText("Observaciones");
        observaciones.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               try
               {
                    
                    return new SimpleStringProperty(kardex.getValue().getProducto().getObservaciones());
               }catch(Exception e)
               {
                 return new SimpleStringProperty("0");
               
               }
                            
            }
        });
                       observaciones.setCellFactory(tc -> {
    TableCell<Factura.Status, String> cell = new TableCell<>();
    Text text = new Text();
    cell.setGraphic(text);
    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
    text.wrappingWidthProperty().bind(observaciones.widthProperty());
    text.textProperty().bind(cell.itemProperty());
   switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: text.setFill(Color.WHITE);
                      break;
                    
         }
    return cell ;
});
        Ta_Existencias.setMinHeight(520);
        Ta_Existencias.setMaxHeight(520);
        Ta_Existencias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Ta_Existencias.getColumns().addAll(Codigo, Nombre,Cantidad_saldo,Valor_saldo,precioventa,ubicacion,observaciones);
        gridpane.add(L_Producto, 0, 0);
        gridpane.add(Tf_Producto, 1, 0);
        gridpane.add(B_Exportar, 2, 0);
        Tf_Producto.setAlignment(Pos.CENTER_LEFT);
        gridpane.add(Ta_Existencias, 0, 1,3,1);
        gridpane.add(hb_totales, 0, 2,3,1);
        GridPane.setHalignment(hb_totales, HPos.RIGHT);
        hb_totales.setAlignment(Pos.BASELINE_RIGHT);
        GridPane.setHalignment(Tf_Producto, HPos.LEFT);
        GridPane.setHalignment(L_Producto, HPos.CENTER);
      
       
        
       //Traer   datos desde la BD
     
       
        gridpane.setMinSize(1000, 615);
        StackPane stack=new StackPane();
        tp_generic.setContent(gridpane);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(tp_generic);
        switch(cb_temas.getSelectionModel().getSelectedIndex())
        {
            case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gridpane);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gridpane);
                     break;              
         }
             //agregamos el formulario y la tabla
      //  gridpane.getStyleClass().add("background");
        gridpane.setAlignment(Pos.TOP_CENTER);
      // gridpane.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        getRecords();
        return stack;
       }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
          javafx.scene.Group g =
                new javafx.scene.Group(imageView);
   
        return g;
    }
 
 private void getDatosExistencias()
 {
      
      li_kardex=AbacoApp.li_kardex.stream().filter(line ->line.getProducto().getCodigobarras().trim().toUpperCase().contains(Tf_Producto.getText().trim().toUpperCase()) || line.getProducto().getNombre().trim().toUpperCase().contains(Tf_Producto.getText().toUpperCase()))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());
       Ol_Inf_existencias.clear();
       Ol_Inf_existencias.addAll(li_kardex);
       Ta_Existencias.setItems(Ol_Inf_existencias);
       float cant_total=0;
       BigDecimal valor_total=BigDecimal.ZERO;
       for(Kardex infe: li_kardex)
       {
           cant_total=cant_total+infe.getCantidad_saldo();
           valor_total=valor_total.add(infe.getValor_saldo());
       }
      
       T_Cant_total_todo.setText(String.valueOf(cant_total));
       T_Valor_total_todo.setText(valor_total.toString());
      
      
 }
   @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
   public void exportar_excel() throws IOException, WriteException
    {
        String rutaArchivo = System.getProperty("user.home")+"/Existencias.xls"; 
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        if(archivoXLS.exists()) 
        {
            archivoXLS.delete(); 
            archivoXLS.createNewFile();
        }
        WritableWorkbook libro;
         FileOutputStream archivo = new FileOutputStream(archivoXLS);
        libro = Workbook.createWorkbook(archivo);
        WritableSheet hoja = libro.createSheet("Mi hoja de trabajo 1",0);
         int f=0;
         
                
         for(Kardex ie:li_kardex )
         {
            
                       
            
              if(f==0)
             {
                
                 hoja.insertRow(f);
                 
                // CellType.LABEL;
                 WritableCellFeatures writableCellFeatures=new WritableCellFeatures();
                 writableCellFeatures.removeDataValidation();
                 label=null;
                 label=new jxl.write.Label(f, f, "Código");
                 hoja.addCell(label);
                             
                 label=null;
                 label=new jxl.write.Label(1, f, "Producto");
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(2, f, "Cantidad Saldo");
                 hoja.addCell(label);
               
              
               label=null;
                 label=new jxl.write.Label(3, f, "Valor Saldo");
                 hoja.addCell(label);
             }
               f++;
                 hoja.insertRow(f);
                 label=null;
                 label=new jxl.write.Label(0, f, ie.getProducto().getCodigobarras());
                 hoja.addCell(label);
              
               
                 label=null;
                 label=new jxl.write.Label(1, f, ie.getProducto().getNombre());
                 hoja.addCell(label);
                 
                 number=null;
                 number=new jxl.write.Number(2, f, ie.getCantidad_saldo());
                 hoja.addCell(number);
                 
                 
                number=null;
                 number=new jxl.write.Number(3, f, ie.getValor_saldo().floatValue());
                 hoja.addCell(number);
               
             
              
              
            
         }
         libro.write();
         libro.close();
         archivo.close();
         Desktop.getDesktop().open(archivoXLS);
    } 
   
  private void show2()  
  {
      
     
      appClass2="abaco.soluciones.procesosadministrativos.IngresoDirectoInventario";
      clz2=null;
      try{
      clz2 = Class.forName(appClass2);
      app2 = clz2.newInstance();
  
      parent2=null;
      parent2 = (Parent) clz2.getMethod("createContent").invoke(app2);
      scene2=null;
      scene2=new Scene(parent2, Color.TRANSPARENT);
       
        if (stage2.isShowing())
       {
           stage2.close();
       }      
        
        
//set scene to stage
                stage2.sizeToScene();
                
              
                //center stage on screen
                stage2.centerOnScreen();
                stage2.setScene(scene2);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stage2.show();
      }catch(Exception e)
      {
         e.printStackTrace();
      }
  }
private void getRecords()
{
       AbacoApp.li_kardex =kardexControllerClient.getRecords();
       List<Kardex> l_kardex= AbacoApp.li_kardex.stream().filter(distinctByKey(b ->b.getProducto())).collect(Collectors.toList());//.distinct().collect(Collectors.toList());
       AbacoApp.li_kardex=l_kardex;
       getDatosExistencias();
}
private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
   {
       if ((Ta_Existencias.getSelectionModel())!=null)
       {
           AbacoApp.s_kardex=(Kardex)Ta_Existencias.getSelectionModel().getSelectedItem();
           return true;
       }
       else
       {
           return false;
       }
   }
}
