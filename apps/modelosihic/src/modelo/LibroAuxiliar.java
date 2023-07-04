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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class LibroAuxiliar implements Serializable,Comparable<LibroAuxiliar> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "SEQ_CONDETALLESCOMPROBANTECONTABILIDAD_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CONDETALLESCOMPROBANTECONTABILIDAD_ID", sequenceName = "SEQ_CONDETALLESCOMPROBANTECONTABILIDAD_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="FACTURA_ID")
    private Factura factura;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="NOTACONTABILIDAD_ID")
    private NotaContabilidad notaContabilidad;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="COMPROBANTECAUSACIONEGRESO_ID")
    private ComprobanteCausacionEgreso comprobanteCausacionEgreso;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="COMPROBANTEEGRESO_ID")
    private ComprobanteEgreso comprobanteEgreso;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="COMPROBANTEINGRESO_ID")
    private ComprobanteEgreso comprobanteIngreso;
    @ManyToOne(optional = true, targetEntity = Puc.class)
    @JoinColumn(name="PUC_ID")
    private Puc conPuc;
    @ManyToOne(optional = true, targetEntity = Proveedores.class)
    @JoinColumn(name="PPROVEEDORES_ID")
    Proveedores proveedores;
    private BigDecimal debe=BigDecimal.ZERO;
    private BigDecimal haber=BigDecimal.ZERO;
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
    
     public LibroAuxiliar()
     {
         
     }
     public LibroAuxiliar(Puc conPuc,BigDecimal debe, BigDecimal haber)
     {
       this.conPuc=conPuc;
       this.debe=debe;
       this.haber=haber;
     }   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Puc getConPuc() {
        return conPuc;
    }

    public void setConPuc(Puc conPuc) {
        this.conPuc = conPuc;
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

   
   
    private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }
    
@Override
     public int compareTo(LibroAuxiliar cc) {
        String a=this.getConPuc().getCodigo();
        String b=cc.getConPuc().getCodigo();
        return a.compareTo(b);
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public NotaContabilidad getNotaContabilidad() {
        return notaContabilidad;
    }

    public void setNotaContabilidad(NotaContabilidad notaContabilidad) {
        this.notaContabilidad = notaContabilidad;
    }

    public ComprobanteCausacionEgreso getComprobanteCausacionEgreso() {
        return comprobanteCausacionEgreso;
    }

    public void setComprobanteCausacionEgreso(ComprobanteCausacionEgreso comprobanteCausacionEgreso) {
        this.comprobanteCausacionEgreso = comprobanteCausacionEgreso;
    }

    public ComprobanteEgreso getComprobanteEgreso() {
        return comprobanteEgreso;
    }

    public void setComprobanteEgreso(ComprobanteEgreso comprobanteEgreso) {
        this.comprobanteEgreso = comprobanteEgreso;
    }

    public ComprobanteEgreso getComprobanteIngreso() {
        return comprobanteIngreso;
    }

    public void setComprobanteIngreso(ComprobanteEgreso comprobanteIngreso) {
        this.comprobanteIngreso = comprobanteIngreso;
    }

    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

    
   
 

    
    
   
}
