package isoftconta;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import isoftconta.glyphfont.GlyphFont;
import isoftconta.glyphfont.GlyphFontRegistry;
import isoftconta.model.EmptyServicio;
import isoftconta.model.Area;
import isoftconta.model.ServicioTree.TreeNode;
import isoftconta.model.WelcomePage;
import isoftconta.servicios.CatalogoCuentasController;
import isoftconta.servicios.CiudadesController;
import isoftconta.servicios.DocumentoSoporteController;
import isoftconta.servicios.EnumDocumentoSoporte;
import isoftconta.servicios.ProductoController;
import isoftconta.util.ServicioScanner;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import modelo.EntidadesStatic;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

public class IsoftConta extends Application {
    
    private Map<String, Area> areasMap;

    private Stage stage;
    private GridPane grid;
    
    private Servicios selectedServicio;
    
    private TreeView<Servicios> serviciosTreeView;
    private TreeItem<Servicios> root;

    private TabPane tabPane;
    private Tab welcomeTab;
    private Tab servicioTab;
    public static ChoiceBox cb_sucursales=new ChoiceBox();
    public static ChoiceBox cb_centrocostos=new ChoiceBox();
    public static ChoiceBox cb_documentossoporte=new ChoiceBox();
    public  static  TextField tercero=IsoftConta.tercero=TextFields.createClearableTextField();
    public static Task task_generic;
    public static Thread th_generic;
    public static Notifications notificationBuilder = Notifications.create();
    public static int resultprocess=0;
  static {
        
        // Register a custom default font
      GlyphFontRegistry.register("icomoon", GlyphFontRegistry.class.getResourceAsStream("icomoon.ttf") , 16);
      GlyphFontRegistry.register("FontAwesome", GlyphFontRegistry.class.getResourceAsStream("fontawesome-webfont.ttf") , 16);
       cb_sucursales.getItems().addAll("San Jose del Guaviare","Villavicencio","Acacías", "Granada");
       cb_centrocostos.getItems().addAll("Compras","Producción","Ventas", "Administración");
       for(EnumDocumentoSoporte eds: EnumDocumentoSoporte.values())
       {
           cb_documentossoporte.getItems().add(DocumentoSoporteController.getdocumentodoporte(eds));
       }
 
    }
public static  GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");
 public static GlyphFont icoMoon = GlyphFontRegistry.font("FontAwesome");
    //	private static char FAW_TRASH = '\uf014';
 public static char FAW_GEAR  = '\uf013';
    //private static char FAW_STAR  = '\uf005';
 public static char IM_BOLD        = '\ue027';
 public static char IM_UNDERSCORED = '\ue02b';
 public static char IM_ITALIC      = '\ue13e';

