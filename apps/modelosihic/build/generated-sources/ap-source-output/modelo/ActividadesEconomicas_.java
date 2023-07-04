package modelo;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(ActividadesEconomicas.class)
public class ActividadesEconomicas_ { 

    public static volatile SingularAttribute<ActividadesEconomicas, String> codigo_ciiu_0079;
    public static volatile SingularAttribute<ActividadesEconomicas, String> descripcion;
    public static volatile SingularAttribute<ActividadesEconomicas, BigDecimal> tarifa_pormil;
    public static volatile SingularAttribute<ActividadesEconomicas, String> codigo_grupotarifa;
    public static volatile SingularAttribute<ActividadesEconomicas, Long> id;
    public static volatile SingularAttribute<ActividadesEconomicas, String> codigo_ciiu_219;
    public static volatile SingularAttribute<ActividadesEconomicas, BigDecimal> tarifa_autoretencion;

}