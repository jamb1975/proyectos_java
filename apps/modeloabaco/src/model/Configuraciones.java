/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author adminlinux
 */
@Entity
public class Configuraciones {
    @javax.persistence.SequenceGenerator(
            name = "SEQ_ID_CONF",
            sequenceName = "SEQ_ID_CONF",
            allocationSize = 1
    )
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_CONF")
    @Column(name = "ID")
    private Long id;
    private float porcentajertefuente=0;
    private float porcentajeica=0;
    private BigDecimal rteica=BigDecimal.ZERO;
    @ManyToOne
    @JoinColumn(name="MERCANCIASNOFAB_NATD")
    private ConPuc mercanciasnofab_natd;
    @ManyToOne
    @JoinColumn(name="IMPUESTOVENTASCOMPRAS_NATD")
    private ConPuc impuestoventascompras_natd;
    @ManyToOne
    @JoinColumn(name="RTEFUENTE_NATC")
    private ConPuc rtefuente_natc;
    @ManyToOne
    @JoinColumn(name="PROVEEDORES_NATC")
    private ConPuc proveedores_natc;
    @ManyToOne
    @JoinColumn(name="PROVEEDORES_NATD")
    private ConPuc proveedores_natd;
    @ManyToOne
    @JoinColumn(name="MONEDANACIONAL_NATC")
    private ConPuc monedanacional_natc;
    @ManyToOne
    @JoinColumn(name="IVARETENIDOCOMPRAS_NATC")
    private ConPuc ivaretenidocompras_natc;
    @ManyToOne
    @JoinColumn(name="ICARETENIDOCOMPRAS_NATC")
    private ConPuc icaretenidocompras_natc;
    @ManyToOne
    @JoinColumn(name="CONPUC_FCREDITOMERCANCIASNOFAB")
    private ConPuc fcreditomercanciasnofab;
   @ManyToOne
    @JoinColumn(name="CONPUC_CAJAGENERAL_NATC")
    private ConPuc cajageneral_natc;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPorcentajertefuente() {
        return porcentajertefuente;
    }

    public void setPorcentajertefuente(float porcentajertefuente) {
        this.porcentajertefuente = porcentajertefuente;
    }

    public float getPorcentajeica() {
        return porcentajeica;
    }

    public void setPorcentajeica(float porcentajeica) {
        this.porcentajeica = porcentajeica;
    }

    public BigDecimal getRteica() {
        return rteica;
    }

    public void setRteica(BigDecimal rteica) {
        this.rteica = rteica;
    }

    public ConPuc getMercanciasnofab_natd() {
        return mercanciasnofab_natd;
    }

    public void setMercanciasnofab_natd(ConPuc mercanciasnofab_natd) {
        this.mercanciasnofab_natd = mercanciasnofab_natd;
    }

    public ConPuc getImpuestoventascompras_natd() {
        return impuestoventascompras_natd;
    }

    public void setImpuestoventascompras_natd(ConPuc impuestoventascompras_natd) {
        this.impuestoventascompras_natd = impuestoventascompras_natd;
    }

    public ConPuc getRtefuente_natc() {
        return rtefuente_natc;
    }

    public void setRtefuente_natc(ConPuc rtefuente_natc) {
        this.rtefuente_natc = rtefuente_natc;
    }

    public ConPuc getProveedores_natc() {
        return proveedores_natc;
    }

    public void setProveedores_natc(ConPuc proveedores_natc) {
        this.proveedores_natc = proveedores_natc;
    }

    public ConPuc getProveedores_natd() {
        return proveedores_natd;
    }

    public void setProveedores_natd(ConPuc proveedores_natd) {
        this.proveedores_natd = proveedores_natd;
    }

    public ConPuc getMonedanacional_natc() {
        return monedanacional_natc;
    }

    public void setMonedanacional_natc(ConPuc monedanacional_natc) {
        this.monedanacional_natc = monedanacional_natc;
    }

    public ConPuc getIvaretenidocompras_natc() {
        return ivaretenidocompras_natc;
    }

    public void setIvaretenidocompras_natc(ConPuc ivaretenidocompras_natc) {
        this.ivaretenidocompras_natc = ivaretenidocompras_natc;
    }

    public ConPuc getIcaretenidocompras_natc() {
        return icaretenidocompras_natc;
    }

    public void setIcaretenidocompras_natc(ConPuc icaretenidocompras_natc) {
        this.icaretenidocompras_natc = icaretenidocompras_natc;
    }

    public ConPuc getFcreditomercanciasnofab() {
        return fcreditomercanciasnofab;
    }

    public void setFcreditomercanciasnofab(ConPuc fcreditomercanciasnofab) {
        this.fcreditomercanciasnofab = fcreditomercanciasnofab;
    }

    public ConPuc getCajageneral_natc() {
        return cajageneral_natc;
    }

    public void setCajageneral_natc(ConPuc cajageneral_natc) {
        this.cajageneral_natc = cajageneral_natc;
    }
    
    
}
