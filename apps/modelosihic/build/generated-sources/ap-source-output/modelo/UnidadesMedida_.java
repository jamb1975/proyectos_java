package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Usuarios;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(UnidadesMedida.class)
public class UnidadesMedida_ { 

    public static volatile SingularAttribute<UnidadesMedida, String> descripcion;
    public static volatile SingularAttribute<UnidadesMedida, String> sigla;
    public static volatile SingularAttribute<UnidadesMedida, String> usuarioModificador;
    public static volatile SingularAttribute<UnidadesMedida, Date> fechaModificacion;
    public static volatile SingularAttribute<UnidadesMedida, Date> fechaCreacion;
    public static volatile SingularAttribute<UnidadesMedida, Long> id;
    public static volatile SingularAttribute<UnidadesMedida, Usuarios> usuarios;
    public static volatile SingularAttribute<UnidadesMedida, String> usuarioCreador;

}