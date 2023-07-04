/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import java.util.List;
import javafx.concurrent.Task;
import modelo.AgnConsultorios;
import modelo.AgnConsultoriosProcedimientos;
import modelo.AgnEspecialidades;
import modelo.AgnMedicos;
import modelo.FacturaItem;
import modelo.HclAntecedentesFarmacologicos;
import modelo.HclAntecedentesGenerales;
import modelo.HclAntecedentesGineco;
import modelo.HclAntecedentesPatologicos;
import modelo.HclCie10;
import modelo.HclConsultas;
import modelo.HclCups;
import modelo.HclDiagnostico;
import modelo.HclDiagnosticosRelacionados;
import modelo.HclFormulacionMedicamentos;
import modelo.HclFormulacionProcedimientos;
import modelo.HclFormulacionesMedicas;

import service.EntityManagerGeneric;
import service.HclHistoriasClinicasController;
import sihic.SihicApp;
import sihic.general.LoadChoiceBoxGeneral;

/**
 *
 * @author adminlinux
 */
public class HclHistoriasClinicasControllerClient {

    private HclHistoriasClinicasController hclHistoriasClinicasController;
    LoadChoiceBoxGeneral loadChoiceBoxGeneral = new LoadChoiceBoxGeneral();

    public static Task<Void> task_eapb;
    public static Thread thimport_eapb;
    public static Task<Void> task_antecedentes;
    public static Thread thimport_antecedentes;
    public static Task<Void> task_nivelesusuarios;
    public static Thread thimport_nivelesusuarios;
    public static Task<Void> task_causaexterna;
    public static Thread thimport_causaexterna;
    public static Task<Void> task_hclcups;
    public static Thread thimport_hclcups;
    public static Task<Void> task_hclcie10;
    public static Thread thimport_hclcie10;
    public static Task<Void> task_hclfinalidad;
    public static Thread thimport_hclfinalidad;
    public static Task<Void> task_hclcodigosconsultas;
    public static Thread thimport_hclcodigosconsultas;
    public static Task<Void> task_presentacionmedivamentos;
    public static Thread thimport_presentaciomedicamentos;
    public static Task<Void> task_tiposformulas;
    public static Thread thimport_tiposformulas;
    public static Task<Void> task_unidaddosis;
    public static Thread thimport_unidaddosis;
    public static Task<Void> task_unidadfrecuencia;
    public static Thread thimport_unidadfrecuencia;
    public static Task<Void> task_unidadtratamiento;
    public static Thread thimport_unidadtratamiento;
    public static Task<Void> task_viaadministracion;
    public static Thread thimport_viaadministracion;
    public static Task<Void> task_categorias;
    public static Thread thimport_categorias;
    public static Task<Void> task_medicos;
    public static Thread thimport_medicos;
    public static Task<Void> task_especialidades;
    public static Thread thimport_especialidades;
    public static Task<Void> task_consultorios;
    public static Thread thimport_consultorios;

    public HclHistoriasClinicasControllerClient() {
        hclHistoriasClinicasController = new HclHistoriasClinicasController();

    }

