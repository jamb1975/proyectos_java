/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karol
 */
@Entity
@XmlRootElement
@Table(name = "ConComprobanteGastos")
public class ConComprobanteGastos implements Serializable {
    private static final long serialVersionUID = 1L;
    
   
 @Column(name = "ID", table = "ConComprobanteGastos", unique = true, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_COMPROBANTEGASTOS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_COMPROBANTEGASTOS_ID", sequenceName = "SEQ_COMPROBANTEGASTOS_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="CONSECUTIVOCOMPROBANTEGASTOS_ID")
    private ConsecutivoComprobanteGastos consecutivoComprobanteGasto;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PERSONAS_ID")
    private Personas genPersonas;
      @ManyToOne(fetch = FetchType.EAGER,optional = true, targetEntity = ConComprobanteContabilidad.class)
    @JoinColumn(name="ConComprobanteContabilidad_ID")
    private ConComprobanteContabilidad conComprobanteContabilidad;
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
    private String nofactura;
    @Column(length = 1)
    private String tipogasto;
    private BigDecimal valor=BigDecimal.ZERO;
    private String concepto;
   @Transient 
   private BigDecimal totalsaldo=BigDecimal.ZERO;
   @Transient 
   private BigDecimal totalegresos=BigDecimal.ZERO;
    @Transient
    private String nocomprobantecerosizquierda;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    

    

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getNocomprobantecerosizquierda() {
     String    cerosizqcomprobante="";
     String noconsecutivo=consecutivoComprobanteGasto.getId().toString();
     for(int i=6;i>noconsecutivo.length();i--)
     {
         cerosizqcomprobante=cerosizqcomprobante+"0";
     }
     nocomprobantecerosizquierda=cerosizqcomprobante+noconsecutivo;
     return nocomprobantecerosizquierda;
    }

  

    public ConsecutivoComprobanteGastos getConsecutivoComprobanteGasto() {
        return consecutivoComprobanteGasto;
    }

    public void setConsecutivoComprobanteGasto(ConsecutivoComprobanteGastos consecutivoComprobanteGasto) {
        this.consecutivoComprobanteGasto = consecutivoComprobanteGasto;
    }

   

    public String getNofactura() {
        return nofactura;
    }

    public void setNofactura(String nofactura) {
        this.nofactura = nofactura;
    }

    public String getTipogasto() {
        return tipogasto;
    }

    public void setTipogasto(String tipogasto) {
        this.tipogasto = tipogasto;
    }

   
   

    
 

   

    public void setTotalegresos(BigDecimal totalegresos) {
        
        this.totalegresos = totalegresos;
    }

    public Personas getGenPersonas() {
        return genPersonas;
    }

    public void setGenPersonas(Personas genPersonas) {
        this.genPersonas = genPersonas;
    }

    public ConComprobanteContabilidad getConComprobanteContabilidad() {
        return conComprobanteContabilidad;
    }

    public void setConComprobanteContabilidad(ConComprobanteContabilidad conComprobanteContabilidad) {
        this.conComprobanteContabilidad = conComprobanteContabilidad;
    }
    
 
}
