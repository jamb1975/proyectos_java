package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Abono implements Serializable {
	  @javax.persistence.SequenceGenerator(
	   	     name="SEQ_ID_ABO",
	   	     sequenceName="SEQ_ID_ABO",
	   	     allocationSize=1
	   	    )
	   @Id 
            @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_ABO")
	   @Column(name="ID")
        private Long id;
	private BigDecimal valor = BigDecimal.ZERO;
        @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fecha=new Date();
        @ManyToOne
	@JoinColumn(name="ID_FAC")
	private Factura factura; 
        @ManyToOne
        @JoinColumn(name="USUARIOS_ID")
	private Usuarios usuario;
	private String Observaciones;
           
	    public Factura getFactura() {
	        return factura;
	    }
	    public void setFactura(Factura factura) {
	        this.factura = factura;
	    }
	  
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public BigDecimal getValor() {
			return valor;
		}
		public void setValor(BigDecimal valor) {
			this.valor = valor;
		}
       
		public Date getFecha() {
			return fecha;
		}
		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}
		

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
	
		
		
		
		
}
