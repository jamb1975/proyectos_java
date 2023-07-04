/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rest.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author karol
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
        resources.add(org.service.CategoriaFacadeREST.class);
        resources.add(org.service.CheckNoMesaResource.class);
        resources.add(org.service.CheckNoMesasResource.class);
        resources.add(org.service.DatosEmpresaFacadeREST.class);
        resources.add(org.service.FacturaFacadeREST.class);
        resources.add(org.service.FacturaItemFacadeREST.class);
        resources.add(org.service.FindproductResource.class);
        resources.add(org.service.Inf_AtencionAClientesFacadeREST.class);
        resources.add(org.service.Inf_ExistenciasFacadeREST.class);
        resources.add(org.service.Inf_VentasPorProductoFacadeREST.class);
        resources.add(org.service.Inf_Ventas_Totales_FacadeREST.class);
        resources.add(org.service.ItemResource.class);
        resources.add(org.service.ItemsResource.class);
        resources.add(org.service.KardexFacadeREST.class);
        resources.add(org.service.Mesas_Atendidas_ViewFacadeREST.class);
        resources.add(org.service.ProductoFacadeREST.class);
        resources.add(org.service.ProductoMateriaPrimaFacadeREST.class);
        resources.add(org.service.TempMesasFacturaFacadeREST.class);
        resources.add(org.service.TercerosFacadeREST.class);
        resources.add(org.service.Ventas_Totales_Mes_ViewFacadeREST.class);
        resources.add(org.service.Credito_Clientes_ViewFacadeREST.class);
    }
    
}
