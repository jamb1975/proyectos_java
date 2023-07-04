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
import model.TempMesasFactura;

/**
 *
 * @author karol
 */
@Stateless
@Path("model.tempmesasfactura")
public class TempMesasFacturaFacadeREST extends AbstractFacade<TempMesasFactura> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;

    public TempMesasFacturaFacadeREST() {
        super(TempMesasFactura.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(TempMesasFactura entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, TempMesasFactura entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        TempMesasFactura tm=new TempMesasFactura();
        try
        {
        tm=(TempMesasFactura)em.createQuery("select tm from TempMesasFactura tm where tm.m_iNoMesa=:pnomesa" )
                          .setParameter("pnomesa",Integer.parseInt(String.valueOf(id)))
                           .getSingleResult();
        tm=em.find(TempMesasFactura.class,tm.getId());
        em.remove(tm);
        em.flush();
       
        }catch(Exception e)
        {
         
        }
        
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public TempMesasFactura find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<TempMesasFactura> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<TempMesasFactura> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
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
