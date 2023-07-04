package modelo;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.FactorHorasExtras;
import modelo.NominaEmpleados;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Novedades.class)
public class Novedades_ { 

    public static volatile SingularAttribute<Novedades, Integer> tipohoraextra;
    public static volatile SingularAttribute<Novedades, BigDecimal> valor;
    public static volatile SingularAttribute<Novedades, Integer> tiponovedad;
    public static volatile SingularAttribute<Novedades, Long> id;
    public static volatile SingularAttribute<Novedades, FactorHorasExtras> factorHorasExtras;
    public static volatile SingularAttribute<Novedades, Integer> cantidad;
    public static volatile SingularAttribute<Novedades, NominaEmpleados> nominaEmpleados;
    public static volatile SingularAttribute<Novedades, String> observacion;

}