/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import modelo.Empleados;
import modelo.GenPersonas;
import servicios.EmpleadoController;
import fxnomina.FXNomina;

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
        empleadoController.create(FXNomina.empleados);
    }

    public Empleados update() {
        return empleadoController.update(FXNomina.empleados);
    }

    public void getRecords(String search) {
        FXNomina.li_empleados = empleadoController.getRecords(search);
    }
}
