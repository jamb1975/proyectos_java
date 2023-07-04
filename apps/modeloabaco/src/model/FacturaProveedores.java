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
public class FacturaProveedores    implements Serializable
{
    @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_FACPROV",
		     sequenceName="SEQ_ID_FACPROV",
		     allocationSize=1
		    )
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_FACPROV")
    @Column(name="ID")
    private Long id;
    private static final long serialVersionUID = -5451107485769007079L;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date facturaDate=new Date();
    @ManyToOne
    @JoinColumn(name="ID_CUST")
    private Proveedores proveedores;
    @Column(name="TOTALAMOUNT",nullable=false,precision=16,scale=2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal efectivo = BigDecimal.ZERO;
    @Transient
    private BigDecimal devolver = BigDecimal.ZERO;
    @OneToMany(mappedBy="facturaProveedores", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<FacturaItemProveedores> facturaItemsProveedores = new ArrayList<FacturaItemProveedores>();
     @OneToMany(mappedBy="facturaproveedores", cascade=CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ConComprobanteEgreso> comprobanteegresos = new ArrayList<ConComprobanteEgreso>();
   
@Column(name="NO_FACTURA")
    private Long no_factura;
    private String forma_pago;
    private BigDecimal valor_abonos = BigDecimal.ZERO;
    private BigDecimal valor_real_venta = BigDecimal.ZERO;
    private Boolean credito;
    
    //datos temporales items de factura
    
    @Transient
    Long id_fi_temp;
    @Transient
   float quantity;
@Transient
   BigDecimal valor;
   @ManyToOne(optional = true, targetEntity = Usuarios.class)
     @JoinColumn(name="USUARIOS_ID")
     private Usuarios usuarios;
    
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

    public List<ConComprobanteEgreso> getComprobanteegresos() {
        return comprobanteegresos;
    }

    public void setComprobanteegresos(List<ConComprobanteEgreso> comprobanteegresos) {
        this.comprobanteegresos = comprobanteegresos;
    }
  
     
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

	
@Transient
    public boolean isEmpty() {
        return (facturaItemsProveedores == null) || (facturaItemsProveedores.size()==0);
    }
    @OneToMany(mappedBy="facturaProveedores", cascade=CascadeType.ALL,orphanRemoval = true)
    public List<FacturaItemProveedores> getFacturaItems() {
        return facturaItemsProveedores;
    }
    public void setFacturaItems(List<FacturaItemProveedores> lines) {
        this.facturaItemsProveedores = lines;
    }

    public void addProduct(Producto product, float quantity, BigDecimal valor,FacturaItemProveedores fiPadre) {
        this.id_fi_temp=product.getId();
        this.quantity=quantity;
        this.valor=valor;
        for (FacturaItemProveedores line: facturaItemsProveedores) {
            if (product.getId().intValue() == line.getProduct().getId().intValue()) {
                line.addQuantityValor(quantity,valor);
                line.setValor_u(new BigDecimal(line.getValor_total().divide(BigDecimal.valueOf(line.getQuantity())).longValue()));
                line.setFacturaItemProveedoresPadre(fiPadre);
                 calculateTotals();
                return;
            }
        }

        FacturaItemProveedores line = new FacturaItemProveedores();
        line.setProduct(product);
        line.setQuantity(quantity);
        line.setFacturaProveedores(this);
        line.setValor_total(round(valor));
        line.setValor_u(new BigDecimal(line.getValor_total().divide(BigDecimal.valueOf(line.getQuantity())).longValue()));
        line.setFacturaItemProveedoresPadre(fiPadre);
        facturaItemsProveedores.add(line);
        calculateTotals();
    }
    public void addAbono(ConComprobanteEgreso abonoProveedores) {
        abonoProveedores.setFacturaproveedores(this);
        for(ConComprobanteEgreso a: comprobanteegresos)
        {
            
        if(a.getId().equals(abonoProveedores.getId()))
        {
         
            a.setValor(abonoProveedores.getValor());
            a.setConcepto(abonoProveedores.getConcepto());
           
            calculateAbonos();
         return;
        }
        }
        comprobanteegresos.add(abonoProveedores);
        calculateAbonos();
    }
public void modifyItem(Producto product, float quantity) {
    id_fi_temp=product.getId();
    double d_valor=0.0;
    double d_valor_total=0.0;
    double d_total=0.0;
    System.out.println("Cant 1->"+quantity);
        for (FacturaItemProveedores line: facturaItemsProveedores) {
            if (product.getId().intValue() == line.getProduct().getId().intValue()) {
                this.quantity=quantity-line.getQuantity();
                this.valor=BigDecimal.valueOf(quantity).multiply(line.getValor_u());
                System.out.println("Valor/u->"+line.getValor_u());
                System.out.println("Valor/t->"+line.getValor_total());
                d_valor=this.valor.doubleValue();
                d_valor_total=line.getValor_total().doubleValue();
                d_total=d_valor-d_valor_total;
                this.valor=BigDecimal.valueOf(d_total);
                System.out.println("Cant2->"+this.quantity);
                System.out.println("valor->"+this.valor);
                line.updateQuantityValor(quantity);
                line.setValor_total(round((BigDecimal.valueOf(line.getQuantity())).multiply(line.getValor_u())));
                calculateTotals();
                
                return;
            }
        }
}
public void modifyPago(ConComprobanteEgreso abono) {
 
    
        for (ConComprobanteEgreso line: comprobanteegresos) {
            if (line == abono) {
               
                line.setValor(abono.getValor());
                line.setConcepto(abono.getConcepto());
                        calculateAbonos();
               
                return;
            }
        }
}
public void removeProduct(Producto product) {
        for (FacturaItemProveedores line: facturaItemsProveedores) {
            if (product.getId() == line.getProduct().getId()) { 
                facturaItemsProveedores.remove(line);
                id_fi_temp=product.getId();
                this.quantity=line.getQuantity()*(-1);
                this.valor=line.getValor_total().multiply(BigDecimal.valueOf(-1.00));
                 calculateTotals();
                return;
            }
        }
    }

   
    public BigDecimal getTotalAmount() {
    	
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal amount) {
        this.totalAmount = amount;
    }

   

   

    

    public void calculateTotals() {
        BigDecimal total = BigDecimal.ZERO;
        int index = 1;
        for (FacturaItemProveedores line: facturaItemsProveedores) {
            line.setPosition(index++);
            total = total.add(line.getValor_total());
        }  
         
        setTotalAmount(round(total));
    }
    
    public void calculateAbonos() {
        BigDecimal total = BigDecimal.ZERO;
        
        
        for (ConComprobanteEgreso line: comprobanteegresos) {
            
            total = total.add(line.getValor());
        }  
        valor_abonos=total;
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
	 * @return the usuario
	 */
	
	/**
	 * @return the valor_descuento
	 */
	
	
	
	public BigDecimal getEfectivo() {
		return efectivo;
	}
	public void setEfectivo(BigDecimal efectivo) {
		this.efectivo = efectivo;
	}
	public BigDecimal getDevolver() {
	devolver=(getEfectivo().subtract(totalAmount));
	if(getEfectivo().longValue()==0)devolver=BigDecimal.ZERO;
		return devolver;
	}
        public void setDevolver(BigDecimal devolver) {
		this.devolver = devolver;
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

    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public List<FacturaItemProveedores> getFacturaItemsProveedores() {
        return facturaItemsProveedores;
    }

    public void setFacturaItemsProveedores(List<FacturaItemProveedores> facturaItemsProveedores) {
        this.facturaItemsProveedores = facturaItemsProveedores;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
	
public void removeAbono(ConComprobanteEgreso abonoProveedores) {
        for (ConComprobanteEgreso line: comprobanteegresos) {
            if (abonoProveedores.getId().equals(line.getId())) { 
                comprobanteegresos.remove(line);
                calculateAbonos();
                return;
            }
        }
    }	
	
}