/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import modelo.ComprobanteIngreso;
import modelo.LibroAuxiliar;

/**
 *
 * @author adminlinux
 */
public class ConDetallesComprobanteContabilidadController {

public void update(LibroAuxiliar conDetallesComprobanteContabilidad)
{
    if(EntityManagerGeneric.et.isActive())
    {
        EntityManagerGeneric.et.rollback();
    }
   
     EntityManagerGeneric.et.begin();
    
    EntityManagerGeneric.em.merge(conDetallesComprobanteContabilidad);
    EntityManagerGeneric.et.commit();
}
public ComprobanteIngreso findconcomprobanteingreso(Long id)
{
    try
    {
    return (ComprobanteIngreso)EntityManagerGeneric.em.createQuery("select ci from ConComprobanteIngreso ci where ci.no_consecutivo=:pid")
                                   .setParameter("pid", id)
                                   .getSingleResult();
                                               
    }catch(Exception e){
        return null;
    }
}
}
