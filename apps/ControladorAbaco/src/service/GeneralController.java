/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.UnidadesMedida;


/**
 *
 * @author adminlinux
 */
public class GeneralController 
{
 public List<UnidadesMedida> load_genunidadesmedida ()
 {
     return EntityManagerGeneric.em.createQuery("select um from UnidadesMedida um order by um.sigla")
                                    .getResultList();
 }
  
}
