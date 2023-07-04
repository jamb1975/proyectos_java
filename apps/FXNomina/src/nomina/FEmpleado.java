package nomina;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modelo.Empleados;
import modelo.GenEapb;
import modelo.GenMunicipios;
import modelo.GenPersonas;
import modelo.GenSexo;
import modelo.GenTiposDocumentos;
import fxnomina.PageBrowser;
import fxnomina.SearchPopover;
import fxnomina.FXNomina;
import control.SearchBox;
import general.LoadChoiceBoxGeneral;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import util.UtilDate;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FEmpleado extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GenPersonas genPersonas;
    private GridPane gp_genpersonas;
    private SearchBox sb_genmunicipios = new SearchBox();
    private SearchPopover sp_genmunicipios;
    private Label la_primer_apellido;
    private TextField primer_apellido;
    private Label la_segundo_apellido;
    private TextField segundo_apellido;
    private Label la_primer_nombre;
    private TextField primer_nombre;
    private Label la_segundo_nombre;
    private TextField segundo_nombre;
    private Label la_barrio;
    private TextField barrio;
    private Label la_direccion;
    private TextField direccion;
    private Label la_fechanacimiento;
    private DatePicker fechanacimiento;
    private Label la_email;
    private TextField email;
    private Label la_documento;
    private TextField documento;
    private Label la_telefono;
    private TextField telefono;
    private Label la_gentiposdocumentos;
    private Label la_gensexo;
    private Label la_genmunicipios;
    private Label la_cargo;
    private CheckBox activo = new CheckBox("");
    private Button save;
    private Button nuevo;
    private Button tributaria;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;

    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private Label la_geneps=new Label("Eps:");
    private SearchBox sb_geneapb=new SearchBox();
    private SearchPopover sp_geneapb;
    private Label la_genpension=new Label("Pensión:");
    private SearchBox sb_pension=new SearchBox();
    private SearchPopover sp_genpension;
    private Label la_genarl=new Label("Arl:");
    private SearchBox sb_arl=new SearchBox();
    private SearchPopover sp_arl;
    private Label la_cajacomp=new Label("Caja Comp. Fam:");
    private SearchBox sb_cajacomp=new SearchBox();
    private SearchPopover sp_cajacomp;
    private String appPathGeneric;
    private Class clzGeneric;
    private Object appClassGeneric;
    private Stage stageGeneric = new Stage(StageStyle.DECORATED);
    private Scene sceneGeneric = null;
    private Parent P_Generic;
    private static ChoiceBox cb_rh = new ChoiceBox();
    private static Image loadfoto;
    private static File filefoto;
    private static FileChooser fileChooser = new FileChooser();
    private static WritableImage wi;
    private static BufferedImage bi;
    private static Button bu_foto;
    private static Button bu_enrrolarfotohuella=new Button();
    private static Button bu_verificarhuella=new Button();
    private static ImageView foto;
    private String appClassfotohuella;
    private Class  clzfotohuella;
    private Object appfotohuella;
    private Parent parentfotohuella;
    public static Stage  stagefotohuella = new Stage(StageStyle.DECORATED);
    Scene scenefotohuella = null;
    ImageView imageView;
    public Parent createContent() throws IOException {
         foto=new ImageView("/images/foto.png");
      foto.setFitHeight(100);
      foto.setFitWidth(100);
         imageView=null;
         imageView=new ImageView("/images/fotohuella.png");
         imageView.setFitHeight(30);
         imageView.setFitWidth(30);
         bu_enrrolarfotohuella.setGraphic(imageView);
         bu_enrrolarfotohuella.setTooltip(new Tooltip("Capturar Foto y Huella"));
         bu_enrrolarfotohuella.setOnAction(e->{try {
            showfotohuella();
             } catch (Exception ex) {
                 Logger.getLogger(FEmpleado.class.getName()).log(Level.SEVERE, null, ex);
             }
        
         });
         imageView=null;
         imageView=new ImageView("/images/verificarhuella.png");
         imageView.setFitHeight(30);
         imageView.setFitWidth(30);
         bu_verificarhuella.setGraphic(imageView);
         bu_verificarhuella.setTooltip(new Tooltip("Verificar Huella"));
         bu_verificarhuella.setOnAction(e->{try {
            showverificarfotohuella();
             } catch (Exception ex) {
                 Logger.getLogger(FEmpleado.class.getName()).log(Level.SEVERE, null, ex);
             }
        
         });
        imageView=null;
        imageView=new ImageView("/images/img.png");
         imageView.setFitHeight(30);
         imageView.setFitWidth(30);
        
         bu_foto =new Button("",imageView);
      
        bu_foto.setTooltip(new Tooltip("Cargas Foto de Empleado"));
        bu_foto.setOnAction(e->{LoadImageFoto();
        
         });
        sp_genmunicipios = new SearchPopover(sb_genmunicipios, new PageBrowser(), FXNomina.s_genMunicipios, false);
        sb_genmunicipios.getProperties().put("requerido", true);
        sp_geneapb = new SearchPopover(sb_geneapb, new PageBrowser(), FXNomina.s_genEapb, true);
        sp_geneapb.getProperties().put("requerido", true);
        sb_pension.getProperties().put("requerido", true);
        sp_genpension = new SearchPopover(sb_pension, new PageBrowser(), FXNomina.s_genEapb, true);
        sb_arl.getProperties().put("requerido", true);
        sp_arl = new SearchPopover(sb_arl, new PageBrowser(), FXNomina.s_genEapb, true);
         sb_cajacomp.getProperties().put("requerido", true);
       sp_cajacomp = new SearchPopover(sb_cajacomp, new PageBrowser(), FXNomina.s_genEapb, true);
         sb_geneapb.setOnAction(e -> {
            FXNomina.empleados.setGenEPS(FXNomina.s_genEapb);
        });
         sb_pension.setOnAction(e -> {
            FXNomina.empleados.setGenpension(FXNomina.s_genEapb);
        });
         sb_arl.setOnAction(e -> {
            FXNomina.empleados.setGenarl(FXNomina.s_genEapb);
        });
         sb_cajacomp.setOnAction(e -> {
            FXNomina.empleados.setCajacomp(FXNomina.s_genEapb);
        });
        sb_arl.getProperties().put("requerido", true);
        cb_rh.getItems().add(0, "0+");
        cb_rh.getItems().add(1, "0-");
        cb_rh.getItems().add(0, "A+");
        cb_rh.getItems().add(0, "A-");
        cb_rh.getItems().add(0, "B+");
        cb_rh.getItems().add(0, "B-");
        cb_rh.getItems().add(0, "AB+");
        cb_rh.getItems().add(0, "AB-");
       
        stack = new StackPane();

         LoadChoiceBoxGeneral.getGen_tipos_documentos_id().setMaxWidth(200);
        la_gentiposdocumentos = new Label("Tipo Documento:");
        la_documento = new Label("No Identificación:");
        
        la_primer_nombre = new Label("Primer Nombre:");
        la_segundo_nombre = new Label("Segundo Nombre:");
        la_primer_apellido = new Label("Primer Apellido:");
        la_segundo_apellido = new Label("Segundo Apellido:");
        la_telefono = new Label("Telefono:");

        la_genmunicipios = new Label("Ciudad:");
        la_direccion = new Label("Dirección:");
        la_barrio = new Label("Barrio:");
        la_email = new Label("Email:");
        la_gensexo = new Label("Sexo:");

        
        la_fechanacimiento = new Label("Fecha de Nacimiento:");
       
        sb_genmunicipios.setMaxWidth(250);
        documento = new TextField();
        documento.getProperties().put("requerido", true);
        documento.setMaxWidth(200);
        primer_nombre = new TextField();
        primer_nombre.getProperties().put("requerido", true);
        primer_nombre.setMaxWidth(200);
        segundo_nombre = new TextField();
        segundo_nombre.setMaxWidth(200);
        primer_apellido = new TextField();
        primer_apellido.setMaxWidth(200);
        primer_apellido.getProperties().put("requerido", true);
        segundo_apellido = new TextField();
        segundo_apellido.setMaxWidth(200);
        telefono = new TextField();
        telefono.getProperties().put("requerido", true);
        telefono.setMaxWidth(200);
        direccion = new TextField();
        direccion.setMaxWidth(200);
        barrio = new TextField();
        email = new TextField();
        email.setMaxWidth(200);
        LoadChoiceBoxGeneral.cb_cargosentidad.setMaxWidth(200);
        fechanacimiento = new DatePicker(LocalDate.now());
        fechanacimiento.getProperties().put("requerido", true);
        barrio.setMaxWidth(200);
         imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        save = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        nuevo = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/tributaria.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        tributaria = new Button("", imageView);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo,bu_foto,bu_enrrolarfotohuella,bu_verificarhuella);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }
        });
        tributaria.setOnAction(e -> {
            try {
                showinformaciontributaria();
            } catch (NoSuchMethodException ex) {
            } catch (IllegalAccessException ex) {
            } catch (IllegalArgumentException ex) {
            } catch (InvocationTargetException ex) {
            } catch (ClassNotFoundException ex) {
            } catch (InstantiationException ex) {
            }
        });
        
