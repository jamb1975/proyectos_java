/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulos.inventarios;

import java.io.IOException;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import menu.Sample;
import model.Inf_Existencias;
import rest.Inf_ExistenciasJerseyClient;

/**
 *
 * @author karolyani
 */
public class Existencias extends Sample {
    
    private TableView Ta_Existencias ;
    private ObservableList Ol_Inf_existencias;
    private GridPane gridpane;
    private GridPane gridpaneRoot;
    private Inf_ExistenciasJerseyClient inf_ExistenciasJerseyClient;
    private Text T_Totales;
    private Text T_Valor_total_todo;
    private Text T_Cant_total_todo;
    private Inf_Existencias[] inf_Existencias;
    public Existencias() throws IOException {
          T_Valor_total_todo=new Text("");
          T_Valor_total_todo.setFill(Color.GOLD);
          T_Valor_total_todo.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Valor_total_todo.setFont(Font.font("ARIAL", FontWeight.BOLD,20));
          T_Cant_total_todo=new Text("");
          T_Cant_total_todo.setFill(Color.GOLD);
          T_Cant_total_todo.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Cant_total_todo.setFont(Font.font("ARIAL", FontWeight.BOLD,20));
          T_Totales=new Text("");
          T_Totales.setFill(Color.GOLD);
          T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD,20));
      
        inf_ExistenciasJerseyClient=new Inf_ExistenciasJerseyClient();
        Ol_Inf_existencias= FXCollections.observableArrayList();
        gridpane=new GridPane();
        gridpaneRoot=new GridPane();
        
        gridpane.setHgap(90);
        gridpane.setVgap(5);
     gridpane.getStyleClass().add("background");
       gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
       gridpaneRoot.getStyleClass().add("background");
        GridPane.setValignment(gridpaneRoot, VPos.TOP);
        //Creamos la tabla para ver existencias
       
        Ta_Existencias=new TableView();
        TableColumn Codigo = new TableColumn();
        Codigo.setMinWidth(130);
        Codigo.setText("Código");
        Codigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        
        TableColumn Nombre = new TableColumn();
        Nombre.setMinWidth(130);
        Nombre.setText("Producto");
        Nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
       
        TableColumn Cantidad_saldo = new TableColumn();
        Cantidad_saldo.setMinWidth(130);
        Cantidad_saldo.setText("Cantidad Saldo");
        Cantidad_saldo.setCellValueFactory(new PropertyValueFactory("cantidad_saldo"));
 
        TableColumn Valor_saldo = new TableColumn();
        Valor_saldo.setMinWidth(130);
        Valor_saldo.setText("Valor Saldo");
        Valor_saldo.setCellValueFactory(new PropertyValueFactory("valor_saldo"));
 
      
      Ta_Existencias.setMinHeight(500);
      Ta_Existencias.setMinWidth(520);
      Ta_Existencias.getColumns().addAll(Codigo, Nombre,Cantidad_saldo,Valor_saldo );
      //colocamos los datos
       Ol_Inf_existencias.clear();
       inf_Existencias=inf_ExistenciasJerseyClient.findAll_XML(Inf_Existencias[].class);
       Ol_Inf_existencias.addAll(inf_Existencias);
       Ta_Existencias.setItems(Ol_Inf_existencias);
       float cant_total=0;
       BigDecimal valor_total=BigDecimal.ZERO;
       for(Inf_Existencias infe: inf_Existencias)
       {
           cant_total=cant_total+infe.getCantidad_saldo();
           valor_total=valor_total.add(infe.getValor_saldo());
       }
       T_Totales.setText("Totales: ");
       T_Cant_total_todo.setText(String.valueOf(cant_total));
       T_Valor_total_todo.setText(valor_total.toString());
       gridpane.add(Ta_Existencias, 0, 0);
       GridPane.setColumnSpan(Ta_Existencias, 4);
       GridPane.setFillWidth(T_Totales, Boolean.TRUE);
       GridPane.setFillWidth(T_Cant_total_todo, Boolean.TRUE);
       GridPane.setFillWidth(T_Valor_total_todo, Boolean.TRUE);
       gridpane.add(T_Cant_total_todo, 2, 1);
       gridpane.add(T_Valor_total_todo, 3, 1);
       
       gridpaneRoot.add(gridpane, 0, 0);
       gridpane.setMinSize(550, 550);
       gridpaneRoot.setMaxSize(550, 550);
     
      
      
        getChildren().add(gridpaneRoot);
       }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
          javafx.scene.Group g =
                new javafx.scene.Group(imageView);
   
        return g;
    }
    
  
}
