/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import service.ConsecutivosContabilidadController;
import sihic.SihicApp;

/**
 *
 * @author innsend
 */
public class ConsecutivosContabilidadControllerClient {

    private ConsecutivosContabilidadController consecutivosContabilidadController;

    public ConsecutivosContabilidadControllerClient() {
        consecutivosContabilidadController = new ConsecutivosContabilidadController();
    }

    public void getRecord() {
        SihicApp.consecutivosContabilidad = consecutivosContabilidadController.getRecord();
      
    }
   public void createconsecutivonominaempleado()
    {
     SihicApp.consecutivosContabilidad= consecutivosContabilidadController.createconsecutivonominaempleado(SihicApp.consecutivosContabilidad);
    }
   public void findlastconsecutivorips()
    {
     SihicApp.consecutivosContabilidad= consecutivosContabilidadController.findlastconsecutivorips(SihicApp.consecutivosContabilidad);
    }
}
