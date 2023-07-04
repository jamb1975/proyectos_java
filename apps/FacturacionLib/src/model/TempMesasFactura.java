/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;


/**
 *
 * @author karol
 */

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@XmlRootElement

public class TempMesasFactura    implements Serializable
{
   
     @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_TMF",
		     sequenceName="SEQ_ID_TMF",
		     allocationSize=1
		    )
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_TMF")
	@Column(name="ID_TMF")
        private Long id;
      @OneToOne
      @JoinColumn(name="ID_FACT")
       private Factura factura;
      private int m_iNoMesa;
    	 /* @return the id
	 */
    
   public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Factura getFactura() {
		return factura;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
    @Column(unique = true)
    public int getM_iNoMesa() {
        return m_iNoMesa;
    }

    public void setM_iNoMesa(int m_iNoMesa) {
        this.m_iNoMesa = m_iNoMesa;
    }

	
	
	
}