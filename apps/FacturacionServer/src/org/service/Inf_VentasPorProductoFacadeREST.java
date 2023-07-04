/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import model.Inf_VentasPorProducto;

/**
 *
 * @author karolyani
 */
@Stateless
@Path("model.inf_ventasporproducto")
public class Inf_VentasPorProductoFacadeREST extends AbstractFacade<Inf_VentasPorProducto> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;

    public Inf_VentasPorProductoFacadeREST() {
        super(Inf_VentasPorProducto.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Inf_VentasPorProducto entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Inf_VentasPorProducto entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Inf_VentasPorProducto find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Inf_VentasPorProducto> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{datefrom}/{dateto}")
    @Produces({"application/xml", "application/json"})
    public List<Inf_VentasPorProducto> findRange(@PathParam("datefrom") String date_from, @PathParam("dateto") String date_to) {
        
      List<Inf_VentasPorProducto> vd=new ArrayList<Inf_VentasPorProducto>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        
        try{
         listResults=em.createNativeQuery("select  p.ID_PROD, p.nombre,f.credito,sum(fi.quantity),sum(fi.valor_total),(select k.valor_unidad from kardex k where k.id=(select max(id) from kardex k2 where k2.ID_PROD=p.ID_PROD))valor_unitario\n" +
                                          
                                            "		from Factura f,\n" +
                                            "		     Facturaitem fi,\n" +
                                            "		     Producto p" +
                                            "\n" +
                                            "		     where fi.ID_FAC=f.ID_FAC\n" +
                                            "		     and   fi.ID_PROD=p.ID_PROD\n" +
                                            "		     and to_char(f.facturadate,'yyyy-mm-dd') >=?1\n" +
                                            "		     and to_char(f.facturadate,'yyyy-mm-dd') <=?2\n" +
                                            "       \n" +
                                            "              group by f.credito,p.ID_PROD,p.nombre")
                                         .setParameter("1",date_from)
                                         .setParameter("2",date_to)
                                        
                                         
                                            .getResultList();
         for (Object[] record : listResults) {
             Inf_VentasPorProducto vt=new Inf_VentasPorProducto();
             vt.setId(Long.valueOf(record[0].toString()));
             vt.setNombre(record[1].toString());
             vt.setCredito(Boolean.valueOf(record[2].toString()));
          
             try{
             vt.setCantidad_total(Float.valueOf(record[3].toString()));
             }catch(Exception e)
             {
                 vt.setCantidad_total(0);
             }
            try{
             vt.setValor_total(BigDecimal.valueOf(Float.valueOf(record[4].toString())));
             }catch(Exception e)
             {
                vt.setValor_total(BigDecimal.valueOf(0));  
             }
             try{
             vt.setValor_unitario(BigDecimal.valueOf(Float.valueOf(record[5].toString())));
             System.out.println("vunit->"+record[5]);
             }catch(Exception e)
             {
                e.printStackTrace();
                vt.setValor_unitario(BigDecimal.valueOf(0));  
             }
             vd.add(vt);
            
            vt=null;
        }
        }catch(Exception e)
        {
            
        
        
        }
        System.out.println("size-->"+vd.size()+date_from+date_to);
            return vd;
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
    
}
