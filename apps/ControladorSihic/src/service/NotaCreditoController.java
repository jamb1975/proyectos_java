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
import modelo.NotaCredito;

/**
 *
 * @author adminlinux
 */
public class NotaCreditoController extends AbstractFacade<NotaCredito>{

    public NotaCreditoController(Class<NotaCredito> entityClass) {
        super(entityClass);
    }

    public void create(NotaCredito entity, ConsecutivosContabilidad consecutivosContabilidad)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        consecutivosContabilidad=EntityManagerGeneric.em.find(ConsecutivosContabilidad.class, consecutivosContabilidad.getId(),LockModeType.PESSIMISTIC_WRITE);
        entity.setNo_consecutivo(consecutivosContabilidad.getConsecutivonotacredito()+Long.valueOf(1));
        consecutivosContabilidad.setConsecutivonotacredito(entity.getNo_consecutivo());
        EntityManagerGeneric.em.merge(consecutivosContabilidad);
        getEntityManager().persist(entity);
        EntityManagerGeneric.et.commit();
    }
  
    @Override
    public NotaCredito update(NotaCredito entity)
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
    
    public List<NotaCredito> getRecords( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<NotaCredito> cq = cb.createQuery(NotaCredito.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<NotaCredito> _notacredito = m.entity(NotaCredito.class);
        Root<NotaCredito> pet = cq.from(_notacredito);
      
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
        Expression<String> pcliente= pet.get("cliente");
        Expression<String> lower_cliente =cb.lower(pcliente);
        Expression<String> pnoident= pet.get("noident");
        Expression<String> lower_noident =cb.lower(pnoident);
        cq.where(predicate,cb.or(cb.like(lower_banco,searchpattern.toUpperCase()),cb.like(lower_nocuenta,searchpattern.toUpperCase()),cb.like(lower_cliente,searchpattern.toUpperCase()),cb.like(lower_noident,searchpattern.toUpperCase()),cb.equal(pet.get("no_consecutivo"),noconsecutivo)));

return EntityManagerGeneric.em.createQuery(cq).getResultList();


  
}

public NotaCredito getRecord(Long id) throws ParseException 
{

                    return (NotaCredito) EntityManagerGeneric.em.createQuery( "select n from NotaCredito n where n.conComprobanteContabilidad.id=:pid")
                                                   .setParameter("pid", id)
                                                  .setMaxResults(1)
                                                  .getSingleResult();
        
     
}     
    
}
