/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inssend.medidorb;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import modelo.ConsumoKwH;
import modelo.ConsumoKwhRequest;
import modelo.Contador;


/**
 *
 * @author adminlinux
 */
@ApplicationScoped
@Produces(APPLICATION_JSON)
@Consumes({"application/xml", "application/json"})
@Path("contadortiemporeal")
public class ContadorTiempoRealEndPoint {
    @Inject
    private MedidorbRepositorio medidorbrepositorio;
    
    @GET
    public Response contadortiemporeal()
    {
        List<Contador> li_testimonios=medidorbrepositorio.consumotiemporeal();
        return Response.ok(li_testimonios).build();
    }
      @POST
       
   public Response create(ConsumoKwhRequest entityrequest) {
        System.out.println("creandoregistro consumo");
        Contador entity=new Contador();
        entity.setFecha(new Date());
        entity.setIrms(new BigDecimal(entityrequest.getIrms()));
        entity.setVrms(new BigDecimal(entityrequest.getVrms()));
        entity.setKwh(new BigDecimal(entityrequest.getKwh()));
        return Response.ok(medidorbrepositorio.createcontador(entity)).build();
    }
}
