/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;


/**
 *
 * @author karol
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonRootName;
@Entity
@XmlRootElement
public class Factura
    implements Serializable
{
    private static final long serialVersionUID = -5451107485769007079L;
     
    private Long id;
    private Date facturaDate=new Date();
    private Terceros customer;
    private Terceros terceros;
    private Terceros empresa;
    private BigDecimal netAmount = BigDecimal.ZERO;//subtotal
    private BigDecimal totalAmount = BigDecimal.ZERO;//total
    private BigDecimal efectivo = BigDecimal.ZERO;
    private BigDecimal devolver = BigDecimal.ZERO;
     
    private List<FacturaItem> facturaItems = new ArrayList<FacturaItem>();
    private List<Abono> facturaabonos = new ArrayList<Abono>();
    private int mt_NoMesa;
    private Terceros empleado;
     @OneToMany(mappedBy="factura", cascade=CascadeType.ALL)
     public List<Abono> getFacturaabonos() {
		return facturaabonos;
	}
	public void setFacturaabonos(List<Abono> facturaabonos) {
		this.facturaabonos = facturaabonos;
	}

	
     
    
     private BigDecimal iva=BigDecimal.ZERO;
     private BigDecimal descuento=BigDecimal.ZERO;
     private BigDecimal valor_descuento=BigDecimal.ZERO;
     private Long no_factura;
     
     private String forma_pago;
     private BigDecimal valor_abonos = BigDecimal.ZERO;
     private BigDecimal valor_real_venta = BigDecimal.ZERO;
     private boolean credito;
   
     @Column(precision=12,scale=2)  
  public BigDecimal getValor_abonos() {
		return valor_abonos;
	}
	public void setValor_abonos(BigDecimal valorAbonos) {
		valor_abonos = valorAbonos;
	}
	public String getForma_pago() {
		return forma_pago;
	}
	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}
	

	/**
	 * @return the no_factura
	 */
       	@Column(name="NO_FACTURA",unique = true)

	public Long getNo_factura() {
		return no_factura;
	}

	/**
	 * @param no_factura the no_factura to set
	 */
	public void setNo_factura(Long no_factura) {
		this.no_factura = no_factura;
	}

	
	
	/**
	 * @return the iva
	 */
        @Column(name="IVA",nullable=false,precision=12,scale=2)
	public BigDecimal getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	

	@Column(precision=12,scale=2)
	public BigDecimal getDescuento() {
		return descuento;
	}

	/**
	 * @param descuento the descuento to set
	 */
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

