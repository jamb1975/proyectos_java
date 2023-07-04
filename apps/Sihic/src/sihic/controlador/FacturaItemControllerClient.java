/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import java.util.List;
import modelo.ConComprobanteProcedimiento;
import modelo.FacturaItem;
import modelo.GenEapb;
import modelo.Producto;
import modelo.RptCitaEstadisticaDTO;
import service.FacturaItemController;

/**
 *
 * @author adminlinux
 */
public class FacturaItemControllerClient {

    FacturaItemController facturaItemController;

    public FacturaItemControllerClient() {
        facturaItemController = new FacturaItemController();
    }

    public List<FacturaItem> getLi_facturaitems(ConComprobanteProcedimiento conComprobanteProcedimiento) {
        return facturaItemController.getLi_facturaitems(conComprobanteProcedimiento);
    }

    public void create(FacturaItem facturaItem) {
        facturaItemController.create(facturaItem);
    }

    public FacturaItem edit(FacturaItem facturaItem) {
        facturaItem = facturaItemController.edit(facturaItem);
        return facturaItem;
    }

    public List<FacturaItem> getLi_facturaitemsProcedimiento(Long id, int opc) {
        return facturaItemController.getLi_facturaitemsProcedimiento(id, opc);
    }

    public List<FacturaItem> getProcedimientos(Long id, int opc) {
        return facturaItemController.getLi_Procedimientos(id, opc);
    }

    public List<FacturaItem> rptprocedimientos(String datefrom, String dateto, Producto producto, String search) throws ParseException {
        return facturaItemController.rptprocedimientos(datefrom, dateto, producto, search);
    }

    public List<FacturaItem> rptestadisticas(String datefrom, String dateto, Producto producto, String search) throws ParseException {
        return facturaItemController.rptestadisticas(datefrom, dateto, producto, search);
    }

    public List<RptCitaEstadisticaDTO> rptestadisticasDTO(String datefrom, String dateto, Producto producto, String search) throws ParseException {
        return facturaItemController.rptestadisticasDTO(datefrom, dateto, producto, search);
    }
public List<FacturaItem> rptfacturaitemscopago(String datefrom,  String dateto) throws ParseException
{
    return facturaItemController.rptfacturaitemscopago(datefrom, dateto);
   
}
public boolean check_ifexistsnoautorizacion(String noautorizacion)
{
    return facturaItemController.check_ifexistsnoautorizacion(noautorizacion);
}
}
