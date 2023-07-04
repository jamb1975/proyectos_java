/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modelo.HclCups;
import modelo.Producto;

/**
 *
 * @author adminlinux
 */
public class HclCupsController {
    
    public Producto edit(Producto producto)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        producto=EntityManagerGeneric.em.merge(producto);
        EntityManagerGeneric.et.commit();
        return producto;
    }
    public HclCups edit(HclCups hclCups)
    {
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
        hclCups=EntityManagerGeneric.em.merge(hclCups);
        EntityManagerGeneric.et.commit();
        return hclCups;
    }
    
}
