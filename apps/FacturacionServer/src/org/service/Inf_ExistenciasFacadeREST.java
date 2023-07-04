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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import model.Inf_Existencias;

/**
 *
 * @author karolyani
 */
@Stateless
@Path("model.inf_existencias")
public class Inf_ExistenciasFacadeREST extends AbstractFacade<Inf_Existencias> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;

    public Inf_ExistenciasFacadeREST() {
        super(Inf_Existencias.class);
    }

    
    
     @GET
    @Produces({"application/xml", "application/json"})
    public List<Inf_Existencias> findAll() {
      List<Inf_Existencias> list_existencias=new ArrayList<Inf_Existencias>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        try{
         listResults=em.createNativeQuery("select p.codigo, \n" +
                                          "       p.nombre, \n" +
                                          "       k.cantidad_saldo, \n" +
                                          "       k.valor_saldo\n" +
                                          "       from producto p,\n" +
                                          "            kardex k\n" +
                                          "            where k.id_prod=p.id_prod\n" +
                                          "            and   k.id=(select max(id) from kardex where id_prod=k.id_prod)\n" +
                                          "            order by p.nombre ")
                                            //.setParameter("df",date_from)
                                           // .setParameter("dt",date_to)
                                            .getResultList();
         for (Object[] record : listResults) {
             Inf_Existencias ie=new Inf_Existencias();
             
             ie.setCodigo(record[0].toString());
             ie.setNombre(record[1].toString());
             ie.setCantidad_saldo(Float.valueOf(record[2].toString()));
             ie.setValor_saldo(BigDecimal.valueOf(Float.valueOf(record[3].toString())));
             list_existencias.add(ie);
            
            ie=null;
        }
        }catch(Exception e)
        {
            e.printStackTrace();
        
        
        }
            return list_existencias;
    }

    

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Inf_Existencias> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
