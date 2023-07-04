/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import modelo.FacturaItem;
import modelo.NotasEstudio;
import modelo.Resultados;
import service.EntityManagerGeneric;
import service.ResultadosController;

/**
 *
 * @author adminlinux
 */
public class ResultadosControllerClient {

    private ResultadosController resultadoscontroller;

    public ResultadosControllerClient() {
        resultadoscontroller = new ResultadosController();
    }

    public void create(Resultados resultados) {
        resultadoscontroller.create(resultados);
    }

    public Resultados update(Resultados resultados) {
        return resultadoscontroller.update(resultados);
    }

    public Resultados getResultados(FacturaItem fi) {
        return resultadoscontroller.getResultados(fi);
    }
}
