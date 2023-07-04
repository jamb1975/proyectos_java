package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.GenUnidadesOrganizacion;
import modelo.HclHistoriasClinicas;
import modelo.HclTiposAtenciones;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclAtenciones.class)
public class HclAtenciones_ { 

    public static volatile SingularAttribute<HclAtenciones, HclHistoriasClinicas> hclHistoriasClinicas;
    public static volatile SingularAttribute<HclAtenciones, String> estado;
    public static volatile SingularAttribute<HclAtenciones, String> usuarioModificador;
    public static volatile SingularAttribute<HclAtenciones, Date> fechaModificacion;
    public static volatile SingularAttribute<HclAtenciones, Date> fechaInicio;
    public static volatile SingularAttribute<HclAtenciones, Date> fechaCreacion;
    public static volatile SingularAttribute<HclAtenciones, Long> id;
    public static volatile SingularAttribute<HclAtenciones, GenUnidadesOrganizacion> genUnidadesOrganizacion;
    public static volatile SingularAttribute<HclAtenciones, Date> fechaFin;
    public static volatile SingularAttribute<HclAtenciones, String> usuarioCreador;
    public static volatile SingularAttribute<HclAtenciones, HclTiposAtenciones> hclTiposAtenciones;

}