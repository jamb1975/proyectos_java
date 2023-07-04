/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import static isoftconta.servicios.EntityManagerGeneric.em;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import modelo.EntidadesStatic;
import modelo.Kardex;
import modelo.Producto;

/**
 *
 * @author karolyani
 */
public class ProductoController extends AbstractFacade<Producto>{
     
private static final String FIND_PRODUCTOS_QUERY = 
            "select p from Producto p "+
            "where  (lower(p.nombre) like :search "+ 
            "or lower(p.codigo) like :search   )order by p.nombre";
  private static final String FIND_PRODUCTOS_BY_CODIGO = 
            "select k from Kardex k "+
            "where  k.producto.codigo=:search  "
            + " and k.id=(select max(k2.id) from Kardex k2 where k2.producto=k.producto)  order by k.producto.nombre";
private static final String FIND_PRODUCTOS_BY_CODIGO2 = 
              "select p from Producto p "+
            " where  lower(p.codigo) like :search "+ 
            " order by p.nombre";
 private static final String FIND_PRODUCTOS_BY_CODIGO3 = 
              "select p from Producto p "+
            " where  lower(p.codigo)like:search "+ 
            " order by p.nombre"; 
  private static final String FIND_PRODUCTOS_BY_CODIGO4 = 
            "select k from Kardex k "+
            "where  lower(k.producto.codigo)like:search  "
            + " and k.id=(select max(k2.id) from Kardex k2 where k2.producto=k.producto)  order by k.producto.nombre";

  private static final String FIND_PRODUCTOS_BY_CODIGO5 = 
              "select p from Producto p "+
            " where  p.codigo =:search "+ 
            " order by p.nombre";
    public ProductoController(Class<Producto> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
             return EntityManagerGeneric.em;  
                                           
    }
      public static int servicio_crear()
   {   try
      {
            
             
       EntityManagerGeneric.verificarestadotransaccion();
       EntityManagerGeneric.et.begin();
       em.merge(EntidadesStatic.es_producto);
       EntityManagerGeneric.et.commit();
       
        return 1;
      }catch(Exception e)
      { 
        e.printStackTrace();
         return -1; 
      
      }
   }
   public static int servicio_actualizar()
   {   try
      {
      
       EntityManagerGeneric.verificarestadotransaccion();
       EntityManagerGeneric.et.begin();
       em.merge(EntidadesStatic.es_producto);
       EntityManagerGeneric.et.commit();
       
      
       return 1;
      }catch(Exception e)
      {
         return -1; 
      }
   }
 public static int servicio_eliminar()
   {   try
      {
          
        
        EntityManagerGeneric.verificarestadotransaccion();
       EntityManagerGeneric.et.begin();
       em.remove(EntidadesStatic.es_producto);
       EntityManagerGeneric.et.commit();
       
       
       return 1;
      }catch(Exception e)
      {
         
         return -1; 
      }
   }
    
   
   
   
     public Kardex findByCodigo(String search) {
         
        Kardex producto=null;
             
        
            
             try{
       producto =(Kardex)EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_BY_CODIGO)
                 .setParameter("search", search)
                 .getSingleResult();
         }catch(Exception e)
                {
                producto=null;
                }
       
        return producto;
    }
    public Producto findByCodigo2(String search) {
         
        Producto producto=null;
             String searchpattern = search.toLowerCase();
        
            
             try{
       producto =(Producto)EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_BY_CODIGO2)
                 .setParameter("search", searchpattern)
                 .getSingleResult();
         }catch(Exception e)
                {
                producto=null;
                }
       
        return producto;
    }
       public Producto findByCodigo3(String search) {
         
        Producto producto=null;
        String searchpattern ='%'+search.toLowerCase().replace('*', '%') + '%';
        
            
             try{
       producto =(Producto)EntityManagerGeneric.em.createQuery(FIND_PRODUCTOS_BY_CODIGO5)
                 .setParameter("search", search)
                 .getSingleResult();
         }catch(Exception e)
                {
                producto=null;
                }
       
        return producto;
    }
       public static List<Producto> getRecords(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery("select p from Producto p "+
                                                   "where  (lower(p.nombre) like :search "+ 
                                                 "or lower(p.codigobarras) like :search   ) and p.codigobarras is not null order by p.nombre")
                                                 .setParameter("search", searchpattern)
                                                 .getResultList();
    } 
           public static List<Producto> getRecordsServicios(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery("select p from Producto p "+
                                                   "where  (lower(p.nombre) like :search "+ 
                                                 "or lower(p.codigobarras) like :search   ) and p.codigobarras is not null and p.tipo='1' order by p.nombre")
                                                 .setParameter("search", searchpattern)
                                              .getResultList();
    } 
    public static List<Producto> getRecordsProductos(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery("select p from Producto p "+
                                                   "where  (lower(p.nombre) like :search "+ 
                                                 "or lower(p.codigobarras) like :search   ) and p.codigobarras is not null and p.tipo='0' order by p.nombre")
                                                 .setParameter("search", searchpattern)
                                              .getResultList();
    }        
      
           
}
