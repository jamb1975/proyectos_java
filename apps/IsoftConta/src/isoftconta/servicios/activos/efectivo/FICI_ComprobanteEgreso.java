package isoftconta.servicios.activos.efectivo;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
import isoftconta.servicios.TercerosController;
import isoftconta.servicios.configuracion.contabilidad.libroscontables.FICI_LibroAuxiliar;
import isoftconta.util.ImpresionDocumentoSoporte;
import isoftconta.util.StageGeneric;
import isoftconta.util.UtilDate;
import modelo.DocumentoSoporte;
import modelo.EntidadesStatic;
import modelo.Terceros;
import org.controlsfx.control.Notifications;
import static isoftconta.IsoftConta.buscartercero;
import static isoftconta.IsoftConta.puc;
import static isoftconta.IsoftConta.tercero;
public class FICI_ComprobanteEgreso extends Application {

    StackPane stack;
    private static final Label la_buscarcliente = new Label("Buscar Tercero:");
    private static final Label la_noconsecutivo = new Label("N° Consecutivo:");
    private static final Label la_concepto = new Label("Concepto");
    private static final Label la_fechaelaboracion = new Label("Fecha de Elaboración");
    private static final Label la_nombre = new Label("Tercero:");
    private static final Label la_nodocumentosoporte = new Label("N° Documento Soporte:");
    private static final TextField concepto = new TextField();
    private static final TextField noconsecutivo = new TextField();
    private static final TextField totaldebito = new TextField();
    private static final TextField totalcredito = new TextField();
    private static final TextField nombretercero = new TextField();
    private static final DatePicker fechaelaboracion = new DatePicker(LocalDate.now());
    private static final FieldNumero fn_valordocumentosoporte=new FieldNumero();
    private static final FieldNumero fn_valorneto=new FieldNumero();
    private static final TextField fn_nodocumentosoporte=new TextField();
    private static final ChoiceBox cb_tipoingreso=new ChoiceBox();
    private static final RadioButton rb_causaciongastos=new RadioButton();
    private static final RadioButton rb_causacionnomina=new RadioButton();
    private static final RadioButton rb_saldosiniciales=new RadioButton();
    private static ToggleGroup tg_radio=new ToggleGroup();
    private Button bu_save;
    private Button nuevo;
    private Button bu_facturas;
    private Button bu_tercero;
    private boolean permitirproceso = false;
    private ImageView imageView;
    private ObservableList ol_conpuc = FXCollections.observableArrayList();
    private ObservableList ol_libroauxiliar = FXCollections.observableArrayList();
    private List<Puc> l_conpuc;
    private GridPane Gp_Generic;
    private Button bu_printcomprobante;
   
    private static TableView tv_detalles;
    private static ObservableList ol_detalles = FXCollections.observableArrayList();
    private List<LibroAuxiliar> l_detalles;
    private static final Label la_cuenta = new Label("Cuenta");
    private static final Label la_detalle = new Label("Concepto");
    private static final Label la_valor = new Label("Valor(Positivo Débito::Negativo Crédito");
    private   TextField detalle = new TextField();
    private static final FieldNumero valor = new FieldNumero();
   
