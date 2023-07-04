/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;


/**
 *
 * @author karol
 */

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@XmlRootElement

public class FacturaItem
    implements Serializable
{
    private static final long serialVersionUID = 207236100660985541L;

      
      @Id 
       @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_ITEM",
		     sequenceName="SEQ_ID_ITEM",
		     allocationSize=1
		    )
      @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_ITEM")
     
      @Column(name="ID")
    private Long    id;
    private int     position;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Producto product;
    private float     quantity;
    private BigDecimal valor_u=BigDecimal.ZERO;
    private BigDecimal valor_total=BigDecimal.ZERO;
    private BigDecimal precio_compra=BigDecimal.ZERO;
    private BigDecimal valor_total2=BigDecimal.ZERO;
    private BigDecimal descuento=BigDecimal.ZERO;
    private String talla;
    private Factura   factura;
    @ManyToOne(optional = true, targetEntity = Usuarios.class)
    @JoinColumn(name="USUARIOS_ID")
     private Usuarios usuarios;

    public BigDecimal getPrecio_compra() {
		return precio_compra;
	}
	public void setPrecio_compra(BigDecimal precioCompra) {
		precio_compra = precioCompra;
	}
	public BigDecimal getValor_total2() {
		return valor_total2;
	}
	public void setValor_total2(BigDecimal valorTotal2) {
		valor_total2 = valorTotal2;
	}
	/**
	 * @return the id
	 */
   
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

    @Column(name="POS")
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    
    @ManyToOne
    @JoinColumn(name="ID_FAC")
    public Factura getFactura() {
        return factura;
    }
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @ManyToOne
	 @JoinColumn(name="ID_PROD")
	 public Producto getProduct() {
        return product;
    }
    public void setProduct(Producto product) {
        this.product=product;
    }

    @Column(name="QUANTITY",nullable=false)
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
    public void addQuantity(float howmany) {
        quantity += howmany;
    }
    public void updateQuantity(float howmany) {
        quantity = howmany;
    }
	/**
	 * @return the valor_total
	 */
   
	public BigDecimal getValor_total() {
    	
		return valor_total;
	}
	/**
	 * @param valor_total the valor_total to set
	 */
	public void setValor_total(BigDecimal valor_total) {
		this.valor_total = valor_total;
	}
	public BigDecimal getValor_u() {
		return valor_u;
	}
	public void setValor_u(BigDecimal valor_u) {
		this.valor_u = valor_u;
	}
	public void setTalla(String talla) {
		this.talla = talla;
	}
	public String getTalla() {
		return talla;
	}

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
 
	
}