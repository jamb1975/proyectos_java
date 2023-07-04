/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.LibroAuxiliar;
import modelo.Puc;
import modelo.temppuc;

/**
 *
 * @author adminlinux
 */
 
public class ConPucController {

    
    public void create(Puc conPuc)
      
{   
   
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }   
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(conPuc);
     EntityManagerGeneric.et.commit();
}
    
 public void update(Puc conPuc)
 {   
   
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }   
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.merge(conPuc);
    EntityManagerGeneric.et.commit();
}
 public void delete(Puc conPuc)
      
{   
   
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }   
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.remove(conPuc);
     EntityManagerGeneric.et.commit();
}
    
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
    public Puc getConPuc()
    {
         Puc  conpuc=null;
        try
        {
        
       
            
               conpuc=EntityManagerGeneric.em.find(Puc.class, Long.valueOf(1));
        }catch(Exception e )
        {
            
        }
       return conpuc;                                           
           
    }
     public List<Puc> getConPucHijos( Puc conPuc)
    {
         
               return EntityManagerGeneric.em.createQuery("select cp from ConPuc cp where cp.conpuc_id=:conpucid")
                                               .setParameter("conpucid", conPuc)
                                               .getResultList();
                               
           
    }  
  
     public Puc findConPuc(String codigo,int vercuentas)
     {
         try
         {
             if(vercuentas==0)
             {
             return (Puc)EntityManagerGeneric.em.createQuery("select cp from ConPuc cp where cp.codigo=:cod and (cp.deshabilitar=false or cp.deshabilitar is null)")
                                                       .setParameter("cod", codigo.trim())
                                                       .getSingleResult();
             }
             else
             {
              if(vercuentas==1)
             {
             return (Puc)EntityManagerGeneric.em.createQuery("select cp from ConPuc cp where cp.codigo=:cod")
                                                       .setParameter("cod", codigo.trim())
                                                       .getSingleResult();
             } 
              else
              {
                  if(vercuentas==2)
             {
             return (Puc)EntityManagerGeneric.em.createQuery("select cp from ConPuc cp where cp.codigo=:cod ")
                                                       .setParameter("cod", codigo.trim())
                                                       .getSingleResult();
             }
             else
                  {
                     return (Puc)EntityManagerGeneric.em.createQuery("select cp from ConPuc cp where cp.codigo=:cod ")
                                                       .setParameter("cod", codigo.trim())
                                                       .getSingleResult(); 
                  }
              }
             }
         }
         catch(Exception e)
         {
           return null;  
         }
     }
     
