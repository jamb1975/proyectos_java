/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import abaco.AbacoApp;
import java.util.List;
import model.Personas;
import model.Usuarios;
import service.GenPersonasController;

/**
 *
 * @author adminlinux
 */
public class GenPersonasControllerClient {
    private GenPersonasController genPersonasController;
    public GenPersonasControllerClient()
    {
         genPersonasController=new GenPersonasController(Personas.class);
    }
    public void create()
    { try{
        genPersonasController.create(AbacoApp.s_genpersonas);
       
    }catch(Exception e)
    {
     
    }
    }
     public void create(Personas gp, Usuarios u)
    { try{
        genPersonasController.saveusuario(gp,u);
       
    }catch(Exception e)
    {
     
    }
    }
    public void update()
    { try{
        genPersonasController.update(AbacoApp.s_genpersonas);
       
    }catch(Exception e)
    {
     
    }
    }
   public void delete()
    {
     genPersonasController.delete(AbacoApp.s_genpersonas);
   }
    public List<Personas> getRecords(String search)
    {
      return  genPersonasController.getRecords(search);
        
    }
   public List<Personas> getEmpleados(String search)
    {
      return  genPersonasController.getGenpersonas(search);
        
    }
      public Personas findbyident(String no_ident)
  {
    
         return genPersonasController.findbyident(no_ident);
  }
 
}
