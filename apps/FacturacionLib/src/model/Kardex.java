/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author karolyani
 */


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@XmlRootElement
public class Kardex implements Serializable
{
    
     private Date           fecha=new Date();
     private float            cantidad_entrada;
     private BigDecimal     valor_entrada=BigDecimal.ZERO;
     private float          cantidad_salida;
     private BigDecimal     valor_salida=BigDecimal.ZERO;
     private float          cantidad_saldo;
     private BigDecimal     valor_saldo=BigDecimal.ZERO;
     private BigDecimal     valor_unidad=BigDecimal.ZERO;
     private Producto       producto;
     private String desc_kar;
     private Long no_fact_kar;
     private Long    id;
     private String tipo;
    
    /**
	 * @return the factura_item
	 */
     private Terceros terceros;
     private Terceros empresa;
    
     @ManyToOne
     @JoinColumn(name="ID_EMP")
 	public Terceros getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Terceros empresa) {
		this.empresa = empresa;
	}
	@ManyToOne
 	@JoinColumn(name = "ID_USUA")
 	public Terceros getTerceros() {
		return terceros;
	}
	public void setTerceros(Terceros terceros) {
		this.terceros = terceros;
	}

	@javax.persistence.SequenceGenerator(
		     name="SEQ_ID_KAR",
		     sequenceName="SEQ_ID_KAR",
		     allocationSize=1
		    )
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_KAR")
	@Column(name="ID")
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    //Relacion Hijo(Inventario) a Padre(PRODUCT) 
    @ManyToOne
    @JoinColumn(name="ID_PROD")
    	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public String getDesc_kar() {
		return desc_kar;
	}
	public void setDesc_kar(String desc_kar) {
		this.desc_kar = desc_kar;
	}
	public Long getNo_fact_kar() {
		return no_fact_kar;
	}
	public void setNo_fact_kar(Long no_fact_kar) {
		this.no_fact_kar = no_fact_kar;
	}
     @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
 @Column(precision=12,scale=2)
    public float getCantidad_entrada() {
        return cantidad_entrada;
    }

    public void setCantidad_entrada(float cantidad_entrada) {
        this.cantidad_entrada = cantidad_entrada;
    }
    @Column(precision=12,scale=2)
    public BigDecimal getValor_entrada() {
        return valor_entrada;
    }

    public void setValor_entrada(BigDecimal valor_entrada) {
        this.valor_entrada = valor_entrada;
    }
@Column(precision=12,scale=2)
    public float getCantidad_salida() {
        return cantidad_salida;
    }

    public void setCantidad_salida(float cantidad_salida) {
        this.cantidad_salida = cantidad_salida;
    }
@Column(precision=12,scale=2)
    public BigDecimal getValor_salida() {
        return valor_salida;
    }

    public void setValor_salida(BigDecimal valor_salida) {
        this.valor_salida = valor_salida;
    }
@Column(precision=12,scale=2)
    public float getCantidad_saldo() {
        return cantidad_saldo;
    }

    public void setCantidad_saldo(float cantidad_saldo) {
        this.cantidad_saldo = cantidad_saldo;
    }
@Column(precision=12,scale=2)
    public BigDecimal getValor_saldo() {
        return valor_saldo;
    }

    public void setValor_saldo(BigDecimal valor_saldo) {
        this.valor_saldo = valor_saldo;
    }
@Column(precision=12,scale=2)
    public BigDecimal getValor_unidad() {
        return valor_unidad;
    }

    public void setValor_unidad(BigDecimal valor_unidad) {
        this.valor_unidad = valor_unidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
	
	

}
