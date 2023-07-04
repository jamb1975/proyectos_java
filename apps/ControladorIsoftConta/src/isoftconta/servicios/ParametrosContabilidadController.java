/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import static isoftconta.servicios.EntityManagerGeneric.em;
import javax.persistence.EntityTransaction;
import modelo.EntidadesStatic;
import modelo.ParametrosContabilidad;

/**
 *
 * @author isoft
 */
public class ParametrosContabilidadController {
    private static   EntityTransaction et=null;
      public static int servicio_crear()
   {   try
      {
            
             et=em.getTransaction();
          
       verificarestadotransacion();
       et.begin();
       em.merge(EntidadesStatic.es_parametroscontabilidad);
       et.commit();
       
        return 1;
      }catch(Exception e)
      { 
        e.printStackTrace();
         return -1; 
      
      }
   }
   public static int servicio_actualizar()
   {   try
      {
          
             et=em.getTransaction();
          
       verificarestadotransacion();
       et.begin();
     
     EntidadesStatic.es_parametroscontabilidad= em.merge(EntidadesStatic.es_parametroscontabilidad);
       et.commit();
       
        return 1;
      }catch(Exception e)
      {
          e.printStackTrace();
         return -1; 
      }
   }
   private static void verificarestadotransacion()
   {
       if(et.isActive())
       {
           et.rollback();
       }
   }
   
 public static ParametrosContabilidad getRecord()
 {
     try
     {
      return   (ParametrosContabilidad)EntityManagerGeneric.em.createQuery("select pc from ParametrosContabilidad pc ")
                                    .setMaxResults(1)
                                   .getSingleResult();
     }catch(Exception e)
     {
         return null;
     }
 }     
}
