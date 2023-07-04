/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.Organizacion;

/**
 *
 * @author adminlinux
 */
public class OrganizacionController {
    private static final String FIND_ORGANIZACION_QUERY = 
            "select o from Organizacion o "+
            "where  (lower(o.nombre) like :search "+ 
            " or o.codigo like :search )order by o.nombre";
    public Organizacion load_GenUnidadesOrganizacion()
 {
     try{
     return (Organizacion)EntityManagerGeneric.em.createQuery("select o from Organizacion o where o.id=(select max(o2.id) from Organizacion o2)")
       
                                                     .getSingleResult();
     
     }catch(Exception e)
     {
         return null;
     }
   
     
 }
public void create(Organizacion organizacion)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(organizacion);
        EntityManagerGeneric.et.commit();
    }
    public Organizacion update(Organizacion organizacion)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        organizacion=EntityManagerGeneric.em.merge(organizacion);
        EntityManagerGeneric.et.commit();
        return organizacion;
    }
public List<Organizacion> getRecords(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery(FIND_ORGANIZACION_QUERY)
                                      .setParameter("search", searchpattern)
                                      .getResultList();
    }    
}
