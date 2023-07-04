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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.ComprobanteIngreso;
import modelo.ConsecutivosContabilidad;
import modelo.Consignaciones;
import modelo.Factura;
import modelo.NotaCredito;
import modelo.NotaDebito;
import modelo.ReciboCaja;

/**
 *
 * @author adminlinux
 */
public class NotaDebitoController extends AbstractFacade<NotaDebito>{

    public NotaDebitoController(Class<NotaDebito> entityClass) {
        super(entityClass);
    }

    public void create(NotaDebito entity,ConsecutivosContabilidad consecutivosContabilidad)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        consecutivosContabilidad=EntityManagerGeneric.em.find(ConsecutivosContabilidad.class, consecutivosContabilidad.getId(),LockModeType.PESSIMISTIC_WRITE);
        entity.setNo_consecutivo(consecutivosContabilidad.getConsecutivonotadebito()+Long.valueOf(1));
        consecutivosContabilidad.setConsecutivonotadebito(entity.getNo_consecutivo());
       
        EntityManagerGeneric.em.merge(consecutivosContabilidad);
    
         getEntityManager().persist(entity);
         EntityManagerGeneric.et.commit();
    }
  
    @Override
    public NotaDebito update(NotaDebito entity)
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
    
    public List<NotaDebito> getRecords( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<NotaDebito> cq = cb.createQuery(NotaDebito.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<NotaDebito> _notaDebito = m.entity(NotaDebito.class);
        Root<NotaDebito> pet = cq.from(_notaDebito);
      
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
        Expression<String> pbanco= pet.get("banco");
        Expression<String> lower_banco =cb.lower(pbanco);
        Expression<String> pnocuenta= pet.get("nocuenta");
        Expression<String> lower_nocuenta =cb.lower(pnocuenta);
        
     
cq.where(predicate,cb.or(cb.like(lower_banco,searchpattern.toUpperCase()),cb.like(lower_nocuenta,searchpattern.toUpperCase()),cb.equal(pet.get("no_consecutivo"),noconsecutivo)));
TypedQuery<ComprobanteIngreso> q =q=null;

return EntityManagerGeneric.em.createQuery(cq).getResultList();


  
}

public NotaDebito getRecord(Long id) throws ParseException 
{

 return (NotaDebito) EntityManagerGeneric.em.createQuery( "select n from NotaDebito n where n.conComprobanteContabilidad.id=:pid")
                                                   .setParameter("pid", id)
                                                  .setMaxResults(1)
                                                  .getSingleResult();
        
     
}     
    
}
