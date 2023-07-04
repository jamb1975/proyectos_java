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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.ComprobanteIngreso;
import modelo.ConsecutivosContabilidad;
import modelo.Consignaciones;
import modelo.FacturaProveedores;
import modelo.Sucursales;

/**
 *
 * @author adminlinux
 */
public class ConsignacionesController extends AbstractFacade<Consignaciones>{

    public ConsignacionesController(Class<Consignaciones> entityClass) {
        super(entityClass);
    }

    public void create(Consignaciones entity,ConsecutivosContabilidad consecutivosContabilidad)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        
        EntityManagerGeneric.et.begin();
        consecutivosContabilidad=EntityManagerGeneric.em.find(ConsecutivosContabilidad.class, consecutivosContabilidad.getId(),LockModeType.PESSIMISTIC_WRITE);
        entity.setNo_consecutivo(consecutivosContabilidad.getConsecutivocomprobanteconsginacion()+Long.valueOf(1));
        consecutivosContabilidad.setConsecutivocomprobanteconsginacion(entity.getNo_consecutivo());
       
        EntityManagerGeneric.em.merge(consecutivosContabilidad);
      
         getEntityManager().persist(entity);
         EntityManagerGeneric.et.commit();
    }
  
    @Override
    public Consignaciones update(Consignaciones entity)
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
    
    public List<Consignaciones> getRecords( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<Consignaciones> cq = cb.createQuery(Consignaciones.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<Consignaciones> _consignacioneso = m.entity(Consignaciones.class);
        Root<Consignaciones> pet = cq.from(_consignacioneso);
      
Calendar calendar = Calendar.getInstance();

SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");
Long noconsignacion=new Long("0");
try
{
    noconsignacion=Long.valueOf(search);
}
catch(Exception e)
{
    noconsignacion=Long.valueOf("0");
}
Predicate predicate=cb.between(pet.get("fechaelaboracion"), fromDate,toDate);
        Expression<String> pbanco= pet.get("banco");
        Expression<String> lower_banco =cb.lower(pbanco);
        Expression<String> pcodbanco= pet.get("codbanco");
        Expression<String> lower_codbanco =cb.lower(pcodbanco);
        Expression<String> pnocuenta= pet.get("nocuenta");
        Expression<String> lower_nocuenta =cb.lower(pnocuenta);
        Expression<String> pnombrecuenta= pet.get("nombrecuenta");
        Expression<String> lower_nombrecuenta =cb.lower(pnombrecuenta);
        Expression<String> pdepositante= pet.get("depositante");
        Expression<String> lower_depositante =cb.lower(pdepositante);
        Expression<String> pnoident= pet.get("noident");
        Expression<String> lower_noident =cb.lower(pnoident);
     
cq.where(predicate,cb.or(cb.like(lower_banco,searchpattern.toUpperCase()),cb.like(lower_codbanco,searchpattern.toUpperCase()),cb.like(lower_nocuenta,searchpattern.toUpperCase()),cb.like(lower_depositante,searchpattern.toUpperCase()),cb.like(lower_noident,searchpattern.toUpperCase()),cb.like(lower_nombrecuenta,searchpattern.toUpperCase()),cb.equal(pet.get("consecutivoConsignaciones").get("id"),noconsignacion)));
TypedQuery<ComprobanteIngreso> q =q=null;

return EntityManagerGeneric.em.createQuery(cq).getResultList();


  
}

public Consignaciones getRecord(Long id) throws ParseException 
{

                    return (Consignaciones) EntityManagerGeneric.em.createQuery( "select c from Consignaciones c where c.conComprobanteContabilidad.id=:pid")
                                                   .setParameter("pid", id)
                                                  .setMaxResults(1)
                                                  .getSingleResult();
        
     
}
    
}
