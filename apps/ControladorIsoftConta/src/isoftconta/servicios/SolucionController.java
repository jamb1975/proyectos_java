/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.Puc;
import modelo.Servicios;

/**
 *
 * @author adminlinux
 */
public class SolucionController extends AbstractFacade<Servicios>{

    public SolucionController(Class<Servicios> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
       return EntityManagerGeneric.em;
               
    }
    
    @Override
    public void create(Servicios solucion)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        super.create(solucion);
        EntityManagerGeneric.et.commit();
    }
    public Servicios update(Servicios solucion)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
       solucion= super.edit(solucion);
        EntityManagerGeneric.et.commit();
        return solucion;
    }
    public Servicios delete(Servicios solucion)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
         super.remove(solucion);
        EntityManagerGeneric.et.commit();
        return solucion;
    }
    public Servicios getSolucion()
    {
         Servicios  solucion=null;
        try
        {
        
       
            
               solucion=EntityManagerGeneric.em.find(Servicios.class, Long.valueOf(1));
        }catch(Exception e )
        {
            
        }
       return solucion;                                           
           
    }
    public List<Servicios> getSolucionHijos( Servicios solucion)
    {
         
               return EntityManagerGeneric.em.createQuery("select s from Solucion s where s.solucionPadre=:sp")
                                               .setParameter("sp", solucion)
                                               .getResultList();
                               
           
    }
    
}
