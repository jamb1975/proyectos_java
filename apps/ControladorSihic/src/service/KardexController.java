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
import modelo.Factura;
import modelo.FacturaItem;
import modelo.Kardex;
import modelo.Producto;

/**
 *
 * @author karolyani
 */


public class KardexController extends AbstractFacade<Kardex> {
  
    private Kardex kardex;
    private String QUERY_FIND_KARDEX="select i from Kardex i where i.id=:id";
    private String QUERY_ID_LAST_KARDEX="select max(i.id) from Kardex i where i.producto=:p and i.tipo='ep'";
   
    public KardexController() {
        super(Kardex.class);
    }

   
    public void create(Kardex entity) {
       
     
        //calcular_saldos();
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
       
        EntityManagerGeneric.et.begin();
       EntityManagerGeneric.em.persist(entity);
        EntityManagerGeneric.et.commit();
    }

   
    public void edit( Long id, Kardex entity) {
        kardex=entity;
        modify_last();
          EntityManagerGeneric.et.begin();
        EntityManagerGeneric.merge(kardex);
        EntityManagerGeneric.et.commit();
        
    }

  
    public void remove( Long id) {
              EntityManagerGeneric.et.begin();
        super.remove(super.find(id));
           EntityManagerGeneric.et.commit();
    }

   
     public Kardex find( Long id, String tipo) 
     {
        kardex=(Kardex)EntityManagerGeneric.em.createQuery("select i from Kardex i where i.producto.id=:id_prod and i.tipo=:t order by i.id desc")
	                         .setParameter("id_prod",id) 
                                 .setParameter("t", tipo)
	                         .setMaxResults(1)
	                         .getSingleResult();
        return super.find(id);
    }

   
    public List<Kardex> findAll() {
        return super.findAll();
    }

