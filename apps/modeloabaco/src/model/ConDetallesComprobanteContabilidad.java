/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class ConDetallesComprobanteContabilidad implements Serializable,Comparable<ConDetallesComprobanteContabilidad> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "SEQ_CONDETALLESCOMPROBANTECONTABILIDAD_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CONDETALLESCOMPROBANTECONTABILIDAD_ID", sequenceName = "SEQ_CONDETALLESCOMPROBANTECONTABILIDAD_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name="COMPROBANTECONTABILIDAD_ID")
    private ConComprobanteContabilidad conComprobanteContabilidad;
    
    @ManyToOne(optional = true, targetEntity = ConPuc.class)
    @JoinColumn(name="PUC_ID")
    private ConPuc conPuc;
  
    private BigDecimal debe=BigDecimal.ZERO;
    private BigDecimal haber=BigDecimal.ZERO;
    private BigDecimal parcialdebe=BigDecimal.ZERO;
    private BigDecimal parcialhaber=BigDecimal.ZERO;
    private BigDecimal saldo=BigDecimal.ZERO;
    private String descripcion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaelaboracion;
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

   

    public ConPuc getConPuc() {
        return conPuc;
    }

    public void setConPuc(ConPuc conPuc) {
        this.conPuc = conPuc;
    }

    public ConComprobanteContabilidad getConComprobanteContabilidad() {
        return conComprobanteContabilidad;
    }

    public void setConComprobanteContabilidad(ConComprobanteContabilidad conComprobanteContabilidad) {
        this.conComprobanteContabilidad = conComprobanteContabilidad;
    }

   

    public BigDecimal getDebe() {
        return round(debe);
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return round(haber);
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaelaboracion() {
        return fechaelaboracion;
    }

    public void setFechaelaboracion(Date fechaelaboracion) {
        this.fechaelaboracion = fechaelaboracion;
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

    public BigDecimal getParcialdebe() {
        return round(parcialdebe);
    }

    public void setParcialdebe(BigDecimal parcialdebe) {
        this.parcialdebe = parcialdebe;
    }

    public BigDecimal getParcialhaber() {
        return round(parcialhaber);
    }

    public void setParcialhaber(BigDecimal parcialhaber) {
        this.parcialhaber = parcialhaber;
    }

    public BigDecimal getSaldo() {
        return round(saldo);
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

   
    private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }
    
@Override
     public int compareTo(ConDetallesComprobanteContabilidad cc) {
        String a=this.getConPuc().getCodigo();
        String b=cc.getConPuc().getCodigo();
        return a.compareTo(b);
    }
   
    
}
