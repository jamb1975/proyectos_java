/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import modelo.Novedades;
import service.NovedadesController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class NovedadesControllerClient {
    private NovedadesController novedadesController;
    public NovedadesControllerClient()
    {
       novedadesController=new  NovedadesController(Novedades.class);
    }
    public void create()
    {
        novedadesController.create(SihicApp.novedades);
    }
    public void update()
    {
        novedadesController.update(SihicApp.novedades);
    }
    public void getRecords()
    {
        SihicApp.li_novedades=novedadesController.getRecords(null);
    }        
    
}
