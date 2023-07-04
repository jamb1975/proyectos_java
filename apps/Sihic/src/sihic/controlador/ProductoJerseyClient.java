/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.util.List;
import modelo.Kardex;

import modelo.Producto;
import service.FindproductResource;
import service.ProductoFacadeREST;
import sihic.contabilidad.FindServicios;

/**
 * Jersey REST client generated for REST resource:ProductoFacadeREST
 * [model.producto]<br>
 * USAGE:
 * <pre>
 *        ProductoJerseyClient client = new ProductoJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author karol
 */
public class ProductoJerseyClient {

    ProductoFacadeREST productoFacadeREST;
    FindproductResource findproductResource;

    public ProductoJerseyClient() {
        productoFacadeREST = new ProductoFacadeREST();
        findproductResource = new FindproductResource();
    }

    public void remove(String id) {
        productoFacadeREST.remove(Long.valueOf(id));
    }

    public String countREST() {

        return productoFacadeREST.countREST();
    }

    public List<Producto> findAll_XML() {
        return productoFacadeREST.findAll();
    }

    public void edit_XML(Object requestEntity, String id) {
        productoFacadeREST.edit(Long.valueOf(id), (Producto) requestEntity);
    }

    public void create_XML(Object requestEntity) {
        productoFacadeREST.create((Producto) requestEntity);
    }

    public List<Producto> findRange_XML(String from, String to) {

        return productoFacadeREST.findRange(Integer.valueOf(from), Integer.valueOf(to));
    }

    public List<Producto> findCriteria_XML(String from, String to, String search) {
        return productoFacadeREST.findRange(Integer.valueOf(from), Integer.valueOf(to), search);
    }

    public List<Kardex> findCriteria(String search, boolean ismateriaprima) {

        return findproductResource.find(search, ismateriaprima);
    }

    public List<Kardex> findInsumosMedicamentos(String search) {

        return findproductResource.findInsumosMedicamentos(search);
    }

    public Producto find_XML(String id) {

        return productoFacadeREST.find(Long.valueOf(id));
    }

    public Producto exists_entity(Producto producto) {
        return productoFacadeREST.exist_entity(producto);
    }

    public void close() {
        productoFacadeREST = null;
        findproductResource = null;
    }

}
