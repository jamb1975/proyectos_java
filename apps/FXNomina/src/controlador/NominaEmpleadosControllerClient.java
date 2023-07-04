/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import servicios.NominaEmpleadosController;
import fxnomina.FXNomina;

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
        FXNomina.nominaEmpleados=nominaEmpleadosController.update(FXNomina.nominaEmpleados);
    }
    
}
