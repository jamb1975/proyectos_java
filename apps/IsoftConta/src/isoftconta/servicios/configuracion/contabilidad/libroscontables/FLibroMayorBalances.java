
package isoftconta.servicios.configuracion.contabilidad.libroscontables;


import impl.org.controlsfx.ReflectionUtils;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import isoftconta.ContabilidadFXServicios;
import isoftconta.IsoftConta;
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import isoftconta.glyphfont.GlyphFont;
import isoftconta.glyphfont.GlyphFontRegistry;
import isoftconta.servicios.DocumentoSoporteController;
import isoftconta.servicios.EnumDocumentoSoporte;
import isoftconta.servicios.LibroAuxiliarController;
import isoftconta.servicios.LibroMayorBalancesController;
import isoftconta.util.ImpresionDocumentoSoporte;
import isoftconta.util.StageGeneric;
import isoftconta.util.UtilDate;
import modelo.DocumentoSoporte;
import modelo.EntidadesStatic;
import modelo.LibroAuxiliar;
import modelo.LibroMayorBalances;
import org.controlsfx.control.Notifications;




public class FLibroMayorBalances extends ContabilidadFXServicios {

   private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_generic = FXCollections.observableArrayList();
    Thread backgroundThread;
    private StackPane stack;
    private SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
    private static DatePicker dp_from;
    private static DatePicker dp_to;
    
    Scene scene = null;
    private ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemdelete;
    private TitledPane tp_generic;
    private Button bu_trasladaralibromayor;
    private Button bu_balanceprueba;
    private Button bu_estsituacionfinanciera;
    private Button bu_estadoresultadosintegrales;
    private final StageGeneric stagebalanceprueba=new StageGeneric("isoftconta.servicios.configuracion.contabilidad.libroscontables.FICI_BalancePrueba", "Balance de Prueba",true);
    private static Notifications notificationBuilder = Notifications.create();
  
    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getServicioNombre() {
        return "Libro Mayor";
    }

    

    
    
    
    

   

