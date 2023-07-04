/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import java.util.List;
import modelo.ComprobanteEgreso;
import modelo.LibroAuxiliar;
import modelo.FacturaItem;
import service.ComprobanteEgresoController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class ConComprobanteEgresoControllerClient {

    ComprobanteEgresoController conComprobanteEgresoController;
    private List<FacturaItem> facturaitem;

    public ConComprobanteEgresoControllerClient() {
        conComprobanteEgresoController = null;
        conComprobanteEgresoController = new ComprobanteEgresoController(ComprobanteEgreso.class);
    }

    public void create(ComprobanteEgreso comprobanteEgreso) {
        conComprobanteEgresoController.create(comprobanteEgreso);
    }

    public ComprobanteEgreso edit(ComprobanteEgreso comprobanteEgreso) {
        return comprobanteEgreso = conComprobanteEgresoController.edit(comprobanteEgreso);
    }

    public List<FacturaItem> findfacturaitem(String search) {
        return facturaitem = conComprobanteEgresoController.findfacturaitem(search);
    }

    public List<LibroAuxiliar> findconComprobanteEgresos(Long id) {
        return conComprobanteEgresoController.findconComprobanteEgresos(id);
    }

    public List<ComprobanteEgreso> getListConComprobanteEgreso(String datefrom, String dateto, String search) throws ParseException {
        return conComprobanteEgresoController.getListConComprobanteEgreso(datefrom, dateto, search);
    }
     public void getRecord() throws ParseException {
     //   SihicApp.conComprobanteEgreso= conComprobanteEgresoController.getRecord(SihicApp.conComprobanteContabilidad.getId());
    }
}
