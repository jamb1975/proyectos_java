/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import abaco.AbacoApp;
import java.util.List;
import model.Kardex;
import model.Producto;
import service.ProductoController;


/**
 *
 * @author karolyani
 */
public class ProductoControllerClient {
    private ProductoController productoController;

    public  ProductoControllerClient()
    {
        productoController=new ProductoController(Producto.class);
    }
    
  public void create()
    { try{
        productoController.create(AbacoApp.s_producto);
       
    }catch(Exception e)
    {
     
    }
    }
    public void update()
    { try{
        productoController.update(AbacoApp.s_producto);
       
    }catch(Exception e)
    {
     
    }
    }
    public void delete()
    { try{
        productoController.delete(AbacoApp.s_producto);
       
    }catch(Exception e)
    {
     
    }
    }
   
    public List<Producto> getRecords(String search)
    {
      return  productoController.getRecords(search);
        
    } 
    public List<Producto> getRecordsProductos(String search)
    {
      return  productoController.getRecordsproductos(search);
        
    } 
    public List<Producto> getRecordsServicios(String search)
    {
      return  productoController.getRecordsServicios(search);
        
    }
 public Kardex finByCodigo(String codigo)
 {
     
   
     return productoController.findByCodigo(codigo);
    
 }
 public Producto finByCodigo2(String codigo)
 {
      
     return productoController.findByCodigo2(codigo);
    
 }
  public Producto finByCodigo3(String codigo)
 {
     
     return productoController.findByCodigo3(codigo);
    
 }
}
