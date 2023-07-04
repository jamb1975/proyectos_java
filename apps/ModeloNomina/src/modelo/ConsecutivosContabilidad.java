/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author innsend
 */
@Entity
public class ConsecutivosContabilidad implements Serializable {
    
  @Id
  private  Long id;
  private  Long consecutivocomprobantediario;
  private  Long consecutivocomprobanteingreso;
  private  Long consecutivocomprobanteegreso;
  private  Long consecutivorecibocaja;
  private  Long consecutivocomprobanteconsginacion;
  private  Long consecutivonotadebito;
  private  Long consecutivonotacredito;
  private  Long consecutivonotacontabilidad;
  private  Long consecutivonomina;
  private  Long consecutivonominaempleado;
  private Long consecutivorips;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsecutivocomprobantediario() {
        return consecutivocomprobantediario;
    }

    public void setConsecutivocomprobantediario(Long consecutivocomprobantediario) {
        this.consecutivocomprobantediario = consecutivocomprobantediario;
    }

    public Long getConsecutivocomprobanteingreso() {
        return consecutivocomprobanteingreso;
    }

    public void setConsecutivocomprobanteingreso(Long consecutivocomprobanteingreso) {
        this.consecutivocomprobanteingreso = consecutivocomprobanteingreso;
    }

    public Long getConsecutivocomprobanteegreso() {
        return consecutivocomprobanteegreso;
    }

    public void setConsecutivocomprobanteegreso(Long consecutivocomprobanteegreso) {
        this.consecutivocomprobanteegreso = consecutivocomprobanteegreso;
    }

    public Long getConsecutivorecibocaja() {
        return consecutivorecibocaja;
    }

    public void setConsecutivorecibocaja(Long consecutivorecibocaja) {
        this.consecutivorecibocaja = consecutivorecibocaja;
    }

    public Long getConsecutivocomprobanteconsginacion() {
        return consecutivocomprobanteconsginacion;
    }

    public void setConsecutivocomprobanteconsginacion(Long consecutivocomprobanteconsginacion) {
        this.consecutivocomprobanteconsginacion = consecutivocomprobanteconsginacion;
    }

    public Long getConsecutivonotadebito() {
        return consecutivonotadebito;
    }

    public void setConsecutivonotadebito(Long consecutivonotadebito) {
        this.consecutivonotadebito = consecutivonotadebito;
    }

    public Long getConsecutivonotacredito() {
        return consecutivonotacredito;
    }

    public void setConsecutivonotacredito(Long consecutivonotacredito) {
        this.consecutivonotacredito = consecutivonotacredito;
    }

    public Long getConsecutivonotacontabilidad() {
        return consecutivonotacontabilidad;
    }

    public void setConsecutivonotacontabilidad(Long consecutivonotacontabilidad) {
        this.consecutivonotacontabilidad = consecutivonotacontabilidad;
    }

    public Long getConsecutivonomina() {
        return consecutivonomina;
    }

    public void setConsecutivonomina(Long consecutivonomina) {
        this.consecutivonomina = consecutivonomina;
    }

    public Long getConsecutivonominaempleado() {
        return consecutivonominaempleado;
    }

    public void setConsecutivonominaempleado(Long consecutivonominaempleado) {
        this.consecutivonominaempleado = consecutivonominaempleado;
    }

    public Long getConsecutivorips() {
        return consecutivorips;
    }

    public void setConsecutivorips(Long consecutivorips) {
        this.consecutivorips = consecutivorips;
    }
    public String getconsecutivoripscerosizquierda() {
        
        String cerosizq="";
     
     for(int i=5;i>consecutivorips.toString().length();i--)
     {
         cerosizq=cerosizq+"0";
     }
    
    
        return cerosizq+consecutivorips.toString();
    }
    
}
