/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import modelo.ReciboCaja;
import service.ReciboCajaController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class ReciboCajaControllerClient {

    private ReciboCajaController reciboCajaController;

    public ReciboCajaControllerClient() {
        reciboCajaController = new ReciboCajaController(ReciboCaja.class);
    }

    public void create() {
        reciboCajaController.create(SihicApp.reciboCaja);
    }

    public void update() {
        reciboCajaController.update(SihicApp.reciboCaja);
    }

    public void getRecords(String datefrom, String dateto, String search) throws ParseException {
        SihicApp.li_recibocaja = reciboCajaController.getRecords(datefrom, dateto, search);
    }
}
