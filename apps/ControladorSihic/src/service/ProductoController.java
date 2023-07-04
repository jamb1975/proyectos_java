/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.GenCategoriasProductos;
import modelo.Kardex;
import modelo.Producto;

/**
 *
 * @author karolyani
 */
public class ProductoController extends AbstractFacade<Producto>{
private static final String FIND_PRODUCTOS_ALL = 
            "select p from Producto p "+
            "where  (lower(p.nombre) like :search "+ 
            "or lower(p.codequivalente) like :search   ) and p.genCategoriasProductos is not null order by p.nombre";
private static final String FIND_PRODUCTOS_SERVICIOS = 
            "select p from Producto p "+
            "where p.genCategoriasProductos.codigo=1 and  (lower(p.nombre) like :search "+ 
            "or lower(p.codigo) like :search)  order by p.nombre";
private static final String FIND_PRODUCTOS_MEDICAMENTOS = 
            "select p from Producto p "+
            "where p.genCategoriasProductos.codigo=2 and  (lower(p.nombre) like :search "+ 
            "or lower(p.codigo) like :search)  order by p.nombre";
private static final String FIND_PRODUCTOS_INSUMOS = 
            "select p from Producto p "+
            "where p.genCategoriasProductos.codigo=3 and  (lower(p.nombre) like :search "+ 
            "or lower(p.codigo) like :search)  order by p.nombre";
  private static final String FIND_PRODUCTOS_CODIGO_KARDEX_LAST = 
            "select k from Kardex k "+
            "where  lower(k.producto.codigo) like :search  "
            + " and k.id=(select max(k2.id) from Kardex k2 where k2.producto=k.producto)  order by k.producto.nombre";
 
   private static final String FIND_SERVICIOS_KARDEX_LAST = 
            "select k from Kardex k "+
            "where  (lower(k.producto.codigo)like :search or lower(k.producto.nombre)like :search )  "
            + " and k.producto.genCategoriasProductos.codigo=1 and k.id=(select max(k2.id) from Kardex k2 where k2.producto=k.producto)  order by k.producto.nombre";
 
   private static final String FIND_PRODUCTOS_BY_CODIGO_ALL = 
              "select p from Producto p "+
            " where  lower(p.codigo) =:search "+ 
            " order by p.nombre";
   
    private static final String FIND_PRODUCTOS_KARDEX_LAST = 
            "select k from Kardex k "+
            "where  (lower(k.producto.codigo)like :search or lower(k.producto.nombre)like :search )  "
            + "  and k.id=(select max(k2.id) from Kardex k2 where k2.producto=k.producto)  order by k.producto.nombre";
    
    private static final String FIND_PRODUCTOS_BY_CATEGORIA = 
            "select p from Producto p "+
            "where p.genCategoriasProductos=:pcat and  (lower(p.nombre) like :search "+ 
            "or lower(p.codigo) like :search)  order by p.nombre";
    public ProductoController(Class<Producto> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
             return EntityManagerGeneric.em;  
                                           
    }
     public List<Producto> findproductosall(String search) {
        String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
      
        List<Producto> productos=EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_ALL)
                 .setParameter("search", searchpattern)
                 .setMaxResults(20)
                 .getResultList();
       
