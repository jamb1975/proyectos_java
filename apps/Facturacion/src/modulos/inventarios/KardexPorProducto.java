/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulos.inventarios;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import menu.Sample;
import model.Kardex;
import modulos.FindBy.FindProduct;
import rest.KardexJerseyClient;


/**
 *
 * @author SIMboxDEV8
 */
public class KardexPorProducto extends Sample{
     private TableView Ta_KardexProducto ;
      private ObservableList Ol_KardexProducto;
     private GridPane gridpaneDatos;
     private GridPane gridpaneRoot;
     private GridPane gridpaneFechas;
     private GridPane gridpane;
     private DatePicker Dp_Date_from;
     private DatePicker Dp_Date_to;
     private KardexJerseyClient kardexJerseyClient;
     private Kardex [] ar_KerdexProducto;
     private Label cant;
     private Label valor;
     private Text T_Datos_Total_Ventas[];
     private Label L_Producto;
     private TextField Tf_Producto;
     private Text T_Totales;
     private Text T_Valor_total_todo;
     private Text T_Cant_total_todo;
     private Label L_Date_from;
     private Label L_Date_to;
      private TextField Tf_CantEntrada;
     private TextField Tf_CantSalida;
     private TextField Tf_ValorEntrada;
     private TextField Tf_ValorSalida;
     private TextField Tf_CantSaldo;
     private TextField Tf_ValorSaldo;
     private Button B_Consultar;
     private Label L_Nombre;
     private Label L_Codigo;
    private  Label L_Metodo; 
    
