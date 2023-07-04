/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulos.reportes;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import menu.Sample;
import model.Inf_AtencionAClientes;
import model.Inf_VentasPorProducto;
import modulos.FindBy.FindProduct;
import rest.Inf_AtencionAClientesJerseyClient;
import rest.Inf_VentasPorProductoJerseyClient;


/**
 *
 * @author SIMboxDEV8
 */
public class AtencionAClientes extends Sample{
     private TableView Ta_AtencionAClientes ;
      private ObservableList Ol_AtencionAClientes;
     private GridPane gridpaneDatos;
     private GridPane gridpaneRoot;
     private GridPane gridpaneFechas;
     private GridPane gridpane;
     private DatePicker Dp_Date_from;
     private DatePicker Dp_Date_to;
     private Inf_AtencionAClientesJerseyClient inf_AtencionAClientesJerseyClient;
     private Inf_AtencionAClientes [] Ar_AtencionAClientes;
     private Text T_Totales;
     private Label L_Date_from;
     private Label L_Date_to;
     private TextField Tf_CantAtencion;
     private TextField Tf_ValorVentas;
    
     private TextField Tf_CantTotal;
     private TextField Tf_ValorTotal;
     private Button B_Consultar;
     
    
   
      public AtencionAClientes()
     {
      Tf_CantAtencion=new TextField();
      Tf_CantAtencion.setMinWidth(90);
       Tf_CantAtencion.setMaxWidth(90);
     
      Tf_ValorVentas=new TextField();
      Tf_ValorVentas.setMinWidth(90);
       Tf_ValorVentas.setMaxWidth(90);
     
      Tf_CantTotal=new TextField();
      Tf_CantTotal.setMinWidth(90);
       Tf_CantTotal.setMaxWidth(90);
      Tf_ValorTotal=new TextField();
      Tf_ValorTotal.setMinWidth(90);
      Tf_ValorTotal.setMaxWidth(90);
        
        
            
          
          T_Totales=new Text("");
          T_Totales.setFill(Color.GOLD);
          T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD,15));
          Dp_Date_from=new DatePicker();
          Dp_Date_to=new DatePicker();
          L_Date_from=new Label("Desde: ");
          L_Date_to=new Label("A: ");
          Ol_AtencionAClientes= FXCollections.observableArrayList();
         Ar_AtencionAClientes=null;
         inf_AtencionAClientesJerseyClient=null;
         inf_AtencionAClientesJerseyClient=new Inf_AtencionAClientesJerseyClient();
         Dp_Date_from.setValue(LocalDate.now());
         Dp_Date_to.setValue(LocalDate.now());
         
         //Creamos la tabla
         
        B_Consultar=new Button("Consultar");
        B_Consultar.setOnMouseClicked((MouseEvent ke) -> {
            load_AtencionAClientes();
        });
        B_Consultar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                load_AtencionAClientes();
            }});
        
        Ta_AtencionAClientes=new TableView();
        TableColumn NoIdent = new TableColumn();
        NoIdent.setMinWidth(30);
        NoIdent.setText("NoIdent");
        NoIdent.setCellValueFactory(new PropertyValueFactory("no_ident"));
 
        
        TableColumn Empleado = new TableColumn();
        Empleado.setMinWidth(150);
        Empleado.setText("Empleado");
        Empleado.setCellValueFactory(new PropertyValueFactory("nombre"));
       
        TableColumn TotalAtencion = new TableColumn();
        TotalAtencion.setMinWidth(120);
        TotalAtencion.setText("Total de Atenciones");
        TotalAtencion.setCellValueFactory(new PropertyValueFactory("total_atencion"));
 
        TableColumn ValorVentas = new TableColumn();
        ValorVentas.setMinWidth(120);
        ValorVentas.setText("Valor Ventas");
        ValorVentas.setCellValueFactory(new PropertyValueFactory("valor_ventas"));
 
        
        
        
      
      Ta_AtencionAClientes.setMinHeight(400);
      Ta_AtencionAClientes.setMinWidth(420);
      Ta_AtencionAClientes.getColumns().addAll(NoIdent, Empleado,TotalAtencion,ValorVentas);
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
        
     
       gridpane.setVgap(5);
       gridpaneRoot.getStyleClass().add("gridpane-border");
       gridpaneRoot.getStylesheets().add("/cssandimages/fxmlGeneric.css");
       gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
       GridPane.setValignment(gridpaneRoot, VPos.TOP);
      
       
         GridPane.setValignment(T_Totales, VPos.TOP);
      
         gridpane.add(gridpaneFechas, 0, 0);
         gridpane.add(Ta_AtencionAClientes, 0, 1);
         gridpaneDatos.add(T_Totales, 0, 0);
         gridpaneDatos.add(Tf_CantAtencion, 17, 0);
         gridpaneDatos.add(Tf_ValorVentas, 18, 0);
         
         
         gridpane.add(gridpaneDatos, 0, 2);
         gridpaneDatos.setHgap(5);
         gridpane.setMinSize(810, 580);
         gridpaneRoot.setMaxSize(810, 580);
         GridPane.setColumnSpan(Ta_AtencionAClientes,3);
         GridPane.setColumnSpan(gridpaneFechas,5);
        GridPane.setHalignment(gridpaneFechas, HPos.CENTER);
        GridPane.setHalignment(gridpaneDatos, HPos.CENTER);
        gridpaneFechas.setAlignment(Pos.CENTER);
        gridpaneDatos.setAlignment(Pos.CENTER);    
        gridpaneRoot.add(gridpane, 0, 0);
         getChildren().add(gridpaneRoot);
         load_AtencionAClientes();
    }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
         imageView.setFitHeight(80);
         imageView.setFitWidth(90);
         javafx.scene.Group g =
               new javafx.scene.Group(imageView);
   
        return g;
    }
  private void  load_AtencionAClientes()
    {
         //colocamos los datos
       
        BigDecimal valor_ventas=BigDecimal.ZERO;
         float total_atencion=0;
        
         
        
        inf_AtencionAClientesJerseyClient= new Inf_AtencionAClientesJerseyClient();
          Ar_AtencionAClientes=inf_AtencionAClientesJerseyClient.findRange_XML(Inf_AtencionAClientes[].class,Dp_Date_from.getValue().toString(),Dp_Date_to.getValue().toString());
      
       Ol_AtencionAClientes.clear();
       
        Ol_AtencionAClientes.addAll(Ar_AtencionAClientes);
       // Ta_KardexProducto.getItems().clear();
       Ta_AtencionAClientes.setItems(Ol_AtencionAClientes);
         for(Inf_AtencionAClientes vd : Ar_AtencionAClientes)
        { 
           
           
            total_atencion=total_atencion+vd.getTotal_atencion();
            valor_ventas=valor_ventas.add(vd.getValor_ventas());
           
           
                  
               }
           
           
           
            
            
            
         
        
        
         
       
              
        
         T_Totales.setText("Total: ");
         Tf_CantAtencion.setText(String.valueOf(total_atencion));
         Tf_ValorVentas.setText(String.valueOf(valor_ventas));
       
         inf_AtencionAClientesJerseyClient.close();
         inf_AtencionAClientesJerseyClient=null;
    }
   
}

 
     

