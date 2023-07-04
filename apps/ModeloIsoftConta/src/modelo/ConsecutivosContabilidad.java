/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author innsend
 */
@Entity
@Table(name = "ConsecutivosContabilidad")
public class ConsecutivosContabilidad implements Serializable {
    
  @Column(name = "ID", table = "ConsecutivosContabilidad", unique = true, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_CONSECUTIVOSCONTABILIDAD_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CONSECUTIVOSCONTABILIDAD_ID", sequenceName = "SEQ_CONSECUTIVOSCONTABILIDAD_ID", allocationSize = 1, initialValue = 1)
 
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
  private Long consecutivocausacioningreso;
  private Long consecutivocausacionegreso;
  private Long consecutivosaldosiniciales;
  private Long consecutivonotadevolucionventas;

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

    public Long getConsecutivocausacioningreso() {
        return consecutivocausacioningreso;
    }

    public void setConsecutivocausacioningreso(Long consecutivocausacioningreso) {
        this.consecutivocausacioningreso = consecutivocausacioningreso;
    }

    public Long getConsecutivocausacionegreso() {
        return consecutivocausacionegreso;
    }

    public void setConsecutivocausacionegreso(Long consecutivocausacionegreso) {
        this.consecutivocausacionegreso = consecutivocausacionegreso;
    }

    public Long getConsecutivosaldosiniciales() {
        return consecutivosaldosiniciales;
    }

    public void setConsecutivosaldosiniciales(Long consecutivosaldosiniciales) {
        this.consecutivosaldosiniciales = consecutivosaldosiniciales;
    }

    public Long getConsecutivonotadevolucionventas() {
        return consecutivonotadevolucionventas;
    }

    public void setConsecutivonotadevolucionventas(Long consecutivonotadevolucionventas) {
        this.consecutivonotadevolucionventas = consecutivonotadevolucionventas;
    }

    

    
    
}
