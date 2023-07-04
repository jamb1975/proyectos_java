package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.GenDepartamentos;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(GenMunicipios.class)
public class GenMunicipios_ { 

    public static volatile SingularAttribute<GenMunicipios, String> codigo;
    public static volatile SingularAttribute<GenMunicipios, String> usuarioModificador;
    public static volatile SingularAttribute<GenMunicipios, Date> fechaModificacion;
    public static volatile SingularAttribute<GenMunicipios, Date> fechaCreacion;
    public static volatile SingularAttribute<GenMunicipios, GenDepartamentos> genDepartamentos;
    public static volatile SingularAttribute<GenMunicipios, Long> id;
    public static volatile SingularAttribute<GenMunicipios, String> nombre;
    public static volatile SingularAttribute<GenMunicipios, String> usuarioCreador;

}