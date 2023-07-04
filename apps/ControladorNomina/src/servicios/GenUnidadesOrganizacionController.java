/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.GenUnidadesOrganizacion;


/**
 *
 * @author adminlinux
 */
public class GenUnidadesOrganizacionController  {

     private static final String FIND_ORGANIZACION_QUERY = 
            "select o from GenUnidadesOrganizacion o "+
            "where  (lower(o.nombre) like :search "+ 
            " or o.codigo like :search )order by o.nombre";
   
    public void saveOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(genUnidadesOrganizacion);
        EntityManagerGeneric.et.commit();
    }
    public GenUnidadesOrganizacion updateOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        genUnidadesOrganizacion=EntityManagerGeneric.em.merge(genUnidadesOrganizacion);
        EntityManagerGeneric.et.commit();
        return genUnidadesOrganizacion;
    }
    public List<GenUnidadesOrganizacion> getOrganizacion(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery(FIND_ORGANIZACION_QUERY)
                                      .setParameter("search", searchpattern)
                                      .getResultList();
    }
  
    
}
