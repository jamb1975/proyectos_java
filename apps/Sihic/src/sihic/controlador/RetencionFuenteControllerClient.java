/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import javafx.concurrent.Task;
import modelo.RetencionFuente;
import service.EntityManagerGeneric;
import service.RetencionFuenteController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class RetencionFuenteControllerClient {

    private RetencionFuenteController retencionFuenteController;

    public RetencionFuenteControllerClient() {
        retencionFuenteController = new RetencionFuenteController(RetencionFuente.class);
    }

    public void create(RetencionFuente retencionFuente) {
        retencionFuenteController.create(retencionFuente);
    }

    public void getRecords() {
        Task task=new Task<Void>() {
        
            @Override
            protected Void call() throws Exception {
              SihicApp.li_retencionfuente = retencionFuenteController.getRecords();
             EntityManagerGeneric.closeEntityManager();
            return null;
         }
        };
          Thread  thimport = new Thread(task);
          thimport.setDaemon(true);
          thimport.start();
      
    }
}
