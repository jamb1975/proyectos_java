/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import modelo.AgnEspecialidades;
import service.AgnEspecialidadesController;

/**
 *
 * @author adminlinux
 */
public class AgnEspecialidadesControllerClient {

    private AgnEspecialidadesController agnEspecialidadesController;

    public AgnEspecialidadesControllerClient() {
        agnEspecialidadesController = new AgnEspecialidadesController();
    }

    public void create(AgnEspecialidades agnEspecialidades) {
        agnEspecialidadesController.create(agnEspecialidades);
    }

    public void update(AgnEspecialidades agnEspecialidades) {
        agnEspecialidadesController.update(agnEspecialidades);
    }

    public void delete(AgnEspecialidades agnEspecialidades) {
        agnEspecialidadesController.delete(agnEspecialidades);
    }
}
