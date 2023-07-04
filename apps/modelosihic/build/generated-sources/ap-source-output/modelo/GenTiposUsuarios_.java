package modelo;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Puc;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(GenTiposUsuarios.class)
public class GenTiposUsuarios_ { 

    public static volatile SingularAttribute<GenTiposUsuarios, BigInteger> estado;
    public static volatile SingularAttribute<GenTiposUsuarios, String> codigo;
    public static volatile SingularAttribute<GenTiposUsuarios, String> usuarioModificador;
    public static volatile SingularAttribute<GenTiposUsuarios, Date> fechaModificacion;
    public static volatile SingularAttribute<GenTiposUsuarios, Puc> conPuc;
    public static volatile SingularAttribute<GenTiposUsuarios, Date> fechaCreacion;
    public static volatile SingularAttribute<GenTiposUsuarios, Long> id;
    public static volatile SingularAttribute<GenTiposUsuarios, String> nombre;
    public static volatile SingularAttribute<GenTiposUsuarios, String> usuarioCreador;

}