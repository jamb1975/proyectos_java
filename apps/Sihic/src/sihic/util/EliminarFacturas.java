/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.util;

import java.text.ParseException;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import service.FacturaController;
import sihic.SihicApp;
import sihic.general.LoadChoiceBoxGeneral;

/**
 *
 * @author javadeveloper
 */
public class EliminarFacturas extends Application {
     private GridPane tp_generic = new GridPane();
     private Label l_prefijofactura= new Label("Prefijo Factura:");
     private static ChoiceBox cb_prefijos=new ChoiceBox(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getItems());
     private Label l_nofactura= new Label("Eliminar facturas desde:");
     private TextField tf_nofactura = new TextField("7000000000000000");
     private Label l_newconsecutivofactura= new Label("Nuevo Consecutivo:(N° = 2195 comienza en 2196");
     private TextField tf_newconsecutivofactura = new TextField();
     private Button bu_procesar = new Button("Procesar");
     private Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
     private ImageView imageView;
     
     public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException 
     {
         tp_generic.autosize();
         tp_generic.setAlignment(Pos.CENTER);
         tp_generic.setHgap(7);
         tp_generic.setVgap(7);
         tp_generic.getStyleClass().add("background");
         imageView = new ImageView("/images/select.png");
         imageView.setFitHeight(40);
         imageView.setFitWidth(40);
         bu_procesar.setGraphic(imageView);
         bu_procesar.setTooltip(new Tooltip("Eliminar facturas"));
         alert.setTitle("Confimación de facturas");
         alert.setHeaderText("Importante esta confirmación");
         alert.setContentText("Esta seguro de eliminar las facturas");
         tp_generic.addRow(0,l_prefijofactura, cb_prefijos);
         tp_generic.addRow(1,l_nofactura, tf_nofactura);
         tp_generic.addRow(2,l_newconsecutivofactura, tf_newconsecutivofactura);
         tp_generic.add(bu_procesar,0,3,2,1);
         
         GridPane.setHalignment(bu_procesar, HPos.CENTER);
         bu_procesar.setOnAction(e ->{
             procesar_eliminacionfacturas();
         });
       return tp_generic;
     }
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    private void procesar_eliminacionfacturas()
    {
        Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK)
    {
        FacturaController.deleteinvoice(cb_prefijos.getValue().toString(), Long.valueOf(tf_nofactura.getText().trim()), Long.valueOf(tf_newconsecutivofactura.getText().trim()), SihicApp.usuarios.getSucursales().getId());
    }    
    else 
    {
           
    }
    }
}
