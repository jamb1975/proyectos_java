/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karolyani
 */
@Entity
@XmlRootElement
public class ProductoMateriaPrima implements Serializable {
     @javax.persistence.SequenceGenerator(
            name = "SEQ_ID_PRODMP",
            sequenceName = "SEQ_ID_PRODMP",
            allocationSize = 1
    )
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_PRODMP")
    @Column(name = "ID")
    private Long id;
    private Producto producto;
    private Producto materiaprima;
    private float cantidad_necesaria;
    private int unidad_medida;
    @ManyToOne(optional = true, targetEntity = Usuarios.class)
    @JoinColumn(name="USUARIOS_ID")
    private Usuarios usuarios;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ManyToOne
    @JoinColumn(name = "id_producto")
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @ManyToOne
    @JoinColumn(name = "id_materiaprima")
    public Producto getMateriaprima() {
        return materiaprima;
    }

    public void setMateriaprima(Producto materiaprima) {
        this.materiaprima = materiaprima;
    }

    public float getCantidad_necesaria() {
        return cantidad_necesaria;
    }

    public void setCantidad_necesaria(float cantidad_necesaria) {
        this.cantidad_necesaria = cantidad_necesaria;
    }

    public int getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(int unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
  
    
   

    
}
