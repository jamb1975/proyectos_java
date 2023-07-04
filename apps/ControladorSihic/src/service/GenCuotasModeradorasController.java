/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Date;
import java.util.List;
import modelo.GenCuotasModeradorasEapb;
import modelo.GenEapb;
import modelo.GenNivelesUsuarios;

/**
 *
 * @author adminlinux
 */
 
public class GenCuotasModeradorasController {
 private GenEapb genEapb;   
    
    
    public GenEapb save(GenEapb eapb)
    {
        try
        {
       genEapb=eapb;
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.commit();
       }
       EntityManagerGeneric.et.begin();
      genEapb= EntityManagerGeneric.em.merge(genEapb);
      EntityManagerGeneric.et.commit();
        }catch(Exception e)
        {
            genEapb= new GenEapb();
        }
      return genEapb;
    }
    public GenEapb addCuotasModeradoras(String search)
    {
         try
        {
       genEapb=(GenEapb)EntityManagerGeneric.em.createQuery("select e FROM GenEapb e where e.nombre like :search")
                                            .setParameter("search", search)
                                             .setMaxResults(1)
                                             .getSingleResult();
     
        List<GenNivelesUsuarios> li_gennivelesusuarios=EntityManagerGeneric.em.createQuery("select nu FROM GenNivelesUsuarios nu order by nu.nivel")
                                                                              .getResultList();
        GenCuotasModeradorasEapb  cuotasModeradorasEapb;
        if(genEapb.getLi_genCuotasModeradorasEapbs().size()<li_gennivelesusuarios.size())
        {
        for(GenNivelesUsuarios nu:li_gennivelesusuarios)
        {
            cuotasModeradorasEapb=null;
            cuotasModeradorasEapb=new GenCuotasModeradorasEapb();
            cuotasModeradorasEapb.setFechaCreacion(new  Date());
            cuotasModeradorasEapb.setGenEapb(genEapb);
            cuotasModeradorasEapb.setGenNivelesUsuarios(nu);
            genEapb.addCuotaModeradora(cuotasModeradorasEapb);
            
        }
        EntityManagerGeneric.et.begin();
        if(genEapb.getId()==null)
        {
         EntityManagerGeneric.em.persist(genEapb);
        }
        else
        {
           EntityManagerGeneric.em.persist(genEapb);  
        }
        EntityManagerGeneric.et.commit();
        }
       
        }catch(Exception e)
        {
            e.printStackTrace();
            genEapb=new GenEapb();
        }
      return genEapb;          
                
    

}
}
