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

public class FacturaItemProveedores
    implements Serializable
{
    private static final long serialVersionUID = 207236100660985541L;
     @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_ITEMPROV",
		     sequenceName="SEQ_ID_ITEMPROV",
		     allocationSize=1
		    )
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_ITEMPROV")
	@Column(name="ID")
    private Long    id;
    private int     position;
    private Producto product;
    private float     quantity;
    private BigDecimal valor_u=BigDecimal.ZERO;
    private BigDecimal valor_total=BigDecimal.ZERO;
    private FacturaProveedores   facturaProveedores;
    FacturaItemProveedores facturaItemProveedoresPadre;
    private float cantidad_unidades;
    @ManyToOne(optional = true, targetEntity = Usuarios.class)
    @JoinColumn(name="USUARIOS_ID")
     private Usuarios usuarios;
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
    @JoinColumn(name="ID_FACPROV")
    public FacturaProveedores getFacturaProveedores() {
        return facturaProveedores;
    }
    public void setFacturaProveedores(FacturaProveedores facturaProveedores) {
        this.facturaProveedores = facturaProveedores;
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
    public void addQuantityValor(float howmany,BigDecimal valor) {
        quantity += howmany;
        valor_total=new BigDecimal(valor_total.toBigInteger().add(valor.toBigInteger()));
    }
    public void updateQuantityValor(float howmany) {
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
	

    @ManyToOne
  @JoinColumn(name = "ID_PADRE")
   

    public FacturaItemProveedores getFacturaItemProveedoresPadre() {
        return facturaItemProveedoresPadre;
    }

    public void setFacturaItemProveedoresPadre(FacturaItemProveedores facturaItemProveedoresPadre) {
        this.facturaItemProveedoresPadre = facturaItemProveedoresPadre;
    }
 public float getCantidad_unidades() {
        return cantidad_unidades;
    }
    public void setCantidad_unidades(float cantidad_unidades) {
        this.cantidad_unidades = cantidad_unidades;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
    
	
}