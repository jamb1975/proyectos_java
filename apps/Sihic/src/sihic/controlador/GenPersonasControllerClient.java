/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import java.util.List;
import modelo.GenPacientes;
import modelo.GenPersonas;
import modelo.GenProfesiones;

import modelo.Usuarios;
import service.GenPersonasController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class GenPersonasControllerClient {

    private GenPersonasController genPersonasController;

    public GenPersonasControllerClient() {
        genPersonasController = new GenPersonasController(GenPersonas.class);
    }

    public void save(GenPersonas gp, GenPacientes gpac) {
        try {
            genPersonasController.save(gp, gpac);
            SihicApp.checkproceso = true;
        } catch (Exception e) {
            SihicApp.checkproceso = false;
        }
    }

    public void saveusuario(GenPersonas gp, Usuarios usuarios) {
        genPersonasController.saveusuario(gp, usuarios);
        SihicApp.checkproceso = true;

    }

    public List<GenPersonas> getGenpersonas(String search) {
        return genPersonasController.getGenpersonas(search);

    }

    public List<GenPacientes> getGenpacientes(String search) {
        return genPersonasController.getPacientes(search);

    }

    public GenPersonas findbyident(String no_ident) {

        return genPersonasController.findbyident(no_ident);
    }

    public List<GenProfesiones> getProfesiones(String search) throws ParseException {
        return genPersonasController.getProfesiones(search);
    }

    public GenPacientes edit(GenPacientes genPacientes) {
        return genPersonasController.edit(genPacientes);
    }

}
