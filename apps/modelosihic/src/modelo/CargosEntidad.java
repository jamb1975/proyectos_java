/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adminlinux
 */
@Entity
@XmlRootElement
@Table(name = "CargosEntidad")
public class CargosEntidad implements Serializable {
    private static final long serialVersionUID = 1L;
    
   
 @Column(name = "ID", table = "CargosEntidad", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_CARGOSENTIDAD_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CARGOSENTIDAD_ID", sequenceName = "SEQ_CARGOSENTIDAD_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    private int nivelcargo;
    private String nombre;
    @Column(length = 4000)
    private String funciones;
    private BigDecimal sueldo=BigDecimal.ZERO;
   @Column(precision = 5,scale=2)
    private BigDecimal porc_arl=BigDecimal.ZERO;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNivelcargo() {
        return nivelcargo;
    }

    public void setNivelcargo(int nivelcargo) {
        this.nivelcargo = nivelcargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Column(length = 3000)
    public String getFunciones() {
        return funciones;
    }

    public void setFunciones(String funciones) {
        this.funciones = funciones;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public BigDecimal getPorc_arl() {
        return porc_arl;
    }

    public void setPorc_arl(BigDecimal porc_arl) {
        this.porc_arl = porc_arl;
    }
    
    public String nivelcargo()
    {
        switch(nivelcargo)
        {
            case 0: return "Auxiliar";
            case 1: return "Técnico";
            case 2: return "Técnologo";
            case 3: return "Profesional";
            case 4: return "Asesor";
            default: return "Auxiliar";
        }
    }        
    
}