
package isoftconta.servicios.configuracion.contabilidad;


import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import isoftconta.ContabilidadFXServicios;
import isoftconta.IsoftConta;
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import isoftconta.servicios.TercerosController;
import isoftconta.util.StageGeneric;
import modelo.EntidadesStatic;
import modelo.Terceros;




public class FTerceros extends ContabilidadFXServicios {

   private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_generic = FXCollections.observableArrayList();
    Thread backgroundThread;
    private StackPane stack;
    private SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
   
    Scene scene = null;
    private ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemdelete;
    private TitledPane tp_generic;
    private Button bu_nuevo;
    private final StageGeneric stageterceros=new StageGeneric("isoftconta.servicios.configuracion.contabilidad.FICI_Terceros", "Terceros",false);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getServicioNombre() {
        return "Terceros";
    }

    

    
    
    
    

   

    @Override
    public Node getPanel(Stage stage) {
        char IM_NUEVO =0xFffe; //'\uf0fe';
        
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
          Glyph glyph_NUEVO=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_SQUARE.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
         
          System.out.println("glyph->"+glyph_NUEVO.getFontFamily());
          bu_nuevo=new Button("", glyph_NUEVO);
     
        bu_nuevo.setOnAction(eh->{
            try {
                show();
            } catch (Exception ex) {
            } 
        });
 ToolBar toolBar=new ToolBar( bu_nuevo);
                
                  
        tp_generic = new TitledPane();
        tp_generic.setText("Administrar Terceros");
        tp_generic.setCollapsible(false);
        
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

        

        
        bu_nuevo = new Button("");
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                EntidadesStatic.es_terceros = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_generic = new TableView();

        TableColumn tipodoc = new TableColumn();
        tipodoc.setMinWidth(150);
        tipodoc.setText("Tipo Documento");
        tipodoc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Terceros, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Terceros, String> terceros) {

                return new SimpleStringProperty(terceros.getValue().getTipoidentificacion()!=null?terceros.getValue().getTipoidentificacion().equals("0")?"Nit":"Cédula":"N/A");

            }
        });
        
        TableColumn nodocumento = new TableColumn();
        nodocumento.setMinWidth(150);
        nodocumento.setText("N° Documento");
        nodocumento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Terceros, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Terceros, String> terceros) {

                return new SimpleStringProperty(terceros.getValue().getNo_ident());

            }
        });
        TableColumn nombre = new TableColumn();
        nombre.setMinWidth(350);
        nombre.setText("Nombres");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Terceros, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Terceros, String> terceros) {

                return new SimpleStringProperty(terceros.getValue().getNombres());

            }
        });
        TableColumn telefono = new TableColumn();
        telefono.setMinWidth(150);
        telefono.setText("Teléfono");
        telefono.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Terceros, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Terceros, String> terceros) {

                return new SimpleStringProperty(terceros.getValue().getCelular());

            }
        });
        tv_generic.getColumns().addAll(tipodoc, nodocumento, nombre, telefono);
        //ta_librodiario.setMinWidth(650);

        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tv_generic.setMinHeight(577);
        tv_generic.setContextMenu(contextMenu);
         
        // gp_generico.setMinWidth(960);
        gp_generico.addRow(0,la_buscar,buscar,  toolBar);
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
            ol_generic.addAll(TercerosController.getrecordsterceros(buscar.getText()));
            tv_generic.setItems(ol_generic);
    }

  
private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
     stageterceros.showmodal();
    }

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            EntidadesStatic.es_terceros = (Terceros) tv_generic.getSelectionModel().getSelectedItem();
            return true;
        } else {
            return false;
        }
    }

   
}