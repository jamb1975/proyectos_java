/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.ConComprobanteGastos;
import model.ConDetallesComprobanteContabilidad;
import model.ConsecutivoComprobanteGastos;

/**
 *
 * @author adminlinux
 */
public class ConComprobanteGastosController {
    
public void create(ConComprobanteGastos conComprobanteGastos )
{
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.getRollbackOnly();
    }
  
        conComprobanteGastos.setConsecutivoComprobanteGasto(new ConsecutivoComprobanteGastos());
    
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(conComprobanteGastos);
    EntityManagerGeneric.et.commit();
}
public ConComprobanteGastos edit(ConComprobanteGastos conComprobanteGastos)
{
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.getRollbackOnly();
    }
    EntityManagerGeneric.et.begin();
    conComprobanteGastos=EntityManagerGeneric.em.merge(conComprobanteGastos);
    EntityManagerGeneric.et.commit();
    return conComprobanteGastos;
}
public List<ConDetallesComprobanteContabilidad> findconComprobanteGastos(Long id)
     {
         return EntityManagerGeneric.em.createQuery("select cdc from ConDetallesComprobanteContabilidad cdc where cdc.conComprobanteGastos.id=:idcg")
                                         .setParameter("idcg", id)
                                         .getResultList();
                                         
     }

public List<ConComprobanteGastos> li_conConComprobanteGastos(String search)
{
    List<ConComprobanteGastos> li_cg;
   String searchpattern=search==null?"%":search+"%";
    li_cg=EntityManagerGeneric.em.createQuery("select cg FROM ConComprobanteGastos cg where (lower(cg.genPersonas.primerNombre) like :search or lower(cg.genPersonas.documento) like :search)  order by cg.fechaelaboracion")
                                 .setParameter("search", searchpattern)
                                 .getResultList();
    return li_cg;

}
}
