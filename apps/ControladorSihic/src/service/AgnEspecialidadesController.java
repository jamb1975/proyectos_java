/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modelo.AgnEspecialidades;

/**
 *
 * @author adminlinux
 */
public class AgnEspecialidadesController {
    public void delete(AgnEspecialidades agnEspecialidades)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        EntityManagerGeneric.et.begin();
        agnEspecialidades=EntityManagerGeneric.em.find(AgnEspecialidades.class, agnEspecialidades.getId());
        EntityManagerGeneric.em.remove(agnEspecialidades);
        EntityManagerGeneric.et.commit();
    }
    public void create(AgnEspecialidades agnEspecialidades)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(agnEspecialidades);
        EntityManagerGeneric.et.commit();
    }
     public void update(AgnEspecialidades agnEspecialidades)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.merge(agnEspecialidades);
        EntityManagerGeneric.et.commit();
    }
    
}
