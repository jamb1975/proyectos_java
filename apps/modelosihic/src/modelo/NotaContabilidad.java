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
@Table(name = "NotaContabilidad", uniqueConstraints = {
           @UniqueConstraint(columnNames = {"no_consecutivo"})
   })
public class NotaContabilidad implements Serializable {
    private static final long serialVersionUID = 1L;
    
   
    @Column(name = "ID", table = "NotaContabilidad", unique = true, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_NOTACONTABILIDAD_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_NOTACONTABILIDAD_ID", sequenceName = "SEQ_NOTACONTABILIDAD_ID", allocationSize = 1, initialValue = 1)
    private Long id;
     
    
    @ManyToOne
    @JoinColumn(name="PROVEEDORES_ID")
    private Proveedores proveedores;
    
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
    @Column(length = 3000)
    private String detalle;
    @Column(length = 2)
    private String tiponota;
    private String observaciones;
    private Long no_consecutivo;
    @Transient
    private String nocomprobantecerosizquierda;
    @ManyToOne
    @JoinColumn(name="NOMINA_ID")
     private Nomina nomina;
     @OneToMany(fetch = FetchType.LAZY,mappedBy="notaContabilidad", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<LibroAuxiliar> li_liLibroAuxiliars = new ArrayList<LibroAuxiliar>();
  
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

    

 
   

    public String getConcepto() {
        return detalle;
    }

    public void setConcepto(String concepto) {
        this.detalle = concepto;
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

   
 public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
 

   
    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

   

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Long getNo_consecutivo() {
        return no_consecutivo;
    }

    public void setNo_consecutivo(Long no_consecutivo) {
        this.no_consecutivo = no_consecutivo;
    }

    public Nomina getNomina() {
        return nomina;
    }

    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

    public String getTiponota() {
        return tiponota;
    }

    public void setTiponota(String tiponota) {
        this.tiponota = tiponota;
    }

    public List<LibroAuxiliar> getLi_liLibroAuxiliars() {
        return li_liLibroAuxiliars;
    }

    public void setLi_liLibroAuxiliars(List<LibroAuxiliar> li_liLibroAuxiliars) {
        this.li_liLibroAuxiliars = li_liLibroAuxiliars;
    }

   
   public void addRegistrosCotables(Puc puc,String concepto,boolean tipomovimiento,BigDecimal valor,Date fechaelab)
   {
           LibroAuxiliar au=null;
           au=new LibroAuxiliar();
           au.setConPuc(puc);
           au.setFechaelaboracion(fechaelaboracion);
           au.setDescripcion(concepto);
           au.setNotaContabilidad(this);
           if(tipomovimiento)
           {
               au.setDebe(valor);
               au.setHaber(BigDecimal.ZERO);
           }
           else
           {
                au.setHaber(valor);
                au.setDebe(BigDecimal.ZERO);
           }
           li_liLibroAuxiliars.add(au);
   
   }        
    
    public void deleteRegistrosCotables(LibroAuxiliar au)
   {
     for(LibroAuxiliar au2: li_liLibroAuxiliars)
     {
         if(au2.getId().equals(au.getId()))
         {
             li_liLibroAuxiliars.remove(au2);
             return;
         }    
     }
   } 
  public BigDecimal totaldebito()
  {
      BigDecimal total=BigDecimal.ZERO;
      for(LibroAuxiliar au:li_liLibroAuxiliars)
      {
          total=total.add(au.getDebe());
      }
      return total;
  }
  public BigDecimal totalcredito()
  {
      BigDecimal total=BigDecimal.ZERO;
      for(LibroAuxiliar au:li_liLibroAuxiliars)
      {
          total=total.add(au.getHaber());
      }
      return total;
  }
  public boolean verificarigualdadendebitoycredito()
  {
     return totalcredito().compareTo(totaldebito())==0 ;
  }
}
