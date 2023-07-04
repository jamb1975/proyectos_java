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
@Table(name = "HclAtenciones")
public class HclAtenciones implements Serializable {
    @Id
    @GeneratedValue(generator = "SEQ_HCLATENCIONES_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_HCLATENCIONES_ID", sequenceName = "SEQ_HCLATENCIONES_ID", allocationSize = 1, initialValue = 1)
    private  Long id;
    @Temporal(TemporalType.TIMESTAMP)
     private Date fechaInicio;
    @ManyToOne(optional = true, targetEntity = HclTiposAtenciones.class)
    @JoinColumn(name="HCLTIPOSATENCIONES_ID")
    private  HclTiposAtenciones hclTiposAtenciones;
    @Column(length = 20)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "HCLHISTORIASCLINICA_ID")        
    HclHistoriasClinicas hclHistoriasClinicas;
     @ManyToOne
    @JoinColumn(name = "GENUNIDADESORGANIZACION_ID") 
    GenUnidadesOrganizacion genUnidadesOrganizacion;
      @ManyToOne
    
    @Temporal(TemporalType.TIMESTAMP)
     private Date fechaFin;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public HclTiposAtenciones getHclTiposAtenciones() {
        return hclTiposAtenciones;
    }

    public void setHclTiposAtenciones(HclTiposAtenciones hclTiposAtenciones) {
        this.hclTiposAtenciones = hclTiposAtenciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public HclHistoriasClinicas getHclHistoriasClinicas() {
        return hclHistoriasClinicas;
    }

    public void setHclHistoriasClinicas(HclHistoriasClinicas hclHistoriasClinicas) {
        this.hclHistoriasClinicas = hclHistoriasClinicas;
    }

    public GenUnidadesOrganizacion getGenUnidadesOrganizacion() {
        return genUnidadesOrganizacion;
    }

    public void setGenUnidadesOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion) {
        this.genUnidadesOrganizacion = genUnidadesOrganizacion;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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
