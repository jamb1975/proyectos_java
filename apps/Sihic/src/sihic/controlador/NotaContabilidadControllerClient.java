/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import modelo.NotaContabilidad;
import service.NotaContabilidadController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class NotaContabilidadControllerClient {

    private NotaContabilidadController notaContabilidadController;

    public NotaContabilidadControllerClient() {
        notaContabilidadController = new NotaContabilidadController(NotaContabilidad.class);
    }

    public void create() {
        notaContabilidadController.create(SihicApp.notaContabilidad,SihicApp.consecutivosContabilidad);
    }

    public void update() {
        notaContabilidadController.update(SihicApp.notaContabilidad);
    }

    public void getrecorsd(String fromdate, String todate, String search) throws ParseException {
        SihicApp.li_notacontabilidad = notaContabilidadController.getRecords(fromdate, todate, search);
    }
public void getRecord() throws ParseException {
        SihicApp.notaContabilidad = notaContabilidadController.getRecord(SihicApp.nomina);
    }

public void getRecordComprobante() throws ParseException {
//        SihicApp.notaContabilidad = notaContabilidadController.getRecord(SihicApp.conComprobanteContabilidad.getId());
    }
}
