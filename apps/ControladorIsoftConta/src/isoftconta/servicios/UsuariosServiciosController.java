/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.util.List;
import javax.persistence.EntityManager;
import modelo.Servicios;
import modelo.UsuarioServicios;
import modelo.Usuarios;

/**
 *
 * @author karolyani
 */
public class UsuariosServiciosController extends AbstractFacade<UsuarioServicios>{
 private static final String FIND_TERCEROS_QUERY = 
            "select u from Usuarios u "+
            "where  (lower(u.genPersonas.nombre) like :search "+ 
            " or u.genPersonas.nombre like :search )order by u.genPersonas.nombre";
    public UsuariosServiciosController(Class<UsuarioServicios> entityClass) {
        super(entityClass);
    }

    public void create(UsuarioServicios entity) {
        if(EntityManagerGeneric.et.isActive())
        {
           EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        super.create(entity);
         EntityManagerGeneric.et.commit();
    }
     public void remove(Servicios s) {
        if(EntityManagerGeneric.et.isActive())
        {
           EntityManagerGeneric.et.rollback();
        } 
        EntityManagerGeneric.et.begin();
        UsuarioServicios us=(UsuarioServicios)EntityManagerGeneric.em.createQuery("select us from UsuarioServicios us where us.solucion=:s")
                                                    .setParameter("s", s)
                                                    .setMaxResults(1)
                                                     .getSingleResult();
        EntityManagerGeneric.em.remove(us);
         EntityManagerGeneric.et.commit();
    }

    public List<Servicios> getSOluciones()
    {
      return  EntityManagerGeneric.em.createQuery("select s from Servicios s where s.solucionPadre.id=:pid order by s.solucion")
              .setParameter("pid",1)
              .getResultList();
    }
    public List<Servicios> getServicioses(Servicios s)
    {
      return  EntityManagerGeneric.em.createQuery("select s from Servicios s where s.solucionPadre=:sp order by s.solucion")
                                            .setParameter("sp", s)
                                           .getResultList();
    }
    public List<UsuarioServicios> getUsuarioServicios(String usua,String password)
    {
        try
        {
             Usuarios usuario=(Usuarios)EntityManagerGeneric.em.createQuery("select u from Usuarios u where u.usuario=:usua and u.password=:pass")
                                                .setParameter("usua", usua)
                                                .setParameter("pass",password)
                                                .getSingleResult();
    
      return  EntityManagerGeneric.em.createQuery("select us from UsuarioServicios us where us.usuario=:usua order by us.solucion.numeral")
                                           .setParameter("usua", usuario)
                                           .getResultList();
        }
        catch(Exception e)
        {
            
            return null;
    }
   
                         
    }
      public List<UsuarioServicios> getUsuarioServicios(Usuarios usuario)
    {
        try
        {
            
    
      return  EntityManagerGeneric.em.createQuery("select us from UsuarioServicios us where us.usuario=:usua order by us.solucion.solucion")
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
            "where  (lower(u.genPersonas.documento) like :search "+ 
            " or lower(concat(u.genPersonas.primerNombre,' ',u.genPersonas.primerApellido))  like :search )order by u.genPersonas.primerNombre")
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
