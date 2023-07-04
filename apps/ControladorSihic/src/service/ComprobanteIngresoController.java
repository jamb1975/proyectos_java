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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.ComprobanteIngreso;
import modelo.LibroAuxiliar;
import modelo.ConsecutivosContabilidad;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.Sucursales;

/**
 *
 * @author adminlinux
 */
 
public class ComprobanteIngresoController extends AbstractFacade<ComprobanteIngreso>{

    private List<FacturaItem> facturaitems;
    public ComprobanteIngresoController(Class<ComprobanteIngreso> entityClass) {
        super(entityClass);
    }
    
    public void create(ComprobanteIngreso conComprobanteIngreso,ConsecutivosContabilidad consecutivosContabilidad,Sucursales sucursales)
            
{   
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }   
    try
    {
        
    EntityManagerGeneric.et.begin();
        consecutivosContabilidad=EntityManagerGeneric.em.find(ConsecutivosContabilidad.class, consecutivosContabilidad.getId(),LockModeType.PESSIMISTIC_WRITE);
        conComprobanteIngreso.setNo_consecutivo(consecutivosContabilidad.getConsecutivocomprobanteingreso()+Long.valueOf(1));
        consecutivosContabilidad.setConsecutivocomprobanteingreso(conComprobanteIngreso.getNo_consecutivo());
        EntityManagerGeneric.em.merge(consecutivosContabilidad);
   
    EntityManagerGeneric.em.persist(conComprobanteIngreso);
     EntityManagerGeneric.et.commit();
    }catch(Exception e)
    {
        if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }  
         EntityManagerGeneric.et.begin();
        consecutivosContabilidad=EntityManagerGeneric.em.find(ConsecutivosContabilidad.class, consecutivosContabilidad.getId(),LockModeType.PESSIMISTIC_WRITE);
        conComprobanteIngreso.setNo_consecutivo(consecutivosContabilidad.getConsecutivocomprobanteingreso()+Long.valueOf(2));
        consecutivosContabilidad.setConsecutivocomprobanteingreso(conComprobanteIngreso.getNo_consecutivo());
        EntityManagerGeneric.em.merge(consecutivosContabilidad);
   
    EntityManagerGeneric.em.persist(conComprobanteIngreso);
     EntityManagerGeneric.et.commit();
    }
}
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
        
     
     public ComprobanteIngreso edit(ComprobanteIngreso conComprobanteIngreso)
     {
         
         if(conComprobanteIngreso.getId()==null)
         {  
           EntityManagerGeneric.et.begin();
                super.create(conComprobanteIngreso);
             
          
           EntityManagerGeneric.et.commit();
         }
         else
         {
               EntityManagerGeneric.et.begin();
               conComprobanteIngreso= super.edit(conComprobanteIngreso);
              
               EntityManagerGeneric.et.commit();
         }
         return conComprobanteIngreso;
     }
     public ComprobanteIngreso editFacturaItem(ComprobanteIngreso conComprobanteIngreso)
     {
          if(conComprobanteIngreso.getId()==null)
         {  
           EntityManagerGeneric.et.begin();
             super.create(conComprobanteIngreso);
           
          
          
           EntityManagerGeneric.et.commit();
         }
         else
         {
               EntityManagerGeneric.et.begin();
               conComprobanteIngreso= super.edit(conComprobanteIngreso);
              
               EntityManagerGeneric.et.commit();
         }
         return conComprobanteIngreso;
     }
     public List<FacturaItem> findfacturaitem(String search)
     {
         String fullnombre="lower(concat(f.hclConsultas.hclHistoriasClinicas.genPacientes.genPersonas.primerNombre,' ',f.hclConsultas.hclHistoriasClinicas.genPacientes.genPersonas.primerApellido)) like :search";
         String fullnombre2="lower(concat(f.hclConsultas.hclHistoriasClinicas.genPacientes.genPersonas.primerNombre,' ',f.hclConsultas.hclHistoriasClinicas.genPacientes.genPersonas.primerApellido)) like :search";
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
         Long pid=Long.MIN_VALUE;
         try{
             pid=Long.valueOf(search);
         }
         catch(Exception e)
         {
             pid=Long.MIN_VALUE;
         }
         return EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi  where fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id=:pid" )
                                                                     .setParameter("pid", pid)
                                                                     .getResultList();
                                       
                                                          
         
     }
     
     public List<LibroAuxiliar> findconComprobanteIngresos(Long id)
     {
         return EntityManagerGeneric.em.createQuery("select cdc from ConDetallesComprobanteContabilidad cdc where cdc.conComprobanteIngreso.id=:idci")
                                         .setParameter("idci", id)
                                         .getResultList();
                                         
     }
      public void deleteallcomprobantesingresodecomprobantecontabilidad(Long id)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        List<ComprobanteIngreso> li_Contabilidads=EntityManagerGeneric.em.createQuery("select ci from ConComprobanteIngreso ci where ci.conComprobanteContabilidad.id=:pid")
                                                                            .setParameter("pid", id) 
                                                                            .getResultList();
        for(ComprobanteIngreso ci:li_Contabilidads)
        {
            EntityManagerGeneric.et.begin();
              EntityManagerGeneric.em.remove(ci);
            EntityManagerGeneric.et.commit();
        }
    }
    public List<ComprobanteIngreso> getListConComprobanteIngreso( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 List<ComprobanteIngreso> list_comprobanteingreso;
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<ComprobanteIngreso> cq = cb.createQuery(ComprobanteIngreso.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<ComprobanteIngreso> _comprobanteingreso = m.entity(ComprobanteIngreso.class);
Root<ComprobanteIngreso> pet = cq.from(_comprobanteingreso);
Join<ComprobanteIngreso, Factura> factura = pet.join("factura");

Calendar calendar = Calendar.getInstance();

SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");
Long nocomprobante=new Long("0");
try
{
    nocomprobante=Long.valueOf(search);
}
catch(Exception e)
{
    nocomprobante=Long.valueOf("0");
}
Predicate predicate=cb.between(pet.get("fechaelaboracion"), fromDate,toDate);
cq.where(predicate);
TypedQuery<ComprobanteIngreso> q =q=null;
list_comprobanteingreso=null;
q=EntityManagerGeneric.em.createQuery(cq);
list_comprobanteingreso=q.getResultList();

  return list_comprobanteingreso;  
}
public ComprobanteIngreso getRecord(Long id) throws ParseException 
{

                    return (ComprobanteIngreso) EntityManagerGeneric.em.createQuery( "select c from ConComprobanteIngreso c where c.conComprobanteContabilidad.id=:pid")
                                                   .setParameter("pid", id)
                                                  .setMaxResults(1)
                                                  .getSingleResult();
        
     
}
}
