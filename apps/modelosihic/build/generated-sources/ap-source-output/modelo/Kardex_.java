package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.FacturaItem;
import modelo.Producto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Kardex.class)
public class Kardex_ { 

    public static volatile SingularAttribute<Kardex, Float> cantidad_salida;
    public static volatile SingularAttribute<Kardex, String> tipo;
    public static volatile SingularAttribute<Kardex, Date> fechaModificacion;
    public static volatile SingularAttribute<Kardex, FacturaItem> facturaItem;
    public static volatile SingularAttribute<Kardex, String> lote;
    public static volatile SingularAttribute<Kardex, String> desc_kar;
    public static volatile SingularAttribute<Kardex, BigDecimal> valor_saldo;
    public static volatile SingularAttribute<Kardex, Producto> producto;
    public static volatile SingularAttribute<Kardex, Date> fechavencimiento;
    public static volatile SingularAttribute<Kardex, BigDecimal> valor_salida;
    public static volatile SingularAttribute<Kardex, String> usuarioCreador;
    public static volatile SingularAttribute<Kardex, BigDecimal> valor_entrada;
    public static volatile SingularAttribute<Kardex, Date> fecha;
    public static volatile SingularAttribute<Kardex, Float> cantidad_entrada;
    public static volatile SingularAttribute<Kardex, Long> no_fact_kar;
    public static volatile SingularAttribute<Kardex, Float> cantidad_saldo;
    public static volatile SingularAttribute<Kardex, Date> fechaCreacion;
    public static volatile SingularAttribute<Kardex, Long> id;
    public static volatile SingularAttribute<Kardex, BigDecimal> valor_unidad;

}