/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import modelo.NotaCredito;
import service.NotaCreditoController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class NotaCreditoControllerClient {

    private NotaCreditoController notaCreditoController;

    public NotaCreditoControllerClient() {
        notaCreditoController = new NotaCreditoController(NotaCredito.class);
    }

    public void create() {
        notaCreditoController.create(SihicApp.notaCredito,SihicApp.consecutivosContabilidad);
    }

    public void update() {
        notaCreditoController.update(SihicApp.notaCredito);
    }

    public void getrecorsd(String fromdate, String todate, String search) throws ParseException {
        SihicApp.li_notacredito = notaCreditoController.getRecords(fromdate, todate, search);
    }
    public void getRecord() throws ParseException {
     //   SihicApp.notaCredito = notaCreditoController.getRecord(SihicApp.conComprobanteContabilidad.getId());
    }

}
