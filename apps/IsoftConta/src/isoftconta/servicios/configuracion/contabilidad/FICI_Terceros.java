/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios.configuracion.contabilidad;

import java.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import isoftconta.IsoftConta;
import isoftconta.fields.FieldNumero;
import isoftconta.glyphfont.Glyph;
import isoftconta.servicios.CiudadesController;
import isoftconta.servicios.TercerosController;
import isoftconta.servicios.pasivos.cuentasporpagar.FICI_CausacionEgreso;
import isoftconta.util.Utils;
import javafx.geometry.VPos;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import modelo.EntidadesStatic;
import modelo.GenMunicipios;
import modelo.Terceros;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author adminlinux
 */
public class FICI_Terceros extends Application{
     StackPane stack;
    private GridPane gp_generic = new GridPane();
    private static FieldNumero no_documento = new FieldNumero(10);
    private static FieldNumero digitoverificacion = new FieldNumero(1);
    private static FieldNumero numeroidentificaciontributaria=new FieldNumero(10);
    private static TextField nombre = new TextField();
    private static TextField otrosnombres = new TextField();
    private static TextField primer_apellido = new TextField();
    private static TextField segundo_apellido = new TextField();
    private static TextField razonsocial = new TextField();
    private static final ChoiceBox cb_tipopersona=new ChoiceBox();
    private static final ChoiceBox cb_tipodocumento=new ChoiceBox();
    private static final ChoiceBox cb_responsabilidades=new ChoiceBox();
    private static CheckBox responsableiva=new CheckBox();
    private static TextField telefono = new TextField();
    private static TextField dir = new TextField();
    private static TextField email = new TextField();
    private static TextField profesion = new TextField();
    private static final TextField sb_ciudad=new TextField();
    private static FieldNumero codigoactividadeconomica=new FieldNumero(4);
    private Button save;
    private Button nuevo;
    private ToolBar toolBar=new ToolBar();
    public static int refrescardatossegunorigen=0;
    private static Notifications notificationBuilder = Notifications.create();
    private  static CheckBox chb_esgrancontribuyente=new CheckBox("Gran contribuyente");
    private static CheckBox chb_esautoretenedor=new CheckBox("Autorretenedor");
    private static CheckBox chb_esagenteretencioniva=new CheckBox("Agente de retención de IVA");
    private VBox vb_responsabilidadesfiscale=new VBox(chb_esgrancontribuyente,chb_esautoretenedor,chb_esagenteretencioniva);
    public Parent createContent() throws IOException {
        //sb***********************************************
        TextFields.bindAutoCompletion(sb_ciudad, EntidadesStatic.as_ciudades);
        chb_esagenteretencioniva.setSelected(false);
        chb_esagenteretencioniva.setTextAlignment(TextAlignment.LEFT);
        chb_esautoretenedor.setSelected(false);
        chb_esautoretenedor.setTextAlignment(TextAlignment.LEFT);
        chb_esgrancontribuyente.setSelected(false);
        chb_esgrancontribuyente.setTextAlignment(TextAlignment.LEFT);
        TextFields.bindAutoCompletion(sb_ciudad, EntidadesStatic.as_ciudades);
         sb_ciudad.setOnAction(e -> {
            
            profesion.requestFocus();

        });
         
      sb_ciudad.focusedProperty().addListener(new ChangeListener<Boolean>()
      {
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
        CiudadesController.findciudadporcodigo(sb_ciudad.getText());
    }
    });
        char IM_BOLD = '\uf0c7';
        char IM_NUEVO = '\uf0fe';
        char IM_PDF = '\uf1c1';
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_save=  IsoftConta.icoMoon.create(IM_BOLD).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_NUEVO=  IsoftConta.icoMoon.create(IM_NUEVO).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_PDF=  IsoftConta.icoMoon.create(IM_PDF).sizeFactor(2).color(randomColor).useGradientEffect();
 
