/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import model.Factura;
import model.FacturaItem;
import model.Kardex;
import model.Producto;

/**
 *
 * @author karolyani
 */
@Stateless
@Path("model.kardex")
@Cacheable(false)
public class KardexFacadeREST extends AbstractFacade<Kardex> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;
    private Kardex kardex;
    private String QUERY_FIND_KARDEX="select i from Kardex i where i.id=:id";
    private String QUERY_ID_LAST_KARDEX="select max(i.id) from Kardex i where i.producto=:p and i.tipo='ep'";
   
    public KardexFacadeREST() {
        super(Kardex.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Kardex entity) {
        kardex=entity;
        calcular_saldos();
        super.create(kardex);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Kardex entity) {
        kardex=entity;
        modify_last();
        super.create(kardex);
        
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /*@GET
    @Path("{id}/{tipo}/{nofactura}")
    @Produces({"application/xml", "application/json"})
    public Kardex find(@PathParam("id") Long id, @PathParam("tipo") String tipo) {
        kardex=(Kardex)em.createQuery("select i from Kardex i where i.producto.id=:id_prod and i.tipo=:t order by i.id desc")
	                         .setParameter("id_prod",id) 
                                 .setParameter("t", tipo)
	                         .setMaxResults(1)
	                         .getSingleResult();
        return super.find(id);
    }*/

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Kardex> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{datefrom}/{dateto}/{idprod}")
    @Produces({"application/xml", "application/json"})
    public List<Kardex> findRange(@PathParam("datefrom") String datefrom, @PathParam("dateto") String dateto, @PathParam("idprod") Long idprod) throws ParseException {
        System.out.println("Kardex->"+idprod);
        List<Kardex> list_kardexprod;/*=em.createQuery("select k from Kardex k "
                                                  + " where month(k.fecha)>=:datefrom and month(k.fecha)<=:dateto and k.producto.id=:idprod")
                                                    .setParameter("datefrom",new Date().getMonth())
                                                    .setParameter("dateto", new Date().getMonth())
                                                    .setParameter("idprod", idprod)
                                                     .getResultList();*/
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Kardex> cq = cb.createQuery(Kardex.class);
        Metamodel m = em.getMetamodel();
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
TypedQuery<Kardex> q =q=null;
list_kardexprod=null;
   q=em.createQuery(cq);
list_kardexprod=q.getResultList();


System.out.println("size"+list_kardexprod.size());
        
        return list_kardexprod;
        
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
  public void calcular_saldos(){
	
	Kardex kard=new Kardex();
	
	
	
	try{
		try{
	Long i=(Long)em.createQuery("select max(i.id) from Kardex i where i.producto=:p")
	                                      .setParameter("p", kardex.getProducto()) 
	                                      .getSingleResult();
	kard=(Kardex)em.createQuery("select i from Kardex i where i.id=:id")
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
	    		   Long id=(Long)em.createQuery(QUERY_ID_LAST_KARDEX)
                                                 .setParameter("p", kardex.getProducto())
                                   .getSingleResult();
                           Kardex kard_last_buy=(Kardex)em.createQuery(QUERY_FIND_KARDEX)
                                                  .setParameter("id", id)
                                   .getSingleResult();
                           BigDecimal valor_unidad=kard_last_buy.getValor_entrada().divide(BigDecimal.valueOf(kard_last_buy.getCantidad_entrada()));
                           kardex.setValor_entrada(kardex.getValor_entrada().multiply(valor_unidad));
	    		   kardex.setCantidad_saldo(kard.getCantidad_saldo()-kard.getCantidad_entrada());
	    		   kardex.setValor_saldo(kard.getValor_saldo().subtract(kard.getValor_entrada()));
	    		   kardex.setValor_unidad(BigDecimal.valueOf((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),3)).longValue()));
               }
                else
              {
	    	if(kardex.getTipo().equals("sp"))  
                {//generar salida
	    		   
	    		   kardex.setCantidad_saldo(kard.getCantidad_saldo()-kardex.getCantidad_salida());
                           kardex.setValor_salida(BigDecimal.valueOf(kardex.getCantidad_salida()).multiply(kard.getValor_unidad()));
	    		   kardex.setValor_saldo(kard.getValor_saldo().subtract(kardex.getValor_salida()));
	    		   kardex.setValor_unidad(BigDecimal.valueOf((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),3)).longValue()));
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
		
		
	}
}
  public void devolucion_ventas(){
	
	Factura factura;
	FacturaItem facturaitem;  
	try{	
	
		factura=(Factura)em.createQuery("select fi from Factura fi where fi.no_factura=:no")
        .setParameter("no", kardex.getNo_fact_kar())
        .setMaxResults(1)
        .getSingleResult();
 
	facturaitem=(FacturaItem)em.createQuery("select fi from FacturaItem fi where fi.factura.no_factura=:no and fi.product=:p")
	                         .setParameter("no", factura.getNo_factura())
	                         .setParameter("p", kardex.getProducto())
	                         .getSingleResult();
	facturaitem=em.find(FacturaItem.class, facturaitem.getId());
	
	 kardex=new Kardex();
	
	Long i=(Long)em.createQuery("select max(id) from Kardex i where i.producto=:p")
	                                      .setParameter("p",kardex.getProducto())
	                                      .getSingleResult();
	Kardex kardex_old=(Kardex)em.createQuery("select i from Kardex i where i.id=:id")
	                             .setParameter("id", i)
	                             .getSingleResult();
	
	kardex.setFecha(new Date());
	kardex.setTipo("dv");
	kardex.setValor_salida(kardex_old.getValor_unidad().multiply(BigDecimal.valueOf(kardex.getCantidad_salida())));
	kardex.setCantidad_saldo(kardex_old.getCantidad_saldo()+kardex.getCantidad_salida());
	kardex.setValor_saldo(kardex_old.getValor_saldo().add(kardex.getValor_salida()));
	kardex.setValor_unidad(BigDecimal.valueOf((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),4,RoundingMode.HALF_UP)).longValue()));
	kardex.setNo_fact_kar(factura.getNo_factura());
	
	facturaitem.setQuantity(facturaitem.getQuantity()-kardex.getCantidad_salida());
	facturaitem.setValor_total(facturaitem.getProduct().getPrecio().multiply(BigDecimal.valueOf(facturaitem.getQuantity())));
	facturaitem.setValor_total2(kardex_old.getValor_unidad().multiply(BigDecimal.valueOf(facturaitem.getQuantity())));

	if(facturaitem.getQuantity()<=0)
		em.remove(facturaitem);
	else
		facturaitem=em.merge(facturaitem);
	
	factura.calculateTotals();
	
	factura=em.merge(factura);
	
	}catch(Exception e){
		
	}
}
  public void modify_last(){
	try{
	
	
	List<Kardex> list_kardex=em.createQuery("select i from Kardex i where i.producto=:p  order by i.id desc")
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
		   kardex.setValor_unidad(BigDecimal.valueOf((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),3)).longValue()));
            }
            else
            {
              if(kardex.getTipo().equals("sp"))
            {
                   kardex.setCantidad_saldo(kardex.getCantidad_entrada()+list_kardex.get(1).getCantidad_saldo());
		   kardex.setValor_saldo(kardex.getValor_entrada().add(list_kardex.get(1).getValor_saldo()));
		   kardex.setValor_unidad(BigDecimal.valueOf((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),3)).longValue()));
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
		   kardex.setValor_unidad(BigDecimal.valueOf((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),3)).longValue()));
	     
            }
        }
        }
	}catch(Exception e){
		

	}
}
private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }
}
