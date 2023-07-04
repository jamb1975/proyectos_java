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
import javax.persistence.OneToOne;

/**
 *
 * @author adminlinux
 */
@Entity
public class Usuarios implements Serializable {
      @javax.persistence.SequenceGenerator(
		     name="SEQ_ID_USUA",
		     sequenceName="SEQ_ID_USUA",
		     allocationSize=1
		    )
    @Id @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ID_USUA")
    @Column(name="ID")
    private Long id;
    
    @OneToOne  
    private Terceros genPersonas;
    @Column(length = 8) 
    private String usuario;
   @Column(length = 8) 
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Terceros getGenPersonas() {
        return genPersonas;
    }

    public void setGenPersonas(Terceros genPersonas) {
        this.genPersonas = genPersonas;
    }

    

    
    
    
    
}
