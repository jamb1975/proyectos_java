/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;



import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.TemporalType;
import modelo.DocumentoSoporte;
import modelo.EntidadesStatic;
import modelo.LibroAuxiliar;

/**
 *
 * @author adminlinux
 */
public class LibroAuxiliarController {
public static double  avance_progress=0;
public static boolean  estado_progress=true;
public static void servicio_crear()
{
    EntityManagerGeneric.verificarestadotransaccion();   
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(EntidadesStatic.es_libroauxiliar);
    EntityManagerGeneric.et.commit();
}
public static void servicio_actualizar()
{
     EntityManagerGeneric.verificarestadotransaccion();   
     EntityManagerGeneric.et.begin();
    
    EntityManagerGeneric.em.merge(EntidadesStatic.es_libroauxiliar);
    EntityManagerGeneric.et.commit();
}
public static void servicio_remover()
{
         EntityManagerGeneric.verificarestadotransaccion();
         EntityManagerGeneric.et.begin();
         EntityManagerGeneric.em.find(LibroAuxiliar.class, EntidadesStatic.es_libroauxiliar.getId());
         EntityManagerGeneric.em.remove(EntidadesStatic.es_libroauxiliar);
         EntityManagerGeneric.et.commit();
      
}      
    public static List<LibroAuxiliar> getRecords(String fromdate, String todate,String tercero,String codpuc) throws ParseException
    {
        System.out.println("fromdate->"+ fromdate);
        System.out.println("todate->"+ todate);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate =df.parse(fromdate+" 00:00:00");
        Date toDate = df.parse(todate+" 23:59:59");
        String stercero=tercero==null?"%":"%"+tercero.toLowerCase().replace('*','%')+"%";
        String scodpuc=codpuc==null?"%":codpuc.replace('*','%')+"%";
        
        return EntityManagerGeneric.em.createQuery("select la from LibroAuxiliar la LEFT OUTER JOIN la.terceros ter LEFT OUTER JOIN la.documentoSoporte.clientes cli where la.documentoSoporte.fechaelaboracion BETWEEN :d1 and :d2 and (ter.no_ident like :ptercero or lower(CONCAT(ter.nombre,' ',ter.primerapellido)) like :ptercero or cli.no_ident like :ptercero or lower(CONCAT(cli.nombre,' ',cli.primerapellido)) like :ptercero ) and la.conPuc.codigo like :pcod order by la.documentoSoporte.fechaelaboracion")
                                      .setParameter("d1", fromDate,TemporalType.TIMESTAMP)
                                      .setParameter("d2", toDate,TemporalType.TIMESTAMP)
                                      .setParameter("ptercero", stercero)
                                      .setParameter("pcod", scodpuc)                      
                                      .getResultList();
                                              
    }
    public static void removerregistrosidsource(int tipocod,Long id)
    {
        BigDecimal saldo=BigDecimal.ZERO;
        
        List<LibroAuxiliar> list_la=EntityManagerGeneric.em.createQuery("select la from LibroAuxiliar la where la.id_source_mov=:idsource").setParameter("idsource", id).getResultList();
           
        //buscamos el registro segun el valor en debe o credito y lo eliminamos  
        for(LibroAuxiliar la:list_la)
        {
              EntidadesStatic.es_libroauxiliar=null;
                   EntidadesStatic.es_libroauxiliar=new LibroAuxiliar();
                   EntidadesStatic.es_libroauxiliar=la;

        
          if(tipocod==3 && la.getHaber().compareTo(BigDecimal.ZERO)==1)
         {
                   saldo=la.getHaber();
                   list_la.remove(la);
                   servicio_remover();
             
              
         break;
         }
         else
         {
         if(tipocod==4 && la.getDebe().compareTo(BigDecimal.ZERO)==1)
         {
           saldo=la.getDebe();
           list_la.remove(la);
           servicio_remover();
         }
         
         
    }
        }
        //realizamos el proceso para que el valor del registro ubicado anteriormente sea igual al registro y eliminamos ese registro
        for(LibroAuxiliar la:list_la)
        {
            
         EntidadesStatic.es_libroauxiliar=null;
         EntidadesStatic.es_libroauxiliar=new LibroAuxiliar();
         EntidadesStatic.es_libroauxiliar=la;
         //si tipo documento es ingreso localizamos el registro con valor mayor a 0 en debito sini en credito
         if(tipocod==3 && la.getDebe().compareTo(BigDecimal.ZERO)==1)
         {
             if(la.getDebe().compareTo(saldo)==1 || la.getDebe().compareTo(saldo)==0)
             {
               saldo=la.getDebe().subtract(saldo);
               la.setHaber(saldo);
                  
                   
               if(saldo.compareTo(BigDecimal.ZERO)==1)
               {
                  servicio_actualizar();
               }
               else
               {
                   servicio_remover();
               }
              break;
             }
             else
             {
                 saldo=saldo.subtract(la.getDebe());
                 servicio_remover();
             }
            
         }
         else
         {
              if(tipocod==4 && la.getHaber().compareTo(BigDecimal.ZERO)==1)
         {
             if(la.getHaber().compareTo(saldo)==1 || la.getHaber().compareTo(saldo)==0)
             {
               saldo=la.getHaber().subtract(saldo);
               la.setHaber(saldo);
                  
                   
               if(saldo.compareTo(BigDecimal.ZERO)==1)
               {
                  servicio_actualizar();
               }
               else
               {
                   servicio_remover();
               }
              break;
             }
             else
             {
                 saldo=saldo.subtract(la.getHaber());
                 servicio_remover();
             }
            
         }
         }
         
         
         
    }
        
    } 
    
public static void restaurarfechasyterceros()
{
    List<DocumentoSoporte> li_comprobantes=EntityManagerGeneric.em.createQuery("select c from DocumentoSoporte c")
                                                                  .getResultList();
  avance_progress=0;
  estado_progress=true;
    for(DocumentoSoporte c: li_comprobantes)
  {
      
      avance_progress=avance_progress+(1.0/li_comprobantes.size());
      List<LibroAuxiliar>li_liAuxiliars=c.getLi_libroauxiliar();
      for(LibroAuxiliar la: li_liAuxiliars)
      {
          EntidadesStatic.es_libroauxiliar=null;
          EntidadesStatic.es_libroauxiliar=new LibroAuxiliar();
          EntidadesStatic.es_libroauxiliar=la;
          EntidadesStatic.es_libroauxiliar.setFechaelaboracion(c.getFechaelaboracion());
          if(EntidadesStatic.es_libroauxiliar.getTerceros()==null)
          {
              EntidadesStatic.es_libroauxiliar.setTerceros(c.getClientes());
          }
          if(EntidadesStatic.es_libroauxiliar.getDocumentoSoporte().getTipodocsoporte()==EnumDocumentoSoporte.COMPROBANTECAUSACIONINGRESO15.ordinal())
          {
          if(EntidadesStatic.es_libroauxiliar.getNofactura()==null)
          {
             EntidadesStatic.es_libroauxiliar.setNofactura(c.getPrefijo()+c.getNo_factura().toString());
          }
          else
          {
          if(EntidadesStatic.es_libroauxiliar.getNofactura().length()==0)
          {
             EntidadesStatic.es_libroauxiliar.setNofactura(c.getPrefijo()+c.getNo_factura().toString());
          }  
          }
          }
          servicio_actualizar();
              //  System.out.println("Comprobante:"+ EntidadesStatic.es_libroauxiliar.getDocumentoSoporte().getNo_consecutivo());
 
      }
  }
    estado_progress=false;
}       
}
