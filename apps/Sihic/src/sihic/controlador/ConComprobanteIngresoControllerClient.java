/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import java.util.List;
import modelo.ComprobanteIngreso;
import modelo.LibroAuxiliar;
import modelo.Factura;
import modelo.FacturaItem;
import service.ComprobanteIngresoController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class ConComprobanteIngresoControllerClient {

    ComprobanteIngresoController conComprobanteIngresoController;
    private List<FacturaItem> facturaitem;

    public ConComprobanteIngresoControllerClient() {
        conComprobanteIngresoController = null;
        conComprobanteIngresoController = new ComprobanteIngresoController(ComprobanteIngreso.class);
    }

    public void create() {
        conComprobanteIngresoController.create(SihicApp.conComprobanteIngreso, SihicApp.consecutivosContabilidad, SihicApp.sucursales);
    }

    public ComprobanteIngreso edit() {
        return SihicApp.conComprobanteIngreso = conComprobanteIngresoController.edit(SihicApp.conComprobanteIngreso);
    }

    public ComprobanteIngreso editFacturaItem(ComprobanteIngreso comprobanteIngreso) {
        return comprobanteIngreso = conComprobanteIngresoController.editFacturaItem(comprobanteIngreso);
    }

    public List<FacturaItem> findfacturaitem(String search) {
        return conComprobanteIngresoController.findfacturaitem(search);
    }

    public void deletecomprobanteingresos(Long id) {
         conComprobanteIngresoController.deleteallcomprobantesingresodecomprobantecontabilidad(id);
    }

    public List<ComprobanteIngreso> getListComprobantesIngresos(String datefrom, String dateto, String search) throws ParseException {
        return conComprobanteIngresoController.getListConComprobanteIngreso(datefrom, dateto, search);

    }
public void getRecord() throws ParseException {
     }
}
