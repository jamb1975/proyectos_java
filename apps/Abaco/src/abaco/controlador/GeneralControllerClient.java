/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.util.List;
import model.UnidadesMedida;
import service.GeneralController;

/**
 *
 * @author adminlinux
 */
public class GeneralControllerClient 
{
private GeneralController generalController;
    public GeneralControllerClient()
    {
         generalController=new GeneralController();
    }
    
    public List<UnidadesMedida> load_unidadesmedida ()
 {
     return generalController.load_genunidadesmedida();
 }
 
  
}
