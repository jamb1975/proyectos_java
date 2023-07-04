/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.util.List;
import static javafx.scene.input.KeyCode.T;
import model.Inf_AtencionAClientes;
import service.Inf_AtencionAClientesFacadeREST;


/**
 * Jersey REST client generated for REST
 * resource:Inf_AtencionAClientesFacadeREST [model.inf_atencionaclientes]<br>
 * USAGE:
 * <pre>
 *        Inf_AtencionAClientesJerseyClient client = new Inf_AtencionAClientesJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author karolyani
 */
public class Inf_AtencionAClientesJerseyClient {
   Inf_AtencionAClientesFacadeREST inf_AtencionAClientesFacadeREST;
  
    public Inf_AtencionAClientesJerseyClient() {
       inf_AtencionAClientesFacadeREST=new Inf_AtencionAClientesFacadeREST();
    }

    public String countREST()  {
        
        return inf_AtencionAClientesFacadeREST.countREST();
    }

   
   


    public List<Inf_AtencionAClientes> findRange_XML(String datefrom, String dateto) {
           return inf_AtencionAClientesFacadeREST.findRange(datefrom, dateto);
    }

    

   

   
    public void close() {
       inf_AtencionAClientesFacadeREST=null;
    }
    
}
