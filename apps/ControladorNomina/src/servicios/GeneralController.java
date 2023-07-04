/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.List;
import modelo.GenDepartamentos;


import modelo.GenMunicipios;

import modelo.GenSexo;
import modelo.GenTiposDocumentos;
import modelo.GenUnidadesOrganizacion;


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
 
   public List<GenSexo> load_gensexo ()
 {
     return EntityManagerGeneric.em.createQuery("select s from GenSexo s")
                                    .getResultList();
 }
    public List<GenMunicipios> load_genmunicipios ()
 {
     return EntityManagerGeneric.em.createQuery("select m from GenMunicipios m order by m.nombre asc")
                                    .getResultList();
 }
      public List<GenDepartamentos> load_gendepartamentos ()
 {
     return EntityManagerGeneric.em.createQuery("select d from GenDepartamentos d order by d.nombre asc")
                                    .getResultList();
 }
      
   
   
 public GenUnidadesOrganizacion load_GenUnidadesOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion)
 {
     try{
     genUnidadesOrganizacion=(GenUnidadesOrganizacion)EntityManagerGeneric.em.createQuery("select uo from GenUnidadesOrganizacion uo where uo.id=(select max(uo2.id) from GenUnidadesOrganizacion uo2)")
       
                                                     .getSingleResult();
     
     }catch(Exception e)
     {
       
         genUnidadesOrganizacion=null;
     }
     return genUnidadesOrganizacion;
     
 }
}
