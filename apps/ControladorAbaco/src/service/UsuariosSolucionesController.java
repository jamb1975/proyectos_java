/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import model.Solucion;
import model.Personas;
import model.UsuarioSoluciones;
import model.Usuarios;

/**
 *
 * @author karolyani
 */
public class UsuariosSolucionesController extends AbstractFacade<UsuarioSoluciones>{
 private static final String FIND_TERCEROS_QUERY = 
            "select u from Usuarios u "+
            "where  (lower(u.genPersonas.nombre) like :search "+ 
            " or u.genPersonas.nombre like :search )order by u.genPersonas.nombre";
    public UsuariosSolucionesController(Class<UsuarioSoluciones> entityClass) {
        super(entityClass);
    }

    public void create(UsuarioSoluciones entity) {
        if(EntityManagerGeneric.et.isActive())
        {
           EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        super.create(entity);
         EntityManagerGeneric.et.commit();
    }
     public void remove(Solucion s) {
        if(EntityManagerGeneric.et.isActive())
        {
           EntityManagerGeneric.et.rollback();
        } 
        EntityManagerGeneric.et.begin();
        UsuarioSoluciones us=(UsuarioSoluciones)EntityManagerGeneric.em.createQuery("select us from UsuarioSoluciones us where us.solucion=:s")
                                                    .setParameter("s", s)
                                                    .setMaxResults(1)
                                                     .getSingleResult();
        EntityManagerGeneric.em.remove(us);
         EntityManagerGeneric.et.commit();
    }

    public List<Solucion> getSOluciones()
    {
      return  EntityManagerGeneric.em.createQuery("select s from Solucion s where s.solucionPadre.numeral=0 order by s.solucion")
                            .getResultList();
    }
    public List<Solucion> getSoluciones(Solucion s)
    {
      return  EntityManagerGeneric.em.createQuery("select s from Solucion s where s.solucionPadre=:sp order by s.solucion")
                                            .setParameter("sp", s)
                                           .getResultList();
    }
    public List<UsuarioSoluciones> getUsuarioSoluciones(String usua,String password)
    {
        try
        {
             Usuarios usuario=(Usuarios)EntityManagerGeneric.em.createQuery("select u from Usuarios u where u.usuario=:usua and u.password=:pass")
                                                .setParameter("usua", usua)
                                                .setParameter("pass",password)
                                                .getSingleResult();
    
      return  EntityManagerGeneric.em.createQuery("select us from UsuarioSoluciones us where us.usuario=:usua order by us.solucion.numeral")
                                           .setParameter("usua", usuario)
                                           .getResultList();
        }
        catch(Exception e)
        {
            
            return null;
    }
   
                         
    }
      public List<UsuarioSoluciones> getUsuarioSoluciones(Usuarios usuario)
    {
        try
        {
            
    
      return  EntityManagerGeneric.em.createQuery("select us from UsuarioSoluciones us where us.usuario=:usua order by us.solucion.solucion")
                                           .setParameter("usua", usuario)
                                           .getResultList();
        }
        catch(Exception e)
        {
            return null;
    }
   
                         
    }
     public List<Usuarios> findUsuario( String search) {
      String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
          List<Usuarios> usuarios;
         
          usuarios= EntityManagerGeneric.em.createQuery("select u from Usuarios u "+
            "where  (lower(u.genPersonas.no_ident) like :search "+ 
            " or lower(u.genPersonas.nombre) like :search ) order by u.genPersonas.nombre")
                 .setParameter("search", searchpattern)
                 .setMaxResults(11)
                 .getResultList();
         return usuarios;
    }
   
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
   
}
