package modelo;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "GEN_TIPOS_AFILIACION")

public class GenTiposAfiliacion implements Serializable {

    @Column(name = "DESCRIPCION", table = "GEN_TIPOS_AFILIACION", unique = false, updatable = true, insertable = true, nullable = true, length = 200, scale = 0, precision = 0)
    @Basic
    private String descripcion;

    @Column(name = "CODIGO", table = "GEN_TIPOS_AFILIACION", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String codigo;

    @Column(name = "USUARIO_MODIFICADOR", table = "GEN_TIPOS_AFILIACION", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION", table = "GEN_TIPOS_AFILIACION", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;

    @Column(name = "FECHA_CREACION", table = "GEN_TIPOS_AFILIACION", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;

    @Column(name = "ID", table = "GEN_TIPOS_AFILIACION", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 0)
    @Id
    @GeneratedValue(generator = "SEQ_GEN_TIPOS_AFILIACION", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_GEN_TIPOS_AFILIACION", sequenceName = "SEQ_GEN_TIPOS_AFILIACION", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "USUARIO_CREADOR", table = "GEN_TIPOS_AFILIACION", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioCreador;

    public GenTiposAfiliacion() {

    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuarioCreador() {
        return this.usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
}
