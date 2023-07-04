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
import java.util.Collections;
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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@XmlRootElement
@Table(name = "FACTURA",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"prefijo", "no_factura"})
   }
)
public class Factura    implements Serializable
{
     @Id
    @Column(name = "ID", table = "FACTURA", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
  
    @GeneratedValue(generator = "SEQ_FACTURA_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_FACTURA_ID", sequenceName = "SEQ_FACTURA_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    
    private static final long serialVersionUID = -5451107485769007079L;
    public enum Status {OPEN,CANCELLED,PROCESSING,SHIPPED}
    public static BigDecimal TAX_RATE = new BigDecimal(".0825");
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date facturaDate=new Date();
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechavencimiento=new Date();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GEN_PERSONAS_ID")
    private GenPersonas genPersonas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GENEAPB_ID")
    private GenEapb genEapb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="HCLCONSULTAS_ID",unique = true)
    private HclConsultas hclConsultas;
    @Column(name="NETAMOUNT",nullable=false,precision=12,scale=2)
    private BigDecimal netAmount = BigDecimal.ZERO;
    @Column(name="TAX",nullable=false,precision=12,scale=2)
    private BigDecimal tax = BigDecimal.ZERO;
    @Column(name="TOTALAMOUNT",nullable=false,precision=12,scale=2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal efectivo = BigDecimal.ZERO;
    @Transient
    private BigDecimal devolver = BigDecimal.ZERO;
    @OneToMany(fetch = FetchType.LAZY,mappedBy="factura", cascade=CascadeType.ALL,orphanRemoval = true)
    @OrderBy("conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id ASC")
    private List<FacturaItem> facturaItems = new ArrayList<FacturaItem>();
    @OneToMany(fetch = FetchType.LAZY,mappedBy="factura", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<ComprobanteIngreso> comprobantesingresos = new ArrayList<ComprobanteIngreso>();
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
    
   
@Column(name="NO_FACTURA",unique = false)
    private Long no_factura;
    private String forma_pago;
     @Column(precision=16,scale=2)
    private BigDecimal valor_abonos = BigDecimal.ZERO;
      @Column(precision=16,scale=2)
    private BigDecimal valor_real_venta = BigDecimal.ZERO;
    private Boolean credito;
    
    //datos temporales items de factura
    @Transient
    Long id_fi_temp;
    @Transient
    float quantity;
    
    private boolean deleted;
    @Transient
    private boolean showiva;
    @Transient
    private String nofacturacerosizquierda;
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
     @Column(length = 2)
     private String prefijo;
     private boolean facturacerrada=false;
     @Transient
     private BigDecimal totalcuotasmoderadoras=BigDecimal.ZERO;
     @Transient
     private BigDecimal totalcopagos=BigDecimal.ZERO;
     private boolean causadaacontabilidad;
    @ManyToOne(fetch = FetchType.LAZY,optional = true, targetEntity = GenTiposUsuarios.class)
    @JoinColumn(name="GEN_TIPOS_USUARIOS_ID")
    private GenTiposUsuarios genTiposUsuarios;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaaceptacion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecharadicacion;
    private String numeroradicado="";
    private boolean aceptada=false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GENCONVENIOS_ID")
    private GenConvenios genConvenios;
    private int tarifa;
     private float porcentajedescuento;
    private BigDecimal diferenciacapitado = BigDecimal.ZERO;
    public Factura()
    {
        
    }
    public Factura(Long id,GenEapb eapb,GenTiposUsuarios genTiposUsuarios,BigDecimal totalamount,Long no_factura)
    {
        this.id=id;
        this.genEapb=eapb;
        this.genTiposUsuarios=genTiposUsuarios;
        this.totalAmount=totalamount;
        this.no_factura=no_factura;
    }
     public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


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
               Collections.sort(facturaItems);
        return facturaItems;
    }
    public void setFacturaItems(List<FacturaItem> lines) {
        this.facturaItems = lines;
    }
    public void addComprobanteIngreso(ComprobanteIngreso comprobanteIngreso)
    {  
        for(ComprobanteIngreso ci:comprobantesingresos)
        {
            if(ci.getId().equals(comprobanteIngreso.getId()))
            {
                return;
            }
        }
        comprobanteIngreso.setFactura(this);
        comprobantesingresos.add(comprobanteIngreso);
        getvalortotalingresos();
    
    }
    public void getvalortotalingresos()
    {
      BigDecimal total=BigDecimal.ZERO;
      for(ComprobanteIngreso ci:comprobantesingresos)
        {
           total=total.add(ci.getValor());
        }
      valor_abonos=total;
    }        
    public void addProduct(BigDecimal copago,BigDecimal cuotamoderadora, Producto product, float quantity, BigDecimal pv,BigDecimal descuento,float descuentoeps,HclConsultas consultas,ConComprobanteProcedimiento conComprobanteProcedimiento,int tarifa) {
        id_fi_temp=product.getId();
        this.quantity=quantity;
        FacturaItem line = new FacturaItem();
        line.setGenPacientes(consultas.getAgnCitas().getGenPacientes());
        line.setHclConsultas(consultas);
        line.setProducto(product);
        line.setQuantity(quantity);
        BigDecimal valordescuentoeps_valor_u=BigDecimal.ZERO;
        line.setFactura(this);
        try{
        if(product.getGenCategoriasProductos().getCodigo()==1)
        {
            line.setValor_u(tarifa==0?line.getProducto().getPrecio2015():tarifa==1?line.getProducto().getPrecio2016():tarifa==2?line.getProducto().getPrecio2017():tarifa==3?line.getProducto().getPrecio2018():line.getProducto().getPrecio2001());
            valordescuentoeps_valor_u=round(BigDecimal.valueOf(descuentoeps*0.01).multiply(line.getValor_u()));
            line.setValor_total((BigDecimal.valueOf(line.getQuantity())).multiply(tarifa==0?line.getProducto().getPrecio2015():tarifa==1?line.getProducto().getPrecio2016():tarifa==2?line.getProducto().getPrecio2017():tarifa==3?line.getProducto().getPrecio2018():line.getProducto().getPrecio2001()));
            BigDecimal valordescuentoeps=round(BigDecimal.valueOf(descuentoeps*0.01).multiply(line.getValor_total()));
            line.setDescuento(valordescuentoeps);
            line.setValor_u(line.getValor_u().subtract(valordescuentoeps_valor_u));
            copago=round((copago.multiply(new BigDecimal(0.01))).multiply(line.getValor_u()));
     
        }
        else
        {
                 line.setValor_u(line.getProducto().getPrecio2018());
                 line.setValor_total((BigDecimal.valueOf(line.getQuantity())).multiply(line.getProducto().getPrecio2018()));
                 line.setDescuento(BigDecimal.ZERO);
                 copago=BigDecimal.ZERO;
                 cuotamoderadora=BigDecimal.ZERO;
        }
        line.setCopago(copago);
        line.setCuotamoderadora(cuotamoderadora);
        BigDecimal total_descuentos=copago.add(cuotamoderadora).add(line.getDescuento());
        line.setDescuento(total_descuentos);
        line.setConComprobanteProcedimiento(conComprobanteProcedimiento);
        line.setValor_total(round(line.getValor_total().subtract(total_descuentos)));
        line.setPrecio_compra(pv);
        line.setValor_total2(round(BigDecimal.valueOf(line.getQuantity())).multiply(line.getPrecio_compra()));
        facturaItems.add(line);
        calculateTotals();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
     public void addProductV2(Long id,BigDecimal copago,BigDecimal cuotamoderadora, Producto product, float quantity, BigDecimal pv,BigDecimal descuento,float descuentoeps,AgnCitas agnCitas,ConComprobanteProcedimiento conComprobanteProcedimiento,BigDecimal tarifa,HclCie10 dmain,HclCie10 din,HclCie10 dse,String tipodiag,String tipocuenta,String clasificacionorigen, String noautorizacion,Sucursales centrocostos,GenEapb genEapb, String discapacidad,String estadopaciente,String tiposervicio) {
        id_fi_temp=product.getId();
        this.quantity=quantity;
        FacturaItem line=null;
        if(id!=null)
        {
            for(FacturaItem fi:facturaItems)
            {
                if(id.equals(fi.getId()))
                {
                    line=fi;
                }
            }
        }
        else{
            line= new FacturaItem();
        }
        line.setGenPacientes(agnCitas.getGenPacientes());
        line.setAgnCitas(agnCitas);
        line.setProducto(product);
        line.setQuantity(quantity);
        line.setDiagnosticoprincipal(dmain);
        line.setTipodiagnostico(tipodiag);
        line.setDiagnosticoingreso(din);
        line.setDiagnosticosecundario(dse);
        line.setTipocuenta(tipocuenta);
        line.setClasificacionorigen(clasificacionorigen);
        line.setGenEapb(genEapb);
        line.setDiscapacidad(discapacidad);
        line.setEstadopaciente(estadopaciente);
        line.setTipoatencion(tiposervicio);
        line.setNoautorizacion(noautorizacion);
        line.setSucursales(centrocostos);
        BigDecimal valordescuentoeps_valor_u=BigDecimal.ZERO;
        line.setFactura(this);
        try{
        if(product.getGenCategoriasProductos().getCodigo()==1)
        {
            line.setValor_u(tarifa);
            valordescuentoeps_valor_u=round(BigDecimal.valueOf(descuentoeps*0.01).multiply(line.getValor_u()));
            line.setValor_total((BigDecimal.valueOf(line.getQuantity())).multiply(tarifa));
            BigDecimal valordescuentoeps=round(BigDecimal.valueOf(descuentoeps*0.01).multiply(line.getValor_total()));
            line.setDescuento(valordescuentoeps);
            line.setValor_u(line.getValor_u().subtract(valordescuentoeps_valor_u));
            copago=round((copago.multiply(new BigDecimal(0.01))).multiply(line.getValor_u()));
     
        }
        else
        {
                 line.setValor_u(line.getProducto().getPrecio2018());
                 line.setValor_total((BigDecimal.valueOf(line.getQuantity())).multiply(line.getProducto().getPrecio2018()));
                 line.setDescuento(BigDecimal.ZERO);
                 copago=BigDecimal.ZERO;
                 cuotamoderadora=BigDecimal.ZERO;
        }
        line.setCopago(copago);
        line.setCuotamoderadora(cuotamoderadora);
        BigDecimal total_descuentos=copago.add(cuotamoderadora).add(line.getDescuento());
        line.setDescuento(total_descuentos);
        line.setConComprobanteProcedimiento(conComprobanteProcedimiento);
        line.setValor_total(round(line.getValor_total().subtract(total_descuentos)));
        line.setPrecio_compra(pv);
        line.setValor_total2(round(BigDecimal.valueOf(line.getQuantity())).multiply(line.getPrecio_compra()));
        if(id==null)
        {
        facturaItems.add(line);
        }
        calculateTotals();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
   
    public void modifyItem(FacturaItem fi, float quantity) {
    id_fi_temp=fi.getProducto().getId();
    
        
                int index=facturaItems.indexOf(fi);
                this.quantity=quantity-fi.getQuantity();
                fi.updateQuantity(quantity);
                if(fi.getProducto().getHclCups()==null)
                {
                 fi.setValor_total(BigDecimal.valueOf(fi.getQuantity()).multiply(fi.getValor_u()));
                
                }else
                {
                     if(fi.getFactura().getPrefijo().substring(0,1).equals("A") || fi.getFactura().getPrefijo().substring(0,1).equals("P"))
                    {
                    BigDecimal totaldescuentoscop=fi.getCopago().add(fi.getCuotamoderadora());
                    if(fi.getGenPacientes().getGenEapb().findlastconvenio()!=null)
                    {
                      float descuentoeps=fi.getGenPacientes().getGenEapb().findlastconvenio().getPorcentajedescuento();
                      BigDecimal totalddescuentoeps=round(BigDecimal.valueOf(descuentoeps*0.01).multiply(fi.getValor_u()));
                      BigDecimal totaldecuentos=totaldescuentoscop.add(totalddescuentoeps);
                      fi.setValor_total((BigDecimal.valueOf(fi.getQuantity()).multiply(fi.getValor_u())).subtract(totaldecuentos));
                    }
                }else{
                          fi.setValor_total(BigDecimal.valueOf(fi.getQuantity()).multiply(fi.getValor_u()));
                          
                     }
                     
                }
            
                facturaItems.set(index, fi);
                calculateTotals();
               
            
        
}
public void modifyCopago(FacturaItem fi, BigDecimal copago) 
{
    
    id_fi_temp=fi.getProducto().getId();
    copago=round((copago.multiply(new BigDecimal(0.01))).multiply(fi.getValor_u()));
    int index=facturaItems.indexOf(fi);
    fi.setValor_total(fi.getValor_total().subtract((copago.subtract(fi.getCopago()))));
    fi.setDescuento(fi.getDescuento().add((copago.subtract(fi.getCopago()))));
    fi.setCopago(copago);
    facturaItems.set(index, fi);
    calculateTotals();
    } 
public void modifyCuota(FacturaItem fi, BigDecimal cuota) 
{
    id_fi_temp=fi.getProducto().getId();
    int index=facturaItems.indexOf(fi);
    fi.setValor_total(fi.getValor_total().subtract((cuota.subtract(fi.getCuotamoderadora()))));
    fi.setDescuento(fi.getDescuento().add((cuota.subtract(fi.getCuotamoderadora()))));
    fi.setCuotamoderadora(cuota);
    facturaItems.set(index, fi);
    calculateTotals();
    } 

public void removeProduct(FacturaItem fi) {
              this.quantity=fi.getQuantity()*(-1);
              id_fi_temp=fi.getProducto().getId();  
              facturaItems.remove(fi);
                
                calculateTotals();
                
           
    }
public void removeitemssinid() {
             for(FacturaItem fi: facturaItems)
             {
                 if(fi.getId()==null)
                 {
                     facturaItems.remove(fi);
                 }
             }
                calculateTotals();
                
           
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
             iva_prod=(line.getProducto().getIva()*0.01);
            if(line.getProducto().getIva()>0)
            {
             total=(total.add((line.getValor_total().subtract(valor_descuento)).divide(BigDecimal.valueOf(1.16),4,RoundingMode.HALF_UP)));
             iva=round(iva.add(((line.getValor_total().subtract(valor_descuento)).divide(BigDecimal.valueOf(1.16),4,RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(iva_prod))));
            }
            else
            {
                total = total.add(line.getValor_total());
            }
            
            
        }  
         System.out.println("***valor total***->"+total);
         setNetAmount(round(total));
         setTotalAmount(round(total.add(iva).add(valor_descuento).subtract(descuento)));
         setTotalAmount(total.add(diferenciacapitado!=null?diferenciacapitado:BigDecimal.ZERO));
          System.out.println("***valor total mount***->"+getTotalAmount().toString());
         calculartotalescopagocuotamoeras();
    }
    public void calculateTotals2(BigDecimal vd) {
        BigDecimal total = BigDecimal.ZERO;
        valor_descuento=vd;
        int index = 1;
        for (FacturaItem line: facturaItems) {
            line.setPosition(index++);
            total = total.add(line.getProducto().getPrecio().multiply(new BigDecimal(line.getQuantity())));
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


    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public GenPersonas getGenPersonas() {
        return genPersonas;
    }

    public void setGenPersonas(GenPersonas genPersonas) {
        this.genPersonas = genPersonas;
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

    public HclConsultas getHclConsultas() {
        return hclConsultas;
    }

    public void setHclConsultas(HclConsultas hclConsultas) {
        this.hclConsultas = hclConsultas;
    }

   

    public GenEapb getGenEapb() {
        return genEapb;
    }

    public void setGenEapb(GenEapb genEapb) {
        this.genEapb = genEapb;
    }

    public Date getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(Date fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

  

             public String getNofacturacerosizquierda() {
        
        String cerosizq="";
     
     for(int i=4;i>no_factura.toString().length();i--)
     {
         cerosizq=cerosizq+"0";
     }
     
      nofacturacerosizquierda = prefijo+cerosizq+no_factura.toString();
        return nofacturacerosizquierda;
    }
    public String getIdcerosizquierda() {
        
        String cerosizq="";
     
     for(int i=5;i>id.toString().length();i--)
     {
         cerosizq=cerosizq+"0";
     }
    
    
        return cerosizq+id.toString();
    }

    public List<ComprobanteIngreso> getComprobantesingresos() {
        return comprobantesingresos;
    }

    public void setComprobantesingresos(List<ComprobanteIngreso> comprobantesingresos) {
        this.comprobantesingresos = comprobantesingresos;
    }

    public boolean isFacturacerrada() {
        return facturacerrada;
    }

    public void setFacturacerrada(boolean facturacerrada) {
        this.facturacerrada = facturacerrada;
    }

    public boolean isCausadaacontabilidad() {
        return causadaacontabilidad;
    }

    public void setCausadaacontabilidad(boolean causadaacontabilidad) {
        this.causadaacontabilidad = causadaacontabilidad;
    }
	
	
public BigDecimal getSaldo()
{
    BigDecimal acum=BigDecimal.ZERO;
    for(ComprobanteIngreso ci:comprobantesingresos)
    {
        acum=acum.add(ci.getValor());
    }
    return totalAmount.subtract(acum);
}

    public void calculartotalescopagocuotamoeras() {
        BigDecimal valorcuota=BigDecimal.ZERO;  
        BigDecimal valorcopago=BigDecimal.ZERO;  
        for(FacturaItem line: facturaItems)
        {
            valorcuota=valorcuota.add(line.getCuotamoderadora());
            valorcopago=valorcopago.add(line.getCopago());
        }
     totalcuotasmoderadoras=valorcuota;
     totalcopagos=valorcopago;
    }
  public long calculartotalescopagocuotamoeras2() {
       
        BigDecimal valorcopago=BigDecimal.ZERO;  
        for(FacturaItem line: facturaItems)
        {
          
            valorcopago=valorcopago.add(line.getCopago());
        }
  
     totalcopagos=valorcopago;
     return totalcopagos.longValue();
    }
  public long calculartotalescuotamoeras() {
        BigDecimal valorcuota=BigDecimal.ZERO;  
      
        for(FacturaItem line: facturaItems)
        {
            valorcuota=valorcuota.add(line.getCuotamoderadora());
        
        }
     totalcuotasmoderadoras=valorcuota;
    
     return totalcuotasmoderadoras.longValue();
    }
    public BigDecimal getTotalcuotasmoderadoras() {
        return totalcuotasmoderadoras;
    }

    public void setTotalcuotasmoderadoras(BigDecimal totalcuotasmoderadoras) {
        this.totalcuotasmoderadoras = totalcuotasmoderadoras;
    }

    public BigDecimal getTotalcopagos() {
        return totalcopagos;
    }

    public void setTotalcopagos(BigDecimal totalcopagos) {
        this.totalcopagos = totalcopagos;
    }

    public GenTiposUsuarios getGenTiposUsuarios() {
        return genTiposUsuarios;
    }

    public void setGenTiposUsuarios(GenTiposUsuarios genTiposUsuarios) {
        this.genTiposUsuarios = genTiposUsuarios;
    }

   

    public Date getFechaaceptacion() {
        return fechaaceptacion;
    }

    public void setFechaaceptacion(Date fechaaceptacion) {
        this.fechaaceptacion = fechaaceptacion;
    }

    public Date getFecharadicacion() {
        return fecharadicacion;
    }

    public void setFecharadicacion(Date fecharadicacion) {
        this.fecharadicacion = fecharadicacion;
    }

    public String getNumeroradicado() {
        return numeroradicado;
    }

    public void setNumeroradicado(String numeroradicado) {
        this.numeroradicado = numeroradicado;
    }

    public boolean isAceptada() {
        return aceptada;
    }

    public void setAceptada(boolean aceptada) {
        this.aceptada = aceptada;
    }

    public GenConvenios getGenConvenios() {
        return genConvenios;
    }

    public void setGenConvenios(GenConvenios genConvenios) {
        this.genConvenios = genConvenios;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }
    
public void removerinsumosymedicamentos(ConComprobanteProcedimiento cp)
{
   int i=0;
       while(i<facturaItems.size())
    {
        if(i<facturaItems.size())
        {    
        if(facturaItems.get(i).getConComprobanteProcedimiento().getId().equals(cp.getId()) && (facturaItems.get(i).getProducto().getGenCategoriasProductos().getCodigo()==2 || facturaItems.get(i).getProducto().getGenCategoriasProductos().getCodigo()==3 ) )
        {
            facturaItems.remove(facturaItems.get(i));
         i--;
        }
      else
        {
              
       i++;
        }
    }
    
    }
}

    public BigDecimal getDiferenciacapitado() {
        return diferenciacapitado;
    }

    public void setDiferenciacapitado(BigDecimal diferenciacapitado) {
        this.diferenciacapitado = diferenciacapitado;
    }
    public void calculardiferenciacapitado()
    {
        totalAmount=totalAmount.add(diferenciacapitado);
    }

    public float getPorcentajedescuento() {
        return porcentajedescuento;
    }

    public void setPorcentajedescuento(float porcentajedescuento) {
        this.porcentajedescuento = porcentajedescuento;
    }
    

}