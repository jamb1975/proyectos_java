/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import modelo.GenTiposUsuarios;

/**
 *
 * @author adminlinux
 */
public class GenTiposUsuariosController {
    
    public List<GenTiposUsuarios> li_gentiposusuarios()
    {
        return EntityManagerGeneric.em.createQuery("select tu FROM GenTiposUsuarios tu order by cast(tu.codigo as integer) desc")
                                                    .getResultList();
    }
    public void create(GenTiposUsuarios genTiposUsuarios)
    {
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(genTiposUsuarios);
        EntityManagerGeneric.et.commit();
    }
    public GenTiposUsuarios edit(GenTiposUsuarios genTiposUsuarios)
    {
       EntityManagerGeneric.et.begin();
       genTiposUsuarios=EntityManagerGeneric.em.merge(genTiposUsuarios);
       EntityManagerGeneric.et.commit();
       return genTiposUsuarios;
    }
    
    public void delete(GenTiposUsuarios genTiposUsuarios)
    {
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.remove(genTiposUsuarios);
        EntityManagerGeneric.et.commit();
    }
    public List<GenTiposUsuarios> li_genTiposUsuarios()
{
    return EntityManagerGeneric.em.createQuery("select tu FROM GenTiposUsuarios tu order by tu.codigo")
                                             .getResultList();
}
}   
  
