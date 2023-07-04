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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author adminlinux
 */
public class ConsultasTab extends Tab {

    private String appPathHclConsultas;
    private Class ClzHclConsultas;
    private Object appClassHclConsultas;
    private Stage stageHclConsultas = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    private Parent P_HclConsultas;

    public ConsultasTab(String text, Node graphic) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

        this.setText(text);
        this.setGraphic(graphic);
        stageHclConsultas.setTitle("Consultas");
        appPathHclConsultas = "sihic.historiasclinicas.historialpaciente.FHclConsultas";
        ClzHclConsultas = Class.forName(appPathHclConsultas);
        appClassHclConsultas = ClzHclConsultas.newInstance();
        P_HclConsultas = (Parent) ClzHclConsultas.getMethod("createContent").invoke(appClassHclConsultas);
        GridPane.setHalignment(P_HclConsultas, HPos.CENTER);

        init();
    }

    public void init() throws ParseException {

        GridPane grid = new GridPane();
        grid.setStyle("padding-top:15px;");
        GridPane.setValignment(P_HclConsultas, VPos.TOP);
        grid.setVgap(5);
        grid.addRow(1, P_HclConsultas);
        grid.alignmentProperty().setValue(Pos.TOP_CENTER);
        this.setContent(grid);
    }

}
