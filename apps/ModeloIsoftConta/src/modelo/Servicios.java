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

/**
 *
 * @author karolyani
 */
@Entity
@XmlRootElement
public class Servicios implements Serializable {
private Long id;
private Servicios solucionPadre ;
private String solucion;
private String descripcion;
private String ruta;
private String clase;
private int numeral;
    
     @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_SOL",
		     sequenceName="SEQ_ID_SOL",
		     allocationSize=1
		    )
    @Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_SOL")
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ManyToOne
    @JoinColumn(name="ID_PADRE")
    public Servicios getSolucionPadre() {
        return solucionPadre;
    }

    public void setSolucionPadre(Servicios solucionPadre) {
        this.solucionPadre = solucionPadre;
    }

   

  

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public int getNumeral() {
        return numeral;
    }

    public void setNumeral(int numeral) {
        this.numeral = numeral;
    }


}
