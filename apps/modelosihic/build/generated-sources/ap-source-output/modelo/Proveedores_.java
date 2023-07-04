package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.InformacionTributaria;
import modelo.Puc;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Proveedores.class)
public class Proveedores_ { 

    public static volatile SingularAttribute<Proveedores, Boolean> responsableiva;
    public static volatile SingularAttribute<Proveedores, String> digitoverificacion;
    public static volatile SingularAttribute<Proveedores, Puc> cuentacreditogastos;
    public static volatile SingularAttribute<Proveedores, Puc> cuentacreditoegresos;
    public static volatile SingularAttribute<Proveedores, String> nombre;
    public static volatile SingularAttribute<Proveedores, String> usuarioCreador;
    public static volatile SingularAttribute<Proveedores, Boolean> cal;
    public static volatile SingularAttribute<Proveedores, String> no_ident;
    public static volatile SingularAttribute<Proveedores, Puc> cuentadebitogastos;
    public static volatile SingularAttribute<Proveedores, String> razonsocial;
    public static volatile SingularAttribute<Proveedores, InformacionTributaria> informacionTributaria;
    public static volatile SingularAttribute<Proveedores, String> celular;
    public static volatile SingularAttribute<Proveedores, Long> id;
    public static volatile SingularAttribute<Proveedores, String> tipopersona;
    public static volatile SingularAttribute<Proveedores, String> email;
    public static volatile SingularAttribute<Proveedores, String> tipoidentificacion;
    public static volatile SingularAttribute<Proveedores, Date> fechaModificacion;
    public static volatile SingularAttribute<Proveedores, String> dpto;
    public static volatile SingularAttribute<Proveedores, String> primerapellido;
    public static volatile SingularAttribute<Proveedores, String> dir1;
    public static volatile SingularAttribute<Proveedores, Puc> cuentadebitoegresos;
    public static volatile SingularAttribute<Proveedores, String> pais;
    public static volatile SingularAttribute<Proveedores, String> segundoapellido;
    public static volatile SingularAttribute<Proveedores, byte[]> foto;
    public static volatile SingularAttribute<Proveedores, String> ciudad;
    public static volatile SingularAttribute<Proveedores, Date> fechaCreacion;
    public static volatile SingularAttribute<Proveedores, String> sexo;
    public static volatile SingularAttribute<Proveedores, String> profesion;
    public static volatile SingularAttribute<Proveedores, String> tel_fijo;

}