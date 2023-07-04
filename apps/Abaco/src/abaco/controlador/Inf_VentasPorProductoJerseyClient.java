/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.util.List;
import model.Inf_VentasPorProducto;
import service.Inf_VentasPorProductoFacadeREST;



/**
 * Jersey REST client generated for REST
 * resource:Inf_VentasPorProductoFacadeREST [model.inf_ventasporproducto]<br>
 * USAGE:
 * <pre>
 *        Inf_VentasPorProductoJerseyClient client = new Inf_VentasPorProductoJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author karolyani
 */
public class Inf_VentasPorProductoJerseyClient {
   Inf_VentasPorProductoFacadeREST inf_VentasPorProductoFacadeREST;
    

    public Inf_VentasPorProductoJerseyClient() {
        inf_VentasPorProductoFacadeREST= new  Inf_VentasPorProductoFacadeREST();
    }

    public String countREST()  {
        
        return inf_VentasPorProductoFacadeREST.countREST();
    }

    
    public List<Inf_VentasPorProducto> findRange_XML(String datefrom, String dateto)  {
       
        return inf_VentasPorProductoFacadeREST.findRange(datefrom, dateto);
    }

    
    public void close() {
        inf_VentasPorProductoFacadeREST=null;
    }
    
}
