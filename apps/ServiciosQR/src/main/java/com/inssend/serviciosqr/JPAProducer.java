/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inssend.serviciosqr;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.enterprise.inject.Produces;

/**
 *
 * @author adminlinux
 */
@ApplicationScoped
public class JPAProducer {
   @Produces
   @PersistenceContext
   private EntityManager em;
}
