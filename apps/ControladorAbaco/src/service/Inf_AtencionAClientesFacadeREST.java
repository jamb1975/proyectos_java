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
import model.Inf_AtencionAClientes;

/**
 *
 * @author karolyani
 */

public class Inf_AtencionAClientesFacadeREST extends AbstractFacade<Inf_AtencionAClientes> {
   

    public Inf_AtencionAClientesFacadeREST() {
        super(Inf_AtencionAClientes.class);
    }

   
  
    public Inf_AtencionAClientes find( String id) {
        return super.find(id);
    }

  
    public List<Inf_AtencionAClientes> findAll() {
        return super.findAll();
    }

    
    public List<Inf_AtencionAClientes> findRange(String date_from,String date_to) {
       List<Inf_AtencionAClientes> vd=new ArrayList<Inf_AtencionAClientes>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        
        try{
         listResults= EntityManagerGeneric.em.createNativeQuery("select t.no_ident,\n" +
                                          "       t.nombre,\n" +
                                          "       count(f.id) total_atencion,\n" +
                                          "       sum(f.totalamount) valor_ventas\n" +
                                          "    from terceros t,\n" +
                                            "         factura f\n" +
                                            "    where f.id_emp=t.id\n" +
                                            "    and   to_char(f.facturadate,'yyyy-mm-dd')>=?1 and  to_char(f.facturadate,'yyyy-mm-dd')<=?2    \n" +
                                            "\n  and f.deleted=false" +
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

  
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return  EntityManagerGeneric.em;
    }
    
}
