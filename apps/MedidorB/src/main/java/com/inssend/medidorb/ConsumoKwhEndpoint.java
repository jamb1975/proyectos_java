    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inssend.medidorb;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import javax.ws.rs.core.Response;
import modelo.ConsumoKwH;
import modelo.Contador;
import modelo.ConsumoKwhRequest;

/**
 *
 * @author adminlinux
 */
@ApplicationScoped
@Produces(APPLICATION_JSON)
@Consumes({"application/xml", "application/json"})
@Path("consumokwh")
public class ConsumoKwhEndpoint {
    @Inject
    private MedidorbRepositorio medidorbrepositorio;
    @GET
    public Response findAll(@QueryParam("start") @DefaultValue("0") int start,
                             @QueryParam("limit") @DefaultValue("0") int limit)
    {
        List<ConsumoKwH> li_testimonios=(start>=0 && limit >0)? medidorbrepositorio.findRange(start, limit)
                                                               :medidorbrepositorio.findAll();
        return Response.ok(li_testimonios).build();
    }
   @POST
    public Response create(ConsumoKwhRequest entityrequest) {
        System.out.println("creandoregistro consumo");
        ConsumoKwH entity=new ConsumoKwH();
        entity.setFecha(new Date());
        entity.setIirms(new BigDecimal(entityrequest.getIrms()));
        entity.setVirms(new BigDecimal(entityrequest.getVrms()));
        entity.setKwh(new BigDecimal(entityrequest.getKwh()));
        return Response.ok(medidorbrepositorio.createconsumokwh(entity)).build();
    }
}
