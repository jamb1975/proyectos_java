/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.text.ParseException;
import java.util.List;
import model.ConComprobanteEgreso;
import model.ConDetallesComprobanteContabilidad;
import model.FacturaItem;
import model.FacturaProveedores;
import service.ConComprobanteEgresoController;

/**
 *
 * @author adminlinux
 */
public class ConComprobanteEgresoControllerClient {
 ConComprobanteEgresoController conComprobanteEgresoController;
 private List<FacturaItem> facturaitem;
    public ConComprobanteEgresoControllerClient() {
        conComprobanteEgresoController=null;
        conComprobanteEgresoController=new ConComprobanteEgresoController(ConComprobanteEgreso.class);
    }
      
    public void create(ConComprobanteEgreso comprobanteEgreso)
    {
        conComprobanteEgresoController.create(comprobanteEgreso);
    }
     public ConComprobanteEgreso edit(ConComprobanteEgreso comprobanteEgreso,FacturaProveedores fp)
    {
       return comprobanteEgreso= conComprobanteEgresoController.edit(comprobanteEgreso,fp);
    } 
     public List<FacturaItem> findfacturaitem(String search)
    {
       return facturaitem= conComprobanteEgresoController.findfacturaitem(search);
    }
      public List<ConDetallesComprobanteContabilidad> findconComprobanteEgresos(Long id)
    {
       return conComprobanteEgresoController.findconComprobanteEgresos(id);
    }
          public List<ConComprobanteEgreso> getRecords(String fromdate,String todate,String search) throws ParseException
    {
        return conComprobanteEgresoController.getRecords(fromdate, todate, search);
    }  
     
}
