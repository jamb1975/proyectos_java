/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Temporal;

/**
 *
 * @author adminlinux
 */
public class RptFacturasDTO {
    
    private String facturaDate;
    private String documento;
    private String nombre;
    private String eapb;
    private String nit;
    private String tipousuario;
    private String direccion;
    private String telefono;
    private Long no_factura;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private GenPacientes genPacientes;
    private String prefijo;
    private String telefonoeapb;
    private String direccioneapb;
    private String apellido;
    
    
    public RptFacturasDTO( Date facturaDate,
    String documento,
    String nombre,
    String apellido,
    String eapb,
    String nit,
    String tipousuario,
    String direccion,
    String telefono,
    Long no_factura,
    BigDecimal totalAmount,
    String prefijo,
    String telefonoeapb,
    String direccioneapb)
   {
       super();
       this.facturaDate=formatodiamesyear(facturaDate);
       this.documento=documento;
       this.nombre=nombre+" "+apellido;
       this.apellido=apellido;
       this.eapb=eapb;
       this.nit=nit;
       this.tipousuario=tipousuario;
       this.direccion=direccion;
       this.telefono=telefono;
       this.no_factura=no_factura;
       this.totalAmount= totalAmount;
       this.prefijo= prefijo;     
       this.telefonoeapb=telefonoeapb;
       this.direccioneapb=direccioneapb;
       
   }

    public String getFacturaDate() {
        return facturaDate;
    }

    public void setFacturaDate(String facturaDate) {
        this.facturaDate = facturaDate;
    }
   
  

   
    

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public GenPacientes getGenPacientes() {
        return genPacientes;
    }

    public void setGenPacientes(GenPacientes genPacientes) {
        this.genPacientes = genPacientes;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }
    
   public String getNofacturacerosizquierda() {
         String nofacturacerosizquierda;
        String cerosizq="";
   
     for(int i=4;i>no_factura.toString().length();i--)
     {
         cerosizq=cerosizq+"0";
     }
      nofacturacerosizquierda=prefijo+cerosizq+no_factura.toString();
      return nofacturacerosizquierda;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEapb() {
        return eapb;
    }

    public void setEapb(String eapb) {
        this.eapb = eapb;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(String tipousuario) {
        this.tipousuario = tipousuario;
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

    public String getTelefonoeapb() {
        return telefonoeapb;
    }

    public void setTelefonoeapb(String telefonoeapb) {
        this.telefonoeapb = telefonoeapb;
    }

    public String getDireccioneapb() {
        return direccioneapb;
    }

    public void setDireccioneapb(String direccioneapb) {
        this.direccioneapb = direccioneapb;
    }

    public Long getNo_factura() {
        return no_factura;
    }

    public void setNo_factura(Long no_factura) {
        this.no_factura = no_factura;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    
    public  String formatodiamesyear(Date d_fecha) {
      SimpleDateFormat dma = new SimpleDateFormat("yyyy-MM-dd");
      String  fecha = dma.format(d_fecha);
        return fecha;
    }
   
}
