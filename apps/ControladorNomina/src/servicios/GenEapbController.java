/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.List;
import modelo.GenEapb;

/**
 *
 * @author adminlinux
 */
public class GenEapbController {
   
    public GenEapb findEps(String search)
    {
        try
        {
            return (GenEapb)EntityManagerGeneric.em.createQuery("select e FROM GenEapb e where e.nombre like :search")
                                       .setParameter("search", search)
                                        .getSingleResult();
        }
        catch(Exception e)
        {
         return null;   
        }
    }
    public List<GenEapb> li_geneapb(String search)
    {
        String searchpattern=search==null?"%":search+"%";
       return EntityManagerGeneric.em.createQuery("select e FROM GenEapb e where (lower(e.nit)like :search or lower(e.nombre) like :search) order by e.nombre")
                                                .setParameter("search", searchpattern.toLowerCase())
                                                .getResultList();
    }
    public void create(GenEapb genEapb)
    {
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(genEapb);
        EntityManagerGeneric.et.commit();
    }
    public GenEapb edit(GenEapb genEapb)
    {
        EntityManagerGeneric.et.begin();
        genEapb=EntityManagerGeneric.em.merge(genEapb);
        EntityManagerGeneric.et.commit();
        return genEapb;
    }
    public void delete(GenEapb genEapb)
    {
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.remove(genEapb);
        EntityManagerGeneric.et.commit();
    }
}
