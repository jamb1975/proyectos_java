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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.AgnCitas;
import modelo.Auditoria;
import modelo.ConComprobanteProcedimiento;
import modelo.ConsecutivoComprobanteProcedimiento;
import modelo.Factura;
import modelo.FacturaItem;

import modelo.ConsecutivosFacturasPorSucursal;
import modelo.GenPacientes ;
import modelo.GenTiposUsuarios;
import modelo.HclConsultas;
import modelo.NotasEstudio;
import modelo.RptFacturasDTO;


/**
 *
 * @author karolyani
 */
public class FacturaController extends AbstractFacade<Factura>{
   private ConsecutivoNoFacturaPorSucursalController consecutivoNoFacturaPorSucursalController=new ConsecutivoNoFacturaPorSucursalController();
   public static     List<FacturaItem> facturasitems=new ArrayList<>();
   private static List<Long> rows = null;
   private int i=0;
   public FacturaController(Class<Factura> entityClass) {
        super(entityClass);
    }
    
    
   
    public boolean editfactura(Factura factura,ConsecutivosFacturasPorSucursal consecutivosFacturasPorSucursal) 
    {
      boolean procesocorrecto=true;
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
         
                 
                     
        
        
     System.out.println("Verificar si factura es nueva");
      if(factura.getId()==null)
      {
          System.out.println("Es nueva");
          String p=factura.getPrefijo().substring(0,1);
          if(EntityManagerGeneric.et.isActive())
          {
              EntityManagerGeneric.et.rollback();
          }
          try
                  {
          switch(p)
          {
              
              case "A": 
                   EntityManagerGeneric.et.begin();
                   consecutivosFacturasPorSucursal=EntityManagerGeneric.em.find(ConsecutivosFacturasPorSucursal.class, consecutivosFacturasPorSucursal.getId(),LockModeType.PESSIMISTIC_WRITE);
                   factura.setNo_factura(consecutivosFacturasPorSucursal.getConsecutivonofacturamultiusuario()+Long.valueOf(1));
                   consecutivosFacturasPorSucursal.setConsecutivonofacturamultiusuario(consecutivosFacturasPorSucursal.getConsecutivonofacturamultiusuario()+Long.valueOf(1));
                  try
                  {
                   EntityManagerGeneric.em.persist(factura);
                   EntityManagerGeneric.em.merge(consecutivosFacturasPorSucursal);
                  
                   EntityManagerGeneric.et.commit();
                  }catch(Exception e)
                  {
                      if(EntityManagerGeneric.et.isActive())
                      {
                          EntityManagerGeneric.et.rollback();
                      }
                      EntityManagerGeneric.et.begin();
                      Long no_factura=(Long)EntityManagerGeneric.em.createQuery("select max(f.no_factura)  from Factura f where SUBSTRING(f.prefijo, 1, 1)='A'")
                                                                         .setMaxResults(1)
                                                                         .getSingleResult();
                        factura.setNo_factura(no_factura+Long.valueOf(1));
                        consecutivosFacturasPorSucursal.setConsecutivonofacturamultiusuario(no_factura+Long.valueOf(1));
                        EntityManagerGeneric.em.persist(factura);
                        EntityManagerGeneric.em.merge(consecutivosFacturasPorSucursal);
                        EntityManagerGeneric.et.commit();
                        
                  }
                  
                  break;
             case "E": 
                 try{
            EntityManagerGeneric.et.begin();
                   consecutivosFacturasPorSucursal=EntityManagerGeneric.em.find(ConsecutivosFacturasPorSucursal.class, consecutivosFacturasPorSucursal.getId(),LockModeType.PESSIMISTIC_WRITE);
                   factura.setNo_factura(consecutivosFacturasPorSucursal.getConsecutivonofacturaparticular()+Long.valueOf(1));
                   consecutivosFacturasPorSucursal.setConsecutivonofacturaparticular(consecutivosFacturasPorSucursal.getConsecutivonofacturaparticular()+Long.valueOf(1));
                   EntityManagerGeneric.em.persist(factura);
                   EntityManagerGeneric.em.merge(consecutivosFacturasPorSucursal);
                   EntityManagerGeneric.et.commit();
                    break;
                 }  
                 catch(Exception e)
                 {
                
                      if(EntityManagerGeneric.et.isActive())
                      {
                          EntityManagerGeneric.et.rollback();
                      }
                      EntityManagerGeneric.et.begin();
                      Long no_factura=(Long)EntityManagerGeneric.em.createQuery("select max(f.no_factura)  from Factura f where SUBSTRING(f.prefijo, 1, 1)='E'")
                                                                         .setMaxResults(1)
                                                                         .getSingleResult();
                        factura.setNo_factura(no_factura+Long.valueOf(1));
                        consecutivosFacturasPorSucursal.setConsecutivonofacturaparticular(no_factura+Long.valueOf(1));
                        EntityManagerGeneric.em.persist(factura);
                        EntityManagerGeneric.em.merge(consecutivosFacturasPorSucursal);
                        EntityManagerGeneric.et.commit();
                    }
             case "P": 
                 System.out.println("es P");
                 try
                 {
                   EntityManagerGeneric.et.begin();
                   System.out.println("buscando consecutivos de facturas");
                   consecutivosFacturasPorSucursal=EntityManagerGeneric.em.find(ConsecutivosFacturasPorSucursal.class, consecutivosFacturasPorSucursal.getId(),LockModeType.PESSIMISTIC_WRITE);
                   System.out.println("colocando nuevo nuemro consecutivo en factura");
                   factura.setNo_factura(consecutivosFacturasPorSucursal.getConsecutivonofacturaindividual()+Long.valueOf(1));
                   System.out.println("colocando de numero consecutivo en consecutivos");
                   consecutivosFacturasPorSucursal.setConsecutivonofacturaindividual(consecutivosFacturasPorSucursal.getConsecutivonofacturaindividual()+Long.valueOf(1));
                   System.out.println("guardando factura");
                   EntityManagerGeneric.em.persist(factura);
                   EntityManagerGeneric.em.merge(consecutivosFacturasPorSucursal);
                  
                   EntityManagerGeneric.et.commit();
                 }catch(Exception e)
                 {
                     System.out.println("antes de crear Numero consecutivo prefijo P");
                      if(EntityManagerGeneric.et.isActive())
                      {
                          EntityManagerGeneric.et.rollback();
                      }
                      EntityManagerGeneric.et.begin();
                      Long no_factura=(Long)EntityManagerGeneric.em.createQuery("select max(f.no_factura)  from Factura f where SUBSTRING(f.prefijo, 1, 1)='P'")
                                                                         .setMaxResults(1)
                                                                         .getSingleResult();
                      System.out.println("Crando Numero consecutivo prefijo P");
                        factura.setNo_factura(no_factura+Long.valueOf(1));
                        consecutivosFacturasPorSucursal.setConsecutivonofacturaindividual(no_factura+Long.valueOf(1));
                        EntityManagerGeneric.em.persist(factura);
                        EntityManagerGeneric.em.merge(consecutivosFacturasPorSucursal);
                        EntityManagerGeneric.et.commit();
                  
                 }
                 break;    
              case "T":
                  try
                  {
                   EntityManagerGeneric.et.begin();
                   consecutivosFacturasPorSucursal=EntityManagerGeneric.em.find(ConsecutivosFacturasPorSucursal.class, consecutivosFacturasPorSucursal.getId(),LockModeType.PESSIMISTIC_FORCE_INCREMENT);
                   factura.setNo_factura(consecutivosFacturasPorSucursal.getConsecutivonofacturasoat()+Long.valueOf(1));
                   consecutivosFacturasPorSucursal.setConsecutivonofacturasoat(consecutivosFacturasPorSucursal.getConsecutivonofacturasoat()+Long.valueOf(1));
                   EntityManagerGeneric.em.persist(factura);
                   EntityManagerGeneric.em.merge(consecutivosFacturasPorSucursal);
                   EntityManagerGeneric.et.commit();
                      }  
                 catch(Exception e)
                 {
                     if(EntityManagerGeneric.et.isActive())
                      {
                          EntityManagerGeneric.et.rollback();
                      }
                      EntityManagerGeneric.et.begin();
                      Long no_factura=(Long)EntityManagerGeneric.em.createQuery("select max(f.no_factura)  from Factura f where SUBSTRING(f.prefijo, 1, 1)='T'")
                                                                         .setMaxResults(1)
                                                                         .getSingleResult();
                        factura.setNo_factura(no_factura+Long.valueOf(1));
                        consecutivosFacturasPorSucursal.setConsecutivonofacturasoat(no_factura+Long.valueOf(1));
                        EntityManagerGeneric.em.persist(factura);
                        EntityManagerGeneric.em.merge(consecutivosFacturasPorSucursal);
                        EntityManagerGeneric.et.commit();
                  
                   
                 }
                 break;  
                 
                 case "S": 
                 System.out.println("es S");
                 try
                 {
                   EntityManagerGeneric.et.begin();
                   System.out.println("buscando consecutivos de facturas");
                   consecutivosFacturasPorSucursal=EntityManagerGeneric.em.find(ConsecutivosFacturasPorSucursal.class, consecutivosFacturasPorSucursal.getId(),LockModeType.PESSIMISTIC_WRITE);
                   System.out.println("colocando nuevo nuemro consecutivo en factura");
                   factura.setNo_factura(consecutivosFacturasPorSucursal.getConsecutivonofacturaindividualsjg()+Long.valueOf(1));
                   System.out.println("colocando de numero consecutivo en consecutivos");
                   consecutivosFacturasPorSucursal.setConsecutivonofacturaindividualsjg(consecutivosFacturasPorSucursal.getConsecutivonofacturaindividualsjg()+Long.valueOf(1));
                   System.out.println("guardando factura");
                   EntityManagerGeneric.em.persist(factura);
                   EntityManagerGeneric.em.merge(consecutivosFacturasPorSucursal);
                  
                   EntityManagerGeneric.et.commit();
                 }catch(Exception e)
                 {
                     e.printStackTrace();
                     System.out.println("antes de crear Numero consecutivo prefijo P");
                      if(EntityManagerGeneric.et.isActive())
                      {
                          EntityManagerGeneric.et.rollback();
                      }
                      EntityManagerGeneric.et.begin();
                      Long no_factura=(Long)EntityManagerGeneric.em.createQuery("select max(f.no_factura)  from Factura f where SUBSTRING(f.prefijo, 1, 1)='S'")
                                                                         .setMaxResults(1)
                                                                         .getSingleResult();
                        System.out.println("Crando Numero consecutivo prefijo S");
                        factura.setNo_factura(no_factura+Long.valueOf(1));
                        consecutivosFacturasPorSucursal.setConsecutivonofacturaindividualsjg(no_factura+Long.valueOf(1));
                        EntityManagerGeneric.em.persist(factura);
                        EntityManagerGeneric.em.merge(consecutivosFacturasPorSucursal);
                        EntityManagerGeneric.et.commit();
                  
                 }
                 break;
          
          }
        
        }catch(Exception e)
                  {
                      e.printStackTrace();
                     procesocorrecto=false;
                     
                  }
         
        
            
          
      }
      else
      { try{
        EntityManagerGeneric.et.begin();
        factura=super.edit(factura);
        EntityManagerGeneric.et.commit();
         }catch(Exception e)
        {
         procesocorrecto=false;
        
        }
       
       
        }  
       
        
      return procesocorrecto;
    }
 public Factura guardarFactura(Factura factura) 
 {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }       
      EntityManagerGeneric.et.begin();
     if(factura.getId()!=null)
     {   
      factura=super.edit(factura);
       factura.setFechaModificacion(new Date());
     }
     
   
      EntityManagerGeneric.et.commit();
      
