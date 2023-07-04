/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import abaco.AbacoApp;
import java.text.ParseException;
import java.util.List;
import model.ConsecutivoFactura;
import model.Factura;
import model.Producto;
import service.FacturaController;

/**
 *
 * @author karolyani
 */
public class FacturaControllerClient {
    private FacturaController facturaController;
    public FacturaControllerClient()
    {
        facturaController=new FacturaController(Factura.class);
    }
  public Factura addproducto(Factura factura,Producto producto)
  {   
      factura=facturaController.edit(factura,producto);
      return factura; 
  }
  public Factura guardarFactura(Factura factura)
  {   facturaController=null;
      facturaController=new FacturaController(Factura.class);
      factura=facturaController.guardarFactura(factura);
      return factura; 
  }
  public Factura nuevaFactura(Factura factura,ConsecutivoFactura consecutivoFactura)
  {   facturaController=null;
      facturaController=new FacturaController(Factura.class);
      factura=facturaController.nuevafactura(factura,consecutivoFactura);
      return factura; 
  }
  public List<Factura> getFacturasACredito(String search)
  {
      return facturaController.getFacturasACredito(search);
  }
   public  List<Factura> getOrdenesGuia(String from_date,String to_date,String search) throws ParseException
    {
        return facturaController.getOrdenesGuia(from_date, to_date, search);
    }
   public  Factura next(Factura f,int id)
   {
       return facturaController.FNext(f,id);
   }
   public  Factura previous(Factura f,int id)
   {
       return facturaController.FPrevious(f,id);
   }
   public void close() {
       facturaController=null;
    }
   
   public List<Factura> getRecords(String search) throws ParseException
   {
       return facturaController.getRecords(search);
   }
   public Factura update()
   {
       return facturaController.update(AbacoApp.s_factura);
   }
   public List<Factura> cartera(String search)
   {
      return facturaController.cartera(search);
   }  
   public Factura getlastfactura()
   {
       return facturaController.getlastfactura();
   }       
}
