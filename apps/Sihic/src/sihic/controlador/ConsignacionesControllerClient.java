/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import modelo.Consignaciones;
import modelo.FacturaProveedores;
import modelo.NotaCredito;
import service.ConsignacionesController;
import service.NotaCreditoController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class ConsignacionesControllerClient {

    private ConsignacionesController consignacionesController;

    public ConsignacionesControllerClient() {
        consignacionesController = new ConsignacionesController(Consignaciones.class);
    }

    public void create() {
        consignacionesController.create(SihicApp.consignaciones,SihicApp.consecutivosContabilidad);
    }

    public void update() {
        consignacionesController.update(SihicApp.consignaciones);
    }

    public void getrecorsd(String fromdate, String todate, String search) throws ParseException {
        SihicApp.li_consignaciones = consignacionesController.getRecords(fromdate, todate, search);
    }
    
     public void getRecord() throws ParseException {
        }

}
