/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package menu;
import config.ProxyDialog;
import controls.BreadcrumbBar;
import controls.ButtonInsertProduct;
import controls.WindowButtons;
import controls.WindowResizeButton;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Categoria;
import model.DatosEmpresa;
import model.FacturaItem;
import model.Producto;
import model.TempFacturaItems;
import model.Terceros;
import netscape.javascript.JSObject;
import pages.SamplePage;
import paint.Mesa;
import rest.CategoriaJerseyClient;
import rest.DatosEmpresaJerseyClient;
import rest.FacturaItemJerseyClient;
import rest.ImagenProductoJerseyClient;
import rest.ProductoJerseyClient;
import rest.TercerosJerseyClient;
 

/**
 *
 * @author karol
 */
public class MenuMain extends Application {
     static {
        // Enable using system proxy if set
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("pNoMesa", "0");
    }
    public static final String BASE_URI = "http://localhost:8080/webresources";
    private static final ObservableList m_oDatosEmpresa = FXCollections.observableArrayList();
    private static final ObservableList m_oDatosProducto = FXCollections.observableArrayList();
    private static ObservableList m_oDatosCategoria = FXCollections.observableArrayList();
    private static ObservableList m_oDatosTerceros = FXCollections.observableArrayList();
    private static ObservableList m_oDatosEmpleados = FXCollections.observableArrayList();
    private static final Vector<Runnable> dataLoadingTasks = new Vector<Runnable>();
    private static final DatosEmpresa datosempresa=new DatosEmpresa();
  
    private static MenuMain menumain;
    private static boolean isApplet = false;
    public static Vector<Mesa> m_vMesa=new Vector<Mesa>();
    private Scene scene;
    private BorderPane root;
    private ToolBar toolBar;
    private SplitPane splitPane;
    private TreeView pageTree;
    private Pane pageArea;
    private Pages pages;
    private Page currentPage;
    private String currentPagePath;
    private Node currentPageView;
    private BreadcrumbBar breadcrumbBar;
    private Stack<Page> history = new Stack<Page>();
    private Stack<Page> forwardHistory = new Stack<Page>();
    private boolean changingPage = false;
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;
    private WindowResizeButton windowResizeButton;
    public boolean fromForwardOrBackButton = false;
    public static  StackPane modalDimmer;
    private ProxyDialog proxyDialog;
    private ToolBar pageToolBar;
    private JSObject browser;
    private String docsUrl;
    private Region veil;
    private ProgressIndicator p;
        
    /**
     * Get the singleton instance of Ensemble
     * 
     * @return The singleton instance
     */
    public static MenuMain getMenuMain() {
        return menumain;
    }
    
