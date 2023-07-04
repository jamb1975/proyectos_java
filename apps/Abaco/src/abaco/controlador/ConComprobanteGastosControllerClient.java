/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.util.List;
import model.ConComprobanteGastos;
import model.ConDetallesComprobanteContabilidad;
import service.ConComprobanteEgresoController;
import service.ConComprobanteGastosController;

/**
 *
 * @author adminlinux
 */
public class ConComprobanteGastosControllerClient {
    private ConComprobanteGastosController conComprobanteGastosController;
    public ConComprobanteGastosControllerClient()
    {
        conComprobanteGastosController=new ConComprobanteGastosController();
    }
    public void create(ConComprobanteGastos conComprobanteGastos )
    {
        conComprobanteGastosController.create(conComprobanteGastos);
    }
    public ConComprobanteGastos edit(ConComprobanteGastos conComprobanteGastos)
    {
        return conComprobanteGastosController.edit(conComprobanteGastos);
    }
    public List<ConDetallesComprobanteContabilidad> findconComprobanteGastos(Long id)
    {
      return conComprobanteGastosController.findconComprobanteGastos(id);
    }
    public List<ConComprobanteGastos> li_conConComprobanteGastos(String search)
    {
        return conComprobanteGastosController.li_conConComprobanteGastos(search);
    }

}
