/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author karol
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "NOTASESTUDIO")
public class NotasEstudio
    implements Serializable
{
    @Column(name = "ID", table = "NOTASESTUDIO", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_NOTASESTUDIO_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_NOTASESTUDIO_ID", sequenceName = "SEQ_NOTASESTUDIO_ID", allocationSize = 1, initialValue = 1)
    private Long       id;
    private String     detalle;
    private String     elaboro;
    private int pacientellego;
    private int estadosalida;
    private String svingresota;
    private String svingresofc;
    private String svingresospo2;
    private String svegresota;
    private String svegresofc;
    private String svegresospo2;
    private float peso; 
    private String conclusion;
    private int horaingreso;
    private int minutosingreso;
    private int horaatencion;
    private int minutosatencion;
    private int horaegreso;
    private int minutosegreso;
    private boolean embarazada;
    private String acompanante;
    private String miembrosuperior;
    private int tipo;
    private float cantiopramida;
    private String jelco;
    @Column(length = 1)
    private String af;
    @ManyToOne
    @JoinColumn(name="HCLCIE10_ID")
    private HclCie10 hclCie10;
    @ManyToOne
    @JoinColumn(name="HCLCONSULTAS_ID")
    private HclConsultas   hclConsultas;
    @ManyToOne
    @JoinColumn(name="FACTURAITEM_ID")
    private FacturaItem   facturaItem;
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
    @Lob
    private String notaenfermeria;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getElaboro() {
        return elaboro;
    }

    public void setElaboro(String elaboro) {
        this.elaboro = elaboro;
    }

    public int getPacientellego() {
        return pacientellego;
    }

    public void setPacientellego(int pacientellego) {
        this.pacientellego = pacientellego;
    }

    public int getEstadosalida() {
        return estadosalida;
    }

    public void setEstadosalida(int estadosalida) {
        this.estadosalida = estadosalida;
    }

    public String getSvingresota() {
        return svingresota;
    }

    public void setSvingresota(String svingresota) {
        this.svingresota = svingresota;
    }

    public String getSvingresofc() {
        return svingresofc;
    }

    public void setSvingresofc(String svingresofc) {
        this.svingresofc = svingresofc;
    }

    public String getSvingresospo2() {
        return svingresospo2;
    }

    public void setSvingresospo2(String svingresospo2) {
        this.svingresospo2 = svingresospo2;
    }

    public String getSvegresota() {
        return svegresota;
    }

    public void setSvegresota(String svegresota) {
        this.svegresota = svegresota;
    }

    public String getSvegresofc() {
        return svegresofc;
    }

    public void setSvegresofc(String svegresofc) {
        this.svegresofc = svegresofc;
    }

    public String getSvegresospo2() {
        return svegresospo2;
    }

    public void setSvegresospo2(String svegresospo2) {
        this.svegresospo2 = svegresospo2;
    }

   
    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public int getHoraegreso() {
        return horaegreso;
    }

    public void setHoraegreso(int horaegreso) {
        this.horaegreso = horaegreso;
    }

    public int getMinutosegreso() {
        return minutosegreso;
    }

    public void setMinutosegreso(int minutosegreso) {
        this.minutosegreso = minutosegreso;
    }

    public HclConsultas getHclConsultas() {
        return hclConsultas;
    }

    public void setHclConsultas(HclConsultas hclConsultas) {
        this.hclConsultas = hclConsultas;
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

    public boolean isEmbarazada() {
        return embarazada;
    }

    public void setEmbarazada(boolean embarazada) {
        this.embarazada = embarazada;
    }

    public int getHoraingreso() {
        return horaingreso;
    }

    public void setHoraingreso(int horaingreso) {
        this.horaingreso = horaingreso;
    }

    public int getMinutosingreso() {
        return minutosingreso;
    }

    public void setMinutosingreso(int minutosingreso) {
        this.minutosingreso = minutosingreso;
    }

    public int getHoraatencion() {
        return horaatencion;
    }

    public void setHoraatencion(int horaatencion) {
        this.horaatencion = horaatencion;
    }

    public int getMinutosatencion() {
        return minutosatencion;
    }

    public void setMinutosatencion(int minutosatencion) {
        this.minutosatencion = minutosatencion;
    }

    public String getAcompanante() {
        return acompanante;
    }

    public void setAcompanante(String acompanante) {
        this.acompanante = acompanante;
    }

    public String getMiembrosuperior() {
        return miembrosuperior;
    }

    public void setMiembrosuperior(String miembrosuperior) {
        this.miembrosuperior = miembrosuperior;
    }

    public FacturaItem getFacturaItem() {
        return facturaItem;
    }

    public void setFacturaItem(FacturaItem facturaItem) {
        this.facturaItem = facturaItem;
    }

    public HclCie10 getHclCie10() {
        return hclCie10;
    }

    public void setHclCie10(HclCie10 hclCie10) {
        this.hclCie10 = hclCie10;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public float getCantiopramida() {
        return cantiopramida;
    }

    public void setCantiopramida(float cantiopramida) {
        this.cantiopramida = cantiopramida;
    }

    public String getAf() {
        return af;
    }

    public void setAf(String af) {
        this.af = af;
    }

    public String getJelco() {
        return jelco;
    }

    public void setJelco(String jelco) {
        this.jelco = jelco;
    }

    public String getNotaenfermeria() {
        return notaenfermeria;
    }

    public void setNotaenfermeria(String notaenfermeria) {
        this.notaenfermeria = notaenfermeria;
    }

    
    
     
}