/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import model.ConsecutivoFactura;


/**
 *
 * @author innsend
 */
public class ConsecutivoNoFacturaController {
    
    public List<ConsecutivoFactura> getRecords()
    {
        return EntityManagerGeneric.em.createQuery("select cf from ConsecutivoFactura cf")
                                       .getResultList();
    }
}