    /**
     * Start the application
     * 
     * @param 
     * The main application stage
     */
    @Override public void start(final Stage stage) {

      stage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

    @Override
    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
        System.out.println("minimized:" + t1.booleanValue());
    }
});
      stage.resizableProperty().addListener(new ChangeListener<Boolean>() {

    @Override
    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
        System.out.println("rezizable:" + t1.booleanValue());
    }
});
      
    
        //Leer datos de empresa y almacenar en un Object
       //notifyPreloader(new PreloaderHandoverEvent(dataLoadingTasks));
        veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
        p = new ProgressIndicator();
        p.setMaxSize(150, 150); 
        p.setProgress(1);
        LoadDataInit();
          menumain = this;
        stage.setTitle("Facturacion");
        // set default docs location
        
        //docsUrl = System.getProperty("docs.url") != null ?
          //      System.getProperty("docs.url") : DEFAULT_DOCS_URL; 
        // create root stack pane that we use to be able to overlay proxy dialog
        StackPane layerPane = new StackPane();
        // check if applet
        try {
            browser = getHostServices().getWebContext();
            isApplet =  browser != null;
        } catch (Exception e) {
            isApplet = false;
        }
        if (!isApplet) {
            stage.initStyle(StageStyle.DECORATED);
            // create window resize button
            windowResizeButton = new WindowResizeButton(stage, 1020,700);
            // create root
            root = new BorderPane() {
                @Override protected void layoutChildren() {
                    super.layoutChildren();
                    windowResizeButton.autosize();
                    windowResizeButton.setLayoutX(getWidth() - windowResizeButton.getLayoutBounds().getWidth());
                    windowResizeButton.setLayoutY(getHeight() - windowResizeButton.getLayoutBounds().getHeight());
                }
            };
          
        } else {
            root = new BorderPane();
         
        }
        root.setId("root");
        layerPane.setDepthTest(DepthTest.DISABLE);
        layerPane.getChildren().addAll(root,veil,p);
        // create scene
        boolean is3dSupported = Platform.isSupported(ConditionalFeature.SCENE3D);
        scene = new Scene(layerPane, 1020, 700, is3dSupported);
        scene.widthProperty().addListener(new ChangeListener<Number>() {
    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
        System.out.println("Width: " + newSceneWidth);
        SamplePage.resizeWidthScrollPane(newSceneWidth.doubleValue());
    }
});  
        if (is3dSupported) {
            //RT-13234
            scene.setCamera(new PerspectiveCamera());
        }
        scene.getStylesheets().add(MenuMain.class.getResource("EnsembleStylesCommon.css").toExternalForm());
        // create modal dimmer, to dim screen when showing modal dialogs
        modalDimmer = new StackPane();
        modalDimmer.setId("ModalDimmer");
        modalDimmer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                t.consume();
                hideModalMessage();
            }
        });
        modalDimmer.setVisible(false);
        layerPane.getChildren().add(modalDimmer);
        // create main toolbar
        toolBar = new ToolBar();
        toolBar.setId("mainToolBar");
        ImageView logo = new ImageView(new Image(MenuMain.class.getResourceAsStream("/images/logo.png")));
        logo.setFitHeight(60);
        logo.setFitWidth(60);
        HBox.setMargin(logo, new Insets(0,0,0,5));
        toolBar.getItems().add(logo);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolBar.getItems().add(spacer);
        Button highlightsButton = new Button();
        highlightsButton.setId("highlightsButton");
        highlightsButton.setMinSize(120, 66);
        highlightsButton.setPrefSize(120, 66);
        highlightsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                goToPage(Pages.HIGHLIGHTS);
            }
        });
        //toolBar.getItems().add(highlightsButton);
        Text _tTexthebreo=new Text("ישוע המשיח הוא דרך האמת והחיים אף אחד לא בא אל האב, אלא על ידי לי \nתודה האבא שלי יהוה צבאות\n" +
"שגואל את החיים שלך מהרס, שמכתיר אותך באהבה וברחמים");
       _tTexthebreo.setFill(Color.GOLD);
       _tTexthebreo.textAlignmentProperty().setValue(TextAlignment.CENTER);
       _tTexthebreo.setFont(Font.font("ARIAL", FontWeight.BOLD,14));
       
        Button newButton = new Button();
        newButton.setId("newButton");
        newButton.setMinSize(120,66);
        newButton.setPrefSize(120,66);
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                goToPage(Pages.NEW);
            }
        });
     
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        toolBar.getItems().add(spacer2);
        
        ImageView simbolCristiano = new ImageView(new Image(MenuMain.class.getResourceAsStream("/images/simbolCristiano.png")));
        HBox.setMargin(logo, new Insets(0,0,0,15));
              
