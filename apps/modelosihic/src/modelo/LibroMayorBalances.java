/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author adminlinux
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"año", "mes","PUC_ID"})})
public class LibroMayorBalances implements Serializable {
    
    @Id
    @GeneratedValue(generator = "SEQ_LIBROMAYORBALANCES_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_LIBROMAYORBALANCES_ID", sequenceName = "SEQ_LIBROMAYORBALANCES_ID", allocationSize = 1, initialValue = 1)
    private Long id; 
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaelaboracion;
    private int año;
    private int mes;
    private String detalle="";
    private BigDecimal saldoanteriordebito=BigDecimal.ZERO;
    private BigDecimal saldoanteriorcredito=BigDecimal.ZERO;
    private BigDecimal movimientosdebito=BigDecimal.ZERO;
    private BigDecimal movimientoscredito=BigDecimal.ZERO;
    private BigDecimal saldodebito=BigDecimal.ZERO;
    private BigDecimal saldocredito=BigDecimal.ZERO;
    @ManyToOne(optional = true, targetEntity = Puc.class)
    @JoinColumn(name="PUC_ID")
    private Puc conPuc;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaelaboracion() {
        return fechaelaboracion;
    }

    public void setFechaelaboracion(Date fechaelaboracion) {
        this.fechaelaboracion = fechaelaboracion;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

   

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getSaldoanteriordebito() {
        return saldoanteriordebito;
    }

    public void setSaldoanteriordebito(BigDecimal saldoanteriordebito) {
        this.saldoanteriordebito = saldoanteriordebito;
    }

    public BigDecimal getSaldoanteriorcredito() {
        return saldoanteriorcredito;
    }

    public void setSaldoanteriorcredito(BigDecimal saldoanteriorcredito) {
        this.saldoanteriorcredito = saldoanteriorcredito;
    }

    public BigDecimal getMovimientosdebito() {
        return movimientosdebito;
    }

    public void setMovimientosdebito(BigDecimal movimientosdebito) {
        this.movimientosdebito = movimientosdebito;
    }

    public BigDecimal getMovimientoscredito() {
        return movimientoscredito;
    }

    public void setMovimientoscredito(BigDecimal movimientoscredito) {
        this.movimientoscredito = movimientoscredito;
    }

    public BigDecimal getSaldodebito() {
        return saldodebito;
    }

    public void setSaldodebito(BigDecimal saldodebito) {
        this.saldodebito = saldodebito;
    }

    public BigDecimal getSaldocredito() {
        return saldocredito;
    }

    public void setSaldocredito(BigDecimal saldocredito) {
        this.saldocredito = saldocredito;
    }

    public Puc getConPuc() {
        return conPuc;
    }

    public void setConPuc(Puc conPuc) {
        this.conPuc = conPuc;
    }
   
}
