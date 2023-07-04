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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adminlinux
 */
@Entity
@XmlRootElement
public class ConsumoKwH implements Serializable {
    @Id
    @GeneratedValue(generator = "SEQ_CONSUMOKWH_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CONSUMOKWH_ID", sequenceName = "SEQ_CONSUMOKWH_ID", allocationSize = 1, initialValue = 1)
    private Long id; 
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fecha;
   @Column(precision = 5,scale=2)
    private BigDecimal virms=BigDecimal.ZERO;//voltaje eficaz(rms)
    @Column(precision = 5,scale=2)
    private BigDecimal iirms=BigDecimal.ZERO;//corriente eficaz(rms)
    @Column(scale=3) 
    private BigDecimal vpp=BigDecimal.ZERO;//voltaje pico a pico
    @Column(scale=3) 
    private BigDecimal ipp=BigDecimal.ZERO;//corriente pico a pico
    @Column(scale=3) 
    private BigDecimal vmax=BigDecimal.ZERO;//voltaje maximo
    @Column(scale=3) 
    private BigDecimal imax=BigDecimal.ZERO;//corriente maximo 
    @Column(scale=3) 
    private BigDecimal vinstantaneo=BigDecimal.ZERO;//voltaje instantaneo
    @Column(scale=3) 
    private BigDecimal iinstantanea=BigDecimal.ZERO;//corriente instantanea
  @Column(precision = 11,scale=7)
    private BigDecimal kwh=BigDecimal.ZERO;//consumo kilovatio hora

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getVirms() {
        return virms;
    }

    public void setVirms(BigDecimal virms) {
        this.virms = virms;
    }

    public BigDecimal getIirms() {
        return iirms;
    }

    public void setIirms(BigDecimal iirms) {
        this.iirms = iirms;
    }

    public BigDecimal getVpp() {
        return vpp;
    }

    public void setVpp(BigDecimal vpp) {
        this.vpp = vpp;
    }

    public BigDecimal getIpp() {
        return ipp;
    }

    public void setIpp(BigDecimal ipp) {
        this.ipp = ipp;
    }

    public BigDecimal getVmax() {
        return vmax;
    }

    public void setVmax(BigDecimal vmax) {
        this.vmax = vmax;
    }

    public BigDecimal getImax() {
        return imax;
    }

    public void setImax(BigDecimal imax) {
        this.imax = imax;
    }

    public BigDecimal getVinstantaneo() {
        return vinstantaneo;
    }

    public void setVinstantaneo(BigDecimal vinstantaneo) {
        this.vinstantaneo = vinstantaneo;
    }

    public BigDecimal getIinstantanea() {
        return iinstantanea;
    }

    public void setIinstantanea(BigDecimal iinstantanea) {
        this.iinstantanea = iinstantanea;
    }

    public BigDecimal getKwh() {
        return kwh;
    }

    public void setKwh(BigDecimal kwh) {
        this.kwh = kwh;
    }
    
}
