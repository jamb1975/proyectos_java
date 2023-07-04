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
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author innsend
 */
@Entity
@XmlRootElement
public class ConsecutivosFacturasPorSucursal implements Serializable {
   @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_CONSECUTIVOSFACTURASPORSUCURSAL",
		     sequenceName="SEQ_ID_CONSECUTIVOSFACTURASPORSUCURSAL",
		     allocationSize=1
		    )
    @Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_CONSECUTIVOSFACTURASPORSUCURSAL")
    @Column(name="ID")
    private Long id;
   @Length(max = 2)
   private String prefijomultiusuario;
   private Long consecutivonofacturamultiusuario;
   @Length(max = 2)
   private String prefijoindivudual;
   private Long consecutivonofacturaindividual;
   @Length(max = 2)
   private String prefijosoat;
   private Long consecutivonofacturasoat;
   @Length(max = 2)
   private String prefijoparticular;
   private Long consecutivonofacturaparticular;
   @ManyToOne
   private Sucursales sucursales;
   Long consecutivorips;
   @Length(max = 3)
   private String prefijoindivudualsjg;
   private Long consecutivonofacturaindividualsjg;
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefijomultiusuario() {
        return prefijomultiusuario;
    }

    public void setPrefijomultiusuario(String prefijomultiusuario) {
        this.prefijomultiusuario = prefijomultiusuario;
    }

    public Long getConsecutivonofacturamultiusuario() {
        return consecutivonofacturamultiusuario;
    }

    public void setConsecutivonofacturamultiusuario(Long consecutivonofacturamultiusuario) {
        this.consecutivonofacturamultiusuario = consecutivonofacturamultiusuario;
    }

    public String getPrefijoindivudual() {
        return prefijoindivudual;
    }

    public void setPrefijoindivudual(String prefijoindivudual) {
        this.prefijoindivudual = prefijoindivudual;
    }

    public Long getConsecutivonofacturaindividual() {
        return consecutivonofacturaindividual;
    }

    public void setConsecutivonofacturaindividual(Long consecutivonofacturaindividual) {
        this.consecutivonofacturaindividual = consecutivonofacturaindividual;
    }

    public String getPrefijosoat() {
        return prefijosoat;
    }

    public void setPrefijosoat(String prefijosoat) {
        this.prefijosoat = prefijosoat;
    }

    public Long getConsecutivonofacturasoat() {
        return consecutivonofacturasoat;
    }

    public void setConsecutivonofacturasoat(Long consecutivonofacturasoat) {
        this.consecutivonofacturasoat = consecutivonofacturasoat;
    }

    public String getPrefijoparticular() {
        return prefijoparticular;
    }

    public void setPrefijoparticular(String prefijoparticular) {
        this.prefijoparticular = prefijoparticular;
    }

    public Long getConsecutivonofacturaparticular() {
        return consecutivonofacturaparticular;
    }

    public void setConsecutivonofacturaparticular(Long consecutivonofacturaparticular) {
        this.consecutivonofacturaparticular = consecutivonofacturaparticular;
    }

    public Sucursales getSucursales() {
        return sucursales;
    }

    public void setSucursales(Sucursales sucursales) {
        this.sucursales = sucursales;
    }

    public Long getConsecutivorips() {
        return consecutivorips;
    }

    public void setConsecutivorips(Long consecutivorips) {
        this.consecutivorips = consecutivorips;
    }

    public String getPrefijoindivudualsjg() {
        return prefijoindivudualsjg;
    }

    public void setPrefijoindivudualsjg(String prefijoindivudualsjg) {
        this.prefijoindivudualsjg = prefijoindivudualsjg;
    }

    public Long getConsecutivonofacturaindividualsjg() {
        return consecutivonofacturaindividualsjg;
    }

    public void setConsecutivonofacturaindividualsjg(Long consecutivonofacturaindividualsjg) {
        this.consecutivonofacturaindividualsjg = consecutivonofacturaindividualsjg;
    }

   
   
   
}
