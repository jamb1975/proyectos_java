/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.util.List;
import model.Inf_Ventas_Totales;
import service.Inf_Ventas_Totales_FacadeREST;


/**
 * Jersey REST client generated for REST
 * resource:Ventas_Totales_Diarias_ViewFacadeREST
 * [model.ventas_totales_diarias_view]<br>
 * USAGE:
 * <pre>
 *        VantasDiariasJerseyClient client = new VantasDiariasJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author SIMboxDEV8
 */
public class VentasTotalesJerseyClient {
    private Inf_Ventas_Totales_FacadeREST inf_VentasTotalesFacadeREST;
    
    

    public VentasTotalesJerseyClient() {
        inf_VentasTotalesFacadeREST=new Inf_Ventas_Totales_FacadeREST();
    }

    public String countREST() {
       
        return inf_VentasTotalesFacadeREST.countREST();
    }

    

    public List<Inf_Ventas_Totales> findRange_XML(String date_from, String date_to){
        
        return inf_VentasTotalesFacadeREST.findAll(date_from, date_to);
    }

  

   

   
    public void close() {
        inf_VentasTotalesFacadeREST=null;
    }
    
}
