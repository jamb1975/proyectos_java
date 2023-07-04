/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.text.ParseException;
import java.util.List;
import modelo.GenPersonas;
import modelo.Usuarios;
import servicios.GenPersonasController;
import fxnomina.FXNomina;

/**
 *
 * @author adminlinux
 */
public class GenPersonasControllerClient {

    private GenPersonasController genPersonasController;

    public GenPersonasControllerClient() {
        genPersonasController = new GenPersonasController(GenPersonas.class);
    }

    

    public void saveusuario(GenPersonas gp, Usuarios usuarios) {
        genPersonasController.saveusuario(gp, usuarios);
        FXNomina.checkproceso = true;

    }

    public List<GenPersonas> getGenpersonas(String search) {
        return genPersonasController.getGenpersonas(search);

    }

  

    public GenPersonas findbyident(String no_ident) {

        return genPersonasController.findbyident(no_ident);
    }

  

}
