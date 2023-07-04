/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author adminlinux
 */
public class DTOFactura {
  private Date   fechaelaboracion=new Date();
  private String prefijo="";
  private Long no_factura;
  private BigDecimal totalamount=BigDecimal.ZERO;
  private String no_ident="";
  private String tipodoc="";        
  private String nombre="";
  private String primer_apellido="";
  private String segundo_apellido="";
  private String direccion="";
  private String telefono="";
  private BigDecimal cuotamoderadora=BigDecimal.ZERO;
  private BigDecimal copago=BigDecimal.ZERO;
  private String digitoverificacion="";

    public Date getFechaelaboracion() {
        return fechaelaboracion;
    }

    public void setFechaelaboracion(Date fechaelaboracion) {
        this.fechaelaboracion = fechaelaboracion;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public Long getNo_factura() {
        return no_factura;
    }

    public void setNo_factura(Long no_factura) {
        this.no_factura = no_factura;
    }

   

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public String getNo_ident() {
        return no_ident;
    }

    public void setNo_ident(String no_ident) {
        this.no_ident = no_ident;
    }

    public String getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(String tipodoc) {
        this.tipodoc = tipodoc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getCuotamoderadora() {
        return cuotamoderadora;
    }

    public void setCuotamoderadora(BigDecimal cuotamoderadora) {
        this.cuotamoderadora = cuotamoderadora;
    }

    public BigDecimal getCopago() {
        return copago;
    }

    public void setCopago(BigDecimal copago) {
        this.copago = copago;
    }

    public String getDigitoverificacion() {
        return digitoverificacion;
    }

    public void setDigitoverificacion(String digitoverificacion) {
        this.digitoverificacion = digitoverificacion;
    }
  
    
}
