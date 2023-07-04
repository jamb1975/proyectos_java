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
import model.ConComprobanteContabilidad;
import model.ConComprobanteIngreso;
import model.ConDetallesComprobanteContabilidad;
import model.ConsecutivoComprobanteIngreso;
import model.Factura;
import model.FacturaItem;

/**
 *
 * @author adminlinux
 */
 
public class ConComprobanteIngresoController extends AbstractFacade<ConComprobanteIngreso>{

    private List<FacturaItem> facturaitems;
    public ConComprobanteIngresoController(Class<ConComprobanteIngreso> entityClass) {
        super(entityClass);
    }
    
    public void create(ConComprobanteIngreso conComprobanteIngreso)
            
{   
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }   
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(conComprobanteIngreso);
     EntityManagerGeneric.et.commit();
}
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
        
     
     public ConComprobanteIngreso edit(ConComprobanteIngreso conComprobanteIngreso, Factura f) throws ParseException
     {
          if(EntityManagerGeneric.et.isActive())
       {
        EntityManagerGeneric.et.rollback();
       }  
           EntityManagerGeneric.et.begin();
           if(conComprobanteIngreso.getId()==null)
           {
            EntityManagerGeneric.em.persist(conComprobanteIngreso);
           }
           else
           {
                conComprobanteIngreso=EntityManagerGeneric.em.merge(conComprobanteIngreso);
           }
           if(f!=null)
           {    
           if(f.getId()!=null)
           {    
           f.addAbono(conComprobanteIngreso);
           EntityManagerGeneric.em.merge(f);
           }
           }
         
          
          
           EntityManagerGeneric.et.commit();
        
         return conComprobanteIngreso;
     }
     public ConComprobanteIngreso editFacturaItem(ConComprobanteIngreso conComprobanteIngreso)
     {
         ConsecutivoComprobanteIngreso consecutivoComprobanteIngreso=null;
         consecutivoComprobanteIngreso=new ConsecutivoComprobanteIngreso();
         if(conComprobanteIngreso.getId()==null)
         {  
           EntityManagerGeneric.et.begin();
           EntityManagerGeneric.em.persist(consecutivoComprobanteIngreso);
           conComprobanteIngreso.setConsecutivoComprobanteIngreso(consecutivoComprobanteIngreso);
           EntityManagerGeneric.em.persist(conComprobanteIngreso);
           
          
          
           EntityManagerGeneric.et.commit();
         }
         else
         {
               EntityManagerGeneric.et.begin();
               conComprobanteIngreso= EntityManagerGeneric.em.merge(conComprobanteIngreso);
              
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
      public List<Factura> findfactura(String search)
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
         return EntityManagerGeneric.em.createQuery("select f from Factura f  where f.prefijo like :search or f.consecutivoFacturaPrefijoA.id= :pid or f.consecutivoFacturaPrefijoE.id= :pid or f.consecutivoFacturaPrefijoT.id= :pid or f.consecutivoFacturaPrefijoP.id= :pid ")
                                                                      .setParameter("pid", pid)
                                                                      .setParameter("search", searchpattern)
                                                                     //.setMaxResults(20)
                                                                      .getResultList();
                                       
                                                          
         
     }
     public List<ConDetallesComprobanteContabilidad> findconComprobanteIngresos(ConComprobanteContabilidad conComprobanteContabilidad)
     {
         return EntityManagerGeneric.em.createQuery("select cdc from ConDetallesComprobanteContabilidad cdc where cdc.conComprobanteContabilidad=:idci")
                                         .setParameter("idci", conComprobanteContabilidad)
                                         .getResultList();
                                         
     }
      public List<ConComprobanteIngreso> getRecords(String fromdate,String todate,String search) throws ParseException
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
         Long pid;
         SimpleDateFormat df=null;
         df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date datefrom=df.parse(fromdate+" 00:00:00");
         Date dateto=df.parse(todate+" 23:59:59");
                   return EntityManagerGeneric.em.createQuery( "select ci from ConComprobanteIngreso ci where ci.fechaelaboracion BETWEEN :fd and :td order by ci.fechaelaboracion")                                                  .setParameter("fd",datefrom,TemporalType.TIMESTAMP)
                                                   .setParameter("td", dateto,TemporalType.TIMESTAMP)
                                                   .getResultList();
        
    }                                                         
       

}
