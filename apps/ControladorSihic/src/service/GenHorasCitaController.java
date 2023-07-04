/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.AgnMedicos;
import modelo.GenHorasCita;

/**
 *
 * @author adminlinux
 */
public class GenHorasCitaController extends AbstractFacade<GenHorasCita>{

    public GenHorasCitaController(Class<GenHorasCita> entityClass) {
        super(entityClass);
    }
public List<GenHorasCita> getGenHorasCita()
{
    List<GenHorasCita> list_genhorascita;
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<GenHorasCita> cq = cb.createQuery(GenHorasCita.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<GenHorasCita> _genhorascita = m.entity(GenHorasCita.class);
        Root<GenHorasCita> pet = cq.from(_genhorascita);
TypedQuery<GenHorasCita> q=null;
list_genhorascita=null;
q=EntityManagerGeneric.em.createQuery(cq);
         
list_genhorascita=q.getResultList();

//System.out.println(EntityManagerGeneric.em.createQuery(cq).toString());
        
        return list_genhorascita;
}
    @Override
    protected EntityManager getEntityManager() {
 return EntityManagerGeneric.em;
    }
 public List<GenHorasCita> getRecords(String search,String fechacita,Long id) throws ParseException
{
    System.out.print("hora->"+search+" id->"+id);
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date fromDate =df.parse(fechacita+" 00:00:00");
    Date toDate = df.parse(fechacita+" 23:59:59");
     int hora;
    try
    {
     hora=Integer.valueOf(search.substring(0,2));
    }catch(Exception e)
    {
      hora=Integer.valueOf(search.substring(0,1));
    }
    return EntityManagerGeneric.em.createQuery("select gh from GenHorasCita gh where gh.hora=:h and  NOT EXISTS (select c from AgnCitas c where c.fechaHora BETWEEN :m1 and :m2 and c.agnMedicos.id=:med and c.genHorasCita=gh )")
                                   .setParameter("h", hora)
                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                   .setParameter("med", id)
                                   .getResultList();
                                   
}   
}
