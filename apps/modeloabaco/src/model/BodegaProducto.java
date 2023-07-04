/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author adminlinux
 */
@Entity
public class BodegaProducto implements Serializable {
  @javax.persistence.SequenceGenerator(
            name = "SEQ_ID_BODPRO",
            sequenceName = "SEQ_ID_BODPRO",
            allocationSize = 1
    )
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_BODPRO")
    @Column(name = "ID")
    private Long id;  
    @ManyToOne(optional = true, targetEntity = Bodega.class)
    @JoinColumn(name="BODEGA_ID")
    private Bodega bodega;
     @ManyToOne(optional = true, targetEntity = Producto.class)
    @JoinColumn(name="PRODUCTO_ID")
    private Producto producto;
    private float cantidad; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
}
