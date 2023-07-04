/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karol
 */
@Entity
@XmlRootElement
@Table(name = "DocumentoSoporte",uniqueConstraints = {
           @UniqueConstraint(columnNames = {"tipodocsoporte", "no_consecutivo"}),
           @UniqueConstraint(columnNames = {"prefijo", "no_factura"})
   })
public class DocumentoSoporte implements Serializable {
    private static final long serialVersionUID = 1L;
    
   
 @Column(name = "ID", table = "DocumentoSoporte", unique = true, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_DOCUMENTOSOPORTE_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_DOCUMENTOSOPORTE_ID", sequenceName = "SEQ_DOCUMENTOSOPORTE_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @ManyToOne(optional = true, targetEntity = Terceros.class,cascade = CascadeType.MERGE)
    @JoinColumn(name="CLIENTE_ID")
    private Terceros clientes;
    private Long no_consecutivo;
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
    @Column(nullable = true,length = 15)
    private String numerodocumentosoporte;
    @Column(length = 3)
    private String prefijo="";
    private Long no_factura;
    private BigDecimal valorbase=BigDecimal.ZERO;
    private BigDecimal valorneto=BigDecimal.ZERO;
    private String concepto;
    @Column(length = 1)
    private String sucursales;//0 sanjose 1 v/cio 2 acacias 3 granada
    @Column(length = 1)
    private String centrocosto;//0 compras 1 produccion 2 ventas 3 administracion
    @Transient
    private String nocomprobantecerosizquierda;
    private int tipodocsoporte;
    @OneToMany(fetch = FetchType.LAZY,mappedBy="documentoSoporte", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<LibroAuxiliar> li_libroauxiliar = new ArrayList<>();
    @Transient
    private BigDecimal totaldebe=BigDecimal.ZERO;
    @Transient
    private BigDecimal totalhaber=BigDecimal.ZERO;
    @Transient
    private BigDecimal totalabonos=BigDecimal.ZERO;
    @Transient
    private String nofacturacerosizquierda;
    private int tipoingreso;
     @ManyToOne(optional = true, targetEntity = DocumentoSoporte.class,cascade = CascadeType.MERGE)
    @JoinColumn(name="DOCUMENTOSOPORTE_ROOT_ID")
    private DocumentoSoporte documentoSoporte;
    @OneToMany(fetch = FetchType.LAZY,mappedBy="documentoSoporte", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<DocumentoSoporte> li_documentosoporte = new ArrayList<>();
 @Transient
 boolean ingresar;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getValorbase() {
        return valorbase;
    }

    public void setValorbase(BigDecimal valorbase) {
        this.valorbase = valorbase;
    }

    public BigDecimal getValorneto() {
        return valorneto;
    }

    public void setValorneto(BigDecimal valorneto) {
        this.valorneto = valorneto;
    }

    

    

  

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getNocomprobantecerosizquierda() {
     String    cerosizqcomprobante="";
      for(int i=6;i>no_consecutivo.toString().length();i--)
     {
         cerosizqcomprobante=cerosizqcomprobante+"0";
     }
     nocomprobantecerosizquierda=cerosizqcomprobante+no_consecutivo.toString();
     return nocomprobantecerosizquierda;
    }

    

    public Long getNo_consecutivo() {
        return no_consecutivo;
    }

    public void setNo_consecutivo(Long no_consecutivo) {
        this.no_consecutivo = no_consecutivo;
    }

    public Terceros getClientes() {
        return clientes;
    }

    public void setClientes(Terceros clientes) {
        this.clientes = clientes;
    }

    public String getNumerodocumentosoporte() {
        return numerodocumentosoporte;
    }

    public void setNumerodocumentosoporte(String numerodocumentosoporte) {
        this.numerodocumentosoporte = numerodocumentosoporte;
    }

   

    public String getSucursales() {
        return sucursales;
    }

    public void setSucursales(String sucursales) {
        this.sucursales = sucursales;
    }

    public String getCentrocosto() {
        return centrocosto;
    }

    public void setCentrocosto(String centrocosto) {
        this.centrocosto = centrocosto;
    }

    public int getTipodocsoporte() {
        return tipodocsoporte;
    }

    public void setTipodocsoporte(int tipodocsoporte) {
        this.tipodocsoporte = tipodocsoporte;
    }

    public List<LibroAuxiliar> getLi_libroauxiliar() {
        return li_libroauxiliar;
    }

    public void setLi_libroauxiliar(List<LibroAuxiliar> li_libroauxiliar) {
        this.li_libroauxiliar = li_libroauxiliar;
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

    public void agregarregistroalibroauxiliar(Puc puc,boolean debeohaber,String descripcion,BigDecimal valor,Date fechaelaboracion)
    {
       LibroAuxiliar libroAuxiliar=null;
       libroAuxiliar=new LibroAuxiliar();
       libroAuxiliar.setConPuc(puc);
       libroAuxiliar.setDescripcion(descripcion);
       libroAuxiliar.setFechaelaboracion(fechaelaboracion);
       if(debeohaber)
       {
           libroAuxiliar.setDebe(valor);
       }
       else
       {
           libroAuxiliar.setHaber(valor);
       }
       libroAuxiliar.setDocumentoSoporte(this);
       li_libroauxiliar.add(libroAuxiliar);
       calculartotalesdebehaber();
    }
    public void agregarregistroalibroauxiliar(Puc puc,boolean debeohaber,String descripcion,BigDecimal valor,Date fechaelaboracion,Terceros tercero,String nofactura)
    {
       LibroAuxiliar libroAuxiliar=null;
       libroAuxiliar=new LibroAuxiliar();
       libroAuxiliar.setConPuc(puc);
       libroAuxiliar.setDescripcion(descripcion);
       libroAuxiliar.setFechaelaboracion(fechaelaboracion);
       libroAuxiliar.setNofactura(nofactura);
       if(tercero!=null)
      {  
      if(tercero.getTipopersona()!=null)
      {
       if(tercero.getTipopersona().length()>1)
       {
           tercero.setTipopersona("0");
       }
      }
      if(tercero.getTipoidentificacion()!=null)
      {
        if(tercero.getTipoidentificacion().length()>1)
       {
           tercero.setTipoidentificacion("0");
       }
      }
      }
       libroAuxiliar.setTerceros(tercero);
       if(debeohaber)
       {
           libroAuxiliar.setDebe(valor);
       }
       else
       {
           libroAuxiliar.setHaber(valor);
       }
       libroAuxiliar.setDocumentoSoporte(this);
       li_libroauxiliar.add(libroAuxiliar);
       calculartotalesdebehaber();
    }
    public void agregarregistroalibroauxiliar(Puc puc,boolean debeohaber,String descripcion,BigDecimal valor,Date fechaelaboracion,Terceros tercero)
    {
       LibroAuxiliar libroAuxiliar=null;
       libroAuxiliar=new LibroAuxiliar();
       libroAuxiliar.setConPuc(puc);
       libroAuxiliar.setDescripcion(descripcion);
       libroAuxiliar.setFechaelaboracion(fechaelaboracion);
       if(tercero!=null)
      {  
      if(tercero.getTipopersona()!=null)
      {
       if(tercero.getTipopersona().length()>1)
       {
           tercero.setTipopersona("0");
       }
      }
      if(tercero.getTipoidentificacion()!=null)
      {
        if(tercero.getTipoidentificacion().length()>1)
       {
           tercero.setTipoidentificacion("0");
       }
      }
      }
       libroAuxiliar.setTerceros(tercero);
       if(debeohaber)
       {
           libroAuxiliar.setDebe(valor);
       }
       else
       {
           libroAuxiliar.setHaber(valor);
       }
       libroAuxiliar.setDocumentoSoporte(this);
       li_libroauxiliar.add(libroAuxiliar);
       calculartotalesdebehaber();
    }
     public void agregarregistroalibroauxiliar(Puc puc,boolean debeohaber,String descripcion,BigDecimal valor,Date fechaelaboracion,Terceros tercero,Long id_source_mov)
    {
       LibroAuxiliar libroAuxiliar=null;
       libroAuxiliar=new LibroAuxiliar();
       libroAuxiliar.setConPuc(puc);
       libroAuxiliar.setDescripcion(descripcion);
       libroAuxiliar.setFechaelaboracion(fechaelaboracion);
       libroAuxiliar.setId_source_mov(id_source_mov);
       if(tercero!=null)
      {  
      if(tercero.getTipopersona()!=null)
      {
       if(tercero.getTipopersona().length()>1)
       {
           tercero.setTipopersona("0");
       }
      }
      if(tercero.getTipoidentificacion()!=null)
      {
        if(tercero.getTipoidentificacion().length()>1)
       {
           tercero.setTipoidentificacion("0");
       }
      }
      }
       libroAuxiliar.setTerceros(tercero);
       if(debeohaber)
       {
           libroAuxiliar.setDebe(valor);
       }
       else
       {
           libroAuxiliar.setHaber(valor);
       }
       libroAuxiliar.setDocumentoSoporte(this);
       li_libroauxiliar.add(libroAuxiliar);
       calculartotalesdebehaber();
    }
    public void modificarregistroalibroauxiliar(LibroAuxiliar libroAuxiliar)
    {
       int index=li_libroauxiliar.indexOf(libroAuxiliar);
       li_libroauxiliar.set(index, libroAuxiliar);
       calculartotalesdebehaber();
    }
    public void removerregistroalibroauxiliar(LibroAuxiliar libroAuxiliar)
    {
     
       li_libroauxiliar.remove(libroAuxiliar);
       calculartotalesdebehaber();
    }
    
    public void calculartotalesdebehaber()
    {
         totaldebe=BigDecimal.ZERO;
        totalhaber=BigDecimal.ZERO;
        for(LibroAuxiliar la:li_libroauxiliar)
        {
            totaldebe=totaldebe.add(la.getDebe());
            totalhaber=totalhaber.add(la.getHaber());
        }
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public Long getNo_factura() {
        return no_factura;
    }

    public void setNo_factura(Long no_factura) {
        this.no_factura = no_factura;
    }
    
   public String getNofacturacerosizquierda() {
        
        String cerosizq="";
     
     for(int i=4;i>no_factura.toString().length();i--)
     {
         cerosizq=cerosizq+"0";
     }
     
        nofacturacerosizquierda=prefijo+cerosizq+no_factura.toString();
        return nofacturacerosizquierda;
    } 

    public int getTipoingreso() {
        return tipoingreso;
    }

    public void setTipoingreso(int tipoingreso) {
        this.tipoingreso = tipoingreso;
    }

    public DocumentoSoporte getDocumentoSoporte() {
        return documentoSoporte;
    }

    public void setDocumentoSoporte(DocumentoSoporte documentoSoporte) {
        this.documentoSoporte = documentoSoporte;
    }

    public List<DocumentoSoporte> getLi_documentosoporte() {
        return li_documentosoporte;
    }

    public void setLi_documentosoporte(List<DocumentoSoporte> li_documentosoporte) {
        this.li_documentosoporte = li_documentosoporte;
    }
    public void agregarregistroabonos(DocumentoSoporte documentoSoporte)
    {
       for(DocumentoSoporte ds:li_documentosoporte)
       {
           if(ds.getId().equals(documentoSoporte.getId()))
           {
               ds=documentoSoporte;
               calculartotalabonos();
               return;
           }  
       }
       documentoSoporte.setDocumentoSoporte(this);
       li_documentosoporte.add(documentoSoporte);
       calculartotalabonos();
    }
    public void modificarregistroabonos(DocumentoSoporte documentoSoporte)
    {
       int index=li_documentosoporte.indexOf(documentoSoporte);
       li_documentosoporte.set(index, documentoSoporte);
       calculartotalesdebehaber();
    }
    public void removerregistroabonos(DocumentoSoporte documentoSoporte)
    {
     
       li_documentosoporte.remove(documentoSoporte);
       calculartotalesdebehaber();
    }
    
    public void calculartotalabonos()
    {
        totalabonos=BigDecimal.ZERO;
        
        for(DocumentoSoporte ds:li_documentosoporte)
        {
            totalabonos=totalabonos.add(ds.valorneto);
           
        }
    }

    public BigDecimal getTotalabonos() {
        return totalabonos;
    }

    public void setTotalabonos(BigDecimal totalabonos) {
        this.totalabonos = totalabonos;
    }

    public boolean isIngresar() {
        return ingresar;
    }

    public void setIngresar(boolean ingresar) {
        this.ingresar = ingresar;
    }
    
}
