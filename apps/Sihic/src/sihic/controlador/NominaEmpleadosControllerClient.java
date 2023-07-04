/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import service.NominaEmpleadosController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class NominaEmpleadosControllerClient {
    private NominaEmpleadosController nominaEmpleadosController;
    public NominaEmpleadosControllerClient()
    {
        nominaEmpleadosController=new NominaEmpleadosController();
    }
    
    public void update()
    {
        SihicApp.nominaEmpleados=nominaEmpleadosController.update(SihicApp.nominaEmpleados);
    }
    
}
