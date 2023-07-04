package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Proveedores;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Consignaciones.class)
public class Consignaciones_ { 

    public static volatile SingularAttribute<Consignaciones, String> noident;
    public static volatile SingularAttribute<Consignaciones, String> usuarioModificador;
    public static volatile SingularAttribute<Consignaciones, Date> fechaModificacion;
    public static volatile SingularAttribute<Consignaciones, BigDecimal> valor;
    public static volatile SingularAttribute<Consignaciones, String> direccion;
    public static volatile SingularAttribute<Consignaciones, Date> fechaelaboracion;
    public static volatile SingularAttribute<Consignaciones, String> banco;
    public static volatile SingularAttribute<Consignaciones, String> noreciboconsignacion;
    public static volatile SingularAttribute<Consignaciones, Date> fechaconsinacion;
    public static volatile SingularAttribute<Consignaciones, String> nocuenta;
    public static volatile SingularAttribute<Consignaciones, String> usuarioCreador;
    public static volatile SingularAttribute<Consignaciones, String> nocheque;
    public static volatile SingularAttribute<Consignaciones, String> depositante;
    public static volatile SingularAttribute<Consignaciones, String> nombrecuenta;
    public static volatile SingularAttribute<Consignaciones, Proveedores> proveedores;
    public static volatile SingularAttribute<Consignaciones, Integer> tipoconsignacion;
    public static volatile SingularAttribute<Consignaciones, String> concepto;
    public static volatile SingularAttribute<Consignaciones, String> observaciones;
    public static volatile SingularAttribute<Consignaciones, Date> fechaCreacion;
    public static volatile SingularAttribute<Consignaciones, Long> id;
    public static volatile SingularAttribute<Consignaciones, Long> no_consecutivo;
    public static volatile SingularAttribute<Consignaciones, String> codbanco;
    public static volatile SingularAttribute<Consignaciones, String> telefono;

}