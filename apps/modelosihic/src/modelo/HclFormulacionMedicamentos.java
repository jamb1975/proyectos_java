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
@Table(name = "HclFormulacionMedicamentos")
public class HclFormulacionMedicamentos implements Serializable {
    private static final long serialVersionUID = 1L;
   @Column(name = "ID", table = "HclFormulacionMedicamentos", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_HCLFORMULACIONMEDICAMENTOS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_HCLFORMULACIONMEDICAMENTOS_ID", sequenceName = "SEQ_HCLFORMULACIONMEDICAMENTOS_ID", allocationSize = 1, initialValue = 1)
    private Long id;
   @ManyToOne(optional = true, targetEntity = HclFormulacionesMedicas.class)
   @JoinColumn(name="HCLFORMULACIONESMEDICAS_ID")   
   private HclFormulacionesMedicas hclFormulacionesMedicas;
    @ManyToOne(optional = true, targetEntity = MedPresentacionMedicamentos.class)
   @JoinColumn(name="MEDPRESENTACIONMEDICAMENTOS_ID")   
   private MedPresentacionMedicamentos medPresentacionMedicamentos;
   @ManyToOne(optional = true, targetEntity = MedUnidadDosis.class)
   @JoinColumn(name="MEDUNIDADDOSIS_ID")   
   private MedUnidadDosis medUnidadDosis;
   @ManyToOne(optional = true, targetEntity = MedUnidadFrecuencia.class)
   @JoinColumn(name="MEDUNIDADFRECUENCIA_ID")   
   private MedUnidadFrecuencia medUnidadFrecuencia;
   @ManyToOne(optional = true, targetEntity = MedUnidadTratamiento.class)
   @JoinColumn(name="MEDUNIDADTRATAMIENTO_ID")   
   private MedUnidadTratamiento medUnidadTratamiento;
   @ManyToOne(optional = true, targetEntity = MedViasAdministracion.class)
   @JoinColumn(name="MEDVIASADMINISTRACION_ID")   
   private MedViasAdministracion medViasAdministracion;
    @Column(length = 20)
   private String codigomedicamento;
   private int frecuencia;
   private int tiempotratamiento;
   @Column(length = 100)
   private String cantidadletras;
   private int cantidad;
   @Column(length = 200)
   private String medicamento;
   private float dosis;
   @Column(length = 500)
   private String recomendacion;
   @Column(length = 500)
   private String justificacion;
   
   
   
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

    public HclFormulacionesMedicas getHclFormulacionesMedicas() {
        return hclFormulacionesMedicas;
    }

    public void setHclFormulacionesMedicas(HclFormulacionesMedicas hclFormulacionesMedicas) {
        this.hclFormulacionesMedicas = hclFormulacionesMedicas;
    }

    public MedPresentacionMedicamentos getMedPresentacionMedicamentos() {
        return medPresentacionMedicamentos;
    }

    public void setMedPresentacionMedicamentos(MedPresentacionMedicamentos medPresentacionMedicamentos) {
        this.medPresentacionMedicamentos = medPresentacionMedicamentos;
    }

    public MedUnidadDosis getMedUnidadDosis() {
        return medUnidadDosis;
    }

    public void setMedUnidadDosis(MedUnidadDosis medUnidadDosis) {
        this.medUnidadDosis = medUnidadDosis;
    }

    public MedUnidadFrecuencia getMedUnidadFrecuencia() {
        return medUnidadFrecuencia;
    }

    public void setMedUnidadFrecuencia(MedUnidadFrecuencia medUnidadFrecuencia) {
        this.medUnidadFrecuencia = medUnidadFrecuencia;
    }

    public MedUnidadTratamiento getMedUnidadTratamiento() {
        return medUnidadTratamiento;
    }

    public void setMedUnidadTratamiento(MedUnidadTratamiento medUnidadTratamiento) {
        this.medUnidadTratamiento = medUnidadTratamiento;
    }

    public MedViasAdministracion getMedViasAdministracion() {
        return medViasAdministracion;
    }

    public void setMedViasAdministracion(MedViasAdministracion medViasAdministracion) {
        this.medViasAdministracion = medViasAdministracion;
    }

    public String getCodigomedicamento() {
        return codigomedicamento;
    }

    public void setCodigomedicamento(String codigomedicamento) {
        this.codigomedicamento = codigomedicamento;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getTiempotratamiento() {
        return tiempotratamiento;
    }

    public void setTiempotratamiento(int tiempotratamiento) {
        this.tiempotratamiento = tiempotratamiento;
    }

    public String getCantidadletras() {
        return cantidadletras;
    }

    public void setCantidadletras(String cantidadletras) {
        this.cantidadletras = cantidadletras;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public float getDosis() {
        return dosis;
    }

    public void setDosis(float dosis) {
        this.dosis = dosis;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
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
