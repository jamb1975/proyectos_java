/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karolyani
 */

@XmlRootElement
public class Inf_VentasPorProducto {
  Long id;
  String nombre;
  float cantidad_credito;
  float cantidad_contado;
  BigDecimal valor_credito=BigDecimal.ZERO;
  BigDecimal valor_contado=BigDecimal.ZERO;
  float cantidad_total;
  BigDecimal valor_total=BigDecimal.ZERO; 
  BigDecimal valor_unitario=BigDecimal.ZERO;
  boolean credito=false;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCantidad_credito() {
        return cantidad_credito;
    }

    public void setCantidad_credito(float cantidad_credito) {
        this.cantidad_credito = cantidad_credito;
    }

    public float getCantidad_contado() {
        return cantidad_contado;
    }

    public void setCantidad_contado(float cantidad_contado) {
        this.cantidad_contado = cantidad_contado;
    }

    public BigDecimal getValor_credito() {
        return valor_credito;
    }

    public void setValor_credito(BigDecimal valor_credito) {
        this.valor_credito = valor_credito;
    }

    public BigDecimal getValor_contado() {
        return valor_contado;
    }

    public void setValor_contado(BigDecimal valor_contado) {
        this.valor_contado = valor_contado;
    }

    public float getCantidad_total() {
        return cantidad_total;
    }

    public void setCantidad_total(float cantidad_total) {
        this.cantidad_total = cantidad_total;
    }

    public BigDecimal getValor_total() {
        return valor_total;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }

    public boolean isCredito() {
        return credito;
    }

    public void setCredito(boolean credito) {
        this.credito = credito;
    }

    public BigDecimal getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(BigDecimal valor_unitario) {
        this.valor_unitario = valor_unitario;
    }
    
        
}
