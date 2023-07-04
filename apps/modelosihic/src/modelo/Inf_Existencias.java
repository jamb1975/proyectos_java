/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karolyani
 */

@XmlRootElement
public class Inf_Existencias {
 
  private String codigo; 
  private String nombre; 
  private float  cantidad_saldo;
  private BigDecimal valor_saldo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCantidad_saldo() {
        return cantidad_saldo;
    }

    public void setCantidad_saldo(float cantidad_saldo) {
        this.cantidad_saldo = cantidad_saldo;
    }

    public BigDecimal getValor_saldo() {
        return valor_saldo;
    }

    public void setValor_saldo(BigDecimal valor_saldo) {
        this.valor_saldo = valor_saldo;
    }
  
  
}
