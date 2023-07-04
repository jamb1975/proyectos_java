/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.reportes;

import static abaco.AbacoApp.cb_temas;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

import model.Inf_VentasPorProducto;
import abaco.controlador.Inf_VentasPorProductoJerseyClient;
import com.aquafx_project.AquaFx;
import java.math.RoundingMode;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import jfxtras.styles.jmetro8.JMetro;
import jxl.Workbook;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.aerofx.AeroFX;



/**
 *
 * @author SIMboxDEV8
 */
public class VentasPorProducto extends Application{
     private TableView Ta_VentasPorProducto ;
      private ObservableList Ol_KardexProducto;
     private GridPane gridpaneDatos;
     private GridPane gridpaneRoot;
     private GridPane gridpaneFechas;
     private GridPane gridpane;
     private DatePicker Dp_Date_from;
     private DatePicker Dp_Date_to;
     private Inf_VentasPorProductoJerseyClient inf_VentasPorProductoJerseyClient;
     private List<Inf_VentasPorProducto> Ar_VentasPorProducto;
     private Text T_Totales;
    
     private Label L_Date_from;
     private Label L_Date_to;
      private TextField Tf_CantCredito;
     private TextField Tf_CantContado;
     private TextField Tf_ValorCredito;
     private TextField Tf_ValorContado;
     private TextField Tf_CantTotal;
     private TextField Tf_ValorTotal;
      private TextField Tf_Valorcompras=new TextField();
     private Button B_Consultar;
     private Button B_Exportar;
     private TitledPane tp_generic;
     PieChart chart_utilidad = new PieChart();
     private  ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
     float cant_contado=0;
     BigDecimal valor_contado=BigDecimal.ZERO;
     float cant_credito=0;
     BigDecimal valor_credito=BigDecimal.ZERO;
     float cant_total=0;
     BigDecimal valor_total=BigDecimal.ZERO;
     BigDecimal valor_compras_credito=BigDecimal.ZERO;
     BigDecimal valor_compras_efectivo=BigDecimal.ZERO;
     private HBox hb_totales=new HBox();
     private  TitledPane tp_totales;
     private  GridPane gp_totales=new GridPane();
     private Label la_valorcredito=new Label();
     private Label la_valorcontado=new Label();
     private Label la_valortotal=new Label();
     private Label la_valorcomprascredito=new Label();
     private Label la_valorcomprascontado=new Label();
     private Label la_valortotalcompras=new Label();
     private Label la_valorutilidadcredito=new Label();
     private Label la_valorutilidadcontado=new Label();
     private Label la_valortotalutilidad=new Label();
      private Label la_descripcion=new Label("Descripción");
      public Parent createContent()
     {
         chart_utilidad.setMaxSize(300, 400);
         
         la_valorcredito.setMinWidth(100);
         la_valorcontado.setMinWidth(100);
         la_valortotal.setMinWidth(100);
         la_descripcion.setMinWidth(120);
         tp_totales = new TitledPane();
         gp_totales.setGridLinesVisible(true);
         gp_totales.setHgap(3);
          gp_totales.setVgap(3);
          gp_totales.setAlignment(Pos.TOP_CENTER);
          gp_totales.getStylesheets().add("/sihic/SofackarStylesCommon.css");
        //  gp_totales.getStyleClass().add("background");
          tp_totales.setText("Total Ventas");
          tp_totales.setCollapsible(false);
          gp_totales.setHgap(3);
          gp_totales.setVgap(3);
          gp_totales.autosize();
          gp_totales.setMaxWidth(300);
          gp_totales.addRow(0,la_descripcion,new Label("Crédito"),new Label("Efectivo"),new Label("Total"));
          gp_totales.addRow(1, new Label("Valor Ventas"),la_valorcredito,la_valorcontado,la_valortotal);
          gp_totales.addRow(2, new Label("Valor Compras"),la_valorcomprascredito,la_valorcomprascontado,la_valortotalcompras);
          gp_totales.addRow(3, new Label("Utilidad"),la_valorutilidadcredito,la_valorutilidadcontado,la_valortotalutilidad);
   
          tp_totales.setContent(gp_totales);
          chart_utilidad.setTitle("Utilidad Sobre las Ventas");
         // chart_utilidad.setLegendSide(Side.LEFT);
         // Place the legend on the left side
    
          tp_generic=new TitledPane();
          tp_generic.setText("Total Ventas Por Producto");
           Tf_CantCredito=new TextField();
          Tf_CantCredito.setMinWidth(90);
          Tf_CantCredito.setMaxWidth(90);
          Tf_CantContado=new TextField();
          Tf_CantContado.setMinWidth(90);
          Tf_CantContado.setMaxWidth(90);
          Tf_ValorCredito=new TextField();
          Tf_ValorCredito.setMinWidth(90);
          Tf_ValorCredito.setMaxWidth(90);
          Tf_ValorContado=new TextField();
          Tf_ValorContado.setMinWidth(90);
          Tf_ValorContado.setMaxWidth(90);
          Tf_CantTotal=new TextField();
          Tf_CantTotal.setMinWidth(90);
          Tf_CantTotal.setMaxWidth(90);
          Tf_ValorTotal=new TextField();
          Tf_ValorTotal.setMinWidth(90);
          Tf_ValorTotal.setMaxWidth(90);
        
        
            
          
          T_Totales=new Text("");
          T_Totales.setFill(Color.BLACK);
          T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD,15));
          Dp_Date_from=new DatePicker();
          Dp_Date_from.setMinWidth(120);
          Dp_Date_to=new DatePicker();
          Dp_Date_to.setMinWidth(120);
          L_Date_from=new Label("Desde: ");
          L_Date_to=new Label("A: ");
          Ol_KardexProducto= FXCollections.observableArrayList();
         Ar_VentasPorProducto=null;
         inf_VentasPorProductoJerseyClient=null;
         inf_VentasPorProductoJerseyClient=new Inf_VentasPorProductoJerseyClient();
         Dp_Date_from.setValue(LocalDate.now());
         Dp_Date_to.setValue(LocalDate.now());
         
         //Creamos la tabla
         
        B_Consultar=new Button("Consultar");
        B_Consultar.setMinWidth(120);
        B_Consultar.setOnMouseClicked((MouseEvent ke) -> {
            load_ventas_product();
        });
        B_Consultar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                load_ventas_product();
            }});
        ImageView imageView;
      
        imageView=null;
        imageView=new ImageView("/images/excel.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
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
        B_Exportar.setMinWidth(120);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Ta_VentasPorProducto=new TableView();
       
 
        
        TableColumn Producto = new TableColumn();
        Producto.setMinWidth(150);
        Producto.setText("Producto");
        Producto.setCellValueFactory(new PropertyValueFactory("nombre"));
       
        TableColumn Cantidad = new TableColumn();
        Cantidad.setMinWidth(120);
        Cantidad.setText("Cantidad");
        Cantidad.setCellValueFactory(new PropertyValueFactory("cantidad_total"));
 
        TableColumn Valor = new TableColumn();
        Valor.setMinWidth(120);
        Valor.setText("V/Venta");
        Valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inf_VentasPorProducto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Inf_VentasPorProducto, String> formapago) {
               
                    return new SimpleStringProperty(formapago.getValue().getValor_total().setScale(0, RoundingMode.HALF_UP).toString());
                            
            }});
        TableColumn valorcompra = new TableColumn();
        valorcompra.setMinWidth(120);
        valorcompra.setText("V/Compra");
        valorcompra.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inf_VentasPorProducto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Inf_VentasPorProducto, String> formapago) {
               
                    return new SimpleStringProperty(formapago.getValue().getValor_unitario().multiply(BigDecimal.valueOf(formapago.getValue().getCantidad_total())).setScale(0, RoundingMode.HALF_UP).toString());
                            
            }});
 
        
        TableColumn formapago = new TableColumn();
        formapago.setMinWidth(110);
        formapago.setText("F/Pago");
        formapago.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inf_VentasPorProducto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Inf_VentasPorProducto, String> formapago) {
               
                    return new SimpleStringProperty(formapago.getValue().isCredito()?"Crédito":"Contado");
                            
            }});
 
       
    
        
      
        Ta_VentasPorProducto.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Ta_VentasPorProducto.setMinHeight(520);
        Ta_VentasPorProducto.setMaxHeight(520);
      
        Ta_VentasPorProducto.getColumns().addAll(Producto,formapago,Cantidad,Valor,valorcompra);
     
         gridpaneDatos=new GridPane();
         gridpaneRoot=new GridPane();
         gridpaneFechas=new GridPane();
         gridpane=new GridPane();
       //  gridpane.getStyleClass().add("background");
         
         gridpaneFechas.add(L_Date_from, 0, 0);
         gridpaneFechas.add(Dp_Date_from, 0, 1);
         gridpaneFechas.add(L_Date_to, 1, 0);
         gridpaneFechas.add(Dp_Date_to, 1, 1);
         gridpaneFechas.add(B_Consultar, 2, 1);
         gridpaneFechas.add(B_Exportar, 3, 1);
         gridpaneFechas.setMaxWidth(330);
         gridpaneFechas.setHgap(5);
        
     
       gridpane.setVgap(5);
         gridpane.setHgap(5);
        gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
       GridPane.setValignment(gridpaneRoot, VPos.TOP);
      
       
         GridPane.setValignment(T_Totales, VPos.TOP);
        hb_totales.getChildren().addAll(T_Totales,Tf_CantCredito,Tf_ValorCredito,Tf_CantContado,Tf_ValorContado,Tf_CantTotal,Tf_ValorTotal,Tf_Valorcompras);
        GridPane.setValignment(tp_totales, VPos.TOP);
        GridPane.setHalignment(tp_totales, HPos.CENTER);
        gridpane.add(gridpaneFechas, 0, 0);
        gridpane.add(Ta_VentasPorProducto, 0, 1);
        gridpane.add(tp_totales, 1, 0);
       // gridpane.setPadding(new Insets(4));
        gridpane.setHgap(4);
        GridPane.setHalignment(chart_utilidad,HPos.CENTER);
        GridPane.setHalignment(Ta_VentasPorProducto,HPos.CENTER);
        gridpane.add(chart_utilidad, 1, 1);
        gridpane.add(hb_totales, 0, 2);
       
         gridpaneDatos.setHgap(5);
         gridpane.setMinSize(1050, 610);
         gridpaneRoot.setMinSize(1050, 610);
         GridPane.setHalignment(gridpaneFechas, HPos.CENTER);
       
         gridpaneFechas.setAlignment(Pos.CENTER);
         gridpaneDatos.setAlignment(Pos.CENTER);    
         gridpane.setAlignment(Pos.CENTER);  
         gridpane.alignmentProperty().setValue(Pos.TOP_CENTER);
         StackPane stack=new StackPane();
         gridpaneRoot.setAlignment(Pos.TOP_CENTER);
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
         load_ventas_product();
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
  private void  load_ventas_product()
    {
         //colocamos los datos
        cant_contado=0;
        valor_contado=BigDecimal.ZERO;
        cant_credito=0;
        valor_credito=BigDecimal.ZERO;
        cant_total=0;
        valor_total=BigDecimal.ZERO;
        valor_compras_credito=BigDecimal.ZERO;
         valor_compras_efectivo=BigDecimal.ZERO;
        inf_VentasPorProductoJerseyClient= new Inf_VentasPorProductoJerseyClient();
        Ar_VentasPorProducto=inf_VentasPorProductoJerseyClient.findRange_XML(Dp_Date_from.getValue().toString(),Dp_Date_to.getValue().toString());
        Ol_KardexProducto.clear();
        Ol_KardexProducto.addAll(Ar_VentasPorProducto);
       // Ta_KardexProducto.getItems().clear();
       Ta_VentasPorProducto.setItems(Ol_KardexProducto);
         for(Inf_VentasPorProducto vd : Ar_VentasPorProducto)
        { 
            if(vd.isCredito())
            {
                cant_credito=cant_credito+vd.getCantidad_total();
                valor_credito=valor_credito.add(vd.getValor_total());
                valor_compras_credito=valor_compras_credito.add(vd.getValor_unitario().multiply(BigDecimal.valueOf(vd.getCantidad_total())));
    
                
            }
            else
            {
               cant_contado=cant_contado+vd.getCantidad_total();
                valor_contado=valor_contado.add(vd.getValor_total()); 
                valor_compras_efectivo=valor_compras_efectivo.add(vd.getValor_unitario().multiply(BigDecimal.valueOf(vd.getCantidad_total())));
    
            }
            cant_total=cant_total+vd.getCantidad_total();
            valor_total=valor_total.add(vd.getValor_total());
         }
           
           
          la_valorcredito.setText(valor_credito.setScale(0, RoundingMode.HALF_UP).toString());
          la_valorcontado.setText(valor_contado.setScale(0, RoundingMode.HALF_UP).toString());
          la_valortotal.setText(valor_total.setScale(0, RoundingMode.HALF_UP).toString());
          la_valorcomprascredito.setText(valor_compras_credito.setScale(0, RoundingMode.HALF_UP).toString());
          la_valorcomprascontado.setText(valor_compras_efectivo.setScale(0, RoundingMode.HALF_UP).toString());
          la_valortotalcompras.setText(valor_compras_credito.setScale(0, RoundingMode.HALF_UP).add(valor_compras_efectivo.setScale(0, RoundingMode.HALF_UP)).toString());
          la_valorutilidadcredito.setText(valor_credito.setScale(0, RoundingMode.HALF_UP).subtract(valor_compras_credito.setScale(0, RoundingMode.HALF_UP)).toString());
          la_valorutilidadcontado.setText(valor_contado.setScale(0, RoundingMode.HALF_UP).subtract(valor_compras_efectivo.setScale(0, RoundingMode.HALF_UP)).toString());
          la_valortotalutilidad.setText(valor_total.setScale(0, RoundingMode.HALF_UP).subtract((valor_compras_efectivo.setScale(0, RoundingMode.HALF_UP).add(valor_compras_credito.setScale(0, RoundingMode.HALF_UP)))).toString());
       
            
            
            
         
        
        
         
        Tf_CantTotal.setText(String.valueOf(cant_total));
        Tf_ValorTotal.setText(String.valueOf(valor_total));
              
        
         T_Totales.setText("Total: ");
         Tf_CantCredito.setText(String.valueOf(cant_credito));
         Tf_ValorCredito.setText(String.valueOf(valor_credito));
         Tf_CantContado.setText(String.valueOf(cant_contado));
         Tf_ValorContado.setText(String.valueOf(valor_contado));
         Tf_Valorcompras.setText(String.valueOf(valor_compras_credito.doubleValue()+valor_compras_credito.doubleValue()));
         inf_VentasPorProductoJerseyClient.close();
         inf_VentasPorProductoJerseyClient=null;
         
         adddatatochart();
    }
   Task<Void> task = new Task<Void>() {
         @Override protected Void call() throws Exception {
            
    //stage finproduct
      
             // Return null at the end of a Task of type Void
             return null;
         }
     };
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
        String rutaArchivo = System.getProperty("user.home")+"/VentasProducto.xls"; 
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
         
                
         for(Inf_VentasPorProducto vp:Ar_VentasPorProducto )
         {
         
            
             
            
              if(f==0)
             {
                
                
                 
                // CellType.LABEL;
                 WritableCellFeatures writableCellFeatures=new WritableCellFeatures();
                 writableCellFeatures.removeDataValidation();
                 label=null;
                 label=new jxl.write.Label(f, f, "Producto");
                 hoja.addCell(label);
              
               
               
                 
                 label=null;
                 label=new jxl.write.Label(1, f, "Forma de Pago-");
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(2, f, "Cantidad");
                 hoja.addCell(label);
               
              
               label=null;
                 label=new jxl.write.Label(3, f, "Valor Venta");
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(4, f, "Valor Compra");
                 hoja.addCell(label);
             }
               f++;
                 hoja.insertRow(f);
                 label=null;
                 label=new jxl.write.Label(0, f, vp.getNombre());
                 hoja.addCell(label);
              
               
                 label=null;
                 label=new jxl.write.Label(1, f, vp.isCredito()?"Crédito":"Contado");
                 hoja.addCell(label);
                 
                 number=null;
                 number=new jxl.write.Number(2, f, vp.getCantidad_total());
                 hoja.addCell(number);
                 
                 
                number=null;
                 number=new jxl.write.Number(3, f, vp.getValor_total().floatValue());
                 hoja.addCell(number);
                 
                  number=null;
                 number=new jxl.write.Number(4, f, vp.getValor_unitario().multiply(BigDecimal.valueOf(vp.getCantidad_total())).floatValue());
                 hoja.addCell(number);
               
             
              
              
            
         }
         libro.write();
         libro.close();
         archivo.close();
         Desktop.getDesktop().open(archivoXLS);
    }
    
 private void adddatatochart()
 {
     chartData.clear();
     chartData.add(new PieChart.Data("Valor Ventas ", valor_total.doubleValue()));
     chartData.add(new PieChart.Data("Valor Compras", valor_compras_credito.doubleValue()+valor_compras_efectivo.doubleValue()));
     chartData.add(new PieChart.Data("Utilidad Total",valor_total.subtract((valor_compras_credito.add(valor_compras_efectivo))).doubleValue()));
     chartData.add(new PieChart.Data("Utilidad Efectiva",valor_contado.subtract((valor_compras_efectivo)).doubleValue()));
     chartData.add(new PieChart.Data("Utilidad Crédito",valor_credito.subtract((valor_compras_credito)).doubleValue()));
     chart_utilidad.setData(chartData);
     chart_utilidad.setLabelsVisible(false);
 }
}

 
     