    private HBox hb_tercero=new HBox();
    private ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemdelete;
    private ToolBar toolBar;
    private static TextField prefijo=new TextField();
    private final StageGeneric stageTercero=new StageGeneric("isoftconta.servicios.configuracion.contabilidad.FICI_Terceros","Cliente",false);
    private final StageGeneric stageLibroAuxiliar=new StageGeneric("isoftconta.servicios.configuracion.contabilidad.libroscontables.FICI_LibroAuxiliar","LibroAuxiliar",false);
    private static Notifications notificationBuilder = Notifications.create();
    private static SuggestionProvider<String> provider = SuggestionProvider.create(EntidadesStatic.possibleSuggestions);
    private static TextField causacionegreso=new TextField();
    private static Button bu_findcausacionegreso=new Button();
    private static Button bu_close=new Button();
    private TitledPane tp_generic=new TitledPane();
    private TableView tv_causacionegreso=new TableView();
    private HBox hb_buscarcausacion=new HBox();
    private HBox hb_buscar=new HBox();
    private VBox vb_buscar=new VBox();
    private TextField tf_tercero=new TextField();
    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException, InterruptedException {
        
        tp_generic.setText("Buscar Causación de Egresos");
        tp_generic.setCollapsible(false);
        hb_buscar.setSpacing(2);
        hb_buscar.getChildren().addAll(tf_tercero,bu_close);
        vb_buscar.getChildren().addAll(hb_buscar,tv_causacionegreso);
        tp_generic.setContent(vb_buscar);
        causacionegreso.setDisable(true);
        causacionegreso.setMinWidth(150);
        HBox hbox = new HBox();
        HBox hbox2 = new HBox();
        hb_buscarcausacion.setSpacing(2);
        hb_buscarcausacion.getChildren().addAll(causacionegreso,bu_findcausacionegreso);
        rb_causaciongastos.setToggleGroup(tg_radio);
        rb_causaciongastos.setSelected(true);
        rb_causacionnomina.setToggleGroup(tg_radio);
         rb_saldosiniciales.setToggleGroup(tg_radio);
       tercero.setText("");
       puc.setText("");
        FICI_LibroAuxiliar.refrescardatossegunorigen=EnumDocumentoSoporte.COMPROBANTEEGRESO4.ordinal();
          new AutoCompletionTextFieldBinding<>(tercero, provider);
           tercero.textProperty().addListener((observable, oldValue, newValue) -> {
           try {
                    TercerosController.load_asterceros(tercero.getText());
                } catch (ParseException ex) {
                    Logger.getLogger(FICI_ComprobanteEgreso.class.getName()).log(Level.SEVERE, null, ex);
                }
                       
                     if(EntidadesStatic.li_terceros!=null)
                     {
                       if(EntidadesStatic.li_terceros.size()>0)
                     {
                         provider.clearSuggestions();
                         provider.addPossibleSuggestions(EntidadesStatic.possibleSuggestions);
                     }  
                     }
        });
      tercero.setOnAction(e->{
            
              puc.requestFocus();
            
        });
       tercero.focusedProperty().addListener(new ChangeListener<Boolean>()
      {
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    { 
        if (!newPropertyValue)
        {
         TercerosController.findtercerospornoident(tercero.getText());
         
        }
     }
    });
        IsoftConta.cb_centrocostos.getSelectionModel().select(0);
        IsoftConta.cb_sucursales.getSelectionModel().select(0);
        fn_nodocumentosoporte.setDisable(true);
        fn_valordocumentosoporte.setDisable(true);
        prefijo.setDisable(true);
        //noconsecutivo.setDisable(true);
        //nombretercero.setDisable(true);
        char IM_BOLD = '\uf0c7';
        char IM_NUEVO = '\uf0fe';
        char IM_PDF = '\uf1c1';
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
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_buscar=  IsoftConta.fontAwesome.create(FontAwesome.Glyph.SEARCH_PLUS.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_close=  IsoftConta.fontAwesome.create(FontAwesome.Glyph.CLOSE.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
           
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
     if(!newPropertyValue)
     {
      DocumentoSoporteController.findpucporcodigo(puc.getText());
     }
    }
    });
      /*tf_tercero.textProperty().addListener((observable, oldValue, newValue) -> {
          
            try {
                getRecordsegresos();
            } catch (ParseException ex) {
                Logger.getLogger(FICI_ComprobanteEgreso.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
        });*/
      tf_tercero.setOnAction(e-> {
          
            try {
                getRecordsegresos();
            } catch (ParseException ex) {
                Logger.getLogger(FICI_ComprobanteEgreso.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
        });
      
        valor.setOnAction(e -> {
            try {
                AddDetalle();
                 
         
           
            
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
            }
        });
        nuevo.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    nuevo();
                } catch (ParseException ex) {
                  } catch (InterruptedException ex) {
                 }
            }
        });
        bu_printcomprobante = new Button("",glyph_PDF);
        bu_printcomprobante.setOnAction(e
                -> {
            try {
                
                 ImpresionDocumentoSoporte.fpdfdocumentosoportecontercero();
            } catch (Exception ex) {

            }
            });

       bu_tercero = new Button("",glyph_tercero);
        bu_tercero.setOnAction(e -> {
            try {
               stageTercero.showmodal();
            } catch (Exception ex) {
            }
        });
         bu_findcausacionegreso.setGraphic(glyph_buscar);
         bu_findcausacionegreso.setOnAction(e -> {
            tp_generic.setVisible(true);
        });
          bu_close.setGraphic(glyph_close);
         bu_close.setOnAction(e -> {
            tp_generic.setVisible(false);
        });
      
      hb_tercero.getChildren().addAll(tercero,bu_tercero);

        //***********************************************  
        stack = new StackPane();
        concepto.getProperties().put("requerido", true);
        concepto.setMinWidth(350);
        concepto.setOnAction(e -> {

        });
       
        // hbox=new HBox(4, nofactura);
        toolBar=new ToolBar(bu_save, nuevo, bu_printcomprobante);
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
         TableColumn fechaelab2 = new TableColumn();
        fechaelab2.setMinWidth(100);
        fechaelab2.setText("Fecha Elab.");
        fechaelab2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> documentosoporte) {

                return new SimpleStringProperty(isoftconta.util.UtilDate.formatodiamesyear(documentosoporte.getValue().getFechaelaboracion()));

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

                return new SimpleStringProperty((detalles.getValue().getDebe().toString()));

            }
        });
        TableColumn ccredito = new TableColumn();
         ccredito.setText("Crédito");
        ccredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> detalles) {

                return new SimpleStringProperty((detalles.getValue().getHaber().toString()));

            }
        });
        
        tv_detalles.getColumns().addAll(fechaelab2,terceros,codigo, ccuenta, detalles, cdebito, ccredito);
        tv_detalles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_detalles.setMaxHeight(200);
        
        
        TableColumn fechaelab = new TableColumn();
        fechaelab.setMinWidth(100);
        fechaelab.setText("Fecha Elab.");
        fechaelab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroAuxiliar, String> documentosoporte) {

                return new SimpleStringProperty(isoftconta.util.UtilDate.formatodiamesyear(documentosoporte.getValue().getDocumentoSoporte().getFechaelaboracion()));

            }
        });
            
        TableColumn cvalor = new TableColumn();
        cvalor.setMinWidth(150);
        cvalor.setText("Valor Egreso");
        cvalor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, String>, ObservableValue<String>>() {
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
                     return new SimpleStringProperty("");
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
        TableColumn buttonCol = new TableColumn<>("Elegir");
        buttonCol.setSortable(false);
        buttonCol.setMinWidth(50);
        buttonCol.setSortable(false);
        buttonCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<LibroAuxiliar, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<LibroAuxiliar, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        buttonCol.setCellFactory(new Callback<TableColumn<LibroAuxiliar, Boolean>, TableCell<LibroAuxiliar, Boolean>>() {
         @Override
            public TableCell<LibroAuxiliar, Boolean> call(TableColumn<LibroAuxiliar, Boolean> p) {
                return new ButtonCell(tv_causacionegreso);
            }

        });
        
        
        tv_causacionegreso.getColumns().addAll(fechaelab,cvalor,cliente,descrip,buttonCol);
        tv_causacionegreso.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Gp_Generic.add(toolBar, 0, 0, 4, 1);
        
         
        Gp_Generic.add(la_fechaelaboracion, 0, 1, 1, 1);
        Gp_Generic.add(fechaelaboracion, 1, 1, 1, 1);
        Gp_Generic.add(la_noconsecutivo, 2, 1, 1, 1);
        Gp_Generic.add(noconsecutivo, 3, 1, 1, 1);
        
              
        Gp_Generic.add(new Label("Centro de costos:"),0 ,2, 1 ,1);
        Gp_Generic.add(IsoftConta.cb_centrocostos, 1, 2, 1, 1);
        Gp_Generic.add(new Label("Sucursal:"),2 ,2, 1 ,1);
        Gp_Generic.add(IsoftConta.cb_sucursales, 3,2 , 1, 1);
        GridPane.setHalignment(hbox2, HPos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(hb_buscarcausacion,puc, detalle, valor);
        Gp_Generic.add(hbox2, 0, 6, 4, 1);
        Gp_Generic.add(tv_detalles, 0, 7, 4, 1);
        Gp_Generic.add(new Label("Totales:"), 0, 8, 1, 1);
        Gp_Generic.add(new HBox(2, totaldebito,totalcredito), 3, 8, 1, 1);
        GridPane.setHalignment(totaldebito, HPos.RIGHT);
        GridPane.setHalignment(totalcredito, HPos.RIGHT);
        Gp_Generic.setHgap(5);
        Gp_Generic.setVgap(5);
        GridPane.setHgrow(concepto, Priority.ALWAYS);
        tp_generic.setVisible(false);
        stack.getChildren().addAll(Gp_Generic,tp_generic);
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
        fechaelaboracion.setValue(LocalDate.now());
        buscartercero.setText("");
        IsoftConta.cb_centrocostos.getSelectionModel().select(0);
        IsoftConta.cb_sucursales.getSelectionModel().select(0);
        valor.setText("0");
        fn_nodocumentosoporte.setText("0");
        fn_valordocumentosoporte.setText("0");
        fn_valorneto.setText("0");
        noconsecutivo.setText("");
        nombretercero.setText("0");
        puc.setText("");
        buscartercero.setText("");
      
    }

    public void save() throws InterruptedException, ParseException {
       
           
                 set_datos();
             
                if (EntidadesStatic.es_documentosoporte.getId() == null) {
                    DocumentoSoporteController.servicio_crear();
                    noconsecutivo.setText(EntidadesStatic.es_documentosoporte.getNocomprobantecerosizquierda());
                       notificationBuilder.text("Registro nuevo agregado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                } else {
                    DocumentoSoporteController.servicio_actualizar();
                      notificationBuilder.text("Registro actualizado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                }
                FComprobanteEgreso.getRecords();

      
    }

    private  void set_datos() {
         EntidadesStatic.es_documentosoporte.setTipodocsoporte(EnumDocumentoSoporte.COMPROBANTEEGRESO4.ordinal());
         EntidadesStatic.es_documentosoporte.setCentrocosto(String.valueOf(IsoftConta.cb_centrocostos.getSelectionModel().getSelectedIndex()));
         EntidadesStatic.es_documentosoporte.setSucursales(String.valueOf(IsoftConta.cb_sucursales.getSelectionModel().getSelectedIndex()));
         EntidadesStatic.es_documentosoporte.setFechaelaboracion(UtilDate.localdatetodate(fechaelaboracion.getValue()));
    }
   private void getdatos() throws ParseException, InterruptedException
   {   noconsecutivo.setText(EntidadesStatic.es_documentosoporte.getNocomprobantecerosizquierda());
       IsoftConta.cb_centrocostos.getSelectionModel().select(Integer.valueOf(EntidadesStatic.es_documentosoporte.getCentrocosto()));
       IsoftConta.cb_sucursales.getSelectionModel().select(Integer.valueOf(EntidadesStatic.es_documentosoporte.getSucursales()));
       fechaelaboracion.setValue(UtilDate.formatoyearmesdia(EntidadesStatic.es_documentosoporte.getFechaelaboracion()));
   
       
        puc.setText("");
        buscartercero.setText("");
       EntidadesStatic.es_documentosoporte.calculartotalesdebehaber();
       gettotales();
   }
     
    public void AddDetalle() throws ParseException, InterruptedException {
        if (EntidadesStatic.es_puc != null) {
           if (EntidadesStatic.es_documentosoporte.getId() == null) 
           {
               save();
           } 
           try
           {
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
                  if(tipomov==false)
                  {
                     EntidadesStatic.es_terceros= EntidadesStatic.es_documentosoporte.getLi_libroauxiliar().size()>0?EntidadesStatic.es_documentosoporte.getLi_libroauxiliar().get(0).getTerceros():null;
                  }
                  if(tipomov==false)
                  {
                     EntidadesStatic.id_source_mov= EntidadesStatic.es_documentosoporte.getLi_libroauxiliar().size()>0?EntidadesStatic.es_documentosoporte.getLi_libroauxiliar().get(0).getId_source_mov():null;
                  }
                  if(EntidadesStatic.es_terceros!=null && EntidadesStatic.id_source_mov!=null)
                  {
                   EntidadesStatic.es_documentosoporte.agregarregistroalibroauxiliar(EntidadesStatic.es_puc, tipomov, detalle.getText(), bdvalor, UtilDate.localdatetodate(fechaelaboracion.getValue()),EntidadesStatic.es_terceros,EntidadesStatic.id_source_mov);
                  }         
               save();
               gettotales();
                
            

        }
            
           }catch(Exception e)
           {
               e.printStackTrace();
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
            e.printStackTrace();
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
       totalcredito.setText(EntidadesStatic.es_documentosoporte.getTotalhaber().toString());
       totaldebito.setText(EntidadesStatic.es_documentosoporte.getTotaldebe().toString());
       EntidadesStatic.totaldebe=EntidadesStatic.es_documentosoporte.getTotaldebe();
       EntidadesStatic.totalhaber=EntidadesStatic.es_documentosoporte.getTotalhaber();
       getlibroauxiliar();
   }
  
   private void setdatosdocumentosoporte()
   {
       if(EntidadesStatic.es_documentosoporte_root!=null)
       {
       EntidadesStatic.es_terceros=EntidadesStatic.es_documentosoporte_root.getClientes();
         if(EntidadesStatic.es_terceros!=null)
         {
           nombretercero.setText(EntidadesStatic.es_terceros.getNo_ident()+" "+EntidadesStatic.es_terceros.getNombres());
           concepto.setText("Pago a Doc Soporte N° "+EntidadesStatic.es_documentosoporte_root.getNumerodocumentosoporte()+ " de "+EntidadesStatic.es_terceros.getNombres());
         }
         
          fn_nodocumentosoporte.setText(EntidadesStatic.es_documentosoporte_root.getNumerodocumentosoporte());
          fn_valordocumentosoporte.setText(EntidadesStatic.es_documentosoporte_root.getValorbase().toString());
        }
       buscartercero.setText("-");
   }
    private void setdatoslibroauxiliar()
   {
       if(EntidadesStatic.es_libroauxiliar!=null)
       {
        EntidadesStatic.id_source_mov=EntidadesStatic.es_libroauxiliar.getId();
        EntidadesStatic.es_terceros=EntidadesStatic.es_libroauxiliar.getTerceros();
       }
       
   }
  private class ButtonCell extends TableCell<LibroAuxiliar, Boolean> {

        //boton a mostrar  private class ButtonCell extends TableCell<Factura, Boolean> {

        //boton a mostrar
        final Button cellButton = new Button();
        final Button cellButtonPdf = new Button();

        ButtonCell(final TableView tblView) {
  
       
        
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_seleccionar=  IsoftConta.fontAwesome.create(FontAwesome.Glyph.CHECK_CIRCLE_ALT.getChar()).sizeFactor(2).size(22).color(randomColor).useGradientEffect();
        
            cellButton.setGraphic(glyph_seleccionar);
            cellButton.setCursor(Cursor.HAND);
            cellButton.setTooltip(new Tooltip("Seleccionar Causación"));
            cellButton.setOnAction(e -> {
          
                int selectdIndex = getTableRow().getIndex();
                EntidadesStatic.es_libroauxiliar = ((LibroAuxiliar) ol_libroauxiliar.get(selectdIndex));
                setdatoslibroauxiliar();
                valor.setText(EntidadesStatic.es_libroauxiliar.getHaber().toString());
                causacionegreso.setText(EntidadesStatic.es_libroauxiliar.getTerceros().getNombres());
                tp_generic.setVisible(false);
            });
        }
        //Muestra un boton si la fila no es nula

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                try {
                    int selectdIndex = getTableRow().getIndex();

                    setGraphic(cellButton);
                } catch (Exception e) {

                }

            } else {
                setGraphic(new Label());

            }
        }

    }
            
        
        public  void getRecordsegresos() throws ParseException{
          ol_libroauxiliar.clear();
          long ci=System.currentTimeMillis();
          ol_libroauxiliar.addAll(DocumentoSoporteController.getrecordsegresosmasivos2(tf_tercero.getText()));
          System.out.println("Tt->"+(System.currentTimeMillis()-ci));
          tv_causacionegreso.setItems(ol_libroauxiliar);
         
          
    }

       
        }

    

