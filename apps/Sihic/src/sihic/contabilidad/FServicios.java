package sihic.contabilidad;

import sihic.SearchPopover;
import sihic.control.SearchBox;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.GenCategoriasProductos;
import modelo.Producto;
import sihic.PageBrowser;
import sihic.SihicApp;
import static sihic.SihicApp.s_productoControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.search.IndexSearcher;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FServicios extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;

    private GridPane gp_generic;

    private TextField codigo;
    private SearchBox sb_cups = new SearchBox();
    private SearchPopover sp_cups;
    private TextField nombre;
    private TextField precio2015;
    private TextField precio2016;
    private TextField precio2017;
    private TextField precio2018;
    private TextField precio2001;
    private TextField precio2015_soat=new TextField();
    private TextField precio2016_soat=new TextField();
    private TextField precio2017_soat=new TextField();
    private TextField precio2018_soat=new TextField();
    private TextField precio2001_soat=new TextField();
    private TextField precio2020=new TextField();
    private TextField precio2020_soat=new TextField();
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private DecimalFormat format = new DecimalFormat("#.0");
private Label la_especialidad;
    public Parent createContent() throws IOException {
        sp_cups = new SearchPopover(sb_cups, new PageBrowser(), SihicApp.s_hclCups, false);
        sp_cups.setMaxWidth(300);
        sb_cups.setMinWidth(300);

        sb_cups.textProperty().addListener((observable, oldValue, newValue) -> {
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);

            codigo.setText(SihicApp.s_hclCups.getCodigo());
            nombre.setText(SihicApp.s_hclCups.getDescripcion());
        });

        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        codigo = new TextField("");
        codigo.setMinWidth(500);
        codigo.getProperties().put("requerido", true);
        precio2015 = new TextField("");
        precio2015.getProperties().put("requerido", true);
        precio2016 = new TextField("");
        precio2016.getProperties().put("requerido", true);
        precio2017 = new TextField("");
        precio2017.getProperties().put("requerido", true);
        precio2018 = new TextField("");
        precio2018.getProperties().put("requerido", true);
        precio2001 = new TextField("");
        precio2001.getProperties().put("requerido", true);
        precio2020 = new TextField("");
        precio2020.getProperties().put("requerido", true);
        precio2020_soat = new TextField("");
        precio2020_soat.getProperties().put("requerido", true);
        sb_cups.getProperties().put("requerido", true);
          la_especialidad = new Label("Especialidad:");
           LoadChoiceBoxGeneral.getCb_agnespecialidades().getProperties().put("requerido", true);
        LoadChoiceBoxGeneral.getCb_agnespecialidades().setMaxWidth(400);
        precio2015.setTextFormatter(new TextFormatter<>(c
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
        precio2016.setTextFormatter(new TextFormatter<>(c
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
        precio2017.setTextFormatter(new TextFormatter<>(c
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
        precio2018.setTextFormatter(new TextFormatter<>(c
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
precio2001.setTextFormatter(new TextFormatter<>(c
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
precio2020_soat.setTextFormatter(new TextFormatter<>(c
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
        nombre = new TextField("");
        nombre.getProperties().put("requerido", true);
        nombre.setMinWidth(300);

        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        save = new Button("", imageView);
        save.setTooltip(new Tooltip("Guardar Producto"));
        hb_botones = new HBox(2);

        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            } catch (ParseException ex) {
                Logger.getLogger(FServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        nuevo = new Button("", imageView);
        nuevo.setTooltip(new Tooltip("Nuevo Producto"));
        nuevo.setOnAction(e -> {
            nuevo();

        });
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);

// gridpane
        gp_generic = new GridPane();
    //    gp_generic.add(new Label("Cups: "), 0, 0, 1, 1);
     //   gp_generic.add(sb_cups, 1, 0, 1, 1);
        gp_generic.add(new Label("Código:"), 0, 0, 1, 1);
        gp_generic.add(new Label("Nombre: "), 0, 1, 1, 1);
        gp_generic.add(codigo, 1, 0, 1, 1);
        gp_generic.add(nombre, 1, 1, 1, 1);
        gp_generic.add(new Label("Precio 2015:"), 0, 2, 1, 1);
        gp_generic.add(new Label("Precio 2016:"), 0, 3, 1, 1);
        gp_generic.add(new Label("Precio 2017:"), 0, 4, 1, 1);
        gp_generic.add(new Label("Precio 2018:"), 0, 5, 1, 1);
        gp_generic.add(new Label("Precio 2020:"), 0, 6, 1, 1);
        gp_generic.add(new Label("Precio 2001:"), 0, 7, 1, 1);
         
        gp_generic.add(precio2015, 1, 2, 1, 1);
        gp_generic.add(precio2016, 1, 3, 1, 1);
        gp_generic.add(precio2017, 1, 4, 1, 1);
        gp_generic.add(precio2018, 1, 5, 1, 1);
        gp_generic.add(precio2020, 1, 6, 1, 1);
        gp_generic.add(precio2001, 1, 7, 1, 1);
          gp_generic.add(new Label("Precio 2015 Soat:"), 0, 8, 1, 1);
        gp_generic.add(new Label("Precio 2016 Soat:"), 0, 9, 1, 1);
        gp_generic.add(new Label("Precio 2017 Soat:"), 0, 10, 1, 1);
        gp_generic.add(new Label("Precio 2018 Soat:"), 0, 11, 1, 1);
        gp_generic.add(new Label("Precio 2001 Soat:"), 0, 12, 1, 1);
         gp_generic.add(new Label("Precio 2020 Soat:"), 0, 13, 1, 1);
          gp_generic.add(precio2015_soat, 1, 8, 1, 1);
        gp_generic.add(precio2016_soat, 1, 9, 1, 1);
        gp_generic.add(precio2017_soat, 1, 10, 1, 1);
        gp_generic.add(precio2018_soat, 1, 11, 1, 1);
        gp_generic.add(precio2001_soat, 1, 12, 1, 1);
        gp_generic.add(precio2020_soat, 1, 13, 1, 1);
        gp_generic.addRow(14, la_especialidad, LoadChoiceBoxGeneral.getCb_agnespecialidades());
        gp_generic.add(hb_botones, 0, 15, 4, 1);
        //gp_generic.add(sp_cups, 1, 1, 3, 4);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(2);
        gp_generic.setVgap(2);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_LEFT);
        // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        gp_generic.autosize();
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinWidth(gp_generic.getLayoutBounds().getWidth());
        Gp_Message.setMaxHeight(40);
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.setAlignment(Pos.TOP_CENTER);
        //stack.setMinWidth(700);
        stack.getChildren().addAll(gp_generic, Gp_Message);
        crearoeditar();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void nuevo() {
        SihicApp.s_producto = null;
        SihicApp.s_producto = new Producto();
        empty_field();
    }

    public void save() throws InterruptedException, ParseException {
        validar_formulario();
        if ((IndexSearcher.searchProductosServicios(codigo.getText())).size() <= 0 || SihicApp.s_producto.getId() != null) {
            if (permitirproceso) {
                set_datos();
                try {

                    if (SihicApp.s_producto.getId() == null) {
                        SihicApp.productoControllerClient.create();
                        L_Message.setText("Registro Almacenado");
                    } else {
                        SihicApp.productoControllerClient.update();
                        L_Message.setText("Registro modificado");
                    }
                    s_productoControllerClient.findproductosall(null);
                  
                    AdminServicios.getRecords();
                    Task_Message = () -> {
                        try {
                            SetMessage();
                            SihicApp.li_producto = SihicApp.productoControllerClient.findserviciosall(null);
                              s_productoControllerClient.li_findproductosKardex(null);
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
        } else {
            L_Message.setText("Ya existe un producto con este Código");
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

    private void set_datos() {
        GenCategoriasProductos genCategoriasProductos = new GenCategoriasProductos();
        genCategoriasProductos.setId(Long.valueOf(1));
        SihicApp.s_producto.setCodigo(codigo.getText().trim());
        SihicApp.s_producto.setNombre(nombre.getText());
        SihicApp.s_producto.setPrecio2015(new BigDecimal(precio2015.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2016(new BigDecimal(precio2016.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2017(new BigDecimal(precio2017.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2018(new BigDecimal(precio2018.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2020(new BigDecimal(precio2020.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2001(new BigDecimal(precio2001.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2015_soat(new BigDecimal(precio2015_soat.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2016_soat(new BigDecimal(precio2016_soat.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2017_soat(new BigDecimal(precio2017_soat.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2018_soat(new BigDecimal(precio2018_soat.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2001_soat(new BigDecimal(precio2001_soat.getText().trim().replaceAll(",", ".")));
        SihicApp.s_producto.setPrecio2020_soat(new BigDecimal(precio2020_soat.getText().trim().replaceAll(",", ".")));
    
        // SihicApp.s_producto.setHclCups(SihicApp.s_hclCups);
        SihicApp.s_producto.setGenCategoriasProductos(genCategoriasProductos);
        SihicApp.s_producto.setAgnEspecialidades(LoadChoiceBoxGeneral.getV_agnespecialidades().get(LoadChoiceBoxGeneral.getCb_agnespecialidades().getSelectionModel().getSelectedIndex()));

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getLength() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
                        permitirproceso = false;
                        general.setStyle("-fx-background-color:#ff1600");

                    }
                }

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    if (general.getProperties().get("requerido") != null) {
                        if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                            permitirproceso = false;
                            general.getStylesheets().add(0, "/sihic/personal.css");
                            general.getStylesheets().set(0, "/sihic/personal.css");
                        }
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
                            if (general.getProperties().get("requerido") != null) {
                                if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                                    general.getStylesheets().set(0, SihicApp.hojaestilos);

                                }
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
// A change listener to track the change in selected index

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

    }

    public void getDatos() {
     
        codigo.setText(SihicApp.s_producto.getCodigo());
        nombre.setText(SihicApp.s_producto.getNombre());
        if (SihicApp.s_producto.getPrecio2015() != null) {
            precio2015.setText(SihicApp.s_producto.getPrecio2015().toString().replaceAll("\\.", ","));
        }
        if (SihicApp.s_producto.getPrecio2016() != null) {
            precio2016.setText(SihicApp.s_producto.getPrecio2016().toString().replaceAll("\\.", ","));
        }
        if (SihicApp.s_producto.getPrecio2017() != null) {
            precio2017.setText(SihicApp.s_producto.getPrecio2017().toString().replaceAll("\\.", ","));
        }
        if (SihicApp.s_producto.getPrecio2018() != null) {
            precio2018.setText(SihicApp.s_producto.getPrecio2018().toString().replaceAll("\\.", ","));
        }
         if (SihicApp.s_producto.getPrecio2020() != null) {
            precio2020.setText(SihicApp.s_producto.getPrecio2020().toString().replaceAll("\\.", ","));
        }
        if (SihicApp.s_producto.getPrecio2001() != null) {
            precio2001.setText(SihicApp.s_producto.getPrecio2001().toString().replaceAll("\\.", ","));
        }
         if (SihicApp.s_producto.getAgnEspecialidades()!= null) {
            LoadChoiceBoxGeneral.getCb_agnespecialidades().setValue(SihicApp.s_producto.getAgnEspecialidades().getNombre());
            
        }
          if (SihicApp.s_producto.getPrecio2015_soat()!= null) {
            precio2015_soat.setText(SihicApp.s_producto.getPrecio2015_soat().toString().replaceAll("\\.", ","));
        }
        if (SihicApp.s_producto.getPrecio2016_soat()!= null) {
            precio2016_soat.setText(SihicApp.s_producto.getPrecio2016_soat().toString().replaceAll("\\.", ","));
        }
        if (SihicApp.s_producto.getPrecio2017_soat()!= null) {
            precio2017_soat.setText(SihicApp.s_producto.getPrecio2017_soat().toString().replaceAll("\\.", ","));
        }
        if (SihicApp.s_producto.getPrecio2018_soat()!= null) {
            precio2018_soat.setText(SihicApp.s_producto.getPrecio2018_soat().toString().replaceAll("\\.", ","));
        }
        if (SihicApp.s_producto.getPrecio2001_soat()!= null) {
            precio2001_soat.setText(SihicApp.s_producto.getPrecio2001_soat().toString().replaceAll("\\.", ","));
        }
        if (SihicApp.s_producto.getPrecio2020_soat()!= null) {
            precio2020_soat.setText(SihicApp.s_producto.getPrecio2020_soat().toString().replaceAll("\\.", ","));
        }
         if (SihicApp.s_producto.getAgnEspecialidades()!= null) {
            LoadChoiceBoxGeneral.getCb_agnespecialidades().setValue(SihicApp.s_producto.getAgnEspecialidades().getNombre());
            
        }
        //sb_cups.setText(SihicApp.s_producto.getHclCups().getCodigo() + " || " + SihicApp.s_producto.getHclCups().getDescripcion());

    }

    public void crearoeditar() {

        if (SihicApp.s_producto != null) {
            getDatos();

        } else {
            SihicApp.s_producto = new Producto();
            nuevo();
        }

    }

    private void empty_field() {
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    general.getSelectionModel().select(-1);

                } else {
                    if (n.getClass() == TextArea.class) {
                        TextArea general = (TextArea) n;

                        general.setText("");

                    } else {
                        if (n.getClass() == RadioButton.class) {
                            RadioButton general = (RadioButton) n;

                            general.setSelected(false);

                        } else {
                            if (n.getClass() == CheckBox.class) {
                                CheckBox general = (CheckBox) n;

                                general.setSelected(false);

                            }
                        }
                    }
                }
            }
        }

    }

    public String getcerosizquierda(String codigo) {

        String cerosizq = "";

        for (int i = 4; i > codigo.length(); i--) {
            cerosizq = cerosizq + "0";
        }
        cerosizq = cerosizq + codigo;
        return cerosizq;
    }

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
