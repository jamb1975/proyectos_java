/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author adminlinux
 */
@Entity
@Table(name = "HCLHISTORIASCLINICAS")
public class HclHistoriasClinicas implements Serializable {
    @Column(name = "ID", table = "HCLHISTORIASCLINICAS", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_HCLHISTORIASCLINICAS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_HCLHISTORIASCLINICAS_ID", sequenceName = "SEQ_HCLHISTORIASCLINICAS_ID", allocationSize = 1, initialValue = 1)
    private  Long id;
    @ManyToOne(optional = true, targetEntity = GenPacientes.class)
    @JoinColumn(name="GEN_PACIENTES_ID",unique = true)
    private GenPacientes genPacientes;  
     private int estado;
     private int tipo;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenPacientes getGenPacientes() {
        return genPacientes;
    }

    public void setGenPacientes(GenPacientes genPacientes) {
        this.genPacientes = genPacientes;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
  
}
