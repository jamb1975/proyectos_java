/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import model.ConComprobanteContabilidad;
import model.ConDetallesComprobanteContabilidad;
import model.ConLibroAuxiliar;
import model.ConLibroDiario;
import model.ConPuc;
import service.ConPucController;

/**
 *
 * @author adminlinux
 */
public class ConPucControllerClient {
ConPucController conPucController;
    public ConPucControllerClient() {
        conPucController=null;
         conPucController=new ConPucController(ConComprobanteContabilidad.class);
    }
      
    public void create(ConPuc conPuc)
    {
        conPucController.create(conPuc);
    }
     public ConPuc getConPuc()
    {
        return conPucController.getConPuc();
    }
     public List<ConPuc> getConPucHijos(ConPuc conPuc)
    {
        return conPucController.getConPucHijos(conPuc);
    }
     public ConComprobanteContabilidad save(ConComprobanteContabilidad conComprobanteContabilidad)
     {
       return  conPucController.edit(conComprobanteContabilidad);
     }
     public ConPuc findConPuc(String codigo)
     {
         return conPucController.findConPuc(codigo);
     }
     public List<ConLibroAuxiliar> getLibroAuxiliar(String desde, String hasta, String cd) throws ParseException
     {
         return conPucController.getLibroAuxiliar(desde, hasta, cd);
     }
      public List<ConLibroDiario> getLibroDiario(String desde, String hasta, String cd) throws ParseException
     {
         return conPucController.getLibroDiario(desde, hasta, cd);
     }
     public void setConPuc()
     {
         conPucController.setIdPadre();
     }
     public void trasladolibrosauxiliardiario(ConComprobanteContabilidad cc) throws ParseException
     {
         conPucController.trasladolibrosauxiliares(cc);
     }
     public ConComprobanteContabilidad findConComprobanteContabilidad(Long factura_id,int tipo) throws ParseException
     {
         return conPucController.findLastComprobanteContabilidad(factura_id,tipo);
     }
     public void quitarespacios()
     {
         conPucController.quitasespacios();
     }
     public void setIdPadre()
     {
         conPucController.setIdPadre();
     }
     public List<ConDetallesComprobanteContabilidad> findcomprobante(int tipo,Date datefind) throws ParseException
     {
        return conPucController.findComprobanteContabilidad(tipo, datefind);
     }
     
     public List<ConPuc> li_conpuc(String search)
     {
         return conPucController.li_conpuc(search);
     }
}
