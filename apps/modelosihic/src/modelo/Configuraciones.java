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
    private float porcentajeivacompras=0;
    private BigDecimal rteica=BigDecimal.ZERO;
    private int tipo_compraregimenes;
    private int tipo_ventaregimenes;
    private int condicion_uvt_reteiva_servicios;
    private int condicion_uvt_reteiva_compras;
    
    private BigDecimal uvt;
    @ManyToOne
    @JoinColumn(name="MERCANCIASNOFAB_NATD")
    private Puc mercanciasnofab_natd;
    @ManyToOne
    @JoinColumn(name="IMPUESTOVENTASCOMPRAS_NATD")
    private Puc impuestoventascompras_natd;
    @ManyToOne
    @JoinColumn(name="RTEFUENTE_NATC")
    private Puc rtefuente_natc;
    @ManyToOne
    @JoinColumn(name="PROVEEDORES_NATC")
    private Puc proveedores_natc;
    @ManyToOne
    @JoinColumn(name="PROVEEDORES_NATD")
    private Puc proveedores_natd;
    @ManyToOne
    @JoinColumn(name="MONEDANACIONAL_NATC")
    private Puc monedanacional_natc;
    @ManyToOne
    @JoinColumn(name="IVARETENIDOCOMPRAS_NATC")
    private Puc ivaretenidocompras_natc;
    @ManyToOne
    @JoinColumn(name="ICARETENIDOCOMPRAS_NATC")
    private Puc icaretenidocompras_natc;
    @ManyToOne
    @JoinColumn(name="CONPUC_FCREDITOMERCANCIASNOFAB")
    private Puc fcreditomercanciasnofab;
   @ManyToOne
    @JoinColumn(name="CONPUC_CAJAGENERAL_NATC")
    private Puc cajageneral_natc;
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

    public Puc getMercanciasnofab_natd() {
        return mercanciasnofab_natd;
    }

    public void setMercanciasnofab_natd(Puc mercanciasnofab_natd) {
        this.mercanciasnofab_natd = mercanciasnofab_natd;
    }

    public Puc getImpuestoventascompras_natd() {
        return impuestoventascompras_natd;
    }

    public void setImpuestoventascompras_natd(Puc impuestoventascompras_natd) {
        this.impuestoventascompras_natd = impuestoventascompras_natd;
    }

    public Puc getRtefuente_natc() {
        return rtefuente_natc;
    }

    public void setRtefuente_natc(Puc rtefuente_natc) {
        this.rtefuente_natc = rtefuente_natc;
    }

    public Puc getProveedores_natc() {
        return proveedores_natc;
    }

    public void setProveedores_natc(Puc proveedores_natc) {
        this.proveedores_natc = proveedores_natc;
    }

    public Puc getProveedores_natd() {
        return proveedores_natd;
    }

    public void setProveedores_natd(Puc proveedores_natd) {
        this.proveedores_natd = proveedores_natd;
    }

    public Puc getMonedanacional_natc() {
        return monedanacional_natc;
    }

    public void setMonedanacional_natc(Puc monedanacional_natc) {
        this.monedanacional_natc = monedanacional_natc;
    }

    public Puc getIvaretenidocompras_natc() {
        return ivaretenidocompras_natc;
    }

    public void setIvaretenidocompras_natc(Puc ivaretenidocompras_natc) {
        this.ivaretenidocompras_natc = ivaretenidocompras_natc;
    }

    public Puc getIcaretenidocompras_natc() {
        return icaretenidocompras_natc;
    }

    public void setIcaretenidocompras_natc(Puc icaretenidocompras_natc) {
        this.icaretenidocompras_natc = icaretenidocompras_natc;
    }

    public Puc getFcreditomercanciasnofab() {
        return fcreditomercanciasnofab;
    }

    public void setFcreditomercanciasnofab(Puc fcreditomercanciasnofab) {
        this.fcreditomercanciasnofab = fcreditomercanciasnofab;
    }

    public Puc getCajageneral_natc() {
        return cajageneral_natc;
    }

    public void setCajageneral_natc(Puc cajageneral_natc) {
        this.cajageneral_natc = cajageneral_natc;
    }

    public int getTipo_compraregimenes() {
        return tipo_compraregimenes;
    }

    public void setTipo_compraregimenes(int tipo_compraregimenes) {
        this.tipo_compraregimenes = tipo_compraregimenes;
    }

    public int getTipo_ventaregimenes() {
        return tipo_ventaregimenes;
    }

    public void setTipo_ventaregimenes(int tipo_ventaregimenes) {
        this.tipo_ventaregimenes = tipo_ventaregimenes;
    }

    public float getPorcentajeivacompras() {
        return porcentajeivacompras;
    }

    public void setPorcentajeivacompras(float porcentajeivacompras) {
        this.porcentajeivacompras = porcentajeivacompras;
    }

    public int getCondicion_uvt_reteiva_servicios() {
        return condicion_uvt_reteiva_servicios;
    }

    public void setCondicion_uvt_reteiva_servicios(int condicion_uvt_reteiva_servicios) {
        this.condicion_uvt_reteiva_servicios = condicion_uvt_reteiva_servicios;
    }

    public int getCondicion_uvt_reteiva_compras() {
        return condicion_uvt_reteiva_compras;
    }

    public void setCondicion_uvt_reteiva_compras(int condicion_uvt_reteiva_compras) {
        this.condicion_uvt_reteiva_compras = condicion_uvt_reteiva_compras;
    }

    public BigDecimal getUvt() {
        return uvt;
    }

    public void setUvt(BigDecimal uvt) {
        this.uvt = uvt;
    }
    
    
}
