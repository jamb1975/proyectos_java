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
import model.Inf_Ventas_Totales;

/**
 *
 * @author SIMboxDEV8
 */


public class Inf_Ventas_Totales_FacadeREST extends AbstractFacade<Inf_Ventas_Totales> {
   
    
  

    public Inf_Ventas_Totales_FacadeREST() {
        super(Inf_Ventas_Totales.class);
    }


    
    public Inf_Ventas_Totales find( String id) {
        return super.find(id);
    }

    
    public List<Inf_Ventas_Totales> findAll(String date_from,String date_to) {
        List<Inf_Ventas_Totales> vd=new ArrayList<Inf_Ventas_Totales>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        System.out.println(date_from);
        try{
         listResults=EntityManagerGeneric.em.createNativeQuery("select nullif(f.credito,false), \n" +
                                        "       sum(fi.quantity), \n" +
                                        "       sum(f.totalamount)total \n" +
                                        "       from Factura f,\n" +
                                        "            FacturaItem fi\n" +
                                        "       where to_char(f.facturadate,'yyyy-mm-dd')>= ?1 and to_char(f.facturadate,'yyyy-mm-dd')<= ?2 \n" +
                                        "        and  fi.factura_id=f.id     \n" +
                                        "       group by nullif(f.credito,false)")
                                         .setParameter("1",date_from)
                                         .setParameter("2",date_to)
                                            .getResultList();
         for (Object[] record : listResults) {
             
             Inf_Ventas_Totales vt=new Inf_Ventas_Totales();
              try{
             vt.setCredito(Boolean.parseBoolean(record[0].toString()));
              } catch(Exception e)
                     {
                         vt.setCredito(false);
                     }
                     
         
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

   
 
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
}
