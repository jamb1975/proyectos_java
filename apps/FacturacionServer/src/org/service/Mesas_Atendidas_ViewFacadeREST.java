/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.service;

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
import model.Mesas_Atendidas_View;

/**
 *
 * @author SIMboxDEV8
 */
@Stateless
@Path("model.mesas_atendidas_view")
@Cacheable(false)
public class Mesas_Atendidas_ViewFacadeREST extends AbstractFacade<Mesas_Atendidas_View> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;

    public Mesas_Atendidas_ViewFacadeREST() {
        super(Mesas_Atendidas_View.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Mesas_Atendidas_View entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Mesas_Atendidas_View entity) {
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
    public Mesas_Atendidas_View find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Mesas_Atendidas_View> findAll() {
            List<Mesas_Atendidas_View> vd=new ArrayList<Mesas_Atendidas_View>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        try{
         listResults=em.createNativeQuery("SELECT \n" +
                                          "    T.NO_IDENT,\n" +
                                          "    T.NOMBRE,\n" +
                                          "    COUNT(F.MT_NOMESA)CANT\n" +
                                          "FROM \n" +
                                          "TERCEROS T,\n" +
                                          "FACTURA F\n" +
                                          "WHERE TO_CHAR(F.FACTURADATE,'YYYY-MM-DD')=TO_CHAR(SYSDATE,'YYYY-MM-DD') AND\n" +
                                          "T.ID_TER=F.ID_EMP\n" +
                                          "GROUP BY T.NO_IDENT,T.NOMBRE")
                                           .getResultList();
         for (Object[] record : listResults) {
             Mesas_Atendidas_View vd2=new Mesas_Atendidas_View();
             vd2.setNo_ident(record[0].toString());
             vd2.setNombre(record[1].toString());
             vd2.setCant(Integer.parseInt(record[2].toString()));
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
    public List<Mesas_Atendidas_View> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
