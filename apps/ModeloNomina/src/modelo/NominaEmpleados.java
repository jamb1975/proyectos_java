/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adminlinux
 */
@Entity
@XmlRootElement
public class NominaEmpleados implements Serializable {
   @Id
    @GeneratedValue(generator = "SEQ_NOMINAEMPLEADOS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_NOMINAEMPLEADOS_ID", sequenceName = "SEQ_NOMINAEMPLEADOS_ID", allocationSize = 1, initialValue = 1)
    private Long id;  
    @ManyToOne(optional = true, targetEntity = Empleados.class)
    @JoinColumn(name="EMPLEADOS_ID")
    private Empleados empleados;
    @ManyToOne(optional = true, targetEntity = Nomina.class)
    @JoinColumn(name="NOMINA_ID")
    private Nomina nomina;
    @OneToMany(fetch = FetchType.LAZY,mappedBy="nominaEmpleados", cascade=CascadeType.ALL,orphanRemoval = true)
      private List<Novedades> novedadesItems = new ArrayList<Novedades>();
      private BigDecimal valorcomisiones=BigDecimal.ZERO;
      private BigDecimal valorhorasextras=BigDecimal.ZERO;
      private BigDecimal valorrecargonocturno=BigDecimal.ZERO;
      private BigDecimal valordeudaterceros=BigDecimal.ZERO;
      private BigDecimal valordeudaentidadfinancieras=BigDecimal.ZERO;
      private BigDecimal valorotrasdeudas=BigDecimal.ZERO;
      private BigDecimal valorretefuente=BigDecimal.ZERO;
      private long cantdiastrabajados=0;
      private long cantdiasnotrabajados=0;
      private BigDecimal totalbasico=BigDecimal.ZERO;
      private BigDecimal auxiliotransporte=BigDecimal.ZERO;
      private BigDecimal totaldevengado=BigDecimal.ZERO;
      private BigDecimal valorsalud=BigDecimal.ZERO;
      private BigDecimal valorpension=BigDecimal.ZERO;
      private BigDecimal totaldeducciones=BigDecimal.ZERO;
      private BigDecimal netopagado=BigDecimal.ZERO;
      private BigDecimal fondosolidaridad=BigDecimal.ZERO;
      @Column(unique = true)
      private Long no_consecutivo;
     public Long getId() {
        return id;
     }

