package isoftconta.servicios.configuracion.contabilidad;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
import isoftconta.glyphfont.Glyph;
import isoftconta.servicios.CatalogoCuentasController;
import isoftconta.servicios.DocumentoSoporteController;
import modelo.EntidadesStatic;
import modelo.Puc;
import org.controlsfx.control.Notifications;



/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FICI_CatalogoCuentas extends Application {

    StackPane stack;
   
    private GridPane gp_generic;
    private Label la_nombre;
    private Label la_codigo;
    private Label la_descripcion;
    private TextField codigo;
    private TextField codigopadre = new TextField();
    private TextField nombre;
    private TextField descripcion;
    private Label la_porc_base=new Label("% Sobre Base:");
    private TextField porc_base=new TextField() ;
    private Button save;
    private Button nuevo;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private ChoiceBox cb_natcuenta=new ChoiceBox();
    private ChoiceBox cb_tipocuenta=new ChoiceBox();
    private ToolBar toolBar=new ToolBar();
    private Notifications notificationBuilder = Notifications.create();
    public Parent createContent() throws IOException {
     
         char IM_BOLD = '\uf0c7';
        char IM_NUEVO = '\uf0fe';
        char IM_PDF = '\uf1c1';
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
       Glyph glyph_save=  IsoftConta.icoMoon.create(IM_BOLD).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
       randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
       Glyph glyph_NUEVO=  IsoftConta.icoMoon.create(IM_NUEVO).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
       randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
       Glyph glyph_PDF=  IsoftConta.icoMoon.create(IM_PDF).sizeFactor(2).color(randomColor).useGradientEffect();
 
        cb_natcuenta.getItems().add("Debito");
        cb_natcuenta.getItems().add("Crédito");
        cb_tipocuenta.getItems().add("Corriente");
        cb_tipocuenta.getItems().add("No Corriente");
        cb_tipocuenta.getItems().add("No definida");
       
        nuevo = new Button("",glyph_NUEVO);
        //nuevo.setMaxWidth(130);  
        nuevo.setOnAction(e
                -> {
            try {

                nuevo();
            } catch (Exception ex) {

            }
        });

        //***********************************************  
        stack = new StackPane();
       
        la_codigo = new Label("Código:");
        la_nombre = new Label("Nombre:");
        la_descripcion = new Label("Descripción:");
        codigo = new TextField();
        nombre = new TextField();
        nombre.setMinWidth(500);
        descripcion = new TextField();
         cb_natcuenta.getSelectionModel().select(0);
        cb_tipocuenta.getSelectionModel().select(0);
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
        gp_generic.setMinSize(510, 200);
          gp_generic.add(toolBar, 0, 0, 2, 1);
        gp_generic.addRow(1, la_codigo, codigo);
        gp_generic.addRow(2, la_nombre, nombre);
        gp_generic.addRow(3, la_descripcion, descripcion);
        gp_generic.addRow(4, la_porc_base, porc_base);
        gp_generic.addRow(5, new Label("Nat. Cuenta:"), cb_natcuenta);
        gp_generic.addRow(6, new Label("Tipo Cuenta:"), cb_tipocuenta);
      

        GridPane.setHalignment(toolBar, HPos.CENTER);
         gp_generic.setHgap(5);
        gp_generic.setVgap(5);
       gp_generic.setVgap(5);
       gp_generic.setHgap(5);
         stack.getChildren().addAll(gp_generic);
        crearoeditar();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void nuevo() {
        EntidadesStatic.es_puc = null;
        EntidadesStatic.es_puc = new Puc();
        codigo.setText("");
        nombre.setText("");
        descripcion.setText("");
        porc_base.setText("0");
        cb_natcuenta.getSelectionModel().select(0);
        cb_tipocuenta.getSelectionModel().select(0);
    }

    public void save() throws InterruptedException, ParseException {
        
       
            set_datos();
            
            if (EntidadesStatic.es_puc.getId() == null) {
               if(DocumentoSoporteController.verificarquenoexistecodigopuc(EntidadesStatic.es_puc.getCodigo()))
               {
               if(DocumentoSoporteController.verificarcodigoigualaroot(EntidadesStatic.es_puc.getCodigo()))
               {
                    CatalogoCuentasController.servicio_crear();
                    notificationBuilder.text("Registro nuevo agregado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showError();
               }
                else
               {
                    notificationBuilder.text("La parte del código que pertence a la cuanta raiz es diferente")
                            .position(Pos.TOP_RIGHT)
                            .showError();
                    return;
                }
               }
               else
               {
                    notificationBuilder.text("Ya existe un código para esta cuenta puc")
                                       .position(Pos.TOP_RIGHT)
                                        .showError();
                    return;
                }
               
              
                } else {
                 if(DocumentoSoporteController.verificarquenoexistecodigopuc2(EntidadesStatic.es_puc.getCodigo()))
               {
               if(DocumentoSoporteController.verificarcodigoigualaroot(EntidadesStatic.es_puc.getCodigo()))
               {
                   CatalogoCuentasController.servicio_actualizar();
                    notificationBuilder.text("Registro actualizado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showError();
               }
                else
               {
                    notificationBuilder.text("La parte del código que pertence a la cuanta raiz es diferente")
                            .position(Pos.TOP_RIGHT)
                            .showError();
                    return;
                }
               }
               else
               {
                    notificationBuilder.text("Ya existe un código para esta cuenta puc")
                                       .position(Pos.TOP_RIGHT)
                                        .showError();
                    return;
                }
                    
                }
              
                FCatalogoCuentas.getPuc();
               EntidadesStatic.li_puc=null;
               EntidadesStatic.li_puc=new ArrayList<>();
               EntidadesStatic.li_puc.addAll(CatalogoCuentasController.li_conpuc(null));
               DocumentoSoporteController.load_aspuc();
               
            

        
    }

    private void set_datos() {
       

        EntidadesStatic.es_puc.setCodigo(codigo.getText());
        EntidadesStatic.es_puc.setDescripcion(descripcion.getText());
        EntidadesStatic.es_puc.setNombre(nombre.getText());
        EntidadesStatic.es_puc.setPorc_base(new BigDecimal(porc_base.getText()));
        EntidadesStatic.es_puc.setNatcuenta(String.valueOf(cb_natcuenta.getSelectionModel().getSelectedIndex()==-1?0:cb_natcuenta.getSelectionModel().getSelectedIndex()));
        EntidadesStatic.es_puc.setTipocuenta(String.valueOf(cb_tipocuenta.getSelectionModel().getSelectedIndex()==-1?0:cb_tipocuenta.getSelectionModel().getSelectedIndex()));
       
        EntidadesStatic.es_puc.setConpuc_id(EntidadesStatic.es_puc_padre);
      

    }

  
     public void crearoeditar() {

        if (EntidadesStatic.es_puc != null) {
            codigo.setText(String.valueOf(EntidadesStatic.es_puc.getCodigo()));
            nombre.setText(String.valueOf(EntidadesStatic.es_puc.getNombre()));
            descripcion.setText(EntidadesStatic.es_puc.getDescripcion());
            cb_natcuenta.getSelectionModel().select(EntidadesStatic.es_puc.getNatcuenta());
            cb_tipocuenta.getSelectionModel().select(EntidadesStatic.es_puc.getTipocuenta());
          try{
            porc_base.setText(EntidadesStatic.es_puc.getPorc_base().toString());
            }catch(Exception e)
            {
               porc_base.setText("0");  
            }
        } else {
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
