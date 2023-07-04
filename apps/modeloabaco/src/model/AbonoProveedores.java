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
public class AbonoProveedores implements Serializable {
         @javax.persistence.SequenceGenerator(
	   	     name="SEQ_ID_ABOPROV",
	   	     sequenceName="SEQ_ID_ABOPROV",
	   	     allocationSize=1
	   	    )
	@Id 
        @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_ABOPROV")
	@Column(name="ID")
	private Long id;
	private BigDecimal valor = BigDecimal.ZERO;
        @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fecha=new Date();
        @ManyToOne
        @JoinColumn(name="ID_FACPROV")
	private FacturaProveedores  facturaProveedores; 
        @ManyToOne(optional = true, targetEntity = Usuarios.class)
        @JoinColumn(name="USUARIOS_ID")
        private Usuarios usuarios;
        private String Observaciones;
           
	   
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

    
     public FacturaProveedores getFacturaProveedores() {
        return facturaProveedores;
    }

    public void setFacturaProveedores(FacturaProveedores facturaProveedores) {
        this.facturaProveedores = facturaProveedores;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
 
		
		
		
		
}
