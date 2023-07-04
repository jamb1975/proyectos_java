package modelo;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Empleados;
import modelo.Nomina;
import modelo.Novedades;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(NominaEmpleados.class)
public class NominaEmpleados_ { 

    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> valorretefuente;
    public static volatile ListAttribute<NominaEmpleados, Novedades> novedadesItems;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> valorhorasextras;
    public static volatile SingularAttribute<NominaEmpleados, Long> cantdiasnotrabajados;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> valorcomisiones;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> totaldevengado;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> valorpension;
    public static volatile SingularAttribute<NominaEmpleados, Long> cantdiastrabajados;
    public static volatile SingularAttribute<NominaEmpleados, Empleados> empleados;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> auxiliotransporte;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> valorsalud;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> valordeudaentidadfinancieras;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> valorotrasdeudas;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> totalbasico;
    public static volatile SingularAttribute<NominaEmpleados, Nomina> nomina;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> fondosolidaridad;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> netopagado;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> totaldeducciones;
    public static volatile SingularAttribute<NominaEmpleados, Long> id;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> valorrecargonocturno;
    public static volatile SingularAttribute<NominaEmpleados, Long> no_consecutivo;
    public static volatile SingularAttribute<NominaEmpleados, BigDecimal> valordeudaterceros;

}