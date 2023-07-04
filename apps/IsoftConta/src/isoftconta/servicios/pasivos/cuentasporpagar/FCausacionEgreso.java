
package isoftconta.servicios.pasivos.cuentasporpagar;


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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
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
import static isoftconta.servicios.activos.cuentasporcobrar.FCausacionIngreso.getRecords;
import isoftconta.util.StageGeneric;
import modelo.DocumentoSoporte;
import modelo.EntidadesStatic;
import modelo.LibroAuxiliar;




public class FCausacionEgreso extends ContabilidadFXServicios {

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
    private Button bu_nuevo;
    private final StageGeneric stagecausacionegreso=new StageGeneric("isoftconta.servicios.pasivos.cuentasporpagar.FICI_CausacionEgreso", "Comprobante Causación Gasto",true);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getServicioNombre() {
        return "Causación de Gastos";
    }

    

    
    
    
    

   

    @Override
    public Node getPanel(Stage stage) {
        char IM_NUEVO = '\uf0fe';
        
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
          Glyph glyph_NUEVO=  IsoftConta.icoMoon.create(IM_NUEVO).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
         bu_nuevo=new Button("", glyph_NUEVO);
     
        bu_nuevo.setOnAction(eh->{
            try {
                   EntidadesStatic.es_documentosoporte = null;
                show();
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
        });
 ToolBar toolBar=new ToolBar( bu_nuevo);
                
                  
        tp_generic = new TitledPane();
        tp_generic.setText("Administrar Comprobantes de Causación de Gastos");
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
        });   itemnuevo = new MenuItem("Nuevo");
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
        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemdelete);
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
        fechaelab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentoSoporte, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DocumentoSoporte, String> documentosoporte) {

                return new SimpleStringProperty(isoftconta.util.UtilDate.formatodiamesyear(documentosoporte.getValue().getFechaelaboracion()));

            }
        });
        
        TableColumn nocomprobante = new TableColumn();
        nocomprobante.setMinWidth(150);
        nocomprobante.setText("N° Comprobante");
        nocomprobante.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentoSoporte, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DocumentoSoporte, String> documentosoporte) {

                return new SimpleStringProperty(documentosoporte.getValue().getNocomprobantecerosizquierda());

            }
        });

        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(150);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentoSoporte, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DocumentoSoporte, String> documentosoporte) {

                return new SimpleStringProperty(documentosoporte.getValue().getNumerodocumentosoporte()!=null?documentosoporte.getValue().getNumerodocumentosoporte():"0");

            }
        });
        TableColumn valor = new TableColumn();
        valor.setMinWidth(150);
        valor.setText("Valor");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentoSoporte, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DocumentoSoporte, String> documentosoporte) {
                  try
                  {
                      documentosoporte.getValue().calculartotalesdebehaber();
                return new SimpleStringProperty(documentosoporte.getValue().getTotaldebe().toString());
                  }catch(Exception e)
                  {
                   return new SimpleStringProperty("0");
               
                   
                  }
            }
        });
        TableColumn cliente = new TableColumn();
        cliente.setMinWidth(250);
        cliente.setText("Cliente");
        cliente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DocumentoSoporte, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DocumentoSoporte, String> documentosoporte) {
                try
                {
                return new SimpleStringProperty(documentosoporte.getValue().getLi_libroauxiliar().size()>0?documentosoporte.getValue().getLi_libroauxiliar().get(0).getTerceros().getNombres():"NT");
}catch(Exception e)
                {
                   
                       return new SimpleStringProperty("NT");
              
               
                }
            }
        });


        tv_generic.getColumns().addAll(fechaelab, nocomprobante, valor,cliente );
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
            ol_generic.addAll(DocumentoSoporteController.getRecords(dp_from.getValue().toString(), dp_to.getValue().toString(),EnumDocumentoSoporte.COMPROBANTECAUSACIONEGRESO14.ordinal()));
            tv_generic.setItems(ol_generic);
    }

  
private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
     stagecausacionegreso.showmodal();
    }

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            EntidadesStatic.es_documentosoporte = (DocumentoSoporte) tv_generic.getSelectionModel().getSelectedItem();
            return true;
        } else {
            return false;
        }
    }

   
}