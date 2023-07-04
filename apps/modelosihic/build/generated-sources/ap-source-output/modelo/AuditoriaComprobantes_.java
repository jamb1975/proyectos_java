package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Usuarios;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(AuditoriaComprobantes.class)
public class AuditoriaComprobantes_ { 

    public static volatile SingularAttribute<AuditoriaComprobantes, String> noident;
    public static volatile SingularAttribute<AuditoriaComprobantes, String> numerofactura;
    public static volatile SingularAttribute<AuditoriaComprobantes, Usuarios> usuarioelimino;
    public static volatile SingularAttribute<AuditoriaComprobantes, BigDecimal> valor;
    public static volatile SingularAttribute<AuditoriaComprobantes, String> usuario;
    public static volatile SingularAttribute<AuditoriaComprobantes, Long> id;
    public static volatile SingularAttribute<AuditoriaComprobantes, Date> fechaElimino;
    public static volatile SingularAttribute<AuditoriaComprobantes, String> numerocomprobante;

}