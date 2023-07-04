/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.List;
import modelo.ParametrosNomina;

/**
 *
 * @author adminlinux
 */
public class ParametrosNominaController {
    
    
    public void create(ParametrosNomina parametrosNomina)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(parametrosNomina);
        EntityManagerGeneric.et.commit();
    } 
    public void update(ParametrosNomina parametrosNomina)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.merge(parametrosNomina);
        EntityManagerGeneric.et.commit();
    }
    public void delete(ParametrosNomina parametrosNomina)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.remove(parametrosNomina);
        EntityManagerGeneric.et.commit();
    }
    public List<ParametrosNomina> getRecords(String search)
    {
        String searchpattern=search==null?"%"+search+"%":search;
        return EntityManagerGeneric.createQuery("select pn from ParametrosNomina pn")
                                    .getResultList();
    }
}
