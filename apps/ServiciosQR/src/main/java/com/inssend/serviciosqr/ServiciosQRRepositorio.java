/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inssend.serviciosqr;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import modelo.Recargas;


@ApplicationScoped
public class ServiciosQRRepositorio {
    @Inject
    private EntityManager em;
    
    public List<Recargas> findAll()
    {
          return em.createQuery("select r from Recargas r",Recargas.class)
                .getResultList();
    }
    
     public List<Recargas> findRange(int firstResult, int maxResults)
    {
        return em.createQuery("select r from Recargas r",Recargas.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }
     @Transactional
     public Recargas createrecargas(Recargas recargas)
     {
         
         em.persist(recargas);
         return recargas;
                 
     }
}
