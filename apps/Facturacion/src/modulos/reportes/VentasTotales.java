/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulos.reportes;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import javafx.util.Callback;
import menu.Sample;
import model.Inf_Ventas_Totales;
import rest.VentasTotalesJerseyClient;


/**
 *
 * @author SIMboxDEV8
 */
public class VentasTotales extends Sample{
     private TableView Ta_VentasTotales ;
      private ObservableList Ol_VentasTotales;
     private GridPane gridpaneDatos;
     private GridPane gridpaneRoot;
     private GridPane gridpaneFechas;
     private GridPane gridpane;
     private DatePicker Dp_Date_from;
     private DatePicker Dp_Date_to;
     private VentasTotalesJerseyClient ventasDiariasJerseyClient;
     private ObservableList m_oDatosVentasMes = FXCollections.observableArrayList();
     private Inf_Ventas_Totales [] ventas_totales;
     private Label cant;
     private Label valor;
     private Text T_Datos_Total_Ventas[];
     
     private Text T_Totales;
     private Text T_Valor_total_todo;
     private Text T_Cant_total_todo;
     private Label L_Date_from;
     private Label L_Date_to;
     //graficos barras
     private StackedBarChart chart;
     private StackedBarChart chart2;
     private CategoryAxis xAxis;
     private NumberAxis yAxis;
     private CategoryAxis xAxis2;
     private NumberAxis yAxis2;
     private Button B_Consultar;
     String[] medidas = { "Valor"};
     String[] medidas2  = {"Cantidad"};
     ObservableList<StackedBarChart.Series> barChartData=FXCollections.observableArrayList();
     ObservableList<StackedBarChart.Series> barChartData2=FXCollections.observableArrayList();
     BigDecimal valor_total=BigDecimal.ZERO;
     float cant_total=0;
     public VentasTotales()
     {
        B_Consultar=new Button("Consultar");
        B_Consultar.setOnMouseClicked((MouseEvent ke) -> {
            query_range_date();
        });
        B_Consultar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                query_range_date();
            }});
          xAxis = new CategoryAxis(observableArrayList(medidas));
        yAxis = new NumberAxis("Valor", 0.0d, 50000.0d, 5000.0d);
         xAxis2 = new CategoryAxis(observableArrayList(medidas2));
        yAxis2 = new NumberAxis("Cantidad", 0.0d, 300.0d, 50.0d);
          T_Valor_total_todo=new Text("");
          T_Valor_total_todo.setFill(Color.GOLD);
          T_Valor_total_todo.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Valor_total_todo.setFont(Font.font("ARIAL", FontWeight.BOLD,24));
          T_Cant_total_todo=new Text("");
          T_Cant_total_todo.setFill(Color.GOLD);
          T_Cant_total_todo.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Cant_total_todo.setFont(Font.font("ARIAL", FontWeight.BOLD,24));
          T_Totales=new Text("");
          T_Totales.setFill(Color.GOLD);
          T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD,24));
          Dp_Date_from=new DatePicker();
          Dp_Date_to=new DatePicker();
          Dp_Date_from.setValue(LocalDate.now());
         Dp_Date_to.setValue(LocalDate.now());
        
           Dp_Date_to.setOnAction((ActionEvent ke) -> {
               query_range_date();
          });
          
          L_Date_from=new Label("Desde: ");
          L_Date_to=new Label("A: ");
          Ol_VentasTotales= FXCollections.observableArrayList();
         ventas_totales=null;
          
         //Creamos la tabla
         
       
        Ta_VentasTotales=new TableView();
        TableColumn FormaPago = new TableColumn();
        FormaPago.setMinWidth(130);
        FormaPago.setText("Forma de Pago");
        FormaPago.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inf_Ventas_Totales, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Inf_Ventas_Totales, String> infventastotales) {
                return new SimpleStringProperty(infventastotales.getValue().isCredito()?"Credito":"Contado");
            }
            });
        
        TableColumn CantidadTotal = new TableColumn();
        CantidadTotal.setMinWidth(130);
        CantidadTotal.setText("Cantidad");
        CantidadTotal.setCellValueFactory(new PropertyValueFactory("cant_total"));
       
        TableColumn ValorTotal = new TableColumn();
        ValorTotal.setMinWidth(130);
        ValorTotal.setText("Valor Total");
        ValorTotal.setCellValueFactory(new PropertyValueFactory("valor_total"));
 
        
 
      
      Ta_VentasTotales.setMaxHeight(200);
      Ta_VentasTotales.setMinWidth(390);
      Ta_VentasTotales.getColumns().addAll(FormaPago, CantidadTotal,ValorTotal);
      //colocamos los datos
       
      
     query_range_date();
         gridpaneDatos=new GridPane();
         gridpaneRoot=new GridPane();
         gridpaneFechas=new GridPane();
         gridpane=new GridPane();
         gridpane.getStyleClass().add("category-page");
        
        
        gridpaneFechas.add(L_Date_from, 0, 0);
        gridpaneFechas.add(Dp_Date_from, 1, 0);
        gridpaneFechas.add(L_Date_to, 2, 0);
        gridpaneFechas.add(Dp_Date_to, 3, 0);
        gridpaneFechas.add(B_Consultar, 4, 0);
        gridpaneFechas.setMinWidth(430);
        gridpaneFechas.setHgap(5);
        
         T_Totales.setText("Totales: ");
        
         chart = new StackedBarChart(xAxis, yAxis, barChartData, 15.0d);
         chart.getStylesheets().add("/cssandimages/fxmlGeneric.css");
        
         chart2 = new StackedBarChart(xAxis2, yAxis2, barChartData2, 15.0d);
           chart2.getStylesheets().add("/cssandimages/fxmlGeneric.css");
      
       gridpane.setVgap(5);
       gridpaneRoot.getStyleClass().add("gridpane-border");
       gridpaneRoot.getStylesheets().add("/cssandimages/fxmlGeneric.css");
       gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
       GridPane.setValignment(gridpaneRoot, VPos.TOP);
      
       
        GridPane.setRowSpan(chart, 3);
        GridPane.setRowSpan(chart2, 3);
        GridPane.setValignment(T_Totales, VPos.TOP);
        GridPane.setValignment(T_Cant_total_todo, VPos.TOP);
        GridPane.setValignment(T_Valor_total_todo, VPos.TOP);
        gridpane.add(gridpaneFechas, 0, 0);
        gridpane.add(Ta_VentasTotales, 0, 1);
        gridpaneDatos.add(T_Totales, 0, 0);
        gridpaneDatos.add(T_Cant_total_todo, 1, 0);
        gridpaneDatos.add(T_Valor_total_todo, 2, 0);
       gridpane.add(gridpaneDatos, 0, 2);
        gridpaneDatos.setHgap(80);
        gridpane.setMinSize(800, 500);
        gridpaneRoot.setMaxSize(800, 500);
        GridPane.setColumnSpan(Ta_VentasTotales,3);
        GridPane.setColumnSpan(gridpaneFechas,5);
        GridPane.setHalignment(gridpaneFechas, HPos.CENTER);
        gridpaneFechas.setAlignment(Pos.CENTER);
               
       gridpaneRoot.add(gridpane, 0, 0);
       chart.setMaxWidth(200);
       chart2.setMaxWidth(200);
        gridpane.add(chart, 3, 1);
          gridpane.add(chart2, 4, 1);
        getChildren().add(gridpaneRoot);
    }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/reportes/ventaspormes.jpg"));
         imageView.setFitHeight(80);
         imageView.setFitWidth(90);
         javafx.scene.Group g =
               new javafx.scene.Group(imageView);
   
        return g;
    }
    public void query_range_date()
    {
          ventasDiariasJerseyClient=null;
         ventasDiariasJerseyClient=new VentasTotalesJerseyClient();
         ventas_totales=ventasDiariasJerseyClient.findRange_XML(Inf_Ventas_Totales[].class,Dp_Date_from.getValue().toString(),Dp_Date_to.getValue().toString());
      cant_total=0;
      valor_total=BigDecimal.ZERO;
      Ol_VentasTotales.clear();
       try
       {
       Ol_VentasTotales.addAll( ventas_totales);
       Ta_VentasTotales.setItems(Ol_VentasTotales);
       ventasDiariasJerseyClient.close();
       
       barChartData.clear();
       barChartData2.clear();
       for(Inf_Ventas_Totales vd : ventas_totales)
        { 
            cant_total=cant_total+vd.getCant_total();
            valor_total=valor_total.add(vd.getValor_total());
                
            
            
              barChartData.add(new StackedBarChart.Series(vd.isCredito()?"Credito":"Contado", 
                        observableArrayList(
                           
                            new StackedBarChart.Data(medidas[0], vd.getValor_total())
                            
                            )
                         )
                     );
 
       barChartData2.add(new StackedBarChart.Series(vd.isCredito()?"Credito":"Contado", 
                        observableArrayList(
                            new StackedBarChart.Data(medidas2[0], vd.getCant_total())
                            
                            
                            )
                         )
                     );
        
        }
       }
       catch(Exception e)
       {
           
       } 
       T_Cant_total_todo.setText(String.valueOf(cant_total));
        T_Valor_total_todo.setText(valor_total.toString());
    }
}

     