    public void setId(Long id) {
        this.id = id;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    public List<Novedades> getNovedadesItems() {
        return novedadesItems;
    }

    public void setNovedadesItems(List<Novedades> novedadesItems) {
        this.novedadesItems = novedadesItems;
    }

    public Nomina getNomina() {
        return nomina;
    }

    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

    public BigDecimal getValorcomisiones() {
        return valorcomisiones;
    }

    public void setValorcomisiones(BigDecimal valorcomisiones) {
        this.valorcomisiones = valorcomisiones;
    }

    public BigDecimal getValorhorasextras() {
        return valorhorasextras;
    }

    public void setValorhorasextras(BigDecimal valorhorasextras) {
        this.valorhorasextras = valorhorasextras;
    }

    public BigDecimal getValordeudaterceros() {
        return valordeudaterceros;
    }

    public void setValordeudaterceros(BigDecimal valordeudaterceros) {
        this.valordeudaterceros = valordeudaterceros;
    }

    public BigDecimal getValordeudaentidadfinancieras() {
        return valordeudaentidadfinancieras;
    }

    public void setValordeudaentidadfinancieras(BigDecimal valordeudaentidadfinancieras) {
        this.valordeudaentidadfinancieras = valordeudaentidadfinancieras;
    }

    public BigDecimal getValorotrasdeudas() {
        return valorotrasdeudas;
    }

    public void setValorotrasdeudas(BigDecimal valorotrasdeudas) {
        this.valorotrasdeudas = valorotrasdeudas;
    }

    public BigDecimal getValorretefuente() {
        return valorretefuente;
    }

    public void setValorretefuente(BigDecimal valorretefuente) {
        this.valorretefuente = valorretefuente;
    }

    
   

    public long getCantdiastrabajados() {
        return cantdiastrabajados;
    }

    public void setCantdiastrabajados(long cantdiastrabajados) {
        this.cantdiastrabajados = cantdiastrabajados;
    }

   
    
    public BigDecimal getTotalbasico() {
        return totalbasico;
    }

    public void setTotalbasico(BigDecimal totalbasico) {
        this.totalbasico = totalbasico;
    }

    public BigDecimal getAuxiliotransporte() {
        return auxiliotransporte;
    }

    public void setAuxiliotransporte(BigDecimal auxiliotransporte) {
        this.auxiliotransporte = auxiliotransporte;
    }

    public BigDecimal getTotaldevengado() {
        return totaldevengado;
    }

    public void setTotaldevengado(BigDecimal totaldevengado) {
        this.totaldevengado = totaldevengado;
    }

    public BigDecimal getValorsalud() {
        return valorsalud;
    }

    public void setValorsalud(BigDecimal valorsalud) {
        this.valorsalud = valorsalud;
    }

    public BigDecimal getValorpension() {
        return valorpension;
    }

    public void setValorpension(BigDecimal valorpension) {
        this.valorpension = valorpension;
    }

    public BigDecimal getTotaldeducciones() {
        return totaldeducciones;
    }

    public void setTotaldeducciones(BigDecimal totaldeducciones) {
        this.totaldeducciones = totaldeducciones;
    }

    public BigDecimal getNetopagado() {
        return netopagado;
    }

    public void setNetopagado(BigDecimal netopagado) {
        this.netopagado = netopagado;
    }
  public void addnovedad(Novedades novedades)
    {
        for(Novedades nov:novedadesItems)
      {
          if(nov.getId().equals(novedades.getId()))
          {   
              int index=novedadesItems.indexOf(nov);
              novedadesItems.set(index,novedades);
              totalnovedad(novedades.getTiponovedad());
              return;
          }
      }
        novedades.setNominaEmpleados(this);
        novedadesItems.add(novedades);
        totalnovedad(novedades.getTiponovedad());
    }
  public void removernovedad(Novedades novedades)
  {
      for(Novedades nov:novedadesItems)
      {
          if(nov.getId().equals(novedades.getId()))
          {
              novedadesItems.remove(nov);
              break;
          }
      }
      totalnovedad(novedades.getTiponovedad());
  }
    public void totalnovedad(int _tipo)
    {
        BigDecimal total=BigDecimal.ZERO;
        int cantidad=0;
        for(Novedades nv: novedadesItems)
        {
            if(_tipo==nv.getTiponovedad())
            {   
               
                    total=total.add(nv.getValor());
                 
            }        
        }
        if(_tipo==EnumTipoNovedad.COMISION0.ordinal())
        {
            valorcomisiones=total;
        }
        else
        {
        if(_tipo==EnumTipoNovedad.HORASEXTRAS1.ordinal())
        {
            valorhorasextras=total;
            
        }
        else
        {
            if(_tipo==EnumTipoNovedad.DEUDASTERCEROS2.ordinal())
        {
            valordeudaterceros=total;
        }
        else
            {
                if(_tipo==EnumTipoNovedad.DEUDASFINANCIERAS3.ordinal())
                {
                 valordeudaentidadfinancieras=total;
                }
                else
                {
                    if(_tipo==EnumTipoNovedad.OTRASDEUDAS4.ordinal())
        {
                   valorotrasdeudas=total;
        }
                    else
                    {
                        if(_tipo==EnumTipoNovedad.DIASNOTRABAJADOS5.ordinal())
        {
                         cantdiasnotrabajados=cantidad;
        }
                    }
                }
            }
        }
        }
    }   
 public void calculardiastrabajados()
    {
        long totdiastrabajados=((nomina.getPeriodofin().getTime()-nomina.getPeriodoinicio().getTime())/86400000)+1;
        if(totdiastrabajados==28 || totdiastrabajados==31)
        {
            totdiastrabajados=30;
        }
        cantdiastrabajados=totdiastrabajados-cantdiasnotrabajados;
        
    }  
public void calculartotalbasico()
{
totalbasico=calcularvalordiasueldo().multiply(BigDecimal.valueOf(cantdiastrabajados));

}
public BigDecimal calcularvalordiasueldo()
{
    System.out.println("valor dia sueldo->"+round(new BigDecimal(30)));
        return empleados.getCargosEntidad().getSueldo().divide(BigDecimal.valueOf(30),9, RoundingMode.UP);
}
public void calcularauxiliotransporte()
{
      if(empleados.getCargosEntidad().getSueldo().doubleValue()<= (nomina.getParametrosNomina().getSalariominimo().multiply(new BigDecimal(nomina.getParametrosNomina().getMaxcantidadsalminauxtransporte()))).doubleValue())
      {
        auxiliotransporte= nomina.getParametrosNomina().getAuxiliotransporte();
      }
      else
      {
          auxiliotransporte= BigDecimal.ZERO;
      }
}
public void calcularhorasextras()
{
      totalnovedad(EnumTipoNovedad.HORASEXTRAS1.ordinal());
}
public void calcularcomisiones()
{
      totalnovedad(EnumTipoNovedad.COMISION0.ordinal());
}
public void calculardeudasaterceros()
{
      totalnovedad(EnumTipoNovedad.DEUDASTERCEROS2.ordinal());
}
public void deudasfinancieras()
{
      totalnovedad(EnumTipoNovedad.DEUDASFINANCIERAS3.ordinal());
}
public void calcularotrasdeudas()
{
      totalnovedad(EnumTipoNovedad.OTRASDEUDAS4.ordinal());
}
public void calculardiasnotrabajados()
{
      totalnovedad(EnumTipoNovedad.DIASNOTRABAJADOS5.ordinal());
}
public void calculartotaldevengado()
{
    calcularauxiliotransporte();
    calcularhorasextras();
    totaldevengado=new BigDecimal(totalbasico.add(auxiliotransporte).add(valorhorasextras).add(valorcomisiones).longValue());
}
public void calcularsalud()
{
      valorsalud=totaldevengado.subtract(auxiliotransporte).multiply(nomina.getParametrosNomina().getPorc_salud().multiply(BigDecimal.valueOf(0.01)));
}
public void calcularpension()
{
      valorpension=totaldevengado.subtract(auxiliotransporte).multiply(nomina.getParametrosNomina().getPorc_pension().multiply(BigDecimal.valueOf(0.01)));
}
public void calculartotaldeducciones()
{
    calcularretefuente();
    totaldeducciones=valordeudaentidadfinancieras.add(valordeudaterceros).add(valorotrasdeudas).add(valorretefuente).add(valorsalud).add(valorpension);
}
public void calcularfondosolidaridad()
{
   if((BigDecimal.valueOf(4).multiply(nomina.getParametrosNomina().getSalariominimo())).compareTo(totaldevengado)==-1)
   {
       fondosolidaridad=totaldevengado.multiply(BigDecimal.valueOf(0.01));
   
   }
     else
       {
            fondosolidaridad=BigDecimal.ZERO;    
       }
}
public void calcularretefuente()
{
    calculardeudasaterceros();
    calcularotrasdeudas();
    BigDecimal ingresolaboralgravado=BigDecimal.ZERO;
    BigDecimal ingresolaboralbase=BigDecimal.ZERO;
    BigDecimal total2=totaldevengado.subtract(valorsalud).subtract(valorpension).subtract(fondosolidaridad);
    BigDecimal total=totaldevengado.subtract(valorsalud).subtract(valorpension).subtract(fondosolidaridad);
    BigDecimal totaldeducion=valordeudaentidadfinancieras.add(valorotrasdeudas);
    total=total.subtract(totaldeducion);
    BigDecimal rentaexenta=total.multiply(new BigDecimal(0.25));
    total=total.subtract(rentaexenta);
    BigDecimal cifracontrol40=total2.multiply(new BigDecimal(0.40));
    BigDecimal totaldeduccionesrentaexenta=valordeudaentidadfinancieras.add(valorotrasdeudas).add(rentaexenta);
    if(totaldeduccionesrentaexenta.doubleValue()>cifracontrol40.doubleValue())
    {
        ingresolaboralbase=total2.subtract(cifracontrol40);
    }
    else
    {
       ingresolaboralbase=total; 
    }
    ingresolaboralgravado=ingresolaboralbase.divide(nomina.getParametrosNomina().getUvt(),2,RoundingMode.HALF_UP);
   if(ingresolaboralgravado.doubleValue()>87 && ingresolaboralgravado.doubleValue()<=145)
   {
       valorretefuente=((ingresolaboralgravado.subtract(new BigDecimal(87))).multiply(nomina.getParametrosNomina().getPorc_retefuente95().multiply(BigDecimal.valueOf(0.01)))).multiply(nomina.getParametrosNomina().getUvt());
   }
   else
   {
      if(ingresolaboralgravado.doubleValue()>145 && ingresolaboralgravado.doubleValue()<=335)
   {
       valorretefuente=((ingresolaboralgravado.subtract(new BigDecimal(145))).multiply(nomina.getParametrosNomina().getPorc_retefuentemay95y150().multiply(BigDecimal.valueOf(0.01)))).multiply(nomina.getParametrosNomina().getUvt());
   }
      else
      {
             if(ingresolaboralgravado.doubleValue()>335 && ingresolaboralgravado.doubleValue()<=640)
   {
       valorretefuente=((ingresolaboralgravado.subtract(new BigDecimal(335))).multiply(nomina.getParametrosNomina().getPorc_retefuentemay150y360().multiply(BigDecimal.valueOf(0.01)))).multiply(nomina.getParametrosNomina().getUvt());
   }
   else
             {
                            if(ingresolaboralgravado.doubleValue()>640 && ingresolaboralgravado.doubleValue()<=945)
   {
       valorretefuente=((ingresolaboralgravado.subtract(new BigDecimal(640))).multiply(nomina.getParametrosNomina().getPorc_retefuentemay360().multiply(BigDecimal.valueOf(0.01)))).multiply(nomina.getParametrosNomina().getUvt());
  
   }
   else
   {
   if(ingresolaboralgravado.doubleValue()>945 && ingresolaboralgravado.doubleValue()<=2300)
   {
       valorretefuente=((ingresolaboralgravado.subtract(new BigDecimal(945))).multiply(nomina.getParametrosNomina().getPorc_retefuentemay360().multiply(BigDecimal.valueOf(0.01)))).multiply(nomina.getParametrosNomina().getUvt());
   }
   else
   {
                  if(ingresolaboralgravado.doubleValue()>2300)
   {
       valorretefuente=((ingresolaboralgravado.subtract(new BigDecimal(2300))).multiply(nomina.getParametrosNomina().getPorc_retefuentemay360().multiply(BigDecimal.valueOf(0.01)))).multiply(nomina.getParametrosNomina().getUvt());
   }
   }
                            }
             }
      }
   }
} 
public void calcularnetopagado()
{
    netopagado=totaldevengado.subtract(totaldeducciones).subtract(fondosolidaridad).subtract(valorretefuente);
}

    public long getCantdiasnotrabajados() {
        return cantdiasnotrabajados;
    }

    public void setCantdiasnotrabajados(long cantdiasnotrabajados) {
        this.cantdiasnotrabajados = cantdiasnotrabajados;
    }

    public Long getNo_consecutivo() {
        return no_consecutivo;
    }

    public void setNo_consecutivo(Long no_consecutivo) {
        this.no_consecutivo = no_consecutivo;
    }
    
       private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }

    public BigDecimal getValorrecargonocturno() {
        return valorrecargonocturno;
    }

    public void setValorrecargonocturno(BigDecimal valorrecargonocturno) {
        this.valorrecargonocturno = valorrecargonocturno;
    }

    public BigDecimal getFondosolidaridad() {
        return fondosolidaridad;
    }

    public void setFondosolidaridad(BigDecimal fondosolidaridad) {
        this.fondosolidaridad = fondosolidaridad;
    }
    

}
