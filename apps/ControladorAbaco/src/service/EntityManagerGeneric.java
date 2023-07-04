/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author karolyani
 */
public class EntityManagerGeneric {
        @PersistenceContext(unitName = "ControladorAbacoPU")
        public static  EntityManager em=Persistence.createEntityManagerFactory("ControladorAbacoPU").createEntityManager();
        public static  EntityTransaction et=em.getTransaction();
}
