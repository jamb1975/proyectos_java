/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.ConsecutivoFactura;

/**
 *
 * @author adminlinux
 */
public class ConsecutivoFacturaController {
    public static Long getNoConsecutivo()
    {
        Long consecutivo=Long.valueOf(0);
       ConsecutivoFactura consecutivofactura;
        long timequery=0;
       
     try
       {
           timequery=System.currentTimeMillis();
           consecutivofactura=(ConsecutivoFactura)EntityManagerGeneric.em.createQuery("select cf from ConsecutivoFactura cf ")
                                                           .setMaxResults(1)
                                                           .getSingleResult();
                 }catch(Exception e)
       {
           consecutivofactura=new ConsecutivoFactura();
           consecutivofactura.setConsecutivonofactura(Long.valueOf(0));
          EntityManagerGeneric.et.begin();
           EntityManagerGeneric.em.persist(consecutivofactura);
           EntityManagerGeneric.et.commit();
           
           
       }
    
          consecutivofactura.setConsecutivonofactura(consecutivofactura.getConsecutivonofactura()+Long.valueOf(1));
          EntityManagerGeneric.et.begin();
          EntityManagerGeneric.em.merge(consecutivofactura);
          EntityManagerGeneric.et.commit();
          consecutivo=consecutivofactura.getConsecutivonofactura();
          timequery=System.currentTimeMillis()-timequery;
          System.out.println("Time Query :"+timequery);
          
           return consecutivo;
    } 
     public static Long getNoConsecutivo2(int tipodocsoporte)
    {
        Long consecutivo=Long.valueOf(0);
        ConsecutivoFactura consecutivofactura;
       long   timequery=System.currentTimeMillis();
      
      try
       {
        consecutivofactura=(ConsecutivoFactura)EntityManagerGeneric.em.createQuery("select cf from ConsecutivoFactura cf ")
                                                           .setMaxResults(1)
                                                           .getSingleResult();
       }catch(Exception e)
       {
           consecutivofactura=new ConsecutivoFactura();
           consecutivofactura.setConsecutivonofactura(Long.valueOf(0));
          EntityManagerGeneric.et.begin();
           EntityManagerGeneric.em.persist(consecutivofactura);
           EntityManagerGeneric.et.commit();
           
           
           
       }
       Long no_factura=(Long)EntityManagerGeneric.em.createQuery("select max(f.no_factura) from Factura f")
                                                           .setMaxResults(1)
                                                           .getSingleResult();
       System.out.println("No factura:"+no_factura);
           timequery=System.currentTimeMillis()-timequery;
           System.out.println("Time Query count:"+timequery);
   
          consecutivofactura.setConsecutivonofactura(no_factura+Long.valueOf(1));
          consecutivo=consecutivofactura.getConsecutivonofactura();
          EntityManagerGeneric.et.begin();
          EntityManagerGeneric.em.merge(consecutivofactura);
          EntityManagerGeneric.et.commit();
             System.out.println("Consecutivo:"+consecutivo);
          return consecutivo;
    }   
}
