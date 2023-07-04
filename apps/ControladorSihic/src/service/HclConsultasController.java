/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.AgnCitas;
import modelo.GenUnidadesOrganizacion;
import modelo.HclConsultas;
import modelo.HclDiagnostico;
import modelo.HclDiagnosticosRelacionados;
import modelo.HclFormulacionMedicamentos;
import modelo.HclFormulacionProcedimientos;
import modelo.HclFormulacionesMedicas;
import modelo.HclTiposAtenciones;

/**
 *
 * @author adminlinux
 */
public class HclConsultasController extends AbstractFacade<HclConsultas>{

     private static final String FIND_ORGANIZACION_QUERY = 
            "select o from GenUnidadesOrganizacion o "+
            "where  (lower(o.nombre) like :search "+ 
            " or o.codigo like :search )order by o.nombre";
    public HclConsultasController(Class<HclConsultas> entityClass) {
        super(entityClass);
    }
   public void save(HclConsultas consultas)
   {
         if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
        
        try
        {
         HclTiposAtenciones hclTiposAtenciones;
         hclTiposAtenciones=EntityManagerGeneric.em.find(HclTiposAtenciones.class,Long.valueOf(1));
         EntityManagerGeneric.et.begin();
         consultas.setHclTiposAtenciones(hclTiposAtenciones);
         getEntityManager().persist(consultas);
         EntityManagerGeneric.et.commit();
        }catch(Exception e)
        {
            
        }
   }
   public HclConsultas findconsulta(AgnCitas agnCitas)
   {
       try
       {
       return (HclConsultas)EntityManagerGeneric.em.createQuery("select c from HclConsultas c where c.agnCitas=:ac")
                                      .setParameter("ac", agnCitas)
                                      .getSingleResult();
       }
       catch(Exception e)
       {
         return null;  
       }
   }
   public void saveDiagnostico(HclDiagnostico hclDiagnostico)
   {
      // HclCie10 hclCie10=EntityManagerGeneric.em.find(HclCie10.class,hclDiagnostico.getHclcie10_id().getId());
       //hclDiagnostico.setHclcie10_id(hclCie10);
      
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
          
          EntityManagerGeneric.et.begin();
            getEntityManager().persist(hclDiagnostico);
                
          EntityManagerGeneric.et.commit();
       
   }
    public void saveDiagnosticoRelacionado(HclDiagnosticosRelacionados hclDiagnosticosRelacionados)
   {
      // HclCie10 hclCie10=EntityManagerGeneric.em.find(HclCie10.class,hclDiagnostico.getHclcie10_id().getId());
       //hclDiagnostico.setHclcie10_id(hclCie10);
      
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
          
          EntityManagerGeneric.et.begin();
            getEntityManager().persist(hclDiagnosticosRelacionados);
                
          EntityManagerGeneric.et.commit();
       
   }
    public void saveFormulasMedicas(HclFormulacionesMedicas formulacionesMedicas )
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(formulacionesMedicas);
        EntityManagerGeneric.et.commit();
    }
    public void saveFormulasMedicaMentos(HclFormulacionMedicamentos formulacionMedicamentos )
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(formulacionMedicamentos);
        EntityManagerGeneric.et.commit();
    }
    public void saveFormulasProcedimientos(HclFormulacionProcedimientos hclFormulacionProcedimientos )
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(hclFormulacionProcedimientos);
        EntityManagerGeneric.et.commit();
    }
    public HclConsultas getHclConsulta(AgnCitas agnCitas)
  {
      return (HclConsultas) EntityManagerGeneric.em.createQuery("select c from HclConsultas c where c.agnCitas=:ac order by c.id desc")
              .setParameter("ac",agnCitas)
              .setMaxResults(1)
              .getSingleResult();
  }
    public HclConsultas edit(HclConsultas hclConsultas)
    {
         EntityManagerGeneric.et.begin();
        hclConsultas=super.edit(hclConsultas);
        EntityManagerGeneric.et.commit();
        return hclConsultas;
    }
    public void saveOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(genUnidadesOrganizacion);
        EntityManagerGeneric.et.commit();
    }
    public GenUnidadesOrganizacion updateOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        genUnidadesOrganizacion=EntityManagerGeneric.em.merge(genUnidadesOrganizacion);
        EntityManagerGeneric.et.commit();
        return genUnidadesOrganizacion;
    }
    public List<GenUnidadesOrganizacion> getOrganizacion(String search)
    {
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery(FIND_ORGANIZACION_QUERY)
                                      .setParameter("search", searchpattern)
                                      .getResultList();
    }
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
}
