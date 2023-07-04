/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import model.ConComprobanteContabilidad;
import model.ConComprobanteEgreso;
import model.ConComprobanteGastos;
import model.ConComprobanteIngreso;
import model.ConDetallesComprobanteContabilidad;
import model.ConsecutivoComprobanteEgreso;
import model.ConsecutivoComprobanteIngreso;
import model.FacturaItem;
import model.FacturaProveedores;

/**
 *
 * @author adminlinux
 */
 
public class ConComprobanteEgresoController extends AbstractFacade<ConComprobanteEgreso>{

    private List<FacturaItem> facturaitems;
    public ConComprobanteEgresoController(Class<ConComprobanteEgreso> entityClass) {
        super(entityClass);
    }
    
    public void create(ConComprobanteEgreso conComprobanteEgreso)
            
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
    
    
      

     public ConComprobanteEgreso edit(ConComprobanteEgreso conComprobanteEgreso,FacturaProveedores fp)
     {
          ConsecutivoComprobanteEgreso consecutivoComprobanteEgreso=null;
         consecutivoComprobanteEgreso=new ConsecutivoComprobanteEgreso();
       if(EntityManagerGeneric.et.isActive())
       {
        EntityManagerGeneric.et.rollback();
       }  
           EntityManagerGeneric.et.begin();
           if(conComprobanteEgreso.getId()==null)
           {
            EntityManagerGeneric.em.persist(consecutivoComprobanteEgreso);
            conComprobanteEgreso.setConsecutivoComprobanteEgreso(consecutivoComprobanteEgreso);
            EntityManagerGeneric.em.persist(conComprobanteEgreso);
           }
           else
           {
                conComprobanteEgreso=EntityManagerGeneric.em.merge(conComprobanteEgreso);
           }
           if(fp!=null)
           {    
           if(fp.getId()!=null)
           {    
           fp.addAbono(conComprobanteEgreso);
           EntityManagerGeneric.em.merge(fp);
           }
           }
         
          
          
           EntityManagerGeneric.et.commit();
        
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
     public List<ConDetallesComprobanteContabilidad> findconComprobanteEgresos(Long id)
     {
         return EntityManagerGeneric.em.createQuery("select cdc from ConDetallesComprobanteContabilidad cdc where cdc.conComprobanteEgreso.id=:idci")
                                         .setParameter("idci", id)
                                         .getResultList();
                                         
     }
     
      public List<ConComprobanteEgreso> getRecords(String fromdate,String todate,String search) throws ParseException
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
         Long pid;
         SimpleDateFormat df=null;
         df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date datefrom=df.parse(fromdate+" 00:00:00");
         Date dateto=df.parse(todate+" 23:59:59");
                   return EntityManagerGeneric.em.createQuery( "select ci from ConComprobanteEgreso ci where ci.fechaelaboracion BETWEEN :fd and :td order by ci.fechaelaboracion")                                                  .setParameter("fd",datefrom,TemporalType.TIMESTAMP)
                                                   .setParameter("td", dateto,TemporalType.TIMESTAMP)
                                                   .getResultList();
        
    }   

}
