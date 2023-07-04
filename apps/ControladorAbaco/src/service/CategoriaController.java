/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.Categoria;
import model.Organizacion;

/**
 *
 * @author adminlinux
 */
public class CategoriaController {
   
public void save(Categoria categoria)
{
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(categoria);
        EntityManagerGeneric.et.commit();
}
    public Categoria update(Categoria categoria)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        categoria=EntityManagerGeneric.em.merge(categoria);
        EntityManagerGeneric.et.commit();
        return categoria;
    }
      public void delete(Categoria categoria)
    {
    if(EntityManagerGeneric.et.isActive())
    {
            EntityManagerGeneric.et.rollback();
    }
        categoria=EntityManagerGeneric.em.find(Categoria.class, categoria.getId());
        System.out.println("id cat->"+categoria.getId());
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.remove(categoria);
        EntityManagerGeneric.et.commit();
    }
public List<Categoria> getRecords(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery("select c from Categoria c where lower(c.m_NombreCat) like :search order by c.m_NombreCat")
                                      .setParameter("search", searchpattern)
                                      .getResultList();
    }    
}
