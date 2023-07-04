/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author adminlinux
 */
@Entity
public class LibroAuxiliar implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "SEQ_LIBROAUXILIAR_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_LIBROAUXILIAR_ID", sequenceName = "SEQ_LIBROAUXILIAR_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="DOCSOPORTE_ID")
    private DocumentoSoporte documentoSoporte;
    @ManyToOne(optional = true, targetEntity = Terceros.class,cascade = CascadeType.MERGE)
    @JoinColumn(name="TERCEROS_ID")
    private Terceros terceros;
    @ManyToOne(optional = true, targetEntity = Puc.class)
    @JoinColumn(name="PUC_ID")
    private Puc conPuc;
    private BigDecimal debe=BigDecimal.ZERO;
    private BigDecimal haber=BigDecimal.ZERO;
    private String descripcion;
    private Long id_source_mov;
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
    @Transient
    boolean ingresar;
    private String nofactura="";
    
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

    public DocumentoSoporte getDocumentoSoporte() {
        return documentoSoporte;
    }

    public void setDocumentoSoporte(DocumentoSoporte documentoSoporte) {
        this.documentoSoporte = documentoSoporte;
    }

   
   
    private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }

    public void set(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Terceros getTerceros() {
        return terceros;
    }

    public void setTerceros(Terceros terceros) {
        this.terceros = terceros;
    }

    public Long getId_source_mov() {
        return id_source_mov;
    }

    public void setId_source_mov(Long id_source_mov) {
        this.id_source_mov = id_source_mov;
    }

    public boolean isIngresar() {
        return ingresar;
    }

    public void setIngresar(boolean ingresar) {
        this.ingresar = ingresar;
    }

    public String getNofactura() {
        return nofactura;
    }

    public void setNofactura(String nofactura) {
        this.nofactura = nofactura;
    }
       
}
