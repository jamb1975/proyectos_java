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
import model.Factura;
import model.Terceros;

/**
 *
 * @author karol
 */
@Stateless
@Path("model.factura")
public class FacturaFacadeREST extends AbstractFacade<Factura> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;

    public FacturaFacadeREST() {
        super(Factura.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Factura entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Factura entity) {
        Terceros t=new Terceros();
        Factura f=new Factura();
        try{
           
             f=em.find(Factura.class, id);
             f.setCustomer(entity.getCustomer());
             f.setEmpleado(entity.getEmpleado());
             f.setCredito(entity.isCredito());
             if(f.getCustomer()!=null)
              {
                 f.getCustomer().setTipo_terc(1);
                 t=em.merge(f.getCustomer());
                 f.setCustomer(t);
              }                       
                  
                  f=em.merge(f);
                 
              
            
        }catch(Exception e)
        {
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Factura find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Factura> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Factura> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
