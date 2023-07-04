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

import modelo.Factura;
import modelo.FacturaItemProveedores;
import modelo.FacturaProveedores;
import modelo.GenCategoriasProductos;
import modelo.Kardex;
import modelo.Producto;
import modelo.Proveedores;


/**
 *
 * @author karolyani
 */
public class FacturaProveedoresController extends AbstractFacade<FacturaProveedores>{
   
     public FacturaProveedoresController(Class<FacturaProveedores> entityClass) {
        super(entityClass);
    }
    
    
    
     
   //@PersistenceContext
    public FacturaProveedores editFactura(FacturaProveedores facturaProveedores,String lote,LocalDate fechavencimiento) 
    {
        double d_valor=0.00;
        double d_valor_total=0.00;
        double d_total=0.00;
        double d_valor_actual=0.00;
        d_valor_actual=facturaProveedores.getValor().doubleValue();
        float cantidad_actual=facturaProveedores.getQuantity();
        BigDecimal valor_actual=facturaProveedores.getValor();
        Long id_producto_actual=facturaProveedores.getId_fi_temp();
         Kardex kardex=new Kardex();
         Producto prod=new Producto();
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
      EntityManagerGeneric.et.begin();
      facturaProveedores=super.edit(facturaProveedores);
      EntityManagerGeneric.et.commit();
        
           
      
      
     
      Long i;
      
        Kardex kard;
        
       
       try{
           prod=EntityManagerGeneric.em.find(Producto.class, id_producto_actual);
           if(prod.getGenCategoriasProductos().getCodigo()==1)
           {
           i=(Long)EntityManagerGeneric.em.createQuery("select max(i.id) from Kardex i where i.producto=:p")
	                                      .setParameter("p", prod) 
	                                      .getSingleResult();
           kard=(Kardex)EntityManagerGeneric.em.createQuery("select i from Kardex i where i.id=:id")
	                             .setParameter("id", i)
	                             .getSingleResult();
           }
           else
           {
                i=(Long)EntityManagerGeneric.em.createQuery("select max(i.id) from Kardex i where i.producto=:p and i.lote=:l")
	                                      .setParameter("p", prod)
                                              .setParameter("l", lote.trim())
	                                      .getSingleResult();
                kard=(Kardex)EntityManagerGeneric.em.createQuery("select i from Kardex i where i.id=:id")
	                             .setParameter("id", i)
	                             .getSingleResult();
           }
                           kardex.setTipo(cantidad_actual>0?"ep":"dc");
                           kardex.setProducto(prod);
                           kardex.setCantidad_entrada(cantidad_actual);
                           kardex.setValor_entrada(BigDecimal.valueOf(0.00));
                           kardex.setValor_entrada(BigDecimal.valueOf(d_valor_actual<0?d_valor_actual*(-1):d_valor_actual));
           		   kardex.setCantidad_saldo(kard.getCantidad_saldo()+kardex.getCantidad_entrada());
                           kardex.setCantidad_entrada(cantidad_actual<0?cantidad_actual*(-1):cantidad_actual);
                           kardex.setDesc_kar("Modificar cantidad pedida: "+facturaProveedores.getProveedores().getNombre());
	    		   Instant instant = Instant.from(fechavencimiento.atStartOfDay(ZoneId.systemDefault()));
                           kardex.setFechavencimiento(Date.from(instant));
                           kardex.setLote(lote.trim());
                           d_valor=kard.getValor_saldo().doubleValue();
                           d_valor_total=d_valor_actual;
                           d_total=d_valor+d_valor_total;
                 	   kardex.setValor_saldo(round(BigDecimal.valueOf(d_total)));
                           if(kardex.getValor_saldo()==BigDecimal.ZERO || kardex.getCantidad_saldo()==0)
                           {
                                kardex.setValor_unidad(BigDecimal.ZERO);
                          
                           }
                           else
                           {
	    		   kardex.setValor_unidad(round(kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)));
                           }
                           EntityManagerGeneric.et.begin();
                           EntityManagerGeneric.em.persist(kardex);
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
                           kardex.setDesc_kar("Compra a Proveedor: "+facturaProveedores.getProveedores().getNombre());
                           Instant instant = Instant.from(fechavencimiento.atStartOfDay(ZoneId.systemDefault()));
                           kardex.setFechavencimiento(Date.from(instant));
                           kardex.setLote(lote.trim());
	    		   kardex.setValor_unidad(round(kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)));
                            EntityManagerGeneric.et.begin();
                           EntityManagerGeneric.em.persist(kardex);
                            EntityManagerGeneric.et.commit();
 
       
      }
        }catch(Exception e)
        {
          
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
      super.create(facturaProveedores);
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
                                                               + " and fip.id=(select max(fip2.id) from FacturaItemProveedores fip2 where fip2.facturaProveedores.proveedores.no_ident=:search and fip2.producto=fip.producto)"
                                                                + " and fip.producto.esmateriaprima=true "+
                                                              "order by fip.producto.nombre")
                                                             .setParameter("search",search)
                                                             .getResultList();
         
        }catch(Exception e)
        {
           
        
        
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

 
 
public List<FacturaProveedores> getFacturasProveedores( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 List<FacturaProveedores> list_facturas;
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

  return list_facturas;  
}


public FacturaProveedores SaveAbonoFactura(FacturaProveedores facturaProveedores) 
    {
        
                
                     
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
     
      EntityManagerGeneric.et.begin();
      facturaProveedores=super.edit(facturaProveedores);
      EntityManagerGeneric.et.commit();
        
           
      return facturaProveedores;
      
}
public List<FacturaProveedores> getRecords( String fromdate,  String todate, String search,GenCategoriasProductos genCategoriasProductos) throws ParseException {

 String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
 Long id=Long.valueOf("0");
 try{
     id=Long.valueOf(search);
 }catch(Exception e)
 {
   id=Long.valueOf("0");  
 }
         SimpleDateFormat df=null;
         df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date datefrom=df.parse(fromdate+" 00:00:00");
       df=null;
         df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date dateto=df.parse(todate+" 23:59:59");
                        return EntityManagerGeneric.em.createQuery( "select f from FacturaProveedores f left join fetch f.proveedores as cust "+
                                                  " where f.facturaDate BETWEEN :fd and :td and (cust is null or cust.nombre is null or cust.no_ident is null or  (lower(cust.nombre) like :search "+ 
                                                    " or cust.no_ident like :search)) and f.id=(select distinct(fi.facturaProveedores.id) from FacturaItemProveedores fi where fi.producto.genCategoriasProductos=:cat and fi.facturaProveedores.id=f.id)  order by f.no_factura desc")
                                                   .setParameter("search", searchpattern)
                                                   .setParameter("cat", genCategoriasProductos)
                                                   .setParameter("fd",datefrom,TemporalType.TIMESTAMP)
                                                   .setParameter("td", dateto,TemporalType.TIMESTAMP)
                                                 
                                                 .getResultList();
        
     
}
public FacturaProveedores getRecord(Long id) throws ParseException 
{

                    return (FacturaProveedores) EntityManagerGeneric.em.createQuery( "select f from FacturaProveedores f where f.conComprobanteContabilidad.id=:pid")
                                                   .setParameter("pid", id)
                                                  .setMaxResults(1)
                                                  .getSingleResult();
        
     
}
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
   public List<FacturaProveedores> getRecorsdCartera( String search)
{
     
     String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
    
        return EntityManagerGeneric.em.createQuery( "select f from FacturaProveedores f "+
                                                  " where (lower(f.proveedores.nombre) like :search or lower(f.proveedores.no_ident)like :search) "+ 
                                                    "  and f.totalAmount > f.valor_egresos  order by f.facturaDate ")
                                                   .setParameter("search", searchpattern)
                                                 .getResultList();  
}
}
