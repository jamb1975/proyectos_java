/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.service;

import org.service.*;
import org.service.AbstractFacade;
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
import model.Categoria;

/**
 *
 * @author karolyani
 */
@Stateless
@Path("model.categoria")
public class CategoriaFacadeREST extends AbstractFacade<Categoria> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;
    private static final String FIND_CATEGORIAS_QUERY = 
            "select c from Categoria c "+
            "where  (lower(c.m_NombreCat) like :search "+ 
            " )order by c.m_NombreCat";
    public CategoriaFacadeREST() {
        super(Categoria.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Categoria entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Categoria entity) {
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
    public Categoria find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Categoria> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Categoria> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
@GET
@Path("{from}/{to}/{search}")
    @Produces({"application/xml", "application/json"})
    public List<Categoria> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to, @PathParam("search") String search) {
      String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
          List<Categoria> productos;
         
          productos=em.createQuery(FIND_CATEGORIAS_QUERY)
                 .setParameter("search", searchpattern)
                 .setMaxResults(21)
                 .getResultList();
         return productos;
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
