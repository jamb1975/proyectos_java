/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulos.reportes;

import java.io.IOException;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import menu.Sample;
import model.Credito_Clientes_View;
import model.Inf_Ventas_Totales;
import rest.CreditosJerseyClient;
import rest.VentasTotalesJerseyClient;


/**
 *
 * @author SIMboxDEV8
 */
public class CreditosClientes extends Sample{
     private GridPane gridpaneRoot;
     private GridPane gridpaneTotal;
     private TableView tableView ;
     private CreditosJerseyClient creditosJerseyClient;
     private ObservableList m_oDatosVentasMes = FXCollections.observableArrayList();
     private Credito_Clientes_View [] credito_Clientes_Views;
     private Label cant;
     private Label valor;
     
     public CreditosClientes()
     {
         credito_Clientes_Views=null;
         creditosJerseyClient=null;
         creditosJerseyClient=new CreditosJerseyClient();
        credito_Clientes_Views=creditosJerseyClient.findAll_XML(Credito_Clientes_View[].class);
        creditosJerseyClient.close();
        m_oDatosVentasMes.clear();
        m_oDatosVentasMes.addAll(credito_Clientes_Views);
        gridpaneRoot=new GridPane();
        gridpaneTotal=new GridPane();
        tableView = new TableView();
        cant=new Label();
        valor= new Label();
        TableColumn c_mes = new TableColumn();
        c_mes.setText("Fecha");
        c_mes.setCellValueFactory(new PropertyValueFactory("fecha"));
        TableColumn c_codigo = new TableColumn();
        c_codigo.setText("No Ident");
        c_codigo.setCellValueFactory(new PropertyValueFactory("no_ident"));
        TableColumn c_nombre = new TableColumn();
        c_nombre.setText("Nombre");
        c_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        TableColumn c_cantidad_total_prod = new TableColumn();
        c_cantidad_total_prod.setText("Valor");
        c_cantidad_total_prod.setCellValueFactory(new PropertyValueFactory("totalamount"));
         tableView.setItems(m_oDatosVentasMes);
        tableView.setEditable(true);
        tableView.getColumns().addAll(c_mes,c_codigo,c_nombre,c_cantidad_total_prod);
        GridPane.setValignment(cant, VPos.TOP);
        GridPane.setValignment(valor, VPos.TOP);
        gridpaneRoot.getStyleClass().add("gridpane-border");
        gridpaneRoot.getStylesheets().add("/cssandimages/fxmlGeneric.css");
        gridpaneRoot.getStyleClass().add("category-page");
        gridpaneRoot.setMaxSize(800, 400);
        gridpaneRoot.autosize();
        
        BigDecimal valor_total=BigDecimal.ZERO;
        int cant_total=0;
        for(Credito_Clientes_View vd : credito_Clientes_Views)
        {
            cant_total=cant_total+1;
            valor_total=valor_total.add(vd.getTotalamount());
        }
        cant.setText("Total de Clientes que deben= "+String.valueOf(cant_total));
        valor.setText("Valor total de cartera= "+valor_total.toString());
        gridpaneRoot.add(tableView, 0, 0);
        gridpaneTotal.add(cant, 0, 0);
        gridpaneTotal.add(valor, 0, 1);
        gridpaneRoot.add(gridpaneTotal, 2, 0);
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
    
}

     

