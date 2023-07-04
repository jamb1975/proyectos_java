/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import modelo.FactorHorasExtras;
import service.FactorHorasExtrasController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class FactorHorasExtrasControllerClient {

    private FactorHorasExtrasController factorHorasExtrasController;

    public FactorHorasExtrasControllerClient() {
        factorHorasExtrasController = new FactorHorasExtrasController(FactorHorasExtras.class);
    }

    public void create() {
        factorHorasExtrasController.create(SihicApp.factorHorasExtras);
    }

    public void update() {
        SihicApp.factorHorasExtras = factorHorasExtrasController.update(SihicApp.factorHorasExtras);
    }

    public void getRecords() {
        SihicApp.li_facFactorHorasExtras = factorHorasExtrasController.getRecords(null);
    }
}
