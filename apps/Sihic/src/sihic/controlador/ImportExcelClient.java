/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import service.ImportExcelController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class ImportExcelClient {
    
    ImportExcelController importExcelController;
    public ImportExcelClient()
    {
        importExcelController=new ImportExcelController();
    }
    public void verificarpersona(String documento)
    {
        SihicApp.genPacientesTemp=importExcelController.verificarpacientes(documento);
    }
     public void verificarcomprobante(Long nocomprobante)
    {
        SihicApp.conComprobanteProcedimiento=importExcelController.verificarcomprobante(nocomprobante);
    }
     public void verificarfactura(Long nofactura,String prefijo)
    {
        SihicApp.s_factura=importExcelController.verificarfactura(nofactura,prefijo);
    }
       public void  verificarfacturaitem(Long nocomprobante, String codigo)
      {
        SihicApp.facturaItem=importExcelController.verificarfacturaitem(nocomprobante,codigo);
      }
       public void  verificarproducto()
      {
        importExcelController.verificarproducto(SihicApp.s_producto);
      }
       public void savefactura()
       {
           importExcelController.savefactura(SihicApp.s_factura,SihicApp.facturaItem);
       }
}
