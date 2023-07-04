/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import service.ParametrosNominaController;
import sihic.SihicApp;

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
        parametrosNominaController.create(SihicApp.parametrosNomina);
    }  
    public void update()
    {
        parametrosNominaController.update(SihicApp.parametrosNomina);
    } 
    public void  getRecords()
    {
        SihicApp.li_parametrosNomina=parametrosNominaController.getRecords(null);
    } 
    
}
