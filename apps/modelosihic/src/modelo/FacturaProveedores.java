

package modelo;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@XmlRootElement
@Table(name = "FACTURAPROVEEDORES")
public class FacturaProveedores    implements Serializable
{
      @Column(name = "ID", table = "FACTURAPROVEEDORES", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_FACTURAPROVEEDORES_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_FACTURAPROVEEDORES_ID", sequenceName = "SEQ_FACTURAPROVEEDORES_ID", allocationSize = 1, initialValue = 1)
   
    private Long id;
    private static final long serialVersionUID = -5451107485769007079L;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date facturaDate=new Date();
     @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PROVEEDORES_ID")
    private Proveedores proveedores;
    
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal efectivo = BigDecimal.ZERO;
    private BigDecimal devolver = BigDecimal.ZERO;
     private List<FacturaItemProveedores> facturaItemsProveedores = new ArrayList<FacturaItemProveedores>();
 //   @OneToMany(fetch = FetchType.LAZY,mappedBy="facturaProveedores", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<ComprobanteEgreso> li_conComprobanteEgresos = new ArrayList<ComprobanteEgreso>();
    private Long no_factura;
    private String forma_pago;
    private float iva;
    private float retefuente;
     private BigDecimal valor_egresos = BigDecimal.ZERO;
    private BigDecimal valor_real_venta = BigDecimal.ZERO;
    private Boolean credito;
    private String nocheque;
    private String notargeta;
    private String banco;
    //datos temporales items de factura
    
    Long id_fi_temp;
   float quantity;
   BigDecimal valor;
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
    
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
   

     
  public BigDecimal getValor_abonos() {
		return valor_egresos;
	}
	public void setValor_abonos(BigDecimal valorAbonos) {
		valor_egresos = valorAbonos;
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
         @Column(name="NO_FACTURA")
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
            if (product.getId().equals(line.getProducto().getId())){
                line.addQuantityValor(quantity,valor);
                line.setValor_u(line.getValor_total().divide(BigDecimal.valueOf(line.getQuantity())));
                line.setFacturaItemProveedoresPadre(fiPadre);
                 calculateTotals();
                return;
            }
        }

        FacturaItemProveedores line = new FacturaItemProveedores();
        line.setProducto(product);
        line.setQuantity(quantity);
        line.setFacturaProveedores(this);
        line.setValor_total(round(valor));
        line.setValor_u(round(line.getValor_total().divide(BigDecimal.valueOf(line.getQuantity()))));
        line.setFacturaItemProveedoresPadre(fiPadre);
        facturaItemsProveedores.add(line);
        calculateTotals();
    }
   
public void modifyItem(Producto product, float quantity) {
    id_fi_temp=product.getId();
    double d_valor=0.0;
    double d_valor_total=0.0;
    double d_total=0.0;
        for (FacturaItemProveedores line: facturaItemsProveedores) {
            if (product.getId().intValue() == line.getProducto().getId().intValue()) {
                this.quantity=quantity-line.getQuantity();
                this.valor=BigDecimal.valueOf(quantity).multiply(line.getValor_u());
                d_valor=this.valor.doubleValue();
                d_valor_total=line.getValor_total().doubleValue();
                d_total=d_valor-d_valor_total;
                this.valor=BigDecimal.valueOf(d_total);
                line.updateQuantityValor(quantity);
                line.setValor_total(round((BigDecimal.valueOf(line.getQuantity())).multiply(line.getValor_u())));
                calculateTotals();
                
                return;
            }
        }
}

public void removeProduct(Producto product) {
        for (FacturaItemProveedores line: facturaItemsProveedores) {
            if (product.getId() == line.getProducto().getId()) { 
                facturaItemsProveedores.remove(line);
                id_fi_temp=product.getId();
                this.quantity=line.getQuantity()*(-1);
                this.valor=line.getValor_total().multiply(BigDecimal.valueOf(-1.00));
                 calculateTotals();
                return;
            }
        }
    }

   
@Column(name="TOTALAMOUNT",nullable=false,precision=16,scale=2)
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
     @Temporal(javax.persistence.TemporalType.TIMESTAMP)
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
	@Transient
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

   
@Transient
    public Long getId_fi_temp() {
        return id_fi_temp;
    }

    public void setId_fi_temp(Long id_fi_temp) {
        this.id_fi_temp = id_fi_temp;
    }
 @Transient
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
@Transient
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

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getRetefuente() {
        return retefuente;
    }

    public void setRetefuente(float retefuente) {
        this.retefuente = retefuente;
    }

    public String getNocheque() {
        return nocheque;
    }

    public void setNocheque(String nocheque) {
        this.nocheque = nocheque;
    }

    public String getNotargeta() {
        return notargeta;
    }

    public void setNotargeta(String notargeta) {
        this.notargeta = notargeta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    

    public List<ComprobanteEgreso> getLi_conComprobanteEgresos() {
        return li_conComprobanteEgresos;
    }

    public void setLi_conComprobanteEgresos(List<ComprobanteEgreso> li_conComprobanteEgresos) {
        this.li_conComprobanteEgresos = li_conComprobanteEgresos;
    }
	
  public void gettotalegresos()
  {
      BigDecimal total=BigDecimal.ZERO;
      for(ComprobanteEgreso ce:li_conComprobanteEgresos)
      {
          total=total.add(ce.getValor());
      }
      valor_egresos=total;
   
  }          

    public BigDecimal getValor_egresos() {
        return valor_egresos;
    }

    public void setValor_egresos(BigDecimal valor_egresos) {
        this.valor_egresos = valor_egresos;
    }
    
    public void add(ComprobanteEgreso ce)
    {
        for(ComprobanteEgreso ce2: li_conComprobanteEgresos)
        {
          if(ce2.getId().equals(ce.getId()))
          {
              return;
          }
        } 
      
        li_conComprobanteEgresos.add(ce);
        gettotalegresos();
    }         

   
	
}