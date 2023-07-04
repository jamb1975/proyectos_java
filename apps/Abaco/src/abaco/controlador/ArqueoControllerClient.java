/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import abaco.AbacoApp;
import java.math.BigDecimal;
import java.util.List;
import model.Arqueo;
import service.ArqueoController;

/**
 *
 * @author adminlinux
 */
public class ArqueoControllerClient {
    private ArqueoController arqueoController;
   public ArqueoControllerClient()
   {
       arqueoController=new ArqueoController(Arqueo.class);
   }
   public void create()
   {
     arqueoController.create(AbacoApp.s_arqueo);
   }
   public List<Object[]> TotalCreditoContado()
   {
       return arqueoController.totalcontadocredito();
   }
   public BigDecimal totalrecibossinfactura()
   {
       return arqueoController.totalrecibossonfactura();
   }
   public void getlastrecord()
   {
       AbacoApp.s_arqueo=arqueoController.getlastrecord();
   }        
}
