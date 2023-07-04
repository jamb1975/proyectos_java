/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import model.Terceros;

/**
 *
 * @author karolyani
 */
@Stateless
@Path("model.terceros")
public class TercerosFacadeREST extends AbstractFacade<Terceros> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;
 private static final String FIND_TERCEROS_QUERY = 
            "select t from Terceros t "+
            "where  (lower(t.nombre) like :search "+ 
            " or t.no_ident like :search )order by t.nombre";
    public TercerosFacadeREST() {
        super(Terceros.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Terceros entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Terceros entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Terceros find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Terceros> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Terceros> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
@GET
    @Path("{from}/{to}/{search}")
    @Produces({"application/xml", "application/json"})
    public List<Terceros> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to, @PathParam("search") String search) {
      String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
          List<Terceros> terceros;
         
          terceros=em.createQuery(FIND_TERCEROS_QUERY)
                 .setParameter("search", searchpattern)
                 .setMaxResults(21)
                 .getResultList();
         return terceros;
    }
    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
