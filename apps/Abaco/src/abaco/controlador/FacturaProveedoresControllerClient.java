/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import abaco.AbacoApp;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import model.AbonoProveedores;
import model.ConComprobanteEgreso;
import model.FacturaItemProveedores;
import model.FacturaProveedores;
import model.Producto;
import service.FacturaProveedoresController;

/**
 *
 * @author karolyani
 */
public class FacturaProveedoresControllerClient {
    private FacturaProveedoresController facturaProveedoresController;
    public FacturaProveedoresControllerClient()
    {
        facturaProveedoresController=new FacturaProveedoresController(FacturaProveedores.class);
    }
 public FacturaProveedores crearFactura(FacturaProveedores facturaProveedores,String lote, LocalDate fechavencimiento)
  {   
      facturaProveedores=facturaProveedoresController.editFactura(facturaProveedores,lote,  fechavencimiento);
      return facturaProveedores; 
  }
  public FacturaProveedores guardarFactura(FacturaProveedores facturaProveedores)
  {   
      facturaProveedores=facturaProveedoresController.guardarFactura(facturaProveedores);
      return facturaProveedores; 
  }
  public List<FacturaProveedores> getFacturasACredito(String search)
  {
      return facturaProveedoresController.getFacturasACredito(search);
  }
   public FacturaProveedores getFacturaProveedor(Long no_factura)
  {
      return facturaProveedoresController.findFactura(no_factura);
  }
   public List<FacturaItemProveedores> getLastItemsmateriaPrima(String search)
  {
      return facturaProveedoresController.findLastItems(search);
  }
   public void save_ItemMateriaPrima(FacturaItemProveedores fip)
   {
       facturaProveedoresController.save_ItemMateriaPrima(fip);
   }
   public List<FacturaItemProveedores> getItemsProveedor( String datefrom,  String dateto, String search) throws ParseException 
       {
           return facturaProveedoresController.getItemsProveedor(datefrom, dateto, search);
       }
   public List<FacturaItemProveedores> getPedidos( String datefrom,  String dateto, String search) throws ParseException 
       {
           return facturaProveedoresController.getPedidos(datefrom, dateto, search);
       }
   public List<AbonoProveedores> getAbonos( String datefrom,  String dateto, String search) throws ParseException 
       {
           return facturaProveedoresController.getAbonos(datefrom, dateto, search);
       }
    public List<ConComprobanteEgreso> getPagos( String datefrom,  String dateto, String search) throws ParseException 
       {
           return facturaProveedoresController.getFacturasPagos(datefrom, dateto, search);
       }
   public List<FacturaProveedores> getRecords(String datefrom,  String dateto, String search,int formapago) throws ParseException
  {
      return facturaProveedoresController.getRecords(datefrom, dateto, search,formapago);
  }
    public FacturaProveedores SaveAbonoFactura(FacturaProveedores facturaProveedores)
  {   
      facturaProveedores=facturaProveedoresController.SaveAbonoFactura(facturaProveedores);
      return facturaProveedores; 
  }
   public void close() {
       facturaProveedoresController=null;
    }
  public FacturaProveedores update()
  {
      return facturaProveedoresController.update(AbacoApp.s_facturaproveedores);
  }
   public void delete()
  {
       facturaProveedoresController.delete(AbacoApp.s_facturaproveedores);
  }
  public  void getlastitemfactura(Producto producto,float cantidad)
  {
      facturaProveedoresController.getlastitemfactura(producto, cantidad);
  }
  public List<FacturaProveedores> deudasaproveedores(String search)
   {
       return facturaProveedoresController.deudasproveedores(search);
   }     
}
