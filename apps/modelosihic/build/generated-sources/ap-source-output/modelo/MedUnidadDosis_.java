package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(MedUnidadDosis.class)
public class MedUnidadDosis_ { 

    public static volatile SingularAttribute<MedUnidadDosis, String> descripcion;
    public static volatile SingularAttribute<MedUnidadDosis, String> usuarioModificador;
    public static volatile SingularAttribute<MedUnidadDosis, Date> fechaModificacion;
    public static volatile SingularAttribute<MedUnidadDosis, Date> fechaCreacion;
    public static volatile SingularAttribute<MedUnidadDosis, Long> id;
    public static volatile SingularAttribute<MedUnidadDosis, String> usuarioCreador;

}