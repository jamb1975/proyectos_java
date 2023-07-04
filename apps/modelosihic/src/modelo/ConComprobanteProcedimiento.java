/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karol
 */
@Entity
@XmlRootElement
@Table(name = "ConComprobanteProcedimiento")
public class ConComprobanteProcedimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    
   
 @Column(name = "ID", table = "ConComprobanteProcedimiento", unique = true, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_COMPROBANTEPROCEDIMIENTO_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_COMPROBANTEPROCEDIMIENTO_ID", sequenceName = "SEQ_COMPROBANTEPROCEDIMIENTO_ID", allocationSize = 1, initialValue = 1)
    private Long id;
        
    @ManyToOne
    @JoinColumn(name="CONSECUTIVOCOMPROBANTEPROCEDIMIENTO_ID")
    private ConsecutivoComprobanteProcedimiento consecutivoComprobanteProcedimiento;
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
     @OneToMany(fetch = FetchType.LAZY,mappedBy="conComprobanteProcedimiento", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<FacturaItem> facturaItems = new ArrayList<FacturaItem>();

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

    public ConsecutivoComprobanteProcedimiento getConsecutivoComprobanteProcedimiento() {
        return consecutivoComprobanteProcedimiento;
    }

    public void setConsecutivoComprobanteProcedimiento(ConsecutivoComprobanteProcedimiento consecutivoComprobanteProcedimiento) {
        this.consecutivoComprobanteProcedimiento = consecutivoComprobanteProcedimiento;
    }

    public List<FacturaItem> getFacturaItems() {
        return facturaItems;
    }

    public void setFacturaItems(List<FacturaItem> facturaItems) {
        this.facturaItems = facturaItems;
    }

  public void AddProcedimiento(FacturaItem facturaItem)
  {
      facturaItem.setConComprobanteProcedimiento(this);
      facturaItems.add(facturaItem);
      
  }

    

    

    
}
