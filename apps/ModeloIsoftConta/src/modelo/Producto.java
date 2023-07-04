/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author karol
 */
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
    @Column(name = "ID")
    private Long id;
   
    @Column(unique = true,length = 20)
    private String codigo;
    private int codigo2 = 0;
     @Column(precision=12,scale=2)
    private BigDecimal costo = BigDecimal.ZERO;
    @Column(precision=12,scale=2)
    private BigDecimal precio = BigDecimal.ZERO;
    private String nombre;
    private String descrip;
    private String tipo;
    private String estado;
    private boolean esmateriaprima;
    private String codigosrelacionados;
    private int topminimo;
    private int topmaximo;
    @Column(unique = true)
    private String codigobarras;    
    private int iva;
    private int ude;
    private String referencia;
    private String modoconsumo;
    @ManyToOne(optional = true, targetEntity = Usuarios.class)
    @JoinColumn(name="USUARIOS_ID")
    private Usuarios usuarios;
    @ManyToOne(optional = true, targetEntity = Terceros.class)
    @JoinColumn(name="PROVEEDORES_ID")
    private Terceros proveedores;
    
    @Transient
    private int m_iCantidad;
    @Lob
    private byte[]img;
    private String observaciones;
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
@Column(precision=12,scale=2)
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
@Column(precision=12,scale=2)
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

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public String getCodigosrelacionados() {
        return codigosrelacionados;
    }

    public void setCodigosrelacionados(String codigosrelacionados) {
        this.codigosrelacionados = codigosrelacionados;
    }

    public int getUde() {
        return ude;
    }

    public void setUde(int ude) {
        this.ude = ude;
    }

   

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getTopminimo() {
        return topminimo;
    }

    public void setTopminimo(int topminimo) {
        this.topminimo = topminimo;
    }

    public int getTopmaximo() {
        return topmaximo;
    }

    public void setTopmaximo(int topmaximo) {
        this.topmaximo = topmaximo;
    }

    public String getCodigobarras() {
        return codigobarras;
    }

    public void setCodigobarras(String codigobarras) {
        this.codigobarras = codigobarras;
    }

    public String getModoconsumo() {
        return modoconsumo;
    }

    public void setModoconsumo(String modoconsumo) {
        this.modoconsumo = modoconsumo;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Terceros getProveedores() {
        return proveedores;
    }

    public void setProveedores(Terceros proveedores) {
        this.proveedores = proveedores;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
  
}
