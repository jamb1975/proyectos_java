/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import static isoftconta.servicios.EntityManagerGeneric.em;
import modelo.EntidadesStatic;
import modelo.LibroAuxiliar;
import modelo.Puc;


/**
 *
 * @author adminlinux
 */
 
public class CatalogoCuentasController {
 @PersistenceContext(unitName = "ControladorJaviContaPU")
         
        private static   EntityTransaction et=null;

    
   public static int servicio_crear()
   {   try
      {
             et=em.getTransaction();
          
       verificarestadotransacion();
       et.begin();
       em.persist(EntidadesStatic.es_puc);
       et.commit();
       
       
       return 1;
      }catch(Exception e)
      {
         return -1; 
      }
   }
    
 public static int servicio_actualizar()
   {   try
      {
         
             et=em.getTransaction();
          
       verificarestadotransacion();
       et.begin();
       em.merge(EntidadesStatic.es_puc);
       et.commit();
       
      
       return 1;
      }catch(Exception e)
      {
         return -1; 
      }
   }
 public static int servicio_eliminar()
   {   try
      {
          
        et=em.getTransaction();
        verificarestadotransacion();
       et.begin();
       em.remove(EntidadesStatic.es_puc);
       et.commit();
       
       
       return 1;
      }catch(Exception e)
      {
          e.printStackTrace();
         return -1; 
      }
   }
    
    
    public static Puc getConPuc()
    {
         Puc  conpuc=null;
       
          
          
        try
        {
        
       
            
               conpuc=em.find(Puc.class, Long.valueOf(1));
             
        }catch(Exception e )
        {
            
        }
       return conpuc;                                           
           
    }
     public static List<Puc> getConPucHijos( Puc conPuc)
    {         List<Puc> li_puc= em.createQuery("select cp from Puc cp where cp.conpuc_id=:conpucid")
                                               .setParameter("conpucid", conPuc)
                                               .getResultList();
                  
                   return li_puc;
                               
           
    }  
  
     public static Puc findConPuc(String codigo,int vercuentas)
     {
         Puc puc;
         
         try
         {
             if(vercuentas==0)
             {
                 
             puc= (Puc)em.createQuery("select cp from Puc cp where cp.codigo=:cod and (cp.deshabilitar=false or cp.deshabilitar is null)")
                                                       .setParameter("cod", codigo.trim())
                                                       .getSingleResult();
             
             }
             else
             {
              if(vercuentas==1)
             {
             puc=(Puc)em.createQuery("select cp from Puc cp where cp.codigo=:cod")
                                                       .setParameter("cod", codigo.trim())
                                                       .getSingleResult();
             } 
              else
              {
                  if(vercuentas==2)
             {
             puc= (Puc)em.createQuery("select cp from Puc cp where cp.codigo=:cod ")
                                                       .setParameter("cod", codigo.trim())
                                                       .getSingleResult();
             }
             else
                  {
                     puc=(Puc)em.createQuery("select cp from Puc cp where cp.codigo=:cod ")
                                                       .setParameter("cod", codigo.trim())
                                                       .getSingleResult(); 
                  }
              }
             }
         }
         catch(Exception e)
         {
           puc=null ;
          
       return puc;
         }
          
         return puc;
      
     }
     
public static List<LibroAuxiliar> getLibroAuxiliar( String datefrom,  String dateto, String codigo) throws ParseException {
String searchpattern = codigo==null ? "%":  codigo.toLowerCase()+ "%";
     
 List<LibroAuxiliar> list_libroauxiliar;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LibroAuxiliar> cq = cb.createQuery(LibroAuxiliar.class);
        Metamodel m = em.getMetamodel();
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
q=em.createQuery(cq);
list_libroauxiliar=q.getResultList();

        
return list_libroauxiliar;


 
        
    } 
public static void quitasespacios()
{
   List<Puc> l_puc= em.createQuery("select cp from Puc cp order by cp.id")
                                             .getResultList();  
   et=em.getTransaction();
  Puc padre_puc=null;
   et=em.getTransaction();
  for(Puc line: l_puc)
  {
      line.setCodigo(line.getCodigo().replace(" ",""));
      line.setCodigo(line.getCodigo().trim());
      line.setNombre(line.getNombre().trim());
      et.begin();
      em.merge(line);
      et.commit();
  }
  
}

public static void setIdPadre()
{
    List<Puc> l_puc= EntityManagerGeneric.em.createQuery("select cp from Puc cp order by cp.id")
                                             .getResultList();  
   et=em.getTransaction();
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
     
      et.begin();
      em.merge(line);
      et.commit();
      
  }
      
}
  
}
public static Puc findPadre(String codigo,int indexI, int indexF,int nivel)
{
    String codigo_="";
    Puc puc;
    
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
    puc= (Puc)EntityManagerGeneric.em.createQuery("select cp from Puc cp where cp.codigo =:cod order by cp.id")
                                                   .setParameter("cod", codigo_)
                                                    .setMaxResults(1)
                                                   .getSingleResult();
    }catch(Exception e)
    {
         return null;
    }
     return puc;
}

private static int calculardiasdelmes(int year, int mes)
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

public static List<LibroAuxiliar> findComprobanteContabilidad(int tipo,Date datefind) throws ParseException
{
    
    Date fromdate=formatfecha(0, datefind);
    Date todate=formatfecha(1, datefind);
 
    List<LibroAuxiliar> li_liLibroAuxiliars= em.createQuery("select cc from ConDetallesComprobanteContabilidad cc where cc.conComprobanteContabilidad.tipo=:tip and cc.conComprobanteContabilidad.fechaelaboracion BETWEEN :m1 and :m2 order by cc.conPuc.codigo,cc.id   ")
                                                                                   .setParameter("m1", fromdate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", todate,TemporalType.TIMESTAMP)
                                                                                    .setParameter("tip", tipo)
                                                                                   .getResultList();
    return li_liLibroAuxiliars;
    
   
    
}
public static Date formatfecha(int tipo,Date fecha) throws ParseException
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
public static List<Puc> li_conpuc(String search)
{
        String searchpattern=search==null?"%":search+"%";
         List<Puc> li_conpuc=em.createQuery("select cp FROM Puc cp where (lower(cp.nombre) like :search or lower(cp.codigo) like :search) and (cp.deshabilitar=false or cp.deshabilitar is null) order by cp.codigo ")
                               .setParameter("search", searchpattern)
                               .getResultList();
     return li_conpuc;
}

public Puc findConPucNombre(String nombre)
{
    Puc conPuc;

    try{
       conPuc=(Puc)em.createQuery("select cp FROM Puc cp where cp.nombre=:nom ")
                                                .setParameter("nom", nombre)
                                                .getSingleResult();
   return conPuc; 
}catch(Exception e)
{
    return null;
}
}
private static void verificarestadotransacion()
   {
       if(et.isActive())
       {
           et.rollback();
       }
   }
}
