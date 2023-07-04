/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import model.Personas;
import model.Organizacion;
import model.Usuarios;


/**
 *
 * @author adminlinux
 */
public class GenPersonasController extends AbstractFacade<Personas>{

    public GenPersonasController(Class<Personas> entityClass) {
        super(entityClass);
    }

   
public void create(Personas gp)
{
     if(EntityManagerGeneric.et.isActive())
     {
          EntityManagerGeneric.et.rollback();
     }
           EntityManagerGeneric.et.begin();
               super.create(gp);
              
                              
              EntityManagerGeneric.et.commit();
}
public void saveusuario(Personas gp,Usuarios usuario)
{
     if(EntityManagerGeneric.et.isActive())
          {
             EntityManagerGeneric.et.rollback();
          }
           EntityManagerGeneric.et.begin();
           gp=EntityManagerGeneric.em.merge(gp);
           usuario.setGenPersonas(gp);
           EntityManagerGeneric.em.merge(usuario);
                          
              EntityManagerGeneric.et.commit();
}

    @Override
    public Personas update(Personas gp)
{
     if(EntityManagerGeneric.et.isActive())
     {
          EntityManagerGeneric.et.rollback();
     }
           EntityManagerGeneric.et.begin();
               gp=super.update(gp);
              
                              
              EntityManagerGeneric.et.commit();
              return gp;
}   
  public void delete(Personas gp)
    {
    if(EntityManagerGeneric.et.isActive())
    {
            EntityManagerGeneric.et.rollback();
    }
        gp=EntityManagerGeneric.em.find(Personas.class, gp.getId());
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.remove(gp);
        EntityManagerGeneric.et.commit();
    }    

 public List<Personas> getGenpersonas(String search)
    {
       String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
     
  return EntityManagerGeneric.em.createQuery("select gp from Personas gp where (lower(gp.nombre) like :search "
                                        +" or gp.no_ident like :search)"
                                            + "  order by gp.nombre " )
                                             .setParameter("search", searchpattern)
                                            .getResultList();
} 
 
public List<Personas> getEmpleados(String search)
    {
       String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
     
  return EntityManagerGeneric.em.createQuery("select gp from Personas gp where (lower(gp.nombre)like :search "
                                        +" or gp.no_ident like :search) and gp.tipo_terc=1"
                                            + "  order by gp.nombre " )
                                             .setParameter("search", searchpattern)
                                            .getResultList();
} 
public Personas findbyident(String no_ident) 
{
        Personas genPersonas;
        try{
      genPersonas=(Personas)EntityManagerGeneric.em.createQuery("select gp from Personas gp where gp.no_ident=:ident")
                                     .setParameter("ident", no_ident)
                                     .getSingleResult();
        }catch(Exception e)
        {
            genPersonas=null;
        }
      return  genPersonas;
    }
    
 public List<Personas> getRecords(String search)
    {
      
         String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
        return EntityManagerGeneric.em.createQuery("select gp from Personas gp where (lower(gp.nombre) like :search or gp.no_ident like :search) order by gp.nombre")
                                      .setParameter("search", searchpattern)
                                      .getResultList();
    }    
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
}
