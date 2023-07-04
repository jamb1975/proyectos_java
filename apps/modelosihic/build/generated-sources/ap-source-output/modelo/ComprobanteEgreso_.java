package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ComprobanteCausacionEgreso;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(ComprobanteEgreso.class)
public class ComprobanteEgreso_ { 

    public static volatile SingularAttribute<ComprobanteEgreso, ComprobanteCausacionEgreso> comprobanteCausacionEgreso;
    public static volatile SingularAttribute<ComprobanteEgreso, String> usuarioModificador;
    public static volatile SingularAttribute<ComprobanteEgreso, Date> fechaModificacion;
    public static volatile SingularAttribute<ComprobanteEgreso, BigDecimal> valor;
    public static volatile SingularAttribute<ComprobanteEgreso, Date> fechaelaboracion;
    public static volatile SingularAttribute<ComprobanteEgreso, String> banco;
    public static volatile SingularAttribute<ComprobanteEgreso, String> usuarioCreador;
    public static volatile SingularAttribute<ComprobanteEgreso, String> nocheque;
    public static volatile SingularAttribute<ComprobanteEgreso, String> notargeta;
    public static volatile SingularAttribute<ComprobanteEgreso, String> afavorde;
    public static volatile SingularAttribute<ComprobanteEgreso, String> concepto;
    public static volatile SingularAttribute<ComprobanteEgreso, Date> fechaCreacion;
    public static volatile SingularAttribute<ComprobanteEgreso, Long> id;
    public static volatile SingularAttribute<ComprobanteEgreso, Long> no_consecutivo;
    public static volatile SingularAttribute<ComprobanteEgreso, Integer> tipotargeta;

}