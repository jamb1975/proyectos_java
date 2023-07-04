package model;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name = "GEN_MUNICIPIOS")

public class GenMunicipios implements Serializable {

    @Column(name = "CODIGO", table = "GEN_MUNICIPIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String codigo;

    @Column(name = "USUARIO_MODIFICADOR", table = "GEN_MUNICIPIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION", table = "GEN_MUNICIPIOS", unique = false, updatable = true, insertable = true, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;
//@Column(name = "GEN_DEPARTAMENTOS_ID", table = "GEN_MUNICIPIOS", unique = false, updatable = true, insertable = true, nullable = true)
    
    @ManyToOne(optional = true, targetEntity = GenDepartamentos.class)
     @JoinColumn(name="GEN_DEPARTAMENTOS_ID")
    private GenDepartamentos genDepartamentos;

    @Column(name = "FECHA_CREACION", table = "GEN_MUNICIPIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;

    @Column(name = "ID", table = "GEN_MUNICIPIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_GEN_MUNICIPIOS", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_GEN_MUNICIPIOS", sequenceName = "SEQ_GEN_MUNICIPIOS", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "NOMBRE", table = "GEN_MUNICIPIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String nombre;

    @Column(name = "USUARIO_CREADOR", table = "GEN_MUNICIPIOS", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioCreador;
    
    public GenMunicipios() {

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

    public GenDepartamentos getGenDepartamentos() {
        return genDepartamentos;
    }

    public void setGenDepartamentos(GenDepartamentos genDepartamentos) {
        this.genDepartamentos = genDepartamentos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
