/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adminlinux
 */
@Entity
@XmlRootElement
@Table(name = "FactorHorasExtras")
public class FactorHorasExtras {
     @Column(name = "ID", table = "FactorHorasExtras", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_FACTORHORASEXTRAS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_FACTORHORASEXTRAS_ID", sequenceName = "SEQ_FACTORHORASEXTRAS_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @Column(precision = 5,scale=2)
    private BigDecimal factor_extradiurnoordinario=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal factor_extranocturnoordinario=BigDecimal.ZERO; 
    @Column(precision = 5,scale=2)
    private BigDecimal factor_dominicalyfestivo=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal factor_dominicalyfestivonocturno=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal factor_extradiurnofestivo=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal factor_extranocturnofestivo=BigDecimal.ZERO;
    @Column(precision = 5,scale=2)
    private BigDecimal factor_recargonocturno=BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFactor_extradiurnoordinario() {
        return factor_extradiurnoordinario;
    }

    public void setFactor_extradiurnoordinario(BigDecimal factor_extradiurnoordinario) {
        this.factor_extradiurnoordinario = factor_extradiurnoordinario;
    }

    public BigDecimal getFactor_extranocturnoordinario() {
        return factor_extranocturnoordinario;
    }

    public void setFactor_extranocturnoordinario(BigDecimal factor_extranocturnoordinario) {
        this.factor_extranocturnoordinario = factor_extranocturnoordinario;
    }

    public BigDecimal getFactor_dominicalyfestivo() {
        return factor_dominicalyfestivo;
    }

    public void setFactor_dominicalyfestivo(BigDecimal factor_dominicalyfestivo) {
        this.factor_dominicalyfestivo = factor_dominicalyfestivo;
    }

    public BigDecimal getFactor_dominicalyfestivonocturno() {
        return factor_dominicalyfestivonocturno;
    }

    public void setFactor_dominicalyfestivonocturno(BigDecimal factor_dominicalyfestivonocturno) {
        this.factor_dominicalyfestivonocturno = factor_dominicalyfestivonocturno;
    }

    public BigDecimal getFactor_extradiurnofestivo() {
        return factor_extradiurnofestivo;
    }

    public void setFactor_extradiurnofestivo(BigDecimal factor_extradiurnofestivo) {
        this.factor_extradiurnofestivo = factor_extradiurnofestivo;
    }

    public BigDecimal getFactor_extranocturnofestivo() {
        return factor_extranocturnofestivo;
    }

    public void setFactor_extranocturnofestivo(BigDecimal factor_extranocturnofestivo) {
        this.factor_extranocturnofestivo = factor_extranocturnofestivo;
    }

    public BigDecimal getFactor_recargonocturno() {
        return factor_recargonocturno;
    }

    public void setFactor_recargonocturno(BigDecimal factor_recargonocturno) {
        this.factor_recargonocturno = factor_recargonocturno;
    }
     
     
   
}
