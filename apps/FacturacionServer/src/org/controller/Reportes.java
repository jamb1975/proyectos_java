/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.controller;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Ventas_Totales_Mes_View;

/**
 *
 * @author karol
 */
@Named
@Stateless
public class Reportes implements Serializable {
@PersistenceContext(unitName = "FacturacionServerPU")
private EntityManager em; 
private List<Ventas_Totales_Mes_View> reportes_ventas=null;
public List<Ventas_Totales_Mes_View> getReportes_ventas() {
reportes_ventas=em.createQuery("select vt from Ventas_Totales_Mes_View vt")
                                      .getResultList();
        System.out.println("Reporte celular");
        return reportes_ventas;
    }


}
