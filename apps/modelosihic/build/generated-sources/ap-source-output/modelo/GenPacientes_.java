package modelo;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.GenConvenios;
import modelo.GenEapb;
import modelo.GenNivelesUsuarios;
import modelo.GenPersonas;
import modelo.GenTiposAfiliacion;
import modelo.GenTiposUsuarios;
import modelo.GenUnidadesOrganizacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(GenPacientes.class)
public class GenPacientes_ { 

    public static volatile SingularAttribute<GenPacientes, BigInteger> estado;
    public static volatile SingularAttribute<GenPacientes, GenNivelesUsuarios> genNivelesUsuarios;
    public static volatile SingularAttribute<GenPacientes, GenEapb> genConveniosEapb;
    public static volatile SingularAttribute<GenPacientes, String> usuarioModificador;
    public static volatile SingularAttribute<GenPacientes, Date> fechaModificacion;
    public static volatile SingularAttribute<GenPacientes, GenTiposUsuarios> genTiposUsuarios;
    public static volatile SingularAttribute<GenPacientes, String> anexo;
    public static volatile SingularAttribute<GenPacientes, Date> fechaAfiliacion;
    public static volatile SingularAttribute<GenPacientes, GenUnidadesOrganizacion> genUnidadesOrganizacion;
    public static volatile SingularAttribute<GenPacientes, String> usuarioCreador;
    public static volatile SingularAttribute<GenPacientes, GenEapb> aseguradora;
    public static volatile SingularAttribute<GenPacientes, String> documentoAfiliadoDelGrupo;
    public static volatile SingularAttribute<GenPacientes, String> novedades;
    public static volatile SingularAttribute<GenPacientes, String> grupoSanguineo;
    public static volatile SingularAttribute<GenPacientes, GenEapb> genEapb;
    public static volatile SingularAttribute<GenPacientes, String> documentoAnterior;
    public static volatile SingularAttribute<GenPacientes, Date> fechaCreacion;
    public static volatile SingularAttribute<GenPacientes, GenConvenios> genConvenios;
    public static volatile SingularAttribute<GenPacientes, Long> id;
    public static volatile SingularAttribute<GenPacientes, String> comentarios;
    public static volatile SingularAttribute<GenPacientes, GenTiposAfiliacion> genTiposAfiliacion;
    public static volatile SingularAttribute<GenPacientes, GenPersonas> genPersonas;
    public static volatile SingularAttribute<GenPacientes, String> factorRh;

}