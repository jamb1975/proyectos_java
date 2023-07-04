/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import model.Factura;
import model.FacturaItem;
import model.Kardex;
import model.ProductoMateriaPrima;
import model.TempMesasFactura;

/**
 * REST Web Service
 *
 * @author karol
 */
@Stateless
@Path("model.facturaitem2")
public  class CheckNoMesasResource extends AbstractFacade<FacturaItem> {
  @PersistenceContext(unitName = "FacturacionServerPU",type = javax.persistence.PersistenceContextType.TRANSACTION)
    private EntityManager em;
private static final String FIND_NOMESA_QUERY = 
            "select "
                + "tm "
                + "from TempMesasFactura tm "
                + "where tm.m_iNoMesa=:nomesa";
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CheckNoMesasResource
     */
    public CheckNoMesasResource() {
        super(FacturaItem.class);
     
    }

    
    @GET
    @Path("{nomesa}")
    @Produces("application/xml")
    public TempMesasFactura getXml(@PathParam("nomesa") String nomesa) {
        Factura factura=new Factura();
        TempMesasFactura tm=new TempMesasFactura();        
        //si estoy facturando por mesas
        if(Integer.parseInt(nomesa)>0)
        {
        try
        {
        Query q = em.createQuery(FIND_NOMESA_QUERY);
        //Parameter<Integer> p1 = q.getParameter("nomesa", Integer.class);
        q.setParameter("nomesa", Integer.parseInt(nomesa));
        tm = (TempMesasFactura)q.getSingleResult();
        }catch(Exception e)
        {
        // e.printStackTrace();
       tm=null;
        }
     if(tm==null)
     {
       factura.setMt_NoMesa(Integer.parseInt((nomesa)));
       Date date=new Date();
       factura.setFacturaDate(date);
       factura=em.merge(factura);
       tm=new TempMesasFactura();
       tm.setFactura(factura);
       tm.setM_iNoMesa(Integer.parseInt((nomesa)));
       tm=em.merge(tm);
           
     }
     //Si estoy realizando una factura desde ventas al mostrador
        }
        else
     {
         Date date=new Date();
         factura.setFacturaDate(date);
         factura=em.merge(factura);
         tm.setFactura(factura);
     }
        return tm;
    }

    
    //Agregamos un producto a la factura
    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
     public void create(FacturaItem entity) {
         Kardex kardex=new Kardex();
        Factura _factura=em.find(Factura.class, entity.getFactura().getId());
      _factura.addProduct(entity.getProduct(), entity.getQuantity(), BigDecimal.ONE, "");
      _factura.calculateTotals();
        try{
       _factura=em.merge(_factura);
        Long i;
        Kardex kard;
       try{
            i=(Long)em.createQuery("select max(i.id) from Kardex i where i.producto=:p")
	                                      .setParameter("p", entity.getProduct()) 
	                                      .getSingleResult();
             kard=(Kardex)em.createQuery("select i from Kardex i where i.id=:id")
	                             .setParameter("id", i)
	                             .getSingleResult();
                           kardex.setTipo("sp");
                           kardex.setProducto(entity.getProduct());
                           kardex.setCantidad_salida(entity.getQuantity());
           		   kardex.setCantidad_saldo(kard.getCantidad_saldo()-kardex.getCantidad_salida());
                           kardex.setValor_salida(BigDecimal.valueOf(kardex.getCantidad_salida()).multiply(kard.getValor_unidad()));
	    		   kardex.setValor_saldo(kard.getValor_saldo().subtract(kardex.getValor_salida()));
	    		   kardex.setValor_unidad(BigDecimal.valueOf((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),3)).longValue()));
             em.persist(kardex);
       }catch(Exception e)
       {
           
       }
       //comenzamos a descargar la cantidad de cada materia primadel producto saliente
        List<ProductoMateriaPrima> list_materiasprimas=em.createQuery("select pmp from ProductoMateriaPrima pmp where pmp.producto.id=:id_prod")
                                                         .setParameter("id_prod", entity.getProduct().getId())
                                                         .getResultList();
        for(ProductoMateriaPrima pmp: list_materiasprimas)
        {
            kardex=null;
            kardex=new Kardex();
       i=(Long)em.createQuery("select max(i.id) from Kardex i where i.producto=:p")
	                                      .setParameter("p", pmp.getMateriaprima()) 
	                                      .getSingleResult();
        kard=(Kardex)em.createQuery("select i from Kardex i where i.id=:id")
	                             .setParameter("id", i)
	                             .getSingleResult();
                           kardex.setTipo("sp");
                           kardex.setProducto(pmp.getMateriaprima());
                           kardex.setCantidad_salida(pmp.getCantidad_necesaria()*entity.getQuantity());
           		   kardex.setCantidad_saldo(kard.getCantidad_saldo()-kardex.getCantidad_salida());
                           kardex.setValor_salida(BigDecimal.valueOf(kardex.getCantidad_salida()).multiply(kard.getValor_unidad()));
	    		   kardex.setValor_saldo(kard.getValor_saldo().subtract(kardex.getValor_salida()));
	    		   kardex.setValor_unidad(BigDecimal.valueOf((kardex.getValor_saldo().divide(BigDecimal.valueOf(kardex.getCantidad_saldo()),3)).longValue()));
                           em.persist(kardex);
        }                           
                
        }catch(Exception e)
        {
            
        }
    }
    /**
     * POST method for creating an instance of CheckNoMesaResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
  
    /**
     * Sub-resource locator method for {name}
     */
    @Path("{name}")
    public CheckNoMesaResource getCheckNoMesaResource(@PathParam("name") String name) {
        return CheckNoMesaResource.getInstance(name);
    }
      @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
  
}