simbolCristiano.setFitHeight(65);
simbolCristiano.setFitWidth(65);
        // toolBar.getItems().add(searchTest);
        //SearchBox searchBox = new SearchBox();
        //HBox.setMargin(searchBox, new Insets(0,5,0,0));
    
        toolBar.setPrefHeight(66);
        toolBar.setMinHeight(66);
        toolBar.setMaxHeight(66);
        GridPane.setConstraints(toolBar, 0, 0);
        if (!isApplet) {
            // add close min max
            final WindowButtons windowButtons = new WindowButtons(stage);
           // toolBar.getItems().add(windowButtons);
            // add window header double clicking
            toolBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2) {
                        windowButtons.toogleMaximized();
                    }
                }
            });
            // add window dragging
            toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {
                    mouseDragOffsetX = event.getSceneX();
                    mouseDragOffsetY = event.getSceneY();
                }
            });
            toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {
                    if(!windowButtons.isMaximized()) {
                        stage.setX(event.getScreenX()-mouseDragOffsetX);
                        stage.setY(event.getScreenY()-mouseDragOffsetY);
                    }
                }
            });
        }
        // create page tree toolbar
        ToolBar pageTreeToolBar = new ToolBar() {
            @Override public void requestLayout() {
                super.requestLayout();
                // keep the height of pageToolBar in sync with pageTreeToolBar so they always match
                if (pageToolBar != null && getHeight() != pageToolBar.prefHeight(-1)) {
                    pageToolBar.setPrefHeight(getHeight());
                }
            }
        };
        pageTreeToolBar.setId("page-tree-toolbar");
        pageTreeToolBar.setMaxWidth(Double.MAX_VALUE);
        pageTreeToolBar.setMinHeight(33);
       // pageTreeToolBar.setMinSize(Double.MAX_VALUE, 29);
          ToggleGroup pageButtonGroup = new ToggleGroup();
       
        final ToggleButton modulosButton = new ToggleButton("Modulos");
       // modulosButton.setMinHeight(200);
        modulosButton.setToggleGroup(pageButtonGroup);
        modulosButton.setSelected(true);
         InvalidationListener treeButtonNotifyListener = new InvalidationListener() {
            public void invalidated(Observable ov) {
                if(modulosButton.isSelected()) {
                    pageTree.setRoot(pages.getModulos());
                }
            }
        };
        modulosButton.selectedProperty().addListener(treeButtonNotifyListener);
        // pageTreeToolBar.getItems().addAll(modulosButton);
        // create page tree
        pages = new Pages();
        proxyDialog = new ProxyDialog(stage, pages);
        //proxyDialog.loadSettings();
        //proxyDialog.getDocsInBackground(true,null);
        pages.parseModulos();
        pageTree = new TreeView();
        pageTree.setId("page-tree");
        pageTree.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pageTree.setRoot(pages.getRoot());
        pageTree.setShowRoot(false);
        pageTree.setEditable(false);
        pageTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        pageTree.setCursor(Cursor.HAND);
        pageTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue,
                    Object newValue) {
                if (!changingPage) {
                    Page selectedPage = (Page)pageTree.getSelectionModel().getSelectedItem();
                    if (selectedPage!=pages.getRoot()) goToPage(selectedPage);
                }
            }
        });
        // create left split pane
        BorderPane leftSplitPane = new BorderPane();
        leftSplitPane.setTop(pageTreeToolBar);
        leftSplitPane.setCenter(pageTree);
        // create page toolbar
        pageToolBar = new ToolBar();
        pageToolBar.setId("page-toolbar");
         pageToolBar.setMinHeight(29);
        pageToolBar.setMaxSize(Double.MAX_VALUE, Control.USE_PREF_SIZE);
        if (!isApplet) {
            Button backButton = new Button();
            backButton.setGraphic(new ImageView(new Image(MenuMain.class.getResourceAsStream("/images/back.png"))));
            backButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) { back(); }
            });
            backButton.setMaxHeight(Double.MAX_VALUE);
            Button forwardButton = new Button();
            forwardButton.setGraphic(new ImageView(new Image(MenuMain.class.getResourceAsStream("/images/forward.png"))));
            forwardButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    forward();
                }
            });
            forwardButton.setMaxHeight(Double.MAX_VALUE);
            Button reloadButton = new Button();
            reloadButton.setGraphic(new ImageView(new Image(MenuMain.class.getResourceAsStream("/images/reload.png"))));
            reloadButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    reload();
                }
            });
            reloadButton.setMaxHeight(Double.MAX_VALUE);
            pageToolBar.getItems().addAll(backButton, forwardButton, reloadButton);
        }
        breadcrumbBar = new BreadcrumbBar();
        pageToolBar.getItems().add(breadcrumbBar);
        if (!isApplet) {
            Region spacer3 = new Region();
            HBox.setHgrow(spacer3, Priority.ALWAYS);
            Button settingsButton = new Button();
            settingsButton.setId("SettingsButton");
            settingsButton.setGraphic(new ImageView(new Image(MenuMain.class.getResourceAsStream("/images/update.jpg"))));
            settingsButton.setOnAction(new EventHandler<ActionEvent>() {
             
                public void handle(ActionEvent event) {
                    LoadDataInit();
                }
            });
            settingsButton.setMaxHeight(29);
            pageToolBar.getItems().addAll(spacer3, settingsButton);
        }
        // create page area
        pageArea = new Pane() {
            @Override protected void layoutChildren() {
                for (Node child:pageArea.getChildren()) {
                    child.resizeRelocate(0, 0, pageArea.getWidth(), pageArea.getHeight());
                }
            }
        };
        pageArea.setId("page-area");
        // create right split pane
        BorderPane rightSplitPane = new BorderPane();
        rightSplitPane.setTop(pageToolBar);
        rightSplitPane.setCenter(pageArea);
        // create split pane
        splitPane = new SplitPane();
        splitPane.setId("page-splitpane");
        splitPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setConstraints(splitPane, 0, 0);
        splitPane.getItems().addAll(leftSplitPane, rightSplitPane);
        splitPane.setDividerPosition(0, 0.17);

        this.root.setTop(toolBar);
        this.root.setCenter(splitPane);
        // add window resize button so its on top
        if (!isApplet) {
            windowResizeButton.setManaged(false);
            this.root.getChildren().add(windowResizeButton);
        }
        // expand first level of the tree
        for (TreeItem child: pages.getRoot().getChildren()) {
            if (child == pages.getHighlighted() || child == pages.getNew()) continue;
            child.setExpanded(true);
            for (TreeItem child2: (ObservableList<TreeItem<String>>)child.getChildren()) {
                child2.setExpanded(true);
            }
        }
        // goto initial page
        if (isApplet) {
            String hashLoc = getBrowserHashLocation();
            if (hashLoc != null) {
                goToPage(hashLoc);
            } else {
                // default to all samples
                goToPage(pages.getModulos());
            }
        } else {
            // default to all samples
            goToPage(pages.getModulos());
        }
        // show stage
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Called from JavaScript in the browser when the page hash location changes
     * 
     * @param hashLoc The new has location, e.g. #SAMPLES
     */
    public void hashChanged(String hashLoc) {
        if (hashLoc != null) {
            // remove # 
            if (hashLoc.length() == 0) {
                hashLoc = null;
            } else {
                hashLoc = hashLoc.substring(1);
            }
            // if new page then navigate to it
            final String path = hashLoc;
            Platform.runLater(new Runnable() {
                public void run() {
                    if (!path.equals(currentPagePath)) goToPage(path);
                }
            });
        }
    }
    
    /**
     * Get the URL of the java doc root directory being used to get 
     * documentation from
     * 
     * @return Documentation directory URL
     */
    public String getDocsUrl() {
        return docsUrl;
    }

    /**
     * Set the URL of the java doc root directory being used to get 
     * documentation from
     * 
     * @param docsUrl Documentation directory URL
     */
    public void setDocsUrl(String docsUrl) {
        this.docsUrl = docsUrl;
    }
    
    /**
     * Fetch the current hash location from the browser via JavaScript
     * 
     * @return Current browsers hash location
     */
    private String getBrowserHashLocation() {
        String hashLoc = null;
        try {
            hashLoc = (String)browser.eval("window.location.hash");
        } catch (Exception e) {
            try {
                System.out.println("Warning failed to get browser location, retrying...");
                hashLoc = (String)browser.eval("window.location.hash");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        // remove #
        if (hashLoc != null) {
            if (hashLoc.length() == 0) {
                hashLoc = null;
            } else {
                hashLoc = hashLoc.substring(1);
            }
        }
        return hashLoc;
    }
    
    /**
     * Show the given node as a floating dialog over the whole application, with 
     * the rest of the application dimmed out and blocked from mouse events.
     * 
     * @param message 
     */
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
    
    /**
     * Hide any modal message that is shown
     */
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
    
    /**
     * Get the pages object that contains the tree of all avaliable pages
     * 
     * @return Pages containing tree of all pages
     * 
     * 
     */
    public Pages getPages() {
        return pages;
    }

    /**
     * Change to new page without swapping views, assumes that the current view 
     * is already showing the new page
     * 
     * @param page The new page object
     */
    public void updateCurrentPage(Page page) {
        goToPage(page, true,false,false);
    }
    
    /**
     * Take ensemble to the given page path, navigating there and adding 
     * current page to history
     * 
     * @param pagePath The path for the new page to show
     */
    public void goToPage(String pagePath) {
        goToPage(pages.getPage(pagePath));
    }

    /**
     * Take ensemble to the given page path, navigating there and adding 
     * current page to history.
     * 
     * @param pagePath  The path for the new page to show
     * @param force     Reload page even if its the current page
     */
    public void goToPage(String pagePath, boolean force) {
        goToPage(pages.getPage(pagePath),true,force,true);
    }

    /**
     * Take ensemble to the given page object, navigating there and adding 
     * current page to history.
     * 
     * @param page Page object to show
     */
    public void goToPage(Page page) {
        goToPage(page, true, false, true);
    }
    
    /**
     * Take ensemble to the given page object, navigating there.
     * 
     * @param page          Page object to show
     * @param addHistory    When true add current page to history before navigating
     * @param force         When true reload page if page is current page
     * @param swapViews     If view should be swapped to new page
     */
    private void goToPage(Page page, boolean addHistory, boolean force, boolean swapViews) {
        if(page==null) return;
        if(!force && page == currentPage) {
            return;
        }
        changingPage = true;
        if (swapViews) {
            Node view = page.createView();
            if (view == null) view = new Region(); // todo temp workaround
            // replace view in pageArea if new
            if (force || view != currentPageView) {
                for (Node child:pageArea.getChildren()){
                    if (child instanceof SamplePage.SamplePageView) {
                        ((SamplePage.SamplePageView)child).stop();
                    }
                }
                pageArea.getChildren().setAll(view);
                currentPageView = view;
            }
        }
        // add page to history
        if (addHistory && currentPage!=null) {
            history.push(currentPage);
            forwardHistory.clear();
        }
        currentPage = page;
        currentPagePath = page.getPath();
        // when in applet update location bar
        if (isApplet) {
            try {
                browser.eval("window.location.hash='"+currentPagePath+"';");
            } catch (Exception e) {
                try {
                    System.out.println("Warning failed to set browser location, retrying...");
                    browser.eval("window.location.hash='"+currentPagePath+"';");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        // expand tree to selected node
        Page p = page;
        while (p!=null) {
            p.setExpanded(true);
            p = (Page) p.getParent();
        }
        // update tree selection
        pageTree.getSelectionModel().select(page);
        // update breadcrumb bar
        breadcrumbBar.setPath(currentPagePath);
        // done
        changingPage = false;
    }
    
    /**
     * Check if current call stack was from back or forward button's action
     * 
     * @return True if current call was caused by action on back or forward button
     */
    public boolean isFromForwardOrBackButton() {
        return fromForwardOrBackButton;
    }
    
    /**
     * Got to previous page in history
     */
    public void back() {
        fromForwardOrBackButton = true;
        if (!history.isEmpty()) {      
            Page prevPage = history.pop();
            forwardHistory.push(currentPage);
            goToPage(prevPage,false, false, true);
        }
        fromForwardOrBackButton = false;
    }
    
    /**
     * Utility method for viewing the page history
     */
    private void printHistory() {
        System.out.print("   HISTORY = ");
        for (Page page:history) {
            System.out.print(page.getName()+"->");
        }
        System.out.print("   ["+currentPage.getName()+"]");
        for (Page page:forwardHistory) {
            System.out.print(page.getName()+"->");
        }
        System.out.print("\n");
    }

    /**
     * Go to next page in history if there is one
     */
    public void forward() {
        fromForwardOrBackButton = true;
        if (!forwardHistory.isEmpty()) {
            Page prevPage = forwardHistory.pop();
            history.push(currentPage);
            goToPage(prevPage,false, false, true);
        }
        fromForwardOrBackButton = false;
    }
    
    /**
     * Reload the current page
     */
    public void reload() {
        goToPage(currentPage,false,true, true);
    }
    
    /**
     * Show the dialog for setting proxy to the user
     */
    public void showProxyDialog() {
        showModalMessage(proxyDialog);
    }
  
    public static void registerDataLoadingTask(Runnable task) {
        dataLoadingTasks.add(task);
    }
    
    
    //Sobrecargado init se encuentran los hilos que se manejan como tareas
        @Override public void init() throws Exception {
        registerDataLoadingTask(new Runnable() {
            @Override public void run() {
                // fetch available regions in the background
                Task<DatosEmpresa[]> getDatosEmpresa = new Task<DatosEmpresa[]>() {
                    @Override protected DatosEmpresa[] call() throws Exception {
                       DatosEmpresaJerseyClient _DatosEmpresaJerseyClient = new DatosEmpresaJerseyClient();
                        DatosEmpresa[] _DatosEmpresa;
                        _DatosEmpresa = _DatosEmpresaJerseyClient.findAll_XML(DatosEmpresa[].class);
                        _DatosEmpresaJerseyClient.close();
                        _DatosEmpresa = Arrays.copyOfRange(_DatosEmpresa, 0, _DatosEmpresa.length);
                        for(DatosEmpresa _oDatosEmpresa:_DatosEmpresa){
                           datosempresa.setM_iNoMesas(_oDatosEmpresa.getM_iNoMesas());
                           datosempresa.setId(_oDatosEmpresa.getId());
                           datosempresa.setM_sNombre(_oDatosEmpresa.getM_sNombre());
                      
                     
                        }
                         updateProgress(1,100);
                         Thread.sleep(1);
                        
                        return _DatosEmpresa;
                    }
                };
                getDatosEmpresa.valueProperty().addListener(new ChangeListener<DatosEmpresa[]>() {
                    @Override public void changed(ObservableValue<? extends DatosEmpresa[]> ov, DatosEmpresa[] t, DatosEmpresa[] t1) {
                        m_oDatosEmpresa.addAll((Object[])t1);
                        
                            FacturaItemJerseyClient facturaJerseyClient=new FacturaItemJerseyClient();
                            List<TempFacturaItems> _TempFacturaItems=facturaJerseyClient.findAll_XML();
                            m_vMesa.clear();
                        for(int i=0;i<datosempresa.getM_iNoMesas();i++)
                        {
                             m_vMesa.add(i,new Mesa(50,Color.WHITE,1,(i+1),false));
                             m_vMesa.get(i).setCursor(Cursor.HAND);
                             for(TempFacturaItems tf:_TempFacturaItems)
                             {
                                 if(m_vMesa.get(i).getM_iNoMesa()==tf.getM_iNoMesa())
                                 {      m_vMesa.get(i).setM_LIdTempMesa(tf.getM_LIdTempMesa());
                                        m_vMesa.get(i).getFactura().getCustomer().setNo_ident(tf.getM_sNoIdent());
                                        m_vMesa.get(i).getFactura().getCustomer().setCelular(tf.getM_sCelular());
                                        m_vMesa.get(i).getFactura().getCustomer().setDir1(tf.getM_sDir1());
                                        m_vMesa.get(i).getFactura().getCustomer().setId(tf.getM_lIdTerc());
                                        m_vMesa.get(i).getFactura().getCustomer().setNombre(tf.getM_sNombre());
                                        m_vMesa.get(i).getFactura().setId(tf.getM_lIdFact());
                                        m_vMesa.get(i).getFactura().setFacturaDate(tf.getM_dFacturaDate());
                                        m_vMesa.get(i).getFactura().setCredito(tf.isCredito());
                                        m_vMesa.get(i).setM_bFreeMesa(false);
                                        for(FacturaItem fi:tf.getFacturaItem())
                                        {
                                            m_vMesa.get(i).getFactura().addProduct(fi.getProduct(), fi.getQuantity(), BigDecimal.ONE,"");
                                            System.out.println("Id fact:"+m_vMesa.get(i).getFactura().getId()+" Producto:"+fi.getProduct().getNombre()+" Cantidad:"+fi.getQuantity());
     
                                        }
                                        m_vMesa.get(i).getFactura().calculateTotals();
                                        
                                 }
                             }
                             m_vMesa.get(i).createmesa();
                              
                        }
                        
                    }
                });
         veil.visibleProperty().bind(getDatosEmpresa.runningProperty());
        p.visibleProperty().bind(getDatosEmpresa.runningProperty());
                p.progressProperty().bind(getDatosEmpresa.progressProperty());
               new Thread(getDatosEmpresa).start();
                //fetch datos categoria
                  Task<Categoria[]> getDatosCategoria = new Task<Categoria[]>() {
                    @Override protected Categoria[] call() throws Exception {
                        CategoriaJerseyClient _DatosCategoriaJerseyClient = new CategoriaJerseyClient();
                        Categoria[] _DatosCategoria;
                        _DatosCategoria = _DatosCategoriaJerseyClient.findAll_XML(Categoria[].class);
                        _DatosCategoriaJerseyClient.close();
                        _DatosCategoria = Arrays.copyOfRange(_DatosCategoria, 0, _DatosCategoria.length);
                         updateProgress(30,100);
                         Thread.sleep(1);
                         System.out.println("Size list categorias->"+_DatosCategoria.length);
                         return _DatosCategoria;
                    }
                };
                getDatosCategoria.valueProperty().addListener(new ChangeListener<Categoria[]>() {
                    @Override public void changed(ObservableValue<? extends Categoria[]> ov, Categoria[] t, Categoria[] t1) {
                          m_oDatosCategoria.addAll((Object)t1);
                        System.out.println("Size list categorias->"+m_oDatosCategoria.size());
                        //fetch all tables
                       
                        
                        
                    }
                });
                  veil.visibleProperty().bind(getDatosEmpresa.runningProperty());
        p.visibleProperty().bind(getDatosEmpresa.runningProperty());
                p.progressProperty().bind(getDatosEmpresa.progressProperty());
       
                new Thread(getDatosCategoria).start();
              //fetch datos terceros
             
                  Task<Terceros[]> getDatosTerceros = new Task<Terceros[]>() {
                    @Override protected Terceros[] call() throws Exception {
                        TercerosJerseyClient _DatosTercerosJerseyClient = new TercerosJerseyClient();
                        Terceros[] _DatosTerceros;
                        Terceros[] _DatosEmpleados;
                        _DatosTerceros = _DatosTercerosJerseyClient.findRange_XML(Terceros[].class,"0","21");
                        _DatosEmpleados = _DatosTercerosJerseyClient.findAll_XML(Terceros[].class);
                        
                        _DatosTercerosJerseyClient.close();
                        _DatosTerceros = Arrays.copyOfRange(_DatosTerceros, 0, _DatosTerceros.length);
                         _DatosEmpleados = Arrays.copyOfRange(_DatosEmpleados, 0, _DatosEmpleados.length);
                        
                        updateProgress(60,100);
                         Thread.sleep(5);
                         m_oDatosEmpleados.clear();
                         m_oDatosEmpleados.addAll((Object)_DatosEmpleados);
                        return _DatosTerceros;
                    }
                };
                getDatosTerceros.valueProperty().addListener(new ChangeListener<Terceros[]>() {
                    @Override public void changed(ObservableValue<? extends Terceros[]> ov, Terceros[] t, Terceros[] t1) {
                        m_oDatosTerceros.clear();
                        m_oDatosTerceros.addAll(t1);
                        
                        //fetch all tables
                       
                        
                        
                    }
                });
                  veil.visibleProperty().bind(getDatosEmpresa.runningProperty());
        p.visibleProperty().bind(getDatosEmpresa.runningProperty());
                p.progressProperty().bind(getDatosEmpresa.progressProperty());
       
                new Thread(getDatosTerceros).start();  
                
                //fetch datos producto
                
                Task<Producto[]> getDatosProducto = new Task<Producto[]>() {
                    @Override protected Producto[] call() throws Exception {
                        ProductoJerseyClient _DatosProductoJerseyClient = new ProductoJerseyClient();
                        Producto[] _DatosProducto;
                        _DatosProducto = _DatosProductoJerseyClient.findAll_XML(Producto[].class);
                        _DatosProductoJerseyClient.close();
                        _DatosProducto = Arrays.copyOfRange(_DatosProducto, 0, _DatosProducto.length);
                          updateProgress(90,100);
                         Thread.sleep(1);
                       
                        return _DatosProducto;
                    }
                };
                getDatosProducto.valueProperty().addListener(new ChangeListener<Producto[]>() {
                    @Override public void changed(ObservableValue<? extends Producto[]> ov, Producto[] t, Producto[] t1) {
                       //Traemos las imagenes de todos los productos
                        ImagenProductoJerseyClient imagenProductoJerseyClient=new ImagenProductoJerseyClient();
                                        
                        
                        ButtonInsertProduct[] _vButtonInsertProduct=new ButtonInsertProduct [t1.length];
                       int i=0; 
                       for(Producto p:t1)
                        {
                                 try {
                                     _vButtonInsertProduct[i]=new ButtonInsertProduct(p);
                                 }catch (IOException ex) {
                                     Logger.getLogger(MenuMain.class.getName()).log(Level.SEVERE, null, ex);
                                 }
                                 i++;
                        }
                        m_oDatosProducto.addAll((Object)_vButtonInsertProduct);
                        //fetch all tables
                       
                        
                        
                    }
                });
                  veil.visibleProperty().bind(getDatosEmpresa.runningProperty());
        p.visibleProperty().bind(getDatosEmpresa.runningProperty());
                p.progressProperty().bind(getDatosEmpresa.progressProperty());
       
                new Thread(getDatosProducto).start();
              
                   
                
            }
        });
       
        // create ui
    }
  public static ObservableList getM_oDatosEmpresa() {
        return m_oDatosEmpresa;
    }
  
   public static DatosEmpresa getDatosempresa() {
        return datosempresa;
    }
   
    public static Vector<Mesa> getM_vMesa() {
        return m_vMesa;
    }

    public static void setM_vMesa(Vector<Mesa> m_vMesa) {
        MenuMain.m_vMesa = m_vMesa;
    }

    public static ObservableList getM_oDatosProducto() {
        return m_oDatosProducto;
    }

    public static ObservableList getM_oDatosCategoria() {
        return m_oDatosCategoria;
    }

    public static ObservableList getM_oDatosTerceros() {
        return m_oDatosTerceros;
    }

    public static void setM_oDatosTerceros(ObservableList m_oDatosTerceros) {
        MenuMain.m_oDatosTerceros = m_oDatosTerceros;
    }

    public static ObservableList getM_oDatosEmpleados() {
        return m_oDatosEmpleados;
    }

   
    
    /**
     * Java Main Method for launching application when not using JavaFX 
     * Launcher, eg from IDE without JavaFX support
     * 
     * @param args Command line arguments
     */
    public static  void LoadDataInit()
    {
        getM_oDatosCategoria().clear();
        getM_oDatosEmpresa().clear();
        getM_oDatosProducto().clear();
        getM_oDatosTerceros();
        for (Runnable task: dataLoadingTasks) 
        {
            Platform.runLater(task);
      
        }
    }
    public static void main(String[] args) {
        Application.launch(args);
    }

   }
