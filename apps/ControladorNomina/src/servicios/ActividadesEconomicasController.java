/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.ActividadesEconomicas;

/**
 *
 * @author adminlinux
 */
public class ActividadesEconomicasController extends  AbstractFacade<ActividadesEconomicas>{

    public ActividadesEconomicasController(Class<ActividadesEconomicas> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
      return EntityManagerGeneric.em;
    }
    @Override
    public void create(ActividadesEconomicas actividadesEconomicas)
    {
       if(EntityManagerGeneric.et.isActive())
       {
          EntityManagerGeneric.et.getRollbackOnly();
       }
       EntityManagerGeneric.et.begin();
       EntityManagerGeneric.em.persist(actividadesEconomicas);
       EntityManagerGeneric.et.commit();
       
    }
    public List<ActividadesEconomicas>  getRecords(){
       return EntityManagerGeneric.em.createQuery("select ae from ActividadesEconomicas ae order by ae.codigo_ciiu_0079 ")
                                                                  .getResultList();
    }
    
}
