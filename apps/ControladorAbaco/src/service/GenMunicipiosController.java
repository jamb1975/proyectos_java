/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.GenMunicipios;

/**
 *
 * @author adminlinux
 */
public class GenMunicipiosController {
   
public void save(GenMunicipios genMunicipios)
{
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(genMunicipios);
        EntityManagerGeneric.et.commit();
}
    public GenMunicipios update(GenMunicipios genMunicipios)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        genMunicipios=EntityManagerGeneric.em.merge(genMunicipios);
        EntityManagerGeneric.et.commit();
        return genMunicipios;
    }
      public void delete(GenMunicipios genMunicipios)
    {
    if(EntityManagerGeneric.et.isActive())
    {
            EntityManagerGeneric.et.rollback();
    }
        genMunicipios=EntityManagerGeneric.em.find(GenMunicipios.class, genMunicipios.getId());
        System.out.println("id cat->"+genMunicipios.getId());
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.remove(genMunicipios);
        EntityManagerGeneric.et.commit();
    }
public List<GenMunicipios> getRecords(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery("select m from GenMunicipios m where lower(m.nombre) like :search order by m.nombre")
                                      .setParameter("search", searchpattern)
                                      .getResultList();
    }    
}
