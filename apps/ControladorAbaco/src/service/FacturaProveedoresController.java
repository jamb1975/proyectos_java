/*                     
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import model.AbonoProveedores;
import model.ConComprobanteEgreso;
import model.Factura;
import model.FacturaItemProveedores;
import model.FacturaProveedores;
import model.Kardex;
import model.Producto;
import model.Proveedores;



/**
 *
 * @author karolyani
 */
public class FacturaProveedoresController extends AbstractFacade<FacturaProveedores>{
   
     public FacturaProveedoresController(Class<FacturaProveedores> entityClass) {
        super(entityClass);
    }
    
    
    
  
   public FacturaProveedores editFactura(FacturaProveedores facturaProveedores,String lote,LocalDate fechavencimiento) 
   {
        double d_valor=0.00;
        double d_valor_total=0.00;
        double d_total=0.00;
        double d_valor_actual=0.00;
        d_valor_actual=facturaProveedores.getValor().doubleValue();
        System.out.println("valor actual->"+d_valor_actual);
        float cantidad_actual=facturaProveedores.getQuantity();
        BigDecimal valor_actual=facturaProveedores.getValor();
        Long id_producto_actual=facturaProveedores.getId_fi_temp();
        Kardex kardex=new Kardex();
        Producto prod=new Producto();
        double valoru=d_valor_actual/cantidad_actual;
        try{
                     
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
     try
     {
     Proveedores p=(Proveedores) EntityManagerGeneric.em.createQuery("select p from Proveedores p where p.no_ident=:noident")
                                                         .setParameter("noident",facturaProveedores.getProveedores().getNo_ident())
                                                         .getSingleResult();
         p.setCelular(facturaProveedores.getProveedores().getCelular());
         p.setDir1(facturaProveedores.getProveedores().getDir1());
         facturaProveedores.setProveedores(p);
         
     }catch(Exception e)
     {
       
     }
    
     
      Long i;
      Kardex kard;
      String message="";
      try{
            EntityManagerGeneric.et.begin();
            facturaProveedores=super.update(facturaProveedores);
            prod=EntityManagerGeneric.em.find(Producto.class, id_producto_actual);
            kard=(Kardex)EntityManagerGeneric.em.createQuery("select i from Kardex i where i.id=(select max(k.id) from Kardex k where k.producto=:p)")
	                             .setParameter("p", prod)
	                             .getSingleResult();
                           kardex.setTipo(cantidad_actual>0?"ep":"dc");
                           
                           if(cantidad_actual>0)
                           {
                              
                             message=  "Entrada Producto,Factura de Compra factura N° "+facturaProveedores.getNo_factura().toString();
                           }
                           else
                           {
                                
                                 message=  "Devolución Producto,Factura de Compra factura N° "+facturaProveedores.getNo_factura().toString();
                           
                           }
                           if(kard.getCantidad_saldo()<0 && kard.getValor_saldo().compareTo(BigDecimal.ZERO)==0)
                           {
                               kard.setValor_saldo(BigDecimal.valueOf(kard.getCantidad_saldo()).multiply(BigDecimal.valueOf(valoru)));
                               kard.setValor_unidad(BigDecimal.valueOf(d_valor_actual).divide(BigDecimal.valueOf(cantidad_actual)));
                         
                               EntityManagerGeneric.em.merge(kard);
                           }
                           System.out.println("Valor U->"+kard.getValor_unidad()+" valor u factura item->"+d_valor_actual);
                          if(d_valor_actual==0)
                          {
                              d_valor_actual=kard.getValor_unidad().floatValue();
                          }
                          if(d_valor_actual==0)
                          {
                              d_valor_actual=kard.getProducto().getPrecio().subtract((kard.getProducto().getPrecio().multiply(BigDecimal.valueOf(0.10)))).floatValue();
                          }
                           kardex.setId(kard.getId());
                           kardex.setProducto(prod);
                           kardex.setCantidad_entrada(cantidad_actual);
                           kardex.setValor_entrada(BigDecimal.valueOf(0.00));
                           kardex.setValor_entrada(BigDecimal.valueOf(d_valor_actual<0?d_valor_actual*(-1):d_valor_actual));
           		   kardex.setCantidad_saldo(kard.getCantidad_saldo()+kardex.getCantidad_entrada());
                           kardex.setCantidad_entrada(cantidad_actual<0?cantidad_actual*(-1):cantidad_actual);
                           kardex.setDesc_kar(message);
	    		   Instant instant = Instant.from(fechavencimiento.atStartOfDay(ZoneId.systemDefault()));
                           kardex.setFechavencimiento(Date.from(instant));
                           kardex.setLote(lote.trim());
                           d_valor=kard.getValor_saldo().doubleValue();
                           d_valor_total=d_valor_actual;
                           d_total=d_valor+d_valor_total;
                 	   kardex.setValor_saldo(round(BigDecimal.valueOf(d_total)));
                           if(kardex.getValor_saldo().compareTo(BigDecimal.ZERO)==1 && kardex.getCantidad_saldo()>0)
                           {
                          
	    		     kardex.setValor_unidad(kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),RoundingMode.UP));
                           }
                           else
                           {
                               if(cantidad_actual!=0)
                               {
                                kardex.setValor_unidad(BigDecimal.valueOf(d_valor_actual).divide(BigDecimal.valueOf(cantidad_actual),RoundingMode.UP));
                               }
                           }
                           System.out.println("Cant saldo->"+kardex.getCantidad_saldo());
                           if(kardex.getCantidad_saldo()==0.0)
                           {
                               kardex.setValor_saldo(BigDecimal.ZERO);
                           }
                           EntityManagerGeneric.em.merge(kardex);
                           EntityManagerGeneric.et.commit();
                    }catch(Exception e)
                    {
                                        
                           kardex.setTipo("ep");
                           kardex.setProducto(prod);
                           kardex.setCantidad_entrada(cantidad_actual);
                           kardex.setValor_entrada(round(valor_actual));
           		   kardex.setCantidad_saldo(kardex.getCantidad_entrada());
                           d_valor=kardex.getValor_entrada().doubleValue();
                 	   kardex.setValor_saldo(BigDecimal.valueOf(d_valor));
                           kardex.setNo_fact_kar(facturaProveedores.getNo_factura());
                           kardex.setDesc_kar(message);
                           Instant instant = Instant.from(fechavencimiento.atStartOfDay(ZoneId.systemDefault()));
                           kardex.setFechavencimiento(Date.from(instant));
                           kardex.setLote(lote.trim());
	    		   kardex.setValor_unidad(kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo())));
                           EntityManagerGeneric.em.merge(kardex);
                           EntityManagerGeneric.et.commit();
 
       
      }
        }catch(Exception e)
        {
          
         if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
        }
      return facturaProveedores;
    }
