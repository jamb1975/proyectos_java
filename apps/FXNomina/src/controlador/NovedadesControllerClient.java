/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Novedades;
import servicios.NovedadesController;
import fxnomina.FXNomina;

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
        novedadesController.create(FXNomina.novedades);
    }
    public void update()
    {
        novedadesController.update(FXNomina.novedades);
    }
    public void getRecords()
    {
        FXNomina.li_novedades=novedadesController.getRecords(null);
    }        
    
}
