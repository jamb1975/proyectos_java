/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import javax.persistence.EntityTransaction;
import static isoftconta.servicios.EntityManagerGeneric.em;
import modelo.DatosPyme;
import modelo.EntidadesStatic;

/**
 *
 * @author adminlinux
 */
public class DatosPymeController {
         private static   EntityTransaction et=null;
      
   public static int servicio_crear()
   {   try
      {
            
             et=em.getTransaction();
          
       verificarestadotransacion();
       et.begin();
       em.merge(EntidadesStatic.es_datospyme);
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
     
     EntidadesStatic.es_datospyme= em.merge(EntidadesStatic.es_datospyme);
       et.commit();
       
        return 1;
      }catch(Exception e)
      {
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
   
 public static DatosPyme getRecord()
 {
     try
     {
      return   (DatosPyme)EntityManagerGeneric.em.createQuery("select dp from DatosPyme dp ")
                                    .setMaxResults(1)
                                   .getSingleResult();
     }catch(Exception e)
     {
         return null;
     }
 }     
         
}
