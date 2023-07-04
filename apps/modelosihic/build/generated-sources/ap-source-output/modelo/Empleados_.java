package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.CargosEntidad;
import modelo.GenEapb;
import modelo.GenPersonas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Empleados.class)
public class Empleados_ { 

    public static volatile SingularAttribute<Empleados, Integer> rh;
    public static volatile SingularAttribute<Empleados, GenEapb> genpension;
    public static volatile SingularAttribute<Empleados, Long> id;
    public static volatile SingularAttribute<Empleados, CargosEntidad> cargosEntidad;
    public static volatile SingularAttribute<Empleados, GenEapb> cajacomp;
    public static volatile SingularAttribute<Empleados, GenEapb> genEPS;
    public static volatile SingularAttribute<Empleados, GenPersonas> genPersonas;
    public static volatile SingularAttribute<Empleados, GenEapb> genarl;
    public static volatile SingularAttribute<Empleados, Boolean> activo;

}