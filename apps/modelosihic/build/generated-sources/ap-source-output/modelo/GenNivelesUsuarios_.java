package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(GenNivelesUsuarios.class)
public class GenNivelesUsuarios_ { 

    public static volatile SingularAttribute<GenNivelesUsuarios, String> usuarioModificador;
    public static volatile SingularAttribute<GenNivelesUsuarios, Date> fechaModificacion;
    public static volatile SingularAttribute<GenNivelesUsuarios, BigDecimal> valor;
    public static volatile SingularAttribute<GenNivelesUsuarios, Date> fechaCreacion;
    public static volatile SingularAttribute<GenNivelesUsuarios, Long> id;
    public static volatile SingularAttribute<GenNivelesUsuarios, Integer> nivel;
    public static volatile SingularAttribute<GenNivelesUsuarios, String> usuarioCreador;

}