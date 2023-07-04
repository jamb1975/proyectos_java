/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import static isoftconta.servicios.EntityManagerGeneric.em;
import modelo.ConsecutivosContabilidad;

/**
 *
 * @author innsend
 */
public class ConsecutivosContabilidadController {
  
    public static Long getNoConsecutivo(int tipodocsoporte)
    {
        Long consecutivo=Long.valueOf(0);
        ConsecutivosContabilidad consecutivosContabilidad;
      try
       {
        consecutivosContabilidad=(ConsecutivosContabilidad)em.createQuery("select cc from ConsecutivosContabilidad cc ")
                                                           .setMaxResults(1)
                                                           .getSingleResult();
       }catch(Exception e)
       {
           consecutivosContabilidad=new ConsecutivosContabilidad();
           consecutivosContabilidad.setConsecutivocomprobanteconsginacion(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivocomprobanteegreso(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivocomprobanteingreso(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonomina(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonominaempleado(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonotacontabilidad(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonotacredito(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonotadebito(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivorecibocaja(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivocausacioningreso(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivocausacionegreso(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivosaldosiniciales(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonotadevolucionventas(Long.valueOf(0));
           em.persist(consecutivosContabilidad);
           
           
       }
      if(tipodocsoporte==EnumDocumentoSoporte.COMPROBANTECAUSACIONINGRESO15.ordinal())
      {
          consecutivosContabilidad.setConsecutivocausacioningreso(consecutivosContabilidad.getConsecutivocausacioningreso()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivocausacioningreso();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.COMPROBANTECAUSACIONEGRESO14.ordinal())
      {
          consecutivosContabilidad.setConsecutivocausacionegreso(consecutivosContabilidad.getConsecutivocausacionegreso()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivocausacionegreso();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.COMPROBANTEINGRESO3.ordinal())
      {
          consecutivosContabilidad.setConsecutivocomprobanteingreso(consecutivosContabilidad.getConsecutivocomprobanteingreso()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivocomprobanteingreso();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.COMPROBANTEEGRESO4.ordinal())
      {
          consecutivosContabilidad.setConsecutivocomprobanteegreso(consecutivosContabilidad.getConsecutivocomprobanteegreso()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivocomprobanteegreso();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.NOTACONTABILIDAD6.ordinal())
      {
          consecutivosContabilidad.setConsecutivonotacontabilidad(consecutivosContabilidad.getConsecutivonotacontabilidad()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivonotacontabilidad();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.NOTADEBITO9.ordinal())
      {
          consecutivosContabilidad.setConsecutivonotadebito(consecutivosContabilidad.getConsecutivonotadebito()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivonotadebito();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.NOTACREDITO10.ordinal())
      {
          consecutivosContabilidad.setConsecutivonotacredito(consecutivosContabilidad.getConsecutivonotacredito()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivonotacredito();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.COMPROBANTECAUSACIONNOMINA17.ordinal())
      {
          consecutivosContabilidad.setConsecutivonomina(consecutivosContabilidad.getConsecutivonomina()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivonomina();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.RECIBOCAJAMENOR2.ordinal())
      {
          consecutivosContabilidad.setConsecutivorecibocaja(consecutivosContabilidad.getConsecutivorecibocaja()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivorecibocaja();
      }
       else
      if(tipodocsoporte==EnumDocumentoSoporte.SALDOINICIAL19.ordinal())
      {
          if(consecutivosContabilidad.getConsecutivosaldosiniciales()==null)
          {
              consecutivosContabilidad.setConsecutivosaldosiniciales(Long.valueOf(0));
          }
          consecutivosContabilidad.setConsecutivosaldosiniciales(consecutivosContabilidad.getConsecutivosaldosiniciales()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivosaldosiniciales();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.NOTIFICACIONDEVOLUCIONVENTA20.ordinal())
      {
         
         if(consecutivosContabilidad.getConsecutivonotadevolucionventas()==null)
         {
            consecutivosContabilidad.setConsecutivonotadevolucionventas(Long.valueOf(0));
         }
          consecutivosContabilidad.setConsecutivonotadevolucionventas(consecutivosContabilidad.getConsecutivonotadevolucionventas()+Long.valueOf(1));
          consecutivo=consecutivosContabilidad.getConsecutivonotadevolucionventas();
      }
      em.merge(consecutivosContabilidad);
      return consecutivo;
    } 
     public static Long getNoConsecutivo2(int tipodocsoporte)
    {
        Long consecutivo=Long.valueOf(0);
        ConsecutivosContabilidad consecutivosContabilidad;
      try
       {
        consecutivosContabilidad=(ConsecutivosContabilidad)em.createQuery("select cc from ConsecutivosContabilidad cc ")
                                                           .setMaxResults(1)
                                                           .getSingleResult();
       }catch(Exception e)
       {
           consecutivosContabilidad=new ConsecutivosContabilidad();
           consecutivosContabilidad.setConsecutivocomprobanteconsginacion(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivocomprobanteegreso(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivocomprobanteingreso(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonomina(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonominaempleado(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonotacontabilidad(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonotacredito(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonotadebito(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivorecibocaja(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivocausacioningreso(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivocausacionegreso(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivosaldosiniciales(Long.valueOf(0));
           consecutivosContabilidad.setConsecutivonotadevolucionventas(Long.valueOf(0));
           em.persist(consecutivosContabilidad);
           
           
       }
      if(tipodocsoporte==EnumDocumentoSoporte.COMPROBANTECAUSACIONINGRESO15.ordinal())
      {
          consecutivosContabilidad.setConsecutivocausacioningreso(consecutivosContabilidad.getConsecutivocausacioningreso()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivocausacioningreso();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.COMPROBANTECAUSACIONEGRESO14.ordinal())
      {
          consecutivosContabilidad.setConsecutivocausacionegreso(consecutivosContabilidad.getConsecutivocausacionegreso()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivocausacionegreso();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.COMPROBANTEINGRESO3.ordinal())
      {
          consecutivosContabilidad.setConsecutivocomprobanteingreso(consecutivosContabilidad.getConsecutivocomprobanteingreso()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivocomprobanteingreso();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.COMPROBANTEEGRESO4.ordinal())
      {
          consecutivosContabilidad.setConsecutivocomprobanteegreso(consecutivosContabilidad.getConsecutivocomprobanteegreso()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivocomprobanteegreso();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.NOTACONTABILIDAD6.ordinal())
      {
          consecutivosContabilidad.setConsecutivonotacontabilidad(consecutivosContabilidad.getConsecutivonotacontabilidad()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivonotacontabilidad();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.NOTADEBITO9.ordinal())
      {
          consecutivosContabilidad.setConsecutivonotadebito(consecutivosContabilidad.getConsecutivonotadebito()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivonotadebito();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.NOTACREDITO10.ordinal())
      {
          consecutivosContabilidad.setConsecutivonotacredito(consecutivosContabilidad.getConsecutivonotacredito()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivonotacredito();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.COMPROBANTECAUSACIONNOMINA17.ordinal())
      {
          consecutivosContabilidad.setConsecutivonomina(consecutivosContabilidad.getConsecutivonomina()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivonomina();
      }
      else
      if(tipodocsoporte==EnumDocumentoSoporte.RECIBOCAJAMENOR2.ordinal())
      {
          consecutivosContabilidad.setConsecutivorecibocaja(consecutivosContabilidad.getConsecutivorecibocaja()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivorecibocaja();
      }
       else
      if(tipodocsoporte==EnumDocumentoSoporte.SALDOINICIAL19.ordinal())
      {
          consecutivosContabilidad.setConsecutivosaldosiniciales(consecutivosContabilidad.getConsecutivosaldosiniciales()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivosaldosiniciales();
      }
       else
      if(tipodocsoporte==EnumDocumentoSoporte.NOTIFICACIONDEVOLUCIONVENTA20.ordinal())
      {
          consecutivosContabilidad.setConsecutivonotadevolucionventas(consecutivosContabilidad.getConsecutivonotadevolucionventas()+Long.valueOf(2));
          consecutivo=consecutivosContabilidad.getConsecutivonotadevolucionventas();
      }
      em.merge(consecutivosContabilidad);
      return consecutivo;
    } 
    
    
}
