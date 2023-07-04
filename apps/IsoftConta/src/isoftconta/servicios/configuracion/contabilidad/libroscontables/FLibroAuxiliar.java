
package isoftconta.servicios.configuracion.contabilidad.libroscontables;


import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import isoftconta.ContabilidadFXServicios;
import isoftconta.IsoftConta;
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import isoftconta.servicios.LibroAuxiliarController;
import isoftconta.util.StageGeneric;
import modelo.EntidadesStatic;
import modelo.LibroAuxiliar;




public class FLibroAuxiliar extends ContabilidadFXServicios {

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
    private Button bu_restaurarfechasyterceros=new Button();
    private static TextField tercero=new TextField();
    private static TextField codigopuc=new TextField();
    private static TextField totaldebe=new TextField();
    private static TextField totalhaber=new TextField();
    private static TextField totalsaldo=new TextField();
    private final StageGeneric stagecausacioningreso=new StageGeneric("isoftconta.servicios.configuracion.contabilidad.libroscontables.FICI_LibroAuxiliar", "Libro Auxiliar",true);
    private ToolBar toolBar;
    private  Stage stagegeneric= new Stage(StageStyle.DECORATED);
    private  Task<Void>   task_generic;
    private  Thread  thimport_generic;
    private  Task<Void>   task_generic2;
    private  Thread  thimport_generic2;
    private ProgressBar pb ;
    private ProgressIndicator pi;
    private GridPane gp=new GridPane();
    private StackPane stackPane;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getServicioNombre() {
        return "Libro Auxiliar";
    }

    @Override
    public Node getPanel(Stage stage) {
        bu_restaurarfechasyterceros.setTooltip(new Tooltip("Restaurar Fechas y Terceros "));
        tercero.setPromptText("Buscar por Tercero");
        tercero.setMaxWidth(120);
        tercero.textProperty().addListener((observable, oldValue, newValue) -> {
            getRecords();
        });
        codigopuc.textProperty().addListener((observable, oldValue, newValue) -> {
            getRecords();
        });
        codigopuc.setPromptText("Buscar por Código Cuenta");
        codigopuc.setMaxWidth(120);
        char IM_NUEVO =0xFffe; //'\uf0fe';
          IsoftConta.cb_documentossoporte.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
      
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
          Glyph glyph_NUEVO=  IsoftConta.icoMoon.create(FontAwesome.Glyph.PLUS_SQUARE.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
            randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
          Glyph glyph_restaurar=  IsoftConta.icoMoon.create(FontAwesome.Glyph.DIAMOND.getChar()).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
      
          System.out.println("glyph->"+glyph_NUEVO.getFontFamily());
          bu_nuevo=new Button("", glyph_NUEVO);
     
        bu_nuevo.setOnAction(eh->{
            try {
                show();
            } catch (Exception ex) {
             } 
        });
         bu_nuevo=new Button("", glyph_NUEVO);
       bu_restaurarfechasyterceros.setGraphic(glyph_restaurar);
        bu_restaurarfechasyterceros.setOnAction(eh->{
            try {
             
                
                showAvancerestaurar();
                avancerestauracion();
                
            } catch (Exception ex) {
             } 
        });
  
                
                  
        tp_generic = new TitledPane();
        tp_generic.setText("Administrar Libro Auxiliar");
        tp_generic.setCollapsible(false);
        dp_from = new DatePicker(LocalDate.now());
        dp_from.setPromptText(" Fecha Desde");
        dp_from.setOnAction(eh->{
           
                 getRecords();
          
        });
        dp_to = new DatePicker(LocalDate.now());
        dp_to.setPromptText(" Fecha Hasta");
         dp_to.setOnAction(eh->{
           
                getRecords();
            
        });
      
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
              
                getRecords();
            
        });

        

        
        bu_nuevo = new Button("");
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                EntidadesStatic.es_documentosoporte = null;
                show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gp_generico = new GridPane();
        tv_generic = new TableView();

