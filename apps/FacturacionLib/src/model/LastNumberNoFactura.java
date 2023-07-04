/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karol
 */
@Entity
@XmlRootElement
public class LastNumberNoFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    
     @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_LAST_FACT",
		     sequenceName="SEQ_ID_LAST_FACT",
		     allocationSize=1
		    )
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_LAST_FACT")
@Column(name = "ID_LASTFACT")

     private Long id;
     private Long m_lNoFact;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getM_lNoFact() {
        return m_lNoFact;
    }

    public void setM_lNoFact(Long m_lNoFact) {
        this.m_lNoFact = m_lNoFact;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LastNumberNoFactura)) {
            return false;
        }
        LastNumberNoFactura other = (LastNumberNoFactura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.LastNumberNoFactura[ id=" + id + " ]";
    }
    
}
