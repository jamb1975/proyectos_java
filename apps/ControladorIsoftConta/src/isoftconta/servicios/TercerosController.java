/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import modelo.EntidadesStatic;
import static modelo.EntidadesStatic.as_terceros;
import modelo.Terceros;



/**
 *
 * @author adminlinux
 */
public class TercerosController extends AbstractFacade<Terceros>{

    public TercerosController(Class<Terceros> entityClass) {
        super(entityClass);
    }
public static int servicio_crear()
{
    try
    {
     if(EntityManagerGeneric.et.isActive())
     {
          EntityManagerGeneric.et.rollback(); 
     }
           
                    
                  
                  
               EntityManagerGeneric.et.begin();
               EntityManagerGeneric.em.persist(EntidadesStatic.es_terceros);
               EntityManagerGeneric.et.commit();
               return 1;
    }catch(Exception e)
    {
        e.printStackTrace();
        return -1;
    }
}
   
 
public static int servicio_actualizar()
{
     if(EntityManagerGeneric.et.isActive())
          {
             EntityManagerGeneric.et.rollback();
          }
     
     try
     {
           EntityManagerGeneric.et.begin();
          
           EntityManagerGeneric.em.merge(EntidadesStatic.es_terceros);
                          
              EntityManagerGeneric.et.commit();
              return 1;
     }catch(Exception e)
     {
         return -1;
     }         
}
 public static List<Terceros> getGenpersonas(String search)
    {
       String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
     
  return EntityManagerGeneric.em.createQuery("select gp from Terceros gp where (lower(gp.primernombre) like :search " 
                                             + "  or lower(gp.primerapellido)like :search  "
                                             +" or lower(gp.segundoapellido)like :search "
                                        +" or gp.no_ident like :search)"
                                            + "  order by gp.primerapellido,gp.segundoapellido,gp.primernombre" )
                                             .setParameter("search", searchpattern)
                                             .setMaxResults(20)
                                            .getResultList();
} 
 
 
public static Terceros findbyident(String no_ident) 
{
        Terceros genPersonas;
        try{
      genPersonas=(Terceros)EntityManagerGeneric.em.createQuery("select gp from Terceros gp where gp.no_ident=:ident")
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
    public static List<Terceros> getrecordsterceros(String buscar) throws ParseException
    {
        String search=buscar==null?"%":'%'+buscar.toLowerCase().replace('*', '%')+'%';
        return EntityManagerGeneric.em.createQuery("select  t from Terceros t where (t.no_ident like :search or lower(CONCAT(t.primernombre,t.primerapellido,t.segundoapellido)) like :search )  order by t.primernombre,t.primerapellido asc")
                                                                                    .setParameter("search", search)
                                                                                    .setMaxResults(7)
                                                                                    .getResultList();
    } 
 public static void load_asterceros(String buscar) throws ParseException
{
    if(!buscar.contains("="))
    {
      
    EntidadesStatic.li_terceros=getrecordsterceros(buscar);
  
    if(EntidadesStatic.li_terceros!=null)
   {
      
     if(EntidadesStatic.li_terceros.size()>0)
   {
         System.out.println("size terceros->"+EntidadesStatic.li_terceros.size());  
        int i=0;
   
   for(Terceros t:EntidadesStatic.li_terceros)
   {
       EntidadesStatic.as_terceros[i]=t.getNo_ident()+"="+t.getNombres();
       i++;
   }
    EntidadesStatic.possibleSuggestions=null;
   EntidadesStatic.possibleSuggestions = new HashSet<>(Arrays.asList(as_terceros));
    }
   }
    }
   
}
public  static void findtercerospornoident(String no_ident)
{
   String[] _as_terceros=no_ident.split("=");

   if(_as_terceros.length>0 && EntidadesStatic.li_terceros!=null)
   {
    
   List<Terceros> _li_terceros=EntidadesStatic.li_terceros.stream().filter(e-> (e.getNo_ident().equals(_as_terceros[0]))).collect(Collectors.toList());
   if(_li_terceros.size()>0)
   {
     EntidadesStatic.es_terceros=_li_terceros.get(0);
   }
   }
}     
}
