package modelo;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "GEN_EAPB")
public class GenEapb implements Serializable {

    @Column(name = "CODIGO", table = "GEN_EAPB", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String codigo;

    @Column(name = "USUARIO_MODIFICADOR", table = "GEN_EAPB", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION", table = "GEN_EAPB", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;

    @Column(name = "NIT", table = "GEN_EAPB", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String nit;

    @Column(name = "FECHA_CREACION", table = "GEN_EAPB", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;

    @Column(name = "ID", table = "GEN_EAPB", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_GEN_EAPB", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_GEN_EAPB", sequenceName = "SEQ_GEN_EAPB", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "NOMBRE", table = "GEN_EAPB", unique = false, updatable = true, insertable = true, nullable = true, length = 200, scale = 0, precision = 0)
    @Basic
    private String nombre;

    @Column(name = "REGIMEN", table = "GEN_EAPB", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String regimen;

    @Column(name = "USUARIO_CREADOR", table = "GEN_EAPB", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioCreador;
     private String direccion;
    private String telefono;
      @Column(length = 10)
    private String codigomovilidad;
     @Column(length = 5)
    private String codigoregionalmov;
      @Column(length = 5)
    private String codigoregionalcont;
    public GenEapb() {

    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUsuarioModificador() {
        return this.usuarioModificador;
    }

    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }

    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getNit() {
        return this.nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRegimen() {
        return this.regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getUsuarioCreador() {
        return this.usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

  
   

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
  

    
  

   

    public String getCodigomovilidad() {
        return codigomovilidad;
    }

    public void setCodigomovilidad(String codigomovilidad) {
        this.codigomovilidad = codigomovilidad;
    }

    public String getCodigoregionalmov() {
        return codigoregionalmov;
    }

    public void setCodigoregionalmov(String codigoregionalmov) {
        this.codigoregionalmov = codigoregionalmov;
    }

    public String getCodigoregionalcont() {
        return codigoregionalcont;
    }

    public void setCodigoregionalcont(String codigoregionalcont) {
        this.codigoregionalcont = codigoregionalcont;
    }

     
}
