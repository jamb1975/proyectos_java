/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.ActividadesEconomicas;
import modelo.RetencionFuente;

/**
 *
 * @author adminlinux
 */
public class RetencionFuenteController extends  AbstractFacade<RetencionFuente>{

    public RetencionFuenteController(Class<RetencionFuente> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
      return EntityManagerGeneric.em;
    }
    @Override
    public void create(RetencionFuente retencionFuente)
    {
       if(EntityManagerGeneric.et.isActive())
       {
          EntityManagerGeneric.et.getRollbackOnly();
       }
       EntityManagerGeneric.et.begin();
       EntityManagerGeneric.em.persist(retencionFuente);
       EntityManagerGeneric.et.commit();
     }
    public List<RetencionFuente>  getRecords()
    {
               return EntityManagerGeneric.em.createQuery("select rf from RetencionFuente rf ")
                                                      .getResultList();
    }
    
}
