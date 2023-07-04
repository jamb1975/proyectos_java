/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.general;

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
import modelo.GenProfesiones;
import modelo.HclCie10;
import modelo.HclCups;
import sihic.SihicApp;
import sihic.controlador.GenPersonasControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;

/**
 *
 * @author adminlinux
 */
public class BuscarProfesiones {

    private GridPane gpbuscarprofesiones;
    private TableView ta_genprofesiones;
    private Label l_buscar;
    private TextField buscar;
    private ObservableList ol_genprofesiones = FXCollections.observableArrayList();
    ;
    private List<GenProfesiones> l_genprofesiones;
    private GenPersonasControllerClient genPersonasControllerClient;
    private Button asignar;
    private Stage stage;
    private GenProfesiones genProfesiones;

    public BuscarProfesiones() {

        ImageView imageView;
        genPersonasControllerClient = new GenPersonasControllerClient();
        stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Profesion u Ocupaciones");
        imageView = new ImageView("/sihic/images/asignar.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        asignar = new Button("Asignar", imageView);
        asignar.setMaxWidth(130);
        asignar.setOnAction(e
                -> {
            try {
                asignar();
            } catch (ParseException ex) {

            }
        });

        genPersonasControllerClient = new GenPersonasControllerClient();
        l_buscar = new Label("Buscar:");
        buscar = new TextField();
        buscar.setOnKeyPressed(e -> {
            try {
                getListHclCups();
            } catch (Exception e3) {
            }
        });
        gpbuscarprofesiones = new GridPane();
        ta_genprofesiones = new TableView();

        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(100);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenProfesiones, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenProfesiones, String> genprofesiones) {

                return new SimpleStringProperty((genprofesiones.getValue().getCodigo()));

            }
        });
        TableColumn descripcion = new TableColumn();
        descripcion.setMinWidth(500);
        descripcion.setText("Descripción");
        descripcion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenProfesiones, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenProfesiones, String> genprofesiones) {

                return new SimpleStringProperty((genprofesiones.getValue().getDescripcion()));

            }
        });
        ta_genprofesiones.getColumns().addAll(codigo, descripcion);
        ta_genprofesiones.setMinWidth(500);
        ta_genprofesiones.setOnKeyPressed(e -> {
            try {
                if (e.getCode() == KeyCode.ENTER) {
                    checkregistroexistente();
                    if (genProfesiones != null) {
                        stage.close();
                    }
                }
            } catch (Exception ex) {
            }
        });
        ta_genprofesiones.setOnMouseClicked(e -> {
            try {

                checkregistroexistente();

            } catch (Exception ex) {
            }
        });

        gpbuscarprofesiones.getStylesheets().add(SihicApp.hojaestilos);
        gpbuscarprofesiones.getStyleClass().add("background");
        gpbuscarprofesiones.add(l_buscar, 0, 0);
        gpbuscarprofesiones.add(buscar, 1, 0);
        gpbuscarprofesiones.add(asignar, 2, 0);
        gpbuscarprofesiones.add(ta_genprofesiones, 0, 1, 3, 1);
        gpbuscarprofesiones.setVgap(5);
        gpbuscarprofesiones.setHgap(5);

    }

    private void getListHclCups() throws ParseException {
        //colocamos los datos

        l_genprofesiones = genPersonasControllerClient.getProfesiones(buscar.getText().trim());

        ol_genprofesiones.clear();

        ol_genprofesiones.addAll(l_genprofesiones.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_genprofesiones.setItems(ol_genprofesiones);

    }

    private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((GenProfesiones) ta_genprofesiones.getSelectionModel().getSelectedItem()) != null) {
            genProfesiones = (GenProfesiones) ta_genprofesiones.getSelectionModel().getSelectedItem();

        }
    }

    public void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Scene scene = null;
        scene = new Scene(gpbuscarprofesiones, Color.TRANSPARENT);

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
        if (genProfesiones != null) {
            //GenPacientes genPacientes=new GenPacientes();
            //genPacientes=SihicApp.genPacientesTemp;

            stage.close();
        }

    }

    public Stage getStage() {
        return stage;
    }

    public GenProfesiones getGenProfesiones() {
        return genProfesiones;
    }

    public void setGenProfesiones(GenProfesiones genProfesiones) {
        this.genProfesiones = genProfesiones;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
