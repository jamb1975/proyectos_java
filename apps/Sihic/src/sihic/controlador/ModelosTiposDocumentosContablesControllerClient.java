/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import service.ModelosTiposDocumentosContablesController;
import sihic.SihicApp;
import sihic.general.LoadChoiceBoxGeneral;

/**
 *
 * @author adminlinux
 */
public class ModelosTiposDocumentosContablesControllerClient {

    private ModelosTiposDocumentosContablesController modelosTiposDocumentosContablesController;

    public ModelosTiposDocumentosContablesControllerClient() {
        modelosTiposDocumentosContablesController = new ModelosTiposDocumentosContablesController();
    }

    public void create() {
        modelosTiposDocumentosContablesController.create(SihicApp.modeloTiposDocumentosContables);
    }

    public void update() {
        modelosTiposDocumentosContablesController.update(SihicApp.modeloTiposDocumentosContables);
    }

    public void delete() {
        modelosTiposDocumentosContablesController.delete(SihicApp.modeloTiposDocumentosContables);
    }

    public void getRecords(String search) {

        SihicApp.li_ModeloTiposDocumentosContableses = modelosTiposDocumentosContablesController.getRecords(search);

    }

    public void getRecords() {

        SihicApp.li_TiposDocumentosContableses = modelosTiposDocumentosContablesController.getRecords();

    }
    public void getRecordsSucursales() {

        SihicApp.li_Sucursales = modelosTiposDocumentosContablesController.getRecordsSucursales();

    }

}
