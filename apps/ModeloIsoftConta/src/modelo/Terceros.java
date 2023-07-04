package modelo;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.NotEmpty;

@Entity
@XmlRootElement
@Table(name = "TERCEROS")
public class Terceros  implements Serializable  { 
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ID", table = "TERCEROS", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_TERCEROS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_TERCEROS_ID", sequenceName = "SEQ_TERCEROS_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @Column(length = 50)
    private String primernombre;
    @Column(length = 50)
    private String otrosnombres;
    @Column(unique = true,length = 50)
    private String no_ident;
    private String dir1;
    private String tel_fijo;
    private String celular;
    private byte[] foto;
    private String sexo;
    private boolean cal=false;
    private String profesion;
    @Column(length = 4)
    private String codigoactivadadeconomica;
    @Email
    @Column(length = 100)
    private String email;
    @Column(length = 2)//11 REGISTRO CIVIL 12 TI 13 CC 21 TEXTRAN CEDEXTRANJ 31 NIT 41PASAPORTE 42 DIEXTRN 50 NIT DE OTRO PAIS 91 NUIP*
    private String tipoidentificacion;
    @Column(length = 1)//0 nit 1 cedula 2 otro
    private String digitoverificacion;
    @Column(length = 1)//1 natural 2 juridica
    private String tipopersona;
    @Column(length = 1)//0 natural 1 juridica
    private String tipotercero;//0 cliente //1 proveedor //empleado
    @Column(length = 40)
    private String primerapellido;
    @Column(length = 40)
    private String segundoapellido;
    private String razonsocial;
    private boolean responsableiva=false;
    @ManyToOne(optional = true, targetEntity = Puc.class)
    //cuentas causar gastos 
    @JoinColumn(name="CUENTADEBITOGASTOS_ID")
    private Puc cuentadebitogastos;
    @ManyToOne(optional = true, targetEntity = Puc.class)
    @JoinColumn(name="CUENTACREDITOGASTOS_ID")
    private Puc cuentacreditogastos;
    //cuenta causar egresos
    @JoinColumn(name="CUENTADEBITOEGRESOS_ID")
    private Puc cuentadebitoegresos;
    @ManyToOne(optional = true, targetEntity = Puc.class)
    @JoinColumn(name="CUENTACREDITOEGRESOS_ID")
    private Puc cuentacreditoegresos;
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
    @ManyToOne(cascade = CascadeType.ALL)
    private GenMunicipios ciudad;
    @Column(length = 2)
    private String codigoresponsabilidad="";
    @Column(length = 200)
    private String descripcionresponsabilidad="";
    private boolean esautoretenedor=false;
    private boolean esgrancontribuyente=false;
    private boolean esagenteretenedoriva=false;
     @Column(length = 50, unique = true)
    private String nuidenttributaria="";
 	public Long getId(){
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the password
	 */
	
	
	/**
	 * @return the nombre
	 */
	
	/**
	 * @return the tipo_doc
	 */
	
	
	/**
	 * @return the no_ident
	 */
        
    @NotEmpty
    @Column(unique = true)
	public String getNo_ident() {
		return no_ident;
	}
	/**
	 * @param no_ident the no_ident to set
	 */
	public void setNo_ident(String no_ident){
		this.no_ident = no_ident;
	}
	
	/**
	 * @return the dir1
	 */
	public String getDir1() {
		return dir1;
	}
	/**
	 * @param dir1 the dir1 to set
	 */
	public void setDir1(String dir1) {
		this.dir1 = dir1;
	}
	/**
	 * @return the tel_fijo
	 */
	public String getTel_fijo() {
		return tel_fijo;
	}
	/**
	 * @param tel_fijo the tel_fijo to set
	 */
	public void setTel_fijo(String tel_fijo) {
		this.tel_fijo = tel_fijo;
	}
	/**
	 * @return the celular
	 */
	public String getCelular() {
		return celular;
	}
	/**
	 * @param celular the celular to set
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}

	
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	
public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getSexo() {
		return sexo;
	}
	
	
	
	
	
	@Lob
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	
	
	public boolean isCal() {
		return cal;
	}
	public void setCal(boolean cal) {
		this.cal = cal;
	}

   

    public Terceros() {
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

    public String getTipoidentificacion() {
        return tipoidentificacion;
    }

    public void setTipoidentificacion(String tipoidentificacion) {
        this.tipoidentificacion = tipoidentificacion;
    }

    public String getTipopersona() {
        return tipopersona;
    }

    public void setTipopersona(String tipopersona) {
        this.tipopersona = tipopersona;
    }

    public String getPrimerapellido() {
        return primerapellido;
    }

    public void setPrimerapellido(String primerapellido) {
        this.primerapellido = primerapellido;
    }

    public String getSegundoapellido() {
        return segundoapellido;
    }

    public void setSegundoapellido(String segundoapellido) {
        this.segundoapellido = segundoapellido;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public boolean isResponsableiva() {
        return responsableiva;
    }

    public void setResponsableiva(boolean responsableiva) {
        this.responsableiva = responsableiva;
    }

    public Puc getCuentadebitogastos() {
        return cuentadebitogastos;
    }

    public void setCuentadebitogastos(Puc cuentadebitogastos) {
        this.cuentadebitogastos = cuentadebitogastos;
    }

    public Puc getCuentacreditogastos() {
        return cuentacreditogastos;
    }

    public void setCuentacreditogastos(Puc cuentacreditogastos) {
        this.cuentacreditogastos = cuentacreditogastos;
    }

    public Puc getCuentadebitoegresos() {
        return cuentadebitoegresos;
    }

    public void setCuentadebitoegresos(Puc cuentadebitoegresos) {
        this.cuentadebitoegresos = cuentadebitoegresos;
    }

    public Puc getCuentacreditoegresos() {
        return cuentacreditoegresos;
    }

    public void setCuentacreditoegresos(Puc cuentacreditoegresos) {
        this.cuentacreditoegresos = cuentacreditoegresos;
    }

    public String getDigitoverificacion() {
        return digitoverificacion;
    }

    public void setDigitoverificacion(String digitoverificacion) {
        this.digitoverificacion = digitoverificacion;
    }

    public String getTipotercero() {
        return tipotercero;
    }

    public void setTipotercero(String tipotercero) {
        this.tipotercero = tipotercero;
    }

    public GenMunicipios getCiudad() {
        return ciudad;
    }

    public void setCiudad(GenMunicipios ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoactivadadeconomica() {
        return codigoactivadadeconomica;
    }

    public void setCodigoactivadadeconomica(String codigoactivadadeconomica) {
        this.codigoactivadadeconomica = codigoactivadadeconomica;
    }
    
   @Transient
   public String getNombres()
   {
       if(primernombre!=null)
       {
         if(primernombre.length()>0)
       {  
       return primernombre+" "+primerapellido+" "+segundoapellido;
       }
       else
       {
            return razonsocial; 
       }
       }else
       {
            return razonsocial; 
       }
   }

    public String getCodigoresponsabilidad() {
        return codigoresponsabilidad;
    }

    public void setCodigoresponsabilidad(String codigoresponsabilidad) {
        this.codigoresponsabilidad = codigoresponsabilidad;
    }

    public String getDescripcionresponsabilidad() {
        return descripcionresponsabilidad;
    }

    public void setDescripcionresponsabilidad(String descripcionresponsabilidad) {
        this.descripcionresponsabilidad = descripcionresponsabilidad;
    }

    public boolean isEsautoretenedor() {
        return esautoretenedor;
    }

    public void setEsautoretenedor(boolean esautoretenedor) {
        this.esautoretenedor = esautoretenedor;
    }

    public boolean isEsgrancontribuyente() {
        return esgrancontribuyente;
    }

    public void setEsgrancontribuyente(boolean esgrancontribuyente) {
        this.esgrancontribuyente = esgrancontribuyente;
    }

    public String getNuidenttributaria() {
        return nuidenttributaria;
    }

    public void setNuidenttributaria(String nuidenttributaria) {
        this.nuidenttributaria = nuidenttributaria;
    }

    public String getPrimernombre() {
        return primernombre;
    }

    public void setPrimernombre(String primernombre) {
        this.primernombre = primernombre;
    }

    public String getOtrosnombres() {
        return otrosnombres;
    }

    public void setOtrosnombres(String otrosnombres) {
        this.otrosnombres = otrosnombres;
    }

    public boolean isEsagenteretenedoriva() {
        return esagenteretenedoriva;
    }

    public void setEsagenteretenedoriva(boolean esagenteretenedoriva) {
        this.esagenteretenedoriva = esagenteretenedoriva;
    }
   
    }

   
   

    

   

   

    

    
	


