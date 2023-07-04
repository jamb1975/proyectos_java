/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import service.ConsecutivoNoFacturaController;
import abaco.AbacoApp;


/**
 *
 * @author innsend
 */
public class ConsecutivoNoFacturaControllerClient {

    private ConsecutivoNoFacturaController consecutivoNoFacturaController;

    public ConsecutivoNoFacturaControllerClient() {
        consecutivoNoFacturaController = new ConsecutivoNoFacturaController();
    }

    public void getRecords() {
        try {
            AbacoApp.li_consecutivofactura = consecutivoNoFacturaController.getRecords();
            AbacoApp.consecutivoFactura = AbacoApp.li_consecutivofactura.get(0);
           
        } catch (Exception e) {

        }
    }
}
