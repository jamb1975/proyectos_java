package modelo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "GEN_TIPOS_USUARIOS")

public class GenTiposUsuarios implements Serializable {

    @Column(name = "ESTADO", table = "GEN_TIPOS_USUARIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private BigInteger estado;

    @Column(name = "USUARIO_MODIFICADOR", table = "GEN_TIPOS_USUARIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION", table = "GEN_TIPOS_USUARIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;

    @Column(name = "FECHA_CREACION", table = "GEN_TIPOS_USUARIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;

    @Column(name = "ID", table = "GEN_TIPOS_USUARIOS", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_GEN_TIPOSUSUARIOS", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_GEN_TIPOSUSUARIOS", sequenceName = "SEQ_GEN_TIPOSUSUARIOS", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "NOMBRE", table = "GEN_TIPOS_USUARIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String nombre;

    @Column(name = "USUARIO_CREADOR", table = "GEN_TIPOS_USUARIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioCreador;
    @Column(length = 10)
    private String codigo;
    @ManyToOne(optional = true, targetEntity = Puc.class)
    @JoinColumn(name="ConPuc_ID")
     private Puc conPuc;
    public GenTiposUsuarios() {

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

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Puc getConPuc() {
        return conPuc;
    }

    public void setConPuc(Puc conPuc) {
        this.conPuc = conPuc;
    }

    

   
    
    
}
