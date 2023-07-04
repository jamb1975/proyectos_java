package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Puc;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(LibroMayorBalances.class)
public class LibroMayorBalances_ { 

    public static volatile SingularAttribute<LibroMayorBalances, BigDecimal> saldoanteriorcredito;
    public static volatile SingularAttribute<LibroMayorBalances, BigDecimal> movimientosdebito;
    public static volatile SingularAttribute<LibroMayorBalances, BigDecimal> saldocredito;
    public static volatile SingularAttribute<LibroMayorBalances, Puc> conPuc;
    public static volatile SingularAttribute<LibroMayorBalances, BigDecimal> saldodebito;
    public static volatile SingularAttribute<LibroMayorBalances, Date> fechaelaboracion;
    public static volatile SingularAttribute<LibroMayorBalances, BigDecimal> movimientoscredito;
    public static volatile SingularAttribute<LibroMayorBalances, Integer> mes;
    public static volatile SingularAttribute<LibroMayorBalances, Long> id;
    public static volatile SingularAttribute<LibroMayorBalances, BigDecimal> saldoanteriordebito;
    public static volatile SingularAttribute<LibroMayorBalances, Integer> año;
    public static volatile SingularAttribute<LibroMayorBalances, String> detalle;

}