    private FindProduct findProduct;
     //graficos barras
      public KardexPorProducto()
     {
      Tf_CantEntrada=new TextField();
      Tf_CantEntrada.setMinWidth(90);
       Tf_CantEntrada.setMaxWidth(90);
      Tf_CantSalida=new TextField();
      Tf_CantSalida.setMinWidth(90);
       Tf_CantSalida.setMaxWidth(90);
      Tf_ValorEntrada=new TextField();
      Tf_ValorEntrada.setMinWidth(90);
       Tf_ValorEntrada.setMaxWidth(90);
      Tf_ValorSalida=new TextField();
      Tf_ValorSalida.setMinWidth(90);
       Tf_ValorSalida.setMaxWidth(90);
      Tf_CantSaldo=new TextField();
      Tf_CantSaldo.setMinWidth(90);
       Tf_CantSaldo.setMaxWidth(90);
      Tf_ValorSaldo=new TextField();
      Tf_ValorSaldo.setMinWidth(90);
      Tf_ValorSaldo.setMaxWidth(90);
      L_Nombre=new Label();
      L_Codigo=new Label();
      L_Metodo=new Label();
      L_Producto=new Label("Producto: ");
           
           Tf_Producto= new TextField();
          T_Totales=new Text("");
          T_Totales.setFill(Color.GOLD);
          T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD,15));
          Dp_Date_from=new DatePicker();
          Dp_Date_to=new DatePicker();
          L_Date_from=new Label("Desde: ");
          L_Date_to=new Label("A: ");
          Ol_KardexProducto= FXCollections.observableArrayList();
         ar_KerdexProducto=null;
         kardexJerseyClient=null;
         kardexJerseyClient=new KardexJerseyClient();
         Dp_Date_from.setValue(LocalDate.now());
         Dp_Date_to.setValue(LocalDate.now());
          findProduct=new FindProduct(false);
         Thread th = new Thread(task);
            th.setDaemon(false);
            th.start();
         //Creamos la tabla
         
        B_Consultar=new Button("Consultar");
        B_Consultar.setOnMouseClicked((MouseEvent ke) -> {
            load_kardex_product();
        });
        B_Consultar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                load_kardex_product();
            }});
        
        Tf_Producto.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
                    findProduct.show();
                    findProduct.getTf_Search().requestFocus();
               
            }});
        findProduct.getStage().setOnHidden(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN ) {
                    if(findProduct.getProducto()!=null)
                    {
                  Tf_Producto.setText(findProduct.getProducto().getNombre());
                  
                    }
                }
                }
        });
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Ta_KardexProducto=new TableView();
        TableColumn Fecha = new TableColumn();
        Fecha.setMinWidth(70);
        Fecha.setText("Fecha");
        Fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
               
                    return new SimpleStringProperty(DateFormat.getDateInstance().format(kardex.getValue().getFecha()));
                            
            }
        });
 
        
        TableColumn Descripcion = new TableColumn();
        Descripcion.setMinWidth(100);
        Descripcion.setText("Descripción");
        Descripcion.setCellValueFactory(new PropertyValueFactory("desc_kar"));
       
        TableColumn CantidadEntrada = new TableColumn();
        CantidadEntrada.setMinWidth(90);
        CantidadEntrada.setText("Cant. Entrada");
        CantidadEntrada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getTipo().equals("dc")?"("+String.valueOf(kardex.getValue().getCantidad_entrada())+")":String.valueOf(kardex.getValue().getCantidad_entrada()));
            }
        });
 
        TableColumn ValorEntrada = new TableColumn();
        ValorEntrada.setMinWidth(90);
        ValorEntrada.setText("Valor Entrada");
        ValorEntrada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getTipo().equals("dc")?"("+String.valueOf(kardex.getValue().getValor_entrada())+")":String.valueOf(kardex.getValue().getValor_entrada()));
            }
        });
 
        
        TableColumn CantidadSalida = new TableColumn();
        CantidadSalida.setMinWidth(90);
        CantidadSalida.setText("Cant. Salida");
        CantidadSalida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getTipo().equals("dv")?"("+String.valueOf(kardex.getValue().getCantidad_salida())+")":String.valueOf(kardex.getValue().getCantidad_salida()));
            }
        });
 
        TableColumn ValorSalida = new TableColumn();
        ValorSalida.setMinWidth(90);
        ValorSalida.setText("Valor Salida");
        ValorSalida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Kardex, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Kardex, String> kardex) {
                return new SimpleStringProperty(kardex.getValue().getTipo().equals("dv")?"("+String.valueOf(kardex.getValue().getValor_salida())+")":String.valueOf(kardex.getValue().getValor_salida()));
            }
        });
  TableColumn Cantidad_saldo = new TableColumn();
        Cantidad_saldo.setMinWidth(90);
        Cantidad_saldo.setText("Cant. Saldo");
        Cantidad_saldo.setCellValueFactory(new PropertyValueFactory("cantidad_saldo"));
       TableColumn Valor_saldo = new TableColumn();
        Valor_saldo.setMinWidth(90);
        Valor_saldo.setText("Valor. Saldo");
        Valor_saldo.setCellValueFactory(new PropertyValueFactory("valor_saldo"));
    
        TableColumn ValorUnidad = new TableColumn();
        ValorUnidad.setMinWidth(90);
        ValorUnidad.setText("Valor/U");
        ValorUnidad.setCellValueFactory(new PropertyValueFactory("valor_unidad"));
 
      
      Ta_KardexProducto.setMinHeight(500);
      Ta_KardexProducto.setMinWidth(800);
      Ta_KardexProducto.getColumns().addAll(Fecha, Descripcion,CantidadEntrada,ValorEntrada,CantidadSalida,ValorSalida,Cantidad_saldo,Valor_saldo,ValorUnidad);
     
         gridpaneDatos=new GridPane();
         gridpaneRoot=new GridPane();
         gridpaneFechas=new GridPane();
         gridpane=new GridPane();
       
         
        gridpaneFechas.add(L_Date_from, 0, 0);
        gridpaneFechas.add(Dp_Date_from, 1, 0);
        gridpaneFechas.add(L_Date_to, 2, 0);
        gridpaneFechas.add(Dp_Date_to, 3, 0);
        gridpaneFechas.add(L_Producto, 4, 0);
        gridpaneFechas.add(Tf_Producto, 5, 0);
        gridpaneFechas.add(B_Consultar, 6, 0);
        gridpaneFechas.setMinWidth(430);
        gridpaneFechas.setHgap(5);
        
     
       gridpane.setVgap(5);
      gridpane.getStyleClass().add("background");
       gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
       gridpaneRoot.getStyleClass().add("background");
       gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
       GridPane.setValignment(gridpaneRoot, VPos.TOP);
      
       
         GridPane.setValignment(T_Totales, VPos.TOP);
      
         gridpane.add(gridpaneFechas, 0, 0);
         gridpane.add(Ta_KardexProducto, 0, 1);
         gridpaneDatos.add(T_Totales, 0, 0);
         gridpaneDatos.add(Tf_CantEntrada, 14, 0);
         gridpaneDatos.add(Tf_ValorEntrada, 15, 0);
         gridpaneDatos.add(Tf_CantSalida, 16, 0);
         gridpaneDatos.add(Tf_ValorSalida, 17, 0);
         gridpaneDatos.add(Tf_CantSaldo, 18, 0);
         gridpaneDatos.add(Tf_ValorSaldo, 19, 0);
         gridpane.add(gridpaneDatos, 0, 2);
         gridpaneDatos.setHgap(5);
         gridpane.setMinSize(810, 580);
         gridpaneRoot.setMaxSize(810, 580);
         GridPane.setColumnSpan(Ta_KardexProducto,3);
         GridPane.setColumnSpan(gridpaneFechas,5);
        GridPane.setHalignment(gridpaneFechas, HPos.CENTER);
        GridPane.setHalignment(gridpaneDatos, HPos.CENTER);
        gridpaneFechas.setAlignment(Pos.CENTER);
        gridpaneDatos.setAlignment(Pos.CENTER);    
        gridpaneRoot.add(gridpane, 0, 0);
         getChildren().add(gridpaneRoot);
         load_kardex_product();
    }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
         imageView.setFitHeight(80);
         imageView.setFitWidth(90);
         javafx.scene.Group g =
               new javafx.scene.Group(imageView);
   
        return g;
    }
  private void  load_kardex_product()
    {
         //colocamos los datos
       
        BigDecimal valor_entrada=BigDecimal.ZERO;
         float cant_entrada=0;
         BigDecimal valor_salida=BigDecimal.ZERO;
         float cant_salida=0;
         Long id;
        if(findProduct.getProducto().getId()!=null)
        {
            id=findProduct.getProducto().getId();
        }
        else
        {
            id=new Long("1");
        }
        kardexJerseyClient= new KardexJerseyClient();
          ar_KerdexProducto=kardexJerseyClient.findRange_XML(Kardex[].class,Dp_Date_from.getValue().toString(),Dp_Date_to.getValue().toString(),id.toString(),"yes");
      if(ar_KerdexProducto.length>0)
      {
          Tf_Producto.setText(ar_KerdexProducto[0].getProducto().getNombre());
      }
       Ol_KardexProducto.clear();
       
        Ol_KardexProducto.addAll(ar_KerdexProducto);
       // Ta_KardexProducto.getItems().clear();
       Ta_KardexProducto.setItems(Ol_KardexProducto);
         for(Kardex vd : ar_KerdexProducto)
        { 
           
           if(vd.getTipo().equals("ep"))
           {
            cant_entrada=cant_entrada+vd.getCantidad_entrada();
            valor_entrada=valor_entrada.add(vd.getValor_entrada());
           
           }
           else{
               if(vd.getTipo().equals("dc"))
           {
            cant_entrada=cant_entrada-vd.getCantidad_entrada();
            valor_entrada=valor_entrada.subtract(vd.getValor_entrada());
           
           }
               else
               {
                   if(vd.getTipo().equals("sp"))
                    {
                    cant_salida=cant_salida+vd.getCantidad_salida();
                    valor_salida=valor_salida.add(vd.getValor_salida());
           
                    } 
                   else
                           {
                             if(vd.getTipo().equals("dv"))
                            {
                                cant_salida=cant_salida-vd.getCantidad_salida();
                                valor_salida=valor_salida.subtract(vd.getValor_salida());
           
                                }   
                           }
               }
           }
           
           
            
            
            
         
        
        }
          if(ar_KerdexProducto.length>0)
        {
        Tf_CantSaldo.setText(String.valueOf(ar_KerdexProducto[ar_KerdexProducto.length-1].getCantidad_saldo()));
        Tf_ValorSaldo.setText(String.valueOf(String.valueOf(ar_KerdexProducto[ar_KerdexProducto.length-1].getValor_saldo())));
              
        } 
         T_Totales.setText("Saldo final: ");
         Tf_CantEntrada.setText(String.valueOf(cant_entrada));
         Tf_ValorEntrada.setText(String.valueOf(valor_entrada));
         Tf_CantSalida.setText(String.valueOf(cant_salida));
         Tf_ValorSalida.setText(String.valueOf(valor_salida));
         kardexJerseyClient.close();
         kardexJerseyClient=null;
    }
   Task<Void> task = new Task<Void>() {
         @Override protected Void call() throws Exception {
            
    //stage finproduct
      findProduct.loadData();
             // Return null at the end of a Task of type Void
             return null;
         }
     };
}

 
     

