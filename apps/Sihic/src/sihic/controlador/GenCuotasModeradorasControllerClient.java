/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import modelo.GenEapb;
import service.GenCuotasModeradorasController;

/**
 *
 * @author adminlinux
 */
public class GenCuotasModeradorasControllerClient {

    GenCuotasModeradorasController genCuotasModeradorasController;

    public GenCuotasModeradorasControllerClient() {
        genCuotasModeradorasController = new GenCuotasModeradorasController();
    }

    public GenEapb save(GenEapb eapb) {
        return genCuotasModeradorasController.save(eapb);
    }

    public GenEapb addCuotasModeradoras(String eapb) {
        return genCuotasModeradorasController.addCuotasModeradoras(eapb);
    }
}
