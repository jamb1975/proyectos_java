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
import model.DatosEmpresa;

/**
 *
 * @author karol
 */
@Stateless
@Path("model.datosempresa")
public class DatosEmpresaFacadeREST extends AbstractFacade<DatosEmpresa> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;
private static final String FIND_DATOSEMPRESA_QUERY = 
            "select de from DatosEmpresa de "+
            "where  (lower(de.m_sNombre) like :search "+ 
            "or lower(de.nit) like :search   )order by de.m_sNombre";
    public DatosEmpresaFacadeREST() {
        super(DatosEmpresa.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(DatosEmpresa entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, DatosEmpresa entity) {
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
    public DatosEmpresa find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<DatosEmpresa> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<DatosEmpresa> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
@GET
    @Path("{from}/{to}/{search}")
    @Produces({"application/xml", "application/json"})
    public List<DatosEmpresa> findCriteria(@PathParam("from") Integer from, @PathParam("to") Integer to, @PathParam("search") String search) {
        String searchpattern = search==null ? "%" :  search.toLowerCase().replace('*', '%') + '%';
          
        return em.createQuery(FIND_DATOSEMPRESA_QUERY)
                 .setParameter("search", searchpattern)
                 .setMaxResults(to-from)
                 .getResultList();
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
