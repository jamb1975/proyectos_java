package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgnEspecialidades;
import modelo.HclCups;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclCupsEspecialidades.class)
public class HclCupsEspecialidades_ { 

    public static volatile SingularAttribute<HclCupsEspecialidades, String> usuarioModificador;
    public static volatile SingularAttribute<HclCupsEspecialidades, Date> fechaModificacion;
    public static volatile SingularAttribute<HclCupsEspecialidades, Date> fechaCreacion;
    public static volatile SingularAttribute<HclCupsEspecialidades, AgnEspecialidades> agnEspecialidades;
    public static volatile SingularAttribute<HclCupsEspecialidades, Long> id;
    public static volatile SingularAttribute<HclCupsEspecialidades, String> usuarioCreador;
    public static volatile SingularAttribute<HclCupsEspecialidades, HclCups> hclCups;

}