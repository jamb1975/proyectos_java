/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inssend.medidorb;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author adminlinux
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.inssend.medidorb.ApiResource.class);
        resources.add(com.inssend.medidorb.ConsumoKwhEndpoint.class);
        resources.add(com.inssend.medidorb.ContadorTiempoRealEndPoint.class);
        resources.add(com.inssend.medidorb.MedidorbREST.class);
        resources.add(filter.CORSFilter.class);
    }
    
}
