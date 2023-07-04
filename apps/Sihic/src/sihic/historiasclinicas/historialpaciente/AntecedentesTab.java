package sihic.historiasclinicas.historialpaciente;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import modelo.GenPacientes;
import modelo.HclAntecedentesFarmacologicos;
import modelo.HclAntecedentesGenerales;
import modelo.HclAntecedentesGineco;
import modelo.HclAntecedentesPatologicos;
import modelo.HclTiposAntecedentesGenerales;
import sihic.SihicApp;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.BuscarPersonas;

public class AntecedentesTab extends Tab {

    private String appPathHclAntecedentesGenerales;
    private Class ClzHzclAntecedentesGenerales;
    private Object appClassHclAntecedentesGenerales;
    private Stage stageHclAntecedentesGenerales = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    private Parent P_AntecedentesGenerales;
    private TableView ta_antecedentesgenerales;
    private TableView ta_antecedentesfarmacologicos;
    private TableView ta_antecedentesgineco;
    private TableView ta_antecedentespatologicos;
    private TableColumn tipoantecedentegeneral;
    private TableColumn descripcionag;
    private TableColumn descripcionaf;
    private TableColumn medicamento;
    private TableColumn descripciongi;
    private TableColumn G;
    private TableColumn P;
    private TableColumn A;
    private TableColumn C;
    private TableColumn FUR;
    private TableColumn FUP;
    private TableColumn tipoantecedentepatologico;
    private TableColumn observacion;

//procesos datos
    private ObservableList ol_antecedentesgenerales = FXCollections.observableArrayList();
    ;
  private List<HclAntecedentesGenerales> l_antecedentesgenerales;
    private ObservableList ol_antecedentesgineco = FXCollections.observableArrayList();
    ;
  private List<HclAntecedentesGineco> l_antecedentesgineco;
    private ObservableList ol_antecedentesfarmacologicos = FXCollections.observableArrayList();
    ;
  private List<HclAntecedentesFarmacologicos> l_antecedentesfarmacologicos;
    private ObservableList ol_antecedentespatologicos = FXCollections.observableArrayList();
    ;
  private List<HclAntecedentesPatologicos> l_antecedentespatologicos;
    private HclHistoriasClinicasControllerClient hclHistoriasClinicasControllerClient;
    private Button NuevoAg;
    private Button NuevoAgi;
    private Button NuevoAf;
    private Button NuevoAp;
    private ImageView imageView;
    private int opc;

    public AntecedentesTab(String text, Node graphic) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        this.setText(text);
        this.setGraphic(graphic);
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        NuevoAg = new Button("", imageView);

