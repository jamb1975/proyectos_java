package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.GenConvenios;
import modelo.GenCuotasModeradorasEapb;
import modelo.GenTiposUsuarios;
import modelo.InformacionTributaria;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(GenEapb.class)
public class GenEapb_ { 

    public static volatile ListAttribute<GenEapb, GenConvenios> convenioseps;
    public static volatile SingularAttribute<GenEapb, String> codigo;
    public static volatile SingularAttribute<GenEapb, String> usuarioModificador;
    public static volatile SingularAttribute<GenEapb, Date> fechaModificacion;
    public static volatile SingularAttribute<GenEapb, GenTiposUsuarios> genTiposUsuarios;
    public static volatile SingularAttribute<GenEapb, String> direccion;
    public static volatile ListAttribute<GenEapb, GenCuotasModeradorasEapb> li_genCuotasModeradorasEapbs;
    public static volatile SingularAttribute<GenEapb, String> codigoregionalcont;
    public static volatile SingularAttribute<GenEapb, String> nombre;
    public static volatile SingularAttribute<GenEapb, String> usuarioCreador;
    public static volatile SingularAttribute<GenEapb, String> nit;
    public static volatile SingularAttribute<GenEapb, InformacionTributaria> informacionTributaria;
    public static volatile SingularAttribute<GenEapb, Date> fechaCreacion;
    public static volatile SingularAttribute<GenEapb, Long> id;
    public static volatile SingularAttribute<GenEapb, String> telefono;
    public static volatile SingularAttribute<GenEapb, String> codigomovilidad;
    public static volatile SingularAttribute<GenEapb, String> regimen;
    public static volatile SingularAttribute<GenEapb, String> codigoregionalmov;

}