// gridpane  sp_geneapb = new SearchPopover(sb_eapb,new PageBrowser());
        gp_genpersonas = new GridPane();
        gp_genpersonas.setMaxWidth(700);
        gp_genpersonas.add(la_gentiposdocumentos, 0, 0);
        gp_genpersonas.add(LoadChoiceBoxGeneral.getGen_tipos_documentos_id(), 1, 0);
        gp_genpersonas.add(la_documento, 2, 0);
        gp_genpersonas.add(documento, 3, 0);
        gp_genpersonas.add(foto, 4, 0,1,5);
        gp_genpersonas.add(la_primer_apellido, 0, 1);
        gp_genpersonas.add(primer_apellido, 1, 1);
        gp_genpersonas.add(la_segundo_apellido, 2, 1);
        gp_genpersonas.add(segundo_apellido, 3, 1);
        gp_genpersonas.add(la_primer_nombre, 0, 2);
        gp_genpersonas.add(primer_nombre, 1, 2);
        gp_genpersonas.add(la_segundo_nombre, 2, 2);
        gp_genpersonas.add(segundo_nombre, 3, 2);
        gp_genpersonas.add(la_genmunicipios, 0, 3);
        gp_genpersonas.add(sb_genmunicipios, 1, 3);
        gp_genpersonas.add(la_direccion, 2, 3);
        gp_genpersonas.add(direccion, 3, 3);
        gp_genpersonas.add(la_barrio, 0, 4);
        gp_genpersonas.add(barrio, 1, 4);
        gp_genpersonas.add(la_gensexo, 2, 4);
        gp_genpersonas.add(LoadChoiceBoxGeneral.getGen_sexo_id(), 3, 4);
        gp_genpersonas.add(la_email, 0, 5);
        gp_genpersonas.add(email, 1, 5);
        gp_genpersonas.add(la_telefono, 2, 5);
        gp_genpersonas.add(telefono, 3, 5);
        gp_genpersonas.add(la_fechanacimiento, 0, 6);
        gp_genpersonas.add(fechanacimiento, 1, 6);
        gp_genpersonas.add(new Label("Rh:"), 2, 6);
        gp_genpersonas.add(cb_rh, 3, 6);
        gp_genpersonas.add(new Label("Cargo:"), 0, 7);
        gp_genpersonas.add(LoadChoiceBoxGeneral.cb_cargosentidad, 1, 7);
        gp_genpersonas.add(new Label("Activo:"), 2, 7);
        gp_genpersonas.add(activo, 3, 7);
        gp_genpersonas.add(la_geneps, 0, 8);
        gp_genpersonas.add(sb_geneapb, 1, 8);
        gp_genpersonas.add(la_genpension, 2, 8);
        gp_genpersonas.add(sb_pension, 3, 8);
        gp_genpersonas.add(la_genarl, 0, 9);
        gp_genpersonas.add(sb_arl, 1, 9);
        gp_genpersonas.add(la_cajacomp, 2, 9);
        gp_genpersonas.add(sb_cajacomp, 3, 9);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_genpersonas.add(hb_botones, 0, 11, 5, 1);
        gp_genpersonas.getStylesheets().add(FXNomina.hojaestilos);
        gp_genpersonas.getStyleClass().add("background");
        gp_genpersonas.setHgap(5);
        gp_genpersonas.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_genpersonas.alignmentProperty().setValue(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_genpersonas.getLayoutBounds().getHeight(), gp_genpersonas.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(FXNomina.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.getChildren().addAll(gp_genpersonas, Gp_Message);
        event_inputs();

        gp_genpersonas.add(sp_genmunicipios, 1, 4, 3, 8);
        gp_genpersonas.add(sp_geneapb, 2, 10, 2, 3);
        gp_genpersonas.add(sp_genpension, 1, 5, 3, 6);
        gp_genpersonas.add(sp_arl, 2, 5, 2, 6);
        gp_genpersonas.add(sp_cajacomp, 1, 6, 3, 5);
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
            try {
                set_datos_genpersonas();
                if (FXNomina.empleados.getId() == null) {
                    FXNomina.empleadosControllerClient.create();
                } else {
                    FXNomina.empleadosControllerClient.update();
                }

                L_Message.setText("Registro Almacenado");
                Task_Message = () -> {
                    try {

                        SetMessage();
                        AdminEmpleados.getRecords();
                    } catch (Exception  ex) {
                         ex.printStackTrace();
                    } 
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            } catch (Exception e) {
                e.printStackTrace();
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

    private void set_datos_genpersonas() {
        int i = 0;

        genPersonas.setBarrio(barrio.getText());
        genPersonas.setDireccion(direccion.getText());
        genPersonas.setDocumento(documento.getText());
        genPersonas.setEMail(email.getText());
        genPersonas.setFechaCreacion(new Date());
        //colocar fecha de datepicker
        LocalDate localDate = fechanacimiento.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        genPersonas.setFechaNacimiento(Date.from(instant));
        genPersonas.setFoto("foto");

        //colocar estados civiles
      
        //
        //colocar etnias
        genPersonas.setGenMunicipios(FXNomina.s_genMunicipios);

        //colocar sexo
        GenSexo genSexo = new GenSexo();
        genSexo.setId(LoadChoiceBoxGeneral.extraer_id_choicebox(LoadChoiceBoxGeneral.getGen_sexo_id(), LoadChoiceBoxGeneral.getV_gen_sexo()));
        genPersonas.setGenSexo(genSexo);

        //colocar tipos documentos
        GenTiposDocumentos genTiposDocumentos = new GenTiposDocumentos();
        genPersonas.setGenTiposDocumentos(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().get(LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getSelectionModel().getSelectedIndex()));

        //colocar profesiones
       
        //colocar zona de residencia
        genPersonas.setPrimerApellido(primer_apellido.getText());
        genPersonas.setPrimerNombre(primer_nombre.getText());
        genPersonas.setSegundoApellido(segundo_apellido.getText());
        genPersonas.setSegundoNombre(segundo_nombre.getText());
        genPersonas.setTelefono(telefono.getText());
        genPersonas.seteMail(email.getText());
        
        FXNomina.empleados.setRh(cb_rh.getSelectionModel().getSelectedIndex());
        FXNomina.empleados.setCargosEntidad(LoadChoiceBoxGeneral.v_cargosentidad.get(LoadChoiceBoxGeneral.cb_cargosentidad.getSelectionModel().getSelectedIndex()));
      
        FXNomina.empleados.setActivo(activo.isSelected());
        //cargamos la imagen
        save_img();
          FXNomina.empleados.setGenPersonas(genPersonas);
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_genpersonas.getChildren().toArray()) {
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

                    if (general.getSelectionModel().getSelectedIndex() < 0 && general.getProperties().get("requerido") == null) {
                        permitirproceso = false;
                        general.getStylesheets().add(0, "/sihic/personal.css");
                        general.getStylesheets().set(0, "/sihic/personal.css");
                    }

                } else {
                    if (n.getClass() == SearchBox.class) {

                        SearchBox general = (SearchBox) n;
                        if (general.getProperties().get("requerido") != null) {
                            if(general.getText()!=null)
                           {        
                            if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
                                permitirproceso = false;
                                general.setStyle("-fx-background-color:#ff1600");
                            }
                           }
                            else
                            {
                               permitirproceso = false;
                                general.setStyle("-fx-background-color:#ff1600"); 
                            }
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
                for (Object n : gp_genpersonas.getChildren().toArray()) {
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

                            if (general.getSelectionModel().getSelectedIndex() < 0 && general.getProperties().get("requerido") == null) {
                                general.getStylesheets().set(0, FXNomina.hojaestilos);

                            }

                        } else {
                            if (n.getClass() == SearchBox.class) {

                                SearchBox general = (SearchBox) n;
                                if (general.getProperties().get("requerido") != null) {

                                    general.setStyle(null);
                                    general.getStyleClass().add("text-field");
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

    private void event_inputs() {
        LoadChoiceBoxGeneral.getGen_tipos_documentos_id().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                documento.requestFocus();
            }
        });
        documento.setOnAction(e -> {
            primer_nombre.requestFocus();
        });
        primer_nombre.setOnAction(e -> {
            primer_apellido.requestFocus();
        });
        primer_apellido.setOnAction(e -> {
            segundo_nombre.requestFocus();
        });
        segundo_nombre.setOnAction(e -> {
            segundo_apellido.requestFocus();
        });
        segundo_apellido.setOnAction(e -> {
            sb_genmunicipios.requestFocus();
        });

        direccion.setOnAction(e -> {
            barrio.requestFocus();
        });
        barrio.setOnAction(e -> {
            LoadChoiceBoxGeneral.getGen_sexo_id().requestFocus();
        });
        LoadChoiceBoxGeneral.getGen_sexo_id().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                LoadChoiceBoxGeneral.getGen_etnias_id().requestFocus();
            }
        });
     
        email.setOnAction(e -> {
            telefono.requestFocus();
        });

        

        fechanacimiento.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                LoadChoiceBoxGeneral.getGen_zona_residencia_id().requestFocus();
            }
        });
       
        save.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();
                    nuevo.requestFocus();
                } catch (InterruptedException ex) {
                }
            }
        });
        nuevo.setOnAction(e -> {
            nuevapersona();
            nuevo.requestFocus();
            LoadChoiceBoxGeneral.getGen_tipos_documentos_id().requestFocus();
        });
        nuevo.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {

                nuevapersona();
                nuevo.requestFocus();
                LoadChoiceBoxGeneral.getGen_tipos_documentos_id().requestFocus();

            }
        });

    }

    private void nuevapersona() {
        genPersonas = null;
        genPersonas = new GenPersonas();
        FXNomina.empleados = null;
        FXNomina.empleados = new Empleados();
          FXNomina.s_genEapb = null;
        FXNomina.s_genEapb = new GenEapb();
        for (Object n : gp_genpersonas.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");
                general.setDisable(false);
            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    general.getSelectionModel().select(-1);
                    general.setDisable(false);
                }

                if (n.getClass() == DatePicker.class) {
                    DatePicker general = (DatePicker) n;
                    general.setValue(LocalDate.now());
                    general.setDisable(false);
                }
                if (n.getClass() == SearchBox.class) {
                    SearchBox general = (SearchBox) n;
                     general.setText("");
                    general.setDisable(false);
                }
            }

        }

    }

    public void disabled_controles() {
        for (Object n : gp_genpersonas.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setDisable(true);
            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    general.setDisable(true);
                }

                if (n.getClass() == DatePicker.class) {
                    DatePicker general = (DatePicker) n;
                    general.setDisable(true);
                }
                if (n.getClass() == SearchBox.class) {
                    SearchBox general = (SearchBox) n;
                    general.setDisable(true);
                }
            }

        }
    }

    public void crearoeditar() {

        if (FXNomina.empleados != null) {
            if (FXNomina.empleados.getId() != null) {
                genPersonas = FXNomina.empleados.getGenPersonas();
                fxnomina.FXNomina.genPersonas = FXNomina.empleados.getGenPersonas();
                FXNomina.s_genMunicipios = genPersonas.getGenMunicipios();
                if(FXNomina.empleados.getGenEPS()!=null)
                {
                sb_geneapb.setText(FXNomina.empleados.getGenEPS().getNombre());
                 sp_geneapb.hide();
                }
                if(FXNomina.empleados.getGenpension()!=null)
                {
                sb_pension.setText(FXNomina.empleados.getGenpension().getNombre());
                 sp_genpension.hide();
                }
                if(FXNomina.empleados.getGenarl()!=null)
                {
                sb_arl.setText(FXNomina.empleados.getGenarl().getNombre());
                 sp_arl.hide();
                }
                if(FXNomina.empleados.getCajacomp()!=null)
                {
                 sb_cajacomp.setText(FXNomina.empleados.getCajacomp().getNombre());
                 sp_cajacomp.hide();
                }
                documento.setText(genPersonas.getDocumento());
                primer_nombre.setText(genPersonas.getPrimerNombre());
                primer_apellido.setText(genPersonas.getPrimerApellido());
                segundo_nombre.setText(genPersonas.getSegundoNombre());
                segundo_apellido.setText(genPersonas.getSegundoApellido());
                telefono.setText(genPersonas.getTelefono());
                direccion.setText(genPersonas.getDireccion());
                cb_rh.getSelectionModel().select(FXNomina.empleados.getRh());
                activo.setSelected(FXNomina.empleados.isActivo());
                LoadChoiceBoxGeneral.cb_cargosentidad.getSelectionModel().select(LoadChoiceBoxGeneral.v_cargosentidad.indexOf(FXNomina.empleados.getCargosEntidad()));
               if (genPersonas.getGenSexo() != null)
               {
                LoadChoiceBoxGeneral.getGen_sexo_id().getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_sexo().indexOf(genPersonas.getGenSexo().getId()));
               }
              
                     if (genPersonas.getGenTiposDocumentos()!= null)
               {
                LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().indexOf(genPersonas.getGenTiposDocumentos()));
               }
                if (genPersonas.getGenMunicipios() != null) {
           
                    sb_genmunicipios.setText(genPersonas.getGenMunicipios().getNombre() + "-" + genPersonas.getGenMunicipios().getGenDepartamentos().getNombre());
                } else {
                    FXNomina.s_genMunicipios = null;
                    FXNomina.s_genMunicipios = new GenMunicipios();

                }

                try {
                    fechanacimiento.setValue(UtilDate.formatoyearmesdia(genPersonas.getFechaNacimiento()));
                } catch (Exception e) {
                    try {
                        fechanacimiento.setValue(UtilDate.formatoyearmesdia2(genPersonas.getFechaNacimiento()));
                    } catch (Exception e2) {

                    }
                }
                  try{
       InputStream in = new ByteArrayInputStream(genPersonas.getImg());
       bi = ImageIO.read(in);
       wi=new WritableImage(1,1);
       wi=SwingFXUtils.toFXImage(bi, wi);
       //img=new ImageView();
       loadfoto=wi;
       foto.setImage(loadfoto);
       }catch(Exception e)
                  {
                  e.printStackTrace();
                  }

            } else {
                nuevo();
            }
        } else {
            nuevo();
        }
    }

    public void nuevo() {
        genPersonas = null;
        FXNomina.empleados = null;
        genPersonas = new GenPersonas();
        FXNomina.empleados = new Empleados();
        FXNomina.s_genEapb = null;
        FXNomina.s_genEapb = new GenEapb();
        FXNomina.s_genMunicipios = null;
        FXNomina.s_genMunicipios = new GenMunicipios();
       
        primer_nombre.setText("");
        primer_apellido.setText("");
        segundo_nombre.setText("");
        segundo_apellido.setText("");
        telefono.setText("");
        direccion.setText("");
        LoadChoiceBoxGeneral.getGen_sexo_id().getSelectionModel().select(-1);
        LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getSelectionModel().select(-1);
        sb_genmunicipios.setText("");
        fechanacimiento.setValue(null);
        cb_rh.getSelectionModel().select(0);

    }

    private void showinformaciontributaria() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
     
        appPathGeneric = "sihic.contabilidad.FInformacionTributaria";
        clzGeneric = null;
        clzGeneric = Class.forName(appPathGeneric);
        appClassGeneric = clzGeneric.newInstance();
        P_Generic = null;
        P_Generic = (Parent) clzGeneric.getMethod("createContent").invoke(appClassGeneric);
        sceneGeneric = null;
        sceneGeneric = new Scene(P_Generic, Color.TRANSPARENT);
        stageGeneric.setTitle("Información Tributaria");
        if (stageGeneric.isShowing()) {
            stageGeneric.close();
        }

