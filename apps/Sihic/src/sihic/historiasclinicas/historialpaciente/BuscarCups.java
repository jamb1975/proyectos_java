/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.historialpaciente;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.HclCie10;
import modelo.HclCups;
import sihic.SihicApp;
import sihic.controlador.HclHistoriasClinicasControllerClient;

/**
 *
 * @author adminlinux
 */
public class BuscarCups {

    private GridPane gpbuscarcups;
    private TableView ta_hclcups;
    private Label l_buscar;
    private TextField buscar;
    private ObservableList ol_hclcups = FXCollections.observableArrayList();
    ;
    private List<HclCups> l_hclcups;
    private HclHistoriasClinicasControllerClient hclHistoriasClinicasControllerClient;
    private Button asignar;
    private Scene scene = null;
    private Stage stage;
    private HclCups hclCups;

    public BuscarCups() {

        ImageView imageView;
        hclHistoriasClinicasControllerClient = new HclHistoriasClinicasControllerClient();
        stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Cups(Procedimientos)");
        imageView = new ImageView("/images/procedimiento.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        asignar = new Button("", imageView);
        asignar.setOnAction(e
                -> {
            try {
                asignar();
            } catch (ParseException ex) {

            }
        });

        hclHistoriasClinicasControllerClient = new HclHistoriasClinicasControllerClient();
        l_buscar = new Label("Buscar:");
        buscar = new TextField();
        buscar.setOnKeyPressed(e -> {
            try {
                getListHclCups();
            } catch (Exception e3) {
            }
        });
        gpbuscarcups = new GridPane();
        ta_hclcups = new TableView();

        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(100);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclCups, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclCups, String> hclcups) {

                return new SimpleStringProperty((hclcups.getValue().getCodigo()));

            }
        });
        TableColumn descripcion = new TableColumn();
        descripcion.setMinWidth(500);
        descripcion.setText("Descripción");
        descripcion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclCups, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclCups, String> hclcups) {

                return new SimpleStringProperty((hclcups.getValue().getDescripcion()));

            }
        });
        ta_hclcups.getColumns().addAll(codigo, descripcion);
        ta_hclcups.setMinWidth(500);
        ta_hclcups.setOnKeyPressed(e -> {
            try {
                if (e.getCode() == KeyCode.ENTER) {
                    checkregistroexistente();
                    if (hclCups != null) {
                        stage.close();
                    }
                }
            } catch (Exception ex) {
            }
        });
        ta_hclcups.setOnMouseClicked(e -> {
            try {

                checkregistroexistente();

            } catch (Exception ex) {
            }
        });
        gpbuscarcups.getStylesheets().add(SihicApp.hojaestilos);
        gpbuscarcups.getStyleClass().add("background");
        gpbuscarcups.add(l_buscar, 0, 0);
        gpbuscarcups.add(buscar, 1, 0);
        gpbuscarcups.add(asignar, 2, 0);
        gpbuscarcups.add(ta_hclcups, 0, 1, 3, 1);
        gpbuscarcups.setVgap(5);
        gpbuscarcups.setHgap(5);

        scene = new Scene(gpbuscarcups, Color.TRANSPARENT);

    }

    private void getListHclCups() throws ParseException {
        //colocamos los datos

        l_hclcups = hclHistoriasClinicasControllerClient.getCups(buscar.getText().trim());

        ol_hclcups.clear();

        ol_hclcups.addAll(l_hclcups.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_hclcups.setItems(ol_hclcups);

    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((HclCups) ta_hclcups.getSelectionModel().getSelectedItem()) != null) {
            hclCups = (HclCups) ta_hclcups.getSelectionModel().getSelectedItem();

        }
    }

    public void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (stage.isShowing()) {
            stage.close();
        }

//set scene to stage
        stage.sizeToScene();

        //center stage on screen
        stage.centerOnScreen();
        stage.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stage.show();
    }

    public void asignar() throws ParseException {
        if (hclCups != null) {
            //GenPacientes genPacientes=new GenPacientes();
            //genPacientes=SihicApp.genPacientesTemp;

            stage.close();
        }

    }

    public HclCups getHclCups() {
        return hclCups;
    }

    public void setHclCups(HclCups hclCups) {
        this.hclCups = hclCups;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
