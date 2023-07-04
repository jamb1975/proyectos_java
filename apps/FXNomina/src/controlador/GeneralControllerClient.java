/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.text.ParseException;
import java.time.LocalDate;
import javafx.concurrent.Task;
import modelo.GenUnidadesOrganizacion;
import servicios.EntityManagerGeneric;
import servicios.GeneralController;
import fxnomina.FXNomina;
import static fxnomina.FXNomina.loadChoiceBoxGeneral;

/**
 *
 * @author adminlinux
 */
public class GeneralControllerClient {

    private GeneralController generalController;
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
    
    }

    public void load_gentiposdocumentos() {
         task_tiposdoc=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                FXNomina.l_gentiposdocumentos.clear();
                 FXNomina.l_gentiposdocumentos.addAll(generalController.load_gentiposdocumentos());
                  loadChoiceBoxGeneral.Load_GenTiposDOcumentos();
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
           FXNomina.l_gentiposdocumentos.clear();
                 FXNomina.l_gentiposdocumentos.addAll(generalController.load_gentiposdocumentos());
                  loadChoiceBoxGeneral.Load_GenTiposDOcumentos();
         
    
    }

  

    public void load_gensexo() {
          task_sexo=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                FXNomina.getL_gensexo().clear();
                FXNomina.getL_gensexo().addAll(generalController.load_gensexo());
                loadChoiceBoxGeneral.Load_GenSexo(); 
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
           FXNomina.getL_gensexo().clear();
                FXNomina.getL_gensexo().addAll(generalController.load_gensexo());
                loadChoiceBoxGeneral.Load_GenSexo(); 
    
       
    }

    public void load_genmunicipios() {
        task_municipios=new Task<Void>() {
        @Override
            protected Void call() throws Exception {
                FXNomina.getL_genmunicipios().clear();
                 FXNomina.getL_genmunicipios().addAll(generalController.load_genmunicipios());
                 
        EntityManagerGeneric.closeEntityManager();
           
                  return null;
            }
        };
       //  thimport_municipios = new Thread(task_municipios);
         // thimport_municipios.setDaemon(true);
        //  thimport_municipios.start();
          FXNomina.getL_genmunicipios().clear();
          FXNomina.getL_genmunicipios().addAll(generalController.load_genmunicipios());
         
      
    }

    public void load_gendepartamentos() {
        task_dpto = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                FXNomina.getL_gendepartamentos().clear();
                FXNomina.getL_gendepartamentos().addAll(generalController.load_gendepartamentos());
                FXNomina.loadChoiceBoxGeneral.Load_GenDepartamentos();

                EntityManagerGeneric.closeEntityManager();

                return null;
            }
        };
           FXNomina.getL_gendepartamentos().addAll(generalController.load_gendepartamentos());
                FXNomina.loadChoiceBoxGeneral.Load_GenDepartamentos();

    }

  
    public GenUnidadesOrganizacion load_GenUnidadesOrganizacion() {
        GenUnidadesOrganizacion genUnidadesOrganizacion = new GenUnidadesOrganizacion();
        genUnidadesOrganizacion = generalController.load_GenUnidadesOrganizacion(genUnidadesOrganizacion);
        return genUnidadesOrganizacion;
    }

    
}
