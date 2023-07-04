package isoftconta.servicios;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.TemporalType;
import modelo.DocumentoSoporte;
import modelo.EntidadesStatic;
import modelo.LibroAuxiliar;
import modelo.Puc;
import modelo.LibroMayorBalances;
import modelo.SumaAsientosContablesDto;
import modelo.UtilDate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adminlinux
 */
public class LibroMayorBalancesController {
    private    String  codigo="";
    private static LibroMayorBalances libroMayorBalances_saldoanterior;
    public void create(LibroMayorBalances libroMayorBalances)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(libroMayorBalances);
        EntityManagerGeneric.et.commit();
    }
   
    
    public void update(LibroMayorBalances libroMayorBalances)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.merge(libroMayorBalances);
        EntityManagerGeneric.et.commit();
    }
    public List<LibroMayorBalances> getLastMovimiento()
    {
        try{
      LibroMayorBalances l=(LibroMayorBalances)EntityManagerGeneric.em.createQuery("select l from LibroMayorBalances l where l.id=(select max(l2.id) from LibroMayorBalances l2)")
                                                                   .getSingleResult();
      return EntityManagerGeneric.em.createQuery("select l from LibroMayorBalances l where l.año=:a and l.mes=:m order by l.fechaelaboracion")
                                     .setParameter("a", l.getAño())
                                     .setParameter("m", l.getMes())
                                     .getResultList();
        }catch(Exception e)
        {
            return null;
        }     
        
   }
   public List<LibroMayorBalances> addNewMovimiento() throws ParseException
    {
        LibroMayorBalances l;
              
      int año=0;
      int mes=0;
      List<LibroMayorBalances>li_movimientomesanterior;
        try{
       l=(LibroMayorBalances)EntityManagerGeneric.em.createQuery("select l from LibroMayorBalances l where l.id=(select max(l2.id) from LibroMayorBalances l2)")
                                                                   .getSingleResult();
      
      li_movimientomesanterior =EntityManagerGeneric.em.createQuery("select l from LibroMayorBalances l where l.año=:a and l.mes=:m order by l.fechaelaboracion")
                                     .setParameter("a", l.getAño())
                                     .setParameter("m", l.getMes())
                                     .getResultList();
   
      if(l.getMes()==12)
      {
          año=l.getAño()+1;
          mes=1;
      }
      else
      {
          año=l.getAño();
          mes=l.getMes()+1;
      }
       }catch(Exception e)
        {
           li_movimientomesanterior=new ArrayList<>();
           año=UtilDate.getyearfecha(new Date());
          mes=UtilDate.getmesfecha(new Date());
            System.out.println("Mes Año->"+mes+"-"+año);
        } 
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate =df.parse(año+"-"+(mes)+"-01 00:00:00");
        Date toDate = df.parse(año+"-"+(mes)+"-"+UtilDate.calculardiasdelmes(año, (mes))+" 23:59:59");
        System.out.println("from date->"+fromDate);
        System.out.println("to date->"+toDate);
        List<SumaAsientosContablesDto> li_asientoscontables=EntityManagerGeneric.em.createQuery("select distinct new modelo.SumaAsientosContablesDto(ac.conPuc,sum(ac.debe),sum(ac.haber)) from ConDetallesComprobanteContabilidad ac where ac.conComprobanteContabilidad.fechaelaboracion BETWEEN :m1 and :m2 group by ac.conPuc order by ac.conPuc.codigo")
                                      .setParameter("m1",fromDate,TemporalType.TIMESTAMP)
                                      .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                      .getResultList();
        BigDecimal acumsumadeb=BigDecimal.ZERO;
        BigDecimal acumsumacred=BigDecimal.ZERO;
      int index=0;
     Puc puc=null;
      for(SumaAsientosContablesDto sac:li_asientoscontables)
      { 
          
         if(index==0)
         {
          codigo=sac.getConPuc().getCodigo();
          if(codigo.length()==4)
          {
              puc=sac.getConPuc();
          }
          else
          {
            codigo=sac.getConPuc().getConpuc_id().getCodigo();
          if(codigo.length()==4)
          {
              puc=sac.getConPuc().getConpuc_id();
          }
          else{
          codigo=sac.getConPuc().getConpuc_id().getConpuc_id().getCodigo();
          if(codigo.length()==4)
          {
              puc=sac.getConPuc().getConpuc_id().getConpuc_id();
          }
           else{
          codigo=sac.getConPuc().getConpuc_id().getConpuc_id().getConpuc_id().getCodigo();
          if(codigo.length()==4)
          {
              puc=sac.getConPuc().getConpuc_id().getConpuc_id().getConpuc_id();
          }
          
          }
          
          }
          }
         }
         if(codigo.equals(sac.getConPuc().getCodigo().substring(0,4)))
         {
             acumsumadeb=acumsumadeb.add(sac.getTotaldebe());
             acumsumacred=acumsumacred.add(sac.getTotalhaber());
             System.out.println("codigo puc->"+codigo);
         }
         
         else
         {
          LibroMayorBalances libroMayorBalances=null;
          libroMayorBalances=new LibroMayorBalances();
          BigDecimal saldoanterior=BigDecimal.ZERO;
          List<LibroMayorBalances> _li_libromayorbalances=li_movimientomesanterior.stream().filter(line ->line.getConPuc().getCodigo().equals(codigo))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());
    if(_li_libromayorbalances.size()>0)
    { 
      if( _li_libromayorbalances.get(0).getSaldodebito().compareTo(_li_libromayorbalances.get(0).getSaldocredito())==1)
      {
          libroMayorBalances.setSaldoanteriordebito(_li_libromayorbalances.get(0).getSaldodebito());
           libroMayorBalances.setSaldoanteriorcredito(BigDecimal.ZERO);
      }
      else
      {
           libroMayorBalances.setSaldoanteriorcredito(_li_libromayorbalances.get(0).getSaldocredito());
           libroMayorBalances.setSaldoanteriordebito(BigDecimal.ZERO);
      }
          
    }
    else
    {
       libroMayorBalances.setSaldoanteriordebito(BigDecimal.ZERO);
       libroMayorBalances.setSaldoanteriorcredito(BigDecimal.ZERO);
    }
    libroMayorBalances.setMovimientosdebito(acumsumadeb);
    libroMayorBalances.setMovimientoscredito(acumsumacred);
      
         if(((libroMayorBalances.getSaldoanteriordebito().add(libroMayorBalances.getMovimientosdebito())).subtract((libroMayorBalances.getSaldoanteriorcredito().add(libroMayorBalances.getMovimientoscredito())))).compareTo(BigDecimal.ZERO)==1)
         {
             libroMayorBalances.setSaldodebito(libroMayorBalances.getSaldoanteriordebito().add(libroMayorBalances.getMovimientosdebito().subtract((libroMayorBalances.getMovimientoscredito().add(libroMayorBalances.getSaldoanteriorcredito())))));
         }
         else
         {
           if(((libroMayorBalances.getSaldoanteriordebito().add(libroMayorBalances.getMovimientosdebito())).subtract((libroMayorBalances.getSaldoanteriorcredito().add(libroMayorBalances.getMovimientoscredito())))).compareTo(BigDecimal.ZERO)==-1)
         {
             libroMayorBalances.setSaldocredito(libroMayorBalances.getSaldoanteriorcredito().add(libroMayorBalances.getMovimientoscredito().subtract((libroMayorBalances.getMovimientosdebito().add(libroMayorBalances.getSaldoanteriordebito())))));
         } 
           else
         {
           if(((libroMayorBalances.getSaldoanteriordebito().add(libroMayorBalances.getMovimientosdebito())).subtract((libroMayorBalances.getSaldoanteriorcredito().add(libroMayorBalances.getMovimientoscredito())))).compareTo(BigDecimal.ZERO)==0)
         {
             libroMayorBalances.setSaldocredito(BigDecimal.ZERO);
             libroMayorBalances.setSaldodebito(BigDecimal.ZERO);
         }  
         }
         }
      libroMayorBalances.setAño(año);
      libroMayorBalances.setMes(mes);
      libroMayorBalances.setConPuc(puc);
      libroMayorBalances.setFechaelaboracion(new Date());
      if(EntityManagerGeneric.et.isActive())
      {
          EntityManagerGeneric.et.rollback();
      }
      EntityManagerGeneric.et.begin();
      EntityManagerGeneric.em.persist(libroMayorBalances);
       System.out.println("Guardado puc->"+codigo);
      EntityManagerGeneric.et.commit();
      
         
         codigo=sac.getConPuc().getCodigo();
          if(codigo.length()==4)
          {
              puc=sac.getConPuc();
          }
          else
          {
            codigo=sac.getConPuc().getConpuc_id().getCodigo();
          if(codigo.length()==4)
          {
              puc=sac.getConPuc().getConpuc_id();
          }
          else{
          codigo=sac.getConPuc().getConpuc_id().getConpuc_id().getCodigo();
          if(codigo.length()==4)
          {
              puc=sac.getConPuc().getConpuc_id().getConpuc_id();
          }
           else{
          codigo=sac.getConPuc().getConpuc_id().getConpuc_id().getConpuc_id().getCodigo();
          if(codigo.length()==4)
          {
              puc=sac.getConPuc().getConpuc_id().getConpuc_id().getConpuc_id();
          }
          
          }
          
          }
          }
         acumsumadeb=BigDecimal.ZERO;
         acumsumacred=BigDecimal.ZERO;
         acumsumadeb=acumsumadeb.add(sac.getTotaldebe());
         acumsumacred=acumsumacred.add(sac.getTotalhaber());
         }
         index++;
      }   
      return EntityManagerGeneric.em.createQuery("select l from LibroMayorBalances l where l.año=:a and l.mes=:m order by l.conPuc.codigo" )
                                       .setParameter("a", año)
                                       .setParameter("m", mes)
                                       .getResultList();
                
   } 
  public List<LibroMayorBalances> volveracalcularlibromayorbalances(int año,int mes) throws ParseException
  {
      if(EntityManagerGeneric.et.isActive())
      {
          EntityManagerGeneric.et.rollback();
      }
      System.out.println("Mes Año->"+mes+"-"+año);
      EntityManagerGeneric.et.begin();
      EntityManagerGeneric.em.createNativeQuery("delete from LibroMayorBalances where aÑo=?1 and mes=?2 ")
                             .setParameter("1", año)
                             .setParameter("2", mes)
                             .executeUpdate();
      EntityManagerGeneric.et.commit();
      return addNewMovimiento();
  }
  
  public static void saldosinicales()
  { 
      int reg=0;
      EntidadesStatic.es_puc=null;
      int year=UtilDate.getyearfecha(EntidadesStatic.es_documentosoporte.getFechaelaboracion());
      int  mes=UtilDate.getmesfecha(EntidadesStatic.es_documentosoporte.getFechaelaboracion());
      BigDecimal acumdeb=BigDecimal.ZERO;
      BigDecimal acumcred=BigDecimal.ZERO;
      Collections.sort(EntidadesStatic.es_documentosoporte.getLi_libroauxiliar(), (o1, o2) -> o1.getConPuc().getCodigo().compareTo(o2.getConPuc().getCodigo()));
     
      String codigotemp="";
       for(LibroAuxiliar la: EntidadesStatic.es_documentosoporte.getLi_libroauxiliar())
      {
      if(reg==0)
       {
           codigotemp=la.getConPuc().getCodigo().substring(0, 4);
           DocumentoSoporteController.findpucporcodigo(codigotemp);
           acumdeb=acumdeb.add(la.getDebe());
           acumcred=acumcred.add(la.getHaber());
       }
       else
          if(reg>0)
          {
              if(codigotemp.equals(la.getConPuc().getCodigo().substring(0, 4)))
              {
                  acumdeb=acumdeb.add(la.getDebe());
                  acumcred=acumcred.add(la.getHaber()); 
              }
              else
              {
                     codigotemp=la.getConPuc().getCodigo().substring(0, 4);
                     DocumentoSoporteController.findpucporcodigo(codigotemp);
                    
                       acumdeb=BigDecimal.ZERO;
                       acumcred=BigDecimal.ZERO;
                       acumdeb=acumdeb.add(la.getDebe());
                       acumcred=acumcred.add(la.getHaber());
                     }
                }
             EntidadesStatic.li_libromayorbalances=EntityManagerGeneric.em.createQuery("select lm from LibroMayorBalances lm where lm.año=:pyear and lm.conPuc.id=:pid")
                                                                .setParameter("pyear", year)
                                                                .setParameter("pid", EntidadesStatic.es_puc.getId())
                                                                .setMaxResults(1)
                                                                .getResultList();
      
                       if( EntidadesStatic.li_libromayorbalances.size()==0)
                       {
                           EntidadesStatic.es_libromayorbalances=null;
                           EntidadesStatic.es_libromayorbalances=new LibroMayorBalances();
                       }
                     else
                      {
                        EntidadesStatic.es_libromayorbalances= EntidadesStatic.li_libromayorbalances.get(0);
                      }
                       BigDecimal total=acumdeb.subtract(acumcred);
                       EntidadesStatic.es_libromayorbalances.setAño(year);
                       EntidadesStatic.es_libromayorbalances.setMes(mes);
                       EntidadesStatic.es_libromayorbalances.setConPuc(EntidadesStatic.es_puc);
                       EntidadesStatic.es_libromayorbalances.setFechaelaboracion(EntidadesStatic.es_documentosoporte.getFechaelaboracion());
                       EntidadesStatic.es_libromayorbalances.setDetalle("Comprobante Saldos Iniciales");
                       EntidadesStatic.es_libromayorbalances.setSaldodebito(total.compareTo(BigDecimal.ZERO)==1?total:BigDecimal.ZERO);
                       EntidadesStatic.es_libromayorbalances.setSaldocredito(total.compareTo(BigDecimal.ZERO)==-1?total.multiply(BigDecimal.valueOf(-1)):BigDecimal.ZERO);
                       EntidadesStatic.es_libromayorbalances.setMovimientosdebito(acumdeb);
                       EntidadesStatic.es_libromayorbalances.setMovimientoscredito(acumcred);
                       if(EntityManagerGeneric.et.isActive())
                       {
                           EntityManagerGeneric.et.rollback();
                       }
                       EntityManagerGeneric.et.begin();
                       
                       if(EntidadesStatic.es_libromayorbalances.getId()!=null)
                       {
                           EntityManagerGeneric.em.persist(EntidadesStatic.es_libromayorbalances);
                       }
                       else
                       {
                           EntityManagerGeneric.em.merge(EntidadesStatic.es_libromayorbalances);
                       }
                       EntityManagerGeneric.et.commit();

                       reg++;
      }
  }
   public static void trasladoalibromayor(String fromdate, String todate) throws ParseException
  { 
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate =df.parse(fromdate+" 00:00:00");
        Date toDate = df.parse(todate+" 23:59:59");
        int reg=0;
        EntidadesStatic.es_puc=null;
        int year=UtilDate.getyearfecha(fromDate);
        int  mes=UtilDate.getmesfecha(fromDate);
        int yearsaldoanterior=UtilDate.getyearfecha(fromDate);
        int  messaldoanterior=UtilDate.getmesfecha(fromDate);
        if(mes==1)
        {
            yearsaldoanterior=year-1;
            messaldoanterior=12;
        }
        else
        {
            messaldoanterior=messaldoanterior-1;
        }
        BigDecimal acumdeb=BigDecimal.ZERO;
        BigDecimal acumcred=BigDecimal.ZERO;
        EntidadesStatic.li_libroauxiliar= EntityManagerGeneric.em.createQuery("select la from LibroAuxiliar la where la.fechaelaboracion BETWEEN :d1 and :d2 order by la.fechaelaboracion")
                                        .setParameter("d1", fromDate,TemporalType.TIMESTAMP)
                                      .setParameter("d2", toDate,TemporalType.TIMESTAMP)
                                        .getResultList();
        Collections.sort(EntidadesStatic.li_libroauxiliar, (o1, o2) -> o1.getConPuc().getCodigo().compareTo(o2.getConPuc().getCodigo()));

      String codigotemp="";
      for(LibroAuxiliar la: EntidadesStatic.li_libroauxiliar)
      {
       if(reg==0)
       {
           codigotemp=la.getConPuc().getCodigo().substring(0, 4);
             DocumentoSoporteController.findpucporcodigo(codigotemp);
           acumdeb=acumdeb.add(la.getDebe());
           acumcred=acumcred.add(la.getHaber());
       }
       else
          if(reg>0)
          {
              if(codigotemp.equals(la.getConPuc().getCodigo().substring(0, 4)))
              {
                  acumdeb=acumdeb.add(la.getDebe());
                  acumcred=acumcred.add(la.getHaber()); 
              }
              else
              {
                  
                  
                      codigotemp=la.getConPuc().getCodigo().substring(0, 4);
                        DocumentoSoporteController.findpucporcodigo(codigotemp);
                    
                       acumdeb=BigDecimal.ZERO;
                       acumcred=BigDecimal.ZERO;
                       acumdeb=acumdeb.add(la.getDebe());
                       acumcred=acumcred.add(la.getHaber());
                }
                }   
                   try
                    {
                        if(mes==1)
                    {
                       libroMayorBalances_saldoanterior=(LibroMayorBalances)EntityManagerGeneric.em.createQuery("select lm from LibroMayorBalances lm where lm.año=:pyear and lm.mes=:pmes  and lm.conPuc.id=:pid and lm.detalle=='Comprobante Saldos Iniciales'")
                                                                .setParameter("pyear", year)
                                                                .setParameter("pmes", mes)
                                                                .setParameter("pid", EntidadesStatic.es_puc.getId())
                                                                .getSingleResult();
                    }
                    
                    else
                       if(mes>1)
                    {     
                         libroMayorBalances_saldoanterior=(LibroMayorBalances)EntityManagerGeneric.em.createQuery("select lm from LibroMayorBalances lm where lm.año=:pyear and lm.mes=:pmes  and lm.conPuc.id=:pid and lm.detalle!='Comprobante Saldos Iniciales'")
                                                                .setParameter("pyear", year)
                                                                .setParameter("pmes", messaldoanterior)
                                                                .setParameter("pid", EntidadesStatic.es_puc.getId())
                                                                .getSingleResult();
                    } 
                  }catch(Exception e)
                  {
                        libroMayorBalances_saldoanterior=null;
                        libroMayorBalances_saldoanterior=new LibroMayorBalances();
                        libroMayorBalances_saldoanterior.setSaldodebito(BigDecimal.ZERO);
                        libroMayorBalances_saldoanterior.setSaldoanteriorcredito(BigDecimal.ZERO);
                  }
                    EntidadesStatic.li_libromayorbalances=EntityManagerGeneric.em.createQuery("select lm from LibroMayorBalances lm where lm.año=:pyear and lm.mes=:pmes and lm.conPuc.id=:pid and lm.detalle!='Comprobante Saldos Iniciales'")
                                                                .setParameter("pyear", year)
                                                                .setParameter("pmes", mes)
                                                                .setParameter("pid", EntidadesStatic.es_puc.getId())
                                                                .setMaxResults(1)
                                                                .getResultList();
      
                       if(EntidadesStatic.li_libromayorbalances.size()==0)
                       {
                           EntidadesStatic.es_libromayorbalances=null;
                           EntidadesStatic.es_libromayorbalances=new LibroMayorBalances();
                       }
                     else
                      {
                        EntidadesStatic.es_libromayorbalances= EntidadesStatic.li_libromayorbalances.get(0);
                      }
                        EntidadesStatic.es_libromayorbalances.setMovimientosdebito(acumdeb);
                       EntidadesStatic.es_libromayorbalances.setMovimientoscredito(acumcred);
                       acumdeb=acumdeb.add(libroMayorBalances_saldoanterior.getSaldodebito());
                       acumcred=acumcred.add(libroMayorBalances_saldoanterior.getSaldocredito());
                       BigDecimal total=acumdeb.subtract(acumcred);
                       EntidadesStatic.es_libromayorbalances.setAño(year);
                       EntidadesStatic.es_libromayorbalances.setMes(mes);
                       EntidadesStatic.es_libromayorbalances.setConPuc(EntidadesStatic.es_puc);
                       EntidadesStatic.es_libromayorbalances.setFechaelaboracion(toDate);
                       EntidadesStatic.es_libromayorbalances.setDetalle("Operaciones en el mes de: "+UtilDate.colocrnombredelmes(mes));
                       EntidadesStatic.es_libromayorbalances.setSaldoanteriordebito(libroMayorBalances_saldoanterior.getSaldodebito());
                       EntidadesStatic.es_libromayorbalances.setSaldoanteriorcredito(libroMayorBalances_saldoanterior.getSaldocredito());
                       EntidadesStatic.es_libromayorbalances.setSaldodebito(total.compareTo(BigDecimal.ZERO)==1?total:BigDecimal.ZERO);
                       EntidadesStatic.es_libromayorbalances.setSaldocredito(total.compareTo(BigDecimal.ZERO)==-1?total.multiply(BigDecimal.valueOf(-1)):BigDecimal.ZERO);
                      
                       if(EntityManagerGeneric.et.isActive())
                       {
                           EntityManagerGeneric.et.rollback();
                       }
                       EntityManagerGeneric.et.begin();
                       
                       if(EntidadesStatic.es_libromayorbalances.getId()!=null)
                       {
                           EntityManagerGeneric.em.persist(EntidadesStatic.es_libromayorbalances);
                       }
                       else
                       {
                           EntityManagerGeneric.em.merge(EntidadesStatic.es_libromayorbalances);
                       }
                       EntityManagerGeneric.et.commit();
                       System.out.println("codigotemo->"+codigotemp);
                       reg++;
      }
  }
   public static List<LibroMayorBalances> getRecords(String fromdate, String todate) throws ParseException
    {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate =df.parse(fromdate+" 00:00:00");
        Date toDate = df.parse(todate+" 23:59:59");
        EntidadesStatic.li_libromayorbalances= EntityManagerGeneric.em.createQuery("select lm from LibroMayorBalances lm where lm.fechaelaboracion BETWEEN :d1 and :d2 order by lm.fechaelaboracion")
                                      .setParameter("d1", fromDate,TemporalType.TIMESTAMP)
                                      .setParameter("d2", toDate,TemporalType.TIMESTAMP)
                                      .getResultList();
        return EntidadesStatic.li_libromayorbalances;
                                              
    }
}
