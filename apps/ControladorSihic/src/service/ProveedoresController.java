/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.Proveedores;


/**
 *
 * @author karolyani
 */

public class ProveedoresController extends AbstractFacade<Proveedores> {
   
 private static final String FIND_PROVEEDORES_QUERY = 
            "select p from Proveedores p "+
            "where  (lower(p.nombre) like :search "+ 
            " or p.no_ident like :search )order by p.nombre";
    public ProveedoresController() {
        super(Proveedores.class);
    }

    
 @Override
    public void create(Proveedores entity) {
        EntityManagerGeneric.et.begin();
        super.create(entity);
        EntityManagerGeneric.et.commit();
    }

    
    public void update( Long id, Proveedores entity) {
         EntityManagerGeneric.et.begin();
           super.update(entity);
        EntityManagerGeneric.et.commit();
    }

   
    public void delete( Proveedores proveedores) {
         EntityManagerGeneric.et.begin();
        super.remove(super.find(proveedores.getId()));
         EntityManagerGeneric.et.commit();
    }

   
     public Proveedores findbyident(String no_ident) 
    {
        Proveedores proveedores;
        try{
      proveedores=(Proveedores)EntityManagerGeneric.em.createQuery("select p from Proveedores p where p.no_ident=:ident")
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
