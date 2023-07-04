/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SIMboxDEV8
 */
@Entity
@XmlRootElement
public class Mesas_Atendidas_View implements Serializable {
 private String no_ident;   
 private String nombre;
 private int cant;         
    @Id 
    public String getNo_ident() {
        return no_ident;
    }

    public void setNo_ident(String no_ident) {
        this.no_ident = no_ident;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
 
 
}
