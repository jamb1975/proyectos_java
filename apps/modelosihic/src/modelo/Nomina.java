/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karol
 */
@Entity
@XmlRootElement
@Table(name = "Nomina",uniqueConstraints = {
           @UniqueConstraint(columnNames = {"periodoinicio", "periodofin"})
   })
public class Nomina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "ID", table = "Nomina", unique = true, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_NOMINA_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_NOMINA_ID", sequenceName = "SEQ_NOMINA_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER,optional = true, targetEntity = ParametrosNomina.class)
    @JoinColumn(name="PARAMETROSNOMINA_ID")
    private ParametrosNomina parametrosNomina;
    @Column(name = "USUARIO_MODIFICADOR",length = 50)
    @Basic
    private String usuarioModificador;
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
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date periodoinicio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date periodofin;
    @Column(scale=2)
    private BigDecimal ss_salud=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal ss_fondopensiones=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal ss_arl=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal pf_cajacf=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal pf_icbf=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal pf_sena=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal pp_cesantias=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal pp_intcesantias=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal pp_prima=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal pp_vacaciones=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal totalbasico=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal totaldevengado=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal totalauxtransporte=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal totaldeducciones=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal totalotrosingresos=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal totalsalud=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal totalpension=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal totalotrasdeducciones=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal totalretefuente=BigDecimal.ZERO;
    @Column(scale=2)
    private BigDecimal totalsueldoneto=BigDecimal.ZERO;
    private boolean exonerado_pfsalud=true;
    private int horasdiarias=8;
    private String observaciones;
    @Column(unique = true)
    private Long no_consecutivo;
    @Transient
    private String nocomprobantecerosizquierda;
     @Transient
    private BigDecimal totalfondosolidaridad=BigDecimal.ZERO; 
    @OneToMany(fetch = FetchType.LAZY,mappedBy="nomina", cascade=CascadeType.ALL,orphanRemoval = true)
      //@OrderBy("conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id ASC")
      private List<NominaEmpleados> nominaempleadositems = new ArrayList<NominaEmpleados>();
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
 

    public String getUsuarioModificador() {
        return usuarioModificador;
    }

    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
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

    

 
   

    public String getNocomprobantecerosizquierda() {
        
     String    cerosizqcomprobante="";
     for(int i=6;i>no_consecutivo.toString().length();i--)
     {
         cerosizqcomprobante=cerosizqcomprobante+"0";
     }
     nocomprobantecerosizquierda=cerosizqcomprobante+no_consecutivo.toString();
     return nocomprobantecerosizquierda;
    }

   
 public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
 

    

    public Date getPeriodoinicio() {
        return periodoinicio;
    }

    public void setPeriodoinicio(Date periodoinicio) {
        this.periodoinicio = periodoinicio;
    }

    public Date getPeriodofin() {
        return periodofin;
    }

    public void setPeriodofin(Date periodofin) {
        this.periodofin = periodofin;
    }

    public BigDecimal getSs_salud() {
        return ss_salud;
    }

    public void setSs_salud(BigDecimal ss_salud) {
        this.ss_salud = ss_salud;
    }

    public BigDecimal getSs_fondopensiones() {
        return ss_fondopensiones;
    }

    public void setSs_fondopensiones(BigDecimal ss_fondopensiones) {
        this.ss_fondopensiones = ss_fondopensiones;
    }

    public BigDecimal getSs_arl() {
        return ss_arl;
    }

    public void setSs_arl(BigDecimal ss_arl) {
        this.ss_arl = ss_arl;
    }

    public BigDecimal getPf_cajacf() {
        return pf_cajacf;
    }

    public void setPf_cajacf(BigDecimal pf_cajacf) {
        this.pf_cajacf = pf_cajacf;
    }

    public BigDecimal getPf_icbf() {
        return pf_icbf;
    }

    public void setPf_icbf(BigDecimal pf_icbf) {
        this.pf_icbf = pf_icbf;
    }

    public BigDecimal getPf_sena() {
        return pf_sena;
    }

    public void setPf_sena(BigDecimal pf_sena) {
        this.pf_sena = pf_sena;
    }

    public BigDecimal getPp_cesantias() {
        return pp_cesantias;
    }

    public void setPp_cesantias(BigDecimal pp_cesantias) {
        this.pp_cesantias = pp_cesantias;
    }

    public BigDecimal getPp_intcesantias() {
        return pp_intcesantias;
    }

    public void setPp_intcesantias(BigDecimal pp_intcesantias) {
        this.pp_intcesantias = pp_intcesantias;
    }

    public BigDecimal getPp_prima() {
        return pp_prima;
    }

    public void setPp_prima(BigDecimal pp_prima) {
        this.pp_prima = pp_prima;
    }

    public BigDecimal getPp_vacaciones() {
        return pp_vacaciones;
    }

    public void setPp_vacaciones(BigDecimal pp_vacaciones) {
        this.pp_vacaciones = pp_vacaciones;
    }

    public boolean isExonerado_pfsalud() {
        return exonerado_pfsalud;
    }

    public void setExonerado_pfsalud(boolean exonerado_pfsalud) {
        this.exonerado_pfsalud = exonerado_pfsalud;
    }

    public int getHorasdiarias() {
        return horasdiarias;
    }

    public void setHorasdiarias(int horasdiarias) {
        this.horasdiarias = horasdiarias;
    }

    public ParametrosNomina getParametrosNomina() {
        return parametrosNomina;
    }

    public void setParametrosNomina(ParametrosNomina parametrosNomina) {
        this.parametrosNomina = parametrosNomina;
    }

    public List<NominaEmpleados> getNominaempleadositems() {
        return nominaempleadositems;
    }

    public void setNominaempleadositems(List<NominaEmpleados> nominaempleadositems) {
        this.nominaempleadositems = nominaempleadositems;
    }

    public void addempleado(NominaEmpleados nominaEmpleados)
    {
       
        for(NominaEmpleados ne:nominaempleadositems)
        {
            if(ne.getEmpleados().getId().equals(nominaEmpleados.getEmpleados().getId()))
            {   
                int index=nominaempleadositems.indexOf(ne); 
                calcular_valoresnominaempleados(ne);
                ne.setNo_consecutivo(nominaEmpleados.getNo_consecutivo());
                nominaempleadositems.set(index, ne);
                 calculartotales();
                return;
            }    
        }
        nominaEmpleados.setNomina(this);
        calcular_valoresnominaempleados(nominaEmpleados);
        nominaempleadositems.add(nominaEmpleados);
        calculartotales();
    }
    public void calculartotales()
    {
      totalauxtransporte=BigDecimal.ZERO;
      totalbasico=BigDecimal.ZERO;
      totaldevengado=BigDecimal.ZERO;
      totalsueldoneto=BigDecimal.ZERO;
      totalsalud=BigDecimal.ZERO;
      totalotrasdeducciones=BigDecimal.ZERO;
      totalpension=BigDecimal.ZERO;
      totalretefuente=BigDecimal.ZERO;
      totalotrosingresos=BigDecimal.ZERO;
      ss_arl=BigDecimal.ZERO;
      totalfondosolidaridad=BigDecimal.ZERO;
     for(NominaEmpleados ne: nominaempleadositems)
     {
         totalauxtransporte=totalauxtransporte.add(new BigDecimal(ne.getAuxiliotransporte().toBigInteger()));
         totalbasico=totalbasico.add(ne.getTotalbasico());
         totaldevengado=totaldevengado.add(ne.getTotaldevengado());
         totalotrosingresos=totalotrosingresos.add(new BigDecimal(ne.getValorcomisiones().toBigInteger()).add(new BigDecimal(ne.getValorhorasextras().toBigInteger())));
         totalotrasdeducciones=totalotrasdeducciones.add(new BigDecimal(ne.getValordeudaentidadfinancieras().toBigInteger()).add(new BigDecimal(ne.getValordeudaterceros().toBigInteger()).add(new BigDecimal(ne.getValorotrasdeudas().toBigInteger()))));
         totalretefuente=totalretefuente.add(new BigDecimal(ne.getValorretefuente().toBigInteger()));
         totalsueldoneto=totalsueldoneto.add(ne.getNetopagado());
         ss_arl=new BigDecimal(ss_arl.add(round((ne.getTotaldevengado().subtract(ne.getAuxiliotransporte())).multiply(new BigDecimal(ne.getEmpleados().getCargosEntidad().getPorc_arl().doubleValue()*(0.01))))).toBigInteger());
         totalfondosolidaridad=totalfondosolidaridad.add(ne.getFondosolidaridad());
     }
     ss_salud=new BigDecimal(round((totaldevengado.subtract(totalauxtransporte)).multiply(new BigDecimal(parametrosNomina.getPorc_salud_empleador().doubleValue()*(0.01)))).toBigInteger());
     ss_fondopensiones=new BigDecimal(round((totaldevengado.subtract(totalauxtransporte)).multiply(new BigDecimal(parametrosNomina.getPorc_fp().doubleValue()*(0.01)))).toBigInteger());
     pf_cajacf=new BigDecimal(round(totalbasico.multiply(new BigDecimal(parametrosNomina.getPorc_cajacompfam().doubleValue()*(0.01)))).toBigInteger());
     pf_icbf=new BigDecimal(round((totaldevengado.subtract(totalauxtransporte)).multiply(new BigDecimal(parametrosNomina.getPorc_icbf().doubleValue()*(0.01)))).toBigInteger());
     pf_sena=new BigDecimal(round((totaldevengado.subtract(totalauxtransporte)).multiply(new BigDecimal(parametrosNomina.getPorc_sena().doubleValue()*(0.01)))).toBigInteger());
     pp_cesantias=new BigDecimal(round(totaldevengado.multiply(new BigDecimal(parametrosNomina.getPorc_cesantias().doubleValue()*(0.01)))).toBigInteger());
     pp_intcesantias=new BigDecimal(round(pp_cesantias.multiply(new BigDecimal(parametrosNomina.getPorc_intcesantias().doubleValue()*(0.01)))).toBigInteger());
     pp_prima=new BigDecimal(round(totaldevengado.multiply(new BigDecimal(parametrosNomina.getPorc_prima().doubleValue()*(0.01)))).toBigInteger());
     pp_vacaciones=new BigDecimal(round((totaldevengado.subtract(totalauxtransporte)).multiply(new BigDecimal(parametrosNomina.getPorc_vacaciones().doubleValue()*(0.01)))).toBigInteger());
     totalsalud=totaldevengado.subtract(totalauxtransporte).multiply(new BigDecimal(parametrosNomina.getPorc_salud().doubleValue()*(0.01)));
     totalpension=totaldevengado.subtract(totalauxtransporte).multiply(new BigDecimal(parametrosNomina.getPorc_pension().doubleValue()*(0.01)));
     totaldeducciones=totalotrasdeducciones.add(totalsalud).add(totalpension).add(totalretefuente);
    }

    public BigDecimal getTotalbasico() {
        return totalbasico;
    }

    public void setTotalbasico(BigDecimal totalbasico) {
        this.totalbasico = totalbasico;
    }

    public BigDecimal getTotaldevengado() {
        return totaldevengado;
    }

    public void setTotaldevengado(BigDecimal totaldevengado) {
        this.totaldevengado = totaldevengado;
    }

    public BigDecimal getTotalauxtransporte() {
        return totalauxtransporte;
    }

    public void setTotalauxtransporte(BigDecimal totalauxtransporte) {
        this.totalauxtransporte = totalauxtransporte;
    }

    public Long getNo_consecutivo() {
        return no_consecutivo;
    }

    public void setNo_consecutivo(Long no_consecutivo) {
        this.no_consecutivo = no_consecutivo;
    }

    public BigDecimal getTotaldeducciones() {
        return totaldeducciones;
    }

    public void setTotaldeducciones(BigDecimal totaldeducciones) {
        this.totaldeducciones = totaldeducciones;
    }

    public BigDecimal getTotalsalud() {
        return totalsalud;
    }

    public void setTotalsalud(BigDecimal totalsalud) {
        this.totalsalud = totalsalud;
    }

    public BigDecimal getTotalpension() {
        return totalpension;
    }

    public void setTotalpension(BigDecimal totalpension) {
        this.totalpension = totalpension;
    }

    public BigDecimal getTotalotrasdeducciones() {
        return totalotrasdeducciones;
    }

    public void setTotalotrasdeducciones(BigDecimal totalotrasdeducciones) {
        this.totalotrasdeducciones = totalotrasdeducciones;
    }

    public BigDecimal getTotalotrosingresos() {
        return totalotrosingresos;
    }

    public void setTotalotrosingresos(BigDecimal totalotrosingresos) {
        this.totalotrosingresos = totalotrosingresos;
    }

    public BigDecimal getTotalretefuente() {
        return totalretefuente;
    }

    public void setTotalretefuente(BigDecimal totalretefuente) 
    {
        this.totalretefuente = totalretefuente;
    }

    public BigDecimal getTotalsueldoneto() {
        return totalsueldoneto;
    }

    public void setTotalsueldoneto(BigDecimal totalsueldoneto) {
        this.totalsueldoneto = totalsueldoneto;
    }

  private void calcular_valoresnominaempleados(NominaEmpleados ne)
  {
       ne.calcularvalordiasueldo();
       ne.calcularauxiliotransporte();
       ne.calcularcomisiones();
       ne.calculardeudasaterceros();
       ne.calculardiasnotrabajados();
       ne.calculardiastrabajados();
       ne.calculartotalbasico();
       ne.calcularhorasextras();
       ne.calcularotrasdeudas();
       ne.calculartotaldevengado();
       ne.calcularfondosolidaridad();
       ne.calcularsalud();
       ne.calcularpension();
       ne.calculartotaldeducciones();
       ne.calcularretefuente();
       ne.calcularnetopagado();
       
      
  }        

    public BigDecimal getTotalfondosolidaridad() {
        return totalfondosolidaridad;
    }

    public void setTotalfondosolidaridad(BigDecimal totalfondosolidaridad) {
        this.totalfondosolidaridad = totalfondosolidaridad;
    }

    
   private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }    
    public void removeempleado(Empleados empleados)
    {
       
        for(NominaEmpleados ne:nominaempleadositems)
        {
            if(ne.getEmpleados().getId().equals(empleados.getId()))
            { 
                nominaempleadositems.remove(ne);
                break;
            }  
            }
        }
}
