/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author adminlinux
 */

@Entity

public class ModeloTiposDocumentosContables implements Serializable {
    @Id
    @GeneratedValue(generator = "SEQ_MODELOTIPOSDOCUMENTOSCONTABLES_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_MODELOTIPOSDOCUMENTOSCONTABLES_ID", sequenceName = "SEQ_MODELOTIPOSDOCUMENTOSCONTABLES_ID", allocationSize = 1, initialValue = 1)
    private Long id;   
    private String descripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    private TiposDocumentosContables tiposDocumentosContables;
    @OneToMany(fetch = FetchType.LAZY,mappedBy="modeloTiposDocumentosContables", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<AsientosModelosTiposDocumentosContables> asientosModelosTiposDocumentosContableses = new ArrayList<>();
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TiposDocumentosContables getTiposDocumentosContables() {
        return tiposDocumentosContables;
    }

    public void setTiposDocumentosContables(TiposDocumentosContables tiposDocumentosContables) {
        this.tiposDocumentosContables = tiposDocumentosContables;
    }

    public List<AsientosModelosTiposDocumentosContables> getAsientosModelosTiposDocumentosContableses() {
        return asientosModelosTiposDocumentosContableses;
    }

    public void setAsientosModelosTiposDocumentosContableses(List<AsientosModelosTiposDocumentosContables> asientosModelosTiposDocumentosContableses) {
        this.asientosModelosTiposDocumentosContableses = asientosModelosTiposDocumentosContableses;
    }

   public void addcuenta(Puc conPuc,String tipomovimiento,BigDecimal porcentajevalor,BigDecimal porcentajevalor2,BigDecimal porcentajevalor3,int centrocostos)
   {
           System.out.println("Bd->"+round(porcentajevalor)); 
       AsientosModelosTiposDocumentosContables asientosModelosTiposDocumentosContables=new AsientosModelosTiposDocumentosContables();
      for(AsientosModelosTiposDocumentosContables amtc:asientosModelosTiposDocumentosContableses)
      {
          
        if(amtc.getConPuc().getId().equals(conPuc.getId()))
        {
            amtc.setCentrocostos(centrocostos);
            amtc.setPorcentajevalor(round(porcentajevalor));
            amtc.setPorcentajevalor2(round(porcentajevalor2));
            amtc.setPorcentajevalor3(round(porcentajevalor3));
            amtc.setTipomovimiento(tipomovimiento);
            return;
        }
      }
            asientosModelosTiposDocumentosContables.setCentrocostos(centrocostos);
            asientosModelosTiposDocumentosContables.setPorcentajevalor(round(porcentajevalor));
            asientosModelosTiposDocumentosContables.setPorcentajevalor2(round(porcentajevalor2));
            asientosModelosTiposDocumentosContables.setPorcentajevalor3(round(porcentajevalor3));
            asientosModelosTiposDocumentosContables.setTipomovimiento(tipomovimiento);
            asientosModelosTiposDocumentosContables.setModeloTiposDocumentosContables(this);
            asientosModelosTiposDocumentosContables.setConPuc(conPuc);
            asientosModelosTiposDocumentosContableses.add(asientosModelosTiposDocumentosContables);
         
   }
  public void remove(AsientosModelosTiposDocumentosContables asientosModelosTiposDocumentosContables)
  {
      asientosModelosTiposDocumentosContableses.remove(asientosModelosTiposDocumentosContables);
  }        
    
  private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }   
}
