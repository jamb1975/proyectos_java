/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cargartablasjaviconta;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author adminlinux
 */
public class CargarTablasJaviConta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
             EntityManager entityManager = Persistence.createEntityManagerFactory("CargarTablasIsoftContaPU").createEntityManager();
  
    }
    
}
