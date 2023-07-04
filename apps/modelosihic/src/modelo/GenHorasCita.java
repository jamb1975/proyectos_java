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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author adminlinux
 */

@Entity
@Table(name = "GEN_HORAS_CITAS")
public class GenHorasCita implements Serializable {
    @Column(name = "ID", table = "GEN_HORAS_CITAS", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 0)
    @Id
    @GeneratedValue(generator = "SEQ_GEN_HORAS_CITAS", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_GEN_HORAS_CITAS", sequenceName = "SEQ_GEN_HORAS_CITAS", allocationSize = 1, initialValue = 1)
    private Long id;
    private int hora;
    private int minutos;
    private int zona;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }
    
    
    

}

