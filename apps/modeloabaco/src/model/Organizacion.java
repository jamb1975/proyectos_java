/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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

/**
 *
 * @author karol
 */
@Entity
@XmlRootElement
public class Organizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private String dir;
    private String tel;
    private String celular;
    private String nit;
    private String sigla;
    private String codigo;
    @Column(length = 1)//c=comun s=simplificado
    private String tiporegimen;
    @ManyToOne(optional = true, targetEntity = GenMunicipios.class)
    @JoinColumn(name = "GEN_MUNICIPIOS_ID")
    private GenMunicipios genMunicipios;
    @ManyToOne(optional = true, targetEntity = Usuarios.class)
    @JoinColumn(name = "USUARIOS_ID")
    private Usuarios usuarios;
    @Lob
    private byte[] logo;
    @javax.persistence.SequenceGenerator(
            name = "SEQ_ID_ORG",
            sequenceName = "SEQ_ID_ORG",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_ORG")
    @Column(name = "ID")
    private Long id;

    private String emailpedidos;
    private String passwordemailpedidos;
    private String email;
    private String passwordemail;
    private String emailbdproductos;
    private String passwordemailbdproductos;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenMunicipios getGenMunicipios() {
        return genMunicipios;
    }

    public void setGenMunicipios(GenMunicipios genMunicipios) {
        this.genMunicipios = genMunicipios;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String getTiporegimen() {
        return tiporegimen;
    }

    public void setTiporegimen(String tiporegimen) {
        this.tiporegimen = tiporegimen;
    }

    public String getEmailpedidos() {
        return emailpedidos;
    }

    public void setEmailpedidos(String emailpedidos) {
        this.emailpedidos = emailpedidos;
    }

    public String getPasswordemailpedidos() {
        return passwordemailpedidos;
    }

    public void setPasswordemailpedidos(String passwordemailpedidos) {
        this.passwordemailpedidos = passwordemailpedidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordemail() {
        return passwordemail;
    }

    public void setPasswordemail(String passwordemail) {
        this.passwordemail = passwordemail;
    }

    public String getEmailbdproductos() {
        return emailbdproductos;
    }

    public void setEmailbdproductos(String emailbdproductos) {
        this.emailbdproductos = emailbdproductos;
    }

    public String getPasswordemailbdproductos() {
        return passwordemailbdproductos;
    }

    public void setPasswordemailbdproductos(String passwordemailbdproductos) {
        this.passwordemailbdproductos = passwordemailbdproductos;
    }

   

}
