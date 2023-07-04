/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.CargosEntidad;

/**
 *
 * @author adminlinux
 */
public class CargosEntidadController extends AbstractFacade<CargosEntidad>
{

    public CargosEntidadController(Class<CargosEntidad> cargosentidad) {
        super(cargosentidad);
    }
   public void create(CargosEntidad cargosEntidad)
   {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
       EntityManagerGeneric.et.begin();
       EntityManagerGeneric.em.persist(cargosEntidad);
        EntityManagerGeneric.et.commit();
   } 
    @Override
   public CargosEntidad update(CargosEntidad cargosEntidad)
   {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
      EntityManagerGeneric.et.begin();
      cargosEntidad= EntityManagerGeneric.em.merge(cargosEntidad);
       EntityManagerGeneric.et.commit();
       return cargosEntidad;
   }
   public List<CargosEntidad> getRecords()
   {
       return EntityManagerGeneric.em.createQuery("select ce from CargosEntidad ce order by ce.nombre")
                                      .getResultList();
   }
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
    
}
