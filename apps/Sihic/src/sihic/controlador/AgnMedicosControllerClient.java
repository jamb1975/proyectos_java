/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.util.List;
import javafx.concurrent.Task;
import modelo.AgnMedicos;
import service.AgnMedicosController;
import service.EntityManagerGeneric;
import sihic.SihicApp;
import static sihic.controlador.ProductoControllerClient.task_serviciosproducto;
import static sihic.controlador.ProductoControllerClient.thimport_serviciosproducto;

/**
 *
 * @author adminlinux
 */
public class AgnMedicosControllerClient {

    private AgnMedicosController agnMedicosController;
    public static Task<Void> task_medicos;
    public static Thread thimport_medicos;

    public void getAgnMedicos() {

        task_medicos = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                agnMedicosController = new AgnMedicosController(AgnMedicos.class);
                SihicApp.getAgnmedicos().clear();
                SihicApp.getAgnmedicos().addAll(agnMedicosController.getAgnMedicos());
                EntityManagerGeneric.closeEntityManager();

                return null;
            }
        };
      //  thimport_medicos = new Thread(task_medicos);
      //  thimport_medicos.setDaemon(true);
        //thimport_medicos.start();
  agnMedicosController = new AgnMedicosController(AgnMedicos.class);
                SihicApp.getAgnmedicos().clear();
                SihicApp.getAgnmedicos().addAll(agnMedicosController.getAgnMedicos());
    }

}