public List<FacturaProveedores> getFacturasACredito( String search)
{
        String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
     
  return EntityManagerGeneric.em.createQuery("select f from FacturaProveedores f where (lower(f.proveedores.nombre) like :search " 
                                             + "  or lower(f.proveedores.no_ident)like :search) "
                                             + "  and f.credito=true " 
                                             + "  and f.totalAmount > f.valor_abonos "
                                             + "  order by f.facturaDate" )
                                             .setParameter("search", searchpattern)
                                            .getResultList();
}
    @Override
    protected EntityManager getEntityManager() 
    {
      return EntityManagerGeneric.em; 
    }
    public FacturaProveedores guardarFactura(FacturaProveedores facturaProveedores) 
    {
            //buscar cliente por cedula
            Proveedores proveedores;
        try{
             
              proveedores=(Proveedores)EntityManagerGeneric.em.createQuery("select p from Proveedores p where p.no_ident=:ident")
                                     .setParameter("ident", facturaProveedores.getProveedores().getNo_ident())
                                     .setMaxResults(1)
                                     .getSingleResult();
              proveedores.setDir1(facturaProveedores.getProveedores().getDir1());
              proveedores.setCelular(facturaProveedores.getProveedores().getCelular());
              facturaProveedores.setProveedores(proveedores);
        }catch(Exception e)
        {  //e.printStackTrace();
            proveedores=null;
             
        }
      EntityManagerGeneric.et.begin();
      facturaProveedores=super.update(facturaProveedores);
      EntityManagerGeneric.et.commit();
      
        return facturaProveedores;
    }   
    public FacturaProveedores findFactura(Long NoFactura)
    {
        FacturaProveedores fp;
        try
        {
         fp=(FacturaProveedores)EntityManagerGeneric.em.createQuery("select fp from FacturaProveedores fp where fp.no_factura=:nf")
                                                                         .setParameter("nf", NoFactura)
                                                                         .getSingleResult();
        return fp;
        }catch(Exception e)
        {
            fp=new FacturaProveedores();
            return fp;
        }
    }
    private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }
    
    /*********************************/
     public List<FacturaItemProveedores> findLastItems(String search) {
    String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';

    List<FacturaItemProveedores> list_ItemsmateriaPrima=new ArrayList<FacturaItemProveedores>();
       
        try{
         list_ItemsmateriaPrima= EntityManagerGeneric.em.createQuery("select fip from FacturaItemProveedores fip "+
                                                               "where  fip.facturaProveedores.proveedores.no_ident=:search  "
                                                               + " and fip.id=(select max(fip2.id) from FacturaItemProveedores fip2 where fip2.facturaProveedores.proveedores.no_ident=:search and fip2.product=fip.product)"
                                                                + " and fip.product.esmateriaprima=true "+
                                                              "order by fip.product.nombre")
                                                             .setParameter("search",search)
                                                             .getResultList();
         
        }catch(Exception e)
        {
            e.printStackTrace();
        
        
        }
            return list_ItemsmateriaPrima;
    }
