/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@XmlRootElement
public class UsuarioServicios implements Serializable {
   private Long id;
   private Servicios solucion;
   private Usuarios usuario;
    @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_USUSOL",
		     sequenceName="SEQ_ID_USUSOL",
		     allocationSize=1
		    )
    @Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_USUSOL")
    @Column(name="ID")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @ManyToOne
    @JoinColumn(name="ID_SOL")
    public Servicios getSolucion() {
        return solucion;
    }
    public void setSolucion(Servicios solucion) {
        this.solucion = solucion;
    }
    @ManyToOne
    @JoinColumn(name="ID_USUA")
    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    
   
   
}
