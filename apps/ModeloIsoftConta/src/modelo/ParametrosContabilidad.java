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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author isoft
 */
@Entity
@XmlRootElement
public class ParametrosContabilidad implements Serializable {
    @Id
    @GeneratedValue(generator = "SEQ_PARAMETROSCONTABILIDAD_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PARAMETROSCONTABILIDAD_ID", sequenceName = "SEQ_PARAMETROSCONTABILIDAD_ID", allocationSize = 1, initialValue = 1)
    private Long id;  
    private BigDecimal valorunidadvalortributario=BigDecimal.ZERO;
    private BigDecimal valorañoanteriorpatrimoniooingresosbrutos=BigDecimal.ZERO;
    private int cantuvtcomprasretefuente=0;
    private int cantuvtpatrimoniooingresosbrutosmayor=0;
    @Column(precision = 5,scale=2)
    private BigDecimal porccomprasretefuenterenta=BigDecimal.ZERO; 
    @Column(precision = 5,scale=2)
    private BigDecimal porccomprasretefuenteiva=BigDecimal.ZERO; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorunidadvalortributario() {
        return valorunidadvalortributario;
    }

    public void setValorunidadvalortributario(BigDecimal valorunidadvalortributario) {
        this.valorunidadvalortributario = valorunidadvalortributario;
    }

    public BigDecimal getValorañoanteriorpatrimoniooingresosbrutos() {
        return valorañoanteriorpatrimoniooingresosbrutos;
    }

    public void setValorañoanteriorpatrimoniooingresosbrutos(BigDecimal valorañoanteriorpatrimoniooingresosbrutos) {
        this.valorañoanteriorpatrimoniooingresosbrutos = valorañoanteriorpatrimoniooingresosbrutos;
    }

    public int getCantuvtcomprasretefuente() {
        return cantuvtcomprasretefuente;
    }

    public void setCantuvtcomprasretefuente(int cantuvtcomprasretefuente) {
        this.cantuvtcomprasretefuente = cantuvtcomprasretefuente;
    }

    public int getCantuvtpatrimoniooingresosbrutosmayor() {
        return cantuvtpatrimoniooingresosbrutosmayor;
    }

    public void setCantuvtpatrimoniooingresosbrutosmayor(int cantuvtpatrimoniooingresosbrutosmayor) {
        this.cantuvtpatrimoniooingresosbrutosmayor = cantuvtpatrimoniooingresosbrutosmayor;
    }

    public BigDecimal getPorccomprasretefuenterenta() {
        return porccomprasretefuenterenta;
    }

    public void setPorccomprasretefuenterenta(BigDecimal porccomprasretefuenterenta) {
        this.porccomprasretefuenterenta = porccomprasretefuenterenta;
    }

    public BigDecimal getPorccomprasretefuenteiva() {
        return porccomprasretefuenteiva;
    }

    public void setPorccomprasretefuenteiva(BigDecimal porccomprasretefuenteiva) {
        this.porccomprasretefuenteiva = porccomprasretefuenteiva;
    }
    
}
