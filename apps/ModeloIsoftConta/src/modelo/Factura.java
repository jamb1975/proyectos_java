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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Factura    implements Serializable
{
   
    
    @Id 
    @javax.persistence.SequenceGenerator(
            name = "SEQ_ID_FAC",
            sequenceName = "SEQ_ID_FAC",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_FAC")
    @Column(name = "ID")
    private Long id;
    private static final long serialVersionUID = -5451107485769007079L;
    public enum Status {OPEN,CANCELLED,PROCESSING,SHIPPED}
    public static BigDecimal TAX_RATE = new BigDecimal(".0825");
      
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date facturaDate=new Date();
@ManyToOne
    @JoinColumn(name="ID_CUST")
    private Terceros customer;

     @ManyToOne
    @JoinColumn(name="ID_LASTNUMBERNOFACTURA",unique = true)
    private LastNumberNoFactura lastNumberNoFactura;
@Column(name="NETAMOUNT",nullable=false,precision=12,scale=2)
    private BigDecimal netAmount = BigDecimal.ZERO;
@Column(name="TAX",nullable=false,precision=12,scale=2)
    private BigDecimal tax = BigDecimal.ZERO;
@Column(name="TOTALAMOUNT",nullable=false,precision=12,scale=2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal efectivo = BigDecimal.ZERO;
@Transient
    private BigDecimal devolver = BigDecimal.ZERO;
     
    @OneToMany(mappedBy="factura",cascade =CascadeType.MERGE ,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<FacturaItem> facturaItems = new ArrayList<FacturaItem>();
    
    private int mt_NoMesa;
    @ManyToOne
    @JoinColumn(name="ID_EMP")
    private Terceros empleado;
    @Column(name="STATUS")
    private Status status = Status.OPEN;
    @Column(name="TRACKING")
    private String trackingNumber;
    boolean have_iva;
     @Column(precision=12,scale=2)
    private BigDecimal iva=BigDecimal.ZERO;
      @Column(precision=12,scale=2)
    private BigDecimal valor_iva=BigDecimal.ZERO;
    private boolean have_descuento;
    private BigDecimal descuento=BigDecimal.ZERO;
    private BigDecimal valor_descuento=BigDecimal.ZERO;
    @Column(name="NO_FACTURA",unique = true)
    private Long no_factura;
    private String forma_pago;
     @Column(precision=16,scale=2)
    private BigDecimal valor_abonos = BigDecimal.ZERO;
      @Column(precision=16,scale=2)
    private BigDecimal valor_real_venta = BigDecimal.ZERO;
    private Boolean credito;
    private BigDecimal ica=BigDecimal.ZERO;
    private BigDecimal retefuente=BigDecimal.ZERO;
    //datos temporales items de factura
    @Transient
    Long id_fi_temp;
    @Transient
    float quantity;
    private boolean deleted;
    @Transient
    private boolean showiva;
    @ManyToOne(optional = true, targetEntity = Usuarios.class)
     @JoinColumn(name="USUARIOS_ID")
     private Usuarios usuarios;
 
  

    @Column(precision=16,scale=2) 
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
	 * @return the have_iva
	 */
	public boolean isHave_iva() {
		return have_iva;
	}

	/**
	 * @param have_iva the have_iva to set
	 */
	public void setHave_iva(boolean have_iva) {
		this.have_iva = have_iva;
	}

	/**
	 * @return the iva
	 */
       
	public BigDecimal getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	/**
	 * @return the have_descuento
	 */
	public boolean isHave_descuento() {
		return have_descuento;
	}

	/**
	 * @param have_descuento the have_descuento to set
	 */
	public void setHave_descuento(boolean have_descuento) {
		this.have_descuento = have_descuento;
	}

	/**
	 * @return the descuento
	 */
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
   
    public List<FacturaItem> getFacturaItems() {
        return facturaItems;
    }
    public void setFacturaItems(List<FacturaItem> lines) {
        this.facturaItems = lines;
    }

    public void addProduct(Producto product, float quantity, BigDecimal pv,BigDecimal descuento) {
        id_fi_temp=product.getId();
        this.quantity=quantity;
        for (FacturaItem line: facturaItems) {
            if (product.getId().intValue() == line.getProduct().getId().intValue()) {
                line.addQuantity(quantity);
                line.setValor_total(new BigDecimal(BigDecimal.valueOf(line.getQuantity()).multiply(line.getProduct().getPrecio()).subtract(line.getDescuento()).longValue()));
                line.setValor_total2(new BigDecimal((BigDecimal.valueOf(line.getQuantity())).multiply(line.getPrecio_compra()).longValue()));
                calculateTotals();
                return;
            }
        }

        FacturaItem line = new FacturaItem();
        line.setProduct(product);
        line.setQuantity(quantity);
        line.setDescuento(descuento);
        line.setFactura(this);
        line.setValor_total(new BigDecimal((BigDecimal.valueOf(line.getQuantity())).multiply(line.getProduct().getPrecio()).subtract(line.getDescuento()).longValue()));
        line.setPrecio_compra(pv);
        line.setValor_total2(new BigDecimal(BigDecimal.valueOf(line.getQuantity()).multiply(line.getPrecio_compra()).longValue()));
        
        facturaItems.add(line);
        calculateTotals();
    }
    public void modifyItem(Producto product, float quantity) {
    id_fi_temp=product.getId();
    
        for (FacturaItem line: facturaItems) 
        {
            if (product.getId().intValue() == line.getProduct().getId().intValue()) 
            {
                this.quantity=quantity-line.getQuantity();
                line.updateQuantity(quantity);
                line.setValor_total(BigDecimal.valueOf(line.getQuantity()).multiply(line.getProduct().getPrecio()));
                line.setValor_total2((BigDecimal.valueOf(line.getQuantity())).multiply(line.getPrecio_compra()));
                calculateTotals();
               
                return;
            }
        }
}


public void removeProduct(Producto product) {
        for (FacturaItem line: facturaItems) {
            if (product.getId() == line.getProduct().getId()) { 
                facturaItems.remove(line);
                id_fi_temp=product.getId();
                this.quantity=line.getQuantity()*(-1);
                 calculateTotals();
                return;
            }
        }
    }

@Column(precision=16,scale=2)
    public BigDecimal getNetAmount() {
    	
        return netAmount;
    }
    public void setNetAmount(BigDecimal amount) {
        this.netAmount = amount;
    }

    public BigDecimal getTax() {
        return tax;
    }
    public void setTax(BigDecimal amount) {
        this.tax = amount;
    }
    @Column(precision=16,scale=2)
    public BigDecimal getTotalAmount() {
    	
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal amount) {
        this.totalAmount = amount;
    }

    public String getTrackingNumber() { 
       return trackingNumber;
    }
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @Transient
    public int getStatusCode() {
        return status.ordinal();
    }

    public void calculateTotals() {
        BigDecimal total = BigDecimal.ZERO;
        int index = 1;
        double iva_prod=0;
        iva=BigDecimal.ZERO;
                
        for (FacturaItem line: facturaItems) {
            line.setPosition(index++);
             iva_prod=(line.getProduct().getIva()*0.01);
            if(line.getProduct().getIva()>0)
            {
                BigDecimal _iva=(new BigDecimal(iva_prod).add(BigDecimal.valueOf(1)));
             total=(total.add(new BigDecimal((line.getValor_total().subtract(valor_descuento)).divide(_iva,4,RoundingMode.HALF_UP).longValue())));
             iva=round(iva.add(new BigDecimal((line.getValor_total().subtract(valor_descuento)).divide(_iva,4,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(iva_prod)).longValue())));
            }
            else
            {
                total = total.add(BigDecimal.valueOf(line.getValor_total().longValue()));
            }
            
            
        }  
         setNetAmount(round(total));
         setTotalAmount(new BigDecimal(round(total.add(iva).add(valor_descuento).subtract(descuento)).longValue()));
    }
    public void calculateTotals2(BigDecimal vd) {
        BigDecimal total = BigDecimal.ZERO;
        valor_descuento=vd;
        int index = 1;
        for (FacturaItem line: facturaItems) {
            line.setPosition(index++);
            total = total.add(line.getProduct().getPrecio().multiply(new BigDecimal(line.getQuantity())));
        }  
         total=(((total.subtract(valor_descuento)).divide(BigDecimal.valueOf(1.16),4,RoundingMode.HALF_UP)));
        setNetAmount(round(total));
        iva=round(total.multiply(BigDecimal.valueOf(0.16)));
        setTotalAmount(round((total.add(iva)).add(valor_descuento)));
    }
  

    public void cancel() {
        setStatus(Status.CANCELLED);
    }

    public void process() {
        setStatus(Status.PROCESSING);
    }

    public void ship(String tracking) {
        setStatus(Status.SHIPPED);
        setTrackingNumber(tracking);
    }
    

    /**
     * round a positive big decimal to 2 decimal points
     */
    private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }

    @Transient
    public boolean isOpen() {
       return status == Status.OPEN;
    }

	/**
	 * @return the id
	 */
   
	/**
	 * @return the facturaDate
	 */
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
	 * @return the valor_iva
	 */
        @Column(precision=12,scale=2)
	public BigDecimal getValor_iva() {
		Double d=Double.valueOf(16)/100;
		valor_iva=getNetAmount().multiply(BigDecimal.valueOf(d));
		return valor_iva;
	}

	/**
	 * @param valor_iva the valor_iva to set
	 */
	public void setValor_iva(BigDecimal valor_iva) {
		this.valor_iva = valor_iva;
	}

	/**
	 * @return the valor_descuento
	 */
	
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
	
	public void setEfectivo(BigDecimal efectivo) {
		this.efectivo = efectivo;
	}
	public BigDecimal getEfectivo() {
		return efectivo;
	}
	public void setDevolver(BigDecimal devolver) {
		this.devolver = devolver;
	}
	
	public BigDecimal getDevolver() {
	devolver=(getEfectivo().subtract(totalAmount)).add(valor_descuento);
	if(getEfectivo().longValue()==0)devolver=BigDecimal.ZERO;
		return devolver;
	}
	public void setValor_real_venta(BigDecimal valor_real_venta) {
		this.valor_real_venta = valor_real_venta;
	}
	
	public BigDecimal getValor_real_venta() {

		return valor_real_venta;
	}
	public void setCredito(Boolean credito) {
		this.credito = credito;
	}
	public Boolean getCredito() {
		return credito;
	}

    public int getMt_NoMesa() {
        return mt_NoMesa;
    }

    public void setMt_NoMesa(int mt_NoMesa) {
        this.mt_NoMesa = mt_NoMesa;
    }
   
    public LastNumberNoFactura getLastNumberNoFactura() {
        return lastNumberNoFactura;
    }

    public void setLastNumberNoFactura(LastNumberNoFactura lastNumberNoFactura) {
        this.lastNumberNoFactura = lastNumberNoFactura;
    }

    public Long getId_fi_temp() {
        return id_fi_temp;
    }

    public void setId_fi_temp(Long id_fi_temp) {
        this.id_fi_temp = id_fi_temp;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public boolean isShowiva() {
        return showiva;
    }

    public void setShowiva(boolean showiva) {
        this.showiva = showiva;
    }

    public static BigDecimal getTAX_RATE() {
        return TAX_RATE;
    }

    public static void setTAX_RATE(BigDecimal TAX_RATE) {
        Factura.TAX_RATE = TAX_RATE;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    

   

    public BigDecimal getIca() {
        return ica;
    }

    public void setIca(BigDecimal ica) {
        this.ica = ica;
    }

    public BigDecimal getRetefuente() {
        return retefuente;
    }

    public void setRetefuente(BigDecimal retefuente) {
        this.retefuente = retefuente;
    }
	
   
     public String getNofacturacerosizquierda() {
        
     String cerosizq="";
     String nofacturacerosizquierda="";
     Long nofactura=no_factura;
     
    
     for(int i=4;i>nofactura.toString().length();i--)
     {
         cerosizq=cerosizq+"0";
     }
      nofacturacerosizquierda=cerosizq+nofactura.toString();
        return nofacturacerosizquierda;
    }      

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Terceros getCustomer() {
        return customer;
    }

    public void setCustomer(Terceros customer) {
        this.customer = customer;
    }

    public Terceros getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Terceros empleado) {
        this.empleado = empleado;
    }
    	

 

    
}