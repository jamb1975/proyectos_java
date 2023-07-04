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
@Table(name = "AgnConsultoriosProcedimientos")
public class AgnConsultoriosProcedimientos implements Serializable {
  @Column(name = "ID", table = "AgnConsultoriosProcedimientos", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
  @Id
  @GeneratedValue(generator = "SEQ_CONSULTORIOSPROCEDIMIENTOS_ID", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "SEQ_CONSULTORIOSPROCEDIMIENTOS_ID", sequenceName = "SEQ_CONSULTORIOSPROCEDIMIENTOS_ID", allocationSize = 1, initialValue = 1)
  private  Long id;
    @Column(name = "USUARIO_MODIFICADOR",length = 50)
    @Basic
    private String usuarioModificador;
    @ManyToOne(optional = true, targetEntity = Producto.class)
    @JoinColumn(name="SERVICIOS_ID")
    private Producto servicios_id;
    @ManyToOne(optional = true, targetEntity = AgnMedicos.class)
    @JoinColumn(name="AGNMEDICOS_ID")
    private AgnMedicos agnMedicos;
    @ManyToOne(optional = true, targetEntity = AgnConsultorios.class)
    @JoinColumn(name="AGNCONSULTORIOS_ID")
    private AgnConsultorios agnConsultorios;
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

    

    public AgnMedicos getAgnMedicos() {
        return agnMedicos;
    }

    public Producto getServicios_id() {
        return servicios_id;
    }

    public void setServicios_id(Producto servicios_id) {
        this.servicios_id = servicios_id;
    }

    public AgnConsultorios getAgnConsultorios() {
        return agnConsultorios;
    }

    public void setAgnConsultorios(AgnConsultorios agnConsultorios) {
        this.agnConsultorios = agnConsultorios;
    }

    public void setAgnMedicos(AgnMedicos agnMedicos) {
        this.agnMedicos = agnMedicos;
    }

   

    
    
    
    
}
