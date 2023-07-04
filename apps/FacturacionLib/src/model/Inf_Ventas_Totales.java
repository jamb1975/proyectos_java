/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SIMboxDEV8
 */

@XmlRootElement
public class Inf_Ventas_Totales  {
  private boolean credito;
  private String nombre;
  private float cant_total;
  private BigDecimal valor_total=BigDecimal.ZERO;

    public boolean isCredito() {
        return credito;
    }

    public void setCredito(boolean credito) {
        this.credito = credito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    

    public BigDecimal getValor_total() {
        return valor_total;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }

    public float getCant_total() {
        return cant_total;
    }

    public void setCant_total(float cant_total) {
        this.cant_total = cant_total;
    }
    
    
}
