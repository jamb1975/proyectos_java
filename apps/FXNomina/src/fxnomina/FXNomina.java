package fxnomina;

import java.awt.image.BufferedImage;
import control.SearchBox;
import control.TitledToolBar;
import generated.Soluciones;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import modelo.ActividadesEconomicas;
import modelo.CargosEntidad;
import modelo.Empleados;
import modelo.FactorHorasExtras;
import modelo.GenDepartamentos;
import modelo.GenEapb;
import modelo.GenMunicipios;
import modelo.GenPersonas;
import modelo.GenSexo;
import modelo.GenTiposDocumentos;
import modelo.Nomina;
import modelo.NominaEmpleados;
import modelo.Novedades;
import modelo.ParametrosNomina;
import modelo.Solucion;
import modelo.UsuarioSoluciones;
import modelo.Usuarios;
import fxnomina.HomePage.HomePageRow;
import fxnomina.HomePage.RowType;
import control.PopoverMenu;
import controlador.ActividadesEconomicasControllerClient;
import controlador.CargosEntidadControllerClient;
import controlador.ConsecutivosContabilidadControllerClient;
import controlador.EmpleadosControllerClient;
import controlador.FactorHorasExtrasControllerClient;
import controlador.GenEapbControllerClient;
import controlador.GenPersonasControllerClient;
import controlador.GenUnidadeOrganizacionControllerClient;
import controlador.GeneralControllerClient;
import  controlador.NominaControllerClient;
import  controlador.NominaEmpleadosControllerClient;
import  controlador.NovedadesControllerClient;
import  controlador.ParametrosNominaControllerClient;
import  general.LoadChoiceBoxGeneral;
import modelo.ConsecutivosContabilidad;
import modelo.GenUnidadesOrganizacion;

/**
 * Ensemble Application
 */
public class FXNomina extends Application {
    public static String hojaestilos="/fxnomina/SofackarStylesCommon.css";
    private static ObservableList<HomePageRow> ol_homepage=FXCollections.observableArrayList();
    public static String nombresolucion = "7";
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String OSArch = System.getProperty("os.arch").toLowerCase();
    private static String OSVersion = System.getProperty("os.version").toLowerCase();
    private static final ProgressIndicator p5 = new ProgressIndicator();
    private static Thread thimport_77;
    private TextField buscarsolucion=new TextField();
    //Variables de la aplicacion que intervienen en los procesos de historias clinica
    public static GenUnidadesOrganizacion genUnidadesOrganizacion = new GenUnidadesOrganizacion();
    public static Usuarios usuarios = new Usuarios();
    public static GenEapb s_genEapb = new GenEapb();
    public static GenEapb aseguradora = new GenEapb();
    public static GenMunicipios s_genMunicipios = new GenMunicipios();
      public static Empleados empleados = new Empleados();
    public static ActividadesEconomicas s_ActividadesEconomicas = new ActividadesEconomicas();
    public static CargosEntidad cargosEntidad = new CargosEntidad();
    public static FactorHorasExtras factorHorasExtras = new FactorHorasExtras();
    public static Solucion solucion = new Solucion();
    public static ConsecutivosContabilidad consecutivosContabilidad = new ConsecutivosContabilidad();
    public static ParametrosNomina parametrosNomina=new ParametrosNomina();
    public static Novedades novedades=new Novedades();
    public static NominaEmpleados nominaEmpleados=new NominaEmpleados();
    public static Nomina nomina=new Nomina();
    public static GenTiposDocumentos genTiposDocumentos=new GenTiposDocumentos();
    public static GenSexo genSexo=new GenSexo();
    public static GenPersonas genPersonas=new GenPersonas();
    public static GenEapb genpension=new GenEapb();
    private static GenEapb genarl=new GenEapb();
    public static String  rh=new String();
    public static String pfechacita="";
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
    private static Scene scene;
    private Pane root;
    private TitledToolBar toolBar;
    public static Button backButton;
    public static Button bu_teclado;
    public static Button forwardButton;
    public static Button homeButton;
    public static Button updateButton;
    public static ToggleButton listButton;
    private ToggleButton searchButton;
    private SearchBox SearchBox = new SearchBox();
    public static PageBrowser pageBrowser;
    private PopoverMenu sampleListPopover;
    private SearchPopover searchPopover;
    private MenuBar menuBar;
    private static Text T_InfEmpresa;
    private static WritableImage wi;
    private static BufferedImage bi;
    private static Image Loadimg;
    private static ImageView imgview;
    public static int cprogreso = 0;
    private static AtomicInteger counter;
    public static Task<Void> task_4;
    public static Thread thimport_4;
    public static Task<Void> task_5;
    public static Thread thimport_5;

