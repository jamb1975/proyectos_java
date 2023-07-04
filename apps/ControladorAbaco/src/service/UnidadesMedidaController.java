/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.UnidadesMedida;

/**
 *
 * @author adminlinux
 */
public class UnidadesMedidaController {
   
public void create(UnidadesMedida unidadesMedida)
{
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(unidadesMedida);
        EntityManagerGeneric.et.commit();
}
    public UnidadesMedida update(UnidadesMedida unidadesMedida)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        unidadesMedida=EntityManagerGeneric.em.merge(unidadesMedida);
        EntityManagerGeneric.et.commit();
        return unidadesMedida;
    }
      public void delete(UnidadesMedida unidadesMedida)
    {
    if(EntityManagerGeneric.et.isActive())
    {
            EntityManagerGeneric.et.rollback();
    }
        unidadesMedida=EntityManagerGeneric.em.find(UnidadesMedida.class, unidadesMedida.getId());
        System.out.println("id cat->"+unidadesMedida.getId());
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.remove(unidadesMedida);
        EntityManagerGeneric.et.commit();
    }
public List<UnidadesMedida> getRecords(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery("select um from UnidadesMedida um where  lower(um.descripcion) like :search order by um.descripcion")
                                      .setParameter("search", searchpattern)
                                      .getResultList();
    }    
}
