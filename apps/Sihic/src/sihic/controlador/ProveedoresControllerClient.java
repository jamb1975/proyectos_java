/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import sihic.SihicApp;
import java.util.List;
import modelo.Proveedores;
import service.ProveedoresController;

/**
 *
 * @author karol
 */
public class ProveedoresControllerClient {

    private ProveedoresController proveedoresController;

    public ProveedoresControllerClient() {
        proveedoresController = new ProveedoresController();
    }

    public void create() {
        try {
            proveedoresController.create(SihicApp.s_proveedores);

        } catch (Exception e) {

        }
    }

    public void update() {
        try {
            proveedoresController.update(SihicApp.s_proveedores);

        } catch (Exception e) {

        }
    }

    public void delete() {
        try {
            proveedoresController.delete(SihicApp.s_proveedores);

        } catch (Exception e) {

        }
    }

    public List<Proveedores> getRecords(String search) {
        return proveedoresController.getRecords(search);

    }

    public Proveedores findbyident(String no_ident) {

        return proveedoresController.findbyident(no_ident);
    }

}
