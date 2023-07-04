/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import java.math.BigDecimal;
import java.util.Vector;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import modelo.CargosEntidad;
import modelo.Empleados;
import modelo.FactorHorasExtras;
import modelo.GenDepartamentos;
import modelo.GenEapb;
import modelo.GenMunicipios;
import modelo.GenSexo;
import modelo.GenTiposDocumentos;

import fxnomina.FXNomina;
import util.AutoCompleteComboBoxListener;

/**
 *
 * @author adminlinux
 */
public final class LoadChoiceBoxGeneral {

    private static ChoiceBox gen_tipos_documentos_id=new ChoiceBox();
    private static Vector<GenTiposDocumentos> v_gen_tipos_documentos = new Vector<GenTiposDocumentos>();
    private static ChoiceBox gen_niveles_educativos_id;
    private static Vector<Long> v_gen_niveleseducativos = new Vector<Long>();
    private static ChoiceBox gen_sexo_id;
    private static Vector<Long> v_gen_sexo = new Vector<Long>();
    private static ChoiceBox gen_estados_civiles_id;
    private static Vector<Long> v_gen_estados_civiles = new Vector<Long>();
    private static ChoiceBox gen_municipios_id;
    private static Vector<Long> v_gen_municipios = new Vector<Long>();
    private static ChoiceBox gen_departamentos_id;
    public static Vector<Long> v_gen_departamentos = new Vector<Long>();
    private static ChoiceBox gen_zona_residencia_id;
    private static ChoiceBox gen_profesiones_id;
    private static Vector<Long> v_gen_profesiones = new Vector<Long>();
    private static ChoiceBox gen_etnias_id;
    private static Vector<Long> v_gen_etnias = new Vector<Long>();
    private static ChoiceBox cb_agnmedicos;
    private static ChoiceBox cb_agnespecialidades;
    private static ChoiceBox cb_agnconsultorios;
    private static ChoiceBox cb_eps;
    private static Vector<GenEapb> v_eps = new Vector<GenEapb>();
    private static ChoiceBox cb_conveniosp;
     private static ComboBox cb_eapb;
    private static Vector<GenEapb> v_eapb = new Vector<GenEapb>();
    private static ChoiceBox cb_gentiposusuarios;
    private static ChoiceBox cb_especialidades;
    private static ChoiceBox cb_unidadesmedida = new ChoiceBox();
    public static ChoiceBox cb_cargosentidad = new ChoiceBox();
    public static Vector<CargosEntidad> v_cargosentidad = new Vector<CargosEntidad>();
    public static ChoiceBox cb_empleados = new ChoiceBox();
    public static Vector<Empleados> v_empleados = new Vector<Empleados>();
    public static ChoiceBox cb_tiposdocumentoscontables = new ChoiceBox();
    public static ChoiceBox cb_ModelosTiposAsientosContable = new ChoiceBox();
    public static ChoiceBox cb_centrocostos = new ChoiceBox();
    public static ChoiceBox cb_conseuctivosnofactutasporsucursal = new ChoiceBox();
    public static ChoiceBox cb_factorhorasextras= new ChoiceBox();
    public static Vector<BigDecimal> v_factorhorasextras=new Vector<>();
    public static ChoiceBox cb_sucursales = new ChoiceBox();
    
    public LoadChoiceBoxGeneral() {
        gen_departamentos_id = new ChoiceBox<>();

    }
    // A change listener to track the change in selected item

    

    

    public void Load_Eps() {
        cb_eps = null;
        cb_eps = new ChoiceBox();

        cb_eps.getItems().clear();
        //   m_CBCategoria.getStyleClass().add("choice-box");
        int i = 0;
        if (FXNomina.l_eapb.size() > 0) {
            v_eps.clear();
            for (GenEapb e : FXNomina.l_eapb) {
                v_eps.add(i, e);
                cb_eps.getItems().add(i, e.getNombre());

            }
        }
    }

    

    public void Load_GenTiposDOcumentos() {
      
        gen_tipos_documentos_id.setMaxHeight(10);
        gen_tipos_documentos_id.getItems().clear();
        //   m_CBCategoria.getStyleClass().add("choice-box");
        int i = 0;
        if (FXNomina.l_gentiposdocumentos.size() > 0) {
            v_gen_tipos_documentos.clear();
            for (GenTiposDocumentos d : FXNomina.l_gentiposdocumentos) {
                v_gen_tipos_documentos.add(d);
                gen_tipos_documentos_id.getItems().add(d.getNombre());

            }
        }
    }

    

