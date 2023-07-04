package modelo;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.Email;
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.NotEmpty;

@Entity
@XmlRootElement
@Table(name = "PROVEEDORES")
public class Proveedores  implements Serializable  { 
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    	
    @Column(name = "ID", table = "PROVEEDORES", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_PROVEEDORES_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PROVEEDORES_ID", sequenceName = "SEQ_PROVEEDORES_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    private String nombre;
    private String no_ident;
    private String dir1;
    private String tel_fijo;
    private String celular;
    private byte[] foto;
    private String sexo;
    private boolean cal=false;
    private String profesion;
    private String pais;
    private String dpto;
    private String ciudad;
    private String email;
    @Column(length = 1)//0 nit 1 cedula 2 otro
    private String tipoidentificacion;
    @Column(length = 1)//0 nit 1 cedula 2 otro
    private String digitoverificacion;
    @Column(length = 1)//0 natural 1 juridica
    private String tipopersona;
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
    @ManyToOne
    private InformacionTributaria informacionTributaria;
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
	@NotEmpty
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
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

	@Email
        @Column(name = "EMAIL")
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
	
	public String getDpto() {
		return dpto;
	}
	public void setDpto(String dpto) {
		this.dpto = dpto;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public boolean isCal() {
		return cal;
	}
	public void setCal(boolean cal) {
		this.cal = cal;
	}

   

    public Proveedores() {
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

   

    public InformacionTributaria getInformacionTributaria() {
        return informacionTributaria;
    }

    public void setInformacionTributaria(InformacionTributaria informacionTributaria) {
        this.informacionTributaria = informacionTributaria;
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

   
    }

   
   

    

   

   

    

    
	