 public static final TextField puc = new TextField();
 public static SuggestionProvider<String> provider = SuggestionProvider.create(EntidadesStatic.possibleSuggestions_docsoporte);
 public static final TextField buscartercero = new TextField(); 
 private  Color randomColor ;
 public static  Glyph glyph_nuevo_32;
 public static  Glyph glyph_find_32;
 public static  Glyph glyph_guardar_32;
 public static  Glyph glyph_nuevo_16;
 public static  Glyph glyph_nuevo_16_2;
 public static  Glyph glyph_nuevo_16_3;
 public static  Glyph glyph_nuevo_14;
 public static  Glyph glyph_nuevo_14_2;
 public static  Glyph glyph_nuevo_14_3;
 public static  Glyph glyph_find_16;
 public static  Glyph glyph_guardar_16;
 public static  Glyph glyph_nuevo_20;
 public static  Glyph glyph_find_20;
 public static  Glyph glyph_guardar_20;
 public static  Glyph glyph_print_16;
 public static  Glyph glyph_print_20;
 public static  Glyph glyph_print_32;
 public static  Glyph glyph_excel_16;
 public static  Glyph glyph_excel_20;
 public static  Glyph glyph_excel_32;
   
    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(final Stage primaryStage) throws Exception {
    
    LoadParametrosGenerales();
    randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
    glyph_nuevo_16=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_CIRCLE.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
    glyph_nuevo_16_2=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_CIRCLE.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
    glyph_nuevo_16_3=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_CIRCLE.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
    glyph_nuevo_20=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_CIRCLE.getChar()).sizeFactor(2).color(randomColor).size(20).useGradientEffect();
    glyph_nuevo_32=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_CIRCLE.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
     glyph_nuevo_14=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_CIRCLE.getChar()).sizeFactor(2).color(randomColor).size(14).useGradientEffect();
    glyph_nuevo_14_2=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_CIRCLE.getChar()).sizeFactor(2).color(randomColor).size(14).useGradientEffect();
    glyph_nuevo_14_3=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_CIRCLE.getChar()).sizeFactor(2).color(randomColor).size(14).useGradientEffect();
 
    randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
    glyph_find_16=  IsoftConta.icoMoon.create(FontAwesome.Glyph.SEARCH.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
    glyph_find_20=  IsoftConta.icoMoon.create(FontAwesome.Glyph.SEARCH.getChar()).sizeFactor(2).color(randomColor).size(20).useGradientEffect();
    glyph_find_32=  IsoftConta.icoMoon.create(FontAwesome.Glyph.SEARCH.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
   
    randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
    glyph_guardar_16=  IsoftConta.icoMoon.create(FontAwesome.Glyph.SAVE.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
    glyph_guardar_20=  IsoftConta.icoMoon.create(FontAwesome.Glyph.SAVE.getChar()).sizeFactor(2).color(randomColor).size(20).useGradientEffect();
    glyph_guardar_32=  IsoftConta.icoMoon.create(FontAwesome.Glyph.SAVE.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
    randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
    glyph_print_16=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PRINT.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
    glyph_print_20=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PRINT.getChar()).sizeFactor(2).color(randomColor).size(20).useGradientEffect();
    glyph_print_32=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PRINT.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
    randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
    glyph_excel_16=  IsoftConta.icoMoon.create(FontAwesome.Glyph.FILE_EXCEL_ALT.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
    glyph_excel_20=  IsoftConta.icoMoon.create(FontAwesome.Glyph.FILE_EXCEL_ALT.getChar()).sizeFactor(2).color(randomColor).size(20).useGradientEffect();
    glyph_excel_32=  IsoftConta.icoMoon.create(FontAwesome.Glyph.FILE_EXCEL_ALT.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
   
         TextFields.bindAutoCompletion(puc, EntidadesStatic.as_puc); 
         AutoCompletionTextFieldBinding ac=  new AutoCompletionTextFieldBinding<>(buscartercero, provider);
         ac.setMinWidth(400);
         this.stage = primaryStage;
    //  primaryStage.getIcons().add(new Image("/isoftconta/logo.png"));
      //  Font.loadFont(FComprobanteCausacion.class.getResourceAsStream("fontawesome-webfont.ttf"), -1);
        ServiceLoader<FXServicioConfiguracion> configurationServiceLoader = ServiceLoader.load(FXServicioConfiguracion.class);

        areasMap = new ServicioScanner().discoverSamples();
        buildSampleTree(null);

        // simple layout: TreeView on left, sample area on right
        grid = new GridPane();
        grid.setPadding(new Insets(5, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        // --- left hand side
        // search box
        final TextField searchBox = new TextField();
        searchBox.setPromptText("Search");
        searchBox.getStyleClass().add("search-box");
        searchBox.textProperty().addListener(new InvalidationListener() {
            @Override public void invalidated(Observable o) {
                buildSampleTree(searchBox.getText());
            }
        });
        GridPane.setMargin(searchBox, new Insets(5, 0, 0, 0));
        grid.add(searchBox, 0, 0);
        
        // treeview
        serviciosTreeView = new TreeView<>(root);
        serviciosTreeView.setShowRoot(false);
        serviciosTreeView.getStyleClass().add("samples-tree");
        serviciosTreeView.setMinWidth(200);
        serviciosTreeView.setMaxWidth(200);
        serviciosTreeView.setCellFactory(new Callback<TreeView<Servicios>, TreeCell<Servicios>>() {
            @Override public TreeCell<Servicios> call(TreeView<Servicios> param) {
                return new TreeCell<Servicios>() {
                    @Override protected void updateItem(Servicios item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText("");
                        } else {
                            setText(item.getServicioNombre());
                        }
                    }
                };
            }
        });
        serviciosTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Servicios>>() {
            @Override public void changed(ObservableValue<? extends TreeItem<Servicios>> observable, TreeItem<Servicios> oldValue, TreeItem<Servicios> newServicio) {
                if (newServicio == null) {
                    return;
                } else if (newServicio.getValue() instanceof EmptyServicio) {
                    Servicios selectedSample = newServicio.getValue();
                    Area selectedProject = areasMap.get(selectedSample.getServicioNombre());
                    if(selectedProject != null) {
                        changeToWelcomeTab(selectedProject.getWelcomePage());
                    }
                    return;
                }
                selectedServicio = newServicio.getValue();
                changeSample();
            }
        });
        GridPane.setVgrow(serviciosTreeView, Priority.ALWAYS);
//        GridPane.setMargin(samplesTreeView, new Insets(5, 0, 0, 0));
        grid.add(serviciosTreeView, 0, 1);

        // right hand side
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.getStyleClass().add(TabPane.STYLE_CLASS_FLOATING);
        tabPane.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {
            @Override public void invalidated(Observable arg0) {
                updateTab();
            }
        });
        GridPane.setHgrow(tabPane, Priority.ALWAYS);
        GridPane.setVgrow(tabPane, Priority.ALWAYS);
        grid.add(tabPane, 1, 0, 1, 2);

        servicioTab = new Tab("Servicio");
       
        // by default we'll show the welcome message of first project in the tree
        // if no projects are available, we'll show the default page
        List<TreeItem<Servicios>> servicios = serviciosTreeView.getRoot().getChildren();
        if(!servicios.isEmpty()) {
            TreeItem<Servicios> firstservicio = servicios.get(0);
            serviciosTreeView.getSelectionModel().select(firstservicio);
        } else {
            changeToWelcomeTab(null);
        }

        // put it all together
       // new JMetro(JMetro.Style.LIGHT).applyTheme(grid);
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("fxservicio.css").toExternalForm());
     
       scene.getStylesheets().add(getClass().getResource("glyphfont/glyphfont.css").toExternalForm());
   //  scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        for (FXServicioConfiguracion fxServicioConfiguracion : configurationServiceLoader) {
        	String stylesheet = fxServicioConfiguracion.getSceneStylesheet();
        	if (stylesheet != null) {
            	scene.getStylesheets().add(stylesheet);
        	}
        }
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        
        // set width / height values to be 75% of users screen resolution
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setWidth(screenBounds.getWidth() * 0.75);
        primaryStage.setHeight(screenBounds.getHeight() * .75);
          
        primaryStage.setTitle("IsoftContaFX");
        primaryStage.show();

        serviciosTreeView.requestFocus();
    }

    protected void buildSampleTree(String searchText) {
        // rebuild the whole tree (it isn't memory intensive - we only scan
        // classes once at startup)
        root = new TreeItem<Servicios>(new EmptyServicio("IsoftContaFX"));
        root.setExpanded(true);
        
        for (String areanombre : areasMap.keySet()) {
            final Area area = areasMap.get(areanombre);
            if (area == null) continue;
            
            // now work through the project sample tree building the rest
            TreeNode n = area.getServicioTree().getRoot();
            root.getChildren().add(n.createTreeItem());
        }
        
        // with this newly built and full tree, we filter based on the search text
        if (searchText != null) {
           pruneServicioTree(root, searchText); 
           
           // FIXME weird bug in TreeView I think
           serviciosTreeView.setRoot(null);
           serviciosTreeView.setRoot(root);
        }
        
        // and finally we sort the display a little
        sort(root, (o1, o2) -> o1.getValue().getServicioNombre().compareTo(o2.getValue().getServicioNombre()));
    }
    
    private void sort(TreeItem<Servicios> node, Comparator<TreeItem<Servicios>> comparator) {
        node.getChildren().sort(comparator);
        for (TreeItem<Servicios> child : node.getChildren()) {
            sort(child, comparator);
        }
    }
    
    // true == keep, false == delete
    private boolean pruneServicioTree(TreeItem<Servicios> treeItem, String searchText) {
        // we go all the way down to the leaf nodes, and check if they match
        // the search text. If they do, they stay. If they don't, we remove them.
        // As we pop back up we check if the branch nodes still have children,
        // and if not we remove them too
        if (searchText == null) return true;
        
        if (treeItem.isLeaf()) {
            // check for match. Return true if we match (to keep), and false
            // to delete
            return treeItem.getValue().getServicioNombre().toUpperCase().contains(searchText.toUpperCase());
        } else {
            // go down the tree...
            List<TreeItem<Servicios>> toRemove = new ArrayList<>();
            
            for (TreeItem<Servicios> child : treeItem.getChildren()) {
                boolean keep = pruneServicioTree(child, searchText);
                if (! keep) {
                    toRemove.add(child);
                }
            }
            
            // remove the unrelated tree items
            treeItem.getChildren().removeAll(toRemove);
            
            // return true if there are children to this branch, false otherwise
            // (by returning false we say that we should delete this now-empty branch)
            return ! treeItem.getChildren().isEmpty();
        }
    }
    
    protected void changeSample() {
        if (selectedServicio == null) {
            return;
        }

        if (tabPane.getTabs().contains(welcomeTab)) {
            tabPane.getTabs().setAll(servicioTab);
        }
        
        updateTab();
    }
    
    private void updateTab() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        
        // we only update the selected tab - leaving the other tabs in their
        // previous state until they are selected
        if (selectedTab == servicioTab) {
            servicioTab.setContent(buildServicioTabContent(selectedServicio));
        } 
    }
    
    private String getResource(String resourceName, Class<?> baseClass) {
        Class<?> clz = baseClass == null? getClass(): baseClass;
        return getResource(clz.getResourceAsStream(resourceName));
    }

    private String getResource(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
        	String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            } 
            return sb.toString();
        } catch (IOException e) {
			e.printStackTrace();
			return "";
		}
    }
    
  

    private Node buildServicioTabContent(Servicios servicio) {
        return ServicioBase.buildServicio(servicio, stage);
    }

    private void changeToWelcomeTab(WelcomePage wPage) {
        if(null == wPage) {
            wPage = getDefaultWelcomePage();
        }
        welcomeTab = new Tab(wPage.getTitle());
        welcomeTab.setContent(wPage.getContent());
        tabPane.getTabs().setAll(welcomeTab);
    }
    
    private WelcomePage getDefaultWelcomePage() {
        // line 1
        Label welcomeLabel1 = new Label("Bienvenido FXIsoftConta!");
        welcomeLabel1.setStyle("-fx-font-size: 2em; -fx-padding: 0 0 0 5;");

        // line 2
        Label welcomeLabel2 = new Label(
                "Explore los avaluados Servicios de Contabilidad\n y otras interesantes areas "
                + "haciendo Click en las Opciones de la Izquierda.");
        welcomeLabel2.setStyle("-fx-font-size: 1.25em; -fx-padding: 0 0 0 5;");

        WelcomePage wPage = new WelcomePage("Welcome!", new VBox(5, welcomeLabel1, welcomeLabel2));
        return wPage;
    }

    
    
    public final GridPane getGrid() {
        return grid;
    }

    public final TabPane getTabPane() {
        return tabPane;
    }
    // should never be null
    public final Tab getWelcomeTab() {
        return welcomeTab;
    }

    public final Tab getSampleTab() {
        return servicioTab;
    }

    public void LoadParametrosGenerales()
    {
        
        EntidadesStatic.li_puc=CatalogoCuentasController.li_conpuc(null);
        DocumentoSoporteController.load_aspuc();
        EntidadesStatic.li_ciudades=CiudadesController.getAllRecords();
        CiudadesController.load_asciudades();
        EntidadesStatic.li_productos=ProductoController.getRecordsProductos(null);
        System.out.println("total prodcutos->"+EntidadesStatic.li_productos.size());
        CiudadesController.load_asproductos();
        EntidadesStatic.li_servicios=ProductoController.getRecordsServicios(null);
        CiudadesController.load_asservicios();
        
    }
    
  public static void   mensajenotificacion()
  {
     switch(resultprocess)
                   {
         case 0:
                       
                     notificationBuilder.text("Registro guardado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT).showConfirm();
                     break;
                
         case -1:     notificationBuilder.text("Error en el proceso")
                                       .position(Pos.TOP_RIGHT)
                                        .showError();
                     break;
                   
          case  1:                   notificationBuilder.text("Registro actualizado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                     break;
          case  2:                   notificationBuilder.text("Registro eliminado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                     break;
          case  3:                   notificationBuilder.text("Item agregado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                     break; 
          case  4:                   notificationBuilder.text("Item modificado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                     break;
          case  5:                   notificationBuilder.text("No se agregado proveedor")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                     break;
         case  6:                   notificationBuilder.text("No se agregado producto")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                     break; 
        case  7:                   notificationBuilder.text("La cantidad debe ser mayor a cero")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                     break;             
     }             
              
  }
}