public List<LibroAuxiliar> getLibroAuxiliar( String datefrom,  String dateto, String codigo) throws ParseException {
String searchpattern = codigo==null ? "%":  codigo.toLowerCase()+ "%";
 List<LibroAuxiliar> list_libroauxiliar;
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<LibroAuxiliar> cq = cb.createQuery(LibroAuxiliar.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<LibroAuxiliar> _libroauxiliar = m.entity(LibroAuxiliar.class);
Root<LibroAuxiliar> pet = cq.from(_libroauxiliar);



Calendar calendar = Calendar.getInstance();
calendar.set(Calendar.HOUR_OF_DAY, 0);
calendar.set(Calendar.MINUTE, 0);
calendar.set(Calendar.SECOND, 0);
 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");

calendar.set(Calendar.HOUR_OF_DAY, 23);
calendar.set(Calendar.MINUTE, 59);
calendar.set(Calendar.SECOND, 59);
Date toDate = df.parse(dateto+" 23:59:59");
Predicate predicate=cb.between(pet.get("fechaelaboracion"), fromDate,toDate);

cq.where(predicate,cb.or(cb.like(pet.get("conPuc").get("codigo"),searchpattern.toUpperCase())));
TypedQuery<LibroAuxiliar> q =q=null;
list_libroauxiliar=null;
q=EntityManagerGeneric.em.createQuery(cq);
     
list_libroauxiliar=q.getResultList();

        
return list_libroauxiliar;


 
        
    } 
public void quitasespacios()
{
     List<Puc> l_puc= EntityManagerGeneric.em.createQuery("select cp from ConPuc cp order by cp.id")
                                             .getResultList();  
  Puc padre_puc=null;
  
  for(Puc line: l_puc)
  {
      line.setCodigo(line.getCodigo().replace(" ",""));
      line.setCodigo(line.getCodigo().trim());
      line.setNombre(line.getNombre().trim());
      EntityManagerGeneric.et.begin();
      EntityManagerGeneric.em.merge(line);
      EntityManagerGeneric.et.commit();
  }
}
public void setpuc()
{
      Puc conPuc=null;
     conPuc=new Puc();
     conPuc.setCodigo("0");
     conPuc.setNombre("Puc");
     EntityManagerGeneric.et.begin();
     EntityManagerGeneric.em.persist(conPuc);
     EntityManagerGeneric.et.commit();
    boolean banderastop;
    String codigo="";
    String descripcion="";
  List<temppuc> l_puc= EntityManagerGeneric.em.createQuery("select tp from temppuc tp order by tp.id")
                                             .getResultList();
  String temp="";
  for(temppuc tp: l_puc)
  {
      banderastop=true;
      codigo="";
      descripcion="";
       temp=new String();
      temp=tp.getCuenta().trim();
      for(int i=0;i<temp.length();i++)
      {
         
         
          temp=new String();
          temp=tp.getCuenta().trim();
          
          if(temp.substring(i, i+1).matches("[0-9]+") && banderastop)
          {
             temp=new String();
             temp=tp.getCuenta().trim();  
             codigo=codigo+temp.substring(i, i+1);
             
          }
          else
          {
               temp=new String();
               temp=tp.getCuenta().trim();
              descripcion=descripcion+temp.substring(i, i+1);
              banderastop=false;
              
          }
      }
      conPuc=null;
     conPuc=new Puc();
     conPuc.setCodigo(codigo.trim());
     conPuc.setNombre(descripcion.trim());
     EntityManagerGeneric.et.begin();
     EntityManagerGeneric.em.persist(conPuc);
     EntityManagerGeneric.et.commit();
  }

}
public void setIdPadre()
{
  List<Puc> l_puc= EntityManagerGeneric.em.createQuery("select cp from ConPuc cp order by cp.id")
                                             .getResultList();  
  Puc padre_puc=null;
  
  for(Puc line: l_puc)
  {
      if((line.getCodigo().equals("1000") ||line.getCodigo().equals("2000")|| line.getCodigo().equals("3000") || line.getCodigo().equals("4000")|| line.getCodigo().equals("5000")||line.getCodigo().equals("6000")))
      {
       padre_puc=new Puc();
       padre_puc.setId(Long.valueOf(1));
       line.setConpuc_id(padre_puc);
      }
      else
      {
      //if((line.getCodigo().equals("1100") ||line.getCodigo().equals("2100")|| line.getCodigo().equals("3100") || line.getCodigo().equals("4100")|| line.getCodigo().equals("5100")||line.getCodigo().equals("6100")))
      boolean find=false;
      for(int i=1100;i<=6100;i=i+100)
      {
              if(line.getCodigo().equals(String.valueOf(i)))
              {
                   System.out.println("codigo puc:"+ line.getCodigo()+ " i->"+i);            
                  find =true;
                  break;
              }
      }
              if(find)
              {       
               line.setConpuc_id(findPadre(line.getCodigo(), 0, 1,2));
              }
      
      else
      {
          if(line.getCodigo().length()==4)
          {
              
               line.setConpuc_id(findPadre(line.getCodigo(), 0, 2,4));
          }
      else
      {
          if(line.getCodigo().length()==6)
          {
              line.setConpuc_id(findPadre(line.getCodigo(), 0, 4,6));
          }
           else
      {
          if(line.getCodigo().length()==11)
          {
              line.setConpuc_id(findPadre(line.getCodigo(), 0, 6,11));
          }
         /* else
      {
          if(line.getCodigo().length()==10)
          {
              line.setConpuc_id(findPadre(line.getCodigo(), 0, 8));
          }
      {
         
      }
      }*/
      
      }
     
      }
         
      
      }
     
      EntityManagerGeneric.et.begin();
      EntityManagerGeneric.em.merge(line);
      EntityManagerGeneric.et.commit();
      
  }
}
}
public Puc findPadre(String codigo,int indexI, int indexF,int nivel)
{
    String codigo_="";
   switch(nivel)
    {    
        case 2:  
           codigo_=codigo.substring(indexI, indexF);
           codigo_=codigo_+"000";
      break;
        case 4:
                     codigo_=codigo.substring(indexI, indexF);
                     codigo_=codigo_+"00";
        break;
          case 6: codigo_=codigo.substring(indexI, indexF);
          break;
           case 11: codigo_=codigo.substring(indexI, indexF);
           
           
    }
    try
    {
    return (Puc)EntityManagerGeneric.em.createQuery("select cp from ConPuc cp where cp.codigo =:cod order by cp.id")
                                                   .setParameter("cod", codigo_)
                                                    .setMaxResults(1)
                                                   .getSingleResult();
    }catch(Exception e)
    {
        return null;
    }
}
/*public void trasladolibrosauxiliares(ConComprobanteContabilidad conComprobanteContabilidad) throws ParseException
{
    /*
      - Buscar el ultimo registro de la cuenta del mes, si no encuentra, entonces se busca el ultimo
        registro del mes anterior para saber el tipo de saldo debito o credito y agregarlo al 
        saldo del mes del nuevo ciclo contable y si no es porque se inicio hasta ahora la contabilidad
        insertando como nuevo saldo el movimiento actual
    */
    
    /* En la cuentas 1.activo ,5.gastos y 6.costos por tener saldo debito la columna de saldos aumenta 
    con los movimientos registrados en el debe y disminuye con los valores registrados en el haber.
    En la cuentas de 2.pasivo, 3.patrimonio e 4.ingresos, por tener saldo crédito su saldo aumenta con los valores registrados
    en el haber y disminuye con los valores registrados en el debe
    */
  /*  Puc cuenta=null;
    String query="";
    CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
    CriteriaQuery<LibroAuxiliar> cq = cb.createQuery(LibroAuxiliar.class);
    Metamodel m = EntityManagerGeneric.em.getMetamodel();
    EntityType<LibroAuxiliar> _conlibroauxiliar = m.entity(LibroAuxiliar.class);
    Root<LibroAuxiliar> pet = cq.from(_conlibroauxiliar);
    Calendar calendar = Calendar.getInstance();
    
      
    SimpleDateFormat df=null;
    df=new SimpleDateFormat("yyyy-MM-dd");
    String fechaelab=df.format(conComprobanteContabilidad.getFechaelaboracion()); 
    LocalDate ld=LocalDate.parse(fechaelab);
    df=new SimpleDateFormat("yyyy-MM");
    String fecha=df.format(conComprobanteContabilidad.getFechaelaboracion());
    String fecha2=df.format(conComprobanteContabilidad.getFechaelaboracion());
    df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date fromDate =df.parse(fecha+"-01 00:00:00");
   
   
   
    Date toDate = df.parse(fecha2+"-"+calculardiasdelmes(ld.getYear(), ld.getMonthValue()-1)+" 23:59:59");
    LibroAuxiliar  conLibroAuxiliar=null;
    LibroAuxiliar conLibroAuxiliarTemp;
    ConLibroDiario conLibroDiario=null; 
 
     df=new SimpleDateFormat("yyyy-MM-dd");
     fecha=df.format(fromDate);
     fecha2=df.format(toDate);
     LocalDate ld1=LocalDate.parse(fecha);
     LocalDate ld2=LocalDate.parse(fecha2);
    for(LibroAuxiliar cd:conComprobanteContabilidad.getDetallesComprobanteContabilidads())
    {
     if(!cd.getParcialdebe().equals(BigDecimal.ZERO) || !cd.getParcialhaber().equals(BigDecimal.ZERO))
     {  
     if(cd.getConPuc().getCodigo().length()==10)
     {
        cuenta=cd.getConPuc().getConpuc_id().getConpuc_id().getConpuc_id();
        
     }
     else
     {
      if(cd.getConPuc().getCodigo().length()==8)
     {
        cuenta=cd.getConPuc().getConpuc_id().getConpuc_id();
      
     }
      else{
           if(cd.getConPuc().getCodigo().length()==6)
     {
        cuenta=cd.getConPuc().getConpuc_id();
      
     }
           else{
           if(cd.getConPuc().getCodigo().length()==4)
           {
             cuenta=cd.getConPuc();
      
        
             }
     
      
     }
    }
}       
  try
  {
   conLibroAuxiliarTemp=(LibroAuxiliar)  EntityManagerGeneric.em.createNamedQuery("findLastLibroAuxiliar")//and "+query+")")
                                            .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                            .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                            .setParameter("cp", cuenta)
                                            .getSingleResult();
    
    conLibroAuxiliar=null;
    conLibroAuxiliar=new LibroAuxiliar();
    conLibroAuxiliar.setDescripcion(cd.getDescripcion());
    
    if(!cd.getParcialdebe().equals(BigDecimal.ZERO))
    {
        conLibroAuxiliar.setDebe(cd.getParcialdebe());
        conLibroAuxiliar.setHaber(BigDecimal.ZERO); 
    }
    else
    {
        conLibroAuxiliar.setHaber(cd.getParcialhaber()); 
        conLibroAuxiliar.setDebe(BigDecimal.ZERO); 
    }
    if(cuenta.getCodigo().substring(0, 1).matches("[0-9]{1}|[0-9]{5}|[0-9]{6}"))
    {
        if(!cd.getParcialdebe().equals(BigDecimal.ZERO))
    {
        conLibroAuxiliar.setSaldo(conLibroAuxiliarTemp.getSaldo().add(conLibroAuxiliar.getDebe()));
    }
    else
    {
        conLibroAuxiliar.setSaldo(conLibroAuxiliarTemp.getSaldo().subtract(conLibroAuxiliar.getHaber()));
    }
    }
    else
    {
       if(cuenta.getCodigo().substring(0, 1).matches("[0-9]{2}|[0-9]{3}|[0-9]{4}"))
    {
       if(cd.getParcialdebe().equals(BigDecimal.ZERO))
    { 
        conLibroAuxiliar.setSaldo(conLibroAuxiliarTemp.getSaldo().subtract(conLibroAuxiliar.getDebe()));
    }
    else
    {
       conLibroAuxiliar.setSaldo(conLibroAuxiliarTemp.getSaldo().add(conLibroAuxiliar.getHaber()));
    }
    }  
    }
    
  }catch(Exception e)
  {
      e.printStackTrace();
     try{ 
    df=new SimpleDateFormat("yyyy");
    fromDate=df.parse(fecha+" -"+String.valueOf(ld.getMonthValue())+"-01 00:00:00");
    toDate=df.parse(fecha+" -"+String.valueOf(ld.getMonthValue())+calculardiasdelmes(ld.getYear(), ld.getMonthValue()-1)+" 23:59:59");
 
        conLibroAuxiliarTemp=(LibroAuxiliar)  EntityManagerGeneric.em.createNamedQuery("findLastLibroAuxiliar")//and "+query+")")
                                            .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                            .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                            .setParameter("cp", cuenta)
                                            .getSingleResult();
  
      conLibroAuxiliar=null;
      conLibroAuxiliar=new LibroAuxiliar();
     conLibroAuxiliar.setDescripcion("Saldo Anterior.........");
      if(!cd.getParcialdebe().equals(BigDecimal.ZERO))
    {
        conLibroAuxiliar.setDebe(conLibroAuxiliarTemp.getSaldo());
        conLibroAuxiliar.setHaber(BigDecimal.ZERO); 
    }
    else
    {
        conLibroAuxiliar.setHaber(conLibroAuxiliarTemp.getSaldo()); 
        conLibroAuxiliar.setDebe(BigDecimal.ZERO); 
    }
    
        conLibroAuxiliar.setSaldo(conLibroAuxiliarTemp.getSaldo());
   
     
     }catch(Exception e2)
     {
        conLibroAuxiliar=null;
        conLibroAuxiliar=new LibroAuxiliar();
     
       
        conLibroAuxiliar.setDescripcion(cd.getDescripcion()); 
    if(!cd.getParcialdebe().equals(BigDecimal.ZERO))
    {
        conLibroAuxiliar.setDebe(cd.getParcialdebe());
        conLibroAuxiliar.setHaber(BigDecimal.ZERO); 
        conLibroAuxiliar.setSaldo(cd.getParcialdebe()); 
    }
    else
    {
        conLibroAuxiliar.setHaber(cd.getParcialhaber()); 
        conLibroAuxiliar.setDebe(BigDecimal.ZERO); 
        conLibroAuxiliar.setSaldo(cd.getParcialhaber()); 
    }
      
     }
  }
     conLibroAuxiliar.setConComprobanteContabilidad(conComprobanteContabilidad);
     conLibroAuxiliar.setConPuc(cuenta);
     conLibroAuxiliar.setFechaelaboracion(conComprobanteContabilidad.getFechaelaboracion());
     EntityManagerGeneric.et.begin();
     EntityManagerGeneric.em.persist(conLibroAuxiliar);
     EntityManagerGeneric.et.commit();
     }
     else
     {
       if(!cd.getDebe().equals(BigDecimal.ZERO) || !cd.getHaber().equals(BigDecimal.ZERO))
       {
           conLibroDiario=null;
           conLibroDiario=new ConLibroDiario();
           conLibroDiario.setFechaelaboracion(conComprobanteContabilidad.getFechaelaboracion());
           conLibroDiario.setConComprobanteContabilidad(conComprobanteContabilidad);
           conLibroDiario.setDebe(cd.getDebe());
           conLibroDiario.setHaber(cd.getHaber());
           conLibroDiario.setDescripcion(conComprobanteContabilidad.getTipo()==0?"Comprobante de Apertura":"Comprobante Diario N° "+conComprobanteContabilidad.getConsecutivoComprobanteContabilidad().getId().toString());
           conLibroDiario.setConPuc(cd.getConPuc());
           EntityManagerGeneric.et.begin();
           EntityManagerGeneric.em.persist(conLibroDiario);
           EntityManagerGeneric.et.commit();
           
       }
     }
 }
                                            
}*/
private int calculardiasdelmes(int year, int mes)
{
 switch(mes){
            case 0:  // Enero
            case 2:  // Marzo
            case 4:  // Mayo
            case 6:  // Julio
            case 7:  // Agosto
            case 9:  // Octubre
            case 11: // Diciembre
                return 31;
            case 3:  // Abril
            case 5:  // Junio
            case 8:  // Septiembre
            case 10: // Noviembre
                return 30;
            case 1:  // Febrero
                if ( ((year%100 == 0) && (year%400 == 0)) ||
                        ((year%100 != 0) && (year%  4 == 0))   )
                    return 29;  // Año Bisiesto
                else
                    return 28;
            default:
                throw new java.lang.IllegalArgumentException(
                "El mes debe estar entre 0 y 11");
        }
}

public List<LibroAuxiliar> findComprobanteContabilidad(int tipo,Date datefind) throws ParseException
{
    
    Date fromdate=formatfecha(0, datefind);
    Date todate=formatfecha(1, datefind);
    return EntityManagerGeneric.em.createQuery("select cc from ConDetallesComprobanteContabilidad cc where cc.conComprobanteContabilidad.tipo=:tip and cc.conComprobanteContabilidad.fechaelaboracion BETWEEN :m1 and :m2 order by cc.conPuc.codigo,cc.id   ")
                                                                                   .setParameter("m1", fromdate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", todate,TemporalType.TIMESTAMP)
                                                                                    .setParameter("tip", tipo)
                                                                                   .getResultList();
  
    
   
    
}
public Date formatfecha(int tipo,Date fecha) throws ParseException
{
    SimpleDateFormat df=null;
    df=new SimpleDateFormat("yyyy-MM-dd");
    String fechaelab=df.format(fecha); 
    LocalDate ld=LocalDate.parse(fechaelab);
    df=new SimpleDateFormat("yyyy-MM");
    String sfecha=df.format(fecha);
    String sfecha2=df.format(fecha);
    df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(tipo==0)
    {
    Date fromDate =df.parse(sfecha+"-01 00:00:00");
    return fromDate;
    }
   else
    {
       System.out.println("Mes->"+ ld.getMonthValue());
       Date toDate = df.parse(sfecha2+"-"+calculardiasdelmes(ld.getYear(), ld.getMonthValue()-1)+" 23:59:59");
       return toDate;
    } 
}
public List<Puc> li_conpuc(String search)
{
    String searchpattern=search==null?"%":search+"%";
    return EntityManagerGeneric.em.createQuery("select cp FROM ConPuc cp where (lower(cp.nombre) like :search or lower(cp.codigo) like :search) and (cp.deshabilitar=false or cp.deshabilitar is null) order by cp.codigo ")
                                               .setParameter("search", searchpattern)
                                               .getResultList();
}

public Puc findConPucNombre(String nombre)
{
    Puc conPuc;

    try{
       conPuc=(Puc)EntityManagerGeneric.em.createQuery("select cp FROM ConPuc cp where cp.nombre=:nom ")
                                                .setParameter("nom", nombre)
                                                .getSingleResult();
             
   return conPuc; 
}catch(Exception e)
{
    return null;
}
}
}
