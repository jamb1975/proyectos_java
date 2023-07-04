/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.Novedades;

/**
 *
 * @author adminlinux
 */
public class NovedadesController extends AbstractFacade<Novedades>{

    public NovedadesController(Class<Novedades> entityClass) {
        super(entityClass);
    }

    
    @Override
   public void create(Novedades novedades)
   {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
       EntityManagerGeneric.et.begin();
       EntityManagerGeneric.em.persist(novedades);
       EntityManagerGeneric.et.commit();
   }
    @Override
   public Novedades update(Novedades novedades)
   {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
       EntityManagerGeneric.et.begin();
       novedades=EntityManagerGeneric.em.merge(novedades);
       EntityManagerGeneric.et.commit();
       return novedades;
   }
   public List<Novedades> getRecords(String search)
   {
       String searchpattern=search==null?"%":"%"+search+"%";
       return getEntityManager().createQuery("select n from Novedades n where n.nominaEmpleados.empleados.genPersonas.documento like :search order by n.nominaEmpleados.nomina.periodoinicio ")
                                .setMaxResults(100)
                                .getResultList();
   }
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
    
}
