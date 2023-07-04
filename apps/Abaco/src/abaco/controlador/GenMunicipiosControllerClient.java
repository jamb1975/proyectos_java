/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import java.util.List;
import model.GenMunicipios;
import service.GenMunicipiosController;

/**
 *
 * @author adminlinux
 */
public class GenMunicipiosControllerClient {
  
    private GenMunicipiosController genMunicipiosController;
    public GenMunicipiosControllerClient()
  {
      genMunicipiosController=new GenMunicipiosController();
  }
  public List<GenMunicipios> getRecords(String search)
  {
      return genMunicipiosController.getRecords(search);
  }
}
