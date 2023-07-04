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
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.AgnConsultorios;
import modelo.AgnConsultoriosProcedimientos;
import modelo.AgnEspecialidades;
import modelo.AgnMedicos;
import modelo.FacturaItem;
import modelo.GenCategoriasProductos;
import modelo.GenEapb;
import modelo.GenNivelesUsuarios;
import modelo.GenPersonas;
import modelo.GenTiposAfiliacion;
import modelo.GenTiposUsuarios;
import modelo.HclAntecedentesFarmacologicos;
import modelo.HclAntecedentesGenerales;
import modelo.HclAntecedentesGineco;
import modelo.HclAntecedentesPatologicos;
import modelo.HclCausaExterna;
import modelo.HclCie10;
import modelo.HclCodigosConsultas;
import modelo.HclConsultas;
import modelo.HclCups;
import modelo.HclDiagnostico;
import modelo.HclDiagnosticosRelacionados;
import modelo.HclFinalidad;
import modelo.HclFormulacionMedicamentos;
import modelo.HclFormulacionProcedimientos;
import modelo.HclFormulacionesMedicas;

import modelo.HclTiposAntecedentesGenerales;
import modelo.HclTiposAntecedentesPatologicos;
import modelo.HclTiposFormulas;
import modelo.MedPresentacionMedicamentos;
import modelo.MedUnidadDosis;
import modelo.MedUnidadFrecuencia;
import modelo.MedUnidadTratamiento;
import modelo.MedViasAdministracion;

/**
 *
 * @author adminlinux
 */
public class HclHistoriasClinicasController {
  public List<HclTiposAntecedentesGenerales> load_hclTiposAntecedentesGenerales ()
 {
     return EntityManagerGeneric.em.createQuery("select tag from HclTiposAntecedentesGenerales tag")
                                    .getResultList();
 }
  
   public List<HclTiposAntecedentesPatologicos> load_hclTiposAntecedentesPatologicos ()
 {
     return EntityManagerGeneric.em.createQuery("select tap from HclTiposAntecedentesPatologicos tap")
                                    .getResultList();
 } 
     public List<GenTiposUsuarios> load_genTiposUsuarios ()
 {
     return EntityManagerGeneric.em.createQuery("select tu from GenTiposUsuarios tu where tu.codigo is not null and tu.nombre is not null")
                                    .getResultList();
 }  
       public List<GenNivelesUsuarios> load_genNivelesUsuarios ()
 {
     return EntityManagerGeneric.em.createQuery("select nu from GenNivelesUsuarios nu")
                                    .getResultList();
 }  
      public List<GenTiposAfiliacion> load_genTiposAfiliacion ()
 {
     return EntityManagerGeneric.em.createQuery("select ta from GenTiposAfiliacion ta where ta.descripcion is not null and ta.codigo is not null")
                                    .getResultList();
 }   
     public List<GenEapb> load_genEapb ()
 {
     return EntityManagerGeneric.em.createQuery("select ea from GenEapb ea where ea.nombre is not null and ea.codigo is not null order by ea.nombre ")
                                    .getResultList();
 }   
  public List<HclCodigosConsultas> load_hclCodigosConsultas ()
 {
     return EntityManagerGeneric.em.createQuery("select cc from HclCodigosConsultas cc")
                                    .getResultList();
 } 
  public void saveAntecedentesGenerales(HclAntecedentesGenerales ag)
  {
      if(EntityManagerGeneric.et.isActive())
      {
          EntityManagerGeneric.et.getRollbackOnly();
      }
      EntityManagerGeneric.et.begin();
      HclConsultas hc=EntityManagerGeneric.em.find(HclConsultas.class,ag.getHclConsultas().getId());
      ag.setHclConsultas(hc);
      EntityManagerGeneric.em.persist(ag);
      EntityManagerGeneric.et.commit();
  } 
  public void saveAntecedentesGineco(HclAntecedentesGineco agi)
  {
      if(EntityManagerGeneric.et.isActive())
      {
          EntityManagerGeneric.et.getRollbackOnly();
      }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(agi);
        EntityManagerGeneric.et.commit();
  } 
  public void saveAntecedentes(HclAntecedentesFarmacologicos af)
  {
      if(EntityManagerGeneric.et.isActive())
      {
          EntityManagerGeneric.et.getRollbackOnly();
      }
      EntityManagerGeneric.et.begin();
      EntityManagerGeneric.em.persist(af);
       EntityManagerGeneric.et.commit();
  } 
  public void saveAntecedentesPatologicos(HclAntecedentesPatologicos ap)
  {
      if(EntityManagerGeneric.et.isActive())
      {
          EntityManagerGeneric.et.getRollbackOnly();
      }
      EntityManagerGeneric.et.begin();
      EntityManagerGeneric.em.persist(ap);
       EntityManagerGeneric.et.commit();
  } 
  
