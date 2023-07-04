package modelo;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.GenCentrosPoblados;
import modelo.GenMunicipios;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(GenUnidadesOrganizacion.class)
public class GenUnidadesOrganizacion_ { 

    public static volatile SingularAttribute<GenUnidadesOrganizacion, String> nivelAtencion;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, Short> estado;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, Short> tipo;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, String> codigo;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, String> sigla;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, String> usuarioModificador;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, Date> fechaModificacion;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, String> direccion;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, String> nombre;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, String> usuarioCreador;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, BigInteger> aprobadorOrdenes;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, String> nit;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, GenCentrosPoblados> genCentrosPoblados;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, Date> fechaCreacion;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, byte[]> logo;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, Long> id;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, String> telefono;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, Short> nivel;
    public static volatile SingularAttribute<GenUnidadesOrganizacion, GenMunicipios> genMunicipios;

}