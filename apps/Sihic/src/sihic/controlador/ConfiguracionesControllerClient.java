/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import modelo.Configuraciones;
import service.ConfiguracionesController;
import sihic.SihicApp;

/**
 *
 * @author karolyani
 */
public class ConfiguracionesControllerClient {

    private ConfiguracionesController configuracionesController;

    public ConfiguracionesControllerClient() {
        configuracionesController = new ConfiguracionesController(Configuraciones.class);
    }

    public void create() {
        try {
            configuracionesController.create(SihicApp.s_configuraciones);

        } catch (Exception e) {

        }
    }

    public void update() {
        try {
            configuracionesController.update(SihicApp.s_configuraciones);

        } catch (Exception e) {

        }
    }

    public void delete() {
        try {
            configuracionesController.delete(SihicApp.s_configuraciones);

        } catch (Exception e) {

        }
    }

    public Configuraciones getRecord() {
        return configuracionesController.getRecord();

    }
}
