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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author adminlinux
 */
@Entity
@Table(name = "HclDiagnostico")
public class HclDiagnostico implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Column(name = "ID", table = "HclDiagnostico", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_HCL_DIAGNOSTICO_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_HCL_DIAGNOSTICO_ID", sequenceName = "SEQ_HCL_DIAGNOSTICO_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    private Long tipo;
    @ManyToOne(optional = true, targetEntity = HclCie10.class)
    @JoinColumn(name="HCLCIE10_ID")
    private HclCie10 hclcie10_id;
    @ManyToOne(optional = true, targetEntity = HclConsultas.class)
    @JoinColumn(name="HCLCONSULTAS_ID")
    private HclConsultas hclConsultas_id;
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

    public Long getTipo() {
        return tipo;
    }

    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }

   

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public HclCie10 getHclcie10_id() {
        return hclcie10_id;
    }

    public void setHclcie10_id(HclCie10 hclcie10_id) {
        this.hclcie10_id = hclcie10_id;
    }

    public HclConsultas getHclConsultas_id() {
        return hclConsultas_id;
    }

    public void setHclConsultas_id(HclConsultas hclConsultas_id) {
        this.hclConsultas_id = hclConsultas_id;
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
