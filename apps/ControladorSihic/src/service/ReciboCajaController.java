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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.ComprobanteIngreso;
import modelo.Factura;
import modelo.ReciboCaja;

/**
 *
 * @author adminlinux
 */
public class ReciboCajaController extends AbstractFacade<ReciboCaja>{

    public ReciboCajaController(Class<ReciboCaja> entityClass) {
        super(entityClass);
    }

    public void create(ReciboCaja reciboCaja)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
         getEntityManager().persist(reciboCaja);
         EntityManagerGeneric.et.commit();
    }
  
    @Override
    public ReciboCaja update(ReciboCaja reciboCaja)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
         super.edit(reciboCaja);
         EntityManagerGeneric.et.commit();
      return reciboCaja;   
    }
    
    
    @Override
    protected EntityManager getEntityManager() {
        
        return EntityManagerGeneric.em;
    }
    
    public List<ReciboCaja> getRecords( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<ReciboCaja> cq = cb.createQuery(ReciboCaja.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<ReciboCaja> _recibocaja = m.entity(ReciboCaja.class);
        Root<ReciboCaja> pet = cq.from(_recibocaja);
      
Calendar calendar = Calendar.getInstance();

SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");
Long norecibocaja=new Long("0");
try
{
    norecibocaja=Long.valueOf(search);
}
catch(Exception e)
{
    norecibocaja=Long.valueOf("0");
}
Predicate predicate=cb.between(pet.get("fechaelaboracion"), fromDate,toDate);
cq.where(predicate,cb.or(cb.like(pet.get("proveedores").get("nombre"),searchpattern.toUpperCase()),cb.like(pet.get("proveedores").get("no_ident"),searchpattern.toUpperCase()),cb.equal(pet.get("consecutivoReciboCaja").get("id"),norecibocaja)));
TypedQuery<ComprobanteIngreso> q =q=null;

return EntityManagerGeneric.em.createQuery(cq).getResultList();


  
}

    
    
}
