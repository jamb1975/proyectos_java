/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author adminlinux
 */
@Entity
@Table(name="HclCie10")
public class HclCie10 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "ID", table = "HclCie10", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_HCL_CIE10_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_HCL_CIE10_ID", sequenceName = "SEQ_HCL_CIE10_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @Column(length = 20)
    private String codigo;  
    @Column(length = 500)  
    private String descripcion;
    @Column(length = 1) 
    private Long masculino;
    @Column(length = 1) 
    private Long femenino;
    private Long gen_cie10_edades_id;
    @Column(name = "USUARIO_MODIFICADOR",length = 50)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;
   @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;
     @Column(name = "USUARIO_CREADOR",length = 50)
    @Basic
    private String usuarioCreador;
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getMasculino() {
        return masculino;
    }

    public void setMasculino(Long masculino) {
        this.masculino = masculino;
    }

    public Long getFemenino() {
        return femenino;
    }

    public void setFemenino(Long femenino) {
        this.femenino = femenino;
    }

    public Long getGen_cie10_edades_id() {
        return gen_cie10_edades_id;
    }

    public void setGen_cie10_edades_id(Long gen_cie10_edades_id) {
        this.gen_cie10_edades_id = gen_cie10_edades_id;
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


   
    
    
}
