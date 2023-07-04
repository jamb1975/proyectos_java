/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.controlador;

import java.text.ParseException;
import java.time.LocalDate;
import javafx.concurrent.Task;
import modelo.GenConvenios;
import modelo.GenUnidadesOrganizacion;
import service.EntityManagerGeneric;
import service.GenConveniosEapbController;
import service.GeneralController;
import sihic.SihicApp;
import static sihic.SihicApp.loadChoiceBoxGeneral;

/**
 *
 * @author adminlinux
 */
public class GeneralControllerClient {

    private GeneralController generalController;
    private GenConveniosEapbController genConveniosEapbController;
    private ConPucControllerClient conPucControllerClient;
    private GenTiposUsuariosControllerClient genTiposUsuariosControllerClient;
    public static Task<Void> task_puc;
    public static Thread thimport_puc;
    public static Task<Void> task_dpto;
    public static Thread thimport_dpto;
    public static Thread thimport_municipios;
    public static Task<Void> task_municipios;
    public static Thread thimport_convenios;
    public static Task<Void> task_convenios;
    public static Thread thimport_tiposusuarios;
    public static Task<Void> task_tiposusuarios;
    public static Thread thimport_estadosciviles;
    public static Task<Void> task_estadosciviles;
    public static Thread thimport_etnias;
    public static Task<Void> task_etnias;
    public static Thread thimport_niveleseducativos;
    public static Task<Void> task_niveleseducativos;
    public static Thread thimport_sexo;
    public static Task<Void> task_sexo;
    public static Thread thimport_tiposdoc;
    public static Task<Void> task_tiposdoc;
    public static Thread thimport_zona;
    public static Task<Void> task_zona;
    public static Thread thimport_profesiones;
    public static Task<Void> task_profesiones;

    public GeneralControllerClient() {
        generalController = new GeneralController();
        genConveniosEapbController = new GenConveniosEapbController(GenConvenios.class);
        conPucControllerClient = new ConPucControllerClient();
        genTiposUsuariosControllerClient = new GenTiposUsuariosControllerClient();
    }

    public void load_gentiposdocumentos() {
         task_tiposdoc=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                SihicApp.getL_gentiposdocumentos().clear();
                 SihicApp.getL_gentiposdocumentos().addAll(generalController.load_gentiposdocumentos());
                  loadChoiceBoxGeneral.Load_GenTiposDOcumentos();
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
           SihicApp.getL_gentiposdocumentos().clear();
                 SihicApp.getL_gentiposdocumentos().addAll(generalController.load_gentiposdocumentos());
                  loadChoiceBoxGeneral.Load_GenTiposDOcumentos();
         
    
    }

    public void load_genniveleeducativos() {
        task_niveleseducativos=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                SihicApp.getL_genniveleseducativos().clear();
                SihicApp.getL_genniveleseducativos().addAll(generalController.load_genniveleeducativos());
                loadChoiceBoxGeneral.Load_GenNivelesEducativos();
                EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
           SihicApp.getL_genniveleseducativos().clear();
                SihicApp.getL_genniveleseducativos().addAll(generalController.load_genniveleeducativos());
                loadChoiceBoxGeneral.Load_GenNivelesEducativos();
    
    }

    public void load_gensexo() {
          task_sexo=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                SihicApp.getL_gensexo().clear();
                SihicApp.getL_gensexo().addAll(generalController.load_gensexo());
                loadChoiceBoxGeneral.Load_GenSexo(); 
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
           SihicApp.getL_gensexo().clear();
                SihicApp.getL_gensexo().addAll(generalController.load_gensexo());
                loadChoiceBoxGeneral.Load_GenSexo(); 
    
       
    }

    public void load_genmunicipios() {
        task_municipios=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                SihicApp.getL_genmunicipios().clear();
                 SihicApp.getL_genmunicipios().addAll(generalController.load_genmunicipios());
                 
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
       //  thimport_municipios = new Thread(task_municipios);
         // thimport_municipios.setDaemon(true);
        //  thimport_municipios.start();
          SihicApp.getL_genmunicipios().clear();
          SihicApp.getL_genmunicipios().addAll(generalController.load_genmunicipios());
         
      
    }