        NuevoAg.setOnAction(e
                -> {
            try {
                opc = 1;
                showantecedentegeneral();
            } catch (Exception ex) {
                Logger.getLogger(BuscarPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        NuevoAgi = new Button("", imageView);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        NuevoAgi.setOnAction(e
                -> {
            try {
                opc = 2;
                showantecedentegeneral();
            } catch (Exception ex) {
                Logger.getLogger(BuscarPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        NuevoAf = new Button("", imageView);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        NuevoAf.setOnAction(e
                -> {
            try {
                opc = 3;
                showantecedentegeneral();
            } catch (Exception ex) {
                Logger.getLogger(BuscarPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        NuevoAp = new Button("", imageView);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        NuevoAp.setOnAction(e
                -> {
            try {
                opc = 4;
                showantecedentegeneral();
            } catch (Exception ex) {
                Logger.getLogger(BuscarPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ta_antecedentesfarmacologicos = new TableView();
        ta_antecedentesgenerales = new TableView();
        ta_antecedentesgineco = new TableView();
        ta_antecedentespatologicos = new TableView();
        tipoantecedentegeneral = new TableColumn();
        tipoantecedentegeneral = new TableColumn();
        descripcionag = new TableColumn();
        descripcionaf = new TableColumn();
        medicamento = new TableColumn();
        descripciongi = new TableColumn();
        G = new TableColumn();
        P = new TableColumn();
        A = new TableColumn();
        C = new TableColumn();
        FUR = new TableColumn();
        FUP = new TableColumn();
        tipoantecedentepatologico = new TableColumn();
        observacion = new TableColumn();
        stageHclAntecedentesGenerales.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                    switch (opc) {
                        case 1: {
                            try {
                                getAntecedentesGenerales();
                            } catch (ParseException ex) {
                                Logger.getLogger(AntecedentesTab.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        case 2: {
                            try {
                                getAntecedentesGineco();
                            } catch (ParseException ex) {
                                Logger.getLogger(AntecedentesTab.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        case 3: {
                            try {
                                getAntecedentesFarmacologicos();
                            } catch (ParseException ex) {
                                Logger.getLogger(AntecedentesTab.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        case 4: {
                            try {
                                getAntecedentesPatologicos();
                            } catch (ParseException ex) {
                                Logger.getLogger(AntecedentesTab.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    }
                }
            }
        });
        init();
    }

    public void init() throws ParseException {

        GridPane grid = new GridPane();
        grid.setStyle("padding-top:15px;");
        GridPane.setValignment(NuevoAg, VPos.TOP);
        GridPane.setValignment(NuevoAgi, VPos.TOP);
        GridPane.setValignment(NuevoAf, VPos.TOP);
        GridPane.setValignment(NuevoAp, VPos.TOP);
        grid.addRow(1, ta_antecedentesgenerales, NuevoAg);
        grid.addRow(2, ta_antecedentesgineco, NuevoAgi);
        grid.addRow(3, ta_antecedentesfarmacologicos, NuevoAf);
        grid.addRow(4, ta_antecedentespatologicos, NuevoAp);
        grid.setVgap(5);
        grid.setHgap(5);
        hclHistoriasClinicasControllerClient = new HclHistoriasClinicasControllerClient();
        columnasTable();
        getAntecedentesGenerales();
        getAntecedentesFarmacologicos();
        getAntecedentesGineco();
        getAntecedentesPatologicos();
        this.setContent(grid);

    }

    private void showantecedentegeneral() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        P_AntecedentesGenerales = null;
        switch (opc) {
            case 1:
                stageHclAntecedentesGenerales.setTitle("Antecedente General");
                appPathHclAntecedentesGenerales = "sihic.historiasclinicas.historialpaciente.FHclAntecedentesGenerales";
                ClzHzclAntecedentesGenerales = Class.forName(appPathHclAntecedentesGenerales);
                appClassHclAntecedentesGenerales = ClzHzclAntecedentesGenerales.newInstance();
                P_AntecedentesGenerales = (Parent) ClzHzclAntecedentesGenerales.getMethod("createContent").invoke(appClassHclAntecedentesGenerales);
                break;

            case 2:
                stageHclAntecedentesGenerales.setTitle("Antecedente Ginecologico");
                appPathHclAntecedentesGenerales = "sihic.historiasclinicas.historialpaciente.FHclAntecedentesGineco";
                ClzHzclAntecedentesGenerales = Class.forName(appPathHclAntecedentesGenerales);
                appClassHclAntecedentesGenerales = ClzHzclAntecedentesGenerales.newInstance();
                P_AntecedentesGenerales = (Parent) ClzHzclAntecedentesGenerales.getMethod("createContent").invoke(appClassHclAntecedentesGenerales);
                break;

            case 3:
                stageHclAntecedentesGenerales.setTitle("Antecedente Farmacologico");
                appPathHclAntecedentesGenerales = "sihic.historiasclinicas.historialpaciente.FHclAntecedentesFarmacologicos";
                ClzHzclAntecedentesGenerales = Class.forName(appPathHclAntecedentesGenerales);
                appClassHclAntecedentesGenerales = ClzHzclAntecedentesGenerales.newInstance();
                P_AntecedentesGenerales = (Parent) ClzHzclAntecedentesGenerales.getMethod("createContent").invoke(appClassHclAntecedentesGenerales);
                break;
            case 4:
                stageHclAntecedentesGenerales.setTitle("Antecedente Patologico");
                appPathHclAntecedentesGenerales = "sihic.historiasclinicas.historialpaciente.FHclAntecedentesPatologicos";
                ClzHzclAntecedentesGenerales = Class.forName(appPathHclAntecedentesGenerales);
                appClassHclAntecedentesGenerales = ClzHzclAntecedentesGenerales.newInstance();
                P_AntecedentesGenerales = (Parent) ClzHzclAntecedentesGenerales.getMethod("createContent").invoke(appClassHclAntecedentesGenerales);
                break;

        }
        scene = null;
        scene = new Scene(P_AntecedentesGenerales, Color.TRANSPARENT);

        if (stageHclAntecedentesGenerales.isShowing()) {
            stageHclAntecedentesGenerales.close();
        }

//set scene to stage
        stageHclAntecedentesGenerales.sizeToScene();

        //center stage on screen
        stageHclAntecedentesGenerales.centerOnScreen();
        stageHclAntecedentesGenerales.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageHclAntecedentesGenerales.show();
    }

    public void columnasTable() {

        tipoantecedentegeneral.setMinWidth(300);
        tipoantecedentegeneral.setMaxWidth(300);
        tipoantecedentegeneral.setText("Tipo");
        tipoantecedentegeneral.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesGenerales, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesGenerales, String> hclantecedentesgenerales) {

                return new SimpleStringProperty((hclantecedentesgenerales.getValue().getHclTiposAntecedentesGenerales() != null ? hclantecedentesgenerales.getValue().getHclTiposAntecedentesGenerales().getDescripcion() : ""));

            }
        });
        descripcionag.setMinWidth(500);
        descripcionag.setText("Descripción");
        descripcionag.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesGenerales, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesGenerales, String> hclantecedentesgenerales) {

                return new SimpleStringProperty((hclantecedentesgenerales.getValue().getDescripcion()));

            }
        });
        TableColumn titulotabla = new TableColumn<>("Antecedentes Generales");
        titulotabla.getColumns().addAll(tipoantecedentegeneral, descripcionag);
        ta_antecedentesgenerales.getColumns().addAll(titulotabla);
        ta_antecedentesgenerales.setMaxWidth(800);
        ta_antecedentesgenerales.setMaxHeight(150);
        ta_antecedentesgenerales.setOnMouseClicked(e -> {
            try {

            } catch (Exception ex) {
            }
        });

        //tabla antecedentes gineco
        descripciongi.setMinWidth(400);
        descripciongi.setText("Tipo");
        descripciongi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesGineco, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesGineco, String> hclantecedentesgineco) {

                return new SimpleStringProperty((hclantecedentesgineco.getValue().getDescripcion()));

            }
        });
        A.setMaxWidth(50);
        A.setText("A");
        A.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesGineco, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesGineco, String> hclantecedentesgineco) {

                return new SimpleStringProperty((hclantecedentesgineco.getValue().getA()));

            }
        });

        C.setMaxWidth(50);
        C.setText("C");
        C.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesGineco, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesGineco, String> hclantecedentesgineco) {

                return new SimpleStringProperty((hclantecedentesgineco.getValue().getC()));

            }
        });
        G.setMaxWidth(50);
        G.setText("G");
        G.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesGineco, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesGineco, String> hclantecedentesgineco) {

                return new SimpleStringProperty((hclantecedentesgineco.getValue().getG()));

            }
        });
        P.setMaxWidth(50);
        P.setText("P");
        P.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesGineco, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesGineco, String> hclantecedentesgineco) {

                return new SimpleStringProperty((hclantecedentesgineco.getValue().getP()));

            }
        });

        FUR.setMinWidth(100);
        FUR.setText("FUR");
        FUR.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesGineco, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesGineco, String> hclantecedentesgineco) {

                return new SimpleStringProperty((DateFormat.getDateInstance().format(hclantecedentesgineco.getValue().getFUR())));

            }
        });
        FUP.setMinWidth(100);
        FUP.setText("FUP");
        FUP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesGineco, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesGineco, String> hclantecedentesgineco) {

                return new SimpleStringProperty((DateFormat.getDateInstance().format(hclantecedentesgineco.getValue().getFUP())));

            }
        });
        TableColumn tituloantgineco = new TableColumn<>("Antecedentes Ginecologicos");
        tituloantgineco.getColumns().addAll(FUR, FUP, A, C, G, P, descripciongi);
        ta_antecedentesgineco.getColumns().addAll(tituloantgineco);
        ta_antecedentesgineco.setMaxWidth(800);
        ta_antecedentesgineco.setMaxHeight(150);
        ta_antecedentesgineco.setOnMouseClicked(e -> {
            try {

            } catch (Exception ex) {
            }
        });

        medicamento.setMinWidth(300);
        medicamento.setMaxWidth(300);
        medicamento.setText("Tipo");
        medicamento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesFarmacologicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesFarmacologicos, String> hclAntecedentesFarmacologicos) {

                return new SimpleStringProperty((hclAntecedentesFarmacologicos.getValue().getMedicamento()));

            }
        });
        descripcionaf.setMinWidth(500);
        descripcionaf.setText("Descripción");
        descripcionaf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesFarmacologicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesFarmacologicos, String> hclAntecedentesFarmacologicos) {

                return new SimpleStringProperty((hclAntecedentesFarmacologicos.getValue().getDescripcion()));

            }
        });

        TableColumn tituloantecedentesfarmaco = new TableColumn<>("Antecedentes Farmacologicos");
        tituloantecedentesfarmaco.getColumns().addAll(medicamento, descripcionaf);
        ta_antecedentesfarmacologicos.getColumns().addAll(tituloantecedentesfarmaco);
        ta_antecedentesfarmacologicos.setMaxWidth(800);
        ta_antecedentesfarmacologicos.setMaxHeight(150);
        ta_antecedentesfarmacologicos.setOnMouseClicked(e -> {
            try {

            } catch (Exception ex) {
            }
        });
        tipoantecedentepatologico.setMinWidth(300);
        tipoantecedentepatologico.setMaxWidth(300);
        tipoantecedentepatologico.setText("Tipo");
        tipoantecedentepatologico.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesPatologicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesPatologicos, String> hclAntecedentesPatologicos) {

                return new SimpleStringProperty((hclAntecedentesPatologicos.getValue().getHclTiposAntecedentesPatologicos() != null ? hclAntecedentesPatologicos.getValue().getHclTiposAntecedentesPatologicos().getNombre() : ""));

            }
        });
        observacion.setMinWidth(500);
        observacion.setText("Observación");
        observacion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HclAntecedentesPatologicos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HclAntecedentesPatologicos, String> hclAntecedentespatologicos) {

                return new SimpleStringProperty((hclAntecedentespatologicos.getValue().getObservacion()));

            }
        });

        TableColumn tituloantecedentespatologicos = new TableColumn<>("Antecedentes Patologicos");
        tituloantecedentespatologicos.getColumns().addAll(tipoantecedentepatologico, observacion);
        ta_antecedentespatologicos.getColumns().addAll(tituloantecedentespatologicos);
        ta_antecedentespatologicos.setMaxWidth(800);
        ta_antecedentespatologicos.setMaxHeight(150);
        ta_antecedentespatologicos.setOnMouseClicked(e -> {
            try {

            } catch (Exception ex) {
            }
        });

        stageHclAntecedentesGenerales.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    refreshTable();
                } catch (Exception e) {

                }
            }
        });

    }

    private void getAntecedentesGenerales() throws ParseException {
        //colocamos los datos

        l_antecedentesgenerales = hclHistoriasClinicasControllerClient.getHclAntecedentesGenerales(SihicApp.hclconsultas);

        ol_antecedentesgenerales.clear();

        ol_antecedentesgenerales.addAll(l_antecedentesgenerales.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_antecedentesgenerales.setItems(ol_antecedentesgenerales);

    }

    private void getAntecedentesGineco() throws ParseException {
        //colocamos los datos

        l_antecedentesgineco = hclHistoriasClinicasControllerClient.getHclAntecedentesGineco(SihicApp.hclconsultas);
        ol_antecedentesgineco.clear();
        ol_antecedentesgineco.addAll(l_antecedentesgineco.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_antecedentesgineco.setItems(ol_antecedentesgineco);

    }

    private void getAntecedentesFarmacologicos() throws ParseException {
        //colocamos los datos

        l_antecedentesfarmacologicos = hclHistoriasClinicasControllerClient.getHclAntecedentesFarmacologicos(SihicApp.hclconsultas);
        ol_antecedentesfarmacologicos.clear();
        ol_antecedentesfarmacologicos.addAll(l_antecedentesfarmacologicos.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_antecedentesfarmacologicos.setItems(ol_antecedentesfarmacologicos);

    }

    private void getAntecedentesPatologicos() throws ParseException {
        //colocamos los datos

        l_antecedentespatologicos = hclHistoriasClinicasControllerClient.getHclAntecedentesPatologicos(SihicApp.hclconsultas);
        ol_antecedentespatologicos.clear();
        ol_antecedentespatologicos.addAll(l_antecedentespatologicos.toArray());
        // Ta_KardexProducto.getItems().clear();
        ta_antecedentespatologicos.setItems(ol_antecedentespatologicos);

    }

    public void refreshTable() throws ParseException {
        switch (opc) {
            case 1:
                getAntecedentesGenerales();
                break;

            case 2:
                getAntecedentesGineco();
                break;

            case 3:
                getAntecedentesFarmacologicos();
                break;
            case 4:
                getAntecedentesPatologicos();
                break;
        }
    }
}
