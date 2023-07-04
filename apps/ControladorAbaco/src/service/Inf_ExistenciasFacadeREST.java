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
import model.Inf_Existencias;

/**
 *
 * @author karolyani
 */

public class Inf_ExistenciasFacadeREST extends AbstractFacade<Inf_Existencias> {
   

    public Inf_ExistenciasFacadeREST() {
        super(Inf_Existencias.class);
    }

    
    
    
    public List<Inf_Existencias> findAll(String search) {
    String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';

    List<Inf_Existencias> list_existencias=new ArrayList<Inf_Existencias>();
        List<Object[]> listResults=new ArrayList<Object[]>();
        try{
         listResults= EntityManagerGeneric.em.createNativeQuery("select p.codigobarras, \n" +
                                          "       p.nombre, \n" +
                                          "       k.cantidad_saldo, \n" +
                                          "       k.valor_saldo\n" +
                                          "       from producto p,\n" +
                                          "            kardex k\n" +
                                          "            where k.producto_id=p.id\n" +
                                          "            and   k.id=(select max(id) from kardex where producto_id=k.producto_id)\n" +
                                          "            and  (lower(p.nombre) like ?1  "+ 
                                          "            or   lower(p.codigo) like ?2  )"+
                                          "              order by p.nombre " )
                                          .setParameter("1",searchpattern)
                                          .setParameter("2",searchpattern)
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

    

   
    public List<Inf_Existencias> findRange(Integer from,Integer to) {
        return super.findRange(new int[]{from, to});
    }

  
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
}
