/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.generic;

import java.util.Vector;
import javafx.scene.control.ChoiceBox;
import model.UnidadesMedida;
import abaco.AbacoApp;



/**
 *
 * @author adminlinux
 */
public final class LoadChoiceBoxGeneral {
    private static ChoiceBox cb_unidadesmedida=new ChoiceBox();
    private static  Vector<UnidadesMedida>v_medunidadmedida=new Vector<UnidadesMedida>();
    
    public LoadChoiceBoxGeneral()
    {
    
  
    }
    // A change listener to track the change in selected item



  public void  Load_GenUnidadesMedida()
  {
        if(AbacoApp.l_unidadmedida.size()>0)
           {
            cb_unidadesmedida.getItems().clear();
            v_medunidadmedida.clear();
            for(UnidadesMedida um:AbacoApp.l_unidadmedida)
            {   
             v_medunidadmedida.add(um);
             cb_unidadesmedida.getItems().add(um.getSigla());          
            }        
         }
                
  }

    public static ChoiceBox getCb_unidadesmedida() {
        return cb_unidadesmedida;
    }

    public static void setCb_unidadesmedida(ChoiceBox cb_unidadesmedida) {
        LoadChoiceBoxGeneral.cb_unidadesmedida = cb_unidadesmedida;
    }

    public static Vector<UnidadesMedida> getV_medunidadmedida() {
        return v_medunidadmedida;
    }

    public static void setV_medunidadmedida(Vector<UnidadesMedida> v_medunidadmedida) {
        LoadChoiceBoxGeneral.v_medunidadmedida = v_medunidadmedida;
    }

   
 

   
}
