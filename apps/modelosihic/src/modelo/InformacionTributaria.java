/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author adminlinux
 */
@Entity
public class InformacionTributaria implements Serializable {
  
    @Id
    @GeneratedValue(generator = "SEQ_INFORMACIONTRIBUTARIA_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_INFORMACIONTRIBUTARIA_ID", sequenceName = "SEQ_INFORMACIONTRIBUTARIA_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    private boolean regimenespecial;
    private boolean contribuyente;
    private boolean autoretenedor;
    private boolean declarante;
    private boolean exento;
    private int entidad;
    private int tipoactoadministrativo_regimenespecial;
    private int tipoactoadministrativo_contribuyente;
    private int tipoactoadministrativo_autoretenedor;
    private int tipoactoadministrativo_declarante;
    private int tipoactoadministrativo_exento;
    private String tipoactoadministrativo_regimenespecial_numero;
    private String tipoactoadministrativo_contribuyente_numero;
    private String tipoactoadministrativo_autoretenedor_numero;
    private String tipoactoadministrativo_declarante_numero;
    private String tipoactoadministrativo_exento_numero;  
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date tipoactoadministrativo_regimenespecial_fecha;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date tipoactoadministrativo_contribuyente_fecha;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date tipoactoadministrativo_autoretenedor_fecha;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date tipoactoadministrativo_declarante_fecha;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date tipoactoadministrativo_exento_fecha;
    private boolean responsableiva;
    private boolean grancontribuyente;
    private int tipoactoadministrativo_grancontribuyente;
    private String tipoactoadministrativo_grancontribuyente_numero;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date tipoactoadministrativo_grancontribuyente_fecha;
    private boolean serviciocomprabogota;
    private boolean domiciliobogota;
   
    private boolean factura;
    private String resolucionfacturacion_numero;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date resolucionfacturacion_fecha;
    private int tiporegimen;
  @ManyToOne
  private ActividadesEconomicas actividadesEconomicas;
   @ManyToOne
  private ActividadesEconomicas actividadesEconomicas_renta;
  private boolean impuestotimbre;
  private int tipoactoadministrativoimpuestotimbre_exento;
  private String tipoactoadministrativoimpuestotimbre_exento_numero;
   @Temporal(TemporalType.TIMESTAMP)
    @Basic
  private Date tipoactoadministrativoimpuestotimbre_exento_fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRegimenespecial() {
        return regimenespecial;
    }

    public void setRegimenespecial(boolean regimenespecial) {
        this.regimenespecial = regimenespecial;
    }

    public boolean isContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(boolean contribuyente) {
        this.contribuyente = contribuyente;
    }

    public boolean isAutoretenedor() {
        return autoretenedor;
    }

    public void setAutoretenedor(boolean autoretenedor) {
        this.autoretenedor = autoretenedor;
    }

    public boolean isDeclarante() {
        return declarante;
    }

    public void setDeclarante(boolean declarante) {
        this.declarante = declarante;
    }

    public boolean isExento() {
        return exento;
    }

    public void setExento(boolean exento) {
        this.exento = exento;
    }

    public int getTipoactoadministrativo_regimenespecial() {
        return tipoactoadministrativo_regimenespecial;
    }

    public void setTipoactoadministrativo_regimenespecial(int tipoactoadministrativo_regimenespecial) {
        this.tipoactoadministrativo_regimenespecial = tipoactoadministrativo_regimenespecial;
    }

    public int getTipoactoadministrativo_contribuyente() {
        return tipoactoadministrativo_contribuyente;
    }

    public void setTipoactoadministrativo_contribuyente(int tipoactoadministrativo_contribuyente) {
        this.tipoactoadministrativo_contribuyente = tipoactoadministrativo_contribuyente;
    }

    public int getTipoactoadministrativo_autoretenedor() {
        return tipoactoadministrativo_autoretenedor;
    }

    public void setTipoactoadministrativo_autoretenedor(int tipoactoadministrativo_autoretenedor) {
        this.tipoactoadministrativo_autoretenedor = tipoactoadministrativo_autoretenedor;
    }

    public int getTipoactoadministrativo_declarante() {
        return tipoactoadministrativo_declarante;
    }

    public void setTipoactoadministrativo_declarante(int tipoactoadministrativo_declarante) {
        this.tipoactoadministrativo_declarante = tipoactoadministrativo_declarante;
    }

    public int getTipoactoadministrativo_exento() {
        return tipoactoadministrativo_exento;
    }

    public void setTipoactoadministrativo_exento(int tipoactoadministrativo_exento) {
        this.tipoactoadministrativo_exento = tipoactoadministrativo_exento;
    }

    public String getTipoactoadministrativo_regimenespecial_numero() {
        return tipoactoadministrativo_regimenespecial_numero;
    }

    public void setTipoactoadministrativo_regimenespecial_numero(String tipoactoadministrativo_regimenespecial_numero) {
        this.tipoactoadministrativo_regimenespecial_numero = tipoactoadministrativo_regimenespecial_numero;
    }

    public String getTipoactoadministrativo_contribuyente_numero() {
        return tipoactoadministrativo_contribuyente_numero;
    }

    public void setTipoactoadministrativo_contribuyente_numero(String tipoactoadministrativo_contribuyente_numero) {
        this.tipoactoadministrativo_contribuyente_numero = tipoactoadministrativo_contribuyente_numero;
    }

    public String getTipoactoadministrativo_autoretenedor_numero() {
        return tipoactoadministrativo_autoretenedor_numero;
    }

    public void setTipoactoadministrativo_autoretenedor_numero(String tipoactoadministrativo_autoretenedor_numero) {
        this.tipoactoadministrativo_autoretenedor_numero = tipoactoadministrativo_autoretenedor_numero;
    }

    public String getTipoactoadministrativo_declarante_numero() {
        return tipoactoadministrativo_declarante_numero;
    }

    public void setTipoactoadministrativo_declarante_numero(String tipoactoadministrativo_declarante_numero) {
        this.tipoactoadministrativo_declarante_numero = tipoactoadministrativo_declarante_numero;
    }

    public String getTipoactoadministrativo_exento_numero() {
        return tipoactoadministrativo_exento_numero;
    }

    public void setTipoactoadministrativo_exento_numero(String tipoactoadministrativo_exento_numero) {
        this.tipoactoadministrativo_exento_numero = tipoactoadministrativo_exento_numero;
    }

    public Date getTipoactoadministrativo_regimenespecial_fecha() {
        return tipoactoadministrativo_regimenespecial_fecha;
    }

    public void setTipoactoadministrativo_regimenespecial_fecha(Date tipoactoadministrativo_regimenespecial_fecha) {
        this.tipoactoadministrativo_regimenespecial_fecha = tipoactoadministrativo_regimenespecial_fecha;
    }

    public Date getTipoactoadministrativo_contribuyente_fecha() {
        return tipoactoadministrativo_contribuyente_fecha;
    }

    public void setTipoactoadministrativo_contribuyente_fecha(Date tipoactoadministrativo_contribuyente_fecha) {
        this.tipoactoadministrativo_contribuyente_fecha = tipoactoadministrativo_contribuyente_fecha;
    }

    public Date getTipoactoadministrativo_autoretenedor_fecha() {
        return tipoactoadministrativo_autoretenedor_fecha;
    }

    public void setTipoactoadministrativo_autoretenedor_fecha(Date tipoactoadministrativo_autoretenedor_fecha) {
        this.tipoactoadministrativo_autoretenedor_fecha = tipoactoadministrativo_autoretenedor_fecha;
    }

    public Date getTipoactoadministrativo_declarante_fecha() {
        return tipoactoadministrativo_declarante_fecha;
    }

    public void setTipoactoadministrativo_declarante_fecha(Date tipoactoadministrativo_declarante_fecha) {
        this.tipoactoadministrativo_declarante_fecha = tipoactoadministrativo_declarante_fecha;
    }

    public Date getTipoactoadministrativo_exento_fecha() {
        return tipoactoadministrativo_exento_fecha;
    }

    public void setTipoactoadministrativo_exento_fecha(Date tipoactoadministrativo_exento_fecha) {
        this.tipoactoadministrativo_exento_fecha = tipoactoadministrativo_exento_fecha;
    }

    public boolean isResponsableiva() {
        return responsableiva;
    }

    public void setResponsableiva(boolean responsableiva) {
        this.responsableiva = responsableiva;
    }

    public boolean isGrancontribuyente() {
        return grancontribuyente;
    }

    public void setGrancontribuyente(boolean grancontribuyente) {
        this.grancontribuyente = grancontribuyente;
    }

    public int getTipoactoadministrativo_grancontribuyente() {
        return tipoactoadministrativo_grancontribuyente;
    }

    public void setTipoactoadministrativo_grancontribuyente(int tipoactoadministrativo_grancontribuyente) {
        this.tipoactoadministrativo_grancontribuyente = tipoactoadministrativo_grancontribuyente;
    }

    public String getTipoactoadministrativo_grancontribuyente_numero() {
        return tipoactoadministrativo_grancontribuyente_numero;
    }

    public void setTipoactoadministrativo_grancontribuyente_numero(String tipoactoadministrativo_grancontribuyente_numero) {
        this.tipoactoadministrativo_grancontribuyente_numero = tipoactoadministrativo_grancontribuyente_numero;
    }

    public Date getTipoactoadministrativo_grancontribuyente_fecha() {
        return tipoactoadministrativo_grancontribuyente_fecha;
    }

    public void setTipoactoadministrativo_grancontribuyente_fecha(Date tipoactoadministrativo_grancontribuyente_fecha) {
        this.tipoactoadministrativo_grancontribuyente_fecha = tipoactoadministrativo_grancontribuyente_fecha;
    }

    public boolean isServiciocomprabogota() {
        return serviciocomprabogota;
    }

    public void setServiciocomprabogota(boolean serviciocomprabogota) {
        this.serviciocomprabogota = serviciocomprabogota;
    }

    public int getTiporegimen() {
        return tiporegimen;
    }

    public void setTiporegimen(int tiporegimen) {
        this.tiporegimen = tiporegimen;
    }

    public ActividadesEconomicas getActividadesEconomicas() {
        return actividadesEconomicas;
    }

    public void setActividadesEconomicas(ActividadesEconomicas actividadesEconomicas) {
        this.actividadesEconomicas = actividadesEconomicas;
    }

    public ActividadesEconomicas getActividadesEconomicas_renta() {
        return actividadesEconomicas_renta;
    }

    public void setActividadesEconomicas_renta(ActividadesEconomicas actividadesEconomicas_renta) {
        this.actividadesEconomicas_renta = actividadesEconomicas_renta;
    }

    public int getEntidad() {
        return entidad;
    }

    public void setEntidad(int entidad) {
        this.entidad = entidad;
    }

    public boolean isFactura() {
        return factura;
    }

    public void setFactura(boolean factura) {
        this.factura = factura;
    }

    public String getResolucionfacturacion_numero() {
        return resolucionfacturacion_numero;
    }

    public void setResolucionfacturacion_numero(String resolucionfacturacion_numero) {
        this.resolucionfacturacion_numero = resolucionfacturacion_numero;
    }

    public Date getResolucionfacturacion_fecha() {
        return resolucionfacturacion_fecha;
    }

    public void setResolucionfacturacion_fecha(Date resolucionfacturacion_fecha) {
        this.resolucionfacturacion_fecha = resolucionfacturacion_fecha;
    }

    public boolean isDomiciliobogota() {
        return domiciliobogota;
    }

    public void setDomiciliobogota(boolean domiciliobogota) {
        this.domiciliobogota = domiciliobogota;
    }

    public boolean isImpuestotimbre() {
        return impuestotimbre;
    }

    public void setImpuestotimbre(boolean impuestotimbre) {
        this.impuestotimbre = impuestotimbre;
    }

    public int getTipoactoadministrativoimpuestotimbre_exento() {
        return tipoactoadministrativoimpuestotimbre_exento;
    }

    public void setTipoactoadministrativoimpuestotimbre_exento(int tipoactoadministrativoimpuestotimbre_exento) {
        this.tipoactoadministrativoimpuestotimbre_exento = tipoactoadministrativoimpuestotimbre_exento;
    }

    public String getTipoactoadministrativoimpuestotimbre_exento_numero() {
        return tipoactoadministrativoimpuestotimbre_exento_numero;
    }

    public void setTipoactoadministrativoimpuestotimbre_exento_numero(String tipoactoadministrativoimpuestotimbre_exento_numero) {
        this.tipoactoadministrativoimpuestotimbre_exento_numero = tipoactoadministrativoimpuestotimbre_exento_numero;
    }

    public Date getTipoactoadministrativoimpuestotimbre_exento_fecha() {
        return tipoactoadministrativoimpuestotimbre_exento_fecha;
    }

    public void setTipoactoadministrativoimpuestotimbre_exento_fecha(Date tipoactoadministrativoimpuestotimbre_exento_fecha) {
        this.tipoactoadministrativoimpuestotimbre_exento_fecha = tipoactoadministrativoimpuestotimbre_exento_fecha;
    }
    
    
}
