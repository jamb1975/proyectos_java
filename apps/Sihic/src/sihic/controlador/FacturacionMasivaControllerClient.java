/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import service.FacturacionMasivaController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class FacturacionMasivaControllerClient {
    FacturacionMasivaController facturacionMasivaController;
    public FacturacionMasivaControllerClient()
    {
        facturacionMasivaController=new FacturacionMasivaController();
    }
    public void create()
    {
        facturacionMasivaController.create(SihicApp.agnCitasTemp);
    }
    public void update()
    {
        facturacionMasivaController.update(SihicApp.agnCitasTemp);
    }
    
}
