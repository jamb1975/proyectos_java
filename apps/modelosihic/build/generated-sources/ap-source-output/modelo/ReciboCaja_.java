package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Proveedores;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(ReciboCaja.class)
public class ReciboCaja_ { 

    public static volatile SingularAttribute<ReciboCaja, String> noident;
    public static volatile SingularAttribute<ReciboCaja, String> usuarioModificador;
    public static volatile SingularAttribute<ReciboCaja, Date> fechaModificacion;
    public static volatile SingularAttribute<ReciboCaja, BigDecimal> valor;
    public static volatile SingularAttribute<ReciboCaja, String> direccion;
    public static volatile SingularAttribute<ReciboCaja, Date> fechaelaboracion;
    public static volatile SingularAttribute<ReciboCaja, String> recibido;
    public static volatile SingularAttribute<ReciboCaja, String> usuarioCreador;
    public static volatile SingularAttribute<ReciboCaja, Proveedores> proveedores;
    public static volatile SingularAttribute<ReciboCaja, String> pagadoa;
    public static volatile SingularAttribute<ReciboCaja, String> concepto;
    public static volatile SingularAttribute<ReciboCaja, String> observaciones;
    public static volatile SingularAttribute<ReciboCaja, Date> fechaCreacion;
    public static volatile SingularAttribute<ReciboCaja, Long> id;
    public static volatile SingularAttribute<ReciboCaja, Long> no_consecutivo;
    public static volatile SingularAttribute<ReciboCaja, String> telefono;

}