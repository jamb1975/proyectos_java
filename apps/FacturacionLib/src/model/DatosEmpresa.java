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
public class DatosEmpresa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String m_sNombre;
    private String m_sDir;
    private String m_sEmail;
    private int m_iNoMesas;
    private Long id;
    private String nit;


    public String getM_sNombre() {
        return m_sNombre;
    }

    public void setM_sNombre(String m_sNombre) {
        this.m_sNombre = m_sNombre;
    }

    public String getM_sDir() {
        return m_sDir;
    }

    public void setM_sDir(String m_sDir) {
        this.m_sDir = m_sDir;
    }

    public String getM_sEmail() {
        return m_sEmail;
    }

    public void setM_sEmail(String m_sEmail) {
        this.m_sEmail = m_sEmail;
    }

    public int getM_iNoMesas() {
        return m_iNoMesas;
    }

    public void setM_iNoMesas(int m_iNoMesas) {
        this.m_iNoMesas = m_iNoMesas;
    }
   
	@javax.persistence.SequenceGenerator(
		     name="SEQ_ID_EMP",
		     sequenceName="SEQ_ID_EMP",
		     allocationSize=1
		    )
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_EMP")
	@Column(name="ID_EMP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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
        if (!(object instanceof DatosEmpresa)) {
            return false;
        }
        DatosEmpresa other = (DatosEmpresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.DatosEmpresa[ id=" + id + " ]";
    }
    
}
