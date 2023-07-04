/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.text.ParseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import modelo.GenPersonas;

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
    
  
 
    @Override
    protected EntityManager getEntityManager() {
        return EntityManagerGeneric.em;
    }
    
}
