/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.util.List;
import model.Solucion;
import service.SolucionController;
import abaco.AbacoApp;
import service.EntityManagerGeneric;

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
        solucionController.create(AbacoApp.solucion);
    }

    public Solucion update() {
        return solucionController.update(AbacoApp.solucion);
    }

    public void delete() {
        solucionController.delete(AbacoApp.solucion);
    }

    public Solucion getSolucion() {
        return solucionController.getSolucion();
    }

    public List<Solucion> getSolucionHijos(Solucion solucion) {
        return solucionController.getSolucionHijos(solucion);
    }
    public void findsolucioncodigo(int numeral)
    {
     AbacoApp.solucion=solucionController.findsolucioncodigo(numeral);
    }
}