    public void getAllTiposAfiliacion() throws ParseException {
        task_eapb = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SihicApp.l_eapb.clear();
                SihicApp.l_eapb.addAll(hclHistoriasClinicasController.load_genEapb());
                loadChoiceBoxGeneral.Load_Eps();
                SihicApp.l_tiposafiliacion.clear();
                SihicApp.l_tiposafiliacion.addAll(hclHistoriasClinicasController.load_genTiposAfiliacion());
              //  EntityManagerGeneric.closeEntityManager();

                return null;
            }
        };
       /// thimport_eapb = new Thread(task_eapb);
       //// thimport_eapb.setDaemon(true);
       // thimport_eapb.start();
                SihicApp.l_eapb.clear();
                SihicApp.l_eapb.addAll(hclHistoriasClinicasController.load_genEapb());
                loadChoiceBoxGeneral.Load_Eps();
                SihicApp.l_tiposafiliacion.clear();
                SihicApp.l_tiposafiliacion.addAll(hclHistoriasClinicasController.load_genTiposAfiliacion());
    }

    public void getAllTipoAntecedentes() throws ParseException {
        task_antecedentes = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_tiposantecedentesgenerales.clear();
                SihicApp.l_tiposantecedentesgenerales.addAll(hclHistoriasClinicasController.load_hclTiposAntecedentesGenerales());
                SihicApp.l_tiposantecedentespatologicos.clear();
                SihicApp.l_tiposantecedentespatologicos.addAll(hclHistoriasClinicasController.load_hclTiposAntecedentesPatologicos());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
      //  thimport_antecedentes = new Thread(task_antecedentes);
        //thimport_antecedentes.setDaemon(true);
       // thimport_antecedentes.start();
        SihicApp.l_tiposantecedentesgenerales.clear();
                SihicApp.l_tiposantecedentesgenerales.addAll(hclHistoriasClinicasController.load_hclTiposAntecedentesGenerales());
                SihicApp.l_tiposantecedentespatologicos.clear();
                SihicApp.l_tiposantecedentespatologicos.addAll(hclHistoriasClinicasController.load_hclTiposAntecedentesPatologicos());
        

    }

    public void getAllNivelesUsuarios() throws ParseException {
        task_nivelesusuarios = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_nivelesusuarios.clear();
                SihicApp.l_nivelesusuarios.addAll(hclHistoriasClinicasController.load_genNivelesUsuarios());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
       // thimport_nivelesusuarios = new Thread(task_nivelesusuarios);
      //  thimport_nivelesusuarios.setDaemon(true);
        //thimport_nivelesusuarios.start();
         SihicApp.l_nivelesusuarios.clear();
         SihicApp.l_nivelesusuarios.addAll(hclHistoriasClinicasController.load_genNivelesUsuarios());
         
    }

    public void getAllCausaExterna() throws ParseException {
        task_causaexterna = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_hclcausaexterna.clear();
                SihicApp.l_hclcausaexterna.addAll(hclHistoriasClinicasController.getHclCausaExterna());
                EntityManagerGeneric.closeEntityManager();

                return null;
            }
        };
      //  thimport_causaexterna = new Thread(task_causaexterna);
      //  thimport_causaexterna.setDaemon(true);
       // thimport_causaexterna.start();
                SihicApp.l_hclcausaexterna.clear();
                SihicApp.l_hclcausaexterna.addAll(hclHistoriasClinicasController.getHclCausaExterna());
                
    }

    public void getAllFinalidad() throws ParseException {
        task_hclfinalidad = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_hclfinalidad.clear();
                SihicApp.l_hclfinalidad.addAll(hclHistoriasClinicasController.getHclFinalidad());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
        //thimport_hclfinalidad = new Thread(task_hclfinalidad);
       // thimport_hclfinalidad.setDaemon(true);
       // thimport_hclfinalidad.start();
        SihicApp.l_hclfinalidad.clear();
                SihicApp.l_hclfinalidad.addAll(hclHistoriasClinicasController.getHclFinalidad());
                
        
    }

    public void getAllCodigoConsultas() throws ParseException {
        task_hclcodigosconsultas = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_hclcodigosconsultas.clear();
                SihicApp.l_hclcodigosconsultas.addAll(hclHistoriasClinicasController.load_hclCodigosConsultas());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
       // thimport_hclcodigosconsultas = new Thread(task_hclcodigosconsultas);
       // thimport_hclcodigosconsultas.setDaemon(true);
       // thimport_hclcodigosconsultas.start();
        SihicApp.l_hclcodigosconsultas.clear();
                SihicApp.l_hclcodigosconsultas.addAll(hclHistoriasClinicasController.load_hclCodigosConsultas());
                
    }

    public void getAllPresentacionMedicamentos() throws ParseException {
        task_presentacionmedivamentos = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_medpresentacionmedicamentos.clear();
                SihicApp.l_medpresentacionmedicamentos.addAll(hclHistoriasClinicasController.getMedPresentacionMedicamentos());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
        //thimport_presentaciomedicamentos = new Thread(task_presentacionmedivamentos);
        //thimport_presentaciomedicamentos.setDaemon(true);
        //thimport_presentaciomedicamentos.start();
         SihicApp.l_medpresentacionmedicamentos.clear();
                SihicApp.l_medpresentacionmedicamentos.addAll(hclHistoriasClinicasController.getMedPresentacionMedicamentos());
              
    }

    public void getAllTiposFormulas() throws ParseException {
        task_tiposformulas = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_tiposformulas.clear();
                SihicApp.l_tiposformulas.addAll(hclHistoriasClinicasController.getHclTiposFormulas());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
      //  thimport_tiposformulas = new Thread(task_tiposformulas);
      //  thimport_tiposformulas.setDaemon(true);
       // thimport_tiposformulas.start();
       SihicApp.l_tiposformulas.clear();
       SihicApp.l_tiposformulas.addAll(hclHistoriasClinicasController.getHclTiposFormulas());
                
    }

    public void getAllUnidadDosis() throws ParseException {
        task_unidaddosis = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_unidaddosis.clear();
                SihicApp.l_unidaddosis.addAll(hclHistoriasClinicasController.getMedUnidadDosis());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
      //  thimport_unidaddosis = new Thread(task_unidaddosis);
      //  thimport_unidaddosis.setDaemon(true);
       // thimport_unidaddosis.start();
       SihicApp.l_unidaddosis.clear();
                SihicApp.l_unidaddosis.addAll(hclHistoriasClinicasController.getMedUnidadDosis());
                
    }

    public void getAllUnidadFrecuencia() throws ParseException {
        task_unidadfrecuencia = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_unidadfrecuencia.clear();
                SihicApp.l_unidadfrecuencia.addAll(hclHistoriasClinicasController.getMedUnidadFrecuencia());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
      //  thimport_unidadfrecuencia = new Thread(task_unidadfrecuencia);
     //   thimport_unidadfrecuencia.setDaemon(true);
       // thimport_unidadfrecuencia.start();
        SihicApp.l_unidadfrecuencia.clear();
                SihicApp.l_unidadfrecuencia.addAll(hclHistoriasClinicasController.getMedUnidadFrecuencia());
                
    }

    public void getAllUnidadTRatamiento() throws ParseException {
        task_unidadtratamiento = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_unidadtratamiento.clear();
                SihicApp.l_unidadtratamiento.addAll(hclHistoriasClinicasController.getMedUnidadTratamiento());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
       // thimport_unidadtratamiento = new Thread(task_unidadtratamiento);
       // thimport_unidadtratamiento.setDaemon(true);
        //thimport_unidadtratamiento.start();
           SihicApp.l_unidadtratamiento.clear();
                SihicApp.l_unidadtratamiento.addAll(hclHistoriasClinicasController.getMedUnidadTratamiento());
                
    }

    public void getCategorias() throws ParseException {
        task_categorias = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_categoriasproductos.clear();
                SihicApp.l_categoriasproductos.addAll(hclHistoriasClinicasController.getGenCategoriasProductos());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
       // thimport_categorias = new Thread(task_categorias);
       // thimport_categorias.setDaemon(true);
       // thimport_categorias.start();
         SihicApp.l_categoriasproductos.clear();
                SihicApp.l_categoriasproductos.addAll(hclHistoriasClinicasController.getGenCategoriasProductos());
                
    }

    public void getViaAdministracion() throws ParseException {
        task_viaadministracion = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.l_viaadministracion.clear();
                SihicApp.l_viaadministracion.addAll(hclHistoriasClinicasController.getMedViasAdministracion());
                EntityManagerGeneric.closeEntityManager();
                return null;
            }
        };
       // thimport_viaadministracion = new Thread(task_viaadministracion);
       // thimport_viaadministracion.setDaemon(true);
       // thimport_viaadministracion.start();
          SihicApp.l_viaadministracion.clear();
                SihicApp.l_viaadministracion.addAll(hclHistoriasClinicasController.getMedViasAdministracion());
                
    }

    public void getHclCups() throws ParseException {
        task_hclcups = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                SihicApp.li_hclcups.clear();
                SihicApp.li_hclcups.addAll(hclHistoriasClinicasController.getCups(null));
                EntityManagerGeneric.closeEntityManager();
                System.out.println("size cups->"+ SihicApp.li_hclcups);
                return null;
            }
        };
       // thimport_hclcups = new Thread(task_hclcups);
       // thimport_hclcups.setDaemon(true);
       // thimport_hclcups.start();
        SihicApp.li_hclcups.clear();
                SihicApp.li_hclcups.addAll(hclHistoriasClinicasController.getCups(null));
                
    }

    public void getHclCie10() throws ParseException {
        task_hclcie10 = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                SihicApp.li_hclcie10.clear();
                SihicApp.li_hclcie10.addAll(hclHistoriasClinicasController.getLi_hclcie10());
                return null;
            }
        };
       // thimport_hclcie10 = new Thread(task_hclcie10);
       // thimport_hclcie10.setDaemon(true);
       // thimport_hclcie10.start();
         SihicApp.li_hclcie10.clear();
                SihicApp.li_hclcie10.addAll(hclHistoriasClinicasController.getLi_hclcie10());

    }

    public List<HclAntecedentesGenerales> getHclAntecedentesGenerales(HclConsultas hc) {
        return hclHistoriasClinicasController.getHclAntecedentesGenerales(hc);
    }

    public List<HclAntecedentesGineco> getHclAntecedentesGineco(HclConsultas hc) {
        return hclHistoriasClinicasController.getHclAntecedentesGineco(hc);
    }

    public List<HclAntecedentesFarmacologicos> getHclAntecedentesFarmacologicos(HclConsultas hc) {
        return hclHistoriasClinicasController.getHclAntecedentesFarmacologicos(hc);
    }

    public List<HclAntecedentesPatologicos> getHclAntecedentesPatologicos(HclConsultas hc) {
        return hclHistoriasClinicasController.getHclAntecedentesPatologicos();
    }

    public void saveAntecedentesGenerales(HclAntecedentesGenerales hc) {
        hclHistoriasClinicasController.saveAntecedentesGenerales(hc);
    }

    public void saveAntecedentesGFarmacologicos(HclAntecedentesFarmacologicos hc) {
        hclHistoriasClinicasController.saveAntecedentes(hc);
    }

    public void saveAntecedentesGineco(HclAntecedentesGineco hc) {
        hclHistoriasClinicasController.saveAntecedentesGineco(hc);
    }

    public void saveAntecedentesPatologicos(HclAntecedentesPatologicos hc) {
        hclHistoriasClinicasController.saveAntecedentesPatologicos(hc);
    }

    public List<HclCie10> getCie10(String search) throws ParseException {
        return hclHistoriasClinicasController.getCie10(search);
    }

    public List<HclCups> getCups(String search) throws ParseException {
        return hclHistoriasClinicasController.getCups(search);
    }

    public List<HclDiagnosticosRelacionados> getDiagnosticosRelacionados(HclDiagnostico hclDiagnostico) throws ParseException {
        return hclHistoriasClinicasController.getHclDiagnosticosRelacionados(hclDiagnostico);
    }

    public List<HclFormulacionesMedicas> getHclFormulacionesMedicas(HclConsultas c) {
        return hclHistoriasClinicasController.getHclFormulacionesMedicas(c);
    }

    public List<HclFormulacionMedicamentos> getHclFormulacionesMedicamentos(HclFormulacionesMedicas fm) {
        return hclHistoriasClinicasController.getHclFormulacionesMedicamentos(fm);
    }

    public List<HclFormulacionProcedimientos> getHclFormulacionProcedimientos(HclFormulacionesMedicas fm) {
        return hclHistoriasClinicasController.getHclFormulacionProcedimientos(fm);
    }

    public List<FacturaItem> getRips(String datefrom, String dateto) throws ParseException {
        return hclHistoriasClinicasController.getRips(datefrom, dateto);
    }

    public void getListEspecialidades() throws ParseException {
          task_especialidades=new Task<Void>() {
        
            @Override
            protected Void call() throws Exception {
       SihicApp.l_especialidades.clear();
        SihicApp.l_especialidades.addAll(hclHistoriasClinicasController.getListEspecialidades());
         loadChoiceBoxGeneral.Load_Agnespecialidades();
          EntityManagerGeneric.closeEntityManager();
                          return null;
         }
        };
         // thimport_especialidades = new Thread(task_especialidades);
         // thimport_especialidades.setDaemon(true);
          //thimport_especialidades.start();
            SihicApp.l_especialidades.clear();
        SihicApp.l_especialidades.addAll(hclHistoriasClinicasController.getListEspecialidades());
         loadChoiceBoxGeneral.Load_Agnespecialidades();
    }

    public void saveEspecialidades(AgnEspecialidades agnEspecialidades) {
        hclHistoriasClinicasController.saveEspecialidades(agnEspecialidades);
    }

    public AgnEspecialidades updateEspecialidades(AgnEspecialidades agnEspecialidades) {
        return hclHistoriasClinicasController.updateEspecialidades(agnEspecialidades);
    }

    public void getListConsultorios() throws ParseException {

        task_consultorios=new Task<Void>() {
        
            @Override
            protected Void call() throws Exception {
            SihicApp.l_consultorios.clear();
            SihicApp.l_consultorios.addAll(hclHistoriasClinicasController.getListConsultorios());
            System.out.println("size consultorios->"+SihicApp.l_consultorios.size());
            loadChoiceBoxGeneral.Load_Agnconsultorios();
            EntityManagerGeneric.closeEntityManager();
            return null;
         }
        };
        //  thimport_consultorios = new Thread(task_consultorios);
          //thimport_consultorios.setDaemon(true);
         // thimport_consultorios.start();
          SihicApp.l_consultorios.clear();
            SihicApp.l_consultorios.addAll(hclHistoriasClinicasController.getListConsultorios());
            System.out.println("size consultorios->"+SihicApp.l_consultorios.size());
            loadChoiceBoxGeneral.Load_Agnconsultorios();
         
      

    }

    public void saveConsultorios(AgnConsultorios agnConsultorios) {
        hclHistoriasClinicasController.saveConsultorios(agnConsultorios);
    }

    public AgnConsultorios updateConsultorios(AgnConsultorios agnConsultorios) {
        return hclHistoriasClinicasController.updateConsultorios(agnConsultorios);
    }

    public void getListMedicos(String search) throws ParseException {
   
          task_medicos=new Task<Void>() {
        
            @Override
            protected Void call() throws Exception {
             SihicApp.l_medicos.clear();
        SihicApp.l_medicos.addAll(hclHistoriasClinicasController.getListMedicos(search));
        loadChoiceBoxGeneral.Load_Agnmedicos();

               EntityManagerGeneric.closeEntityManager();
          
                          return null;
         }
        };
        //  thimport_medicos = new Thread(task_medicos);
         // thimport_medicos.setDaemon(true);
        //  thimport_medicos.start();
          SihicApp.l_medicos.clear();
        SihicApp.l_medicos.addAll(hclHistoriasClinicasController.getListMedicos(search));
        loadChoiceBoxGeneral.Load_Agnmedicos();

             

    }

    public void saveMedicos(AgnMedicos agnMedicos) {
        hclHistoriasClinicasController.saveMedicos(agnMedicos);
    }

    public AgnMedicos updateMedicos(AgnMedicos agnMedicos) {
        return hclHistoriasClinicasController.updateMedicos(agnMedicos);
    }

    public List<AgnConsultoriosProcedimientos> getListConsultoriosProcedimientos(String search) throws ParseException {

        return hclHistoriasClinicasController.getListConsultoriosProcedimientos(search);
    }

    public void saveConsultoriosProcedimientos(AgnConsultoriosProcedimientos agnConsultoriosProcedimientos) {

        hclHistoriasClinicasController.saveConsultoriosProcedimientos(agnConsultoriosProcedimientos);
    }

    public AgnConsultoriosProcedimientos updateConsultoriosProcedimientos(AgnConsultoriosProcedimientos agnConsultoriosProcedimientos) {
        return hclHistoriasClinicasController.updateConsultoriosProcedimientos(agnConsultoriosProcedimientos);
    }

}
