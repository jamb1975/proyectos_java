/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import abaco.AbacoApp;
import java.util.List;
import model.Categoria;
import model.UnidadesMedida;
import service.CategoriaController;
import service.UnidadesMedidaController;

/**
 *
 * @author adminlinux
 */
public class UnidadesMedidaControllerClient {
    private UnidadesMedidaController unidadesMedidaController;
    public UnidadesMedidaControllerClient()
    {
      unidadesMedidaController=new UnidadesMedidaController();
    }
   public void save()
   {
     unidadesMedidaController.create(AbacoApp.s_unidadesmedida);
   }
    public UnidadesMedida update()
   {
    return unidadesMedidaController.update(AbacoApp.s_unidadesmedida);
   }
    public void delete()
    {
     unidadesMedidaController.delete(AbacoApp.s_unidadesmedida);
   }
   public List<UnidadesMedida> getRecords(String search)
    {
        return unidadesMedidaController.getRecords(search);
    }   
}
