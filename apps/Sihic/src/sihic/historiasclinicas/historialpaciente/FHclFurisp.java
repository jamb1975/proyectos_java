package sihic.historiasclinicas.historialpaciente;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import message.EstadosMensaje;
import modelo.GenMunicipios;
import modelo.GenPersonas;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.controlador.GenEapbControllerClient;
import sihic.controlador.GenPersonasControllerClient;
import sihic.general.FGenPersonas;
import sihic.general.LoadChoiceBoxGeneral;
import sihic.reportespdf.PdfFurisp;
import sihic.util.UtilDate;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FHclFurisp extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;

    TitledPane tp_I;
    private GridPane gp_I;
    TitledPane tp_II;
    private GridPane gp_II;
    TitledPane tp_III;
    private GridPane gp_III;
    TitledPane tp_IV;
    private GridPane gp_IV;
    TitledPane tp_V;
    private GridPane gp_V;
    TitledPane tp_VI;
    private GridPane gp_VI;
    TitledPane tp_VI_VEHICULOSINVOLUCRADOS;
    private GridPane gp_VI_VEHICULOSINVOLUCRADOS;

    TitledPane tp_VII;
    private GridPane gp_VII;
    TitledPane tp_VIII;
    private GridPane gp_VIII;
    TitledPane tp_IX;
    private GridPane gp_IX;
    TitledPane tp_X;
    private GridPane gp_X;
    private GridPane gp_inicial;
    //radicado,nofactura y datos personales
    private SearchBox sb_genmunicipiosvictimas = new SearchBox();
    private SearchPopover sp_genmunicipiosvictimas;
    private GenPersonas genPersonasvictimas;
    private Label la_noradicado;
    private TextField noradicado;
    private Label la_fecharadicado;
    private DatePicker fecharadicado;
    private Label la_rg;
    private CheckBox rg;
    private Label la_noradicadoanterior;
    private TextField noradicadoanterior;
    private Label la_nofactura;
    private TextField nofactura;
    //********************************I
    private TextField I_razonsocial;
    private TextField I_codigo;
    private TextField I_nit;
    private TextField I_direccion;
    private TextField I_dpto;
    private TextField I_coddpto;
    private TextField I_telefono;
    private TextField I_municipio;
    private TextField I_codmunicipio;
    //***********************************II
    private TextField II_primer_apellidovictimas;
    private TextField II_segundo_apellidovictimas;
    private TextField II_primer_nombrevictimas;
    private TextField II_segundo_nombreapellidos;
    private TextField II_direccion;
    private TextField II_fechanacimiento;
    private TextField II_documento;
    private TextField II_dpto;
    private TextField II_coddpto;
    private TextField II_telefono;
    private TextField II_municipio;
    private TextField II_codmunicipio;
    private ChoiceBox II_condicionaccidentado;
    private TextField II_tipodocumento;
    private TextField II_sexo;
    //***************************III
    private ChoiceBox III_naturales;
    private ChoiceBox III_terroristas;
    private TextField III_cual;
    private TextField III_dir_ocurrencia;
    private DatePicker III_fechaeventoaccicente;
    private TextField III_horaeventoaccicente;
    private TextField III_mineventoaccicente;
    private SearchBox III_sbmunicipio;
    private SearchPopover III_spmunicipio;
    private TextArea III_enuncie;
    private ChoiceBox III_zona;
    private GenPersonasControllerClient genPersonasControllerClient;
    private GenMunicipios III_genMunicipios;
    //********************************** IV
    private ChoiceBox IV_cb_estadoseguro;
    private TextField IV_marca;
    private TextField IV_placa;
    private ChoiceBox IV_cb_tiposervicio;
    private TextField IV_nombreaseguradora;
    private TextField IV_codigoaseguradora;
    private TextField IV_nopoliza;
    private RadioButton IV_rb_intautoridadsi;
    private RadioButton IV_rb_intautoridadno;
    private ToggleGroup IV_tg_interautoridad;
    private RadioButton IV_rb_cobroexcedentesi;
    private RadioButton IV_rb_cobroexcedenteno;
    private ToggleGroup IV_tg_cobroexcedente;
    private DatePicker IV_dp_vigenciadesde;
    private DatePicker IV_dp_vigenciahasta;
    private HBox hb_intautoriada;
    private HBox hb_cobroexcedente;
    //**********************************V.
    private TextField V_primer_apellidovictimas;
    private TextField V_segundo_apellidovictimas;
    private TextField V_primer_nombrevictimas;
    private TextField V_segundo_nombreapellidos;
    private TextField V_direccion;
    private TextField V_fechanacimiento;
    private TextField V_documento;
    private TextField V_telefono;
    private SearchBox V_sbmunicipio = new SearchBox();
    private SearchPopover V_spmunicipio;
    private GenMunicipios V_GenMunicipios;
    private GenPersonas V_genGenPersonas;
    private ChoiceBox V_tipodocumento;
    //**************************************VI
    private TextField VI_primer_apellidovictimas;
    private TextField VI_segundo_apellidovictimas;
    private TextField VI_primer_nombrevictimas;
    private TextField VI_segundo_nombreapellidos;
    private TextField VI_direccion;
    private TextField VI_fechanacimiento;
    private TextField VI_documento;
    private TextField VI_telefono;
    private SearchBox VI_sbmunicipio = new SearchBox();
    private SearchPopover VI_spmunicipio;
    private GenMunicipios VI_GenMunicipios;
    private ChoiceBox VI_tipodocumento;
    private ChoiceBox VI_cb_estadoseguro_vehiculoinvolucrado1;
    private TextField VI_placa_vehiculoinvolucrado1;
    private ChoiceBox VI_tipodocumento_vehiculoinvolucrado1;
    private TextField VI_documento_vehiculoinvolucrado1;
    private ChoiceBox VI_cb_estadoseguro_vehiculoinvolucrado2;
    private TextField VI_placa_vehiculoinvolucrado2;
    private ChoiceBox VI_tipodocumento_vehiculoinvolucrado2;
    private TextField VI_documento_vehiculoinvolucrado2;

    //**************************************VII
    private ChoiceBox VII_cb_tiporeferencia;
    private DatePicker VII_dp_remisiondesde;
    private TextField VII_remisionhora;
    private TextField VII_remisionminutos;
    private TextField VII_prestadorremite;
    private TextField VII_codigoinscripcionremite;
    private TextField VII_profesionalremite;
    private TextField VII_profesionalremitecargo;
    private SearchBox VII_sb_municipioremite;
    private SearchPopover VII_sp_municipioremite;
    private DatePicker VII_dp_fechaaceptacion;
    private TextField VII_aceptacionhora;
    private TextField VII_aceptacionminutos;
    private TextField VII_prestadorrecibe;
    private TextField VII_codigoinscripcionrecibe;
    private TextField VII_profesionalrecibe;
    private TextField VII_profesionalrecibecargo;
    private SearchBox VII_sb_municipiorecibe;
    private SearchPopover VII_sp_municipiorecibe;
    private GenMunicipios VII_genmunicipiorecibe;
    private GenMunicipios VII_genmunicipioremite;
    private TextField VII_direccionremite;
    private TextField VII_direccionrecibe;

    //**********************************************VIII
    private TextField VIII_placa;
    private TextField VIII_transportodesde;
    private TextField VIII_transportohasta;
    private ChoiceBox VIII_tipotransporte;
    private ChoiceBox VIII_zona;
    private TextField VIII_primerapellidoconductor;
    private TextField VIII_segundoapellidoconductor;
    private TextField VIII_primernombreconductor;
    private TextField VIII_segundonombreconductor;
    private ChoiceBox VIII_tipodocumento;
    private TextField VIII_documento;

    //***********************************************************IX
    private DatePicker IX_fechaingreso;
    private TextField IX_horaingreso;
    private TextField IX_minutosingreso;
    private DatePicker IX_fechaegreso;
    private TextField IX_horaegreso;
    private TextField IX_minutosegreso;
    private SearchBox IX_sb_dxmainingreso;
    private SearchPopover IX_sp_dxmainingreso;
    private SearchBox IX_sb_dxotroingreso;
    private SearchPopover IX_sp_dxotroingreso;
    private SearchBox IX_sb_dxotro2ingreso;
    private SearchPopover IX_sp_dxotro2ingreso;
    private SearchBox IX_sb_dxmainegreso;
    private SearchPopover IX_sp_dxmainegreso;
    private SearchBox IX_sb_dxotroegreso;
    private SearchPopover IX_sp_dxotroegreso;
    private SearchBox IX_sb_dxotro2egreso;
    private SearchPopover IX_sp_dxotro2egreso;

    private TextField IX_primerapellidoprofesionaltratante;
    private TextField IX_segundoapellidoprofesionaltratante;
    private TextField IX_primernombreprofesionaltratante;
    private TextField IX_segundonombreprofesionaltratante;
    private ChoiceBox IX_tipodocumento;
    private TextField IX_documento;
    private TextField IX_noregistromedico;
    //********************************************************X
    private TextField X_gastosmedicostotalfacturado;
    private TextField X_gastosmedicosfosyga;
    private TextField X_gastostransportetotalfacturado;
    private TextField X_gastostransportefosyga;

    private Button save;
    private Button pdffurisp;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private VBox vb_generic;
    Thread backgroundThread;
    GenEapbControllerClient genEapbControllerClient;
    private ScrollPane scp_generic;
    private PdfFurisp pdfFurisp;

    public Parent createContent() throws IOException {
        pdfFurisp = new PdfFurisp();
        estadosMensaje = new EstadosMensaje();
        scp_generic = new ScrollPane();
        stack = new StackPane();
        stack.getStylesheets().add(SihicApp.hojaestilos);
        stack.getStyleClass().add("background");
        stack.setMinHeight(600);
        vb_generic = new VBox(7);
        //*********************I. DATOS DE LA INSTITUCION
        tp_I = new TitledPane();
        tp_I.getStylesheets().add(SihicApp.hojaestilos);
        // tp_I.getStyleClass().add("background");
        tp_I.setStyle("color: white;");
        tp_I.setText("I. DATOS DE LA INSTITUCIÓN PRESTADORA DE SERVICIOS DE SALUD");
        gp_I = new GridPane();
        gp_I.getStylesheets().add(SihicApp.hojaestilos);
        gp_I.getStyleClass().add("background");
        gp_I.setHgap(3);
        gp_I.setVgap(3);
        gp_I.setMinWidth(840);
        gp_I.setMaxWidth(840);
        I_razonsocial = new TextField();
        I_codigo = new TextField();
        I_nit = new TextField();
        I_direccion = new TextField();
        I_dpto = new TextField();
        I_coddpto = new TextField();
        I_telefono = new TextField();
        I_municipio = new TextField();
        I_codmunicipio = new TextField();
        gp_I.add(new Label("Razón Social: "), 0, 0, 1, 1);
        gp_I.add(I_razonsocial, 1, 0, 5, 1);
        gp_I.add(new Label("Código Habilitación: "), 0, 1, 1, 1);
        gp_I.add(I_codigo, 1, 1, 1, 1);
        gp_I.add(new Label("Nit: "), 2, 1, 1, 1);
        gp_I.add(I_nit, 3, 1, 3, 1);
        gp_I.add(new Label("Dirección: "), 0, 2, 1, 1);
        gp_I.add(I_direccion, 1, 2, 5, 1);
        gp_I.add(new Label("Departamento: "), 0, 3, 1, 1);
        gp_I.add(I_dpto, 1, 3, 1, 1);
        gp_I.add(new Label("Cod Dpto: "), 2, 3, 1, 1);
        gp_I.add(I_coddpto, 3, 3, 1, 1);
        gp_I.add(new Label("Teléfono: "), 4, 3, 1, 1);
        gp_I.add(I_telefono, 5, 3, 1, 1);
        gp_I.add(new Label("Municipio: "), 0, 4, 1, 1);
        gp_I.add(I_municipio, 1, 4, 3, 1);
        gp_I.add(new Label("Cod Municipio: "), 4, 4, 1, 1);
        gp_I.add(I_codmunicipio, 5, 4, 1, 1);
        tp_I.setContent(gp_I);

        //********************************************IIDATOS DE LA VICTIMA
        tp_II = new TitledPane();
        tp_II.getStylesheets().add(SihicApp.hojaestilos);
        tp_II.setText("II. DATOS DE LA VICTIMA DEL EVENTO CATASTRÓFICO O ACCIEDENTE DE TRANSITO");
        gp_II = new GridPane();
        gp_II.getStylesheets().add(SihicApp.hojaestilos);
        gp_II.getStyleClass().add("background");
        gp_II.setHgap(3);
        gp_II.setVgap(3);
        gp_II.setMinWidth(840);
        gp_II.setMaxWidth(840);
        II_primer_apellidovictimas = new TextField();
        II_segundo_apellidovictimas = new TextField();
        II_primer_nombrevictimas = new TextField();
        II_segundo_nombreapellidos = new TextField();
        II_tipodocumento = new TextField();
        II_documento = new TextField();
        II_fechanacimiento = new TextField();
        II_sexo = new TextField();
        II_direccion = new TextField();
        II_dpto = new TextField();
        II_coddpto = new TextField();
        II_telefono = new TextField();
        II_municipio = new TextField();
        II_codmunicipio = new TextField();
        II_condicionaccidentado = new ChoiceBox();
        II_condicionaccidentado.getItems().add(jenum.EnumCondicionAcciedentado.CONDUCTOR.ordinal(), "CONDUCTOR");
        II_condicionaccidentado.getItems().add(jenum.EnumCondicionAcciedentado.PEATON.ordinal(), "PEATÓN");
        II_condicionaccidentado.getItems().add(jenum.EnumCondicionAcciedentado.OCUPANTE.ordinal(), "OCUPANTE");
        II_condicionaccidentado.getItems().add(jenum.EnumCondicionAcciedentado.CICLISTA.ordinal(), "CICLISTA");

        gp_II.add(II_primer_apellidovictimas, 0, 0, 3, 1);
        gp_II.add(new Label("1re Apellido"), 0, 1, 1, 1);
        gp_II.add(II_segundo_apellidovictimas, 3, 0, 3, 1);
        gp_II.add(new Label("2do Apellido"), 3, 1, 1, 1);
        gp_II.add(II_primer_nombrevictimas, 0, 2, 3, 1);
        gp_II.add(new Label("1re Nombre"), 0, 3, 1, 1);
        gp_II.add(II_segundo_nombreapellidos, 3, 2, 3, 1);
        gp_II.add(new Label("2do Nombre"), 3, 3, 1, 1);
        gp_II.add(new Label("Tipo de Documento: "), 0, 4, 1, 1);
        gp_II.add(II_tipodocumento, 1, 4, 2, 1);
        gp_II.add(new Label("N° Documento: "), 3, 4, 1, 1);
        gp_II.add(II_documento, 4, 4, 2, 1);
        gp_II.add(new Label("Fecha de Nacimiento: "), 0, 5, 1, 1);
        gp_II.add(II_fechanacimiento, 1, 5, 2, 1);
        gp_II.add(new Label("Sexo: "), 3, 5, 1, 1);
        gp_II.add(II_sexo, 4, 5, 2, 1);
        gp_II.add(new Label("Dirección Residencia: "), 0, 6, 1, 1);
        gp_II.add(II_direccion, 1, 6, 5, 1);
        gp_II.add(new Label("Departamento: "), 0, 7, 1, 1);
        gp_II.add(II_dpto, 1, 7, 1, 1);
        gp_II.add(new Label("Cod Dpto: "), 2, 7, 1, 1);
        gp_II.add(II_coddpto, 3, 7, 1, 1);
        gp_II.add(new Label("Teléfono: "), 4, 7, 1, 1);
        gp_II.add(II_telefono, 5, 7, 1, 1);
        gp_II.add(new Label("Municipio: "), 0, 8, 1, 1);
        gp_II.add(II_municipio, 1, 8, 3, 1);
        gp_II.add(new Label("Cod Municipio: "), 4, 8, 1, 1);
        gp_II.add(II_codmunicipio, 5, 8, 1, 1);
        gp_II.add(new Label("Condición Accidentado: "), 0, 9, 1, 1);
        gp_II.add(II_condicionaccidentado, 1, 9, 3, 1);
        tp_II.setContent(gp_II);

        //***********************************************************************III. DATOS DONDE OCURRIO EL EVENTO
        tp_III = new TitledPane();
        tp_III.getStylesheets().add(SihicApp.hojaestilos);
        tp_III.setText("III. DATOS DEL SITIO DONDE OCURRIO EL EVENTO CATASTRÓFICO O EL ACCIDENTE DE TRANSITO");
        gp_III = new GridPane();
        III_genMunicipios = new GenMunicipios();
        gp_III.getStylesheets().add(SihicApp.hojaestilos);
        gp_III.getStyleClass().add("background");
        gp_III.setHgap(3);
        gp_III.setVgap(3);
        gp_III.setMinWidth(840);
        gp_III.setMaxWidth(840);
        III_naturales = new ChoiceBox();
        III_naturales.getItems().addAll("ACCIDENTE TRANSITO", "SISMO", "INUNDACIONES", "RAYO", "MAREMOTO", "AVALANCHA", "VENDAVAL", "ERUCIONES VOLCANICAS", "DELIZAMIENTO TIERRA", "TORNADO", "HURACAN", "INCENDIO NATURAL", "EXPLOSION", "INCENDIO", "MASACRE", "ATAQUE A MUNICIPIOS", "MINA ANTIPERSONAL", "COMBATE", "OTRO");
        III_cual = new TextField("");
        III_dir_ocurrencia = new TextField("");
        III_fechaeventoaccicente = new DatePicker(LocalDate.now());
        III_horaeventoaccicente = new TextField("");
        III_mineventoaccicente = new TextField("");

        III_sbmunicipio = new SearchBox();
        III_sbmunicipio.setOnAction(e -> {
            SihicApp.s_hclfurisp.setIII_genMunicipios(SihicApp.s_genMunicipios);
        });
        III_spmunicipio = new SearchPopover(III_sbmunicipio, new PageBrowser(), SihicApp.s_genMunicipios, false);
        III_spmunicipio.setMinHeight(100);
        III_spmunicipio.setMaxHeight(100);
        III_enuncie = new TextArea("");
        III_zona = new ChoiceBox(LoadChoiceBoxGeneral.getGen_zona_residencia_id().getItems());
        gp_III.add(new Label("Naturaleza Evento:"), 0, 0);
        gp_III.add(III_naturales, 1, 0);
        gp_III.add(new Label("Cual?:"), 2, 0, 1, 1);
        gp_III.add(III_cual, 3, 0, 3, 1);
        gp_III.add(new Label("Dirección de la Ocurrencia:"), 0, 1, 1, 1);
        gp_III.add(III_dir_ocurrencia, 1, 1, 5, 1);
        gp_III.add(new Label("Fecha Evento/Accidente:"), 0, 2, 1, 1);
        gp_III.add(III_fechaeventoaccicente, 1, 2, 1, 1);
        gp_III.add(new Label("Hora:"), 2, 2, 1, 1);
        gp_III.add(III_horaeventoaccicente, 3, 2, 1, 1);
        gp_III.add(new Label("Minutos:"), 4, 2, 1, 1);
        gp_III.add(III_mineventoaccicente, 5, 2, 1, 1);
        gp_III.add(new Label("Municipio:"), 0, 3, 1, 1);
        gp_III.add(III_sbmunicipio, 1, 3, 3, 1);
        gp_III.add(new Label("Zona:"), 4, 3, 1, 1);
        gp_III.add(III_zona, 5, 3, 1, 1);
        gp_III.add(new Label("Descripción breve:"), 0, 4, 1, 1);
        gp_III.add(III_enuncie, 1, 4, 5, 1);
        gp_III.add(III_spmunicipio, 1, 4, 3, 3);
        tp_III.setContent(gp_III);
        //***********************************************************************IV. 
        tp_IV = new TitledPane();
        tp_IV.getStylesheets().add(SihicApp.hojaestilos);
        tp_IV.setText("IV. DATOS DEL VEHICULO DE ACCIDENTE DE TRANSITO");
        gp_IV = new GridPane();
        gp_IV.getStylesheets().add(SihicApp.hojaestilos);
        gp_IV.getStyleClass().add("background");
        gp_IV.setHgap(3);
        gp_IV.setVgap(3);
        gp_IV.setMinWidth(840);
        gp_IV.setMaxWidth(840);
        IV_cb_estadoseguro = new ChoiceBox();
        IV_cb_estadoseguro.getItems().addAll("ASEGURADO",
                "NO ASEGURADO",
                "VEHICULO FANTASMA",
                "POLIZA FALSA",
                "VEHICULO FUGA");
        IV_cb_tiposervicio = new ChoiceBox();
        IV_cb_tiposervicio.getItems().addAll("PARTICULAR",
                "PÚBLICO",
                "OFICIAL",
                "VEHICULO DE EMERGENCIA",
                "VEHICULO DE SERVICIO DIPLOMATICO O CONSULAR",
                "VEHICULO DE TRANSPORTE MASIVO",
                "VEHICULO ESCOLAR");
        IV_cb_tiposervicio.setMaxWidth(200);
        IV_nombreaseguradora = new TextField("");
        IV_codigoaseguradora = new TextField("");
        IV_marca = new TextField("");
        IV_placa = new TextField("");
        IV_nopoliza = new TextField("");
        IV_rb_intautoridadsi = new RadioButton("Sí  ");
        IV_rb_intautoridadno = new RadioButton("No  ");
        IV_tg_interautoridad = new ToggleGroup();
        IV_rb_intautoridadsi.setToggleGroup(IV_tg_interautoridad);
        IV_rb_intautoridadno.setToggleGroup(IV_tg_interautoridad);
        hb_intautoriada = new HBox(IV_rb_intautoridadsi, IV_rb_intautoridadno);
        IV_rb_cobroexcedentesi = new RadioButton("Sí  ");
        IV_rb_cobroexcedenteno = new RadioButton("No  ");
        IV_tg_interautoridad = new ToggleGroup();
        IV_rb_cobroexcedentesi.setToggleGroup(IV_tg_interautoridad);
        IV_rb_cobroexcedenteno.setToggleGroup(IV_tg_interautoridad);
        hb_cobroexcedente = new HBox(IV_rb_cobroexcedentesi, IV_rb_cobroexcedenteno);
        IV_dp_vigenciadesde = new DatePicker(LocalDate.now());
        IV_dp_vigenciahasta = new DatePicker(LocalDate.now());
        gp_IV.addRow(0, new Label("Estado de Seguro:"), IV_cb_estadoseguro, new Label("Marca:"), IV_marca, new Label("Placa:"), IV_placa);
        gp_IV.addRow(1, new Label("Tipo Servicio:"), IV_cb_tiposervicio, new Label("Código Aseguradora:"), IV_codigoaseguradora, new Label("N° Poliza:"), IV_nopoliza);
        gp_IV.add(new Label("Nombre Aseguradora:"), 0, 2);
        gp_IV.add(IV_nombreaseguradora, 1, 2, 5, 1);
        gp_IV.add(new Label("Intervención de Autoridad:"), 0, 3, 1, 1);
        gp_IV.add(hb_intautoriada, 1, 3, 2, 1);
        gp_IV.add(new Label("Cobro Excedente Poliza:"), 3, 3, 1, 1);
        gp_IV.add(hb_cobroexcedente, 4, 3, 2, 1);
        gp_IV.add(new Label("Vigencia Desde:"), 0, 4, 1, 1);
        gp_IV.add(IV_dp_vigenciadesde, 1, 4, 2, 1);
        gp_IV.add(new Label("Hasta:"), 3, 4, 1, 1);
        gp_IV.add(IV_dp_vigenciahasta, 4, 4, 2, 1);
        tp_IV.setContent(gp_IV);
        //***********************************************************************V. 
        tp_V = new TitledPane();
        tp_V.getStylesheets().add(SihicApp.hojaestilos);
        tp_V.setText("V. DATOS DE PROPIETARIO DEL VEHICULO");
        gp_V = new GridPane();
        gp_V.getStylesheets().add(SihicApp.hojaestilos);
        gp_V.getStyleClass().add("background");
        gp_V.setHgap(3);
        gp_V.setVgap(3);
        gp_V.setMinWidth(840);
        gp_V.setMaxWidth(840);
        V_primer_apellidovictimas = new TextField();
        V_primer_apellidovictimas.setMinWidth(350);
        V_segundo_apellidovictimas = new TextField();
        V_segundo_apellidovictimas.setMinWidth(350);
        V_primer_nombrevictimas = new TextField();
        V_segundo_nombreapellidos = new TextField();
        V_tipodocumento = new ChoiceBox(LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getItems());
        // V_tipodocumento=LoadChoiceBoxGeneral.getGen_tipos_documentos_id();
        V_documento = new TextField();
        V_fechanacimiento = new TextField();
        V_direccion = new TextField();
        V_telefono = new TextField();
        V_sbmunicipio = new SearchBox();
        V_sbmunicipio.setOnAction(e -> {
            SihicApp.s_hclfurisp.setV_genMunicipios(SihicApp.s_genMunicipios);
        });
        V_spmunicipio = new SearchPopover(V_sbmunicipio, new PageBrowser(), SihicApp.s_genMunicipios, false);
        V_spmunicipio.setMaxHeight(30);
        gp_V.add(V_primer_apellidovictimas, 0, 0, 3, 1);
        gp_V.add(new Label("1re Apellido"), 0, 1, 1, 1);
        gp_V.add(V_segundo_apellidovictimas, 3, 0, 3, 1);
        gp_V.add(new Label("2do Apellido"), 3, 1, 1, 1);
        gp_V.add(V_primer_nombrevictimas, 0, 2, 3, 1);
        gp_V.add(new Label("1re Nombre"), 0, 3, 1, 1);
        gp_V.add(V_segundo_nombreapellidos, 3, 2, 3, 1);
        gp_V.add(new Label("2do Nombre"), 3, 3, 1, 1);
        gp_V.add(new Label("Tipo de Documento:"), 0, 4, 1, 1);
        gp_V.add(V_tipodocumento, 1, 4, 2, 1);
        gp_V.add(new Label("N° Documento:"), 3, 4, 1, 1);
        gp_V.add(V_documento, 4, 4, 2, 1);
        gp_V.add(new Label("Dirección Residencia: "), 0, 5, 1, 1);
        gp_V.add(V_direccion, 1, 5, 3, 1);

        gp_V.add(new Label("Teléfono: "), 4, 5, 1, 1);
        gp_V.add(V_telefono, 5, 5, 1, 1);
        gp_V.add(new Label("Municipio: "), 0, 6, 1, 1);
        gp_V.add(V_sbmunicipio, 1, 6, 5, 1);
        gp_V.add(V_spmunicipio, 1, 7, 5, 2);
        tp_V.setContent(gp_V);
        //***********************************************************************VI. 
        tp_VI = new TitledPane();
        tp_VI.getStylesheets().add(SihicApp.hojaestilos);
        tp_VI.setText("VI. DATOS DEL CONDUCTOR DEL VEHICULO O INVOLUCRADO EN EL ACCIDENTE DE TRANSITO");
        gp_VI = new GridPane();
        gp_VI.getStylesheets().add(SihicApp.hojaestilos);
        gp_VI.getStyleClass().add("background");
        gp_VI.setHgap(3);
        gp_VI.setVgap(3);
        gp_VI.setMinWidth(840);
        gp_VI.setMaxWidth(840);
        tp_VI_VEHICULOSINVOLUCRADOS = new TitledPane();
        tp_VI_VEHICULOSINVOLUCRADOS.getStylesheets().add(SihicApp.hojaestilos);
        tp_VI_VEHICULOSINVOLUCRADOS.setText("DATOS DE VEHICULOS INVOLUCRADOS");
        gp_VI_VEHICULOSINVOLUCRADOS = new GridPane();
        gp_VI_VEHICULOSINVOLUCRADOS.getStylesheets().add(SihicApp.hojaestilos);
        gp_VI_VEHICULOSINVOLUCRADOS.getStyleClass().add("background");
        gp_VI_VEHICULOSINVOLUCRADOS.setHgap(3);
        gp_VI_VEHICULOSINVOLUCRADOS.setVgap(3);
        gp_VI_VEHICULOSINVOLUCRADOS.setMinWidth(840);
        gp_VI_VEHICULOSINVOLUCRADOS.setMaxWidth(840);

        V_spmunicipio.setMinHeight(50);
        V_spmunicipio.setMaxHeight(50);
        VI_primer_apellidovictimas = new TextField();
        VI_primer_apellidovictimas.setMinWidth(350);
        VI_segundo_apellidovictimas = new TextField();
        VI_segundo_apellidovictimas.setMinWidth(350);
        VI_primer_nombrevictimas = new TextField();
        VI_segundo_nombreapellidos = new TextField();
        VI_tipodocumento = new ChoiceBox(LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getItems());
        //VI_tipodocumento=LoadChoiceBoxGeneral.getGen_tipos_documentos_id();
        VI_documento = new TextField();
        VI_fechanacimiento = new TextField();
        VI_direccion = new TextField();
        VI_telefono = new TextField();
        VI_sbmunicipio = new SearchBox();
        VI_sbmunicipio.setOnAction(e -> {
            SihicApp.s_hclfurisp.setVI_genMunicipios(SihicApp.s_genMunicipios);
        });
        VI_spmunicipio = new SearchPopover(VI_sbmunicipio, new PageBrowser(), SihicApp.s_genMunicipios, false);
        VI_spmunicipio.setMinHeight(50);
        VI_spmunicipio.setMaxHeight(50);
        VI_tipodocumento_vehiculoinvolucrado1 = new ChoiceBox(LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getItems());
        VI_documento_vehiculoinvolucrado1 = new TextField();
        VI_cb_estadoseguro_vehiculoinvolucrado1 = new ChoiceBox();
        VI_cb_estadoseguro_vehiculoinvolucrado1.getItems().addAll("ASEGURADO",
                "NO ASEGURADO",
                "VEHICULO FANTASMA",
                "POLIZA FALSA",
                "VEHICULO FUGA");
        VI_placa_vehiculoinvolucrado1 = new TextField();
        VI_tipodocumento_vehiculoinvolucrado2 = new ChoiceBox(LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getItems());
        VI_documento_vehiculoinvolucrado2 = new TextField();
        VI_cb_estadoseguro_vehiculoinvolucrado2 = new ChoiceBox();
        VI_cb_estadoseguro_vehiculoinvolucrado2.getItems().addAll("ASEGURADO",
                "NO ASEGURADO",
                "VEHICULO FANTASMA",
                "POLIZA FALSA",
                "VEHICULO FUGA");
        VI_placa_vehiculoinvolucrado2 = new TextField();

        gp_VI.add(VI_primer_apellidovictimas, 0, 0, 3, 1);
        gp_VI.add(new Label("1er Apellido"), 0, 1, 3, 1);
        gp_VI.add(VI_segundo_apellidovictimas, 3, 0, 3, 1);
        gp_VI.add(new Label("2do Apellido"), 3, 1, 3, 1);
        gp_VI.add(VI_primer_nombrevictimas, 0, 2, 3, 1);
        gp_VI.add(new Label("1er Nombre"), 0, 3, 3, 1);
        gp_VI.add(VI_segundo_nombreapellidos, 3, 2, 3, 1);
        gp_VI.add(new Label("2do Nombre"), 3, 3, 3, 1);
        gp_VI.add(new Label("Tipo de Documento: "), 0, 4, 2, 1);
        gp_VI.add(VI_tipodocumento, 1, 4, 2, 1);
        gp_VI.add(new Label("N° Documento: "), 3, 4, 1, 1);
        gp_VI.add(VI_documento, 4, 4, 2, 1);
        gp_VI.add(new Label("Dirección Residencia: "), 0, 5, 1, 1);
        gp_VI.add(VI_direccion, 1, 5, 3, 1);
        gp_VI.add(new Label("Municipio: "), 0, 6, 1, 1);
        gp_VI.add(VI_sbmunicipio, 1, 6, 4, 1);
        gp_VI.add(new Label("Teléfono: "), 4, 6, 1, 1);
        gp_VI.add(VI_telefono, 5, 6, 1, 1);
        gp_VI.add(VI_spmunicipio, 0, 7, 5, 2);
        tp_VI.setContent(gp_VI);
        Label la_asegurado = new Label("Asegurado:");
        la_asegurado.setMinWidth(100);
        Label la_placa = new Label("Placa:");
        la_placa.setMinWidth(100);
        Label la_tipo_doc = new Label("Tipo Documento:");
        la_tipo_doc.setMinWidth(150);
        Label la_numero = new Label("Número:");
        la_numero.setMinWidth(100);
        gp_VI_VEHICULOSINVOLUCRADOS.add(new Label("Datos del Segundo Vehiculo:"), 0, 0, 2, 1);
        gp_VI_VEHICULOSINVOLUCRADOS.add(la_asegurado, 0, 1);
        gp_VI_VEHICULOSINVOLUCRADOS.add(VI_cb_estadoseguro_vehiculoinvolucrado1, 1, 1);
        gp_VI_VEHICULOSINVOLUCRADOS.add(la_placa, 2, 1);
        gp_VI_VEHICULOSINVOLUCRADOS.add(VI_placa_vehiculoinvolucrado1, 3, 1);
        gp_VI_VEHICULOSINVOLUCRADOS.add(la_tipo_doc, 4, 1);
        gp_VI_VEHICULOSINVOLUCRADOS.add(VI_tipodocumento_vehiculoinvolucrado1, 5, 1);
        gp_VI_VEHICULOSINVOLUCRADOS.add(la_numero, 6, 1);
        gp_VI_VEHICULOSINVOLUCRADOS.add(VI_documento_vehiculoinvolucrado1, 7, 1);
        gp_VI_VEHICULOSINVOLUCRADOS.add(new Label("Datos del Tercer Vehiculo:"), 0, 2, 2, 1);
        gp_VI_VEHICULOSINVOLUCRADOS.add(new Label("Asegurado"), 0, 3);
        gp_VI_VEHICULOSINVOLUCRADOS.add(VI_cb_estadoseguro_vehiculoinvolucrado2, 1, 3);
        gp_VI_VEHICULOSINVOLUCRADOS.add(new Label("Placa:"), 2, 3);
        gp_VI_VEHICULOSINVOLUCRADOS.add(VI_placa_vehiculoinvolucrado2, 3, 3);
        gp_VI_VEHICULOSINVOLUCRADOS.add(new Label("Tipo Documento:"), 4, 3);
        gp_VI_VEHICULOSINVOLUCRADOS.add(VI_tipodocumento_vehiculoinvolucrado2, 5, 3);
        gp_VI_VEHICULOSINVOLUCRADOS.add(new Label("Número:"), 6, 3);
        gp_VI_VEHICULOSINVOLUCRADOS.add(VI_documento_vehiculoinvolucrado2, 7, 3);
        tp_VI_VEHICULOSINVOLUCRADOS.setContent(gp_VI_VEHICULOSINVOLUCRADOS);
        //*************************************************
        tp_VII = new TitledPane();
        tp_VII.getStylesheets().add(SihicApp.hojaestilos);
        tp_VII.setText("VII. DATOS DE REMISIÓN");
        gp_VII = new GridPane();
        gp_VII.getStylesheets().add(SihicApp.hojaestilos);
        gp_VII.getStyleClass().add("background");
        gp_VII.setHgap(3);
        gp_VII.setVgap(3);
        gp_VII.setMinWidth(840);
        gp_VII.setMaxWidth(840);
        VII_cb_tiporeferencia = new ChoiceBox();
        VII_cb_tiporeferencia.getItems().addAll("REMISIÓN", "ORDEN DE SERVICIO", "Ninguna");
        VII_profesionalrecibe = new TextField("");
        VII_dp_remisiondesde = new DatePicker(LocalDate.now());
        VII_remisionhora = new TextField("");
        VII_remisionminutos = new TextField("");
        VII_prestadorremite = new TextField("");
        VII_codigoinscripcionremite = new TextField("");
        VII_profesionalremite = new TextField("");
        VII_profesionalremitecargo = new TextField("");
        VII_sb_municipioremite = new SearchBox();
        VII_sb_municipioremite.setOnAction(e -> {
            SihicApp.s_hclfurisp.setVII_genMunicipiosRemite(SihicApp.s_genMunicipios);
        });
        VII_sp_municipioremite = new SearchPopover(VII_sb_municipioremite, new PageBrowser(), SihicApp.s_genMunicipios, false);
        VII_sp_municipioremite.setMaxHeight(50);
        VII_dp_fechaaceptacion = new DatePicker(LocalDate.now());
        VII_aceptacionhora = new TextField("");
        VII_aceptacionminutos = new TextField("");
        VII_prestadorrecibe = new TextField("");
        VII_profesionalrecibe = new TextField("");
        VII_codigoinscripcionrecibe = new TextField("");
        VII_profesionalrecibecargo = new TextField("");
        VII_sb_municipiorecibe = new SearchBox();
        VII_sb_municipiorecibe.setOnAction(e -> {
            SihicApp.s_hclfurisp.setVII_genMunicipiosRecibe(SihicApp.s_genMunicipios);
        });
        VII_sp_municipiorecibe = new SearchPopover(VII_sb_municipiorecibe, new PageBrowser(), SihicApp.s_genMunicipios, false);
        VII_sp_municipiorecibe.setMaxHeight(30);
        VII_sp_municipioremite.setMaxHeight(30);
        VII_direccionremite = new TextField("");
        VII_direccionrecibe = new TextField("");
        gp_VII.add(new Label("Tipo Referencia: "), 0, 0);
        gp_VII.add(VII_cb_tiporeferencia, 1, 0, 5, 1);
        gp_VII.add(new Label("Fecha Remisión: "), 0, 1);
        gp_VII.add(VII_dp_remisiondesde, 1, 1, 1, 1);
        gp_VII.add(new Label(" a las"), 2, 1);
        gp_VII.add(VII_remisionhora, 3, 1, 1, 1);
        gp_VII.add(new Label(" : "), 4, 1);
        gp_VII.add(VII_remisionminutos, 5, 1, 1, 1);
        gp_VII.add(new Label("Prestador que Remite:"), 0, 2);
        gp_VII.add(VII_prestadorremite, 1, 2, 5, 1);
        gp_VII.add(new Label("Código de Inscripción: "), 0, 3);
        gp_VII.add(VII_codigoinscripcionremite, 1, 3, 5, 1);
        gp_VII.add(new Label("Profesional que Remite:"), 0, 4);
        gp_VII.add(VII_profesionalremite, 1, 4, 3, 1);
        gp_VII.add(new Label("Cargo:"), 4, 4);
        gp_VII.add(VII_profesionalremitecargo, 5, 4, 4, 1);
        gp_VII.add(new Label("Dirección que Remite:"), 0, 5);
        gp_VII.add(VII_direccionremite, 1, 5, 5, 1);
        gp_VII.add(new Label("Municipio IPS que Remite:"), 0, 6);
        gp_VII.add(VII_sb_municipioremite, 1, 6, 5, 1);
        gp_VII.add(new Label("Fecha Aceptación: "), 0, 7);
        gp_VII.add(VII_dp_fechaaceptacion, 1, 7, 1, 1);
        gp_VII.add(new Label(" a las"), 2, 7);
        gp_VII.add(VII_aceptacionhora, 3, 7, 1, 1);
        gp_VII.add(new Label(" : "), 4, 7);
        gp_VII.add(VII_aceptacionminutos, 5, 7, 1, 1);
        gp_VII.add(new Label("Prestador que Recibe:"), 0, 8);
        gp_VII.add(VII_prestadorrecibe, 1, 8, 5, 1);
        gp_VII.add(new Label("Código de Inscripción: "), 0, 9);
        gp_VII.add(VII_codigoinscripcionrecibe, 1, 9, 5, 1);
        gp_VII.add(new Label("Profesional que Recibe:"), 0, 10);
        gp_VII.add(VII_profesionalrecibe, 1, 10, 3, 1);
        gp_VII.add(new Label("Cargo:"), 4, 10);
        gp_VII.add(VII_profesionalrecibecargo, 5, 10, 4, 1);
        gp_VII.add(new Label("Dirección que Recibe:"), 0, 11, 1, 1);
        gp_VII.add(VII_direccionrecibe, 1, 11, 4, 1);
        gp_VII.add(new Label("Municipio IPS que Recibe:"), 0, 12, 1, 1);
        gp_VII.add(VII_sb_municipiorecibe, 1, 12, 4, 1);
        gp_VII.add(VII_sp_municipioremite, 1, 6, 5, 6);
        gp_VII.add(VII_sp_municipiorecibe, 1, 13, 5, 2);
        tp_VII.setContent(gp_VII);
        //*********************************************************VIII

        tp_VIII = new TitledPane();
        tp_VIII.getStylesheets().add(SihicApp.hojaestilos);
        tp_VIII.setText("VIII. AMPARO DE TRANSPORTE Y MOVILIZACIÓN DE LA VICTIMA");
        gp_VIII = new GridPane();
        gp_VIII.getStylesheets().add(SihicApp.hojaestilos);
        gp_VIII.getStyleClass().add("background");
        gp_VIII.setHgap(3);
        gp_VIII.setVgap(3);
        gp_VIII.setMinWidth(840);
        gp_VIII.setMaxWidth(840);
        VIII_placa = new TextField();
        VIII_transportodesde = new TextField();
        VIII_transportohasta = new TextField();
        VIII_tipotransporte = new ChoiceBox();
        VIII_tipotransporte.getItems().addAll("AMBULANCIA BÁSICA", "AMBULANCIA MÉDICADA");
        VIII_tipotransporte.setMinWidth(200);
        VIII_zona = new ChoiceBox(LoadChoiceBoxGeneral.getGen_zona_residencia_id().getItems());
        VIII_zona.setMinWidth(200);
        VIII_primerapellidoconductor = new TextField();
        VIII_primerapellidoconductor.setMinWidth(350);
        VIII_segundoapellidoconductor = new TextField();
        VIII_segundoapellidoconductor.setMinWidth(350);
        VIII_primernombreconductor = new TextField();
        VIII_primernombreconductor.setMinWidth(350);
        VIII_segundonombreconductor = new TextField();
        VIII_segundonombreconductor.setMinWidth(350);
        VIII_tipodocumento = new ChoiceBox(LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getItems());
        VIII_documento = new TextField();
        gp_VIII.add(new Label("Placa N°:"), 0, 0);
        gp_VIII.add(VIII_placa, 1, 0, 5, 1);
        gp_VIII.add(VIII_primerapellidoconductor, 0, 1, 3, 1);
        gp_VIII.add(new Label("1er Apellido"), 0, 2, 3, 1);
        gp_VIII.add(VIII_segundoapellidoconductor, 3, 1, 3, 1);
        gp_VIII.add(new Label("2do Apellido"), 3, 2, 3, 1);
        gp_VIII.add(VIII_primernombreconductor, 0, 3, 3, 1);
        gp_VIII.add(new Label("1er Nombre"), 0, 4, 3, 1);
        gp_VIII.add(VIII_segundonombreconductor, 3, 3, 3, 1);
        gp_VIII.add(new Label("2do Nombre Médico o Profesional Tratante"), 3, 4, 3, 1);
        gp_VIII.add(new Label("Tipo Documento:"), 0, 5, 1, 1);
        gp_VIII.add(VIII_tipodocumento, 1, 5, 2, 1);
        gp_VIII.add(new Label("Documento:"), 3, 5, 1, 1);
        VIII_documento.setMinWidth(200);
        gp_VIII.add(VIII_documento, 4, 5, 2, 1);

        gp_VIII.add(new Label("Transporto Victima desde:"), 0, 6, 1, 1);
        gp_VIII.add(VIII_transportodesde, 1, 6, 2, 1);
        gp_VIII.add(new Label("Hasta:"), 3, 6, 1, 1);
        VIII_transportohasta.setMinWidth(200);
        gp_VIII.add(VIII_transportohasta, 4, 6, 2, 1);
        gp_VIII.add(new Label("Tipo de Transporte:"), 0, 7, 1, 1);
        gp_VIII.add(VIII_tipotransporte, 1, 7, 1, 1);
        gp_VIII.add(new Label("Zona:"), 3, 7, 1, 1);
        gp_VIII.add(VIII_zona, 4, 7, 2, 1);
        tp_VIII.setContent(gp_VIII);

        //**************************************************IX
        tp_IX = new TitledPane();
        tp_IX.getStylesheets().add(SihicApp.hojaestilos);
        tp_IX.setText("IX. CERTIFICADO DE LA ATENCIÓN MÉDICA DE LA VICTIMA COMO PRUEBA DEL ACCIDENTE O EVENTO");
        gp_IX = new GridPane();
        gp_IX.getStylesheets().add(SihicApp.hojaestilos);
        gp_IX.getStyleClass().add("background");
        gp_IX.setHgap(3);
        gp_IX.setVgap(3);
        gp_IX.setMinWidth(840);
        gp_IX.setMaxWidth(840);
        IX_fechaingreso = new DatePicker(LocalDate.now());
        IX_horaingreso = new TextField();
        IX_minutosingreso = new TextField();
        IX_fechaegreso = new DatePicker(LocalDate.now());
        IX_horaegreso = new TextField();
        IX_minutosegreso = new TextField();
        IX_primerapellidoprofesionaltratante = new TextField();
        IX_segundoapellidoprofesionaltratante = new TextField();
        IX_primernombreprofesionaltratante = new TextField();
        IX_segundonombreprofesionaltratante = new TextField();
        IX_tipodocumento = new ChoiceBox(LoadChoiceBoxGeneral.getGen_tipos_documentos_id().getItems());
        IX_tipodocumento.setMaxWidth(150);
        IX_documento = new TextField();
        IX_noregistromedico = new TextField();
        IX_sb_dxmainingreso = new SearchBox();
        IX_sb_dxmainingreso.setOnAction(e -> {
            SihicApp.s_hclfurisp.setDxmaininit(SihicApp.hclCie10);
        });
        IX_sp_dxmainingreso = new SearchPopover(IX_sb_dxmainingreso, new PageBrowser(), SihicApp.hclCie10, false);

        IX_sb_dxotroingreso = new SearchBox();
        IX_sb_dxotroingreso.setOnAction(e -> {
            SihicApp.s_hclfurisp.setDxtherinit(SihicApp.hclCie10);
        });
        IX_sp_dxotroingreso = new SearchPopover(IX_sb_dxotroingreso, new PageBrowser(), SihicApp.hclCie10, false);

        IX_sb_dxotro2ingreso = new SearchBox();
        IX_sb_dxotro2ingreso.setOnAction(e -> {
            SihicApp.s_hclfurisp.setDxther2init(SihicApp.hclCie10);
        });
        IX_sp_dxotro2ingreso = new SearchPopover(IX_sb_dxotro2ingreso, new PageBrowser(), SihicApp.hclCie10, false);
        IX_sb_dxmainegreso = new SearchBox();
        IX_sb_dxmainegreso.setOnAction(e -> {
            SihicApp.s_hclfurisp.setDxmainend(SihicApp.hclCie10);
        });
        IX_sp_dxmainegreso = new SearchPopover(IX_sb_dxmainegreso, new PageBrowser(), SihicApp.hclCie10, false);

        IX_sb_dxotroegreso = new SearchBox();
        IX_sb_dxotroegreso.setOnAction(e -> {
            SihicApp.s_hclfurisp.setDxtherend(SihicApp.hclCie10);
        });
        IX_sp_dxotroegreso = new SearchPopover(IX_sb_dxotroegreso, new PageBrowser(), SihicApp.hclCie10, false);

        IX_sb_dxotro2egreso = new SearchBox();
        IX_sb_dxotro2egreso.setOnAction(e -> {
            SihicApp.s_hclfurisp.setDxther2end(SihicApp.hclCie10);
        });
        IX_sp_dxotro2egreso = new SearchPopover(IX_sb_dxotro2egreso, new PageBrowser(), SihicApp.hclCie10, false);

        gp_IX.add(new Label("Fecha de Ingreso:"), 0, 0);
        gp_IX.add(IX_fechaingreso, 1, 0);
        gp_IX.add(new Label("a las:"), 2, 0);
        gp_IX.add(IX_horaingreso, 3, 0);
        gp_IX.add(new Label(":"), 4, 0);
        gp_IX.add(IX_minutosingreso, 5, 0);
        gp_IX.add(new Label("Fecha de Egreso:"), 0, 1);
        gp_IX.add(IX_fechaegreso, 1, 1);
        gp_IX.add(new Label("a las:"), 2, 1);
        gp_IX.add(IX_horaegreso, 3, 1);
        gp_IX.add(new Label(":"), 4, 1);
        gp_IX.add(IX_minutosegreso, 5, 1);
        gp_IX.add(new Label("Dx Principal Ingreso:"), 0, 2, 1, 1);
        gp_IX.add(IX_sb_dxmainingreso, 1, 2, 2, 1);
        gp_IX.add(new Label("Dx Principal Egreso:"), 3, 2, 1, 1);
        gp_IX.add(IX_sb_dxmainegreso, 4, 2, 2, 1);
        gp_IX.add(new Label("Otro Dx Ingreso:"), 0, 3, 1, 1);
        gp_IX.add(IX_sb_dxotroingreso, 1, 3, 2, 1);
        gp_IX.add(new Label("Otro Dx Egreso:"), 3, 3, 1, 1);
        gp_IX.add(IX_sb_dxotroegreso, 4, 3, 2, 1);
        gp_IX.add(new Label("Otro Dx Ingreso:"), 0, 4, 1, 1);
        gp_IX.add(IX_sb_dxotro2ingreso, 1, 4, 2, 1);
        gp_IX.add(new Label("Otro Dx Egreso:"), 3, 4, 1, 1);
        gp_IX.add(IX_sb_dxotro2egreso, 4, 4, 2, 1);
        gp_IX.add(IX_primerapellidoprofesionaltratante, 0, 5, 3, 1);
        gp_IX.add(new Label("1er Apellido Médico o Profesional Tratante"), 0, 6, 3, 1);
        gp_IX.add(IX_segundoapellidoprofesionaltratante, 3, 5, 3, 1);
        gp_IX.add(new Label("2do Apellido Médico o Profesional Tratante"), 3, 6, 3, 1);
        gp_IX.add(IX_primernombreprofesionaltratante, 0, 7, 3, 1);
        gp_IX.add(new Label("1er Nombre Médico o Profesional Tratante"), 0, 8, 3, 1);
        gp_IX.add(IX_segundonombreprofesionaltratante, 3, 7, 3, 1);
        gp_IX.add(new Label("2do Nombre Médico o Profesional Tratante"), 3, 8, 3, 1);
        gp_IX.add(new Label("Tipo Documento:"), 0, 9, 1, 1);
        gp_IX.add(IX_tipodocumento, 1, 9, 1, 1);
        gp_IX.add(new Label("N° Documento:"), 2, 9, 1, 1);
        gp_IX.add(IX_documento, 3, 9, 1, 1);
        gp_IX.add(new Label("N° Registro Médico:"), 4, 9, 1, 1);
        gp_IX.add(IX_noregistromedico, 5, 9, 1, 1);
        gp_IX.add(IX_sp_dxmainingreso, 1, 3, 5, 9);
        gp_IX.add(IX_sp_dxmainegreso, 4, 3, 5, 9);
        gp_IX.add(IX_sp_dxotroingreso, 1, 4, 5, 9);
        gp_IX.add(IX_sp_dxotroegreso, 4, 4, 5, 9);
        gp_IX.add(IX_sp_dxotro2ingreso, 1, 5, 5, 9);
        gp_IX.add(IX_sp_dxotro2egreso, 4, 5, 5, 9);
        IX_sp_dxmainingreso.setMaxHeight(50);
        IX_sp_dxmainegreso.setMaxHeight(50);
        IX_sp_dxotroingreso.setMaxHeight(50);
        IX_sp_dxotroegreso.setMaxHeight(50);
        IX_sp_dxotro2ingreso.setMaxHeight(50);
        IX_sp_dxotro2egreso.setMaxHeight(50);
        IX_sp_dxmainingreso.setMaxHeight(50);

        tp_IX.setContent(gp_IX);
        //***************************************************************X
        tp_X = new TitledPane();
        tp_X.getStylesheets().add(SihicApp.hojaestilos);
        tp_X.setText("X. AMPAROS QUE RECLAMA");
        gp_X = new GridPane();
        gp_X.getStylesheets().add(SihicApp.hojaestilos);
        gp_X.getStyleClass().add("background");
        gp_X.setHgap(3);
        gp_X.setVgap(3);
        gp_X.setMinWidth(840);
        gp_X.setMaxWidth(840);
        X_gastosmedicosfosyga = new TextField();
        X_gastosmedicostotalfacturado = new TextField();
        X_gastostransportefosyga = new TextField();
        X_gastostransportetotalfacturado = new TextField();
        gp_X.add(new Label("Valor total facturado"), 1, 0);
        gp_X.add(new Label("Valor reclamado al FOSYGA"), 2, 0);
        gp_X.add(new Label("Gastos médicos quirurgicos"), 0, 1);
        gp_X.add(new Label("Gastos de transporte y movilización de la víctima"), 0, 2);
        gp_X.add(X_gastosmedicostotalfacturado, 1, 1);
        gp_X.add(X_gastosmedicosfosyga, 2, 1);
        gp_X.add(X_gastostransportetotalfacturado, 1, 2);
        gp_X.add(X_gastostransportefosyga, 2, 2);
        tp_X.setContent(gp_X);

        genPersonasControllerClient = new GenPersonasControllerClient();
        genEapbControllerClient = new GenEapbControllerClient();
        //********************radicado,nofactura y datos personales**********************
        la_rg = new Label("RG:");
        la_nofactura = new Label("N° Factura:");
        la_noradicado = new Label("N° Radicado:");
        la_noradicadoanterior = new Label("N° Radicado Anterior:");
        la_fecharadicado = new Label("Fecha Radicado:");
        rg = new CheckBox();
        nofactura = new TextField();
        noradicado = new TextField();
        noradicadoanterior = new TextField();
        fecharadicado = new DatePicker(LocalDate.now());
        sp_genmunicipiosvictimas = new SearchPopover(sb_genmunicipiosvictimas, new PageBrowser(), SihicApp.s_genMunicipios, false);
        sb_genmunicipiosvictimas.getProperties().put("requerido", true);
        genPersonasvictimas = new GenPersonas();
        LoadChoiceBoxGeneral.getGen_tipos_documentos_id().setMaxWidth(300);
        sb_genmunicipiosvictimas.setMaxWidth(300);
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        save = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/pdf.png");
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        pdffurisp = new Button("", imageView);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, pdffurisp);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {
            }
        });

