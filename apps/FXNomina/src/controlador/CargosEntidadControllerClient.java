/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import modelo.CargosEntidad;
import servicios.CargosEntidadController;
import fxnomina.FXNomina;
import general.LoadChoiceBoxGeneral;

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
        cargosEntidadController.create(FXNomina.cargosEntidad);
    }

    public void update() {
        FXNomina.cargosEntidad = cargosEntidadController.update(FXNomina.cargosEntidad);
    }

    public List<CargosEntidad> getRecords() {
        FXNomina.li_CargosEntidads = FXNomina.li_CargosEntidads = cargosEntidadController.getRecords();
        LoadChoiceBoxGeneral.Load_CargosEntidad();
        return FXNomina.li_CargosEntidads;
    }
}
