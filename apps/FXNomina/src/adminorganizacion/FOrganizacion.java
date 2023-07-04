package adminorganizacion;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import modelo.GenMunicipios;
import modelo.GenUnidadesOrganizacion;
import fxnomina.FXNomina;
import general.LoadChoiceBoxGeneral;


/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FOrganizacion extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;

    private GenUnidadesOrganizacion genUnidadesOrganizacion;
    private GridPane gp_generic;
    private Label la_genDptos;
    private Label la_genMunicipios;
    private ChoiceBox genMunicipios;
    private Vector<GenMunicipios> v_genMunicipios = new Vector<GenMunicipios>();
       private Label la_codigo;
    private TextField codigo;
    private Label la_nit;
    private TextField nit;
    private Label la_nombre;
    private TextField nombre;
    private Label la_nivelatencion;
    private ChoiceBox nivelatencion;
    private Label la_telefono;
    private TextField telefono;
    private Label la_direccion;
    private TextField direccion;
    private Label la_sigla;
    private TextField sigla;
      private Button save;
    private Button nuevo;
   
    private Stage stage;
    private HBox hb_botones;
    private ImageView logo;
    private Button bu_logo;
    private Image loadlogo;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private File fileLogo;
    private FileChooser fileChooser = new FileChooser();
    private WritableImage wi;
    private BufferedImage bi;

    public Parent createContent() throws IOException {
       
        genUnidadesOrganizacion = new GenUnidadesOrganizacion();

        logo = new ImageView();
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        //***********************************************  
      
        stack = new StackPane();
       
        genMunicipios = new ChoiceBox();
        genMunicipios.setMaxWidth(300);
        genMunicipios.getProperties().put("requerido", true);
        LoadChoiceBoxGeneral.getGen_departamentos_id().setMaxWidth(300);
        LoadChoiceBoxGeneral.getGen_departamentos_id().getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);

        la_codigo = new Label("Código:");
        la_nit = new Label("Nit:");
        la_nombre = new Label("Nombre:");
        la_nivelatencion = new Label("Nivel de Atención:");
        la_sigla = new Label("Sigla:");
        la_telefono = new Label("Telefono:");
        la_direccion = new Label("Dirección:");
        la_genDptos = new Label("Departamento:");
        la_genMunicipios = new Label("Ciudad:");
       
        codigo = new TextField();
        nit = new TextField();
        codigo.getProperties().put("requerido", true);
        nit = new TextField();
        nit.getProperties().put("requerido", true);
        nombre = new TextField();
        nombre.getProperties().put("requerido", true);

        nivelatencion = new ChoiceBox();
        nivelatencion.getItems().addAll("1", "2", "3", "4");
        nivelatencion.setMinWidth(300);
        direccion = new TextField();
        sigla = new TextField();
        telefono = new TextField();
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        save = new Button("", imageView);
        hb_botones = new HBox(5);

        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        imageView = null;
        imageView = new ImageView("/images/insert.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        nuevo = new Button("", imageView);

        nuevo.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        imageView = null;
        imageView = new ImageView("/images/img.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        bu_logo = new Button("", imageView);

        bu_logo.setOnAction(e -> {
            LoadImageLogo();
        });
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo, bu_logo);

// gridpane
        gp_generic = new GridPane();
        gp_generic.add(la_codigo, 0, 0);
        gp_generic.add(codigo, 1, 0);
        gp_generic.add(la_nit, 0, 1);
        gp_generic.add(nit, 1, 1);
        gp_generic.add(logo, 2, 0, 2, 4);
        gp_generic.add(la_nombre, 0, 2);
        gp_generic.add(nombre, 1, 2);
        gp_generic.add(la_sigla, 0, 3);
        gp_generic.add(sigla, 1, 3);
        gp_generic.add(la_genDptos, 0, 4);
        gp_generic.add(LoadChoiceBoxGeneral.getGen_departamentos_id(), 1, 4);
        gp_generic.add(la_genMunicipios, 0, 5);
        gp_generic.add(genMunicipios, 1, 5);
        gp_generic.add(la_telefono, 0, 6);
        gp_generic.add(telefono, 1, 6);
        gp_generic.add(la_direccion, 0, 7);
        gp_generic.add(direccion, 1, 7);
        gp_generic.add(hb_botones, 0, 8, 3, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add(FXNomina.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        // gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_generic.getLayoutBounds().getHeight(), gp_generic.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(FXNomina.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic, Gp_Message);
        crearoeditar();
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
            set_datos_oranizacion();
            try {

                if (genUnidadesOrganizacion.getId() == null) {
                    fxnomina.FXNomina.genUnidadeOrganizacionControllerClient.saveOrganizacion(genUnidadesOrganizacion);
                    L_Message.setText("Registro Almacenado");
                } else {
                    genUnidadesOrganizacion = fxnomina.FXNomina.genUnidadeOrganizacionControllerClient.updateOrganizacion(genUnidadesOrganizacion);
                    L_Message.setText("Registro modificado");
                }
                AdminOrganizacion.getOrganizacion();
                FXNomina.loaddatosorganizacion_toobar();
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

    private void set_datos_oranizacion() {
        genUnidadesOrganizacion.setCodigo(codigo.getText().trim());
        genUnidadesOrganizacion.setNit(nit.getText().trim());
        genUnidadesOrganizacion.setNombre(nombre.getText());
        genUnidadesOrganizacion.setSigla(sigla.getText());
        genUnidadesOrganizacion.setTelefono(telefono.getText());
        genUnidadesOrganizacion.setDireccion(direccion.getText());
        
        genUnidadesOrganizacion.setGenMunicipios(v_genMunicipios.get(genMunicipios.getSelectionModel().getSelectedIndex()));
        //cargamos la imagen
        save_img();

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

                    if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
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
                                general.getStylesheets().set(0, FXNomina.hojaestilos);

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
        System.out.println("Indexchanged: old = " + oldValue + ", new = " + newValue);
        Load_GenMunicipios();
//gp_genpersonas.getChildren().remove(gen_municipios_id);
//gp_genpersonas.add(gen_municipios_id, 3, 3);

//gp_genpersonas.getChildren().remove(index-1);
    }

    public void Load_GenMunicipios() {

        genMunicipios.setMaxHeight(10);

        int i = 0;
        int index = LoadChoiceBoxGeneral.getGen_departamentos_id().getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Long id_dpto = LoadChoiceBoxGeneral.v_gen_departamentos.get(index);

            System.out.println("Indexchanged: index = " + index);

            if (FXNomina.getL_genmunicipios().size() > 0) {
                genMunicipios.getItems().clear();
                v_genMunicipios.clear();
                for (GenMunicipios d : FXNomina.getL_genmunicipios()) {
                    if (id_dpto.equals(d.getGenDepartamentos().getId())) {
                        v_genMunicipios.add(i, d);
                        ov.add(i, d.getNombre());

                    }

                }
                genMunicipios.setItems(ov);
            }
        }
    }

    public void LoadImageLogo() {

        fileLogo = fileChooser.showOpenDialog(stage);

        if (fileLogo != null) {
            byte[] img2;
            try {
                bi = ImageIO.read(fileLogo);

                wi = SwingFXUtils.toFXImage(bi, wi);
                loadlogo = wi;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        logo.setImage(loadlogo);
    }

    public void save_img() {
        byte[] data;
        InputStream in;
        try {
            in = new FileInputStream(fileLogo);
            data = new byte[in.available()];
            in.read(data);
            genUnidadesOrganizacion.setLogo(data);
        } catch (Exception e) {

        }
    }

    public void check_exist_organizacion() {

        //genUnidadesOrganizacion=SihicApp.genUnidadesOrganizacion;
        codigo.setText(genUnidadesOrganizacion.getCodigo());
        nit.setText(genUnidadesOrganizacion.getNit());
        nombre.setText(genUnidadesOrganizacion.getNombre());
        sigla.setText(genUnidadesOrganizacion.getSigla());
        telefono.setText(genUnidadesOrganizacion.getTelefono());
        direccion.setText(genUnidadesOrganizacion.getDireccion());
       
        LoadChoiceBoxGeneral.getGen_departamentos_id().getSelectionModel().select(LoadChoiceBoxGeneral.v_gen_departamentos.indexOf(genUnidadesOrganizacion.getGenMunicipios().getGenDepartamentos().getId()));
        genMunicipios.getSelectionModel().select(v_genMunicipios.indexOf(genUnidadesOrganizacion.getGenMunicipios()));
        try {
            InputStream in = new ByteArrayInputStream(genUnidadesOrganizacion.getLogo());
            bi = ImageIO.read(in);
            wi = new WritableImage(1, 1);
            wi = SwingFXUtils.toFXImage(bi, wi);
            //img=new ImageView();
            loadlogo = wi;
            logo.setImage(loadlogo);
        } catch (Exception e) {

        }

    }

    public void crearoeditar() {
        genUnidadesOrganizacion = AdminOrganizacion.getGenUnidadesOrganizacion();
        if (genUnidadesOrganizacion != null) {
            check_exist_organizacion();

        }
        else
        {
            genUnidadesOrganizacion=new GenUnidadesOrganizacion();
        }

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
