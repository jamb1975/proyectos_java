/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import modelo.ModeloTiposDocumentosContables;
import modelo.Sucursales;
import modelo.TiposDocumentosContables;

/**
 *
 * @author adminlinux
 */
public class ModelosTiposDocumentosContablesController {
    
 public void create(ModeloTiposDocumentosContables modeloTiposDocumentosContables)
 {
     if(EntityManagerGeneric.et.isActive())
     {
         EntityManagerGeneric.et.rollback();
     }
     EntityManagerGeneric.et.begin();
       EntityManagerGeneric.em.persist(modeloTiposDocumentosContables);
       EntityManagerGeneric.et.commit();
 }
 
  public void update(ModeloTiposDocumentosContables modeloTiposDocumentosContables)
 {
     if(EntityManagerGeneric.et.isActive())
     {
         EntityManagerGeneric.et.rollback();
     }
       EntityManagerGeneric.et.begin();
       EntityManagerGeneric.em.merge(modeloTiposDocumentosContables);
       EntityManagerGeneric.et.commit();
 } 
 public void delete(ModeloTiposDocumentosContables modeloTiposDocumentosContables)
 {
     if(EntityManagerGeneric.et.isActive())
     {
         EntityManagerGeneric.et.rollback();
     }
     modeloTiposDocumentosContables=EntityManagerGeneric.em.find(ModeloTiposDocumentosContables.class, modeloTiposDocumentosContables.getId());
     EntityManagerGeneric.et.begin();
       EntityManagerGeneric.em.remove(modeloTiposDocumentosContables);
       EntityManagerGeneric.et.commit();
 } 
 public List<ModeloTiposDocumentosContables> getRecords(String search)
 {
     String searchpattern=search==null?"%":"%"+search.toLowerCase()+"%";
     return EntityManagerGeneric.em.createQuery("select m from ModeloTiposDocumentosContables m where lower(m.descripcion) like :search")
                                   .setParameter("search", searchpattern)
                                   .getResultList();
 }
 public List<TiposDocumentosContables> getRecords()
 {
    return EntityManagerGeneric.em.createQuery("select tdc from TiposDocumentosContables tdc order by tdc.codigo")
                                              .getResultList();
 }
  public List<Sucursales> getRecordsSucursales()
 {
    return EntityManagerGeneric.em.createQuery("select s from Sucursales s where s.nombre is not null and s.codigo is not null order by s.nombre ")
                                              .getResultList();
 }       
}
