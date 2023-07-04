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

@Entity
@Table(name = "AGN_MEDICOS")

public class AgnMedicos implements Serializable {

    @Column(name = "USUARIO_MODIFICADOR", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;

    @Column(name = "NO_CONSULTORIO", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String noConsultorio;

    @Column(name = "DOCUMENTO", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String documento;

    @Column(name = "FECHA_CREACION", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;

    @Column(name = "ID", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_AGN_MEDICOS", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_AGN_MEDICOS", sequenceName = "SEQ_AGN_MEDICOS", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "NUMERO_REGISTRO", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String numeroRegistro;

    @Column(name = "NOMBRE", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = true, length = 200, scale = 0, precision = 0)
    @Basic
    private String nombre;

    @Column(name = "USUARIO_CREADOR", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioCreador;

    //@Column(name = "GEN_UNIDADES_ORGANIZACION_ID", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenUnidadesOrganizacion.class)
    @JoinColumn(name="GEN_UNIDADES_ORGANIZACION_ID")
    private GenUnidadesOrganizacion genUnidadesOrganizacion;
    
    //@Column(name = "GEN_PERSONAS_ID", table = "AGN_MEDICOS", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenPersonas.class)
    @JoinColumn(name="GEN_PERSONAS_ID")
    private GenPersonas genPersonas;
    @OneToMany(mappedBy="agnMedicos",fetch = FetchType.EAGER,orphanRemoval = true)
    private List<AgnMedicosEspecialidades> medicosEspecialidadeses = new ArrayList<AgnMedicosEspecialidades>();
    public AgnMedicos() {

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

    public String getNoConsultorio() {
        return this.noConsultorio;
    }

    public void setNoConsultorio(String noConsultorio) {
        this.noConsultorio = noConsultorio;
    }

    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    

    public String getNumeroRegistro() {
        return this.numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuarioCreador() {
        return this.usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenUnidadesOrganizacion getGenUnidadesOrganizacion() {
        return genUnidadesOrganizacion;
    }

    public void setGenUnidadesOrganizacion(GenUnidadesOrganizacion genUnidadesOrganizacion) {
        this.genUnidadesOrganizacion = genUnidadesOrganizacion;
    }

    public GenPersonas getGenPersonas() {
        return genPersonas;
    }

    public void setGenPersonas(GenPersonas genPersonas) {
        this.genPersonas = genPersonas;
    }

    public List<AgnMedicosEspecialidades> getMedicosEspecialidadeses() {
        return medicosEspecialidadeses;
    }

    public void setMedicosEspecialidadeses(List<AgnMedicosEspecialidades> medicosEspecialidadeses) {
        this.medicosEspecialidadeses = medicosEspecialidadeses;
    }
   public String getEspecialidades()
   {
       String especialidades="";
       for(AgnMedicosEspecialidades me: medicosEspecialidadeses)
       {
           especialidades=especialidades+me.getAgnEspecialidades().getNombre()+" Código:"+me.getAgnEspecialidades().getCodigo()+" ";
       }
       return especialidades;
   } 
   public void addespecialidad(AgnEspecialidades agnEspecialidades)
   {
       AgnMedicosEspecialidades me=new AgnMedicosEspecialidades();
       if(getMedicosEspecialidadeses().size()>0)
       {
       for(AgnMedicosEspecialidades item: medicosEspecialidadeses)
       {
           if(item.getAgnEspecialidades().equals(agnEspecialidades))
           {
               
               return;
           }
       }
       }
      
            me.setAgnEspecialidades(agnEspecialidades);
            me.setAgnMedicos(this);
            medicosEspecialidadeses.add(me);
       
   }  
   public void removeespecialidad(AgnEspecialidades agnEspecialidades)
   {
       
       if(getMedicosEspecialidadeses().size()>0)
       {
       for(AgnMedicosEspecialidades item: medicosEspecialidadeses)
       {
           if(item.getAgnEspecialidades().equals(agnEspecialidades))
           {
              medicosEspecialidadeses.remove(item);
              return;
           }
       }
       }
      
         
       
   } 
   public String nombreEspecialidades()
   {
       String especialidades="";
       for(AgnMedicosEspecialidades e: medicosEspecialidadeses)
       {
          especialidades =especialidades+"["+e.getAgnEspecialidades().getNombre()+"]";
       }
       return especialidades;
   }
    
}
