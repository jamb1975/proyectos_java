/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.util.List;
import modelo.AgnCitas;
import modelo.GenUnidadesOrganizacion;
import modelo.HclConsultas;
import modelo.HclDiagnostico;
import modelo.HclDiagnosticosRelacionados;
import modelo.HclFormulacionMedicamentos;
import modelo.HclFormulacionProcedimientos;
import modelo.HclFormulacionesMedicas;
import service.HclConsultasController;

/**
 *
 * @author adminlinux
 */
public class HclConsultasControllerClient {

    private final HclConsultasController hclConsultasController;

    public HclConsultasControllerClient() {
        hclConsultasController = new HclConsultasController(HclConsultas.class);
    }

    public void save(HclConsultas hclConsultas) {
        hclConsultasController.save(hclConsultas);
    }

    public HclConsultas findconsulta(AgnCitas agnCitas) {

        return hclConsultasController.findconsulta(agnCitas);
    }

    public HclConsultas edit(HclConsultas hclConsultas) {
        return hclConsultasController.edit(hclConsultas);
    }

    public void saveDiagnostico(HclDiagnostico hclDiagnostico) {
        hclConsultasController.saveDiagnostico(hclDiagnostico);
    }

    public void saveFormulasMedicas(HclFormulacionesMedicas formulacionesMedicas) {
        hclConsultasController.saveFormulasMedicas(formulacionesMedicas);
    }

    public void saveFormulasMedicaMentos(HclFormulacionMedicamentos formulacionMedicamentos) {
        hclConsultasController.saveFormulasMedicaMentos(formulacionMedicamentos);
    }

    public void saveFormulasProcedimientos(HclFormulacionProcedimientos hclFormulacionProcedimientos) {
        hclConsultasController.saveFormulasProcedimientos(hclFormulacionProcedimientos);

    }

    public HclConsultas getHclConsulta(AgnCitas agnCitas) {
        return hclConsultasController.getHclConsulta(agnCitas);
    }

    public void saveDiagnosticoRelacionado(HclDiagnosticosRelacionados hclDiagnosticosRelacionados) {
        hclConsultasController.saveDiagnosticoRelacionado(hclDiagnosticosRelacionados);
    }

    public void saveOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion) {
        hclConsultasController.saveOrganizacion(genUnidadesOrganizacion);
    }

    public GenUnidadesOrganizacion updateOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion) {
        return genUnidadesOrganizacion = hclConsultasController.updateOrganizacion(genUnidadesOrganizacion);
    }

    public List<GenUnidadesOrganizacion> getOrganizacion(String search) {
        return hclConsultasController.getOrganizacion(search);
    }

}
