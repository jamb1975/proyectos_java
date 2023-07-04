/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import modelo.GenEapb;
import servicios.GenEapbController;

/**
 *
 * @author adminlinux
 */
public class GenEapbControllerClient {

    GenEapbController genEapbController;

    public GenEapbControllerClient() {
        genEapbController = new GenEapbController();
    }

    public GenEapb findEps(String search) {
        return genEapbController.findEps(search);
    }

    public List<GenEapb> li_geneapb(String search) {
        return genEapbController.li_geneapb(search);
    }

    public void create(GenEapb genEapb) {
        genEapbController.create(genEapb);
    }

    public GenEapb edit(GenEapb genEapb) {
        return genEapbController.edit(genEapb);
    }

    public void delete(GenEapb genEapb) {
        genEapbController.delete(genEapb);
    }
}