//set scene to stage
        stageGeneric.sizeToScene();

        //center stage on screen
        stageGeneric.centerOnScreen();
        stageGeneric.setScene(sceneGeneric);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageGeneric.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void LoadImageFoto() {
        
       
        filefoto=fileChooser.showOpenDialog(stage);
         
        if(filefoto!=null)
        {
            byte[]img2;
            try {
                   bi = ImageIO.read(filefoto);
                  
                  wi=SwingFXUtils.toFXImage(bi, wi);
                  loadfoto=wi;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
        }
    
        foto.setImage(loadfoto);
    }
     public static  void LoadImageFoto2(){
       filefoto=null; 
       filefoto=new File("c:\\fotohuella\\"+fxnomina.FXNomina.genPersonas.getDocumento()+"\\"+fxnomina.FXNomina.genPersonas.getId().toString()+".png");
       if(filefoto!=null)
        {
            byte[]img2;
            try {
                   bi = ImageIO.read(filefoto);
                  
                  wi=SwingFXUtils.toFXImage(bi, wi);
                  loadfoto=wi;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
        }
    
        foto.setImage(loadfoto);
    }

public void save_img()
{
        byte[]data;
        InputStream in;
        try{
        in = new FileInputStream(filefoto);
        data=new byte[in.available()];
	in.read(data);
        genPersonas.setImg(data);
        }catch(Exception e)
        {
           e.printStackTrace();
        }
}
 private void showfotohuella() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        stagefotohuella.setTitle("Finger Printer, Sistema de Captura de Huella y Foto");
        appClassfotohuella = "enrolarfotohuella.EnrrolarFotoHuella";
        clzfotohuella = Class.forName(appClassfotohuella);
        appfotohuella = clzfotohuella.newInstance();
        parentfotohuella = null;
        parentfotohuella = (Parent) clzfotohuella.getMethod("createContent").invoke(appfotohuella);
        scenefotohuella = null;
        scenefotohuella = new Scene(parentfotohuella, Color.TRANSPARENT);

        if (stagefotohuella.isShowing()) 
        {
            stagefotohuella.close();
        }
         stagefotohuella.sizeToScene();
         stagefotohuella.centerOnScreen();
         stagefotohuella.setScene(scenefotohuella);
         stagefotohuella.showAndWait();
       }
 private void showverificarfotohuella() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        stagefotohuella.setTitle("Finger Printer, Sistema de Verificación Huella y Foto");
        appClassfotohuella = "verificarfotohuella.SegBiom";
        clzfotohuella = Class.forName(appClassfotohuella);
        appfotohuella = clzfotohuella.newInstance();
        parentfotohuella = null;
        parentfotohuella = (Parent) clzfotohuella.getMethod("createContent").invoke(appfotohuella);
        scenefotohuella = null;
        scenefotohuella = new Scene(parentfotohuella, Color.TRANSPARENT);

        if (stagefotohuella.isShowing()) 
        {
            stagefotohuella.close();
        }
         stagefotohuella.sizeToScene();
         stagefotohuella.centerOnScreen();
         stagefotohuella.setScene(scenefotohuella);
         stagefotohuella.showAndWait();
       }

  
}
