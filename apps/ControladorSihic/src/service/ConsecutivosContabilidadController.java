/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.persistence.LockModeType;
import modelo.ConsecutivosContabilidad;

/**
 *
 * @author innsend
 */
public class ConsecutivosContabilidadController {
  
    public ConsecutivosContabilidad getRecord()
    {
       return (ConsecutivosContabilidad)EntityManagerGeneric.em.createQuery("select cc from ConsecutivosContabilidad cc")
                                     .setMaxResults(1)
                                      .getSingleResult();
    } 
    public ConsecutivosContabilidad createconsecutivonominaempleado(ConsecutivosContabilidad consecutivosContabilidad)
    {
       EntityManagerGeneric.et.begin();    
       consecutivosContabilidad=EntityManagerGeneric.em.find(ConsecutivosContabilidad.class, consecutivosContabilidad.getId(),LockModeType.PESSIMISTIC_WRITE);
       consecutivosContabilidad.setConsecutivonominaempleado(consecutivosContabilidad.getConsecutivonominaempleado()+Long.valueOf(1));
       consecutivosContabilidad=EntityManagerGeneric.em.merge(consecutivosContabilidad);
       EntityManagerGeneric.et.commit();
     return consecutivosContabilidad;
    }
    public ConsecutivosContabilidad findlastconsecutivorips(ConsecutivosContabilidad consecutivosContabilidad)
    {
       EntityManagerGeneric.et.begin();    
       consecutivosContabilidad=EntityManagerGeneric.em.find(ConsecutivosContabilidad.class, consecutivosContabilidad.getId(),LockModeType.PESSIMISTIC_WRITE);
       consecutivosContabilidad.setConsecutivorips(consecutivosContabilidad.getConsecutivorips()+Long.valueOf(1));
       consecutivosContabilidad=EntityManagerGeneric.em.merge(consecutivosContabilidad);
       EntityManagerGeneric.et.commit();
     return consecutivosContabilidad;
    }
    
}