        EntidadesStatic.es_ciudades=new GenMunicipios();
        sb_ciudad.setMaxWidth(200);
        sb_ciudad.setMinWidth(200);
        cb_tipodocumento.getItems().clear();
        cb_tipodocumento.getItems().addAll("Registro civil","Tarjeta de identidad","Cédula de ciudadanía","Tarjeta de extranjería","Cédula de extrajería","Pasaporte","Documento de identificación extranjero","NIT de otro país","NUIP*");
        cb_tipopersona.getItems().clear();
        cb_tipopersona.getItems().addAll("Natural","Persona Jurídica");
        cb_responsabilidades.getItems().clear();
        cb_responsabilidades.getItems().addAll("Régimen simple de tributación","Impuesto sobre las ventas","No responsable de IVA","No responsable de consumo restaurantes y bares","Agente  retención impoconsumo de bienes inmuebles");
      
        stack = new StackPane();
        no_documento.getProperties().put("requerido", true);
        nombre = new TextField("");
        nombre.getProperties().put("requerido", true);
        nombre.setMinWidth(300);
        dir = new TextField("");
        telefono = new TextField("");
        telefono.getProperties().put("requerido", true);
        email = new TextField("");
         save = new Button("",glyph_save);
        save.setTooltip(new Tooltip("Guardar Cliente"));
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
         nuevo = new Button("",glyph_NUEVO);
        nuevo.setTooltip(new Tooltip("Nuevo Proveedor"));
        nuevo.setOnAction(e -> {
            nuevo();
        });
      
