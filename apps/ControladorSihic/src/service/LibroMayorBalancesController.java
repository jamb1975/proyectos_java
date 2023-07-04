package service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.TemporalType;
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
    public void create(LibroMayorBalances libroMayorBalances)
    {
        if(EntityManagerGeneric.isActive())
        {
            EntityManagerGeneric.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(libroMayorBalances);
        EntityManagerGeneric.et.commit();
    }
   
    
    public void update(LibroMayorBalances libroMayorBalances)
    {
        if(EntityManagerGeneric.isActive())
        {
            EntityManagerGeneric.rollback();
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
      if(EntityManagerGeneric.isActive())
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
}
