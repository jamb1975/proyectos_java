/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modelo.AgnCitas;

/**
 *
 * @author adminlinux
 */
public class FacturacionMasivaController {
    
    
    public void create(AgnCitas agnCitas)
    {
       
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
     
      EntityManagerGeneric.et.begin();
      EntityManagerGeneric.em.persist(agnCitas);
      EntityManagerGeneric.et.commit();
           
     
    /*  EntityManagerGeneric.et.begin();
      EntityManagerGeneric.em.merge(agnCitas);
      EntityManagerGeneric.et.commit();*/
            
    }
    public void update(AgnCitas agnCitas)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
     
      EntityManagerGeneric.et.begin();
      EntityManagerGeneric.em.merge(agnCitas);
      EntityManagerGeneric.et.commit();
    }    
    
}
