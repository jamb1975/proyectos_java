package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class DetallesPedido implements Serializable {

    @javax.persistence.SequenceGenerator(
            name = "SEQ_ID_DETALLESPEDIDO",
            sequenceName = "SEQ_ID_DETALLESPEDIDO",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_DETALLESPEDIDO")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    private Pedidos pedidos;
    @ManyToOne
    private Producto producto;

    private int cantidad;

    private BigDecimal valor_uni = BigDecimal.ZERO;
    private BigDecimal valor_total = BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedidos getPedidos() {
        return pedidos;
    }

    public void setPedidos(Pedidos pedidos) {
        this.pedidos = pedidos;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValor_uni() {
        return valor_uni;
    }

    public void setValor_uni(BigDecimal valor_uni) {
        this.valor_uni = valor_uni;
    }

    public BigDecimal getValor_total() {
        return valor_total;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }

}
