/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javiconta.configuracion.contabilidad;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

/**
 *
 * @author adminlinux
 */
public class MenuContabilidad extends Application{
    
    private GridPane gp_generic=new GridPane();
    
    private IconoGenerico ig_catalogocuentas=new IconoGenerico("#916F6F", "Catálogo de Cuentas", "javiconta.configuracion.contabilidad.FCatalogoCuentas","/images/catalogocuentas.png");
    public Parent createContent()
    {
        new JMetro(JMetro.Style.DARK).applyTheme(gp_generic);
        return gp_generic;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

