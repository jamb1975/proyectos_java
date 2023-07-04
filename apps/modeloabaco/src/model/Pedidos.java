/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JAMB
 */
@Entity
@XmlRootElement
public class Pedidos implements Serializable {

    @javax.persistence.SequenceGenerator(
            name = "SEQ_ID_PEDIDOS",
            sequenceName = "SEQ_ID_PEDIDOS",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_PEDIDOS")
    @Column(name = "ID")
    private Long id;
    
    private String no_pedido;

    @ManyToOne
    private Personas cliente;

    private int cantidad;
    private int estado;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha=new Date();

    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pedidos", fetch = FetchType.LAZY)
    private List<DetallesPedido> li_detallespedido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo_pedido() {
        return no_pedido;
    }

    public void setNo_pedido(String no_pedido) {
        this.no_pedido = no_pedido;
    }

    public Personas getCliente() {
        return cliente;
    }

    public void setCliente(Personas cliente) {
        this.cliente = cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<DetallesPedido> getLi_detallespedido() {
        return li_detallespedido;
    }

    public void setLi_detallespedido(List<DetallesPedido> li_detallespedido) {
        this.li_detallespedido = li_detallespedido;
    }


}
