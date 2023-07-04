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
@Table(name = "HclFormulacionesMedicas")
public class HclFormulacionesMedicas implements Serializable {
    private static final long serialVersionUID = 1L;
   @Column(name = "ID", table = "HclFormulacionesMedicas", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_HCLFORMULACIONESMEDICAS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_HCLFORMULACIONESMEDICAS_ID", sequenceName = "SEQ_HCLFORMULACIONESMEDICAS_ID", allocationSize = 1, initialValue = 1)
    private Long id;
   @ManyToOne(optional = true, targetEntity = HclConsultas.class)
   @JoinColumn(name="HCLCONSULTAS_ID")   
   private HclConsultas hclConsultas;
    @ManyToOne(optional = true, targetEntity = GenEstadoFormula.class)
   @JoinColumn(name="GENESTADOFORMULA_ID")   
   private GenEstadoFormula genEstadoFormula;
   @ManyToOne(optional = true, targetEntity = HclTiposFormulas.class)
   @JoinColumn(name="HCLTIPOSFORMULAS_ID")   
   private HclTiposFormulas hclTiposFormulas;
   @Temporal(TemporalType.TIMESTAMP)
   @Basic
   private Date fechaformulacion;
   @Column(length = 300)
   private String ordenmedica;
   @Column(length = 300)
   private String ordenterapeutica;
   @Column(length = 100)
   private String descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public HclConsultas getHclConsultas() {
        return hclConsultas;
    }

    public void setHclConsultas(HclConsultas hclConsultas) {
        this.hclConsultas = hclConsultas;
    }

    public GenEstadoFormula getGenEstadoFormula() {
        return genEstadoFormula;
    }

    public void setGenEstadoFormula(GenEstadoFormula genEstadoFormula) {
        this.genEstadoFormula = genEstadoFormula;
    }

   

    public Date getFechaformulacion() {
        return fechaformulacion;
    }

    public void setFechaformulacion(Date fechaformulacion) {
        this.fechaformulacion = fechaformulacion;
    }

    public String getOrdenmedica() {
        return ordenmedica;
    }

    public void setOrdenmedica(String ordenmedica) {
        this.ordenmedica = ordenmedica;
    }

    public String getOrdenterapeutica() {
        return ordenterapeutica;
    }

    public void setOrdenterapeutica(String ordenterapeutica) {
        this.ordenterapeutica = ordenterapeutica;
    }

    public HclTiposFormulas getHclTiposFormulas() {
        return hclTiposFormulas;
    }

    public void setHclTiposFormulas(HclTiposFormulas hclTiposFormulas) {
        this.hclTiposFormulas = hclTiposFormulas;
    }

   
    
}
