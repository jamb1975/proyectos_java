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
public class ResultadosController {
    
    public void  create(Resultados resultados)
    {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
       EntityManagerGeneric.et.begin();
       EntityManagerGeneric.em.persist(resultados);
       EntityManagerGeneric.et.commit();
    }
    public Resultados  update(Resultados resultados)
    {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
       EntityManagerGeneric.et.begin();
        resultados=EntityManagerGeneric.em.merge(resultados);
       EntityManagerGeneric.et.commit();
       return resultados;
    }
    public Resultados getResultados(FacturaItem fi)
{ 
    try
{
  return (Resultados)EntityManagerGeneric.em.createQuery("select r from Resultados r where r.facturaItem= :fi")
                                              .setParameter("fi", fi)
                                              .setMaxResults(0)
                                              .getSingleResult();
}catch(Exception e)
{
    return null;
}
}
}
