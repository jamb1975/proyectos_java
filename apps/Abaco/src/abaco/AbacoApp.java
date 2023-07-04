package abaco;
import java.awt.image.BufferedImage;
import abaco.control.PopoverMenu;
import abaco.control.SearchBox;
import abaco.control.TitledToolBar;
import abaco.controlador.ArqueoControllerClient;
import abaco.controlador.CategoriaControllerClient;
import abaco.controlador.ConComprobanteEgresoControllerClient;
import abaco.controlador.ConComprobanteIngresoControllerClient;
import abaco.controlador.ConPucControllerClient;
import abaco.controlador.ConfiguracionesControllerClient;
import abaco.controlador.ConsecutivoNoFacturaControllerClient;
import abaco.controlador.FacturaControllerClient;
import abaco.controlador.FacturaProveedoresControllerClient;
import abaco.controlador.GenMunicipiosControllerClient;
import abaco.generated.Soluciones;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import model.Categoria;
import model.Organizacion;
import model.Producto;
import model.Proveedores;
import model.Personas;
import model.UnidadesMedida;
import abaco.controlador.OrganizacionControllerClient;
import abaco.controlador.GenPersonasControllerClient;
import abaco.controlador.GeneralControllerClient;
import abaco.controlador.KardexControllerClient;
import abaco.controlador.PedidosControllerClient;
import abaco.controlador.ProductoControllerClient;
import abaco.controlador.ProveedoresControllerClient;
import abaco.controlador.UnidadesMedidaControllerClient;
import abaco.generic.LoadChoiceBoxGeneral;
import com.aquafx_project.AquaFx;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IdleManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import jfxtras.styles.jmetro8.JMetro;
import model.Abono;
import model.AbonoProveedores;
import model.Arqueo;
import model.ConComprobanteContabilidad;
import model.ConComprobanteEgreso;
import model.ConComprobanteIngreso;
import model.ConPuc;
import model.Configuraciones;
import model.ConsecutivoFactura;
import model.DetallesPedido;
import model.Factura;
import model.FacturaProveedores;
import model.GenMunicipios;
import model.Kardex;
import model.Pedidos;
import model.Solucion;
import model.UsuarioSoluciones;
import model.Usuarios;
import org.aerofx.AeroFX;
import org.controlsfx.control.PopOver;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import service.EntityManagerGeneric;


/**
 * Ensemble Application
 */
