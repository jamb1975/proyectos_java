/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import model.Inf_VentasPorProducto;

/**
 *
 * @author karolyani
 */

public class Inf_VentasPorProductoFacadeREST extends AbstractFacade<Inf_VentasPorProducto> {
  
   

    public Inf_VentasPorProductoFacadeREST() {
        super(Inf_VentasPorProducto.class);
    }

  

   
    public Inf_VentasPorProducto find(String id) {
        return super.find(id);
    }

   
    public List<Inf_VentasPorProducto> findAll() {
        return super.findAll();
    }

   
    public List<Inf_VentasPorProducto> findRange(String date_from,String date_to) {
        
        
       List<Inf_VentasPorProducto> vd=new ArrayList<Inf_VentasPorProducto>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        
        try{
         listResults=EntityManagerGeneric.em.createNativeQuery("select  p.id, p.nombre,f.credito,sum(fi.quantity),sum(fi.valor_total),(select k.valor_unidad from kardex k where k.id=(select max(id) from kardex k2 where k2.producto_id=p.id))valor_unitario\n" +
                                          
                                            "		from Factura f,\n" +
                                            "		     Facturaitem fi,\n" +
                                            "		     Producto p\n" +
                                            "\n" +
                                            "		     where f.deleted=false and fi.factura_id=f.id\n" +
                                            "		     and   fi.product_id=p.id\n" +
                                            "		     and to_char(f.facturadate,'yyyy-mm-dd') >=?1\n" +
                                            "		     and to_char(f.facturadate,'yyyy-mm-dd') <=?2\n" +
                                            "       \n" +
                                            "              group by f.credito,p.id,p.nombre")
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
          
             }catch(Exception e)
             {
               
                vt.setValor_unitario(BigDecimal.valueOf(0));  
             }
             vd.add(vt);
            
            vt=null;
        }
        }catch(Exception e)
        {
            
        
        
        }
        
            return vd;
    }

  
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
 
    
}
