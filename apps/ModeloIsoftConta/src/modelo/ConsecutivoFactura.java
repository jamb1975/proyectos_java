/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author innsend
 */
@Entity
@XmlRootElement
public class ConsecutivoFactura implements Serializable {
   @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_CONSECUTIVOFACTURA",
		     sequenceName="SEQ_ID_CONSECUTIVOFACTURA",
		     allocationSize=1
		    )
    @Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_CONSECUTIVOFACTURA")
    @Column(name="ID")
    private Long id;
    private Long consecutivonofactura;
  
   
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsecutivonofactura() {
        return consecutivonofactura;
    }

    public void setConsecutivonofactura(Long consecutivonofactura) {
        this.consecutivonofactura = consecutivonofactura;
    }

    

   
   
   
}
