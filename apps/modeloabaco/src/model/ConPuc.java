/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
@Table(name = "ConPuc")
public class ConPuc implements Serializable {
  @Column(name = "ID", table = "ConPuc", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_CONPUC_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CONPUC_ID", sequenceName = "SEQ_CONPUC_ID", allocationSize = 1, initialValue = 1)
  private  Long id;
  @ManyToOne
  @JoinColumn(name="CONPUC_ID")
  private  ConPuc conpuc_id;
  private  String codigo;
  @Column(length = 1000)
  private  String nombre;
  private  String descripcion;
  private int tipo;
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
   // @OneToMany(mappedBy="conpuc_id", cascade=CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
   // private List<ConPuc> l_conpuc = new ArrayList<>(); 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public ConPuc getConpuc_id() {
        return conpuc_id;
    }

    public void setConpuc_id(ConPuc conpuc_id) {
        this.conpuc_id = conpuc_id;
    }

   /* public List<ConPuc> getL_conpuc() {
        return l_conpuc;
    }

    public void setL_conpuc(List<ConPuc> l_conpuc) {
        this.l_conpuc = l_conpuc;
    }*/
    
    
}
