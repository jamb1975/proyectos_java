/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import servicios.ConsecutivosContabilidadController;
import fxnomina.FXNomina;

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
        FXNomina.consecutivosContabilidad = consecutivosContabilidadController.getRecord();
      
    }
   public void createconsecutivonominaempleado()
    {
     FXNomina.consecutivosContabilidad= consecutivosContabilidadController.createconsecutivonominaempleado(FXNomina.consecutivosContabilidad);
    }
   public void findlastconsecutivorips()
    {
     FXNomina.consecutivosContabilidad= consecutivosContabilidadController.findlastconsecutivorips(FXNomina.consecutivosContabilidad);
    }
}
