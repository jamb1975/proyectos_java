/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.Empleados;

/**
 *
 * @author adminlinux
 */
public class EmpleadoController extends AbstractFacade<Empleados>{

    public EmpleadoController(Class<Empleados> entityClass) {
        super(entityClass);
    }
public void create(Empleados empleados)
{
     if(EntityManagerGeneric.et.isActive())
     {
          EntityManagerGeneric.et.rollback();
     }
           EntityManagerGeneric.et.begin();
           super.create(empleados);
                      
              EntityManagerGeneric.et.commit();
}
public Empleados update(Empleados empleados)
{
    EntityManagerGeneric.et.begin();
    empleados=EntityManagerGeneric.em.merge(empleados);
    EntityManagerGeneric.et.commit();
    return empleados;
}

 
 
 public List<Empleados> getRecords(String search)
    {
       String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
     
  return EntityManagerGeneric.em.createQuery("select emp from Empleados emp where (lower(emp.genPersonas.primerNombre) like :search " 
                                             + "  or lower(emp.genPersonas.primerApellido)like :search  "
                                        +"  or lower(emp.genPersonas.segundoNombre)like :search "
                                        +" or lower(emp.genPersonas.segundoApellido)like :search "
                                        +" or lower(emp.genPersonas.documento) like :search)"
                                            + "  order by emp.genPersonas.primerApellido,emp.genPersonas.segundoApellido,emp.genPersonas.primerNombre,emp.genPersonas.segundoNombre " )
                                             .setParameter("search", searchpattern)
                                            
                                            .getResultList();
} 

 
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
}
