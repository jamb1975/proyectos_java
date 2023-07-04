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
import model.ProductoMateriaPrima;

/**
 *
 * @author karolyani
 */
@Stateless
@Path("model.productomateriaprima")
public class ProductoMateriaPrimaFacadeREST extends AbstractFacade<ProductoMateriaPrima> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;

    public ProductoMateriaPrimaFacadeREST() {
        super(ProductoMateriaPrima.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ProductoMateriaPrima entity ) {
       if(verificar_no_repetir_materia_prima(entity) && ! entity.getProducto().isEsmateriaprima())
       {    
        em.persist(entity);
       }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, ProductoMateriaPrima entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

   /* @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ProductoMateriaPrima find(@PathParam("id") Long id) {
        return super.find(id);
    }*/

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ProductoMateriaPrima> findAll() {
        return super.findAll();
    }
    @GET
    @Path("{id_prod}")
    @Produces({"application/xml", "application/json"})
    public List<ProductoMateriaPrima> findMateriasPrimas(@PathParam("id_prod") Long id_prod) 
    {
        List<ProductoMateriaPrima> list_materiasprimas=em.createQuery("select pmp from ProductoMateriaPrima pmp where pmp.producto.id=:id_prod")
                                                         .setParameter("id_prod", id_prod)
                                                         .getResultList();
        return list_materiasprimas;
    }
    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ProductoMateriaPrima> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
 private boolean verificar_no_repetir_materia_prima(ProductoMateriaPrima entity)
 {
     try
     {
     entity=(ProductoMateriaPrima)em.createQuery("select pmp from ProductoMateriaPrima pmp where pmp.producto=:p and pmp.materiaprima=:mp")
             .setParameter("p", entity.getProducto())
             .setParameter("mp", entity.getMateriaprima())
             .getSingleResult();
     return false;
 }catch(Exception e)
 {
    return true; 
 }
     
}
}