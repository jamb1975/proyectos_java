/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import servicios.ParametrosNominaController;
import fxnomina.FXNomina;

/**
 *
 * @author adminlinux
 */
public class ParametrosNominaControllerClient {
    
    private ParametrosNominaController parametrosNominaController;
    public ParametrosNominaControllerClient()
    {
        parametrosNominaController=new ParametrosNominaController();
    }
    public void create()
    {
        parametrosNominaController.create(FXNomina.parametrosNomina);
    }  
    public void update()
    {
        parametrosNominaController.update(FXNomina.parametrosNomina);
    } 
    public void  getRecords()
    {
        FXNomina.li_parametrosNomina=parametrosNominaController.getRecords(null);
    } 
    
}
