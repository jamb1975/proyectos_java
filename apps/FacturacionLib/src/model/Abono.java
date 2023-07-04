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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Abono implements Serializable {
	private Long id;
	private BigDecimal valor = BigDecimal.ZERO;
	private Date fecha=new Date();
	private Factura factura; 
	private Terceros usuario;
	
            @ManyToOne
	    @JoinColumn(name="ID_FAC")
	    public Factura getFactura() {
	        return factura;
	    }
	    public void setFactura(Factura factura) {
	        this.factura = factura;
	    }
	    @javax.persistence.SequenceGenerator(
	   	     name="SEQ_ID_ABO",
	   	     sequenceName="SEQ_ID_ABO",
	   	     allocationSize=1
	   	    )
	   @Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_ABO")
	   @Column(name="ID_ABO")
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
        @Temporal(javax.persistence.TemporalType.DATE)
		public Date getFecha() {
			return fecha;
		}
		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}
		@ManyToOne
		@JoinColumn(name="ID_USUA")
		 public Terceros getUsuario() {
			return usuario;
		}
		public void setUsuario(Terceros usuario) {
			this.usuario = usuario;
		}
	
		
		
		
		
}
