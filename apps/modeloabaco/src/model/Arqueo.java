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
public class Arqueo implements Serializable {
	  @javax.persistence.SequenceGenerator(
	   	     name="SEQ_ID_ARQ",
	   	     sequenceName="SEQ_ID_ARQ",
	   	     allocationSize=1
	   	    )
	   @Id 
            @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_ARQ")
	   @Column(name="ID")
        private Long id;
	private BigDecimal valor_contado = BigDecimal.ZERO;
        private BigDecimal valor_credito = BigDecimal.ZERO;
        private BigDecimal valor_caja = BigDecimal.ZERO;
         @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fecha=new Date();
       
        @ManyToOne
        @JoinColumn(name="USUARIOS_ID")
	private Usuarios usuario;
	private String Observaciones;
        private String recibe;
        private String entrega;
	   
	  
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
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

    public BigDecimal getValor_contado() {
        return valor_contado;
    }

    public void setValor_contado(BigDecimal valor_contado) {
        this.valor_contado = valor_contado;
    }

    public BigDecimal getValor_credito() {
        return valor_credito;
    }

    public void setValor_credito(BigDecimal valor_credito) {
        this.valor_credito = valor_credito;
    }

    public BigDecimal getValor_caja() {
        return valor_caja;
    }

    public void setValor_caja(BigDecimal valor_caja) {
        this.valor_caja = valor_caja;
    }

    public String getRecibe() {
        return recibe;
    }

    public void setRecibe(String recibe) {
        this.recibe = recibe;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }
	
		
		
		
		
}
