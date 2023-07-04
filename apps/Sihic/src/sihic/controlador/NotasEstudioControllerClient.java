/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import modelo.FacturaItem;
import modelo.NotasEstudio;
import modelo.Resultados;
import service.EntityManagerGeneric;
import service.NotasEstudioController;

/**
 *
 * @author adminlinux
 */
public class NotasEstudioControllerClient {

    private NotasEstudioController notasEstudioController;

    public NotasEstudioControllerClient() {
        notasEstudioController = new NotasEstudioController();
    }

    public NotasEstudio getNotasEstudio(FacturaItem fi) {
        return notasEstudioController.getNotasEstudio(fi);
    }

    public void create(NotasEstudio notasEstudio) {
        notasEstudioController.create(notasEstudio);
    }

    public NotasEstudio update(NotasEstudio notasEstudio) {
        return notasEstudioController.update(notasEstudio);
    }

    public void deleteNotasEstudio(NotasEstudio notasEstudio) {
        notasEstudioController.deleteNotasEstudio(notasEstudio);
    }
    /*public Resultados getResultados(FacturaItem fi)
 {
     return notasEstudioController.getResultados(fi);
 }
public void create(Resultados resultados)
{
   notasEstudioController.create(resultados);
}
public Resultados update(Resultados resultados)
{
    return notasEstudioController.update(resultados);
}
     */
}
