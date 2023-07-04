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
import modelo.ComprobanteEgreso;
import modelo.LibroAuxiliar;
import modelo.FacturaItem;
import modelo.FacturaProveedores;

/**
 *
 * @author adminlinux
 */
 
public class ComprobanteEgresoController extends AbstractFacade<ComprobanteEgreso>{

    private List<FacturaItem> facturaitems;
    public ComprobanteEgresoController(Class<ComprobanteEgreso> entityClass) {
        super(entityClass);
    }
    
    public void create(ComprobanteEgreso conComprobanteEgreso)
            
{   
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }   
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(conComprobanteEgreso);
     EntityManagerGeneric.et.commit();
}
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
    
      

     public ComprobanteEgreso edit(ComprobanteEgreso conComprobanteEgreso)
     {
         if(conComprobanteEgreso.getId()==null)
         {try
         {
               EntityManagerGeneric.et.begin();
              EntityManagerGeneric.em.persist(conComprobanteEgreso);
             EntityManagerGeneric.et.commit();
         }catch(Exception e){
             e.printStackTrace();
             
         }
         }
         else
         {
               EntityManagerGeneric.et.begin();
               conComprobanteEgreso=  EntityManagerGeneric.em.merge(conComprobanteEgreso);
               EntityManagerGeneric.et.commit();
         }
        
         return conComprobanteEgreso;
     }
     public List<FacturaItem> findfacturaitem(String search)
     {
         String fullnombre="lower(concat(fi.hclConsultas.hclHistoriasClinicas.genPacientes.genPersonas.primerNombre,' ',fi.hclConsultas.hclHistoriasClinicas.genPacientes.genPersonas.primerApellido)) like :search";
         String fullnombre2="lower(concat(fi.factura.hclConsultas.hclHistoriasClinicas.genPacientes.genPersonas.primerNombre,' ',fi.factura.hclConsultas.hclHistoriasClinicas.genPacientes.genPersonas.primerApellido)) like :search";
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
         Long pid=Long.MIN_VALUE;
         try{
             pid=Long.valueOf(search);
         }
         catch(Exception e)
         {
             pid=Long.MIN_VALUE;
         }
         return facturaitems=EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where (fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id=:pid or "
                                                              + " fi.factura.consecutivoFacturaPrefijoA.id=:pid or fi.factura.consecutivoFacturaPrefijoE.id=:pid or fi.factura.consecutivoFacturaPrefijoT.id=:pid or "
                                                              +"  fi.hclConsultas.hclHistoriasClinicas.genPacientes.genPersonas.documento like :search or"
                                                              +" fi.factura.hclConsultas.hclHistoriasClinicas.genPacientes.genPersonas.documento like :search or  "
                                                            + fullnombre+ " or "
                                                              + fullnombre2 +")" )
                                                              .setParameter("pid", pid)
                                                              .setParameter("search", searchpattern)
                                                              .setMaxResults(20)
                                                             .getResultList();
                                       
                                                          
         
     }
     public List<LibroAuxiliar> findconComprobanteEgresos(Long id)
     {
         return EntityManagerGeneric.em.createQuery("select cdc from ConDetallesComprobanteContabilidad cdc where cdc.conComprobanteEgreso.id=:idci")
                                         .setParameter("idci", id)
                                         .getResultList();
                                         
     }
      public List<ComprobanteEgreso> getListConComprobanteEgreso( String datefrom,  String dateto, String search) throws ParseException {

 String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 List<ComprobanteEgreso> list_comprobanteegreso;
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<ComprobanteEgreso> cq = cb.createQuery(ComprobanteEgreso.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<ComprobanteEgreso> _comprobanteegreso = m.entity(ComprobanteEgreso.class);
Root<ComprobanteEgreso> pet = cq.from(_comprobanteegreso);
Join<ComprobanteEgreso, FacturaProveedores> facturaproveedores = pet.join("facturaProveedores");

Calendar calendar = Calendar.getInstance();

SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");

Predicate predicate=cb.between(pet.get("fechaelaboracion"), fromDate,toDate);
cq.where(predicate,cb.or(cb.like(pet.get("facturaProveedores").get("proveedores").get("nombre"),searchpattern.toUpperCase())));
TypedQuery<ComprobanteEgreso> q =q=null;
list_comprobanteegreso=null;
q=EntityManagerGeneric.em.createQuery(cq);
list_comprobanteegreso=q.getResultList();

  return list_comprobanteegreso;  
}
public ComprobanteEgreso getRecord(Long id) throws ParseException 
{

                    return (ComprobanteEgreso) EntityManagerGeneric.em.createQuery( "select c from ConComprobanteEgreso c where c.conComprobanteContabilidad.id=:pid")
                                                   .setParameter("pid", id)
                                                  .setMaxResults(1)
                                                  .getSingleResult();
        
     
}
}
