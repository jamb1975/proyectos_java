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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author adminlinux
 */
@Entity
public class Auditoria implements Serializable {
 
 @Id
 @GeneratedValue(generator = "SEQ_AUDITORIA_ID", strategy = GenerationType.SEQUENCE)
 @SequenceGenerator(name = "SEQ_AUDITORIA_ID", sequenceName = "SEQ_AUDITORIA_ID", allocationSize = 1, initialValue = 1)
 private  Long id;
 @Column(length = 1)   
 private String tipo_proceso;  //0 conexion 1 consulta 2 insertar  3 actualizar 4 eliminar 
 @ManyToOne
 private Usuarios usuarios;
 private String descripcion;
 @Temporal(TemporalType.TIMESTAMP)
 @Basic
 private Date fechasuceso;
 @Column(length = 200)
 private String modulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo_proceso() {
        return tipo_proceso;
    }

    public void setTipo_proceso(String tipo_proceso) {
        this.tipo_proceso = tipo_proceso;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechasuceso() {
        return fechasuceso;
    }

    public void setFechasuceso(Date fechasuceso) {
        this.fechasuceso = fechasuceso;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
 
  
}
