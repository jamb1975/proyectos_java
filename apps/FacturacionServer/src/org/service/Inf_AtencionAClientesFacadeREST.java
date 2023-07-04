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
import model.Inf_AtencionAClientes;

/**
 *
 * @author karolyani
 */
@Stateless
@Path("model.inf_atencionaclientes")
public class Inf_AtencionAClientesFacadeREST extends AbstractFacade<Inf_AtencionAClientes> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;

    public Inf_AtencionAClientesFacadeREST() {
        super(Inf_AtencionAClientes.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Inf_AtencionAClientes entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Inf_AtencionAClientes entity) {
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
    public Inf_AtencionAClientes find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Inf_AtencionAClientes> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{datefrom}/{dateto}")
    @Produces({"application/xml", "application/json"})
    public List<Inf_AtencionAClientes> findRange(@PathParam("datefrom") String date_from, @PathParam("dateto") String date_to) {
       List<Inf_AtencionAClientes> vd=new ArrayList<Inf_AtencionAClientes>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        
        try{
         listResults=em.createNativeQuery("select t.no_ident,\n" +
                                          "       t.nombre,\n" +
                                          "       count(f.id_fac) total_atencion,\n" +
                                          "       sum(f.totalamount) valor_ventas\n" +
                                          "    from terceros t,\n" +
                                            "         factura f\n" +
                                            "    where f.id_emp=t.id_ter\n" +
                                            "    and   to_char(f.facturadate,'yyyy-mm-dd')>=?1 and  to_char(f.facturadate,'yyyy-mm-dd')<=?2    \n" +
                                            "\n" +
                                            "    group by t.no_ident,t.nombre")
                                         .setParameter("1",date_from)
                                         .setParameter("2",date_to)
                                            .getResultList();
         for (Object[] record : listResults) {
             Inf_AtencionAClientes vt=new Inf_AtencionAClientes();
             
             vt.setNo_ident(record[0].toString());
             vt.setNombre(record[1].toString());
             vt.setTotal_atencion(Integer.valueOf(record[2].toString()));
             vt.setValor_ventas(BigDecimal.valueOf(Float.valueOf(record[3].toString())));
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
