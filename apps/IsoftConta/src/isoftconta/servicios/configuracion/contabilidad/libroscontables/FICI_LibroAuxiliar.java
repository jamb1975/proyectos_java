package isoftconta.servicios.configuracion.contabilidad.libroscontables;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import isoftconta.IsoftConta;
import isoftconta.fields.FieldNumero;
import isoftconta.glyphfont.Glyph;
import isoftconta.servicios.CatalogoCuentasController;
import isoftconta.servicios.DocumentoSoporteController;
import isoftconta.servicios.EnumDocumentoSoporte;
import isoftconta.servicios.LibroAuxiliarController;
import isoftconta.servicios.activos.cuentasporcobrar.FICI_CausacionIngreso;
import isoftconta.servicios.activos.efectivo.FICI_ComprobanteEgreso;
import isoftconta.servicios.activos.efectivo.FICI_ComprobanteIngreso;
import isoftconta.servicios.configuracion.contabilidad.FICI_NotaCredito;
import isoftconta.servicios.configuracion.contabilidad.FICI_NotaDebito;
import isoftconta.servicios.configuracion.contabilidad.FICI_SaldosIniciales;
import isoftconta.servicios.pasivos.cuentasporpagar.FICI_CausacionEgreso;
import isoftconta.servicios.pasivos.cuentasporpagar.FICI_CausacionNomina;
import isoftconta.servicios.activos.efectivo.FICI_NotaContabilidad;
import isoftconta.servicios.pasivos.cuentasporpagar.FICI_ReciboCajaMenor;
import isoftconta.util.UtilDate;
import modelo.EntidadesStatic;
import modelo.LibroAuxiliar;
import modelo.Puc;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;



