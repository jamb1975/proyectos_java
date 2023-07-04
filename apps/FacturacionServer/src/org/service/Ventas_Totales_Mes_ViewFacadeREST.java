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
import model.Ventas_Totales_Mes_View;

/**
 *
 * @author SIMboxDEV8
 */

@Stateless
@Path("model.ventas_totales_mes_view")
@Cacheable(false)
public class Ventas_Totales_Mes_ViewFacadeREST extends AbstractFacade<Ventas_Totales_Mes_View> {
    @PersistenceContext(unitName = "FacturacionServerPU")
    private EntityManager em;

    public Ventas_Totales_Mes_ViewFacadeREST() {
        super(Ventas_Totales_Mes_View.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Ventas_Totales_Mes_View entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Ventas_Totales_Mes_View entity) {
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
    public Ventas_Totales_Mes_View find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Ventas_Totales_Mes_View> findAll() {
       List<Ventas_Totales_Mes_View> vd=new ArrayList<Ventas_Totales_Mes_View>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        try{
         listResults=em.createNativeQuery("SELECT \n" +
                                           "    P.CODIGO,\n" +
                                           "    P.NOMBRE,\n" +
                                           "    TO_CHAR(F.FACTURADATE,'MM') AS NUMERO_MES,\n" +
                                           "    TO_CHAR(F.FACTURADATE,'MONTH') AS MES_COMPLETO,\n" +
                                           "    SUM(FI.QUANTITY) AS CANTIDAD_TOTAL_PRODUCTO,\n" +
                                           "    SUM(FI.VALOR_TOTAL) AS VALOR_TOTAL_PRODUCTO\n" +
                                           "   \n" +
                                            "FROM \n" +
                                             "PRODUCTO P,\n" +
                                            "FACTURA F,\n" +
                                            "FACTURAITEM FI\n" +
                                            "\n" +
                                            "WHERE\n" +
                                            "P.ID_PROD=FI.ID_PROD AND\n" +
                                            "F.ID_FAC=FI.ID_FAC\n" +
                                            "\n" +
                                            "GROUP BY P.CODIGO,P.NOMBRE, TO_CHAR(F.FACTURADATE,'MONTH'), TO_CHAR(F.FACTURADATE,'MM') \n" +
                                            "ORDER BY P.NOMBRE,TO_CHAR(F.FACTURADATE,'MM')")
                                           .getResultList();
         for (Object[] record : listResults) {
             Ventas_Totales_Mes_View vd2=new Ventas_Totales_Mes_View();
             vd2.setCodigo(record[0].toString());
             vd2.setNombre(record[1].toString());
             vd2.setNumero_mes(Integer.parseInt(record[2].toString()));
              vd2.setMes_completo(record[3].toString());
                vd2.setCantidad_total_producto(BigDecimal.valueOf(Integer.parseInt(record[4].toString())));
             vd2.setValor_total_producto(BigDecimal.valueOf(Integer.parseInt(record[5].toString())));
           
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
    public List<Ventas_Totales_Mes_View> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
