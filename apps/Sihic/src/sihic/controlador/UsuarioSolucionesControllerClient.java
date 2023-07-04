/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.util.List;
import javafx.concurrent.Task;
import modelo.Solucion;
import modelo.GenPersonas;
import modelo.UsuarioSoluciones;
import modelo.Usuarios;
import service.EntityManagerGeneric;
import service.UsuariosSolucionesController;
import sihic.SihicApp;
import static sihic.administracion.FLogin.Pf_Password;
import static sihic.administracion.FLogin.Tf_Usuario;
import static sihic.controlador.HclHistoriasClinicasControllerClient.task_medicos;
import static sihic.controlador.HclHistoriasClinicasControllerClient.thimport_medicos;

/**
 *
 * @author karolyani
 */
public class UsuarioSolucionesControllerClient {

    UsuariosSolucionesController usuariosSolucionesController;
    public static Task<Void> task_us;
    public static Thread thimport_us;

    public UsuarioSolucionesControllerClient() {
        usuariosSolucionesController = new UsuariosSolucionesController(UsuarioSoluciones.class);
    }

    public List<Solucion> getSoluciones() {
        return usuariosSolucionesController.getSOluciones();
    }

    public List<Solucion> getSoluciones(Solucion s) {
        return usuariosSolucionesController.getSoluciones(s);
    }

    public void getUsuarioSoluciones(String usuario, String password) {
        /* task_us=new Task<Void>() {
        
            @Override
            protected Void call() throws Exception {
             EntityManagerGeneric.closeEntityManager();
                          return null;
         }
        };
          thimport_us = new Thread(task_us);
          thimport_us.setDaemon(false);
          thimport_us.start();*/
        SihicApp.Li_UsuarioSoluciones.addAll(usuariosSolucionesController.getUsuarioSoluciones(usuario, password));

    }

    public List<UsuarioSoluciones> getUsuarioSoluciones(Usuarios usuario) {
        return usuariosSolucionesController.getUsuarioSoluciones(usuario);
    }

    public void create(UsuarioSoluciones sc) {
        usuariosSolucionesController.create(sc);
    }

    public void remove(Solucion s) {
        usuariosSolucionesController.remove(s);
    }

    public List<Usuarios> getUsuarios(String search) {
        return usuariosSolucionesController.findUsuario(search);
    }
}