        return factura;
 }   
public List<Factura> getFacturasACredito( String search)
{
        String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
     
  return EntityManagerGeneric.em.createQuery("select f from Factura f where (lower(f.customer.nombre) like :search " 
                                             + "  or lower(f.customer.no_ident)like :search) "
                                             + "  and f.credito=true " 
                                             + "  and f.totalAmount > f.valor_abonos "
                                             + "  order by f.facturaDate" )
                                             .setParameter("search", searchpattern)
                                            .getResultList();
}


public Factura FindLastFacturaEps(AgnCitas agnCitas,String prefijo )
{
    Factura f=new Factura();
    
try
{
   
   f=(Factura)getEntityManager().createQuery("select f from Factura f where f.genTiposUsuarios=:tu and f.genEapb=:ge and  f.facturacerrada=false and f.prefijo=:pref")
                             .setParameter("ge",agnCitas.getGenPacientes().getGenEapb())
                             .setParameter("tu", agnCitas.getGenPacientes().getGenTiposUsuarios())
                             .setParameter("pref",prefijo )
                              .setMaxResults(1)
                             .getSingleResult(); 
    return f;
}catch(Exception e)
{
    return null;
  //  factura.setGenTiposUsuarios(genTiposUsuarios);
    //factura=edit(factura);
}
 
}
public void saveComprobante(ConComprobanteProcedimiento conComprobanteProcedimiento)
{
   
     if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }
    ConsecutivoComprobanteProcedimiento consecutivoComprobanteProcedimiento=new ConsecutivoComprobanteProcedimiento();
    EntityManagerGeneric.et.begin();
    if(conComprobanteProcedimiento.getId()==null)
    {
    EntityManagerGeneric.em.persist(consecutivoComprobanteProcedimiento);
    conComprobanteProcedimiento.setConsecutivoComprobanteProcedimiento(consecutivoComprobanteProcedimiento);
    EntityManagerGeneric.em.persist(conComprobanteProcedimiento);
    }
    else
    {
        if(conComprobanteProcedimiento.getConsecutivoComprobanteProcedimiento()==null)
        {
            EntityManagerGeneric.em.persist(consecutivoComprobanteProcedimiento);
           conComprobanteProcedimiento.setConsecutivoComprobanteProcedimiento(consecutivoComprobanteProcedimiento);
        }
          EntityManagerGeneric.em.merge(conComprobanteProcedimiento);
    }
     EntityManagerGeneric.et.commit();
}

    @Override
    protected EntityManager getEntityManager() 
    {
      return EntityManagerGeneric.em; 
    }
   private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
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
       Date toDate = df.parse(sfecha2+"-"+calculardiasdelmes(ld.getYear(), ld.getMonthValue()-1)+" 23:59:59");
       return toDate;
    } 
}

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
public List<Factura> facturasacerrar(String datefrom,  String dateto, String search,boolean estado) throws ParseException
{
String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");
    return EntityManagerGeneric.em.createQuery("select f from Factura f where f.facturaDate BETWEEN :m1 and :m2 and (lower(f.genEapb.nit) like :search or lower(f.genEapb.nombre) like :search) and (f.prefijo='A' or f.prefijo='AA' or f.prefijo='AV' or f.prefijo='AG') and f.facturacerrada=:estado")
                                                                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("search", searchpattern)
                                                                                   .setParameter("estado", estado)
                                                                                   .getResultList();
   
}
public List<Factura> facturasacerradasrips(String datefrom,  String dateto, String search,String prefijo,String nit,GenTiposUsuarios genTiposUsuarios) throws ParseException
{
String searchpattern = nit==null ? "%" :  '%'+ nit.toLowerCase().replace('*', '%') + '%';
SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");
String condicioneapb="";
if(prefijo.substring(0, 1).equals("T"))
{
  
  return EntityManagerGeneric.em.createQuery("select  f from Factura f where f.facturaDate BETWEEN :m1 and :m2 and f.prefijo like :prefijo% ")
                                                                                   .setParameter("m1",fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("prefijo", prefijo)
                                                                                   .getResultList();
  
}
else
{
    List<Long> rows = null;
    List<Factura> facturas = new ArrayList<Factura>();
    Query query;
    condicioneapb=" f.geneapb_id=g.id and g.nit  like ?1";

    query= EntityManagerGeneric.em.createNativeQuery("select  f.id from Factura f ,gen_eapb g, gen_tipos_usuarios tu where "+condicioneapb+ " and to_char(f.facturadate,'yyyy-mm-dd') >=?2 and to_char(f.facturadate,'yyyy-mm-dd') <=?3 and f.prefijo like ?4 and f.gen_tipos_usuarios_id=?5 and tu.id=f.gen_tipos_usuarios_id  order by f.no_factura, f.facturadate")//(select distinct fi.factura.id from FacturaItem fi where fi.genPacientes.genTiposUsuarios=:tu and fi.factura.id=f.id )   order by f.facturaDate")
                                                                                   .setParameter("2", datefrom)
                                                                                   .setParameter("3", dateto)
                                                                                   .setParameter("4", prefijo+"%")
                                                                                   .setParameter("1", searchpattern)
                                                                                   .setParameter("5", genTiposUsuarios.getId());
   rows = query.getResultList();
        for ( Long row : rows ) {
            facturas.add(EntityManagerGeneric.em.find(Factura.class,row) );
        }
        return facturas;                                                                                
    
}
}
public List<FacturaItem> facturasprefijotipop(String datefrom,  String dateto, String search,String prefijo,String nit,GenTiposUsuarios genTiposUsuarios) throws ParseException
{

facturasitems.clear();
    Query query;
String searchpattern = nit==null ? "%" :  '%'+ nit.toLowerCase().replace('*', '%') + '%';
SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");
  query= EntityManagerGeneric.em.createNativeQuery("select  fi.id from FacturaItem fi, Factura f ,gen_eapb g, gen_tipos_usuarios tu, concomprobantecontabilidad cc where fi.factura_id=f.id and  to_char(f.facturadate,'yyyy-mm-dd') >=?2 and to_char(f.facturadate,'yyyy-mm-dd') <=?3 and f.prefijo=?4 and f.gen_tipos_usuarios_id=?5 and tu.id=f.gen_tipos_usuarios_id and f.concomprobantecontabilidad_id=cc.id order by f.facturadate")//(select distinct fi.factura.id from FacturaItem fi where fi.genPacientes.genTiposUsuarios=:tu and fi.factura.id=f.id )   order by f.facturaDate")
                                                                                   .setParameter("2", datefrom)
                                                                                   .setParameter("3", dateto)
                                                                                   .setParameter("4", prefijo)
                                                                                  .setParameter("1", searchpattern)
                                                                                   .setParameter("5", genTiposUsuarios.getId());
  
  
   
       
  rows = query.getResultList();
      
         Task  task_1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {  
               for(i=0;i<rows.size();i++)
               {  
                facturasitems.add(EntityManagerGeneric.em.find(FacturaItem.class,rows.get(i)) );
               }
             return null;
            }
          };
         Thread tth = new Thread(task_1);
        tth.setDaemon(true);
       // tth.start();
       facturasitems.add(EntityManagerGeneric.em.find(FacturaItem.class,rows.get(i)) );
        
         
        return facturasitems;
}


