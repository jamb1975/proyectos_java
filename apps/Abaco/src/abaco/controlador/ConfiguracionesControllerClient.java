/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import model.Configuraciones;
import service.ConfiguracionesController;

/**
 *
 * @author adminlinux
 */
public class ConfiguracionesControllerClient {
    ConfiguracionesController  configuracionesController;
    public ConfiguracionesControllerClient()
    {
        configuracionesController=new ConfiguracionesController();
    }
    public void create(Configuraciones configuraciones)
    {
        configuracionesController.create(configuraciones);
    }
    public void update(Configuraciones configuraciones)
    {
        configuracionesController.update(configuraciones);
    }
    public Configuraciones getConfiguraciones()
    {
        return configuracionesController.getConfiguraciones();
    }
}
