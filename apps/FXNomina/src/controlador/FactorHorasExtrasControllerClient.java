/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.FactorHorasExtras;
import servicios.FactorHorasExtrasController;
import fxnomina.FXNomina;

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
        factorHorasExtrasController.create(FXNomina.factorHorasExtras);
    }

    public void update() {
        FXNomina.factorHorasExtras = factorHorasExtrasController.update(FXNomina.factorHorasExtras);
    }

    public void getRecords() {
        FXNomina.li_facFactorHorasExtras = factorHorasExtrasController.getRecords(null);
    }
}