public List<Factura> facturasacartera(String datefrom,  String dateto, String search) throws ParseException
{
  if(EntityManagerGeneric.et.isActive())
  {
      EntityManagerGeneric.et.commit();
  }
  List<Factura> li_facturas=null;  
 String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 Date fromDate =df.parse(datefrom+" 00:00:00");
 Date toDate = df.parse(dateto+" 23:59:59");
 li_facturas=EntityManagerGeneric.em.createQuery("select f from Factura f LEFT OUTER JOIN f.genEapb ea LEFT OUTER JOIN f.genPersonas gp  where  (lower(ea.nit) like :search or lower(ea.nombre) like :search or gp.documento like :search)  and(f.aceptada=true  and f.totalAmount > f.valor_abonos ) order by f.facturaDate")
                                                                                   //.setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                                                   //.setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("search", searchpattern)
                                                                                   .getResultList();
   
   return li_facturas;
}
public void cerrarfactura(Factura factura)
{
    if(EntityManagerGeneric.et.isActive())
    {
       EntityManagerGeneric.et.rollback();
    }
    EntityManagerGeneric.et.begin();
    factura.setFacturacerrada(true);
    EntityManagerGeneric.em.merge(factura);
    EntityManagerGeneric.et.commit();
}
public void abrirfactura(Factura factura)
{
    if(EntityManagerGeneric.et.isActive())
    {
       EntityManagerGeneric.et.rollback();
    }
    EntityManagerGeneric.et.begin();
    factura.setFacturacerrada(false);
    EntityManagerGeneric.em.merge(factura);
    EntityManagerGeneric.et.commit();
}
public Factura findfacturapornumeroytipo(Long nofactura,String tipo)
{
    try
    {
    return (Factura)EntityManagerGeneric.em.createQuery("select f from Factura f where f.prefijo=:pf and (f.consecutivoFacturaPrefijoE.id=:nf or f.consecutivoFacturaPrefijoP.id=:nf or f.consecutivoFacturaPrefijoT.id=:nf)")
                                           .setParameter("pf", tipo)
                                           .setParameter("nf", nofactura)
                                           .getSingleResult();
    }
    catch(Exception e)
    {
        
        return null;
    }
    
} 
public List<RptFacturasDTO> facturas(String datefrom,  String dateto, int segunmonto) throws ParseException
{
  
  List<RptFacturasDTO> li_facturas=null;  
 
         
 
 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 Date fromDate =df.parse(datefrom+" 00:00:00");
 Date toDate = df.parse(dateto+" 23:59:59");
 switch(segunmonto)
 {
    case 0:
     li_facturas=EntityManagerGeneric.em.createQuery("select distinct new modelo.RptFacturasDTO (f.facturaDate,'N/A','N/A','N/A','N/A','N/A','N/A','N/A','N/A',f.no_factura,f.totalAmount,f.prefijo,'N/A','N/A') from Factura f  where f.totalAmount<=0", RptFacturasDTO.class)
                                                                                .getResultList();
    break; 
     
    case 1:
         try
        {
     li_facturas=EntityManagerGeneric.em.createQuery("select distinct new modelo.RptFacturasDTO(f.facturaDate,fi.agnCitas.genPacientes.genPersonas.documento,fi.agnCitas.genPacientes.genPersonas.primerNombre,fi.agnCitas.genPacientes.genPersonas.primerApellido,fi.agnCitas.genPacientes.genEapb.nombre,fi.agnCitas.genPacientes.genEapb.nit,fi.agnCitas.genPacientes.genTiposUsuarios.nombre,fi.agnCitas.genPacientes.genPersonas.direccion,fi.agnCitas.genPacientes.genPersonas.telefono,f.no_factura,f.totalAmount,f.prefijo,fi.agnCitas.genPacientes.genEapb.telefono,fi.agnCitas.genPacientes.genEapb.direccion ) from Factura f JOIN FETCH f.facturaItems fi where f.facturaDate BETWEEN :m1 and :m2 and f.totalAmount>0", RptFacturasDTO.class)
                                                                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                    .getResultList();
      }catch(Exception e)
            {
             e.printStackTrace();
            }
    break;
    case 2:
        try
        {
        long ti=System.currentTimeMillis();
        
     li_facturas=EntityManagerGeneric.em.createQuery("select distinct new modelo.RptFacturasDTO(f.facturaDate,fi.agnCitas.genPacientes.genPersonas.documento,fi.agnCitas.genPacientes.genPersonas.primerNombre,fi.agnCitas.genPacientes.genPersonas.primerApellido,ea.nombre,ea.nit,fi.agnCitas.genPacientes.genTiposUsuarios.nombre,fi.agnCitas.genPacientes.genPersonas.direccion,fi.agnCitas.genPacientes.genPersonas.telefono,f.no_factura,f.totalAmount,f.prefijo,ea.telefono,ea.direccion ) from Factura f JOIN FETCH f.facturaItems fi LEFT OUTER JOIN fi.agnCitas.genPacientes.genEapb ea  where f.facturaDate BETWEEN :m1 and :m2  order by fi.factura.prefijo,fi.factura.no_factura", RptFacturasDTO.class)
                                                                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   
              .getResultList();
       
      float tf=ti-System.currentTimeMillis();
    // System.out.println("total tiempo millisegndos->"+tf);
    break;
        }catch(Exception e)
            {
             e.printStackTrace();
            }
    
 }
    
   
   return li_facturas;
}