    //Clases static que se utilizan en las demas soluciones como datos generales
    //  private static final DatosEmpresa datosempresa=new DatosEmpresa();
    static {
        System.setProperty("java.net.useSystemProxies", "true");
        Logger.getLogger(FXNomina.class.getName()).log(Level.FINER, "IS_IPHONE = {0}", IS_IPHONE);
        Logger.getLogger(FXNomina.class.getName()).finer("IS_MAC = " + IS_MAC);
        Logger.getLogger(FXNomina.class.getName()).finer("IS_ANDROID = " + IS_ANDROID);
        Logger.getLogger(FXNomina.class.getName()).finer("IS_EMBEDDED = " + IS_EMBEDDED);
        Logger.getLogger(FXNomina.class.getName()).finer("IS_DESKTOP = " + IS_DESKTOP);
    }
    public static final List<GenTiposDocumentos> l_gentiposdocumentos = new ArrayList<GenTiposDocumentos>();
    private static final List<GenSexo> l_gensexo = new ArrayList<GenSexo>();
    private static final List<GenMunicipios> l_genmunicipios = new ArrayList<GenMunicipios>();
    private static final List<GenDepartamentos> l_gendepartamentos = new ArrayList<GenDepartamentos>();
    public static final List<GenEapb> l_eapb = new ArrayList<GenEapb>();
    public static List<ActividadesEconomicas> li_actividadeseconomicas = new ArrayList<>();
    public static List<Empleados> li_empleados = new ArrayList<>();
    public static List<CargosEntidad> li_CargosEntidads = new ArrayList<>();
    public static List<FactorHorasExtras> li_facFactorHorasExtras = new ArrayList<>();
    public static List<ParametrosNomina> li_parametrosNomina=new ArrayList<>();
    public static List<Novedades> li_novedades=new ArrayList<>();
    public static List<Nomina> li_nomina=new ArrayList<>();
    public static List<GenPersonas> li_GenPersonas=new ArrayList<>();
    public static List<String> li_rh=new ArrayList<>();
    public static GeneralControllerClient generalControllerClient = new GeneralControllerClient();
    public static GenEapbControllerClient genEapbControllerClient = new GenEapbControllerClient();
    public static GenUnidadeOrganizacionControllerClient genUnidadeOrganizacionControllerClient=new GenUnidadeOrganizacionControllerClient();
    public static CargosEntidadControllerClient cargosEntidadControllerClient = new CargosEntidadControllerClient();
    public static LoadChoiceBoxGeneral loadChoiceBoxGeneral;
    public static ActividadesEconomicasControllerClient actividadesEconomicasControllerClient = new ActividadesEconomicasControllerClient();
    public static EmpleadosControllerClient empleadosControllerClient = new EmpleadosControllerClient();
    public static FactorHorasExtrasControllerClient factorHorasExtrasControllerClient = new FactorHorasExtrasControllerClient();
    public static ConsecutivosContabilidadControllerClient consecutivosContabilidadControllerClient = new ConsecutivosContabilidadControllerClient();
    public static ParametrosNominaControllerClient parametrosNominaControllerClient=new ParametrosNominaControllerClient();
    public static NovedadesControllerClient novedadesControllerClient=new NovedadesControllerClient();
    public static NominaControllerClient nominaControllerClient=new NominaControllerClient();
    public static NominaEmpleadosControllerClient nominaEmpleadosControllerClient=new NominaEmpleadosControllerClient();
    public static GenPersonasControllerClient genPersonasControllerClient=new GenPersonasControllerClient();
    private static final Vector<Runnable> dataLoadingTasks = new Vector<Runnable>();
    private static FXNomina sofackarmain;
    public static StackPane modalDimmer;
    public static ImageView I_ImagenLogo = new ImageView();
    public static ImageView LogoEnterprise = new ImageView();
    public static Boolean checkproceso = new Boolean(false);
    public static HashMap<String, Solucion> hm_usuariosoluciones = new HashMap<>();
    public static Scene sc_Login = null;
    public static boolean Bo_LoginCorrecto;
    public static List<UsuarioSoluciones> Li_UsuarioSoluciones = new ArrayList<>();
    private String appPathLogin;
    private Class clzLogin;
    private Object appClassLogin;
    public static Stage stageLogin = new Stage(StageStyle.DECORATED);
    public static Stage stageProgreso = new Stage(StageStyle.DECORATED);
    private Parent pa_login;
    private SearchBox searchBox = new SearchBox();
    KeyCombination kf = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
    Runnable task = () ->  pageBrowser.goToPage("solucion:///Documentos/Facturar");
    public static final ChoiceBox cb_soluciones=new ChoiceBox();
      KeyCombination kc = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);
      Runnable task2 = () ->   pageBrowser.goToPage("solucion:///HistoriasClinicas/AsignarCitas");
      KeyCombination kp = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
      Runnable task3 = () ->   pageBrowser.goToPage("solucion:///HistoriasClinicas/AdminGenPersonas");
      KeyCombination ko = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
      Runnable task4 = () ->   pageBrowser.goToPage("solucion:///Contabilidad/FindFacturaClose");
      KeyCombination km = new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN);
      Runnable task5=() ->   pageBrowser.goToPage("solucion:///Contabilidad/AdminFacturas");
      KeyCombination kn = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
      Runnable task6= () ->   pageBrowser.goToPage("solucion:///HistoriasClinicas/AdminNotasEstudio");
      KeyCombination kd = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
      Runnable task7= () ->   pageBrowser.goToPage("solucion:///HistoriasClinicas/AdminConveniosEps");
      KeyCombination ki= new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);
      Runnable task8= () ->   pageBrowser.goToPage("solucion:///Contabilidad/InventarioServicios");
      KeyCombination kr= new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
      Runnable task9= () ->   pageBrowser.goToPage("solucion:///HistoriasClinicas/Rips");
      KeyCombination ke= new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
      Runnable task10= () ->   pageBrowser.goToPage("solucion:///Contabilidad/AdminFacturaItem");
      KeyCombination kt= new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
      Runnable task11= () ->   pageBrowser.goToPage("solucion:///Contabilidad/AdminMedicamentos");
      KeyCombination ks= new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
      Runnable task12= () ->   pageBrowser.goToPage("solucion:///Contabilidad/AdminInsumos");
      
      ;
    @Override
    public void init() throws Exception {
        load_all_parameters_and_lists();
         
         
        
        p5.setPrefSize(150, 150);
        p5.setStyle("-fx-foreground-color: #FFFFFF");

        root = new Pane() {
            @Override
            protected void layoutChildren() {
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
                pageBrowser.resize(w, h - toolBarHeight);
                pageBrowser.resize(w, h - toolBarHeight - menuHeight);

                sampleListPopover.autosize();
                Point2D listBtnBottomCenter = listButton.localToScene(listButton.getWidth() / 2, listButton.getHeight());
                sampleListPopover.setLayoutX((int) listBtnBottomCenter.getX() - 50);
                sampleListPopover.setLayoutY((int) listBtnBottomCenter.getY());
                /*   Point2D searchBoxBottomCenter = searchBox.localToScene(searchBox.getWidth()/2, searchBox.getHeight());
               searchPopover.setLayoutX((int)searchBoxBottomCenter.getX()-searchPopover.getLayoutBounds().getWidth()+100);
               searchPopover.setLayoutY((int)searchBoxBottomCenter.getY());*/
            }
        };
        // CREATE MENUBAR
        if (SHOW_MENU) {
            menuBar = new MenuBar();
            menuBar.setUseSystemMenuBar(true);
            menuBar.getStyleClass().add("menu-bar");
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
        toolBar.getStyleClass().add("toolbar");
        root.getChildren().add(toolBar);
        imgview = new ImageView("/images/previous.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        backButton = new Button("", imgview);
        backButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        backButton.setStyle("-fx-border-color:   white;\n" + "       -fx-border-width: 0;");

        imgview = new ImageView("/images/next.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        forwardButton = new Button("", imgview);
        forwardButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        forwardButton.setStyle("-fx-border-color:   white;\n"
                + "       -fx-border-width: 0;");
        imgview = null;
        imgview = new ImageView("/images/home.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        homeButton = new Button("", imgview);
        homeButton.setStyle("-fx-border-color:   white;\n"
                + "       -fx-border-width: 0;");
        imgview = null;
        imgview = new ImageView("/images/updatewhite.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        updateButton = new Button("", imgview);
        updateButton.setStyle("-fx-border-color:   white;\n"
                + "       -fx-border-width: 0;");
        updateButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        imgview = new ImageView("/images/teclado.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        bu_teclado = new Button("", imgview);
        bu_teclado.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        //bu_teclado.setStyle("-fx-border-color:   white;\n" + "       -fx-border-width: 0;");
        LogoEnterprise = new ImageView("/images/enterprise.png");
        //imgview.setFitHeight(20);
        //imgview.setFitWidth(20);
        HBox navButtons = new HBox(7, backButton, forwardButton, homeButton, updateButton,bu_teclado);
        imgview = null;
        imgview = new ImageView("/images/menu.png");
        imgview.setFitHeight(20);
        imgview.setFitWidth(20);
        listButton = new ToggleButton("", imgview);
       // listButton.setStyle("-fx-border-color:   white;\n" + "-fx-border-width: 0;-fx-background-color: transparent; -fx-border-insets: -2.1;\n"
        //        + "       -fx-b order-radius:7;");

        searchBox.setPrefWidth(200);
        //listButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        HBox.setMargin(listButton, new Insets(0, 0, 0, 7));

        searchButton = new ToggleButton();
        searchButton.setId("search");
        searchButton.setPrefSize(TOOL_BAR_BUTTON_SIZE, TOOL_BAR_BUTTON_SIZE);
        SearchBox.setPrefWidth(200);
        searchButton.setGraphic(new Region());
        T_InfEmpresa = new Text();
        T_InfEmpresa.textAlignmentProperty().setValue(TextAlignment.CENTER);
        T_InfEmpresa.setFont(Font.font("ARIAL", FontWeight.BOLD, 17));
        T_InfEmpresa.setFill(Color.WHITE);
        I_ImagenLogo.setFitHeight(30);
        I_ImagenLogo.setFitWidth(120);
        toolBar.addLeftItems(navButtons, listButton,buscarsolucion);
        toolBar.addRightItems(I_ImagenLogo, T_InfEmpresa);
        pageBrowser = new PageBrowser();
        pageBrowser.setStyle("-fx-background-image: url(\"images/enterprise.png\");\n"
                + "    -fx-background-position:left bottom ;\n"
                + "    -fx-background-repeat: no-repeat no-repeat ;\n"
                + "    -fx-background-size: auto;-fx-padding: 50px;\n"
                + "    -fx-border-insets: 50px;\n"
                + "    -fx-background-insets: 50px;  -fx-border-width: 50px;");

        toolBar.titleTextProperty().bind(pageBrowser.currentPageTitleProperty());
        root.getChildren().add(0, pageBrowser);
        pageBrowser.goHome();
        // wire nav buttons
        backButton.setOnAction((ActionEvent event) -> {
            pageBrowser.backward();
        });
        //backButton.disableProperty().bind(pageBrowser.backPossibleProperty().not());
        forwardButton.setOnAction((ActionEvent event) -> {
            pageBrowser.forward();
        });
        // forwardButton.disableProperty().bind(pageBrowser.forwardPossibleProperty().not());
        homeButton.setOnAction((ActionEvent event) -> {
            pageBrowser.goHome();
        });
        updateButton.setOnAction(e -> {
            try {

                load_all_parameters_and_lists();
                //showModalMessage();
            } catch (ParseException ex) {
                Logger.getLogger(FXNomina.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(FXNomina.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FXNomina.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
            bu_teclado.setOnAction(e->{
            try {
                mostrarteclasdeaccesorapido();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
            
        buscarsolucion.setOnAction(e->{
            System.out.println("ol_home page size->"+ol_homepage.size());  
           FXNomina.pageBrowser.homePage.getItems().clear();
          for(int i=0;i<ol_homepage.size();i++)
          {
                 
                     if(ol_homepage.get(i).soluciones==null)
                     {
                            if(FXNomina.pageBrowser.homePage.getItems().size()<(i+1))
                            {
                               FXNomina.pageBrowser.homePage.getItems().add(i,new HomePageRow(RowType.Soluciones, null, null, null));
                            }
                     }
                    
                    
                 List<SolucionInfo> li_si=null;
                 li_si=new ArrayList<>();
                 if(ol_homepage.get(i).soluciones!=null)
                 {    
                  
                        
                  li_si.addAll(Arrays.asList(ol_homepage.get(i).soluciones));   
                  li_si=Arrays.asList(ol_homepage.get(i).soluciones).stream().filter(t->t.nameSolucion.toLowerCase().contains(buscarsolucion.getText().toLowerCase()) || buscarsolucion.getText().isEmpty()).collect(Collectors.toList());
                  if(FXNomina.pageBrowser.homePage.getItems().size()<(i+1))
                        {
                               FXNomina.pageBrowser.homePage.getItems().add(i,new HomePageRow(RowType.Soluciones, null, new SolucionInfo[li_si.toArray(new SolucionInfo[0]).length], null));
                        } 
                   /* if(SihicApp.pageBrowser.homePage.getItems().get(i)!=null)
                    {
                         SihicApp.pageBrowser.homePage.getItems().get(i).soluciones=new SolucionInfo[li_si.toArray(new SolucionInfo[0]).length];
                    }
                    else
                    {
                                 SihicApp.pageBrowser.homePage.getItems().set(i,new HomePageRow(RowType.Soluciones, null, new SolucionInfo[li_si.toArray(new SolucionInfo[0]).length], null)); 
                    }*/
                   int k=0;
                 /*  if(li_si.size()==0)
                   {
                     SihicApp.pageBrowser.homePage.getItems().set(i,null);   
                   }*/
                    SolucionInfo []si2=new SolucionInfo[li_si.size()];
                   for(SolucionInfo si:li_si)
                   {
                            System.out.println("si->"+i+"-"+k+"-"+si.nameSolucion);
                    
                      si2[k]=new SolucionInfo(si.name, si.description, si.sofackarPath, si.baseUri, si.appClass, si.previewUrl, si.playgroundProperties ,null,true, si.nameSolucion);
                       k++; 
                   }
                     if(si2.length>0)
                         FXNomina.pageBrowser.homePage.getItems().get(i).soluciones=si2;
                
                  } 
              
          }
         });
       
        sampleListPopover = new PopoverMenu();
        sampleListPopover.setPrefWidth(440);
        root.getChildren().add(sampleListPopover);
        final SamplePopoverTreeList rootPage = new SamplePopoverTreeList(Soluciones.ROOT, pageBrowser);
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

        modalDimmer = new StackPane();
        modalDimmer.setId("ModalDimmer");
        modalDimmer.setVisible(false);
        root.setDepthTest(DepthTest.DISABLE);
        root.getChildren().add(modalDimmer);
        // root.getChildren().add(searchPopover);
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
                    root.resize(width / 2, height / 2);
                } else {
                    root.getChildrenUnmodifiable().get(0).resize(width / 2, height / 2);
                }
            } else {
                Parent root = scene.getRoot();
                if (root instanceof Group) {
                    Pane oldRoot = (Pane) root.getChildrenUnmodifiable().get(0);
                    ((Group) root).getChildren().clear();
                    oldRoot.getTransforms().clear();
                    scene.setRoot(oldRoot);
                }
            }
        });
        return radioMenuItem;
    }

    private void setStylesheets() {
        final String EXTERNAL_STYLESHEET = "http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600";
        scene.getStylesheets().setAll(hojaestilos);
        //new JMetro(JMetro.Style.DARK).applyTheme(root);
        scene.setFill(Color.TRANSPARENT);
        Thread backgroundThread = new Thread(() -> {
            try {
                URL url = new URL(EXTERNAL_STYLESHEET);
                try (
                        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                        Reader newReader = Channels.newReader(rbc, "ISO-8859-1");
                        BufferedReader bufferedReader = new BufferedReader(newReader)) {
                    // Checking whether we can read a line from this url
                    // without exception
                    bufferedReader.readLine();
                }
                Platform.runLater(() -> {
                    // scene.getStylesheets().add(EXTERNAL_STYLESHEET);
                });
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXNomina.class.getName()).log(Level.FINE, "Failed to load external stylesheet", ex);
            } catch (IOException ex) {
                Logger.getLogger(FXNomina.class.getName()).log(Level.FINE, "Failed to load external stylesheet", ex);
            }
        }, "Trying to reach external styleshet");
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    @Override
    public void start(final Stage stage) throws Exception {
        // CREATE SCENE
           loaddatosorganizacion_toobar();
        showlogin();
        if (Bo_LoginCorrecto) {
           
           
            loadpopover();
         
            Rectangle2D primaryScreenBounds2 = Screen.getPrimary().getVisualBounds();
            scene = new Scene(root, (primaryScreenBounds2.getWidth()), 768, Color.BLUE);
            scene.getAccelerators().put(kf, task);
            scene.getAccelerators().put(kc, task2);
            scene.getAccelerators().put(kp, task3);
            scene.getAccelerators().put(ko, task4);
            scene.getAccelerators().put(km, task5);
            scene.getAccelerators().put(kn, task6);
            scene.getAccelerators().put(kd, task7);
            scene.getAccelerators().put(ki, task8);
            scene.getAccelerators().put(kr, task9);
            scene.getAccelerators().put(ke, task10);
            scene.getAccelerators().put(kt, task11);
            scene.getAccelerators().put(ks, task12);
            if (IS_EMBEDDED || IS_ANDROID) {
                new ScrollEventSynthesizer(scene);
            }
            setStylesheets();

            stage.setScene(scene);
            // START FULL SCREEN IF WANTED
            if (PlatformFeatures.START_FULL_SCREEN) {
                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX(primaryScreenBounds.getMinX());
                stage.setY(primaryScreenBounds.getMinY());
                stage.setWidth(primaryScreenBounds.getWidth());
                stage.setHeight(primaryScreenBounds.getHeight());
            }
            stage.setTitle("Nomina 1.0");
            stage.setMaximized(true);
            stage.show();
              
              for(int i=0;i<pageBrowser.homePage.getItems().size();i++)
              {
                 HomePageRow homePagerow;
                 if(pageBrowser.homePage.getItems().get(i).soluciones!=null)
                 {     
                      
                         
                       SolucionInfo []solucionInfo=new SolucionInfo[pageBrowser.homePage.getItems().get(i).soluciones.length];
                   for(int j=0;j<pageBrowser.homePage.getItems().get(i).soluciones.length;j++)
                   {
                      // System.out.println("name->"+i+"-"+pageBrowser.homePage.getItems().get(i).soluciones[j].name+solucionInfo[j]);
                       solucionInfo[j]=null;
                       solucionInfo[j]=new SolucionInfo(pageBrowser.homePage.getItems().get(i).soluciones[j].name, pageBrowser.homePage.getItems().get(i).soluciones[j].description, pageBrowser.homePage.getItems().get(i).soluciones[j].sofackarPath, pageBrowser.homePage.getItems().get(i).soluciones[j].baseUri, pageBrowser.homePage.getItems().get(i).soluciones[j].appClass, pageBrowser.homePage.getItems().get(i).soluciones[j].previewUrl, pageBrowser.homePage.getItems().get(i).soluciones[j].playgroundProperties ,null,true, pageBrowser.homePage.getItems().get(i).soluciones[j].nameSolucion);
                       
                   }
                  homePagerow=new HomePageRow(HomePage.RowType.Soluciones,null,solucionInfo,null); 
                 }
                 else
                 {
                     homePagerow=new HomePageRow(HomePage.RowType.Soluciones,null,null,null);   
                 }
              
                      
                 ol_homepage.add(homePagerow);
              }
        }
    }
//getter y setter variables static

    /*  public static DatosEmpresa getDatosempresa() {
        return datosempresa;
    }*/
    public static void main(String[] args) {
        launch(args);
    }

    public static void showModalMessage() {

        p5.styleProperty().bind(Bindings.createStringBinding(
                () -> {
                    final double percent = p5.getProgress();
                    // p5.setProgress((SihicApp.cprogreso*(SihicApp.cprogreso*2.56)));
                    if (percent < 0) {
                        return null; // progress bar went indeterminate
                    }                    //
                    // poor man's gradient for stops: red, yellow 50%, green
                    // Based on http://en.wikibooks.org/wiki/Color_Theory/Color_gradient#Linear_RGB_gradient_with_6_segments
                    //
                    final double m = (percent);
                    final int n = (int) m;
                    final double f = m - n;
                    final int t = (int) (255 * f);
                    int r = 1, g = 1, b = 1;
                    switch (n) {
                        case 0:
                            r = 255;
                            g = t;
                            b = 0;
                            break;
                        case 1:
                            r = 255 - t;
                            g = 255;
                            b = 0;
                            break;
                        case 2:
                            r = 0;
                            g = 255;
                            b = 0;
                            break;

                    }
                    final String style = String.format("-fx-progress-color: rgb(%d,%d,%d)", r, g, b);
                    return style;
                },
                p5.progressProperty()
        ));
        p5.progressProperty().bind(FXNomina.task_4.progressProperty());
        p5.visibleProperty().bind(FXNomina.task_4.runningProperty());
        modalDimmer.getChildren().add(p5);
        modalDimmer.setOpacity(0);
        modalDimmer.setVisible(true);
        modalDimmer.setCache(true);
        /*  TimelineBuilder.create().keyFrames(
            new KeyFrame(Duration.seconds(1), 
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        modalDimmer.setCache(false);
                    }
                },
                new KeyValue(modalDimmer.opacityProperty(),1, Interpolator.EASE_BOTH)
        )).build().play();*/

        Task<Void> task_77 = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (true) {
                    //  System.out.println("Cprogreso->"+SihicApp.cprogreso);
                    if (FXNomina.tproceso() >= 39) {
                        // System.out.println("Cprogreso->"+SihicApp.cprogreso);
                        hideModalMessage();

                        return null;
                    }
                }

            }
        };
        thimport_77 = new Thread(task_77);

        thimport_77.setDaemon(true);
        thimport_77.start();
    }

    public static void hideModalMessage() {
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
                        new KeyValue(modalDimmer.opacityProperty(), 0, Interpolator.EASE_BOTH)
                )).build().play();
    }

   

    public static List<GenSexo> getL_gensexo() {
        return l_gensexo;
    }

  

    public static List<GenMunicipios> getL_genmunicipios() {
        return l_genmunicipios;
    }

    public static List<GenDepartamentos> getL_gendepartamentos() {
        return l_gendepartamentos;
    }

    
    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

    }

    public static boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);

    }

    private void showlogin() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        appPathLogin = "administracion.FLogin";
        clzLogin = Class.forName(appPathLogin);
        appClassLogin = clzLogin.newInstance();
        pa_login = null;
        pa_login = (Parent) clzLogin.getMethod("createContent").invoke(appClassLogin);
        sc_Login = null;
        sc_Login = new Scene(pa_login, Color.TRANSPARENT);

        if (stageLogin.isShowing()) {
            stageLogin.close();
        }

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

  
    public void loadpopover() {
        // create and setup list popover

        final SamplePopoverTreeList rootPage = new SamplePopoverTreeList(Soluciones.ROOT, pageBrowser);
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

    
    public static void loaddatosorganizacion_toobar() throws IOException {
        try
        {
        if (generalControllerClient.load_GenUnidadesOrganizacion() != null) {
            genUnidadesOrganizacion = generalControllerClient.load_GenUnidadesOrganizacion();
            if (genUnidadesOrganizacion.getLogo() != null) {
                InputStream in = new ByteArrayInputStream(genUnidadesOrganizacion.getLogo());
                bi = ImageIO.read(in);
                wi = new WritableImage(1, 1);
                wi = SwingFXUtils.toFXImage(bi, wi);
                //img=new ImageView();
                Loadimg = wi;
                I_ImagenLogo.setImage(Loadimg);
            }
            T_InfEmpresa.setText(genUnidadesOrganizacion.getNombre() + " Nit: " + genUnidadesOrganizacion.getNit());
        }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        FXNomina.scene = scene;
    }

    public static void load_all_parameters_and_lists() throws ParseException, NoSuchMethodException, IllegalAccessException {
        try {
             System.out.println("inicio carga parametros");
            counter = new AtomicInteger(0);

            loadChoiceBoxGeneral = new LoadChoiceBoxGeneral();
            //*******************************************************proceso paraelo1
            task_4 = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                   
                   
                    generalControllerClient.load_gendepartamentos();
                    l_eapb.clear();
                    l_eapb.addAll(genEapbControllerClient.li_geneapb(null));
                    updateProgress(1, 7);
                    cprogreso++;
                    counter.incrementAndGet();
                    generalControllerClient.load_genmunicipios();
                   

                    updateProgress(2, 7);
                    cprogreso++;
                    counter.incrementAndGet();

                    generalControllerClient.load_gensexo();

                

                    generalControllerClient.load_gentiposdocumentos();
                    updateProgress(3, 7);
                    cprogreso++;
                    counter.incrementAndGet();
                   consecutivosContabilidadControllerClient.getRecord();
                    updateProgress(4, 7);
                    cprogreso++;
                    counter.incrementAndGet();
                   cargosEntidadControllerClient.getRecords();
                    updateProgress(5, 7);
                    cprogreso++;
                    counter.incrementAndGet();
                  empleadosControllerClient.getRecords(null);
                   updateProgress(6, 7);
                    cprogreso++;
                    counter.incrementAndGet();
                  factorHorasExtrasControllerClient.getRecords();
                   updateProgress(7, 7);
                    cprogreso++;
                    counter.incrementAndGet();
                  LoadChoiceBoxGeneral.Load_TiposHorasExtra();
                   parametrosNominaControllerClient.getRecords();
                    
                     System.out.println("fin carga parametros");
                    thimport_4 = null;

                    return null;
                }
            };
            thimport_4 = new Thread(task_4);

            thimport_4.setDaemon(true);
            thimport_4.start();

            //*********************************************proceso paralelo 2
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int tproceso() {

        return counter.get();
    }

    private void facturar() {
        pageBrowser.goToPage("solucion:///Documentos/Facturar");
    }
    private static void mostrarteclasdeaccesorapido() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
    {
          String appClass;
     Class clz;
     Object app;
     Parent parent;
     Stage stage = new Stage(StageStyle.DECORATED);
        appClass = "sihic.CombinacionTeclas";
        clz=null;
        clz = Class.forName(appClass);
        app = clz.newInstance();
        parent = null;
        parent = (Parent) clz.getMethod("createContent").invoke(app);
      Scene  scene2 = null;
        scene2 = new Scene(parent, Color.TRANSPARENT);

        if (stage.isShowing()) {
            stage.close();
        }

//set scene to stage
        stage.sizeToScene();

        //center stage on screen
        stage.centerOnScreen();
        stage.setScene(scene2);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stage.show();
    }

   
    
}
