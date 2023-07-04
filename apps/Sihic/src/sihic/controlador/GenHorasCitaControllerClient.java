/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import modelo.GenHorasCita;
import service.GenHorasCitaController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class GenHorasCitaControllerClient {

    private GenHorasCitaController genHorasCitaController;
public GenHorasCitaControllerClient()
{
      genHorasCitaController = new GenHorasCitaController(GenHorasCita.class);
}
    public void getGenHorasCita() {
      
        SihicApp.getGenhorascita().addAll(genHorasCitaController.getGenHorasCita());
    }
    public  void getRecords(String search,String fechacita,Long id) throws ParseException
    {
        SihicApp.li_horasdisponibles=genHorasCitaController.getRecords(search, fechacita, id);
    }
}
