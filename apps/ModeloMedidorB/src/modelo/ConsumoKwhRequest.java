/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adminlinux
 */
@XmlRootElement
public class ConsumoKwhRequest {
    private String irms;
    private String vrms;
    private String kwh;

    public String getIrms() {
        return irms;
    }

    public void setIrms(String irms) {
        this.irms = irms;
    }

    public String getVrms() {
        return vrms;
    }

    public void setVrms(String vrms) {
        this.vrms = vrms;
    }

    public String getKwh() {
        return kwh;
    }

    public void setKwh(String kwh) {
        this.kwh = kwh;
    }
    
}
