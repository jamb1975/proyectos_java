/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import service.NominaController;
import sihic.SihicApp;

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
      nominaController.create(SihicApp.nomina,SihicApp.consecutivosContabilidad,SihicApp.sucursales);
  }
  public void update()
  {
      nominaController.update(SihicApp.nomina);
  }
  public void delete()
  {
      nominaController.delete(SihicApp.nomina);
  }
  public void getRecords(String datefrom, String dateto) throws ParseException
  {
      SihicApp.li_nomina=nominaController.getRecords(datefrom, dateto);
  }
}
