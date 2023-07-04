/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import java.util.List;
import modelo.AgnCitas;
import modelo.AgnEstadosCita;
import modelo.AgnMedicos;
import modelo.GenHorasCita;
import service.AgnCitasController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class AgnCitasControllerClient {

    private AgnCitasController agnCitasController;

    public AgnCitasControllerClient() {
        agnCitasController = new AgnCitasController(AgnCitas.class);
    }

    public List<AgnCitas> getAgnCitas(String date, AgnMedicos agnMedicos, int estadoscita) throws ParseException {

        return agnCitasController.getCitasFecha(date, agnMedicos, estadoscita);
    }

   

    public List<AgnCitas> setHorasCitaMedicos(List<AgnMedicos> l_med, List<GenHorasCita> l_hc, String datecurrent, AgnMedicos agnMedicos, int estado) throws ParseException {
        return agnCitasController.setHorasCitaMedicos(l_med, l_hc, datecurrent, estado, agnMedicos);
    }

    public List<AgnCitas> getCitasAgendadas(String search, int est, String fecha) throws ParseException {

        return agnCitasController.getCitasAgendadas(search, est, fecha);
    }
    public List<AgnCitas> agncitasagendada(String search, String fechacurrent) throws ParseException {

        return agnCitasController.agncitasagendadas(search,fechacurrent);
    }
    public List<AgnCitas> getCitasProceso(String search, String fecha) throws ParseException {

        return agnCitasController.getCitasProceso(search, fecha);
    }

    public List<GenHorasCita> getHorasCita() throws ParseException {

        return agnCitasController.getHorasCita();
    }

    public void saveHorasCita(GenHorasCita genHorasCita) {
        agnCitasController.saveHorasCita(genHorasCita);
    }

    public GenHorasCita updateHorasCita(GenHorasCita genHorasCita) {
        return agnCitasController.updateHorasCita(genHorasCita);
    }
    public void create()
    {
        agnCitasController.create(SihicApp.agnCitasTemp);
    }
      public void update()
    {
        SihicApp.agnCitasTemp=agnCitasController.update(SihicApp.agnCitasTemp);
    }
}
