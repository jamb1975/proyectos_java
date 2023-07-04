/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios.configuracion.contabilidad;


import isoftconta.ContabilidadFXServicios;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import isoftconta.IsoftConta;
import isoftconta.fields.FieldDouble;
import isoftconta.fields.FieldNumero;
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import isoftconta.servicios.ParametrosContabilidadController;
import isoftconta.util.Utils;
import java.io.IOException;
import java.math.BigDecimal;
import static javafx.application.Application.launch;


import javafx.scene.Node;
import modelo.EntidadesStatic;
import modelo.ParametrosContabilidad;
import org.controlsfx.control.Notifications;

/**
 *
 * @author adminlinux
 */
public class FICI_Parametros  extends ContabilidadFXServicios{
     StackPane stack;
    private GridPane gp_generic = new GridPane();
    private static FieldNumero valorunidadvalortributario = new FieldNumero(10);
    private static FieldNumero cantuvtcomprasretefuente = new FieldNumero(10);
    private static FieldDouble porccomprasretefuenterenta=new FieldDouble();
    private static FieldDouble porccomprasretefuenteiva=new FieldDouble();
    private static FieldNumero cantuvtpatrimoniooingresosbrutosmayor= new FieldNumero(10);
    private static FieldNumero valorañoanteriorpatrimoniooingresosbrutos= new FieldNumero(10);
    
    private Button save;
    private Button nuevo;
    private ToolBar toolBar=new ToolBar();
    public static int refrescardatossegunorigen=0;
    private static Notifications notificationBuilder = Notifications.create();
  
    
    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getServicioNombre() {
        return "Parámetros generales";
    }
    @Override
    public Node getPanel(Stage stage) { 
       
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
 
        
        stack = new StackPane();
        valorunidadvalortributario.getProperties().put("requerido", true);
        save = new Button("",glyph_save);
        save.setTooltip(new Tooltip("Guardar registro"));
        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        nuevo = new Button("",glyph_NUEVO);
        nuevo.setTooltip(new Tooltip("Nuevo registro"));
        nuevo.setOnAction(e -> {
            nuevo();
        });
          
        toolBar.getItems().clear();
        toolBar.getItems().addAll(save,nuevo);
        gp_generic = new GridPane();
        gp_generic.setPadding(new Insets(5, 5, 5, 5));
        gp_generic.add(toolBar, 0, 0,4,1);
        gp_generic.add(new Label("Unidad de valor tributario(UVT): "), 0, 1);
        gp_generic.add(valorunidadvalortributario, 1, 1);
        gp_generic.add(new Label("Valor ingresos o patrimonio bruto año anterior: "), 0, 2);
        gp_generic.add(valorañoanteriorpatrimoniooingresosbrutos, 1, 2);
        gp_generic.add(new Label("Cantidad UVT base retefuente compras: "), 0, 3);
        gp_generic.add(cantuvtcomprasretefuente, 1, 3);
        gp_generic.add(new Label("Ingresos o patrimonio brutos mayor a cantidad UVT: "), 0, 4);
        gp_generic.add(cantuvtpatrimoniooingresosbrutosmayor, 1, 4);
        gp_generic.add(new Label("% Retefuente a titulo renta compras: "), 0, 5);
        gp_generic.add(porccomprasretefuenterenta, 1, 5);
        gp_generic.add(new Label("% Retefuente a titulo IVA compras: "), 0, 6);
        gp_generic.add(porccomprasretefuenteiva, 1, 6);
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic);
        try {
            crearoeditar();
        } catch (IOException ex) {
         
        }
        
      
   return stack;
    }

  
    private void nuevo() {
        EntidadesStatic.es_parametroscontabilidad= null;
       EntidadesStatic.es_parametroscontabilidad = new ParametrosContabilidad();
       Utils.empty_field(gp_generic);
       
    }

    public  void save() throws InterruptedException {
        
            set_datos();
               if (EntidadesStatic.es_parametroscontabilidad.getId() == null) 
               {
                      
                   int resultprocess= ParametrosContabilidadController.servicio_crear();
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
                    ParametrosContabilidadController.servicio_actualizar();
                     notificationBuilder.text("Registro  actualizado satisfactoriamente")
                                            .position(Pos.TOP_RIGHT)
                                            .showConfirm();
                }
              
              
               
    }

    private  void set_datos() {

        EntidadesStatic.es_parametroscontabilidad.setValorunidadvalortributario(new BigDecimal(valorunidadvalortributario.getText().trim()));
        EntidadesStatic.es_parametroscontabilidad.setValorañoanteriorpatrimoniooingresosbrutos(new BigDecimal(valorañoanteriorpatrimoniooingresosbrutos.getText().trim()));
        EntidadesStatic.es_parametroscontabilidad.setPorccomprasretefuenterenta(new BigDecimal(porccomprasretefuenterenta.getText().trim()));
        EntidadesStatic.es_parametroscontabilidad.setPorccomprasretefuenteiva(new BigDecimal(porccomprasretefuenteiva.getText().trim()));
        EntidadesStatic.es_parametroscontabilidad.setCantuvtcomprasretefuente(Integer.valueOf(cantuvtcomprasretefuente.getText().trim()));
        EntidadesStatic.es_parametroscontabilidad.setCantuvtpatrimoniooingresosbrutosmayor(Integer.valueOf(cantuvtpatrimoniooingresosbrutosmayor.getText().trim()));
       
    }
private void getDatos() throws IOException 
{
        valorunidadvalortributario.setText(EntidadesStatic.es_parametroscontabilidad.getValorunidadvalortributario().toString());
        valorañoanteriorpatrimoniooingresosbrutos.setText(EntidadesStatic.es_parametroscontabilidad.getValorañoanteriorpatrimoniooingresosbrutos().toEngineeringString());
        porccomprasretefuenterenta.setText(EntidadesStatic.es_parametroscontabilidad.getPorccomprasretefuenterenta().toString());
        porccomprasretefuenteiva.setText(EntidadesStatic.es_parametroscontabilidad.getPorccomprasretefuenteiva().toString());
        cantuvtcomprasretefuente.setText(String.valueOf(EntidadesStatic.es_parametroscontabilidad.getCantuvtcomprasretefuente()));
        cantuvtpatrimoniooingresosbrutosmayor.setText(String.valueOf(EntidadesStatic.es_parametroscontabilidad.getCantuvtpatrimoniooingresosbrutosmayor()));
 }
    public void crearoeditar() throws IOException {
          EntidadesStatic.es_parametroscontabilidad=ParametrosContabilidadController.getRecord();
        if (EntidadesStatic.es_parametroscontabilidad != null) {
           if (EntidadesStatic.es_parametroscontabilidad.getId() != null) {
           
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