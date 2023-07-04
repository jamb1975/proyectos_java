/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import service.ConDetallesComprobanteContabilidadController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class ConDetallesComprobanteContabilidadControllerCient {
ConDetallesComprobanteContabilidadController conDetallesComprobanteContabilidadController=new ConDetallesComprobanteContabilidadController();
    public ConDetallesComprobanteContabilidadControllerCient() {
    conDetallesComprobanteContabilidadController=new ConDetallesComprobanteContabilidadController();
    }
    
  public void update()
  {
      conDetallesComprobanteContabilidadController.update(SihicApp.conDetallesComprobanteContabilidad);
  } 
  public void findconcomprobanteingreso(Long id)
  {
      SihicApp.conComprobanteIngreso=conDetallesComprobanteContabilidadController.findconcomprobanteingreso(id);
  }
}
