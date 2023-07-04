/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.general;

import java.math.BigDecimal;
import java.util.Vector;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import modelo.AgnConsultorios;
import modelo.AgnEspecialidades;
import modelo.AgnMedicos;
import modelo.CargosEntidad;
import modelo.Empleados;
import modelo.FactorHorasExtras;
import modelo.GenConvenios;
import modelo.GenDepartamentos;
import modelo.GenEapb;
import modelo.GenEstadosCiviles;
import modelo.GenEtnias;
import modelo.GenMunicipios;
import modelo.GenNivelesEducativos;
import modelo.GenProfesiones;
import modelo.GenSexo;
import modelo.GenTiposDocumentos;
import modelo.GenTiposUsuarios;
import modelo.GenZonaResidencia;
import modelo.MedUnidadDosis;
import modelo.ModeloTiposDocumentosContables;
import modelo.Sucursales;
import modelo.TiposDocumentosContables;
import modelo.UnidadesMedida;
import sihic.SihicApp;
import sihic.util.AutoCompleteComboBoxListener;

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
    private static Vector<GenZonaResidencia> v_gen_zona_residencia = new Vector<GenZonaResidencia>();
    private static ChoiceBox gen_profesiones_id;
    private static Vector<Long> v_gen_profesiones = new Vector<Long>();
    private static ChoiceBox gen_etnias_id;
    private static Vector<Long> v_gen_etnias = new Vector<Long>();
    private static ChoiceBox cb_agnmedicos;
    private static Vector<AgnMedicos> v_agnmedicos = new Vector<AgnMedicos>();
    private static ChoiceBox cb_agnespecialidades;
    private static Vector<AgnEspecialidades> v_agnespecialidades = new Vector<AgnEspecialidades>();
    private static ChoiceBox cb_agnconsultorios;
    private static Vector<AgnConsultorios> v_agnconsultorios = new Vector<AgnConsultorios>();
    private static ChoiceBox cb_eps;
    private static Vector<GenEapb> v_eps = new Vector<GenEapb>();
    private static ChoiceBox cb_conveniosp;
    private static Vector<GenConvenios> v_conveniosp = new Vector<GenConvenios>();
    private static ComboBox cb_eapb;
    private static Vector<GenEapb> v_eapb = new Vector<GenEapb>();
    private static ChoiceBox cb_gentiposusuarios;
    private static Vector<GenTiposUsuarios> v_gentiposusuarios = new Vector<GenTiposUsuarios>();
    private static ChoiceBox cb_especialidades;
    private static Vector<AgnEspecialidades> v_especialidades = new Vector<AgnEspecialidades>();
    private static ChoiceBox cb_unidadesmedida = new ChoiceBox();
    public static Vector<MedUnidadDosis> v_medunidadmedida = new Vector<MedUnidadDosis>();
    public static ChoiceBox cb_cargosentidad = new ChoiceBox();
    public static Vector<CargosEntidad> v_cargosentidad = new Vector<CargosEntidad>();
    public static ChoiceBox cb_empleados = new ChoiceBox();
    public static Vector<Empleados> v_empleados = new Vector<Empleados>();
    public static ChoiceBox cb_tiposdocumentoscontables = new ChoiceBox();
    public static Vector<TiposDocumentosContables> v_tiposdocumentoscontables = new Vector<TiposDocumentosContables>();
    public static ChoiceBox cb_ModelosTiposAsientosContable = new ChoiceBox();
    public static Vector<ModeloTiposDocumentosContables> v_ModelosTiposAsientosContables = new Vector<ModeloTiposDocumentosContables>();
    public static ChoiceBox cb_centrocostos = new ChoiceBox();
    public static ChoiceBox cb_conseuctivosnofactutasporsucursal = new ChoiceBox();
    public static ChoiceBox cb_factorhorasextras= new ChoiceBox();
    public static Vector<BigDecimal> v_factorhorasextras=new Vector<>();
     public static ChoiceBox cb_sucursales = new ChoiceBox();
    public  static Vector<Sucursales> v_sucursales = new Vector<Sucursales>();
   
    public LoadChoiceBoxGeneral() {
        gen_departamentos_id = new ChoiceBox<>();

    }
    // A change listener to track the change in selected item

    public void Load_Agnmedicos() {
        cb_agnmedicos = null;
        cb_agnmedicos = new ChoiceBox();
        cb_agnmedicos.setMaxHeight(10);
        cb_agnmedicos.getItems().clear();
        //   m_CBCategoria.getStyleClass().add("choice-box");
        int i = 0;
        if (SihicApp.getL_agnmedicos().size() > 0) {
            v_agnmedicos.clear();
            for (AgnMedicos m : SihicApp.l_medicos) {
                v_agnmedicos.add(i, m);
                cb_agnmedicos.getItems().add(i, m.getGenPersonas().getPrimerNombre() + " " + m.getGenPersonas().getPrimerApellido());
            }
        }
    }

    public void Load_Agnespecialidades() {
        cb_agnespecialidades = null;
        cb_agnespecialidades = new ChoiceBox();
        cb_agnespecialidades.setMaxHeight(10);
        cb_agnespecialidades.getItems().clear();
        //   m_CBCategoria.getStyleClass().add("choice-box");
        int i = 0;
        if (SihicApp.getL_especialidades().size() > 0) {
            v_agnespecialidades.clear();
            for (AgnEspecialidades e : SihicApp.getL_especialidades()) {
                v_agnespecialidades.add(i, e);
                cb_agnespecialidades.getItems().add(i, e.getNombre());

            }
        }
    }

    public void Load_Eps() {
        cb_eps = null;
        cb_eps = new ChoiceBox();

        cb_eps.getItems().clear();
        //   m_CBCategoria.getStyleClass().add("choice-box");
        int i = 0;
        if (SihicApp.l_eapb.size() > 0) {
            v_eps.clear();
            for (GenEapb e : SihicApp.l_eapb) {
                v_eps.add(i, e);
                cb_eps.getItems().add(i, e.getNombre());

            }
        }
    }

    public void Load_Agnconsultorios() {
        cb_agnconsultorios = null;
        cb_agnconsultorios = new ChoiceBox();
        cb_agnconsultorios.setMaxHeight(10);
        cb_agnconsultorios.getItems().clear();
        //   m_CBCategoria.getStyleClass().add("choice-box");
        int i = 0;
        if (SihicApp.getL_especialidades().size() > 0) {
            v_agnconsultorios.clear();
            for (AgnConsultorios c : SihicApp.getL_consultorios()) {
                v_agnconsultorios.add(i, c);
                cb_agnconsultorios.getItems().add(i, c.getNumero() + " " + c.getDescripcion());
            }
        }
    }

    public void Load_GenTiposDOcumentos() {
      
        gen_tipos_documentos_id.setMaxHeight(10);
        gen_tipos_documentos_id.getItems().clear();
        //   m_CBCategoria.getStyleClass().add("choice-box");
        int i = 0;
        if (SihicApp.getL_gentiposdocumentos().size() > 0) {
            v_gen_tipos_documentos.clear();
            for (GenTiposDocumentos d : SihicApp.getL_gentiposdocumentos()) {
                v_gen_tipos_documentos.add(d);
                gen_tipos_documentos_id.getItems().add(d.getNombre());

            }
        }
    }

    public void Load_GenNivelesEducativos() {
        gen_niveles_educativos_id = null;
        gen_niveles_educativos_id = new ChoiceBox();
        gen_niveles_educativos_id.setMaxHeight(10);
        int i = 0;
        if (SihicApp.getL_genniveleseducativos().size() > 0) {
            v_gen_niveleseducativos.clear();
            for (GenNivelesEducativos d : SihicApp.getL_genniveleseducativos()) {
                v_gen_niveleseducativos.add(i, d.getId());
                gen_niveles_educativos_id.getItems().add(i, d.getDescripcion());

            }
        }
    }

    public void Load_GenSexo() {
        gen_sexo_id = null;
        gen_sexo_id = new ChoiceBox();
        gen_sexo_id.setMaxHeight(10);
        int i = 0;
        if (SihicApp.getL_gensexo().size() > 0) {
            v_gen_sexo.clear();
            for (GenSexo d : SihicApp.getL_gensexo()) {
                v_gen_sexo.add(i, d.getId());
                gen_sexo_id.getItems().add(i, d.getDescripcion());

            }
        }
    }

    public void Load_GenEstadosCiviles() {
        gen_estados_civiles_id = null;
        gen_estados_civiles_id = new ChoiceBox();
        gen_estados_civiles_id.setMaxHeight(10);
        int i = 0;
        if (SihicApp.getL_genestadosciviles().size() > 0) {
            v_gen_estados_civiles.clear();
            for (GenEstadosCiviles d : SihicApp.getL_genestadosciviles()) {
                v_gen_estados_civiles.add(i, d.getId());
                gen_estados_civiles_id.getItems().add(i, d.getNombre());

            }
        }
    }

    public void Load_GenEtnias() {
        gen_etnias_id = null;
        gen_etnias_id = new ChoiceBox();
        gen_etnias_id.setMaxHeight(10);
        int i = 0;
        if (SihicApp.getL_genetnias().size() > 0) {
            v_gen_etnias.clear();
            for (GenEtnias d : SihicApp.getL_genetnias()) {
                v_gen_etnias.add(i, d.getId());
                gen_etnias_id.getItems().add(i, d.getNombre());

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

            if (SihicApp.getL_genmunicipios().size() > 0) {
                v_gen_municipios.clear();
                for (GenMunicipios d : SihicApp.getL_genmunicipios()) {
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
        if (SihicApp.getL_gendepartamentos().size() > 0) {
            v_gen_departamentos.clear();
            for (GenDepartamentos d : SihicApp.getL_gendepartamentos()) {
                v_gen_departamentos.add(i, d.getId());
                gen_departamentos_id.getItems().add(i, d.getNombre());

            }
        }
    }

    public void Load_GenZonaResidencia() {
        gen_zona_residencia_id = null;
        gen_zona_residencia_id = new ChoiceBox();
        gen_zona_residencia_id.setMaxHeight(10);
        int i = 0;
        if (SihicApp.getL_genzonaresidencia().size() > 0) {
            v_gen_zona_residencia.clear();
            for (GenZonaResidencia d : SihicApp.getL_genzonaresidencia()) {
                v_gen_zona_residencia.add(d);
                gen_zona_residencia_id.getItems().add(d.getDescripcion());

            }
        }
    }

    public void Load_GenProfesiones() {
        gen_profesiones_id = null;
        gen_profesiones_id = new ChoiceBox();
        gen_profesiones_id.setMaxHeight(10);
        int i = 0;
        if (SihicApp.getL_genprofesiones().size() > 0) {
            v_gen_profesiones.clear();
            for (GenProfesiones d : SihicApp.getL_genprofesiones()) {
                v_gen_profesiones.add(i, d.getId());
                gen_profesiones_id.getItems().add(i, d.getDescripcion());

            }
        }
    }

    public void Load_ConveniosP() {
        cb_conveniosp = null;
        cb_conveniosp = new ChoiceBox();
        cb_conveniosp.getItems().clear();
        int i = 0;
        if (SihicApp.l_conveniosp.size() > 0) {
            v_conveniosp.clear();

            for (GenConvenios e : SihicApp.l_conveniosp) {
                if (i == 0) {
                    v_conveniosp.add(i, null);
                    cb_conveniosp.getItems().add(i, "Ninguno");
                } else {
                    v_conveniosp.add(i, e);
                    cb_conveniosp.getItems().add(i, e.getDescripcion());
                }
                i++;

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
        if (SihicApp.l_eapb.size() > 0) {
            v_eapb.clear();
            for (GenEapb e : SihicApp.l_eapb) {
                v_eapb.add(i, e);
                cb_eapb.getItems().add(i, e.getNombre());

            }
            cb_eapb.getItems().add(0, "Ninguno");
        }

        new AutoCompleteComboBoxListener<>(cb_eapb);
    }

    public void Load_GenTiposUsuarios() {
        cb_gentiposusuarios = null;
        cb_gentiposusuarios = new ChoiceBox();

        cb_gentiposusuarios.getItems().clear();
        //   m_CBCategoria.getStyleClass().add("choice-box");
        int i = 0;
        if (SihicApp.l_tiposusuarios.size() > 0) {
            v_eps.clear();
            for (GenTiposUsuarios e : SihicApp.l_tiposusuarios) {
                v_gentiposusuarios.add(i, e);
                cb_gentiposusuarios.getItems().add(i, e.getNombre());

            }
        }
    }

    public void Load_Especialidades() {
        cb_especialidades = null;
        cb_especialidades = new ChoiceBox();

        cb_especialidades.getItems().clear();
        //   m_CBCategoria.getStyleClass().add("choice-box");
        int i = 0;
        if (SihicApp.l_especialidades.size() > 0) {
            v_especialidades.clear();
            for (AgnEspecialidades e : SihicApp.l_especialidades) {
                v_especialidades.add(i, e);
                cb_especialidades.getItems().add(i, e.getNombre());

            }
        }
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

    public static Vector<GenZonaResidencia> getV_gen_zona_residencia() {
        return v_gen_zona_residencia;
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

    public static Vector<AgnMedicos> getV_agnmedicos() {
        return v_agnmedicos;
    }

    public static void setV_agnmedicos(Vector<AgnMedicos> v_agnmedicos) {
        LoadChoiceBoxGeneral.v_agnmedicos = v_agnmedicos;
    }

    public static ChoiceBox getCb_agnespecialidades() {
        return cb_agnespecialidades;
    }

    public static void setCb_agnespecialidades(ChoiceBox cb_agnespecialidades) {
        LoadChoiceBoxGeneral.cb_agnespecialidades = cb_agnespecialidades;
    }

    public static Vector<AgnEspecialidades> getV_agnespecialidades() {
        return v_agnespecialidades;
    }

    public static void setV_agnespecialidades(Vector<AgnEspecialidades> v_agnespecialidades) {
        LoadChoiceBoxGeneral.v_agnespecialidades = v_agnespecialidades;
    }

    public static ChoiceBox getCb_agnconsultorios() {
        return cb_agnconsultorios;
    }

    public static void setCb_agnconsultorios(ChoiceBox cb_agnconsultorios) {
        LoadChoiceBoxGeneral.cb_agnconsultorios = cb_agnconsultorios;
    }

    public static Vector<AgnConsultorios> getV_agnconsultorios() {
        return v_agnconsultorios;
    }

    public static void setV_agnconsultorios(Vector<AgnConsultorios> v_agnconsultorios) {
        LoadChoiceBoxGeneral.v_agnconsultorios = v_agnconsultorios;
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

    public static Vector<GenConvenios> getV_conveniosp() {
        return v_conveniosp;
    }

    public static void setV_conveniosp(Vector<GenConvenios> v_conveniosp) {
        LoadChoiceBoxGeneral.v_conveniosp = v_conveniosp;
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

    public static Vector<GenTiposUsuarios> getV_gentiposusuarios() {
        return v_gentiposusuarios;
    }

    public static void setV_gentiposusuarios(Vector<GenTiposUsuarios> v_gentiposusuarios) {
        LoadChoiceBoxGeneral.v_gentiposusuarios = v_gentiposusuarios;
    }

    public static ChoiceBox getCb_especialidades() {
        return cb_especialidades;
    }

    public static void setCb_especialidades(ChoiceBox cb_especialidades) {
        LoadChoiceBoxGeneral.cb_especialidades = cb_especialidades;
    }

    public static Vector<AgnEspecialidades> getV_especialidades() {
        return v_especialidades;
    }

    public static void setV_especialidades(Vector<AgnEspecialidades> v_especialidades) {
        LoadChoiceBoxGeneral.v_especialidades = v_especialidades;
    }

    public void Load_GenUnidadesMedida() {
        if (SihicApp.li_unidadmedida.size() > 0) {
            cb_unidadesmedida.getItems().clear();
            v_medunidadmedida.clear();
            for (MedUnidadDosis um : SihicApp.li_unidadmedida) {
                v_medunidadmedida.add(um);
                cb_unidadesmedida.getItems().add(um.getDescripcion());
            }
        }

    }

    public static ChoiceBox getCb_unidadesmedida() {
        return cb_unidadesmedida;
    }

    public static void setCb_unidadesmedida(ChoiceBox cb_unidadesmedida) {
        LoadChoiceBoxGeneral.cb_unidadesmedida = cb_unidadesmedida;
    }

    public static Vector<MedUnidadDosis> getV_medunidadmedida() {
        return v_medunidadmedida;
    }

    public static void setV_medunidadmedida(Vector<MedUnidadDosis> v_medunidadmedida) {
        LoadChoiceBoxGeneral.v_medunidadmedida = v_medunidadmedida;
    }

    public static void Load_CargosEntidad() {
        if (SihicApp.li_CargosEntidads.size() > 0) {
            cb_cargosentidad.getItems().clear();
            v_cargosentidad.clear();
            for (CargosEntidad ce : SihicApp.li_CargosEntidads) {
                v_cargosentidad.add(ce);
                cb_cargosentidad.getItems().add("Cargo: " + ce.getNombre() + "Nivel: " + ce.nivelcargo() + " Sueldo: " + ce.getSueldo().toString());
            }
        }

    }

    public static void Load_ConsecutivosNofacturasPorSucursal() {
        cb_conseuctivosnofactutasporsucursal.getItems().clear();
        cb_conseuctivosnofactutasporsucursal.getItems().add(0, SihicApp.consecutivosFacturasPorSucursal.getPrefijomultiusuario());
        cb_conseuctivosnofactutasporsucursal.getItems().add(1, SihicApp.consecutivosFacturasPorSucursal.getPrefijoindivudual());
        cb_conseuctivosnofactutasporsucursal.getItems().add(2, SihicApp.consecutivosFacturasPorSucursal.getPrefijosoat());
        cb_conseuctivosnofactutasporsucursal.getItems().add(3, SihicApp.consecutivosFacturasPorSucursal.getPrefijoparticular());
        cb_conseuctivosnofactutasporsucursal.getItems().add(4, SihicApp.consecutivosFacturasPorSucursal.getPrefijoindivudualsjg());
 
    }
    public static void Load_prefijoscontador() {
        cb_conseuctivosnofactutasporsucursal.getItems().clear();
        cb_conseuctivosnofactutasporsucursal.getItems().add(0, "A");
        cb_conseuctivosnofactutasporsucursal.getItems().add(1, "AA");
        cb_conseuctivosnofactutasporsucursal.getItems().add(2, "AG");
        cb_conseuctivosnofactutasporsucursal.getItems().add(3, "AV");
        cb_conseuctivosnofactutasporsucursal.getItems().add(4, "P");
        cb_conseuctivosnofactutasporsucursal.getItems().add(5, "PA");
        cb_conseuctivosnofactutasporsucursal.getItems().add(6, "PG");
        cb_conseuctivosnofactutasporsucursal.getItems().add(7, "PV");
        cb_conseuctivosnofactutasporsucursal.getItems().add(8, "T");
        cb_conseuctivosnofactutasporsucursal.getItems().add(9, "TA");
        cb_conseuctivosnofactutasporsucursal.getItems().add(10,"TG");
        cb_conseuctivosnofactutasporsucursal.getItems().add(11,"TV");
        cb_conseuctivosnofactutasporsucursal.getItems().add(12, "E");
        cb_conseuctivosnofactutasporsucursal.getItems().add(13, "EA");
        cb_conseuctivosnofactutasporsucursal.getItems().add(14, "EG");
        cb_conseuctivosnofactutasporsucursal.getItems().add(15, "EV");
     }

    public static void Load_TiposDocumentosContables() {
        cb_centrocostos.getItems().add(jenum.EnumCentroCostos.COMPRAS0.ordinal(), "COMPRAS");
        cb_centrocostos.getItems().add(jenum.EnumCentroCostos.PRODUCCION1.ordinal(), "PRODUCCION");
        cb_centrocostos.getItems().add(jenum.EnumCentroCostos.VENTAS2.ordinal(), "VENTAS");
        cb_centrocostos.getItems().add(jenum.EnumCentroCostos.ADMINISTRACION3.ordinal(), "ADMINISTRACION");
        cb_centrocostos.getSelectionModel().select(0);
        if (SihicApp.li_TiposDocumentosContableses.size() > 0) {
            cb_tiposdocumentoscontables.getItems().clear();
            v_tiposdocumentoscontables.clear();
            for (TiposDocumentosContables tdt : SihicApp.li_TiposDocumentosContableses) {
                v_tiposdocumentoscontables.add(tdt);
                cb_tiposdocumentoscontables.getItems().add(tdt.getCodigo() + " - " + tdt.getNombre());
            }
        }
         if (SihicApp.li_Sucursales.size() > 0) {
            cb_sucursales.getItems().clear();
            v_sucursales.clear();
            for (Sucursales tdt : SihicApp.li_Sucursales) {
                v_sucursales.add(tdt);
                cb_sucursales.getItems().add(tdt.getCodigo() + " - " + tdt.getNombre());
            }
        }

    }
public static void Load_TiposHorasExtra()
{
    cb_factorhorasextras.getItems().clear();
    v_factorhorasextras.clear();
    for(FactorHorasExtras fh: SihicApp.li_facFactorHorasExtras)
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