/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FICI_LibroAuxiliar extends Application {

    StackPane stack;
   
    private GridPane gp_generic;
    private Label la_fechaelaboracion=new Label("Fecha Elaboración:");
    private Label la_puc=new Label("Cuenta Puc:");
    private Label la_debe=new Label("Debe:");
     private Label la_haber=new Label("Haber:");
    private Label la_descripcion=new Label("Descripción");
    private Label la_documentosoporte=new Label("Documento Soporte:");
    private TextField documentosoporte=new TextField();
    private FieldNumero debe = new FieldNumero();
    private FieldNumero haber = new FieldNumero();
    private TextField puc=new TextField();
    private TextField descripcion=new TextField();
    private TextField fechaelaboracion=new TextField() ;
    private Button save=new Button();
    private Button nuevo=new Button();
    private ToolBar toolBar=new ToolBar();
    public static int refrescardatossegunorigen=0;
    private static Notifications notificationBuilder = Notifications.create();
    public Parent createContent() throws IOException {
        TextFields.bindAutoCompletion(puc, EntidadesStatic.as_puc);
         char IM_BOLD = '\uf0c7';
        char IM_NUEVO = '\uf0fe';
        char IM_PDF = '\uf1c1';
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_save=  IsoftConta.icoMoon.create(IM_BOLD).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_NUEVO=  IsoftConta.icoMoon.create(IM_NUEVO).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_PDF=  IsoftConta.icoMoon.create(IM_PDF).sizeFactor(2).color(randomColor).useGradientEffect();
 
        
        nuevo = new Button("",glyph_NUEVO);
        //nuevo.setMaxWidth(130);  
        nuevo.setOnAction(e
                -> {
            try {

                nuevo();
            } catch (Exception ex) {

            }
        });
puc.setOnAction(e -> {
            
        
            descripcion.requestFocus();

        });
         
      puc.focusedProperty().addListener(new ChangeListener<Boolean>()
      {
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
     DocumentoSoporteController.findpucporcodigo(puc.getText());
    }
    });
        //***********************************************  
        stack = new StackPane();
       
        puc.setMinWidth(300);
        descripcion = new TextField();
        
        save = new Button("",glyph_save);
        
        toolBar.getItems().addAll(save, nuevo);
        save.setOnAction(e -> {
            try {
                try {
                    save();
                } catch (ParseException ex) {
                }
            } catch (InterruptedException ex) {

            }
        });

        // gridpane
        gp_generic = new GridPane();
         gp_generic.setPadding(new Insets(5, 5, 5, 5));
        gp_generic.setPadding(new Insets(5, 5, 5, 5));
       
        gp_generic.add(toolBar, 0, 0, 2, 1);
        gp_generic.addRow(1, la_fechaelaboracion, fechaelaboracion);
        gp_generic.addRow(2, la_documentosoporte, documentosoporte);
        gp_generic.addRow(3, la_puc, puc);
        gp_generic.addRow(4, la_descripcion, descripcion);
        gp_generic.addRow(5, la_debe, debe);
        gp_generic.addRow(6, la_haber, haber);
        GridPane.setHalignment(toolBar, HPos.CENTER);
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
       
         stack.getChildren().addAll(gp_generic);
        crearoeditar();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void nuevo()
    {
        EntidadesStatic.es_libroauxiliar=null;
        EntidadesStatic.es_libroauxiliar=new LibroAuxiliar();
        debe.setText("0");
        haber.setText("0");
        descripcion.setText("");
        EntidadesStatic.es_puc=null;
        EntidadesStatic.es_puc=new Puc();
    }

    public void save() throws InterruptedException, ParseException {
        
        if(EntidadesStatic.es_documentosoporte!=null)
        {   
         if(EntidadesStatic.es_documentosoporte.getId()!=null)
        {   
            set_datos();
            if (EntidadesStatic.es_libroauxiliar.getId() == null) {
                    EntidadesStatic.es_documentosoporte.agregarregistroalibroauxiliar(EntidadesStatic.es_puc, EntidadesStatic.es_libroauxiliar.getDebe().compareTo(BigDecimal.ZERO)==1?true:false, EntidadesStatic.es_libroauxiliar.getDescripcion(), EntidadesStatic.es_libroauxiliar.getDebe().compareTo(BigDecimal.ZERO)==1?EntidadesStatic.es_libroauxiliar.getDebe():EntidadesStatic.es_libroauxiliar.getHaber(), EntidadesStatic.es_libroauxiliar.getFechaelaboracion());
                     notificationBuilder.text("Registro nuevo agregado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                } else {
                EntidadesStatic.es_documentosoporte.modificarregistroalibroauxiliar(EntidadesStatic.es_libroauxiliar);
                      notificationBuilder.text("Registro actualizado  satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                }
              
              DocumentoSoporteController.servicio_actualizar();
              switch(DocumentoSoporteController.valorequivalenteenenum(refrescardatossegunorigen))
              {
                  case COMPROBANTEINGRESO3:
                       FICI_ComprobanteIngreso.gettotales();
                       break;
                    case RECIBOCAJAMENOR2:
                        FICI_ReciboCajaMenor.gettotales();
                       break;    
                   case COMPROBANTECAUSACIONINGRESO15:
                       FICI_CausacionIngreso.gettotales();
                       break;  
                   case COMPROBANTECAUSACIONEGRESO14:
                       FICI_CausacionEgreso.gettotales();
                       break; 
                   case COMPROBANTEEGRESO4:
                       FICI_ComprobanteEgreso.gettotales();
                       break;   
                   case NOTACONTABILIDAD6:
                       FICI_NotaContabilidad.gettotales();
                       break; 
                       case NOTACREDITO10:
                           FICI_NotaCredito.gettotales();
                       break;
                   case NOTADEBITO9:
                       FICI_NotaDebito.gettotales();
                       break;     
                   case COMPROBANTECAUSACIONNOMINA17:
                       FICI_CausacionNomina.gettotales();
                       break;
                   case SALDOINICIAL19:
                       FICI_SaldosIniciales.gettotales();
                       break;    
        }     }
        }
            

        
    }

    private void set_datos() {
       

     //   EntidadesStatic.es_libroauxiliar.set(documentosoporte.getText());
        EntidadesStatic.es_libroauxiliar.setDescripcion(descripcion.getText());
        EntidadesStatic.es_libroauxiliar.setDebe(new BigDecimal(debe.getText()));
        EntidadesStatic.es_libroauxiliar.setHaber(new BigDecimal(haber.getText()));
        EntidadesStatic.es_libroauxiliar.setConPuc(EntidadesStatic.es_puc);
        EntidadesStatic.es_libroauxiliar.setDocumentoSoporte(EntidadesStatic.es_documentosoporte);
        EntidadesStatic.es_libroauxiliar.setFechaelaboracion(EntidadesStatic.es_documentosoporte.getFechaelaboracion());
    }
    
    private void getdatos() {
       

     //   EntidadesStatic.es_libroauxiliar.set(documentosoporte.getText());
        fechaelaboracion.setText(UtilDate.s_formatoyearmesdia(EntidadesStatic.es_documentosoporte.getFechaelaboracion()));
        descripcion.setText(EntidadesStatic.es_libroauxiliar.getDescripcion());
        debe.setText(EntidadesStatic.es_libroauxiliar.getDebe().toString());
        haber.setText(EntidadesStatic.es_libroauxiliar.getHaber().toString());
        EntidadesStatic.es_puc=EntidadesStatic.es_libroauxiliar.getConPuc();
        puc.setText(EntidadesStatic.es_puc.getCodigo()+"-"+EntidadesStatic.es_puc.getNombre());
        if(EntidadesStatic.es_documentosoporte!=null){
           if(EntidadesStatic.es_documentosoporte.getId()!=null){
            documentosoporte.setText("N° "+DocumentoSoporteController.getdocumentodoporte(DocumentoSoporteController.valorequivalenteenenum(EntidadesStatic.es_documentosoporte.getTipodocsoporte()))[0]+" "+EntidadesStatic.es_documentosoporte.getNocomprobantecerosizquierda()
            );
        } 
        }

    }
  
     public void crearoeditar() {

        if (EntidadesStatic.es_libroauxiliar != null) {
            if (EntidadesStatic.es_libroauxiliar.getId()!= null) {
             
           getdatos();
            
        } else {
            nuevo();
        }
        }
         else
            {
              nuevo();
            }

    }

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
