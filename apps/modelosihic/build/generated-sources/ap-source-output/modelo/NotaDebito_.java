package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Proveedores;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(NotaDebito.class)
public class NotaDebito_ { 

    public static volatile SingularAttribute<NotaDebito, String> noident;
    public static volatile SingularAttribute<NotaDebito, String> usuarioModificador;
    public static volatile SingularAttribute<NotaDebito, Date> fechaModificacion;
    public static volatile SingularAttribute<NotaDebito, BigDecimal> valor;
    public static volatile SingularAttribute<NotaDebito, String> direccion;
    public static volatile SingularAttribute<NotaDebito, Date> fechaelaboracion;
    public static volatile SingularAttribute<NotaDebito, String> banco;
    public static volatile SingularAttribute<NotaDebito, String> nocuenta;
    public static volatile SingularAttribute<NotaDebito, String> usuarioCreador;
    public static volatile SingularAttribute<NotaDebito, Proveedores> proveedores;
    public static volatile SingularAttribute<NotaDebito, String> cliente;
    public static volatile SingularAttribute<NotaDebito, String> concepto;
    public static volatile SingularAttribute<NotaDebito, String> observaciones;
    public static volatile SingularAttribute<NotaDebito, Date> fechaCreacion;
    public static volatile SingularAttribute<NotaDebito, Long> id;
    public static volatile SingularAttribute<NotaDebito, Long> no_consecutivo;
    public static volatile SingularAttribute<NotaDebito, String> telefono;

}