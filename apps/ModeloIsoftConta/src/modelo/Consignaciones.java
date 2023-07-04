/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Consignaciones")
public class Consignaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    
   
    @Column(name = "ID", table = "Consignaciones", unique = true, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_CONSIGNACIONES_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CONSIGNACIONES_ID", sequenceName = "SEQ_CONSIGNACIONES_ID", allocationSize = 1, initialValue = 1)
    private Long id;
   
      @ManyToOne
    @JoinColumn(name="PROVEEDORES_ID")
    private Terceros proveedores;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaelaboracion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaconsinacion;
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
    private BigDecimal valor=BigDecimal.ZERO;
    private String concepto;
    private String observaciones;
    private String banco;
    private String codbanco;
    private String depositante;
    private String nocuenta;
    private String nombrecuenta;
    private String nocheque;
    private String noreciboconsignacion;
    private String noident;
    private String direccion;
    private String telefono;
    private int tipoconsignacion;
     private Long no_consecutivo;
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
     for(int i=6;i>no_consecutivo.toString().length();i--)
     {
         cerosizqcomprobante=cerosizqcomprobante+"0";
     }
     nocomprobantecerosizquierda=cerosizqcomprobante+no_consecutivo.toString();
     return nocomprobantecerosizquierda;
    }

   
 public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

   
    public Terceros getProveedores() {
        return proveedores;
    }

    public void setProveedores(Terceros proveedores) {
        this.proveedores = proveedores;
    }

   
    public String getNoident() {
        return noident;
    }

    public void setNoident(String noident) {
        this.noident = noident;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaconsinacion() {
        return fechaconsinacion;
    }

    public void setFechaconsinacion(Date fechaconsinacion) {
        this.fechaconsinacion = fechaconsinacion;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCodbanco() {
        return codbanco;
    }

    public void setCodbanco(String codbanco) {
        this.codbanco = codbanco;
    }

    public String getDepositante() {
        return depositante;
    }

    public void setDepositante(String depositante) {
        this.depositante = depositante;
    }

    public String getNocuenta() {
        return nocuenta;
    }

    public void setNocuenta(String nocuenta) {
        this.nocuenta = nocuenta;
    }

    public String getNombrecuenta() {
        return nombrecuenta;
    }

    public void setNombrecuenta(String nombrecuenta) {
        this.nombrecuenta = nombrecuenta;
    }

    public String getNocheque() {
        return nocheque;
    }

    public void setNocheque(String nocheque) {
        this.nocheque = nocheque;
    }

    

    public String getNoreciboconsignacion() {
        return noreciboconsignacion;
    }

    public void setNoreciboconsignacion(String noreciboconsignacion) {
        this.noreciboconsignacion = noreciboconsignacion;
    }

    public int getTipoconsignacion() {
        return tipoconsignacion;
    }

    public void setTipoconsignacion(int tipoconsignacion) {
        this.tipoconsignacion = tipoconsignacion;
    }

    public Long getNo_consecutivo() {
        return no_consecutivo;
    }

    public void setNo_consecutivo(Long no_consecutivo) {
        this.no_consecutivo = no_consecutivo;
    }
    
    
    
}