        toolBar.getItems().addAll(save,nuevo);
        gp_generic = new GridPane();
        gp_generic.setPadding(new Insets(5, 5, 5, 5));
        gp_generic.add(toolBar, 0, 0,4,1);
        gp_generic.add(new Label("NIT: "), 0, 1);
        gp_generic.add(numeroidentificaciontributaria, 1, 1);
        gp_generic.add(new Label("Digito de  verificación: "), 2, 1);
        gp_generic.add(digitoverificacion, 3, 1);
        gp_generic.add(new Label("Tipo persona: "), 0, 2);
        gp_generic.add(cb_tipopersona, 1, 2);
        gp_generic.add(new Label("Tipo documento: "), 2, 2);
        gp_generic.add(cb_tipodocumento, 3, 2);
        gp_generic.add(new Label("N° documento: "), 0, 3);
        gp_generic.add(no_documento, 1, 3);
        gp_generic.add(new Label("Primer apellido: "), 2, 3);
        gp_generic.add(primer_apellido, 3, 3);
        gp_generic.add(new Label("Segundo apellido: "), 0, 4);
        gp_generic.add(segundo_apellido, 1, 4);
        gp_generic.add(new Label("Primer nombre: "), 2, 4);
        gp_generic.add(nombre, 3, 4);
        gp_generic.add(new Label("Otros Nombres: "), 0, 5);
        gp_generic.add(otrosnombres, 1, 5);
        gp_generic.add(new Label("Ciudad: "), 2, 5);
        gp_generic.add(sb_ciudad, 3, 5);
        gp_generic.add(new Label("Profesión: "), 0, 6);
        gp_generic.add(profesion, 1, 6);
        gp_generic.add(new Label("Dirección: "), 2, 6);
        gp_generic.add(dir, 3, 6);
        gp_generic.add(new Label("Telefono: "), 0, 7);
        gp_generic.add(telefono, 1, 7);
        gp_generic.add(new Label("Email: "), 2, 7);
        gp_generic.add(email, 3, 7);
        gp_generic.add(new Label("Razón social: "), 0, 8);
        gp_generic.add(razonsocial, 1, 8);
        gp_generic.add(new Label("Código actividad económica: "), 2, 8);
        gp_generic.add(codigoactividadeconomica, 3, 8);
        gp_generic.add(new Label("Responsabilidades: "), 0, 9);
        gp_generic.add(cb_responsabilidades, 1, 9);
        gp_generic.add(new Label("Responsabilidades Fiscales: "), 2, 9);
        GridPane.setValignment(vb_responsabilidadesfiscale, VPos.BASELINE);
        gp_generic.add(vb_responsabilidadesfiscale, 3, 9,1,3);
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic);
        crearoeditar();
        
      
   return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void nuevo() {
        EntidadesStatic.es_terceros= null;
       EntidadesStatic.es_terceros = new Terceros();
       Utils.empty_field(gp_generic);
        cb_responsabilidades.getSelectionModel().select(2);
        cb_tipodocumento.getSelectionModel().select(2);
        cb_tipopersona.getSelectionModel().select(0);
    }

    public  void save() throws InterruptedException {
        
            set_datos();
               if (EntidadesStatic.es_terceros.getId() == null) 
               {
                   if(TercerosController.findbyident(EntidadesStatic.es_terceros.getNo_ident())==null)
                   {    
                   int resultprocess= TercerosController.servicio_crear();
                   if(resultprocess==1)
                   {
                       
                     notificationBuilder.text("Registro guardado satisfactoriamente")
                                       .position(Pos.TOP_RIGHT)
                                        .showConfirm();
                   }
                   else
                   {
                       notificationBuilder.text("Error en el proceso al guardar")
                                       .position(Pos.TOP_RIGHT)
                                        .showError();
                   }
                   }else
                                 notificationBuilder.text("El número de Identificación ya existe")
                                       .position(Pos.TOP_RIGHT)
                                        .showError();
                    
                } else 
               {
                    TercerosController.servicio_actualizar();
                     notificationBuilder.text("Registro  actualizado satisfactoriamente")
                                            .position(Pos.TOP_RIGHT)
                                            .showConfirm();
                }
               if(refrescardatossegunorigen==isoftconta.util.EnumOrigenTerceros.FCAUSACIONEGRESOS0.ordinal())
               {
                   FICI_CausacionEgreso.colocardatosproveedor();
               }
              
               
    }

    private static void set_datos() {

        EntidadesStatic.es_terceros.setNo_ident(no_documento.getText().trim());
        EntidadesStatic.es_terceros.setNuidenttributaria(numeroidentificaciontributaria.getText().trim());
        EntidadesStatic.es_terceros.setPrimernombre(nombre.getText());
        EntidadesStatic.es_terceros.setOtrosnombres(otrosnombres.getText());
        EntidadesStatic.es_terceros.setPrimerapellido(primer_apellido.getText());
        EntidadesStatic.es_terceros.setSegundoapellido(segundo_apellido.getText());
        EntidadesStatic.es_terceros.setCelular(telefono.getText());
        EntidadesStatic.es_terceros.setEmail(email.getText());
        EntidadesStatic.es_terceros.setDir1(dir.getText());
        if(EntidadesStatic.es_ciudades!=null)
        { 
        if(EntidadesStatic.es_ciudades.getId()!=null)
        { 
        EntidadesStatic.es_terceros.setCiudad(EntidadesStatic.es_ciudades);
        }  else   EntidadesStatic.es_terceros.setCiudad(null);
        }
        else   EntidadesStatic.es_terceros.setCiudad(null);
        EntidadesStatic.es_terceros.setTipoidentificacion(String.valueOf(cb_tipodocumento.getSelectionModel().getSelectedIndex()));
        EntidadesStatic.es_terceros.setTipopersona(String.valueOf(cb_tipopersona.getSelectionModel().getSelectedIndex()));
        EntidadesStatic.es_terceros.setCodigoactivadadeconomica(codigoactividadeconomica.getText()!=null?codigoactividadeconomica.getText().trim():"0");
        EntidadesStatic.es_terceros.setDigitoverificacion(digitoverificacion.getText());
        EntidadesStatic.es_terceros.setRazonsocial(razonsocial.getText()!=null?razonsocial.getText().trim():"N/A");
        EntidadesStatic.es_terceros.setProfesion(profesion.getText()!=null?profesion.getText().trim():"N/A");
        String codrespon="";
        switch(cb_responsabilidades.getSelectionModel().getSelectedIndex())
        {
            case 0: codrespon="47";
                     break;
            case 1: codrespon="48";
                     break;
            case 2: codrespon="49";
                     break; 
            case 3: codrespon="50";
                     break;    
            case 4: codrespon="51";
                     break;         
        }
        EntidadesStatic.es_terceros.setCodigoresponsabilidad(codrespon);
        EntidadesStatic.es_terceros.setEsgrancontribuyente(chb_esgrancontribuyente.isSelected());
        EntidadesStatic.es_terceros.setEsautoretenedor(chb_esautoretenedor.isSelected());
        EntidadesStatic.es_terceros.setEsagenteretenedoriva(chb_esagenteretencioniva.isSelected());
        EntidadesStatic.es_terceros.setTipotercero("0");
    }
