package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Proveedores;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(NotaCredito.class)
public class NotaCredito_ { 

    public static volatile SingularAttribute<NotaCredito, String> noident;
    public static volatile SingularAttribute<NotaCredito, String> usuarioModificador;
    public static volatile SingularAttribute<NotaCredito, Date> fechaModificacion;
    public static volatile SingularAttribute<NotaCredito, BigDecimal> valor;
    public static volatile SingularAttribute<NotaCredito, String> direccion;
    public static volatile SingularAttribute<NotaCredito, Date> fechaelaboracion;
    public static volatile SingularAttribute<NotaCredito, String> banco;
    public static volatile SingularAttribute<NotaCredito, String> nocuenta;
    public static volatile SingularAttribute<NotaCredito, String> usuarioCreador;
    public static volatile SingularAttribute<NotaCredito, Proveedores> proveedores;
    public static volatile SingularAttribute<NotaCredito, String> cliente;
    public static volatile SingularAttribute<NotaCredito, String> concepto;
    public static volatile SingularAttribute<NotaCredito, String> observaciones;
    public static volatile SingularAttribute<NotaCredito, Date> fechaCreacion;
    public static volatile SingularAttribute<NotaCredito, Long> id;
    public static volatile SingularAttribute<NotaCredito, Long> no_consecutivo;
    public static volatile SingularAttribute<NotaCredito, String> telefono;

}