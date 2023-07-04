/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Date;
import modelo.Auditoria;

/**
 *
 * @author adminlinux
 */
public class AuditoriaController {
    
    public static void create(Auditoria auditoria)
    {
          
             if(EntityManagerGeneric.et.isActive())
             {
                 EntityManagerGeneric.et.rollback();
             }
             EntityManagerGeneric.et.begin();
             EntityManagerGeneric.em.persist(auditoria);
             EntityManagerGeneric.et.commit();
        
    }        
            
}
