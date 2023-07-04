/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import java.util.List;
import modelo.AgnCitas;
import modelo.ConComprobanteProcedimiento;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.GenTiposUsuarios;
import modelo.RptFacturasDTO;
import service.FacturaController;
import sihic.SihicApp;

/**
 *
 * @author karolyani
 */
public class FacturaControllerClient {

    private static FacturaController facturaController;

    public FacturaControllerClient() {
        facturaController = new FacturaController(Factura.class);
    }

    public boolean crearFactura(Factura factura) {
        facturaController = null;
        facturaController = new FacturaController(Factura.class);
        return facturaController.editfactura(factura, SihicApp.consecutivosFacturasPorSucursal);
      
    }

    public Factura findfacturapornumeroytipo(Long nofactura, String tipo) {

        return facturaController.findfacturapornumeroytipo(nofactura, tipo);

    }

    public static Factura guardarFactura(Factura factura) {
        facturaController = null;
        facturaController = new FacturaController(Factura.class);
        factura = facturaController.guardarFactura(factura);
        return factura;
    }

    public List<Factura> getFacturasACredito(String search) {
        return facturaController.getFacturasACredito(search);
    }

    

    public Factura FindLAstFacturaEps(AgnCitas agnCitas, String prefijo) {
        return facturaController.FindLastFacturaEps(agnCitas, prefijo);
    }

    public void saveComprobante(ConComprobanteProcedimiento conComprobanteProcedimiento) {
        facturaController.saveComprobante(conComprobanteProcedimiento);
    }

    public List<Factura> facturasacerrar(String datefrom, String dateto, String search, boolean estado) throws ParseException {
        return facturaController.facturasacerrar(datefrom, dateto, search, estado);
    }

    public List<FacturaItem> facturasripsprefijop(String datefrom, String dateto, String search, String prefijo, String nit, GenTiposUsuarios genTiposUsuarios) throws ParseException {
        return facturaController.facturasprefijotipop(datefrom, dateto, search, prefijo, nit, genTiposUsuarios);
    }

    public List<Factura> facturasacartera(String datefrom, String dateto, String search) throws ParseException {

        return facturaController.facturasacartera(datefrom, dateto, search);
    }

    public List<Factura> facturascerradasrips(String datefrom, String dateto, String search, String prefijo, String nit, GenTiposUsuarios genTiposUsuarios) throws ParseException {
    System.out.println(" Rips-->"+prefijo.substring(0,1));
        return facturaController.facturasacerradasrips(datefrom, dateto, search, prefijo.substring(0,1), nit, genTiposUsuarios);
    }

    public List<RptFacturasDTO> facturas(String datefrom, String dateto, int segunmonto) throws ParseException {
        return facturaController.facturas(datefrom, dateto, segunmonto);
    }
    public List<Factura> facturaselectronicas(String datefrom, String dateto) throws ParseException {
        return facturaController.facturaselectronicas(datefrom, dateto);
    }
    public void cerrarfactura(Factura factura) {
        facturaController.cerrarfactura(factura);
    }

    public void abrirfactura(Factura factura) {
        facturaController.abrirfactura(factura);
    }

    public void close() {
        facturaController = null;
    }

    public List<Factura> getRecords(String datefrom, String dateto, String search, String prefijo) throws ParseException {
        return facturaController.getRecords(datefrom, dateto, search, prefijo);
    }

    public int validarfacturatotalcero(String prefijo) {
        return facturaController.validarfacturatotalcero(prefijo);
    }

    public List<Factura> getrecordscartera(String search) {
        return facturaController.getrecorscartera(search);
    }

    public List<Factura> getRecordsfind(String search) {
        return facturaController.getrecordsfind(search);
    }
    
     public List<Factura> cartera(String prefijo,String fechadesde,String fechato,String noident,String nofactura) throws ParseException
   {
      return facturaController.cartera(prefijo,fechadesde, fechato, noident, nofactura);
   } 
    public List<Factura> findfacturasportipousuarioyfecha(String datefrom,String todate,int tipo) throws ParseException
   {
      return facturaController.findfacturasportipousuarioyfecha(datefrom, todate, tipo);
   }  
     public List<Factura> findfacturasporprefijoyfecha(String datefrom,String todate,String pref) throws ParseException
   {
      return facturaController.findfacturasporprefijoyfecha(datefrom, todate, pref);
   }  
     public static void findcomprobanteporno(String no) throws ParseException
   {
     
      
   }   
}
