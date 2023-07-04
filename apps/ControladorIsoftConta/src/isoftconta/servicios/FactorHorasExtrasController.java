/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.FactorHorasExtras;

/**
 *
 * @author adminlinux
 */
public class FactorHorasExtrasController extends AbstractFacade<FactorHorasExtras>{

    public FactorHorasExtrasController(Class<FactorHorasExtras> entityClass) {
        super(entityClass);
    }
   public void create(FactorHorasExtras factorHorasExtras)
   {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
       EntityManagerGeneric.et.begin();
       EntityManagerGeneric.em.persist(factorHorasExtras);
       EntityManagerGeneric.et.commit();
   }
   public FactorHorasExtras update(FactorHorasExtras factorHorasExtras)
   {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
       EntityManagerGeneric.et.begin();
       factorHorasExtras=EntityManagerGeneric.em.merge(factorHorasExtras);
       EntityManagerGeneric.et.commit();
       return factorHorasExtras;
   }
   public List<FactorHorasExtras> getRecords(String search)
   {
      
       return getEntityManager().createQuery("select fhe from FactorHorasExtras fhe ")
                                 .getResultList();
   }
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
}
