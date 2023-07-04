package model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;

@Entity
@XmlRootElement

public class Terceros  implements Serializable  { 
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    @Column(name = "EMAIL")
    private String email;
   @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_TER",
		     sequenceName="SEQ_ID_TER",
		     allocationSize=1
		    )
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_TER")
	@Column(name="ID_TER")	
  
    private Long id;
    private String password;
    private String nombre;
    @NotEmpty
    @Column(unique = true)
    private String no_ident;
    private String lugar_exp;
    private String tipoDoc;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha_nac;
    private String dir1;
    private String tel_fijo;
    private String celular;
    private byte[] foto;
    private String sexo;
    private boolean cal=false;
    private String profesion;
    @Temporal(javax.persistence.TemporalType.DATE)
     private Date fecha;
     private String rh;
	private int edad;
	private String est_civil;
	private String nacionalidad;
	private String pais;
	private String dpto;
	private String ciudad;
	private int tipo_terc;
    @Id
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
	
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
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
	 * @return the lugar_exp
	 */
	public String getLugar_exp() {
		return lugar_exp;
	}
	/**
	 * @param lugar_exp the lugar_exp to set
	 */
	public void setLugar_exp(String lugar_exp) {
		this.lugar_exp = lugar_exp;
	}
	/**
	 * @return the fecha_nac
	 */
        
	public Date getFecha_nac() {
		return fecha_nac;
	}
	/**
	 * @param fecha_nac the fecha_nac to set
	 */
	public void setFecha_nac(Date fecha_nac) {
		this.fecha_nac = fecha_nac;
	}
	/**
	 * @return the edad
	 */
	@Transient
	public int getEdad() {
		return edad;
	}
	/**
	 * @param edad the edad to set
	 */
	public void setEdad(int edad) {
		this.edad = edad;
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
	
	
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
      public Date getFecha() {
		return fecha;
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
	public String getRh() {
		return rh;
	}
	public void setRh(String rh) {
		this.rh = rh;
	}
	public String getEst_civil() {
		return est_civil;
	}
	public void setEst_civil(String est_civil) {
		this.est_civil = est_civil;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
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

    public int getTipo_terc() {
        return tipo_terc;
    }

    public void setTipo_terc(int tipo_terc) {
        this.tipo_terc = tipo_terc;
    }

    public Terceros() {
    }

   
   

    

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

   

    

    
	

}
