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
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author adminlinux
 */
@Entity
@NamedQuery(
            name="findLastLibroAuxiliar",
            query="select la from ConLibroAuxiliar la where la.id=(select MAX(la2.id) from ConLibroAuxiliar la2" + 
                                        " where   la2.fechaelaboracion between :m1 and :m2 or(la2.conPuc.conpuc_id.conpuc_id.conpuc_id=:cp or la2.conPuc.conpuc_id.conpuc_id=:cp or la2.conPuc.conpuc_id=:cp or la2.conPuc=:cp))" 
    )
public class ConLibroAuxiliar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "SEQ_CONLIBROAUXILIAR_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CONLIBROAUXILIAR_ID", sequenceName = "SEQ_CONLIBROAUXILIAR_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @ManyToOne(optional = true, targetEntity = ConPuc.class)
    @JoinColumn(name="PUC_ID")
    private ConPuc conPuc;
    @ManyToOne
    @JoinColumn(name="COMPROBANTECONTABILIDAD_ID")
    private ConComprobanteContabilidad conComprobanteContabilidad;
    private BigDecimal saldo;
    private BigDecimal debe;
    private BigDecimal haber;
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

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

   

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
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

   

   
   
    
}
