/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package abaco.controlador;


import abaco.AbacoApp;
import java.util.List;
import model.Proveedores;
import service.ProveedoresController;


/**
 
 * @author karol
 */
public class ProveedoresControllerClient {
    private ProveedoresController proveedoresController;
   
 
    public ProveedoresControllerClient() {
       proveedoresController=new ProveedoresController();
    }

 public void create()
    { try{
        proveedoresController.create(AbacoApp.s_proveedores);
       
    }catch(Exception e)
    {
     
    }
    }
    public void update()
    { try{
        proveedoresController.update(AbacoApp.s_proveedores);
       
    }catch(Exception e)
    {
     
    }
    }
    public void delete()
    { try{
        proveedoresController.delete(AbacoApp.s_proveedores);
       
    }catch(Exception e)
    {
     
    }
    }
   
    public List<Proveedores> getRecords(String search)
    {
      return  proveedoresController.getRecords(search);
        
    }   

     public Proveedores findbyident(String no_ident)
  {
  
         return proveedoresController.findbyident(no_ident);
  }

   
    
}
