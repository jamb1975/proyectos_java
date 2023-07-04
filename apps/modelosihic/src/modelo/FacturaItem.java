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
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "FACTURAITEM")
public class FacturaItem
    implements Serializable,Comparable<FacturaItem>
{
   
    @Column(name = "ID", table = "FACTURAITEM", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_FACTURAITEM_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_FACTURAITEM_ID", sequenceName = "SEQ_FACTURAITEM_ID", allocationSize = 1, initialValue = 1)
   
    private Long    id;
    private int     position;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PRODUCTO_ID")
    private Producto producto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GENPACIENTES_ID")
    private GenPacientes genPacientes;
     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="HCLCONSULTAS_ID")
    private HclConsultas hclConsultas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="AGNCITAS_ID")
    private AgnCitas agnCitas;
   private float     quantity;
    private BigDecimal valor_u=BigDecimal.ZERO;
    private BigDecimal valor_total=BigDecimal.ZERO;
    private BigDecimal precio_compra=BigDecimal.ZERO;
    private BigDecimal valor_total2=BigDecimal.ZERO;
    private BigDecimal descuento=BigDecimal.ZERO;
    private BigDecimal cuotamoderadora=BigDecimal.ZERO;
    private BigDecimal copago=BigDecimal.ZERO;
    private String noautorizacion;
     @ManyToOne
    @JoinColumn(name="DIAGNOSTICOPRINCIPAL_ID")
    private HclCie10 diagnosticoprincipal;
     @Column(length = 1)
    private String tipodiagnostico;
    @ManyToOne
    @JoinColumn(name="DIAGNOSTICOINGRESO_ID")
    private HclCie10 diagnosticoingreso;
     @ManyToOne
    @JoinColumn(name="DIAGNOSTICOSECUNDARIO_ID")
    private HclCie10 diagnosticosecundario;
     @Column(length = 2)
     private String tipoatencion;
     @Column(length = 1)
     private String clasificacionorigen;
     @Column(length = 1)
     private String discapacidad;
     @Column(length = 1)
     private String estadopaciente;
      @Column(length = 10)
     private String tipocuenta;
    
    private String talla;
    @ManyToOne
    @JoinColumn(name="SUCURSALES_ID")
    Sucursales sucursales;
    @ManyToOne
    @JoinColumn(name="GEN_EAPB_ID")
   GenEapb genEapb;
   @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaapertura=new Date();
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechadesecho=new Date();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="FACTURA_ID")
    private Factura   factura;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="COMPROBANTEPROCEDIMIENTO_ID")
    private ConComprobanteProcedimiento   conComprobanteProcedimiento;
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
   @Transient  
   BigDecimal valor_unitario=BigDecimal.ZERO;
   
    public BigDecimal getPrecio_compra() {
		return precio_compra;
	}
	public void setPrecio_compra(BigDecimal precioCompra) {
		precio_compra = precioCompra;
	}
	public BigDecimal getValor_total2() {
		return valor_total2;
	}
	public void setValor_total2(BigDecimal valorTotal2) {
		valor_total2 = valorTotal2;
	}
	/**
	 * @return the id
	 */
   
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

    @Column(name="POS")
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    
    
    public Factura getFactura() {
        return factura;
    }
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    
	 public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto=producto;
    }

    @Column(name="QUANTITY",nullable=false)
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
    public void addQuantity(float howmany) {
        quantity += howmany;
    }
    public void updateQuantity(float howmany) {
        quantity = howmany;
    }
	/**
	 * @return the valor_total
	 */
   
	public BigDecimal getValor_total() {
    	
		return valor_total;
	}
	/**
	 * @param valor_total the valor_total to set
	 */
	public void setValor_total(BigDecimal valor_total) {
		this.valor_total = valor_total;
	}
	public BigDecimal getValor_u() {
		return valor_u;
	}
	public void setValor_u(BigDecimal valor_u) {
		this.valor_u = valor_u;
	}
	public void setTalla(String talla) {
		this.talla = talla;
	}
	public String getTalla() {
		return talla;
	}

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
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

    public Date getFechaapertura() {
        return fechaapertura;
    }

    public void setFechaapertura(Date fechaapertura) {
        this.fechaapertura = fechaapertura;
    }

    public Date getFechadesecho() {
        return fechadesecho;
    }

    public void setFechadesecho(Date fechadesecho) {
        this.fechadesecho = fechadesecho;
    }

    public GenPacientes getGenPacientes() {
        return genPacientes;
    }

    public void setGenPacientes(GenPacientes genPacientes) {
        this.genPacientes = genPacientes;
    }

    public HclConsultas getHclConsultas() {
        return hclConsultas;
    }

    public void setHclConsultas(HclConsultas hclConsultas) {
        this.hclConsultas = hclConsultas;
    }

    public String getNoautorizacion() {
        return noautorizacion;
    }

    public void setNoautorizacion(String noautorizacion) {
        this.noautorizacion = noautorizacion;
    }

    public ConComprobanteProcedimiento getConComprobanteProcedimiento() {
        return conComprobanteProcedimiento;
    }

    public void setConComprobanteProcedimiento(ConComprobanteProcedimiento conComprobanteProcedimiento) {
        this.conComprobanteProcedimiento = conComprobanteProcedimiento;
    }

    public BigDecimal getCuotamoderadora() {
        return cuotamoderadora;
    }

    public void setCuotamoderadora(BigDecimal cuotamoderadora) {
        this.cuotamoderadora = cuotamoderadora;
    }

    public BigDecimal getCopago() {
        return copago;
    }

    public void setCopago(BigDecimal copago) {
        this.copago = copago;
    }

    public BigDecimal getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(BigDecimal valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public AgnCitas getAgnCitas() {
        return agnCitas;
    }

    public void setAgnCitas(AgnCitas agnCitas) {
        this.agnCitas = agnCitas;
    }

    public HclCie10 getDiagnosticoprincipal() {
        return diagnosticoprincipal;
    }

    public void setDiagnosticoprincipal(HclCie10 diagnosticoprincipal) {
        this.diagnosticoprincipal = diagnosticoprincipal;
    }

    public String getTipodiagnostico() {
        return tipodiagnostico;
    }

    public void setTipodiagnostico(String tipodiagnostico) {
        this.tipodiagnostico = tipodiagnostico;
    }

    

    public HclCie10 getDiagnosticoingreso() {
        return diagnosticoingreso;
    }

    public void setDiagnosticoingreso(HclCie10 diagnosticoingreso) {
        this.diagnosticoingreso = diagnosticoingreso;
    }

    public HclCie10 getDiagnosticosecundario() {
        return diagnosticosecundario;
    }

    public void setDiagnosticosecundario(HclCie10 diagnosticosecundario) {
        this.diagnosticosecundario = diagnosticosecundario;
    }

   

    public String getClasificacionorigen() {
        return clasificacionorigen;
    }

    public void setClasificacionorigen(String clasificacionorigen) {
        this.clasificacionorigen = clasificacionorigen;
    }

    public Sucursales getSucursales() {
        return sucursales;
    }

    public void setSucursales(Sucursales sucursales) {
        this.sucursales = sucursales;
    }

    public GenEapb getGenEapb() {
        return genEapb;
    }

    public void setGenEapb(GenEapb genEapb) {
        this.genEapb = genEapb;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getTipoatencion() {
        return tipoatencion;
    }

    public void setTipoatencion(String tipoatencion) {
        this.tipoatencion = tipoatencion;
    }

    public String getEstadopaciente() {
        return estadopaciente;
    }

    public void setEstadopaciente(String estadopaciente) {
        this.estadopaciente = estadopaciente;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

   

   

   
     @Override
     public int compareTo(FacturaItem fi) {
        Long a=this.getId();
        Long b=fi.getId();
        return a.compareTo(b);
    } 
     
}