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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author adminlinux
 */

@Entity

public class AsientosModelosTiposDocumentosContables implements Serializable {
   @Id
    @GeneratedValue(generator = "SEQ_ASIENTOSMODELOSTIPOSDOCUMENTOSCONTABLES_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_ASIENTOSMODELOSTIPOSDOCUMENTOSCONTABLES_ID", sequenceName = "SEQ_ASIENTOSMODELOSTIPOSDOCUMENTOSCONTABLES_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @Column(length = 1)
    private String tipomovimiento;
    @ManyToOne(fetch = FetchType.LAZY)
    private Puc conPuc;
    @ManyToOne(fetch = FetchType.LAZY)
    private ModeloTiposDocumentosContables modeloTiposDocumentosContables;
    private int centrocostos;
    @Column(precision = 5,scale=2)
    private BigDecimal porcentajevalor;
    @Column(precision = 5,scale=2)
    private BigDecimal porcentajevalor2;
    @Column(precision = 5,scale=2)
    private BigDecimal porcentajevalor3;
    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipomovimiento() {
        return tipomovimiento;
    }

    public void setTipomovimiento(String tipomovimiento) {
        this.tipomovimiento = tipomovimiento;
    }

    public Puc getConPuc() {
        return conPuc;
    }

    public void setConPuc(Puc conPuc) {
        this.conPuc = conPuc;
    }

    public ModeloTiposDocumentosContables getModeloTiposDocumentosContables() {
        return modeloTiposDocumentosContables;
    }

    public void setModeloTiposDocumentosContables(ModeloTiposDocumentosContables modeloTiposDocumentosContables) {
        this.modeloTiposDocumentosContables = modeloTiposDocumentosContables;
    }

    public int getCentrocostos() {
        return centrocostos;
    }

    public void setCentrocostos(int centrocostos) {
        this.centrocostos = centrocostos;
    }

    public BigDecimal getPorcentajevalor() {
        return porcentajevalor;
    }

    public void setPorcentajevalor(BigDecimal porcentajevalor) {
        this.porcentajevalor = porcentajevalor;
    }

    public BigDecimal getPorcentajevalor2() {
        return porcentajevalor2;
    }

    public void setPorcentajevalor2(BigDecimal porcentajevalor2) {
        this.porcentajevalor2 = porcentajevalor2;
    }

    public BigDecimal getPorcentajevalor3() {
        return porcentajevalor3;
    }

    public void setPorcentajevalor3(BigDecimal porcentajevalor3) {
        this.porcentajevalor3 = porcentajevalor3;
    }
    public String centrocosto()
    {
        switch(centrocostos)
        {
            case 0: return "COMPRAS";
            case 1: return "PRODUCCIÓN";
            case 2: return "VENTAS";
            case 3: return "PRODUCCION";
            default: return "COMPRAS";
                    
        }
    }
   public BigDecimal porcentajereal()
   {
       return porcentajevalor.multiply(new BigDecimal(0.01));
   }
}
