/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import modelo.GenConvenios;
import modelo.GenEapb;

/**
 *
 * @author adminlinux
 */
public class GenConveniosEapbController extends AbstractFacade<GenConvenios> {

    public GenConveniosEapbController(Class<GenConvenios> entityClass) {
        super(entityClass);
    }
    

 public void create(GenConvenios genConveniosEapb,GenEapb eapb)
 {
     if(EntityManagerGeneric.et.isActive())
     {
         EntityManagerGeneric.et.commit();
     } 
     
     EntityManagerGeneric.et.begin();
     super.create(genConveniosEapb);
     eapb.addConvenio(genConveniosEapb);
     EntityManagerGeneric.em.merge(eapb);
     EntityManagerGeneric.et.commit();
 }
 public void createParticular(GenConvenios genConveniosEapb)
 {
     if(EntityManagerGeneric.et.isActive())
     {
         EntityManagerGeneric.et.commit();
     } 
     
     EntityManagerGeneric.et.begin();
     super.create(genConveniosEapb);
     EntityManagerGeneric.et.commit();
 }
 @Override
 public GenConvenios edit(GenConvenios genConveniosEapb)
 {
      if(EntityManagerGeneric.et.isActive())
     {
         EntityManagerGeneric.et.commit();
     } 
     EntityManagerGeneric.et.begin();
     genConveniosEapb=super.edit(genConveniosEapb);
     EntityManagerGeneric.et.commit();
     return genConveniosEapb;
 }
public List<GenConvenios> convenioseps(String datefrom,  String dateto, String search,int tipoconvenio) throws ParseException
{
  
  List<GenConvenios> li_convenioseps=null;  
 
 String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");
if(tipoconvenio==0)
{  
     li_convenioseps=EntityManagerGeneric.em.createQuery("select ce from GenConvenios ce where (lower(ce.genEapb.nit) like :search or lower(ce.genEapb.nombre) like :search) or ce.fechacontrato BETWEEN :m1 AND :m2  and ce.tipoconvenio like 'C' order by ce.fechacontrato")
                                                                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("search", searchpattern)
                                                                                   .getResultList();
}
else
{
     li_convenioseps=EntityManagerGeneric.em.createQuery("select ce from GenConvenios ce where ce.fechacontrato BETWEEN :m1 AND :m2 and ((lower(ce.descripcion) like :search) )and ce.tipoconvenio like 'P' order by ce.fechacontrato")
                                                                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("search", searchpattern)
                                                                                   .getResultList();
  
}
   return li_convenioseps;
}
public GenConvenios findConvenio(String search)
{
    try{
        
    
    return (GenConvenios)EntityManagerGeneric.em.createQuery("select c FROM GenConvenios c where c.descripcion like :search")
                                                .setParameter("search", search)
                                                .getSingleResult();
    }catch(Exception e)
    {
        return null;
    }
}
public List<GenConvenios> getRecords()
{
    try
    {
    return EntityManagerGeneric.em.createQuery("select gc from GenConvenios gc order by gc.fechacontrato,gc.genEapb.id")
                                  .getResultList();
    }catch(Exception e)
    {
        e.printStackTrace();
        return new ArrayList<>();
    }
}      
@Override
protected EntityManager getEntityManager() 
{
        return EntityManagerGeneric.em;
}
    
}