@Transient
    public boolean isEmpty() {
        return (facturaItems == null) || (facturaItems.size()==0);
    }
   
    @OneToMany(mappedBy="factura", cascade=CascadeType.ALL)
    public List<FacturaItem> getFacturaItems() {
        return facturaItems;
    }
    public void setFacturaItems(List<FacturaItem> lines) {
        this.facturaItems = lines;
    }

    public void addProduct(Producto product, float quantity, BigDecimal pv,String talla) {
        for (FacturaItem line: facturaItems) {
            if (product.getId().intValue() == line.getProduct().getId().intValue()) {
                line.addQuantity(quantity);
                line.setValor_total(BigDecimal.valueOf(line.getQuantity()).multiply(line.getProduct().getPrecio()));
                line.setValor_total2((BigDecimal.valueOf(line.getQuantity())).multiply(line.getPrecio_compra()));
                
                return;
            }
        }

        FacturaItem line = new FacturaItem();
        line.setProduct(product);
        line.setQuantity(quantity);
        line.setFactura(this);
        line.setValor_total((BigDecimal.valueOf(line.getQuantity())).multiply(line.getProduct().getPrecio()));
        line.setPrecio_compra(pv);
        line.setValor_total2((BigDecimal.valueOf(line.getQuantity())).multiply(line.getPrecio_compra()));
        line.setTalla(talla);
        facturaItems.add(line);
        
    }

    public void removeProduct(Producto product) {
        for (FacturaItem line: facturaItems) {
            if (product.getId() == line.getProduct().getId()) { 
                facturaItems.remove(line);
                return;
            }
        }
    }

    @Column(name="NETAMOUNT",nullable=false,precision=12,scale=2)
    public BigDecimal getNetAmount() {
    	
        return netAmount;
    }
    public void setNetAmount(BigDecimal amount) {
        this.netAmount = amount;
    }


    @Column(name="TOTALAMOUNT",nullable=false,precision=12,scale=2)
    public BigDecimal getTotalAmount() {
    	
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal amount) {
        this.totalAmount = amount;
    }

   
    
    public void calculateTotals() {
        BigDecimal total = BigDecimal.ZERO;
        int index = 1;
        for (FacturaItem line: facturaItems) {
            line.setPosition(index++);
            total = total.add(line.getProduct().getPrecio().multiply(new BigDecimal(line.getQuantity())));
        }  
         total=(((total.subtract(valor_descuento)).divide(BigDecimal.valueOf(1.16),4,RoundingMode.HALF_UP)));
        setNetAmount(round(total));
        iva=round(total.multiply(BigDecimal.valueOf(0.16)));
     
        setTotalAmount(round(total.add(iva)));
    }
    
    public void calculateAbonos() {
        BigDecimal total = BigDecimal.ZERO;
        
        
        for (Abono line: facturaabonos) {
            
            total = total.add(line.getValor());
        }  
        setValor_abonos(total);
       }

    
    

    /**
     * round a positive big decimal to 2 decimal points
     */
    private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }

    

	/**
	 * @return the id
	 */
    @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_FAC",
		     sequenceName="SEQ_ID_FAC",
		     allocationSize=1
		    )
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_FAC")
	@Column(name="ID_FAC")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the facturaDate
	 */
    @Temporal(javax.persistence.TemporalType.DATE)
	public Date getFacturaDate() {
		   
		return facturaDate;
	}

	/**
	 * @param facturaDate the facturaDate to set
	 */
	public void setFacturaDate(Date facturaDate) {
		this.facturaDate = facturaDate;
	}

	/**
	 * @return the usuario
	 */
	    @ManyToOne
	    @JoinColumn(name="ID_USUA")
	   
	public Terceros getTerceros() {
		return terceros;
	}
	public void setTerceros(Terceros terceros) {
		this.terceros = terceros;
	}

	/**
	 * @return the customer
	 */
	 @ManyToOne
	 @JoinColumn(name="ID_CUST")
	public Terceros getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Terceros customer) {
		this.customer = customer;
	}

	
	/**
	 * @return the valor_descuento
	 */
	@Column(precision=12,scale=2)
	public BigDecimal getValor_descuento() {
		//Double d=descuento.doubleValue()/100;
		//valor_descuento=getNetAmount().multiply(BigDecimal.valueOf(d));
		return valor_descuento;
	}

	/**
	 * @param valor_descuento the valor_descuento to set
	 */
	public void setValor_descuento(BigDecimal valor_descuento) {
		this.valor_descuento = valor_descuento;
	}
	@ManyToOne
	@JoinColumn(name="ID_EMPPRE")
	public Terceros getEmpresa(){
		return empresa;
	}

	public void setEmpresa(Terceros empresa){
		this.empresa = empresa;
	}
	public void setEfectivo(BigDecimal efectivo) {
		this.efectivo = efectivo;
	}
        @Column(precision=12,scale=2)
	public BigDecimal getEfectivo() {
		return efectivo;
	}
	public void setDevolver(BigDecimal devolver) {
		this.devolver = devolver;
	}
	
	
        @Column(precision=12,scale=2)
	public BigDecimal getDevolver() {
	devolver=round((getEfectivo().subtract(totalAmount)));
	if(getEfectivo().longValue()==0)devolver=BigDecimal.ZERO;
		return devolver;
	}
	public void setValor_real_venta(BigDecimal valor_real_venta) {
		this.valor_real_venta = valor_real_venta;
	}
	@Column(precision=12,scale=2)
	public BigDecimal getValor_real_venta() {

		return valor_real_venta;
	}
	

    public int getMt_NoMesa() {
        return mt_NoMesa;
    }

    public void setMt_NoMesa(int mt_NoMesa) {
        this.mt_NoMesa = mt_NoMesa;
    }
    @ManyToOne
    @JoinColumn(name="ID_EMP")
    public Terceros getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Terceros empleado) {
        this.empleado = empleado;
    }

    public boolean isCredito() {
        return credito;
    }

    public void setCredito(boolean credito) {
        this.credito = credito;
    }
	
	
	
}