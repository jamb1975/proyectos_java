package modelo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "GEN_PACIENTES")

public class GenPacientes implements Serializable {

    @Column(name = "ESTADO", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private BigInteger estado;

    @Column(name = "USUARIO_MODIFICADOR", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;

    @Column(name = "ANEXO", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String anexo;

    @Column(name = "FECHA_AFILIACION", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaAfiliacion;

    @Column(name = "USUARIO_CREADOR", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioCreador;

    @Column(name = "DOCUMENTO_AFILIADO_DEL_GRUPO", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String documentoAfiliadoDelGrupo;

    @Column(name = "NOVEDADES", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String novedades;

    @Column(name = "GRUPO_SANGUINEO", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 2, scale = 0, precision = 0)
    @Basic
    private String grupoSanguineo;

    @Column(name = "DOCUMENTO_ANTERIOR", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String documentoAnterior;

    @Column(name = "FECHA_CREACION", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;

    @Column(name = "ID", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_GEN_PACIENTES", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_GEN_PACIENTES", sequenceName = "SEQ_GEN_PACIENTES", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "COMENTARIOS", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String comentarios;

    @Column(name = "FACTOR_RH", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true, length = 1, scale = 0, precision = 0)
    @Basic
    private String factorRh;

    //@Column(name = "GEN_TIPOS_USUARIOS_ID", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenTiposUsuarios.class,fetch = FetchType.LAZY)
    @JoinColumn(name="GEN_TIPOS_USUARIOS_ID")
    private GenTiposUsuarios genTiposUsuarios;
    
    @ManyToOne(optional = true, targetEntity = GenNivelesUsuarios.class,fetch = FetchType.LAZY)
    @JoinColumn(name="GENNIVELESUSUARIOS_ID")
    private GenNivelesUsuarios genNivelesUsuarios;
   // @Column(name = "GEN_TIPOS_AFILIACION_ID", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenTiposAfiliacion.class,fetch = FetchType.LAZY)
      @JoinColumn(name="GEN_TIPOS_AFILIACION_ID")
    private GenTiposAfiliacion genTiposAfiliacion;
    
   // @Column(name = "GEN_EAPB_ID", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenEapb.class,fetch = FetchType.LAZY)
     @JoinColumn(name="GEN_EAPB_ID")
    private GenEapb genEapb;
    @ManyToOne(optional = true, targetEntity = GenEapb.class,fetch = FetchType.LAZY)
     @JoinColumn(name="ASEGURADORA_ID")
    private GenEapb aseguradora;
   // @Column(name = "GEN_PERSONAS_ID", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenPersonas.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="GEN_PERSONAS_ID")
    private GenPersonas genPersonas;
    
    //@Column(name = "GEN_UNIDADES_ORGANIZACION_ID", table = "GEN_PACIENTES", unique = false, updatable = true, insertable = true, nullable = true)
     @ManyToOne(optional = true, targetEntity = GenUnidadesOrganizacion.class,fetch = FetchType.LAZY)
     @JoinColumn(name="GEN_UNIDADES_ORGANIZACION_ID")
     private GenUnidadesOrganizacion genUnidadesOrganizacion;
     @ManyToOne(optional = true, targetEntity = GenEapb.class,fetch = FetchType.LAZY)
     @JoinColumn(name="GENCONVENIOSEAPB_ID")
     private GenConvenios genConveniosEapb;
     
     @ManyToOne(optional = true, targetEntity = GenConvenios.class,fetch = FetchType.LAZY)
     @JoinColumn(name="GENCONVENIOS_ID")
     private GenConvenios genConvenios;
     @Transient
     private BigDecimal valorcuotamoderadora=BigDecimal.ZERO;
     public GenPacientes() {

    }

    public BigInteger getEstado() {
        return this.estado;
    }

    public void setEstado(BigInteger estado) {
        this.estado = estado;
    }

    public String getUsuarioModificador() {
        return this.usuarioModificador;
    }

    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }

    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getAnexo() {
        return this.anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public Date getFechaAfiliacion() {
        return this.fechaAfiliacion;
    }

    public void setFechaAfiliacion(Date fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public String getUsuarioCreador() {
        return this.usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getDocumentoAfiliadoDelGrupo() {
        return this.documentoAfiliadoDelGrupo;
    }

    public void setDocumentoAfiliadoDelGrupo(String documentoAfiliadoDelGrupo) {
        this.documentoAfiliadoDelGrupo = documentoAfiliadoDelGrupo;
    }

    public String getNovedades() {
        return this.novedades;
    }

    public void setNovedades(String novedades) {
        this.novedades = novedades;
    }

    public String getGrupoSanguineo() {
        return this.grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getDocumentoAnterior() {
        return this.documentoAnterior;
    }

    public void setDocumentoAnterior(String documentoAnterior) {
        this.documentoAnterior = documentoAnterior;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenTiposUsuarios getGenTiposUsuarios() {
        return genTiposUsuarios;
    }

    public void setGenTiposUsuarios(GenTiposUsuarios genTiposUsuarios) {
        this.genTiposUsuarios = genTiposUsuarios;
    }

    public GenTiposAfiliacion getGenTiposAfiliacion() {
        return genTiposAfiliacion;
    }

    public void setGenTiposAfiliacion(GenTiposAfiliacion genTiposAfiliacion) {
        this.genTiposAfiliacion = genTiposAfiliacion;
    }

    public GenEapb getGenEapb() {
        return genEapb;
    }

    public void setGenEapb(GenEapb genEapb) {
        this.genEapb = genEapb;
    }

    public GenPersonas getGenPersonas() {
        return genPersonas;
    }

    public void setGenPersonas(GenPersonas genPersonas) {
        this.genPersonas = genPersonas;
    }

    

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getFactorRh() {
        return this.factorRh;
    }

    public void setFactorRh(String factorRh) {
        this.factorRh = factorRh;
    }

    public GenUnidadesOrganizacion getGenUnidadesOrganizacion() {
        return genUnidadesOrganizacion;
    }

    public void setGenUnidadesOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion) {
        this.genUnidadesOrganizacion = genUnidadesOrganizacion;
    }

    public GenNivelesUsuarios getGenNivelesUsuarios() {
        return genNivelesUsuarios;
    }

    public void setGenNivelesUsuarios(GenNivelesUsuarios genNivelesUsuarios) {
        this.genNivelesUsuarios = genNivelesUsuarios;
    }

   

    public GenConvenios getGenConveniosEapb() {
        return genConveniosEapb;
    }

    public void setGenConveniosEapb(GenConvenios genConveniosEapb) {
        this.genConveniosEapb = genConveniosEapb;
    }

    public GenConvenios getGenConvenios() {
        return genConvenios;
    }

    public void setGenConvenios(GenConvenios genConvenios) {
        this.genConvenios = genConvenios;
    }

    public BigDecimal getValorcuotamoderadora() {
        for(GenCuotasModeradorasEapb cm: genEapb.getLi_genCuotasModeradorasEapbs())
        {
            if(cm.getGenNivelesUsuarios().equals(genNivelesUsuarios))
            {
                valorcuotamoderadora=cm.getValor();
                break;
            }
        }
        return valorcuotamoderadora;
    }

    public void setValorcuotamoderadora(BigDecimal valorcuotamoderadora) {
        this.valorcuotamoderadora = valorcuotamoderadora;
    }

    public GenEapb getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(GenEapb aseguradora) {
        this.aseguradora = aseguradora;
    }
    
    
}
