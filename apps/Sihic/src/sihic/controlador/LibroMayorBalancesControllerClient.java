/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import service.LibroMayorBalancesController;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class LibroMayorBalancesControllerClient {
    private LibroMayorBalancesController libroMayorBalancesController;
    
    public LibroMayorBalancesControllerClient()
    {
        libroMayorBalancesController=new LibroMayorBalancesController();
    }
    public void getLastMovimiento()
    {
        SihicApp.li_libromayorbalances=libroMayorBalancesController.getLastMovimiento();
    }
    public void addNewMovimiento() throws ParseException
    {
        SihicApp.li_libromayorbalances=libroMayorBalancesController.addNewMovimiento();
    }
    public void volveracalcularlibromayorbalances(int año,int mes) throws ParseException
    {
         SihicApp.li_libromayorbalances=libroMayorBalancesController.volveracalcularlibromayorbalances(mes,año);
    }
}
