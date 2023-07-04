/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modelo.NominaEmpleados;

/**
 *
 * @author adminlinux
 */
public class NominaEmpleadosController {
    
    public NominaEmpleados update(NominaEmpleados nominaEmpleados)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.merge(nominaEmpleados);
        EntityManagerGeneric.et.commit();
        return nominaEmpleados;
    }   
}
