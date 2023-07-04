package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.NominaEmpleados;
import modelo.ParametrosNomina;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Nomina.class)
public class Nomina_ { 

    public static volatile SingularAttribute<Nomina, BigDecimal> ss_arl;
    public static volatile SingularAttribute<Nomina, BigDecimal> pp_prima;
    public static volatile SingularAttribute<Nomina, Date> periodofin;
    public static volatile SingularAttribute<Nomina, BigDecimal> ss_fondopensiones;
    public static volatile SingularAttribute<Nomina, String> usuarioCreador;
    public static volatile SingularAttribute<Nomina, Integer> horasdiarias;
    public static volatile SingularAttribute<Nomina, BigDecimal> totalretefuente;
    public static volatile SingularAttribute<Nomina, BigDecimal> pf_sena;
    public static volatile SingularAttribute<Nomina, Long> id;
    public static volatile SingularAttribute<Nomina, String> usuarioModificador;
    public static volatile SingularAttribute<Nomina, Date> fechaModificacion;
    public static volatile SingularAttribute<Nomina, BigDecimal> totalotrosingresos;
    public static volatile SingularAttribute<Nomina, BigDecimal> totalotrasdeducciones;
    public static volatile SingularAttribute<Nomina, BigDecimal> totaldevengado;
    public static volatile SingularAttribute<Nomina, BigDecimal> pp_intcesantias;
    public static volatile ListAttribute<Nomina, NominaEmpleados> nominaempleadositems;
    public static volatile SingularAttribute<Nomina, BigDecimal> totalauxtransporte;
    public static volatile SingularAttribute<Nomina, BigDecimal> pp_cesantias;
    public static volatile SingularAttribute<Nomina, BigDecimal> totalsalud;
    public static volatile SingularAttribute<Nomina, BigDecimal> pf_cajacf;
    public static volatile SingularAttribute<Nomina, Date> periodoinicio;
    public static volatile SingularAttribute<Nomina, BigDecimal> pf_icbf;
    public static volatile SingularAttribute<Nomina, BigDecimal> totalbasico;
    public static volatile SingularAttribute<Nomina, BigDecimal> pp_vacaciones;
    public static volatile SingularAttribute<Nomina, String> observaciones;
    public static volatile SingularAttribute<Nomina, Date> fechaCreacion;
    public static volatile SingularAttribute<Nomina, BigDecimal> totaldeducciones;
    public static volatile SingularAttribute<Nomina, Boolean> exonerado_pfsalud;
    public static volatile SingularAttribute<Nomina, BigDecimal> totalsueldoneto;
    public static volatile SingularAttribute<Nomina, Long> no_consecutivo;
    public static volatile SingularAttribute<Nomina, BigDecimal> ss_salud;
    public static volatile SingularAttribute<Nomina, ParametrosNomina> parametrosNomina;
    public static volatile SingularAttribute<Nomina, BigDecimal> totalpension;

}