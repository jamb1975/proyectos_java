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

/**
 *
 * @author adminlinux
 */
@Entity
@Table(name = "HclConsultas")
public class HclConsultas implements Serializable {
     @Column(name = "ID", table = "HclConsultas", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_HCL_CONSULTAS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_HCL_CONSULTAS_ID", sequenceName = "SEQ_HCL_CONSULTAS_ID", allocationSize = 1, initialValue = 1)
    private  Long id;
    @ManyToOne(optional = true, targetEntity = HclCodigosConsultas.class)
    @JoinColumn(name="HCLCODIGOSCONSULTAS_ID") 
    private HclCodigosConsultas codigosConsultas;
    @ManyToOne(optional = true, targetEntity = HclCausaExterna.class,fetch = FetchType.LAZY)
    @JoinColumn(name="HCL_CAUSAEXTERNA_ID")
    private HclCausaExterna causaExterna;
    @ManyToOne(optional = true, targetEntity = HclFinalidad.class,fetch = FetchType.LAZY)
    @JoinColumn(name="HCL_FINALIDAD_ID")
    private HclFinalidad hclFinalidad;
     
    @ManyToOne(optional = true, targetEntity = AgnCitas.class,fetch = FetchType.LAZY)
    @JoinColumn(name="HCL_AGNCITAS_ID", unique = true)
    private AgnCitas agnCitas;
    @ManyToOne(optional = true, targetEntity = HclTiposAtenciones.class,fetch = FetchType.LAZY)
    @JoinColumn(name="HCL_TIPOATENCIONES_ID")
    private HclTiposAtenciones HclTiposAtenciones;
    @Column(length = 100)
    private String concepto;
    @Column(length = 1000)
    private String anamnesis;
     @Column(length = 1000)
    private String motivo;
    @Column(length = 50) 
    private String numeroautorizacion;
    
    @Column(name = "fechaevolucion")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaevolucion;
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
    private String examensolicitado;
    private String descripcion;
    private String opinion;
    private String procedimiento;
    private String medicoqueordeno;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HclCodigosConsultas getCodigosConsultas() {
        return codigosConsultas;
    }

    public void setCodigosConsultas(HclCodigosConsultas codigosConsultas) {
        this.codigosConsultas = codigosConsultas;
    }

    public HclCausaExterna getCausaExterna() {
        return causaExterna;
    }

    public void setCausaExterna(HclCausaExterna causaExterna) {
        this.causaExterna = causaExterna;
    }

    public HclFinalidad getHclFinalidad() {
        return hclFinalidad;
    }

    public void setHclFinalidad(HclFinalidad hclFinalidad) {
        this.hclFinalidad = hclFinalidad;
    }

        public AgnCitas getAgnCitas() {
        return agnCitas;
    }

    public void setAgnCitas(AgnCitas agnCitas) {
        this.agnCitas = agnCitas;
    }

    public HclTiposAtenciones getHclTiposAtenciones() {
        return HclTiposAtenciones;
    }

    public void setHclTiposAtenciones(HclTiposAtenciones HclTiposAtenciones) {
        this.HclTiposAtenciones = HclTiposAtenciones;
    }

    

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNumeroautorizacion() {
        return numeroautorizacion;
    }

    public void setNumeroautorizacion(String numeroautorizacion) {
        this.numeroautorizacion = numeroautorizacion;
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

    public Date getFechaevolucion() {
        return fechaevolucion;
    }

    public void setFechaevolucion(Date fechaevolucion) {
        this.fechaevolucion = fechaevolucion;
    }

    public String getExamensolicitado() {
        return examensolicitado;
    }

    public void setExamensolicitado(String examensolicitado) {
        this.examensolicitado = examensolicitado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getMedicoqueordeno() {
        return medicoqueordeno;
    }

    public void setMedicoqueordeno(String medicoqueordeno) {
        this.medicoqueordeno = medicoqueordeno;
    }
    
     
}
