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
@Table(name = "GenConvenios")
public class GenConvenios implements Serializable,Comparable<GenConvenios> {
  @Column(name = "ID", table = "GenConvenios", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
  @Id
  @GeneratedValue(generator = "SEQ_GENCONVENIOS_ID", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "SEQ_GENCONVENIOS_ID", sequenceName = "SEQ_GENCONVENIOS_ID", allocationSize = 1, initialValue = 1)
  private  Long id;
  @ManyToOne(optional = true, targetEntity = GenEapb.class)
  @JoinColumn(name="GENEAPB_ID")
  private GenEapb genEapb;
  
  @Column(length = 50)
  private String numerocontrato;
  private float porcentajedescuento;
  @Column(length = 1000)
  private String descripcion;
  @Temporal(TemporalType.TIMESTAMP)
  @Basic
  private Date fechacontrato;
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
    
   @Column(length = 1)
  private String tipoconvenio;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNumerocontrato() {
        return numerocontrato;
    }

    public void setNumerocontrato(String numerocontrato) {
        this.numerocontrato = numerocontrato;
    }

    public float getPorcentajedescuento() {
        return porcentajedescuento;
    }

    public void setPorcentajedescuento(float porcentajedescuento) {
        this.porcentajedescuento = porcentajedescuento;
    }

   

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public GenEapb getGenEapb() {
        return genEapb;
    }

    public void setGenEapb(GenEapb genEapb) {
        this.genEapb = genEapb;
    }

    public Date getFechacontrato() {
        return fechacontrato;
    }

    public void setFechacontrato(Date fechacontrato) {
        this.fechacontrato = fechacontrato;
    }

     @Override
     public int compareTo(GenConvenios gc) {
        Long a=this.getId();
        Long b=gc.getId();
        return a.compareTo(b);
    } 


    public String getTipoconvenio() {
        return tipoconvenio;
    }

    public void setTipoconvenio(String tipoconvenio) {
        this.tipoconvenio = tipoconvenio;
    }

   

    
    
    
    
}
