/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.stream.Collectors;
import modelo.AsientosModelosTiposDocumentosContables;
import sihic.SihicApp;
import sihic.general.LoadChoiceBoxGeneral;

/**
 *
 * @author adminlinux
 */
public  class MovimientoCuentas {
    private static String codigo = ""; 
    private static String concepto = "";
     public static void colocarasientoscontables(int tipo) throws ParseException, InterruptedException {
      switch(tipo)
      {
       
          case 3: //ventas
        if ((SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("P")) && SihicApp.s_factura.getGenTiposUsuarios().getCodigo().trim().equals("1")) {
            codigo = "4";
            concepto = "FACTURADO A RÉGIMEN CONTRIBUTIVO  "+SihicApp.s_factura.getGenEapb().getNombre()+" N° Factura: "+SihicApp.s_factura.getNofacturacerosizquierda();;
        } else {
            if ((SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("P")) && SihicApp.s_factura.getGenTiposUsuarios().getCodigo().trim().equals("2")) {

                codigo = "5";
               concepto = "FACTURADO A RÉGIMEN SUBSIDIADO  "+SihicApp.s_factura.getGenEapb().getNombre()+" N° Factura: "+SihicApp.s_factura.getNofacturacerosizquierda();
            } else {
                if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("T")) {
                    codigo = "6";
                    try
                    {
                    concepto = "FACTURADO A SOAT ASEGURADORA: "+SihicApp.s_factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getNombre()+" BENEFICIARIO: "+SihicApp.s_factura.getFacturaItems().get(0).getGenPacientes().getGenPersonas().getNombres()+" N° Factura: "+SihicApp.s_factura.getNofacturacerosizquierda();
                    }catch(Exception e)
                    {
                        try{
                       concepto = "FACTURADO A SOAT ASEGURADORA: "+SihicApp.s_factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenEapb().getNombre()+" BENEFICIARIO: "+SihicApp.s_factura.getFacturaItems().get(0).getGenPacientes().getGenPersonas().getNombres()+" N° Factura: "+SihicApp.s_factura.getNofacturacerosizquierda();
                        }catch(Exception e2){
                                concepto = "FACTURADO A SOAT ASEGURADORA: Ninguna BENEFICIARIO: "+SihicApp.s_factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getNombres()+" N° Factura: "+SihicApp.s_factura.getNofacturacerosizquierda();
                    
                             
                             }
                        
                    }
                } else {
                    if (SihicApp.s_factura.getPrefijo().substring(0, 1).equals("E")) {
                       
                        concepto = "FACTURADO A PARTICULAR: "+SihicApp.s_factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getNombres();
          
                        codigo = "7";
                    } else {
                        if ((SihicApp.s_factura.getPrefijo().substring(0, 1).equals("A") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("P")) && SihicApp.s_factura.getGenTiposUsuarios().getCodigo().trim().equals("10")) {

                            codigo = "8";
                                    concepto = "FACTURADO A EMPRESAS SOCIALES DEL ESTADO  "+SihicApp.s_factura.getGenEapb().getNombre()+" N° Factura: "+SihicApp.s_factura.getNofacturacerosizquierda();
 
                            
                        } else {
                            codigo = "9";
                            
                            concepto = "FACTURADO A OTROS: "+SihicApp.s_factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getNombres()+" N° Factura: "+SihicApp.s_factura.getNofacturacerosizquierda();
          
                        }
                    }
                }
            }
        }
          break;
          case 1://ingresos
                codigo = "12";
                concepto = SihicApp.conComprobanteIngreso.getConcepto();
                 break;
            
        
      }
        try {
            SihicApp.modeloTiposDocumentosContables = SihicApp.li_ModeloTiposDocumentosContableses.stream().filter(line -> String.valueOf(line.getTiposDocumentosContables().getCodigo()).equals(codigo)) //filters the line, equals to "mkyong"
                    .collect(Collectors.toList()).get(0);
        } catch (Exception e) {
         
        }
        
        for (AsientosModelosTiposDocumentosContables amtc : SihicApp.modeloTiposDocumentosContables.getAsientosModelosTiposDocumentosContableses()) {
            SihicApp.conPuc = amtc.getConPuc();
            AddAsientosSegumModelo(amtc.porcentajereal().multiply(SihicApp.s_factura.getTotalAmount()), amtc.getTipomovimiento().toUpperCase().equals("D") ? true : false, amtc.getCentrocostos());
        }
    }

    public static void AddAsientosSegumModelo(BigDecimal valor, boolean movimiento, int centrocostos) throws ParseException, InterruptedException {
        

    }

   

   
}
