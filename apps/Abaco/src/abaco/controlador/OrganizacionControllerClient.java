/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import abaco.AbacoApp;
import java.util.List;
import model.Organizacion;
import service.OrganizacionController;

/**
 *
 * @author karolyani
 */
public class OrganizacionControllerClient {
    private OrganizacionController organizacionController;
   
   public  OrganizacionControllerClient()
   {
       organizacionController=new OrganizacionController();
   }
    public void load_organizacion()
 {
    AbacoApp.s_organizacion=organizacionController.load_GenUnidadesOrganizacion();
     
 }
  public void save()
   {
     organizacionController.create(AbacoApp.s_organizacion);
   }
    public Organizacion update()
   {
    return organizacionController.update(AbacoApp.s_organizacion);
   }
   public List<Organizacion> getRecords(String search)
    {
        return organizacionController.getRecords(search);
    }  
}
