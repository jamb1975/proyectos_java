/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class ConComprobanteContabilidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "SEQ_CONCOMPROBANTECONTABILIDAD_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CONCOMPROBANTECONTABILIDAD_ID", sequenceName = "SEQ_CONCOMPROBANTECONTABILIDAD_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @ManyToOne(optional = true, targetEntity = ConsecutivoComprobanteContabilidad.class)
    @JoinColumn(name="CONSECUTIVOCOMPROBANTECONTABILIDAD_ID")
    private ConsecutivoComprobanteContabilidad consecutivoComprobanteContabilidad;
     @ManyToOne(optional = true, targetEntity = Factura.class)
    @JoinColumn(name="FACTURA_ID")
    private Factura factura;
    @OneToMany(mappedBy="conComprobanteContabilidad", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<ConDetallesComprobanteContabilidad> detallesComprobanteContabilidads = new ArrayList<ConDetallesComprobanteContabilidad>();
    private int tipo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaelaboracion;
    @Column(name = "USUARIO_MODIFICADOR",length = 50)
    @Basic
    private String usuarioModificador;
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
    private BigDecimal totaldebe=BigDecimal.ZERO;
    @Transient
    private BigDecimal totalhaber=BigDecimal.ZERO;
    private String nocheque;
    private String notargeta;
    private String banco;
    private int tipotargeta;
    private int tipocomprobante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConsecutivoComprobanteContabilidad getConsecutivoComprobanteContabilidad() {
        return consecutivoComprobanteContabilidad;
    }

    public void setConsecutivoComprobanteContabilidad(ConsecutivoComprobanteContabilidad consecutivoComprobanteContabilidad) {
        this.consecutivoComprobanteContabilidad = consecutivoComprobanteContabilidad;
    }


    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Date getFechaelaboracion() {
        return fechaelaboracion;
    }

    public void setFechaelaboracion(Date fechaelaboracion) {
        this.fechaelaboracion = fechaelaboracion;
    }

    public String getUsuarioModificador() {
        return usuarioModificador;
    }

    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
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

    public List<ConDetallesComprobanteContabilidad> getDetallesComprobanteContabilidads() {
        return detallesComprobanteContabilidads;
    }

    public void setDetallesComprobanteContabilidads(List<ConDetallesComprobanteContabilidad> detallesComprobanteContabilidads) {
        this.detallesComprobanteContabilidads = detallesComprobanteContabilidads;
    }

 

   public void addPuc(ConPuc conPuc,String detalle,BigDecimal valor,boolean debe)
   {
       
      
       BigDecimal parciales=BigDecimal.ZERO;
       BigDecimal bd_debe=BigDecimal.ZERO;
       BigDecimal bd_haber=BigDecimal.ZERO;
       ConDetallesComprobanteContabilidad conDetallesComprobanteContabilidad;
      
          
       
      
       
       
          
           
             
               
          
           conDetallesComprobanteContabilidad=null;
           conDetallesComprobanteContabilidad=new ConDetallesComprobanteContabilidad();
           conDetallesComprobanteContabilidad.setConPuc(conPuc);
           conDetallesComprobanteContabilidad.setConComprobanteContabilidad(this);
           conDetallesComprobanteContabilidad.setParcialdebe(BigDecimal.ZERO);
           conDetallesComprobanteContabilidad.setParcialhaber(BigDecimal.ZERO);
          
           if(!debe)
           {
             conDetallesComprobanteContabilidad.setDebe(BigDecimal.ZERO);
             conDetallesComprobanteContabilidad.setHaber(valor);
             conDetallesComprobanteContabilidad.setParcialdebe(BigDecimal.ZERO);
             conDetallesComprobanteContabilidad.setParcialhaber(valor);
           }
           else
           {
               conDetallesComprobanteContabilidad.setHaber(BigDecimal.ZERO);
               conDetallesComprobanteContabilidad.setDebe(valor);
               conDetallesComprobanteContabilidad.setParcialhaber(BigDecimal.ZERO);
               conDetallesComprobanteContabilidad.setParcialdebe(valor);
           }
                
       
          
             detallesComprobanteContabilidads.add(conDetallesComprobanteContabilidad);
           
         
   
        
       calculartotales();
       
   
       
   }    
   public void calculartotales()
   {
       for(ConDetallesComprobanteContabilidad cd: detallesComprobanteContabilidads)
       {
           totaldebe=totaldebe.add(cd.getDebe());
           totalhaber=totaldebe.add(cd.getHaber());
       }
   }
   public void removeDetalle(ConDetallesComprobanteContabilidad conDetallesComprobanteContabilidad)
   {
       detallesComprobanteContabilidads.remove(conDetallesComprobanteContabilidad);
   }

    public BigDecimal getTotaldebe() {
        return totaldebe;
    }

    public void setTotaldebe(BigDecimal totaldebe) {
        this.totaldebe = totaldebe;
    }

    public BigDecimal getTotalhaber() {
        return totalhaber;
    }

    public void setTotalhaber(BigDecimal totalhaber) {
        this.totalhaber = totalhaber;
    }

  
    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
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

    public int getTipotargeta() {
        return tipotargeta;
    }

    public void setTipotargeta(int tipotargeta) {
        this.tipotargeta = tipotargeta;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public int getTipocomprobante() {
        return tipocomprobante;
    }

    public void setTipocomprobante(int tipocomprobante) {
        this.tipocomprobante = tipocomprobante;
    }

    
    

    
    
}
