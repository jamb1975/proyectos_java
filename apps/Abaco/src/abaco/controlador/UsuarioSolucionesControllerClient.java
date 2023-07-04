/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.util.List;
import javafx.concurrent.Task;
import model.Solucion;
import model.UsuarioSoluciones;
import model.Usuarios;
import service.UsuariosSolucionesController;
import abaco.AbacoApp;

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
        AbacoApp.Li_UsuarioSoluciones.addAll(usuariosSolucionesController.getUsuarioSoluciones(usuario, password));

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
