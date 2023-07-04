/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import modelo.ConsecutivosFacturasPorSucursal;
import modelo.Sucursales;

/**
 *
 * @author innsend
 */
public class ConsecutivoNoFacturaPorSucursalController {
    
    public List<ConsecutivosFacturasPorSucursal> getRecords(Sucursales sucursales)
    {
        return EntityManagerGeneric.em.createQuery("select cf from ConsecutivosFacturasPorSucursal cf where cf.sucursales=:suc")
                                       .setParameter("suc", sucursales)
                                       .getResultList();
    }
}
