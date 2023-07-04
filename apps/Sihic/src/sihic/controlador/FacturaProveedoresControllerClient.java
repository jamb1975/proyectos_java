/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import sihic.SihicApp;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import modelo.FacturaItemProveedores;
import modelo.FacturaProveedores;
import modelo.GenCategoriasProductos;
import service.FacturaProveedoresController;

/**
 *
 * @author karolyani
 */
public class FacturaProveedoresControllerClient {

    private FacturaProveedoresController facturaProveedoresController;

    public FacturaProveedoresControllerClient() {
        facturaProveedoresController = new FacturaProveedoresController(FacturaProveedores.class);
    }

    public FacturaProveedores crearFactura(FacturaProveedores facturaProveedores, String lote, LocalDate fechavencimiento) {
        facturaProveedores = facturaProveedoresController.editFactura(facturaProveedores, lote, fechavencimiento);
        return facturaProveedores;
    }

    public void  guardarFactura() {
          facturaProveedoresController.guardarFactura(SihicApp.s_FacturaProveedores);
       System.out.println("id fp1->"+SihicApp.s_FacturaProveedores.getId()); 
    }

    public List<FacturaProveedores> getFacturasACredito(String search) {
        return facturaProveedoresController.getFacturasACredito(search);
    }

    public FacturaProveedores getFacturaProveedor(Long no_factura) {
        return facturaProveedoresController.findFactura(no_factura);
    }

    public List<FacturaItemProveedores> getLastItemsmateriaPrima(String search) {
        return facturaProveedoresController.findLastItems(search);
    }

    public void save_ItemMateriaPrima(FacturaItemProveedores fip) {
        facturaProveedoresController.save_ItemMateriaPrima(fip);
    }

    public List<FacturaItemProveedores> getItemsProveedor(String datefrom, String dateto, String search) throws ParseException {
        return facturaProveedoresController.getItemsProveedor(datefrom, dateto, search);
    }

    public List<FacturaItemProveedores> getPedidos(String datefrom, String dateto, String search) throws ParseException {
        return facturaProveedoresController.getPedidos(datefrom, dateto, search);
    }

    public List<FacturaProveedores> getRecords(String datefrom, String dateto, String search, GenCategoriasProductos genCategoriasProductos) throws ParseException {
        return facturaProveedoresController.getRecords(datefrom, dateto, search, genCategoriasProductos);
    }
     public void getRecord() throws ParseException {
       // SihicApp.s_FacturaProveedores= facturaProveedoresController.getRecord(SihicApp.conComprobanteContabilidad.getId());
    }

    public FacturaProveedores SaveAbonoFactura(FacturaProveedores facturaProveedores) {
        facturaProveedores = facturaProveedoresController.SaveAbonoFactura(facturaProveedores);
        return facturaProveedores;
    }

    public void close() {
        facturaProveedoresController = null;
    }

    public FacturaProveedores update() {
        return facturaProveedoresController.update(SihicApp.s_FacturaProveedores);
    }

    public List<FacturaProveedores> getRecordsCartera(String search) {
        return facturaProveedoresController.getRecorsdCartera(search);
    }

}
