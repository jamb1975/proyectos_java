/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;

import modelo.GenUnidadesOrganizacion;
import servicios.GenUnidadesOrganizacionController;


/**
 *
 * @author adminlinux
 */
public class GenUnidadeOrganizacionControllerClient {

    private final GenUnidadesOrganizacionController genUnidadesOrganizacionController;

    public GenUnidadeOrganizacionControllerClient() {
        genUnidadesOrganizacionController = new GenUnidadesOrganizacionController();
    }

   
    public void saveOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion) {
        genUnidadesOrganizacionController.saveOrganizacion(genUnidadesOrganizacion);
    }

    public GenUnidadesOrganizacion updateOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion) {
        return genUnidadesOrganizacion = genUnidadesOrganizacionController.updateOrganizacion(genUnidadesOrganizacion);
    }

    public List<GenUnidadesOrganizacion> getOrganizacion(String search) {
        return genUnidadesOrganizacionController.getOrganizacion(search);
    }

}
