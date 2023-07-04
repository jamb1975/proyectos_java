/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.util.List;
import javafx.concurrent.Task;
import modelo.Kardex;
import modelo.Producto;
import service.EntityManagerGeneric;
import service.ProductoController;
import sihic.SihicApp;

/**
 *
 * @author karolyani
 */
public class ProductoControllerClient {

    private ProductoController productoController;
    public static Task<Void> task_producto;
    public static Thread thimport_producto;
    public static Task<Void> task_servicioskardex;
    public static Thread thimport_servicioskardex;
    public static Task<Void> task_productokardex;
    public static Thread thimport_productokardex;
    public static Task<Void> task_serviciosproducto;
    public static Thread thimport_serviciosproducto;

    public ProductoControllerClient() {
        productoController = new ProductoController(Producto.class);
    }

    public void findproductosall(String search) {
        task_producto = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SihicApp.li_producto.clear();
                SihicApp.li_producto.addAll(productoController.findproductosall(search));

                EntityManagerGeneric.closeEntityManager();

                return null;
            }
        };
           SihicApp.li_producto.clear();
           SihicApp.li_producto.addAll(productoController.findproductosall(search));

    }

    public List<Producto> findproductosall2(String search) {

        return productoController.findproductosall(search);

    }

    public List<Producto> findserviciosall(String search) {

        return productoController.findserviciosall(search);
    }

    public List<Producto> findmedicamentosall(String search) {

        return productoController.findmedicamentosall(search);
    }

    public List<Producto> findinsumosall(String search) {

        return productoController.findinsumosall(search);
    }

    public Kardex findByCodigoLastKardex(String codigo) {

        return productoController.findByCodigoLastKardex(codigo);

    }

    public Producto findByCodigoExactAllProductos(String codigo) {

        return productoController.findByCodigoExactAllProductos(codigo);

    }

    public Producto findByCodigoAllProducto(String codigo) {

        return productoController.findByCodigoAllProducto(codigo);

    }

    public void li_findserviciosProductos(String search) {
        task_serviciosproducto = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SihicApp.li_serviciosProductos.clear();
                SihicApp.li_serviciosProductos.addAll(productoController.li_findserviciosProductos(search));

                EntityManagerGeneric.closeEntityManager();

                return null;
            }
        };
         SihicApp.li_serviciosProductos.clear();
         SihicApp.li_serviciosProductos.addAll(productoController.li_findserviciosProductos(search));

    }

    public void li_findserviciosKardex(String search) {
        task_servicioskardex = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
               
                EntityManagerGeneric.closeEntityManager();

                return null;
            }
        };
        SihicApp.li_serviciosKardex.clear();
        SihicApp.li_serviciosKardex.addAll(productoController.li_findservicioskardex(search));


    }

    public List<Kardex> li_findserviciosKardex2(String search) {

        return productoController.li_findservicioskardex(search);

    }

    public void li_findproductosKardex(String search) {
        task_productokardex = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SihicApp.li_productosKardex.clear();
                SihicApp.li_productosKardex.addAll(productoController.li_findproductoskardex(search));

                EntityManagerGeneric.closeEntityManager();

                return null;
            }
        };
        SihicApp.li_productosKardex.clear();
                SihicApp.li_productosKardex.addAll(productoController.li_findproductoskardex(search));

    }

    public List<Kardex> li_findproductosKardex2(String search) {
        return productoController.li_findproductoskardex(search);
    }

    public void create() {
        try {
            productoController.create(SihicApp.s_producto);

        } catch (Exception e) {

        }
    }

    public void update() {
        try {
            productoController.update(SihicApp.s_producto);

        } catch (Exception e) {

        }
    }

    public void delete() {
        try {
            productoController.delete(SihicApp.s_producto);

        } catch (Exception e) {

        }
    }

    public List<Producto> getRecords(String search) {
        return productoController.getRecords(search);

    }

    public List<Producto> getRecordsbycategoria(String search) {
        return productoController.getRecordsbycategoria(search, SihicApp.genCategoriasProductos);

    }
}
