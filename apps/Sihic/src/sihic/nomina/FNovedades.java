package sihic.nomina;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.Novedades;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.general.LoadChoiceBoxGeneral;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FNovedades extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GridPane gp_generic;
    private static final Label la_tiponovedad = new Label("Tipo Novedad:");
    private static final ChoiceBox novedad = new ChoiceBox();
    private static final Label la_tipohoraextra = new Label("Factor Hora Extra:");
   
    private static final Label la_cantidad = new Label("Cantidad:");
    private static final TextField cantidad = new TextField();
    private static final Label la_valor = new Label("Valor:");
    private static final TextField valor = new TextField();
    private static final Label la_observacion = new Label("Observación:");
    private static final TextField observacion = new TextField();
    
    private Button save;
    private Button nuevo;

    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private boolean permitirproceso = false;

    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;

    private String appPathGeneric;
    private Class clzGeneric;
    private Object appClassGeneric;
    private Stage stageGeneric = new Stage(StageStyle.DECORATED);
    private Scene sceneGeneric = null;
    private Parent P_Generic;
    private static ChoiceBox cb_rh = new ChoiceBox();

    public Parent createContent() throws IOException {
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();
        novedad.getItems().add(jenum.EnumTipoNovedad.COMISION0.ordinal(), "Comisión");
        novedad.getItems().add(jenum.EnumTipoNovedad.HORASEXTRAS1.ordinal(), "Horas Extras");
        novedad.getItems().add(jenum.EnumTipoNovedad.DEUDASTERCEROS2.ordinal(), "Deudas a Terceros");
        novedad.getItems().add(jenum.EnumTipoNovedad.DEUDASFINANCIERAS3.ordinal(), "Deudas Financieras");
        novedad.getItems().add(jenum.EnumTipoNovedad.OTRASDEUDAS4.ordinal(), "Otras Deudas");
        novedad.getItems().add(jenum.EnumTipoNovedad.DIASNOTRABAJADOS5.ordinal(), "Días No Trabajados");
        novedad.getSelectionModel().select(0);
         novedad.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);

        sihic.util.ValidacionesCampo.ValidarTextField(valor);
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        save = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        nuevo = new Button("", imageView);
        imageView = null;
        imageView = new ImageView("/images/tributaria.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        gp_generic = new GridPane();
        gp_generic.add(la_tiponovedad, 0, 0);
        gp_generic.add(novedad, 1, 0);
        gp_generic.add(la_tipohoraextra, 0, 1);
        gp_generic.add(LoadChoiceBoxGeneral.cb_factorhorasextras, 1, 1);
        gp_generic.add(la_cantidad, 0, 2);
        gp_generic.add(cantidad, 1, 2);
        gp_generic.add(la_valor, 0, 3);
        gp_generic.add(valor, 1, 3);
        gp_generic.add(la_observacion, 0, 4);
        gp_generic.add(observacion, 1, 4);
        
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.add(hb_botones, 0, 5, 3, 1);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.alignmentProperty().setValue(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(gp_generic.getLayoutBounds().getHeight(), gp_generic.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.getChildren().addAll(gp_generic, Gp_Message);

        crearoeditar();
        //cambiar si novedad es hora extras
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            try {
                set_datos();
                
                    SihicApp.nominaEmpleadosControllerClient.update();
                

                L_Message.setText("Registro Almacenado");
                Task_Message = () -> {
                    try {

                        SetMessage();
                        AdminNovedades.getRecords();
                        FNomina.crearnomina();
                    } catch (InterruptedException ex) {

                    } catch (ParseException ex) {
                        Logger.getLogger(FEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            } catch (Exception e) {
                e.printStackTrace();
                L_Message.setText("Error Almacenando");
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
    }

    private void set_datos() 
    {
       
        SihicApp.novedades.setTiponovedad(novedad.getSelectionModel().getSelectedIndex());
        SihicApp.novedades.setTipohoraextra(LoadChoiceBoxGeneral.cb_factorhorasextras.getSelectionModel().getSelectedIndex());
        try
        {
        SihicApp.novedades.setCantidad(Integer.valueOf(cantidad.getText()));
        }catch(Exception e)
        {
             SihicApp.novedades.setCantidad(0);
        }
        try
        {
        SihicApp.novedades.setValor(new BigDecimal(valor.getText()));
        }catch(Exception e)
        {
             SihicApp.novedades.setValor(BigDecimal.ZERO);
        }
        if(jenum.EnumTipoNovedad.HORASEXTRAS1.ordinal()==novedad.getSelectionModel().getSelectedIndex())
        {
          SihicApp.novedades.setValor((SihicApp.nominaEmpleados.calcularvalordiasueldo().divide(BigDecimal.valueOf(SihicApp.nomina.getHorasdiarias()),2,RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(SihicApp.novedades.getCantidad())).multiply(LoadChoiceBoxGeneral.v_factorhorasextras.get(LoadChoiceBoxGeneral.cb_factorhorasextras.getSelectionModel().getSelectedIndex())));
         String tipohoraextra="";
          switch(LoadChoiceBoxGeneral.cb_factorhorasextras.getSelectionModel().getSelectedIndex())
          {
              case 0: tipohoraextra="Dominical y Festivo";
                       break;
                       
             case 1: tipohoraextra="Dominical y Festivo Nocturno";
                       break;
             case 2: tipohoraextra="Extra Diurno Festivo";
                      break;
             case 3: tipohoraextra="Extra Diurno Ordinario ";
                       break;
             case 4: tipohoraextra="Extra Nocturno Festivo";
                       break;
             case 5: tipohoraextra="Extra Nocturno Ordinario";
                       break;
             case 6: tipohoraextra="Recargo Nocturno";
                       break;
          }
        SihicApp.novedades.setObservacion(tipohoraextra);
        }
        else
        {
         SihicApp.novedades.setObservacion(observacion.getText());
        }
         SihicApp.nominaEmpleados.addnovedad(SihicApp.novedades);
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_generic.getChildren().toArray()) {
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

                    if (general.getSelectionModel().getSelectedIndex() < 0 && general.getProperties().get("requerido") == null) {
                        permitirproceso = false;
                        general.getStylesheets().add(0, "/sihic/personal.css");
                        general.getStylesheets().set(0, "/sihic/personal.css");
                    }

                } else {
                    if (n.getClass() == SearchBox.class) {

                        SearchBox general = (SearchBox) n;
                        if (general.getProperties().get("requerido") != null) {
                            if ((general.getText().length() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
                                permitirproceso = false;
                                general.setStyle("-fx-background-color:#ff1600");
                            }
                        }
                    }
                }
            }
        }
        FadeTransition ft = new FadeTransition(Duration.millis(2000), save);
        //ft.setFromValue(0.0);
        // ft.setToValue(1);

        ft.play();

        //success.setOpacity(0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Object n : gp_generic.getChildren().toArray()) {
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

                            if (general.getSelectionModel().getSelectedIndex() < 0 && general.getProperties().get("requerido") == null) {
                                general.getStylesheets().set(0, SihicApp.hojaestilos);

                            }

                        } else {
                            if (n.getClass() == SearchBox.class) {

                                SearchBox general = (SearchBox) n;
                                if (general.getProperties().get("requerido") != null) {

                                    general.setStyle(null);
                                    general.getStyleClass().add("text-field");
                                }
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

    private void nuevoRecord() {
        SihicApp.novedades = null;
        SihicApp.novedades = new Novedades();
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");
                general.setDisable(false);
            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    general.getSelectionModel().select(0);
                    general.setDisable(false);
                }

                if (n.getClass() == DatePicker.class) {
                    DatePicker general = (DatePicker) n;
                    general.setValue(LocalDate.now());
                    general.setDisable(false);
                }
                if (n.getClass() == SearchBox.class) {
                    SearchBox general = (SearchBox) n;
                    general.setDisable(false);
                }
            }

        }

    }

    public void disabled_controles() {
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setDisable(true);
            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;
                    general.setDisable(true);
                }

                if (n.getClass() == DatePicker.class) {
                    DatePicker general = (DatePicker) n;
                    general.setDisable(true);
                }
                if (n.getClass() == SearchBox.class) {
                    SearchBox general = (SearchBox) n;
                    general.setDisable(true);
                }
            }

        }
    }

    public void crearoeditar() {

        if (SihicApp.novedades != null) {
            if (SihicApp.novedades.getId() != null) {
             cantidad.setText(String.valueOf(SihicApp.novedades.getCantidad()));
             valor.setText(SihicApp.novedades.getValor().toString());
             novedad.getSelectionModel().select(SihicApp.novedades.getTiponovedad());
             LoadChoiceBoxGeneral.cb_tiposdocumentoscontables.getSelectionModel().select(LoadChoiceBoxGeneral.v_factorhorasextras.indexOf(SihicApp.novedades.getTipohoraextra()));
             observacion.setText(SihicApp.novedades.getObservacion());
            } else {
                nuevoRecord();
            }
        } else {
            nuevoRecord();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
   
    
    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
      
            
          if(newValue.intValue()==jenum.EnumTipoNovedad.HORASEXTRAS1.ordinal())
          {
              if(gp_generic.getChildren().indexOf(LoadChoiceBoxGeneral.cb_factorhorasextras)==-1)
            {
                gp_generic.getChildren().add(la_tipohoraextra);
                gp_generic.getChildren().add(LoadChoiceBoxGeneral.cb_factorhorasextras);  
                
            }  
             if(gp_generic.getChildren().indexOf(cantidad)==-1)
            {
                gp_generic.getChildren().add(la_cantidad);
                gp_generic.getChildren().add(cantidad);  
                
            } 
              if(gp_generic.getChildren().indexOf(valor)!=-1)
            {
                gp_generic.getChildren().remove(la_valor);
                gp_generic.getChildren().remove(valor);  
                
            } 
          }
          else
          {
          if(newValue.intValue()==jenum.EnumTipoNovedad.DIASNOTRABAJADOS5.ordinal())
          {
              if(gp_generic.getChildren().indexOf(LoadChoiceBoxGeneral.cb_factorhorasextras)!=-1)
            {
                gp_generic.getChildren().remove(la_tipohoraextra);
                gp_generic.getChildren().remove(LoadChoiceBoxGeneral.cb_factorhorasextras);  
                
            }  
             if(gp_generic.getChildren().indexOf(cantidad)==-1)
            {
                gp_generic.getChildren().add(la_cantidad);
                gp_generic.getChildren().add(cantidad);  
                
            } 
              if(gp_generic.getChildren().indexOf(valor)!=-1)
            {
                gp_generic.getChildren().remove(la_valor);
                gp_generic.getChildren().remove(valor);  
                
            } 
          }
          else
          {
              if(newValue.intValue()!=jenum.EnumTipoNovedad.DIASNOTRABAJADOS5.ordinal() && newValue.intValue()!=jenum.EnumTipoNovedad.HORASEXTRAS1.ordinal())
          {
              if(gp_generic.getChildren().indexOf(LoadChoiceBoxGeneral.cb_factorhorasextras)!=-1)
            {
                gp_generic.getChildren().remove(la_tipohoraextra);
                gp_generic.getChildren().remove(LoadChoiceBoxGeneral.cb_factorhorasextras);  
                
            }  
             if(gp_generic.getChildren().indexOf(cantidad)!=-1)
            {
                gp_generic.getChildren().remove(la_cantidad);
                gp_generic.getChildren().remove(cantidad);  
                
            } 
              if(gp_generic.getChildren().indexOf(valor)==-1)
            {
                gp_generic.getChildren().add(la_valor);
                gp_generic.getChildren().add(valor);  
                
            } 
          }
          }
          }
          
              
          
          
      
    }
}
