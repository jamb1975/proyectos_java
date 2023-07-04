package sihic.historiasclinicas.consultas;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.AgnEspecialidades;
import modelo.AgnMedicos;
import modelo.AgnMedicosEspecialidades;
import modelo.GenPersonas;
import modelo.HclCupsMedicamentos;
import modelo.Kardex;
import modelo.Producto;
import modelo.ProductoMedicamentos;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.controlador.HclCupsControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

public class FCupsMedicamentos extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private DecimalFormat format = new DecimalFormat("#.0");
    private AgnMedicos agnMedicos;
    private GridPane gp_generic;
    private Label la_cups;
    private Label la_medicamento;
    private SearchBox sb_servicio = new SearchBox();
    private SearchBox sb_producto = new SearchBox();
    private SearchPopover sp_servicio;
    private SearchPopover sp_producto;
    private HclCupsControllerClient hclCupsControllerClient;
    private Button save;

    private Button medicamentos;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private TableView tv_medicamentos;
    private TableColumn tc_codigo;
    private TableColumn tc_nombre;

    private static ObservableList ol_medicamentos = FXCollections.observableArrayList();
    private ContextMenu contextMenu;
    private MenuItem itemDelete;
    private ImageView imageView;
    private Button especialidad;
    private Label la_cantidad;
    private TextField cantidad;

    public Parent createContent() throws IOException, ParseException {

        la_cantidad = new Label("Cantidad:");
        cantidad = new TextField();
        cantidad.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        especialidad = new Button("", imageView);
        especialidad.setOnAction(e -> {
            try {
                addMedicamento();
            } catch (InterruptedException ex) {
                Logger.getLogger(FAgnMedicos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FCupsMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        hclCupsControllerClient = new HclCupsControllerClient();
        sp_servicio = new SearchPopover(sb_servicio, new PageBrowser(), SihicApp.s_producto, true);
        sp_producto = new SearchPopover(sb_producto, new PageBrowser(), SihicApp.s_kardex, false);
        sb_servicio.setMinWidth(300);
        sb_producto.setMinWidth(300);
        la_cups = new Label("Cups: ");
        la_medicamento = new Label("Médicamento: ");
        sb_servicio.setOnAction(e -> {
            try {

                getListMedicamentos();
            } catch (ParseException ex) {

            }
            sb_producto.requestFocus();
        });
        sb_producto.setOnAction(e -> {
            // SihicApp.s_producto=SihicApp.s_kardex.getProducto();
            especialidad.requestFocus();
        });
        //***********************************************  
        imageView = null;
        imageView = new ImageView("/images/delete.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        itemDelete = new MenuItem("Eliminar", imageView);
        itemDelete.setOnAction(e -> {
            try {
                removemedicamento();
            } catch (InterruptedException ex) {
                Logger.getLogger(FAgnMedicos.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        contextMenu = new ContextMenu(itemDelete);
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();

        LoadChoiceBoxGeneral.getCb_agnespecialidades().getProperties().put("requerido", false);
        cantidad.getProperties().put("requerido", true);
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        save = new Button("", imageView);
        hb_botones = new HBox(5);
        tv_medicamentos = new TableView();
        tc_codigo = new TableColumn();
        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        tc_codigo.setMinWidth(100);
        tc_codigo.setText("Código");
        tc_codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductoMedicamentos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProductoMedicamentos, String> productomedicamentos) {
               try
                {
                return new SimpleStringProperty(String.valueOf(productomedicamentos.getValue().getMedicamento().getCodigo()));
                }catch(Exception e)
                {
                     return new SimpleStringProperty("");
              
                }
            }
        });
        tc_codigo.setCellFactory(column -> {
            return new TableCell<Producto, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: yellow;-fx-text-fill: black;");
                        setText(item);

                    }
                }
            };
        }
        );
        tc_nombre = new TableColumn();
        tc_nombre.setText("Médicamento");
        tc_nombre.setMinWidth(350);
        tc_nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductoMedicamentos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProductoMedicamentos, String> productomedicamentos) {
                try{
                return new SimpleStringProperty(String.valueOf(productomedicamentos.getValue().getMedicamento().getNombre()));
                 }catch(Exception e)
                {
                     return new SimpleStringProperty("");
              
                }
            }
        });
        TableColumn tc_precio2015 = new TableColumn();
        tc_precio2015.setText("Precio 2015");
        tc_precio2015.setMinWidth(150);
        tc_precio2015.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductoMedicamentos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProductoMedicamentos, String> productomedicamentos) {
            try{
                return new SimpleStringProperty(String.valueOf(productomedicamentos.getValue().getMedicamento().getPrecio2015()));
                 }catch(Exception e)
                {
                     return new SimpleStringProperty("");
              
                }
            }
        });
        TableColumn tc_precio2016 = new TableColumn();
        tc_precio2016.setText("Precio 2016");
        tc_precio2016.setMinWidth(150);
        tc_precio2016.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductoMedicamentos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProductoMedicamentos, String> productomedicamentos) {

                return new SimpleStringProperty(String.valueOf(productomedicamentos.getValue().getMedicamento().getPrecio2016()));
            }
        });
        TableColumn tc_precio2017 = new TableColumn();
        tc_precio2017.setText("Precio 2017");
        tc_precio2017.setMinWidth(150);
        tc_precio2017.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductoMedicamentos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProductoMedicamentos, String> productomedicamentos) {

                return new SimpleStringProperty(String.valueOf(productomedicamentos.getValue().getMedicamento().getPrecio2017()));
            }
        });
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        tv_medicamentos.getColumns().addAll(tc_codigo, tc_nombre, tc_precio2015, tc_precio2016, tc_precio2017);
        tv_medicamentos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_medicamentos.setMaxHeight(290);
        tv_medicamentos.setContextMenu(contextMenu);
        save.setOnKeyPressed(e -> {

            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();

                } catch (InterruptedException ex) {
                    Logger.getLogger(FAgnMedicos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        medicamentos = new Button("", imageView);
        medicamentos.setOnAction(e -> {
            try {
                try {
                    addMedicamento();
                } catch (ParseException ex) {
                    Logger.getLogger(FCupsMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(FAgnMedicos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save);
        gp_generic = new GridPane();
        gp_generic.addRow(0, la_cups, sb_servicio);
        gp_generic.addRow(1, la_medicamento, sb_producto);
        gp_generic.addRow(2, la_cantidad, cantidad);
        GridPane.setValignment(especialidad, VPos.TOP);
        gp_generic.add(especialidad, 3, 3);
        gp_generic.add(tv_medicamentos, 0, 3, 2, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.add(sp_servicio, 0, 2, 2, 3);
        gp_generic.add(sp_producto, 0, 2, 2, 3);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMaxSize(300, 30);
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        gp_generic.setMinWidth(950);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic, Gp_Message);

        getListMedicamentos();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {

            try {

                SihicApp.s_producto = hclCupsControllerClient.edit(SihicApp.s_producto);

                L_Message.setText("Proceso Correcto");

                getListMedicamentos();

                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {

                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            } catch (Exception e) {
                L_Message.setText("Error Almacenando");
                e.printStackTrace();
                Task_Message = () -> {
                    try {

                        SetMessage();
                    } catch (InterruptedException ex) {

                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            }
        }
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
                        permitirproceso = false;
                        general.setStyle("-fx-background-color:#ff1600");

                    }
                }

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    if ((boolean) general.getProperties().get("requerido") == true) {
                        permitirproceso = false;
                        general.getStylesheets().add(0, "/sihic/personal.css");
                        general.getStylesheets().set(0, "/sihic/personal.css");
                    }

                }
            }
        }
        FadeTransition ft = new FadeTransition(Duration.millis(2000), save);
        //ft.setFromValue(0.0);
        // ft.setToValue(1);

        ft.play();

        //success.setOpacity(0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Object n : gp_generic.getChildren().toArray()) {
                    if (n.getClass() == TextField.class) {
                        TextField general = (TextField) n;
                        if (general.getProperties().get("requerido") != null) {
                            System.out.println("propiedad-->" + general.getProperties().get("requerido"));
                            if (((boolean) general.getProperties().get("requerido") == true)) {

                                general.setStyle(null);
                                general.getStyleClass().add("text-field");
                            }
                        }

                    } else {
                        if (n.getClass() == ChoiceBox.class) {
                            ChoiceBox general = (ChoiceBox) n;

                            if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                                general.getStylesheets().set(0, SihicApp.hojaestilos);

                            }

                        }
                    }
                }
            }
        });

    }

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void addMedicamento() throws InterruptedException, ParseException {
        if (SihicApp.s_producto != null) {
            validar_formulario();
            if (permitirproceso) {
                SihicApp.s_producto.AddMedicamentos(SihicApp.s_kardex.getProducto(), Float.valueOf(cantidad.getText()));
                save();
                getListMedicamentos();
            }
        }
    }

    private void removemedicamento() throws InterruptedException {
        if (tv_medicamentos.getSelectionModel().getSelectedItem() != null) {

            Producto p = ((ProductoMedicamentos) tv_medicamentos.getSelectionModel().getSelectedItem()).getMedicamento();
            SihicApp.s_producto.removeMedicamentos(p);
            save();
        }
    }

    public void getListMedicamentos() throws ParseException {
        //colocamos los datos

        try {

            ol_medicamentos.clear();

            ol_medicamentos.addAll(SihicApp.s_producto.getMedicamentosItems().toArray());
            // Ta_KardexProducto.getItems().clear();
            tv_medicamentos.setItems(ol_medicamentos);
        } catch (Exception e) {

        }
    }
}
