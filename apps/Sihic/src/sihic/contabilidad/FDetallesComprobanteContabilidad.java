package sihic.contabilidad;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.Puc;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import static sihic.SihicApp.pageBrowser;
import sihic.contabilidad.documentos.FNotaContabilidad;
import sihic.control.SearchBox;
import sihic.controlador.ConPucControllerClient;
import sihic.general.LoadChoiceBoxGeneral;
public class FDetallesComprobanteContabilidad extends Application {
    StackPane stack;
    private Button bu_save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private HBox hb_botones;
    private boolean permitirproceso = false;
    private ImageView imageView;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private GridPane Gp_Generic;
    private Scene scene;
    private Parent parent;
    private String appPath;
    private Class clz;
    private Object appClass;
    private Puc conPuc;
    private Puc conPuc_;
    private ConPucControllerClient conPucControllerClient;
    private DecimalFormat format = new DecimalFormat("#,0");
    private static final SearchBox sb_cuenta = new SearchBox();
    private static final Label la_cuenta = new Label("Cuenta");
    private static final Label la_detalle = new Label("Detalle");
    private static final Label la_tipodoc= new Label("Tipo Documento");
    private static final Label la_documento = new Label("Documento");  
    private static final Label la_telefono = new Label("Teléfono");  
    private static final Label la_direccion = new Label("Dirección");
    private static final Label la_tipomovimiento = new Label("0:Débito o 1 Crédito)");
    private static final Label la_valor = new Label("Valor");
    private SearchPopover sp_cuenta;
    private static final TextField detalle = new TextField();
    private static ChoiceBox tipodoc=new ChoiceBox();
    private static final TextField documento = new TextField();
    private static final TextField telefono = new TextField();
    private static final TextField direccion = new TextField();
    private static final TextField tipomovimiento = new TextField();
    private static final TextField valor = new TextField();
    private HBox hbox = new HBox();
    private HBox hbox2 = new HBox();
    private Button bu_asiento;
    private static final Label la_tercero = new Label("Tercero:");
    SearchBox sb_proveedores=new SearchBox();
    SearchPopover sp_proveedore;
    private static ChoiceBox tiponota=new ChoiceBox();
    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        LoadChoiceBoxGeneral.cb_sucursales.getSelectionModel().select(0);
        sp_cuenta = new SearchPopover(sb_cuenta, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_proveedore=new SearchPopover(sb_proveedores, new PageBrowser(), SihicApp.s_proveedores, false);
        sp_cuenta.setMaxSize(200, 70);
        sb_cuenta.setMaxWidth(200);
        sb_cuenta.setMinWidth(200);
         sp_proveedore.setMaxSize(200, 70);
        sb_proveedores.setMaxSize(200, 70);
        sb_proveedores.setMaxWidth(200);
        sb_proveedores.setMinWidth(200);
       
       
        sb_cuenta.setOnAction(e -> {
         
            detalle.requestFocus();

        });
  
      
    
        
        valor.setOnAction(e -> {
            try {
                
            bu_save.requestFocus();
         
        
            } catch (Exception ex) {
               ex.printStackTrace();
            }
        });
        sb_proveedores.setOnAction(e->{
         sp_cuenta.hide();
         sb_cuenta.requestFocus();
        });
         hbox.getChildren().clear();
        hbox2.getChildren().clear();
     
        hbox.setSpacing(3);
        
        hbox2.setSpacing(3);
        conPucControllerClient = new ConPucControllerClient();
        sb_cuenta.setOnAction(e->
        {
            detalle.requestFocus();
        });
        detalle.setOnAction(e->{
            valor.requestFocus();
        });
        tipomovimiento.setOnAction(e->{
            valor.requestFocus();
        });
        
        
    
        Gp_Generic = new GridPane();
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        bu_save = new Button("", imageView);
        bu_save.setOnKeyPressed(e -> {
            if (e.getCode().equals(e.getCode().ENTER)) {
                try {
                    save();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
          bu_save.setOnAction(e -> {
             try {
                    save();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            
        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        
        
        imageView = null;

        imageView = null;
        imageView = new ImageView("/images/pdf.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
      

        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
              
        imageView = null;
        imageView = new ImageView("/images/asientos.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
       

        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        
        hb_botones = new HBox(10);
        // hbox=new HBox(4, nofactura);
        
        
        GridPane.setHalignment(hbox, HPos.CENTER);
      
        Gp_Generic.add(la_tercero, 0, 0, 1, 1);
        Gp_Generic.add(sb_proveedores, 1, 0, 1, 1);
        Gp_Generic.add(la_cuenta, 0, 1, 1, 1);
        Gp_Generic.add(sb_cuenta, 1, 1, 1, 1);
        Gp_Generic.add(la_detalle, 0, 2, 1, 1);        
        Gp_Generic.add(detalle, 1, 2, 1, 1);
        Gp_Generic.add(la_valor, 0, 3, 1, 1);
        Gp_Generic.add(valor, 1, 3, 1, 1);
        Gp_Generic.add(bu_save, 0, 4, 2, 1);
        GridPane.setHalignment(bu_save, HPos.CENTER);
        Gp_Generic.add(sp_proveedore, 0, 1, 2, 5);
        Gp_Generic.add(sp_cuenta, 0, 2, 2, 5);
        GridPane.setValignment(sp_proveedore, VPos.TOP);
        GridPane.setValignment(sp_cuenta, VPos.TOP);
        Gp_Generic.setHgap(5);
        Gp_Generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Generic.getStylesheets().add(SihicApp.hojaestilos);
        Gp_Generic.getStyleClass().add("background");
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(Gp_Generic.getLayoutBounds().getWidth(), 40);
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        Gp_Message.setMaxHeight(40);
        stack.getChildren().addAll(Gp_Generic, Gp_Message);
        //Gp_Generic.setStyle("border-width:1;border-color: #0007ff;");
        stack.setAlignment(Pos.TOP_CENTER);
        Gp_Generic.setAlignment(Pos.TOP_CENTER);
        // stack.autosize();
        // Gp_Generic.setMaxSize(1200, 500);
        // stack.setMaxSize(1200, 500);
        //Gp_Generic.setMinSize(1200, 500);
        Gp_Generic.setAlignment(Pos.TOP_CENTER);
        // stack.setMinSize(1200, 500);   
  
        crearoeditar();
        
        return stack;
    }
    public void save() throws InterruptedException, ParseException {

        validar_formulario();

        if (permitirproceso) {
            set_datos();

            try {

           
               SihicApp.conDetallesComprobanteContabilidadControllerCient.update();
               L_Message.setText("Registro Almacenado");
                FNotaContabilidad.gettotales();
                thread_message();

            } catch (Exception e) {
                e.printStackTrace();
                L_Message.setText("Error Almacenando");
                thread_message();
            }

        }

        
       
    }

    private void set_datos() {

        SihicApp.conDetallesComprobanteContabilidad.setConPuc(SihicApp.conPuc);
        SihicApp.conDetallesComprobanteContabilidad.setDescripcion(detalle.getText());
        SihicApp.conDetallesComprobanteContabilidad.setProveedores(SihicApp.s_proveedores);
        if(new BigDecimal(valor.getText()).compareTo(BigDecimal.ZERO)==1)
        {    
         SihicApp.conDetallesComprobanteContabilidad.setDebe(new BigDecimal(valor.getText()));
          SihicApp.conDetallesComprobanteContabilidad.setDebe(new BigDecimal(valor.getText()));
             SihicApp.conDetallesComprobanteContabilidad.setHaber(BigDecimal.ZERO); 
               SihicApp.conDetallesComprobanteContabilidad.setHaber(BigDecimal.ZERO);
        }
        else
        {    valor.setText(valor.getText().replace("-", ""));
              SihicApp.conDetallesComprobanteContabilidad.setHaber(new BigDecimal(valor.getText())); 
               SihicApp.conDetallesComprobanteContabilidad.setHaber(new BigDecimal(valor.getText()));
                         SihicApp.conDetallesComprobanteContabilidad.setDebe(BigDecimal.ZERO); 
               SihicApp.conDetallesComprobanteContabilidad.setDebe(BigDecimal.ZERO);
  
        }
       

    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : Gp_Generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
                        permitirproceso = false;
                        general.setStyle("-fx-background-color:#ff1600");

                    }
                }

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    if (general.getProperties().get("requerido") != null) {
                        permitirproceso = false;
                        general.getStylesheets().add(0, "/sihic/personal.css");
                        general.getStylesheets().set(0, "/sihic/personal.css");
                    }

                }
            }
        }
        FadeTransition ft = new FadeTransition(Duration.millis(2000), bu_save);
        //ft.setFromValue(0.0);
        // ft.setToValue(1);

        ft.play();

        //success.setOpacity(0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Object n : Gp_Generic.getChildren().toArray()) {
                    if (n.getClass() == TextField.class) {
                        TextField general = (TextField) n;
                        if (general.getProperties().get("requerido") != null) {
                            System.out.println("propiedad-->" + general.getProperties().get("requerido"));
                            if (((boolean) general.getProperties().get("requerido") == true)) {

                                general.setStyle(null);
                                general.getStyleClass().add("text-field");
                            }
                        }

                    } else {
                        if (n.getClass() == ChoiceBox.class) {
                            ChoiceBox general = (ChoiceBox) n;

                            if (general.getProperties().get("requerido") != null) {
                                general.getStylesheets().set(0, SihicApp.hojaestilos);

                            }

                        }
                    }
                }
            }
        });

    }

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }

  

    public void enableAll() {
        for (Object n : Gp_Generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setDisable(false);
            }
            if (n.getClass() == ChoiceBox.class) {
                ChoiceBox general = (ChoiceBox) n;
                general.setDisable(false);
            }
            if (n.getClass() == Button.class) {
                Button general = (Button) n;
                general.setDisable(false);
            }

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

    public void crearoeditar() throws ParseException {
           if (SihicApp.notaContabilidad != null) {
            if (SihicApp.notaContabilidad.getId() != null)
            {
                sb_cuenta.setText(SihicApp.conDetallesComprobanteContabilidad.getConPuc().getCodigo()+"||"+SihicApp.conDetallesComprobanteContabilidad.getConPuc().getNombre());
               SihicApp.conPuc=SihicApp.conDetallesComprobanteContabilidad.getConPuc();
               if(SihicApp.conDetallesComprobanteContabilidad.getProveedores()!=null)
               {
                sb_proveedores.setText(SihicApp.conDetallesComprobanteContabilidad.getProveedores().getNo_ident()+"||"+SihicApp.conDetallesComprobanteContabilidad.getProveedores().getNombre());
                SihicApp.s_proveedores=SihicApp.conDetallesComprobanteContabilidad.getProveedores();
               }
                detalle.setText(SihicApp.conDetallesComprobanteContabilidad.getDescripcion());
                if(SihicApp.conDetallesComprobanteContabilidad.getDebe().compareTo(BigDecimal.ZERO)==1)
                {
                    valor.setText(SihicApp.conDetallesComprobanteContabilidad.getDebe().toString());
                }
                else
                {
                    valor.setText(SihicApp.conDetallesComprobanteContabilidad.getHaber().toString());
                }
                 
               
              
        }
    }

    

  
 
  
    }
      public void thread_message() {
        Task_Message = () -> {
            try {
                SetMessage();
                
            } catch (InterruptedException ex) {

            }
        };
        backgroundThread = new Thread(Task_Message);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);

        // Start the thread
        backgroundThread.start();
    }
       
}
