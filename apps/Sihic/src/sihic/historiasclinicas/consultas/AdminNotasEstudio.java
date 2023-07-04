/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.consultas;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.NotasEstudio;
import modelo.Resultados;
import sihic.SihicApp;
import sihic.controlador.FacturaControllerClient;
import sihic.controlador.FacturaItemControllerClient;
import sihic.controlador.NotasEstudioControllerClient;
import sihic.controlador.ResultadosControllerClient;

public class AdminNotasEstudio extends Application {

    private NotasEstudioControllerClient notasEstudioControllerClient;
    private ResultadosControllerClient resultadosControllerClient;
    private GridPane gp_generico;
    private TableView tv_facturas;
    private Label la_fechadesde;
    private DatePicker fechadesde;
    private Label la_fechahasta;
    private Label la_buscar;
    private TextField buscar;
    private DatePicker fechahasta;
    private ObservableList Ol_FacturaItem = FXCollections.observableArrayList();
    ;
    private List<FacturaItem> Li_facturasItem;
    private FacturaItemControllerClient facturaItemControllerClient;
    private Button bu_rips;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private FileChooser fileDialog;
    private FacturaControllerClient facturaControllerClient;
    private Label la_opcion;
    private ChoiceBox opcion;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private String appClass;
    private String appClassResultados;
    private Class clz;
    private Class clzResultados;
    private Object app;
    private Object appResultados;
    private Parent parent, parentResultados;
    private Stage stage = new Stage(StageStyle.DECORATED);
    private Stage stageResultados = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    private Scene sceneResultados = null;

    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        notasEstudioControllerClient = new NotasEstudioControllerClient();
        resultadosControllerClient = new ResultadosControllerClient();
        appClass = "sihic.historiasclinicas.consultas.FNotasEstudio";
        clz = Class.forName(appClass);
        app = clz.newInstance();
        stage.setTitle("Notas de Estudio");
        appClassResultados = "sihic.historiasclinicas.consultas.FResultados";
        clzResultados = Class.forName(appClassResultados);
        appResultados = clzResultados.newInstance();
        stageResultados.setTitle("Resultado");
        facturaItemControllerClient = new FacturaItemControllerClient();
        facturaControllerClient = new FacturaControllerClient();
        stack = new StackPane();
        la_fechadesde = new Label("Desde: ");
        la_fechadesde.setMinWidth(50);
        la_fechahasta = new Label("Hasta: ");
        la_buscar = new Label("Buscar:");
        la_opcion = new Label("Tipo:");
        opcion = new ChoiceBox();
        opcion.getItems().add(0, "Factura");
        opcion.getItems().add(1, "Comprobante");
        opcion.getSelectionModel().select(0);
        buscar = new TextField();
        la_fechahasta.setMinWidth(50);
        fechadesde = new DatePicker(LocalDate.now());
        fechadesde.setMinWidth(120);
        fechahasta = new DatePicker(LocalDate.now());
        fechahasta.setMinWidth(120);
        ImageView imageView;
        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        gp_generico = new GridPane();
        tv_facturas = new TableView();
        tv_facturas.setMinHeight(550);

        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(100);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(facturaItem.getValue().getFactura().getNofacturacerosizquierda());

            }
        });
        TableColumn nocomp = new TableColumn();
        nocomp.setMinWidth(100);
        nocomp.setText("N° Comp");
        nocomp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(facturaItem.getValue().getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString());

            }
        });
        TableColumn eapb = new TableColumn();
        eapb.setMinWidth(200);
        eapb.setText("Eapb");
        eapb.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {
                try {
                    return new SimpleStringProperty(facturaItem.getValue().getHclConsultas().getAgnCitas().getGenPacientes().getGenEapb().getNombre());
                } catch (Exception e) {
                    try{
                           return new SimpleStringProperty(facturaItem.getValue().getAgnCitas().getGenPacientes().getGenEapb().getNombre());
                    }catch(Exception e2)
                    {
                            return new SimpleStringProperty("N/A"); 
                    }
                }

            }
        });
        TableColumn codigo = new TableColumn();
        codigo.setMinWidth(100);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(facturaItem.getValue().getProducto().getCodigo());

            }
        });
        TableColumn producto = new TableColumn();
        producto.setMinWidth(200);
        producto.setText("Procedimiento");
        producto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {

                return new SimpleStringProperty(facturaItem.getValue().getProducto().getNombre());

            }
        });
        TableColumn paciente = new TableColumn();
        paciente.setMinWidth(200);
        paciente.setText("Paciente");
        paciente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacturaItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacturaItem, String> facturaItem) {
       try{
                return new SimpleStringProperty(facturaItem.getValue().getGenPacientes().getGenPersonas().getNombres());
       }catch(Exception e)
       {
                      return new SimpleStringProperty(facturaItem.getValue().getAgnCitas().getGenPacientes().getGenPersonas().getNombres());
   
       }
            }
        });
        //Se agrega la celda modificada con el botón a la tabla

        TableColumn buttonColNotasEstudio = new TableColumn<>("");
        buttonColNotasEstudio.setMinWidth(50);
        buttonColNotasEstudio.setSortable(false);
        buttonColNotasEstudio.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FacturaItem, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<FacturaItem, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        buttonColNotasEstudio.setCellFactory(
                new Callback<TableColumn<FacturaItem, Boolean>, TableCell<FacturaItem, Boolean>>() {

            @Override
            public TableCell<FacturaItem, Boolean> call(TableColumn<FacturaItem, Boolean> p) {
                return new ButtonCellNotasEstudio(tv_facturas);
            }

        });
        TableColumn buttonColResultados = new TableColumn<>("");
        buttonColResultados.setMinWidth(50);
        buttonColResultados.setSortable(false);
        buttonColResultados.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<FacturaItem, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<FacturaItem, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        buttonColResultados.setCellFactory(
                new Callback<TableColumn<FacturaItem, Boolean>, TableCell<FacturaItem, Boolean>>() {

            @Override
            public TableCell<FacturaItem, Boolean> call(TableColumn<FacturaItem, Boolean> p) {
                return new ButtonCellResultados(tv_facturas);
            }

        });
        tv_facturas.getColumns().addAll(nofactura, nocomp, eapb, codigo, producto, paciente, buttonColNotasEstudio, buttonColResultados);
        tv_facturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_facturas.setEditable(true);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_generico.getLayoutBounds().getHeight(), gp_generico.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        gp_generico.getStylesheets().add(SihicApp.hojaestilos);
        gp_generico.getStyleClass().add("background");
        gp_generico.setMinSize(1020, 620);
        gp_generico.addRow(0, la_buscar, buscar, la_opcion, opcion);
        gp_generico.add(tv_facturas, 0, 3, 4, 1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generico, Gp_Message);

        buscar.setOnAction(e -> {
            try {
                getFacturasItems();
            } catch (ParseException ex) {
            }
        });
        getFacturasItems();
        return stack;
    }

    private void getFacturasItems() throws ParseException {
        //colocamos los datos

        Li_facturasItem = facturaItemControllerClient.getProcedimientos(buscar.getText().trim().equals("") ? Long.valueOf(47) : Long.valueOf(buscar.getText()), opcion.getSelectionModel().getSelectedIndex());
        Ol_FacturaItem.clear();
        Ol_FacturaItem.addAll(Li_facturasItem.toArray());
        tv_facturas.setItems(Ol_FacturaItem);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private class ButtonCellNotasEstudio extends TableCell<FacturaItem, Boolean> {

        //boton a mostrar
        final Button cellButton = new Button();

        ButtonCellNotasEstudio(final TableView tblView) {
            cellButton.setPrefWidth(12);
            cellButton.setPrefHeight(12);
            Image imageOk = new Image(getClass().getResourceAsStream("/images/iconnotasestudio.png"));
            ImageView iv = new ImageView(imageOk);
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            cellButton.setGraphic(iv);
            cellButton.setCursor(Cursor.HAND);
            cellButton.setTooltip(new Tooltip("Notas Estudio"));
            cellButton.setPrefWidth(16);
            cellButton.setPrefHeight(16);
            cellButton.setOnAction(e -> {
                int selectindex = getTableRow().getIndex();
                  int horain;
                int minin ;
                int horaat;
                int minat ;
                int horafin;
                int minfin ;

                //borramos el objeto obtenido de la fila
                  try {
                SihicApp.facturaItem = ((FacturaItem) Ol_FacturaItem.get(selectindex));
                 horain = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
                minin= sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
                horaat = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
                minat = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
                horafin = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());
                minfin = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaIngreso());

                } catch (Exception e2) {
                try{    
                horain = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
                minin = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
                horaat = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
                minat = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
                horafin = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
                minfin = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getAgnCitas().getFechaHora());
                }
                catch(Exception e3)
                    {
                       horain = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
                       minin= sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
                       horaat = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
                       minat = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
                       horafin = sihic.util.UtilDate.gethorafecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());
                       minfin = sihic.util.UtilDate.getminutofecha(SihicApp.facturaItem.getHclConsultas().getAgnCitas().getFechaIngreso());

          
                    }
                }
                    SihicApp.notasEstudio = notasEstudioControllerClient.getNotasEstudio(SihicApp.facturaItem);
                    if (SihicApp.notasEstudio == null) {
                        SihicApp.notasEstudio = new NotasEstudio();
                        SihicApp.notasEstudio.setHoraingreso(horain);
                        SihicApp.notasEstudio.setMinutosingreso(minin);
                        SihicApp.notasEstudio.setFacturaItem(SihicApp.facturaItem);

                    }
                try {
                    show();
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(AdminNotasEstudio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(AdminNotasEstudio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(AdminNotasEstudio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(AdminNotasEstudio.class.getName()).log(Level.SEVERE, null, ex);
                }
               

            });

        }

        //Muestra un boton si la fila no es nula
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {

                setGraphic(cellButton);

            } else {
                setGraphic(new Label());

            }
        }

    }

    private class ButtonCellResultados extends TableCell<FacturaItem, Boolean> {

        //boton a mostrar
        final Button cellButton = new Button();

        ButtonCellResultados(final TableView tblView) {
            cellButton.setPrefWidth(12);
            cellButton.setPrefHeight(12);
            Image imageOk = new Image(getClass().getResourceAsStream("/images/iconresultados.png"));
            ImageView iv = new ImageView(imageOk);
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            cellButton.setGraphic(iv);
            cellButton.setCursor(Cursor.HAND);
            cellButton.setTooltip(new Tooltip("Resultados"));
            cellButton.setPrefWidth(16);
            cellButton.setPrefHeight(16);
            cellButton.setOnAction(e -> {
                int selectindex = getTableRow().getIndex();
                //borramos el objeto obtenido de la fila
                FacturaItem fi = ((FacturaItem) Ol_FacturaItem.get(selectindex));

                try {
                    SihicApp.resultados = resultadosControllerClient.getResultados(fi);
                    if (SihicApp.resultados == null) {
                        SihicApp.resultados = new Resultados();

                    }
                    SihicApp.resultados.setHclConsultas(fi.getHclConsultas());
                    SihicApp.resultados.setFacturaItem(fi);

                    showResultados();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            });

        }

        //Muestra un boton si la fila no es nula
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {

                setGraphic(cellButton);

            } else {
                setGraphic(new Label());

            }
        }

    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        parent = null;
        parent = (Parent) clz.getMethod("createContent").invoke(app);
        scene = null;
        scene = new Scene(parent, Color.TRANSPARENT);

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

    private void showResultados() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        parentResultados = null;
        parentResultados = (Parent) clzResultados.getMethod("createContent").invoke(appResultados);
        sceneResultados = null;
        sceneResultados = new Scene(parentResultados, Color.TRANSPARENT);

        if (stageResultados.isShowing()) {
            stageResultados.close();
        }

//set scene to stage
        stageResultados.setMaxHeight(650);
        stageResultados.setMinWidth(780);

        //center stage on screen
        stageResultados.centerOnScreen();
        stageResultados.setScene(sceneResultados);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageResultados.show();
    }
}
