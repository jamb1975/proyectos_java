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
public class Inf_AtencionAClientes {

  private String no_ident;
  private String nombre;
  private int total_atencion;
  private BigDecimal valor_ventas=BigDecimal.ZERO;

    public String getNo_ident() {
        return no_ident;
    }

    public void setNo_ident(String no_ident) {
        this.no_ident = no_ident;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTotal_atencion() {
        return total_atencion;
    }

    public void setTotal_atencion(int total_atencion) {
        this.total_atencion = total_atencion;
    }

    public BigDecimal getValor_ventas() {
        return valor_ventas;
    }

    public void setValor_ventas(BigDecimal valor_ventas) {
        this.valor_ventas = valor_ventas;
    }
          
    
}
