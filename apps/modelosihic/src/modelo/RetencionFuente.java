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
public class RetencionFuente implements Serializable {
    @Id
    @GeneratedValue(generator = "SEQ_RETENCIONFUENTE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_RETENCIONFUENTE", sequenceName = "SEQ_RETENCIONFUENTE", allocationSize = 1, initialValue = 1)
    private Long id;
    @Column(length = 2000)
    private String concepto;
    private int codigo;
    private int condicionuvt;
    @Column(precision=16,scale=2)
    BigDecimal porcentaje=BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCondicionuvt() {
        return condicionuvt;
    }

    public void setCondicionuvt(int condicionuvt) {
        this.condicionuvt = condicionuvt;
    }

    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
   
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    
}
