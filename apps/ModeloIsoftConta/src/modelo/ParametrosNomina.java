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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karol
 */
@Entity
@XmlRootElement
@Table(name = "ParametrosNomina")
public class ParametrosNomina implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "ID", table = "ParametrosNomina", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_PARAMETROSNOMINA_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PARAMETROSNOMINA_ID", sequenceName = "SEQ_PARAMETROSNOMINA_ID", allocationSize = 1, initialValue = 1)
    private Long id;
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
    @Column(scale=2) 
    private BigDecimal salariominimo=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal auxiliotransporte=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal porc_salud=BigDecimal.ZERO;
     @Column(precision = 5,scale=2)
    private BigDecimal porc_pension=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal porc_fp=BigDecimal.ZERO;
    @Column(precision = 5,scale=3)
    private BigDecimal porc_arl=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal porc_cajacompfam=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal porc_icbf=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal porc_sena=BigDecimal.ZERO;
     @Column(precision = 5,scale=3)
    private BigDecimal porc_cesantias=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal porc_intcesantias=BigDecimal.ZERO;
    @Column(precision = 5,scale=3)
    private BigDecimal porc_prima=BigDecimal.ZERO;
    @Column(precision = 5,scale=3)
    private BigDecimal porc_vacaciones=BigDecimal.ZERO;
     @Column(precision = 5,scale=2)
    private BigDecimal porc_solidaridad=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal uvt=BigDecimal.ZERO;
     @Column(precision = 5,scale=2)
    private BigDecimal porc_retefuente95=BigDecimal.ZERO;
    private int cantidaduvtsalarios95=95;
    @Column(precision = 5,scale=2)
    private BigDecimal porc_retefuentemay95y150=BigDecimal.ZERO;
    private int cantidaduvtsalarios95y150=150;
    @Column(precision = 5,scale=2)
    private BigDecimal porc_retefuentemay150y360=BigDecimal.ZERO;
    private int cantidaduvtsalarios150y360=360;
    private BigDecimal porc_retefuentemay360=BigDecimal.ZERO;
    private int cantidaduvtsalariosmay360=360;
   
    private int maxcantidadsalminimosolidarida=4;
    @Column(precision = 5,scale=2)
    private BigDecimal porc_salud_empleador=BigDecimal.ZERO;
   
    
    @Column(unique = true)
    private int año; 
   
   
    private int maxcantidadsalminauxtransporte=2;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getSalariominimo() {
        return salariominimo;
    }

    public void setSalariominimo(BigDecimal salariominimo) {
        this.salariominimo = salariominimo;
    }

    public BigDecimal getAuxiliotransporte() {
        return auxiliotransporte;
    }

    public void setAuxiliotransporte(BigDecimal auxiliotransporte) {
        this.auxiliotransporte = auxiliotransporte;
    }

    public BigDecimal getPorc_salud() {
        return porc_salud;
    }

    public void setPorc_salud(BigDecimal porc_salud) {
        this.porc_salud = porc_salud;
    }

    public BigDecimal getPorc_fp() {
        return porc_fp;
    }

    public void setPorc_fp(BigDecimal porc_fp) {
        this.porc_fp = porc_fp;
    }

    public BigDecimal getPorc_arl() {
        return porc_arl;
    }

    public void setPorc_arl(BigDecimal porc_arl) {
        this.porc_arl = porc_arl;
    }

    public BigDecimal getPorc_cajacompfam() {
        return porc_cajacompfam;
    }

    public void setPorc_cajacompfam(BigDecimal porc_cajacompfam) {
        this.porc_cajacompfam = porc_cajacompfam;
    }

    public BigDecimal getPorc_icbf() {
        return porc_icbf;
    }

    public void setPorc_icbf(BigDecimal porc_icbf) {
        this.porc_icbf = porc_icbf;
    }

    public BigDecimal getPorc_sena() {
        return porc_sena;
    }

    public void setPorc_sena(BigDecimal porc_sena) {
        this.porc_sena = porc_sena;
    }

    public BigDecimal getPorc_cesantias() {
        return porc_cesantias;
    }

    public void setPorc_cesantias(BigDecimal porc_cesantias) {
        this.porc_cesantias = porc_cesantias;
    }

    public BigDecimal getPorc_intcesantias() {
        return porc_intcesantias;
    }

    public void setPorc_intcesantias(BigDecimal porc_intcesantias) {
        this.porc_intcesantias = porc_intcesantias;
    }

    public BigDecimal getPorc_prima() {
        return porc_prima;
    }

    public void setPorc_prima(BigDecimal porc_prima) {
        this.porc_prima = porc_prima;
    }

    public BigDecimal getPorc_vacaciones() {
        return porc_vacaciones;
    }

    public void setPorc_vacaciones(BigDecimal porc_vacaciones) {
        this.porc_vacaciones = porc_vacaciones;
    }

    public BigDecimal getPorc_solidaridad() {
        return porc_solidaridad;
    }

    public void setPorc_solidaridad(BigDecimal porc_solidaridad) {
        this.porc_solidaridad = porc_solidaridad;
    }

    public BigDecimal getUvt() {
        return uvt;
    }

    public void setUvt(BigDecimal uvt) {
        this.uvt = uvt;
    }

    public BigDecimal getPorc_retefuente95() {
        return porc_retefuente95;
    }

    public void setPorc_retefuente95(BigDecimal porc_retefuente95) {
        this.porc_retefuente95 = porc_retefuente95;
    }

    public int getCantidaduvtsalarios95() {
        return cantidaduvtsalarios95;
    }

    public void setCantidaduvtsalarios95(int cantidaduvtsalarios95) {
        this.cantidaduvtsalarios95 = cantidaduvtsalarios95;
    }

    public BigDecimal getPorc_retefuentemay95y150() {
        return porc_retefuentemay95y150;
    }

    public void setPorc_retefuentemay95y150(BigDecimal porc_retefuentemay95y150) {
        this.porc_retefuentemay95y150 = porc_retefuentemay95y150;
    }

    public int getCantidaduvtsalarios95y150() {
        return cantidaduvtsalarios95y150;
    }

    public void setCantidaduvtsalarios95y150(int cantidaduvtsalarios95y150) {
        this.cantidaduvtsalarios95y150 = cantidaduvtsalarios95y150;
    }

    public BigDecimal getPorc_retefuentemay150y360() {
        return porc_retefuentemay150y360;
    }

    public void setPorc_retefuentemay150y360(BigDecimal porc_retefuentemay150y360) {
        this.porc_retefuentemay150y360 = porc_retefuentemay150y360;
    }

    public int getCantidaduvtsalarios150y360() {
        return cantidaduvtsalarios150y360;
    }

    public void setCantidaduvtsalarios150y360(int cantidaduvtsalarios150y360) {
        this.cantidaduvtsalarios150y360 = cantidaduvtsalarios150y360;
    }

    public BigDecimal getPorc_retefuentemay360() {
        return porc_retefuentemay360;
    }

    public void setPorc_retefuentemay360(BigDecimal porc_retefuentemay360) {
        this.porc_retefuentemay360 = porc_retefuentemay360;
    }

    public int getCantidaduvtsalariosmay360() {
        return cantidaduvtsalariosmay360;
    }

    public void setCantidaduvtsalariosmay360(int cantidaduvtsalariosmay360) {
        this.cantidaduvtsalariosmay360 = cantidaduvtsalariosmay360;
    }

    public int getMaxcantidadsalminimosolidarida() {
        return maxcantidadsalminimosolidarida;
    }

    public void setMaxcantidadsalminimosolidarida(int maxcantidadsalminimosolidarida) {
        this.maxcantidadsalminimosolidarida = maxcantidadsalminimosolidarida;
    }

    public int getMaxcantidadsalminauxtransporte() {
        return maxcantidadsalminauxtransporte;
    }

    public void setMaxcantidadsalminauxtransporte(int maxcantidadsalminauxtransporte) {
        this.maxcantidadsalminauxtransporte = maxcantidadsalminauxtransporte;
    }

    public BigDecimal getPorc_pension() {
        return porc_pension;
    }

    public void setPorc_pension(BigDecimal porc_pension) {
        this.porc_pension = porc_pension;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }


    

    public BigDecimal getPorc_salud_empleador() {
        return porc_salud_empleador;
    }

    public void setPorc_salud_empleador(BigDecimal porc_salud_empleador) {
        this.porc_salud_empleador = porc_salud_empleador;
    }

   

    
  

   
   
}
