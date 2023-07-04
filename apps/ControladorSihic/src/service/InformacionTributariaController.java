/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.persistence.EntityManager;
import modelo.InformacionTributaria;

/**
 *
 * @author adminlinux
 */
public class InformacionTributariaController extends AbstractFacade<InformacionTributaria>{

    public InformacionTributariaController(Class<InformacionTributaria> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    public void save(InformacionTributaria informacionTributaria)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(informacionTributaria);
        EntityManagerGeneric.et.commit();
    }
    
    public void modificar(InformacionTributaria informacionTributaria)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.merge(informacionTributaria);
        EntityManagerGeneric.et.commit();
    }
            
    
}
