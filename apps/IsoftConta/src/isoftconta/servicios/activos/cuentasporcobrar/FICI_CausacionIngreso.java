package isoftconta.servicios.activos.cuentasporcobrar;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import isoftconta.IsoftConta;
import isoftconta.fields.FieldNumero;
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import modelo.LibroAuxiliar;
import modelo.Puc;
import isoftconta.servicios.DocumentoSoporteController;
import isoftconta.servicios.EnumDocumentoSoporte;
import isoftconta.servicios.LibroAuxiliarController;
import isoftconta.servicios.TercerosController;
import isoftconta.servicios.configuracion.contabilidad.libroscontables.FICI_LibroAuxiliar;
import isoftconta.util.ImpresionDocumentoSoporte;
import isoftconta.util.StageGeneric;
import isoftconta.util.UtilDate;
import modelo.DTOFactura;
import modelo.DocumentoSoporte;
import modelo.EntidadesStatic;
import modelo.Terceros;
import org.controlsfx.control.textfield.TextFields;

public class FICI_CausacionIngreso extends Application {

    StackPane stack;
    private static final Label la_noconsecutivo = new Label("N° Consecutivo:");
    private static final Label la_concepto = new Label("Concepto");
    private static final Label la_fechaelaboracion = new Label("Fecha de Elaboración");
    private static final Label la_nombre = new Label("Cliente:");
    private static final Label la_nofactura = new Label("Factura:");
    private static final TextField concepto = new TextField();
    private static final TextField noconsecutivo = new TextField();
    private static final TextField totaldebito = new TextField();
    private static final TextField totalcredito = new TextField();
    private static final TextField nombre = new TextField();
    private static final DatePicker fechaelaboracion = new DatePicker(LocalDate.now());
    private static final FieldNumero fn_valorbase=new FieldNumero(10);
    private static final FieldNumero fn_valorneto=new FieldNumero(10);
    private static final FieldNumero fn_nofactura=new FieldNumero(10);
    private static final ChoiceBox cb_tipoingreso=new ChoiceBox();
    private Button bu_save;
    private Button nuevo;
    private Button bu_facturas;
    private boolean permitirproceso = false;
    private ImageView imageView;
    private ObservableList ol_conpuc = FXCollections.observableArrayList();
    private List<Puc> l_conpuc;
    private GridPane Gp_Generic;
    private Button bu_printcomprobante;
    private Button bu_tercero;
    private static TableView tv_detalles;
    private static ObservableList ol_detalles = FXCollections.observableArrayList();
    private List<LibroAuxiliar> l_detalles;
    private static final TextField puc = new TextField();
    private static final Label la_cuenta = new Label("Cuenta");
    private static final Label la_detalle = new Label("Concepto");
    private static final Label la_valor = new Label("Valor(Positivo Débito::Negativo Crédito");
    private  static TextField detalle = new TextField();
    private static final FieldNumero valor = new FieldNumero();
    private HBox hbox = new HBox();
    private HBox hbox2 = new HBox();
    private HBox hb_tercero=new HBox();
    private ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemdelete;
    