  public List<HclAntecedentesGenerales> getHclAntecedentesGenerales(HclConsultas hc)
  {
     return EntityManagerGeneric.getEntityManager().createQuery("select ag from HclAntecedentesGenerales ag where ag.hclConsultas=:hc")
              .setParameter("hc",hc)
              .getResultList();
  }
  public List<HclAntecedentesGineco> getHclAntecedentesGineco(HclConsultas hc)
  {
     return EntityManagerGeneric.getEntityManager().createQuery("select agi from HclAntecedentesGineco agi where agi.hclConsultas=:hc")
              .setParameter("hc",hc)
              .getResultList();
  }
  public List<HclAntecedentesFarmacologicos> getHclAntecedentesFarmacologicos(HclConsultas hc)
  {
     return EntityManagerGeneric.getEntityManager().createQuery("select af from HclAntecedentesFarmacologicos af where af.hclHistoriasClinicas=:hc")
              .setParameter("hc",hc)
              .getResultList();
  }
  public List<HclAntecedentesPatologicos> getHclAntecedentesPatologicos()
  {
     return EntityManagerGeneric.getEntityManager().createQuery("select ap from HclAntecedentesPatologicos ap where ap.hclHistoriasClinicas=:hc")
              
              .getResultList();
  }
   public List<HclFinalidad> getHclFinalidad()
  {
     return EntityManagerGeneric.em.createQuery("select f from HclFinalidad f")
                           .getResultList();
  }
    public List<HclCausaExterna> getHclCausaExterna()
  {
     return EntityManagerGeneric.em.createQuery("select ce from HclCausaExterna ce")
                           .getResultList();
  }
    
public List<HclCie10> getCie10(String search) throws ParseException
  {
  String searchpattern = search==null ? "%":  "%"+ search+ "%";
 List<HclCie10> list_hclcie10;
     
        CriteriaBuilder cb = EntityManagerGeneric.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<HclCie10> cq = cb.createQuery(HclCie10.class);
        Metamodel m = EntityManagerGeneric.getEntityManager().getMetamodel();
        EntityType<HclCie10> _hclcie10 = m.entity(HclCie10.class);
        Root<HclCie10> pet = cq.from(_hclcie10);
        cq.where(cb.or(cb.like(pet.get("codigo"),searchpattern),cb.like(pet.get("descripcion"),searchpattern)));
        TypedQuery<HclCie10> q=null;
        list_hclcie10=null;
        cq.orderBy(cb.asc(pet.get("codigo")),cb.asc(pet.get("descripcion")));

q=EntityManagerGeneric.getEntityManager().createQuery(cq);
list_hclcie10=q.getResultList();


        
        return list_hclcie10;


  }
public List<HclCups> getCups(String search) throws ParseException
  {
      try
      {
  String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
   List<HclCups> list_hclcups;
     
        /*CriteriaBuilder cb = EntityManagerGeneric.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<HclCups> cq = cb.createQuery(HclCups.class);
        Metamodel m = EntityManagerGeneric.getEntityManager().getMetamodel();
        EntityType<HclCups> _hclcups = m.entity(HclCups.class);
        Root<HclCups> pet = cq.from(_hclcups);
        cq.where(cb.or(cb.like(pet.get("codigo"),searchpattern),cb.like(pet.get("descripcion"),searchpattern.toUpperCase())));
        TypedQuery<HclCups> q=null;
        list_hclcups=null;
        cq.orderBy(cb.asc(pet.get("codigo")),cb.asc(pet.get("descripcion")));*/
        System.out.println("Cargando");
list_hclcups=EntityManagerGeneric.getEntityManager().createQuery("select hc from HclCups hc where hc.codigo like :sp or hc.descripcion like :sp ")
                                    .setParameter("sp",searchpattern)
                                    .setMaxResults(20)
                                    .getResultList();



System.out.println("total"+ list_hclcups.size());


        
        return list_hclcups;
      }catch(Exception e)
      {
          e.printStackTrace();
          return null;
      }

  }
  public List<HclDiagnosticosRelacionados> getHclDiagnosticosRelacionados(HclDiagnostico hclDiagnostico )
  {
     return EntityManagerGeneric.getEntityManager().createQuery("select dr from HclDiagnosticosRelacionados dr where dr.hcldiagnostico_id=:dp")
                           .setParameter("dp",hclDiagnostico )
                           .getResultList();
  }
  public List<HclTiposFormulas> getHclTiposFormulas( )
  {
     return EntityManagerGeneric.em.createQuery("select tf from HclTiposFormulas tf")
                                               .getResultList();
  }
  public List<MedPresentacionMedicamentos> getMedPresentacionMedicamentos()
  {
     return EntityManagerGeneric.em.createQuery("select pm from MedPresentacionMedicamentos pm")
                                                .getResultList();
  }
  public List<MedUnidadDosis> getMedUnidadDosis()
  {
     return EntityManagerGeneric.em.createQuery("select ud from MedUnidadDosis ud")
                                                .getResultList();
  }
  public List<MedUnidadFrecuencia> getMedUnidadFrecuencia( )
  {
     return EntityManagerGeneric.em.createQuery("select uf from MedUnidadFrecuencia uf")
                                                .getResultList();
  }
  public List<MedUnidadTratamiento> getMedUnidadTratamiento()
  {
     return EntityManagerGeneric.em.createQuery("select uf from MedUnidadTratamiento uf")
                                                .getResultList();
  }
  public List<MedViasAdministracion> getMedViasAdministracion()
  {
     return EntityManagerGeneric.em.createQuery("select va from MedViasAdministracion va")
                                                .getResultList();
  }
  public List<HclFormulacionesMedicas> getHclFormulacionesMedicas(HclConsultas c)
  {
     return EntityManagerGeneric.getEntityManager().createQuery("select c from HclFormulacionesMedicas c where c.hclConsultas=:c")
              .setParameter("c",c)
              .getResultList();
  }
  public List<HclFormulacionMedicamentos> getHclFormulacionesMedicamentos(HclFormulacionesMedicas fm)
  {
     return EntityManagerGeneric.getEntityManager().createQuery("select fm from HclFormulacionMedicamentos fm where fm.hclFormulacionesMedicas=:f")
              .setParameter("f",fm)
              .getResultList();
  }
  public List<HclFormulacionProcedimientos> getHclFormulacionProcedimientos(HclFormulacionesMedicas fm)
  {
     return EntityManagerGeneric.getEntityManager().createQuery("select fp from HclFormulacionProcedimientos fp where fp.hclFormulacionesMedicas=:f")
              .setParameter("f",fm)
              .getResultList();
  }
  public List<GenCategoriasProductos> getGenCategoriasProductos()
  {
     return EntityManagerGeneric.em.createQuery("select cp from GenCategoriasProductos cp order by cp.nombre")
                         .getResultList();
  }
  public List<FacturaItem> getRips(String datefrom,String dateto) throws ParseException
  {
      SimpleDateFormat df=null;
      df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date fromdate=df.parse(datefrom+" 00:00:00");
      Date todate=df.parse(dateto+" 23:59:59");
      return EntityManagerGeneric.getEntityManager().createQuery("select fi from FacturaItem fi where fi.fechaapertura BETWEEN :fd and :td and fi.producto.hclCups IS NOT NULL")
                                    .setParameter("fd",fromdate,TemporalType.TIMESTAMP)
                                     .setParameter("td", todate,TemporalType.TIMESTAMP)
                                     .getResultList();
                                    
  }
    public List<AgnEspecialidades> getListEspecialidades() throws ParseException
    {
       
        return EntityManagerGeneric.em.createQuery("select es from AgnEspecialidades es order by es.nombre")
                                        .getResultList();
    }
     public void saveEspecialidades(AgnEspecialidades agnEspecialidades)
     {
         if(EntityManagerGeneric.et.isActive())
         {
             EntityManagerGeneric.et.rollback();
         }
         EntityManagerGeneric.et.begin();
         EntityManagerGeneric.getEntityManager().persist(agnEspecialidades);
         EntityManagerGeneric.et.commit();
     }
      public AgnEspecialidades updateEspecialidades(AgnEspecialidades agnEspecialidades)
     {
         if(EntityManagerGeneric.et.isActive())
         {
             EntityManagerGeneric.et.rollback();
         }
         EntityManagerGeneric.et.begin();
         agnEspecialidades=EntityManagerGeneric.getEntityManager().merge(agnEspecialidades);
         EntityManagerGeneric.et.commit();
         return agnEspecialidades;
     }
    public List<AgnConsultorios> getListConsultorios() throws ParseException
    {
       
        return EntityManagerGeneric.em.createQuery("select c from AgnConsultorios c where c.descripcion is not null and c.numero>0 order by c.numero")
                                       .getResultList();
    }
     public void saveConsultorios(AgnConsultorios agnConsultorios)
     {
         if(EntityManagerGeneric.et.isActive())
         {
             EntityManagerGeneric.et.rollback();
         }
         EntityManagerGeneric.beginTransaction();
         EntityManagerGeneric.getEntityManager().persist(agnConsultorios);
         EntityManagerGeneric.commit();
     }
      public AgnConsultorios updateConsultorios(AgnConsultorios agnConsultorios)
     {
         if(EntityManagerGeneric.et.isActive())
         {
             EntityManagerGeneric.et.rollback();
         }
         EntityManagerGeneric.et.begin();
         agnConsultorios=EntityManagerGeneric.getEntityManager().merge(agnConsultorios);
         EntityManagerGeneric.et.commit();
         return agnConsultorios;
     }
     public List<AgnMedicos> getListMedicos(String search) throws ParseException
    {
        String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
     
        return EntityManagerGeneric.em.createQuery("select m from AgnMedicos m where (m.genPersonas.documento like :search or lower(concat(m.genPersonas.primerApellido,' ',m.genPersonas.segundoApellido)) like :search) order by m.genPersonas.primerApellido,m.genPersonas.primerNombre")
                                        .setParameter("search", searchpattern)
                                        .getResultList();
    }
     public void saveMedicos(AgnMedicos agnMedicos)
     {
         GenPersonas genPersonas=new GenPersonas();
         genPersonas=agnMedicos.getGenPersonas();
         if(EntityManagerGeneric.et.isActive())
         {
             EntityManagerGeneric.et.rollback();
         }
         EntityManagerGeneric.et.begin();
         EntityManagerGeneric.em.persist(genPersonas);
         agnMedicos.setGenPersonas(genPersonas);
         EntityManagerGeneric.em.persist(agnMedicos);
         EntityManagerGeneric.et.commit();
     }
      public AgnMedicos updateMedicos(AgnMedicos agnMedicos)
     {
         if(EntityManagerGeneric.et.isActive())
         {
             EntityManagerGeneric.et.rollback();
         }
         EntityManagerGeneric.et.begin();
         agnMedicos=EntityManagerGeneric.em.merge(agnMedicos);
         EntityManagerGeneric.et.commit();
         return agnMedicos;
     } 
public List<AgnConsultoriosProcedimientos> getListConsultoriosProcedimientos(String search) throws ParseException
    {
        String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
      
        return EntityManagerGeneric.getEntityManager().createQuery("select cp from AgnConsultoriosProcedimientos cp where (cp.agnMedicos.genPersonas.documento like :search or lower(concat(cp.agnMedicos.genPersonas.primerApellido,' ',cp.agnMedicos.genPersonas.segundoApellido)) like :search) order by cp.agnMedicos.genPersonas.primerApellido,cp.agnMedicos.genPersonas.primerNombre")
                                        .setParameter("search", searchpattern)
                                        .getResultList();
    }
     public void saveConsultoriosProcedimientos(AgnConsultoriosProcedimientos agnConsultoriosProcedimientos)
     {
        
         if(EntityManagerGeneric.et.isActive())
         {
             EntityManagerGeneric.et.rollback();
         }
         EntityManagerGeneric.et.begin();
         EntityManagerGeneric.getEntityManager().persist(agnConsultoriosProcedimientos);
         EntityManagerGeneric.et.commit();
     }
      public AgnConsultoriosProcedimientos updateConsultoriosProcedimientos(AgnConsultoriosProcedimientos agnConsultoriosProcedimientos)
     {
         if(EntityManagerGeneric.et.isActive())
         {
             EntityManagerGeneric.et.rollback();
         }
         EntityManagerGeneric.et.begin();
         agnConsultoriosProcedimientos=EntityManagerGeneric.getEntityManager().merge(agnConsultoriosProcedimientos);
         EntityManagerGeneric.et.commit();
         return agnConsultoriosProcedimientos;
     } 
  public List<HclCie10> getLi_hclcie10()
  {
      return EntityManagerGeneric.em.createQuery("select dx from HclCie10 dx")
                                     .setMaxResults(20)
                                     .getResultList();
  }
}
