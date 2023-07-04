/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;
import modelo.Puc;

/**
 *
 * @author adminlinux
 */
public class SumaAsientosContablesDto {
    
    private Puc conPuc;
    private BigDecimal totaldebe=BigDecimal.ZERO;
    private BigDecimal totalhaber=BigDecimal.ZERO;
    public SumaAsientosContablesDto(Puc conPuc, BigDecimal debe, BigDecimal haber)
    {
        this.conPuc=conPuc;
        this.totaldebe=debe;
        this.totalhaber=haber;
    } 

    public Puc getConPuc() {
        return conPuc;
    }

    public void setConPuc(Puc conPuc) {
        this.conPuc = conPuc;
    }

    public BigDecimal getTotaldebe() {
        return totaldebe;
    }

    public void setTotaldebe(BigDecimal totaldebe) {
        this.totaldebe = totaldebe;
    }

    public BigDecimal getTotalhaber() {
        return totalhaber;
    }

    public void setTotalhaber(BigDecimal totalhaber) {
        this.totalhaber = totalhaber;
    }
            
}