// gridpane  sp_geneapb = new SearchPopover(sb_eapb,new PageBrowser());
        gp_inicial = new GridPane();
        gp_inicial.getStylesheets().add(SihicApp.hojaestilos);
        gp_inicial.getStyleClass().add("background");
        gp_inicial.setHgap(3);
        gp_inicial.setVgap(3);
        gp_inicial.addRow(0, la_fecharadicado, fecharadicado, la_noradicado, noradicado, la_rg, rg);
        gp_inicial.addRow(1, la_noradicadoanterior, noradicadoanterior, la_nofactura, nofactura);

        Gp_Message = new GridPane();

        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        vb_generic.getStylesheets().add(SihicApp.hojaestilos);
        vb_generic.getStyleClass().add("background2");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);

        tp_I.setExpanded(false);
        tp_II.setExpanded(false);
        tp_III.setExpanded(false);
        tp_IV.setExpanded(false);
        tp_V.setExpanded(false);
        tp_VI.setExpanded(false);
        tp_VII.setExpanded(false);
        tp_VIII.setExpanded(false);
        tp_IX.setExpanded(false);
        tp_X.setExpanded(false);
        vb_generic.getChildren().addAll(gp_inicial, tp_I, tp_II, tp_III, tp_IV, tp_V, tp_VI, tp_VI_VEHICULOSINVOLUCRADOS, tp_VII, tp_VIII, tp_IX, tp_X, hb_botones);
        stack.getChildren().addAll(vb_generic, Gp_Message);
        scp_generic.setContent(stack);
        //stack.alignmentProperty().setValue(Pos.TOP_CENTER);
        event_inputs();
        Load_ChoiceBox();

        Gp_Message.setMinSize(300, 30);
        Gp_Message.setMaxSize(300, 30);

        crearoeditar();
        return scp_generic;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void save() throws InterruptedException {

        try {
            set_datos();
            if (SihicApp.s_hclfurisp.getId() == null) {
                SihicApp.hclFurispControllerClient.create(SihicApp.s_hclfurisp);

                L_Message.setText("Registro Creado");
            } else {
                SihicApp.s_hclfurisp = SihicApp.hclFurispControllerClient.update(SihicApp.s_hclfurisp);

                L_Message.setText("Registro Modificado");
            }
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

    private void set_datos() throws ParseException {
        SihicApp.s_hclfurisp.setFecharadicacion(UtilDate.localdatetodate(fecharadicado.getValue()));
        SihicApp.s_hclfurisp.setRg(rg.isSelected());
        SihicApp.s_hclfurisp.setNoradicado(noradicado.getText());
        SihicApp.s_hclfurisp.setNoradicadoanterior(noradicadoanterior.getText());
        SihicApp.s_hclfurisp.setCondicionaccidentado(II_condicionaccidentado.getSelectionModel().getSelectedIndex());

        //*********************************************************************************************III
        SihicApp.s_hclfurisp.setNaturalezaevento(III_naturales.getSelectionModel().getSelectedIndex());
        SihicApp.s_hclfurisp.setNaturaleza(III_cual.getText());
        SihicApp.s_hclfurisp.setDireccionocurrencia(III_dir_ocurrencia.getText());
        try {
            SihicApp.s_hclfurisp.setFecharevento(UtilDate.colocarfechahoraespecifica2(III_fechaeventoaccicente.getValue(), III_horaeventoaccicente.getText().trim(), III_mineventoaccicente.getText()));
        } catch (Exception e) {

        }
        SihicApp.s_hclfurisp.setDescripciobreve(III_enuncie.getText());
        try {
            SihicApp.s_hclfurisp.setGenZonaResidencia(LoadChoiceBoxGeneral.getV_gen_zona_residencia().get(III_zona.getSelectionModel().getSelectedIndex()));
        } catch (Exception e) {

        }
        //***************************************************************************************************IV
        SihicApp.s_hclfurisp.setEstadoaseguramiento(IV_cb_estadoseguro.getSelectionModel().getSelectedIndex());
        SihicApp.s_hclfurisp.setMarca(IV_marca.getText());
        SihicApp.s_hclfurisp.setPlaca(IV_placa.getText());
        SihicApp.s_hclfurisp.setTiposervicio(IV_cb_tiposervicio.getSelectionModel().getSelectedIndex());
        SihicApp.s_hclfurisp.setNombreaseguradora(IV_nombreaseguradora.getText());
        SihicApp.s_hclfurisp.setNopoliza(IV_nopoliza.getText());
        SihicApp.s_hclfurisp.setIntervencionautoridad(IV_rb_intautoridadsi.isSelected());
        SihicApp.s_hclfurisp.setCobroexcedentepoliza(IV_rb_cobroexcedentesi.isSelected());
        SihicApp.s_hclfurisp.setFecharvigenciadesde(UtilDate.localdatetodate(IV_dp_vigenciadesde.getValue()));
        SihicApp.s_hclfurisp.setFechavigenciahasta(UtilDate.localdatetodate(IV_dp_vigenciahasta.getValue()));
        SihicApp.s_hclfurisp.setCodigoaseguradora(IV_codigoaseguradora.getText());
        //************************************************************************************************************V
        if (SihicApp.s_hclfurisp.getPropietariovehiculo() == null) {
            SihicApp.s_hclfurisp.setPropietariovehiculo(new GenPersonas());
        }
        SihicApp.s_hclfurisp.getPropietariovehiculo().setPrimerApellido(V_primer_apellidovictimas.getText());
        SihicApp.s_hclfurisp.getPropietariovehiculo().setSegundoApellido(V_segundo_apellidovictimas.getText());
        SihicApp.s_hclfurisp.getPropietariovehiculo().setPrimerNombre(V_primer_nombrevictimas.getText());
        SihicApp.s_hclfurisp.getPropietariovehiculo().setSegundoNombre(V_segundo_nombreapellidos.getText());
        try {
            SihicApp.s_hclfurisp.getPropietariovehiculo().setGenTiposDocumentos(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().get(V_tipodocumento.getSelectionModel().getSelectedIndex()));
        } catch (Exception e) {

        }
        SihicApp.s_hclfurisp.getPropietariovehiculo().setDocumento(V_documento.getText());
        SihicApp.s_hclfurisp.getPropietariovehiculo().setDireccion(V_direccion.getText());
        SihicApp.s_hclfurisp.getPropietariovehiculo().setTelefono(V_telefono.getText());

        //*******************************************************************************************************VI
        if (SihicApp.s_hclfurisp.getConsuctorvehiculo() == null) {
            SihicApp.s_hclfurisp.setConsuctorvehiculo(new GenPersonas());
        }
        SihicApp.s_hclfurisp.getConsuctorvehiculo().setPrimerApellido(VI_primer_apellidovictimas.getText());
        SihicApp.s_hclfurisp.getConsuctorvehiculo().setSegundoApellido(VI_segundo_apellidovictimas.getText());
        SihicApp.s_hclfurisp.getConsuctorvehiculo().setPrimerNombre(VI_primer_nombrevictimas.getText());
        SihicApp.s_hclfurisp.getConsuctorvehiculo().setSegundoNombre(VI_segundo_nombreapellidos.getText());
        try {
            SihicApp.s_hclfurisp.getConsuctorvehiculo().setGenTiposDocumentos(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().get(VI_tipodocumento.getSelectionModel().getSelectedIndex()));

        } catch (Exception e) {

        }
        SihicApp.s_hclfurisp.getConsuctorvehiculo().setDocumento(VI_documento.getText());
        SihicApp.s_hclfurisp.getConsuctorvehiculo().setDireccion(VI_direccion.getText());
        SihicApp.s_hclfurisp.getConsuctorvehiculo().setTelefono(VI_telefono.getText());
        try {
            SihicApp.s_hclfurisp.setVI_tipodocumentos_vehiculosinvolucrados1(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().get(VI_tipodocumento_vehiculoinvolucrado1.getSelectionModel().getSelectedIndex()));
        } catch (Exception e) {

        }
        try {
            SihicApp.s_hclfurisp.setVI_tipodocumentos_vehiculosinvolucrados2(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().get(VI_tipodocumento_vehiculoinvolucrado2.getSelectionModel().getSelectedIndex()));
        } catch (Exception e) {

        }
        SihicApp.s_hclfurisp.setVI_estadoaseguramiento_vehiculosinvolucrados1(VI_cb_estadoseguro_vehiculoinvolucrado1.getSelectionModel().getSelectedIndex());
        SihicApp.s_hclfurisp.setVI_estadoaseguramiento_vehiculosinvolucrados2(VI_cb_estadoseguro_vehiculoinvolucrado2.getSelectionModel().getSelectedIndex());
        SihicApp.s_hclfurisp.setVI_placa_vehiculosinvolucrados1(VI_placa_vehiculoinvolucrado1.getText());
        SihicApp.s_hclfurisp.setVI_placa_vehiculosinvolucrados2(VI_placa_vehiculoinvolucrado2.getText());
        SihicApp.s_hclfurisp.setVI_documento_vehiculosinvolucrados1(VI_documento_vehiculoinvolucrado1.getText());
        SihicApp.s_hclfurisp.setVI_documento_vehiculosinvolucrados2(VI_documento_vehiculoinvolucrado2.getText());

        //************************************************************************************************************VII
        SihicApp.s_hclfurisp.setTiporeferencia(VII_cb_tiporeferencia.getSelectionModel().getSelectedIndex());
        try {
            SihicApp.s_hclfurisp.setFecharemision(UtilDate.colocarfechahoraespecifica2(VII_dp_remisiondesde.getValue(), VII_remisionhora.getText(), VII_remisionminutos.getText()));
        } catch (Exception e) {
            SihicApp.s_hclfurisp.setFecharemision(null);
        }
        SihicApp.s_hclfurisp.setPersonaqueremite(VII_prestadorremite.getText());
        SihicApp.s_hclfurisp.setVII_codigoinscripcionremite(VII_codigoinscripcionremite.getText());
        SihicApp.s_hclfurisp.setVII_profesionalremite(VII_profesionalremite.getText());
        SihicApp.s_hclfurisp.setVII_profesionalremitecargo(VII_profesionalremitecargo.getText());
        SihicApp.s_hclfurisp.setVII_direccionremite(VII_direccionremite.getText());
        try {
            SihicApp.s_hclfurisp.setFechaaceptacion(UtilDate.colocarfechahoraespecifica2(VII_dp_fechaaceptacion.getValue(), VII_aceptacionhora.getText(), VII_aceptacionminutos.getText()));
        } catch (Exception e) {
            SihicApp.s_hclfurisp.setFechaaceptacion(null);
        }
        SihicApp.s_hclfurisp.setPersonaquerecibe(VII_prestadorrecibe.getText());
        SihicApp.s_hclfurisp.setVII_codigoinscripcionrecibe(VII_codigoinscripcionrecibe.getText());
        SihicApp.s_hclfurisp.setVII_profesionalrecibe(VII_profesionalrecibe.getText());
        SihicApp.s_hclfurisp.setVII_profesionalrecibecargo(VII_profesionalrecibecargo.getText());
        SihicApp.s_hclfurisp.setVII_direccionrecibe(VII_direccionrecibe.getText());
        if (VII_sb_municipioremite.getText().equals("")) {
            SihicApp.s_hclfurisp.setVII_genMunicipiosRemite(null);
        }
        if (VII_sb_municipiorecibe.getText().equals("")) {
            SihicApp.s_hclfurisp.setVII_genMunicipiosRecibe(null);
        }
        //************************************************************************************VIII
        if (SihicApp.s_hclfurisp.getVIII_consuctorvehiculo() == null) {
            SihicApp.s_hclfurisp.setVIII_consuctorvehiculo(new GenPersonas());
        }
        SihicApp.s_hclfurisp.setPlacamovvictima(VIII_placa.getText());
        SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().setPrimerApellido(VIII_primerapellidoconductor.getText());
        SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().setSegundoApellido(VIII_segundoapellidoconductor.getText());
        SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().setPrimerNombre(VIII_primernombreconductor.getText());
        SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().setSegundoNombre(VIII_segundonombreconductor.getText());
        try {
            SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().setGenTiposDocumentos(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().get(VIII_tipodocumento.getSelectionModel().getSelectedIndex()));
        } catch (Exception e) {

        }
        SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().setDocumento(VIII_documento.getText());
        SihicApp.s_hclfurisp.setTrasnportodesde(VIII_transportodesde.getText());
        SihicApp.s_hclfurisp.setTransportehasta(VIII_transportohasta.getText());
        SihicApp.s_hclfurisp.setTipotransporte(VIII_tipotransporte.getSelectionModel().getSelectedIndex());
        try {
            SihicApp.s_hclfurisp.setVIII_zona(LoadChoiceBoxGeneral.getV_gen_zona_residencia().get(VIII_zona.getSelectionModel().getSelectedIndex()));
        } catch (Exception e) {
            SihicApp.s_hclfurisp.setTrasnportodesde(VIII_transportodesde.getText());
            SihicApp.s_hclfurisp.setTransportehasta(VIII_transportohasta.getText());
            SihicApp.s_hclfurisp.setTipotransporte(VIII_tipotransporte.getSelectionModel().getSelectedIndex());

        }
        if (VIII_placa.getText().equals("")) {
            SihicApp.s_hclfurisp.setTrasnportodesde("");
            SihicApp.s_hclfurisp.setTransportehasta("");
            SihicApp.s_hclfurisp.setTipotransporte(3);
            SihicApp.s_hclfurisp.setVIII_consuctorvehiculo(null);
            SihicApp.s_hclfurisp.setVIII_zona(null);

        }
        //***********************************************************************************************************IX
        if (SihicApp.s_hclfurisp.getIX_profesionaltratante() == null) {
            SihicApp.s_hclfurisp.setIX_profesionaltratante(new GenPersonas());
        }
        try {
            SihicApp.s_hclfurisp.setFechaingreso(UtilDate.colocarfechahoraespecifica2(IX_fechaingreso.getValue(), IX_horaingreso.getText(), IX_minutosingreso.getText()));
            SihicApp.s_hclfurisp.setFechaegreso(UtilDate.colocarfechahoraespecifica2(IX_fechaegreso.getValue(), IX_horaegreso.getText(), IX_minutosegreso.getText()));
        } catch (Exception e) {

        }
        SihicApp.s_hclfurisp.getIX_profesionaltratante().setPrimerApellido(IX_primerapellidoprofesionaltratante.getText());
        SihicApp.s_hclfurisp.getIX_profesionaltratante().setSegundoApellido(IX_segundoapellidoprofesionaltratante.getText());
        SihicApp.s_hclfurisp.getIX_profesionaltratante().setPrimerNombre(IX_primernombreprofesionaltratante.getText());
        SihicApp.s_hclfurisp.getIX_profesionaltratante().setSegundoNombre(IX_segundonombreprofesionaltratante.getText());
        try {
            SihicApp.s_hclfurisp.getIX_profesionaltratante().setGenTiposDocumentos(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().get(IX_tipodocumento.getSelectionModel().getSelectedIndex()));
        } catch (Exception e) {

        }
        SihicApp.s_hclfurisp.getIX_profesionaltratante().setDocumento(IX_documento.getText());
        SihicApp.s_hclfurisp.setIX_noregistromedico(IX_noregistromedico.getText());
        if (IX_sb_dxmainingreso.getText().equals("")) {
            SihicApp.s_hclfurisp.setDxmaininit(null);
        }
        if (IX_sb_dxmainegreso.getText().equals("")) {
            SihicApp.s_hclfurisp.setDxmainend(null);
        }

        if (IX_sb_dxotroingreso.getText().equals("")) {
            SihicApp.s_hclfurisp.setDxtherinit(null);
        }

        if (IX_sb_dxotroegreso.getText().equals("")) {
            SihicApp.s_hclfurisp.setDxtherend(null);
        }

        if (IX_sb_dxotro2ingreso.getText().equals("")) {
            SihicApp.s_hclfurisp.setDxther2init(null);
        }

        if (IX_sb_dxotro2egreso.getText().equals("")) {
            SihicApp.s_hclfurisp.setDxther2end(null);
        }

        //******************************************************************************************************X
        try {
            SihicApp.s_hclfurisp.setX_gastosmedicostotal(new BigDecimal(X_gastosmedicostotalfacturado.getText()));
            SihicApp.s_hclfurisp.setX_gastosmedicosfosyga(new BigDecimal(X_gastosmedicosfosyga.getText()));
            SihicApp.s_hclfurisp.setX_gastostransportetotal(new BigDecimal(X_gastostransportetotalfacturado.getText()));
            SihicApp.s_hclfurisp.setX_gastostransportefosyga(new BigDecimal(X_gastostransportefosyga.getText()));
        } catch (Exception e) {
            SihicApp.s_hclfurisp.setX_gastosmedicostotal(BigDecimal.valueOf(0));
            SihicApp.s_hclfurisp.setX_gastosmedicosfosyga(BigDecimal.valueOf(0));
            SihicApp.s_hclfurisp.setX_gastostransportetotal(BigDecimal.valueOf(0));
            SihicApp.s_hclfurisp.setX_gastostransportefosyga(BigDecimal.valueOf(0));

        }

    }

    public void Load_ChoiceBox() {

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

            }
        });

        LoadChoiceBoxGeneral.getGen_sexo_id().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                LoadChoiceBoxGeneral.getGen_etnias_id().requestFocus();
            }
        });

        LoadChoiceBoxGeneral.getGen_niveles_educativos_id().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {

            }
        });

        LoadChoiceBoxGeneral.getGen_zona_residencia_id().setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                LoadChoiceBoxGeneral.getGen_estados_civiles_id().requestFocus();
            }
        });

        save.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();
                    pdffurisp.requestFocus();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FGenPersonas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        pdffurisp.setOnAction(e -> {

            try {
                pdfFurisp.furisp();
            } catch (IOException ex) {
                Logger.getLogger(FHclFurisp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(FHclFurisp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pdffurisp.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {

                pdffurisp.requestFocus();
                LoadChoiceBoxGeneral.getGen_tipos_documentos_id().requestFocus();

            }
        });

    }

    public void crearoeditar() {

        //***************************************************I
        I_razonsocial.setText(SihicApp.genUnidadesOrganizacion.getNombre());
        I_codigo.setText(SihicApp.genUnidadesOrganizacion.getCodigo());
        I_nit.setText(SihicApp.genUnidadesOrganizacion.getNit());
        I_direccion.setText(SihicApp.genUnidadesOrganizacion.getDireccion());
        I_dpto.setText(SihicApp.genUnidadesOrganizacion.getGenMunicipios().getGenDepartamentos().getNombre());
        I_coddpto.setText(SihicApp.genUnidadesOrganizacion.getGenMunicipios().getGenDepartamentos().getCodigo());
        I_telefono.setText(SihicApp.genUnidadesOrganizacion.getTelefono());
        I_municipio.setText(SihicApp.genUnidadesOrganizacion.getGenMunicipios().getNombre());
        I_codmunicipio.setText(SihicApp.genUnidadesOrganizacion.getGenMunicipios().getCodigo());
        //***************************************************II
        if (SihicApp.s_hclfurisp.getFactura().getFacturaItems().size() > 0) {
            try
            {
            II_primer_apellidovictimas.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido());
            II_segundo_apellidovictimas.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido());
            II_primer_nombrevictimas.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre());
            II_segundo_nombreapellidos.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre());
            II_tipodocumento.setText((SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla()));
            II_documento.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
           II_fechanacimiento.setText(sihic.util.UtilDate.formatodiamesyear(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento()));
            II_sexo.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getDescripcion());
            II_direccion.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDireccion());
            }catch(Exception e)
            {
            II_primer_apellidovictimas.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido());
            II_segundo_apellidovictimas.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido());
            II_primer_nombrevictimas.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre());
            II_segundo_nombreapellidos.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre());
            II_tipodocumento.setText((SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla()));
            II_documento.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
            II_fechanacimiento.setText(sihic.util.UtilDate.formatodiamesyear(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento()));
            II_sexo.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getDescripcion());
            II_direccion.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDireccion());
             
            }
            try
            {
            II_dpto.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getNombre());
            II_coddpto.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo());
             II_telefono.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getTelefono());
            
            }catch(Exception e)
            {
                try
                {
                 II_dpto.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getNombre());
            II_coddpto.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo());
               II_telefono.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getTelefono());
                }catch(Exception e2)
                {
                    
                }
            }
             try
            {
              II_municipio.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getNombre());
              II_codmunicipio.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5));
            }catch(Exception e)
            { try
            {
                   II_municipio.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getNombre());
              II_codmunicipio.setText(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5));
          
            }
             catch(Exception e2)
                {
                    
                }
            }
        }
        nofactura.setText(SihicApp.s_hclfurisp.getFactura().getNofacturacerosizquierda());
        if (SihicApp.s_hclfurisp.getId() != null) {
            fecharadicado.setValue(sihic.util.UtilDate.formatoyearmesdia(SihicApp.s_hclfurisp.getFecharadicacion()));
            rg.setSelected(SihicApp.s_hclfurisp.isRg());
            noradicado.setText(SihicApp.s_hclfurisp.getNoradicado());
            noradicadoanterior.setText(SihicApp.s_hclfurisp.getNoradicadoanterior());
            II_condicionaccidentado.getSelectionModel().select(SihicApp.s_hclfurisp.getCondicionaccidentado());

            //*********************************************************************************************III
            III_naturales.getSelectionModel().select(SihicApp.s_hclfurisp.getNaturalezaevento());
            III_cual.setText(SihicApp.s_hclfurisp.getNaturaleza());
            III_dir_ocurrencia.setText(SihicApp.s_hclfurisp.getDireccionocurrencia());
            try {
                III_fechaeventoaccicente.setValue(sihic.util.UtilDate.formatoyearmesdia(SihicApp.s_hclfurisp.getFecharevento()));
            } catch (Exception e) {

            }
            try {
                III_horaeventoaccicente.setText(String.valueOf(sihic.util.UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharevento())));
            } catch (Exception e) {

            }
            try {
                III_mineventoaccicente.setText(String.valueOf(sihic.util.UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharevento())));
            } catch (Exception e) {

            }
            III_genMunicipios = SihicApp.s_hclfurisp.getIII_genMunicipios();
            if (SihicApp.s_hclfurisp.getIII_genMunicipios() != null) {
                III_sbmunicipio.setText(SihicApp.s_hclfurisp.getIII_genMunicipios().getNombre());
            }
            III_spmunicipio.hide();
            III_zona.getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_zona_residencia().indexOf(SihicApp.s_hclfurisp.getGenZonaResidencia()));
            III_enuncie.setText(SihicApp.s_hclfurisp.getDescripciobreve());
            //***************************************************************************************************IV
            IV_cb_estadoseguro.getSelectionModel().select(SihicApp.s_hclfurisp.getEstadoaseguramiento());
            IV_marca.setText(SihicApp.s_hclfurisp.getMarca());
            IV_placa.setText(SihicApp.s_hclfurisp.getPlaca());
            IV_cb_tiposervicio.getSelectionModel().select(SihicApp.s_hclfurisp.getTiposervicio());
            IV_nombreaseguradora.setText(SihicApp.s_hclfurisp.getNombreaseguradora());
            IV_nopoliza.setText(SihicApp.s_hclfurisp.getNopoliza());
            IV_rb_intautoridadsi.setSelected(SihicApp.s_hclfurisp.isIntervencionautoridad() ? true : false);
            IV_rb_intautoridadno.setSelected(!SihicApp.s_hclfurisp.isIntervencionautoridad() ? true : false);
            IV_rb_cobroexcedentesi.setSelected(SihicApp.s_hclfurisp.isCobroexcedentepoliza() ? true : false);
            IV_rb_cobroexcedenteno.setSelected(!SihicApp.s_hclfurisp.isCobroexcedentepoliza() ? true : false);
            IV_dp_vigenciadesde.setValue(sihic.util.UtilDate.formatoyearmesdia(SihicApp.s_hclfurisp.getFecharvigenciadesde()));
            IV_dp_vigenciahasta.setValue(sihic.util.UtilDate.formatoyearmesdia(SihicApp.s_hclfurisp.getFechavigenciahasta()));
            IV_codigoaseguradora.setText(SihicApp.s_hclfurisp.getCodigoaseguradora());
            //************************************************************************************************************V
            if (SihicApp.s_hclfurisp.getPropietariovehiculo() != null) {
                V_primer_apellidovictimas.setText(SihicApp.s_hclfurisp.getPropietariovehiculo().getPrimerApellido());
                V_segundo_apellidovictimas.setText(SihicApp.s_hclfurisp.getPropietariovehiculo().getSegundoApellido());
                V_primer_nombrevictimas.setText(SihicApp.s_hclfurisp.getPropietariovehiculo().getPrimerNombre());
                V_segundo_nombreapellidos.setText(SihicApp.s_hclfurisp.getPropietariovehiculo().getSegundoNombre());

                V_tipodocumento.getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().indexOf(SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos()));
                V_documento.setText(SihicApp.s_hclfurisp.getPropietariovehiculo().getDocumento());
                V_direccion.setText(SihicApp.s_hclfurisp.getPropietariovehiculo().getDireccion());
                V_telefono.setText(SihicApp.s_hclfurisp.getPropietariovehiculo().getTelefono());
                if (SihicApp.s_hclfurisp.getV_genMunicipios() != null) {
                    V_sbmunicipio.setText(SihicApp.s_hclfurisp.getV_genMunicipios().getNombre());

                    V_GenMunicipios = SihicApp.s_hclfurisp.getV_genMunicipios();
                    V_genGenPersonas = SihicApp.s_hclfurisp.getPropietariovehiculo();
                }
                V_spmunicipio.hide();
            }
            //*******************************************************************************************************VI
            if (SihicApp.s_hclfurisp.getConsuctorvehiculo() != null) {
                VI_primer_apellidovictimas.setText(SihicApp.s_hclfurisp.getConsuctorvehiculo().getPrimerApellido());
                VI_segundo_apellidovictimas.setText(SihicApp.s_hclfurisp.getConsuctorvehiculo().getSegundoApellido());
                VI_primer_nombrevictimas.setText(SihicApp.s_hclfurisp.getConsuctorvehiculo().getPrimerNombre());
                VI_segundo_nombreapellidos.setText(SihicApp.s_hclfurisp.getConsuctorvehiculo().getSegundoNombre());
                VI_tipodocumento.getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().indexOf(SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos()));
                VI_documento.setText(SihicApp.s_hclfurisp.getConsuctorvehiculo().getDocumento());
                VI_direccion.setText(SihicApp.s_hclfurisp.getConsuctorvehiculo().getDireccion());
                VI_telefono.setText(SihicApp.s_hclfurisp.getConsuctorvehiculo().getTelefono());
                if (SihicApp.s_hclfurisp.getVI_genMunicipios() != null) {
                    VI_sbmunicipio.setText(SihicApp.s_hclfurisp.getVI_genMunicipios().getNombre());
                }
                VI_spmunicipio.hide();
            }
            VI_tipodocumento_vehiculoinvolucrado1.getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().indexOf(SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1()));
            VI_cb_estadoseguro_vehiculoinvolucrado1.getSelectionModel().select(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados1());
            VI_placa_vehiculoinvolucrado1.setText(SihicApp.s_hclfurisp.getVI_placa_vehiculosinvolucrados1());
            VI_documento_vehiculoinvolucrado1.setText(SihicApp.s_hclfurisp.getVI_documento_vehiculosinvolucrados1());
            VI_tipodocumento_vehiculoinvolucrado2.getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().indexOf(SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2()));
            VI_cb_estadoseguro_vehiculoinvolucrado2.getSelectionModel().select(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados2());
            VI_placa_vehiculoinvolucrado2.setText(SihicApp.s_hclfurisp.getVI_placa_vehiculosinvolucrados2());
            VI_documento_vehiculoinvolucrado2.setText(SihicApp.s_hclfurisp.getVI_documento_vehiculosinvolucrados2());

            //************************************************************************************************************VII
            VII_cb_tiporeferencia.getSelectionModel().select(SihicApp.s_hclfurisp.getTiporeferencia());
            try {
                VII_dp_remisiondesde.setValue(sihic.util.UtilDate.formatoyearmesdia(SihicApp.s_hclfurisp.getFecharemision()));

                VII_remisionhora.setText(String.valueOf(sihic.util.UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharemision())));
                VII_remisionminutos.setText(String.valueOf(sihic.util.UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharemision())));
                VII_prestadorremite.setText(SihicApp.s_hclfurisp.getPersonaqueremite());
                VII_codigoinscripcionremite.setText(SihicApp.s_hclfurisp.getVII_codigoinscripcionremite());
                VII_profesionalremite.setText(SihicApp.s_hclfurisp.getVII_profesionalremite());
                VII_profesionalremitecargo.setText(SihicApp.s_hclfurisp.getVII_profesionalrecibecargo());
                VII_genmunicipioremite = SihicApp.s_hclfurisp.getVII_genMunicipiosRemite();
            } catch (Exception e) {
                VII_dp_remisiondesde.setValue(null);
            }
            if (SihicApp.s_hclfurisp.getVII_genMunicipiosRemite() != null) {
                VII_sb_municipioremite.setText(SihicApp.s_hclfurisp.getVII_genMunicipiosRemite().getNombre());
            }
            VII_sp_municipioremite.hide();
            VII_direccionremite.setText(SihicApp.s_hclfurisp.getVII_direccionremite());
            try {
                VII_dp_fechaaceptacion.setValue(sihic.util.UtilDate.formatoyearmesdia(SihicApp.s_hclfurisp.getFechaaceptacion()));

                VII_aceptacionhora.setText(String.valueOf(sihic.util.UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaaceptacion())));
                VII_aceptacionminutos.setText(String.valueOf(sihic.util.UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaaceptacion())));
            } catch (Exception e) {
                VII_dp_fechaaceptacion.setValue(null);
            }
            VII_prestadorrecibe.setText(SihicApp.s_hclfurisp.getPersonaquerecibe());
            VII_codigoinscripcionrecibe.setText(SihicApp.s_hclfurisp.getVII_codigoinscripcionrecibe());
            VII_profesionalrecibe.setText(SihicApp.s_hclfurisp.getVII_profesionalrecibe());
            VII_profesionalrecibecargo.setText(SihicApp.s_hclfurisp.getVII_profesionalrecibecargo());
            VII_genmunicipiorecibe = SihicApp.s_hclfurisp.getVII_genMunicipiosRecibe();
            if (SihicApp.s_hclfurisp.getVII_genMunicipiosRecibe() != null) {
                VII_sb_municipiorecibe.setText(SihicApp.s_hclfurisp.getVII_genMunicipiosRecibe().getNombre());
                VII_direccionrecibe.setText(SihicApp.s_hclfurisp.getVII_direccionrecibe());
            }
            VII_sp_municipiorecibe.hide();
            //************************************************************************************VIII
            VIII_placa.setText(SihicApp.s_hclfurisp.getPlacamovvictima());
            if (SihicApp.s_hclfurisp.getVIII_consuctorvehiculo() != null) {
                VIII_primerapellidoconductor.setText(SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().getPrimerApellido());
                VIII_segundoapellidoconductor.setText(SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().getSegundoApellido());
                VIII_primernombreconductor.setText(SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().getPrimerNombre());
                VIII_segundonombreconductor.setText(SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().getSegundoNombre());
                VIII_tipodocumento.getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().indexOf(SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().getGenTiposDocumentos()));
                VIII_documento.setText(SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().getDocumento());
            }
            VIII_transportodesde.setText(SihicApp.s_hclfurisp.getTrasnportodesde());
            VIII_transportohasta.setText(SihicApp.s_hclfurisp.getTransportehasta());
            VIII_tipotransporte.getSelectionModel().select(SihicApp.s_hclfurisp.getTipotransporte());
            VIII_zona.getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_zona_residencia().indexOf(SihicApp.s_hclfurisp.getVIII_zona()));
            //***********************************************************************************************************IX
            try {
                IX_fechaingreso.setValue(UtilDate.formatoyearmesdia(SihicApp.s_hclfurisp.getFechaingreso()));

                IX_horaingreso.setText(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaingreso())));
                IX_minutosingreso.setText(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaingreso())));
                IX_fechaegreso.setValue(UtilDate.formatoyearmesdia(SihicApp.s_hclfurisp.getFechaegreso()));
                IX_horaegreso.setText(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaegreso())));
                IX_minutosegreso.setText(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaegreso())));
            } catch (Exception e) {

            }
            try {
                IX_sb_dxmainingreso.setText(SihicApp.s_hclfurisp.getDxmaininit().getCodigo() + "||" + SihicApp.s_hclfurisp.getDxmaininit().getDescripcion());
            } catch (Exception e) {
            }
            try {
                IX_sb_dxotroingreso.setText(SihicApp.s_hclfurisp.getDxtherinit().getCodigo() + "||" + SihicApp.s_hclfurisp.getDxtherinit().getDescripcion());
            } catch (Exception e) {
            }
            try {
                IX_sb_dxotro2ingreso.setText(SihicApp.s_hclfurisp.getDxther2init().getCodigo() + "||" + SihicApp.s_hclfurisp.getDxther2init().getDescripcion());
            } catch (Exception e) {
            }
            try {

                IX_sb_dxmainegreso.setText(SihicApp.s_hclfurisp.getDxmainend().getCodigo() + "||" + SihicApp.s_hclfurisp.getDxmainend().getDescripcion());
            } catch (Exception e) {
            }
            try {
                IX_sb_dxotroegreso.setText(SihicApp.s_hclfurisp.getDxtherend().getCodigo() + "||" + SihicApp.s_hclfurisp.getDxtherend().getDescripcion());
            } catch (Exception e) {
            }
            try {
                IX_sb_dxotro2egreso.setText(SihicApp.s_hclfurisp.getDxther2end().getCodigo() + "||" + SihicApp.s_hclfurisp.getDxther2end().getDescripcion());
            } catch (Exception e) {
            }

            if (SihicApp.s_hclfurisp.getIX_profesionaltratante() != null) {
                try {
                    IX_primerapellidoprofesionaltratante.setText(SihicApp.s_hclfurisp.getIX_profesionaltratante().getPrimerApellido());
                    IX_segundoapellidoprofesionaltratante.setText(SihicApp.s_hclfurisp.getIX_profesionaltratante().getSegundoApellido());
                    IX_primernombreprofesionaltratante.setText(SihicApp.s_hclfurisp.getIX_profesionaltratante().getPrimerNombre());
                    IX_segundonombreprofesionaltratante.setText(SihicApp.s_hclfurisp.getIX_profesionaltratante().getSegundoNombre());
                    IX_tipodocumento.getSelectionModel().select(LoadChoiceBoxGeneral.getV_gen_tipos_documentos().indexOf(SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos()));
                    IX_documento.setText(SihicApp.s_hclfurisp.getIX_profesionaltratante().getDocumento());
                } catch (Exception e) {
                }
            }
            IX_noregistromedico.setText(SihicApp.s_hclfurisp.getIX_noregistromedico());
            IX_sp_dxmainingreso.hide();
            IX_sp_dxmainegreso.hide();
            IX_sp_dxotroingreso.hide();
            IX_sp_dxotroegreso.hide();
            IX_sp_dxotro2ingreso.hide();
            IX_sp_dxotro2egreso.hide();
            //******************************************************************************************************X
            X_gastosmedicostotalfacturado.setText(SihicApp.s_hclfurisp.getX_gastosmedicostotal().toString());
            X_gastosmedicosfosyga.setText(SihicApp.s_hclfurisp.getX_gastosmedicosfosyga().toString());
            X_gastostransportetotalfacturado.setText(SihicApp.s_hclfurisp.getX_gastostransportetotal().toString());
            X_gastostransportefosyga.setText(SihicApp.s_hclfurisp.getX_gastostransportefosyga().toString());

        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
