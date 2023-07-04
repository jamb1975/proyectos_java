/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import modelo.LibroAuxiliar;


import modelo.Puc;
import service.ConPucController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class ConPucControllerClient {

    ConPucController conPucController;

    public ConPucControllerClient() {
        conPucController = null;
       
    }

    public void create(Puc conPuc) {
        conPucController.create(conPuc);
    }

    public Puc getConPuc() {
        return conPucController.getConPuc();
    }

    public List<Puc> getConPucHijos(Puc conPuc) {
        return conPucController.getConPucHijos(conPuc);
    }

    

    public Puc findConPuc(String codigo, int vercuentas) 
    {
        return conPucController.findConPuc(codigo,vercuentas);
    }

    
   

    public void setConPuc() {
        conPucController.setIdPadre();
    }

   
    

    public void create() throws ParseException {
        conPucController.create(SihicApp.conPuc);
    }

    public void update() throws ParseException {
        conPucController.update(SihicApp.conPuc);
    }

    public void delete() throws ParseException {
        conPucController.delete(SihicApp.conPuc);
    }

    public void quitarespacios() {
        conPucController.quitasespacios();
    }

    public void setIdPadre() {
        conPucController.setIdPadre();
    }

    public List<LibroAuxiliar> findcomprobante(int tipo, Date datefind) throws ParseException {
        return conPucController.findComprobanteContabilidad(tipo, datefind);
    }

    public List<Puc> li_conpuc(String search) {

        return conPucController.li_conpuc(search);
    }
}
