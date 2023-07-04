package modelo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "GEN_UNIDADES_ORGANIZACION")

public class GenUnidadesOrganizacion implements Serializable {

    @Column(name = "NIVEL_ATENCION", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 200, scale = 0, precision = 0)
    @Basic
    private String nivelAtencion;

    @Column(name = "ESTADO", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private Short estado;

    @Column(name = "TIPO", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private Short tipo;

    @Column(name = "CODIGO", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String codigo;
    @Column(length = 50)
    private String nit;

    @Column(name = "SIGLA", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String sigla;

    @Column(name = "USUARIO_MODIFICADOR", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;

    @Column(name = "DIRECCION", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 200, scale = 0, precision = 0)
    @Basic
    private String direccion;

    @Column(name = "NOMBRE", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 500, scale = 0, precision = 0)
    @Basic
    private String nombre;

    @Column(name = "USUARIO_CREADOR", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioCreador;

    @Column(name = "APROBADOR_ORDENES", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private BigInteger aprobadorOrdenes;

    @Column(name = "FECHA_CREACION", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;

    @Column(name = "ID", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_GEN_UNIDADES_ORG", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_GEN_UNIDADES_ORG", sequenceName = "SEQ_GEN_UNIDADES_ORG", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "TELEFONO", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String telefono;

    @Column(name = "NIVEL", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private Short nivel;

    private byte[]logo;
    //@Column(name = "GEN_MUNICIPIOS_ID", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true)
   
    @ManyToOne(optional = true, targetEntity = GenMunicipios.class)
     @JoinColumn(name = "GEN_MUNICIPIOS_ID")
    private GenMunicipios genMunicipios;
    
     //@Column(name = "GEN_CENTROS_POBLADOS_ID", table = "GEN_UNIDADES_ORGANIZACION", unique = false, updatable = true, insertable = true, nullable = true)
  
     @ManyToOne(optional = true, targetEntity = GenCentrosPoblados.class)
     @JoinColumn(name = "GEN_CENTROS_POBLADOS_ID")
    private GenCentrosPoblados genCentrosPoblados;
    public GenUnidadesOrganizacion() {

    }

    public String getNivelAtencion() {
        return this.nivelAtencion;
    }

    public void setNivelAtencion(String nivelAtencion) {
        this.nivelAtencion = nivelAtencion;
    }

    public Short getEstado() {
        return this.estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Short getTipo() {
        return this.tipo;
    }

    public void setTipo(Short tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSigla() {
        return this.sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
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

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuarioCreador() {
        return this.usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public BigInteger getAprobadorOrdenes() {
        return this.aprobadorOrdenes;
    }

    public void setAprobadorOrdenes(BigInteger aprobadorOrdenes) {
        this.aprobadorOrdenes = aprobadorOrdenes;
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

   

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Short getNivel() {
        return this.nivel;
    }

    public void setNivel(Short nivel) {
        this.nivel = nivel;
    }

    public GenMunicipios getGenMunicipios() {
        return genMunicipios;
    }

    public void setGenMunicipios(GenMunicipios genMunicipios) {
        this.genMunicipios = genMunicipios;
    }

    public GenCentrosPoblados getGenCentrosPoblados() {
        return genCentrosPoblados;
    }

    public void setGenCentrosPoblados(GenCentrosPoblados genCentrosPoblados) {
        this.genCentrosPoblados = genCentrosPoblados;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
    @Lob
    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
    
    
}
