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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Javier Murcia
 */
@Entity
@XmlRootElement
public class Categoria implements Serializable {
    
    public Categoria()
    {
        
    }       
            
    public Categoria(Long _id)
    {
        this.id=_id;
    }
@Column(unique = true)
    private String m_NombreCat;
    private static final long serialVersionUID = 1L;
    @javax.persistence.SequenceGenerator(
	     name="SEQ_ID_CAT",
	     sequenceName="SEQ_ID_CAT",
	     allocationSize=1
	    )
@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_CAT")
@Column(name="ID")
    private Long id;
@ManyToOne(optional = true, targetEntity = Usuarios.class)
@JoinColumn(name="USUARIOS_ID")
     private Usuarios usuarios;
private String codigo;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        public String getM_NombreCat() {
        return m_NombreCat;
    }

    public void setM_NombreCat(String m_NombreCat) {
        this.m_NombreCat = m_NombreCat;
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
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Categoria[ id=" + id + " ]";
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
}
