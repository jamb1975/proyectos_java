/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inssend.medidorb;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import modelo.ConsumoKwH;

/**
 *
 * @author adminlinux
 */
@Path("medidorb")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@ApplicationScoped
public class MedidorbREST {
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
   
    
}