public List<Factura> facturaselectronicas(String datefrom,  String dateto) throws ParseException
{
  
 
         
 
 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 Date fromDate =df.parse(datefrom+" 00:00:00");
 Date toDate = df.parse(dateto+" 23:59:59");
  return EntityManagerGeneric.em.createQuery("select f from Factura f  where  f.facturaDate BETWEEN :m1 and :m2 order by f.prefijo, f.no_factura")
                                          .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                          .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                           .getResultList();
                                           
 
    
   
  
}
public List<Factura> getRecords(String datefrom,  String dateto,String search,String prefijo) throws ParseException
{
 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 Date fromDate =df.parse(datefrom+" 00:00:00");
 Date toDate = df.parse(dateto+" 23:59:59");
 Long pid=Long.MIN_VALUE;
 
 
try{
     pid=Long.valueOf(search);
    return EntityManagerGeneric.em.createQuery("select f from Factura f where (f.no_factura =:pid)  ",Factura.class)
                                          .setParameter("pid", pid)
                                         
                                          
                                                    
                                  .getResultList();
}
catch(Exception e)
{
   
   
    return EntityManagerGeneric.em.createQuery("select f from Factura f  where  f.prefijo like :ppref and  f.facturaDate BETWEEN :m1 and :m2 order by f.no_factura")
                                          .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                          .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                            .setParameter("ppref", prefijo.substring(0,1)+"%")
                                                    
                                  .getResultList();
}    
    
  
}
public int validarfacturatotalcero(String prefijo)
{
    Long cantreg= (Long)EntityManagerGeneric.em.createQuery("select COUNT(f) from Factura f where f.prefijo= :p and f.totalAmount<=0")
                                 .setParameter("p", prefijo)
                                 .getSingleResult();
    System.out.println("total en cero->"+ cantreg);
 return Integer.valueOf(cantreg.toString());
}
 public List<Factura> getrecorscartera(String search)
     {         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
         Long pid=Long.MIN_VALUE;
         try{
             pid=Long.valueOf(search);
         }
         catch(Exception e)
         {
             pid=Long.MIN_VALUE;
         }
         return EntityManagerGeneric.em.createQuery("select f from Factura f left outer join f.genPersonas pe   where (f.no_factura=:pid or concat(lower(pe.primerNombre),' ',lower(pe.primerApellido)) like :search or pe.documento=:search) and (f.totalAmount>f.valor_abonos)")
                                                                      .setParameter("pid", pid)
                                                                      .setParameter("search", searchpattern)
                                                                     //.setMaxResults(20)
                                                                      .getResultList();
                                       
                                                          
         
     }
 public List<Factura> getrecordsfind(String search)
 {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
         Long pid=Long.MIN_VALUE;
         try{
             pid=Long.valueOf(search);
         }
         catch(Exception e)
         {
             pid=Long.MIN_VALUE;
         }
         return EntityManagerGeneric.em.createQuery("select f from Factura f  where f.no_factura=:pid ")
                                                                      .setParameter("pid", pid)
                                                                     //.setMaxResults(20)
                                                                      .getResultList();
                                       
                                                          
         
     }
    public List<Factura> cartera(String prefijo,String fechadesde,String fechato,String noident, String nofactura) throws ParseException
   {
         String searchpattern = noident==null ? "%" :  '%'+ noident.toLowerCase().replace('*', '%') + '%';
     SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     Date fromDate =df.parse(fechadesde+" 00:00:00");
     Date toDate = df.parse(fechato+" 23:59:59");
     Long pnofactura;
     try{
         pnofactura=Long.valueOf(nofactura);
     }
     catch(Exception e)
     {
        pnofactura=Long.valueOf(0);
     }
        return EntityManagerGeneric.em.createQuery( "select f from Factura f LEFT OUTER JOIN f.genEapb ea LEFT OUTER JOIN f.genPersonas gp  where f.prefijo=:pprefijo and f.facturaDate between :m1 and :m2 and (ea.nit like :psearch or gp.documento like :psearch or f.no_factura=:pnofactura)"+
                                                    "     order by f.facturaDate")
                                                   .setParameter("pprefijo", prefijo)
                                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                   .setParameter("psearch", searchpattern)
                                                   .setParameter("pnofactura", pnofactura)
                                                 .getResultList();  
   }
  public List<Factura> findfacturasportipousuarioyfecha(String datefrom,String dateto,int tipo) throws ParseException
 {
         System.out.println("fromdate->"+datefrom+" totdare->"+dateto);
   SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   Date fromDate =df.parse(datefrom+" 00:00:00");
   Date toDate = df.parse(dateto+" 23:59:59");
   switch(tipo)
   {
       
       case 0://contributivos
           System.out.println("tipo 0->"+tipo);
           List<Factura> li_factura=EntityManagerGeneric.em.createQuery("select f from Factura f  where  f.prefijo in('A','AV','AA','AG','P','PV','PA','PG' ) and f.genTiposUsuarios.codigo='1' and  f.facturaDate BETWEEN :m1 and :m2 order by f.no_factura")
                                          .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                          .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                              
                                          .getResultList();
               System.out.println("size contrib.->"+li_factura.size());
                 
             return li_factura;  
       case 1://subsidiado
            return EntityManagerGeneric.em.createQuery("select f from Factura f  where f.prefijo in('A','AV','AA','AG','P','PV','PA','PG' ) and  f.genTiposUsuarios.codigo='2' and  f.facturaDate BETWEEN :m1 and :m2 order by f.no_factura")
                                          .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                          .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                              
                                          .getResultList();
        case 2://particulares
            return EntityManagerGeneric.em.createQuery("select f from Factura f  where  f.prefijo in('E','EV','EA','EG') and  f.facturaDate BETWEEN :m1 and :m2 order by f.no_factura")
                                          .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                          .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                              
                                          .getResultList();    
        case 3://SOAT
            return EntityManagerGeneric.em.createQuery("select f from Factura f  where  f.prefijo in('T','TV','TA','TG') and  f.facturaDate BETWEEN :m1 and :m2 order by f.no_factura")
                                          .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                          .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                              
                                          .getResultList();
        case 4://OTRO
            return EntityManagerGeneric.em.createQuery("select f from Factura f  where f.prefijo in('A','AV','AA','AG','P','PV','PA','PG' ) and  f.genTiposUsuarios.codigo not in('1','2') and  f.facturaDate BETWEEN :m1 and :m2 order by f.no_factura")
                                          .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                          .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                              
                                          .getResultList();
        default: 
          return new ArrayList<>();
                                                                              
                                         
   }
    
  
}
  
 public List<Factura> findfacturasporprefijoyfecha(String datefrom,String dateto,String pref) throws ParseException
 {
         System.out.println("fromdate->"+datefrom+" totdare->"+dateto);
   SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   Date fromDate =df.parse(datefrom+" 00:00:00");
   Date toDate = df.parse(dateto+" 23:59:59");
  
       
    
           System.out.println("prefijo->"+pref);
           List<Factura> li_factura=EntityManagerGeneric.em.createQuery("select f from Factura f  where  f.prefijo=:pref   and  f.facturaDate BETWEEN :m1 and :m2 order by f.no_factura")
                                          .setParameter("pref", toDate)
                                          .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                          .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                              
                                          .getResultList();
               System.out.println("size contrib.->"+li_factura.size());
                 
             return li_factura;  
   
            }