    private ToolBar toolBar;
    private static TextField prefijo=new TextField();
    private final StageGeneric stageCliente=new StageGeneric("isoftconta.servicios.configuracion.contabilidad.FICI_Terceros","Cliente",false);
    private final StageGeneric stageFacturas=new StageGeneric("isoftconta.servicios.activos.cuentasporcobrar.FFacturas","Facturas por Servicios Médicos",false);
    private final StageGeneric stageLibroAuxiliar=new StageGeneric("isoftconta.servicios.configuracion.contabilidad.libroscontables.FICI_LibroAuxiliar","Libro Auxiliar",false);

    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException, InterruptedException {
        FICI_LibroAuxiliar.refrescardatossegunorigen=EnumDocumentoSoporte.COMPROBANTECAUSACIONINGRESO15.ordinal();
        cb_tipoingreso.getItems().addAll("Factura Venta","Cuota Moderadora","Copago");
        cb_tipoingreso.getSelectionModel().select(0);
        IsoftConta.cb_centrocostos.getSelectionModel().select(0);
        IsoftConta.cb_sucursales.getSelectionModel().select(0);
        fn_nofactura.setDisable(true);
        fn_valorbase.setDisable(true);
        prefijo.setDisable(true);
        noconsecutivo.setDisable(true);
        nombre.setDisable(true);
        TextFields.bindAutoCompletion(puc, EntidadesStatic.as_puc);
        char IM_BOLD = '\uf0C7';
        char IM_NUEVO = '\uf0fe';
        char IM_PDF = '\uf1C1';
        char IM_USER='\uf007';
        char IM_DATABASE='\uf155';
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_save=  IsoftConta.icoMoon.create(IM_BOLD).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_NUEVO=  IsoftConta.icoMoon.create(IM_NUEVO).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_PDF=  IsoftConta.fontAwesome.create(FontAwesome.Glyph.FILE_TEXT_ALT.getChar()).sizeFactor(2).color(randomColor).useGradientEffect();
        Glyph glyph_tercero=  IsoftConta.icoMoon.create(IM_USER).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
        Glyph glyph_database=  IsoftConta.icoMoon.create(IM_DATABASE).color(randomColor).size(32).useGradientEffect();
     
            itemeditar = new MenuItem("Editar");
             itemeditar.setOnAction(e -> {

            try {
                if(checkregistroexistente())
                {
             stageLibroAuxiliar.showmodal();
                }
            } catch (Exception x) {
                  x.printStackTrace();
            }
        });
        
        itemdelete = new MenuItem("Eliminar");
        itemdelete.setOnAction(e -> {
                try {
                removeDetalle();
            } catch (Exception ex) {
                
            }
        });
        contextMenu = new ContextMenu(itemeditar, itemdelete);
        EntidadesStatic.es_puc=new Puc();
      
        puc.setMaxWidth(300);
        puc.setMinWidth(300);
        puc.setPromptText("Cuenta Puc");
        EntidadesStatic.es_terceros=new Terceros();
        detalle.setPromptText("Concepto");
        valor.setPromptText("Valor: D+: C-");
        totaldebito.setMaxWidth(150);
        totalcredito.setMaxWidth(150);
        puc.setOnAction(e -> {
            
            detalle.setText(concepto.getText());
            detalle.requestFocus();

        });
         
      puc.focusedProperty().addListener(new ChangeListener<Boolean>()
      {
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
     DocumentoSoporteController.findpucporcodigo(puc.getText());
    }
    });
       
        valor.setOnAction(e -> {
            try {
                AddDetalle();
                valor.setText("0");
                puc.setText("");
                puc.requestFocus();
           
            } catch (Exception ex) {
               ex.printStackTrace();
            }
        });
        
         hbox.getChildren().clear();
        hbox2.getChildren().clear();
     
        hbox.setSpacing(3);
        
        hbox2.setSpacing(3);
       
        detalle.setOnAction(e->{
            valor.setText(fn_valorneto.getText());
            valor.requestFocus();
        });
           
        Gp_Generic = new GridPane();
        Gp_Generic.setPadding(new Insets(5, 5, 5, 5));
        bu_save = new Button("",glyph_save);
        bu_save.setContentDisplay(ContentDisplay.CENTER);
        bu_save.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
        nuevo = new Button("",glyph_NUEVO);
        nuevo.setOnAction(e -> {
            try {
                nuevo();
            } catch (ParseException ex) {
            } catch (InterruptedException ex) {
                Logger.getLogger(FICI_CausacionIngreso.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        nuevo.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    nuevo();
                } catch (ParseException ex) {
                  } catch (InterruptedException ex) {
                    Logger.getLogger(FICI_CausacionIngreso.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bu_printcomprobante = new Button("",glyph_PDF);
        bu_printcomprobante.setOnAction(e
                -> {
            try {
                ImpresionDocumentoSoporte.fpdfdocumentosoportecontercero();
            } catch (Exception ex) {
                  ex.printStackTrace();
            }
            });

       bu_tercero = new Button("",glyph_tercero);
        bu_tercero.setOnAction(e -> {
            try {
               stageCliente.showmodal();
            } catch (Exception ex) {
            }
        });
        bu_facturas= new Button("",glyph_database);
        bu_facturas.setMinSize(36, 36);
        bu_facturas.setOnAction(e -> {
            try {
               stageFacturas.show();
            } catch (Exception ex) {
            }
        });
      hb_tercero.getChildren().addAll(nombre,bu_tercero);

        //***********************************************  
        stack = new StackPane();
        concepto.getProperties().put("requerido", true);
        concepto.setMinWidth(350);
        concepto.setOnAction(e -> {

        });
       
        // hbox=new HBox(4, nofactura);
        toolBar=new ToolBar(bu_save, nuevo, bu_printcomprobante,bu_facturas);
        bu_save.setOnAction(e -> {
            try {
                try {
                    save();
                } catch (ParseException ex) {
                }
            } catch (InterruptedException ex) {

            }
        });

        tv_detalles = new TableView();
        tv_detalles.setContextMenu(contextMenu);

        tv_detalles.setOnKeyPressed(e -> {
            try {
                if (e.getCode().equals(e.getCode().DELETE)) {
                    removeDetalle();
                }
            } catch (Exception ex) {
               
            }
        });
         TableColumn fechaelab = new TableColumn();
        fechaelab.setMinWidth(100);
        fechaelab.setText("Fecha Elab.");
        fechaelab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> libroauxiliar) {

                return new SimpleStringProperty(isoftconta.util.UtilDate.formatodiamesyear(libroauxiliar.getValue().getFechaelaboracion()));

            }
        });
         TableColumn terceros = new TableColumn();
        //codigo.setMinWidth(150);
        terceros.setText("Tercero");
        terceros.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {
               try
               {
                return new SimpleStringProperty((detalles.getValue().getTerceros().getNombres()));
               }catch(Exception e)
               {
                return new SimpleStringProperty("NT");   
               }
            }
        });
       
