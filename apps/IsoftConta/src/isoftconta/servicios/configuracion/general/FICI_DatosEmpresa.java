/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios.configuracion.general;


import isoftconta.ContabilidadFXServicios;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import isoftconta.searchpopover.SearchPopover;
import isoftconta.servicios.CiudadesController;
import isoftconta.servicios.DatosPymeController;
import isoftconta.servicios.TercerosController;
import isoftconta.servicios.pasivos.cuentasporpagar.FICI_CausacionEgreso;
import isoftconta.util.Utils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;


import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import modelo.DatosPyme;
import modelo.EntidadesStatic;
import modelo.GenMunicipios;
import modelo.Terceros;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author adminlinux
 */
public class FICI_DatosEmpresa  extends ContabilidadFXServicios{
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
    private static SearchPopover sp_ciudad;
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
    private ImageView logo;
    private Button bu_logo;
    private Image loadlogo;
    private File fileLogo;
    private FileChooser fileChooser = new FileChooser();
    private WritableImage wi;
    private BufferedImage bi;
    private Stage stage;
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getServicioNombre() {
        return "Datos de empresa";
    }
    @Override
    public Node getPanel(Stage stage) { 
         logo=new ImageView("/images/foto.png");
         logo.setFitHeight(100);
         logo.setFitWidth(100);
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
        randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_logo=  IsoftConta.icoMoon.create(FontAwesome.Glyph.FILE_PICTURE_ALT.getChar()).sizeFactor(2).color(randomColor).useGradientEffect();
 
        EntidadesStatic.es_ciudades=new GenMunicipios();
        
        cb_tipodocumento.getItems().clear();
        cb_tipodocumento.getItems().addAll("Registro civil","Tarjeta de identidad","Cédula de ciudadanía","Tarjeta de extranjería","Cédula de extrajería","Pasaporte","Documento de identificación extranjero","NIT de otro país","NUIP*");
        cb_tipopersona.getItems().clear();
        cb_tipopersona.getItems().addAll("Natural","Persona Jurídica");
        cb_responsabilidades.getItems().clear();
        cb_responsabilidades.getItems().addAll("Régimen simple de tributación","Impuesto sobre las ventas","No responsable de IVA","No responsable consumo restaurante, bar","Ag. retención impoconsumo bien inmueble");
      
        stack = new StackPane();
        no_documento.getProperties().put("requerido", true);
        nombre = new TextField("");
        nombre.getProperties().put("requerido", true);
        
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
          bu_logo = new Button("",glyph_logo);
          bu_logo.setTooltip(new Tooltip("Cargar imagen..."));
          bu_logo.setOnAction(e->{LoadImageLogo();
         });
        toolBar.getItems().clear();
        toolBar.getItems().addAll(save,nuevo,bu_logo);
        gp_generic = new GridPane();
        gp_generic.setPadding(new Insets(5, 5, 5, 5));
        gp_generic.add(toolBar, 0, 0,4,1);
        gp_generic.add(logo, 3, 1);
        gp_generic.add(new Label("NIT: "), 0, 2);
        gp_generic.add(numeroidentificaciontributaria, 1, 2);
        gp_generic.add(new Label("Digito de  verificación: "), 2, 2);
        gp_generic.add(digitoverificacion, 3, 2);
        gp_generic.add(new Label("Tipo persona: "), 0, 3);
        gp_generic.add(cb_tipopersona, 1, 3);
        gp_generic.add(new Label("Tipo documento: "), 2, 3);
        gp_generic.add(cb_tipodocumento, 3, 3);
        gp_generic.add(new Label("N° documento: "), 0, 4);
        gp_generic.add(no_documento, 1, 4);
        gp_generic.add(new Label("Primer apellido: "), 2, 4);
        gp_generic.add(primer_apellido, 3, 4);
        gp_generic.add(new Label("Segundo apellido: "), 0, 5);
        gp_generic.add(segundo_apellido, 1, 5);
        gp_generic.add(new Label("Primer nombre: "), 2, 5);
        gp_generic.add(nombre, 3, 5);
        gp_generic.add(new Label("Otros Nombres: "), 0, 6);
        gp_generic.add(otrosnombres, 1, 6);
        gp_generic.add(new Label("Ciudad: "), 2, 6);
        gp_generic.add(sb_ciudad, 3, 6);
        gp_generic.add(new Label("Profesión: "), 0, 7);
        gp_generic.add(profesion, 1, 7);
        gp_generic.add(new Label("Dirección: "), 2, 7);
        gp_generic.add(dir, 3, 7);
        gp_generic.add(new Label("Telefono: "), 0, 8);
        gp_generic.add(telefono, 1, 8);
        gp_generic.add(new Label("Email: "), 2, 8);
        gp_generic.add(email, 3, 8);
        gp_generic.add(new Label("Razón social: "), 0, 9);
        gp_generic.add(razonsocial, 1, 9);
        gp_generic.add(new Label("Código actividad económica: "), 2, 9);
        gp_generic.add(codigoactividadeconomica, 3, 9);
        gp_generic.add(new Label("Responsabilidad: "), 0, 10);
        gp_generic.add(cb_responsabilidades, 1, 10);
        gp_generic.add(new Label("Responsabilidades Fiscales: "), 2, 10);
        GridPane.setValignment(vb_responsabilidadesfiscale, VPos.BASELINE);
        gp_generic.add(vb_responsabilidadesfiscale, 3, 10,1,3);
        
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic);
        try {
            crearoeditar();
        } catch (IOException ex) {
            Logger.getLogger(FICI_DatosEmpresa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
   return stack;
    }

  
    private void nuevo() {
        EntidadesStatic.es_datospyme= null;
       EntidadesStatic.es_datospyme = new DatosPyme();
       Utils.empty_field(gp_generic);
        cb_responsabilidades.getSelectionModel().select(2);
        cb_tipodocumento.getSelectionModel().select(2);
        cb_tipopersona.getSelectionModel().select(0);
    }

    public  void save() throws InterruptedException {
        
            set_datos();
               if (EntidadesStatic.es_datospyme.getId() == null) 
               {
                      
                   int resultprocess= DatosPymeController.servicio_crear();
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
               {
                    DatosPymeController.servicio_actualizar();
                     notificationBuilder.text("Registro  actualizado satisfactoriamente")
                                            .position(Pos.TOP_RIGHT)
                                            .showConfirm();
                }
               if(refrescardatossegunorigen==isoftconta.util.EnumOrigenTerceros.FCAUSACIONEGRESOS0.ordinal())
               {
                   FICI_CausacionEgreso.colocardatosproveedor();
               }
              
               
    }

    private  void set_datos() {

        EntidadesStatic.es_datospyme.setNumeroidentificacion(no_documento.getText().trim());
        EntidadesStatic.es_datospyme.setNuidenttributaria(numeroidentificaciontributaria.getText().trim());
        EntidadesStatic.es_datospyme.setPrimernombre(nombre.getText());
        EntidadesStatic.es_datospyme.setOtrosnombres(otrosnombres.getText());
        EntidadesStatic.es_datospyme.setPrimerapellido(primer_apellido.getText());
        EntidadesStatic.es_datospyme.setSegundoapellido(segundo_apellido.getText());
        EntidadesStatic.es_datospyme.setTelefono1(telefono.getText());
        EntidadesStatic.es_datospyme.setEmail(email.getText());
        EntidadesStatic.es_datospyme.setDireccion(dir.getText());
        if(EntidadesStatic.es_ciudades!=null)
        { 
        if(EntidadesStatic.es_ciudades.getId()!=null)
        { 
        EntidadesStatic.es_datospyme.setCiudad(EntidadesStatic.es_ciudades);
        }  else   EntidadesStatic.es_datospyme.setCiudad(null);
        }
        else   EntidadesStatic.es_datospyme.setCiudad(null);
        EntidadesStatic.es_datospyme.setTipoidentificacion(String.valueOf(cb_tipodocumento.getSelectionModel().getSelectedIndex()));
        EntidadesStatic.es_datospyme.setTipopersona(String.valueOf(cb_tipopersona.getSelectionModel().getSelectedIndex()));
        EntidadesStatic.es_datospyme.setCodigoactivadadeconomica(codigoactividadeconomica.getText()!=null?codigoactividadeconomica.getText().trim():"0");
        EntidadesStatic.es_datospyme.setDigitoverificacion(digitoverificacion.getText());
        EntidadesStatic.es_datospyme.setRazonsocial(razonsocial.getText()!=null?razonsocial.getText().trim():"N/A");
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
        EntidadesStatic.es_datospyme.setCodigoresponsabilidad(codrespon);
        EntidadesStatic.es_datospyme.setEsgrancontribuyente(chb_esgrancontribuyente.isSelected());
        EntidadesStatic.es_datospyme.setEsautoretenedor(chb_esautoretenedor.isSelected());
        EntidadesStatic.es_datospyme.setEsagenteretenedoriva(chb_esagenteretencioniva.isSelected());
      
        save_img();
    }
private void getDatos() throws IOException 
{
        no_documento.setText(EntidadesStatic.es_datospyme.getNumeroidentificacion());
        numeroidentificaciontributaria.setText(EntidadesStatic.es_datospyme.getNuidenttributaria());
        nombre.setText(EntidadesStatic.es_datospyme.getPrimernombre());
        otrosnombres.setText(EntidadesStatic.es_datospyme.getOtrosnombres());
        dir.setText(EntidadesStatic.es_datospyme.getDireccion());
        telefono.setText(EntidadesStatic.es_datospyme.getTelefono1());
        email.setText(EntidadesStatic.es_datospyme.getEmail());
        primer_apellido.setText(EntidadesStatic.es_datospyme.getPrimerapellido());
        segundo_apellido.setText(EntidadesStatic.es_datospyme.getSegundoapellido());
        
        try
        {
            int index=Integer.valueOf(EntidadesStatic.es_datospyme.getTipoidentificacion());
            System.out.println("index->"+Integer.valueOf(EntidadesStatic.es_datospyme.getTipoidentificacion()));
        cb_tipodocumento.getSelectionModel().select(index);
        }catch(Exception e)
        {
             cb_tipodocumento.getSelectionModel().select(0);
       
        }
         
        try
        {
       
        int index=Integer.valueOf(EntidadesStatic.es_datospyme.getTipopersona());
        cb_tipopersona.getSelectionModel().select(index);
         }catch(Exception e)
        {
             cb_tipopersona.getSelectionModel().select(0);
       
        }
        codigoactividadeconomica.setText(EntidadesStatic.es_datospyme.getCodigoactividadeconomica());
        digitoverificacion.setText(EntidadesStatic.es_datospyme.getDigitoverificacion());
        razonsocial.setText(EntidadesStatic.es_datospyme.getRazonsocial());
        int index=0;
        switch(EntidadesStatic.es_datospyme.getCodigoresponsabilidad())
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
        chb_esagenteretencioniva.setSelected(EntidadesStatic.es_datospyme.isEsagenteretenedoriva());
        chb_esautoretenedor.setSelected(EntidadesStatic.es_datospyme.isEsautoretenedor());
        chb_esgrancontribuyente.setSelected(EntidadesStatic.es_datospyme.isEsgrancontribuyente());
        EntidadesStatic.es_ciudades=EntidadesStatic.es_datospyme.getCiudad();
        if(EntidadesStatic.es_ciudades!=null)
        {
           sb_ciudad.setText(EntidadesStatic.es_ciudades.getCodigo()+" "+EntidadesStatic.es_ciudades.getNombre()); 
        }  
        if(EntidadesStatic.es_datospyme.getLogo()!=null)
        {
           InputStream in = new ByteArrayInputStream(EntidadesStatic.es_datospyme.getLogo());
          bi = ImageIO.read(in);
          wi=new WritableImage(1,1);
          wi=SwingFXUtils.toFXImage(bi, wi);
          //img=new ImageView();
          loadlogo=wi;
          logo.setImage(loadlogo);
        }
    }
    public void crearoeditar() throws IOException {
          EntidadesStatic.es_datospyme=DatosPymeController.getRecord();
        if (EntidadesStatic.es_datospyme != null) {
           if (EntidadesStatic.es_datospyme.getId() != null) {
           
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
     
     
 public void LoadImageLogo() {
        
       
        fileLogo=fileChooser.showOpenDialog(stage);
         
        if(fileLogo!=null)
        {
            byte[]img2;
            try {
                   bi = ImageIO.read(fileLogo);
                  
                  wi=SwingFXUtils.toFXImage(bi, wi);
                  loadlogo=wi;
                } catch (IOException ex) {
                   
                }
        }
    
        logo.setImage(loadlogo);
    }

public void save_img()
{
        byte[]data;
        InputStream in;
        try{
        in = new FileInputStream(fileLogo);
        data=new byte[in.available()];
	in.read(data);
        EntidadesStatic.es_datospyme.setLogo(data);
        }catch(Exception e)
        {
           
        }
}  

}