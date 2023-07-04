
package isoftconta.servicios.activos.efectivo;


import impl.org.controlsfx.ReflectionUtils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.CheckBoxTableCell;
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
import static isoftconta.IsoftConta.puc;
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import isoftconta.glyphfont.GlyphFont;
import isoftconta.glyphfont.GlyphFontRegistry;
import isoftconta.servicios.DocumentoSoporteController;
import isoftconta.servicios.EnumDocumentoSoporte;
import isoftconta.servicios.activos.cuentasporcobrar.FCausacionIngreso;
import isoftconta.util.StageGeneric;
import isoftconta.util.UtilDate;
import modelo.DocumentoSoporte;
import modelo.EntidadesStatic;
import modelo.LibroAuxiliar;
import org.controlsfx.control.Notifications;




public class FICI_ComprobanteEgresoMasivo extends Application {

   private GridPane gp_generico;
    private static TableView tv_generic;
    private Label la_buscar;
    private static TextField buscar;
    private static ObservableList ol_generic = FXCollections.observableArrayList();
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
    private SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
    private String appClass;
    private Class clz;
    private Object app;
    private Parent parent;
     private static DatePicker dp_fechadesde=new DatePicker();
    private static DatePicker dp_fechahasta=new DatePicker();
    private static DatePicker dp_fechaelaboracion=new DatePicker();;
    
    Scene scene = null;
   
    private ImageView img;
    private TitledPane tp_generic;
    private Button bu_ejecutar;
    private StageGeneric stagecomprobanteingreso=new StageGeneric("isoftconta.servicios.activos.efectivo.FICI_ComprobanteEgreso", "Comprobante de  Egreso",true);
    private static Notifications notificationBuilder = Notifications.create();
    private BigDecimal totalingresos=BigDecimal.ZERO;
    private String inicionofactura="";
    private String finnofactura="";
    private static TextField tf_nocomprobante=new TextField();
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    

    
    
    
    

   

     public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException, InterruptedException {
        puc.setMaxWidth(100);
        puc.setMinWidth(100);
        tf_nocomprobante.setMaxWidth(100);
        tf_nocomprobante.setMinWidth(100);
        dp_fechadesde.setMaxWidth(120);
        dp_fechadesde.setMinWidth(120);
        dp_fechaelaboracion.setMaxWidth(120);
        dp_fechaelaboracion.setMinWidth(120);
        dp_fechahasta.setMaxWidth(120);
        dp_fechahasta.setMinWidth(120);
        puc.setPromptText("Cuenta Puc");
        puc.setOnAction(e -> {
            
           bu_ejecutar.requestFocus();

        });
         
       puc.focusedProperty().addListener(new ChangeListener<Boolean>()
      {
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
     DocumentoSoporteController.findpucporcodigo(puc.getText());
    }
    });
        dp_fechaelaboracion.setValue(LocalDate.now());
        dp_fechahasta.setValue(LocalDate.now());
        dp_fechadesde.setValue(LocalDate.now());
        tf_nocomprobante.setPromptText("N° Comprobante");
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
          Glyph glyph_NUEVO=  IsoftConta.fontAwesome.create(FontAwesome.Glyph.CHECK_CIRCLE.getChar()).sizeFactor(2).color(randomColor).useGradientEffect();
         bu_ejecutar=new Button("", glyph_NUEVO);
     
