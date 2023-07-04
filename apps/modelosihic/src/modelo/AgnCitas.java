package modelo;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "AGN_CITAS")

public class AgnCitas implements Serializable {

    @Column(name = "horaFin", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date horaFin;

    @Column(name = "USUARIO_MODIFICADOR", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;

    @Column(name = "NO_CONSULTORIO", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true, length = 20, scale = 0, precision = 0)
    @Basic
    private String noConsultorio;

    @Column(name = "FECHA_HORA", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaHora;

    @Column(name = "FECHA_INGRESO", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaIngreso;
    @Column(name = "FECHA_CREACION", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;

    @Column(name = "ID", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_AGN_CITAS", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_AGN_CITAS", sequenceName = "SEQ_AGN_CITAS", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "horaInicio", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date horaInicio;

    @Column(name = "USUARIO_CREADOR", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioCreador;
    
    
   // @Column(name = "AGN_MEDICOS_ID", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = AgnMedicos.class)
     @JoinColumn(name="AGN_MEDICOS_ID")
    private AgnMedicos agnMedicos;

  //  @Column(name = "GEN_PACIENTES_ID", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenPacientes.class,cascade = CascadeType.ALL)
     @JoinColumn(name="GEN_PACIENTES_ID")
    private GenPacientes genPacientes;
    
    //@Column(name = "AGN_ESTADOS_CITA_ID", table = "AGN_CITAS", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = AgnEstadosCita.class)
     @JoinColumn(name="AGN_ESTADOS_CITA_ID")
    private AgnEstadosCita agnEstadosCita;
    
    @ManyToOne(optional = true, targetEntity = GenHorasCita.class,cascade = CascadeType.ALL)
    @JoinColumn(name="GEN_HORAS_CITAS")
    private GenHorasCita genHorasCita;
    
    @ManyToOne(optional = true, targetEntity = Producto.class,cascade = CascadeType.MERGE)
    @JoinColumn(name="SERVICIO_ID")
    private Producto servicio;
   
@ManyToOne(optional = true, targetEntity = AgnConsultorios.class)
    @JoinColumn(name="AGNCONSULTORIOS_ID")
    private AgnConsultorios agnConsultorios;    
@Transient
    private LocalDate fechahora;
    public AgnCitas() {

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

    public Date getFechaHora() {
        return this.fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

  

   

    public String getUsuarioCreador() {
        return this.usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

   

    public AgnMedicos getAgnMedicos() {
        return agnMedicos;
    }

    public void setAgnMedicos(AgnMedicos agnMedicos) {
        this.agnMedicos = agnMedicos;
    }

    public GenPacientes getGenPacientes() {
        return genPacientes;
    }

    public void setGenPacientes(GenPacientes genPacientes) {
        this.genPacientes = genPacientes;
    }

    public AgnEstadosCita getAgnEstadosCita() {
        return agnEstadosCita;
    }

    public void setAgnEstadosCita(AgnEstadosCita agnEstadosCita) {
        this.agnEstadosCita = agnEstadosCita;
    }

    public GenHorasCita getGenHorasCita() {
        return genHorasCita;
    }

    public void setGenHorasCita(GenHorasCita genHorasCita) {
        this.genHorasCita = genHorasCita;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Producto getServicio() {
        return servicio;
    }

    public void setServicio(Producto servicio) {
        this.servicio = servicio;
    }

 

    public AgnConsultorios getAgnConsultorios() {
        return agnConsultorios;
    }

    public void setAgnConsultorios(AgnConsultorios agnConsultorios) {
        this.agnConsultorios = agnConsultorios;
    }

    public LocalDate getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDate fechahora) {
        this.fechahora = fechahora;
    }
    
    
}
