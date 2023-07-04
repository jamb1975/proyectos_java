/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.text.ParseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import modelo.GenPacientes;
import modelo.GenPersonas;
import modelo.GenProfesiones;
import modelo.GenTiposDocumentos;

import modelo.Usuarios;

/**
 *
 * @author adminlinux
 */
public class GenPersonasController extends AbstractFacade<GenPersonas>{

    public GenPersonasController(Class<GenPersonas> entityClass) {
        super(entityClass);
    }
public void save(GenPersonas gp,GenPacientes gpac)
{
     if(EntityManagerGeneric.et.isActive())
     {
          EntityManagerGeneric.et.rollback();
     }
           EntityManagerGeneric.et.begin();
           GenTiposDocumentos td=EntityManagerGeneric.em.find(GenTiposDocumentos.class,gp.getGenTiposDocumentos().getId());
           gp.setGenTiposDocumentos(td);
                      
              gpac.setGenPersonas(gp);
              EntityManagerGeneric.em.persist(gpac);
                      
              EntityManagerGeneric.et.commit();
}
   
    public GenPacientes edit(GenPacientes genPacientes)
{
    EntityManagerGeneric.et.begin();
    genPacientes=EntityManagerGeneric.em.merge(genPacientes);
    EntityManagerGeneric.et.commit();
    return genPacientes;
}
public void saveusuario(GenPersonas gp,Usuarios usuario)
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
 public List<GenPersonas> getGenpersonas(String search)
    {
       String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
     
  return EntityManagerGeneric.em.createQuery("select gp from GenPersonas gp where (lower(gp.primerNombre) like :search " 
                                             + "  or lower(gp.primerApellido)like :search  "
                                        +"  or lower(gp.segundoNombre)like :search "
                                        +" or lower(gp.segundoApellido)like :search "
                                        +" or gp.documento like :search)"
                                            + "  order by gp.primerApellido,gp.segundoApellido,gp.primerNombre,gp.segundoNombre " )
                                             .setParameter("search", searchpattern)
                                             .setMaxResults(20)
                                            .getResultList();
} 
 
 public List<GenPacientes> getPacientes(String search)
    {
       String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
     
  return EntityManagerGeneric.em.createQuery("select gpac from GenPacientes gpac where (lower(gpac.genPersonas.primerNombre) like :search " 
                                             + "  or lower(gpac.genPersonas.primerApellido)like :search  "
                                        +"  or lower(gpac.genPersonas.segundoNombre)like :search "
                                        +" or lower(gpac.genPersonas.segundoApellido)like :search "
                                        +" or lower(gpac.genPersonas.documento) like :search)"
                                            + "  order by gpac.genPersonas.primerApellido,gpac.genPersonas.segundoApellido,gpac.genPersonas.primerNombre,gpac.genPersonas.segundoNombre " )
                                             .setParameter("search", searchpattern)
                                             .setMaxResults(20)
                                            .getResultList();
} 
public GenPersonas findbyident(String no_ident) 
{
        GenPersonas genPersonas;
        try{
      genPersonas=(GenPersonas)EntityManagerGeneric.em.createQuery("select gp from GenPersonas gp where gp.documento=:ident")
                                     .setParameter("ident", no_ident)
                                     .getSingleResult();
        }catch(Exception e)
        {
            genPersonas=null;
        }
      return  genPersonas;
    }
    
  public List<GenProfesiones> getProfesiones(String search) throws ParseException
  {
  String searchpattern = search==null ? "%":  "%"+ search.toLowerCase()+ "%";
 List<GenProfesiones> list_genprofesiones;
     
        CriteriaBuilder cb = EntityManagerGeneric.em.getCriteriaBuilder();
        CriteriaQuery<GenProfesiones> cq = cb.createQuery(GenProfesiones.class);
        Metamodel m = EntityManagerGeneric.em.getMetamodel();
        EntityType<GenProfesiones> _genprofesiones = m.entity(GenProfesiones.class);
        Root<GenProfesiones> pet = cq.from(_genprofesiones);
        cq.where(cb.or(cb.like(pet.get("codigo"),searchpattern),cb.like(pet.get("descripcion"),searchpattern.toUpperCase())));
        TypedQuery<GenProfesiones> q=null;
        list_genprofesiones=null;
        cq.orderBy(cb.asc(pet.get("codigo")),cb.asc(pet.get("descripcion")));

q=EntityManagerGeneric.em.createQuery(cq);
list_genprofesiones=q.getResultList();

        
        return list_genprofesiones;


  }
 
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
}
