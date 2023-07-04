package modelo;


import java.io.Serializable;
import java.math.BigDecimal;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adminlinux
 */

@Entity
@Table(name = "GEN_PROFESIONES")
public class GenProfesiones implements Serializable {
    @Column(name = "CODIGO", table = "GEN_PROFESIONES", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String codigo;

    @Column(name = "DESCRIPCION", table = "GEN_PROFESIONES", unique = false, updatable = true, insertable = true, nullable = true, length = 500, scale = 0, precision = 0)
    @Basic
    private String descripcion;

    @Column(name = "USUARIO_MODIFICADOR", table = "GEN_PROFESIONES", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION", table = "GEN_PROFESIONES", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;

    @Column(name = "FECHA_CREACION", table = "GEN_PROFESIONES", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;

    @Column(name = "ID", table = "GEN_PROFESIONES", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 0)
    @Id
    @GeneratedValue(generator = "SEQ_GEN_PROFESIONES", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_GEN_PROFESIONES", sequenceName = "SEQ_GEN_PROFESIONES", allocationSize = 1, initialValue = 1)
    private Long id;

    
    @Column(name = "USUARIO_CREADOR", table = "GEN_PROFESIONES", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioCreador;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuarioModificador() {
        return usuarioModificador;
    }

    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    

    public String getUsuarioCreador() {
        return usuarioCreador;
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

   
    
}