private void getDatos() 
{
        no_documento.setText(EntidadesStatic.es_terceros.getNo_ident());
        nombre.setText(EntidadesStatic.es_terceros.getPrimernombre());
        otrosnombres.setText(EntidadesStatic.es_terceros.getOtrosnombres());
        dir.setText(EntidadesStatic.es_terceros.getDir1());
        telefono.setText(EntidadesStatic.es_terceros.getCelular());
        email.setText(EntidadesStatic.es_terceros.getEmail());
        primer_apellido.setText(EntidadesStatic.es_terceros.getPrimerapellido());
        segundo_apellido.setText(EntidadesStatic.es_terceros.getSegundoapellido());
        profesion.setText(EntidadesStatic.es_terceros.getProfesion());
        try
        {
            int index=Integer.valueOf(EntidadesStatic.es_terceros.getTipoidentificacion());
            System.out.println("index->"+Integer.valueOf(EntidadesStatic.es_terceros.getTipoidentificacion()));
        cb_tipodocumento.getSelectionModel().select(index);
        }catch(Exception e)
        {
             cb_tipodocumento.getSelectionModel().select(0);
       
        }
         
        try
        {
       
        int index=Integer.valueOf(EntidadesStatic.es_terceros.getTipopersona());
        cb_tipopersona.getSelectionModel().select(index);
         }catch(Exception e)
        {
             cb_tipopersona.getSelectionModel().select(0);
       
        }
        codigoactividadeconomica.setText(EntidadesStatic.es_terceros.getCodigoactivadadeconomica());
        digitoverificacion.setText(EntidadesStatic.es_terceros.getDigitoverificacion());
        razonsocial.setText(EntidadesStatic.es_terceros.getRazonsocial());
        int index=0;
        switch(EntidadesStatic.es_terceros.getCodigoresponsabilidad())
        {
            case "47": index=0;
                     break;
            case "48": index=1;
                     break;
            case "49": index=2;
                     break; 
            case "50": index=3;
                     break;    
            case "51": index=4;
                     break;         
        }
        cb_responsabilidades.getSelectionModel().select(index);
        chb_esagenteretencioniva.setSelected(EntidadesStatic.es_terceros.isEsagenteretenedoriva());
        chb_esautoretenedor.setSelected(EntidadesStatic.es_terceros.isEsautoretenedor());
        chb_esgrancontribuyente.setSelected(EntidadesStatic.es_terceros.isEsgrancontribuyente());
        EntidadesStatic.es_ciudades=EntidadesStatic.es_terceros.getCiudad();
        if(EntidadesStatic.es_ciudades!=null)
        {
           sb_ciudad.setText(EntidadesStatic.es_ciudades.getCodigo()+" "+EntidadesStatic.es_ciudades.getNombre()); 
        }   
        
    }
    public void crearoeditar() {

        if (EntidadesStatic.es_terceros != null) {
           if (EntidadesStatic.es_terceros.getId() != null) {
           
            getDatos();
           }
           else
           {
             nuevo();  
           }
        } else {
            
            nuevo();
        }

    }
     
     
   
}
