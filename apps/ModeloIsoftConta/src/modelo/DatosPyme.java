/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author isoft
 */
@Entity
@XmlRootElement
@Table(name = "DatosPyme")
public class DatosPyme {
    @Column(name = "ID", table = "DatosPyme", unique = true, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_DATOSPYME_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_DATOSPYME_ID", sequenceName = "SEQ_DATOSPYME_ID", allocationSize = 1, initialValue = 1)
    private Long id; 
    @Column(length = 50, unique = true)
    private String nuidenttributaria="";
     @Column(length = 1)//0 nit 1 cedula 2 otro
    private String digitoverificacion;
    @Column(length = 1)
    private String dverificacion="";
    @Column(length = 1)
    private String tipopersona="";//0 natural 1 Jurídica
    @Column(length = 50, unique = true)
    private String numeroidentificacion="";
    @Column(length = 500)
    private String razonsocial="";
    @Column(length = 500)
    private String nombrecomercial="";
    @Column(length = 20)
    private String sigla="";
    @ManyToOne
    private GenMunicipios genMunicipios;
    @Column(length = 500)
    private String direccion="";
    @Column(length = 100)
    private String email="";
    @Column(length = 10)
    private String telefono1="";
    @Column(length = 10)
    private String telefono2="";
    @Column(length = 10)
    private String codigopostal="";
    @Column(length = 4)
    private String codigoactividadeconomica="";
    @Column(length = 4)
    private String codigoactividadeconomicasecundaria="";
    @Column(length = 2)
    private String codigoresponsabilidad="";
    @Column(length = 200)
    private String descripcionresponsabilidad="";
    private boolean esautoretenedor=false;
    private boolean esgrancontribuyente=false;
    private boolean esagenteretenedoriva=false;
    @Column(length = 100)
    private String primerapellido="";
    @Column(length = 100)
    private String segundoapellido="";
    @Column(length = 100)
    private String primernombre="";
    @Column(length = 100)
    private String otrosnombres="";
     @Lob
     private byte[]logo;
    @ManyToOne(cascade = CascadeType.ALL)
    private GenMunicipios ciudad;
    @Column(length = 2)//11 REGISTRO CIVIL 12 TI 13 CC 21 TEXTRAN CEDEXTRANJ 31 NIT 41PASAPORTE 42 DIEXTRN 50 NIT DE OTRO PAIS 91 NUIP*
    private String tipoidentificacion;
    @Column(length = 4)
    private String codigoactivadadeconomica;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNuidenttributaria() {
        return nuidenttributaria;
    }

    public void setNuidenttributaria(String nuidenttributaria) {
        this.nuidenttributaria = nuidenttributaria;
    }

    public String getDverificacion() {
        return dverificacion;
    }

    public void setDverificacion(String dverificacion) {
        this.dverificacion = dverificacion;
    }

    public String getTipopersona() {
        return tipopersona;
    }

    public void setTipopersona(String tipopersona) {
        this.tipopersona = tipopersona;
    }

    public String getNumeroidentificacion() {
        return numeroidentificacion;
    }

    public void setNumeroidentificacion(String numeroidentificacion) {
        this.numeroidentificacion = numeroidentificacion;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getNombrecomercial() {
        return nombrecomercial;
    }

    public void setNombrecomercial(String nombrecomercial) {
        this.nombrecomercial = nombrecomercial;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public GenMunicipios getGenMunicipios() {
        return genMunicipios;
    }

    public void setGenMunicipios(GenMunicipios genMunicipios) {
        this.genMunicipios = genMunicipios;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getCodigoactividadeconomica() {
        return codigoactividadeconomica;
    }

    public void setCodigoactividadeconomica(String codigoactividadeconomica) {
        this.codigoactividadeconomica = codigoactividadeconomica;
    }

    public String getCodigoactividadeconomicasecundaria() {
        return codigoactividadeconomicasecundaria;
    }

    public void setCodigoactividadeconomicasecundaria(String codigoactividadeconomicasecundaria) {
        this.codigoactividadeconomicasecundaria = codigoactividadeconomicasecundaria;
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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getDigitoverificacion() {
        return digitoverificacion;
    }

    public void setDigitoverificacion(String digitoverificacion) {
        this.digitoverificacion = digitoverificacion;
    }

    public boolean isEsagenteretenedoriva() {
        return esagenteretenedoriva;
    }

    public void setEsagenteretenedoriva(boolean esagenteretenedoriva) {
        this.esagenteretenedoriva = esagenteretenedoriva;
    }

    public GenMunicipios getCiudad() {
        return ciudad;
    }

    public void setCiudad(GenMunicipios ciudad) {
        this.ciudad = ciudad;
    }

    public String getTipoidentificacion() {
        return tipoidentificacion;
    }

    public void setTipoidentificacion(String tipoidentificacion) {
        this.tipoidentificacion = tipoidentificacion;
    }

    public String getCodigoactivadadeconomica() {
        return codigoactivadadeconomica;
    }

    public void setCodigoactivadadeconomica(String codigoactivadadeconomica) {
        this.codigoactivadadeconomica = codigoactivadadeconomica;
    }
   
   
    
    
}
