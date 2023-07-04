/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.historialpaciente;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author adminlinux
 */
public class ExamenFisicoTab extends Tab {

    public ExamenFisicoTab(String text, Node graphic) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

        this.setText(text);
        this.setGraphic(graphic);

        init();
    }

    public void init() throws ParseException {
        Image image = new Image(getClass().getResourceAsStream("/images/hombre.png"));
        Canvas canvas = new Canvas(800, 600);
        Group root = new Group(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.setOnMouseClicked(e -> {
            gc.fillOval(e.getX(), e.getY(), 20, 20);
        });
        // gc.drawImage(image, 20, 260);
        gc.drawImage(image, 20, 80, 600, 600);
        gc.fillOval(50, 80, 20, 20);

        GridPane grid = new GridPane();
        grid.setStyle("padding-top:15px;");
        GridPane.setValignment(root, VPos.TOP);
        grid.setVgap(5);
        grid.addRow(1, root);
        grid.alignmentProperty().setValue(Pos.TOP_CENTER);
        this.setContent(grid);

    }

}
