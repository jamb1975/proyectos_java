package sihic.general;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import jfxtras.styles.jmetro8.JMetro;
import message.EstadosMensaje;
import modelo.GenEapb;
import modelo.GenEstadosCiviles;
import modelo.GenEtnias;
import modelo.GenMunicipios;
import modelo.GenNivelesEducativos;
import modelo.GenNivelesUsuarios;
import modelo.GenPacientes;
import modelo.GenPersonas;
import modelo.GenProfesiones;
import modelo.GenSexo;
import modelo.GenTiposAfiliacion;
import modelo.GenTiposDocumentos;
import modelo.GenTiposUsuarios;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.controlador.GenEapbControllerClient;
import sihic.controlador.GenPersonasControllerClient;
import sihic.util.UtilDate;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FGenPersonas extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GenPersonas genPersonas;
    private GenPacientes genPacientes;
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
    private Label la_observaciones;
    private TextField observaciones;
    private Label la_gentiposdocumentos;
    private Label la_genestadosciviles;
    private Label la_gensexo;
    private Label la_genetnias;
    private Label la_genprofesiones;
    private Label la_genmunicipios;
    private Label la_genzonaresidencia;
    private Label la_genniveleseducativos;
    private TextField profesion;
    private GenPersonasControllerClient genPersonasControllerClient;
    private Button save;
    private Button nuevo;
    private Button tributaria;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private BuscarProfesiones buscarProfesion;
    private Button nuevaProfesion;
    private Label la_eapb;
    private Label la_tipoafiliacion;
    private Label la_tipousuario;
    private Label la_nivelusuario;
    private ChoiceBox eapb;
    private Vector<GenTiposAfiliacion> v_tiposafiliacion = new Vector<GenTiposAfiliacion>();
    private Vector<GenTiposUsuarios> v_tiposusuarios = new Vector<GenTiposUsuarios>();
    private Vector<GenNivelesUsuarios> v_nivelesusuarios = new Vector<GenNivelesUsuarios>();
    private ChoiceBox tipoafiliacion;
    private ChoiceBox tipousuario;
    private ChoiceBox nivelusuario;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private GenEapb genEapb;
    private SearchPopover sp_geneapb;
    private SearchBox sb_eapb = new SearchBox();
    private SearchPopover sp_aseguradora;
    private SearchBox sb_aseguradora = new SearchBox();
    private SearchBox sb_genprofesiones = new SearchBox();
    private SearchPopover sp_genprofesiones;
    GenEapbControllerClient genEapbControllerClient;

    private String appPathGeneric;
    private Class clzGeneric;
    private Object appClassGeneric;
    private Stage stageGeneric = new Stage(StageStyle.DECORATED);
    private Scene sceneGeneric = null;
    private Parent P_Generic;
    
        public Parent createContent() throws IOException {
        sp_geneapb = new SearchPopover(sb_eapb, new PageBrowser(), SihicApp.s_genEapb, true);
        sp_aseguradora = new SearchPopover(sb_aseguradora, new PageBrowser(), SihicApp.aseguradora, false);
        sp_aseguradora.setMaxHeight(30);
        sp_genmunicipios = new SearchPopover(sb_genmunicipios, new PageBrowser(), SihicApp.s_genMunicipios, false);
        sp_genprofesiones = new SearchPopover(sb_genprofesiones, new PageBrowser(), SihicApp.s_GenProfesiones, false);
        sb_eapb.getProperties().put("requerido", true);
        sb_genmunicipios.getProperties().put("requerido", true);
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        genPersonas = new GenPersonas();
        genPacientes = new GenPacientes();
        buscarProfesion = new BuscarProfesiones();
        genPersonasControllerClient = new GenPersonasControllerClient();
        genEapbControllerClient = new GenEapbControllerClient();
        LoadChoiceBoxGeneral.getGen_etnias_id().setMaxWidth(300);
        LoadChoiceBoxGeneral.getGen_niveles_educativos_id().setMaxWidth(300);
        LoadChoiceBoxGeneral.getGen_tipos_documentos_id().setMaxWidth(300);
        la_gentiposdocumentos = new Label("Tipo Documento:");
        la_documento = new Label("No Identificación:");
        la_genestadosciviles = new Label("Estado Civil:");
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
        la_genetnias = new Label("Etnias:");
        la_genniveleseducativos = new Label("Nivel Educativo:");
        la_genprofesiones = new Label("Profesión");
        profesion = new TextField();
        la_genzonaresidencia = new Label("Zona Residencia:");
        la_fechanacimiento = new Label("Fecha de Nacimiento:");
        la_observaciones = new Label("Observaciones:");

        la_eapb = new Label("Empresa adm. planes beneficio: ");
        la_tipoafiliacion = new Label("Tipo afiliación: ");
        la_tipousuario = new Label("Tipo usuario: ");
        la_nivelusuario = new Label("Nivel usuario: ");
        eapb = new ChoiceBox();
        eapb.setMaxWidth(300);
        eapb.getProperties().put("requerido", false);
        tipoafiliacion = new ChoiceBox();
        tipousuario = new ChoiceBox();
        nivelusuario = new ChoiceBox();
        nivelusuario.setMaxWidth(300);
        tipousuario.setMaxWidth(300);
        sb_genprofesiones.setMaxWidth(300);
        sb_genmunicipios.setMaxWidth(300);
        sb_eapb.setMaxWidth(300);
        sb_aseguradora.setMaxWidth(300);
        documento = new TextField();
        documento.getProperties().put("requerido", true);
        documento.setMaxWidth(300);
        primer_nombre = new TextField();
        primer_nombre.getProperties().put("requerido", true);
        primer_nombre.setMaxWidth(300);
        segundo_nombre = new TextField();
        segundo_nombre.setMaxWidth(300);
        primer_apellido = new TextField();
        primer_apellido.setMaxWidth(300);
        primer_apellido.getProperties().put("requerido", true);
        segundo_apellido = new TextField();
        segundo_apellido.setMaxWidth(300);
        telefono = new TextField();
        telefono.getProperties().put("requerido", true);
        telefono.setMaxWidth(300);
        direccion = new TextField();
        direccion.setMaxWidth(300);
        barrio = new TextField();
        email = new TextField();
        email.setMaxWidth(300);
        fechanacimiento = new DatePicker(LocalDate.now());
        observaciones = new TextField();
        barrio.setMaxWidth(300);
        observaciones.setMaxWidth(300);
        ImageView imageView = new ImageView("/images/save.png");
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
        hb_botones.getChildren().addAll(save, nuevo, tributaria);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {
                Logger.getLogger(FGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        tributaria.setOnAction(e -> {
            try {
                showinformaciontributaria();
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(FGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(FGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(FGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(FGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

// gridpane  sp_geneapb = new SearchPopover(sb_eapb,new PageBrowser());
        gp_genpersonas = new GridPane();
        gp_genpersonas.add(la_gentiposdocumentos, 0, 0);
        gp_genpersonas.add(LoadChoiceBoxGeneral.getGen_tipos_documentos_id(), 1, 0);
        gp_genpersonas.add(la_documento, 2, 0);
        gp_genpersonas.add(documento, 3, 0);

        gp_genpersonas.add(la_primer_nombre, 0, 1);
        gp_genpersonas.add(primer_nombre, 1, 1);
        gp_genpersonas.add(la_primer_apellido, 2, 1);
        gp_genpersonas.add(primer_apellido, 3, 1);

        gp_genpersonas.add(la_segundo_nombre, 0, 2);
        gp_genpersonas.add(segundo_nombre, 1, 2);
        gp_genpersonas.add(la_segundo_apellido, 2, 2);
        gp_genpersonas.add(segundo_apellido, 3, 2);

        gp_genpersonas.add(la_genmunicipios, 0, 3);
        gp_genpersonas.add(sb_genmunicipios, 1, 3);

        gp_genpersonas.add(la_direccion, 0, 4);
        gp_genpersonas.add(direccion, 1, 4);
        gp_genpersonas.add(la_barrio, 2, 4);
        gp_genpersonas.add(barrio, 3, 4);

        gp_genpersonas.add(la_gensexo, 0, 5);
        gp_genpersonas.add(LoadChoiceBoxGeneral.getGen_sexo_id(), 1, 5);
        gp_genpersonas.add(la_genetnias, 2, 5);
        gp_genpersonas.add(LoadChoiceBoxGeneral.getGen_etnias_id(), 3, 5);

        gp_genpersonas.add(la_email, 0, 6);
        gp_genpersonas.add(email, 1, 6);
        gp_genpersonas.add(la_telefono, 2, 6);
        gp_genpersonas.add(telefono, 3, 6);
        gp_genpersonas.add(la_genniveleseducativos, 2, 7);
        gp_genpersonas.add(LoadChoiceBoxGeneral.getGen_niveles_educativos_id(), 3, 7);
        gp_genpersonas.add(la_genprofesiones, 0, 7);
        gp_genpersonas.add(sb_genprofesiones, 1, 7);
        gp_genpersonas.add(la_fechanacimiento, 0, 8);
        gp_genpersonas.add(fechanacimiento, 1, 8);
        gp_genpersonas.add(la_genzonaresidencia, 2, 8);
        gp_genpersonas.add(LoadChoiceBoxGeneral.getGen_zona_residencia_id(), 3, 8);
        gp_genpersonas.add(la_genestadosciviles, 0, 9);
        gp_genpersonas.add(LoadChoiceBoxGeneral.getGen_estados_civiles_id(), 1, 9);
        gp_genpersonas.add(la_observaciones, 2, 9);
        gp_genpersonas.add(observaciones, 3, 9);
        gp_genpersonas.add(la_tipoafiliacion, 0, 10);
        gp_genpersonas.add(tipoafiliacion, 1, 10);
        gp_genpersonas.add(la_tipousuario, 2, 10);
        gp_genpersonas.add(tipousuario, 3, 10);
        gp_genpersonas.add(la_eapb, 2, 3);
        gp_genpersonas.add(sb_eapb, 3, 3);
        gp_genpersonas.add(la_nivelusuario, 0, 11);
        gp_genpersonas.add(nivelusuario, 1, 11);
        gp_genpersonas.add(new Label("Aseguradora:"), 2, 11);
        gp_genpersonas.add(sb_aseguradora, 3, 11);
        gp_genpersonas.add(hb_botones, 0, 13, 3, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_genpersonas.getStylesheets().add(SihicApp.hojaestilos);
        gp_genpersonas.getStyleClass().add("background");
        gp_genpersonas.setHgap(5);
        gp_genpersonas.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_genpersonas.alignmentProperty().setValue(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_genpersonas.getLayoutBounds().getHeight(), gp_genpersonas.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        //  new JMetro(JMetro.Style.DARK).applyTheme(stack);
        stack.getChildren().addAll(gp_genpersonas, Gp_Message);
        event_inputs();
        Load_ChoiceBox();

        sp_geneapb.getStylesheets().add(SihicApp.hojaestilos);
        gp_genpersonas.add(sp_geneapb, 1, 4, 3, 10);
        gp_genpersonas.add(sp_aseguradora, 2, 12, 3, 3);
        gp_genpersonas.add(sp_genmunicipios, 1, 4, 3, 9);
        gp_genpersonas.add(sp_genprofesiones, 1, 8, 3, 6);
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
                if (genPersonas.getId() == null) {
                    genPersonasControllerClient.save(genPersonas, genPacientes);
                } else {
                    genPacientes.setGenPersonas(genPersonas);
                    genPacientes = genPersonasControllerClient.edit(genPacientes);
                }
                AdminGenPersonas.getListGenPersonas();
                L_Message.setText("Registro Almacenado");
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
        GenEstadosCiviles genEstadosCiviles = new GenEstadosCiviles();
        genEstadosCiviles.setId(LoadChoiceBoxGeneral.extraer_id_choicebox(LoadChoiceBoxGeneral.getGen_estados_civiles_id(), LoadChoiceBoxGeneral.getV_gen_estados_civiles()));
        genPersonas.setGenEstadosCiviles(genEstadosCiviles);

        //
        //colocar etnias
        GenEtnias genEtnias = new GenEtnias();
        genEtnias.setId(LoadChoiceBoxGeneral.extraer_id_choicebox(LoadChoiceBoxGeneral.getGen_etnias_id(), LoadChoiceBoxGeneral.getV_gen_etnias()));
        genPersonas.setGenEtnias(genEtnias);
        genPersonas.setGenMunicipios(SihicApp.s_genMunicipios);

        //colocar sexo
        GenSexo genSexo = new GenSexo();
        genSexo.setId(LoadChoiceBoxGeneral.extraer_id_choicebox(LoadChoiceBoxGeneral.getGen_sexo_id(), LoadChoiceBoxGeneral.getV_gen_sexo()));
        genPersonas.setGenSexo(genSexo);

        //colocar tipos documentos
        GenTiposDocumentos genTiposDocumentos = new GenTiposDocumentos();
        System.out.println("index->"+LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getSelectionModel().getSelectedIndex());
        genPersonas.setGenTiposDocumentos(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().get(LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getSelectionModel().getSelectedIndex()));

        //colocar profesiones
        GenNivelesEducativos genNivelesEducativos = new GenNivelesEducativos();
        genNivelesEducativos.setId(LoadChoiceBoxGeneral.extraer_id_choicebox(LoadChoiceBoxGeneral.getGen_niveles_educativos_id(), LoadChoiceBoxGeneral.getV_gen_niveleseducativos()));
        if (genNivelesEducativos.getId() != null) {
            genPersonas.setGenNivelesEducativos(genNivelesEducativos);
        }
        //colocar zona de residencia
        genPersonas.setGen_zona_residencia(LoadChoiceBoxGeneral.getV_gen_zona_residencia().get(LoadChoiceBoxGeneral.getGen_zona_residencia_id().getSelectionModel().getSelectedIndex()));

        genPersonas.setObservaciones(observaciones.getText());
        genPersonas.setPrimerApellido(primer_apellido.getText());
        genPersonas.setPrimerNombre(primer_nombre.getText());
        genPersonas.setSegundoApellido(segundo_apellido.getText());
        genPersonas.setSegundoNombre(segundo_nombre.getText());
        genPersonas.setTelefono(telefono.getText());
        genPersonas.seteMail(email.getText());
        if (SihicApp.s_genEapb.getId() != null) {
            genPacientes.setGenEapb(SihicApp.s_genEapb);
        } else {
            genPacientes.setGenEapb(null);
        }
        if (SihicApp.aseguradora.getId() != null) {
            genPacientes.setAseguradora(SihicApp.aseguradora);
        } else {
            genPacientes.setAseguradora(null);
        }
        genPacientes.setGenTiposAfiliacion(v_tiposafiliacion.get(tipoafiliacion.getSelectionModel().getSelectedIndex()));
        genPacientes.setGenTiposUsuarios(v_tiposusuarios.get(tipousuario.getSelectionModel().getSelectedIndex()));
        genPacientes.setGenNivelesUsuarios(v_nivelesusuarios.get(nivelusuario.getSelectionModel().getSelectedIndex()));

        if (SihicApp.s_GenProfesiones.getId() != null) {
            genPersonas.setGen_profesiones(SihicApp.s_GenProfesiones);
        }
        if (SihicApp.s_informacionTributaria != null) {
            if (SihicApp.s_informacionTributaria.getId() != null) {
                genPersonas.setInformacionTributaria(SihicApp.s_informacionTributaria);
            }
        }
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
                            if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
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
                                general.getStylesheets().set(0, SihicApp.hojaestilos);

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

    public void Load_ChoiceBox() {
  
        //hcl causaexterna
        if (SihicApp.l_tiposafiliacion.size() > 0) {
            tipoafiliacion.getItems().clear();
            v_tiposafiliacion.clear();
            for (GenTiposAfiliacion ce : SihicApp.l_tiposafiliacion) {
                v_tiposafiliacion.add(ce);
                tipoafiliacion.getItems().add(ce.getDescripcion());
                System.out.println("id tipo ->"+ce.getId());
            }
        }
        //codigos consultas
        if (SihicApp.l_tiposusuarios.size() > 0) {
            tipousuario.getItems().clear();
            v_tiposusuarios.clear();
            for (GenTiposUsuarios cc : SihicApp.l_tiposusuarios) {
                v_tiposusuarios.add(cc);
                tipousuario.getItems().add(cc.getNombre());
            }
        }

        //codigos consultas
        if (SihicApp.l_nivelesusuarios.size() > 0) {
            nivelusuario.getItems().clear();
            v_nivelesusuarios.clear();
            for (GenNivelesUsuarios cc : SihicApp.l_nivelesusuarios) {
                v_nivelesusuarios.add(cc);
                nivelusuario.getItems().add(cc.getNivel());
                                
            }
        }

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
        sb_eapb.setOnAction(e -> {
            direccion.requestFocus();
        });
        sb_genmunicipios.setOnAction(e -> {

            sb_eapb.requestFocus();

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
        LoadChoiceBoxGeneral.getGen_etnias_id().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                email.requestFocus();
            }
        });
        email.setOnAction(e -> {
            telefono.requestFocus();
        });
        telefono.setOnAction(e -> {
            sb_genprofesiones.requestFocus();
        });

        LoadChoiceBoxGeneral.getGen_niveles_educativos_id().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                fechanacimiento.requestFocus();
            }
        });
        sb_genprofesiones.setOnAction(e -> {
            LoadChoiceBoxGeneral.getGen_niveles_educativos_id().requestFocus();
        });
        fechanacimiento.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                LoadChoiceBoxGeneral.getGen_zona_residencia_id().requestFocus();
            }
        });
        LoadChoiceBoxGeneral.getGen_zona_residencia_id().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                LoadChoiceBoxGeneral.getGen_estados_civiles_id().requestFocus();
            }
        });
        LoadChoiceBoxGeneral.getGen_estados_civiles_id().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                observaciones.requestFocus();
            }
        });
        observaciones.setOnAction(e -> {
            tipoafiliacion.requestFocus();
        });
        tipoafiliacion.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                tipousuario.requestFocus();
            }
        });
        tipousuario.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                nivelusuario.requestFocus();
            }
        });

        nivelusuario.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                save.requestFocus();
            }
        });
        save.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();
                    nuevo.requestFocus();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
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
        genPacientes = AdminGenPersonas.getGenPacientes();
        if (genPacientes != null) {
            
            genPersonas = genPacientes.getGenPersonas();
            SihicApp.s_genEapb = genPacientes.getGenEapb();
            SihicApp.aseguradora = genPacientes.getAseguradora();
            SihicApp.s_GenProfesiones = genPersonas.getGen_profesiones();
            SihicApp.s_genMunicipios = genPersonas.getGenMunicipios();
            documento.setText(genPersonas.getDocumento());
            primer_nombre.setText(genPersonas.getPrimerNombre());
            primer_apellido.setText(genPersonas.getPrimerApellido());
            segundo_nombre.setText(genPersonas.getSegundoNombre());
            segundo_apellido.setText(genPersonas.getSegundoApellido());
            telefono.setText(genPersonas.getTelefono());
            direccion.setText(genPersonas.getDireccion());
            try{
            tipousuario.getSelectionModel().select(v_tiposusuarios.indexOf(genPacientes.getGenTiposUsuarios()));
            nivelusuario.getSelectionModel().select(genPacientes.getGenNivelesUsuarios().getNivel()-1);
            tipoafiliacion.getSelectionModel().select(Integer.valueOf(genPacientes.getGenTiposAfiliacion().getCodigo())-1);
            }catch(Exception e)
            {
                
            }
            if (genPacientes.getGenEapb() != null) {
                sb_eapb.setText(genPacientes.getGenEapb().getCodigo() + " " + genPacientes.getGenEapb().getNombre());
            } else {
                SihicApp.s_genEapb = null;
                SihicApp.s_genEapb = new GenEapb();
                sb_eapb.setText("");
            }
             if (genPacientes.getAseguradora()!= null) {
                sb_aseguradora.setText(genPacientes.getAseguradora().getCodigo() + " " + genPacientes.getAseguradora().getNombre());
            } else {
                SihicApp.aseguradora = null;
                SihicApp.aseguradora = new GenEapb();
                sb_aseguradora.setText("");

            }
                LoadChoiceBoxGeneral.getGen_sexo_id().getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_sexo().indexOf(genPersonas.getGenSexo().getId()));
            LoadChoiceBoxGeneral.getGen_zona_residencia_id().getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_zona_residencia().indexOf(genPersonas.getGen_zona_residencia()));
            try{
            LoadChoiceBoxGeneral.getGen_niveles_educativos_id().getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_niveleseducativos().indexOf(genPersonas.getGenNivelesEducativos().getId()));
            
                LoadChoiceBoxGeneral.getGen_etnias_id().getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_etnias().indexOf(genPersonas.getGenEtnias().getId()));
            
            LoadChoiceBoxGeneral.getGen_estados_civiles_id().getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_estados_civiles().indexOf(genPersonas.getGenEstadosCiviles().getId()));
            }catch(Exception e)
            {
                
            }
            LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().indexOf(genPersonas.getGenTiposDocumentos()));
            if (genPersonas.getGenMunicipios() != null) {
                sb_genmunicipios.setText(genPersonas.getGenMunicipios().getNombre() + "-" + genPersonas.getGenMunicipios().getGenDepartamentos().getNombre());
            } else {
                SihicApp.s_genMunicipios = null;
                SihicApp.s_genMunicipios = new GenMunicipios();

            }
            if (genPersonas.getGen_profesiones() != null) {
                sb_genprofesiones.setText(genPersonas.getGen_profesiones().getCodigo() + " " + genPersonas.getGen_profesiones().getDescripcion());
            } else {
                SihicApp.s_GenProfesiones = null;
                SihicApp.s_GenProfesiones = new GenProfesiones();
            }
            try {
                fechanacimiento.setValue(UtilDate.formatoyearmesdia(genPersonas.getFechaNacimiento()));
            } catch (Exception e) {
                try {
                    fechanacimiento.setValue(UtilDate.formatoyearmesdia2(genPersonas.getFechaNacimiento()));
                } catch (Exception e2) {

                }
            }
            observaciones.setText(genPersonas.getObservaciones());

        } else {
            nuevo();
        }

    }

    public void nuevo() {
        genPersonas = null;
        genPacientes = null;
        genPersonas = new GenPersonas();
        genPacientes = new GenPacientes();
        SihicApp.s_genEapb = null;
        SihicApp.s_genEapb = new GenEapb();
        SihicApp.aseguradora = null;
        SihicApp.aseguradora = new GenEapb();
        SihicApp.s_genMunicipios = null;
        SihicApp.s_genMunicipios = new GenMunicipios();
        SihicApp.s_GenProfesiones = null;
        SihicApp.s_GenProfesiones = new GenProfesiones();
        primer_nombre.setText("");
        primer_apellido.setText("");
        segundo_nombre.setText("");
        segundo_apellido.setText("");
        telefono.setText("");
        direccion.setText("");
        tipousuario.getSelectionModel().select(-1);
        sb_eapb.setText("");
        sb_aseguradora.setText("");
        nivelusuario.getSelectionModel().select(-1);
        tipoafiliacion.getSelectionModel().select(-1);
        LoadChoiceBoxGeneral.getGen_sexo_id().getSelectionModel().select(-1);
        LoadChoiceBoxGeneral.getGen_zona_residencia_id().getSelectionModel().select(-1);
        LoadChoiceBoxGeneral.getGen_niveles_educativos_id().getSelectionModel().select(-1);
        LoadChoiceBoxGeneral.getGen_etnias_id().getSelectionModel().select(-1);
        LoadChoiceBoxGeneral.getGen_estados_civiles_id().getSelectionModel().select(-1);
        LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getSelectionModel().select(-1);
        sb_genmunicipios.setText("");
        sb_genprofesiones.setText("");
        fechanacimiento.setValue(null);
        observaciones.setText("");
        buscarProfesion.setGenProfesiones(null);
    }

    private void showinformaciontributaria() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        if (genPersonas.getId() != null) {
            SihicApp.s_informacionTributaria = genPersonas.getInformacionTributaria();
        } else {
            SihicApp.s_informacionTributaria = null;
        }
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
}
