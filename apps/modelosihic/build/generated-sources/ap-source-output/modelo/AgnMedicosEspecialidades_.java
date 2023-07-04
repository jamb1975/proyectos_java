package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgnEspecialidades;
import modelo.AgnMedicos;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(AgnMedicosEspecialidades.class)
public class AgnMedicosEspecialidades_ { 

    public static volatile SingularAttribute<AgnMedicosEspecialidades, String> usuarioModificador;
    public static volatile SingularAttribute<AgnMedicosEspecialidades, Date> fechaModificacion;
    public static volatile SingularAttribute<AgnMedicosEspecialidades, Date> fechaCreacion;
    public static volatile SingularAttribute<AgnMedicosEspecialidades, AgnEspecialidades> agnEspecialidades;
    public static volatile SingularAttribute<AgnMedicosEspecialidades, Long> id;
    public static volatile SingularAttribute<AgnMedicosEspecialidades, AgnMedicos> agnMedicos;
    public static volatile SingularAttribute<AgnMedicosEspecialidades, String> usuarioCreador;

}