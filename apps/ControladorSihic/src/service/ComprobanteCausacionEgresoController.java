/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import modelo.ComprobanteCausacionEgreso;
import modelo.LibroAuxiliar;

/**
 *
 * @author adminlinux
 */
public class ComprobanteCausacionEgresoController {
    
public void create(ComprobanteCausacionEgreso conComprobanteGastos )
{
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.getRollbackOnly();
    }
   
    EntityManagerGeneric.et.begin();
    EntityManagerGeneric.em.persist(conComprobanteGastos);
    EntityManagerGeneric.et.commit();
}
public ComprobanteCausacionEgreso edit(ComprobanteCausacionEgreso conComprobanteGastos)
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
public List<LibroAuxiliar> findconComprobanteGastos(Long id)
     {
         return EntityManagerGeneric.em.createQuery("select cdc from ConDetallesComprobanteContabilidad cdc where cdc.conComprobanteGastos.id=:idcg")
                                         .setParameter("idcg", id)
                                         .getResultList();
                                         
     }

public List<ComprobanteCausacionEgreso> li_conConComprobanteGastos(String search)
{
    List<ComprobanteCausacionEgreso> li_cg;
   String searchpattern=search==null?"%":search+"%";
    li_cg=EntityManagerGeneric.em.createQuery("select cg FROM ConComprobanteGastos cg where (lower(cg.genPersonas.primerNombre) like :search or lower(cg.genPersonas.documento) like :search)  order by cg.fechaelaboracion")
                                 .setParameter("search", searchpattern)
                                 .getResultList();
    return li_cg;

}
}
