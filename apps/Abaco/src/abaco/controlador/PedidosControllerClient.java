/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import abaco.AbacoApp;
import service.PedidosController;

/**
 *
 * @author isoft
 */
public class PedidosControllerClient {
    private PedidosController pedidosController;
    public PedidosControllerClient()
    {
      pedidosController=null;
      pedidosController=new PedidosController();
    }
    public void create()
    {
      
      pedidosController.create(AbacoApp.pedidos);
    }
     public void update()
    {
      
      pedidosController.update(AbacoApp.pedidos);
    }
    public void getrecordsporestado(int estado)
    {
        AbacoApp.li_pedidos= pedidosController.getrecordspedidosporestado(estado);
    }
    public void getfindbynopedido(String nopedido)
   {
     AbacoApp.pedidos=pedidosController.getfindbynopedido(nopedido);
   }
}
