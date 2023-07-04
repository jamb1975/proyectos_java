/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modelo.FacturaItem;
import modelo.NotasEstudio;
import modelo.Resultados;

/**
 *
 * @author adminlinux
 */
public class NotasEstudioController {
  
public NotasEstudio getNotasEstudio(FacturaItem fi)
{ try
{
  return (NotasEstudio)EntityManagerGeneric.em.createQuery("select ne from NotasEstudio ne where ne.facturaItem=:fi")
                                              .setParameter("fi", fi)
                                              .setMaxResults(0)
                                              .getSingleResult();
}catch(Exception e)
{
    return null;
}
}
public void create(NotasEstudio notasEstudio)
{
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(notasEstudio);
    EntityManagerGeneric.et.commit();
}
public NotasEstudio update(NotasEstudio notasEstudio)
{
    EntityManagerGeneric.et.begin();
    notasEstudio=EntityManagerGeneric.em.merge(notasEstudio);
    EntityManagerGeneric.et.commit();
    
    return notasEstudio;
}
public Resultados getResultados(FacturaItem fi)
{ try
{
  return (Resultados)EntityManagerGeneric.em.createQuery("select r from Resultados r where r.facturaItem=:fi")
                                              .setParameter("fi", fi)
                                              .setMaxResults(0)
                                              .getSingleResult();
}catch(Exception e)
{
    return null;
}
}
public void create(Resultados resultados)
{
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.getRollbackOnly();
    }
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(resultados);
    EntityManagerGeneric.et.commit();
}
public Resultados update(Resultados resultados)
{
    EntityManagerGeneric.et.begin();
    resultados=EntityManagerGeneric.em.merge(resultados);
    EntityManagerGeneric.et.commit();
    
    return resultados;
}
public void deleteNotasEstudio(NotasEstudio notasEstudio)
{
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.getRollbackOnly();
    }
    EntityManagerGeneric.et.begin();
    notasEstudio= EntityManagerGeneric.em.find(NotasEstudio.class, notasEstudio.getId());
    EntityManagerGeneric.em.remove(notasEstudio);
     EntityManagerGeneric.et.commit();
}
        
}
