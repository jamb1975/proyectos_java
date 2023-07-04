/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import service.ConsecutivoNoFacturaPorSucursalController;
import sihic.SihicApp;
import sihic.general.LoadChoiceBoxGeneral;

/**
 *
 * @author innsend
 */
public class ConsecutivoNoFacturaPorSucursalControllerClient {

    private ConsecutivoNoFacturaPorSucursalController consecutivoNoFacturaPorSucursalController;

    public ConsecutivoNoFacturaPorSucursalControllerClient() {
        consecutivoNoFacturaPorSucursalController = new ConsecutivoNoFacturaPorSucursalController();
    }

    public void getRecords() {
        try {
            SihicApp.li_coConsecutivosFacturasPorSucursals = consecutivoNoFacturaPorSucursalController.getRecords(SihicApp.sucursales);
            SihicApp.consecutivosFacturasPorSucursal = SihicApp.li_coConsecutivosFacturasPorSucursals.get(0);
            LoadChoiceBoxGeneral.Load_ConsecutivosNofacturasPorSucursal();
        } catch (Exception e) {
             LoadChoiceBoxGeneral.Load_prefijoscontador();
        }
    }
}
