/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.control.TreeTableView;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author karol
 */
@XmlRootElement

public class TempFacturaItems {
    private Long m_LIdTempMesa;
    private Long m_lIdTerc;
    private String m_sNombre;
    private String m_sNoIdent;
    private String m_sDir1;
    private String m_sCelular;
    private String m_sEmail;
    private Long m_lIdFact;
    private int m_iNoMesa;
    private boolean credito;
    private Date m_dFacturaDate;
    private List<FacturaItem> facturaItem=new ArrayList<FacturaItem>();
    TreeTableView tt;
    public Long getM_lIdTerc() {
        return m_lIdTerc;
    }

    public void setM_lIdTerc(Long m_lIdTerc) {
        this.m_lIdTerc = m_lIdTerc;
    }

    public String getM_sNombre() {
        return m_sNombre;
    }

    public void setM_sNombre(String m_sNombre) {
        this.m_sNombre = m_sNombre;
    }

    public String getM_sNoIdent() {
        return m_sNoIdent;
    }

    public void setM_sNoIdent(String m_sNoIdent) {
        this.m_sNoIdent = m_sNoIdent;
    }

    public String getM_sDir1() {
        return m_sDir1;
    }

    public void setM_sDir1(String m_sDir1) {
        this.m_sDir1 = m_sDir1;
    }

    public String getM_sCelular() {
        return m_sCelular;
    }

    public void setM_sCelular(String m_sCelular) {
        this.m_sCelular = m_sCelular;
    }

    public String getM_sEmail() {
        return m_sEmail;
    }

    public void setM_sEmail(String m_sEmail) {
        this.m_sEmail = m_sEmail;
    }

    public Long getM_lIdFact() {
        return m_lIdFact;
    }

    public void setM_lIdFact(Long m_lIdFact) {
        this.m_lIdFact = m_lIdFact;
    }

   

    public int getM_iNoMesa() {
        return m_iNoMesa;
    }

    public void setM_iNoMesa(int m_iNoMesa) {
        this.m_iNoMesa = m_iNoMesa;
    }

    public Date getM_dFacturaDate() {
        return m_dFacturaDate;
    }

    public void setM_dFacturaDate(Date m_dFacturaDate) {
        this.m_dFacturaDate = m_dFacturaDate;
    }

    public List<FacturaItem> getFacturaItem() {
        return facturaItem;
    }

    public void setFacturaItem(List<FacturaItem> facturaItem) {
        this.facturaItem = facturaItem;
    }

    public Long getM_LIdTempMesa() {
        return m_LIdTempMesa;
    }

    public void setM_LIdTempMesa(Long m_LIdTempMesa) {
        this.m_LIdTempMesa = m_LIdTempMesa;
    }

    public boolean isCredito() {
        return credito;
    }

    public void setCredito(boolean credito) {
        this.credito = credito;
    }

    
   
  

}