public static ConsecutivosFacturasPorSucursal deleteinvoice(String prefijo, Long nofactura, Long newnoconsecutivo, Long sucursales_id)
{
    List<Factura> li_facturas= EntityManagerGeneric.em.createQuery("select f from Factura f where f.prefijo= :ppref and f.no_factura >= :pnofac ")
                                                                  .setParameter("ppref", prefijo)
                                                                  .setParameter("pnofac", nofactura)
                                                                   .getResultList();
    ConComprobanteProcedimiento cp=null;
    HclConsultas hc=null;
    AgnCitas ac=null;
    NotasEstudio ne=null;
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }
        
   
    for(Factura f :li_facturas)
    {
        List<FacturaItem>li_facturaitems=f.getFacturaItems();
        Map<Long, ConComprobanteProcedimiento>li_cp=new HashMap<>();
        for(FacturaItem fi :li_facturaitems)
        {
            cp=fi.getConComprobanteProcedimiento();
            li_cp.put(cp.getId(), cp);
            //hc=fi.getHclConsultas();
            //ac=fi.getAgnCitas();
            
            Query querydeletenotas=EntityManagerGeneric.em.createQuery("delete  from NotasEstudio  where facturaItem.id = :pid")
                                                                 .setParameter("pid", fi.getId());
          
            EntityManagerGeneric.et.begin();
            querydeletenotas.executeUpdate();
            EntityManagerGeneric.et.commit();
        }
        Query querydeleteitems=EntityManagerGeneric.em.createQuery("delete  from FacturaItem  where factura.id = :pid")
                                                                 .setParameter("pid", f.getId());
          
            EntityManagerGeneric.et.begin();
            querydeleteitems.executeUpdate();
            EntityManagerGeneric.et.commit();
        for(ConComprobanteProcedimiento c :li_cp.values())
        {
            c=EntityManagerGeneric.em.find(ConComprobanteProcedimiento.class, c.getId());
            EntityManagerGeneric.et.begin();
            EntityManagerGeneric.em.remove(c);
            EntityManagerGeneric.et.commit();
        }
        Factura f2=null;
        f2=new Factura();
        f2=f;
       // f2=EntityManagerGeneric.em.find(Factura.class, f2.getId());
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.remove(f2);
        EntityManagerGeneric.et.commit();
    }
    ConsecutivosFacturasPorSucursal cfc=null;
    try
    {
        cfc =(ConsecutivosFacturasPorSucursal)EntityManagerGeneric.em.createQuery("select cf from ConsecutivosFacturasPorSucursal cf where cf.sucursales.id = :psucid and(cf.prefijoindivudual = :ppref or cf.prefijomultiusuario = :ppref or cf.prefijoparticular = :ppref or cf.prefijosoat = :ppref or cf.prefijoindivudualsjg = :ppref)")
                                                                                .setParameter("psucid", sucursales_id)
                                                                                .setParameter("ppref", prefijo) 
                                                                                .getSingleResult();
        switch(prefijo.substring(0, 1))
        {
            case "P": cfc.setConsecutivonofacturaindividual(newnoconsecutivo);
                      break;
            case "A": cfc.setConsecutivonofacturamultiusuario(newnoconsecutivo);
                      break;
            case "E": cfc.setConsecutivonofacturaparticular(newnoconsecutivo);
                      break;
            case "T": cfc.setConsecutivonofacturasoat(newnoconsecutivo);
                      break;
            case "S": cfc.setConsecutivonofacturaindividualsjg(newnoconsecutivo);
                      break;          
        }
        EntityManagerGeneric.et.begin();
        cfc=EntityManagerGeneric.em.merge(cfc);
        EntityManagerGeneric.et.commit();
        return cfc;
    }
    catch(Exception e)
    {
     return cfc;   
    }    
}

public static void cambiarfechafacturacion(String prefijo, String todasdefecha, String todasconfecha) throws ParseException
{
      SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date todasDeFecha =df.parse(todasdefecha+" 00:00:00");
      Date todasConFecha =df.parse(todasconfecha+" 00:00:00");
  
     List<Factura> li_factura=EntityManagerGeneric.em.createQuery("select f from  Factura f   where f.prefijo = :ppref and f.facturaDate = :tdf")
                                                                  .setParameter("ppref", prefijo) 
                                                                  .setParameter("tdf", todasDeFecha,TemporalType.TIMESTAMP)
                                                                  .getResultList();
          for(Factura f : li_factura)
          {
            f.setFacturaDate(todasConFecha);
            EntityManagerGeneric.et.begin();
              EntityManagerGeneric.em.merge(f);
            EntityManagerGeneric.et.commit();
          }
}
}