     public List<Kardex> findRange( String datefrom,  String dateto, Long idprod) throws ParseException {
        List<Kardex> list_kardexprod;/*=em.createQuery("select k from Kardex k "
                                                  + " where month(k.fecha)>=:datefrom and month(k.fecha)<=:dateto and k.producto.id=:idprod")
                                                    .setParameter("datefrom",new Date().getMonth())
                                                    .setParameter("dateto", new Date().getMonth())
                                                    .setParameter("idprod", idprod)
                                                     .getResultList();*/
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<Kardex> cq = cb.createQuery(Kardex.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<Kardex> Kard_ = m.entity(Kardex.class);
Root<Kardex> pet = cq.from(Kard_);
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


 Producto p=new Producto();
 p.setId(idprod);
 Predicate predicate=cb.between(pet.get("fecha"), fromDate,toDate);
cq.where(predicate,cb.and(pet.get("producto").in(p)));
cq.orderBy(cb.asc(pet.get("id")));
TypedQuery<Kardex> q =q=null;
list_kardexprod=null;
   q=EntityManagerGeneric.em.createQuery(cq);
list_kardexprod=q.getResultList();


System.out.println("size"+list_kardexprod.size());
        
        return list_kardexprod;
        
    }

   
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
  public void calcular_saldos(){
	
	
	
	try{
            Kardex kard=new Kardex();
	if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.commit();
        }
	if(kardex.getProducto().getId()==null)
        {
            Producto p=kardex.getProducto();
            EntityManagerGeneric.et.begin();
            EntityManagerGeneric.em.persist(p);
            EntityManagerGeneric.et.commit();
            kardex.setProducto(p);
            System.out.println("id-->"+kardex.getProducto().getId());
        }
		try{
	Long i=(Long)EntityManagerGeneric.em.createQuery("select max(i.id) from Kardex i where i.producto=:p")
	                                      .setParameter("p", kardex.getProducto()) 
	                                      .getSingleResult();
	kard=(Kardex)EntityManagerGeneric.em.createQuery("select i from Kardex i where i.id=:id")
	                             .setParameter("id", i)
	                             .getSingleResult();

	      if(kardex.getTipo().equals("ep")){//entrada nueva en compras
		   kardex.setCantidad_saldo(kardex.getCantidad_entrada()+kard.getCantidad_saldo());
		   kardex.setValor_saldo(kardex.getValor_entrada().add(kard.getValor_saldo()));
		   kardex.setValor_unidad(round(kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)));
	      }
              else
              {
	    	if(kardex.getTipo().equals("dc")) 
                {//devolucion en compras
	    		   Long id=(Long)EntityManagerGeneric.em.createQuery(QUERY_ID_LAST_KARDEX)
                                                 .setParameter("p", kardex.getProducto())
                                   .getSingleResult();
                           Kardex kard_last_buy=(Kardex)EntityManagerGeneric.em.createQuery(QUERY_FIND_KARDEX)
                                                  .setParameter("id", id)
                                   .getSingleResult();
                           BigDecimal valor_unidad=round(kard_last_buy.getValor_entrada().divide(BigDecimal.valueOf(kard_last_buy.getCantidad_entrada()),4,RoundingMode.HALF_UP));
                           kardex.setValor_entrada(BigDecimal.valueOf(kardex.getCantidad_entrada()).multiply(valor_unidad));
	    		   kardex.setCantidad_saldo(kard.getCantidad_saldo()-kardex.getCantidad_entrada());
	    		   kardex.setValor_saldo(kard.getValor_saldo().subtract(kardex.getValor_entrada()));
                            if(kardex.getCantidad_saldo()>0)
                           {
	    		    kardex.setValor_unidad(round(kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)));
                           }
                            else
                            {
                                  kardex.setValor_unidad(kard.getValor_unidad());
                            }
               }
                else
              {
	    	if(kardex.getTipo().equals("sp"))  
                {//generar salida
	    		   
	    		   kardex.setCantidad_saldo(kard.getCantidad_saldo()-kardex.getCantidad_salida());
                           kardex.setValor_salida(BigDecimal.valueOf(kardex.getCantidad_salida()).multiply(kard.getValor_unidad()));
	    		   kardex.setValor_saldo(kard.getValor_saldo().subtract(kardex.getValor_salida()));
	    		   kardex.setValor_unidad(round(kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)));
               }
              }
              }
		}catch(Exception e2){
                         	    if(kardex.getTipo().equals("ep"))
            {	
		   kardex.setCantidad_saldo(kardex.getCantidad_entrada());
		   kardex.setValor_saldo(kardex.getValor_entrada());
         	   kardex.setValor_unidad(round(kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)));
	       }
	}
                
  
	}catch(Exception e3){
		
		e3.printStackTrace();
	}
}
  public void devolucion_ventas(){
	
	Factura factura;
	FacturaItem facturaitem;  
	try{	
	
		factura=(Factura)EntityManagerGeneric.em.createQuery("select fi from Factura fi where fi.no_factura=:no")
        .setParameter("no", kardex.getNo_fact_kar())
        .setMaxResults(1)
        .getSingleResult();
 
	facturaitem=(FacturaItem)EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.factura.no_factura=:no and fi.product=:p")
	                         .setParameter("no", factura.getNo_factura())
	                         .setParameter("p", kardex.getProducto())
	                         .getSingleResult();
	facturaitem=EntityManagerGeneric.em.find(FacturaItem.class, facturaitem.getId());
	
	 kardex=new Kardex();
	
	Long i=(Long)EntityManagerGeneric.em.createQuery("select max(id) from Kardex i where i.producto=:p")
	                                      .setParameter("p",kardex.getProducto())
	                                      .getSingleResult();
	Kardex kardex_old=(Kardex)EntityManagerGeneric.em.createQuery("select i from Kardex i where i.id=:id")
	                             .setParameter("id", i)
	                             .getSingleResult();
	
	kardex.setFecha(new Date());
	kardex.setTipo("dv");
	kardex.setValor_salida(kardex_old.getValor_unidad().multiply(BigDecimal.valueOf(kardex.getCantidad_salida())));
	kardex.setCantidad_saldo(kardex_old.getCantidad_saldo()+kardex.getCantidad_salida());
	kardex.setValor_saldo(kardex_old.getValor_saldo().add(kardex.getValor_salida()));
	kardex.setValor_unidad(round((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP))));
	kardex.setNo_fact_kar(factura.getNo_factura());
	
	facturaitem.setQuantity(facturaitem.getQuantity()-kardex.getCantidad_salida());
	facturaitem.setValor_total(facturaitem.getProducto().getPrecio().multiply(BigDecimal.valueOf(facturaitem.getQuantity())));
	facturaitem.setValor_total2(kardex_old.getValor_unidad().multiply(BigDecimal.valueOf(facturaitem.getQuantity())));

	if(facturaitem.getQuantity()<=0)
		EntityManagerGeneric.em.remove(facturaitem);
	else
		facturaitem=EntityManagerGeneric.em.merge(facturaitem);
	
	factura.calculateTotals();
	
	factura=EntityManagerGeneric.em.merge(factura);
	
	}catch(Exception e){
		
	}
}
  public void modify_last(){
	try{
	
	
	List<Kardex> list_kardex=EntityManagerGeneric.em.createQuery("select i from Kardex i where i.producto=:p  order by i.id desc")
	                         .setParameter("p",kardex.getProducto()) 
	                         .setMaxResults(2)
	                         .getResultList();
	
	if(list_kardex.size()==2)
        {
         
            if(kardex==list_kardex.get(0))
            {  
            if(kardex.getTipo().equals("ep"))
            {
                   kardex.setCantidad_saldo(kardex.getCantidad_entrada()+list_kardex.get(1).getCantidad_saldo());
		   kardex.setValor_saldo(kardex.getValor_entrada().add(list_kardex.get(1).getValor_saldo()));
		   kardex.setValor_unidad(round((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP))));
            }
            else
            {
              if(kardex.getTipo().equals("sp"))
            {
                   kardex.setCantidad_saldo(kardex.getCantidad_entrada()+list_kardex.get(1).getCantidad_saldo());
		   kardex.setValor_saldo(kardex.getValor_entrada().add(list_kardex.get(1).getValor_saldo()));
		   kardex.setValor_unidad(round((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP))));
            }  
            }
            }
        }
        else
        {
            if(list_kardex.size()==1)
        {
         
            if(kardex==list_kardex.get(0))
            {
                   kardex.setCantidad_saldo(kardex.getCantidad_entrada());
		   kardex.setValor_saldo(kardex.getValor_entrada());
		   kardex.setValor_unidad(round((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP))));
	     
            }
        }
        }
	}catch(Exception e){
		

	}
}
  public List<Kardex> getRecords()
  {
      return EntityManagerGeneric.em.createQuery("select k from Kardex k where k.id=(select max(k2.id) from Kardex k2 where k2.producto=k.producto group by k2.producto) order by k.producto.nombre")
                                     .getResultList();
  }
  public List<Kardex> getRecords(String search,String datefrom,String dateto) throws ParseException
  {
      String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
      SimpleDateFormat df=null;
      df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date fromdate=df.parse(datefrom+" 00:00:00");
      Date todate=df.parse(dateto+" 23:59:59");
      return EntityManagerGeneric.em.createQuery("select k from Kardex k where (lower(k.producto.nombre) like :search or k.producto.codigobarras like :search) and k.fecha BETWEEN :fd and :td order by k.fecha,k.producto.nombre")
                                               .setParameter("search", searchpattern)
                                               .setParameter("fd",fromdate,TemporalType.TIMESTAMP)
                                               .setParameter("td", todate,TemporalType.TIMESTAMP)
                                               .getResultList();
  }
  public List<Kardex> getRecords(Producto producto) throws ParseException
  {
      return EntityManagerGeneric.em.createQuery("select k from Kardex k where k.producto=:prod")
                                               .setParameter("prod", producto)
                                               .getResultList();
  }

  public Kardex update(Kardex kardex)
  {
      if(EntityManagerGeneric.et.isActive())
      {
          EntityManagerGeneric.et.getRollbackOnly();
      }
      EntityManagerGeneric.et.begin();
      kardex=EntityManagerGeneric.em.merge(kardex);
      EntityManagerGeneric.et.commit();
      return kardex;
  }
  public void delete(Kardex kardex)
  {
     if(EntityManagerGeneric.et.isActive())
      {
          EntityManagerGeneric.et.getRollbackOnly();
      }
     kardex=EntityManagerGeneric.em.find(Kardex.class, kardex.getId());
     EntityManagerGeneric.et.begin();
     EntityManagerGeneric.em.remove(kardex);
     EntityManagerGeneric.et.commit();
  }
private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }
}
