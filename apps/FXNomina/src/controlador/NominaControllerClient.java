/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.text.ParseException;
import servicios.NominaController;
import fxnomina.FXNomina;

/**
 *
 * @author adminlinux
 */
public class NominaControllerClient {
    private NominaController nominaController;
    public NominaControllerClient()
    {
        nominaController=new NominaController();
    }
  public void create()
  {
      nominaController.create(FXNomina.nomina,FXNomina.consecutivosContabilidad);
  }
  public void update()
  {
      nominaController.update(FXNomina.nomina);
  }
  public void delete()
  {
      nominaController.delete(FXNomina.nomina);
  }
  public void getRecords(String datefrom, String dateto) throws ParseException
  {
      FXNomina.li_nomina=nominaController.getRecords(datefrom, dateto);
  }
}
