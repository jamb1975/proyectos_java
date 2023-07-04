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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author adminlinux
 */
@Entity
public class HclAntecedentesGenerales implements Serializable {
    private static final long serialVersionUID = 1L;
  
     @Id
    @GeneratedValue(generator = "SEQ_ANTECEDENTES_GENERALES", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_ANTECEDENTES_GENERALES", sequenceName = "SEQ_ANTECEDENTES_GENERALES", allocationSize = 1, initialValue = 1)
    private Long id;
    
    @ManyToOne(optional = true, targetEntity = HclConsultas.class)
    @JoinColumn(name="HCLCONSULTAS_ID")
    private HclConsultas hclConsultas;
    
     @ManyToOne(optional = true, targetEntity = HclTiposAntecedentesGenerales.class)
    @JoinColumn(name="HCLTIPOSANTECEDENTESGENERALES_ID")
    private HclTiposAntecedentesGenerales hclTiposAntecedentesGenerales;
    
     private  String descripcion;
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

    public HclConsultas getHclConsultas() {
        return hclConsultas;
    }

    public void setHclConsultas(HclConsultas hclConsultas) {
        this.hclConsultas = hclConsultas;
    }

    public HclTiposAntecedentesGenerales getHclTiposAntecedentesGenerales() {
        return hclTiposAntecedentesGenerales;
    }

    public void setHclTiposAntecedentesGenerales(HclTiposAntecedentesGenerales hclTiposAntecedentesGenerales) {
        this.hclTiposAntecedentesGenerales = hclTiposAntecedentesGenerales;
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

    
}
