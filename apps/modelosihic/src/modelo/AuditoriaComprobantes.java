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
import javax.persistence.CascadeType;
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
@Table(name = "AuditoriaComprobantes")
public class AuditoriaComprobantes implements Serializable {
  @Column(name = "ID", table = "AuditoriaComprobantes", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
  @Id
  @GeneratedValue(generator = "SEQ_AUDITORIACOMPROBANTES_ID", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "SEQ_AUDITORIACOMPROBANTES_ID", sequenceName = "SEQ_AUDITORIACOMPROBANTES_ID", allocationSize = 1, initialValue = 1)
  private  Long id;
  private String numerofactura;
  private String numerocomprobante;
  private String usuario;
  private String noident;
  private BigDecimal valor=BigDecimal.ZERO;

    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaElimino;
    @ManyToOne(cascade = CascadeType.ALL)
    Usuarios usuarioelimino;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumerofactura() {
        return numerofactura;
    }

    public void setNumerofactura(String numerofactura) {
        this.numerofactura = numerofactura;
    }

    public String getNumerocomprobante() {
        return numerocomprobante;
    }

    public void setNumerocomprobante(String numerocomprobante) {
        this.numerocomprobante = numerocomprobante;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNoident() {
        return noident;
    }

    public void setNoident(String noident) {
        this.noident = noident;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getFechaElimino() {
        return fechaElimino;
    }

    public void setFechaElimino(Date fechaElimino) {
        this.fechaElimino = fechaElimino;
    }

    public Usuarios getUsuarioelimino() {
        return usuarioelimino;
    }

    public void setUsuarioelimino(Usuarios usuarioelimino) {
        this.usuarioelimino = usuarioelimino;
    }

    


   

    
    
    
    
}
