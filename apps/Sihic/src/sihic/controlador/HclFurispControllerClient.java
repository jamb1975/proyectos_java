/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import modelo.Factura;
import modelo.HclFurisp;
import service.HclFurispController;

/**
 *
 * @author adminlinux
 */
public class HclFurispControllerClient {

    private HclFurispController hclFurispController;

    public HclFurispControllerClient() {
        hclFurispController = new HclFurispController(HclFurisp.class);
    }

    public HclFurisp getHclFurisp(Factura factura) {
        return hclFurispController.getFurisp(factura);
    }

    public void create(HclFurisp hclFurisp) {
        hclFurispController.create(hclFurisp);
    }

    public HclFurisp update(HclFurisp hclFurisp) {
        return hclFurispController.update(hclFurisp);
    }

}
