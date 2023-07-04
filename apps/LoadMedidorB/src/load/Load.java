/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package load;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author adminlinux
 */
public class Load {
    public static void main(String[] args) {
              EntityManager entityManager = Persistence.createEntityManagerFactory("LoadBaseDatosMisionCristianaCumaralPU").createEntityManager();

    }
    
}
