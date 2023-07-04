/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.EntidadesStatic;
import modelo.Proveedores;


/**
 *
 * @author karolyani
 */

public class ProveedoresController extends AbstractFacade<Proveedores> {
   
 private static final String FIND_PROVEEDORES_QUERY = 
            "select t from Terceros t "+
            "where  (lower(t.primerapellido) like :search "+ 
            " or t.no_ident like :search )order by t.primerapellido";
    public ProveedoresController() {
        super(Proveedores.class);
    }

    
    public static void servicio_crear() {
         if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }
   
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(EntidadesStatic.es_terceros);
    EntityManagerGeneric.et.commit();
    }

   public static void servicio_actualizar() {
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }
   
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.merge(EntidadesStatic.es_terceros);
    EntityManagerGeneric.et.commit();
    } 
  

   
  public static void servicio_remover() {
         if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }
   
         EntityManagerGeneric.et.begin();
         EntityManagerGeneric.em.find(Proveedores.class, EntidadesStatic.es_terceros.getId());
         EntityManagerGeneric.em.remove(EntidadesStatic.es_terceros);
         EntityManagerGeneric.et.commit();
    }
   
     public Proveedores findbyident(String no_ident) 
    {
        Proveedores proveedores;
        try{
      proveedores=(Proveedores)EntityManagerGeneric.em.createQuery("select t from Terceros t where t.no_ident=:ident")
                                     .setParameter("ident", no_ident)
                                     .getSingleResult();
        }catch(Exception e)
        {
            proveedores=null;
        }
      return  proveedores;
    }
      public List<Proveedores> getRecords(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery(FIND_PROVEEDORES_QUERY)
                                      .setParameter("search", searchpattern)
                                      .getResultList();
    } 

    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
}
