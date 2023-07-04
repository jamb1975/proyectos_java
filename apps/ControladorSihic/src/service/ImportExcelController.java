/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.math.BigDecimal;
import modelo.ConComprobanteProcedimiento;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.GenPacientes;
import modelo.Producto;

/**
 *
 * @author adminlinux
 */
public class ImportExcelController {
    
    public GenPacientes  verificarpacientes(String documento)
    {
        try
        {
        return (GenPacientes)EntityManagerGeneric.em.createQuery("select p from GenPacientes p where p.genPersonas.documento=:pdoc")
                                       .setParameter("pdoc", documento)
                                       .setMaxResults(0)
                                       .getSingleResult();
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public ConComprobanteProcedimiento verificarcomprobante(Long nocomprobante)
    {
        try
        {
        return (ConComprobanteProcedimiento)EntityManagerGeneric.em.createQuery("select cp from ConComprobanteProcedimiento cp where cp.consecutivoComprobanteProcedimiento=:pnc")
                                       .setParameter("pnc", nocomprobante)
                                       .setMaxResults(0)
                                       .getSingleResult();
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public Factura verificarfactura(Long nofactura,String prefijo)
    {
        try
        {
        return (Factura)EntityManagerGeneric.em.createQuery("select f from Factura f where f.no_factura=:pnf and f.prefijo=:ppref")
                                       .setParameter("pnf", nofactura)
                                       .setParameter("ppref", prefijo)
                                       .getSingleResult();
        }
        catch(Exception e)
        {
           
            return null;
        }
    }
    public FacturaItem verificarfacturaitem(Long nocomprobante,String codigo)
    {
        try
        {
        return (FacturaItem)EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.conComprobanteProcedimiento.id=:pnc and fi.producto.codigo=:pcod")
                                       .setParameter("pnc", nocomprobante)
                                       .setParameter("pcod", codigo)
                                       .getSingleResult();
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public void verificarproducto(Producto p)
    {
        try
        {
     Producto   p2=  (Producto)EntityManagerGeneric.em.createQuery("select p from Producto p where p.codigo=:pcod")
                                       .setParameter("pcod", p.getCodigo())
                                       .setMaxResults(1)
                                       .getSingleResult();
        }
        catch(Exception e)
        {
            EntityManagerGeneric.et.begin();
            EntityManagerGeneric.em.persist(p);
            EntityManagerGeneric.et.commit();
        }
    }
    public void savefactura(Factura f,FacturaItem fi2)
    {
        
        if(EntityManagerGeneric.et.isActive())
        {
           EntityManagerGeneric.et.rollback();
        }
        EntityManagerGeneric.et.begin();
          f=EntityManagerGeneric.em.merge(f);
          fi2.setFactura(f);
         fi2=EntityManagerGeneric.em.merge(fi2);
        f=(Factura)EntityManagerGeneric.em.createQuery("select f from Factura f where f.id=:pid")
                              .setParameter("pid", f.getId())
                              .getSingleResult();
        EntityManagerGeneric.et.commit();
        BigDecimal total=BigDecimal.ZERO;
        for(FacturaItem fi:f.getFacturaItems())
        {
            total=total.add(fi.getValor_total());
            System.out.println("total->"+total);
        }
        f.setTotalAmount(total);
          EntityManagerGeneric.et.begin();
        f=EntityManagerGeneric.em.merge(f);
        EntityManagerGeneric.et.commit();
    }
}
