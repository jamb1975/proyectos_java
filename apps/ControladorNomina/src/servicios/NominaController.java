/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.LockModeType;
import javax.persistence.TemporalType;
import modelo.ConsecutivosContabilidad;
import modelo.Nomina;


/**
 *
 * @author adminlinux
 */
public class NominaController {
   
    public void create(Nomina nomina,ConsecutivosContabilidad consecutivosContabilidad)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        consecutivosContabilidad=EntityManagerGeneric.em.find(ConsecutivosContabilidad.class, consecutivosContabilidad.getId(),LockModeType.PESSIMISTIC_WRITE);
        nomina.setNo_consecutivo(consecutivosContabilidad.getConsecutivonomina()+Long.valueOf(1));
        consecutivosContabilidad.setConsecutivonomina(nomina.getNo_consecutivo());
        EntityManagerGeneric.em.merge(consecutivosContabilidad);
        EntityManagerGeneric.em.persist(nomina);
        EntityManagerGeneric.et.commit();
    }
    public void update(Nomina nomina)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
  
        EntityManagerGeneric.em.merge(nomina);
        EntityManagerGeneric.et.commit();
    }
    public void delete(Nomina nomina)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.remove(nomina);
        EntityManagerGeneric.et.commit();
    }
    public List<Nomina> getRecords(String datefrom,  String dateto) throws ParseException
    {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate =df.parse(datefrom+" 00:00:00");
        Date toDate = df.parse(dateto+" 23:59:59");
       return EntityManagerGeneric.em.createQuery("select  n from Nomina n where n.periodoinicio BETWEEN :m1 and :m2 order by n.periodoinicio desc")
                                                                                   .setParameter("m1",fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .getResultList();
    }
}
