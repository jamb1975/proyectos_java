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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author innsend
 */
@Entity
@XmlRootElement
public class Sucursales implements Serializable {
   private Long id;
   private String codigo;
   private String nombre;
 @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_SUCURSALES",
		     sequenceName="SEQ_ID_SUCURSALES",
		     allocationSize=1
		    )
    @Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_SUCURSALES")
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
   
   
   
}
