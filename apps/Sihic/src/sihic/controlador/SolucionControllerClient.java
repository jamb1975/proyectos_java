/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.util.List;
import modelo.Solucion;
import service.SolucionController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class SolucionControllerClient {

    private SolucionController solucionController;

    public SolucionControllerClient() {
        solucionController = new SolucionController(Solucion.class);
    }

    public void create() {
        solucionController.create(SihicApp.solucion);
    }

    public Solucion update() {
        return solucionController.update(SihicApp.solucion);
    }

    public void delete() {
        solucionController.delete(SihicApp.solucion);
    }

    public Solucion getSolucion() {
        return solucionController.getSolucion();
    }

    public List<Solucion> getSolucionHijos(Solucion solucion) {
        return solucionController.getSolucionHijos(solucion);
    }
}
