/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import modelo.ConsecutivosContabilidad;
import modelo.NotaDebito;
import service.NotaDebitoController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class NotaDebitoControllerClient {

    private NotaDebitoController notaDebitoController;

    public NotaDebitoControllerClient() {
        notaDebitoController = new NotaDebitoController(NotaDebito.class);
    }

    public void create() {
        notaDebitoController.create(SihicApp.notaDebito,SihicApp.consecutivosContabilidad);
    }

    public void update() {
        notaDebitoController.update(SihicApp.notaDebito);
    }

    public void getrecorsd(String fromdate, String todate, String search) throws ParseException {
        SihicApp.li_notadebito = notaDebitoController.getRecords(fromdate, todate, search);
    }
 public void getRecord() throws ParseException {
        //SihicApp.notaDebito = notaDebitoController.getRecord(SihicApp.conComprobanteContabilidad.getId());
    }
}
