/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import modelo.GenDepartamentos;
import modelo.GenEstadosCiviles;
import modelo.GenEtnias;
import modelo.GenMunicipios;
import modelo.GenNivelesEducativos;
import modelo.GenProfesiones;
import modelo.GenSexo;
import modelo.GenTiposDocumentos;
import modelo.GenUnidadesOrganizacion;
import modelo.GenZonaResidencia;

/**
 *
 * @author adminlinux
 */
public class GeneralController 
{
 public List<GenTiposDocumentos> load_gentiposdocumentos ()
 {
     return EntityManagerGeneric.em.createQuery("select td from GenTiposDocumentos td where td.sigla is not null and td.nombre is not null")
                                    .getResultList();
 }
  public List<GenNivelesEducativos> load_genniveleeducativos ()
 {
     return EntityManagerGeneric.em.createQuery("select ne from GenNivelesEducativos ne")
                                    .getResultList();
 }
   public List<GenSexo> load_gensexo ()
 {
     return EntityManagerGeneric.em.createQuery("select s from GenSexo s")
                                    .getResultList();
 }
    public List<GenMunicipios> load_genmunicipios ()
 {
     return EntityManagerGeneric.em.createQuery("select m from GenMunicipios m")
                                    .getResultList();
 }
      public List<GenDepartamentos> load_gendepartamentos ()
 {
     return EntityManagerGeneric.em.createQuery("select d from GenDepartamentos d")
                                    .getResultList();
 }
      
   public List<GenEtnias> load_genetnias ()
 {
     return EntityManagerGeneric.em.createQuery("select e from GenEtnias e")
                                    .getResultList();
 }    
     public List<GenZonaResidencia> load_genzonaresidencia ()
 {
     return EntityManagerGeneric.em.createQuery("select zr from GenZonaResidencia zr where zr.codigo is not null and zr.descripcion is not null")
                                    .getResultList();
 }
       public List<GenProfesiones> load_genprofesiones ()
 {
     return EntityManagerGeneric.em.createQuery("select p from GenProfesiones p")
                                    .getResultList();
 }
 public List<GenEstadosCiviles> load_genestadosciviles ()
 {
     return EntityManagerGeneric.em.createQuery("select ec from GenEstadosCiviles ec")
                                    .getResultList();
 }
 public GenUnidadesOrganizacion load_GenUnidadesOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion)
 {
     try{
     genUnidadesOrganizacion=(GenUnidadesOrganizacion)EntityManagerGeneric.em.createQuery("select uo from GenUnidadesOrganizacion uo where uo.id=(select max(uo2.id) from GenUnidadesOrganizacion uo2)")
       
                                                     .getSingleResult();
     
     }catch(Exception e)
     {
         e.printStackTrace();
         genUnidadesOrganizacion=null;
     }
     return genUnidadesOrganizacion;
     
 }
}
