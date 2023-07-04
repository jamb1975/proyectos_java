package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Usuarios;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Auditoria.class)
public class Auditoria_ { 

    public static volatile SingularAttribute<Auditoria, String> descripcion;
    public static volatile SingularAttribute<Auditoria, String> tipo_proceso;
    public static volatile SingularAttribute<Auditoria, Long> id;
    public static volatile SingularAttribute<Auditoria, Usuarios> usuarios;
    public static volatile SingularAttribute<Auditoria, String> modulo;
    public static volatile SingularAttribute<Auditoria, Date> fechasuceso;

}