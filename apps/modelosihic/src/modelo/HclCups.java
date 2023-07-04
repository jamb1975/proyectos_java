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

/**
 *
 * @author adminlinux
 */
@Entity
@Table(name = "HclCups")
public class HclCups implements Serializable {
 @Column(name = "ID", table = "HclCups", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
 @Id
 @GeneratedValue(generator = "SEQ_HCLCUPS_ID", strategy = GenerationType.SEQUENCE)
 @SequenceGenerator(name = "SEQ_HCLCUPS_ID", sequenceName = "SEQ_HCLCUPS_ID", allocationSize = 1, initialValue = 1)
  private  Long id;
  private  String codigo;
  private  String descripcion;
  private String recomendaciones_especificas;
  private Long gen_recomendacion_especiali_id;
  private Long gen_procedimientos_apoyo_id;
  private Long atencion;
  @ManyToOne
  @JoinColumn(name = "agnEspecialidades_id")
  private AgnEspecialidades agnEspecialidades;
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
   @OneToMany(fetch = FetchType.EAGER,mappedBy="hclCups", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<HclCupsMedicamentos> medicamentosItems = new ArrayList<HclCupsMedicamentos>();
   
     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getRecomendaciones_especificas() {
        return recomendaciones_especificas;
    }

    public void setRecomendaciones_especificas(String recomendaciones_especificas) {
        this.recomendaciones_especificas = recomendaciones_especificas;
    }

    public Long getGen_recomendacion_especiali_id() {
        return gen_recomendacion_especiali_id;
    }

    public void setGen_recomendacion_especiali_id(Long gen_recomendacion_especiali_id) {
        this.gen_recomendacion_especiali_id = gen_recomendacion_especiali_id;
    }

    public Long getGen_procedimientos_apoyo_id() {
        return gen_procedimientos_apoyo_id;
    }

    public void setGen_procedimientos_apoyo_id(Long gen_procedimientos_apoyo_id) {
        this.gen_procedimientos_apoyo_id = gen_procedimientos_apoyo_id;
    }

    public Long getAtencion() {
        return atencion;
    }

    public void setAtencion(Long atencion) {
        this.atencion = atencion;
    }

    public AgnEspecialidades getAgnEspecialidades() {
        return agnEspecialidades;
    }

    public void setAgnEspecialidades(AgnEspecialidades agnEspecialidades) {
        this.agnEspecialidades = agnEspecialidades;
    }

   

    
    public void AddMedicamentos(Producto producto)
    {
        HclCupsMedicamentos hclCupsMedicamentos=new HclCupsMedicamentos();
        for(HclCupsMedicamentos cm :medicamentosItems)
        {
            if(cm.getProducto().equals(producto))
            {
                return;
            }
            
        }
        hclCupsMedicamentos.setProducto(producto);
        hclCupsMedicamentos.setHclCups(this);
        medicamentosItems.add(hclCupsMedicamentos);
    }
     public void removeMedicamentos(Producto producto)
    {
       for(HclCupsMedicamentos cm :medicamentosItems)
        {
            if(cm.getProducto().equals(producto))
            { 
                medicamentosItems.remove(cm);
                return;
            }
            
        }
    }

    public List<HclCupsMedicamentos> getMedicamentosItems() {
        return medicamentosItems;
    }

    public void setMedicamentosItems(List<HclCupsMedicamentos> medicamentosItems) {
        this.medicamentosItems = medicamentosItems;
    }
    
}
