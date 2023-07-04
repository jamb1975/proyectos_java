/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.concurrent.Task;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.AgnCitas;
import modelo.AgnConsultoriosProcedimientos;
import modelo.AgnEstadosCita;
import modelo.AgnMedicos;
import modelo.GenHorasCita;
/**
 *
 * @author adminlinux
 */
public class AgnCitasController extends AbstractFacade<AgnCitas>{
private AgnCitas agnCitas;
public static Task<Void>   task_agncitas;
public static Thread  thimport_agncitas;
public AgnCitasController(Class<AgnCitas> entityClass) 
    {
        super(entityClass);
    }
  public List<AgnCitas> getCitasFecha(String datecurrent,AgnMedicos agnMedicos,int estadoscita) throws ParseException
  {
  
 List<AgnCitas> list_agncitas;
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<AgnCitas> cq = cb.createQuery(AgnCitas.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<AgnCitas> _agncitas = m.entity(AgnCitas.class);
Root<AgnCitas> pet = cq.from(_agncitas);
SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datecurrent+" 00:00:00");
Predicate _fecha=cb.between(pet.get("fechaHora"), fromDate, fromDate);
Predicate _estadoscita;
AgnEstadosCita agnEstadosCita=new AgnEstadosCita();
_estadoscita=cb.and(cb.equal(pet.get("agnMedicos").get("id"),agnMedicos.getId()));
      cq.where(_fecha,_estadoscita);
TypedQuery<AgnCitas> q=null;
list_agncitas=null;
cq.orderBy(cb.asc(pet.get("agnMedicos").get("id")),cb.asc(pet.get("genHorasCita").get("zona")),cb.asc(pet.get("genHorasCita").get("hora")),cb.asc(pet.get("genHorasCita").get("minutos")));
q=EntityManagerGeneric.em.createQuery(cq);
list_agncitas=q.getResultList();

        return list_agncitas;


  }
   public List<AgnCitas> getCitasAgendadas(String search,int est,String fecha) throws ParseException
  {
  String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 List<AgnCitas> list_agncitas;
     
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<AgnCitas> cq = cb.createQuery(AgnCitas.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<AgnCitas> _agncitas = m.entity(AgnCitas.class);
        Root<AgnCitas> pet = cq.from(_agncitas);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate =new Date();
        Date toDate =new Date();
        toDate= df.parse(fecha +" 23:59:59");
        fromDate= df.parse(fecha +" 00:00:00");
        //cb.between(pet.get("fechaHora"), fromDate, fromDate),
        Predicate predicate=cb.between(pet.get("fechaHora"), fromDate,toDate);
        Expression<String> pdocumento= pet.get("genPacientes").get("genPersonas").get("documento");
        Expression<String> lower_doc =cb.lower(pdocumento);
        Expression<String> papellido= pet.get("genPacientes").get("genPersonas").get("primerApellido");
        Expression<String> lower_apellido =cb.lower(papellido);
        Expression<String> papellido2= pet.get("genPacientes").get("genPersonas").get("segundoApellido");
        Expression<String> lower_apellido2 =cb.lower(papellido2);
        Expression<String> pnombre= pet.get("genPacientes").get("genPersonas").get("primerNombre");
        Expression<String> lower_nombre =cb.lower(pnombre);
        cq.where(predicate,cb.equal(pet.get("agnEstadosCita").get("id"),est),cb.or(cb.like(lower_doc,searchpattern),cb.like(lower_apellido,searchpattern),cb.like(lower_nombre,searchpattern)));
        TypedQuery<AgnCitas> q=null;
        list_agncitas=null;
        cq.orderBy(cb.asc(pet.get("fechaHora")),cb.asc(pet.get("genHorasCita").get("hora")),cb.asc(pet.get("genHorasCita").get("minutos")),cb.asc(pet.get("genPacientes").get("genPersonas").get("primerApellido")),cb.asc(pet.get("genPacientes").get("genPersonas").get("segundoApellido")));
        q=EntityManagerGeneric.em.createQuery(cq);
        q.setMaxResults(49);
        list_agncitas=q.getResultList();

System.out.println(EntityManagerGeneric.em.createQuery(cq).toString());
        
        return list_agncitas;


  }
  public List<AgnCitas> getCitasProceso(String search,String fecha) throws ParseException
  {
  String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
  List<AgnCitas> list_agncitas;
     
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<AgnCitas> cq = cb.createQuery(AgnCitas.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<AgnCitas> _agncitas = m.entity(AgnCitas.class);
        Root<AgnCitas> pet = cq.from(_agncitas);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate =new Date();
         Date toDate =new Date();
        toDate= df.parse(fecha +" 23:59:59");
        fromDate= df.parse(fecha +" 00:00:00");
        Predicate predicate=cb.between(pet.get("fechaHora"), fromDate,toDate);
        cq.where(predicate,cb.equal(pet.get("agnEstadosCita").get("id"),2),cb.or(cb.like(pet.get("genPacientes").get("genPersonas").get("documento"),searchpattern),cb.like(pet.get("genPacientes").get("genPersonas").get("primerApellido"),searchpattern.toUpperCase()),cb.like(pet.get("genPacientes").get("genPersonas").get("segundoApellido"),searchpattern.toUpperCase())));
        TypedQuery<AgnCitas> q=null;
        list_agncitas=null;
        cq.orderBy(cb.asc(pet.get("genPacientes").get("genPersonas").get("primerApellido")),cb.asc(pet.get("genPacientes").get("genPersonas").get("segundoApellido")));
        q=EntityManagerGeneric.em.createQuery(cq);
        list_agncitas=q.getResultList();

System.out.println(EntityManagerGeneric.em.createQuery(cq).toString());
        
        return list_agncitas;


  }  
   public List<AgnCitas> setHorasCitaMedicos(List<AgnMedicos> l_med,List<GenHorasCita> l_hc,String datecurrent,int estado,AgnMedicos agnMedicos ) throws ParseException
  {
       /*
     if(EntityManagerGeneric.et.isActive())
     {
             EntityManagerGeneric.et.rollback();
     }
     int modulo=(l_med.size()%10); 
     int totareg=(l_med.size()/10);
   List<List<GenHorasCita>>Al_hc1=new ArrayList<List<GenHorasCita>>();
    for(int i=0;i<l_hc.size()-modulo;i+=totareg)  
     {
           Al_hc1.add(l_hc.subList(i, totareg-1));
     }
      if(modulo>0)
      {
           Al_hc1.add(l_hc.subList((l_hc.size()-modulo), l_hc.size()-1));
      }
     
      
     
               
            
       for(GenHorasCita hc:l_hc)  
       {
           
           
           
          
     task_agncitas=new Task<Void>() {
        
            @Override
          protected Void call() throws Exception {
          CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
          CriteriaQuery<AgnCitas> cq = cb.createQuery(AgnCitas.class);
          Metamodel m = EntityManagerGeneric.em.getMetamodel();
          EntityType<AgnCitas> _agncitas = m.entity(AgnCitas.class);
          Root<AgnCitas> pet = cq.from(_agncitas);
          SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date fromDate =df.parse(datecurrent+" 23:59:59");
          Predicate _fecha=cb.between(pet.get("fechaHora"), fromDate, fromDate);
          cq.where(_fecha,cb.equal(pet.get("agnMedicos").get("id"),agnMedicos.getId()),cb.equal(pet.get("genHorasCita").get("id"),hc.getId()));
          TypedQuery<AgnCitas> q=null;
          q=EntityManagerGeneric.em.createQuery(cq);
          AgnCitas c;
         try
         {
         c=q.getSingleResult();
         }catch(Exception e)
         {
          try{
           c=new AgnCitas();
           c.setGenHorasCita(hc);
           c.setFechaHora(fromDate);
           c.setFechaCreacion(fromDate);
           c.setAgnMedicos(agnMedicos);
          if(EntityManagerGeneric.et2.isActive())
          {
           //  EntityManagerGeneric.et2.rollback();
          }
           if(EntityManagerGeneric.et.isActive())
          {
             EntityManagerGeneric.et.rollback();
          }
           EntityManagerGeneric.et.begin();//beginTransaction();
           EntityManagerGeneric.em.persist(c);
           EntityManagerGeneric.et.commit();
          } catch(Exception e2)
                   {
                   e2.printStackTrace();
                   }
         }
             
                 
          }
        };
        thimport_agncitas= new Thread(task_agncitas);
          thimport_agncitas.setDaemon(true);
          thimport_agncitas.start();    
     }*/
          
     
     return  getCitasFecha(datecurrent,agnMedicos,estado);
   //  EntityManagerGeneric.cerrartodaslasconexiones(); 
  }
  
     public List<GenHorasCita> getHorasCita() throws ParseException
    {
       
        return getEntityManager().createQuery("select hc from GenHorasCita hc order by hc.hora,hc.minutos,hc.zona")
                                        .getResultList();
    }
     public void saveHorasCita(GenHorasCita genHorasCita)
     {
         if(EntityManagerGeneric.et.isActive())
         {
             EntityManagerGeneric.et.rollback();
         }
         EntityManagerGeneric.et.begin();
         getEntityManager().persist(genHorasCita);
         EntityManagerGeneric.et.commit();
     }
      public GenHorasCita updateHorasCita(GenHorasCita genHorasCita)
     {
         if(EntityManagerGeneric.et.isActive())
         {
             EntityManagerGeneric.et.rollback();
         }
         EntityManagerGeneric.et.begin();
         genHorasCita=getEntityManager().merge(genHorasCita);
         EntityManagerGeneric.et.commit();
         return genHorasCita;
     }
      

      public void update2(AgnCitas agnCitas)
      {
          if(EntityManagerGeneric.et.isActive())
          {
              EntityManagerGeneric.et.rollback();
          }
          EntityManagerGeneric.et.begin();
          EntityManagerGeneric.em.merge(agnCitas);
          EntityManagerGeneric.et.commit();
      }
      public List<AgnCitas> agncitasagendadas(String search,String fechacurrent) throws ParseException
      {
          String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
          SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date fromDate =df.parse(fechacurrent+" 00:00:00");
         Date toDate = df.parse(fechacurrent+" 23:59:59");
         return EntityManagerGeneric.em.createQuery("select ac from  AgnCitas ac  where  ac.fechaHora BETWEEN :m1 and :m2 and ac.genPacientes.genPersonas.documento like :pdoc order by ac.fechaCreacion desc")
                                          .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                          .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                          .setParameter("pdoc", searchpattern)
                                         
                                                    
                                  .getResultList();
         
      }
    @Override
    protected EntityManager getEntityManager() 
    {
        return EntityManagerGeneric.em;
      }
    
@Override
    public void create(AgnCitas agnCitas)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(agnCitas);
        EntityManagerGeneric.et.commit();
    }

@Override
    public AgnCitas update(AgnCitas agnCitas)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        agnCitas=  EntityManagerGeneric.em.merge(agnCitas);
        EntityManagerGeneric.et.commit();
        return agnCitas;
    }
    
}