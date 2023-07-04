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

/**
 *
 * @author adminlinux
 */

@Entity

public class TiposDocumentosContables implements Serializable {
    @Id
    @GeneratedValue(generator = "SEQ_TIPOSDOCUMENTOSCONTABLES_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_TIPOSDOCUMENTOSCONTABLES_ID", sequenceName = "SEQ_TIPOSDOCUMENTOSCONTABLES_ID", allocationSize = 1, initialValue = 1)
    private Long id;   
    private String codigo;
    private String nombre;

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
