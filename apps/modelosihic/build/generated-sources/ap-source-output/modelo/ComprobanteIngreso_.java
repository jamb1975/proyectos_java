package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Factura;
import modelo.FacturaItem;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(ComprobanteIngreso.class)
public class ComprobanteIngreso_ { 

    public static volatile SingularAttribute<ComprobanteIngreso, String> usuarioModificador;
    public static volatile SingularAttribute<ComprobanteIngreso, Date> fechaModificacion;
    public static volatile SingularAttribute<ComprobanteIngreso, FacturaItem> facturaItem;
    public static volatile SingularAttribute<ComprobanteIngreso, BigDecimal> valor;
    public static volatile SingularAttribute<ComprobanteIngreso, Date> fechaelaboracion;
    public static volatile SingularAttribute<ComprobanteIngreso, String> banco;
    public static volatile SingularAttribute<ComprobanteIngreso, String> usuarioCreador;
    public static volatile SingularAttribute<ComprobanteIngreso, String> nocheque;
    public static volatile SingularAttribute<ComprobanteIngreso, Factura> factura;
    public static volatile SingularAttribute<ComprobanteIngreso, String> notargeta;
    public static volatile SingularAttribute<ComprobanteIngreso, String> concepto;
    public static volatile SingularAttribute<ComprobanteIngreso, String> observaciones;
    public static volatile SingularAttribute<ComprobanteIngreso, Boolean> efectivo;
    public static volatile SingularAttribute<ComprobanteIngreso, Date> fechaCreacion;
    public static volatile SingularAttribute<ComprobanteIngreso, Long> id;
    public static volatile SingularAttribute<ComprobanteIngreso, Long> no_consecutivo;
    public static volatile SingularAttribute<ComprobanteIngreso, Integer> tipotargeta;

}