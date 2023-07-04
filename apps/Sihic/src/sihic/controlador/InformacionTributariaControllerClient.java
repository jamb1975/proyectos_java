/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import modelo.InformacionTributaria;
import service.InformacionTributariaController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class InformacionTributariaControllerClient {

    private InformacionTributariaController informacionTributariaController;

    public InformacionTributariaControllerClient() {
        informacionTributariaController = new InformacionTributariaController(InformacionTributaria.class);
    }

    public void save() {
        informacionTributariaController.save(SihicApp.s_informacionTributaria);
    }

    public void modificar() {
        informacionTributariaController.modificar(SihicApp.s_informacionTributaria);
    }

}
