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
import model.Credito_Clientes_View;

/**
 *
 * @author karol
 */
@Stateless
@Path("model.credito_clientes_view")
@Cacheable(false)
public class Credito_Clientes_ViewFacadeREST extends AbstractFacade<Credito_Clientes_View> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;

    public Credito_Clientes_ViewFacadeREST() {
        super(Credito_Clientes_View.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Credito_Clientes_View entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Credito_Clientes_View entity) {
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
    public Credito_Clientes_View find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Credito_Clientes_View> findAll() {
         List<Credito_Clientes_View> vd=new ArrayList<Credito_Clientes_View>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        try{
         listResults=em.createNativeQuery("SELECT \n" +
                                           "    TO_CHAR(F.FACTURADATE,'YYYY-MM-DD')FECHA,\n" +
                                           "    T.NO_IDENT,\n" +
                                           "    T.NOMBRE,\n" +
                                           "    F.TOTALAMOUNT\n" +
                                           "FROM \n" +
                                           "TERCEROS T,\n" +
                                           "FACTURA F\n" +
                                           "\n" +
                                           "WHERE\n" +
                                           "T.ID_TER=F.ID_CUST  AND\n" +
                                           "F.CREDITO=1 ")
                                  .getResultList();
         for (Object[] record : listResults) {
             Credito_Clientes_View vd2=new Credito_Clientes_View();
             vd2.setFecha(record[0].toString());
             vd2.setNo_ident(record[1].toString());
             vd2.setNombre(record[2].toString());
             vd2.setTotalamount(BigDecimal.valueOf(Integer.parseInt(record[3].toString())));
              vd.add(vd2);
            System.out.println("Pos 1:"+record[1]);
            vd2=null;
        }
        }catch(Exception e)
        {
            e.printStackTrace();
        
        
        }
            return vd;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Credito_Clientes_View> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
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
