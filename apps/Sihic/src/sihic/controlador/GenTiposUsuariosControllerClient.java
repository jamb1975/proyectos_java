/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.util.List;
import modelo.GenTiposUsuarios;
import service.GenTiposUsuariosController;

/**
 *
 * @author adminlinux
 */
public class GenTiposUsuariosControllerClient {

    private GenTiposUsuariosController genTiposUsuariosController;

    public GenTiposUsuariosControllerClient() {
        genTiposUsuariosController = new GenTiposUsuariosController();

    }

    public void create(GenTiposUsuarios genTiposUsuarios) {
        genTiposUsuariosController.create(genTiposUsuarios);
    }

    public GenTiposUsuarios edit(GenTiposUsuarios genTiposUsuarios) {
        return genTiposUsuarios = genTiposUsuariosController.edit(genTiposUsuarios);
    }

    public void delete(GenTiposUsuarios genTiposUsuarios) {
        genTiposUsuariosController.delete(genTiposUsuarios);
    }

    public List<GenTiposUsuarios> li_genTiposUsuarios() {
        return genTiposUsuariosController.li_gentiposusuarios();
    }
}
