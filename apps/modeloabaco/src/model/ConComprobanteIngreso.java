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
import javax.persistence.OneToOne;
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
@Table(name = "ConComprobanteIngreso")
public class ConComprobanteIngreso implements Serializable {
    private static final long serialVersionUID = 1L;
    
   
    @Column(name = "ID", table = "ConComprobanteIngreso", unique = true, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_COMPROBANTEINGRESO_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_COMPROBANTEINGRESO_ID", sequenceName = "SEQ_COMPROBANTEINGRESO_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @OneToOne
    @JoinColumn(name="FACTURAITEM_ID",unique = true)
    private FacturaItem facturaItem;
    
    @ManyToOne
    @JoinColumn(name="CONSECUTIVOCOMPROBANTEINGRESO_ID")
    private ConsecutivoComprobanteIngreso consecutivoComprobanteIngreso;
    @ManyToOne(fetch = FetchType.EAGER,optional = true, targetEntity = Factura.class)
    @JoinColumn(name="FACTURA_ID")
    private Factura factura;
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
    private String nocheque;
    private String notargeta;
    private  String banco;
    private int tipotargeta;
    private BigDecimal valor=BigDecimal.ZERO;
    private String concepto;
    private String observaciones;
    @Transient
    private String nocomprobantecerosizquierda;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FacturaItem getFacturaItem() {
        return facturaItem;
    }

    public void setFacturaItem(FacturaItem facturaItem) {
        this.facturaItem = facturaItem;
    }

    public ConsecutivoComprobanteIngreso getConsecutivoComprobanteIngreso() {
        return consecutivoComprobanteIngreso;
    }

    public void setConsecutivoComprobanteIngreso(ConsecutivoComprobanteIngreso consecutivoComprobanteIngreso) {
        this.consecutivoComprobanteIngreso = consecutivoComprobanteIngreso;
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

    

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNocheque() {
        return nocheque;
    }

    public void setNocheque(String nocheque) {
        this.nocheque = nocheque;
    }

    public String getNotargeta() {
        return notargeta;
    }

    public void setNotargeta(String notargeta) {
        this.notargeta = notargeta;
    }

    public int getTipotargeta() {
        return tipotargeta;
    }

    public void setTipotargeta(int tipotargeta) {
        this.tipotargeta = tipotargeta;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
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
   for(int i=6;i>id.toString().length();i--)
     {
         cerosizqcomprobante=cerosizqcomprobante+"0";
     }
     nocomprobantecerosizquierda=cerosizqcomprobante+id.toString();
     return nocomprobantecerosizquierda;
    }

   
 public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
 public ConComprobanteContabilidad getConComprobanteContabilidad() {
        return conComprobanteContabilidad;
    }

    public void setConComprobanteContabilidad(ConComprobanteContabilidad conComprobanteContabilidad) {
        this.conComprobanteContabilidad = conComprobanteContabilidad;
    }
}