public class AbacoApp extends Application {
    public static enum ESTADOPEDIDO{NUEVOS,CONFIRMADOS,DESPACHADOS};
    private static final String OS_NAME = System.getProperty("ensemble.os.name", System.getProperty("os.name"));
    private static final String OS_ARCH = System.getProperty("ensemble.os.arch", System.getProperty("os.arch"));
    public static final boolean IS_IPHONE = false;
    public static final boolean IS_IOS = "iOS".equals(OS_NAME);
    public static final boolean IS_ANDROID = "android".equals(System.getProperty("javafx.platform")) || "Dalvik".equals(System.getProperty("java.vm.name"));
    public static final boolean IS_EMBEDDED = "arm".equals(OS_ARCH) && !IS_IOS && !IS_ANDROID;
    public static final boolean IS_DESKTOP = !IS_EMBEDDED && !IS_IOS && !IS_ANDROID;
    public static final boolean IS_MAC = OS_NAME.startsWith("Mac");
    public static final boolean PRELOAD_PREVIEW_IMAGES = true;
    public static final boolean SHOW_HIGHLIGHTS = IS_DESKTOP;
    public static final boolean SHOW_MENU = Boolean.getBoolean("ensemble.menu.show");
    public static final boolean SELECT_IOS_THEME = false;
    private static final int TOOL_BAR_BUTTON_SIZE = 30;
    public static String nombresolucion="7";
    private static Scene scene;
    public static int cantidadpedidosnuevos=0;
    public static Button bu_cantidadpedidosnuevos=new Button();
    public static int cantidadpedidosconfirmados=0;
    public static Button bu_cantidadpedidosconfirmados=new Button();
    public static int cantidadpedidosdespachados=0;
    public static Button bu_cantidadpedidosdespachados=new Button();
    private Pane root;
    private static ImageView imgview;
    private static TitledToolBar toolBar;
    private Button backButton;
    private Button forwardButton;
    private Button homeButton;
    public static Button updateButton;
    private ToggleButton listButton;
    private ToggleButton searchButton;
    private final SearchBox SearchBox = new SearchBox();
    private PageBrowser pageBrowser;
    private PopoverMenu sampleListPopover;
    private SearchPopover searchPopover;
    private MenuBar menuBar;
    private static Text T_InfEmpresa;
    private static WritableImage wi;
    private static BufferedImage bi;
    private static Image Loadimg;
    private static ImageView im_alarmaonpedidosnuevos;
    private static ImageView im_alarmaoffpedidosnuevos;
    private static ImageView im_alarmaonpedidosconfirmados;
    private static ImageView im_alarmaoffpedidosconfirmados;
    private static ImageView im_alarmaonpedidosdespachados;
    private static ImageView im_alarmaoffpedidosdespachados;
    //Clases static que se utilizan en las demas soluciones como datos generales
    public static HashMap<String,Solucion> hm_usuariosoluciones=new HashMap<>();
    public static Scene sc_Login =null;
    public static boolean Bo_LoginCorrecto;
    public static final ChoiceBox cb_soluciones=new ChoiceBox();
    private String appPathLogin;
    private Class  clzLogin;
    private Object appClassLogin ; 
    public static Stage  stageLogin = new Stage(StageStyle.DECORATED);
    public static Stage  stageProgreso = new Stage(StageStyle.DECORATED);
    private Parent pa_login ;
    public static List<UsuarioSoluciones> Li_UsuarioSoluciones=new ArrayList<>();
    private static final ObservableList ol_empresa = FXCollections.observableArrayList();
    public static List<Organizacion>li_organizacion=new ArrayList();
    private static final ObservableList ol_producto = FXCollections.observableArrayList();
    public static List<Producto>li_producto=new ArrayList();
    public static List<Kardex>li_kardex=new ArrayList();
    public static List<Kardex>li_kardexservicios=new ArrayList();
    public static List<GenMunicipios>li_genmunicipios=new ArrayList();
    private static ObservableList ol_categoria = FXCollections.observableArrayList();
    public static List<Categoria>li_categoria=new ArrayList();
    private static ObservableList ol_genpersonas = FXCollections.observableArrayList();
    public static List<Personas>li_genpersonas=new ArrayList();
    private static ObservableList ol_datosproveedores = FXCollections.observableArrayList();
    public static List<Proveedores>li_proveedores=new ArrayList();
    private static ObservableList ol_empleados = FXCollections.observableArrayList();
    public static List<Personas>li_empleados=new ArrayList();
    public static List<Factura>li_facturas=new ArrayList();
    public static List<FacturaProveedores>li_facturasproveedores=new ArrayList();
    public static List<UnidadesMedida>li_unidadesmedida=new ArrayList<>();
    public static List<ConComprobanteIngreso>li_conComprobanteIngreso=new ArrayList<>();
    public static List<ConPuc> li_conpuc=new ArrayList<>();
    public static List<ConsecutivoFactura> li_consecutivofactura=new ArrayList<>();
    public static List<Pedidos> li_pedidos=new ArrayList<>();
    private static final Vector<Runnable> dataLoadingTasks = new Vector<Runnable>();
    private static AbacoApp sofackarmain;
    public static  StackPane modalDimmer;
    public static final List<UnidadesMedida> l_unidadmedida=new ArrayList<UnidadesMedida>();
    public static ImageView I_ImagenLogo=new ImageView();
    public static GeneralControllerClient generalControllerClient=new GeneralControllerClient();
    public static OrganizacionControllerClient organizacionControllerClient=new OrganizacionControllerClient();
    public static CategoriaControllerClient categoriaControllerClient=new CategoriaControllerClient();
    public static ProveedoresControllerClient proveedoresControllerClient = new ProveedoresControllerClient();
    public static ProductoControllerClient productoControllerClient=new ProductoControllerClient();
    public static GenPersonasControllerClient genPersonasControllerClient=new GenPersonasControllerClient();
    public static FacturaControllerClient facturaControllerClient=new FacturaControllerClient();
    public static FacturaProveedoresControllerClient facturaProveedoresControllerClient=new FacturaProveedoresControllerClient();
    public static GenMunicipiosControllerClient genMunicipiosControllerClient=new GenMunicipiosControllerClient();
    public static KardexControllerClient kardexControllerClient=new KardexControllerClient();
    public static UnidadesMedidaControllerClient unidadesMedidaControllerClient=new UnidadesMedidaControllerClient();
    public static ConComprobanteIngresoControllerClient conComprobanteIngresoControllerClient=new ConComprobanteIngresoControllerClient();
    public static ConPucControllerClient conPucControllerClient=new ConPucControllerClient();
    public static ConfiguracionesControllerClient configuracionesControllerClient=new ConfiguracionesControllerClient();
    public static ArqueoControllerClient arqueoControllerClient=new ArqueoControllerClient();
    public static ConsecutivoNoFacturaControllerClient consecutivoNoFacturaControllerClient=new ConsecutivoNoFacturaControllerClient();
    public static ConComprobanteEgresoControllerClient conComprobanteEgresoControllerClient=new ConComprobanteEgresoControllerClient();
    public static PedidosControllerClient   pedidosControllerclient=new PedidosControllerClient();
    public static ConComprobanteIngreso s_conComprobanteIngreso=new ConComprobanteIngreso();
    public static ConsecutivoFactura consecutivoFactura=new ConsecutivoFactura();
    public static ConComprobanteEgreso conComprobanteEgreso=new ConComprobanteEgreso();
    public static ConPuc s_conPuc=new ConPuc();
    public static Solucion solucion=new Solucion();
    public static  LoadChoiceBoxGeneral loadChoiceBoxGeneral;
    public static  int cprogreso=0;
    private static AtomicInteger counter;
    public static Task<Void>   task_4;
    public static  Thread  thimport_4;
    public static Task<Void>   task_leerbdpedidosabaco;
    public static  Thread  thimport_leerbdpedidosabaco;
    public static  Usuarios s_usuarios=new Usuarios();
    public static  Producto s_producto=new Producto();
    public static  Kardex s_kardex=new Kardex();
    public static  Organizacion s_organizacion=new Organizacion();
    public static  Categoria s_categoria=new Categoria();
    public static  GenMunicipios s_genmunicipios=new GenMunicipios(); 
    public static  Proveedores s_proveedores=new Proveedores();
    public static  Personas s_genpersonas=new Personas();
    public static  Factura  s_factura=new Factura();
    public static  FacturaProveedores  s_facturaproveedores=new FacturaProveedores();
    public static Abono s_abono=new Abono();
    public static AbonoProveedores s_abonoproveedores=new AbonoProveedores();
    public static UnidadesMedida s_unidadesmedida=new UnidadesMedida();
    public static ConPuc s_ConPuc=new ConPuc();
    public static ConComprobanteContabilidad conComprobanteContabilidad=new ConComprobanteContabilidad();
    public static Configuraciones s_configuraciones=new Configuraciones();
    public static Arqueo s_arqueo=new Arqueo();
    public static Pedidos pedidos=new Pedidos();
    public static ChoiceBox cb_temas=new ChoiceBox();
    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object,Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
} 
    private static IMAPFolder folder = null;
    private static Store store = null;
    private static String subject = null;
    private static Flags.Flag flag = null;
    private static ExecutorService es = Executors.newCachedThreadPool();
    private static IdleManager idleManager = null;
    private static Session session;
    private static PopOver popover;
    private static  Task<Void>   task_generic;
    private static Thread  thimport_generic;
    private static ProgressBar pb ;
    private static ProgressIndicator pi;
    private static GridPane gp=new GridPane();
    public static   double  avance_progress=0;
    private  String appclasspedido;
    private  Class clzpedido ;
    private  Object apppedido ;
    private  Parent parentpedido;
    private  Stage stagepedido= new Stage(StageStyle.DECORATED);
    private Scene sc_pedido=null;
    public static int estadopedido=ESTADOPEDIDO.NUEVOS.ordinal();
    private static Thread t;
    @Override public void init() throws Exception {
        // CREATE ROOT
        bu_cantidadpedidosnuevos.setOnAction(e->{
            try {
                estadopedido=ESTADOPEDIDO.NUEVOS.ordinal();
                showpedidosnuevos();
            } catch (Exception ex) {
                Logger.getLogger(AbacoApp.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        bu_cantidadpedidosconfirmados.setOnAction(e->{
            try {
                estadopedido=ESTADOPEDIDO.CONFIRMADOS.ordinal();
                showpedidosnuevos();
            } catch (Exception ex) {
                Logger.getLogger(AbacoApp.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        bu_cantidadpedidosdespachados.setOnAction(e->{
            try {
                estadopedido=ESTADOPEDIDO.DESPACHADOS.ordinal();
                showpedidosnuevos();
            } catch (Exception ex) {
                Logger.getLogger(AbacoApp.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
         

          popover=new PopOver(gp);
          im_alarmaonpedidosnuevos=new ImageView("/images/alarmaon.png");
          im_alarmaonpedidosnuevos.setFitHeight(20);
          im_alarmaonpedidosnuevos.setFitWidth(20);
          im_alarmaoffpedidosnuevos=new ImageView("/images/alarmaoff.png");
          im_alarmaoffpedidosnuevos.setFitHeight(20);
          im_alarmaoffpedidosnuevos.setFitWidth(20);
          
          im_alarmaonpedidosconfirmados=new ImageView("/images/alarma2on.png");
          im_alarmaonpedidosconfirmados.setFitHeight(20);
          im_alarmaonpedidosconfirmados.setFitWidth(20);
          im_alarmaoffpedidosconfirmados=new ImageView("/images/alarmaoff.png");
          im_alarmaoffpedidosconfirmados.setFitHeight(20);
          im_alarmaoffpedidosconfirmados.setFitWidth(20);
          
          im_alarmaonpedidosdespachados=new ImageView("/images/alarma3on.png");
          im_alarmaonpedidosdespachados.setFitHeight(20);
          im_alarmaonpedidosdespachados.setFitWidth(20);
          im_alarmaoffpedidosdespachados=new ImageView("/images/alarmaoff.png");
          im_alarmaoffpedidosdespachados.setFitHeight(20);
          im_alarmaoffpedidosdespachados.setFitWidth(20);
          
          
          bu_cantidadpedidosnuevos.setGraphic(im_alarmaoffpedidosnuevos);
          bu_cantidadpedidosnuevos.setText("+"+String.valueOf(cantidadpedidosnuevos)+" pedidos nuevos");
          bu_cantidadpedidosconfirmados.setGraphic(im_alarmaoffpedidosconfirmados);
          bu_cantidadpedidosconfirmados.setText("+"+cantidadpedidosconfirmados+" pedidos confirmados");
          bu_cantidadpedidosdespachados.setGraphic(im_alarmaoffpedidosdespachados);
          bu_cantidadpedidosdespachados.setText("+"+cantidadpedidosdespachados+" pedidos despachados");
          cb_temas.getItems().clear();
          cb_temas.getItems().addAll("Metro Dark","Metro Light","AeroFX","AquaFx");
          cb_temas.getSelectionModel().select(0);
      
        loadChoiceBoxGeneral=new LoadChoiceBoxGeneral();
        appPathLogin="abaco.admon.FLogin";
        clzLogin = Class.forName(appPathLogin);
        appClassLogin=clzLogin.newInstance();
        root = new Pane() {
            @Override protected void layoutChildren() {
                super.layoutChildren();
                final double w = getWidth();
                final double h = getHeight();
                final double menuHeight = SHOW_MENU ? menuBar.prefHeight(w) : 0;
                final double toolBarHeight = toolBar.prefHeight(w);
                if (menuBar != null) {
                    menuBar.resize(w, menuHeight);
                }
                toolBar.resizeRelocate(0, menuHeight, w, toolBarHeight);
                pageBrowser.setLayoutY(toolBarHeight + menuHeight);
                pageBrowser.resize(w, h-toolBarHeight);
                pageBrowser.resize(w, h - toolBarHeight - menuHeight);
                
                sampleListPopover.autosize();
                Point2D listBtnBottomCenter = listButton.localToScene(listButton.getWidth()/2, listButton.getHeight());
                sampleListPopover.setLayoutX((int)listBtnBottomCenter.getX()-50);
                sampleListPopover.setLayoutY((int)listBtnBottomCenter.getY()+20);
                Point2D SearchBoxBottomCenter = SearchBox.localToScene(SearchBox.getWidth()/2, SearchBox.getHeight());
            }
        };
        // CREATE MENUBAR
        if (SHOW_MENU) {
            menuBar = new MenuBar();
            menuBar.setUseSystemMenuBar(true);
            menuBar.setStyle("  -fx-background-repeat: repeat;\n" +
"    -fx-background-color:\n" +
"        linear-gradient(azure,azure, #EEEEEE 100%),\n" +
"        linear-gradient(azure, #EEEEEE),\n" +
"        radial-gradient(center 50% 0%, radius 100%, rgba(0,0,0,0.2), rgba(255,0,0,0));\n" +
"    -fx-padding: 8px;\n" +
"    -fx-border-width:0px");
            ToggleGroup screenSizeToggle = new ToggleGroup();
            Menu menu = new Menu("Screen size");
            menu.getItems().addAll(
                    screenSizeMenuItem("iPad Landscape", 1024, 768, false, screenSizeToggle),
                    screenSizeMenuItem("iPad Portrait", 768, 1024, false, screenSizeToggle),
                    screenSizeMenuItem("Beagleboard", 1024, 600, false, screenSizeToggle),
                    screenSizeMenuItem("iPad Retina Landscape", 2048, 1536, true, screenSizeToggle),
                    screenSizeMenuItem("iPad Retina Portrait", 1536, 2048, true, screenSizeToggle),
                    screenSizeMenuItem("iPhone Landscape", 480, 320, false, screenSizeToggle),
                    screenSizeMenuItem("iPhone Portrait", 320, 480, false, screenSizeToggle),
                    screenSizeMenuItem("iPhone 4 Landscape", 960, 640, true, screenSizeToggle),
                    screenSizeMenuItem("iPhone 4 Portrait", 640, 960, true, screenSizeToggle),
                    screenSizeMenuItem("iPhone 5 Landscape", 1136, 640, true, screenSizeToggle),
                    screenSizeMenuItem("iPhone 5 Portrait", 640, 1136, true, screenSizeToggle));
            menuBar.getMenus().add(menu);
            screenSizeToggle.selectToggle(screenSizeToggle.getToggles().get(0));
            
            root.getChildren().add(menuBar);
        }
        // CREATE TOOLBAR
        toolBar = new TitledToolBar();
       // toolBar.getStyleClass().add("toolbar");
        root.getChildren().add(toolBar);
        imgview=new ImageView("/images/previous.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        backButton = new Button("",imgview);
        backButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        backButton. setStyle("-fx-border-color:   #007fff;\n" +"       -fx-border-width: 0;");
      
        imgview=new ImageView("/images/next.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        forwardButton = new Button("",imgview);
        forwardButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        forwardButton. setStyle("-fx-border-color:   #007fff;\n" +
"       -fx-border-width: 0;");
        imgview=null;
        imgview=new ImageView("/images/home.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        homeButton = new Button("",imgview);
        homeButton.setStyle("-fx-border-color:   #007fff;\n" +
"       -fx-border-width: 0;");
        homeButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
       imgview=null;
        imgview=new ImageView("/images/updatewhite.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        updateButton = new Button("",imgview);
        updateButton.setStyle("-fx-border-color:   #007fff;\n" +
"       -fx-border-width: 0;");
        updateButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);

        HBox navButtons = new HBox(7,backButton,forwardButton,homeButton,updateButton,bu_cantidadpedidosnuevos,bu_cantidadpedidosconfirmados,bu_cantidadpedidosdespachados,gp);
         imgview=null;
        imgview=new ImageView("/images/menu.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        listButton = new ToggleButton("",imgview);
        listButton.setStyle("-fx-border-color:   white;\n" +"-fx-border-width: 0;-fx-background-color: transparent; -fx-border-insets: -2.1;\n" +
"       -fx-border-radius:7;");
   
       
        //listButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        HBox.setMargin(listButton, new Insets(0, 0, 0, 7));
        searchButton = new ToggleButton();
        searchButton.setId("search");
        searchButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        SearchBox.setPrefWidth(200);
        //backButton.setGraphic(new Region());
       // forwardButton.setGraphic(new Region());
        //.setGraphic(new Region());
       
        searchButton.setGraphic(new Region());
       T_InfEmpresa=new Text();
        T_InfEmpresa.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_InfEmpresa.setFont(Font.font("ARIAL", FontWeight.NORMAL,14));
        T_InfEmpresa.setFill(Color.WHITE);
        I_ImagenLogo.setFitHeight(30);
        I_ImagenLogo.setFitWidth(30);
      
        toolBar.addLeftItems(navButtons,listButton);
        toolBar.addRightItems(I_ImagenLogo,T_InfEmpresa);
          load_all_parameters_and_lists();
        // create PageBrowser
        pageBrowser = new PageBrowser();
        pageBrowser.setStyle("-fx-background-image: url(\"images/enterprise.png\");\n" +
                         "    -fx-background-position:left bottom ;\n" +
                         "    -fx-background-repeat: no-repeat no-repeat ;\n" +
                         "    -fx-background-size: auto;-fx-padding: 50px;\n" +
                        
"    -fx-border-insets: 50px;\n" +
"    -fx-background-insets: 50px;  -fx-border-width: 50px;");
        toolBar.titleTextProperty().bind(pageBrowser.currentPageTitleProperty());
        root.getChildren().add(0, pageBrowser);
        pageBrowser.goHome();
        // wire nav buttons
        backButton.setOnAction((ActionEvent event) -> {
            pageBrowser.backward();
        });
        backButton.disableProperty().bind(pageBrowser.backPossibleProperty().not());
        forwardButton.setOnAction((ActionEvent event) -> {
            pageBrowser.forward();
        });
        forwardButton.disableProperty().bind(pageBrowser.forwardPossibleProperty().not());
        homeButton.setOnAction((ActionEvent event) -> {
            pageBrowser.goHome();
        });
        homeButton.disableProperty().bind(pageBrowser.atHomeProperty());
        
        // create and setup list popover
        sampleListPopover = new PopoverMenu();
        sampleListPopover.setPrefWidth(440);
        root.getChildren().add(sampleListPopover);
        final SamplePopoverTreeList rootPage = new SamplePopoverTreeList(Soluciones.ROOT,pageBrowser);
        listButton.setOnMouseClicked((MouseEvent e) -> {
            if (sampleListPopover.isVisible()) {
                sampleListPopover.hide();
            } else {
                sampleListPopover.clearPages();
                sampleListPopover.pushPage(rootPage);
                sampleListPopover.show(() -> {
                    listButton.setSelected(false);
                });
            }
        });
        updateButton.setOnAction(e->{
             try {
                   
                 load_all_parameters_and_lists();
                
             } catch (ParseException ex) {
                
             } catch (NoSuchMethodException ex) {
                 
             } catch (IllegalAccessException ex) {
                 
             }
        });
        // create AndroidStyle menu handling
        if (IS_ANDROID) {
            root.setOnKeyReleased(new EventHandler<KeyEvent>() {
                private int exitCount = 0;

                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        if (sampleListPopover.isVisible()) {
                            sampleListPopover.hide();
                            event.consume();
                            return;
                        }

                        if (!backButton.isDisabled()) {
                            pageBrowser.backward();
                            event.consume();
                            return;
                        }
                        exitCount++;
                        if (exitCount == 2) {
                            System.exit(0);
                        }
                    } else {
                        exitCount = 0;
                    }

                    if (event.getCode() == KeyCode.CONTEXT_MENU) {
                        if (sampleListPopover.isVisible()) {
                            sampleListPopover.hide();
                        } else {
                            sampleListPopover.clearPages();
                            sampleListPopover.pushPage(rootPage);
                            sampleListPopover.show(() -> {
                                listButton.setSelected(false);
                            });
                        }
                        event.consume();
                    }
                }
            });
        }
        //correr tarea
             
        // create and setup search popover
                       //LoadDataInit();
         
      
        modalDimmer = new StackPane();
        //modalDimmer.setId("ModalDimmer");
        modalDimmer.setVisible(false);
         root.setDepthTest(DepthTest.DISABLE);
        root.getChildren().add(modalDimmer);
     
    }

    private RadioMenuItem screenSizeMenuItem(String text, final int width, final int height, final boolean retina, ToggleGroup tg) {
        RadioMenuItem radioMenuItem = new RadioMenuItem(text + " " + width + "x" + height);
        radioMenuItem.setToggleGroup(tg);
        radioMenuItem.setOnAction((ActionEvent t) -> {
            double menuHeight = IS_IOS || IS_MAC || IS_ANDROID ? 0 : menuBar.prefHeight(width);
            scene.getWindow().setWidth(width + scene.getWindow().getWidth() - scene.getWidth());
            scene.getWindow().setHeight(height + menuHeight + scene.getWindow().getHeight() - scene.getHeight());
            if (retina) {
                Parent root = scene.getRoot();
                if (root instanceof Pane) {
                    Group newRoot = new Group();
                    newRoot.setAutoSizeChildren(false);
                    scene.setRoot(newRoot);
                    newRoot.getChildren().add(root);
                    root.getTransforms().add(new Scale(2, 2, 0, 0));
                    root.resize(width/2, height/2);
                } else {
                    root.getChildrenUnmodifiable().get(0).resize(width/2, height/2);
                }
            } else {
                Parent root = scene.getRoot();
                if (root instanceof Group) {
                    Pane oldRoot = (Pane)root.getChildrenUnmodifiable().get(0);
                    ((Group)root).getChildren().clear();
                    oldRoot.getTransforms().clear();
                    scene.setRoot(oldRoot);
                }
            }
        });
        return radioMenuItem;
    }

    private void setStylesheets() {
        final String EXTERNAL_STYLESHEET = "http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600";
      //  scene.getStylesheets().setAll("/abaco/SofackarStylesCommon.css");
         scene.setFill(Color.TRANSPARENT);
         switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(root);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(root);
                      break;
             case 2:    AeroFX.style();
                        AeroFX.styleAllAsGroupBox(root);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(root);
                     
                      break;              
         }
         
        Thread backgroundThread = new Thread(() -> {
            try {
                URL url = new URL(EXTERNAL_STYLESHEET);
                try (
                        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                        Reader newReader = Channels.newReader(rbc, "ISO-8859-1");
                        BufferedReader bufferedReader = new BufferedReader(newReader)
                        ) {
                    // Checking whether we can read a line from this url
                    // without exception
                    bufferedReader.readLine();
                }
                Platform.runLater(() -> {
                   scene.getStylesheets().add(EXTERNAL_STYLESHEET);
                });
            }catch (MalformedURLException ex) {
                Logger.getLogger(AbacoApp.class.getName()).log(Level.FINE, "Failed to load external stylesheet", ex);
            }catch (IOException ex) {
                    Logger.getLogger(AbacoApp.class.getName()).log(Level.FINE, "Failed to load external stylesheet", ex);
                }
        }, "Trying to reach external styleshet");
      // backgroundThread.setDaemon(true);
     //  backgroundThread.start();
    }    
    
    @Override public void start(final Stage stage) throws Exception {
        
        showlogin();
          if(Bo_LoginCorrecto)
         {
          loadpopover();
          Rectangle2D primaryScreenBounds2 = Screen.getPrimary().getVisualBounds();
        scene = new Scene(root,(primaryScreenBounds2.getWidth()), 768, Color.BLUE);
        if (IS_EMBEDDED || IS_ANDROID) {
            new ScrollEventSynthesizer(scene);
        }
        setStylesheets();
       // stage.setOpacity(0.5);
        stage.setScene(scene);
        
        // START FULL SCREEN IF WANTED
        if (PlatformFeatures.START_FULL_SCREEN) {
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(primaryScreenBounds.getMinX());
            stage.setY(primaryScreenBounds.getMinY());
            stage.setWidth(primaryScreenBounds.getWidth());
            stage.setHeight(primaryScreenBounds.getHeight());
        }
        stage.setTitle("Abaco 1.0");
        stage.setMaximized(true);
      //  stage.setOpacity(0.9);
             
       // showavanceproceso();      
        stage.show();
       // leerbdpedidosabaco();  
      /*  stage.setOnHiding(new EventHandler<WindowEvent>() {

         @Override
         public void handle(WindowEvent event) {
             Platform.runLater(new Runnable() {

                 @Override
                 public void run() {
                       System.out.println("Cerrando alguna conexion email");
                       try
                       {  
                         
                      //     idleManager.stop();
                         if (folder != null && folder.isOpen()) { folder.close(true); }
                          if (store != null) { store.close(); }
                            t.interrupt();
                       }catch(Exception e2){
                           e2.printStackTrace();
                       }
                     System.out.println("Application Closed by click to Close Button(X)");
                     System.exit(0);
                 }
             });
         }
     }); */  
        
         }
    }
//getter y setter variables static

    public static Organizacion getDatosempresa() {
        return s_organizacion;
    }
    
       
        
        
        //get setter observable lis models
         public static ObservableList getM_oDatosEmpresa() {
        return ol_empresa;
    }
  
 
   
   

   
    public static ObservableList getM_oDatosProducto() {
        return ol_producto;
    }

    public static ObservableList getM_oDatosCategoria() {
        return ol_categoria;
    }

    public static ObservableList getM_oDatosTerceros() {
        return ol_genpersonas;
    }

    public static void setM_oDatosTerceros(ObservableList m_oDatosTerceros) {
        AbacoApp.ol_genpersonas = m_oDatosTerceros;
    }

    public static ObservableList getM_oDatosProveedores() {
        return ol_datosproveedores;
    }

    public static void setM_oDatosProveedores(ObservableList m_oDatosProveedores) {
        AbacoApp.ol_datosproveedores = m_oDatosProveedores;
    }

    public static ObservableList getM_oDatosEmpleados() {
        return ol_empleados;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
      public static void registerDataLoadingTask(Runnable task) {
        dataLoadingTasks.add(task);
    }
      public static  void LoadDataInit()
    {
        getM_oDatosCategoria().clear();
        getM_oDatosEmpresa().clear();
        getM_oDatosProducto().clear();
        getM_oDatosTerceros().clear();
        for (Runnable task: dataLoadingTasks) 
        {
            Platform.runLater(task);
      
        }
    }
      public static void  showModalMessage(Node message) {
        modalDimmer.getChildren().add(message);
        modalDimmer.setOpacity(0);
        modalDimmer.setVisible(true);
        modalDimmer.setCache(true);
        TimelineBuilder.create().keyFrames(
            new KeyFrame(Duration.seconds(1), 
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        modalDimmer.setCache(false);
                    }
                },
                new KeyValue(modalDimmer.opacityProperty(),1, Interpolator.EASE_BOTH)
        )).build().play();
    }
      public static void  hideModalMessage() {
        modalDimmer.setCache(true);
        TimelineBuilder.create().keyFrames(
            new KeyFrame(Duration.seconds(1), 
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        modalDimmer.setCache(false);
                        modalDimmer.setVisible(false);
                        modalDimmer.getChildren().clear();
                    }
                },
                new KeyValue(modalDimmer.opacityProperty(),0, Interpolator.EASE_BOTH)
        )).build().play();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        AbacoApp.scene = scene;
    }
public static void addMask(final TextField tf, final String mask) {
    tf.setText(mask);
    addTextLimiter(tf, mask.length());

    tf.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            String value = stripMask(tf.getText(), mask);
            tf.setText(merge(value, mask));
        }
    });

    tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(final KeyEvent e) {
            int caretPosition = tf.getCaretPosition();
            if (caretPosition < mask.length()-1 && mask.charAt(caretPosition) != ' ' && e.getCode() != KeyCode.BACK_SPACE && e.getCode() != KeyCode.LEFT) {
                tf.positionCaret(caretPosition + 1);
            }
        }
    });
}

static String merge(final String value, final String mask) {
    final StringBuilder sb = new StringBuilder(mask);
    int k = 0;
    for (int i = 0; i < mask.length(); i++) {
        if (mask.charAt(i) == ' ' && k < value.length()) {
            sb.setCharAt(i, value.charAt(k));
            k++;
        }
    }
    return sb.toString();
}

static String stripMask(String text, final String mask) {
    final Set<String> maskChars = new HashSet<>();
    for (int i = 0; i < mask.length(); i++) {
        char c = mask.charAt(i);
        if (c != ' ') {
            maskChars.add(String.valueOf(c));
        }
    }
    for (String c : maskChars) {
        text = text.replace(c, "");
    }
    return text;
}

public static void addTextLimiter(final TextField tf, final int maxLength) {
    tf.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        }
    });
}
 public static void load_all_parameters_and_lists() throws ParseException, NoSuchMethodException, IllegalAccessException
  {
      
      counter=new AtomicInteger(0);
      task_4=new Task<Void>() {
            @Override
            protected Void call() throws Exception {
               try{
             
             organizacionControllerClient.load_organizacion();  
            
             
             li_conpuc.clear();
             li_conpuc.addAll(conPucControllerClient.li_conpuc(null));
             l_unidadmedida.clear();
             l_unidadmedida.addAll(generalControllerClient.load_unidadesmedida());
             loadChoiceBoxGeneral.Load_GenUnidadesMedida();  
             counter.incrementAndGet();
             cprogreso++;
             updateProgress(1, 7);
              li_categoria.clear();
              li_categoria = categoriaControllerClient.getRecords(null);
              
             counter.incrementAndGet();
             cprogreso++;
                updateProgress(2, 7);
                li_genmunicipios.clear();
                li_genmunicipios=genMunicipiosControllerClient.getRecords(null);
                li_genpersonas = genPersonasControllerClient.getRecords(null);
                li_empleados = genPersonasControllerClient.getEmpleados(null);
                        
                       
                         counter.incrementAndGet();
             cprogreso++;
                updateProgress(3, 7);
                       
                         ol_empleados.clear();
                         ol_empleados.addAll((Object)li_empleados);
                          counter.incrementAndGet();
                          cprogreso++;
                           updateProgress(4, 7);
                           li_producto.clear();
                          li_producto = productoControllerClient.getRecords(null);
                            counter.incrementAndGet();
                          cprogreso++;
                           updateProgress(5, 7);
                             li_proveedores.clear();
                        li_proveedores =proveedoresControllerClient.getRecords(null);
                      
                         counter.incrementAndGet();
                          cprogreso++;
                           updateProgress(6, 7);
                           
                            System.out.println("size kardex->"+li_kardex.size());
                            consecutivoNoFacturaControllerClient.getRecords();
                            li_kardex.clear();
                            li_kardex =kardexControllerClient.getRecords();
                             
                              List<Kardex> l_kardex= li_kardex.stream().filter(distinctByKey(b ->b.getProducto())).collect(Collectors.toList());//.distinct().collect(Collectors.toList());
                         li_kardex=l_kardex;
                          loaddatosorganizacion_toobar();
                         counter.incrementAndGet();
                          cprogreso++;
                              updateProgress(7, 7);
                            
                        //int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
		        //System.out.println("Hay "+dias+" dias de diferencia");
                      
               }catch(Exception e)
               {
                   e.printStackTrace();
               }
                  return null;
            }
        };
          thimport_4 = new Thread(task_4);
                
  
        thimport_4.setDaemon(true);
        thimport_4.start();
 
  }
public static int tproceso()
{
      
      return counter.get();
}   
 private void showlogin() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
   {
      pa_login=null;
      pa_login = (Parent) clzLogin.getMethod("createContent").invoke(appClassLogin);
      sc_Login=null;
      sc_Login=new Scene(pa_login, Color.TRANSPARENT);
     // sc_Login.getStylesheets().add("/abaco/SofackarStylesCommon.css"); 
     
       
        if (stageLogin.isShowing())
       {
           stageLogin.close();
       }      
            //stageLogin.setOpacity(0.85);
         stageLogin.sizeToScene();
                
              
                //center stage on screen
                stageLogin.centerOnScreen();
                stageLogin.setTitle("Login del Sistema");
                stageLogin.setScene(sc_Login);
                 //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stageLogin.showAndWait();
   }
 public  void loadpopover()
   {
        // create and setup list popover
       
        final SamplePopoverTreeList rootPage = new SamplePopoverTreeList(Soluciones.ROOT,pageBrowser);
        listButton.setOnMouseClicked((MouseEvent e) -> {
            if (sampleListPopover.isVisible()) {
                sampleListPopover.hide();
            } else {
                sampleListPopover.clearPages();
                sampleListPopover.pushPage(rootPage);
                sampleListPopover.show(() -> {
                    listButton.setSelected(false);
                });
            }
        });
        
   }
 public static void loaddatosorganizacion_toobar() throws IOException
    {
        
       if(s_organizacion!=null)
           {
               if(s_organizacion.getLogo()!=null)
               {
               InputStream in = new ByteArrayInputStream(s_organizacion.getLogo());
               bi = ImageIO.read(in);
               wi=new WritableImage(1,1);
               wi=SwingFXUtils.toFXImage(bi, wi);
              //img=new ImageView();
               Loadimg=wi;
               I_ImagenLogo.setImage(Loadimg);
               }
               T_InfEmpresa.setText(s_organizacion.getNombre()+" Nit: "+s_organizacion.getNit());
           } 
    } 
 
 public synchronized static void leerbdpedidosabaco()
 {
  
    //Platform.runLater(new Runnable() {
   // @Override
   // public void run() {
   Runnable task = () -> {     
             
             String os = System.getProperty("os.name"); 
        try 
        {
          System.out.println("->1");
          Properties props = System.getProperties();
          props.setProperty("mail.store.protocol", "imaps");
          props.put("mail.event.scope", "session"); // or "application"
          props.put("mail.event.executor", es);
          session = Session.getDefaultInstance(props, null);
          // idleManager = new IdleManager(session, es);
          store = session.getStore("imaps");
          System.out.println("->2");
          store.connect("imap.gmail.com",AbacoApp.s_organizacion.getEmailpedidos(),AbacoApp.s_organizacion.getPasswordemailpedidos());
          System.out.println("->3");
          folder = (IMAPFolder) store.getFolder("inbox"); //This works for both email account
          if(!folder.isOpen())
          folder.open(Folder.READ_WRITE);
          Message[] messages = folder.getMessages();
           
          System.out.println("No of Messages : " + folder.getMessageCount());
          System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
          System.out.println(messages.length);
            System.out.println("->3");
            avance_progress=0;
          for (int i=0; i < messages.length;i++) 
          {

            System.out.println("*****************************************************************************");
            System.out.println("MESSAGE " + (i + 1) + ":");
            Message msg =  messages[i];
            subject = msg.getSubject();
            System.out.println("Subject: " + subject);
            System.out.println("From: " + msg.getFrom()[0]);
            System.out.println("To: "+msg.getAllRecipients()[0]);
            System.out.println("Date: "+msg.getReceivedDate());
            System.out.println("Size: "+msg.getSize());
            System.out.println(msg.getFlags());
            System.out.println("Body: \n"+ msg.getContent());
            System.out.println(msg.getContentType());
            Multipart multiPart = (Multipart) msg.getContent();
            for (int j = 0; j < multiPart.getCount(); j++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
              String destFilePath = "";
            if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
            {
               destFilePath = "/home/adminlinux/abaco/" + part.getFileName();
            }
          else
             {
               destFilePath = "\\home\\adminlinux\\abaco\\" + part.getFileName();  
             }
            FileOutputStream output=null;
           output = new FileOutputStream(destFilePath);
          InputStream input = part.getInputStream();
          byte[] buffer = new byte[4096];
          int byteRead;
          while ((byteRead = input.read(buffer)) != -1) 
          {
           output.write(buffer, 0, byteRead);
          }
          output.close();
          input.close();
       }
            
          }
          almacenarbdfromjsonpedidos();
       //  avance_progress=avance_progress+(1.0/messages.length);
       //    pb.setProgress(avance_progress);
        //   pi.setProgress(avance_progress);
        }
        }
        catch(Exception e)
        { 
          e.printStackTrace();
          if (folder != null && folder.isOpen()) { try {
              folder.close(true);
              } catch (MessagingException ex) {
                  Logger.getLogger(AbacoApp.class.getName()).log(Level.SEVERE, null, ex);
              }
}
          if (store != null) {try {
              store.close();
              } catch (MessagingException ex) {
                  Logger.getLogger(AbacoApp.class.getName()).log(Level.SEVERE, null, ex);
              }
}
             
                     
                
        }
               if (folder != null && folder.isOpen()) { try {
                   folder.close(true);
                 } catch (MessagingException ex) {
                     Logger.getLogger(AbacoApp.class.getName()).log(Level.SEVERE, null, ex);
                 }
}
          if (store != null) {try {
              store.close();
                 } catch (MessagingException ex) {
                     Logger.getLogger(AbacoApp.class.getName()).log(Level.SEVERE, null, ex);
                 }
}  
          System.out.println("Cerrada: conexion envia email");
           avance_progress=0.5;
        pb.setProgress(avance_progress);
           pi.setProgress(avance_progress);
           try
           {
            borraremaillbdpedidos();
           }catch(Exception e)
           {
               e.printStackTrace();
           }
           avance_progress=0.75;
           pb.setProgress(avance_progress);
           pi.setProgress(avance_progress);
           actualizarlistadepedidos();
           pb.setProgress(1);
           pi.setProgress(1);
          gp.setVisible(false);
          try{
          listenernuevosemails();
          }catch(Exception e)
          {
              e.printStackTrace();
          }
    };
   //task.run();
Thread thread = new Thread(task);
thread.setDaemon(true);
thread.start();
           
                      
 }
 public   static void borraremaillbdpedidos() throws NoSuchProviderException, MessagingException, IOException
 {
     IMAPFolder folder2 = null;
     Session sesiondeleteemail=null;
     Store storedeleteemail=null;
        try 
        {
          Properties props = System.getProperties();
          props.setProperty("mail.store.protocol", "imaps");
         // props.put("mail.event.scope", "session"); // or "application"
        // // props.put("mail.event.executor", es);
          sesiondeleteemail = Session.getDefaultInstance(props, null);
          storedeleteemail = sesiondeleteemail.getStore(props.getProperty("mail.store.protocol"));
          if(!storedeleteemail.isConnected())
          {
                storedeleteemail.connect("imap.gmail.com",AbacoApp.s_organizacion.getEmailpedidos(),AbacoApp.s_organizacion.getPasswordemailpedidos());
      
          }
        
         // store.connect("imap.gmail.com",AbacoApp.s_organizacion.getEmailbdproductos(),AbacoApp.s_organizacion.getPasswordemailbdproductos());
         // folder2 = (IMAPFolder) store.getFolder("[Gmail]/inbox"); // This doesn't work for other email account
         // folder2 = (IMAPFolder)store.getFolder("INBOX.Trash"); //This works for both email account
         folder2 = (IMAPFolder)storedeleteemail.getDefaultFolder(); 
     //    if(!folder.isOpen())
         //folder.open(Folder.READ_WRITE);
         
       
           Folder [] folders = folder2.list();
           System.out.println("Total de folderes:"+folders.length);
	   System.out.println("Nombre de folder:"+folders[0].getFullName());
            System.out.println("Nombre de folder:"+folders[1].getFullName());
		long messgCnt = 0;
		for(Folder currFolder: folders) {
 
			try {
 
				if ((currFolder.getType() & Folder.HOLDS_MESSAGES) == 0) {
					continue; // Skip this folder type
				}
                               
				currFolder.open(Folder.READ_WRITE);
				Message [] messages = currFolder.getMessages();
 
				// Mark all the messages for delete
				for (Message message: messages) {
					try {
                                                System.out.println("Mensaje contenttype:"+message.getContentType());
				                System.out.println("Mensaje subjet:"+message.getSubject());
						System.out.println("Mensajes before size:"+messages.length);
                                                System.out.println("Mensajes fecha:"+message.getSentDate());
                                                message.setFlag(Flags.Flag.DELETED, true);
						MimeMessage currMessage = (MimeMessage) message;
						messages = currFolder.expunge(); // Confirm delete for all the messages on the current folder
						messgCnt += messages.length;
                                                  System.out.println("Mensajes after size:"+messages.length);
						
					}  catch (MessagingException messgExp) {
					}
				}
                                
				currFolder.close(false);
                               
			} catch (MessagingException messgExp) {
                    			}
 
		}
        
          
        }
       catch(Exception e)
        {
           e.printStackTrace();
          if (folder2 != null && folder2.isOpen()) { folder2.close(true); }
          if (storedeleteemail != null) { store.close(); }
           
                          
        }
         if (folder2 != null && folder2.isOpen()) { folder2.close(true); }
          if (store != null) { storedeleteemail.close(); }
          System.out.println("Cerrada: conexion borrando email");
        
       
    }
public static void almacenarbdfromjsonpedidos() throws FileNotFoundException, IOException, ParseException
{
            File yourFile = new File("\\home\\adminlinux\\abaco\\pedidos.json");
            FileInputStream stream = new FileInputStream(yourFile);
            String jString = null;
            FileChannel fc = stream.getChannel();
            long size = fc.size();
            ByteBuffer cb = null;
            try {
              cb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size);
              //  MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                /* Instead of using default, pass in a decoder. */
                jString = Charset.defaultCharset().decode(cb).toString();
                fc.close();
                    
               

      
     

      
     


                
                
            }
            finally {
                System.out.println("cerrando stream");
                System.out.println("cerrando fc");
                try { fc.close(); } catch (Exception ex) { }
                try { stream.close(); } catch (Exception ex) { }
                  closeDirectBuffer(cb);
              
                 System.out.println("eliminando archivo.....");
               if (yourFile.delete())
                 System.out.println("El fichero ha sido borrado satisfactoriamente");
              else
            System.err.println(
        "I cannot find '" + yourFile + "' ('" + yourFile.getAbsolutePath() + "')");
                
            }
            JSONArray jsonarray = new JSONArray(jString);
            Producto p=null;
            System.out.println("leyendo JSon");
           
                for (int i = 0; i < jsonarray.length(); i++) {
                    p=new Producto();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    int cantidadproducto = jsonobject.getInt("cantidad_detallespedido");
                    BigDecimal valor_uni_producto = jsonobject.getBigDecimal("valor_uni_detallespedido");
                    BigDecimal valot_total_producto = jsonobject.getBigDecimal("valor_total_detallespedido");
                    String codigo_producto=jsonobject.getString("codigo_producto");
                    Date fecha=abaco.util.UtilDate.colocarfechacomplicada(jsonobject.getString("fecha_pedido"));
                    int cantidadpedido = jsonobject.getInt("cantidad_pedido");
                    BigDecimal total_pedido = jsonobject.getBigDecimal("total_pedido");
                    String noident=jsonobject.getString("noident");
                    Long id_pedido=jsonobject.getLong("id_pedido");
                    String nombre=jsonobject.getString("nombre");
                    String email=jsonobject.getString("email");
                    String celular=jsonobject.getString("celular");
                    String dir=jsonobject.getString("dir");
                    if(i==0)
                    {
                    AbacoApp.s_genpersonas=AbacoApp.genPersonasControllerClient.findbyident(noident);
                    if(AbacoApp.s_genpersonas==null)
                    {
                        AbacoApp.s_genpersonas=new Personas();
                        AbacoApp.s_genpersonas.setNo_ident(noident);
                    }
                    AbacoApp.s_genpersonas.setNombre(nombre);
                    AbacoApp.s_genpersonas.setEmail(email);
                    AbacoApp.s_genpersonas.setCelular(celular);
                    AbacoApp.s_genpersonas.setDir1(dir);
                    if(AbacoApp.s_genpersonas.getId()!=null)
                    {
                        AbacoApp.genPersonasControllerClient.update();
                    }
                    else
                    {
                        AbacoApp.genPersonasControllerClient.create();
                    }
                    pedidosControllerclient.getfindbynopedido(id_pedido.toString()+noident);
                    if(AbacoApp.pedidos==null)
                    {
                        AbacoApp.pedidos=new Pedidos();
                        AbacoApp.pedidos.setNo_pedido(id_pedido.toString()+noident);
                        AbacoApp.pedidos.setCliente(AbacoApp.s_genpersonas);
                     }
                    else
                    {
                        pedidos.getLi_detallespedido().clear();
                    }
                    pedidos.setCantidad(cantidadpedido);
                    pedidos.setEstado(0);
                    pedidos.setFecha(fecha);
                    pedidos.setTotal(total_pedido);
                    
                    if(pedidos.getId()==null)
                    {
                      pedidosControllerclient.create();
                    }
                                           
                    }
                        s_producto=productoControllerClient.finByCodigo2(codigo_producto);
                        DetallesPedido dp=new DetallesPedido();
                        dp.setPedidos(pedidos);
                        dp.setProducto(s_producto);
                        dp.setCantidad(cantidadproducto);
                        dp.setValor_uni(valor_uni_producto);
                        dp.setValor_total(valot_total_producto);
                        
                        pedidos.getLi_detallespedido().add(dp);
                        pedidosControllerclient.update();
                }
                    
                
}
public static void actualizarlistadepedidos()
{
     System.out.println("Actualiando botones pedidos...");
    pedidosControllerclient.getrecordsporestado(0);
                    cantidadpedidosnuevos=li_pedidos.size();
             
                          if(li_pedidos.size()>0)
                          {
                             
                             
                              im_alarmaoffpedidosnuevos.setImage(new Image("/images/alarmaon.png"));
                    
                             
                          }
                          else
                          {
                              
                              im_alarmaoffpedidosnuevos.setImage(new Image("/images/alarmaoff.png"));
                          }
                           Platform.runLater(() -> bu_cantidadpedidosnuevos.setText("+"+String.valueOf(cantidadpedidosnuevos)+" pedidos nuevos"));  
                        //  bu_cantidadpedidosnuevos.setText("+"+String.valueOf(cantidadpedidosnuevos)+" pedidos nuevos");
                           pedidosControllerclient.getrecordsporestado(1);
                           cantidadpedidosconfirmados=li_pedidos.size();
                          if(li_pedidos.size()>0)
                          {
                              
                               im_alarmaoffpedidosconfirmados.setImage(new Image("/images/alarma2on.png"));
                          }
                          else
                          {
                            
                           im_alarmaoffpedidosconfirmados.setImage(new Image("/images/alarmaoff.png"));
                      
                          }
                      Platform.runLater(() -> bu_cantidadpedidosconfirmados.setText("+"+String.valueOf(cantidadpedidosconfirmados)+" pedidos confirmados"));  
                                pedidosControllerclient.getrecordsporestado(2);
                          cantidadpedidosdespachados=li_pedidos.size();
                          
                          if(li_pedidos.size()>0)
                          {
                              
                             im_alarmaoffpedidosdespachados.setImage(new Image("/images/alarma3on.png"));
                          }
                          else
                          {
                           im_alarmaoffpedidosdespachados.setImage(new Image("/images/alarmaoff.png"));
                            }
                        Platform.runLater(() -> bu_cantidadpedidosdespachados.setText("+"+String.valueOf(cantidadpedidosdespachados)+" pedidos despachados"));  
                      
}

 private static void showavanceproceso() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
 {  
      Platform.runLater(() ->  gp.getChildren().clear());  
                       
 
  
  pb=null;
  pi=null;
  pb=new ProgressBar(0);
  pi=new ProgressIndicator(0);
   Platform.runLater(() ->  gp.addRow(0, pb,pi));  
 
  //popover.setArrowLocation(PopOver.ArrowLocation.LEFT_CENTER);
 // popover.setCornerRadius(4);
 //  popover.setDetached(true);
  //popover.centerOnScreen();
 // popover.setDetachedTitle("     Descargando y almacenando pedidos..");
//  double clickX = bu_cantidadpedidosconfirmados.localToScreen(bu_cantidadpedidosconfirmados.getBoundsInLocal()).getMinX();
//  double clickY = (bu_cantidadpedidosconfirmados.localToScreen(bu_cantidadpedidosconfirmados.getBoundsInLocal()).getMinY() +
               // bu_cantidadpedidosconfirmados.localToScreen(bu_cantidadpedidosconfirmados.getBoundsInLocal()).getMaxY()) / 2;
 //System.out.println("pos X->"+clickX);
// System.out.println("pos Y->"+clickY);
      //  popover.show(stage);
       // Show on left
//        popOver.setX(clickX - popOver.getWidth());
//        popOver.setY(clickY - popOver.getHeight() / 2);
        // Show on right
    //  popover.setX(clickX + 120.0);
    //    popover.setY(clickY - popover.getHeight() / 2);
  gp.setAlignment(Pos.CENTER);
  
 }  
 private void showpedidosnuevos() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  
    appclasspedido="abaco.soluciones.procesosadministrativos.AdminPedidosNuevos";
    clzpedido = Class.forName(appclasspedido);
    apppedido= clzpedido.newInstance();
    parentpedido=null;
    parentpedido= (Parent) clzpedido.getMethod("createContent").invoke(apppedido);
    sc_pedido=null;
    sc_pedido=new Scene(parentpedido, Color.TRANSPARENT);
      
        if (stagepedido.isShowing())
       {
           stagepedido.close();
       }      
        
        
