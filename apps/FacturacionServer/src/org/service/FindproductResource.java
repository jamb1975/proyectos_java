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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import model.Producto;

/**
 * REST Web Service
 *
 * @author karolyani
 */
@Path("findproduct")
@Stateless
public class FindproductResource extends AbstractFacade<Producto>{
 @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;
   private static final String FIND_PRODUCTOS_QUERY = 
            "select p from Producto p "+
            "where  (lower(p.nombre) like :search "+ 
            "or lower(p.codigo) like :search   )order by p.nombre";
private static final String FIND_PRODUCTOS_BY_MATERIA_PRIMA = 
            "select p from Producto p "+
            "where  (lower(p.nombre) like :search "+ 
            "or lower(p.codigo) like :search   ) and p.esmateriaprima=true order by p.nombre";
    /**
     * Creates a new instance of FindproductResource
     */
    public FindproductResource() {
        super(Producto.class);
    }

    /**
     * Retrieves representation of an instance of service.FindproductResource
     * @return an instance of java.lang.String
     */
   
    @GET
    @Path("{search}/{materiaprima}")
    @Produces({"application/xml", "application/json"})
      public List<Producto> find(@PathParam("search")String search,@PathParam("materiaprima")boolean materiaprima) 
      {        
          String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
          List<Producto> productos;
         if(!materiaprima)
         {
          productos=em.createQuery(FIND_PRODUCTOS_QUERY)
                 .setParameter("search", searchpattern)
                 .setMaxResults(14)
                 .getResultList();
         }
         else
         {
             productos=em.createQuery(FIND_PRODUCTOS_BY_MATERIA_PRIMA)
                 .setParameter("search", searchpattern)
                 .setMaxResults(14)
                 .getResultList();
         }
      
        return productos;
    }
      
      @GET
      @Produces({"application/xml", "application/json"})
      @Path("{materiaprima}")
      public List<Producto> findAll(@PathParam("materiaprima")boolean materiaprima) {     
          String searchpattern ="%";
           List<Producto> productos;
        if(!materiaprima)
         {
          productos=em.createQuery(FIND_PRODUCTOS_QUERY)
                 .setParameter("search", searchpattern)
                 .setMaxResults(14)
                 .getResultList();
         }
         else
         {
             productos=em.createQuery(FIND_PRODUCTOS_BY_MATERIA_PRIMA)
                 .setParameter("search", searchpattern)
                 .setMaxResults(14)
                 .getResultList();
         }
      
      
                  return productos;
    }
      
   /* @POST
    @Produces({"application/xml", "application/json"})
    public  List<Producto> createmateriaprima(Producto materiaprima) {
        materiaprima=em.merge(materiaprima);
        List<Producto> list_producto=em.createQuery("select p from ProductoMateriaPrima p where p.productopadre=:pp")
                                        .setParameter("pp", materiaprima.);
                                        .getResultList();
        return list_producto;
    }*/
   
   
    @Override
    protected EntityManager getEntityManager() {
      return em;
    }
}