    public void Load_GenSexo() {
        gen_sexo_id = null;
        gen_sexo_id = new ChoiceBox();
        gen_sexo_id.setMaxHeight(10);
        int i = 0;
        if (FXNomina.getL_gensexo().size() > 0) {
            v_gen_sexo.clear();
            for (GenSexo d : FXNomina.getL_gensexo()) {
                v_gen_sexo.add(i, d.getId());
                gen_sexo_id.getItems().add(i, d.getDescripcion());

            }
        }
    }

    

   

    public static void Load_GenMunicipios() {
        gen_municipios_id = null;
        gen_municipios_id = new ChoiceBox();
        gen_municipios_id.setMaxHeight(10);

        int i = 0;
        int index = gen_departamentos_id.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Long id_dpto = v_gen_departamentos.get(index);

            System.out.println("Indexchanged: index = " + index);

            if (FXNomina.getL_genmunicipios().size() > 0) {
                v_gen_municipios.clear();
                for (GenMunicipios d : FXNomina.getL_genmunicipios()) {
                    if (id_dpto.equals(d.getGenDepartamentos().getId())) {
                        v_gen_municipios.add(i, d.getId());
                        gen_municipios_id.getItems().add(i, d.getNombre());
                    }

                }
            }
        }
    }

    public void Load_GenDepartamentos() {

        gen_departamentos_id = null;
        gen_departamentos_id = new ChoiceBox<>();

        gen_departamentos_id.setMaxHeight(10);
        int i = 0;
        if (FXNomina.getL_gendepartamentos().size() > 0) {
            v_gen_departamentos.clear();
            for (GenDepartamentos d : FXNomina.getL_gendepartamentos()) {
                v_gen_departamentos.add(i, d.getId());
                gen_departamentos_id.getItems().add(i, d.getNombre());

            }
        }
    }


    
    

    public void Load_Eapb() {
        cb_eapb = null;
        cb_eapb = new ComboBox();
        cb_eapb.setEditable(true);
        cb_eapb.getItems().clear();
        cb_eapb.setMaxWidth(300);
        int i = 0;
        if (FXNomina.l_eapb.size() > 0) {
            v_eapb.clear();
            for (GenEapb e : FXNomina.l_eapb) {
                v_eapb.add(i, e);
                cb_eapb.getItems().add(i, e.getNombre());

            }
            cb_eapb.getItems().add(0, "Ninguno");
        }

        new AutoCompleteComboBoxListener<>(cb_eapb);
    }

    
   
    public static ChoiceBox getGen_tipos_documentos_id() {
        return gen_tipos_documentos_id;
    }

    public static void setGen_tipos_documentos_id(ChoiceBox gen_tipos_documentos_id) {
        LoadChoiceBoxGeneral.gen_tipos_documentos_id = gen_tipos_documentos_id;
    }

    public static Vector<GenTiposDocumentos> getV_gen_tipos_documentos() {
        return v_gen_tipos_documentos;
    }

    public void setV_gen_tipos_documentos(Vector<GenTiposDocumentos> v_gen_tipos_documentos) {
        this.v_gen_tipos_documentos = v_gen_tipos_documentos;
    }

    public static ChoiceBox getGen_niveles_educativos_id() {
        return gen_niveles_educativos_id;
    }

    public static void setGen_niveles_educativos_id(ChoiceBox gen_niveles_educativos_id) {
        LoadChoiceBoxGeneral.gen_niveles_educativos_id = gen_niveles_educativos_id;
    }

    public static Vector<Long> getV_gen_niveleseducativos() {
        return v_gen_niveleseducativos;
    }

    public void setV_gen_niveleseducativos(Vector<Long> v_gen_niveleseducativos) {
        this.v_gen_niveleseducativos = v_gen_niveleseducativos;
    }

    public static ChoiceBox getGen_sexo_id() {
        return gen_sexo_id;
    }

    public static void setGen_sexo_id(ChoiceBox gen_sexo_id) {
        LoadChoiceBoxGeneral.gen_sexo_id = gen_sexo_id;
    }

    public static Vector<Long> getV_gen_sexo() {
        return v_gen_sexo;
    }

    public void setV_gen_sexo(Vector<Long> v_gen_sexo) {
        this.v_gen_sexo = v_gen_sexo;
    }

    public static ChoiceBox getGen_estados_civiles_id() {
        return gen_estados_civiles_id;
    }

    public static void setGen_estados_civiles_id(ChoiceBox gen_estados_civiles_id) {
        LoadChoiceBoxGeneral.gen_estados_civiles_id = gen_estados_civiles_id;
    }

    public static Vector<Long> getV_gen_estados_civiles() {
        return v_gen_estados_civiles;
    }

    public void setV_gen_estados_civiles(Vector<Long> v_gen_estados_civiles) {
        this.v_gen_estados_civiles = v_gen_estados_civiles;
    }

    public static ChoiceBox getGen_municipios_id() {
        return gen_municipios_id;
    }

    public static void setGen_municipios_id(ChoiceBox gen_municipios_id) {
        LoadChoiceBoxGeneral.gen_municipios_id = gen_municipios_id;
    }

    public static Vector<Long> getV_gen_municipios() {
        return v_gen_municipios;
    }

    public void setV_gen_municipios(Vector<Long> v_gen_municipios) {
        this.v_gen_municipios = v_gen_municipios;
    }

    public static ChoiceBox getGen_departamentos_id() {
        return gen_departamentos_id;
    }

    public static void setGen_departamentos_id(ChoiceBox gen_departamentos_id) {
        LoadChoiceBoxGeneral.gen_departamentos_id = gen_departamentos_id;
    }

    public static Vector<Long> getV_gen_departamentos() {
        return v_gen_departamentos;
    }

    public void setV_gen_departamentos(Vector<Long> v_gen_departamentos) {
        this.v_gen_departamentos = v_gen_departamentos;
    }

    public static ChoiceBox getGen_zona_residencia_id() {
        return gen_zona_residencia_id;
    }

    public static void setGen_zona_residencia_id(ChoiceBox gen_zona_residencia_id) {
        LoadChoiceBoxGeneral.gen_zona_residencia_id = gen_zona_residencia_id;
    }

   

    public static ChoiceBox getGen_profesiones_id() {
        return gen_profesiones_id;
    }

    public static void setGen_profesiones_id(ChoiceBox gen_profesiones_id) {
        LoadChoiceBoxGeneral.gen_profesiones_id = gen_profesiones_id;
    }

    public static Vector<Long> getV_gen_profesiones() {
        return v_gen_profesiones;
    }

    public void setV_gen_profesiones(Vector<Long> v_gen_profesiones) {
        this.v_gen_profesiones = v_gen_profesiones;
    }

    public static ChoiceBox getGen_etnias_id() {
        return gen_etnias_id;
    }

    public static void setGen_etnias_id(ChoiceBox gen_etnias_id) {
        LoadChoiceBoxGeneral.gen_etnias_id = gen_etnias_id;
    }

    public static Vector<Long> getV_gen_etnias() {
        return v_gen_etnias;
    }

    public static ChoiceBox getCb_agnmedicos() {
        return cb_agnmedicos;
    }

    public static void setCb_agnmedicos(ChoiceBox cb_agnmedicos) {
        LoadChoiceBoxGeneral.cb_agnmedicos = cb_agnmedicos;
    }

    
    public static ChoiceBox getCb_agnespecialidades() {
        return cb_agnespecialidades;
    }

    public static void setCb_agnespecialidades(ChoiceBox cb_agnespecialidades) {
        LoadChoiceBoxGeneral.cb_agnespecialidades = cb_agnespecialidades;
    }

    
    public static ChoiceBox getCb_agnconsultorios() {
        return cb_agnconsultorios;
    }

    public static void setCb_agnconsultorios(ChoiceBox cb_agnconsultorios) {
        LoadChoiceBoxGeneral.cb_agnconsultorios = cb_agnconsultorios;
    }

   

    public void setV_gen_etnias(Vector<Long> v_gen_etnias) {
        this.v_gen_etnias = v_gen_etnias;
    }

    public static Long extraer_id_choicebox(ChoiceBox cb2, Vector v2) {
        ChoiceBox cb = new ChoiceBox();
        cb = cb2;
        Vector v = new Vector();
        v = v2;
        int index = cb.getSelectionModel().getSelectedIndex();
        Long id_gen = null;
        if (index >= 0) {
            id_gen = (Long) v.get(index);
        }

        return id_gen;
    }

    public static ChoiceBox getCb_eps() {
        return cb_eps;
    }

    public static void setCb_eps(ChoiceBox cb_eps) {
        LoadChoiceBoxGeneral.cb_eps = cb_eps;
    }

    public static Vector<GenEapb> getV_eps() {
        return v_eps;
    }

    public static void setV_eps(Vector<GenEapb> v_eps) {
        LoadChoiceBoxGeneral.v_eps = v_eps;
    }

    public static ChoiceBox getCb_conveniosp() {
        return cb_conveniosp;
    }

    public static void setCb_conveniosp(ChoiceBox cb_conveniosp) {
        LoadChoiceBoxGeneral.cb_conveniosp = cb_conveniosp;
    }


    public static ComboBox getCb_eapb() {
        return cb_eapb;
    }

    public static void setCb_eapb(ComboBox cb_eapb) {
        LoadChoiceBoxGeneral.cb_eapb = cb_eapb;
    }

    public static Vector<GenEapb> getV_eapb() {
        return v_eapb;
    }

    public static void setV_eapb(Vector<GenEapb> v_eapb) {
        LoadChoiceBoxGeneral.v_eapb = v_eapb;
    }

    public static ChoiceBox getCb_gentiposusuarios() {
        return cb_gentiposusuarios;
    }

    public static void setCb_gentiposusuarios(ChoiceBox cb_gentiposusuarios) {
        LoadChoiceBoxGeneral.cb_gentiposusuarios = cb_gentiposusuarios;
    }

    

    public static ChoiceBox getCb_especialidades() {
        return cb_especialidades;
    }

    public static void setCb_especialidades(ChoiceBox cb_especialidades) {
        LoadChoiceBoxGeneral.cb_especialidades = cb_especialidades;
    }

   

    public static ChoiceBox getCb_unidadesmedida() {
        return cb_unidadesmedida;
    }

    public static void setCb_unidadesmedida(ChoiceBox cb_unidadesmedida) {
        LoadChoiceBoxGeneral.cb_unidadesmedida = cb_unidadesmedida;
    }

    public static void Load_CargosEntidad() {
        if (FXNomina.li_CargosEntidads.size() > 0) {
            cb_cargosentidad.getItems().clear();
            v_cargosentidad.clear();
            for (CargosEntidad ce : FXNomina.li_CargosEntidads) {
                v_cargosentidad.add(ce);
                cb_cargosentidad.getItems().add("Cargo: " + ce.getNombre() + "Nivel: " + ce.nivelcargo() + " Sueldo: " + ce.getSueldo().toString());
            }
        }

    }

   
