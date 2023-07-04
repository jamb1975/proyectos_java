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
public class RptCitaEstadisticaDTO {
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechahora=new Date();
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechacreacion=new Date();
    private int hora;
    private int minutos;
    private String primernombre;
    private String segundonombre;
    private String primerapellido;
    private String segundoapellido;
    private String tiposdocumentos;
    private String documento;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechanacimiento=new Date();                   
    private int year;
    private String telefono;
    private String sexo;
    private String direccion;
    private String eapb;    
    private String medico;
    private String tipousuario;
    private String codigocups;
    private String descripcioncups;
    private BigDecimal valortotal;  
    private String especialidad;
    private String prefijo;
    private Long consecutivocomprobanteprocedimiento;
    private int codigocategoria; 
    private String medicoordeno;
    private String tipousuariodesc;
    private Long  no_factura;
    private String noautorizacion;
    public RptCitaEstadisticaDTO(
     Date fechahora,
     Date fechacreacion,
     int hora,
     int minutos,
     String primernombre,
     String segundonombre,
     String primerapellido,
     String segundoapellido,
     String tiposdocumentos,
     String documento,
     Date fechanacimiento,                   
     String telefono,
     String sexo,
     String direccion,
     String eapb,    
     String medico,
     String tipousuario,
     String codigocups,
     String descripcioncups,
     BigDecimal valortotal,  
     String especialidad,
     String prefijo,
     Long consecutivocomprobanteprocedimiento,
     int codigocategoria, 
     String medicoordeno,
     String tipousuariodesc,
     Long no_factura,
     String noautorizacion)
    {
     this.fechahora=fechahora;
     this.fechacreacion=fechacreacion;
     this.hora=hora;
     this.minutos=minutos;
     this.primernombre=primernombre;
     this.segundonombre=segundonombre;
     this.primerapellido=primerapellido;
     this.segundoapellido=segundoapellido;
     this.tiposdocumentos=tiposdocumentos;
     this.documento=documento;
     this.fechahora=fechahora;  
     this.fechanacimiento=fechanacimiento;    
     this.year=year;
     this.telefono=telefono;
     this.sexo=sexo;
     this.direccion=direccion;
     this.eapb=eapb;    
     this.medico=medico;
     this.tipousuario=tipousuario;
     this.codigocups=codigocups;
     this.descripcioncups=descripcioncups;
     this.valortotal=valortotal;  
     this.especialidad=especialidad;
     this.prefijo=prefijo;
     this.consecutivocomprobanteprocedimiento=consecutivocomprobanteprocedimiento;
     this.codigocategoria=codigocategoria; 
     this.medicoordeno=medicoordeno; 
     this.tipousuariodesc=tipousuariodesc;
     this.no_factura=no_factura;
     this.noautorizacion = noautorizacion;
    }
     public String year()
    {
       
       Date fechaDeHoy = new Date();
       SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
       String fecha = formato.format(fechaDeHoy);
       String[] fechaHoy = fecha.split("/");
       Integer diaHoy = Integer.parseInt(fechaHoy[0]);
       Integer mesHoy = Integer.parseInt(fechaHoy[1]);
       Integer anioHoy = Integer.parseInt(fechaHoy[2]);
       String fechaAnterior =formato.format(fechanacimiento);
       String[] fechaAnt = fechaAnterior.split("/");
       Integer diaAnt = Integer.parseInt(fechaAnt[0]);
       Integer mesAnt = Integer.parseInt(fechaAnt[1]);
       Integer anioAnt = Integer.parseInt(fechaAnt[2]);
       if((anioHoy-anioAnt)>0)
       { 
           int year =anioHoy-anioAnt;
           if(mesHoy<mesAnt)
           {
               year=year-1;
               if(year==0)
               {
                  return String.valueOf((12-(mesAnt))+mesHoy); 
               }
           }
            return String.valueOf(year);
       }
       else
       {
         if((mesHoy-mesAnt)>0)
       {
            return String.valueOf(mesHoy-mesAnt);
       } 
         else
       {
         if((diaHoy-diaAnt)>0)
       {
            return String.valueOf(diaHoy-diaAnt);
       } 
       }
       }
     return String.valueOf(0);
                
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public String getPrimernombre() {
        return primernombre;
    }

    public void setPrimernombre(String primernombre) {
        this.primernombre = primernombre;
    }

    public String getSegundonombre() {
        return segundonombre;
    }

    public void setSegundonombre(String segundonombre) {
        this.segundonombre = segundonombre;
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

    public String getTiposdocumentos() {
        return tiposdocumentos;
    }

    public void setTiposdocumentos(String tiposdocumentos) {
        this.tiposdocumentos = tiposdocumentos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEapb() {
        return eapb;
    }

    public void setEapb(String eapb) {
        this.eapb = eapb;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(String tipousuario) {
        this.tipousuario = tipousuario;
    }


    public String getCodigocups() {
        return codigocups;
    }

    public void setCodigocups(String codigocups) {
        this.codigocups = codigocups;
    }

    public String getDescripcioncups() {
        return descripcioncups;
    }

    public void setDescripcioncups(String descripcioncups) {
        this.descripcioncups = descripcioncups;
    }

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public Long getConsecutivocomprobanteprocedimiento() {
        return consecutivocomprobanteprocedimiento;
    }

    public void setConsecutivocomprobanteprocedimiento(Long consecutivocomprobanteprocedimiento) {
        this.consecutivocomprobanteprocedimiento = consecutivocomprobanteprocedimiento;
    }

    public int getCodigocategoria() {
        return codigocategoria;
    }

    public void setCodigocategoria(int codigocategoria) {
        this.codigocategoria = codigocategoria;
    }

    
    public String getMedicoordeno() {
        return medicoordeno;
    }

    public void setMedicoordeno(String medicoordeno) {
        this.medicoordeno = medicoordeno;
    }

    public String getTipousuariodesc() {
        return tipousuariodesc;
    }

    public void setTipousuariodesc(String tipousuariodesc) {
        this.tipousuariodesc = tipousuariodesc;
    }

    public Long getNo_factura() {
        return no_factura;
    }

    public void setNo_factura(Long no_factura) {
        this.no_factura = no_factura;
    }

    public String getNoautorizacion() {
        return noautorizacion;
    }

    public void setNoautorizacion(String noautorizacion) {
        this.noautorizacion = noautorizacion;
    }

    
     
}

             

