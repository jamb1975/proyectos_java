/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.util.List;
import model.Inf_AtencionAClientes;
import model.Inf_Existencias;
import service.Inf_ExistenciasFacadeREST;



/**
 * Jersey REST client generated for REST resource:Inf_ExistenciasFacadeREST
 * [model.inf_existencias]<br>
 * USAGE:
 * <pre>
 *        Inf_ExistenciasJerseyClient client = new Inf_ExistenciasJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author karolyani
 */
public class Inf_ExistenciasJerseyClient {
   
   private Inf_ExistenciasFacadeREST inf_ExistenciasFacadeREST;
    public Inf_ExistenciasJerseyClient() {
      inf_ExistenciasFacadeREST= new Inf_ExistenciasFacadeREST();
    }

    public String countREST()  {
        
        return inf_ExistenciasFacadeREST.countREST();
    }

   
   


    public List<Inf_Existencias> findAll_XML(String search) {
           return inf_ExistenciasFacadeREST.findAll(search);
    }

    

   

   
    public void close() {
       inf_ExistenciasFacadeREST=null;
    }
    
}
