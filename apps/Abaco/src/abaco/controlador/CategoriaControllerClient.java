/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import abaco.AbacoApp;
import java.util.List;
import model.Categoria;
import service.CategoriaController;

/**
 *
 * @author adminlinux
 */
public class CategoriaControllerClient {
    private CategoriaController categoriaController;
    public CategoriaControllerClient()
    {
      categoriaController=new CategoriaController();
    }
   public void save()
   {
     categoriaController.save(AbacoApp.s_categoria);
   }
    public Categoria update()
   {
    return categoriaController.update(AbacoApp.s_categoria);
   }
    public void delete()
    {
     categoriaController.delete(AbacoApp.s_categoria);
   }
   public List<Categoria> getRecords(String search)
    {
        return categoriaController.getRecords(search);
    }   
}
