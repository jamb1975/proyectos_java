package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HclCups;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclCodigosConsultas.class)
public class HclCodigosConsultas_ { 

    public static volatile SingularAttribute<HclCodigosConsultas, String> descripcion;
    public static volatile SingularAttribute<HclCodigosConsultas, String> codigo;
    public static volatile SingularAttribute<HclCodigosConsultas, String> usuarioModificador;
    public static volatile SingularAttribute<HclCodigosConsultas, Date> fechaModificacion;
    public static volatile SingularAttribute<HclCodigosConsultas, Date> fechaCreacion;
    public static volatile SingularAttribute<HclCodigosConsultas, Long> id;
    public static volatile SingularAttribute<HclCodigosConsultas, String> usuarioCreador;
    public static volatile SingularAttribute<HclCodigosConsultas, HclCups> hclcups_id;

}