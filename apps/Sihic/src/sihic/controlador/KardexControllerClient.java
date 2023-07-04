/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import sihic.SihicApp;
import java.text.ParseException;
import java.util.List;
import modelo.Kardex;
import modelo.Producto;
import service.KardexController;

/**
 * Jersey REST client generated for REST resource:KardexController
 * [model.kardex]<br>
 * USAGE:
 * <pre>
 * KardexControllerClient client = new KardexControllerClient();
 * Object response = client.XXX(...);
 * // do whatever with response
 * client.close();
 * </pre>
 *
 * @author karolyani
 */
public class KardexControllerClient {

    private KardexController kardexController;

    public KardexControllerClient() {
        kardexController = new KardexController();
    }

    public String countREST() {

        return kardexController.countREST();
    }

    public void edit_XML(Object requestEntity, String id) {
        kardexController.edit(Long.valueOf(id), (Kardex) requestEntity);
    }

    public List<Kardex> findRange_XML(String datefrom, String dateto, String idprod, String otro) throws ParseException {
        return kardexController.findRange(datefrom, dateto, Long.valueOf(idprod));
    }

    public void create(Object requestEntity) {
        kardexController.create((Kardex) requestEntity);
    }

    public List<Kardex> findAll_XML() {
        return kardexController.findAll();
    }

    public Kardex find_XML(String id_product, String tipo, String no_fac) {

        return kardexController.find(Long.valueOf(id_product), tipo);
    }

    public void remove(String id) {
        kardexController.remove(Long.valueOf(id));
    }

    public void close() {
        kardexController = null;
    }

    public List<Kardex> getRecords() {
        return kardexController.getRecords();
    }

    public List<Kardex> getRecords(String search, String datefrom, String dateto) throws ParseException {
        return kardexController.getRecords(search, datefrom, dateto);
    }

    public List<Kardex> getRecords(Producto producto) throws ParseException {
        return kardexController.getRecords(producto);
    }

    public Kardex update() {
        return kardexController.update(SihicApp.s_kardex);
    }

    public void delete() {
        kardexController.delete(SihicApp.s_kardex);
    }

}
