/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

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
import modelo.EntidadesStatic;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.Kardex;
import modelo.Producto;

/**
 *
 * @author karolyani
 */


public class KardexController extends AbstractFacade<Kardex> {
  
   
    private static String QUERY_FIND_KARDEX="select i from Kardex i where i.id=:id";
    private static String QUERY_ID_LAST_KARDEX="select max(i.id) from Kardex i where i.producto=:p and i.tipo='ep'";
   
    public KardexController() {
        super(Kardex.class);
    }

   
    public static  void servicios_crear() {
       
       
        calcular_saldos();
       EntityManagerGeneric.verificarestadotransaccion();
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(EntidadesStatic.es_kardex);
        EntityManagerGeneric.et.commit();
    }

   
    public void servicios_actualizar( ) {
        
        modify_last();
        EntityManagerGeneric.verificarestadotransaccion();
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.find(Kardex.class,EntidadesStatic.es_kardex.getId());
        EntityManagerGeneric.em.remove(EntidadesStatic.es_kardex);
        EntityManagerGeneric.et.commit();
        
    }

  
    public void servicios_eliminar( Long id) {
              EntityManagerGeneric.et.begin();
        super.remove(super.find(id));
           EntityManagerGeneric.et.commit();
    }

   
     public Kardex find( Long id, String tipo) 
     {
        EntidadesStatic.es_kardex=(Kardex)EntityManagerGeneric.em.createQuery("select i from Kardex i where i.producto.id=:id_prod and i.tipo=:t order by i.id desc")
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
  public static void calcular_saldos(){
	
	
	
	try{
            Kardex kard=new Kardex();
	if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.commit();
        }
	if(EntidadesStatic.es_kardex.getProducto().getId()==null)
        {
            Producto p=EntidadesStatic.es_kardex.getProducto();
            EntityManagerGeneric.et.begin();
            EntityManagerGeneric.em.persist(p);
            EntityManagerGeneric.et.commit();
            EntidadesStatic.es_kardex.setProducto(p);
            System.out.println("id-->"+EntidadesStatic.es_kardex.getProducto().getId());
        }
		try{
	Long i=(Long)EntityManagerGeneric.em.createQuery("select max(i.id) from Kardex i where i.producto=:p")
	                                      .setParameter("p", EntidadesStatic.es_kardex.getProducto()) 
	                                      .getSingleResult();
	kard=(Kardex)EntityManagerGeneric.em.createQuery("select i from Kardex i where i.id=:id")
	                             .setParameter("id", i)
	                             .getSingleResult();

	      if(EntidadesStatic.es_kardex.getTipo().equals("ep")){//entrada nueva en compras
		   EntidadesStatic.es_kardex.setCantidad_saldo(EntidadesStatic.es_kardex.getCantidad_entrada()+kard.getCantidad_saldo());
		   EntidadesStatic.es_kardex.setValor_saldo(EntidadesStatic.es_kardex.getValor_entrada().add(kard.getValor_saldo()));
		   EntidadesStatic.es_kardex.setValor_unidad(round(EntidadesStatic.es_kardex.getValor_saldo().divide(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)));
	      }
              else
              {
	    	if(EntidadesStatic.es_kardex.getTipo().equals("dc")) 
                {//devolucion en compras
	    		   Long id=(Long)EntityManagerGeneric.em.createQuery(QUERY_ID_LAST_KARDEX)
                                                 .setParameter("p", EntidadesStatic.es_kardex.getProducto())
                                   .getSingleResult();
                           Kardex kard_last_buy=(Kardex)EntityManagerGeneric.em.createQuery(QUERY_FIND_KARDEX)
                                                  .setParameter("id", id)
                                   .getSingleResult();
                           BigDecimal valor_unidad=round(kard_last_buy.getValor_entrada().divide(BigDecimal.valueOf(kard_last_buy.getCantidad_entrada()),4,RoundingMode.HALF_UP));
                           EntidadesStatic.es_kardex.setValor_entrada(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_entrada()).multiply(valor_unidad));
	    		   EntidadesStatic.es_kardex.setCantidad_saldo(kard.getCantidad_saldo()-EntidadesStatic.es_kardex.getCantidad_entrada());
	    		   EntidadesStatic.es_kardex.setValor_saldo(kard.getValor_saldo().subtract(EntidadesStatic.es_kardex.getValor_entrada()));
                            if(EntidadesStatic.es_kardex.getCantidad_saldo()>0)
                           {
	    		    EntidadesStatic.es_kardex.setValor_unidad(round(EntidadesStatic.es_kardex.getValor_saldo().divide(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)));
                           }
                            else
                            {
                                  EntidadesStatic.es_kardex.setValor_unidad(kard.getValor_unidad());
                            }
               }
                else
              {
	    	if(EntidadesStatic.es_kardex.getTipo().equals("sp"))  
                {//generar salida
	    		   
	    		   EntidadesStatic.es_kardex.setCantidad_saldo(kard.getCantidad_saldo()-EntidadesStatic.es_kardex.getCantidad_salida());
                           EntidadesStatic.es_kardex.setValor_salida(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_salida()).multiply(kard.getValor_unidad()));
	    		   EntidadesStatic.es_kardex.setValor_saldo(kard.getValor_saldo().subtract(EntidadesStatic.es_kardex.getValor_salida()));
	    		   EntidadesStatic.es_kardex.setValor_unidad(round(EntidadesStatic.es_kardex.getValor_saldo().divide(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)));
               }
              }
              }
		}catch(Exception e2){
                         	    if(EntidadesStatic.es_kardex.getTipo().equals("ep"))
            {	
		   EntidadesStatic.es_kardex.setCantidad_saldo(EntidadesStatic.es_kardex.getCantidad_entrada());
		   EntidadesStatic.es_kardex.setValor_saldo(EntidadesStatic.es_kardex.getValor_entrada());
         	   EntidadesStatic.es_kardex.setValor_unidad(round(EntidadesStatic.es_kardex.getValor_saldo().divide(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)));
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
        .setParameter("no", EntidadesStatic.es_kardex.getNo_fact_kar())
        .setMaxResults(1)
        .getSingleResult();
 
	facturaitem=(FacturaItem)EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.factura.no_factura=:no and fi.product=:p")
	                         .setParameter("no", factura.getNo_factura())
	                         .setParameter("p", EntidadesStatic.es_kardex.getProducto())
	                         .getSingleResult();
	facturaitem=EntityManagerGeneric.em.find(FacturaItem.class, facturaitem.getId());
	
	 EntidadesStatic.es_kardex=new Kardex();
	
	Long i=(Long)EntityManagerGeneric.em.createQuery("select max(id) from Kardex i where i.producto=:p")
	                                      .setParameter("p",EntidadesStatic.es_kardex.getProducto())
	                                      .getSingleResult();
	Kardex kardex_old=(Kardex)EntityManagerGeneric.em.createQuery("select i from Kardex i where i.id=:id")
	                             .setParameter("id", i)
	                             .getSingleResult();
	
	EntidadesStatic.es_kardex.setFecha(new Date());
	EntidadesStatic.es_kardex.setTipo("dv");
	EntidadesStatic.es_kardex.setValor_salida(kardex_old.getValor_unidad().multiply(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_salida())));
	EntidadesStatic.es_kardex.setCantidad_saldo(kardex_old.getCantidad_saldo()+EntidadesStatic.es_kardex.getCantidad_salida());
	EntidadesStatic.es_kardex.setValor_saldo(kardex_old.getValor_saldo().add(EntidadesStatic.es_kardex.getValor_salida()));
	EntidadesStatic.es_kardex.setValor_unidad(round((EntidadesStatic.es_kardex.getValor_saldo().divide(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP))));
	EntidadesStatic.es_kardex.setNo_fact_kar(factura.getNo_factura());
	
	facturaitem.setQuantity(facturaitem.getQuantity()-EntidadesStatic.es_kardex.getCantidad_salida());
	facturaitem.setValor_total(facturaitem.getProduct().getPrecio().multiply(BigDecimal.valueOf(facturaitem.getQuantity())));
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
	                         .setParameter("p",EntidadesStatic.es_kardex.getProducto()) 
	                         .setMaxResults(2)
	                         .getResultList();
	
	if(list_kardex.size()==2)
        {
         
            if(EntidadesStatic.es_kardex==list_kardex.get(0))
            {  
            if(EntidadesStatic.es_kardex.getTipo().equals("ep"))
            {
                   EntidadesStatic.es_kardex.setCantidad_saldo(EntidadesStatic.es_kardex.getCantidad_entrada()+list_kardex.get(1).getCantidad_saldo());
		   EntidadesStatic.es_kardex.setValor_saldo(EntidadesStatic.es_kardex.getValor_entrada().add(list_kardex.get(1).getValor_saldo()));
		   EntidadesStatic.es_kardex.setValor_unidad(round((EntidadesStatic.es_kardex.getValor_saldo().divide(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP))));
            }
            else
            {
              if(EntidadesStatic.es_kardex.getTipo().equals("sp"))
            {
                   EntidadesStatic.es_kardex.setCantidad_saldo(EntidadesStatic.es_kardex.getCantidad_entrada()+list_kardex.get(1).getCantidad_saldo());
		   EntidadesStatic.es_kardex.setValor_saldo(EntidadesStatic.es_kardex.getValor_entrada().add(list_kardex.get(1).getValor_saldo()));
		   EntidadesStatic.es_kardex.setValor_unidad(round((EntidadesStatic.es_kardex.getValor_saldo().divide(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP))));
            }  
            }
            }
        }
        else
        {
            if(list_kardex.size()==1)
        {
         
            if(EntidadesStatic.es_kardex==list_kardex.get(0))
            {
                   EntidadesStatic.es_kardex.setCantidad_saldo(EntidadesStatic.es_kardex.getCantidad_entrada());
		   EntidadesStatic.es_kardex.setValor_saldo(EntidadesStatic.es_kardex.getValor_entrada());
		   EntidadesStatic.es_kardex.setValor_unidad(round((EntidadesStatic.es_kardex.getValor_saldo().divide(BigDecimal.valueOf(EntidadesStatic.es_kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP))));
	     
            }
        }
        }
	}catch(Exception e){
		

	}
}
  public static List<Kardex> getRecords()
  {
       
       
       
      return EntityManagerGeneric.em.createQuery("select distinct k from Kardex k where k.producto.codigobarras  is not null and exists(select max(k2.id) from Kardex k2 where k2.producto=k.producto) group by k having k.id=max(k.id) order by k.id desc")
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
  
  @Override
  public Kardex update(Kardex kardex)
  {
      if(EntityManagerGeneric.et.isActive())
      {
          EntityManagerGeneric.et.getRollbackOnly();
      }
      EntityManagerGeneric.et.begin();
      kardex=super.update(kardex);
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
private static BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }
}
