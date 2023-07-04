/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.persistence.EntityManager;
import modelo.Factura;
import modelo.HclFurisp;

/**
 *
 * @author adminlinux
 */
public class HclFurispController extends AbstractFacade<HclFurisp> {

    public HclFurispController(Class<HclFurisp> entityClass) {
        super(entityClass);
    }
    public HclFurisp getFurisp(Factura factura)
    {
        try
        {
        return(HclFurisp) EntityManagerGeneric.em.createQuery("select fu from HclFurisp fu where fu.factura=:f")
                                       .setParameter("f", factura)
                                       .setMaxResults(1)
                                       .getSingleResult();
        }catch(Exception e)
        {
            return null;
        }
    }
    @Override
    public void create(HclFurisp hclFurisp)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
         super.create(hclFurisp);
        EntityManagerGeneric.et.commit();
    }
    public HclFurisp update(HclFurisp hclFurisp)
    {
         if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        EntityManagerGeneric.et.begin();
         hclFurisp=super.edit(hclFurisp);
        EntityManagerGeneric.et.commit();
        return hclFurisp;
    }

    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
}
