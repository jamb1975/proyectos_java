/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacionloader;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author karol
 */
public class FacturacionLoader {

    /**
     * @param args the command line arguments
     */
    @PersistenceUnit
    public static void main(String[] args) {
               EntityManager entityManager = Persistence.createEntityManagerFactory("FacturacionLoaderPU").createEntityManager();
 
    }
    
}