public void save_ItemMateriaPrima(FacturaItemProveedores fip)
{
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.merge(fip);
    EntityManagerGeneric.et.commit();
}
public List<FacturaItemProveedores> getItemsProveedor( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 List<FacturaItemProveedores> list_facturaItem;
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<FacturaItemProveedores> cq = cb.createQuery(FacturaItemProveedores.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<FacturaItemProveedores> _facturaItem = m.entity(FacturaItemProveedores.class);
Root<FacturaItemProveedores> pet = cq.from(_facturaItem);
Join<FacturaItemProveedores, Factura> factura = pet.join("facturaProveedores");

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
Long noFactura=new Long("0");
try
{
    noFactura=Long.valueOf(search);
}
catch(Exception e)
{
    noFactura=Long.valueOf("0");
}
Predicate predicate=cb.between(pet.get("facturaDate"), fromDate,toDate);
cq.where(predicate,cb.or(cb.like(factura.get("proveedores").get("nombre"),searchpattern.toUpperCase()),cb.like(factura.get("proveedores").get("no_ident"),searchpattern.toUpperCase()),cb.equal(factura.get("no_factura"),noFactura)));
TypedQuery<FacturaItemProveedores> q =q=null;
list_facturaItem=null;
   q=EntityManagerGeneric.em.createQuery(cq);
                         
list_facturaItem=q.getResultList();

  return list_facturaItem;  
}
public List<FacturaItemProveedores> getPedidos( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 List<FacturaItemProveedores> list_facturaItem;
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<FacturaItemProveedores> cq = cb.createQuery(FacturaItemProveedores.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<FacturaItemProveedores> _facturaItem = m.entity(FacturaItemProveedores.class);
Root<FacturaItemProveedores> pet = cq.from(_facturaItem);
Join<FacturaItemProveedores, Factura> factura = pet.join("facturaProveedores");

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
Long noFactura=new Long("0");
try
{
    noFactura=Long.valueOf(search);
}
catch(Exception e)
{
    noFactura=Long.valueOf("0");
}
Predicate predicate=cb.between(pet.get("facturaDate"), fromDate,toDate);
cq.where(predicate,cb.and(cb.like(factura.get("proveedores").get("no_ident"),search)),cb.and(cb.isNull(pet.get("facturaItemProveedoresPadre"))));
TypedQuery<FacturaItemProveedores> q =q=null;
list_facturaItem=null;
   q=EntityManagerGeneric.em.createQuery(cq);
                         
list_facturaItem=q.getResultList();

  return list_facturaItem;  
}
 public List<AbonoProveedores> getAbonos( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 List<AbonoProveedores> list_abonos;
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<AbonoProveedores> cq = cb.createQuery(AbonoProveedores.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<AbonoProveedores> _abonos = m.entity(AbonoProveedores.class);
Root<AbonoProveedores> pet = cq.from(_abonos);
Join<AbonoProveedores, Factura> factura = pet.join("facturaProveedores");

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
Long noFactura=new Long("0");
try
{
    noFactura=Long.valueOf(search);
}
catch(Exception e)
{
    noFactura=Long.valueOf("0");
}
Predicate predicate=cb.between(pet.get("facturaDate"), fromDate,toDate);
cq.where(predicate,cb.and(cb.like(factura.get("proveedores").get("no_ident"),search)));
TypedQuery<AbonoProveedores> q =q=null;
list_abonos=null;
   q=EntityManagerGeneric.em.createQuery(cq);
                         
list_abonos=q.getResultList();

  return list_abonos;  
}
 
 
public List<FacturaProveedores> getRecords( String fromdate,  String todate, String search,int formapago) throws ParseException {

 String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
         Long pid;
         SimpleDateFormat df=null;
         df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date datefrom=df.parse(fromdate+" 00:00:00");
         Date dateto=df.parse(todate+" 23:59:59");
      if(formapago <0 || formapago >3)
      {
          formapago=0;
      }
         try{
                pid=Long.valueOf(search.trim());
         }catch(Exception e)
         {
          pid=Long.valueOf(0);
         }
        switch(formapago)
        {
            
            case 0:
                        return EntityManagerGeneric.em.createQuery( "select f from FacturaProveedores f left join fetch f.proveedores as cust "+
                                                  " where f.facturaDate BETWEEN :fd and :td and (cust is null or cust.nombre is null or cust.no_ident is null or  (lower(cust.nombre) like :search "+ 
                                                    " or cust.no_ident like :search) or f.no_factura=:pid)   order by f.no_factura desc")
                                                   .setParameter("search", searchpattern)
                                                   .setParameter("pid", pid)
                                                   .setParameter("fd",datefrom,TemporalType.TIMESTAMP)
                                                   .setParameter("td", dateto,TemporalType.TIMESTAMP)
                                                   
                                                 .getResultList();
        
              case 1:
                       return EntityManagerGeneric.em.createQuery( "select f from FacturaProveedores f left join fetch f.proveedores as cust "+
                                                  " where f.facturaDate BETWEEN :fd and :td and (f.credito=false or f.credito is null) and (cust is null or cust.nombre is null or cust.no_ident is null or  (lower(cust.nombre) like :search "+ 
                                                    " or cust.no_ident like :search) or f.no_factura=:pid)   order by f.no_factura desc")
                                                   .setParameter("search", searchpattern)
                                                   .setParameter("pid", pid)
                                                   .setParameter("fd",datefrom,TemporalType.TIMESTAMP)
                                                   .setParameter("td", dateto,TemporalType.TIMESTAMP)
                                                   
                                                 .getResultList();
             case 2:
                        return EntityManagerGeneric.em.createQuery( "select f from FacturaProveedores f left join fetch f.proveedores as cust "+
                                                  " where f.facturaDate BETWEEN :fd and :td and f.credito=true and (cust is null or cust.nombre is null or cust.no_ident is null or  (lower(cust.nombre) like :search "+ 
                                                    " or cust.no_ident like :search) or f.no_factura=:pid)   order by f.no_factura desc")
                                                   .setParameter("search", searchpattern)
                                                   .setParameter("pid", pid)
                                                   .setParameter("fd",datefrom,TemporalType.TIMESTAMP)
                                                   .setParameter("td", dateto,TemporalType.TIMESTAMP)
                                                   
                                                 .getResultList(); 
             case 3:
                        return EntityManagerGeneric.em.createQuery( "select f from FacturaProveedores f left join fetch f.proveedores as cust "+
                                                  " where f.facturaDate BETWEEN :fd and :td and f.credito=true and (cust is null or cust.nombre is null or cust.no_ident is null or  (lower(cust.nombre) like :search "+ 
                                                    " or cust.no_ident like :search) or f.no_factura=:pid) and f.totalAmount > f.valor_abonos  order by f.no_factura desc")
                                                   .setParameter("search", searchpattern)
                                                   .setParameter("pid", pid)
                                                   .setParameter("fd",datefrom,TemporalType.TIMESTAMP)
                                                   .setParameter("td", dateto,TemporalType.TIMESTAMP)
                                                   
                                                 .getResultList();   
                        

    } 
        return EntityManagerGeneric.em.createQuery( "select f from FacturaProveedores f left join fetch f.proveedores as cust "+
                                                  " where f.facturaDate BETWEEN :fd and :td and (cust is null or cust.nombre is null or cust.no_ident is null or  (lower(cust.nombre) like :search "+ 
                                                    " or cust.no_ident like :search) or f.no_factura=:pid)   order by f.no_factura desc")
                                                   .setParameter("search", searchpattern)
                                                   .setParameter("pid", pid)
                                                   .setParameter("fd",datefrom,TemporalType.TIMESTAMP)
                                                   .setParameter("td", dateto,TemporalType.TIMESTAMP)
                                                   
                                                 .getResultList();

  
}
public List<ConComprobanteEgreso> getFacturasPagos( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 List<FacturaProveedores> list_facturas;
 List<ConComprobanteEgreso> list_pagos;
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<FacturaProveedores> cq = cb.createQuery(FacturaProveedores.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<FacturaProveedores> _Proveedores = m.entity(FacturaProveedores.class);
Root<FacturaProveedores> pet = cq.from(_Proveedores);
Join<FacturaProveedores, Proveedores> proveedor = pet.join("proveedores");

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
Long noFactura=new Long("0");
try
{
    noFactura=Long.valueOf(search);
}
catch(Exception e)
{
    noFactura=Long.valueOf("0");
}
Predicate predicate=cb.between(pet.get("facturaDate"), fromDate,toDate);
cq.where(predicate,cb.or(cb.like(proveedor.get("nombre"),searchpattern),cb.like(proveedor.get("no_ident"),searchpattern)));
TypedQuery<FacturaProveedores> q =q=null;
list_facturas=null;
   q=EntityManagerGeneric.em.createQuery(cq);
                         
list_facturas=q.getResultList();
list_pagos=null;
list_pagos=new ArrayList<ConComprobanteEgreso>();
    ConComprobanteEgreso abonoProveedores=new ConComprobanteEgreso();
for(FacturaProveedores facturaProveedores:list_facturas)
{
    if(!facturaProveedores.getCredito())
    {
        abonoProveedores.setFechaelaboracion(facturaProveedores.getFacturaDate());
        abonoProveedores.setConcepto("Pago de Contado");
        abonoProveedores.setValor(facturaProveedores.getTotalAmount());
        list_pagos.add(abonoProveedores);
    }
    else
    {   
        if(facturaProveedores.getComprobanteegresos().size()>0)
        {
        for(ConComprobanteEgreso ap:facturaProveedores.getComprobanteegresos())
        {
            abonoProveedores.setFechaelaboracion(ap.getFechaelaboracion());
            abonoProveedores.setConcepto("Abono a Crédito");
            abonoProveedores.setValor(ap.getValor());
            
            
        }
        list_pagos.add(abonoProveedores);
        }
        
       
    }
    
        
        abonoProveedores=null;
        abonoProveedores=new ConComprobanteEgreso();
}
  return list_pagos;  
}


public FacturaProveedores SaveAbonoFactura(FacturaProveedores facturaProveedores) 
    {
        
                
                     
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
     
      EntityManagerGeneric.et.begin();
      facturaProveedores=super.update(facturaProveedores);
      EntityManagerGeneric.et.commit();
        
           
      return facturaProveedores;
      
}
  @Override
   public FacturaProveedores update(FacturaProveedores facturaProveedores)
   {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.getRollbackOnly();
       }
       EntityManagerGeneric.et.begin();
       facturaProveedores=EntityManagerGeneric.em.merge(facturaProveedores);
       EntityManagerGeneric.et.commit();
       return facturaProveedores;
   } 
   public void delete(FacturaProveedores facturaProveedores)
   {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.getRollbackOnly();
       }
       EntityManagerGeneric.et.begin();
       facturaProveedores=EntityManagerGeneric.em.find(FacturaProveedores.class,facturaProveedores.getId());
       EntityManagerGeneric.em.remove(facturaProveedores);
       EntityManagerGeneric.et.commit();
       
   } 
    public  void getlastitemfactura(Producto producto,float cantidad)
  {
     
     FacturaItemProveedores facturaItemProveedores=(FacturaItemProveedores)EntityManagerGeneric.em.createQuery("select fi from FacturaItemProveedores fi where fi.id=(select max(fi2.id) from FacturaItemProveedores fi2 where fi2.product.id=:pid)")
                                                                          .setParameter("pid", producto.getId())
                                                                          .setMaxResults(1)
                                                                          .getSingleResult();
     
      facturaItemProveedores.getFacturaProveedores().modifyItem(producto, cantidad+facturaItemProveedores.getQuantity());
      editFactura(facturaItemProveedores.getFacturaProveedores(), "N/A", LocalDate.now());
  }
  public List<FacturaProveedores> deudasproveedores(String search)
   {
            String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
    
        return EntityManagerGeneric.em.createQuery( "select f from FacturaProveedores f "+
                                                  " where (lower(f.proveedores.nombre) like :search or lower(f.proveedores.no_ident)like :search) and f.credito=true "+ 
                                                    "  and f.totalAmount > f.valor_abonos  order by f.facturaDate ")
                                                   .setParameter("search", searchpattern)
                                                 .getResultList();  
   }  
}
