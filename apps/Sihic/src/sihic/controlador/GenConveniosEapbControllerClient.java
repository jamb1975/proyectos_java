/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import java.util.List;
import modelo.GenConvenios;
import modelo.GenEapb;
import service.GenConveniosEapbController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class GenConveniosEapbControllerClient {

    private GenConveniosEapbController genConveniosEapbController;

    public GenConveniosEapbControllerClient() {
        genConveniosEapbController = new GenConveniosEapbController(GenConvenios.class);
    }

    public List<GenConvenios> convenioseps(String datefrom, String dateto, String search, int tipoconvenio) throws ParseException {
        return genConveniosEapbController.convenioseps(datefrom, dateto, search, tipoconvenio);
    }
    public void getRecords() throws ParseException
   {
        SihicApp.l_conveniosp=genConveniosEapbController.getRecords();
   }
    public void create(GenConvenios genConveniosEapb, GenEapb eapb) {
        genConveniosEapbController.create(genConveniosEapb, eapb);
    }

    public void createParticula(GenConvenios genConveniosEapb) {
        genConveniosEapbController.createParticular(genConveniosEapb);
    }

    public GenConvenios edit(GenConvenios genConveniosEapb) {
        return genConveniosEapbController.edit(genConveniosEapb);
    }

    public GenConvenios findConvenios(String search) {
        return genConveniosEapbController.findConvenio(search);
    }
}
