package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.GenEapb;
import modelo.GenNivelesUsuarios;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(GenCuotasModeradorasEapb.class)
public class GenCuotasModeradorasEapb_ { 

    public static volatile SingularAttribute<GenCuotasModeradorasEapb, GenNivelesUsuarios> genNivelesUsuarios;
    public static volatile SingularAttribute<GenCuotasModeradorasEapb, String> usuarioModificador;
    public static volatile SingularAttribute<GenCuotasModeradorasEapb, Date> fechaModificacion;
    public static volatile SingularAttribute<GenCuotasModeradorasEapb, GenEapb> genEapb;
    public static volatile SingularAttribute<GenCuotasModeradorasEapb, BigDecimal> valor;
    public static volatile SingularAttribute<GenCuotasModeradorasEapb, Date> fechaCreacion;
    public static volatile SingularAttribute<GenCuotasModeradorasEapb, Long> id;
    public static volatile SingularAttribute<GenCuotasModeradorasEapb, String> usuarioCreador;

}