/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.Configuraciones;

/**
 *
 * @author adminlinux
 */
public class ConfiguracionesController {
   
    
public void create(Configuraciones configuraciones)
{
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(configuraciones);
    EntityManagerGeneric.et.commit();
}
public void update(Configuraciones configuraciones)
{
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.merge(configuraciones);
    EntityManagerGeneric.et.commit();
}
public Configuraciones getConfiguraciones()
{try
{
    return (Configuraciones)EntityManagerGeneric.em.createQuery("select c from Configuraciones c")
                                   .setMaxResults(1)
                                   .getSingleResult();
}catch(Exception e)
{
  return   new Configuraciones();
   
}
}
}
