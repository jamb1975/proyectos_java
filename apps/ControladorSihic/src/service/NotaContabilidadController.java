/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.ConsecutivosContabilidad;
import modelo.Nomina;
import modelo.NotaContabilidad;

/**
 *
 * @author adminlinux
 */
public class NotaContabilidadController extends AbstractFacade<NotaContabilidad>{

    public NotaContabilidadController(Class<NotaContabilidad> entityClass) {
        super(entityClass);
    }

    public void create(NotaContabilidad entity,ConsecutivosContabilidad consecutivosContabilidad)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        consecutivosContabilidad=EntityManagerGeneric.em.find(ConsecutivosContabilidad.class, consecutivosContabilidad.getId(),LockModeType.PESSIMISTIC_WRITE);
        entity.setNo_consecutivo(consecutivosContabilidad.getConsecutivonotacontabilidad()+Long.valueOf(1));
        consecutivosContabilidad.setConsecutivonotacontabilidad(entity.getNo_consecutivo());
        EntityManagerGeneric.em.merge(consecutivosContabilidad);
        getEntityManager().persist(entity);
        EntityManagerGeneric.et.commit();
    }
  
    @Override
    public NotaContabilidad update(NotaContabilidad entity)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
         super.edit(entity);
         EntityManagerGeneric.et.commit();
      return entity;   
    }
    
    
    @Override
    protected EntityManager getEntityManager() {
        
        return EntityManagerGeneric.em;
    }
    
    public List<NotaContabilidad> getRecords( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<NotaContabilidad> cq = cb.createQuery(NotaContabilidad.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<NotaContabilidad> _notacontabilidad = m.entity(NotaContabilidad.class);
        Root<NotaContabilidad> pet = cq.from(_notacontabilidad);
      
Calendar calendar = Calendar.getInstance();

SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");
Long noconsecutivo=new Long("0");
try
{
    noconsecutivo=Long.valueOf(search);
}
catch(Exception e)
{
    noconsecutivo=Long.valueOf("0");
}
Predicate predicate=cb.between(pet.get("fechaelaboracion"), fromDate,toDate);
        Expression<String> pdetalle= pet.get("detalle");
        Expression<String> lower_detalle =cb.lower(pdetalle);
        Expression<String> pobservaciones= pet.get("observaciones");
       Expression<String> lower_observaciones =cb.lower(pobservaciones);
        cq.where(predicate,cb.or(cb.like(lower_detalle,searchpattern.toUpperCase()),cb.like(lower_observaciones,searchpattern.toUpperCase()),cb.equal(pet.get("no_consecutivo"),noconsecutivo)));

return EntityManagerGeneric.em.createQuery(cq).getResultList();


  
}
public NotaContabilidad getRecord(Nomina nomina)
{
    try{
    return (NotaContabilidad)EntityManagerGeneric.em.createQuery("select nc from NotaContabilidad nc where nc.nomina=:n")
                                   .setParameter("n", nomina)
                                   .setMaxResults(1)
                                   .getSingleResult();
    }catch(Exception e)
    {
        return null;
    }

}
 public NotaContabilidad getRecord(Long id) throws ParseException 
{

                    return (NotaContabilidad) EntityManagerGeneric.em.createQuery( "select n from NotaContabilidad n where n.conComprobanteContabilidad.id=:pid")
                                                   .setParameter("pid", id)
                                                  .setMaxResults(1)
                                                  .getSingleResult();
        
     
}   
    
}
