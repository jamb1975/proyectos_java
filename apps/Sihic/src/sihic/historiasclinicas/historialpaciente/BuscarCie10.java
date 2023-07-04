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
import sihic.SihicApp;
import sihic.controlador.HclHistoriasClinicasControllerClient;

/**
 *
 * @author adminlinux
 */
public class BuscarCie10 {

    private GridPane gpbuscarcie10;
    private TableView ta_hclcie10;
    private Label l_buscar;
    private TextField buscar;
    private ObservableList ol_hclcie10 = FXCollections.observableArrayList();
    ;
    private List<HclCie10> l_hclcie10;
    private HclHistoriasClinicasControllerClient hclHistoriasClinicasControllerClient;
    private Button asignar;
    private Scene scene = null;
    private Stage stage;
    private HclCie10 hclCie10;

    public BuscarCie10() {

        ImageView imageView;
        hclHistoriasClinicasControllerClient = new HclHistoriasClinicasControllerClient();
        stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Cie10(Diagnósticos)");
        imageView = new ImageView("/images/cie10.png");
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
                getListHclCie10();
            } catch (Exception e3) {
            }
        });
        gpbuscarcie10 = new GridPane();
        ta_hclcie10 = new TableView();

        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(100);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclCie10, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclCie10, String> hclcie10) {

                return new SimpleStringProperty((hclcie10.getValue().getCodigo()));

            }
        });
        TableColumn descripcion = new TableColumn();
        descripcion.setMinWidth(500);
        descripcion.setText("Descripción");
        descripcion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclCie10, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclCie10, String> hclcie10) {

                return new SimpleStringProperty((hclcie10.getValue().getDescripcion()));

            }
        });
        ta_hclcie10.getColumns().addAll(codigo, descripcion);
        ta_hclcie10.setMinWidth(500);
        ta_hclcie10.setOnKeyPressed(e -> {
            try {
                if (e.getCode() == KeyCode.ENTER) {
                    checkregistroexistente();
                    if (hclCie10 != null) {
                        stage.close();
                    }
                }
            } catch (Exception ex) {
            }
        });
        ta_hclcie10.setOnMouseClicked(e -> {
            try {

                checkregistroexistente();

            } catch (Exception ex) {
            }
        });

        gpbuscarcie10.getStylesheets().add(SihicApp.hojaestilos);
        gpbuscarcie10.getStyleClass().add("background");
        gpbuscarcie10.add(l_buscar, 0, 0);
        gpbuscarcie10.add(buscar, 1, 0);
        gpbuscarcie10.add(asignar, 2, 0);
        gpbuscarcie10.add(ta_hclcie10, 0, 1, 3, 1);
        gpbuscarcie10.setVgap(5);
        gpbuscarcie10.setHgap(5);

        scene = new Scene(gpbuscarcie10, Color.TRANSPARENT);
    }

    private void getListHclCie10() throws ParseException {
        //colocamos los datos

        l_hclcie10 = hclHistoriasClinicasControllerClient.getCie10(buscar.getText().trim());

        ol_hclcie10.clear();

        ol_hclcie10.addAll(l_hclcie10.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_hclcie10.setItems(ol_hclcie10);

    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((HclCie10) ta_hclcie10.getSelectionModel().getSelectedItem()) != null) {
            hclCie10 = (HclCie10) ta_hclcie10.getSelectionModel().getSelectedItem();

        }
    }

    public void showhclcie10() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

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
        if (hclCie10 != null) {
            //GenPacientes genPacientes=new GenPacientes();
            //genPacientes=SihicApp.genPacientesTemp;

            stage.close();
        }

    }

    public HclCie10 getHclCie10() {
        return hclCie10;
    }

    public void setHclCie10(HclCie10 hclCie10) {
        this.hclCie10 = hclCie10;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
