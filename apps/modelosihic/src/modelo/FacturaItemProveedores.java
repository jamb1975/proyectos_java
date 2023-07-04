/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author karol
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@XmlRootElement
@Table(name = "FACTURAITEMPROVEEDORES")
public class FacturaItemProveedores
    implements Serializable
{
    private static final long serialVersionUID = 207236100660985541L;
   @Column(name = "ID", table = "FACTURAITEMPROVEEDORES", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_FACTURAITEMPROVEEDORES_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_FACTURAITEMPROVEEDORES_ID", sequenceName = "SEQ_FACTURAITEMPROVEEDORES_ID", allocationSize = 1, initialValue = 1)
    private Long    id;
    private int     position;
     @ManyToOne(optional = true, targetEntity = Producto.class)
     @JoinColumn(name="PRODUCTO_ID")
    private Producto producto;
    private float     quantity;
    private BigDecimal valor_u=BigDecimal.ZERO;
    private BigDecimal valor_total=BigDecimal.ZERO;
     @ManyToOne
    @JoinColumn(name="FACTURAPROVEEDORES_ID")
    private FacturaProveedores   facturaProveedores;
     @ManyToOne
  @JoinColumn(name = "FACTURAITEMPROVEEDORES_PADRE_ID")
    FacturaItemProveedores facturaItemProveedoresPadre;
    private float cantidad_unidades;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;
   @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;
     @Column(name = "USUARIO_CREADOR",length = 50)
    @Basic
    private String usuarioCreador;
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
    
   
    public FacturaProveedores getFacturaProveedores() {
        return facturaProveedores;
    }
    public void setFacturaProveedores(FacturaProveedores facturaProveedores) {
        this.facturaProveedores = facturaProveedores;
    }

   
	 public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto product) {
        this.producto=product;
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
        valor_total=valor_total.add(valor);
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

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
    
	
}