//set scene to stage
                stagepedido.sizeToScene();
                
              
                //center stage on screen
                stagepedido.centerOnScreen();
                stagepedido.setScene(sc_pedido);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stagepedido.show();
  }  
 public static void listenernuevosemails() throws NoSuchProviderException, MessagingException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
 {
     
      String os = System.getProperty("os.name"); 
        
          Properties props = System.getProperties();
          props.setProperty("mail.store.protocol", "imaps");
          props.put("mail.event.scope", "session"); // or "application"
          props.put("mail.event.executor", es);
          props.put("mail.imaps.usesocketchannels", "true");
           session=null;
           session = Session.getDefaultInstance(props, null);
          idleManager = new IdleManager(session, es);
          store = session.getStore("imaps");
          store.connect("imap.gmail.com",AbacoApp.s_organizacion.getEmailpedidos(),AbacoApp.s_organizacion.getPasswordemailpedidos());

         // folder = (IMAPFolder) store.getFolder("[Gmail]/Spam"); // This doesn't work for other email account
          folder = (IMAPFolder) store.getFolder("inbox"); //This works for both email account
         
       
 
          if(!folder.isOpen())
          folder.open(Folder.READ_WRITE);
          folder.addMessageCountListener(new MessageCountAdapter() {
            public void messagesAdded(MessageCountEvent ev) {
                try {
                    showavanceproceso();
                } catch (Exception ex) {
            ex.printStackTrace();
                } 
              gp.setVisible(true);
                Folder folder = (Folder)ev.getSource();
                Message[] msgs = ev.getMessages();
                System.out.println("Folder: " + folder +
                    " got " + msgs.length + " new messages");
                     for (int i=0; i < msgs.length;i++) 
          {

                    try {
                        Message msg =  msgs[i];
                        
                        Multipart multiPart = null;
                        
                        try {
                            multiPart = (Multipart) msg.getContent();
                        } catch (MessagingException ex) {
                               ex.printStackTrace();    
                        }
                   
                        
                        
                        for (int j = 0; j < multiPart.getCount(); j++) {
                            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                
                                String destFilePath = "";
                                if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
                                {
                                 destFilePath = "/home/adminlinux/abaco/" + part.getFileName();
                                }
                                else
                                {
                                 destFilePath = "\\home\\adminlinux\\abaco\\" + part.getFileName();  
                                }
                                FileOutputStream output = new FileOutputStream(destFilePath);
                                
                                InputStream input = part.getInputStream();
                                
                                byte[] buffer = new byte[4096];
                                
                                int byteRead;
                                
                                while ((byteRead = input.read(buffer)) != -1) {
                                    output.write(buffer, 0, byteRead);
                                }
                                output.close();
                            }
                        }         
                        almacenarbdfromjsonpedidos();
                    } catch (Exception ex) {
                ex.printStackTrace();    } 
        }
                      avance_progress=0.5;
                      pb.setProgress(avance_progress);
                       pi.setProgress(avance_progress);
           try
           {
            borraremaillbdpedidos();
           }catch(Exception e)
           {
               e.printStackTrace();
           }
           avance_progress=0.75;
           pb.setProgress(avance_progress);
           pi.setProgress(avance_progress);
           actualizarlistadepedidos();
           pb.setProgress(1);
           pi.setProgress(1);
          gp.setVisible(false);
            }
        });
        t = new Thread(new Runnable() {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                //try {
                   // System.out.println("hi, interrupted = " + Thread.currentThread().isInterrupted());
                try {
                    folder.getMessageCount();
                } catch (MessagingException ex) {
                    Logger.getLogger(AbacoApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    idleManager.watch(folder);
                      Thread.currentThread().sleep(700);
                    } catch (MessagingException | InterruptedException ex) {
                    ex.printStackTrace();
                    }
               
            }
          
        }
       
    });
          // idleManager.watch(folder);
      t.start();    //  folder.idle();
        
   
        }
       

  private static void closeDirectBuffer(ByteBuffer cb) {
       if (cb==null || !cb.isDirect()) return;

       // we could use this type cast and call functions without reflection code,
       // but static import from sun.* package is risky for non-SUN virtual machine.
       //try { ((sun.nio.ch.DirectBuffer)cb).cleaner().clean(); } catch (Exception ex) { }
       try {
           Method cleaner = cb.getClass().getMethod("cleaner");
           cleaner.setAccessible(true);
           Method clean = Class.forName("sun.misc.Cleaner").getMethod("clean");
           clean.setAccessible(true);
           clean.invoke(cleaner.invoke(cb));
       } catch(Exception ex) { }
       cb = null;
}  
      
}
