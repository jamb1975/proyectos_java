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
public class Contador implements Serializable {
    @Id
    @GeneratedValue(generator = "SEQ_CONTADOR_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CONTADOR_ID", sequenceName = "SEQ_CONTADOR_ID", allocationSize = 1, initialValue = 1)
    private Long id; 
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fecha;
    @Column(precision = 11,scale=7)
    private BigDecimal kwh=BigDecimal.ZERO;//consumo kilovatio hora
    private BigDecimal irms=BigDecimal.ZERO;
     private BigDecimal vrms=BigDecimal.ZERO;
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

   
    public BigDecimal getKwh() {
        return kwh;
    }

    public void setKwh(BigDecimal kwh) {
        this.kwh = kwh;
    }

    public BigDecimal getIrms() {
        return irms;
    }

    public void setIrms(BigDecimal irms) {
        this.irms = irms;
    }

    public BigDecimal getVrms() {
        return vrms;
    }

    public void setVrms(BigDecimal vrms) {
        this.vrms = vrms;
    }
    
    
}
