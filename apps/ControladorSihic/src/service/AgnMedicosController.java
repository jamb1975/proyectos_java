/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.AgnMedicos;

/**
 *
 * @author adminlinux
 */
public class AgnMedicosController extends AbstractFacade<AgnMedicos>{

    public AgnMedicosController(Class<AgnMedicos> entityClass) {
        super(entityClass);
    }

    
    public List<AgnMedicos> getAgnMedicos()
    {
        List<AgnMedicos> list_agnmedicos;
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<AgnMedicos> cq = cb.createQuery(AgnMedicos.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<AgnMedicos> _agnmedicos = m.entity(AgnMedicos.class);
        Root<AgnMedicos> pet = cq.from(_agnmedicos);
TypedQuery<AgnMedicos> q=null;
list_agnmedicos=null;
q=EntityManagerGeneric.em.createQuery(cq);
         
list_agnmedicos=q.getResultList();

        
        return list_agnmedicos;


    }
    @Override
    protected EntityManager getEntityManager() 
    
    {
        return EntityManagerGeneric.em;
    }
    
}
