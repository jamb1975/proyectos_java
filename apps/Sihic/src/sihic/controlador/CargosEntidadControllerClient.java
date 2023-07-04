/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.util.List;
import modelo.CargosEntidad;
import service.CargosEntidadController;
import sihic.SihicApp;
import sihic.general.LoadChoiceBoxGeneral;

/**
 *
 * @author adminlinux
 */
public class CargosEntidadControllerClient {

    private CargosEntidadController cargosEntidadController;

    public CargosEntidadControllerClient() {
        cargosEntidadController = new CargosEntidadController(CargosEntidad.class);
    }

    public void create() {
        cargosEntidadController.create(SihicApp.cargosEntidad);
    }

    public void update() {
        SihicApp.cargosEntidad = cargosEntidadController.update(SihicApp.cargosEntidad);
    }

    public List<CargosEntidad> getRecords() {
        SihicApp.li_CargosEntidads = SihicApp.li_CargosEntidads = cargosEntidadController.getRecords();
        LoadChoiceBoxGeneral.Load_CargosEntidad();
        return SihicApp.li_CargosEntidads;
    }
}