        TableColumn fechaelab = new TableColumn();
        fechaelab.setMinWidth(100);
        fechaelab.setText("Fecha Elab.");
        fechaelab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> libroauxiliar) {

                return new SimpleStringProperty(isoftconta.util.UtilDate.formatodiamesyear(libroauxiliar.getValue().getFechaelaboracion()));

            }
        });
        
        TableColumn nocomprobante = new TableColumn();
        nocomprobante.setMinWidth(150);
        nocomprobante.setText("N° Comprobante");
        nocomprobante.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> libroauxiliar) {

                return new SimpleStringProperty(libroauxiliar.getValue().getDocumentoSoporte().getNocomprobantecerosizquierda());

            }
        });
           TableColumn ctercero = new TableColumn();
        ctercero.setMinWidth(150);
        ctercero.setText("Tercero");
        ctercero.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> libroauxiliar) {
                try
                {
                return new SimpleStringProperty(libroauxiliar.getValue().getTerceros()!=null?libroauxiliar.getValue().getTerceros().getNombres():libroauxiliar.getValue().getDocumentoSoporte().getClientes().getNombres());
                }catch(Exception e)
                {
                    return new SimpleStringProperty("SN");
                }
            }
        });
        TableColumn codcuenta = new TableColumn();
        codcuenta.setMinWidth(100);
        codcuenta.setText("Código");
        codcuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> libroauxiliar) {

                return new SimpleStringProperty(libroauxiliar.getValue().getConPuc().getCodigo());

            }
        });
        TableColumn cuenta = new TableColumn();
        cuenta.setMinWidth(100);
        cuenta.setText("Cuenta");
        cuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> libroauxuliar) {

                return new SimpleStringProperty(libroauxuliar.getValue().getConPuc().getNombre());

            }
        });
        TableColumn valordebito = new TableColumn();
        valordebito.setMinWidth(150);
        valordebito.setText("Débito");
        valordebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> libroauxiliar) {

                return new SimpleStringProperty(libroauxiliar.getValue().getDebe().toString());

            }
        });
        TableColumn valorcredito = new TableColumn();
        valorcredito.setMinWidth(150);
        valorcredito.setText("Crédito");
        valorcredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> libroauxiliar) {

                return new SimpleStringProperty(libroauxiliar.getValue().getHaber().toString());

            }
        });
        tv_generic.getColumns().addAll(fechaelab, nocomprobante,ctercero, codcuenta, cuenta,valordebito,valorcredito );
        //ta_librodiario.setMinWidth(650);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tv_generic.setMinHeight(577);
        tv_generic.setContextMenu(contextMenu);
         
        // gp_generico.setMinWidth(960);
        gp_generico.addRow(0, dp_from, dp_to);
        gp_generico.add(tercero, 2,0);
        gp_generico.add(codigopuc, 3,0);
        gp_generico.add(bu_restaurarfechasyterceros, 4,0);
        gp_generico.add(tv_generic, 0, 1, 5, 1);
        gp_generico.addRow(2,totaldebe,totalhaber,totalsaldo);
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
       // gp_generico.setMinWidth(1345);
        tp_generic.setContent(gp_generico);
        stack.setAlignment(Pos.TOP_CENTER);
        //tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic);
     
           getRecords();
       
   
        return stack;
    }
     public static void getRecords(){
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
            try
            {
            List<LibroAuxiliar> _li_libroauxiliar=LibroAuxiliarController.getRecords(dp_from.getValue().toString(), dp_to.getValue().toString(),tercero.getText(),codigopuc.getText());
                BigDecimal bd_totaldebe=BigDecimal.ZERO;
                BigDecimal bd_totalhaber=BigDecimal.ZERO;
                BigDecimal bd_totalsaldo=BigDecimal.ZERO;
            for(LibroAuxiliar la: _li_libroauxiliar)
            {
               bd_totaldebe=bd_totaldebe.add(la.getDebe());
               bd_totalhaber=bd_totalhaber.add(la.getHaber());
               
            }
            bd_totalsaldo=bd_totaldebe.subtract(bd_totalhaber);
            if(bd_totalsaldo.compareTo(BigDecimal.ZERO)==-1)
            {
                bd_totalsaldo=bd_totalsaldo.multiply(BigDecimal.valueOf(-1));
                totalsaldo.setText("Saldo Crédito="+ bd_totalsaldo.toString());
            }
            else
            {
               totalsaldo.setText("Saldo Débito="+ bd_totalsaldo.toString());  
            }
            totaldebe.setText("Total Débito="+bd_totaldebe.toString());
            totalhaber.setText("Total Crédito="+bd_totalhaber.toString());
            ol_generic.addAll(_li_libroauxiliar);
            tv_generic.setItems(ol_generic);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            
    }

  
private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
     stagecausacioningreso.showmodal();
    }

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            EntidadesStatic.es_libroauxiliar = (LibroAuxiliar) tv_generic.getSelectionModel().getSelectedItem();
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
   private void showAvancerestaurar() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  
 
  gp.getChildren().clear();
  pb=null;
  pi=null;
  pb=new ProgressBar(0);
  pi=new ProgressIndicator(1);
  gp.addRow(0, pb,pi);
 
  
    stackPane=null;
      stackPane=new StackPane(gp);
    Scene scenegeneric=null;
    scenegeneric=new Scene(stackPane, Color.TRANSPARENT);
      
        if (stagegeneric.isShowing())
       {
           stagegeneric.close();
       }      

                stagegeneric.sizeToScene();
          
                stagegeneric.centerOnScreen();
                stagegeneric.setScene(scenegeneric);
                 
           
                stagegeneric.show();
  }   
    private void avancerestauracion() throws ParseException, InterruptedException
    {
         //  System.out.println("Comprobante:"+ EntidadesStatic.es_libroauxiliar.getDocumentoSoporte().getNo_consecutivo());
 
       gp.setAlignment(Pos.CENTER);
       LibroAuxiliarController.avance_progress=0.0;
         task_generic2=new Task<Void>() {
        
            @Override
            protected Void call() throws Exception {
             
           LibroAuxiliarController.restaurarfechasyterceros();
           
          
         
        
        
           return null;
           
             }
         };
            
             thimport_generic2 = new Thread(task_generic2);
             thimport_generic2.setDaemon(true);
             thimport_generic2.start();
       task_generic=new Task<Void>() {
        
            @Override
            protected Void call() throws Exception {
             System.out.println("estado progress:"+LibroAuxiliarController.estado_progress);
             double i=0;
        while(LibroAuxiliarController.estado_progress)
        {  
            i=LibroAuxiliarController.avance_progress;
            System.out.println("avance:"+i);
            pb.setProgress(LibroAuxiliarController.avance_progress);
            pi.setProgress(LibroAuxiliarController.avance_progress);
        
        }
            
        
           
          
           pb.setProgress(1);
           pi.setProgress(1);
           return null;
           
             }
         };
            
             thimport_generic = new Thread(task_generic);
                
  
        thimport_generic.setDaemon(true);
        thimport_generic.start();
        
         
    }
}