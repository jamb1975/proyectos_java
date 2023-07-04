package modelo;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
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

@Entity
@Table(name = "GEN_PERSONAS")
public class GenPersonas implements Serializable {
    @Column(name = "SEGUNDO_NOMBRE", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String segundoNombre;

    @Column(name = "BARRIO", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private String barrio;

    @Column(name = "USUARIO_MODIFICADOR", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioModificador;

    @Column(name = "FECHA_MODIFICACION", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;

    @Column(name = "PRIMER_NOMBRE", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String primerNombre;

    @Column(name = "PRIMER_APELLIDO", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String primerApellido;

    @Column(name = "FECHA_NACIMIENTO", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaNacimiento;

    @Column(name = "DIRECCION", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Basic
    private String direccion;

    @Column(name = "SEGUNDO_APELLIDO", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String segundoApellido;

    @Column(name = "DOCUMENTO", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String documento;

    @Column(name = "USUARIO_CREADOR", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 50, scale = 0, precision = 0)
    @Basic
    private String usuarioCreador;

    @Column(name = "E_MAIL", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String eMail;

    @Column(name = "FOTO", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 100, scale = 0, precision = 0)
    @Basic
    private String foto;

    @Column(name = "OBSERVACIONES", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 1000, scale = 0, precision = 0)
    @Basic
    private String observaciones;

    @Column(name = "FECHA_CREACION", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 255, scale = 0, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;

    @Column(name = "ID", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_GEN_PERSONAS", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_GEN_PERSONAS", sequenceName = "SEQ_GEN_PERSONAS", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "TELEFONO", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true, length = 40, scale = 0, precision = 0)
    @Basic
    private String telefono;
    
   // @Column(name = "GEN_ZONA_RESIDENCIA_ID", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenZonaResidencia.class,fetch = FetchType.LAZY)
     @JoinColumn(name="GEN_ZONA_RESIDENCIA_ID")
    private GenZonaResidencia gen_zona_residencia;
   
   // @Column(name = "GEN_PROFESIONES_ID", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenProfesiones.class,fetch = FetchType.LAZY)
    @JoinColumn(name="GEN_PROFESIONES_ID")
    private GenProfesiones gen_profesiones;
     
    @ManyToOne(optional = true, targetEntity = GenNivelesEducativos.class,fetch = FetchType.LAZY)
    @JoinColumn(name="GEN_NIVELES_EDUCATIVOS_ID")
    private GenNivelesEducativos genNivelesEducativos;
    // @Column(name = "GEN_ESTADOS_CIVILES_ID", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenEstadosCiviles.class,fetch = FetchType.LAZY)
    @JoinColumn(name="GEN_ESTADOS_CIVILES_ID")
    private GenEstadosCiviles genEstadosCiviles;
     
  //  @Column(name = "GEN_ETNIAS_ID", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenEtnias.class,fetch = FetchType.LAZY)
     @JoinColumn(name="GEN_ETNIAS_ID")
    private GenEtnias genEtnias;
      
     
    @ManyToOne(optional = true, targetEntity = GenMunicipios.class,fetch = FetchType.LAZY)
     @JoinColumn(name="GEN_MUNICIPIOS_ID")
    private GenMunicipios genMunicipios;
    
     @ManyToOne(optional = true, targetEntity = GenTiposDocumentos.class,fetch = FetchType.LAZY)
      @JoinColumn(name="GEN_TIPOS_DOCUMENTOS_ID")
    private GenTiposDocumentos genTiposDocumentos;
     
     
   
   // @Column(name = "GEN_SEXO_ID", table = "GEN_PERSONAS", unique = false, updatable = true, insertable = true, nullable = true)
    @ManyToOne(optional = true, targetEntity = GenSexo.class,fetch = FetchType.LAZY)
    @JoinColumn(name="GEN_SEXO_ID")
    private GenSexo genSexo;
    @ManyToOne(fetch = FetchType.LAZY)
    private InformacionTributaria informacionTributaria; 
     
    @Transient
    private String nombres;
    public GenPersonas() {

    }

    public String getSegundoNombre() {
        return this.segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getBarrio() {
        return this.barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
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

    public String getPrimerNombre() {
        return this.primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getUsuarioCreador() {
        return this.usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getEMail() {
        return this.eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getFoto() {
        return this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public GenEstadosCiviles getGenEstadosCiviles() {
        return genEstadosCiviles;
    }

    public void setGenEstadosCiviles(GenEstadosCiviles genEstadosCiviles) {
        this.genEstadosCiviles = genEstadosCiviles;
    }

    public GenEtnias getGenEtnias() {
        return genEtnias;
    }

    public void setGenEtnias(GenEtnias genEtnias) {
        this.genEtnias = genEtnias;
    }

    public GenMunicipios getGenMunicipios() {
        return genMunicipios;
    }

    public void setGenMunicipios(GenMunicipios genMunicipios) {
        this.genMunicipios = genMunicipios;
    }

    public GenTiposDocumentos getGenTiposDocumentos() {
        return genTiposDocumentos;
    }

    public void setGenTiposDocumentos(GenTiposDocumentos genTiposDocumentos) {
        this.genTiposDocumentos = genTiposDocumentos;
    }

    public GenSexo getGenSexo() {
        return genSexo;
    }

    public void setGenSexo(GenSexo genSexo) {
        this.genSexo = genSexo;
    }

    

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public GenZonaResidencia getGen_zona_residencia() {
        return gen_zona_residencia;
    }

    public void setGen_zona_residencia(GenZonaResidencia gen_zona_residencia) {
        this.gen_zona_residencia = gen_zona_residencia;
    }

    public GenProfesiones getGen_profesiones() {
        return gen_profesiones;
    }

    public void setGen_profesiones(GenProfesiones gen_profesiones) {
        this.gen_profesiones = gen_profesiones;
    }

    public GenNivelesEducativos getGenNivelesEducativos() {
        return genNivelesEducativos;
    }

    public void setGenNivelesEducativos(GenNivelesEducativos genNivelesEducativos) {
        this.genNivelesEducativos = genNivelesEducativos;
    }

    public String getNombres() {
        if(segundoNombre==null)
        {
            segundoNombre="";
        }
        if(segundoApellido==null)
        {
            segundoApellido="";
        }
        nombres=primerNombre +" "+segundoNombre+" "+primerApellido+" "+segundoApellido;
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String year()
    {
       
       Date fechaDeHoy = new Date();
       SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
       String fecha = formato.format(fechaDeHoy);
       String[] fechaHoy = fecha.split("/");
       Integer diaHoy = Integer.parseInt(fechaHoy[0]);
       Integer mesHoy = Integer.parseInt(fechaHoy[1]);
       Integer anioHoy = Integer.parseInt(fechaHoy[2]);
       String fechaAnterior =formato.format(fechaNacimiento);
       String[] fechaAnt = fechaAnterior.split("/");
       Integer diaAnt = Integer.parseInt(fechaAnt[0]);
       Integer mesAnt = Integer.parseInt(fechaAnt[1]);
       Integer anioAnt = Integer.parseInt(fechaAnt[2]);
       if((anioHoy-anioAnt)>0)
       { 
           int year =anioHoy-anioAnt;
           if(mesHoy<mesAnt)
           {
               year=year-1;
               if(year==0)
               {
                  return String.valueOf((12-(mesAnt))+mesHoy); 
               }
           }
            return String.valueOf(year);
       }
       else
       {
         if((mesHoy-mesAnt)>0)
       {
            return String.valueOf(mesHoy-mesAnt);
       } 
         else
       {
         if((diaHoy-diaAnt)>0)
       {
            return String.valueOf(diaHoy-diaAnt);
       } 
       }
       }
     return String.valueOf(0);
                
    }
    public String unidad_medida_edad()
    {
       
       Date fechaDeHoy = new Date();
       SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
       String fecha = formato.format(fechaDeHoy);
       String[] fechaHoy = fecha.split("/");
       Integer diaHoy = Integer.parseInt(fechaHoy[0]);
       Integer mesHoy = Integer.parseInt(fechaHoy[1]);
       Integer anioHoy = Integer.parseInt(fechaHoy[2]);
       String fechaAnterior =formato.format(fechaNacimiento);
       String[] fechaAnt = fechaAnterior.split("/");
       Integer diaAnt = Integer.parseInt(fechaAnt[0]);
       Integer mesAnt = Integer.parseInt(fechaAnt[1]);
       Integer anioAnt = Integer.parseInt(fechaAnt[2]);
       if((anioHoy-anioAnt)>0)
       {
           int year =anioHoy-anioAnt;
           if(mesHoy<mesAnt)
           {
               year=year-1;
               if(year==0)
               {
                  return String.valueOf(2); 
               }
           }
            return String.valueOf(1);
       }
       else
       {
         if((mesHoy-mesAnt)>0)
       {
            return String.valueOf(2);
       } 
         else
       {
         if((diaHoy-diaAnt)>0)
       {
            return String.valueOf(3);
       } 
       }
       }
     return String.valueOf(0);
                
    }

    public InformacionTributaria getInformacionTributaria() {
        return informacionTributaria;
    }

    public void setInformacionTributaria(InformacionTributaria informacionTributaria) {
        this.informacionTributaria = informacionTributaria;
    }

    
    
}
