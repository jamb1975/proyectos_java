/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import modelo.HclCups;
import modelo.Producto;
import service.HclCupsController;

/**
 *
 * @author adminlinux
 */
public class HclCupsControllerClient {

    private HclCupsController hclCupsController;

    public HclCupsControllerClient() {
        hclCupsController = new HclCupsController();
    }

    public Producto edit(Producto producto) {
        return hclCupsController.edit(producto);
    }

    public HclCups edit(HclCups hclCups) {
        return hclCupsController.edit(hclCups);
    }
}
