/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author adminlinux
 */
@Entity
public class Bodega {
    @javax.persistence.SequenceGenerator(
            name = "SEQ_ID_BOD",
            sequenceName = "SEQ_ID_BOD",
            allocationSize = 1
    )
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_BOD")
    @Column(name = "ID")
    private Long id;
    private String dir;
    private String nombre;
    private String tel;
    private String latitud;
    private String longitud;
    private String codigo;
    private String responsable;
     private String email;
    @ManyToOne(optional = true, targetEntity = GenMunicipios.class)
    @JoinColumn(name="GENMUNICIPIOS_ID")
    private GenMunicipios genMunicipios;
    @ManyToOne(optional = true, targetEntity = Usuarios.class)
    @JoinColumn(name="USUARIOS_ID")
    private Usuarios usuarios;
    private int tipo;//0: bodega 1: almacen
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
     
}
