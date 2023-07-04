package modelo;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;

@Entity
@XmlRootElement
public class Proveedores  implements Serializable  { 
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    	
    @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_PROV",
		     sequenceName="SEQ_ID_PROV",
		     allocationSize=1
		    )
    @Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_PROV")
    @Column(name="ID")
    private Long id;
    private String nombre;
   @NotEmpty
    @Column(unique = true)
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
    @Email
    private String email;
    @ManyToOne(optional = true, targetEntity = Usuarios.class)
    @JoinColumn(name="USUARIOS_ID")
    private Usuarios usuarios;
   
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

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

   

    public Proveedores() {
    }

   
   

    

   

   

    

    
	

}
