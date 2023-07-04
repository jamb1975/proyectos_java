/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.text.ParseException;
import java.util.List;
import model.ConComprobanteContabilidad;
import model.ConComprobanteIngreso;
import model.ConDetallesComprobanteContabilidad;
import model.Factura;
import model.FacturaItem;
import service.ConComprobanteIngresoController;

/**
 *
 * @author adminlinux
 */
public class ConComprobanteIngresoControllerClient {
 ConComprobanteIngresoController conComprobanteIngresoController;
 private List<FacturaItem> facturaitem;
    public ConComprobanteIngresoControllerClient() {
        conComprobanteIngresoController=null;
         conComprobanteIngresoController=new ConComprobanteIngresoController(ConComprobanteIngreso.class);
    }
      
    public void create(ConComprobanteIngreso comprobanteIngreso)
    {
        conComprobanteIngresoController.create(comprobanteIngreso);
    }
     public ConComprobanteIngreso edit(ConComprobanteIngreso comprobanteIngreso,Factura f) throws ParseException
    {
       return comprobanteIngreso= conComprobanteIngresoController.edit(comprobanteIngreso,f);
    }
      public ConComprobanteIngreso editFacturaItem(ConComprobanteIngreso comprobanteIngreso)
    {
       return comprobanteIngreso= conComprobanteIngresoController.editFacturaItem(comprobanteIngreso);
    } 
     public List<FacturaItem> findfacturaitem(String search)
    {
       return conComprobanteIngresoController.findfacturaitem(search);
    }
      public List<Factura> findfactura(String search)
    {
       return conComprobanteIngresoController.findfactura(search);
    }
      public List<ConDetallesComprobanteContabilidad> findconComprobanteIngresos(ConComprobanteContabilidad conComprobanteContabilidad)
    {
       return conComprobanteIngresoController.findconComprobanteIngresos(conComprobanteContabilidad);
    }
       public List<ConComprobanteIngreso> getRecords(String fromdate,String todate,String search) throws ParseException
    {
        return conComprobanteIngresoController.getRecords(fromdate, todate, search);
    }  
     
}
