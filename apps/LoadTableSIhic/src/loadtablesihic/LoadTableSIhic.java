/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadtablesihic;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author adminlinux
 */
public class LoadTableSIhic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
        EntityManager entityManager = Persistence.createEntityManagerFactory("LoadTableSIhicPU").createEntityManager();
    }
    
}
