/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.util.List;
import modelo.Empleados;
import modelo.GenPersonas;
import service.EmpleadoController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class EmpleadosControllerClient {

    private EmpleadoController empleadoController;

    public EmpleadosControllerClient() {
        empleadoController = new EmpleadoController(Empleados.class);
    }

    public void create() {
        empleadoController.create(SihicApp.empleados);
    }

    public Empleados update() {
        return empleadoController.update(SihicApp.empleados);
    }

    public void getRecords(String search) {
        SihicApp.li_empleados = empleadoController.getRecords(search);
    }
}