    @Override
    public Node getPanel(Stage stage) {
        char IM_NUEVO =0xFffe; //'\uf0fe';
          IsoftConta.cb_documentossoporte.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
      
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_NUEVO=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_SQUARE.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_trasladasalibromayor=  IsoftConta.icoMoon.create(FontAwesome.Glyph.BOOK.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_balanceprueba=  IsoftConta.icoMoon.create(FontAwesome.Glyph.ANCHOR.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_estadosituacionfinanciera=  IsoftConta.icoMoon.create(FontAwesome.Glyph.BAR_CHART.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_estadoresultadosintegrales=  IsoftConta.icoMoon.create(FontAwesome.Glyph.CUBES.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
 
        bu_trasladaralibromayor = new Button("",glyph_trasladasalibromayor);
        bu_trasladaralibromayor.setTooltip(new Tooltip("Trasladar a Libro Mayor"));
        bu_trasladaralibromayor.setOnAction(e
                -> {
            try {
               int year=UtilDate.getyearfecha(UtilDate.localdatetodate(dp_from.getValue()));
               int mes=UtilDate.getmesfecha(UtilDate.localdatetodate(dp_from.getValue()));
               int yearf=UtilDate.getyearfecha(UtilDate.localdatetodate(dp_to.getValue()));
               int mesf=UtilDate.getmesfecha(UtilDate.localdatetodate(dp_to.getValue()));
               System.out.println("mes->"+mes+"mes f->"+mesf);
               if(year==yearf && mes==mesf)
               {
                  LibroMayorBalancesController.trasladoalibromayor(dp_from.getValue().toString(),dp_to.getValue().toString()); 
               }
               else
               {
                    notificationBuilder.text("¡El Mes y El Año deben ser iguales tanto en la fecha Inicial como Final!")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
               }
                getRecords();
                   
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            });
        bu_balanceprueba = new Button("",glyph_balanceprueba);
        bu_balanceprueba.setTooltip(new Tooltip("Balance de Prueba"));
        bu_balanceprueba.setOnAction(e
                -> {
            try {
                   ImpresionDocumentoSoporte.crearbalancedeprueba();
                   show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            });
        bu_estsituacionfinanciera = new Button("",glyph_estadosituacionfinanciera);
        bu_estsituacionfinanciera.setTooltip(new Tooltip("Estado de Situación Financiera"));
        bu_estsituacionfinanciera.setOnAction(e
                -> {
            try {
                  ImpresionDocumentoSoporte.fpdf_estadosituacionfinanciera();
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            });
        bu_estadoresultadosintegrales = new Button("",glyph_estadoresultadosintegrales);
        bu_estadoresultadosintegrales.setTooltip(new Tooltip("Estado de Resultados Integrales"));
        bu_estadoresultadosintegrales.setOnAction(e
                -> {
            try {
                   ImpresionDocumentoSoporte.fpdf_estadoresultadosintegrales();
            } catch (Exception ex) {
               ex.printStackTrace();
            }
            });
 ToolBar toolBar=new ToolBar( bu_trasladaralibromayor,bu_balanceprueba,bu_estsituacionfinanciera,bu_estadoresultadosintegrales);
                
                  
        tp_generic = new TitledPane();
        tp_generic.setText("Administrar Libro Mayor");
        tp_generic.setCollapsible(false);
        dp_from = new DatePicker(LocalDate.now());
        dp_from.setPromptText(" Fecha Desde");
        dp_from.setOnAction(eh->{
            try {
                 getRecords();
            } catch (ParseException ex) {
             }
        });
        dp_to = new DatePicker(LocalDate.now());
        dp_to.setPromptText(" Fecha Hasta");
         dp_to.setOnAction(eh->{
            try {
                getRecords();
            } catch (ParseException ex) {
             }
        });
       IsoftConta.cb_documentossoporte.getSelectionModel().select(0);
        itemnuevo = new MenuItem("Nuevo");
        
        itemnuevo.setOnAction(e -> {
            try {
               
                show();
            } catch (Exception x) {

            }
        });
        
        itemeditar = new MenuItem("Editar");
        itemeditar.setOnAction(e -> {

            try {
                if (checkregistroexistente()) {
                    show();
                }
            } catch (Exception x) {
                x.printStackTrace();
            }
        });
      
        itemdelete = new MenuItem("Eliminar");
        itemdelete.setOnAction(e -> {

            try {

            } catch (Exception ex) {

            }
        });
        contextMenu = new ContextMenu();
        stack = new StackPane();
          
     
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(100);
        buscar = new TextField();
        buscar.setOnKeyReleased(e -> {
            try {
                getRecords();
            } catch (ParseException ex) {

            }
        });
     

        gp_generico = new GridPane();
        tv_generic = new TableView();

        TableColumn fechaelab = new TableColumn();
        fechaelab.setMinWidth(100);
        fechaelab.setText("Fecha Elab.");
        fechaelab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libromayor) {

                return new SimpleStringProperty(isoftconta.util.UtilDate.formatodiamesyear(libromayor.getValue().getFechaelaboracion()));

            }
        });
         TableColumn cuenta = new TableColumn();
        cuenta.setMinWidth(100);
        cuenta.setText("Cuenta");
        cuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libromayor) {

                return new SimpleStringProperty(libromayor.getValue().getConPuc().getCodigo()+" "+libromayor.getValue().getConPuc().getNombre());

            }
        });
        cuenta.setCellFactory(column -> {
    return new TableCell<LibroMayorBalances, String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
         
            if(this.getTableRow().getItem()!=null)
            {
            if(!this.getTableRow().getItem().getDetalle().equals("Comprobante Saldos Iniciales")) 
            {
                   setText(item);
                    setStyle("-fx-text-fill: black;");
            }
            else
            { 
              if(this.getTableRow().getItem().getDetalle().equals("Comprobante Saldos Iniciales")) 
            {
                   
               setTextFill(Color.WHITE);
               setStyle("-fx-background-color: green;-fx-text-fill: white;");
               setText(this.getTableRow().getItem().getConPuc().getCodigo()+" "+this.getTableRow().getItem().getConPuc().getNombre());     
              }
             
              
             
               }
            }
            else
            {
                setText("");
                    setStyle(""); 
            }
        }
        };
    }
);
         TableColumn saldodebitoanterior = new TableColumn();
        saldodebitoanterior.setMinWidth(100);
        saldodebitoanterior.setText("Saldo Débito Anterior");
        saldodebitoanterior.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libromayor) {

                return new SimpleStringProperty(libromayor.getValue().getSaldoanteriordebito().toString());

            }
        });
        
       TableColumn saldocreditoanterior = new TableColumn();
        saldocreditoanterior.setMinWidth(100);
        saldocreditoanterior.setText("Saldo Crédito Anterior");
        saldocreditoanterior.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libromayor) {

                return new SimpleStringProperty(libromayor.getValue().getSaldoanteriorcredito().toString());

            }
        });
        TableColumn movdebito = new TableColumn();
        movdebito.setMinWidth(100);
        movdebito.setText("Mov. Débito");
        movdebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libromayor) {

                return new SimpleStringProperty(libromayor.getValue().getMovimientosdebito().toString());

            }
        });
        TableColumn movcredito = new TableColumn();
        movcredito.setMinWidth(100);
        movcredito.setText("Mov. Crédito");
        movcredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libromayor) {

                return new SimpleStringProperty(libromayor.getValue().getMovimientoscredito().toString());

            }
        });
         TableColumn saldodebito= new TableColumn();
        saldodebito.setMinWidth(100);
        saldodebito.setText("Saldo Débito");
        saldodebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libromayor) {

                return new SimpleStringProperty(libromayor.getValue().getSaldodebito().toString());

            }
        });
        TableColumn saldocredito= new TableColumn();
        saldocredito.setMinWidth(100);
        saldocredito.setText("Saldo Crédito");
        saldocredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libromayor) {

                return new SimpleStringProperty(libromayor.getValue().getSaldocredito().toString());

            }
        });
        tv_generic.getColumns().addAll(fechaelab,cuenta, saldodebitoanterior, saldocreditoanterior, movdebito,movcredito,saldodebito,saldocredito);
        //ta_librodiario.setMinWidth(650);

        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tv_generic.setMinHeight(577);
        tv_generic.setContextMenu(contextMenu);
         
        // gp_generico.setMinWidth(960);
        gp_generico.addRow(0, dp_from, dp_to, toolBar);
        gp_generico.add(tv_generic, 0, 3, 3, 1);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
       // gp_generico.setMinWidth(1345);
        tp_generic.setContent(gp_generico);
        stack.setAlignment(Pos.TOP_CENTER);
        //tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic);
       try {
           getRecords();
       } catch (ParseException ex) {
   }
        return stack;
    }
     public static void getRecords() throws ParseException {
        //colocamos los datos
      
      /*   JaviConta.task_generic = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            
      

                return null;
            }
        };
        JaviConta.th_generic = new Thread(JaviConta.task_generic);
        JaviConta.th_generic.setDaemon(true);
        JaviConta.th_generic.start();*/
            ol_generic.clear();
            ol_generic.addAll(LibroMayorBalancesController.getRecords(dp_from.getValue().toString(), dp_to.getValue().toString()));
            tv_generic.setItems(ol_generic);
    }

  
private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
     stagebalanceprueba.showmodal();
    }

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            EntidadesStatic.es_libromayorbalances = (LibroMayorBalances) tv_generic.getSelectionModel().getSelectedItem();
            return true;
        } else 
        {
            return false;
        }
    }
 public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {
            
                       
            
            getRecords();
        } catch (Exception e) {
        }
    }
   
}