    public void load_gendepartamentos() {
        task_dpto = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SihicApp.getL_gendepartamentos().clear();
                SihicApp.getL_gendepartamentos().addAll(generalController.load_gendepartamentos());
                SihicApp.loadChoiceBoxGeneral.Load_GenDepartamentos();

                EntityManagerGeneric.closeEntityManager();

                return null;
            }
        };
           SihicApp.getL_gendepartamentos().addAll(generalController.load_gendepartamentos());
                SihicApp.loadChoiceBoxGeneral.Load_GenDepartamentos();

    }

    public void load_genetnias() {
       task_etnias=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                SihicApp.getL_genetnias().clear();
                 SihicApp.getL_genetnias().addAll(generalController.load_genetnias());
                   loadChoiceBoxGeneral.Load_GenEtnias();
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
                SihicApp.getL_genetnias().clear();
                 SihicApp.getL_genetnias().addAll(generalController.load_genetnias());
                   loadChoiceBoxGeneral.Load_GenEtnias();

    }

    public void load_genzonaresidencia() {
           task_zona=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
             SihicApp.getL_genzonaresidencia().clear();
        SihicApp.getL_genzonaresidencia().addAll(generalController.load_genzonaresidencia());
        loadChoiceBoxGeneral.Load_GenZonaResidencia();
        
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
        SihicApp.getL_genzonaresidencia().clear();
        SihicApp.getL_genzonaresidencia().addAll(generalController.load_genzonaresidencia());
        loadChoiceBoxGeneral.Load_GenZonaResidencia();
    }

    public void load_genprofesiones() {
         task_profesiones=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                SihicApp.getL_genprofesiones().clear();
                SihicApp.getL_genprofesiones().addAll(generalController.load_genprofesiones());
                loadChoiceBoxGeneral.Load_GenProfesiones();
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
          SihicApp.getL_genprofesiones().clear();
          SihicApp.getL_genprofesiones().addAll(generalController.load_genprofesiones());
          loadChoiceBoxGeneral.Load_GenProfesiones();
     
    }

    public void load_genestadosciviles() {
        task_estadosciviles=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                SihicApp.getL_genestadosciviles().clear();
                SihicApp.getL_genestadosciviles().addAll(generalController.load_genestadosciviles());
                loadChoiceBoxGeneral.Load_GenEstadosCiviles(); 
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
                    SihicApp.getL_genestadosciviles().clear();
                SihicApp.getL_genestadosciviles().addAll(generalController.load_genestadosciviles());
                loadChoiceBoxGeneral.Load_GenEstadosCiviles(); 

      

    }

    public void load_conveniosp() throws ParseException {
        task_convenios=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                SihicApp.l_conveniosp.clear();
                 SihicApp.l_conveniosp.addAll(genConveniosEapbController.convenioseps(LocalDate.now().toString(), LocalDate.now().toString(), null, 1));
                 loadChoiceBoxGeneral.Load_ConveniosP();
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
                     SihicApp.l_conveniosp.clear();
                 SihicApp.l_conveniosp.addAll(genConveniosEapbController.convenioseps(LocalDate.now().toString(), LocalDate.now().toString(), null, 1));
                 loadChoiceBoxGeneral.Load_ConveniosP();

    }

    public GenUnidadesOrganizacion load_GenUnidadesOrganizacion() {
        GenUnidadesOrganizacion genUnidadesOrganizacion = new GenUnidadesOrganizacion();
        genUnidadesOrganizacion = generalController.load_GenUnidadesOrganizacion(genUnidadesOrganizacion);
        return genUnidadesOrganizacion;
    }

    public void load_conpuc() {
        task_puc = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SihicApp.li_conpuc.clear();
                SihicApp.li_conpuc.addAll(conPucControllerClient.li_conpuc(null));
                System.out.println("size puc_>" + SihicApp.li_conpuc.size());
                return null;
            }
        };
        thimport_puc = new Thread(task_puc);
        thimport_puc.setDaemon(true);
        thimport_puc.start();

    }

    public void load_gentiposusuarios() {
        task_tiposusuarios=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                 SihicApp.l_tiposusuarios.clear();
                 SihicApp.l_tiposusuarios.addAll(genTiposUsuariosControllerClient.li_genTiposUsuarios());
                 loadChoiceBoxGeneral.Load_GenTiposUsuarios();
                 EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
        /// thimport_tiposusuarios = new Thread(task_tiposusuarios);
       //   thimport_tiposusuarios.setDaemon(true);
        //  thimport_tiposusuarios.start();
           SihicApp.l_tiposusuarios.clear();
                 SihicApp.l_tiposusuarios.addAll(genTiposUsuariosControllerClient.li_genTiposUsuarios());
                 loadChoiceBoxGeneral.Load_GenTiposUsuarios();
       

    }
}
