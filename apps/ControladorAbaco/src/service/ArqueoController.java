/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import model.Arqueo;

/**
 *
 * @author adminlinux
 */
public class ArqueoController extends AbstractFacade<Arqueo> {

    public ArqueoController(Class<Arqueo> entityClass) {
       
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
    public void create(Arqueo arqueo)
    {
         super.create(arqueo);
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        EntityManagerGeneric.et.begin();
        super.update(arqueo);
        EntityManagerGeneric.et.commit();
    }
    public List<Object[]> totalcontadocredito()
    {
        SimpleDateFormat  df=new SimpleDateFormat("yyyy-MM-dd");
        String datefromto=df.format(new Date());
          
      return  EntityManagerGeneric.em.createNativeQuery( " select credito,(select sum(totalAmount) from Factura"+
                                                  " where to_char(facturadate,'yyyy-mm-dd') >=?1 " +
                                            "		     and to_char(facturadate,'yyyy-mm-dd') <=?2 and (credito=FALSE or credito is null)"+ 
                                                    " ) totalcontado,(select sum(totalAmount-valor_abonos) from Factura"+
                                                  " where  to_char(facturadate,'yyyy-mm-dd') >=?3 " +
                                            "		     and to_char(facturadate,'yyyy-mm-dd') <=?4 and (credito=TRUE  and totalAmount>valor_abonos) "+ 
                                                    " )totalcredito, (select sum(valor) from concomprobanteingreso"+
                                                  " where  to_char(fechaelaboracion,'yyyy-mm-dd') >=?5 " +
                                            "		     and to_char(fechaelaboracion,'yyyy-mm-dd') <=?6 and factura_id is not null"+ 
                                                    " )totalabonos from Factura  group by credito")
                                                   
                                                   .setParameter("1",datefromto)
                                                   .setParameter("2", datefromto)
                                                   .setParameter("3",datefromto)
                                                   .setParameter("4", datefromto)
                                                   .setParameter("5",datefromto)
                                                   .setParameter("6", datefromto)
                                                    
                                                    .getResultList();
      
    }    
public BigDecimal totalrecibossonfactura()
    {
        SimpleDateFormat  df=new SimpleDateFormat("yyyy-MM-dd");
        String datefromto=df.format(new Date());
       try
       {
      BigDecimal total=(BigDecimal)EntityManagerGeneric.em.createNativeQuery( " select sum(valor) from ConComprobanteIngreso"+
                                                  " where to_char(fechaelaboracion,'yyyy-mm-dd') >=?1 " +
                                            "		     and to_char(fechaelaboracion,'yyyy-mm-dd') <=?2  and factura_id is null ")
                                                   .setParameter("1",datefromto)
                                                   .setParameter("2", datefromto)
                                                   .getSingleResult();
      if(total!=null)
      {
          return total;
      }
      else
      {
          total=BigDecimal.ZERO;
          return total;
      }
       }catch(Exception e)
       {
           return BigDecimal.ZERO;
       }
      
    }   
public Arqueo getlastrecord()    
{
    try
    {
    return (Arqueo)getEntityManager().createQuery("select a from Arqueo a where a.id=(select max(a2.id) from Arqueo a2)")
                             .getSingleResult();
    }catch(Exception e)
    {
        return null;
    }
}
    
}