                  return productos;
    }
     public List<Producto> findserviciosall(String search) {
        String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
      
        List<Producto> productos=EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_SERVICIOS)
                 .setParameter("search", searchpattern)
                 .setMaxResults(20)
                 .getResultList();
       
                  return productos;
    }
      public List<Producto> findmedicamentosall(String search) {
        String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
      
        List<Producto> productos=EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_MEDICAMENTOS)
                 .setParameter("search", searchpattern)
                 .getResultList();
       
                  return productos;
    }
      public List<Producto> findinsumosall(String search) {
        String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
      
        List<Producto> productos=EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_INSUMOS)
                 .setParameter("search", searchpattern)
                 .getResultList();
       
                  return productos;
    }
     public Kardex findByCodigoLastKardex(String search) {
         
        Kardex producto=null;
             String searchpattern = '%'+search.toLowerCase().replace('*', '%') + '%';
        
            
             try{
       producto =(Kardex)EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_CODIGO_KARDEX_LAST)
                                             .setParameter("search", search.trim())
                                              .setMaxResults(1)
                 .getSingleResult();
         }catch(Exception e)
                {
              
                producto=null;
                }
       
        return producto;
    }
    public Producto findByCodigoExactAllProductos(String search) {
         
        Producto producto=null;
        String searchpattern = search.toLowerCase();
       try{
       producto =(Producto)EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_BY_CODIGO_ALL)
                 .setParameter("search", searchpattern)
                 .setMaxResults(1)
                 .getSingleResult();
         }
       catch(Exception e)
       {
        producto=null;
       }
       
        return producto;
    }
       public Producto findByCodigoAllProducto(String search) {
         
        Producto producto=null;
        String searchpattern ='%'+search.toLowerCase().replace('*', '%') + '%';
        
            
             try{
       producto =(Producto)EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_BY_CODIGO_ALL)
                 .setParameter("search", search)
                 .getSingleResult();
         }catch(Exception e)
                {
                producto=null;
                }
       
        return producto;
    }
   public List<Producto> li_findserviciosProductos(String search)
           
   {
       String searchPattern=search==null?"%":search+"%";
       return EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_SERVICIOS)
                                     .setParameter("search", searchPattern)
                                     .setMaxResults(20)
                                     .getResultList();
   }
    public List<Kardex> li_findservicioskardex(String search)
           
   {
       String searchPattern=search==null?"%":search+"%";
       
       return EntityManagerGeneric.em.createQuery(FIND_SERVICIOS_KARDEX_LAST)
                                     .setParameter("search", searchPattern)
                                     .setMaxResults(20)
                                     .getResultList();
   }
   public List<Kardex> li_findproductoskardex(String search)
           
   {
       String searchPattern=search==null?"%":search+"%";
       return EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_KARDEX_LAST)
                                     .setParameter("search", searchPattern)
                                     .setMaxResults(20)
                                     .getResultList();
   } 
   public void create(Producto entity) {
        Producto p;
     EntityManagerGeneric.et.begin();    
         try
         {
          p=(Producto)EntityManagerGeneric.em.createQuery("select p from Producto p where p.codigo=:pcod")
                                   .setParameter("pcod", entity.getCodigo())
                                   .setMaxResults(1)
                                   .getSingleResult();
          
         }catch(Exception e)
         {
     
        super.create(entity);
       }
        Kardex kardex;
       try
       {
       kardex=(Kardex)EntityManagerGeneric.em.createQuery("select k from Kardex k where k.producto.codigo=:pcod")
                                   .setParameter("pcod", entity.getCodigo())
                                   .setMaxResults(1)
                                   .getSingleResult();
       } catch(Exception e)
               {
                 kardex=new Kardex();
                 kardex.setProducto(entity);
                 EntityManagerGeneric.em.persist(kardex);
               }
       EntityManagerGeneric.et.commit();
    }

   
    
  

   
@Override
    public Producto update(Producto entity) {
          EntityManagerGeneric.et.begin();
        entity=super.update(entity);
         Kardex kardex;
        try
       {
       kardex=(Kardex)EntityManagerGeneric.em.createQuery("select k from Kardex k where k.producto.codigo=:pcod")
                                   .setParameter("pcod", entity.getCodigo())
                                   .setMaxResults(1)
                                   .getSingleResult();
       } catch(Exception e)
               {
                 kardex=new Kardex();
                 kardex.setProducto(entity);
                 EntityManagerGeneric.em.persist(kardex);
               }
         EntityManagerGeneric.et.commit();
         return entity;
    }

   
    public void delete(Producto entity) {
          EntityManagerGeneric.et.begin();
        super.remove(super.find(entity.getId()));
           EntityManagerGeneric.et.commit();
    }

   
        public List<Producto> getRecords(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery("select p from Producto p "+
                                                   "where  (lower(p.nombre) like :search "+ 
                                                 "or lower(p.codigo) like :search   ) and p.genCategoriasProductos is not null order by p.nombre")
                                                 .setParameter("search", searchpattern)
                                              .getResultList();
    }
        public List<Producto> getRecordsbycategoria(String search, GenCategoriasProductos genCategoriasProductos)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_BY_CATEGORIA)
                                                 .setParameter("pcat", genCategoriasProductos)
                                                 .setParameter("search", searchpattern)
                                                 .getResultList();
    }    
}
