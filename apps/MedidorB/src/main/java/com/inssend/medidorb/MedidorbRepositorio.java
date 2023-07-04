/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inssend.medidorb;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import modelo.ConsumoKwH;
import modelo.Contador;


@ApplicationScoped
public class MedidorbRepositorio {
    @Inject
    private EntityManager em;
    
    public List<ConsumoKwH> findAll()
    {
          return em.createQuery("select mb from ConsumoKwH mb",ConsumoKwH.class)
                .getResultList();
    }
    
     public List<ConsumoKwH> findRange(int firstResult, int maxResults)
    {
        return em.createQuery("select mb from ConsumoKwH mb",ConsumoKwH.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }
     public List<Contador> consumotiemporeal()
    {
        return em.createQuery("select c from Contador c",Contador.class)
                
                .setMaxResults(1)
                 .getResultList();
    }
    @Transactional
     public ConsumoKwH createconsumokwh(ConsumoKwH consumoKwH)
     {
         
         em.persist(consumoKwH);
         return consumoKwH;
                 
     }
     @Transactional
     public Contador createcontador(Contador contador)
     {
        
         try
         {
       Contador c=(Contador)  em.createQuery("select c from Contador c")
                              .getSingleResult();
       contador.setId(c.getId());
       em.merge(contador);
         }catch(Exception e)
         {
           em.persist(contador);
         }
         return contador;
                 
     }
}