public static void Load_TiposHorasExtra()
{
    cb_factorhorasextras.getItems().clear();
    v_factorhorasextras.clear();
    for(FactorHorasExtras fh: FXNomina.li_facFactorHorasExtras)
    {
       v_factorhorasextras.add(fh.getFactor_dominicalyfestivo());
       cb_factorhorasextras.getItems().add("Dominical y Festivo");
       v_factorhorasextras.add(fh.getFactor_dominicalyfestivonocturno());
       cb_factorhorasextras.getItems().add("Dominical y Festivo Nocturno");
       v_factorhorasextras.add(fh.getFactor_extradiurnofestivo());
       cb_factorhorasextras.getItems().add("Extra Diurno Festivo");
       v_factorhorasextras.add(fh.getFactor_extradiurnoordinario());
       cb_factorhorasextras.getItems().add("Extra Diurno Ordinario ");
       v_factorhorasextras.add(fh.getFactor_extranocturnofestivo());
       cb_factorhorasextras.getItems().add("Extra Nocturno Festivo");
       v_factorhorasextras.add(fh.getFactor_extranocturnoordinario());
       cb_factorhorasextras.getItems().add("Extra Nocturno Ordinario");
       v_factorhorasextras.add(fh.getFactor_recargonocturno());
       cb_factorhorasextras.getItems().add("Recargo Nocturno");
    }
}
}
