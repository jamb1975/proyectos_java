/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Kardex;
import model.Producto;

/**
 * REST Web Service
 *
 * @author karolyani
 */

public class FindproductResource extends AbstractFacade<Producto>{

   private static final String FIND_PRODUCTOS_QUERY = 
            "select k from Kardex k "+
            "where  (lower(k.producto.nombre) like :search "+ 
            "or lower(k.producto.codigo) like :search   )"
            + " and k.id=(select max(k2.id) from Kardex k2 where k2.producto=k.producto)  order by k.producto.nombre";
private static final String FIND_PRODUCTOS_BY_MATERIA_PRIMA = 
            "select k from Kardex k "+
            "where  (lower(k.producto.nombre) like :search "+ 
            "or lower(k.producto.codigo) like :search   )"
            + " and k.id=(select max(k2.id) from Kardex k2 where k2.producto=k.producto)"
           + " and k.producto.esmateriaprima=true order by k.producto.nombre";

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
   
   
      public List<Kardex> find(String search,boolean materiaprima) 
      {        
          String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
          List<Kardex> productos;
         if(!materiaprima)
         {
          productos=EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_QUERY)
                 .setParameter("search", searchpattern)
                 .setMaxResults(14)
                 .getResultList();
         }
         else
         {
             productos=EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_BY_MATERIA_PRIMA)
                 .setParameter("search", searchpattern)
                 .setMaxResults(14)
                 .getResultList();
         }
      
        return productos;
    }
      
     
      public List<Kardex> findAll(boolean materiaprima) {     
          String searchpattern ="%";
           List<Kardex> productos;
        if(!materiaprima)
         {
          productos=EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_QUERY)
                 .setParameter("search", searchpattern)
                 .setMaxResults(14)
                 .getResultList();
         }
         else
         {
             productos=EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_BY_MATERIA_PRIMA)
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
      return EntityManagerGeneric.em;
    }
}
