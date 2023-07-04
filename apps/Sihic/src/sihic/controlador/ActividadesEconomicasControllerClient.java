/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import javafx.concurrent.Task;
import modelo.ActividadesEconomicas;
import service.ActividadesEconomicasController;
import service.EntityManagerGeneric;
import sihic.SihicApp;


/**
 *
 * @author adminlinux
 */
public class ActividadesEconomicasControllerClient {

    private ActividadesEconomicasController actividadesEconomicasController;

    public ActividadesEconomicasControllerClient() {
        actividadesEconomicasController = new ActividadesEconomicasController(ActividadesEconomicas.class);
    }

    public void create(ActividadesEconomicas actividadesEconomicas) {
        actividadesEconomicasController.create(actividadesEconomicas);
    }

    public void getRecords() {
         Task task=new Task<Void>() {
        
            @Override
            protected Void call() throws Exception {
            SihicApp.li_actividadeseconomicas = actividadesEconomicasController.getRecords();
             EntityManagerGeneric.closeEntityManager();
            return null;
         }
        };
          Thread  thimport = new Thread(task);
          thimport.setDaemon(true);
          thimport.start();
       
    }
}
