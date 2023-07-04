/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.List;
import javax.persistence.EntityManager;

import modelo.Solucion;

/**
 *
 * @author adminlinux
 */
public class SolucionController extends AbstractFacade<Solucion>{

    public SolucionController(Class<Solucion> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
       return EntityManagerGeneric.em;
               
    }
    
    @Override
    public void create(Solucion solucion)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        super.create(solucion);
        EntityManagerGeneric.et.commit();
    }
    public Solucion update(Solucion solucion)
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
    public Solucion delete(Solucion solucion)
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
    public Solucion getSolucion()
    {
         Solucion  solucion=null;
        try
        {
        
       
            
               solucion=EntityManagerGeneric.em.find(Solucion.class, Long.valueOf(1));
        }catch(Exception e )
        {
            
        }
       return solucion;                                           
           
    }
    public List<Solucion> getSolucionHijos( Solucion solucion)
    {
         
               return EntityManagerGeneric.em.createQuery("select s from Solucion s where s.solucionPadre=:sp")
                                               .setParameter("sp", solucion)
                                               .getResultList();
                               
           
    }
    public Solucion findsolucioncodigo(int numeral)
    {
      List<Solucion> li_solucion=  EntityManagerGeneric.em.createQuery("select s from Solucion s where s.numeral=:num")
                                                  .setParameter("num", numeral)
                                                  .getResultList();
      if(li_solucion.size()>0)
        {
            return  li_solucion.get(0);
        }
      else
      {
          return null;
      }
    }
    
}
