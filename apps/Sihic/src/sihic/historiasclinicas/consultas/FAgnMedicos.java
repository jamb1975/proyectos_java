package sihic.historiasclinicas.consultas;
import sihic.SihicApp;
import java.io.IOException;
import java.text.ParseException;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.AgnEspecialidades;
import modelo.AgnMedicos;
import modelo.AgnMedicosEspecialidades;
import modelo.GenPersonas;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

public class FAgnMedicos extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private AgnMedicos agnMedicos;
    private GridPane gp_generic;
    private Label la_cedula;
    private Label la_noregistro;
    private Label la_primernombre;
    private Label la_segundonombre;
    private Label la_primerapellido;
    private Label la_segundoapellido;
    private Label la_celular;
    private Label la_direccion;
    private Label la_email;
    private Label la_especialidad;
    private TextField cedula;
    private TextField noregistro;
    private TextField primernombre;
    private TextField segundonombre;
    private TextField primerapellido;
    private TextField segundoapellido;
    private TextField celular;
    private TextField direccion;
    private TextField email;
    private HclHistoriasClinicasControllerClient agnmedicosControllerClient;
    private Button save;
    private Button nuevo;
    private Button especialidad;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private TableView tv_especialidades;
    private TableColumn<AgnMedicosEspecialidades, String> tc_codigo;
    private TableColumn<AgnMedicosEspecialidades, String> tc_nombre;
    private AgnEspecialidades agnMEspecialidades;
    private static ObservableList ol_especialidades = FXCollections.observableArrayList();
    private ContextMenu contextMenu;
    private MenuItem itemDelete;
    private ImageView imageView;

    public Parent createContent() throws IOException, ParseException {
        agnmedicosControllerClient = new HclHistoriasClinicasControllerClient();
        agnMEspecialidades = new AgnEspecialidades();
        //***********************************************  
        imageView = null;
        imageView = new ImageView("/images/delete.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        itemDelete = new MenuItem("Eliminar", imageView);
        itemDelete.setOnAction(e -> {
            try {
                removeespecialidad();
            } catch (InterruptedException ex) {
                Logger.getLogger(FAgnMedicos.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        contextMenu = new ContextMenu(itemDelete);
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        la_cedula = new Label("Cédula:");
        la_noregistro = new Label("N° Registro:");
        la_cedula = new Label("Cédula:");
        la_primernombre = new Label("Primer nombre:");
        la_segundonombre = new Label("Segundo nombre:");
        la_primerapellido = new Label("Primer apellido:");
        la_segundoapellido = new Label("Segundo apellido:");
        la_celular = new Label("Celular:");
        la_direccion = new Label("Dirección:");
        la_email = new Label("Email:");
        la_especialidad = new Label("Especialidad:");
        cedula = new TextField();
        noregistro = new TextField();
        primernombre = new TextField();
        segundonombre = new TextField();
        primerapellido = new TextField();
        segundoapellido = new TextField();
        celular = new TextField();
        direccion = new TextField();
        email = new TextField();
        cedula.getProperties().put("requerido", true);
        noregistro.getProperties().put("requerido", true);
        primernombre.getProperties().put("requerido", true);
        primerapellido.getProperties().put("requerido", true);
        celular.getProperties().put("requerido", true);
        LoadChoiceBoxGeneral.getCb_agnespecialidades().getProperties().put("requerido", false);
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        save = new Button("", imageView);
        hb_botones = new HBox(5);
        tv_especialidades = new TableView();
        tc_codigo = new TableColumn();
        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        tc_codigo.setMinWidth(100);
        tc_codigo.setText("Código");
        tc_codigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAgnEspecialidades().getCodigo()));
        tc_codigo.setCellFactory(column -> {
            return new TableCell<AgnMedicosEspecialidades, String>() {
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
        tc_nombre.setText("Especialidad");
        tc_nombre.setMinWidth(350);
        tc_nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAgnEspecialidades().getNombre()));
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        tv_especialidades.getColumns().addAll(tc_codigo, tc_nombre);
        tv_especialidades.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_especialidades.setMaxHeight(150);
        tv_especialidades.setContextMenu(contextMenu);
        save.setOnKeyPressed(e -> {

            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();
                    nuevo.requestFocus();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FAgnMedicos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        nuevo = new Button("", imageView);
        nuevo.setOnKeyPressed(e -> {

            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    nuevahora();
                } catch (ParseException ex) {
                    Logger.getLogger(FAgnMedicos.class.getName()).log(Level.SEVERE, null, ex);
                }
                noregistro.requestFocus();
            }

        });

        nuevo.setOnAction(e -> {
            try {
                nuevahora();
            } catch (ParseException ex) {
                Logger.getLogger(FAgnMedicos.class.getName()).log(Level.SEVERE, null, ex);
            }
            noregistro.requestFocus();

        });
        noregistro.setOnAction(e -> {
            cedula.requestFocus();

        });
        cedula.setOnAction(e -> {
            save.requestFocus();
        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        especialidad = new Button("", imageView);
        especialidad.setOnAction(e -> {
            try {
                addespecialidad();
            } catch (InterruptedException ex) {
                Logger.getLogger(FAgnMedicos.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);
        gp_generic = new GridPane();
        gp_generic.addRow(0, la_noregistro, noregistro, la_cedula, cedula);
        gp_generic.addRow(1, la_primernombre, primernombre, la_segundonombre, segundonombre);
        gp_generic.addRow(2, la_primerapellido, primerapellido, la_segundoapellido, segundoapellido);
        gp_generic.addRow(3, la_celular, celular, la_direccion, direccion);
        gp_generic.addRow(4, la_email, email);
        gp_generic.add(hb_botones, 0, 6, 4, 1);
        gp_generic.add(la_especialidad, 0, 7);
        gp_generic.add(LoadChoiceBoxGeneral.getCb_agnespecialidades(), 1, 7);
        gp_generic.add(especialidad, 2, 7);
        gp_generic.add(tv_especialidades, 0, 8, 4, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
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
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic, Gp_Message);
        crearoeditar();
        getListEspecialidades();
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
            set_datos();
            try {
                if (agnMedicos.getId() == null) {
                    agnmedicosControllerClient.saveMedicos(agnMedicos);

                    L_Message.setText("Registro Almacenado");
                } else {
                    agnMedicos = agnmedicosControllerClient.updateMedicos(agnMedicos);
                    L_Message.setText("Registro modificado");
                }
                getListEspecialidades();
                AdminAgnMedicos.getListMedicos();
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

    private void set_datos() {
        agnMedicos.setGenPersonas(new GenPersonas());
        agnMedicos.setNumeroRegistro(noregistro.getText().trim());
        agnMedicos.getGenPersonas().setDocumento(cedula.getText().trim());
        agnMedicos.getGenPersonas().setPrimerNombre(primernombre.getText().trim());
        agnMedicos.getGenPersonas().setSegundoNombre(segundonombre.getText().trim());
        agnMedicos.getGenPersonas().setPrimerApellido(primerapellido.getText().trim());
        agnMedicos.getGenPersonas().setSegundoApellido(segundoapellido.getText().trim());
        agnMedicos.getGenPersonas().setTelefono(celular.getText().trim());
        agnMedicos.getGenPersonas().setDireccion(direccion.getText().trim());
        agnMedicos.getGenPersonas().setEMail(email.getText().trim());
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

    public void crearoeditar() throws ParseException {
        agnMedicos = AdminAgnMedicos.getAgnmedicos();
        if (agnMedicos.getId() != null) {
            noregistro.setText(agnMedicos.getNumeroRegistro());
            cedula.setText(String.valueOf(agnMedicos.getGenPersonas().getDocumento()));
            primernombre.setText(String.valueOf(agnMedicos.getGenPersonas().getPrimerNombre()));
            segundonombre.setText(String.valueOf(agnMedicos.getGenPersonas().getSegundoNombre()));
            primerapellido.setText(String.valueOf(agnMedicos.getGenPersonas().getPrimerApellido()));
            segundoapellido.setText(String.valueOf(agnMedicos.getGenPersonas().getSegundoApellido()));
            celular.setText(String.valueOf(agnMedicos.getGenPersonas().getTelefono()));
            direccion.setText(String.valueOf(agnMedicos.getGenPersonas().getDireccion()));
            email.setText(String.valueOf(agnMedicos.getGenPersonas().getEMail()));

        } else {
            nuevahora();
        }

    }

    public void nuevahora() throws ParseException {
        agnMedicos = null;
        agnMedicos = new AgnMedicos();
        noregistro.setText("");
        cedula.setText("");
        primernombre.setText("");
        segundonombre.setText("");
        primerapellido.setText("");
        celular.setText("");
        segundoapellido.setText("");
        direccion.setText("");
        email.setText("");
        getListEspecialidades();

    }

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void addespecialidad() throws InterruptedException {
        if (LoadChoiceBoxGeneral.getCb_agnespecialidades().getSelectionModel().getSelectedIndex() >= 0) {
            agnMEspecialidades = LoadChoiceBoxGeneral.getV_agnespecialidades().get(LoadChoiceBoxGeneral.getCb_agnespecialidades().getSelectionModel().getSelectedIndex());
            agnMedicos.addespecialidad(agnMEspecialidades);
            save();
        }
    }

    private void removeespecialidad() throws InterruptedException {
        if (tv_especialidades.getSelectionModel().getSelectedItem() != null) {
            agnMEspecialidades = ((AgnMedicosEspecialidades) tv_especialidades.getSelectionModel().getSelectedItem()).getAgnEspecialidades();
            agnMedicos.removeespecialidad(agnMEspecialidades);
            save();
        }
    }

    public void getListEspecialidades() throws ParseException {
        //colocamos los datos

        try {

            ol_especialidades.clear();

            ol_especialidades.addAll(agnMedicos.getMedicosEspecialidadeses().toArray());
            // Ta_KardexProducto.getItems().clear();
            tv_especialidades.setItems(ol_especialidades);
        } catch (Exception e) {

        }
    }
}
