/*
 * Copyright (c) 2014, JAVIER MURCIA.
 
 */
package modulos.facturas;


import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import menu.Sample;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FormFacturas extends Sample {
    TextField text;
    Pane myPane;
   
    public FormFacturas() throws IOException {
            myPane = (Pane)FXMLLoader.load(getClass().getResource("FXMLFacturas.fxml"));
            getChildren().add(myPane);
      
    }
    public  static Node createIconContent() throws IOException {
      
        ImageView imageView = new ImageView(new Image("/modulos/facturas/Facturas.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
          javafx.scene.Group g =
                new javafx.scene.Group(imageView);
        return g;
    }
}