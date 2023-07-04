/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author karolyani
 */


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "KARDEX")
public class Kardex implements Serializable
{
    @Column(name = "ID", table = "KARDEX", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_KARDEX_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_KARDEX_ID", sequenceName = "SEQ_KARDEX_ID", allocationSize = 1, initialValue = 1)
    private Long    id;
  @ManyToOne
    @JoinColumn(name="FACTURAITEM_ID")
    private FacturaItem   facturaItem;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
     private Date           fecha=new Date();
     @Temporal(TemporalType.TIMESTAMP)
    @Basic
     private Date           fechavencimiento=new Date();
     private float            cantidad_entrada;
     private BigDecimal     valor_entrada=BigDecimal.ZERO;
     private float          cantidad_salida;
     private BigDecimal     valor_salida=BigDecimal.ZERO;
     private float          cantidad_saldo;
     private BigDecimal     valor_saldo=BigDecimal.ZERO;
     private BigDecimal     valor_unidad=BigDecimal.ZERO;
     @Column(length = 50)
     private String lote;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCTO_ID")
     private Producto       producto;
     private String desc_kar;
     private Long no_fact_kar;
     private String tipo;
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
	 * @return the factura_item
	 */
    
	
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    //Relacion Hijo(Inventario) a Padre(PRODUCT) 
   
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
    @Column(precision=16,scale=2)
    public BigDecimal getValor_entrada() {
        return round(valor_entrada);
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
@Column(precision=16,scale=2)
    public BigDecimal getValor_salida() {
        return round(valor_salida);
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
@Column(precision=16,scale=2)
    public BigDecimal getValor_saldo() {
        return round(valor_saldo);
    }

    public void setValor_saldo(BigDecimal valor_saldo) {
        this.valor_saldo = valor_saldo;
    }
@Column(precision=16,scale=2)
    public BigDecimal getValor_unidad() {
        return round(valor_unidad);
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

    public FacturaItem getFacturaItem() {
        return facturaItem;
    }

    public void setFacturaItem(FacturaItem facturaItem) {
        this.facturaItem = facturaItem;
    }

    public Date getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(Date fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

   
	
 private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }	

}
