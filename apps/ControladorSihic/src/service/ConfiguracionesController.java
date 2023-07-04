/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.Configuraciones;
import modelo.Producto;

/**
 *
 * @author karolyani
 */
public class ConfiguracionesController extends AbstractFacade<Configuraciones>{
 public ConfiguracionesController(Class<Configuraciones> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
             return EntityManagerGeneric.em;  
                                           
    }
    public void create(Configuraciones entity) {
     
     EntityManagerGeneric.et.begin();    
       
        super.create(entity);
       
       EntityManagerGeneric.et.commit();
    }

   
    
  

   
@Override
    public Configuraciones update(Configuraciones entity) {
          EntityManagerGeneric.et.begin();
          entity=super.update(entity);
          EntityManagerGeneric.et.commit();
          return entity;
    }

   
    public void delete(Configuraciones entity) {
          EntityManagerGeneric.et.begin();
        super.remove(super.find(entity.getId()));
           EntityManagerGeneric.et.commit();
    }

   
        public Configuraciones getRecord()
    {
        try{
            
        
        return (Configuraciones)EntityManagerGeneric.em.createQuery("select c from Configuraciones c ")
                                              .setMaxResults(1)
                                              .getSingleResult();
        }catch(Exception e)
        {
            return new Configuraciones();
        }
    } 
}
