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

/**
 *
 * @author adminlinux
 */

@Entity
@Table(name = "ACTIVIDADESECONOMICAS")
public class ActividadesEconomicas implements Serializable {
  @Column(name = "ID", table = "ACTIVIDADESECONOMICAS", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_ACTIVIDADESECONOMICAS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_ACTIVIDADESECONOMICAS_ID", sequenceName = "SEQ_ACTIVIDADESECONOMICAS_ID", allocationSize = 1, initialValue = 1)
    private Long id;   
    private String codigo_grupotarifa;
    private String codigo_ciiu_219;
    private String codigo_ciiu_0079;
    @Column(length = 2000)
    private String descripcion;
     @Column(precision=16,scale=4)
    BigDecimal  tarifa_pormil;
     @Column(precision=16,scale=4)
    BigDecimal  tarifa_autoretencion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo_grupotarifa() {
        return codigo_grupotarifa;
    }

    public void setCodigo_grupotarifa(String codigo_grupotarifa) {
        this.codigo_grupotarifa = codigo_grupotarifa;
    }

    public String getCodigo_ciiu_219() {
        return codigo_ciiu_219;
    }

    public void setCodigo_ciiu_219(String codigo_ciiu_219) {
        this.codigo_ciiu_219 = codigo_ciiu_219;
    }

    public String getCodigo_ciiu_0079() {
        return codigo_ciiu_0079;
    }

    public void setCodigo_ciiu_0079(String codigo_ciiu_0079) {
        this.codigo_ciiu_0079 = codigo_ciiu_0079;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getTarifa_pormil() {
        return tarifa_pormil;
    }

    public void setTarifa_pormil(BigDecimal tarifa_pormil) {
        this.tarifa_pormil = tarifa_pormil;
    }

    public BigDecimal getTarifa_autoretencion() {
        return tarifa_autoretencion;
    }

    public void setTarifa_autoretencion(BigDecimal tarifa_autoretencion) {
        this.tarifa_autoretencion = tarifa_autoretencion;
    }

   
    
    
    
}
