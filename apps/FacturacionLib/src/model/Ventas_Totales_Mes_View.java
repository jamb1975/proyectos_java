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
 * @author karol
 */

@Entity
@XmlRootElement
public class Ventas_Totales_Mes_View implements Serializable {
    private String codigo;
    private String nombre;
    private int numero_mes;
    private String mes_completo;
    private BigDecimal cantidad_total_producto=BigDecimal.ZERO;
    private BigDecimal valor_total_producto=BigDecimal.ZERO;
    @Id
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

    public int getNumero_mes() {
        return numero_mes;
    }

    public void setNumero_mes(int numero_mes) {
        this.numero_mes = numero_mes;
    }

    public String getMes_completo() {
        return mes_completo;
    }

    public void setMes_completo(String mes_completo) {
        this.mes_completo = mes_completo;
    }

    public BigDecimal getCantidad_total_producto() {
        return cantidad_total_producto;
    }

    public void setCantidad_total_producto(BigDecimal cantidad_total_producto) {
        this.cantidad_total_producto = cantidad_total_producto;
    }

    public BigDecimal getValor_total_producto() {
        return valor_total_producto;
    }

    public void setValor_total_producto(BigDecimal valor_total_producto) {
        this.valor_total_producto = valor_total_producto;
    }
    
}
