/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author karol
 */
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement

public class Producto implements Serializable {
    @javax.persistence.SequenceGenerator(
            name = "SEQ_ID_PROD",
            sequenceName = "SEQ_ID_PROD",
            allocationSize = 1
    )
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_PROD")
    @Column(name = "ID_PROD")
    private Long id;
    @Column(unique = true)
    private String codigo;
    private int codigo2 = 0;
    private BigDecimal costo = BigDecimal.ZERO;
    private BigDecimal precio = BigDecimal.ZERO;
    private String nombre;
    private String descrip;
    private String tipo;
    private String estado;
    private boolean esmateriaprima;

    //private List<ProductoMateriaPrima> materiaprimaItems = new ArrayList<ProductoMateriaPrima>();
   
    @ManyToOne
    @JoinColumn(name = "ID_CAT")
     private Categoria categoria;
@Transient
    private int m_iCantidad;
@Lob
    private byte[]img;
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the no_hab
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the usuario
     */
    public int getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(int codigo2) {
        this.codigo2 = codigo2;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public int getM_iCantidad() {
        return m_iCantidad;
    }

    public void setM_iCantidad(int m_iCantidad) {
        this.m_iCantidad = m_iCantidad;
    }
    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public boolean isEsmateriaprima() {
        return esmateriaprima;
    }

    public void setEsmateriaprima(boolean esmateriaprima) {
        this.esmateriaprima = esmateriaprima;
    }
/*@OneToMany(mappedBy="producto", cascade=CascadeType.ALL)
   public List<ProductoMateriaPrima> getMateriaprimaItems() {
        return materiaprimaItems;
    }

    public void setMateriaprimaItems(List<ProductoMateriaPrima> materiaprimaItems) {
        this.materiaprimaItems = materiaprimaItems;
    }

    //agregamos materia prima
    public void addMateriaPrima(Producto materiaprima, float cantidad_gastada,int unidad_medida ) {
        for (ProductoMateriaPrima line: materiaprimaItems) {
            if (materiaprima.getId().intValue() == line.getMateriaprima().getId().intValue()) {
                 
                return;
            }
        }

        ProductoMateriaPrima line = new ProductoMateriaPrima();
        line.setMateriaprima(materiaprima);
        line.setCantidad_necesaria(cantidad_gastada);
        line.setUnidad_medida(unidad_medida);
        materiaprimaItems.add(line);
        
    }

 */
    

   
 

    
}
