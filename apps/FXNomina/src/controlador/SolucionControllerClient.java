/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import modelo.Solucion;
import servicios.SolucionController;
import fxnomina.FXNomina;

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
        solucionController.create(FXNomina.solucion);
    }

    public Solucion update() {
        return solucionController.update(FXNomina.solucion);
    }

    public void delete() {
        solucionController.delete(FXNomina.solucion);
    }

    public Solucion getSolucion() {
        return solucionController.getSolucion();
    }

    public List<Solucion> getSolucionHijos(Solucion solucion) {
        return solucionController.getSolucionHijos(solucion);
    }
     public void findsolucioncodigo(int numeral)
    {
     FXNomina.solucion=solucionController.findsolucioncodigo(numeral);
    }
}
