package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.GenMunicipios;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(GenCentrosPoblados.class)
public class GenCentrosPoblados_ { 

    public static volatile SingularAttribute<GenCentrosPoblados, String> codigo;
    public static volatile SingularAttribute<GenCentrosPoblados, String> tipo;
    public static volatile SingularAttribute<GenCentrosPoblados, String> usuarioModificador;
    public static volatile SingularAttribute<GenCentrosPoblados, Date> fechaModificacion;
    public static volatile SingularAttribute<GenCentrosPoblados, Date> fechaCreacion;
    public static volatile SingularAttribute<GenCentrosPoblados, Long> id;
    public static volatile SingularAttribute<GenCentrosPoblados, String> nombre;
    public static volatile SingularAttribute<GenCentrosPoblados, GenMunicipios> genMunicipios;
    public static volatile SingularAttribute<GenCentrosPoblados, String> usuarioCreador;

}