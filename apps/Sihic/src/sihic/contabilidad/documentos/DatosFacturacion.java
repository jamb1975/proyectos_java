package sihic.contabilidad.documentos;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import message.EstadosMensaje;
import modelo.AgnCitas;
import modelo.AgnConsultorios;
import modelo.AgnMedicos;
import modelo.FacturaItem;
import modelo.GenEapb;
import modelo.GenEstadosCiviles;
import modelo.GenEtnias;
import modelo.GenHorasCita;
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
import modelo.GenZonaResidencia;
import modelo.HclCie10;
import modelo.Kardex;
import modelo.Producto;
import modelo.Sucursales;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.contabilidad.ImprimirPdf;
import sihic.control.SearchBox;
import sihic.controlador.GenEapbControllerClient;
import sihic.controlador.GenPersonasControllerClient;
import sihic.general.FGenPersonas;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class DatosFacturacion extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GenPersonas genPersonas;
    private GenPacientes genPacientes;
    private GridPane gp_genpersonas;
    private SearchBox sb_genmunicipios = new SearchBox();
    private SearchPopover sp_genmunicipios;
    private static Button bu_printcomprobante;
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
    private SearchBox sb_documento=new SearchBox();
    private SearchPopover sp_documento;
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
    SearchBox sb_gentipodocumentos=new SearchBox();
    SearchPopover sp_gentipodocumentos;
    SearchBox sb_gensexo=new SearchBox();
    SearchPopover sp_gensexo;
    SearchBox sb_genetnias=new SearchBox();
    SearchPopover sp_genetnias;
    SearchBox sb_genniveleseducativos=new SearchBox();
    SearchPopover sp_genniveleseducativos;
    SearchBox sb_genzonaresidencia=new SearchBox();
    SearchPopover sp_genzonaresidencia;
    SearchBox sb_genestadosciviles=new SearchBox();
    SearchPopover sp_genestadosciviles;
    SearchBox sb_gentiposafiliacion=new SearchBox();
    SearchPopover sp_gentiposafiliacion;
    SearchPopover sp_gentiposusuarios;
    SearchBox sb_gentiposusuarios=new SearchBox();
    SearchPopover sp_gennivelessusuarios;
    SearchBox sb_gennivelesusuarios=new SearchBox();
   
    GenEapbControllerClient genEapbControllerClient;
    private String appPathGeneric;
    private Class clzGeneric;
    private Object appClassGeneric;
    private Stage stageGeneric = new Stage(StageStyle.DECORATED);
    private Scene sceneGeneric = null;
    private Parent P_Generic;
    private Label la_fechacita=new Label("Fecha de Cita(*):");
    private DatePicker fechacita=new DatePicker(LocalDate.now());
    private Label la_genhorascitas=new Label("Hora de Cita(*):");
    private SearchBox sb_genhorascitas=new SearchBox();
    private SearchPopover sp_genhorascitas;
    private Label la_agnmedicos=new Label("Médico(*):");
    private SearchBox sb_agnmedicos=new SearchBox();
    private SearchPopover sp_agnmedicos;
    private Label la_agnconsultorio=new Label("Consultorio(*):");
    private SearchBox     sb_agnconsultorios=new SearchBox();
    private SearchPopover sp_agnconsultorios;
    private Label la_procedimiento=new Label("Procedimiento(*):");
    private SearchBox sb_procedimiento=new SearchBox();
    private SearchPopover sp_procedimiento;
    private Label la_cie10principal=new Label("Diagnóstico principal:");
    private SearchBox sb_cie10principal=new SearchBox();
    private SearchPopover sp_cie10principal;
    private Label la_cie10ingreso=new Label("Diagnóstico ingreso:");
    private SearchBox sb_cie1ingreso=new SearchBox();
    private SearchPopover sp_cie10ingreso;
    private Label la_cie10secundario=new Label("Diagnóstico secundario:");
    private SearchBox sb_cie10secundario=new SearchBox();
    private SearchPopover sp_cie10secundario;
    private Label la_numeroautorizacion=new Label("Número de autorización(*):");
    private TextField noautorizacion=new TextField();
    private Label la_tipocie10=new Label("Tipo de diagnóstico:");
    private TextField tipocie10=new TextField();
    private Label la_tipocuenta=new Label("Tipo cuenta:");
    private TextField tipocuenta=new TextField();
    private Label la_clasificacionorigen=new Label("Clasificación origen:");
    private TextField clasificacionorigen=new TextField();
    private Label la_centrodecostos=new Label("Centro de costos(*):");
    private SearchBox sb_centrocostos=new SearchBox();
    private SearchPopover sp_centrocostos;
    private Label la_rh=new Label("Rh:");
    private SearchBox sb_rh=new SearchBox();
    private SearchPopover sp_rh;
    private Label la_agncitas=new Label("Buscar Cita:");
    private SearchBox sb_agncitas=new SearchBox();
    private SearchPopover sp_agncitas;
    private Label la_cantidad=new Label("Cantidad(*):");
    private TextField cantidad=new TextField();
    private Label la_copagos=new Label("Copagos(*):");
    private TextField copagos=new TextField();
    private Label la_cuotamoderadora=new Label("Cuota Moderadora(*):");
    private TextField cuotamoderadora=new TextField();
    private Label la_discapacidad=new Label("Discapacidad(S/N):");
    private TextField discapacidad=new TextField();
    private Label la_estadopaciente=new Label("Estado paciente:");
    private TextField estadopaciente=new TextField();
    private Label la_tipoatencion=new Label("Tipo servicio:");
    private TextField tiposervicio=new TextField();
    
        
    public Parent createContent() throws IOException {
        
        SihicApp.pfechacita=fechacita.getValue().toString();
        SihicApp.s_genEapb=new GenEapb();
        SihicApp.genPacientesTemp=new GenPacientes();
        SihicApp.aseguradora=new GenEapb();
        SihicApp.s_genMunicipios=new GenMunicipios();
        SihicApp.s_GenProfesiones=new GenProfesiones();
        SihicApp.genTiposDocumentos=new GenTiposDocumentos();
        SihicApp.genSexo=new GenSexo();
        SihicApp.genEtnias=new GenEtnias();
        SihicApp.genNivelesEducativos=new GenNivelesEducativos();
        SihicApp.genZonaResidencia=new GenZonaResidencia();
        SihicApp.genEstadosCiviles=new GenEstadosCiviles();
        SihicApp.genTiposAfiliacion=new GenTiposAfiliacion();
        SihicApp.genTiposUsuarios=new GenTiposUsuarios();
        SihicApp.genNivelesUsuarios=new GenNivelesUsuarios();
        SihicApp.genHorasCita=new GenHorasCita();
        SihicApp.agnMedicos=new AgnMedicos();
        SihicApp.agnConsultorios=new AgnConsultorios();
        SihicApp.hclCie10=new HclCie10();
        SihicApp.agnCitasTemp=new AgnCitas();
        fechacita.setPromptText("dd/MM/aaaa");
         fechacita.getProperties().put("requerido", true);
        sp_geneapb = new SearchPopover(sb_eapb, new PageBrowser(), SihicApp.s_genEapb, true);
        sp_geneapb.setMaxHeight(20);
        sp_geneapb.setMaxWidth(100);
        sp_documento = new SearchPopover(sb_documento, new PageBrowser(), SihicApp.genPacientesTemp, false);
        sb_documento.getProperties().put("requerido", true);
        sp_documento.setMaxHeight(20);
        sp_documento.setMaxWidth(200);
        sp_aseguradora = new SearchPopover(sb_aseguradora, new PageBrowser(), SihicApp.aseguradora, false);
        sp_aseguradora.setMaxHeight(20);
        sp_aseguradora.setMaxWidth(200);
        sp_genmunicipios = new SearchPopover(sb_genmunicipios, new PageBrowser(), SihicApp.s_genMunicipios, false);
        sp_genmunicipios.setMaxHeight(20);
        sp_genmunicipios.setMaxWidth(200);
        sp_genprofesiones = new SearchPopover(sb_genprofesiones, new PageBrowser(), SihicApp.s_GenProfesiones, false);
        sp_genprofesiones.setMaxHeight(20);
        sp_genprofesiones.setMaxWidth(200);
        sp_gentipodocumentos = new SearchPopover(sb_gentipodocumentos, new PageBrowser(), SihicApp.genTiposDocumentos, false);
        sb_gentipodocumentos.getProperties().put("requerido", true);
        sp_gentipodocumentos.setMaxHeight(20);
        sp_gentipodocumentos.setMaxWidth(200);
        sp_gensexo = new SearchPopover(sb_gensexo, new PageBrowser(), SihicApp.genSexo, false);
        sb_gensexo.getProperties().put("requerido", true);
        sp_gensexo.setMaxHeight(20);
        sp_gensexo.setMaxWidth(200);
        sp_genetnias = new SearchPopover(sb_genetnias, new PageBrowser(), SihicApp.genEtnias, false);
         sp_genetnias.setMaxHeight(20);
         sp_genetnias.setMaxWidth(200);
         sp_genniveleseducativos = new SearchPopover(sb_genniveleseducativos, new PageBrowser(), SihicApp.genNivelesEducativos, false);
         sp_genniveleseducativos.setMaxHeight(20);
         sp_genniveleseducativos.setMaxWidth(200);
         sp_genzonaresidencia = new SearchPopover(sb_genzonaresidencia, new PageBrowser(), SihicApp.genZonaResidencia, false);
         sp_genzonaresidencia.setMaxHeight(20);
         sp_genzonaresidencia.setMaxWidth(200);
         sb_genzonaresidencia.getProperties().put("requerido", true);
         sp_genestadosciviles= new SearchPopover(sb_genestadosciviles, new PageBrowser(), SihicApp.genEstadosCiviles, false);
         sp_genestadosciviles.setMaxHeight(20);
         sp_genestadosciviles.setMaxWidth(200);
         sp_gentiposafiliacion= new SearchPopover(sb_gentiposafiliacion, new PageBrowser(), SihicApp.genTiposAfiliacion, false);
         sp_gentiposafiliacion.setMaxHeight(20);
          sp_gentiposafiliacion.setMaxWidth(200);
         sp_gentiposusuarios= new SearchPopover(sb_gentiposusuarios, new PageBrowser(), SihicApp.genTiposUsuarios, false);
         sp_gentiposusuarios.setMaxHeight(20);
         sp_gentiposusuarios.setMaxWidth(200);
         sp_gennivelessusuarios= new SearchPopover(sb_gennivelesusuarios, new PageBrowser(), SihicApp.genNivelesUsuarios, false);
         sp_gennivelessusuarios.setMaxHeight(10);
         sp_gennivelessusuarios.setMaxWidth(200);
         sp_genhorascitas= new SearchPopover(sb_genhorascitas, new PageBrowser(), SihicApp.genHorasCita, false);
         sb_genhorascitas.getProperties().put("requerido", true);
         sp_genhorascitas.setMaxHeight(20);
         sp_genhorascitas.setMaxWidth(200);
         sp_agnmedicos= new SearchPopover(sb_agnmedicos, new PageBrowser(), SihicApp.agnMedicos, false);
         sb_agnmedicos.getProperties().put("requerido", true);
         sp_agnmedicos.setMaxHeight(20);
         sp_agnmedicos.setMaxWidth(200);
         sp_agnconsultorios= new SearchPopover(sb_agnconsultorios, new PageBrowser(), SihicApp.agnConsultorios, false);
          sb_agnconsultorios.getProperties().put("requerido", true);
         sp_agnconsultorios.setMaxHeight(20);
         sp_geneapb.setMaxWidth(200);
         sp_cie10principal= new SearchPopover(sb_cie10principal, new PageBrowser(), SihicApp.hclCie10, false);
         
         sp_cie10principal.setMaxHeight(20);
         sp_agnconsultorios.setMaxWidth(200);
         sp_cie10ingreso= new SearchPopover(sb_cie1ingreso, new PageBrowser(), SihicApp.hclCie10, false);
         sp_cie10ingreso.setMaxHeight(20);
         sp_cie10secundario= new SearchPopover(sb_cie10secundario, new PageBrowser(), SihicApp.hclCie10, false);
         sp_cie10secundario.setMaxHeight(20);
         sp_cie10ingreso.setMaxWidth(200);
         sp_centrocostos= new SearchPopover(sb_centrocostos, new PageBrowser(), SihicApp.centrocostos, false);
         sp_centrocostos.setMaxHeight(20);
         sp_centrocostos.setMaxWidth(200);
          sb_centrocostos.getProperties().put("requerido", true);
         sp_procedimiento= new SearchPopover(sb_procedimiento, new PageBrowser(), SihicApp.s_kardex, false);
         sb_procedimiento.getProperties().put("requerido", true);
          sp_procedimiento.setMaxHeight(20);
          sp_procedimiento.setMaxWidth(200);
          sp_rh= new SearchPopover(sb_rh, new PageBrowser(), SihicApp.rh, false);
          sp_rh.setMaxHeight(20);
          sp_rh.setMaxWidth(200);
          sp_agncitas= new SearchPopover(sb_agncitas, new PageBrowser(), SihicApp.agnCitasTemp, false);
          sp_agncitas.setMaxHeight(20);
          sp_agncitas.setMaxWidth(200);
          cantidad.getProperties().put("requerido", true);
          copagos.getProperties().put("requerido", true);
          cuotamoderadora.getProperties().put("requerido", true);
          noautorizacion.getProperties().put("requerido", true);
       
       
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        genPersonas = new GenPersonas();
        genPacientes = new GenPacientes();
       
        genPersonasControllerClient = new GenPersonasControllerClient();
        genEapbControllerClient = new GenEapbControllerClient();
        
        la_gentiposdocumentos = new Label("Tipo Documento(*):");
        la_documento = new Label("No Identificación(*):");
        la_genestadosciviles = new Label("Estado Civil:");
        la_primer_nombre = new Label("Primer Nombre(*):");
        la_segundo_nombre = new Label("Segundo Nombre:");
        la_primer_apellido = new Label("Primer Apellido(*):");
        la_segundo_apellido = new Label("Segundo Apellido:");
        la_telefono = new Label("Telefono:");

        la_genmunicipios = new Label("Ciudad:");
        la_direccion = new Label("Dirección:");
        la_barrio = new Label("Barrio:");
        la_email = new Label("Email:");
        la_gensexo = new Label("Sexo(*):");
        la_genetnias = new Label("Etnias:");
        la_genniveleseducativos = new Label("Nivel Educativo:");
        la_genprofesiones = new Label("Profesión");
        profesion = new TextField();
        la_genzonaresidencia = new Label("Zona Residencia(*)    :");
        la_fechanacimiento = new Label("Fecha de Nacimiento(*):");
        la_observaciones = new Label("Observaciones:");

        la_eapb = new Label("Eps: ");
        la_tipoafiliacion = new Label("Tipo afiliación: ");
        la_tipousuario = new Label("Tipo usuario: ");
        la_nivelusuario = new Label("Nivel usuario: ");
         
        eapb = new ChoiceBox();
        eapb.setMaxWidth(300);
       
        tipoafiliacion = new ChoiceBox();
        tipousuario = new ChoiceBox();
        nivelusuario = new ChoiceBox();
        nivelusuario.setMaxWidth(300);
        tipousuario.setMaxWidth(300);
        sb_genprofesiones.setMaxWidth(300);
        sb_genmunicipios.setMaxWidth(300);
        sb_eapb.setMaxWidth(300);
        sb_aseguradora.setMaxWidth(300);
        sb_gentipodocumentos.setMaxWidth(300);
        sb_genetnias.setMaxWidth(300);
        sb_genzonaresidencia.setMaxWidth(300);
        sb_gentiposusuarios.setMaxWidth(300);
        sb_genniveleseducativos.setMaxWidth(300);
        sb_gensexo.setMaxWidth(300);
        sb_agnmedicos.setMaxWidth(300);
        sb_agnconsultorios.setMaxWidth(300);
        sb_genhorascitas.setMaxWidth(300);
        sb_centrocostos.setMaxWidth(300);
        sb_cie10principal.setMaxWidth(300);
        sb_cie10secundario.setMaxWidth(300);
        sb_cie1ingreso.setMaxWidth(300);
        sb_genestadosciviles.setMaxWidth(300);
        tipocuenta.setMaxWidth(300);
        tipocie10.setMaxWidth(300);
      
        sb_documento.setMaxWidth(300);
        sb_rh.setMaxWidth(300);
        sb_procedimiento.setMaxWidth(300);
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
        
        telefono.setMaxWidth(300);
        direccion = new TextField();
        direccion.setMaxWidth(300);
        barrio = new TextField();
        email = new TextField();
        email.setMaxWidth(300);
        fechanacimiento = new DatePicker(LocalDate.now());
        fechanacimiento.setPromptText("dd/MM/aaaa");
         fechanacimiento.getProperties().put("requerido", true);
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
         imageView = new ImageView("/images/comprobante.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
         bu_printcomprobante = new Button("", imageView);
        tributaria = new Button("", imageView);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save,nuevo,bu_printcomprobante);
      
          bu_printcomprobante.setOnAction(e -> {
            try {
                ImprimirPdf.fpdfcomprobanteprocedimiento();
            } catch (IOException ex) {
                Logger.getLogger(DatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(DatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
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
        
//******************* datos de cita********************************
        gp_genpersonas = new GridPane();
         gp_genpersonas.add(la_agncitas, 0, 0);
        gp_genpersonas.add(sb_agncitas, 1, 0,3,1);
        gp_genpersonas.add(la_fechacita, 0, 1);
        gp_genpersonas.add(fechacita, 1, 1);
        gp_genpersonas.add(la_agnmedicos, 2, 1);
        gp_genpersonas.add(sb_agnmedicos, 3, 1);
        gp_genpersonas.add(la_genhorascitas, 4, 1);
        gp_genpersonas.add(sb_genhorascitas, 5, 1);
        gp_genpersonas.add(la_agnconsultorio, 0, 2);
        gp_genpersonas.add(sb_agnconsultorios, 1, 2);
//*******************datos de paciente******************************
        gp_genpersonas.add(la_documento, 2, 2);
        gp_genpersonas.add(sb_documento, 3, 2);
        gp_genpersonas.add(la_gentiposdocumentos, 4, 2);
        gp_genpersonas.add(sb_gentipodocumentos,5, 2);
        gp_genpersonas.add(la_primer_nombre, 0, 3);
        gp_genpersonas.add(primer_nombre, 1, 3);
        gp_genpersonas.add(la_segundo_nombre, 2, 3);
        gp_genpersonas.add(segundo_nombre, 3, 3);
         gp_genpersonas.add(la_primer_apellido, 4, 3);
        gp_genpersonas.add(primer_apellido, 5, 3);
        gp_genpersonas.add(la_segundo_apellido, 0, 4);
        gp_genpersonas.add(segundo_apellido, 1, 4);
        gp_genpersonas.add(la_genmunicipios, 2, 4);
        gp_genpersonas.add(sb_genmunicipios, 3, 4);
        gp_genpersonas.add(la_direccion, 4, 4);
        gp_genpersonas.add(direccion, 5, 4);
        gp_genpersonas.add(la_barrio, 0, 5);
        gp_genpersonas.add(barrio, 1, 5);
        gp_genpersonas.add(la_gensexo, 2, 5);
        gp_genpersonas.add(sb_gensexo, 3, 5);
        gp_genpersonas.add(la_genetnias, 4, 5);
        gp_genpersonas.add(sb_genetnias, 5, 5);
        gp_genpersonas.add(la_email, 0, 6);
        gp_genpersonas.add(email, 1, 6);
        gp_genpersonas.add(la_telefono, 2, 6);
        gp_genpersonas.add(telefono, 3, 6);
        gp_genpersonas.add(la_genniveleseducativos, 4, 6);
        gp_genpersonas.add(sb_genniveleseducativos, 5, 6);
        gp_genpersonas.add(la_genprofesiones, 0, 7);
        gp_genpersonas.add(sb_genprofesiones, 1, 7);
        gp_genpersonas.add(la_fechanacimiento, 2, 7);
        gp_genpersonas.add(fechanacimiento, 3, 7);
        gp_genpersonas.add(la_genzonaresidencia, 4, 7);
        gp_genpersonas.add(sb_genzonaresidencia, 5, 7);
        gp_genpersonas.add(la_genestadosciviles, 0, 8);
        gp_genpersonas.add(sb_genestadosciviles, 1, 8);
       // gp_genpersonas.add(la_observaciones, 2, 7);
       // gp_genpersonas.add(observaciones, 3, 7);
        gp_genpersonas.add(la_tipoafiliacion, 2, 8);
        gp_genpersonas.add(sb_gentiposafiliacion, 3, 8);
        gp_genpersonas.add(la_tipousuario, 4, 8);
        gp_genpersonas.add(sb_gentiposusuarios, 5, 8);
        gp_genpersonas.add(la_eapb, 0, 9);
        gp_genpersonas.add(sb_eapb, 1, 9);
      
        gp_genpersonas.add(new Label("Aseguradora:"), 2, 9);
        gp_genpersonas.add(sb_aseguradora, 3, 9);
        //*****************datos de autorización*************************
         gp_genpersonas.add(la_numeroautorizacion, 4, 9);
         gp_genpersonas.add(noautorizacion, 5, 9);
         gp_genpersonas.add(la_tipocuenta, 0, 10);
         gp_genpersonas.add(tipocuenta, 1, 10);
         gp_genpersonas.add(la_clasificacionorigen, 2,10);
         gp_genpersonas.add(clasificacionorigen, 3, 10);
         gp_genpersonas.add(la_centrodecostos, 4, 10);
         gp_genpersonas.add(sb_centrocostos, 5, 10);
         gp_genpersonas.add(la_cie10principal, 0, 11);
         gp_genpersonas.add(sb_cie10principal, 1, 11);
         gp_genpersonas.add(la_tipocie10, 2, 11);
         gp_genpersonas.add(tipocie10, 3, 11);
         gp_genpersonas.add(la_cie10ingreso, 4, 11);
         gp_genpersonas.add(sb_cie1ingreso, 5, 11);
         gp_genpersonas.add(la_cie10secundario, 0, 12);
         gp_genpersonas.add(sb_cie10secundario, 1, 12);
         gp_genpersonas.add(la_procedimiento, 2, 12);
         gp_genpersonas.add(sb_procedimiento, 3, 12);
         gp_genpersonas.add(la_cantidad, 4, 12);
         gp_genpersonas.add(cantidad, 5, 12);
         gp_genpersonas.add(la_copagos, 0, 13);
         gp_genpersonas.add(copagos, 1, 13);
         gp_genpersonas.add(la_cuotamoderadora, 2, 13);
         gp_genpersonas.add(cuotamoderadora, 3, 13);
         gp_genpersonas.add(la_rh, 4, 13);
         gp_genpersonas.add(sb_rh, 5, 13);
         gp_genpersonas.add(la_discapacidad, 0, 14);
         gp_genpersonas.add(discapacidad, 1, 14);
         gp_genpersonas.add(la_estadopaciente, 2, 14);
         gp_genpersonas.add(estadopaciente, 3, 14);
         gp_genpersonas.add(la_tipoatencion, 4, 14);
         gp_genpersonas.add(tiposervicio, 5, 14);
         gp_genpersonas.add(la_nivelusuario, 0, 15);
         gp_genpersonas.add(sb_gennivelesusuarios, 1, 15);
         gp_genpersonas.add(hb_botones, 0, 16, 6, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
         gp_genpersonas.getStylesheets().add(SihicApp.hojaestilos);
        gp_genpersonas.getStyleClass().add("background");
        gp_genpersonas.setHgap(3);
        gp_genpersonas.setVgap(3);
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
        stack.getChildren().addAll(gp_genpersonas, Gp_Message);
        event_inputs();
      

        sp_geneapb.getStylesheets().add(SihicApp.hojaestilos);
        gp_genpersonas.add(sp_agncitas, 1, 1, 3, 5);
        gp_genpersonas.add(sp_geneapb, 1, 10, 3, 5);
        gp_genpersonas.add(sp_aseguradora, 2, 10, 3, 5   );
        gp_genpersonas.add(sp_genmunicipios, 3, 5, 3, 5);
        gp_genpersonas.add(sp_genprofesiones, 1, 8, 3, 5);
        gp_genpersonas.add(sp_documento, 3, 3, 3, 5);
        gp_genpersonas.add(sp_gentipodocumentos, 3, 3, 3, 5);
        gp_genpersonas.add(sp_gensexo, 3, 6, 3, 5);
        gp_genpersonas.add(sp_genetnias, 3, 6, 3, 5);
        gp_genpersonas.add(sp_genniveleseducativos, 3, 7, 3, 5);
        gp_genpersonas.add(sp_genzonaresidencia, 3, 8, 3, 5);
        gp_genpersonas.add(sp_genestadosciviles, 1, 9, 3, 5);
        gp_genpersonas.add(sp_gentiposafiliacion, 3, 9, 3, 5);
        gp_genpersonas.add(sp_gentiposusuarios, 3, 9, 3, 5);
      
        gp_genpersonas.add(sp_agnmedicos, 3, 2, 3, 5);
        gp_genpersonas.add(sp_genhorascitas, 3, 2, 3, 5);
        gp_genpersonas.add(sp_agnconsultorios, 1, 3, 3, 5);
        gp_genpersonas.add(sp_centrocostos, 3, 11, 3, 5);
        gp_genpersonas.add(sp_cie10principal, 1, 12, 3, 5);
        gp_genpersonas.add(sp_cie10ingreso, 3, 12, 3, 5);
        gp_genpersonas.add(sp_cie10secundario, 1, 14, 3, 3);
        gp_genpersonas.add(sp_procedimiento, 2, 14, 3, 3);
        gp_genpersonas.add(sp_gennivelessusuarios, 0, 16, 3, 1);
        gp_genpersonas.add(sp_rh, 3, 15, 3, 2);
        GridPane.setValignment(sp_gennivelessusuarios, VPos.TOP);
         GridPane.setValignment(sp_rh, VPos.TOP);
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
                set_datos();
                if (SihicApp.agnCitasTemp.getId() == null) {
                   SihicApp.facturacionMasivaControllerClient.create();
                } else {
                      SihicApp.facturacionMasivaControllerClient.update();
                }
                FacturacionMasiva.addProduct();
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

    private void set_datos() {
       
        SihicApp.genPersonas.setBarrio(barrio.getText());
        SihicApp.genPersonas.setDireccion(direccion.getText());
        SihicApp.genPersonas.setDocumento(sb_documento.getText());
        SihicApp.genPersonas.setEMail(email.getText());
        SihicApp.genPersonas.setFechaCreacion(new Date());
        LocalDate localDate = fechanacimiento.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        SihicApp.genPersonas.setFechaNacimiento(Date.from(instant));
        SihicApp.genPersonas.setFoto("foto");
        if(SihicApp.genEstadosCiviles!=null)
        { 
         if(SihicApp.genEstadosCiviles.getId()!=null)
        { 
            SihicApp.genPersonas.setGenEstadosCiviles(SihicApp.genEstadosCiviles);
        }
        }
         if(SihicApp.genEtnias!=null)
        { 
         if(SihicApp.genEtnias.getId()!=null)
        {    
         SihicApp.genPersonas.setGenEtnias(SihicApp.genEtnias);
        }
        }
        if(SihicApp.s_genMunicipios!=null)
        { 
          if(SihicApp.s_genMunicipios.getId()!=null)
        { 
          SihicApp.genPersonas.setGenMunicipios(SihicApp.s_genMunicipios);
        }
        }
        SihicApp.genPersonas.setGenSexo(SihicApp.genSexo);
        SihicApp.genPersonas.setGenTiposDocumentos(SihicApp.genTiposDocumentos);
        if(SihicApp.genNivelesEducativos!=null)
        { 
        if(SihicApp.genNivelesEducativos.getId()!=null)
        { 
         SihicApp.genPersonas.setGenNivelesEducativos(SihicApp.genNivelesEducativos);
        }
        }
           if(SihicApp.genZonaResidencia!=null)
        { 
        if(SihicApp.genZonaResidencia.getId()!=null)
        { 
        SihicApp.genPersonas.setGen_zona_residencia(SihicApp.genZonaResidencia);
        }
        }
           
        SihicApp.genPersonas.setPrimerApellido(primer_apellido.getText());
        SihicApp.genPersonas.setPrimerNombre(primer_nombre.getText());
        SihicApp.genPersonas.setSegundoApellido(segundo_apellido.getText());
        SihicApp.genPersonas.setSegundoNombre(segundo_nombre.getText());
        SihicApp.genPersonas.setTelefono(telefono.getText());
        SihicApp.genPersonas.seteMail(email.getText());
         if (SihicApp.s_GenProfesiones != null) {
               if (SihicApp.s_GenProfesiones.getId() != null) {
            SihicApp.genPersonas.setGen_profesiones(SihicApp.s_GenProfesiones);
               }
        }
        //*********************datos de paciente*************************************************
        SihicApp.genPacientesTemp.setGenPersonas(SihicApp.genPersonas);
         if(SihicApp.s_genEapb!=null)
        { 
            if(SihicApp.s_genEapb.getId()!=null)
        { 
          SihicApp.genPacientesTemp.setGenEapb(SihicApp.s_genEapb);
        }
        }
        if (SihicApp.aseguradora!= null) {
            if (SihicApp.aseguradora.getId() != null) {
        
            SihicApp.genPacientesTemp.setAseguradora(SihicApp.aseguradora);
        } 
        }
        if (SihicApp.genTiposAfiliacion!= null) 
        {
         if (SihicApp.genTiposAfiliacion.getId()!= null) 
        {
             SihicApp.genPacientesTemp.setGenTiposAfiliacion(SihicApp.genTiposAfiliacion);
        }
        }
         if (SihicApp.genTiposUsuarios!= null) 
        {
             if (SihicApp.genTiposUsuarios.getId() != null) 
        {
          SihicApp.genPacientesTemp.setGenTiposUsuarios(SihicApp.genTiposUsuarios);
        }
        }
       
        SihicApp.genPacientesTemp.setFactorRh(SihicApp.rh);
        if(SihicApp.genNivelesUsuarios!=null)
        { 
        if(SihicApp.genNivelesUsuarios.getId()!=null)
        { 
        SihicApp.genPacientesTemp.setGenNivelesUsuarios(SihicApp.genNivelesUsuarios);
        }
        }
        
        //**********************datos de cita********************************************************
        SihicApp.agnCitasTemp.setFechaHora(sihic.util.UtilDate.localdatetodate(fechacita.getValue()));
        SihicApp.agnCitasTemp.setAgnMedicos(SihicApp.agnMedicos);
        SihicApp.agnCitasTemp.setAgnConsultorios(SihicApp.agnConsultorios);
        SihicApp.agnCitasTemp.setServicio(SihicApp.s_kardex.getProducto());
        SihicApp.agnCitasTemp.setGenPacientes(SihicApp.genPacientesTemp);
        SihicApp.agnCitasTemp.setGenHorasCita(SihicApp.genHorasCita);
        //*******************************datos detalle facturacion*************************************
        SihicApp.facturaItem.setAgnCitas(SihicApp.agnCitasTemp);
        SihicApp.facturaItem.setClasificacionorigen(clasificacionorigen.getText());
        SihicApp.facturaItem.setCopago(new BigDecimal(copagos.getText().trim()));
        SihicApp.facturaItem.setCuotamoderadora(new BigDecimal(cuotamoderadora.getText().trim()));
        if(SihicApp.cie10principal!=null)
        {
              if(SihicApp.cie10principal.getId()!=null)
        {
         SihicApp.facturaItem.setDiagnosticoprincipal(SihicApp.cie10principal);
        }
        }
        if(SihicApp.cie10ingreso!=null)
        { 
            if(SihicApp.cie10ingreso.getId()!=null)
        {
         SihicApp.facturaItem.setDiagnosticoingreso(SihicApp.cie10ingreso);
        }
        }
        if(SihicApp.cie10secundario!=null)
        {     
            if(SihicApp.cie10secundario.getId()!=null)
        { 
         SihicApp.facturaItem.setDiagnosticosecundario(SihicApp.cie10secundario);
        }
        }
         if(SihicApp.s_genEapb!=null)
        {   
             if(SihicApp.s_genEapb.getId()!=null)
        {   
         SihicApp.facturaItem.setGenEapb(SihicApp.s_genEapb);
        }
        }
        SihicApp.facturaItem.setNoautorizacion(noautorizacion.getText());
        SihicApp.facturaItem.setProducto(SihicApp.s_kardex.getProducto());
        SihicApp.facturaItem.setQuantity(Float.valueOf(cantidad.getText().trim()));
        if(SihicApp.centrocostos!=null)
        {   
        if(SihicApp.centrocostos.getId()!=null)
        {   
         SihicApp.facturaItem.setSucursales(SihicApp.centrocostos);
        }
        }
        if(tipocuenta.getText()!=null)
        {
            if(tipocuenta.getText().length()>10)
            {
                tipocuenta.setText(tipocuenta.getText().substring(0,9));
            }
        }
        if(clasificacionorigen.getText()!=null)
        {
            if(clasificacionorigen.getText().length()>1)
            {
                clasificacionorigen.setText(clasificacionorigen.getText().substring(0,1));
            }
        }
         if(discapacidad.getText()!=null)
        {
            if(discapacidad.getText().length()>1)
            {
                discapacidad.setText(discapacidad.getText().substring(0,1));
            }
        }
          if(estadopaciente.getText()!=null)
        {
            if(estadopaciente.getText().length()>1)
            {
                estadopaciente.setText(estadopaciente.getText().substring(0,1));
            }
        }
           if(tiposervicio.getText()!=null)
        {
            if(tiposervicio.getText().length()>1)
            {
                tiposervicio.setText(tiposervicio.getText().substring(0,2));
            }
        }
        SihicApp.facturaItem.setTipocuenta(tipocuenta.getText());
        SihicApp.facturaItem.setTipodiagnostico(tipocie10.getText());
        SihicApp.facturaItem.setDiscapacidad(discapacidad.getText());
        SihicApp.facturaItem.setEstadopaciente(estadopaciente.getText());
        SihicApp.facturaItem.setTipoatencion(tiposervicio.getText());
        
       
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_genpersonas.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if(general.getText()==null)
                    {
                        general.setText("");
                    }
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
                            if(general.getText()==null)
                            {
                                general.setText("");
                            }
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

  

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }

    private void event_inputs() {
      
        sb_agncitas.setOnAction(e -> {
             get_datos();        });
        
        fechacita.setOnAction(e -> {
             SihicApp.pfechacita=fechacita.getValue().toString();
            sb_agnmedicos.requestFocus();
        });
         sb_agnmedicos.setOnAction(e -> {
            sb_genhorascitas.requestFocus();
        });
        sb_agnmedicos.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            fechacita.requestFocus();
           }
        });
         sb_genhorascitas.setOnAction(e -> {
            sb_agnconsultorios.requestFocus();
        });
        sb_genhorascitas.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_agnmedicos.requestFocus();
           }
        });
         sb_agnconsultorios.setOnAction(e -> {
        sb_documento.requestFocus();
        });
        sb_agnconsultorios.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_genhorascitas.requestFocus();
           }
        });
         sb_documento.setOnAction(e -> {
             if(SihicApp.genPacientesTemp!=null)
             {
                 if(SihicApp.genPacientesTemp.getId()!=null)
                 {
                     get_datos_personales();
                     get_datos_paciente();
                 }
             }
            sb_gentipodocumentos.requestFocus();
        });
        sb_documento.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_agnconsultorios.requestFocus();
           }
        });
        sb_documento.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().DELETE) || e.getCode().equals(e.getCode().BACK_SPACE))
           {          
         //  SihicApp.genPersonas=null;
           //SihicApp.genPersonas=new GenPersonas();
           //get_datos_personales();
           }
        });
        sb_gentipodocumentos.setOnAction(e -> {
            primer_nombre.requestFocus();
        });
        sb_gentipodocumentos.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_documento.requestFocus();
           }
        });
         primer_nombre.setOnAction(e -> {
            segundo_nombre.requestFocus();
        });
        primer_nombre.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_gentipodocumentos.requestFocus();
           }
        });
         segundo_nombre.setOnAction(e -> {
            primer_apellido.requestFocus();
        });
        segundo_nombre.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            primer_nombre.requestFocus();
           }
        });
         primer_apellido.setOnAction(e -> {
            segundo_apellido.requestFocus();
        });
        primer_apellido.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            segundo_nombre.requestFocus();
           }
        });
         segundo_apellido.setOnAction(e -> {
            sb_genmunicipios.requestFocus();
        });
        segundo_apellido.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            primer_apellido.requestFocus();
           }
        });
         sb_genmunicipios.setOnAction(e -> {
            direccion.requestFocus();
        });
        sb_genmunicipios.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            segundo_apellido.requestFocus();
           }
        });
         direccion.setOnAction(e -> {
            barrio.requestFocus();
        });
        direccion.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_genmunicipios.requestFocus();
           }
        });
         barrio.setOnAction(e -> {
            sb_gensexo.requestFocus();
        });
        barrio.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            direccion.requestFocus();
           }
        });
         sb_gensexo.setOnAction(e -> {
            sb_genetnias.requestFocus();
        });
        sb_gensexo.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            barrio.requestFocus();
           }
        });
         sb_genetnias.setOnAction(e -> {
            email.requestFocus();
        });
        sb_genetnias.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_gensexo.requestFocus();
           }
        });
         email.setOnAction(e -> {
            telefono.requestFocus();
        });
        email.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_genetnias.requestFocus();
           }
        });
         telefono.setOnAction(e -> {
            sb_genniveleseducativos.requestFocus();
        });
        telefono.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            email.requestFocus();
           }
        });
         sb_genniveleseducativos.setOnAction(e -> {
            sb_genprofesiones.requestFocus();
        });
        sb_genniveleseducativos.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            telefono.requestFocus();
           }
        });
         sb_genprofesiones.setOnAction(e -> {
            fechanacimiento.requestFocus();
        });
        sb_genprofesiones.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_genniveleseducativos.requestFocus();
           }
        });
         fechanacimiento.setOnAction(e -> {
            sb_genzonaresidencia.requestFocus();
        });
        fechanacimiento.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_genprofesiones.requestFocus();
           }
        });
         sb_genzonaresidencia.setOnAction(e -> {
            sb_genestadosciviles.requestFocus();
        });
        sb_genzonaresidencia.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            fechanacimiento.requestFocus();
           }
        });
         sb_genestadosciviles.setOnAction(e -> {
            sb_gentiposafiliacion.requestFocus();
        });
        sb_genestadosciviles.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_genzonaresidencia.requestFocus();
           }
        });
         sb_gentiposafiliacion.setOnAction(e -> {
            sb_gentiposusuarios.requestFocus();
        });
        sb_gentiposafiliacion.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_genestadosciviles.requestFocus();
           }
        });
         sb_gentiposusuarios.setOnAction(e -> {
            sb_eapb.requestFocus();
        });
        sb_gentiposusuarios.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_gentiposafiliacion.requestFocus();
           }
        });
         sb_eapb.setOnAction(e -> {
            sb_aseguradora.requestFocus();
        });
        sb_eapb.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_gentiposusuarios.requestFocus();
           }
        });
         sb_aseguradora.setOnAction(e -> {
            noautorizacion.requestFocus();
        });
        sb_aseguradora.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_eapb.requestFocus();
           }
        });
         noautorizacion.setOnAction(e -> {
            tipocuenta.requestFocus();
        });
        noautorizacion.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_aseguradora.requestFocus();
           }
        });
         tipocuenta.setOnAction(e -> {
            clasificacionorigen.requestFocus();
        });
        tipocuenta.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            noautorizacion.requestFocus();
           }
        });
         clasificacionorigen.setOnAction(e -> {
            sb_centrocostos.requestFocus();
        });
        clasificacionorigen.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            tipocuenta.requestFocus();
           }
        });
         sb_centrocostos.setOnAction(e -> {
            sb_cie10principal.requestFocus();
        });
        sb_centrocostos.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            clasificacionorigen.requestFocus();
           }
        });
         sb_cie10principal.setOnAction(e -> {
             SihicApp.cie10principal=SihicApp.hclCie10;
            tipocie10.requestFocus();
        });
        sb_cie10principal.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_centrocostos.requestFocus();
           }
        });
         tipocie10.setOnAction(e -> {
            sb_cie1ingreso.requestFocus();
        });
        tipocie10.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_cie10principal.requestFocus();
           }
        });
         sb_cie1ingreso.setOnAction(e -> {
            SihicApp.cie10ingreso=SihicApp.hclCie10;
            sb_cie10secundario.requestFocus();
        });
        sb_cie1ingreso.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            tipocie10.requestFocus();
           }
        });
         sb_cie10secundario.setOnAction(e -> {
            SihicApp.cie10secundario=SihicApp.hclCie10;
            sb_procedimiento.requestFocus();
        });
        sb_cie10secundario.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_cie1ingreso.requestFocus();
           }
        });
         sb_procedimiento.setOnAction(e -> {
            
            cantidad.requestFocus();
        });
        sb_procedimiento.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_cie10secundario.requestFocus();
           }
        });
         cantidad.setOnAction(e -> {
            copagos.requestFocus();
        });
        cantidad.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_procedimiento.requestFocus();
           }
        });
         copagos.setOnAction(e->{
           
           cuotamoderadora.requestFocus();
       });
         copagos.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            cantidad.requestFocus();
           }
        });
         cuotamoderadora.setOnAction(e->{
           
           sb_rh.requestFocus();
       });
         cuotamoderadora.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            copagos.requestFocus();
           }
        });
         sb_rh.setOnAction(e->{
           
           discapacidad.requestFocus();
       });
         sb_rh.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            cuotamoderadora.requestFocus();
           }
        });
          discapacidad.setOnAction(e->{
           
           estadopaciente.requestFocus();
       });
         discapacidad.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            sb_rh.requestFocus();
           }
        });  
            estadopaciente.setOnAction(e->{
           
           tiposervicio.requestFocus();
       });
         estadopaciente.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            discapacidad.requestFocus();
           }
        });
            tiposervicio.setOnAction(e->{
           
          sb_gennivelesusuarios.requestFocus();
       });
         tiposervicio.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            estadopaciente.requestFocus();
           }
        });
           sb_gennivelesusuarios.setOnAction(e->{
           
           save.requestFocus();
       });
         sb_gennivelesusuarios.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            tiposervicio.requestFocus();
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
         save.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
               sb_gennivelesusuarios.requestFocus();
           }
        });
         save.setOnAction(e -> {
            
            try {
                save();
                nuevo.requestFocus();
            } catch (InterruptedException ex) {
                Logger.getLogger(DatosFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        });
        nuevo.setOnAction(e -> {
          
           
              
                new_record();
           
            fechacita.requestFocus();
           
        });
        nuevo.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {

               new_record();
          
            fechacita.requestFocus();
               

            }
        });
       nuevo.setOnKeyReleased(e -> {
           if(e.getCode().equals(e.getCode().ESCAPE))
           {          
            save.requestFocus();
           }
        }); 
      
    }

    private void new_record() {
        SihicApp.genPersonas = null;
        SihicApp.genPersonas = new GenPersonas();
        SihicApp.agnCitasTemp = null;
        SihicApp.agnCitasTemp = new AgnCitas();
        SihicApp.agnMedicos = null;
        SihicApp.agnMedicos = new AgnMedicos();
        SihicApp.agnConsultorios = null;
        SihicApp.agnConsultorios = new AgnConsultorios();
        SihicApp.genHorasCita = null;
        SihicApp.genHorasCita = new GenHorasCita();
        SihicApp.genTiposDocumentos = null;
        SihicApp.genTiposDocumentos = new GenTiposDocumentos();
        SihicApp.s_genMunicipios = null;
        SihicApp.s_genMunicipios = new GenMunicipios();
        SihicApp.genEtnias = null;
        SihicApp.genEtnias = new GenEtnias();
        SihicApp.genSexo = null;
        SihicApp.genSexo = new GenSexo();
        SihicApp.genNivelesEducativos = null;
        SihicApp.genNivelesEducativos = new GenNivelesEducativos();
        SihicApp.s_GenProfesiones = null;
        SihicApp.s_GenProfesiones = new GenProfesiones();
        SihicApp.genZonaResidencia = null;
        SihicApp.genZonaResidencia = new GenZonaResidencia();
        SihicApp.genEstadosCiviles= null;
        SihicApp.genEstadosCiviles = new GenEstadosCiviles();
        SihicApp.genTiposAfiliacion= null;
        SihicApp.genTiposAfiliacion = new GenTiposAfiliacion();
        SihicApp.genTiposUsuarios = null;
        SihicApp.genTiposUsuarios = new GenTiposUsuarios();
        SihicApp.s_genEapb = null;
        SihicApp.s_genEapb = new GenEapb();
        SihicApp.aseguradora = null;
        SihicApp.aseguradora = new GenEapb();
        SihicApp.centrocostos= null;
        SihicApp.centrocostos = new Sucursales();
        SihicApp.cie10principal = null;
        SihicApp.cie10principal = new HclCie10();
        SihicApp.cie10ingreso = null;
        SihicApp.cie10ingreso = new HclCie10();
        SihicApp.cie10secundario = null;
        SihicApp.cie10secundario = new HclCie10();
        SihicApp.s_kardex = null;
        SihicApp.s_kardex = new Kardex();
        SihicApp.s_producto = null;
        SihicApp.s_producto = new Producto();
        SihicApp.facturaItem=null;
        SihicApp.facturaItem=new FacturaItem();
        SihicApp.genPacientesTemp=null;
        SihicApp.genPacientesTemp=new GenPacientes();
         SihicApp.genNivelesUsuarios=null;
        SihicApp.genNivelesUsuarios=new GenNivelesUsuarios();
        
        
        
        for (Object n : gp_genpersonas.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");
               
            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    general.getSelectionModel().select(-1);
                   
                }

                if (n.getClass() == DatePicker.class) {
                    DatePicker general = (DatePicker) n;
                    general.setValue(LocalDate.now());
                    general.setDisable(false);
                }
                if (n.getClass() == SearchBox.class) {
                    SearchBox general = (SearchBox) n;
                   general.setText("");
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
        
        if (SihicApp.facturaItem == null) {
            
          new_record();   
        } else {
          if (SihicApp.facturaItem.getId() == null) {
            
          new_record();   
        }
          else
          {
               if(SihicApp.facturaItem.getAgnCitas()!=null)
               {
                 SihicApp.agnCitasTemp=SihicApp.facturaItem.getAgnCitas();
               }
              else
              {
                SihicApp.agnCitasTemp=SihicApp.facturaItem.getHclConsultas().getAgnCitas();  
               }
              get_datos();
          }
        }

    }
private void get_datos()
{
    //************************CAPTURA DATOS DE CITA**************************************************
   
     SihicApp.genPacientesTemp=SihicApp.agnCitasTemp.getGenPacientes();
     SihicApp.genPersonas=SihicApp.genPacientesTemp.getGenPersonas();
     fechacita.setValue(sihic.util.UtilDate.formatoyearmesdia(SihicApp.agnCitasTemp.getFechaHora()));
     SihicApp.agnMedicos=SihicApp.agnCitasTemp.getAgnMedicos();
     if(SihicApp.agnMedicos.getGenPersonas()!=null)
     {
      sb_agnmedicos.setText(SihicApp.agnMedicos.getGenPersonas().getNombres());
     }
     sp_agnmedicos.hide();
     SihicApp.genHorasCita=SihicApp.agnCitasTemp.getGenHorasCita();
     sb_genhorascitas.setText(SihicApp.genHorasCita.getHora()+":"+SihicApp.genHorasCita.getMinutos()+" "+(SihicApp.genHorasCita.getZona()==0?"AM":"PM"));
     sp_genhorascitas.hide();
     SihicApp.agnConsultorios=SihicApp.agnCitasTemp.getAgnConsultorios();
     
     sb_agnconsultorios.setText(SihicApp.agnConsultorios!=null?SihicApp.agnConsultorios.getDescripcion():"");
     sp_agnconsultorios.hide();
     //*************************CAPTURA DATOS DE PERSONA*************************************************
     get_datos_personales();
     //**********************************CAPTURA DATOS PACIENTES*******************************************************
     get_datos_paciente();
      //***********************************CAPTURA DATOS DETALLES FACTURACION********************
      noautorizacion.setText(SihicApp.facturaItem.getNoautorizacion());
      tipocuenta.setText(SihicApp.facturaItem.getTipocuenta());
      clasificacionorigen.setText(SihicApp.facturaItem.getClasificacionorigen());
      SihicApp.centrocostos=SihicApp.facturaItem.getSucursales();
      if(SihicApp.centrocostos!=null){
       if(SihicApp.centrocostos.getId()!=null){
      sb_centrocostos.setText(SihicApp.centrocostos.getNombre());
      sp_centrocostos.hide();
       }
      }
      else
      {
          SihicApp.centrocostos=new Sucursales();
      }
      SihicApp.cie10principal=SihicApp.facturaItem.getDiagnosticoprincipal();
      if(SihicApp.cie10principal!=null)
      {    
       if(SihicApp.cie10principal.getId()!=null)
      {
      sb_cie10principal.setText(SihicApp.cie10principal.getDescripcion());
        sp_cie10principal.hide();
      }
      }
      else
      {
        SihicApp.cie10principal=new HclCie10();
      }
      tipocie10.setText(SihicApp.facturaItem.getTipodiagnostico());
      SihicApp.cie10ingreso=SihicApp.facturaItem.getDiagnosticoingreso();
      if(SihicApp.cie10ingreso!=null)
      {    
       if(SihicApp.cie10ingreso.getId()!=null)
      {
      sb_cie1ingreso.setText(SihicApp.cie10ingreso.getDescripcion());
        sp_cie10ingreso.hide();
      }
      }
      else
      {
          SihicApp.cie10ingreso=new HclCie10();
      }
       SihicApp.cie10secundario=SihicApp.facturaItem.getDiagnosticosecundario();
      if(SihicApp.cie10secundario!=null)
      {    
       if(SihicApp.cie10secundario.getId()!=null)
      {
      sb_cie10secundario.setText(SihicApp.cie10secundario.getDescripcion());
        sp_cie10secundario.hide();
      }
      }
      else
      {
          SihicApp.cie10secundario=new HclCie10();
      }
      try{
      SihicApp.s_kardex=SihicApp.li_productosKardex.stream().filter(line->line.getProducto().getCodigo().equals(SihicApp.facturaItem.getProducto().getCodigo())).collect(Collectors.toList()).get(0);
      }catch(Exception e)
      {    
       
      }
      SihicApp.s_producto=SihicApp.facturaItem.getProducto();
      try{
      sb_procedimiento.setText(SihicApp.s_kardex.getProducto().getNombre());
        sp_procedimiento.hide();
      }catch(Exception e)
      {
          
      }
      cantidad.setText(String.valueOf(SihicApp.facturaItem.getQuantity()));
      copagos.setText(String.valueOf(SihicApp.facturaItem.getCopago()));
      cuotamoderadora.setText(String.valueOf(SihicApp.facturaItem.getCuotamoderadora()));
      sb_rh.setText(SihicApp.rh);
        sp_rh.hide();
      discapacidad.setText(SihicApp.facturaItem.getDiscapacidad());
      estadopaciente.setText(SihicApp.facturaItem.getEstadopaciente());
      tiposervicio.setText(SihicApp.facturaItem.getTipoatencion());
}
    
private void get_datos_personales()
{
    SihicApp.genPersonas=SihicApp.genPacientesTemp.getGenPersonas();
     sb_documento.setText(SihicApp.genPersonas.getDocumento());
     sp_documento.hide();
      SihicApp.genTiposDocumentos=SihicApp.genPersonas.getGenTiposDocumentos();
      if(SihicApp.genTiposDocumentos!=null)
      {
       sb_gentipodocumentos.setText(SihicApp.genTiposDocumentos.getNombre());
      }
       sp_gentipodocumentos.hide();
      primer_nombre.setText(SihicApp.genPersonas.getPrimerNombre());
      segundo_nombre.setText(SihicApp.genPersonas.getSegundoNombre());
      primer_apellido.setText(SihicApp.genPersonas.getPrimerApellido());
      segundo_apellido.setText(SihicApp.genPersonas.getSegundoApellido());
      SihicApp.s_genMunicipios=SihicApp.genPersonas.getGenMunicipios();
      
      if(SihicApp.s_genMunicipios!=null)
      {
      if(SihicApp.s_genMunicipios.getId()!=null)
      {  
         sb_genmunicipios.setText(SihicApp.s_genMunicipios.getNombre());
         sp_genmunicipios.hide();
      }
      }
      else
      {
          SihicApp.s_genMunicipios=new GenMunicipios();
      }
      direccion.setText(SihicApp.genPersonas.getDireccion());
      barrio.setText(SihicApp.genPersonas.getBarrio());
      SihicApp.genSexo=SihicApp.genPersonas.getGenSexo();
      sb_gensexo.setText(SihicApp.genSexo.getDescripcion());
      sp_gensexo.hide();
      SihicApp.genEtnias=SihicApp.genPersonas.getGenEtnias();
      if(SihicApp.genEtnias!=null)
      {
      if(SihicApp.genEtnias.getId()!=null)
      {  
         sb_genetnias.setText(SihicApp.genEtnias.getNombre());
          sp_genetnias.hide();
      }
      }
      else
      {
       SihicApp.genEtnias=new GenEtnias();
      }
      email.setText(SihicApp.genPersonas.getEMail());
      telefono.setText(SihicApp.genPersonas.getTelefono());
      SihicApp.genNivelesEducativos=SihicApp.genPersonas.getGenNivelesEducativos();
      if(SihicApp.genNivelesEducativos!=null)
      {
      if(SihicApp.genNivelesEducativos.getId()!=null)
      {  
         sb_genniveleseducativos.setText(SihicApp.genNivelesEducativos.getDescripcion());
         sp_genniveleseducativos.hide();
      }
      }
      else
      {
        SihicApp.genNivelesEducativos=new GenNivelesEducativos();
      }
      SihicApp.s_GenProfesiones=SihicApp.genPersonas.getGen_profesiones();
      if(SihicApp.s_GenProfesiones!=null)
      {
      if(SihicApp.s_GenProfesiones.getId()!=null)
      {  
         sb_genprofesiones.setText(SihicApp.s_GenProfesiones.getDescripcion());
          sp_genprofesiones.hide();
      }
      }
      else
      {
          SihicApp.s_GenProfesiones=new GenProfesiones();
      }
      fechanacimiento.setValue(sihic.util.UtilDate.formatoyearmesdia(SihicApp.genPersonas.getFechaNacimiento()));
        SihicApp.genZonaResidencia=SihicApp.genPersonas.getGen_zona_residencia();
      if(SihicApp.genZonaResidencia!=null)
      {
       if(SihicApp.genZonaResidencia.getId()!=null)
      { 
      SihicApp.genZonaResidencia=SihicApp.genPersonas.getGen_zona_residencia();
      sb_genzonaresidencia.setText(SihicApp.genZonaResidencia.getDescripcion());
      sp_genzonaresidencia.hide();
      }
      }else
      {
         SihicApp.genZonaResidencia=new GenZonaResidencia();
      }
     
       SihicApp.genEstadosCiviles=SihicApp.genPersonas.getGenEstadosCiviles();
      if(SihicApp.genEstadosCiviles!=null)
      {
      if(SihicApp.genEstadosCiviles.getId()!=null)
      {  
         sb_genestadosciviles.setText(SihicApp.genEstadosCiviles.getNombre());
         sp_genestadosciviles.hide();
      }
      }
      else
      {
          SihicApp.genEstadosCiviles=new GenEstadosCiviles();
      }
     
}
private void get_datos_paciente()
{
     SihicApp.genTiposAfiliacion=SihicApp.genPacientesTemp.getGenTiposAfiliacion();
      if(SihicApp.genTiposAfiliacion!=null)
      {    
       if(SihicApp.genTiposAfiliacion.getId()!=null)
      {     
        sb_gentiposafiliacion.setText(SihicApp.genTiposAfiliacion.getDescripcion());
         sp_gentiposafiliacion.hide();
      }
      }
      else
      {
          SihicApp.genTiposAfiliacion=new GenTiposAfiliacion();
      }
      SihicApp.genTiposUsuarios=SihicApp.genPacientesTemp.getGenTiposUsuarios();
      if(SihicApp.genTiposUsuarios!=null)
      {    
       if(SihicApp.genTiposUsuarios.getId()!=null)
      {
      sb_gentiposusuarios.setText(SihicApp.genTiposUsuarios.getNombre());
       sp_gentiposusuarios.hide();
      }
      }
      else
      {
        SihicApp.genTiposUsuarios=new GenTiposUsuarios();
      }
      SihicApp.s_genEapb=SihicApp.genPacientesTemp.getGenEapb();
      if(SihicApp.s_genEapb!=null)
      {    
       if(SihicApp.s_genEapb.getId()!=null)
      {
       sb_eapb.setText(SihicApp.s_genEapb.getNombre());
       sp_geneapb.hide();
      }
      }
      else
      {
        SihicApp.s_genEapb=new GenEapb();
      }
      SihicApp.genNivelesUsuarios=SihicApp.genPacientesTemp.getGenNivelesUsuarios();
      if(SihicApp.genNivelesUsuarios!=null)
      {    
       if(SihicApp.genNivelesUsuarios.getId()!=null)
      {
      sb_gennivelesusuarios.setText(String.valueOf(SihicApp.genNivelesUsuarios.getNivel()));
       sp_gennivelessusuarios.hide();
      }
      }
      else
      {
         SihicApp.genNivelesUsuarios=new GenNivelesUsuarios();
      }
      SihicApp.aseguradora=SihicApp.genPacientesTemp.getAseguradora();
       if(SihicApp.aseguradora!=null)
      {    
       if(SihicApp.aseguradora.getId()!=null)
      {
      sb_aseguradora.setText(SihicApp.aseguradora.getNombre());
       sp_aseguradora.hide();
      }
      }
       else
       {
           SihicApp.aseguradora=new GenEapb();
       }
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
