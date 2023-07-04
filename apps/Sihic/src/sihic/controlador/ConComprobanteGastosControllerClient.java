/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.util.List;
import modelo.ComprobanteCausacionEgreso;
import modelo.LibroAuxiliar;
import service.ComprobanteEgresoController;
import service.ComprobanteCausacionEgresoController;

/**
 *
 * @author adminlinux
 */
public class ConComprobanteGastosControllerClient {

    private ComprobanteCausacionEgresoController conComprobanteGastosController;

    public ConComprobanteGastosControllerClient() {
        conComprobanteGastosController = new ComprobanteCausacionEgresoController();
    }

    public void create(ComprobanteCausacionEgreso conComprobanteGastos) {
        conComprobanteGastosController.create(conComprobanteGastos);
    }

    public ComprobanteCausacionEgreso edit(ComprobanteCausacionEgreso conComprobanteGastos) {
        return conComprobanteGastosController.edit(conComprobanteGastos);
    }

    public List<LibroAuxiliar> findconComprobanteGastos(Long id) {
        return conComprobanteGastosController.findconComprobanteGastos(id);
    }

    public List<ComprobanteCausacionEgreso> li_conConComprobanteGastos(String search) {
        return conComprobanteGastosController.li_conConComprobanteGastos(search);
    }

}