        TableColumn codigo = new TableColumn();
        //codigo.setMinWidth(150);
        codigo.setText("Codigo");
        codigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getCodigo()));

            }
        });
        TableColumn ccuenta = new TableColumn();
        //ccuenta.setMinWidth(300);
        ccuenta.setText("Cuenta");
        ccuenta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getConPuc().getNombre()));

            }
        });
        TableColumn detalles = new TableColumn();
       // detalles.setMinWidth(300);
        detalles.setText("Concepto");
        detalles.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getDescripcion()));

            }
        });
        TableColumn cdebito = new TableColumn();
        //cdebito.setMinWidth(150);
        cdebito.setText("Débito");
        cdebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getDebe().setScale(0, RoundingMode.HALF_UP).toString()));

            }
        });
        TableColumn ccredito = new TableColumn();
         ccredito.setText("Crédito");
        ccredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getHaber().setScale(0, RoundingMode.HALF_UP).toString()));

            }
        });
        tv_detalles.getColumns().addAll(fechaelab,terceros, codigo, ccuenta, detalles, cdebito, ccredito);
        tv_detalles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tv_detalles.setMaxHeight(200);
        Gp_Generic.add(toolBar, 0, 0, 4, 1);
        Gp_Generic.add(la_fechaelaboracion, 0, 1, 1, 1);
        Gp_Generic.add(fechaelaboracion, 1, 1, 1, 1);
        
        Gp_Generic.add(new Label("Centro de costos:"),2 ,1, 1 ,1);
        Gp_Generic.add(IsoftConta.cb_centrocostos, 3, 1, 1, 1);
        Gp_Generic.add(new Label("Sucursal:"),0 ,2, 1 ,1);
        Gp_Generic.add(IsoftConta.cb_sucursales, 1, 2, 1, 1);
        Gp_Generic.add(new Label("Descripción:"),2 ,2, 1 ,1);
        Gp_Generic.add(concepto, 3, 2, 1, 1);
        
        GridPane.setHalignment(hbox2, HPos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(puc, detalle, valor);
        Gp_Generic.add(hbox2, 0, 6, 4, 1);
        Gp_Generic.add(tv_detalles, 0, 7, 4, 1);
        Gp_Generic.add(new Label("Totales:"), 0, 8, 1, 1);
        Gp_Generic.add(new HBox(2, totaldebito,totalcredito), 3, 8, 1, 1);
        GridPane.setHalignment(totaldebito, HPos.RIGHT);
        GridPane.setHalignment(totalcredito, HPos.RIGHT);
        Gp_Generic.setHgap(5);
        Gp_Generic.setVgap(5);
        GridPane.setHgrow(concepto, Priority.ALWAYS);
        stack.getChildren().addAll(Gp_Generic);
        stack.setAlignment(Pos.TOP_CENTER);
        Gp_Generic.setAlignment(Pos.TOP_CENTER);
        Gp_Generic.setAlignment(Pos.TOP_CENTER);
              
        crearoeditar();
     
        return stack;
    }

    public void nuevo() throws ParseException, InterruptedException {

        EntidadesStatic.es_puc = null;
        EntidadesStatic.es_puc  = new Puc();
         EntidadesStatic.es_documentosoporte = null;
        EntidadesStatic.es_documentosoporte  = new DocumentoSoporte();
        gettotales();
        concepto.setText("");
        fechaelaboracion.setValue(LocalDate.now());
        nombre.setText("");
        IsoftConta.cb_centrocostos.getSelectionModel().select(0);
        IsoftConta.cb_sucursales.getSelectionModel().select(0);
        valor.setText("0");
        fn_nofactura.setText("0");
        fn_valorbase.setText("0");
        fn_valorneto.setText("0");
        noconsecutivo.setText("");
    }

    public static void save() throws InterruptedException, ParseException {
       
      
      
                 set_datos();
                 DocumentoSoporte ds=DocumentoSoporteController.findfacturaporprefijonumero(EntidadesStatic.es_documentosoporte.getPrefijo(),EntidadesStatic.es_documentosoporte.getNo_factura());
                      if(ds!=null)
                      {
                          EntidadesStatic.es_documentosoporte.setId(ds.getId());
                          EntidadesStatic.es_documentosoporte.setNo_consecutivo(ds.getNo_consecutivo());
                      }     
                if (EntidadesStatic.es_documentosoporte.getId() == null) {
                    DocumentoSoporteController.servicio_crear();
                    noconsecutivo.setText(EntidadesStatic.es_documentosoporte.getNocomprobantecerosizquierda());
                } else {
                    DocumentoSoporteController.servicio_actualizar();
                }
                FCausacionIngreso.getRecords();

      
    }

    private static  void set_datos() {
      EntidadesStatic.es_documentosoporte.setCentrocosto(String.valueOf(IsoftConta.cb_centrocostos.getSelectionModel().getSelectedIndex()));
      EntidadesStatic.es_documentosoporte.setSucursales(String.valueOf(IsoftConta.cb_sucursales.getSelectionModel().getSelectedIndex()));
      EntidadesStatic.es_documentosoporte.setClientes(EntidadesStatic.es_terceros);
      EntidadesStatic.es_documentosoporte.setFechaelaboracion(UtilDate.localdatetodate(fechaelaboracion.getValue()));
      EntidadesStatic.es_documentosoporte.setConcepto(concepto.getText());
      EntidadesStatic.es_documentosoporte.setTipodocsoporte(EnumDocumentoSoporte.COMPROBANTECAUSACIONINGRESO15.ordinal());
      EntidadesStatic.es_documentosoporte.setTipoingreso(cb_tipoingreso.getSelectionModel().getSelectedIndex());
      
    }

   private void getdatos() throws ParseException, InterruptedException
   {    noconsecutivo.setText(EntidadesStatic.es_documentosoporte.getNocomprobantecerosizquierda());
       IsoftConta.cb_centrocostos.getSelectionModel().select(Integer.valueOf(EntidadesStatic.es_documentosoporte.getCentrocosto()));
       IsoftConta.cb_sucursales.getSelectionModel().select(Integer.valueOf(EntidadesStatic.es_documentosoporte.getSucursales()));
       EntidadesStatic.es_terceros=EntidadesStatic.es_documentosoporte.getClientes();
       if(EntidadesStatic.es_terceros!=null)
       {
           nombre.setText(EntidadesStatic.es_terceros.getNo_ident()+" "+EntidadesStatic.es_terceros.getNombres());
       }
       fechaelaboracion.setValue(UtilDate.formatoyearmesdia(EntidadesStatic.es_documentosoporte.getFechaelaboracion()));
       cb_tipoingreso.getSelectionModel().select(EntidadesStatic.es_documentosoporte.getTipoingreso());
       EntidadesStatic.es_documentosoporte.calculartotalesdebehaber();
       nombre.setText(EntidadesStatic.es_terceros.getNo_ident()+" "+EntidadesStatic.es_terceros.getNombres());
       concepto.setText(EntidadesStatic.es_documentosoporte.getConcepto());
       gettotales();
   }
     
    private static void AddDetalle() throws ParseException, InterruptedException {
        if (EntidadesStatic.es_puc != null) {
           if (EntidadesStatic.es_documentosoporte.getId() == null) 
           {
               save();
           } 
            if (EntidadesStatic.es_documentosoporte.getId() != null) {
                boolean tipomov=true;
                BigDecimal bdvalor=new BigDecimal(valor.getText());
                if(bdvalor.compareTo(BigDecimal.ZERO)==-1)
                {
                    tipomov=false;
                     bdvalor= bdvalor.multiply(new  BigDecimal(-1));
                }
                if(EntidadesStatic.es_puc.getPorc_base()==null)
                {
                    EntidadesStatic.es_puc.setPorc_base(BigDecimal.ZERO);
                }
                if(EntidadesStatic.es_puc.getPorc_base().compareTo(BigDecimal.ZERO)==1)
                {
                  bdvalor=bdvalor.multiply((EntidadesStatic.es_puc.getPorc_base().multiply(BigDecimal.valueOf(0.01))));
                }
               EntidadesStatic.es_documentosoporte.agregarregistroalibroauxiliar(EntidadesStatic.es_puc, tipomov, detalle.getText(), bdvalor, UtilDate.localdatetodate(fechaelaboracion.getValue()));
               save();
               gettotales();
                
            }

        }

    }

    public static void getlibroauxiliar() throws ParseException {
        //colocamos los datos

        try {

           ol_detalles.clear();
           ol_detalles.addAll(EntidadesStatic.es_documentosoporte.getLi_libroauxiliar().toArray());
           tv_detalles.setItems(ol_detalles);
        } catch (Exception e) {
            
        }

    }

    private void cerrarcomprobante() {
        bu_save.setDisable(true);
       
        nuevo.setDisable(false);
        nuevo.requestFocus();
        l_detalles = null;
        l_detalles = new ArrayList<>();
        ol_detalles.clear();
        ol_detalles.addAll(l_detalles.toArray());
        tv_detalles.setItems(ol_detalles);
    }

    public void removeDetalle() throws ParseException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (((LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem()) != null) {
            EntidadesStatic.id_source_mov=((LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem()).getId();
             LibroAuxiliarController.removerregistrosidsource(3, EntidadesStatic.id_source_mov);
            EntidadesStatic.es_documentosoporte.removerregistroalibroauxiliar((LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem());
     
            save();
           gettotales();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void crearoeditar() throws ParseException, InterruptedException {
       
           if(EntidadesStatic.es_documentosoporte!=null)
           {
               if(EntidadesStatic.es_documentosoporte.getId()!=null)
               {
                   getdatos();
               }
               else
                   nuevo();
           }
           else nuevo();
   
    }

     
  private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_detalles.getSelectionModel()) != null) {
           EntidadesStatic.es_libroauxiliar= (LibroAuxiliar) tv_detalles.getSelectionModel().getSelectedItem();
            return true;
        }
        else
        {
            return false;
        }
    }  
   public static void gettotales() throws ParseException, InterruptedException
   {
       totalcredito.setText(EntidadesStatic.es_documentosoporte.getTotalhaber().setScale(0, RoundingMode.HALF_UP).toString());
       totaldebito.setText(EntidadesStatic.es_documentosoporte.getTotaldebe().setScale(0, RoundingMode.HALF_UP).toString());
       EntidadesStatic.totaldebe=EntidadesStatic.es_documentosoporte.getTotaldebe();
       EntidadesStatic.totalhaber=EntidadesStatic.es_documentosoporte.getTotalhaber();
       getlibroauxiliar();
   }
   public static void procesarcausarfacturas(Long no_comprobante) throws ParseException, InterruptedException
   {
       BigDecimal total=BigDecimal.ZERO;
       try
       {
         EntidadesStatic.es_documentosoporte=DocumentoSoporteController.findbynoconsecutivo(no_comprobante);
        
      if(EntidadesStatic.es_documentosoporte==null)
      {
        EntidadesStatic.es_documentosoporte=new DocumentoSoporte();  
        
      }
 }catch(Exception e)
 {
       EntidadesStatic.es_documentosoporte=null;
       EntidadesStatic.es_documentosoporte=new DocumentoSoporte();
 }  
      for(DTOFactura dtofactura2:EntidadesStatic.li_dtoFacturas)
      {
                EntidadesStatic.dtofactura=null;
                EntidadesStatic.dtofactura=new DTOFactura();
                EntidadesStatic.dtofactura=dtofactura2;
       
         EntidadesStatic.es_terceros=TercerosController.findbyident(EntidadesStatic.dtofactura.getNo_ident());
      if(EntidadesStatic.es_terceros==null)
       {
          EntidadesStatic.es_terceros=new Terceros();
       }
      if(EntidadesStatic.dtofactura.getPrefijo().substring(0,1).equals("A") || EntidadesStatic.dtofactura.getPrefijo().substring(0,1).equals("P")||EntidadesStatic.dtofactura.getPrefijo().substring(0,1).equals("T"))
      {
         EntidadesStatic.es_terceros.setTipoidentificacion("0");
      }
      else
      {
         EntidadesStatic.es_terceros.setTipoidentificacion("1");  
      }
       EntidadesStatic.es_terceros.setCelular(EntidadesStatic.dtofactura.getTelefono());
       EntidadesStatic.es_terceros.setDir1(EntidadesStatic.dtofactura.getDireccion());
       EntidadesStatic.es_terceros.setPrimernombre(EntidadesStatic.dtofactura.getNombre());
       EntidadesStatic.es_terceros.setRazonsocial(EntidadesStatic.dtofactura.getNombre());
       EntidadesStatic.es_terceros.setNo_ident(EntidadesStatic.dtofactura.getNo_ident());
       EntidadesStatic.es_terceros.setDigitoverificacion(EntidadesStatic.dtofactura.getDigitoverificacion());
       EntidadesStatic.es_terceros.setPrimerapellido(EntidadesStatic.dtofactura.getPrimer_apellido());
       EntidadesStatic.es_terceros.setSegundoapellido(EntidadesStatic.dtofactura.getSegundo_apellido());
       EntidadesStatic.es_terceros.setTipotercero("0");
       TercerosController.servicio_crear();
       EntidadesStatic.es_documentosoporte.setClientes(EntidadesStatic.es_terceros);
       nombre.setText(EntidadesStatic.es_terceros.getNo_ident()+" "+EntidadesStatic.es_terceros.getNombres());
       concepto.setText("Causación Ingreso por Factura de Servicios Médicos");
      
           
           
       
       prefijo.setText(EntidadesStatic.dtofactura.getPrefijo());
       noconsecutivo.requestFocus();
       importartodasfacturas();
       total=total.add(EntidadesStatic.dtofactura.getTotalamount().add(EntidadesStatic.dtofactura.getCopago()!=null?EntidadesStatic.dtofactura.getCopago():BigDecimal.ZERO).add(EntidadesStatic.dtofactura.getCuotamoderadora()!=null?EntidadesStatic.dtofactura.getCuotamoderadora():BigDecimal.ZERO));
      }
      DocumentoSoporteController.findpucporcodigo("41010501");
      EntidadesStatic.es_documentosoporte.agregarregistroalibroauxiliar(EntidadesStatic.es_puc, false, detalle.getText(), total, EntidadesStatic.es_documentosoporte.getFechaelaboracion(),EntidadesStatic.es_terceros);
      save();

   }
   
public static void  importartodasfacturas() throws ParseException, InterruptedException
{
    
   BigDecimal valorbase=EntidadesStatic.dtofactura.getTotalamount().add(EntidadesStatic.dtofactura.getCopago()!=null?EntidadesStatic.dtofactura.getCopago():BigDecimal.ZERO).add(EntidadesStatic.dtofactura.getCuotamoderadora()!=null?EntidadesStatic.dtofactura.getCuotamoderadora():BigDecimal.ZERO);
   BigDecimal valorneto=EntidadesStatic.dtofactura.getTotalamount();
   detalle.setText("Factura de Servicios Médicos N°: "+EntidadesStatic.dtofactura.getPrefijo()+EntidadesStatic.dtofactura.getNo_factura().toString());
    save();
    DocumentoSoporteController.findpucporcodigo("11010101");
   EntidadesStatic.es_documentosoporte.agregarregistroalibroauxiliar(EntidadesStatic.es_puc, true, detalle.getText(), valorbase.subtract(valorneto), EntidadesStatic.es_documentosoporte.getFechaelaboracion(),EntidadesStatic.es_terceros);
   save();
    DocumentoSoporteController.findpucporcodigo("13012601");
   EntidadesStatic.es_documentosoporte.agregarregistroalibroauxiliar(EntidadesStatic.es_puc, true, detalle.getText(), valorneto, EntidadesStatic.es_documentosoporte.getFechaelaboracion(),EntidadesStatic.es_terceros,EntidadesStatic.dtofactura.getPrefijo()+EntidadesStatic.dtofactura.getNo_factura().toString());
   save();
       
    
}
}
