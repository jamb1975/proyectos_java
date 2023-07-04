/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.Producto;
import modelo.Proveedores;

/**
 *
 * @author karolyani
 */

public class ProductoFacadeREST extends AbstractFacade<Producto> {
   
  private static final String FIND_PRODUCTOS_QUERY = 
            "select p from Producto p "+
            "where  (lower(p.nombre) like :search "+ 
            "or lower(p.codigo) like :search   )order by p.nombre";
    public ProductoFacadeREST() {
        super(Producto.class);
    }

   
    public void create(Producto entity) {
        Producto p;
     EntityManagerGeneric.et.begin();    
       if(entity.getId()!=null)
       {
          p=(Producto)EntityManagerGeneric.em.createQuery("select p from Producto p where p.codigo=:pcod")
                                   .setParameter("pcod", entity.getCodigo())
                                   .setMaxResults(1)
                                   .getSingleResult();
          entity.setId(p.getId());
        super.edit(p);
       }
       else
       {
        super.create(entity);
       }
       EntityManagerGeneric.et.commit();
    }

   
    public void edit(Long id, Producto entity) {
          EntityManagerGeneric.et.begin();
        super.edit(entity);
         EntityManagerGeneric.et.commit();
    }

   
    public void remove( Long id) {
          EntityManagerGeneric.et.begin();
        super.remove(super.find(id));
           EntityManagerGeneric.et.commit();
    }

   
    public Producto find(Long id) {
        return super.find(id);
    }

   
    public List<Producto> findAll() {
        return super.findAll();
    }

   
    public List<Producto> findRange(Integer from, Integer to) {
        return super.findRange(new int[]{from, to});
    }

    public List<Producto> findRange(Integer from,Integer to,String search) {
      String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
          List<Producto> productos;
         
          productos=EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_QUERY)
                 .setParameter("search", searchpattern)
                 .setMaxResults(21)
                 .getResultList();
         return productos;
    }
    
    public String countREST() {
        return String.valueOf(super.count());
    }
public Producto exist_entity(Producto entity)
      {
          try
          {
              Producto p=new Producto();
              p=entity;
              entity=(Producto)EntityManagerGeneric.em.createQuery("select p from Producto p where p.codigo=:cod")
                                                        .setParameter("cod",entity.getCodigo())
                                                        .getSingleResult();
               EntityManagerGeneric.et.begin();
               entity.setNombre(p.getNombre());
               entity.setPrecio(p.getPrecio());
               entity.setCosto(p.getCosto());
               entity.setUde(p.getUde());
               entity.setCodigosrelacionados(p.getCodigosrelacionados());
               entity=EntityManagerGeneric.em.merge(entity);
               EntityManagerGeneric.et.commit();
          }
          catch(Exception e)
          {
              EntityManagerGeneric.et.begin();
              super.create(entity);
              EntityManagerGeneric.et.commit();
          }
          return entity;
      }
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
}
