/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adminlinux
 */
@Entity
@XmlRootElement
@Table(name = "Novedades")
public class Novedades {
     @Column(name = "ID", table = "Novedades", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_NOVEDADES_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_NOVEDADES_ID", sequenceName = "SEQ_NOVEDADES_ID", allocationSize = 1, initialValue = 1)
    private Long id;
   
    @ManyToOne
    @JoinColumn(name="NOMINAEMPLEADOS_ID") 
    private NominaEmpleados nominaEmpleados;
    private int tiponovedad;
    private int tipohoraextra;
    @ManyToOne
    @JoinColumn(name="FACTORHORASEXTRAS_ID") 
    private FactorHorasExtras factorHorasExtras;
    @Column(length = 3000)
    private String observacion;
    private int cantidad;
    private BigDecimal valor=BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public int getTiponovedad() {
        return tiponovedad;
    }

    public void setTiponovedad(int tiponovedad) {
        this.tiponovedad = tiponovedad;
    }

    public int getTipohoraextra() {
        return tipohoraextra;
    }

    public void setTipohoraextra(int tipohoraextra) {
        this.tipohoraextra = tipohoraextra;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public NominaEmpleados getNominaEmpleados() {
        return nominaEmpleados;
    }

    public void setNominaEmpleados(NominaEmpleados nominaEmpleados) {
        this.nominaEmpleados = nominaEmpleados;
    }
    
   public String getTipoNovedad()
   {
   
    if(tiponovedad==EnumTipoNovedad.COMISION0.ordinal())
    {
        return("Comisión");
    }
    else
    {
      if(tiponovedad==EnumTipoNovedad.HORASEXTRAS1.ordinal())
    {
        return("Horas Extras");
    }
     else
      {
          if(tiponovedad==EnumTipoNovedad.DEUDASTERCEROS2.ordinal())
    {
        return("Deudas a Terceros");
    }
          else
          {
              if(tiponovedad==EnumTipoNovedad.DEUDASFINANCIERAS3.ordinal())
    {
        return("Deudas Financieras");
    }
              else
              {
                  if(tiponovedad==EnumTipoNovedad.OTRASDEUDAS4.ordinal())
    {
        return("Otras Deudas");
    }
                  else
                  {
                      return "No Escogio ningún tipo de novedad";
                  }
              }
          }
      }
    }
   }

    public FactorHorasExtras getFactorHorasExtras() {
        return factorHorasExtras;
    }

    public void setFactorHorasExtras(FactorHorasExtras factorHorasExtras) {
        this.factorHorasExtras = factorHorasExtras;
    }
    
public void calcularvalorhorasextras(BigDecimal factor)
{
    
}
 
}