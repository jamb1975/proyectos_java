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
import javax.persistence.Cacheable;
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
import model.Inf_Ventas_Totales;

/**
 *
 * @author SIMboxDEV8
 */
@Stateless
@Path("model.ventas_totales_diarias_view")
@Cacheable(false)
public class Inf_Ventas_Totales_FacadeREST extends AbstractFacade<Inf_Ventas_Totales> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    
    private EntityManager em;

    public Inf_Ventas_Totales_FacadeREST() {
        super(Inf_Ventas_Totales.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Inf_Ventas_Totales entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Inf_Ventas_Totales entity) {
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
    public Inf_Ventas_Totales find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Path("{datefrom}/{dateto}")
    @Produces({"application/xml", "application/json"})
    public List<Inf_Ventas_Totales> findAll(@PathParam("datefrom")String date_from,@PathParam("dateto")String date_to) {
        List<Inf_Ventas_Totales> vd=new ArrayList<Inf_Ventas_Totales>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        System.out.println(date_from);
        try{
         listResults=em.createNativeQuery("select f.credito, \n" +
                                        "       sum(fi.quantity), \n" +
                                        "       sum(f.totalamount)total \n" +
                                        "       from Factura f,\n" +
                                        "            FacturaItem fi\n" +
                                        "       where to_char(f.facturadate,'yyyy-mm-dd')>= ?1 and to_char(f.facturadate,'yyyy-mm-dd')<= ?2 \n" +
                                        "        and  fi.id_fac=f.id_fac     \n" +
                                        "       group by f.credito")
                                         .setParameter("1",date_from)
                                         .setParameter("2",date_to)
                                            .getResultList();
         for (Object[] record : listResults) {
             Inf_Ventas_Totales vt=new Inf_Ventas_Totales();
             System.out.println(Boolean.parseBoolean(record[0].toString()));
             vt.setCredito(Boolean.parseBoolean(record[0].toString()));
             vt.setCant_total(Float.valueOf(record[1].toString()));
             vt.setValor_total(BigDecimal.valueOf(Float.valueOf(record[2].toString())));
             vd.add(vt);
            
            vt=null;
        }
        }catch(Exception e)
        {
            e.printStackTrace();
        
        
        }
        System.out.println("size-->"+vd.size());
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
