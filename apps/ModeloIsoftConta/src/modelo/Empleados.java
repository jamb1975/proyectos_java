/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adminlinux
 */
@Entity
@XmlRootElement
@Table(name = "Empleados")
public class Empleados {
 @Column(name = "ID", table = "Empleados", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_EMPLEADOS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_EMPLEADOS_ID", sequenceName = "SEQ_EMPLEADOS_ID", allocationSize = 1, initialValue = 1)
    private Long id;  
    @ManyToOne(optional = true, targetEntity = Terceros.class,cascade = CascadeType.ALL)
    @JoinColumn(name="TERCEROS_ID")
    private Terceros genPersonas;
    @ManyToOne(optional = true, targetEntity = CargosEntidad.class)
    @JoinColumn(name="CARGOSENTIDAD_ID")
    private CargosEntidad cargosEntidad;
    @ManyToOne(optional = true, targetEntity = GenEapb.class,fetch = FetchType.LAZY)
    @JoinColumn(name="GEN_EPS_ID")
    private GenEapb genEPS;
    @ManyToOne(optional = true, targetEntity = GenEapb.class,fetch = FetchType.LAZY)
    @JoinColumn(name="GEN_PENSION_ID")
    private GenEapb genpension;
    @ManyToOne(optional = true, targetEntity = GenEapb.class,fetch = FetchType.LAZY)
    @JoinColumn(name="GEN_ARL_ID")
    private GenEapb genarl;
    @ManyToOne(optional = true, targetEntity = GenEapb.class,fetch = FetchType.LAZY)
    @JoinColumn(name="CAJA_COMP_ID")
    private GenEapb cajacomp;
    private boolean activo=true;
    private int rh;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Terceros getGenPersonas() {
        return genPersonas;
    }

    public void setGenPersonas(Terceros genPersonas) {
        this.genPersonas = genPersonas;
    }

    

    public CargosEntidad getCargosEntidad() {
        return cargosEntidad;
    }

    public void setCargosEntidad(CargosEntidad cargosEntidad) {
        this.cargosEntidad = cargosEntidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getRh() {
        return rh;
    }

    public void setRh(int rh) {
        this.rh = rh;
    }

    public GenEapb getGenEPS() {
        return genEPS;
    }

    public void setGenEPS(GenEapb genEPS) {
        this.genEPS = genEPS;
    }

    public GenEapb getGenpension() {
        return genpension;
    }

    public void setGenpension(GenEapb genpension) {
        this.genpension = genpension;
    }

    public GenEapb getGenarl() {
        return genarl;
    }

    public void setGenarl(GenEapb genarl) {
        this.genarl = genarl;
    }

    public GenEapb getCajacomp() {
        return cajacomp;
    }

    public void setCajacomp(GenEapb cajacomp) {
        this.cajacomp = cajacomp;
    }
    
 
}