        bu_ejecutar.setOnAction(eh->{
            try {
                aplicar_ingresos();
            } catch (ParseException ex) {
                Logger.getLogger(FICI_ComprobanteEgresoMasivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       ToolBar toolBar=new ToolBar( );
                
                  
        tp_generic = new TitledPane();
        tp_generic.setText("Egresos a Pagar");
        tp_generic.setCollapsible(false);
        dp_fechaelaboracion = new DatePicker(LocalDate.now());
        dp_fechaelaboracion.setPromptText("Fecha Elab");
       
        stack = new StackPane();
          
     
        la_buscar = new Label("Buscar: ");
        la_buscar.setMinWidth(100);
        buscar = new TextField();
       buscar.textProperty().addListener((observable, oldValue, newValue) -> {
           /* try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(FICI_ComprobanteIngresoMasivo.class.getName()).log(Level.SEVERE, null, ex);
            }*/
       });
  buscar.setOnAction(e->{
            try {
                getRecords();
            } catch (ParseException ex) {
                Logger.getLogger(FICI_ComprobanteEgresoMasivo.class.getName()).log(Level.SEVERE, null, ex);
            }
  });
        dp_fechadesde.setOnAction(eh->{
           
        });
         dp_fechahasta.setOnAction(eh->{
            
        });

        
        

        gp_generico = new GridPane();
        tv_generic = new TableView();

        TableColumn fechaelab = new TableColumn();
        fechaelab.setMinWidth(100);
        fechaelab.setText("Fecha Elab.");
        fechaelab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> documentosoporte) {

                return new SimpleStringProperty(isoftconta.util.UtilDate.formatodiamesyear(documentosoporte.getValue().getFechaelaboracion()));

            }
        });
        
        TableColumn nocomprobante = new TableColumn();
        nocomprobante.setMinWidth(100);
        nocomprobante.setText("N° Comprobante");
        nocomprobante.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> documentosoporte) {

                return new SimpleStringProperty(documentosoporte.getValue().getDocumentoSoporte().getNocomprobantecerosizquierda());

            }
        });

        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(100);
        nofactura.setText("Id");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> documentosoporte) {

                return new SimpleStringProperty(documentosoporte.getValue().getId().toString());

            }
        });
        TableColumn valor = new TableColumn();
        valor.setMinWidth(150);
        valor.setText("Valor Egreso");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> documentosoporte) {

                return new SimpleStringProperty(documentosoporte.getValue().getHaber().toString());

            }
        });
        
        TableColumn cliente = new TableColumn();
        cliente.setMinWidth(200);
        cliente.setText("Tercero");
        cliente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> documentosoporte) {
                try
                {
                return new SimpleStringProperty(documentosoporte.getValue().getTerceros().getNombres());
                }catch(Exception e)
                {
                     return new SimpleStringProperty(documentosoporte.getValue().getDocumentoSoporte().getClientes().getNombres());
                }
            }
        });
          
        TableColumn descrip = new TableColumn();
        descrip.setMinWidth(150);
        descrip.setText("Descripción");
        descrip.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> documentosoporte) {
                try
                {
                return new SimpleStringProperty(documentosoporte.getValue().getDescripcion());
                }catch(Exception e)
                {
                     return new SimpleStringProperty(documentosoporte.getValue().getDocumentoSoporte().getConcepto());
                }
            }
        });
        TableColumn<LibroAuxiliar, Boolean> cingresar = new TableColumn<>("Egreso");
          cingresar.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, Boolean>, ObservableValue<Boolean>>() {
        
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<LibroAuxiliar, Boolean> param) {
                LibroAuxiliar ingresar = param.getValue();
                 SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(ingresar.isIngresar());
 
                // Note: singleCol.setOnEditCommit(): Not work for
                // CheckBoxTableCell.
 
                // When "Single?" column change.
                booleanProp.addListener(new  ChangeListener<Boolean>() {
                 @Override
                 public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) {
                        ingresar.setIngresar(newValue);
                        
                        
                    }
                });
                return booleanProp;
            }
        });
         cingresar.setCellFactory(new Callback<TableColumn<LibroAuxiliar, Boolean>,TableCell<LibroAuxiliar, Boolean>>() {
            @Override
            public TableCell<LibroAuxiliar, Boolean> call(TableColumn<LibroAuxiliar, Boolean> p) {
                CheckBoxTableCell<LibroAuxiliar, Boolean> cell = new CheckBoxTableCell<LibroAuxiliar, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        cingresar.setMinWidth(150);
        tv_generic.getColumns().addAll(fechaelab, nofactura,valor,cliente,descrip,cingresar);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setEditable(true);
        TableViewSelectionModel<DocumentoSoporte> tsm = tv_generic.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);
        gp_generico.addRow(0, new Label("Fecha Inicial"),new Label("Fecha Final"),new Label("Fecha Elaboración"),new Label("Buscar"),new Label("Centro de Costos"),new Label("Sucursal"),new Label("N° Comprobante"),new Label("Ingresar"));
        gp_generico.addRow(1,dp_fechadesde,dp_fechahasta, dp_fechaelaboracion,buscar,IsoftConta.cb_centrocostos,IsoftConta.cb_sucursales,tf_nocomprobante, bu_ejecutar);
        gp_generico.add(tv_generic, 0, 2, 8, 1);
        gp_generico.setVgap(2);
        gp_generico.setHgap(2);
        gp_generico.setAlignment(Pos.TOP_CENTER);
       // gp_generico.setMinWidth(1345);
        tp_generic.setContent(gp_generico);
        stack.setAlignment(Pos.TOP_CENTER);
        //tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic);
     
           getRecords();
       
        return stack;
    }
     public static void getRecords() throws ParseException{
          ol_generic.clear();
          long ci=System.currentTimeMillis();
          ol_generic.addAll(DocumentoSoporteController.getrecordsegresosmasivos(dp_fechadesde.getValue().toString(),dp_fechahasta.getValue().toString(),buscar.getText(),EnumDocumentoSoporte.COMPROBANTECAUSACIONEGRESO14.ordinal()));
          System.out.println("Tt->"+(System.currentTimeMillis()-ci));
          tv_generic.setItems(ol_generic);
          System.out.println("Size->"+tv_generic.getItems().size());
          
    }
     

  
private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
     stagecomprobanteingreso.showmodal();
    }

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            EntidadesStatic.es_documentosoporte = (DocumentoSoporte) tv_generic.getSelectionModel().getSelectedItem();
            return true;
        } else {
            return false;
        }
    }

 private void aplicar_ingresos() throws ParseException
 {     try
 {
       EntidadesStatic.es_documentosoporte=DocumentoSoporteController.findbynoconsecutivoegreso(Long.valueOf(tf_nocomprobante.getText().trim()));
      if(EntidadesStatic.es_documentosoporte==null)
      {
        EntidadesStatic.es_documentosoporte=new DocumentoSoporte();  
        //EntidadesStatic.es_documentosoporte.getLi_libroauxiliar().clear();
      }
 }catch(Exception e)
 {
       EntidadesStatic.es_documentosoporte=null;
       EntidadesStatic.es_documentosoporte=new DocumentoSoporte();
 }     
       int j=0;
     totalingresos=BigDecimal.ZERO;
     for(int i=0;i<tv_generic.getItems().size();i++)
     {
         if(((LibroAuxiliar)tv_generic.getItems().get(i)).isIngresar())
         {
             if(j==0)
             {
                 inicionofactura=((LibroAuxiliar)tv_generic.getItems().get(i)).getId().toString();
             }
                 finnofactura=((LibroAuxiliar)tv_generic.getItems().get(i)).getId().toString();
         // System.out.println("Factura No."+((DocumentoSoporte)tv_generic.getItems().get(i)).getNofacturacerosizquierda());
             EntidadesStatic.es_documentosoporte_root=((LibroAuxiliar)tv_generic.getItems().get(i)).getDocumentoSoporte();
             EntidadesStatic.es_terceros=EntidadesStatic.es_documentosoporte_root.getClientes();
             if(EntidadesStatic.es_terceros==null)
             {
                EntidadesStatic.es_terceros=((LibroAuxiliar)tv_generic.getItems().get(i)).getTerceros();
              
             }
             totalingresos=totalingresos.add(((LibroAuxiliar)tv_generic.getItems().get(i)).getHaber());
             EntidadesStatic.es_libroauxiliar=((LibroAuxiliar)tv_generic.getItems().get(i));
             EntidadesStatic.id_source_mov=EntidadesStatic.es_libroauxiliar.getId();
             EntidadesStatic.es_puc=EntidadesStatic.es_libroauxiliar.getConPuc();
             AddDetalle();
             j++;
         }         
     }
         notificationBuilder.text("Egresos Ingresados Satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
         getRecords();
         FComprobanteEgreso.getRecords();
 }
 public void save()  {
                 
                 set_datos();
                           
                if (EntidadesStatic.es_documentosoporte.getId() == null) {
                    DocumentoSoporteController.servicio_crear();
                   } else {
                    DocumentoSoporteController.servicio_actualizar();
                    }
             

      
    }

    private  void set_datos() {
      EntidadesStatic.es_documentosoporte.setCentrocosto(String.valueOf(IsoftConta.cb_centrocostos.getSelectionModel().getSelectedIndex()));
      EntidadesStatic.es_documentosoporte.setSucursales(String.valueOf(IsoftConta.cb_sucursales.getSelectionModel().getSelectedIndex()));
      EntidadesStatic.es_documentosoporte.setFechaelaboracion(UtilDate.localdatetodate(dp_fechaelaboracion.getValue()));
      EntidadesStatic.es_documentosoporte.setValorneto(totalingresos);
      EntidadesStatic.es_documentosoporte.setValorbase(totalingresos);
      EntidadesStatic.es_documentosoporte.setConcepto("Egreso de Gastos desde el N° ");
      EntidadesStatic.es_documentosoporte.setTipodocsoporte(EnumDocumentoSoporte.COMPROBANTEEGRESO4.ordinal());
      EntidadesStatic.es_documentosoporte.setDocumentoSoporte(EntidadesStatic.es_documentosoporte_root);
      EntidadesStatic.es_documentosoporte.setClientes(EntidadesStatic.es_terceros);
     }
  public void AddDetalle()  {
       
           if (EntidadesStatic.es_documentosoporte.getId() == null) 
           {
               save();
           } 
            if (EntidadesStatic.es_documentosoporte.getId() != null) {
                
                BigDecimal bdvalor=EntidadesStatic.es_libroauxiliar.getHaber();
               
              
               /* DocumentoSoporteController.findpucporcodigo("11010401");
                 if (EntidadesStatic.es_puc != null)
                 {
                       if(EntidadesStatic.es_puc.getPorc_base()==null)
                {
                    EntidadesStatic.es_puc.setPorc_base(BigDecimal.ZERO);
                }
                if(EntidadesStatic.es_puc.getPorc_base().compareTo(BigDecimal.ZERO)==1)
                {
                  bdvalor=bdvalor.multiply((EntidadesStatic.es_puc.getPorc_base().multiply(BigDecimal.valueOf(0.01))));
                }
                EntidadesStatic.es_documentosoporte.agregarregistroalibroauxiliar(EntidadesStatic.es_puc, true, EntidadesStatic.es_documentosoporte.getConcepto(), bdvalor, UtilDate.localdatetodate(dp_fechaelaboracion.getValue()),EntidadesStatic.es_terceros,EntidadesStatic.id_source_mov);
                 }*/
                  
                 // DocumentoSoporteController.findpucporcodigo("13012601");
                  if (EntidadesStatic.es_puc != null) 
                  {
                        if(EntidadesStatic.es_puc.getPorc_base()==null)
                {
                    EntidadesStatic.es_puc.setPorc_base(BigDecimal.ZERO);
                }
                if(EntidadesStatic.es_puc.getPorc_base().compareTo(BigDecimal.ZERO)==1)
                {
                  bdvalor=bdvalor.multiply((EntidadesStatic.es_puc.getPorc_base().multiply(BigDecimal.valueOf(0.01))));
                }
                   EntidadesStatic.es_documentosoporte.agregarregistroalibroauxiliar(EntidadesStatic.es_puc, true, EntidadesStatic.es_documentosoporte.getConcepto(), bdvalor, UtilDate.localdatetodate(dp_fechaelaboracion.getValue()),EntidadesStatic.es_terceros,EntidadesStatic.id_source_mov);
                  } 
                          
               save();
              
                
            }

        }

    

}